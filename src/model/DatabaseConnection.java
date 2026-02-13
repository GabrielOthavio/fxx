package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
// 1. URL ajustada para PostgreSQL (porta 5432)
    // IMPORTANTE: Se você não criou um banco chamado "sistema_vendas", 
    // troque o final da URL para "/postgres"
    private static final String URL = "jdbc:postgresql://localhost:5432/vendas";
    
    // 2. Usuário padrão do Postgres
    private static final String USER = "postgres";
    
    // 3. A senha que configuramos via SQL Shell
    private static final String PASSWORD = "*925STF9*pmc";
   
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado", e);
        }
    }
}
