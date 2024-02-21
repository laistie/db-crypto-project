package uel.br.project.model;

import java.sql.Date;

public class Moedavolatil {
    private Date dataRequisicao;
    private String sigla;
    private double valorusd;
    private double marketcap;

    public Date getDataRequisicao() {
        return dataRequisicao;
    }

    public void setDataRequisicao(Date dataRequisicao) {
        this.dataRequisicao = dataRequisicao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public double getValorusd() {
        return valorusd;
    }

    public void setValorusd(double valorusd) {
        this.valorusd = valorusd;
    }

    public double getMarketcap() {
        return marketcap;
    }

    public void setMarketcap(double marketcap) {
        this.marketcap = marketcap;
    }
}
