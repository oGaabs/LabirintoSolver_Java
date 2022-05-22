public class Labirinto implements Cloneable {
    private char[][] labirinto;
    private int qtdLinhas;
    private int qtdColunas;
    private int numeroDeEntradas;
    private int numeroDeSaidas;
    private Coordenada atual;
    private Coordenada posicaoEntrada;
    private boolean encontrouSaida = false;

    public Labirinto(Arquivo arqLabirinto) throws Exception {
        // Pega o numero de linhas, lendo a primeira linha
        this.qtdLinhas = arqLabirinto.pegaUmInt();

        // Pega o numero de colunas, lendo a primeira linha
        String segundaLinha = arqLabirinto.pegaProximaLinha();

        this.qtdColunas = segundaLinha.length();

        if (this.qtdLinhas == 0 || this.qtdColunas == 0)
            throw new Exception("Labirinto inválido");

        // Cria o labirinto, desconsiderando a primeira linha
        this.labirinto = new char[qtdLinhas][qtdColunas];

        inserirLinha(segundaLinha, 0);

        String linha;
        int numLinha = 1;

        while ((linha = arqLabirinto.pegaProximaLinha()) != null) {
            inserirLinha(linha, numLinha);
            numLinha++;
        }

        arqLabirinto.fecharArquivo();

        if (numLinha != this.qtdLinhas)
            throw new Exception("Quantidade de linhas diferente da especificada");

        // Verifica se o labirinto possui uma unica entrada e saida
        verificarSePossuiEntradasSaidas();
        checarParedes();

        System.out.println("Labirinto Original! Encontrado o caminho...");
        imprimeLabirinto();
    }

    private void inserirLinha(String linha, int numeroDaLinha) throws Exception {
        verificaoDeLinha(linha, numeroDaLinha);

        this.labirinto[numeroDaLinha] = linha.toCharArray();
    }

    private void verificaoDeLinha(String linha, int numeroDaLinha) throws Exception {
        int tamanhoLinha = linha.length();

        //Verifica se o numero de colunas é válido
        if (tamanhoLinha != qtdColunas)
            throw new Exception("O Labirinto possui linhas maiores que outras");

        // Verifica se o numero de linhas é válido
        if (numeroDaLinha < 0 || numeroDaLinha >= qtdLinhas)
            throw new Exception("O Labirinto possui mais linhas do que o especificado");

        String caracteresPermitidos = "# ES";

        for (int numeroColuna = 0; numeroColuna < tamanhoLinha; numeroColuna++) {
            char caractere = Character.toUpperCase(linha.charAt(numeroColuna));

            // Verifica se a linha possui um caractere invalido
            if (caracteresPermitidos.indexOf(caractere) == -1)
                throw new Exception("Caractere ('" + caractere + "') inválido, encontrado no labirinto");

            switch (caractere) {
                case 'E' -> {
                    numeroDeEntradas++;
                    atual = new Coordenada(numeroDaLinha, numeroColuna);
                    posicaoEntrada = new Coordenada(numeroDaLinha, numeroColuna);
                }
                case 'S' -> numeroDeSaidas++;
                default -> {}
            }
        }
    }

    public void acharSolucao() throws Exception {
        Pilha<Coordenada> caminho = new Pilha<>(qtdColunas * qtdLinhas);
        Pilha<Fila<Coordenada>> possibilidades = new Pilha<>(qtdColunas * qtdLinhas);

        Fila<Coordenada> filaDeAdjacentes = new Fila<>(3);

        acharPosicoesAdjacentes(filaDeAdjacentes);
        atual = filaDeAdjacentes.recupereUmItem();
        filaDeAdjacentes.removaUmItem();

        labirinto[atual.getLinha()][atual.getColuna()] = '*';

        caminho.guardeUmItem(atual);
        possibilidades.guardeUmItem(filaDeAdjacentes);

        while (!encontrouSaida) {
            andarPosicoesAdjacentes(new Fila<Coordenada>(3), possibilidades, caminho);
        }

        System.out.println("Solução encontrada! Mostrando o caminho...");
        imprimeLabirinto();

        retrocederCaminho(caminho);
    }

    private void andarPosicoesAdjacentes(Fila<Coordenada> filaDeAdjacentes, Pilha<Fila<Coordenada>> possibilidades, Pilha<Coordenada> caminho) throws Exception {
        acharPosicoesAdjacentes(filaDeAdjacentes);

        // Modo regressivo
        if (filaDeAdjacentes.isVazia()) {
            do {
                atual = caminho.recupereUmItem();
                caminho.removaUmItem();
                if (caminho.isVazia())
                    throw new Exception("Saida não encontrada");

                inserirCaracterNaCoordenada(' ', atual);

                filaDeAdjacentes = possibilidades.recupereUmItem();
                possibilidades.removaUmItem();
                if (possibilidades.isVazia())
                    throw new Exception("Não existe caminho que leva da entrada  até a saída ");
            }
            while (filaDeAdjacentes.isVazia());
        }

        // Modo progressivo
        atual = filaDeAdjacentes.recupereUmItem();
        if (Character.toUpperCase(labirinto[atual.getLinha()][atual.getColuna()]) == 'S') {
            encontrouSaida = true;
            return;
        }
        filaDeAdjacentes.removaUmItem();
        inserirCaracterNaCoordenada('*', atual);
        caminho.guardeUmItem(atual);
        possibilidades.guardeUmItem(filaDeAdjacentes);
    }

    private void retrocederCaminho(Pilha<Coordenada> caminho) throws Exception {
        Pilha<Coordenada> inverso = new Pilha<>();
        while (!caminho.isVazia()) {
            inverso.guardeUmItem(caminho.recupereUmItem());
            caminho.removaUmItem();
        }

        // Indice utilizado apenas para printar de 5 em 5 coordenadas
        int indice = 0;
        while (!inverso.isVazia()) {
            System.out.print(inverso.recupereUmItem());
            inverso.removaUmItem();
            indice++;
            if (indice % 5 == 0)
                System.out.print('\n');
        }
        System.out.println('\n');
    }

    private void inserirCaracterNaCoordenada(char caracter, Coordenada coordenada) {
        labirinto[coordenada.getLinha()][coordenada.getColuna()] = caracter;
    }

    private void acharPosicoesAdjacentes(Fila<Coordenada> filaDeAdjacentes) throws Exception {
        int linha = atual.getLinha();
        int coluna = atual.getColuna();

        // Posição à direita
        adicionarPosicaoAdjacente(filaDeAdjacentes, linha, coluna + 1);
        // Posição à cima
        adicionarPosicaoAdjacente(filaDeAdjacentes, linha - 1, coluna );
        // Posição à baixo
        adicionarPosicaoAdjacente(filaDeAdjacentes, linha + 1, coluna );
        // Posição à esquerda
        adicionarPosicaoAdjacente(filaDeAdjacentes, linha, coluna - 1);
    }

    private void adicionarPosicaoAdjacente(Fila<Coordenada> filaDeAdjacentes, int linha, int coluna) throws Exception {
        if (linha < 0 || linha > qtdLinhas)
            return;
        if (coluna < 0 || coluna > qtdColunas)
            return;

        char caracter = labirinto[linha][coluna];

        if ("#*E".indexOf(caracter) == -1)
            filaDeAdjacentes.guardeUmItem(new Coordenada(linha, coluna));
    }

    private void verificarSePossuiEntradasSaidas() throws Exception {
        if (numeroDeEntradas == 0)
            throw new Exception("Não existe entrada no labirinto");
        if (numeroDeEntradas > 1)
            throw new Exception("O labirinto possui mais de uma entrada");

        if (numeroDeSaidas == 0)
            throw new Exception("Não existe saida no labirinto");
        if (numeroDeSaidas > 1)
            throw new Exception("O labirinto possui mais de uma saida");
    }

    private void checarParedes() throws Exception {
        for (int j = 0; j < qtdColunas; j++) {
            // Parede de Cima
            if (labirinto[0][j] == ' ')
                throw new Exception("Não existe parede em cima do labirinto");
            // Parede de Baixo
            if (labirinto[qtdLinhas - 1][j] == ' ')
                throw new Exception("Não existe parede em baixo do labirinto");
        }

        for (int i = 0; i < qtdLinhas; i++) {
            // Parede Esquerda
            if (labirinto[i][0] == ' ')
                throw new Exception("Não existe parede na esquerda do labirinto");
            // Parede Direita
            if (labirinto[i][qtdColunas - 1] == ' ')
                throw new Exception("Não existe parede na direita do labirinto");
        }
    }

    private void imprimeLabirinto() {
        System.out.print(this + "\n");
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < qtdLinhas; i++) {
            for (int j = 0; j < qtdColunas; j++) {
                ret += this.labirinto[i][j];
            }
            ret += "\n";
        }
        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) return false;

        Labirinto lab = (Labirinto) obj;

        if (this.qtdLinhas != lab.qtdLinhas) return false;
        if (this.qtdColunas != lab.qtdColunas) return false;
        if (this.numeroDeEntradas != lab.numeroDeEntradas) return false;
        if (this.numeroDeSaidas != lab.numeroDeSaidas) return false;
        if (this.encontrouSaida != lab.encontrouSaida) return false;

        for (int i = 0; i < qtdLinhas; i++) {
            for (int j = 0; j < qtdColunas; j++) {
                if (this.labirinto[i][j] != lab.labirinto[i][j]) return false;
            }
        }

        if (!this.atual.equals(lab.atual)) return false;
        if (!this.posicaoEntrada.equals(lab.posicaoEntrada)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int ret = 666;

        ret = ret * 7 + Integer.valueOf(qtdLinhas).hashCode();
        ret = ret * 7 + Integer.valueOf(qtdColunas).hashCode();
        ret = ret * 7 + Integer.valueOf(numeroDeEntradas).hashCode();
        ret = ret * 7 + Integer.valueOf(numeroDeSaidas).hashCode();

        for (int i = 0; i < qtdLinhas; i++) {
            for (int j = 0; j < qtdColunas; j++) {
                ret = ret * 7 + Character.hashCode(this.labirinto[i][j]);
            }
        }

        if (atual != null)
            ret = ret * 7 + atual.hashCode();

        ret = ret * 7 + posicaoEntrada.hashCode();
        ret = ret * 7 + (encontrouSaida ? 1 : 0);

        if (ret < 0) ret = -ret;

        return ret;
    }

    // construtor de copia
    public Labirinto(Labirinto modelo) throws Exception {
        if (modelo == null)
            throw new Exception("Modelo ausente");

        this.qtdLinhas = modelo.qtdLinhas;
        this.qtdColunas = modelo.qtdColunas;
        this.numeroDeEntradas = modelo.numeroDeEntradas;
        this.numeroDeSaidas = modelo.numeroDeSaidas;
        this.encontrouSaida = modelo.encontrouSaida;
        this.posicaoEntrada = modelo.posicaoEntrada;
        this.atual = modelo.atual;

        this.labirinto = new char[qtdLinhas][qtdColunas];
        for (int i = 0; i < qtdLinhas; i++) {
            for (int j = 0; j < qtdColunas; j++) {
                this.labirinto[i][j] = modelo.labirinto[i][j];
            }
        }
    }

    public Object clone() {
        Labirinto ret = null;

        try {
            ret = new Labirinto(this);
        }
        catch (Exception ignored) {}

        return ret;
    }
}

