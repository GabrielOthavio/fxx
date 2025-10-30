package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.DatabaseConnection;
import model.Venda;

public class VendaDAO {
    
    // CREATE - Inserir nova venda
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
            
        }
    }
    
    // READ - Listar todas as vendas
    public List<Venda> read() throws SQLException {
        String sql = "SELECT * FROM vendas";
        List<Venda> vendas = new ArrayList<>();
        
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
                vendas.add(venda);
            }
        }
        return vendas;
    }
    
    // UPDATE - Atualizar venda existente
    public void atualizar(Venda venda) throws SQLException {
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
    
    // DELETE - Deletar venda
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM vendas WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
