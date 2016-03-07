package forms;

import static puzzle.Parameters.GRID_SIZE;

public class Coordinator {

    static double GENERAL_TOP_INDENT = 0.0D;
    static double GENERAL_LEFT_INDENT = 0.0D;
    @Deprecated static int inLastRow;
    static int inRow;
    static int rowCount;
    static double TOP_INDENT;
    static double LEFT_INDENT;
    int count;
    static double gridCellSize;
    static double buttonSize;

    public Coordinator(int count) {
        this.count = count;
        gridCellSize = GRID_SIZE / count;
        switch (count) {
            case 3: {
                inRow = 2;
                rowCount = 2;
                inLastRow = 1;
                TOP_INDENT = 0.0;
                buttonSize = gridCellSize / inRow;
                LEFT_INDENT = buttonSize / 2;
            } break;
            case 4: {
                inRow = 2;
                rowCount = 2;
                inLastRow = 2;
                TOP_INDENT = 0.0;
                buttonSize = gridCellSize / inRow;
                LEFT_INDENT = 0.0;
            } break;
            case 5: {
                inRow = 3;
                rowCount = 2;
                inLastRow = 2;
                buttonSize = gridCellSize / inRow;
                TOP_INDENT = buttonSize / 2;
                LEFT_INDENT = buttonSize / 2;
            } break;
            case 6: {
                inRow = 3;
                rowCount = 2;
                inLastRow = 3;
                buttonSize = gridCellSize / inRow;
                TOP_INDENT = buttonSize / 2;
                LEFT_INDENT = 0.0;
            } break;
            case 7: {
                inRow = 3;
                rowCount = 3;
                inLastRow = 1;
                buttonSize = gridCellSize / inRow;
                TOP_INDENT = 0.0;
                LEFT_INDENT = buttonSize;
            } break;
            case 8: {
                inRow = 3;
                rowCount = 3;
                inLastRow = 2;
                buttonSize = gridCellSize / inRow;
                TOP_INDENT = 0.0;
                LEFT_INDENT = buttonSize / 2;
            } break;
            case 9: {
                inRow = 3;
                rowCount = 3;
                inLastRow = 3;
                buttonSize = gridCellSize / inRow;
                TOP_INDENT = 0.0;
                LEFT_INDENT = 0.0;
            } break;
            default: throw new RuntimeException("In elements count 3-9");
        }
    }

    public Coordinate getCoordinate(double x, double y) {
        x -= (int)GENERAL_LEFT_INDENT;
        y -= (int)GENERAL_TOP_INDENT;

        int r = (int)Math.floor( y / gridCellSize ) ;
        int c = (int)Math.floor( x / gridCellSize ) ;

        x -= c * gridCellSize;
        y -= r * gridCellSize;

        y -= TOP_INDENT;
        if ( y < 0 ) return null;
        int br = (int)Math.floor(y / buttonSize);
        int b = -1;
        for (int i = 0; i < br; i++) b += inRow;

        if (br == rowCount -1) x -= LEFT_INDENT;
        if ( x < 0 ) return null;
        int bc = (int)Math.floor(x / buttonSize);
        b += bc+1;

        if ( b >= count ) return null;

        return new Coordinate(r, c, b);
    }

    public Position getPosition(int row, int col, int but) {
        double x = GENERAL_LEFT_INDENT, y = GENERAL_TOP_INDENT;
        x += col * gridCellSize;
        y += row * gridCellSize;

        y += TOP_INDENT;
        int r = 0;
        while ( but >= inRow ) {
            y += buttonSize;
            but -= inRow;
            r++;
        }
        x += but * buttonSize;
        if ( r == rowCount - 1 ) x += LEFT_INDENT;

        return new Position(x, y);
    }

    public static double getGridCellSize() {
        return gridCellSize;
    }

    public static double getButtonSize() {
        return buttonSize;
    }

    /* Remove after debug
    public static void main(String[] args) {
        Coordinator coord = new Coordinator(4);
        System.out.println(coord.getPosition(0, 0, 0));
        System.out.println(coord.getPosition(0, 0, 1));
        System.out.println(coord.getPosition(0, 0, 2));
        System.out.println(coord.getPosition(0, 0, 3));
        System.out.println(coord.getPosition(0, 1, 0));
        System.out.println(coord.getPosition(0, 1, 1));
        System.out.println(coord.getPosition(0, 1, 2));
        System.out.println(coord.getPosition(0, 2, 0));
        System.out.println(coord.getPosition(0, 2, 1));
        System.out.println(coord.getPosition(0, 2, 2));
        System.out.println(coord.getPosition(1, 0, 0));
        System.out.println(coord.getPosition(1, 0, 1));
        System.out.println(coord.getPosition(1, 0, 2));
    }
    //*/
}
