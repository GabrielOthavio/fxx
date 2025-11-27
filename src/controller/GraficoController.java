package controller;

import dao.VendaDAO;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;

import java.util.Map;

public class GraficoController {
    
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    
    private VendaDAO vendaDAO;
    
    @FXML
    public void initialize() {
        vendaDAO = new VendaDAO();
        carregarGrafico();
    }
    
    private void carregarGrafico() {
        try {
            Map<String, Double> vendasPorMes = vendaDAO.getVendasMensais();
            
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Vendas Mensais");
            
            for (Map.Entry<String, Double> entry : vendasPorMes.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
            
            barChart.getData().clear();
            barChart.getData().add(series);
            
        } catch (Exception e) {
            mostrarAlerta("Erro ao carregar gráfico: " + e.getMessage());
        }
    }
    
    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}