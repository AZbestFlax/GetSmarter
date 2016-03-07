package forms;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static puzzle.Parameters.PUZZLE_SIZE;

public class BufferedGlyph {

    private BufferedImage[][] images;

    public BufferedGlyph() {
        images = new BufferedImage[PUZZLE_SIZE][PUZZLE_SIZE];
        try {
            for ( int i = 0; i < PUZZLE_SIZE; i++ ) {
                for ( int j = 0; j < PUZZLE_SIZE; j++ ) {
                    String source = "" + ((i + 1) * 10 + j + 1);
                        images[i][j] = ImageIO.read(new File("src/image/" + source + ".png"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getGlyph(int row, int col) {
        return images[row][col];
    }

}
