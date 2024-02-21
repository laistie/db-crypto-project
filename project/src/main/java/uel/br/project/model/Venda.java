package uel.br.project.model;

public class Venda {
    private Mercado mercado;
    private Cryptomoeda moeda;

    public Mercado getMercado() {
        return mercado;
    }

    public void setMercado(Mercado mercado) {
        this.mercado = mercado;
    }

    public Cryptomoeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Cryptomoeda moeda) {
        this.moeda = moeda;
    }
}
