package tests;

import forms.Coordinate;
import forms.Coordinator;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static puzzle.Parameters.GRID_SIZE;

public class CoordTest extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        Coordinator coord = new Coordinator(9);

        BorderPane root = new BorderPane();

        MenuBar mainMenu = new MenuBar();
        mainMenu.setPrefHeight(30);
        Menu mFile = new Menu("File");
        MenuItem miOpen = new MenuItem("Open");
        mFile.getItems().add(miOpen);
        mainMenu.getMenus().add(mFile);
        root.setTop(mainMenu);

        Label label = new Label("Show info");
        label.setMinHeight(20.0);
        Label label2 = new Label("Coordinates");
        label2.setMinHeight(20.0);

        ImageView iv = new ImageView("image/11.png");
        iv.setFitWidth(GRID_SIZE);
        iv.setFitHeight(GRID_SIZE);
        iv.setOnMouseClicked( (ae) -> {
            if ( ae.getButton().equals(MouseButton.PRIMARY) ) {
                Coordinate c = coord.getCoordinate( ae.getX(), ae.getY() );
                if ( c != null ) label.setText("Row: " + c.row + " Col: " + c.col + " Button: " + c.but);
                else label.setText("Miss");
            }
        } );

        iv.setOnMouseMoved( (ae) -> label2.setText("X: " + ae.getX() + "\t\nY: " + ae.getY() + "\t") );


        root.setCenter(iv);


        root.setBottom(label);
        root.setRight(label2);

        primaryStage.setTitle("GS");
        primaryStage.setScene(new Scene(root, GRID_SIZE+70, GRID_SIZE+30+20));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
