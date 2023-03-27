
public class Main {
    public static void main(String[] args) {
        String caminhoDoArquivo = args.length > 0 ? args[0] : null;

        while (true) {
            start(caminhoDoArquivo);
            caminhoDoArquivo = null;
        }
    }

    public static void start(String caminhoDoArquivo) {
        while (caminhoDoArquivo == null || caminhoDoArquivo.equals("")) {
            System.out.println("Digite o caminho do arquivo de texto, ou S para sair");
            caminhoDoArquivo = Keyboard.getUmString();
            System.out.println();
        } 
        if (caminhoDoArquivo.equalsIgnoreCase("s"))
            System.exit(0);

        Labirinto labirinto;
        try {
            FileManager arqLabirinto = new FileManager(caminhoDoArquivo);
            try { 
                // Achar a solução para o labirinto, caso exista.
                labirinto = new Labirinto(arqLabirinto);
                labirinto.acharSolucao();
            } catch (Exception e) {
                System.err.println("Sem Solução: " + e.getMessage());
            }
        } catch (FileManager.ArchiveException e) {
            System.err.println("Arquivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
        System.out.println("========================================\n");
    }
}
