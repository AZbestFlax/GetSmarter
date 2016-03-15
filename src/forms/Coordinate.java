package forms;

public class Coordinate {
    public int row, col, but;

    public Coordinate(int row, int col, int but) {
        this.row = row;
        this.col = col;
        this.but = but;
    }

    public Coordinate() {
        this(0, 0, 0);
    }
}
