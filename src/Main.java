import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    @SuppressWarnings("UseSpecificCatch")
    public void start(Stage primaryStage) {
        // Elementos da calculadora
        TextField num1Field = new TextField();
        num1Field.setPromptText("Número 1");

        TextField num2Field = new TextField();
        num2Field.setPromptText("Número 2");

        ComboBox<String> operacaoBox = new ComboBox<>();
        operacaoBox.getItems().addAll("+", "-", "*", "/");
        operacaoBox.setValue("+");

        Button calcularBtn = new Button("Calcular");
        Label resultadoLabel = new Label("Resultado: ");

        // Layout
        HBox entradaBox = new HBox(10, num1Field, operacaoBox, num2Field);
        VBox root = new VBox(15, entradaBox, calcularBtn, resultadoLabel);
        root.setPadding(new Insets(20));

        // Evento do botão
        calcularBtn.setOnAction((ActionEvent e) -> {
            try {
                double n1 = Double.parseDouble(num1Field.getText());
                double n2 = Double.parseDouble(num2Field.getText());
                String op = operacaoBox.getValue();
                double res = 0;
                switch (op) {
                    case "+" -> res = n1 + n2;
                    case "-" -> res = n1 - n2;
                    case "*" -> res = n1 * n2;
                    case "/" -> res = n2 != 0 ? n1 / n2 : Double.NaN;
                }
                resultadoLabel.setText("Resultado: " + res);
            } catch (Exception ex) {
                resultadoLabel.setText("Erro: entrada inválida");
            }
        });

        Scene scene = new Scene(root, 350, 150);
        primaryStage.setTitle("Calculadora FX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}