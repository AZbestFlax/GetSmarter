package puzzle;

import rules.OpenRule;
import rules.Rule;

import java.util.ArrayList;
import java.util.List;

import static puzzle.Parameters.PUZZLE_SIZE;

public class Puzzle {
    private List<Rule> rules;
    private int[][] puzzle;
    private boolean[] visibleRules;
    public Possibilities possibilities;

    public Puzzle() {
        rules = new ArrayList<Rule>();
        puzzle = new int[PUZZLE_SIZE][PUZZLE_SIZE];
        possibilities = new Possibilities();
        PuzzleGenerator.genPuzzle(puzzle, rules);
        visibleRules = new boolean[rules.size()];
        for (int i = 0; i < rules.size(); i++) {
            if ( rules.get(i).getClass() == OpenRule.class ) {
                visibleRules[i] = false;
                rules.get(i).apply(possibilities);
            } else visibleRules[i] = true;
        }
        PuzzleGenerator.printRules(rules);
        PuzzleGenerator.printPuzzle(puzzle);
    }

    public static void main(String[] args) {
        new Puzzle();
    }

}
