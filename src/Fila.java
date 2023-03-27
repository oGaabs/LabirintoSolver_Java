import java.lang.reflect.*;
import java.util.Arrays;
// FIFO
public class Fila<X> implements Cloneable {
    private Object[] items;
    //private int size = 0;
    private int tamanhoInicial;
    private int ultimo = -1;
    private int primeiro = 0;
    

    public static class QueueException extends Exception {
        public QueueException(String errorMessage) {
            super(errorMessage);
        }
    }

    public Fila(int tamanho) throws QueueException {
        if (tamanho <= 0)
            throw new QueueException("Tamanho invalido");

        this.items = new Object[tamanho];
    }   

    public Fila() {
        this.items = new Object[10];
    }

    private void redimensioneSe(float fator) {
        int novoTamanho = Math.round(this.items.length * fator);
        Object[] newArray = Arrays.copyOf(this.items, novoTamanho);

        this.items = newArray;
    }   



    public void guardeUmItem(X x) throws QueueException
    {
        if (x == null)
            throw new QueueException("Falta o que guardar");

        if (this.ultimo + 1 == this.items.length) // cheia
            this.redimensioneSe(2.0F);

        this.ultimo++;

        if (x instanceof Cloneable)
            this.items[this.ultimo] = meuCloneDeX(x);
        else
            this.items[this.ultimo] = x;
    }

    public X recupereUmItem() throws QueueException
    {
        if (this.ultimo == -1) // vazia
            throw new QueueException("Nada a recuperar");

        X ret = null;
        if (this.items[this.ultimo] instanceof Cloneable)
            ret = meuCloneDeX((X) this.items[this.primeiro]);
        else
            ret = (X) this.items[this.primeiro];

        return ret;
    }

    public void removaUmItem() throws QueueException
    {
        if (this.ultimo == -1) // vazia
            throw new QueueException("Nada a remover");

        for (int i = 0; i <= this.ultimo - 1; i++) {
            this.items[i] = this.items[i + 1];
        }

        this.items[this.ultimo] = null;
        this.ultimo--; 

        if (this.items.length > this.tamanhoInicial &&
                this.ultimo + 1 <= Math.round(this.items.length * 0.25F))
            this.redimensioneSe(0.5F);
    }

    public void clear(){
        this.items = new Object[10];
        this.ultimo = -1;
    }

    public int tamanho() {
        return this.ultimo + 1;
    }
    /* 
    public boolean isVazia() {
        return size == 0;
    }
    
    public boolean isCheia() {
        return size == items.length;
    }*/

    public boolean isVazia() {
        return this.ultimo == -1;
    }
    
    public boolean isCheia() {
        return this.ultimo + 1 == this.items.length;
    }
    
    public String debugString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.ultimo + 1).append(" elemento(s)");
        
        if (this.ultimo != -1) {
            sb.append(": (");
            for (int i = 0; i <= this.ultimo; i++) {
                sb.append(this.items[i]).append(",");
            }
            sb.setLength(sb.length() - 1); // remove a última vírgula
            sb.append(")");
        }
        
        return sb.toString();
    }


    public X peek() {
        if (this.ultimo == -1)
            throw new IllegalStateException("Fila vazia");
        
        return (X) items[0];
    }
    
    public String toString() {
        String ret = (this.ultimo + 1) + " elemento(s)";

        if (this.ultimo != -1)
            ret += ", sendo o ultimo " + this.items[this.ultimo] + ", sendo o primeiro "
                    + this.items[this.primeiro];

        return ret;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        Fila<X> pil = (Fila<X>) obj;

        if (this.ultimo != pil.ultimo)
            return false;
        if (this.primeiro != pil.primeiro)
            return false;

        if (this.tamanhoInicial != pil.tamanhoInicial)
            return false;

        for (int i = 0; i < this.ultimo; i++)
            if (!this.items[i].equals(pil.items[i]))
                return false;

        return true;
    }

    public int hashCode() {
        int ret = 23;

        ret = ret * 7 + Integer.valueOf(this.ultimo).hashCode();
        ret = ret * 7 + Integer.valueOf(this.tamanhoInicial).hashCode();

        for (int i = 0; i < this.ultimo; i++)
            ret = ret * 7 + this.items[i].hashCode();

        if (ret < 0)
            ret = -ret;

        return ret;
    }

    public Fila(Fila<X> model) throws CloneNotSupportedException {
        if (model == null)
            throw new CloneNotSupportedException("Modelo ausente");

        this.items = new Object[model.items.length]; 

        for (int i = 0; i < model.items.length; i++){
            this.items[i] = model.items[i];
        }

        this.tamanhoInicial = model.tamanhoInicial;
        this.ultimo = model.ultimo;
    }

    private X meuCloneDeX(X x) {
        X ret = null;

        try {
            Class<?> classe = x.getClass();
            Class<?>[] tipoDosParms = null;
            Method metodo = classe.getMethod("clone", tipoDosParms);
            Object[] parms = null;
            ret = (X) metodo.invoke(x, parms);
        } catch (NoSuchMethodException erro) {
        } catch (IllegalAccessException erro) {
        } catch (InvocationTargetException erro) {
        }

        return ret;
    }

    @Override
    public Object clone() {
        Fila<X> ret = null;

        try {
            ret = new Fila<X>(this);
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }

        return ret;
    }
}
