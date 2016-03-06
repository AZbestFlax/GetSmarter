package forms;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static puzzle.Parameters.PUZZLE_SIZE;

public class TestForm extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GS");
        BorderPane rootPane = new BorderPane();
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);

        GridPane gridGame = new GridPane();
        for (int i = 0; i < PUZZLE_SIZE; i++) {
            gridGame.addColumn(i);
            gridGame.addRow(i);
        }
        gridGame.setAlignment(Pos.CENTER);
        //gridGame.setHgap(100);
        //gridGame.setVgap(100);
        gridGame.setGridLinesVisible(true);
        //gridGame.setMinSize(30.0, 30.0);
        rootPane.setCenter(gridGame);

        Button btn1 = new Button("Button1");
        btn1.setMinSize(30.0, 30.0);
        gridGame.add(btn1, 2, 2);

        Button btn2 = new Button("Button1");
        btn2.setMinSize(30.0, 30.0);
        gridGame.add(btn2, 1, 2);

        scene.getStylesheets().add
                (getClass().getResource("../styles/TestForm.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
