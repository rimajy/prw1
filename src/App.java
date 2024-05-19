//Накреслити Спінер
//Зробив Тарасенко Тимофій
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class App extends Application {


    public void start(Stage primaryStage) {
        primaryStage.setTitle("Графік Функції");

        // Створення осей
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Вісь X");
        yAxis.setLabel("Вісь Y");

        // Створення графіка
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("Побудова Функції");

        // Поля вводу для діапазонів та коефіцієнта
        TextField minXField = new TextField("-10");
        TextField maxXField = new TextField("10");
        TextField minYField = new TextField("-10");
        TextField maxYField = new TextField("10");
        TextField coefficientField = new TextField("1");

        // Кнопка для побудови графіка
        Button plotButton = new Button("Побудувати Графік");

        // Обробник події для кнопки побудови графіка
        plotButton.setOnAction(event -> {
            double minX = Double.parseDouble(minXField.getText());
            double maxX = Double.parseDouble(maxXField.getText());
            double minY = Double.parseDouble(minYField.getText());
            double maxY = Double.parseDouble(maxYField.getText());
            double coefficient = Double.parseDouble(coefficientField.getText());

            // Виклик функції для побудови графіка
            plotFunction(scatterChart, minX, maxX, minY, maxY, coefficient);
        });

        // Кнопка для збереження графіка як PNG
        Button saveButton = new Button("Зберегти як PNG");
        saveButton.setOnAction(event -> {
            Scene scene = primaryStage.getScene();
            WritableImage image = scene.snapshot(null);
            File file = new File("scene.png");

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Сцену збережено як scene.png");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Не вдалося зберегти сцену як PNG");
                alert.showAndWait();
            }
        });

        // Розташування полів вводу та кнопок у VBox
        VBox inputBox = new VBox(10);
        inputBox.setPadding(new Insets(10));
        inputBox.getChildren().addAll(
                new Label("Зробив: Тарасенко Тимофій"),
                new Label("Мін X:"), minXField,
                new Label("Макс X:"), maxXField,
                new Label("Мін Y:"), minYField,
                new Label("Макс Y:"), maxYField,
                new Label("Коефіцієнт:"), coefficientField, plotButton, saveButton);

        // Розташування елементів у BorderPane
        BorderPane root = new BorderPane();
        root.setCenter(scatterChart);
        root.setRight(inputBox);

        // Створення та встановлення сцени
        Scene scene = new Scene(root, 900, 900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Функція для побудови графіка
    private void plotFunction(ScatterChart<Number, Number> scatterChart, double minX, double maxX, double minY,
                              double maxY, double coefficient) {
        scatterChart.getData().clear();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Функція: X^2 + Y^2 – 4X * (X^2 - 3 * Y^2) / (X^2+Y^2) + 4 – (X^2 + Y^2 - 3X * (X^2 – 3 * Y^2) / (X^2 + Y^2) + 1)^2 = 0");
        double c = 1/coefficient;
        // Додавання точок на графік
        for (double x = minX; x <= maxX; x += 0.01) {
            for (double y = minY; y <= maxY; y += 0.01) {
                double functionValue = Math.pow(c*x, 2) + Math.pow(y, 2) - 4 * c*x * (Math.pow(c*x, 2) - 3 * Math.pow(y, 2)) / (Math.pow(c*x, 2) + Math.pow(y, 2)) + 4 -
                        Math.pow((Math.pow(c*x, 2) + Math.pow(y, 2) - 3 * c*x * (Math.pow(c*x, 2) - 3 * Math.pow(y, 2)) / (Math.pow(c*x, 2) + Math.pow(y, 2))), 2);
                if (Math.abs(functionValue) < 0.5) {
                    XYChart.Data<Number, Number> data = new XYChart.Data<>(x, y);
                    Circle circle = new Circle();
                    circle.setRadius(0.5);
                    data.setNode(circle);
                    series.getData().add(data);
                }
            }
        }

        // Додавання серії на графік
        scatterChart.getData().add(series);
    }

    public static void main(String[] args) {
        launch(args);
    }
}



