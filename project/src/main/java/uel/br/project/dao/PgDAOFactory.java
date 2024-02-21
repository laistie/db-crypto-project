package uel.br.project.dao;

import java.sql.Connection;

public class PgDAOFactory extends DAOFactory{
    public PgDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public MercadoDAO getMercadoDAO() {
        return new PgMercadoDAO(this.connection);
    }
}
