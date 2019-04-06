import java.util.ArrayList;
import java.util.List;

class Board {

    // State of the board
    private boolean[][] board;
    private int rowPos = 0;
    private int columnPos = 0;

    // Lists to store the necessary data
    private List<Piece> usedPieces = new ArrayList<>();
    private List<Piece> unusedPieces = new ArrayList<>();

    // Constructor to se the board and the pieces
    Board (boolean[][] board, List<Piece> pieces) {

        this.board = board;
        unusedPieces = new ArrayList<>(pieces);
        List<Piece> possiblePieces = new ArrayList<>(pieces);
        tessellate(possiblePieces.get(0));
    }

    // This is the fail condition for the program
    private boolean fail(Piece piece) {

        // To store the moving over
        int localColumnPos = columnPos - piece.getShift();

        // If the piece will go outside the bounds of the board, return false
        if (piece.getMatrix().length + rowPos > board.length ||
                piece.getMatrix()[0].length + localColumnPos > board[0].length || localColumnPos < 0) return true;

        // Check for no true true collisions
        for (int i = 0; i < piece.getMatrix().length; i++) {
            for (int j = 0; j < piece.getMatrix()[0].length; j++) {
                // If there is a true + true anywhere, do not add piece
                if (piece.getMatrix()[i][j] && board[i + rowPos][j + localColumnPos]) return true;
            }
        }
        return false;
    }

    // Attempt to add a piece to the board at a specific position
    private boolean addPiece(Piece piece, int rowPos, int columnPos) {

        // No true true collisions
        for (int i = 0; i < piece.getMatrix().length; i++) {
            for (int j = 0; j < piece.getMatrix()[0].length; j++) {
                board[i + rowPos][j + columnPos] = piece.getMatrix()[i][j] ^ board[i + rowPos][j + columnPos];
            }
        }

        // Set the position of the piece for output?
        piece.rowPosition = rowPos;
        piece.columnPosition = columnPos;

        return true;
    }

    // Fill false elements with true elements, counting up how many changed
    private int floodFill(boolean[][] board, int startRow, int startCol) {

        int counter = 0;

        if (startRow < 0 || startRow >= board.length || startCol < 0 || startCol >= board[0].length) return 0;
        if (board[startRow][startCol]) return 0;

        board[startRow][startCol] = true;
        counter++;

        counter += floodFill(board, startRow + 1, startCol);
        counter += floodFill(board, startRow - 1, startCol);
        counter += floodFill(board, startRow, startCol + 1);
        counter += floodFill(board, startRow, startCol - 1);

        return counter;
    }

    // Checks if the floodFill() returned a multiple of five
    private boolean multipleOfFive() {

        boolean[][] tempBoard = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, tempBoard[i], 0, board[0].length);
        }
        for (int i = 0; i < tempBoard.length; i++) {
            for (int j = 0; j < tempBoard[0].length; j++) {
                if (!tempBoard[i][j]) {
                    int result = floodFill(tempBoard, i, j);
                    if(result % 5 != 0) return false;
                }
            }
        }
        return true;
    }

    // Find new rowPos and columnPos
    private void resetPosition() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (!board[i][j]) {
                    rowPos = i;
                    columnPos = j;
                    return;
                }
            }
        }
    }

    // If success, toggle variable and finish it up
    private boolean done() {

        // If there is a false, then there are still gaps
        for (boolean[] row : board) {
            for (boolean item : row) {
                if (!item) return false;
            }
        }
        return true;
    }

    // Prints the piece
    private void printPiece(Piece piece) {
        System.out.println(piece);
        piece.displayMatrix();
        System.out.println();
    }

    // Gets next "piece", which could be the same piece but rotated
    private Piece nextPiece(Piece piece, List<Piece> possiblePieces) {

        if (!piece.nextTransformation()) {
            possiblePieces.remove(piece);
            if (possiblePieces.size() == 0) return null;
            return possiblePieces.get(0);
        } else {
            return piece;
        }
    }

    // Remove a piece from the board by adding the same one, the xor in addpiece will take care of the logic
    private void removePiece(Piece piece) {
        addPiece(piece, piece.rowPosition, piece.columnPosition);
    }

    // Backtracking
    private boolean tessellate(Piece piece) {

        // Tests to see if the piece falls within the border
        if (fail(piece)) return false;

        // Add piece to board
        addPiece(piece, rowPos, columnPos - piece.getShift());

        // If the piece creates a closed space on the board that is not a multiple of 5, remove it
        if (!multipleOfFive()) {
            removePiece(piece);
            return false;
        }

        // Check if the program is done
        if (done()) return true;

        // Reset where the tessellation begins; save the piece for removal later from board
        resetPosition();
        Piece addedPiece = piece.clone();
        unusedPieces.remove(piece);

        // These are the possible pieces (+ their orientations to test for the next round)
        List<Piece> possiblePieces = new ArrayList<>(unusedPieces);
        piece = possiblePieces.get(0);

        while (piece != null) {
            // Try to tessellate
            boolean success = tessellate(piece);
            // Recursively print
            if (success) {

                printPiece(piece);
                return true;
            }

            // Finds the next piece / next orientation of the piece
            piece = nextPiece(piece, possiblePieces);
        }

        // Executes if there is no further path down this tree and removes the piece
        unusedPieces.add(addedPiece);
        removePiece(addedPiece);
        resetPosition();

        return false;
    }
}
