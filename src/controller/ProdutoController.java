package controller;

import dao.ProdutoDAO;
import model.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.Collections;

public class ProdutoController {
    @FXML private TableView<Produto> tabelaView;
    
    @FXML private TableColumn<Produto, Integer> colunaId;
    @FXML private TableColumn<Produto, String> colunaNome;
    @FXML private TableColumn<Produto, String> colunaDescricao;
    @FXML private TableColumn<Produto, Double> colunaPreco;
    @FXML private TableColumn<Produto, Integer> colunaEstoque;
    
    @FXML private TextField txtNome;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtPreco;
    @FXML private TextField txtEstoque;

    private ObservableList<Produto> produtoLista;
    private final ProdutoDAO produtoDAO;

    public ProdutoController() {
        this.produtoDAO = new ProdutoDAO();
    }

    @FXML
    public void initialize() {
        produtoLista = FXCollections.observableArrayList();
        
        tabelaView.setItems(produtoLista);
        
        colunaId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colunaNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colunaDescricao.setCellValueFactory(cellData -> cellData.getValue().descricaoProperty());
        colunaPreco.setCellValueFactory(cellData -> cellData.getValue().precoProperty().asObject());
        colunaEstoque.setCellValueFactory(cellData -> cellData.getValue().estoqueProperty().asObject());
        
        carregarProdutos();
        
        tabelaView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarProduto(newValue));
    }
    
    private void carregarProdutos() {
        try {
            produtoLista.clear();
            produtoLista.addAll(produtoDAO.read());
        } catch (Exception e) {
            mostrarAlerta("Erro ao carregar produtos: " + e.getMessage());
        }
    }

    public void adicionarProduto(Produto produto) {
        try {
            produtoDAO.create(produto);
            carregarProdutos();
        } catch (Exception e) {
            mostrarAlerta("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    public List<Produto> listarProdutos() {
        try {
            return produtoDAO.read();
        } catch (Exception e) {
            mostrarAlerta("Erro ao listar produtos: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void atualizarProduto(Produto produto) {
        try {
            produtoDAO.atualizar(produto);
            carregarProdutos();
        } catch (Exception e) {
            mostrarAlerta("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public void deletarProduto(int id) {
        try {
            produtoDAO.deletar(id);
            carregarProdutos();
        } catch (Exception e) {
            mostrarAlerta("Erro ao deletar produto: " + e.getMessage());
        }
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
    
    private void selecionarProduto(Produto produto) {
        if (produto != null) {
            txtNome.setText(produto.getNome());
            txtDescricao.setText(produto.getDescricao());
            txtPreco.setText(String.valueOf(produto.getPreco()));
            txtEstoque.setText(String.valueOf(produto.getEstoque()));
        }
    }
    
    @FXML
    private void handleSalvar() {
        if (validarCampos()) {
            try {
                double preco = Double.parseDouble(txtPreco.getText());
                int estoque = Integer.parseInt(txtEstoque.getText());
                
                Produto produto = new Produto();
                produto.setNome(txtNome.getText());
                produto.setDescricao(txtDescricao.getText());
                produto.setPreco(preco);
                produto.setEstoque(estoque);
                
                produtoDAO.create(produto);
                carregarProdutos();
                limparCampos();
                mostrarSucesso("Produto adicionado com sucesso!");
                
            } catch (NumberFormatException e) {
                mostrarAlerta("Preço e Estoque devem ser números válidos!");
            } catch (Exception e) {
                mostrarAlerta("Erro ao salvar produto: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleAtualizar() {
        Produto produtoSelecionado = tabelaView.getSelectionModel().getSelectedItem();
        
        if (produtoSelecionado == null) {
            mostrarAlerta("Selecione um produto para atualizar!");
            return;
        }
        
        if (validarCampos()) {
            try {
                double preco = Double.parseDouble(txtPreco.getText());
                int estoque = Integer.parseInt(txtEstoque.getText());
                
                produtoSelecionado.setNome(txtNome.getText());
                produtoSelecionado.setDescricao(txtDescricao.getText());
                produtoSelecionado.setPreco(preco);
                produtoSelecionado.setEstoque(estoque);
                
                produtoDAO.atualizar(produtoSelecionado);
                carregarProdutos();
                limparCampos();
                mostrarSucesso("Produto atualizado com sucesso!");
                
            } catch (NumberFormatException e) {
                mostrarAlerta("Preço e Estoque devem ser números válidos!");
            } catch (Exception e) {
                mostrarAlerta("Erro ao atualizar produto: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleDeletar() {
        Produto produtoSelecionado = tabelaView.getSelectionModel().getSelectedItem();
        
        if (produtoSelecionado != null) {
            try {
                produtoDAO.deletar(produtoSelecionado.getId());
                carregarProdutos();
                limparCampos();
                mostrarSucesso("Produto deletado com sucesso!");
            } catch (Exception e) {
                mostrarAlerta("Erro ao deletar produto: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Selecione um produto para deletar!");
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
        if (txtPreco.getText().trim().isEmpty()) {
            mostrarAlerta("Preço é obrigatório!");
            return false;
        }
        if (txtEstoque.getText().trim().isEmpty()) {
            mostrarAlerta("Estoque é obrigatório!");
            return false;
        }
        return true;
    }
    
    private void limparCampos() {
        txtNome.clear();
        txtDescricao.clear();
        txtPreco.clear();
        txtEstoque.clear();
    }
    
    private void mostrarSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}