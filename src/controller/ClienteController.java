package controller;

import dao.ClienteDAO;
import model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClienteController {
    @FXML private TableView<Cliente> tabelaView;
    
    @FXML private TableColumn<Cliente, Integer> colunaId;
    @FXML private TableColumn<Cliente, String> colunaNome;
    @FXML private TableColumn<Cliente, String> colunaEmail;
    @FXML private TableColumn<Cliente, String> colunaTelefone;
    @FXML private TableColumn<Cliente, String> colunaEndereco;

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEndereco;

    private ObservableList<Cliente> clienteLista;
    private ClienteDAO clienteDAO;
    
    @FXML 
    public void initialize() {
        clienteDAO = new ClienteDAO();
        clienteLista = FXCollections.observableArrayList();

        tabelaView.setItems(clienteLista);

        colunaId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colunaNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colunaEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colunaTelefone.setCellValueFactory(cellData -> cellData.getValue().telefoneProperty());
        colunaEndereco.setCellValueFactory(cellData -> cellData.getValue().enderecoProperty());
        
        carregarClientes();

        tabelaView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarCliente(newValue));
    }
    
    private void carregarClientes() {
        try{
            clienteLista.clear();
            clienteLista.addAll(clienteDAO.read());
        } catch (Exception e) {
            mostrarAlerta("Erro ao carregar clientes: " + e.getMessage());
        }
    }

    
    private void selecionarCliente(Cliente cliente) {
        if (cliente != null) {
            txtNome.setText(cliente.getNome());
            txtEmail.setText(cliente.getEmail());
            txtTelefone.setText(cliente.getTelefone());
            txtEndereco.setText(cliente.getEndereco());
        }
    }



    @FXML
    private void handleSalvar() {
        if (validarCampos()) {
            try {
                Cliente cliente = new Cliente();
                cliente.setNome(txtNome.getText());
                cliente.setEmail(txtEmail.getText());
                cliente.setTelefone(txtTelefone.getText());
                cliente.setEndereco(txtEndereco.getText());

                clienteDAO.create(cliente);
                carregarClientes();
                limparCampos();
                mostrarSucesso("Cliente adicionado com sucesso!");

            } catch (Exception e) {
                mostrarAlerta("Erro ao salvar cliente: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleAtualizar() {
        Cliente clienteSelecionado = tabelaView.getSelectionModel().getSelectedItem();
        
        if (clienteSelecionado == null) {
            mostrarAlerta("Selecione um cliente para atualizar!");
            return;
        }
        
        if (validarCampos()) {   
            try {
                clienteSelecionado.setNome(txtNome.getText());
                clienteSelecionado.setEmail(txtEmail.getText());
                clienteSelecionado.setTelefone(txtTelefone.getText());
                clienteSelecionado.setEndereco(txtEndereco.getText());
                
                clienteDAO.atualizar(clienteSelecionado);
                carregarClientes();
                limparCampos();
                mostrarSucesso("Cliente atualizado com sucesso!");
                
            } catch (Exception e) {
                mostrarAlerta("Erro ao atualizar cliente: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleDeletar() {
        Cliente clienteSelecionado = tabelaView.getSelectionModel().getSelectedItem();
        
        if (clienteSelecionado != null) {
            try {
                clienteDAO.deletar(clienteSelecionado.getId());
                carregarClientes();
                limparCampos();
                mostrarSucesso("Cliente deletado com sucesso!");
            } catch (Exception e) {
                mostrarAlerta("Erro ao deletar cliente: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Selecione um cliente para deletar!");
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
        if (txtEmail.getText().trim().isEmpty()) {
            mostrarAlerta("Email é obrigatório!");
            return false;
        }
        if (txtTelefone.getText().trim().isEmpty()) {
            mostrarAlerta("Telefone é obrigatório!");
            return false;
        }
        if (txtEndereco.getText().trim().isEmpty()) {
            mostrarAlerta("Endereço é obrigatório!");
            return false;
        }
        return true;
    }
    
    private void limparCampos() {
        txtNome.clear();
        txtEmail.clear();
        txtTelefone.clear();
        txtEndereco.clear();
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