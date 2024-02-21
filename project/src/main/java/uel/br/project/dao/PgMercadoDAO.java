package uel.br.project.dao;

import org.springframework.stereotype.Repository;
import uel.br.project.model.Mercado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PgMercadoDAO implements MercadoDAO {
    private final Connection connection;

    public PgMercadoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Mercado mercado) throws SQLException {
        String sql = "INSERT INTO dbproject.mercado(nome, rank, pares_troca) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, mercado.getNome());
            stmt.setInt(2, mercado.getRank());
            stmt.setInt(3, mercado.getParesTroca());
            stmt.executeUpdate();
        }
    }

    @Override
    public Mercado read(String nome) throws SQLException {
        String sql = "SELECT * FROM dbproject.mercado WHERE nome = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Mercado mercado = new Mercado();
                mercado.setNome(rs.getString("nome"));
                mercado.setRank(rs.getInt("rank"));
                mercado.setParesTroca(rs.getInt("pares_troca"));
                return mercado;
            } else {
                return null;
            }
        }
    }

    @Override
    public void update(Mercado mercado) throws SQLException {
        String sql = "UPDATE dbproject.mercado SET rank = ?, pares_troca = ? WHERE nome = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, mercado.getRank());
            stmt.setInt(2, mercado.getParesTroca());
            stmt.setString(3, mercado.getNome());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(String nome) throws SQLException {
        String sql = "DELETE FROM dbproject.mercado WHERE nome = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Mercado> all() throws SQLException {
        String sql = "SELECT * FROM dbproject.mercado";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            List<Mercado> mercados = new ArrayList<>();

            while (rs.next()) {
                Mercado mercado = new Mercado();
                mercado.setNome(rs.getString("nome"));
                mercado.setRank(rs.getInt("rank"));
                mercado.setParesTroca(rs.getInt("pares_troca"));
                mercados.add(mercado);
            }

            return mercados;
        }
    }

    @Override
    public Mercado getMercadoByNome(String nome) throws SQLException {
        String sql = "SELECT * FROM dbproject.mercado WHERE nome = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Mercado mercado = new Mercado();
                mercado.setNome(rs.getString("nome"));
                mercado.setRank(rs.getInt("rank"));
                mercado.setParesTroca(rs.getInt("pares_troca"));
                return mercado;
            } else {
                return null;
            }
        }
    }
}
