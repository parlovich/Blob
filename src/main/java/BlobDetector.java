import java.util.LinkedList;
import java.util.Queue;

public class BlobDetector {

    // find blob execution results
    public static class Execution {
        public int cellReads;
        public BlobBoundaries blob; // can be null is no blob has been found

        public Execution(int cellReads, BlobBoundaries blob) {
            this.cellReads = cellReads;
            this.blob = blob;
        }
    }

    // Boundaries of the blob
    public static class BlobBoundaries {
        public int left;
        public int top;
        public int right;
        public int bottom;

        public BlobBoundaries(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }
    }

    // Cell coordinates
    // NOTE: this class is optional. We can avoid having it if we treat the cells area as one-dimensional array
    private class Cell {
        int x;
        int y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // Cells area dimensions
    private int n, m;

    // Visited cells.
    // If visited[i][j] == true then we have already visited the cell (meaning we have read its value)
    // NOTE: alternatively we could use one-dimensional array here
    private boolean[][] visited;

    // Number of cells reads. Should be equal to the number of visited cells
    private int reads = 0;

    public BlobDetector(int n, int m) {
        this.n = n;
        this.m = m;
        visited = new boolean[n][m];
    }

    public Execution findBlob(boolean[][] cells) {
        // boundaries of the blob
        int top = 0;
        int left = 0;
        int bottom = 0;
        int right = 0;

        // Queue of the blob cells that still needs to be visited.
        // Empty queue means we have visited all the blob's cells
        Queue<Cell> q = new LinkedList<Cell>();

        // find the first cell of the blob
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (visit(cells, i, j)) {
                    q.add(new Cell(i, j));
                    top = bottom = i;
                    left = right = j;
                    break;
                }
            }
            if (!q.isEmpty()) break;
        }

        // No blob found
        if (q.isEmpty()) {
            return new Execution(reads, null);
        }

        // Loop till there are blob's cells we still need to visit
        while (!q.isEmpty()) {
            Cell cell = q.remove();

            // Top
            if (cell.x != 0 && visit(cells, cell.x - 1, cell.y)) {
                q.add(new Cell(cell.x - 1, cell.y));
                top = Math.min(top, cell.x - 1);
            }
            // Left
            if (cell.y != 0 && visit(cells, cell.x, cell.y - 1)) {
                q.add(new Cell(cell.x, cell.y - 1));
                left = Math.min(left, cell.y - 1);
            }
            // Bottom
            if (cell.x < n - 1 && visit(cells, cell.x + 1, cell.y)) {
                q.add(new Cell(cell.x + 1, cell.y));
                bottom = Math.max(bottom, cell.x + 1);
            }
            // Right
            if (cell.y < m - 1 && visit(cells, cell.x, cell.y + 1)) {
                q.add(new Cell(cell.x, cell.y + 1));
                right = Math.max(right, cell.y + 1);
            }
        }

        return new Execution(reads, new BlobBoundaries(left, top, right, bottom));
    }

    /**
     * Visit one cell
     * @param cells
     * @param x
     * @param y
     * @return True only if the cell is the blob cell and it hasn't been visited before
     */
    private boolean visit(boolean[][] cells, int x, int y) {
        if (visited[x][y])
            return false;
        else {
            visited[x][y] = true;
            reads++;
            return cells[x][y];
        }
    }
}
