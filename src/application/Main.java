package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        try {
            mainStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
            Scene scene = new Scene(root);
            mainStage.setTitle("Cadastro de Clientes");
            mainStage.setScene(scene);
            mainStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}