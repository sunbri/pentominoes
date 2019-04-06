public class Piece {

    // Stores the true false matrix of the piece
    private boolean[][] matrix;

    // Stores the position on the top left corner
    int rowPosition;
    int columnPosition;

    // Information about the transformations of the piece
    private int numRotations = 0;
    private boolean flipped = false;
    private boolean canBeFlipped;

    // Identifier for the piece regardless of transformation features
    private String name;

    // canBeFlipped means that flipping the piece over an axis allows it to have new orientations
    public Piece (boolean[][] matrix, String name, boolean canBeFlipped) {
        this.matrix = matrix;
        this.name = name;
        this.canBeFlipped = canBeFlipped;
        if (!canBeFlipped) flipped = true;
    }

    // Get matrix
    boolean[][] getMatrix() {
        return matrix;
    }

    @Override
    // Clone for deep copy of arrays
    public Piece clone() {

        Piece piece = new Piece(this.matrix, this.name, this.canBeFlipped);
        piece.rowPosition = this.rowPosition;
        piece.columnPosition = this.columnPosition;

        return piece;
    }

    // Displays the matrix
    void displayMatrix() {
        for (boolean[] row : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (row[j]) {
                    System.out.print("T ");
                } else {
                    System.out.print("· ");
                }
            }
            System.out.println();
        }
    }

    // Gives the next transformation of the piece, false if needs to move onto the next piece
    boolean nextTransformation() {

        // 3 comes from the fact that 0,1,2,3 different 90 degree angles a rotation can take
        if (numRotations == 3) {
            if (!flipped) {
                this.flip();
                // Flipped, reset rotations
                this.numRotations = 0;
            } else {
                // Make it not flipped if it is able to be flipped
                if (this.canBeFlipped) {
                    this.flipped = false;
                }
                // "Reset" the rotations
                this.numRotations = 0;
                return false;
            }
        } else {
            this.rotate();
        }
        return true;
    }

    // Rotate the piece clockwise by 90 degrees
    private void rotate() {
        boolean[][] newMatrix = new boolean[matrix[0].length][matrix.length];

        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[0].length; j++) {
                newMatrix[newMatrix.length - 1 - i][j] = matrix[j][i];
            }
        }

        matrix = newMatrix;
        numRotations++;
    }

    // Flip the piece over the X axis
    private void flip() {

        boolean[][] newMatrix = new boolean[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            newMatrix[i] = matrix[matrix.length - 1 - i];
        }

        matrix = newMatrix;
        flipped = true;
    }

    // Figure out how much of the top left corner is false
    int getShift() {

        // If there is empty space in the top right corner, which is where the piece will be placed, shift it over
        // Don't do it for inverse
        for (int i = 0; i < this.getMatrix()[0].length; i++) {
            // Find where the piece "starts" in the first row
            if (this.getMatrix()[0][i]) {
                return i;
            }
        }
        return getMatrix()[0].length - 1;
    }

    // Override equals to remove pieces from ArrayList
    // This is key, allows the code to identify the piece by name
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Piece)) return false;
        Piece piece = (Piece) obj;
        return this.name.equals(piece.name);
    }

    @Override
    // Makes it easier to display the matrix
    public String toString() {
        return name + " (" + rowPosition + ", " + columnPosition + ")";
    }
}
