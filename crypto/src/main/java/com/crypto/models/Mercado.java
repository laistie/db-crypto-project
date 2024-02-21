package com.crypto.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "mercado", schema = "dbproject")
public class Mercado {
    @Id
    @Column(name = "nome", length = 25)
    private String nome;

    @Column(name = "rank")
    private int rank;

    @Column(name = "pares_troca")
    private int paresTroca;

    public Mercado() {
    }

    public Mercado(String n, int r, int p) {
        this.nome = n;
        this.rank = r;
        this.paresTroca = p;
    }

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
