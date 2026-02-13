package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        primaryStage.setTitle("Sistema de Vendas - CRUD MVC");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        System.out.println(">> VERSÃO DO JAVA RODANDO: " + System.getProperty("java.version"));
        System.out.println(">> LOCAL DO JAVA: " + System.getProperty("java.home"));
        launch(args);
    }
}