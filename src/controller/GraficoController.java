package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import dao.ClienteDAO;
import dao.ProdutoDAO;
import dao.VendaDAO;
import dao.CategoriaDAO;
import model.Cliente;
import model.Produto;
import model.Venda;
import model.Categoria;

import java.util.List;

public class GraficoController {

    @FXML
    private BarChart<String, Number> barChartClientes;

    @FXML
    private PieChart pieChartClientes;

    @FXML
    private BarChart<String, Number> barChartProdutos;

    @FXML
    private PieChart pieChartProdutos;

    @FXML
    private BarChart<String, Number> barChartVendas;

    @FXML
    private PieChart pieChartVendas;

    @FXML
    private BarChart<String, Number> barChartCategorias;

    @FXML
    private PieChart pieChartCategorias;

    private ClienteDAO clienteDAO;
    private ProdutoDAO produtoDAO;
    private VendaDAO vendaDAO;
    private CategoriaDAO categoriaDAO;

    @FXML
    public void initialize() {
        clienteDAO = new ClienteDAO();
        produtoDAO = new ProdutoDAO();
        vendaDAO = new VendaDAO();
        categoriaDAO = new CategoriaDAO();

        carregarGraficosClientes();
        carregarGraficosProdutos();
        carregarGraficosVendas();
        carregarGraficosCategorias();
    }

    private void carregarGraficosClientes() {
        List<Cliente> clientes = clienteDAO.listarTodos();

        // BarChart para Clientes
        XYChart.Series<String, Number> seriesClientes = new XYChart.Series<>();
        seriesClientes.setName("Clientes");
        for (Cliente cliente : clientes) {
            seriesClientes.getData().add(new XYChart.Data<>(cliente.getNome(), cliente.getId()));
        }
        barChartClientes.getData().add(seriesClientes);

        // PieChart para Clientes
        for (Cliente cliente : clientes) {
            PieChart.Data slice = new PieChart.Data(cliente.getNome(), cliente.getId());
            pieChartClientes.getData().add(slice);
        }
    }

    private void carregarGraficosProdutos() {
        List<Produto> produtos = produtoDAO.listarTodos();

        // BarChart para Produtos
        XYChart.Series<String, Number> seriesProdutos = new XYChart.Series<>();
        seriesProdutos.setName("Produtos");
        for (Produto produto : produtos) {
            seriesProdutos.getData().add(new XYChart.Data<>(produto.getNome(), produto.getQuantidadeEstoque()));
        }
        barChartProdutos.getData().add(seriesProdutos);

        // PieChart para Produtos
        for (Produto produto : produtos) {
            PieChart.Data slice = new PieChart.Data(produto.getNome(), produto.getQuantidadeEstoque());
            pieChartProdutos.getData().add(slice);
        }
    }

    private void carregarGraficosVendas() {
        List<Venda> vendas = vendaDAO.listarTodos();

        // BarChart para Vendas
        XYChart.Series<String, Number> seriesVendas = new XYChart.Series<>();
        seriesVendas.setName("Vendas");
        for (Venda venda : vendas) {
            seriesVendas.getData().add(new XYChart.Data<>(venda.getProduto().getNome(), venda.getValor()));
        }
        barChartVendas.getData().add(seriesVendas);

        // PieChart para Vendas
        for (Venda venda : vendas) {
            PieChart.Data slice = new PieChart.Data(venda.getProduto().getNome(), venda.getValor());
            pieChartVendas.getData().add(slice);
        }
    }

    private void carregarGraficosCategorias() {
        List<Categoria> categorias = categoriaDAO.listarTodos();

        // BarChart para Categorias
        XYChart.Series<String, Number> seriesCategorias = new XYChart.Series<>();
        seriesCategorias.setName("Categorias");
        for (Categoria categoria : categorias) {
            seriesCategorias.getData().add(new XYChart.Data<>(categoria.getNome(), categoria.getQuantidadeProdutos()));
        }
        barChartCategorias.getData().add(seriesCategorias);

        // PieChart para Categorias
        for (Categoria categoria : categorias) {
            PieChart.Data slice = new PieChart.Data(categoria.getNome(), categoria.getQuantidadeProdutos());
            pieChartCategorias.getData().add(slice);
        }
    }
}