package rules;

import puzzle.IconSet;
import puzzle.Possibilities;
import puzzle.ShowOptions;

import java.util.Random;

import static puzzle.Parameters.puzzleSize;
import static puzzle.ShowOptions.SHOW_HORIZ;

public class BetweenRule extends Rule {
    public int row1, thing1;
    public int row2, thing2;
    public int centerRow, centerThing;

    public BetweenRule(int[][] puzzle) {
        super();
        Random rndGen = new Random();
        centerRow = rndGen.nextInt(puzzleSize);
        row1 = rndGen.nextInt(puzzleSize);
        row2 = rndGen.nextInt(puzzleSize);

        int centerCol = rndGen.nextInt(puzzleSize - 2) + 1;
        centerThing = puzzle[centerRow][centerCol];
        if (rndGen.nextInt(2) != 0) {
            thing1 = puzzle[row1][centerCol - 1];
            thing2 = puzzle[row2][centerCol + 1];
        } else {
            thing1 = puzzle[row1][centerCol + 1];
            thing2 = puzzle[row2][centerCol - 1];
        }
    }

    public String toString() {
        return getThingName(centerRow, centerThing) +
                " is between " + getThingName(row1, thing1) + " and " +
                getThingName(row2, thing2);
    }

    public boolean apply(Possibilities possibilities) {
        boolean changed = false;
        if (possibilities.isPossible(0, centerRow, centerThing)) {
            changed = true;
            possibilities.exclude(0, centerRow, centerThing);
        }
        if (possibilities.isPossible(puzzleSize -1, centerRow, centerThing)) {
            changed = true;
            possibilities.exclude(puzzleSize -1, centerRow, centerThing);
        }
        boolean goodLoop;
        do {
            goodLoop = false;
            for (int i = 1; i < puzzleSize -1; i++) {
                if (possibilities.isPossible(i, centerRow, centerThing)) {
                    if (! ((possibilities.isPossible(i-1, row1, thing1) &&
                            possibilities.isPossible(i+1, row2, thing2)) ||
                            (possibilities.isPossible(i-1, row2, thing2) &&
                                    possibilities.isPossible(i+1, row1, thing1))))
                    {
                        possibilities.exclude(i, centerRow, centerThing);
                        goodLoop = true;
                    }
                }
            }
            for (int i = 0; i < puzzleSize; i++) {
                boolean leftPossible, rightPossible;
                if (possibilities.isPossible(i, row2, thing2)) {
                    if (i < 2)
                        leftPossible = false;
                    else
                        leftPossible = (possibilities.isPossible(i-1, centerRow, centerThing)
                                && possibilities.isPossible(i-2, row1, thing1));
                    if (i >= puzzleSize - 2)
                        rightPossible = false;
                    else
                        rightPossible = (possibilities.isPossible(i+1, centerRow, centerThing)
                                && possibilities.isPossible(i+2, row1, thing1));
                    if ((! leftPossible) && (! rightPossible)) {
                        possibilities.exclude(i, row2, thing2);
                        goodLoop = true;
                    }
                }
                if (possibilities.isPossible(i, row1, thing1)) {
                    if (i < 2)
                        leftPossible = false;
                    else
                        leftPossible = (possibilities.isPossible(i-1, centerRow, centerThing)
                                && possibilities.isPossible(i-2, row2, thing2));
                    if (i >= puzzleSize - 2)
                        rightPossible = false;
                    else
                        rightPossible = (possibilities.isPossible(i+1, centerRow, centerThing)
                                && possibilities.isPossible(i+2, row2, thing2));
                    if ((! leftPossible) && (! rightPossible)) {
                        possibilities.exclude(i, row1, thing1);
                        goodLoop = true;
                    }
                }
            }
            if (goodLoop)
                changed = true;
        } while (goodLoop);
        return changed;
    }

    public void draw(int x, int y, IconSet iconSet, boolean highlight) {

    }

    public ShowOptions getShowOpts() { return SHOW_HORIZ; };
}
