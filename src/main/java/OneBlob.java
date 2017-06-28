import java.util.LinkedList;
import java.util.Queue;

public class OneBlob {

    private class Cell {
        int x, y;
        int comeFrom = 0;  // 0 - left; 1 - top; 2 - right; 3 - bottom

        public Cell(int x, int y, int comeFrom) {
            this.x = x;
            this.y = y;
            this.comeFrom = comeFrom;
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
                    q.add(new Cell(i, j, 0));
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

            top = Math.min(top, cell.x);
            left = Math.min(left, cell.y);
            bottom = Math.max(bottom, cell.x);
            right = Math.max(right, cell.y);

            // Top
            if (cell.comeFrom != 1 && cell.x != 0 && !visited[cell.x - 1][cell.y]) {
                reads++;
                visited[cell.x - 1][cell.y] = true;
                if (cells[cell.x - 1][cell.y]) {
                    q.add(new Cell(cell.x - 1, cell.y, 3));
                }
            }
            // Left
            if (cell.comeFrom != 0 && cell.y != 0 && !visited[cell.x][cell.y - 1]) {
                reads++;
                visited[cell.x][cell.y - 1] = true;
                if (cells[cell.x][cell.y - 1]) {
                    q.add(new Cell(cell.x, cell.y - 1, 2));
                }
            }
            // Bottom
            if (cell.comeFrom != 3 && cell.x < n - 1 && !visited[cell.x + 1][cell.y]) {
                reads++;
                visited[cell.x + 1][cell.y] = true;
                if (cells[cell.x + 1][cell.y]) {
                    q.add(new Cell(cell.x + 1, cell.y, 1));
                }
            }
            // Right
            if (cell.comeFrom != 2 && cell.y < m - 1 && !visited[cell.x][cell.y + 1]) {
                reads++;
                visited[cell.x][cell.y + 1] = true;
                if (cells[cell.x][cell.y + 1]) {
                    q.add(new Cell(cell.x, cell.y + 1, 0));
                }
            }
        }

        return new Execution(reads, new BlobBoundary[]{ new BlobBoundary(left, top, right, bottom)});
    }

}
