package controller;

import dao.CategoriaDAO;
import model.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CategoriasController implements Initializable {
    
    
    @FXML private TableView<Categoria> tableView;              
    @FXML private TableColumn<Categoria, Integer> colId;       
    @FXML private TableColumn<Categoria, String> colNome;      
    @FXML private TableColumn<Categoria, String> colDescricao; 
    
    @FXML private TextField txtNome;        
    @FXML private TextArea txtDescricao;    
    
    private ObservableList<Categoria> categoriasList;  
    private CategoriaDAO categoriaDAO;                 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
        categoriaDAO = new CategoriaDAO();
        
        categoriasList = FXCollections.observableArrayList();
        
        tableView.setItems(categoriasList);
        
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
       
        colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        
        colDescricao.setCellValueFactory(cellData -> cellData.getValue().descricaoProperty());
       
        carregarCategorias();
        
        
        tableView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selecionarCategoria(newValue));
    }
    
    private void carregarCategorias() {
        try {
            
            categoriasList.clear();
            
            categoriasList.addAll(categoriaDAO.read());
            
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
                
                Categoria categoria = new Categoria(
                    txtNome.getText(), 
                    txtDescricao.getText()
                );
                
                categoriaDAO.create(categoria);
                
                carregarCategorias();
                
                limparCampos();
               
                mostrarAlerta("Categoria salva com sucesso!", Alert.AlertType.INFORMATION);
                
            } catch (Exception e) {
                mostrarAlerta("Erro ao salvar categoria: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleAtualizar() {
        
        Categoria categoriaSelecionada = tableView.getSelectionModel().getSelectedItem();
        
        if (categoriaSelecionada != null && validarCampos()) {
            try {
                categoriaSelecionada.setNome(txtNome.getText());
                categoriaSelecionada.setDescricao(txtDescricao.getText());
                
                categoriaDAO.update(categoriaSelecionada);
                
                carregarCategorias();
                
                limparCampos();
                
                mostrarAlerta("Categoria atualizada com sucesso!", Alert.AlertType.INFORMATION);
                
            } catch (Exception e) {
                mostrarAlerta("Erro ao atualizar categoria: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Selecione uma categoria para atualizar!");
        }
    }
    
    @FXML
    private void handleExcluir() {
        Categoria categoriaSelecionada = tableView.getSelectionModel().getSelectedItem();
        
        if (categoriaSelecionada != null) {
            try {
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacao.setTitle("Confirmação de Exclusão");
                confirmacao.setHeaderText(null);
                confirmacao.setContentText("Tem certeza que deseja excluir a categoria '" + 
                                         categoriaSelecionada.getNome() + "'?");
                
                if (confirmacao.showAndWait().get() == ButtonType.OK) {

                    categoriaDAO.delete(categoriaSelecionada.getId());
                    
                    carregarCategorias();
                    
                    limparCampos();
                    
                    mostrarAlerta("Categoria excluída com sucesso!", Alert.AlertType.INFORMATION);
                }
                
            } catch (Exception e) {
                mostrarAlerta("Erro ao excluir categoria: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Selecione uma categoria para excluir!");
        }
    }
    
    @FXML
    private void handleLimpar() {

        limparCampos();
        
        tableView.getSelectionModel().clearSelection();
    }
    
    private void limparCampos() {
        txtNome.clear();        
        txtDescricao.clear();   
    }
    
    private boolean validarCampos() {
        
        if (txtNome.getText().isEmpty()) {
            
            mostrarAlerta("Nome da categoria é obrigatório!");
            txtNome.requestFocus(); 
            return false;
        }
        
        if (txtNome.getText().trim().length() < 2) {
            mostrarAlerta("Nome da categoria deve ter pelo menos 2 caracteres!");
            txtNome.requestFocus();
            return false;
        }
        return true;
    }
    
    
    private void mostrarAlerta(String mensagem) {
        mostrarAlerta(mensagem, Alert.AlertType.ERROR);
    }
    
    private void mostrarAlerta(String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        
        if (tipo == Alert.AlertType.ERROR) {
            alert.setTitle("Erro");
        } else if (tipo == Alert.AlertType.INFORMATION) {
            alert.setTitle("Informação");
        } else if (tipo == Alert.AlertType.WARNING) {
            alert.setTitle("Aviso");
        }
     
        alert.setHeaderText(null);
 
        alert.setContentText(mensagem);
        
        alert.showAndWait();
    }
    
    @FXML
    private void handlePesquisar() {
        mostrarAlerta("Funcionalidade de pesquisa será implementada em breve!", 
                     Alert.AlertType.INFORMATION);
    }
}