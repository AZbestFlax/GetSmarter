package forms;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
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
import java.util.Date;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static puzzle.Parameters.*;

public class MainForm extends Application {

    private double percent = 0.0;
    Puzzle puzzle;
    private boolean[][] openButtons;
    private boolean[][] guessedButtons;
    BufferedGlyph glyph;
    VisualHints vhh;
    ImageView field;
    BufferedImage backgroundField;
    Coordinator coordinator;
    final Stage dialog = new Stage();
    final Stage difficulty = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception{

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text("Wrong"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);

        difficulty.initModality(Modality.APPLICATION_MODAL);
        difficulty.initOwner(primaryStage);
        VBox diff = new VBox(3);
        diff.setAlignment(Pos.CENTER);

        ToggleGroup tg = new ToggleGroup();
        RadioButton[] btns = new RadioButton[7];
        for (int i = 0; i < 7; i++) {
            btns[i] = new RadioButton(""+(i+3));
            btns[i].setToggleGroup(tg);
            diff.getChildren().add(btns[i]);
            btns[i].setOnAction( (ae) -> {
                CurrentParameters.puzzleSize = Integer.valueOf(((RadioButton)ae.getSource()).getText());
            } );
        }
        btns[3].fire();
        Scene diffScene = new Scene(diff, 300, 200);
        difficulty.setScene(diffScene);

        puzzle = new Puzzle();
        reset();

        BorderPane root = new BorderPane();
        VBox leftSide = new VBox();
        ScrollPane rightSide = new ScrollPane();
        rightSide.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightSide.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        root.setCenter(leftSide);
        root.setRight(rightSide);

        FlowPane horizontalHints = new FlowPane();
        horizontalHints.setAlignment(Pos.TOP_LEFT);
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
                    //((Button)(ae).getSource()).setOpacity(0.2);
                    //((Button)(ae).getSource()).setDisable(true);
                    //((Button)(ae).getSource()).setVisible(false);
                    horizontalHints.getChildren().remove(ae.getSource());
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
        verticalHints.setOrientation(Orientation.VERTICAL);

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
        leftSide.setPrefWidth( (Math.ceil(Math.sqrt(puzzleSize))*BTN_SIZE + 10.0)* puzzleSize);
        leftSide.getChildren().addAll(field, bottomSide);

        //*
        for (int i = 0; i < puzzleSize; i++)
            updateButton(i);
        //*/
        /*
        info = new Label();
        root.setRight(info);
*/

        MenuBar mainMenu = new MenuBar();
        mainMenu.setPrefHeight(25);
        Menu mGame = new Menu("_Game");
        MenuItem miOpen = new MenuItem("Open");
        miOpen.setAccelerator(KeyCombination.keyCombination("shortcut+O"));
        miOpen.setMnemonicParsing(true);
        MenuItem miSave = new MenuItem("Save");
        MenuItem miReset = new MenuItem("Reset");
        MenuItem miExit = new MenuItem("Exit");
        mGame.getItems().addAll(miOpen, miSave, miReset, new SeparatorMenuItem(), miExit);
        mainMenu.getMenus().add(mGame);
        root.setTop(mainMenu);


        field.setImage(SwingFXUtils.toFXImage(backgroundField, null));
        primaryStage.setTitle("GS");
        root.setPrefHeight(GRID_SIZE + verticalHints.getPrefHeight());
        primaryStage.setResizable(false);

        /*
            Label info = new Label("Time: 00:00:00");
            Timer timer = new Timer();
            UpdateTime updateTime = new UpdateTime(info);
            timer.schedule(updateTime,0,10000);
            root.setBottom(info);
        */
        primaryStage.setScene(new Scene(root, GRID_SIZE + horizontalHints.getPrefWidth(), GRID_SIZE + verticalHints.getPrefHeight() + mainMenu.getHeight() + 35));
        primaryStage.show();
    }

    public void reset() {

        System.out.print("Start reset: ");
        System.out.println(new Date());

        glyph = new BufferedGlyph();

        System.out.print("After Glyph creation: ");
        System.out.println(new Date());

        vhh = new VisualHints(puzzle.getRules(), glyph);
        coordinator = new Coordinator(puzzleSize);



        openButtons = new boolean[puzzleSize][puzzleSize];
        guessedButtons = new boolean[puzzleSize][puzzleSize];
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                openButtons[i][j] = false;
                guessedButtons[i][j] = false;
            }
        }

        System.out.print("After Buttons creation: ");
        System.out.println(new Date());

        int buttonSize = (int) Coordinator.getButtonSize();
        field = new ImageView();
        backgroundField = new BufferedImage((int)GRID_SIZE + 5, (int)GRID_SIZE, TYPE_INT_ARGB);
        Graphics graph = backgroundField.getGraphics();
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++ ) {
                for (int k = 0; k < puzzleSize; k++ ) {
                    Position pos = coordinator.getPosition(i, j, k);
                    graph.drawImage(
                            glyph.getGlyph(i, k).getScaledInstance(buttonSize, buttonSize, Image.SCALE_DEFAULT)
                    , (int)pos.x, (int)pos.y, null);
                }
            }
        }

        System.out.print("After Buttons loading: ");
        System.out.println(new Date());

    }


    private void updateButton(int i) {
        for (int j = 0; j < puzzleSize; j++ ) {
            if ( puzzle.possibilities.isDefined(j, i) ) {
                setButtonActive(i, j, puzzle.possibilities.getDefined(j, i)-1);
                guessedButtons[i][puzzle.possibilities.getDefined(j, i)-1] = true;
            } else {
                for (int k = 0; k < puzzleSize; k++ ) {
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
