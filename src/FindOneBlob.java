import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class FindOneBlob {
    public static void main(String args[]) {
        try {
            File file = new File(args[0]);
            Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));

            boolean[][] cells = readCells(scanner);
            Execution execution = new OneBlob().find(cells);
            printExecution(execution);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean[][] readCells(Scanner scanner) {
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

    private static void printExecution(Execution execution) {
        System.out.println("Cell Reads: " + execution.getCellReads());
        for (BlobBoundary boundary : execution.getBlobs()) {
            System.out.println("Top: " + boundary.getTop());
            System.out.println("Left: " + boundary.getLeft());
            System.out.println("Bottom: " + boundary.getBottom());
            System.out.println("Right: " + boundary.getRight());
        }
    }
}
