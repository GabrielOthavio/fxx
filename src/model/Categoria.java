package model;

import javafx.beans.property.*;

public class Categoria {
    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty descricao;
    
    public Categoria() {
        this.id = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
        this.descricao = new SimpleStringProperty();
    }
    
    public Categoria(String nome, String descricao) {
        this();
        this.nome.set(nome);
        this.descricao.set(descricao);
    }
    
    // Getters e Setters para propriedades
    public int getId() {
        return id.get();
    }
    
    public void setId(int id) {
        this.id.set(id);
    }
    
    public IntegerProperty idProperty() {
        return id;
    }
    
    public String getNome() {
        return nome.get();
    }
    
    public void setNome(String nome) {
        this.nome.set(nome);
    }
    
    public StringProperty nomeProperty() {
        return nome;
    }
    
    public String getDescricao() {
        return descricao.get();
    }
    
    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }
    
    public StringProperty descricaoProperty() {
        return descricao;
    }
    
    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", descricao='" + getDescricao() + '\'' +
                '}';
    }
}
