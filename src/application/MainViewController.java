package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private TableView<Cliente> tabelaClientes;
    @FXML
    private TableColumn<Cliente, Integer> colunaId;
    @FXML
    private TableColumn<Cliente, String> colunaNome;
    @FXML
    private TableColumn<Cliente, String> colunaEmail;
    @FXML
    private Button adicionarBtn;
    @FXML
    private Button editarBtn;
    @FXML
    private Button excluirBtn;

    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configura as colunas da tabela para se vincularem às propriedades da classe Cliente
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Adiciona alguns clientes de exemplo
        listaClientes.add(new Cliente(1, "João Silva", "joao@email.com"));
        listaClientes.add(new Cliente(2, "Maria Souza", "maria@email.com"));
        tabelaClientes.setItems(listaClientes);
    }

    @FXML
    private void handleAdicionar() {
        // Lógica para abrir a tela de formulário para adicionar um novo cliente
        System.out.println("Botão Adicionar clicado!");
    }

    @FXML
    private void handleEditar() {
        // Lógica para abrir a tela de formulário com os dados do cliente selecionado
        Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            System.out.println("Botão Editar clicado para: " + clienteSelecionado.getNome());
        }
    }

    @FXML
    private void handleExcluir() {
        // Lógica para remover o cliente selecionado da lista e da persistência
        Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            listaClientes.remove(clienteSelecionado);
            System.out.println("Cliente " + clienteSelecionado.getNome() + " excluído.");
        }
    }
}