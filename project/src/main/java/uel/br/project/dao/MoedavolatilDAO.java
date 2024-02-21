package uel.br.project.dao;

import uel.br.project.model.Moedavolatil;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface MoedavolatilDAO extends DAO<Moedavolatil> {
    Moedavolatil read(Date data, String sigla) throws SQLException;
    void delete(Date data, String sigla) throws SQLException;

    public Moedavolatil getMoedavolatil(Date data, String sigla) throws SQLException;
}
