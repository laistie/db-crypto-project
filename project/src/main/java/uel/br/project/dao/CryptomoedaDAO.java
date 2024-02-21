package uel.br.project.dao;

import uel.br.project.model.Cryptomoeda;
import uel.br.project.model.Moedavolatil;

import java.sql.SQLException;
import java.util.List;

public interface CryptomoedaDAO extends DAO<Cryptomoeda>{
    void delete(String sigla) throws SQLException;

    public Cryptomoeda getCryptomoedaBySigla(String sigla) throws SQLException;
    public List<Moedavolatil> getMoedavolatilByCrypto(String sigla) throws SQLException;
}
