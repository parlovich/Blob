
public class BlobDetector1 {




    private class Cell {
        int x, y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private enum Direction {
        RIGHT,
        UP,
        LEFT,
        DOWN
    }


    public Execution find(boolean[][] cells) {
        int n = cells.length;
        int m = cells[0].length;

        int reads = 0;

        int top = 0;
        int left = 0;
        int bottom = 0;
        int right = 0;

        Cell curCell = null;
        Direction direction = Direction.RIGHT;

        boolean[][] visited = new boolean[n][m];

        // find first 1
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                reads++;
                visited[i][j] = true;
                if (cells[i][j]) {
                    top = bottom = i;
                    left = right = j;
                    curCell = new Cell(i, j);
                    break;
                }
            }
        }
        if (curCell == null)
            return new Execution(reads, new BlobBoundary[0]);

        while (true) {
            Direction d = clockWiseNextDirection(direction);
            Cell c = nextCell(curCell, d, n, m);
            if (c != null) {
                reads++;
                if (cells[c.x][c.y]) {

                }
            }

        }

        return new Execution(reads, new BlobBoundary[]{ new BlobBoundary(left, top, right, bottom)});
    }

    private Cell nextCell(Cell curCell, Direction direction, int n, int m) {
        switch(direction) {
            case RIGHT:
                if (curCell.y < m - 1) return new Cell(curCell.x, curCell.y + 1);
                break;
            case LEFT:
                if (curCell.y > 0) return new Cell(curCell.x, curCell.y - 1);
                break;
            case UP:
                if (curCell.x > 0) return new Cell(curCell.x - 1, curCell.y);
                break;
            case DOWN:
                if (curCell.x < n - 1) return new Cell(curCell.x + 1, curCell.y);
                break;
        }
        return null;
    }

    private Direction clockWiseNextDirection(Direction direction) {
        switch(direction) {
            case RIGHT: return Direction.UP;
            case LEFT: return Direction.DOWN;
            case UP: return Direction.LEFT;
            case DOWN: return Direction.RIGHT;
        }
        return direction;
    }

}
