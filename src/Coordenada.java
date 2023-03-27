public class Coordenada {
    private int x;
    private int y;

    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getLinha() {
        return x;
    }

    public int getColuna() {
        return y;
    }

    public void setLinha(int x) {
        if (x < 0) 
            throw new IllegalArgumentException("Valor inválido para coordenada x: " + x);
        this.x = x;
    }

    public void setColuna(int y) {
        if (y < 0) 
            throw new IllegalArgumentException("Valor inválido para coordenada y: " + y);
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || this.getClass() != obj.getClass())
            return false;

        Coordenada cord = (Coordenada) obj;

        if (this.x != cord.x || this.y != cord.y)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int ret = 666;

        ret = ret * 7 + Integer.valueOf(this.x).hashCode();
        ret = ret * 7 + Integer.valueOf(this.y).hashCode();

        if (ret < 0)
            ret = -ret;

        return ret;
    }

    @Override
    public String toString() {
        String coordX = String.format("%02d", x);
        String coordY = String.format("%02d", y);

        return "(" + coordX + "," + coordY + ")";
    }

    // construtor de copia
    public Coordenada(Coordenada model) throws Exception {
        if (model == null)
            throw new Exception("Modelo ausente");

        this.x = model.x;
        this.y = model.y;
    }

    @Override
    public Object clone() {
        Coordenada ret = null;

        try {
            ret = new Coordenada(this);
        } catch (Exception ignored) {
        }

        return ret;
    }
}
