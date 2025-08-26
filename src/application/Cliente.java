package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cliente {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nome;
    private final SimpleStringProperty email;

    public Cliente(int id, String nome, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.email = new SimpleStringProperty(email);
    }

    public int getId() {
        return id.get();
    }

    public String getNome() {
        return nome.get();
    }

    public String getEmail() {
        return email.get();
    }
}