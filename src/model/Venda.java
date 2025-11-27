package model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Venda {
    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<LocalDate> dataVenda = new SimpleObjectProperty<>();
    private IntegerProperty clienteId = new SimpleIntegerProperty();
    private StringProperty clienteNome = new SimpleStringProperty();
    private IntegerProperty produtoId = new SimpleIntegerProperty();
    private StringProperty produtoNome = new SimpleStringProperty();
    private IntegerProperty quantidade = new SimpleIntegerProperty();
    private DoubleProperty valorTotal = new SimpleDoubleProperty();
    
    public Venda() {
    	
    }
    
    public Venda(LocalDate dataVenda, int clienteId, int produtoId, int quantidade, double valorTotal) {
        setDataVenda(dataVenda);
        setClienteId(clienteId);
        setProdutoId(produtoId);
        setQuantidade(quantidade);
        setValorTotal(valorTotal);
    }
    
    public IntegerProperty idProperty() { return id; }
    public ObjectProperty<LocalDate> dataVendaProperty() { return dataVenda; }
    public IntegerProperty clienteIdProperty() { return clienteId; }
    public StringProperty clienteNomeProperty() { return clienteNome; }
    public IntegerProperty produtoIdProperty() { return produtoId; }
    public StringProperty produtoNomeProperty() { return produtoNome; }
    public IntegerProperty quantidadeProperty() { return quantidade; }
    public DoubleProperty valorTotalProperty() { return valorTotal; }
    
    public int getId() { 
    	return id.get(); 
    }
    
    public void setId(int id) { 
    	this.id.set(id); 
    }
    
    public LocalDate getDataVenda() { 
    	return dataVenda.get(); 
    }
    
    public void setDataVenda(LocalDate dataVenda) { 
    	this.dataVenda.set(dataVenda); 
    }
    
    public int getClienteId() { 
    	return clienteId.get(); 
    }
    
    public void setClienteId(int clienteId) { 
    	this.clienteId.set(clienteId); 
    }
    
    public String getClienteNome() { 
    	return clienteNome.get(); 
    }
    
    public void setClienteNome(String clienteNome) { 
    	this.clienteNome.set(clienteNome); 
    }
    
    public int getProdutoId() { 
    	return produtoId.get(); 
    }
    
    public void setProdutoId(int produtoId) { 
    	this.produtoId.set(produtoId); 
    }
    
    public String getProdutoNome() { 
    	return produtoNome.get();
    }
    
    public void setProdutoNome(String produtoNome) { 
    	this.produtoNome.set(produtoNome); 
    }
    
    public int getQuantidade() { 
    	return quantidade.get(); 
    }
    
    public void setQuantidade(int quantidade) { 
    	this.quantidade.set(quantidade); 
    }
    
    public double getValorTotal() { 
    	return valorTotal.get(); 
    }
    
    public void setValorTotal(double valorTotal) { 
    	this.valorTotal.set(valorTotal); 
    }
    
    @Override
    public String toString() {
        return "Venda #" + getId() + " - " + getDataVenda() + " - R$ " + getValorTotal();
    }
}