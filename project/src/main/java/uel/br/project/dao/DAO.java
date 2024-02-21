package uel.br.project.dao;

import java.sql.SQLException;
import java.util.List;

// Para abstrair e encapsular todo o acesso à fonte de dados, gerenciamento de conexão
public interface DAO<RECORD> {
    public void create(RECORD record) throws SQLException;
    public RECORD read(String nome) throws SQLException;
    public void update(RECORD record) throws SQLException;
    public void delete(String key) throws SQLException;
    public List<RECORD> all() throws SQLException;
}
