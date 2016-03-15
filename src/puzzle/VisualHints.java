package puzzle;

import forms.BufferedGlyph;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import rules.BetweenRule;
import rules.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static puzzle.Parameters.BTN_SIZE;

public class VisualHints {

    public ArrayList<ImageView> horizontalList = new ArrayList<>();
    public ArrayList<ImageView> verticalList = new ArrayList<>();
    private BufferedGlyph bg;

    public VisualHints(ArrayList<Rule> rules, BufferedGlyph bg) {
        this.bg = bg;
        int u = 1;

        BufferedImage left = null;
        BufferedImage near = null;
        try {
            left = ImageIO.read(new File("src/image/left.png"));
            near = ImageIO.read(new File("src/image/near.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (Rule r: rules) {
            BufferedImage tmpImage;
            if ( r.getClass().equals(rules.BetweenRule.class) ) {
                tmpImage = new BufferedImage((int)BTN_SIZE*2*3, (int)BTN_SIZE*2, TYPE_INT_ARGB);
                BetweenRule t = (BetweenRule)r;
                BufferedImage bi = bg.getGlyph(t.row1, t.thing1-1);
                Image scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                tmpImage.getGraphics().drawImage( scaled , 0, 0, null);
                bi = bg.getGlyph(t.centerRow, t.centerThing-1);
                scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                tmpImage.getGraphics().drawImage( scaled , (int)BTN_SIZE*2, 0, null);
                bi = bg.getGlyph(t.row2, t.thing2-1);
                scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                tmpImage.getGraphics().drawImage( scaled, (int)BTN_SIZE*2*2, 0, null);
                ImageView iv = new ImageView();
                iv.setImage(SwingFXUtils.toFXImage(tmpImage, null));
                horizontalList.add(iv);
            } else if ( r.getClass().equals(rules.DirectionRule.class) ) {
                tmpImage = new BufferedImage((int)BTN_SIZE*2*3, (int)BTN_SIZE*2, TYPE_INT_ARGB);
                DirectionRule t = (DirectionRule)r;
                BufferedImage bi = bg.getGlyph(t.row1, t.thing1-1);
                Image scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                tmpImage.getGraphics().drawImage( scaled , 0, 0, null);

                scaled = left.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                tmpImage.getGraphics().drawImage( scaled , (int)BTN_SIZE*2, 0, null);
                bi = bg.getGlyph(t.row2, t.thing2-1);
                scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                tmpImage.getGraphics().drawImage( scaled, (int)BTN_SIZE*2*2, 0, null);
                ImageView iv = new ImageView();
                iv.setImage(SwingFXUtils.toFXImage(tmpImage, null));
                horizontalList.add(iv);
            } else if ( r.getClass().equals(rules.NearRule.class) ) {
                tmpImage = new BufferedImage((int)BTN_SIZE*2*3, (int)BTN_SIZE*2, TYPE_INT_ARGB);
                NearRule t = (NearRule)r;
                BufferedImage bi = bg.getGlyph(t.thing1[0], t.thing1[1]-1);
                Image scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                tmpImage.getGraphics().drawImage( scaled , 0, 0, null);
                scaled = near.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                tmpImage.getGraphics().drawImage( scaled , (int)BTN_SIZE*2, 0, null);
                bi = bg.getGlyph(t.thing2[0], t.thing2[1]-1);
                scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                tmpImage.getGraphics().drawImage( scaled, (int)BTN_SIZE*2*2, 0, null);
                ImageView iv = new ImageView();
                iv.setImage(SwingFXUtils.toFXImage(tmpImage, null));
                horizontalList.add(iv);
            } else if ( r.getClass().equals(rules.UnderRule.class) ) {
                tmpImage = new BufferedImage((int)BTN_SIZE*2, (int)BTN_SIZE*2*2, TYPE_INT_ARGB);
                UnderRule t = (UnderRule)r;
                BufferedImage bi = bg.getGlyph(t.row1, t.thing1-1);
                Image scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                tmpImage.getGraphics().drawImage( scaled , 0, 0, null);

                bi = bg.getGlyph(t.row2, t.thing2-1);
                scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                tmpImage.getGraphics().drawImage( scaled, 0, (int)BTN_SIZE*2, null);
                ImageView iv = new ImageView();
                iv.setImage(SwingFXUtils.toFXImage(tmpImage, null));
                verticalList.add(iv);
            }
        }

    }

}
