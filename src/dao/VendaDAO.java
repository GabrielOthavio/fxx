package dao;

import model.Venda;
import model.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendaDAO {
    
    public void create(Venda venda) throws SQLException {
        String sql = "INSERT INTO vendas (data_venda, cliente_id, produto_id, quantidade, valor_total) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setDate(1, Date.valueOf(venda.getDataVenda()));
            stmt.setInt(2, venda.getClienteId());
            stmt.setInt(3, venda.getProdutoId());
            stmt.setInt(4, venda.getQuantidade());
            stmt.setDouble(5, venda.getValorTotal());
            stmt.executeUpdate();
            
            // Obtém o ID gerado automaticamente
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    venda.setId(generatedKeys.getInt(1));
                }
            }
        }
    }
    
    public List<Venda> read() throws SQLException {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT v.*, c.nome as cliente_nome, p.nome as produto_nome " +
                    "FROM vendas v " +
                    "LEFT JOIN clientes c ON v.cliente_id = c.id " +
                    "LEFT JOIN produtos p ON v.produto_id = p.id " +
                    "ORDER BY v.data_venda DESC, v.id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setDataVenda(rs.getDate("data_venda").toLocalDate());
                venda.setClienteId(rs.getInt("cliente_id"));
                venda.setProdutoId(rs.getInt("produto_id"));
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setValorTotal(rs.getDouble("valor_total"));
                venda.setClienteNome(rs.getString("cliente_nome"));
                venda.setProdutoNome(rs.getString("produto_nome"));
                vendas.add(venda);
            }
        }
        return vendas;
    }
    
    public Venda findById(int id) throws SQLException {
        String sql = "SELECT v.*, c.nome as cliente_nome, p.nome as produto_nome " +
                    "FROM vendas v " +
                    "LEFT JOIN clientes c ON v.cliente_id = c.id " +
                    "LEFT JOIN produtos p ON v.produto_id = p.id " +
                    "WHERE v.id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setDataVenda(rs.getDate("data_venda").toLocalDate());
                venda.setClienteId(rs.getInt("cliente_id"));
                venda.setProdutoId(rs.getInt("produto_id"));
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setValorTotal(rs.getDouble("valor_total"));
                venda.setClienteNome(rs.getString("cliente_nome"));
                venda.setProdutoNome(rs.getString("produto_nome"));
                return venda;
            }
        }
        return null;
    }
    
    public void update(Venda venda) throws SQLException {
        String sql = "UPDATE vendas SET data_venda = ?, cliente_id = ?, produto_id = ?, quantidade = ?, valor_total = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(venda.getDataVenda()));
            stmt.setInt(2, venda.getClienteId());
            stmt.setInt(3, venda.getProdutoId());
            stmt.setInt(4, venda.getQuantidade());
            stmt.setDouble(5, venda.getValorTotal());
            stmt.setInt(6, venda.getId());
            stmt.executeUpdate();
        }
    }
    
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM vendas WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public List<Venda> findByDateRange(LocalDate dataInicio, LocalDate dataFim) throws SQLException {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT v.*, c.nome as cliente_nome, p.nome as produto_nome " +
                    "FROM vendas v " +
                    "LEFT JOIN clientes c ON v.cliente_id = c.id " +
                    "LEFT JOIN produtos p ON v.produto_id = p.id " +
                    "WHERE v.data_venda BETWEEN ? AND ? " +
                    "ORDER BY v.data_venda DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(dataInicio));
            stmt.setDate(2, Date.valueOf(dataFim));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setDataVenda(rs.getDate("data_venda").toLocalDate());
                venda.setClienteId(rs.getInt("cliente_id"));
                venda.setProdutoId(rs.getInt("produto_id"));
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setValorTotal(rs.getDouble("valor_total"));
                venda.setClienteNome(rs.getString("cliente_nome"));
                venda.setProdutoNome(rs.getString("produto_nome"));
                vendas.add(venda);
            }
        }
        return vendas;
    }
    
    public List<Venda> findByCliente(int clienteId) throws SQLException {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT v.*, c.nome as cliente_nome, p.nome as produto_nome " +
                    "FROM vendas v " +
                    "LEFT JOIN clientes c ON v.cliente_id = c.id " +
                    "LEFT JOIN produtos p ON v.produto_id = p.id " +
                    "WHERE v.cliente_id = ? " +
                    "ORDER BY v.data_venda DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setDataVenda(rs.getDate("data_venda").toLocalDate());
                venda.setClienteId(rs.getInt("cliente_id"));
                venda.setProdutoId(rs.getInt("produto_id"));
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setValorTotal(rs.getDouble("valor_total"));
                venda.setClienteNome(rs.getString("cliente_nome"));
                venda.setProdutoNome(rs.getString("produto_nome"));
                vendas.add(venda);
            }
        }
        return vendas;
    }
    
    public Map<String, Double> getVendasMensais() throws SQLException {
        Map<String, Double> vendasPorMes = new HashMap<>();
        String sql = "SELECT DATE_FORMAT(data_venda, '%Y-%m') as mes, SUM(valor_total) as total " +
                    "FROM vendas " +
                    "GROUP BY DATE_FORMAT(data_venda, '%Y-%m') " +
                    "ORDER BY mes";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String mes = rs.getString("mes");
                Double total = rs.getDouble("total");
                vendasPorMes.put(mes, total);
            }
        }
        return vendasPorMes;
    }
    
    public double getTotalVendas() throws SQLException {
        String sql = "SELECT SUM(valor_total) as total FROM vendas";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }
    
    public Map<String, Double> getVendasUltimosMeses(int meses) throws SQLException {
        Map<String, Double> vendasPorMes = new HashMap<>();
        String sql = "SELECT DATE_FORMAT(data_venda, '%Y-%m') as mes, SUM(valor_total) as total " +
                    "FROM vendas " +
                    "WHERE data_venda >= DATE_SUB(CURDATE(), INTERVAL ? MONTH) " +
                    "GROUP BY DATE_FORMAT(data_venda, '%Y-%m') " +
                    "ORDER BY mes";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, meses);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String mes = rs.getString("mes");
                Double total = rs.getDouble("total");
                vendasPorMes.put(mes, total);
            }
        }
        return vendasPorMes;
    }
}