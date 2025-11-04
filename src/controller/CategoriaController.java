package controller;

import dao.CategoriaDAO;
import model.Categoria;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoriaController {
    @FXML private TableView<Categoria> tabelaView;
    
    @FXML private TableColumn<Categoria, Integer> colunaId;
    @FXML private TableColumn<Categoria, String> colunaNome;
    @FXML private TableColumn<Categoria, String> colunaDescricao;
    
    @FXML private TextField txtNome;
    @FXML private TextField txtDescricao;
    
    private ObservableList<Categoria> categoriaLista;
    private CategoriaDAO categoriaDAO;

    @FXML
    public void initialize() {
        categoriaDAO = new CategoriaDAO();
        categoriaLista = FXCollections.observableArrayList();
        
        tabelaView.setItems(categoriaLista);
        
        colunaId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colunaNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colunaDescricao.setCellValueFactory(cellData -> cellData.getValue().descricaoProperty());
        
        carregarCategorias();
        
        tabelaView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarCategoria(newValue));
    }
    
    private void carregarCategorias() {
        try {
            categoriaLista.clear();
            categoriaLista.addAll(categoriaDAO.read());
        } catch (Exception e) {
            mostrarAlerta("Erro ao carregar categorias: " + e.getMessage());
        }
    }

    private void selecionarCategoria(Categoria categoria) {
        if (categoria != null) {
            txtNome.setText(categoria.getNome());
            txtDescricao.setText(categoria.getDescricao());
        }
    }
    
    @FXML
    private void handleSalvar() {
        if (validarCampos()) {
            try {
                Categoria categoria = new Categoria();
                categoria.setNome(txtNome.getText());
                categoria.setDescricao(txtDescricao.getText());
                
                categoriaDAO.create(categoria);
                carregarCategorias();
                limparCampos();
                mostrarSucesso("Categoria adicionada com sucesso!");
                
            } catch (Exception e) {
                mostrarAlerta("Erro ao salvar categoria: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleAtualizar() {
        Categoria categoriaSelecionada = tabelaView.getSelectionModel().getSelectedItem();
        
        if (categoriaSelecionada == null) {
            mostrarAlerta("Selecione uma categoria para atualizar!");
            return;
        }
        
        if (validarCampos()) {
            try {
                categoriaSelecionada.setNome(txtNome.getText());
                categoriaSelecionada.setDescricao(txtDescricao.getText());
                
                categoriaDAO.atualizar(categoriaSelecionada);
                carregarCategorias();
                limparCampos();
                mostrarSucesso("Categoria atualizada com sucesso!");
                
            } catch (Exception e) {
                mostrarAlerta("Erro ao atualizar categoria: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleDeletar() {
        Categoria categoriaSelecionada = tabelaView.getSelectionModel().getSelectedItem();
        
        if (categoriaSelecionada != null) {
            try {
                categoriaDAO.deletar(categoriaSelecionada.getId());
                carregarCategorias();
                limparCampos();
                mostrarSucesso("Categoria deletada com sucesso!");
            } catch (Exception e) {
                mostrarAlerta("Erro ao deletar categoria: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Selecione uma categoria para deletar!");
        }
    }
    
    @FXML
    private void handleLimpar() {
        limparCampos();
        tabelaView.getSelectionModel().clearSelection();
    }
    
    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            mostrarAlerta("Nome é obrigatório!");
            return false;
        }
        return true;
    }
    
    private void limparCampos() {
        txtNome.clear();
        txtDescricao.clear();
    }
    
    private void mostrarSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarAlerta(String mensagem) {
        mostrarAlerta(mensagem, Alert.AlertType.ERROR);
    }

    private void mostrarAlerta(String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(tipo == Alert.AlertType.ERROR ? "Erro" : "Informação");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}