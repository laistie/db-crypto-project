package uel.br.project.dao;

import uel.br.project.model.Mercado;
import java.sql.SQLException;
import java.util.List;

public interface MercadoDAO extends DAO<Mercado>{
    void delete(String nome) throws SQLException;

    public Mercado getMercadoByNome(String nome) throws SQLException;
}
