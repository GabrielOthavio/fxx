package controller;

import dao.VendaDAO;
import dao.ClienteDAO;
import dao.ProdutoDAO;
import model.Venda;
import model.Cliente;
import model.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VendasController implements Initializable {
    
    @FXML private TableView<Venda> tableView;                      
    @FXML private TableColumn<Venda, Integer> colId;              
    @FXML private TableColumn<Venda, String> colData;             
    @FXML private TableColumn<Venda, String> colCliente;          
    @FXML private TableColumn<Venda, String> colProduto;          
    @FXML private TableColumn<Venda, Integer> colQuantidade;      
    @FXML private TableColumn<Venda, Double> colValorTotal;       
    
    @FXML private DatePicker dateDataVenda;                       
    @FXML private ComboBox<Cliente> comboCliente;                 
    @FXML private ComboBox<Produto> comboProduto;                 
    @FXML private TextField txtQuantidade;                        
    @FXML private TextField txtValorUnitario;                     
    @FXML private Label lblValorTotal;                            
    @FXML private Label lblEstoqueDisponivel;                     
    
    private ObservableList<Venda> vendasList;         
    private ObservableList<Cliente> clientesList;     
    private ObservableList<Produto> produtosList;     
    
    private VendaDAO vendaDAO;                        
    private ClienteDAO clienteDAO;                    
    private ProdutoDAO produtoDAO;                   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        vendaDAO = new VendaDAO();
        clienteDAO = new ClienteDAO();
        produtoDAO = new ProdutoDAO();
        
        vendasList = FXCollections.observableArrayList();
        clientesList = FXCollections.observableArrayList();
        produtosList = FXCollections.observableArrayList();
        
        tableView.setItems(vendasList);
        comboCliente.setItems(clientesList);
        comboProduto.setItems(produtosList);
        
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        
        colData.setCellValueFactory(cellData -> cellData.getValue().dataVendaProperty().asString());
        
        colCliente.setCellValueFactory(cellData -> cellData.getValue().clienteNomeProperty());
        
        colProduto.setCellValueFactory(cellData -> cellData.getValue().produtoNomeProperty());
        
        colQuantidade.setCellValueFactory(cellData -> cellData.getValue().quantidadeProperty().asObject());
        
        colValorTotal.setCellValueFactory(cellData -> cellData.getValue().valorTotalProperty().asObject());
        
        dateDataVenda.setValue(LocalDate.now());
        
        carregarDados();
        
        tableView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selecionarVenda(newValue));
        
        comboProduto.valueProperty().addListener(
            (observable, oldValue, newValue) -> atualizarInfoProduto(newValue));
        
        txtQuantidade.textProperty().addListener(
            (observable, oldValue, newValue) -> calcularValorTotal());
    }
    
    private void carregarDados() {
        try {
            vendasList.clear();
            vendasList.addAll(vendaDAO.read());
            
            clientesList.clear();
            clientesList.addAll(clienteDAO.read());
            
            produtosList.clear();
            produtosList.addAll(produtoDAO.read());
            
        } catch (Exception e) {
            mostrarAlerta("Erro ao carregar dados: " + e.getMessage());
        }
    }
    
    private void selecionarVenda(Venda venda) {
        if (venda != null) {
            dateDataVenda.setValue(venda.getDataVenda());
            
            for (Cliente cliente : clientesList) {
                if (cliente.getId() == venda.getClienteId()) {
                    comboCliente.getSelectionModel().select(cliente);
                    break;
                }
            }
            
            for (Produto produto : produtosList) {
                if (produto.getId() == venda.getProdutoId()) {
                    comboProduto.getSelectionModel().select(produto);
                    break;
                }
            }
            
            txtQuantidade.setText(String.valueOf(venda.getQuantidade()));
            calcularValorTotal(); 
        }
    }
    
    private void atualizarInfoProduto(Produto produto) {
        if (produto != null) {
            txtValorUnitario.setText(String.format("R$ %.2f", produto.getPreco()));
            
            lblEstoqueDisponivel.setText("Estoque: " + produto.getEstoque() + " unidades");
            
            calcularValorTotal();
            
        } else {
            txtValorUnitario.clear();
            lblEstoqueDisponivel.setText("Estoque: --");
            lblValorTotal.setText("R$ 0,00");
        }
    }
    
    private void calcularValorTotal() {
        Produto produtoSelecionado = comboProduto.getValue();
        String quantidadeText = txtQuantidade.getText();
        
        if (produtoSelecionado != null && !quantidadeText.isEmpty()) {
            try {
                int quantidade = Integer.parseInt(quantidadeText);
                double valorUnitario = produtoSelecionado.getPreco();
                double valorTotal = quantidade * valorUnitario;
                
                lblValorTotal.setText(String.format("R$ %.2f", valorTotal));
                
            } catch (NumberFormatException e) {
                lblValorTotal.setText("R$ 0,00");
            }
        } else {
            lblValorTotal.setText("R$ 0,00");
        }
    }
    
    @FXML
    private void handleRegistrarVenda() {
        if (validarCampos()) {
            try {
                LocalDate dataVenda = dateDataVenda.getValue();
                Cliente clienteSelecionado = comboCliente.getValue();
                Produto produtoSelecionado = comboProduto.getValue();
                int quantidade = Integer.parseInt(txtQuantidade.getText());
                double valorTotal = calcularValorTotalNumerico();
                
                if (quantidade > produtoSelecionado.getEstoque()) {
                    mostrarAlerta("Estoque insuficiente! Disponível: " + 
                                produtoSelecionado.getEstoque() + " unidades");
                    return;
                }
                
                Venda venda = new Venda(
                    dataVenda,
                    clienteSelecionado.getId(),
                    produtoSelecionado.getId(),
                    quantidade,
                    valorTotal
                );
                
                vendaDAO.create(venda);
                
                produtoSelecionado.setEstoque(produtoSelecionado.getEstoque() - quantidade);
                produtoDAO.update(produtoSelecionado);
  
                carregarDados();
                
                limparCampos();
                
                mostrarAlerta("Venda registrada com sucesso!", Alert.AlertType.INFORMATION);
                
            } catch (Exception e) {
                mostrarAlerta("Erro ao registrar venda: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleAtualizar() {
        Venda vendaSelecionada = tableView.getSelectionModel().getSelectedItem();
        
        if (vendaSelecionada != null && validarCampos()) {
            try {
                int quantidadeAntiga = vendaSelecionada.getQuantidade();
                Produto produtoAntigo = encontrarProdutoPorId(vendaSelecionada.getProdutoId());
                
                LocalDate dataVenda = dateDataVenda.getValue();
                Cliente clienteSelecionado = comboCliente.getValue();
                Produto produtoSelecionado = comboProduto.getValue();
                int quantidadeNova = Integer.parseInt(txtQuantidade.getText());
                double valorTotal = calcularValorTotalNumerico();
                
                int estoqueNecessario = quantidadeNova;
                if (produtoSelecionado.getId() == produtoAntigo.getId()) {
                    estoqueNecessario = quantidadeNova - quantidadeAntiga;
                }
                
                if (estoqueNecessario > produtoSelecionado.getEstoque()) {
                    mostrarAlerta("Estoque insuficiente! Disponível: " + 
                                produtoSelecionado.getEstoque() + " unidades");
                    return;
                }
                
                vendaSelecionada.setDataVenda(dataVenda);
                vendaSelecionada.setClienteId(clienteSelecionado.getId());
                vendaSelecionada.setProdutoId(produtoSelecionado.getId());
                vendaSelecionada.setQuantidade(quantidadeNova);
                vendaSelecionada.setValorTotal(valorTotal);
                
                vendaDAO.update(vendaSelecionada);
                
                if (produtoSelecionado.getId() == produtoAntigo.getId()) {
                    produtoSelecionado.setEstoque(produtoSelecionado.getEstoque() - (quantidadeNova - quantidadeAntiga));
                } else {
                    produtoAntigo.setEstoque(produtoAntigo.getEstoque() + quantidadeAntiga);
                    produtoSelecionado.setEstoque(produtoSelecionado.getEstoque() - quantidadeNova);
                    produtoDAO.update(produtoAntigo);
                }
                produtoDAO.update(produtoSelecionado);
                
                carregarDados();
                
                limparCampos();
                
                mostrarAlerta("Venda atualizada com sucesso!", Alert.AlertType.INFORMATION);
                
            } catch (Exception e) {
                mostrarAlerta("Erro ao atualizar venda: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Selecione uma venda para atualizar!");
        }
    }
    
    @FXML
    private void handleExcluir() {
        Venda vendaSelecionada = tableView.getSelectionModel().getSelectedItem();
        
        if (vendaSelecionada != null) {
            try {
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacao.setTitle("Confirmação de Exclusão");
                confirmacao.setHeaderText(null);
                confirmacao.setContentText("Tem certeza que deseja excluir esta venda?\n" +
                                         "Esta ação devolverá o estoque do produto.");
                
                if (confirmacao.showAndWait().get() == ButtonType.OK) {
                    // Devolve o estoque do produto
                    Produto produto = encontrarProdutoPorId(vendaSelecionada.getProdutoId());
                    if (produto != null) {
                        produto.setEstoque(produto.getEstoque() + vendaSelecionada.getQuantidade());
                        produtoDAO.update(produto);
                    }
                    
                    vendaDAO.delete(vendaSelecionada.getId());
                    
                    carregarDados();
                    
                    limparCampos();
                    
                    mostrarAlerta("Venda excluída com sucesso!", Alert.AlertType.INFORMATION);
                }
                
            } catch (Exception e) {
                mostrarAlerta("Erro ao excluir venda: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Selecione uma venda para excluir!");
        }
    }
    
    @FXML
    private void handleLimpar() {
        limparCampos();
        tableView.getSelectionModel().clearSelection();
    }
    
    private void limparCampos() {
        dateDataVenda.setValue(LocalDate.now());  
        comboCliente.getSelectionModel().clearSelection();
        comboProduto.getSelectionModel().clearSelection();
        txtQuantidade.clear();
        txtValorUnitario.clear();
        lblValorTotal.setText("R$ 0,00");
        lblEstoqueDisponivel.setText("Estoque: --");
    }
    
    private Produto encontrarProdutoPorId(int id) {
        for (Produto produto : produtosList) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }
    
    private double calcularValorTotalNumerico() {
        Produto produtoSelecionado = comboProduto.getValue();
        String quantidadeText = txtQuantidade.getText();
        
        if (produtoSelecionado != null && !quantidadeText.isEmpty()) {
            try {
                int quantidade = Integer.parseInt(quantidadeText);
                return quantidade * produtoSelecionado.getPreco();
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }
    
    private boolean validarCampos() {

        if (dateDataVenda.getValue() == null) {
            mostrarAlerta("Data da venda é obrigatória!");
            dateDataVenda.requestFocus();
            return false;
        }
        
        if (comboCliente.getValue() == null) {
            mostrarAlerta("Selecione um cliente!");
            comboCliente.requestFocus();
            return false;
        }
        
        if (comboProduto.getValue() == null) {
            mostrarAlerta("Selecione um produto!");
            comboProduto.requestFocus();
            return false;
        }
        
        if (txtQuantidade.getText().isEmpty()) {
            mostrarAlerta("Quantidade é obrigatória!");
            txtQuantidade.requestFocus();
            return false;
        }
        
        try {
            int quantidade = Integer.parseInt(txtQuantidade.getText());
            if (quantidade <= 0) {
                mostrarAlerta("Quantidade deve ser maior que zero!");
                txtQuantidade.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Quantidade deve ser um número inteiro válido!");
            txtQuantidade.requestFocus();
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
        }
        
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}