import java.util.LinkedList;
import java.util.Queue;

public class BlobDetector {

    private class Cell {
        int x, y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Execution find(boolean[][] cells) {
        int n = cells.length;
        int m = cells[0].length;

        int reads = 0;

        int top = 0;
        int left = 0;
        int bottom = 0;
        int right = 0;

        boolean[][] visited = new boolean[n][m];
        Queue<Cell> q = new LinkedList<Cell>();

        // find first 1
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                reads++;
                visited[i][j] = true;
                if (cells[i][j]) {
                    q.add(new Cell(i, j));
                    top = bottom = i;
                    left = right = j;
                    break;
                }
            }
            if (!q.isEmpty()) break;
        }
        if (q.isEmpty()) {
            return new Execution(reads, new BlobBoundary[0]);
        }

        while (!q.isEmpty()) {
            Cell cell = q.remove();

            // Top
            if (cell.x != 0 && !visited[cell.x - 1][cell.y]) {
                reads++;
                visited[cell.x - 1][cell.y] = true;
                if (cells[cell.x - 1][cell.y]) {
                    q.add(new Cell(cell.x - 1, cell.y));
                    top = Math.min(top, cell.x - 1);
                }
            }
            // Left
            if (cell.y != 0 && !visited[cell.x][cell.y - 1]) {
                reads++;
                visited[cell.x][cell.y - 1] = true;
                if (cells[cell.x][cell.y - 1]) {
                    q.add(new Cell(cell.x, cell.y - 1));
                    left = Math.min(left, cell.y - 1);
                }
            }
            // Bottom
            if (cell.x < n - 1 && !visited[cell.x + 1][cell.y]) {
                reads++;
                visited[cell.x + 1][cell.y] = true;
                if (cells[cell.x + 1][cell.y]) {
                    q.add(new Cell(cell.x + 1, cell.y));
                    bottom = Math.max(bottom, cell.x + 1);
                }
            }
            // Right
            if (cell.y < m - 1 && !visited[cell.x][cell.y + 1]) {
                reads++;
                visited[cell.x][cell.y + 1] = true;
                if (cells[cell.x][cell.y + 1]) {
                    q.add(new Cell(cell.x, cell.y + 1));
                    right = Math.max(right, cell.y + 1);
                }
            }
        }

        return new Execution(reads, new BlobBoundary[]{ new BlobBoundary(left, top, right, bottom)});
    }

}
