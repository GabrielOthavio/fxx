package controller;

import dao.VendaDAO;
import model.Venda;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class VendaController {
    @FXML private TableView<Venda> tabelaView;
    
    @FXML private TableColumn<Venda, Integer> colunaId;
    @FXML private TableColumn<Venda, Integer> colunaClienteId;
    @FXML private TableColumn<Venda, LocalDate> colunaData;
    @FXML private TableColumn<Venda, Double> colunaTotal;
    
    @FXML private TextField txtClienteId;
    @FXML private DatePicker txtData;
    @FXML private TextField txtTotal;

    private ObservableList<Venda> vendaLista;
    private VendaDAO vendaDAO;

    @FXML
    public void initialize() {
        vendaDAO = new VendaDAO();
        vendaLista = FXCollections.observableArrayList();
        
        tabelaView.setItems(vendaLista);
        
        colunaId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colunaClienteId.setCellValueFactory(cellData -> cellData.getValue().clienteIdProperty().asObject());
        colunaData.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
        colunaTotal.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        
        carregarVendas();
        
        tabelaView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarVenda(newValue));
    }
    
    private void carregarVendas() {
        try {
            vendaLista.clear();
            vendaLista.addAll(vendaDAO.read());
        } catch (Exception e) {
            mostrarAlerta("Erro ao carregar vendas: " + e.getMessage());
        }
    }

    private void selecionarVenda(Venda venda) {
        if (venda != null) {
            txtClienteId.setText(String.valueOf(venda.getClienteId()));
            txtData.setValue(venda.getData());
            txtTotal.setText(String.valueOf(venda.getTotal()));
        }
    }
    
    @FXML
    private void handleSalvar() {
        if (validarCampos()) {
            try {
                int clienteId = Integer.parseInt(txtClienteId.getText());
                LocalDate data = txtData.getValue();
                double total = Double.parseDouble(txtTotal.getText());
                
                Venda venda = new Venda();
                venda.setClienteId(clienteId);
                venda.setData(data);
                venda.setTotal(total);
                
                vendaDAO.create(venda);
                carregarVendas();
                limparCampos();
                mostrarSucesso("Venda adicionada com sucesso!");
                
            } catch (NumberFormatException e) {
                mostrarAlerta("Cliente ID e Total devem ser números válidos!");
            } catch (Exception e) {
                mostrarAlerta("Erro ao salvar venda: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleAtualizar() {
        Venda vendaSelecionada = tabelaView.getSelectionModel().getSelectedItem();
        
        if (vendaSelecionada == null) {
            mostrarAlerta("Selecione uma venda para atualizar!");
            return;
        }
        
        if (validarCampos()) {
            try {
                int clienteId = Integer.parseInt(txtClienteId.getText());
                LocalDate data = txtData.getValue();
                double total = Double.parseDouble(txtTotal.getText());
                
                vendaSelecionada.setClienteId(clienteId);
                vendaSelecionada.setData(data);
                vendaSelecionada.setTotal(total);
                
                vendaDAO.atualizar(vendaSelecionada);
                carregarVendas();
                limparCampos();
                mostrarSucesso("Venda atualizada com sucesso!");
                
            } catch (NumberFormatException e) {
                mostrarAlerta("Cliente ID e Total devem ser números válidos!");
            } catch (Exception e) {
                mostrarAlerta("Erro ao atualizar venda: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleDeletar() {
        Venda vendaSelecionada = tabelaView.getSelectionModel().getSelectedItem();
        
        if (vendaSelecionada != null) {
            try {
                vendaDAO.deletar(vendaSelecionada.getId());
                carregarVendas();
                limparCampos();
                mostrarSucesso("Venda deletada com sucesso!");
            } catch (Exception e) {
                mostrarAlerta("Erro ao deletar venda: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Selecione uma venda para deletar!");
        }
    }
    
    @FXML
    private void handleLimpar() {
        limparCampos();
        tabelaView.getSelectionModel().clearSelection();
    }
    
    private boolean validarCampos() {
        if (txtClienteId.getText().trim().isEmpty()) {
            mostrarAlerta("Cliente ID é obrigatório!");
            return false;
        }
        if (txtData.getValue() == null) {
            mostrarAlerta("Data é obrigatória!");
            return false;
        }
        if (txtTotal.getText().trim().isEmpty()) {
            mostrarAlerta("Total é obrigatório!");
            return false;
        }
        return true;
    }
    
    private void limparCampos() {
        txtClienteId.clear();
        txtData.setValue(null);
        txtTotal.clear();
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
