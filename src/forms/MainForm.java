package forms;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import puzzle.Puzzle;

import static puzzle.Parameters.PUZZLE_SIZE;
import static puzzle.Parameters.BTN_SIZE;

public class MainForm extends Application {

    Puzzle puzzle;
    Button[][][] tb;
    GridPane field;
    private boolean[][] openButtons;

    @Override
    public void start(Stage primaryStage) throws Exception{
        puzzle = new Puzzle();

        openButtons = new boolean[PUZZLE_SIZE][PUZZLE_SIZE];
        for ( int i = 0; i < PUZZLE_SIZE; i++) {
            for ( int j = 0; j < PUZZLE_SIZE; j++) {
                openButtons[i][j] = false;
            }
        }

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
        horizontalHints.setPrefWrapLength(140.0);
        horizontalHints.setHgap(4);
        rightSide.setContent(horizontalHints);

        field = new GridPane();

        for (int i=0; i<PUZZLE_SIZE; i++) {
            field.addColumn(i);
            field.addRow(i);
        }
        FlowPane[][] flow = new FlowPane[PUZZLE_SIZE][PUZZLE_SIZE];

        tb = new Button[PUZZLE_SIZE][PUZZLE_SIZE][PUZZLE_SIZE];
        for ( int i = 0; i < PUZZLE_SIZE; ++i ) {
            for ( int j = 0; j < PUZZLE_SIZE; ++j ) {
                flow[i][j] = new FlowPane();
                flow[i][j].setPrefWrapLength( Math.ceil(Math.sqrt(PUZZLE_SIZE))*BTN_SIZE + 10.0);
                flow[i][j].setPrefWidth( Math.ceil(Math.sqrt(PUZZLE_SIZE))*BTN_SIZE + 10.0 );
                flow[i][j].setPrefHeight( Math.ceil(Math.sqrt(PUZZLE_SIZE))*BTN_SIZE + 10.0 );
                flow[i][j].setAlignment(Pos.CENTER);
                flow[i][j].setId("" + (i*10 + j));
                field.add(flow[i][j], j, i);

                for ( int k = 0; k < PUZZLE_SIZE; ++k ) {
                    tb[i][j][k] = new Button("" + (k + 1));
                    tb[i][j][k].setPrefSize(BTN_SIZE, BTN_SIZE);
                    tb[i][j][k].setId("" + (i*100 + j*10 + k));

                    final int finalJ = j;
                    final int finalI = i;
                    final int finalK = k;
                    tb[i][j][k].setOnMouseClicked( (ae) -> {
                        if ( ae.getButton() == MouseButton.SECONDARY ) {
                            puzzle.possibilities.exclude(finalJ, finalI, finalK+1);
                        } else {
                            puzzle.possibilities.set(finalJ, finalI, finalK+1);
                        }
                        updateButton(finalI);
                    } );

                    flow[i][j].getChildren().add(tb[i][j][k]);
                }

            }
        }

        field.setGridLinesVisible(true);

        ScrollPane bottomSide = new ScrollPane();
        FlowPane verticalHints = new FlowPane();
        bottomSide.setContent(verticalHints);
        bottomSide.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        bottomSide.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        leftSide.setOpaqueInsets(new Insets(10.0));
        leftSide.getChildren().addAll(field, bottomSide);

        for (int i = 0; i < PUZZLE_SIZE; i++) {
            for (int j = 0; j < PUZZLE_SIZE; j++) {
                if (puzzle.possibilities.isDefined(i, j)) {
                }
            }
        }

        for (int i = 0; i < PUZZLE_SIZE; i++)
            updateButton(i);

        primaryStage.setTitle("GS");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }

    private void updateButton(int i) {
        for ( int j = 0; j < PUZZLE_SIZE; j++ ) {
            if ( puzzle.possibilities.isDefined(j, i) ) {
                setButtonActive(i, j, puzzle.possibilities.getDefined(j, i)-1);
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
            Button tmp = tb[i][j][k];
            field.getChildren().remove(tmp.getParent());
            tmp.setPrefWidth(Math.ceil(Math.sqrt(PUZZLE_SIZE)) * BTN_SIZE + 10.0);
            tmp.setPrefHeight(Math.ceil(Math.sqrt(PUZZLE_SIZE)) * BTN_SIZE + 10.0);
            tmp.setOnMouseClicked(null);
            field.add(tmp, j, i);
            openButtons[i][j] = true;
        }
    }

    private void setButtonInactive(int i, int j, int k) {
        Button tmp = tb[i][j][k];
        tmp.setOpacity(0.2);
        tmp.setDisable(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
