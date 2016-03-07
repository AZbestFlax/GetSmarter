package image;

import javafx.scene.image.ImageView;

import static puzzle.Parameters.BTN_SIZE;
import static puzzle.Parameters.PUZZLE_SIZE;

@Deprecated
public class Glyph {

    //private ImageView[][][] images;
    private ImageView[][] images;

    public Glyph() {
        //images = new ImageView[PUZZLE_SIZE][PUZZLE_SIZE][PUZZLE_SIZE];
        images = new ImageView[PUZZLE_SIZE][PUZZLE_SIZE];
        for ( int i = 0; i < PUZZLE_SIZE; i++ ) {
            for ( int j = 0; j < PUZZLE_SIZE; j++ ) {
                for ( int k = 0; k < PUZZLE_SIZE; k++ ) {
                    String source = "" + ((i + 1) * 10 + j + 1);
                    images[i][j] = new ImageView("image/" + source + ".png");
                    images[i][j].setFitHeight(BTN_SIZE);
                    images[i][j].setFitWidth(BTN_SIZE);
                }
            }
        }
    }

    /*
    public ImageView getGlyph(int i, int j, int k) {
        return images[i][j][k];
    }
    //*/

    //*
    public ImageView getGlyph(int i, int j) {
        return images[i][j];
    }
    //*/

}
