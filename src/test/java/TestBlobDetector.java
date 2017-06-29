import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestBlobDetector {

    private Map<String, BlobDetector.Execution> expectations = new TreeMap<String, BlobDetector.Execution>() {{
        put("1.txt", new BlobDetector.Execution(0, new BlobDetector.BlobBoundaries(0, 0, 0, 0)));

        put("5_no_blob.txt", new BlobDetector.Execution(0, null));
        put("5_left_top.txt", new BlobDetector.Execution(0, new BlobDetector.BlobBoundaries(0, 0, 0, 0)));
        put("5_right_top.txt", new BlobDetector.Execution(0, new BlobDetector.BlobBoundaries(4, 0, 4, 0)));
        put("5_left_bottom.txt", new BlobDetector.Execution(0, new BlobDetector.BlobBoundaries(0, 4, 0, 4)));
        put("5_right_bottom.txt", new BlobDetector.Execution(0, new BlobDetector.BlobBoundaries(4, 4, 4, 4)));

        put("10_blob1.txt", new BlobDetector.Execution(0, new BlobDetector.BlobBoundaries(2, 1, 6, 7)));
        put("10_blob2.txt", new BlobDetector.Execution(0, new BlobDetector.BlobBoundaries(0, 0, 9, 9)));
        put("10_blob3.txt", new BlobDetector.Execution(0, new BlobDetector.BlobBoundaries(2, 2, 7, 7)));

        put("20_blob.txt", new BlobDetector.Execution(0, new BlobDetector.BlobBoundaries(6, 0, 19, 5)));
    }};

    @Test
    public void testBlobs() throws Exception {
        for(Map.Entry<String, BlobDetector.Execution> entry : expectations.entrySet()) {
            System.out.println("File: " + entry.getKey());
            boolean[][] cells = readCellsFromFile(entry.getKey());
            BlobDetector.Execution execution = new BlobDetector(cells.length, cells[0].length).findBlob(cells);
            printExecution(execution);
            assertExecution(entry.getValue(), execution);
            System.out.println();
        }
    }



    private void assertExecution(BlobDetector.Execution o1, BlobDetector.Execution o2) {
        if (o1.blob == null)
            assertNull(o2.blob);
        else
            assertBlobBoundary(o1.blob, o2.blob);
    }

    private void assertBlobBoundary(BlobDetector.BlobBoundaries o1, BlobDetector.BlobBoundaries o2) {
        assertEquals(o1.left, o2.left);
        assertEquals(o1.top, o2.top);
        assertEquals(o1.right, o2.right);
        assertEquals(o1.bottom, o2.bottom);
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

    private static void printExecution(BlobDetector.Execution execution) {
        System.out.println("Cell Reads: " + execution.cellReads);
        if (execution.blob != null) {
            System.out.println("Top: " + execution.blob.top);
            System.out.println("Left: " + execution.blob.left);
            System.out.println("Bottom: " + execution.blob.bottom);
            System.out.println("Right: " + execution.blob.right);
        }
    }
}
