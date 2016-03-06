import puzzle.Puzzle;
import puzzle.PuzzleGenerator;
import rules.Rule;

import java.util.ArrayList;
import java.util.List;

import static puzzle.Parameters.PUZZLE_SIZE;

public class Program {

    public static void main(String args[]) {
        /*List<Rule> rules = new ArrayList<Rule>();
        int[][] puzzle = new int[PUZZLE_SIZE][PUZZLE_SIZE];
        PuzzleGenerator.genPuzzle(puzzle, rules);
        PuzzleGenerator.printPuzzle(puzzle);
        PuzzleGenerator.printRules(rules);*/

        System.out.println( Math.ceil(Math.sqrt(5)) );

    }

}
