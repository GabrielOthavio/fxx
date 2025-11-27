package controller;

import dao.ProdutoDAO;
import dao.CategoriaDAO;
import model.Produto;
import model.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ProdutosController implements Initializable {
    
    @FXML private TableView<Produto> tableView;
    @FXML private TableColumn<Produto, Integer> colId;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, String> colDescricao;
    @FXML private TableColumn<Produto, Double> colPreco;
    @FXML private TableColumn<Produto, Integer> colEstoque;
    @FXML private TableColumn<Produto, String> colCategoria;
    
    @FXML private TextField txtNome;
    @FXML private TextArea txtDescricao;
    @FXML private TextField txtPreco;
    @FXML private TextField txtEstoque;
    @FXML private ComboBox<Categoria> comboCategoria;
    
    private ObservableList<Produto> produtosList;
    private ObservableList<Categoria> categoriasList;
    private ProdutoDAO produtoDAO;
    private CategoriaDAO categoriaDAO;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        produtoDAO = new ProdutoDAO();
        categoriaDAO = new CategoriaDAO();
        produtosList = FXCollections.observableArrayList();
        categoriasList = FXCollections.observableArrayList();
        
        tableView.setItems(produtosList);
        
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colDescricao.setCellValueFactory(cellData -> cellData.getValue().descricaoProperty());
        colPreco.setCellValueFactory(cellData -> cellData.getValue().precoProperty().asObject());
        colEstoque.setCellValueFactory(cellData -> cellData.getValue().estoqueProperty().asObject());
        colCategoria.setCellValueFactory(cellData -> cellData.getValue().categoriaNomeProperty());
       
        comboCategoria.setItems(categoriasList);
        
        carregarDados();
       
        tableView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selecionarProduto(newValue));
    }
    
    private void carregarDados() {
        try {
            produtosList.clear();
            produtosList.addAll(produtoDAO.read());
            
            categoriasList.clear();
            categoriasList.addAll(categoriaDAO.read());
            
        } catch (Exception e) {
            mostrarAlerta("Erro ao carregar dados: " + e.getMessage());
        }
    }
    
    private void selecionarProduto(Produto produto) {
        if (produto != null) {
            txtNome.setText(produto.getNome());
            txtDescricao.setText(produto.getDescricao());
            txtPreco.setText(String.valueOf(produto.getPreco()));
            txtEstoque.setText(String.valueOf(produto.getEstoque()));

            for (Categoria categoria : categoriasList) {
                if (categoria.getId() == produto.getCategoriaId()) {
                    comboCategoria.getSelectionModel().select(categoria);
                    break;
                }
            }
        }
    }
    
    @FXML
    private void handleSalvar() {
        if (validarCampos()) {
            try {
                Categoria categoriaSelecionada = comboCategoria.getSelectionModel().getSelectedItem();
                if (categoriaSelecionada == null) {
                    mostrarAlerta("Selecione uma categoria!");
                    return;
                }
                
                Produto produto = new Produto(
                    txtNome.getText(),
                    txtDescricao.getText(),
                    Double.parseDouble(txtPreco.getText()),
                    Integer.parseInt(txtEstoque.getText()),
                    categoriaSelecionada.getId()
                );
                
                produtoDAO.create(produto);
                carregarDados();
                limparCampos();
                mostrarAlerta("Produto salvo com sucesso!", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Erro ao salvar produto: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleAtualizar() {
        Produto produtoSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null && validarCampos()) {
            try {
                Categoria categoriaSelecionada = comboCategoria.getSelectionModel().getSelectedItem();
                if (categoriaSelecionada == null) {
                    mostrarAlerta("Selecione uma categoria!");
                    return;
                }
                
                produtoSelecionado.setNome(txtNome.getText());
                produtoSelecionado.setDescricao(txtDescricao.getText());
                produtoSelecionado.setPreco(Double.parseDouble(txtPreco.getText()));
                produtoSelecionado.setEstoque(Integer.parseInt(txtEstoque.getText()));
                produtoSelecionado.setCategoriaId(categoriaSelecionada.getId());
                
                produtoDAO.update(produtoSelecionado);
                carregarDados();
                limparCampos();
                mostrarAlerta("Produto atualizado com sucesso!", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Erro ao atualizar produto: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Selecione um produto para atualizar!");
        }
    }
    
    @FXML
    private void handleExcluir() {
        Produto produtoSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            try {
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacao.setTitle("Confirmação");
                confirmacao.setContentText("Tem certeza que deseja excluir o produto " + produtoSelecionado.getNome() + "?");
                
                if (confirmacao.showAndWait().get() == ButtonType.OK) {
                    produtoDAO.delete(produtoSelecionado.getId());
                    carregarDados();
                    limparCampos();
                    mostrarAlerta("Produto excluído com sucesso!", Alert.AlertType.INFORMATION);
                }
            } catch (Exception e) {
                mostrarAlerta("Erro ao excluir produto: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Selecione um produto para excluir!");
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
        txtPreco.clear();
        txtEstoque.clear();
        comboCategoria.getSelectionModel().clearSelection();
    }
    
    private boolean validarCampos() {
        if (txtNome.getText().isEmpty()) {
            mostrarAlerta("Nome é obrigatório!");
            txtNome.requestFocus();
            return false;
        }
        if (txtPreco.getText().isEmpty() || !txtPreco.getText().matches("\\d+(\\.\\d+)?")) {
            mostrarAlerta("Preço deve ser um número válido!");
            txtPreco.requestFocus();
            return false;
        }
        if (txtEstoque.getText().isEmpty() || !txtEstoque.getText().matches("\\d+")) {
            mostrarAlerta("Estoque deve ser um número inteiro!");
            txtEstoque.requestFocus();
            return false;
        }
        return true;
    }
    
    private void mostrarAlerta(String mensagem) {
        mostrarAlerta(mensagem, Alert.AlertType.ERROR);
    }
    
    private void mostrarAlerta(String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}