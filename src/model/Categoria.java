package model;

import javafx.beans.property.*;

public class Categoria {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty descricao = new SimpleStringProperty();
    
    public Categoria() {}
    
    public Categoria(String nome, String descricao) {
        setNome(nome);
        setDescricao(descricao);
    }
    
    // Property methods para JavaFX
    public IntegerProperty idProperty() { return id; }
    public StringProperty nomeProperty() { return nome; }
    public StringProperty descricaoProperty() { return descricao; }
    
    // Getters e Setters
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    
    public String getNome() { return nome.get(); }
    public void setNome(String nome) { this.nome.set(nome); }
    
    public String getDescricao() { return descricao.get(); }
    public void setDescricao(String descricao) { this.descricao.set(descricao); }
    
    @Override
    public String toString() {
        return getNome();
    }
}