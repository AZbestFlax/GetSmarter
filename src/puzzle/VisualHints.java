package puzzle;

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

    private ArrayList<Image> horizontalList = new ArrayList<>();
    private ArrayList<Image> verticalList = new ArrayList<>();

    public VisualHints(ArrayList<Rule> rules) {
        int u = 1;
        for (Rule r: rules) {
            BufferedImage tmpImage = new BufferedImage((int)BTN_SIZE*2*3, (int)BTN_SIZE*2, TYPE_INT_ARGB);
            if ( r.getClass().equals(rules.BetweenRule.class) ) {
                BetweenRule t = (BetweenRule)r;
                int leftImage = (t.row1+1)*10 + t.thing1;
                int centerImage = (t.centerRow+1)*10 + t.centerThing;
                int rightImage = (t.row2+1)*10 + t.thing2;
                try {
                    BufferedImage bi = ImageIO.read(new File("src/image/" + leftImage + ".png"));
                    Image scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                    tmpImage.getGraphics().drawImage( scaled , 0, 0, null);
                    bi = ImageIO.read(new File("src/image/" + centerImage + ".png"));
                    scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                    tmpImage.getGraphics().drawImage( scaled , (int)BTN_SIZE*2, 0, null);
                    bi = ImageIO.read(new File("src/image/" + rightImage + ".png"));
                    scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                    tmpImage.getGraphics().drawImage( scaled, (int)BTN_SIZE*2*2, 0, null);
                    horizontalList.add(tmpImage);
                    //*
                    ImageIO.write(tmpImage, "png", new File(u+".png"));
                    u++;
                    //*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if ( r.getClass().equals(rules.DirectionRule.class) ) {
                DirectionRule t = (DirectionRule)r;
                int leftImage = (t.row1+1)*10 + t.thing1;
                int rightImage = (t.row2+1)*10 + t.thing2;
                try {
                    BufferedImage bi = ImageIO.read(new File("src/image/" + leftImage + ".png"));
                    Image scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                    tmpImage.getGraphics().drawImage( scaled , 0, 0, null);
                    bi = ImageIO.read(new File("src/image/left.png"));
                    scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                    tmpImage.getGraphics().drawImage( scaled , (int)BTN_SIZE*2, 0, null);
                    bi = ImageIO.read(new File("src/image/" + rightImage + ".png"));
                    scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                    tmpImage.getGraphics().drawImage( scaled, (int)BTN_SIZE*2*2, 0, null);
                    horizontalList.add(tmpImage);
                    //* for Debug
                    ImageIO.write(tmpImage, "png", new File(u+".png"));
                    u++;
                    //*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if ( r.getClass().equals(rules.NearRule.class) ) {
                NearRule t = (NearRule)r;
                int leftImage = (t.thing1[0]+1)*10 + t.thing1[1];
                int rightImage = (t.thing2[0]+1)*10 + t.thing2[1];
                try {
                    BufferedImage bi = ImageIO.read(new File("src/image/" + leftImage + ".png"));
                    Image scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                    tmpImage.getGraphics().drawImage( scaled , 0, 0, null);
                    bi = ImageIO.read(new File("src/image/near.png"));
                    scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                    tmpImage.getGraphics().drawImage( scaled , (int)BTN_SIZE*2, 0, null);
                    bi = ImageIO.read(new File("src/image/" + rightImage + ".png"));
                    scaled = bi.getScaledInstance((int)BTN_SIZE*2, (int)BTN_SIZE*2, Image.SCALE_DEFAULT);
                    tmpImage.getGraphics().drawImage( scaled, (int)BTN_SIZE*2*2, 0, null);
                    horizontalList.add(tmpImage);
                    //* for Debug
                    ImageIO.write(tmpImage, "png", new File(u+".png"));
                    u++;
                    //*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if ( r.getClass().equals(rules.UnderRule.class) ) {

            }
        }

    }

}
