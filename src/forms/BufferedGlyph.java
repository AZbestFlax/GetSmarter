package forms;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static puzzle.Parameters.PUZZLE_SIZE;

public class BufferedGlyph {

    private final int SZ = 436;

    private BufferedImage[][] images;

    public BufferedGlyph() {
        images = new BufferedImage[PUZZLE_SIZE][PUZZLE_SIZE];
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File("src/image/All.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for ( int i = 0; i < PUZZLE_SIZE; i++ ) {
            for ( int j = 0; j < PUZZLE_SIZE; j++ ) {
                    images[i][j] = bi.getSubimage(i*SZ, j*SZ, SZ, SZ);
            }
        }
    }

    public BufferedImage getGlyph(int row, int col) {
        return images[row][col];
    }

}
