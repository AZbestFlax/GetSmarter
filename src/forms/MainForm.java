package forms;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import puzzle.Puzzle;
import puzzle.VisualHints;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static puzzle.Parameters.BTN_SIZE;
import static puzzle.Parameters.GRID_SIZE;
import static puzzle.Parameters.PUZZLE_SIZE;

public class MainForm extends Application {

    Puzzle puzzle;
    private boolean[][] openButtons;
    BufferedGlyph glyph;
    VisualHints vhh;
    ImageView field;
    BufferedImage backgroundField;
    Coordinator coordinator;

    @Override
    public void start(Stage primaryStage) throws Exception{
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
        horizontalHints.setPrefWrapLength(140.0);
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
        field.setImage(SwingFXUtils.toFXImage(backgroundField, null));


        ScrollPane bottomSide = new ScrollPane();
        FlowPane verticalHints = new FlowPane();

        if ( !vhh.verticalList.isEmpty() ) {
            Button[] veerticalButton = new Button[vhh.verticalList.size()];
            int i = 0;
            for (ImageView img : vhh.verticalList) {
                veerticalButton[i] = new Button("", img);
                veerticalButton[i].setOnMouseClicked( (ae) -> {
                ((Button)(ae).getSource()).setOpacity(0.2);
                ((Button)(ae).getSource()).setDisable(true);
                } );
                verticalHints.getChildren().add(veerticalButton[i++]);
            }
        }

        bottomSide.setContent(verticalHints);
        bottomSide.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        bottomSide.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        leftSide.setOpaqueInsets(new Insets(10.0));
        leftSide.setPrefWidth( (Math.ceil(Math.sqrt(PUZZLE_SIZE))*BTN_SIZE + 10.0)*PUZZLE_SIZE );
        leftSide.getChildren().addAll(field, bottomSide);

        for (int i = 0; i < PUZZLE_SIZE; i++)
            updateButton(i);

        primaryStage.setTitle("GS");
        primaryStage.setScene(new Scene(root, 800, 400));
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

    public void reset() {
        puzzle = new Puzzle();
        glyph = new BufferedGlyph();
        vhh = new VisualHints(puzzle.getRules());
        coordinator = new Coordinator(PUZZLE_SIZE);

        openButtons = new boolean[PUZZLE_SIZE][PUZZLE_SIZE];
        for ( int i = 0; i < PUZZLE_SIZE; i++) {
            for ( int j = 0; j < PUZZLE_SIZE; j++) {
                openButtons[i][j] = false;
            }
        }

        int buttonSize = (int) Coordinator.getButtonSize();
        field = new ImageView();
        backgroundField = new BufferedImage((int)GRID_SIZE, (int)GRID_SIZE, TYPE_INT_ARGB);
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

    private void setButtonActive(int i, int j, int k) {
        if ( !openButtons[i][j] ) {
            openButtons[i][j] = true;
        }
    }

    private void setButtonInactive(int i, int j, int k) {
    }

    public static void main(String[] args) {
        launch(args);
    }
}
