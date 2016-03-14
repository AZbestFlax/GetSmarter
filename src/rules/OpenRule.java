package rules;

import puzzle.IconSet;
import puzzle.Possibilities;
import puzzle.ShowOptions;

import java.util.Random;

import static puzzle.Parameters.puzzleSize;
import static puzzle.ShowOptions.SHOW_NOTHING;

public class OpenRule extends Rule {
    private int col, row, thing;

    public OpenRule(int[][] puzzle) {
        super();
        Random rndGen = new Random();
        col = rndGen.nextInt(puzzleSize);
        row = rndGen.nextInt(puzzleSize);
        thing = puzzle[row][col];
    }

    public String toString() {
        return getThingName(row, thing) + " is at column " + (col+1);
    }

    public boolean apply(Possibilities possibilities) {
        if (! possibilities.isDefined(col, row)) {
            possibilities.set(col, row, thing);
            return true;
        }
        return false;
    }

    public void draw(int x, int y, IconSet iconSet, boolean highlight) {

    }

    public boolean applyOnStart() { return true; }

    public ShowOptions getShowOpts() { return SHOW_NOTHING; }

}
