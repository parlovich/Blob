import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class TestOneBlob {

    private Map<String, Execution> expectations = new HashMap<String, Execution>() {{
        put("5_no_blob.txt", new Execution(44, new BlobBoundary[0]));

        put("10_blob.txt", new Execution(57, new BlobBoundary[]{new BlobBoundary(2, 1, 6, 7)}));


        put("5_left_top.txt", new Execution(44, new BlobBoundary[]{new BlobBoundary(0, 0, 0, 0)}));
        put("5_right_top.txt", new Execution(44, new BlobBoundary[]{new BlobBoundary(4, 0, 4, 0)}));
        put("5_left_bottom.txt", new Execution(44, new BlobBoundary[]{new BlobBoundary(0, 4, 0, 4)}));
        put("5_right_bottom.txt", new Execution(44, new BlobBoundary[]{new BlobBoundary(4, 4, 4, 4)}));
    }};

    @Test
    public void test() throws Exception {
        for(Map.Entry<String, Execution> entry : expectations.entrySet()) {
            System.out.println(entry.getKey());
            boolean[][] cells = readCellsFromFile(entry.getKey());
            Execution execution = new OneBlob().find(cells);
            printExecution(execution);
            assertExecution(entry.getValue(), execution);
            System.out.println();
        }
    }

    private void assertExecution(Execution o1, Execution o2) {
        assertEquals(o1.getBlobs().length, o2.getBlobs().length);
        for (int i = 0; i < o1.getBlobs().length; i++)
            assertBlob(o1.getBlobs()[i], o2.getBlobs()[i]);
    }

    private void assertBlob(BlobBoundary o1, BlobBoundary o2) {
        assertEquals(o1.getLeft(), o2.getLeft());
        assertEquals(o1.getTop(), o2.getTop());
        assertEquals(o1.getRight(), o2.getRight());
        assertEquals(o1.getBottom(), o2.getBottom());
    }

    private boolean[][] readCellsFromFile(String fileName) throws FileNotFoundException {
        File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
        Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));
        return readCells(scanner);
    }

    private boolean[][] readCells(Scanner scanner) {
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        scanner.nextLine();

        boolean[][] result = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            for(int j = 0; j < m; j++) {
                result[i][j] = line.charAt(j) == '1';
            }
        }
        return result;
    }

    private void printExecution(Execution execution) {
        System.out.println("Cell Reads: " + execution.getCellReads());
        for (BlobBoundary boundary : execution.getBlobs()) {
            System.out.println("Top: " + boundary.getTop());
            System.out.println("Left: " + boundary.getLeft());
            System.out.println("Bottom: " + boundary.getBottom());
            System.out.println("Right: " + boundary.getRight());
        }
    }
}
