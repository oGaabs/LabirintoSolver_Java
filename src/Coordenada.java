public class Coordenada{
    private int x;
    private int y;

    public Coordenada (int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getLinha() { return x; }
    public int getColuna() { return y; }

    public void setLinha(int x) { this.x = x; }
    public void setColuna(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) return false;

        Coordenada cord = (Coordenada) obj;

        if (this.x != cord.x) return false;
        if (this.y != cord.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int ret = 666;

        ret = ret*7+ Integer.valueOf(this.x).hashCode();
        ret = ret*7+ Integer.valueOf(this.y).hashCode();

        if (ret<0) ret=-ret;

        return ret;
    }

    @Override
    public String toString() {
        String coordX = this.x < 10 ? "0" + this.x : this.x +"";
        String coordY = this.y < 10 ? "0" + this.y : this.y +"";

        return "(" + coordX + "," + coordY + ")";
    }

    // construtor de copia
    public Coordenada (Coordenada modelo) throws Exception
    {
        if(modelo == null)
            throw new Exception("Modelo ausente");

        this.x = modelo.x;
        this.y = modelo.y;
    }

    public Object clone ()
    {
        Coordenada ret = null;

        try
        {
            ret = new Coordenada(this);
        }
        catch(Exception ignored)
        {}

        return ret;
    }
}
