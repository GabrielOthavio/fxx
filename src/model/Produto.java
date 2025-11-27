package model;

import javafx.beans.property.*;

public class Produto {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty descricao = new SimpleStringProperty();
    private DoubleProperty preco = new SimpleDoubleProperty();
    private IntegerProperty estoque = new SimpleIntegerProperty();
    private IntegerProperty categoriaId = new SimpleIntegerProperty();
    private StringProperty categoriaNome = new SimpleStringProperty();
    
    public Produto() {}
    
    public Produto(String nome, String descricao, double preco, int estoque, int categoriaId) {
        setNome(nome);
        setDescricao(descricao);
        setPreco(preco);
        setEstoque(estoque);
        setCategoriaId(categoriaId);
    }
    
    public IntegerProperty idProperty() { 
    	return id; 
    }
    public StringProperty nomeProperty() { 
    	return nome; 
    }
    public StringProperty descricaoProperty() { 
    	return descricao; 
    }
    public DoubleProperty precoProperty() { 
    	return preco; 
    }
    public IntegerProperty estoqueProperty() { 
    	return estoque; 
    }
    public IntegerProperty categoriaIdProperty() { 
    	return categoriaId; 
    }
    public StringProperty categoriaNomeProperty() { 
    	return categoriaNome; 
    }
    
    public int getId() { 
    	return id.get(); 
    }
    
    public void setId(int id) { 
    	this.id.set(id); 
    }
    
    public String getNome() { 
    	return nome.get(); 
    }
    
    public void setNome(String nome) { 
    	this.nome.set(nome); 
    }
    
    public String getDescricao() { 
    	return descricao.get(); 
    }
    
    public void setDescricao(String descricao) { 
    	this.descricao.set(descricao); 
    }
    
    public double getPreco() { 
    	return preco.get(); 
    }
    
    public void setPreco(double preco) { 
    	this.preco.set(preco); 
    }
    
    public int getEstoque() { 
    	return estoque.get(); 
    }
    
    public void setEstoque(int estoque) { 
    	this.estoque.set(estoque); 
    }
    
    public int getCategoriaId() { 
    	return categoriaId.get(); 
    }
    
    public void setCategoriaId(int categoriaId) { 
    	this.categoriaId.set(categoriaId); 
    }
    
    public String getCategoriaNome() { 
    	return categoriaNome.get(); 
    }
    
    public void setCategoriaNome(String categoriaNome) { 
    	this.categoriaNome.set(categoriaNome); 
    	}
    
    @Override
    public String toString() {
        return getNome() + " - R$ " + getPreco();
    }
}