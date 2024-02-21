package uel.br.project.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

@Configuration
public class PgConnectionFactory extends ConnectionFactory {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    public PgConnectionFactory() {}

    @Bean
    @Override
    public Connection getConnection () throws IOException, SQLException, ClassNotFoundException {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

            throw new SQLException("Erro de conexão ao banco de dados.");
        }

        return connection;
    }
}
