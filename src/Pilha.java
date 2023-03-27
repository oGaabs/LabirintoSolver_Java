import java.lang.reflect.*;
import java.util.Arrays;

// LIFO
public class Pilha<X> implements Cloneable {
    private Object[] items;
    private int tamanhoInicial;
    private int ultimo = -1; // vazio

    public static class StackException extends Exception {
        public StackException(String errorMessage) {
            super(errorMessage);
        }
    }

    public Pilha(int tamanho) throws StackException {
        if (tamanho <= 0)
            throw new StackException("Tamanho invalido");

        this.items = new Object[tamanho];
        this.tamanhoInicial = tamanho;
    }

    public Pilha() {
        this.items = new Object[10];
        this.tamanhoInicial = 10;
    }

    private void redimensionar(float fator) {
        int novoTamanho = Math.round(this.items.length * fator);
        Object[] novoArray = Arrays.copyOf(this.items, novoTamanho);

        this.items = novoArray;
    }

    private X meuCloneDeX(X x) {
        X ret = null;

        try {
            Class<?> classe = x.getClass();
            Class<?>[] tipoDosParms = null;
            Method metodo = classe.getMethod("clone", tipoDosParms);
            Object[] parms = null;
            ret = (X) metodo.invoke(x, parms);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException erro) {
        }  

        return ret;
    }

    public void guardeUmItem(X x) throws StackException {
        if (x == null)
            throw new StackException("Falta o que guardar");

        if (this.ultimo + 1 == this.items.length) // cheia
            this.redimensionar(2.0F);

        this.ultimo++;

        if (x instanceof Cloneable)
            this.items[this.ultimo] = meuCloneDeX(x);
        else
            this.items[this.ultimo] = x;
    }

    public X recupereUmItem() throws StackException {
        if (this.ultimo == -1) // vazia
            throw new StackException("Nada a recuperar");

        X ret = null;
        if (this.items[this.ultimo] instanceof Cloneable)
            ret = meuCloneDeX((X) this.items[this.ultimo]);
        else
            ret = (X) this.items[this.ultimo];

        return ret;
    }

    public void removaUmItem() throws StackException {
        if (this.ultimo == -1) // vazia
            throw new StackException("Nada a remover");

        this.items[this.ultimo] = null;
        this.ultimo--;

        if (this.items.length > this.tamanhoInicial &&
                this.ultimo + 1 <= Math.round(this.items.length * 0.25F))
            this.redimensionar(0.5F);
    }

    public boolean isVazia() {
        if (this.ultimo == -1)
            return true;

        return false;
    }

    public boolean isCheia() {
        if (this.ultimo + 1 == this.items.length)
            return true;

        return false;
    }

    public String toString() {
        String ret = (this.ultimo + 1) + " elemento(s)";

        if (this.ultimo != -1)
            ret += ", sendo o ultimo " + this.items[this.ultimo];

        return ret;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        Pilha<X> pil = (Pilha<X>) obj;

        if (this.ultimo != pil.ultimo)
            return false;

        if (this.tamanhoInicial != pil.tamanhoInicial)
            return false;

        for (int i = 0; i < this.ultimo; i++)
            if (!this.items[i].equals(pil.items[i]))
                return false;

        return true;
    }

    public int hashCode() {
        int ret = 666;

        ret = ret * 7 + Integer.valueOf(this.ultimo).hashCode();
        ret = ret * 7 + Integer.valueOf(this.tamanhoInicial).hashCode();

        for (int i = 0; i < this.ultimo; i++)
            ret = ret * 7 + this.items[i].hashCode();

        if (ret < 0)
            ret = -ret;

        return ret;
    }

    // construtor de copia
    public Pilha(Pilha<X> model) throws StackException {
        if (model == null)
            throw new StackException("Modelo ausente");

        this.tamanhoInicial = model.tamanhoInicial;
        this.ultimo = model.ultimo;

        this.items = new Object[model.items.length];

        for (int i = 0; i < model.items.length; i++)
            this.items[i] = model.items[i];
    }

    public Object clone() {
        Pilha<X> ret = null;

        try {
            ret = new Pilha<X>(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}