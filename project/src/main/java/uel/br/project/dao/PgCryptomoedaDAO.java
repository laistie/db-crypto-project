package uel.br.project.dao;

import org.springframework.stereotype.Repository;
import uel.br.project.model.Cryptomoeda;
import uel.br.project.model.Moedavolatil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PgCryptomoedaDAO implements CryptomoedaDAO{

    private final Connection connection;

    public PgCryptomoedaDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Cryptomoeda moeda) throws SQLException {
        String sql = "INSERT INTO dbproject.cryptomoeda(sigla, nome) VALUES(?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, moeda.getSigla());
            stmt.setString(2, moeda.getNome());
            stmt.executeUpdate();
        }
    }

    @Override
    public Cryptomoeda read(String sigla) throws SQLException {
        String sql = "SELECT * FROM dbproject.cryptomoeda WHERE sigla = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sigla);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cryptomoeda moeda = new Cryptomoeda();
                moeda.setSigla(rs.getString("sigla"));
                moeda.setNome(rs.getString("nome"));
                return moeda;
            } else {
                return null;
            }
        }
    }

    @Override
    public void update(Cryptomoeda moeda) throws SQLException {
        String sql = "UPDATE dbproject.cryptomoeda SET nome = ? WHERE sigla = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, moeda.getNome());
            stmt.setString(2, moeda.getSigla());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(String sigla) throws SQLException {
        String sql = "DELETE FROM dbproject.cryptomoeda WHERE sigla = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sigla);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Cryptomoeda> all() throws SQLException {
        String sql = "SELECT * FROM dbproject.cryptomoeda";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            List<Cryptomoeda> moedas = new ArrayList<>();

            while (rs.next()) {
                Cryptomoeda moeda = new Cryptomoeda();
                moeda.setSigla(rs.getString("sigla"));
                moeda.setNome(rs.getString("nome"));
                moedas.add(moeda);
            }

            return moedas;
        }
    }

    @Override
    public Cryptomoeda getCryptomoedaBySigla(String sigla) throws SQLException {
        String sql = "SELECT * FROM dbproject.cryptomoeda WHERE sigla = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sigla);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cryptomoeda moeda = new Cryptomoeda();
                moeda.setSigla(rs.getString("sigla"));
                moeda.setNome(rs.getString("nome"));
                return moeda;
            } else {
                return null;
            }
        }
    }

    public List<Moedavolatil> getMoedavolatilByCrypto(String sigla) throws SQLException {
        String sql = "SELECT moedavolatil.* FROM moedavolatil INNER JOIN cryptomoeda ON moedavolatil.sigla = cryptomoeda.sigla WHERE cryptomoeda.sigla = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sigla);
            ResultSet rs = stmt.executeQuery();
            List<Moedavolatil> moedas = new ArrayList<>();
            while (rs.next()) {
                Moedavolatil moeda = new Moedavolatil();
                moeda.setDataRequisicao(rs.getDate("data_requisicao"));
                moeda.setValorusd(rs.getDouble("valorusd"));
                moeda.setMarketcap(rs.getDouble("marketcap"));
                moedas.add(moeda);
            }
            return moedas;
        }
    }
}
