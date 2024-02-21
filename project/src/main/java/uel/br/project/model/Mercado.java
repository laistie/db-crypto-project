package uel.br.project.model;

public class Mercado {
    private String nome;
    private int rank;
    private int paresTroca;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getParesTroca() {
        return paresTroca;
    }

    public void setParesTroca(int paresTroca) {
        this.paresTroca = paresTroca;
    }

}