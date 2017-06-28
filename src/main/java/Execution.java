
public class Execution {
    int cellReads;
    BlobBoundary blobs[];

    public Execution(int cellReads, BlobBoundary[] blobs) {
        this.cellReads = cellReads;
        this.blobs = blobs;
    }

    public int getCellReads() {
        return cellReads;
    }

    public BlobBoundary[] getBlobs() {
        return blobs;
    }
}
