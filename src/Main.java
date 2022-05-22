public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Digite o nome do arquivo de texto, exemplo Teste1.txt ou S para sair");
            String nomeDoArquivo = Teclado.getUmString();
            System.out.println();

            Labirinto labirinto;
            try
            {
                if (nomeDoArquivo.equals(""))
                    throw new Exception("Especifique um nome de arquivo");
                if (nomeDoArquivo.equalsIgnoreCase("s") )
                    break;

                Arquivo arqLabirinto = new Arquivo(nomeDoArquivo);
                try
                {
                    // Achar a solução para o labirinto, caso exista.
                    labirinto = new Labirinto(arqLabirinto);
                    labirinto.acharSolucao();
                }
                catch (Exception e) {
                    System.err.println("Sem Solução: " + e.getMessage());
                }
            }
            catch (Exception e)
            {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }
}
