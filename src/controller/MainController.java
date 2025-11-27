package controller;

import javafx.fxml.FXML;           
import javafx.fxml.FXMLLoader;     
import javafx.scene.Parent;        
import javafx.scene.Scene;         
import javafx.scene.control.Alert; 
import javafx.stage.Stage;         
import java.io.IOException;        


public class MainController {
    
    @FXML
    private void abrirClientes() {
        carregarTela("/view/ClientesView.fxml", "Gerenciar Clientes");
    }
    
    @FXML
    private void abrirProdutos() {
        carregarTela("/view/ProdutosView.fxml", "Gerenciar Produtos");
    }
    
    @FXML
    private void abrirCategorias() {
        carregarTela("/view/CategoriasView.fxml", "Gerenciar Categorias");
    }
    
    @FXML
    private void abrirVendas() {
        carregarTela("/view/VendasView.fxml", "Gerenciar Vendas");
    }
    
    @FXML
    private void abrirGrafico() {
        carregarTela("/view/GraficoView.fxml", "Gráfico de Vendas Mensal");
    }
    
    private void carregarTela(String fxml, String titulo) {
        try {
            
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
           
            Stage stage = new Stage();
            
            stage.setTitle(titulo);
            
            stage.setScene(new Scene(root));
            
            stage.show();
            
        } catch (IOException e) {
            mostrarAlerta("Erro ao carregar a tela: " + e.getMessage());
        }
    }
   
    private void mostrarAlerta(String mensagem) {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
       
        alert.setTitle("Erro");
       
        alert.setContentText(mensagem);
        
        alert.showAndWait();
    }
}