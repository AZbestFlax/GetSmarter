package puzzle;

import rules.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PuzzleGenerator implements Parameters{

    public static void genPuzzle(int[][] puzzle, List<Rule> rules) {
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++)
                puzzle[i][j] = j + 1;
            shuffle(puzzle[i]);
        }

        genRules(puzzle, rules);
        removeRules(puzzle, rules);
        //printPuzzle(puzzle);
        //printRules(rules);
    }
    public static void openInitial(Possibilities possibilities, List<Rule> rules) {
        for (Rule r: rules) {
            if (r.applyOnStart())
                r.apply(possibilities);
        }
    }

    public static void getHintsQty(List<Rule> rules, int vert, int horiz) {
        vert = 0;
        horiz = 0;

        for (Rule r: rules) {
            ShowOptions so = r.getShowOpts();
            switch (so) {
                case SHOW_VERT: vert++; break;
                case SHOW_HORIZ: horiz++; break;
                default:
            }
        }
    }

    private static Rule getRule(List<Rule> rules, int no)
    {
        return rules.get(no);
    }

    private static void genRules(int[][] puzzle, List<Rule> rules)
    {
        boolean rulesDone = false;
        boolean inactiveRule;
        do {
            inactiveRule = false;
            Rule rule = Rule.genRule(puzzle);
            if (rule != null) {
                String s = rule.toString();
                for (Rule r: rules)
                    if (r.toString().equals(s)) {
                        inactiveRule = true;
                        break;
                    }
                if ( !inactiveRule ) {
                    //System.out.println("adding rule " + rule);
                    rules.add(rule);
                    rulesDone = canSolve(puzzle, rules);
                }
            }
        } while (! rulesDone);
    }

    private static void shuffle(int arr[])
    {
        Random rand = new Random();
        int a, c;
        for (int i = 0; i < puzzleSize; i++) {
            a = rand.nextInt(puzzleSize);
            c = arr[a];
            arr[a] = arr[i];
            arr[i] = c;
        }
    }

    private static boolean canSolve(int[][] puzzle, List<Rule> rules)
    {
        Possibilities pos = new Possibilities();
        boolean changed;
        do {
            changed = false;
            for (Rule r: rules) {
                if ( r.apply(pos) ) {
                    changed = true;
                    if (! pos.isValid(puzzle)) {
                        System.out.println("after error:");
                        System.out.println(pos);
                        throw new RuntimeException("Invalid possibilities after rule " + r);
                    }
                }
            }
        } while (changed);
        return pos.isSolved();
    }

    private static void removeRules(int[][] puzzle, List<Rule> rules)
    {
        boolean possible;
        do {
            possible = false;
            for (Rule r: rules) {
                List<Rule> excludedRules = new ArrayList<Rule>(rules);
                excludedRules.remove(r);
                if (canSolve(puzzle, excludedRules)) {
                    possible = true;
                    rules.remove(r);
                    break;
                }
            }
        } while (possible);
    }

    /*
            void savePuzzle(SolvedPuzzle &puzzle, std::ostream &stream)
            {
                for (int row = 0; row < puzzleSize; row++)
                for (int col = 0; col < puzzleSize; col++)
                    writeInt(stream, puzzle[row][col]);
            }

            void loadPuzzle(SolvedPuzzle &puzzle, std::istream &stream)
            {
                for (int row = 0; row < puzzleSize; row++)
                    for (int col = 0; col < puzzleSize; col++)
                        puzzle[row][col] = readInt(stream);
            }
    */
    public static void printPuzzle(int[][] puzzle)
    {
        for (int i = 0; i < puzzleSize; i++) {
            char prefix = (char)((int)'A' + i);
            for (int j = 0; j < puzzleSize; j++) {
                if (j != 0)
                    System.out.print("  ");
                System.out.print("" + prefix + puzzle[i][j]);
            }
            System.out.println();
    }
}


    public static void printRules(List<Rule> rules)
    {
        for (Rule r: rules)
            System.out.println(r);
    }

}


/*int main(int argc, char *argv[])
{
    srand(time(NULL));

    Rules rules;
    Puzzle puzzle;

    genPuzzle(puzzle, rules);
    printPuzzle(puzzle);
    printRules(rules);

    return 0;
}*/