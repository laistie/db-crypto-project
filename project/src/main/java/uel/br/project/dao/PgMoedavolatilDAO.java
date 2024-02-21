package uel.br.project.dao;

import org.springframework.stereotype.Repository;
import uel.br.project.model.Moedavolatil;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PgMoedavolatilDAO implements MoedavolatilDAO{
    private final Connection connection;

    public PgMoedavolatilDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Moedavolatil moeda) throws SQLException {
        String sql = "INSERT INTO dbproject.moedavolatil(data_requisicao, sigla, valorusd, marketcap) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, moeda.getDataRequisicao());
            stmt.setString(2, moeda.getSigla());
            stmt.setDouble(3, moeda.getValorusd());
            stmt.setDouble(4, moeda.getMarketcap());
            stmt.executeUpdate();
        }
    }

    @Override
    public Moedavolatil read(String nome) throws SQLException {
        return null;
    }

    @Override
    public Moedavolatil read(Date data, String sigla) throws SQLException {
        String sql = "SELECT * FROM dbproject.moedavolatil WHERE data_requisicao = ? AND sigla = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, data);
            stmt.setString(2, sigla);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Moedavolatil moeda = new Moedavolatil();
                moeda.setDataRequisicao(rs.getDate("data_requisicao"));
                moeda.setValorusd(rs.getDouble("valorusd"));
                moeda.setMarketcap(rs.getDouble("marketcap"));
                return moeda;
            } else {
                return null;
            }
        }
    }

    @Override
    public void update(Moedavolatil moeda) throws SQLException {
        String sql = "UPDATE dbproject.moedavolatil SET valorusd = ?, marketcap = ? WHERE data_requisicao = ? AND sigla = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, moeda.getValorusd());
            stmt.setDouble(2, moeda.getMarketcap());
            stmt.setDate(3, moeda.getDataRequisicao());
            stmt.setString(4, moeda.getSigla());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(String key) throws SQLException {
    }

    @Override
    public void delete(Date data, String sigla) throws SQLException {
        String sql = "DELETE FROM dbproject.moedavolatil WHERE data_requisicao = ? AND sigla = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, data);
            stmt.setString(2, sigla);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Moedavolatil> all() throws SQLException {
        String sql = "SELECT * FROM dbproject.moedavolatil";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            List<Moedavolatil> moedas = new ArrayList<>();

            while (rs.next()) {
                Moedavolatil moeda = new Moedavolatil();
                moeda.setDataRequisicao(rs.getDate("data_requisicao"));
                moeda.setSigla(rs.getString("sigla"));
                moeda.setValorusd(rs.getDouble("valorusd"));
                moeda.setMarketcap(rs.getDouble("marketcap"));
                moedas.add(moeda);
            }

            return moedas;
        }
    }

    @Override
    public Moedavolatil getMoedavolatil(Date data, String sigla) throws SQLException {
        String sql = "SELECT * FROM dbproject.moedavolatil WHERE data_requisicao = ? AND sigla = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, data);
            stmt.setString(2, sigla);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Moedavolatil moeda = new Moedavolatil();
                moeda.setDataRequisicao(rs.getDate("data_requisicao"));
                moeda.setSigla(rs.getString("sigla"));
                moeda.setValorusd(rs.getDouble("valorusd"));
                moeda.setMarketcap(rs.getDouble("marketcap"));
                return moeda;
            } else {
                return null;
            }
        }
    }
}
