import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VectorVisualizer extends Application {
    private static final int WIDTH = 500, HEIGHT = 500;
    private static final int ORIGIN_X = WIDTH / 2, ORIGIN_Y = HEIGHT / 2;
    private Canvas canvas;
    private GraphicsContext gc;

    @Override
    public void start(Stage stage) {
        showInputStage(stage);
    }

    private void showInputStage(Stage stage) {
        Label label1 = new Label("Enter Vector 1 (one, two):");
        TextField vector1Field = new TextField();
        ChoiceBox<String> format1Box = new ChoiceBox<>();
        format1Box.getItems().addAll("Magnitude & Angle", "Components (x, y)");
        format1Box.setValue("Magnitude & Angle");

        Label label2 = new Label("Enter Vector 2 (one, two):");
        TextField vector2Field = new TextField();
        ChoiceBox<String> format2Box = new ChoiceBox<>();
        format2Box.getItems().addAll("Magnitude & Angle", "Components (x, y)");
        format2Box.setValue("Magnitude & Angle");

        Label operationLabel = new Label("Select Operation:");
        ChoiceBox<String> operationBox = new ChoiceBox<>();
        operationBox.getItems().addAll("Add", "Subtract", "Dot Product", "Cross Product");
        operationBox.setValue("Add");

        Button submitButton = new Button("Compute");
        System.out.println("Vector 1: red, Vector 2: blue, Result: green");
        submitButton.setOnAction(e -> {
            String v1Text = vector1Field.getText();
            String v2Text = vector2Field.getText();
            String operation = operationBox.getValue();

            boolean isV1Magnitude = format1Box.getValue().equals("Magnitude & Angle");
            boolean isV2Magnitude = format2Box.getValue().equals("Magnitude & Angle");

            Vector v1 = parseVector(v1Text, isV1Magnitude);
            Vector v2 = parseVector(v2Text, isV2Magnitude);

            if (v1 != null && v2 != null) {
                showResultStage(stage, v1, v2, operation);
            } else {
                System.out.println("Invalid input format.");
            }
        });

        VBox layout = new VBox(10, label1, vector1Field, format1Box, label2, vector2Field, format2Box, operationLabel, operationBox, submitButton);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 350);
        stage.setScene(scene);
        stage.setTitle("Vector Input");
        stage.show();
    }

    private void showResultStage(Stage stage, Vector v1, Vector v2, String operation) {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Vector result = null;
        switch (operation) {
            case "Add":
                result = Vector.add(v1, v2);
                break;
            case "Subtract":
                result = Vector.subtract(v1, v2);
                break;
            case "Dot Product":
                System.out.println("Dot Product: " + Vector.dotProduct(v1, v2));
                break;
            case "Cross Product":
                System.out.println("Cross Product: " + Vector.crossProduct(v1, v2));
                break;
        }

        drawVector(v1, Color.RED);
        drawVector(v2, Color.BLUE);
        if (result != null) drawVector(result, Color.GREEN);

        BorderPane root = new BorderPane();
        root.setCenter(canvas);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Vector Visualization");
        stage.show();
    }

    private void drawVector(Vector v, Color color) {
        gc.setStroke(color);
        gc.setLineWidth(2);
        double endX = ORIGIN_X + v.getx();
        double endY = ORIGIN_Y - v.gety();
        gc.strokeLine(ORIGIN_X, ORIGIN_Y, endX, endY);

        double arrowSize = 10;
        double angle = Math.atan2(-v.gety(), v.getx());
        double arrowX1 = endX - arrowSize * Math.cos(angle - Math.PI / 6);
        double arrowY1 = endY + arrowSize * Math.sin(angle - Math.PI / 6);
        double arrowX2 = endX - arrowSize * Math.cos(angle + Math.PI / 6);
        double arrowY2 = endY + arrowSize * Math.sin(angle + Math.PI / 6);
        gc.strokeLine(endX, endY, arrowX1, arrowY1);
        gc.strokeLine(endX, endY, arrowX2, arrowY2);
    }

    private Vector parseVector(String input, boolean isMagnitudeAngle) {
        String[] parts = input.split(",");
        if (parts.length == 2) {
            try {
                double v1 = Double.parseDouble(parts[0].trim());
                double v2 = Double.parseDouble(parts[1].trim());
                return new Vector(v1, v2, isMagnitudeAngle);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
