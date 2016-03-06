package rules;

import puzzle.IconSet;
import puzzle.Possibilities;
import puzzle.ShowOptions;

import java.util.Random;

import static puzzle.Parameters.PUZZLE_SIZE;
import static puzzle.ShowOptions.SHOW_HORIZ;

public class DirectionRule extends Rule {
    public int row1, thing1, row2, thing2;

    public DirectionRule(int[][] puzzle) {
        super();
        Random rndGen = new Random();
        row1 = rndGen.nextInt(PUZZLE_SIZE);
        row2 = rndGen.nextInt(PUZZLE_SIZE);
        int col1 = rndGen.nextInt(PUZZLE_SIZE - 1);
        int col2 = rndGen.nextInt(PUZZLE_SIZE - col1 - 1) + col1 + 1;
        thing1 = puzzle[row1][col1];
        thing2 = puzzle[row2][col2];
    }

    public String toString() {
        return getThingName(row1, thing1) +
                " is from the left of " + getThingName(row2, thing2);
    }

    public boolean apply(Possibilities possibilities) {
        boolean changed = false;
        for (int i = 0; i < PUZZLE_SIZE; i++) {
            if (possibilities.isPossible(i, row2, thing2)) {
                possibilities.exclude(i, row2, thing2);
                changed = true;
            }
            if (possibilities.isPossible(i, row1, thing1))
                break;
        }
        for (int i = PUZZLE_SIZE-1; i >= 0; i--) {
            if (possibilities.isPossible(i, row1, thing1)) {
                possibilities.exclude(i, row1, thing1);
                changed = true;
            }
            if (possibilities.isPossible(i, row2, thing2))
                break;
        }
        return changed;
    }

    public void draw(int x, int y, IconSet iconSet, boolean highlight) {

    }

    public ShowOptions getShowOpts() { return SHOW_HORIZ; }

}
