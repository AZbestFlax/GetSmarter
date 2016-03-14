package image;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static puzzle.Parameters.BTN_SIZE;
import static puzzle.Parameters.PUZZLE_SIZE;

@Deprecated
public class Glyph {

    final int SZ = 436;

    //private ImageView[][][] images;
    private ImageView[][] images;

    public Glyph() {
        images = new ImageView[PUZZLE_SIZE][PUZZLE_SIZE];
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File("src/image/All.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for ( int i = 0; i < PUZZLE_SIZE; i++ ) {
            for ( int j = 0; j < PUZZLE_SIZE; j++ ) {
                for ( int k = 0; k < PUZZLE_SIZE; k++ ) {
                    images[i][j] = new ImageView();
                    images[i][j].setImage(
                            SwingFXUtils.toFXImage(bi.getSubimage(i*SZ, j*SZ, SZ, SZ), null)
                    );
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
