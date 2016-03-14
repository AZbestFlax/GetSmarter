package puzzle;

public class Possibilities implements Parameters {
    private int pos[][][];

    public Possibilities() {
        pos = new int[puzzleSize][puzzleSize][puzzleSize];
        for (int i = 0; i < puzzleSize; i++)
            for (int j = 0; j < puzzleSize; j++)
                for (int k = 0; k < puzzleSize; k++)
                    pos[i][j][k] = k + 1;
    }

    public void exclude(int col, int row, int element) {
        if (pos[col][row][element - 1] == 0)
            return;
        pos[col][row][element - 1] = 0;
        checkSingles(row);
    }

    public void set(int col, int row, int element) {
        for (int i = 0; i < puzzleSize; i++)
            if ((i != element - 1))
                pos[col][row][i] = 0;
            else
                pos[col][row][i] = element;

        for (int j = 0; j < puzzleSize; j++)
            if (j != col)
                pos[j][row][element - 1] = 0;

        checkSingles(row);
    }

    public boolean isPossible(int col, int row, int element) {
        return pos[col][row][element - 1] == element;
    }

    public boolean isDefined(int col, int row) {
        int solvedCnt = 0, unsolvedCnt = 0;
        for (int i = 0; i < puzzleSize; i++)
            if (pos[col][row][i] == 0)
                unsolvedCnt++;
            else
                solvedCnt++;
        return ((unsolvedCnt == puzzleSize -1) && (solvedCnt == 1));
    }

    public int getDefined(int col, int row) {
        for (int i = 0; i < puzzleSize; i++)
            if (pos[col][row][i] != 0)
                return i + 1;
        return 0;
    }

    public int getPosition(int row, int element) {
        int cnt = 0;
        int lastPos = -1;

        for (int i = 0; i < puzzleSize; i++)
            if (pos[i][row][element - 1] == element) {
                cnt++;
                lastPos = i;
            }

        return cnt == 1 ? lastPos : -1;
    }

     public boolean isSolved() {
        for (int i = 0; i < puzzleSize; i++)
            for (int j = 0; j < puzzleSize; j++)
                if (! isDefined(i, j))
                    return false;
        return true;
    }

    @Override
    public String toString() {
        String s = "";
        for (int row = 0; row < puzzleSize; row++) {
            s += (char)('A' + row) + " ";
            for (int col = 0; col < puzzleSize; col++) {
                for (int i = 0; i < puzzleSize; i++)
                    if (pos[col][row][i] != 0)
                        s += pos[col][row][i];
                    else
                        s += " ";
                s += "   ";
            }
            s += "\n";
        }
        return s;
    }

    public boolean isAccessible(int row, int col, int k) {
        return pos[row][col][k] != 0;
    }

    public boolean isValid(int[][] puzzle) {
        for (int row = 0; row < puzzleSize; row++)
            for (int col = 0; col < puzzleSize; col++)
                if (! isPossible(col, row, puzzle[row][col]))
                    return false;
        return true;
    }

    public void makePossible(int col, int row, int element) {
        pos[col][row][element-1] = element;
    }


    private  void checkSingles(int row) {
        int cellsCnt[] = new int[puzzleSize];   // count of elements in cells
        int elsCnt[] = new int[puzzleSize];     // total count of elements in row
        int elements[] = new int[puzzleSize];   // one element of each cell
        int elCells[] = new int[puzzleSize];    // one cell of each element

        for (int i = 0; i < puzzleSize; i++) {
            cellsCnt[i] = 0;
            elsCnt[i] = 0;
            elements[i] = 0;
            elCells[i] = 0;
        }

        // check if there is only one element left in cell(col, row)
        for (int col = 0; col < puzzleSize; col++)
            for (int i = 0; i < puzzleSize; i++) {
                if (pos[col][row][i] != 0) {
                    elsCnt[i]++;
                    elCells[i] = col;
                    cellsCnt[col]++;
                    elements[col] = i + 1;
                }
            }

        boolean changed = false;

        // check for cells with single element
        for (int col = 0; col < puzzleSize; col++) {
            if ((cellsCnt[col] == 1) && (elsCnt[elements[col] - 1] != 1)) {
                // there is only one element in cell but it used somewhere else
                int e = elements[col] - 1;
                for (int i = 0; i < puzzleSize; i++)
                    if (i != col)
                        pos[i][row][e] = 0;
                changed = true;
            }
        }

        // check for single element without exclusive cell
        for (int el = 0; el < puzzleSize; el++)
            if ((elsCnt[el] == 1) && (cellsCnt[elCells[el]] != 1)) {
                int col = elCells[el];
                for (int i = 0; i < puzzleSize; i++)
                    if (i != el)
                        pos[col][row][i] = 0;
                changed = true;
            }

        if (changed)
            checkSingles(row);
    }

}
