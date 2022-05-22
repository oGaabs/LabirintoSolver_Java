import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;

public class Arquivo{
    private BufferedReader arquivoReader;
    private final String caminhoDeEntrada;

    public Arquivo(String nomeDoArquivo) throws Exception {
        if (nomeDoArquivo.isEmpty() || nomeDoArquivo.isBlank())
            throw new Exception("Nome do arquivo nulo");

        try
        {
            this.caminhoDeEntrada =  FileSystems.getDefault().getPath("src/Testes/" + nomeDoArquivo).toAbsolutePath().toString();
            this.arquivoReader = new BufferedReader(new FileReader(this.caminhoDeEntrada));
        }
        catch (IOException erro){
            throw new Exception("Arquivo nao encontrado");
        }
    }

    public String pegaProximaLinha(){
        String ret = null;

        try
        {
            ret = arquivoReader.readLine();
        }
        catch (IOException ignored)
        {}

        return ret;
    }

    public int pegaUmInt() throws Exception {
        int ret = 0;

        try
        {
            ret = Integer.parseInt(arquivoReader.readLine());
        }
        catch (IOException ignored)
        {} // sei que nao vai dar erro
        catch (NumberFormatException ignored)
        {
            throw new Exception ("Int invalido!");
        }

        return ret;
    }

    public void lerNovamente() throws Exception {
        try
        {
            this.arquivoReader.close();
            this.arquivoReader = new BufferedReader(new FileReader(this.caminhoDeEntrada));
        }
        catch (IOException erro){
            throw new Exception(erro);
        }
    }

    public void fecharArquivo() throws IOException {
        arquivoReader.close();
    }

    @Override
    public String toString() {
        String ret = caminhoDeEntrada + "\n";
        try
        {
            BufferedReader arqReader = new BufferedReader(new FileReader(this.caminhoDeEntrada));
            String linha;
            while ((linha = arqReader.readLine()) != null) {
                ret += linha + "\n";
            }
            this.arquivoReader.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) return false;

        Arquivo arq = (Arquivo) obj;

        if (!this.arquivoReader.equals(arq.arquivoReader))
            return false;

        if (!this.caminhoDeEntrada.equals(arq.caminhoDeEntrada))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int ret = 666;

        ret = 7* ret + this.caminhoDeEntrada.hashCode();
        ret = 7* ret + this.arquivoReader.hashCode();

        if (ret < 0) ret = -ret;

        return ret;
    }
}
