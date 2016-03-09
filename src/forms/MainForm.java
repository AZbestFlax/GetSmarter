package forms;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import puzzle.Puzzle;
import puzzle.VisualHints;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static puzzle.Parameters.*;

public class MainForm extends Application {

    Puzzle puzzle;
    private boolean[][] openButtons;
    private boolean[][] guessedButtons;
    BufferedGlyph glyph;
    VisualHints vhh;
    ImageView field;
    BufferedImage backgroundField;
    Coordinator coordinator;
    final Stage dialog = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception{

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text("Wrong"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);

        reset();

        BorderPane root = new BorderPane();
        VBox leftSide = new VBox();
        ScrollPane rightSide = new ScrollPane();
        rightSide.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightSide.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        root.setCenter(leftSide);
        root.setRight(rightSide);

        FlowPane horizontalHints = new FlowPane();
        horizontalHints.setAlignment(Pos.TOP_CENTER);
        horizontalHints.setVgap(8);
        horizontalHints.setPrefWrapLength(BTN_SIZE * 12 + 40);
        horizontalHints.setPrefWidth(BTN_SIZE * 12 + 40);
        horizontalHints.setHgap(4);

        if ( !vhh.horizontalList.isEmpty() ) {
            Button[] horizontalButton = new Button[vhh.horizontalList.size()];
            int i = 0;
            for (ImageView img : vhh.horizontalList) {
                horizontalButton[i] = new Button("", img);
                horizontalButton[i].setOnMouseClicked( (ae) -> {
                    ((Button)(ae).getSource()).setOpacity(0.2);
                    ((Button)(ae).getSource()).setDisable(true);
                } );
                horizontalHints.getChildren().add(horizontalButton[i++]);
            }
        }

        rightSide.setContent(horizontalHints);

        field = new ImageView();
        field.setOnMouseClicked( (ae) -> {
            Coordinate c = coordinator.getCoordinate(ae.getX(), ae.getY());
            if (c != null) {
                if ( openButtons[c.row][c.col] || guessedButtons[c.row][c.but] ) return;
                if ( (ae).getButton().equals(MouseButton.PRIMARY) ) {
                    if ( c.but != puzzle.getRightValue(c.row, c.col) - 1 ) {
                        dialog.show();
                        return;
                    }
                    puzzle.possibilities.set(c.col, c.row, c.but+1);
                } else if ( (ae).getButton().equals(MouseButton.SECONDARY) ) {
                    if ( c.but == puzzle.getRightValue(c.row, c.col) - 1 ) {
                        dialog.show();
                        return;
                    }
                    puzzle.possibilities.exclude(c.col, c.row, c.but+1);
                }
                updateButton(c.row);
                field.setImage(SwingFXUtils.toFXImage(backgroundField, null));
            }
        } );

        ScrollPane bottomSide = new ScrollPane();
        FlowPane verticalHints = new FlowPane();
        verticalHints.setPrefWrapLength(BTN_SIZE * 4 + 10);
        verticalHints.setPrefHeight(BTN_SIZE * 4 + 10);

        if ( !vhh.verticalList.isEmpty() ) {
            Button[] verticalButton = new Button[vhh.verticalList.size()];
            int i = 0;
            for (ImageView img : vhh.verticalList) {
                verticalButton[i] = new Button("", img);
                verticalButton[i].setOnMouseClicked( (ae) -> {
                ((Button)(ae).getSource()).setOpacity(0.2);
                ((Button)(ae).getSource()).setDisable(true);
                } );
                verticalHints.getChildren().add(verticalButton[i++]);
            }
        }

        bottomSide.setContent(verticalHints);
        bottomSide.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        bottomSide.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        leftSide.setOpaqueInsets(new Insets(10.0));
        leftSide.setPrefWidth( (Math.ceil(Math.sqrt(PUZZLE_SIZE))*BTN_SIZE + 10.0)*PUZZLE_SIZE );
        leftSide.getChildren().addAll(field, bottomSide);

        //*
        for (int i = 0; i < PUZZLE_SIZE; i++)
            updateButton(i);
        //*/
        /*
        info = new Label();
        root.setRight(info);
*/

        field.setImage(SwingFXUtils.toFXImage(backgroundField, null));
        primaryStage.setTitle("GS");
        root.setPrefHeight(GRID_SIZE + verticalHints.getPrefHeight());
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root, GRID_SIZE + horizontalHints.getPrefWidth(), GRID_SIZE + verticalHints.getPrefHeight()));
        primaryStage.show();
    }

    public void reset() {
        puzzle = new Puzzle();
        glyph = new BufferedGlyph();
        vhh = new VisualHints(puzzle.getRules());
        coordinator = new Coordinator(PUZZLE_SIZE);

        openButtons = new boolean[PUZZLE_SIZE][PUZZLE_SIZE];
        guessedButtons = new boolean[PUZZLE_SIZE][PUZZLE_SIZE];
        for ( int i = 0; i < PUZZLE_SIZE; i++) {
            for ( int j = 0; j < PUZZLE_SIZE; j++) {
                openButtons[i][j] = false;
                guessedButtons[i][j] = false;
            }
        }

        int buttonSize = (int) Coordinator.getButtonSize();
        field = new ImageView();
        backgroundField = new BufferedImage((int)GRID_SIZE + 5, (int)GRID_SIZE, TYPE_INT_ARGB);
        Graphics graph = backgroundField.getGraphics();
        for (int i = 0; i < PUZZLE_SIZE; i++) {
            for ( int j = 0; j < PUZZLE_SIZE; j++ ) {
                for ( int k = 0; k < PUZZLE_SIZE; k++ ) {
                    Position pos = coordinator.getPosition(i, j, k);
                    graph.drawImage(
                            glyph.getGlyph(i, k).getScaledInstance(buttonSize, buttonSize, Image.SCALE_DEFAULT)
                    , (int)pos.x, (int)pos.y, null);
                }
            }
        }
    }

    private void updateButton(int i) {
        for ( int j = 0; j < PUZZLE_SIZE; j++ ) {
            if ( puzzle.possibilities.isDefined(j, i) ) {
                setButtonActive(i, j, puzzle.possibilities.getDefined(j, i)-1);
                guessedButtons[i][puzzle.possibilities.getDefined(j, i)-1] = true;
            } else {
                for ( int k = 0; k < PUZZLE_SIZE; k++ ) {
                    if ( !puzzle.possibilities.isAccessible(j, i, k) ) {
                        setButtonInactive(i, j, k);
                    }
                }
            }
        }
    }

    private void setButtonActive(int i, int j, int k) {
        if ( !openButtons[i][j] ) {
            Graphics graph = backgroundField.getGraphics();
            Position pos = coordinator.getPosition(i, j, 0);
            graph.drawImage(
                    glyph.getGlyph(i, k).getScaledInstance(Coordinator.getGridCellSize(), Coordinator.getGridCellSize(), Image.SCALE_DEFAULT)
                    , (int)pos.x, (int)pos.y - Coordinator.getTopIndent(), null);
            openButtons[i][j] = true;
        }
    }

    private void setButtonInactive(int i, int j, int k) {
        if ( !openButtons[i][j] ) {
            Graphics graph = backgroundField.getGraphics();
            Position pos = coordinator.getPosition(i, j, k);

            graph.setColor(graph.getColor());
            graph.fillRect((int)pos.x, (int)pos.y, (int)Coordinator.getButtonSize(), (int)Coordinator.getButtonSize());

//            graph.clearRect((int)pos.x, (int)pos.y, (int)Coordinator.getButtonSize(), (int)Coordinator.getButtonSize());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
