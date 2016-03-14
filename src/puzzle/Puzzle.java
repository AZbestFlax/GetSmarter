package puzzle;

import rules.OpenRule;
import rules.Rule;

import java.io.Serializable;
import java.util.ArrayList;

import static puzzle.Parameters.puzzleSize;

public class Puzzle implements Serializable {
    private ArrayList<Rule> rules;
    private int[][] puzzle;
    private boolean[] visibleRules;
    public Possibilities possibilities;

    public Puzzle() {
        rules = new ArrayList<Rule>();
        puzzle = new int[puzzleSize][puzzleSize];
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

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public static void main(String[] args) {
        new Puzzle();
    }

    public int getRightValue(int row, int col) {
        return puzzle[row][col];
    }

}
