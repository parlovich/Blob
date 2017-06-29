import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class FindBlobBoundaries {

    public static void main(String args[]) {
        if (args.length != 1) {
            System.out.println("Wrong arguments");
            printUsage();
            return;
        }
        String fileName = args[0];

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));

            boolean[][] cells = readCells(scanner);
            BlobDetector.Execution execution = new BlobDetector(cells.length, cells[0].length).findBlob(cells);
            printExecution(execution);

        } catch (IOException e) {
            System.out.println("Error while reading file '" + fileName + "':\n\t" + e.getMessage());
        }
    }

    private static boolean[][] readCells(Scanner scanner) {
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        scanner.nextLine();

        boolean[][] result = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < m; j++) {
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

    private static void printUsage() {
        System.out.print("Use:\n\t" +
                FindBlobBoundaries.class.getSimpleName() + " <file name>");
    }
}
