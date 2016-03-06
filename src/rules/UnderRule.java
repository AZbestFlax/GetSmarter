package rules;

import puzzle.IconSet;
import puzzle.Possibilities;
import puzzle.ShowOptions;

import java.util.Random;

import static puzzle.Parameters.PUZZLE_SIZE;
import static puzzle.ShowOptions.SHOW_VERT;

public class UnderRule extends Rule {

    public int row1, thing1, row2, thing2;

    public UnderRule(int[][] puzzle) {
        super();
        Random rndGen = new Random();
        int col = rndGen.nextInt(PUZZLE_SIZE);
        row1 = rndGen.nextInt(PUZZLE_SIZE);
        thing1 = puzzle[row1][col];
        do {
            row2 = rndGen.nextInt(PUZZLE_SIZE);
        } while (row2 == row1) ;
        thing2 = puzzle[row2][col];
    }

    public String toString() {
        return getThingName(row1, thing1) + " is the same column as " +
                getThingName(row2, thing2);
    }

    public boolean apply(Possibilities possibilities) {
        boolean changed = false;
        for (int i = 0; i < PUZZLE_SIZE; i++) {
            if ((! possibilities.isPossible(i, row1, thing1)) &&
                    possibilities.isPossible(i, row2, thing2))
            {
                possibilities.exclude(i, row2, thing2);
                changed = true;
            }
            if ((! possibilities.isPossible(i, row2, thing2)) &&
                    possibilities.isPossible(i, row1, thing1))
            {
                possibilities.exclude(i, row1, thing1);
                changed = true;
            }
        }
        return changed;
    }

    public void draw(int x, int y, IconSet iconSet, boolean highlight) {

    }

    public ShowOptions getShowOpts() { return SHOW_VERT; };

}
