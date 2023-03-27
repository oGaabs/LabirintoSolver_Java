import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {
    private BufferedReader arquivoReader;
    private final String filePath;

    public FileManager(String filePath) throws ArchiveException {
        if (filePath.isEmpty() || filePath.isBlank())
            throw new ArchiveException("Nome do arquivo invalido!");

        try {
            this.filePath = filePath;
            this.arquivoReader = new BufferedReader(new FileReader(this.filePath));
        } catch (IOException erro) {
            throw new ArchiveException("Arquivo nao encontrado");
        }
    }

    public static class ArchiveException extends Exception {
        public ArchiveException(String errorMessage) {
            super(errorMessage);
        }
    }

    public String readLine() {
        String ret = null;

        try {
            ret = arquivoReader.readLine();  
        } catch (IOException ignored) {
        }

        return ret;
    }

    public int readIntLine() throws ArchiveException {
        int ret = 0;

        try {
            ret = Integer.parseInt(arquivoReader.readLine());
        } catch (IOException ignored) {
        } catch (NumberFormatException ignored) {
            throw new ArchiveException("Int invalido!");
        }

        return ret;
    }

    public void readNewFile(String pathFile) throws ArchiveException {
        if (pathFile.isEmpty() || pathFile.isBlank())
            throw new ArchiveException("Nome do arquivo invalido!");

        try {
            this.arquivoReader.close();
            this.arquivoReader = new BufferedReader(new FileReader(this.filePath));
        } catch (IOException erro) {
            throw new ArchiveException("Arquivo nao encontrado");
        }
    }

    public void closeFile() throws IOException {
        try {
            this.arquivoReader.close();
        } catch (IOException erro) {
            throw new IOException("Erro ao fechar o arquivo");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        FileManager arq = (FileManager) obj;

        if (!this.arquivoReader.equals(arq.arquivoReader))
            return false;

        if (!this.filePath.equals(arq.filePath))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int ret = 666;

        ret = 7 * ret + this.filePath.hashCode();
        ret = 7 * ret + this.arquivoReader.hashCode();

        if (ret < 0)
            ret = -ret;

        return ret;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();

        ret.append(this.filePath).append(System.lineSeparator());

        try {
            BufferedReader arqReader = new BufferedReader(new FileReader(this.filePath));
            arqReader.lines().forEach(line -> ret.append(line).append(System.lineSeparator()));
            arqReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ret.toString();
    }
}
