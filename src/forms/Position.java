package forms;

public class Position {

    public double x, y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position() {
        this(0.0, 0.0);
    }

    @Override
    public String toString() {
        return "(" + x + ";" + y + ')';
    }
}
