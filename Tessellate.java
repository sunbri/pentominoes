import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;

// Class: Curves, Surfaces, and Shapes

// THE GOAL OF THE PROGRAM IS TO TESSELLATE A 6X10 RECTANGLE WITH 12 PENTOMINOES TO SOLVE THE PROBLEM, INSTEAD OF
// PHYSICALLY USING THE PIECES AND TRYING FOR HOURS AND HOURS (although probably could've found a solution in the time
// it took to write this program)
public class Tessellate {

    // Objects for the 12 Pentominoes
    private static Piece V = new Piece(new boolean[][] {
            { true, true, true, },
            { true, false, false },
            { true, false, false }
    }, "V", false);

    private static Piece X = new Piece(new boolean[][] {
            { false, true, false },
            { true, true, true },
            { false, true, false }
    }, "X", false);

    private static Piece I = new Piece(new boolean[][] {
            { true, true, true, true, true }
    }, "I", false);

    private static Piece T = new Piece(new boolean[][] {
            { true, true, true },
            { false, true, false },
            { false, true, false }
    }, "T", false);

    private static Piece W = new Piece(new boolean[][] {
            { true, false, false },
            { true, true, false },
            { false, true, true }
    }, "W", false);

    private static Piece U = new Piece(new boolean[][] {
            { true, false, true },
            { true, true, true }
    }, "U", false);

    private static Piece N = new Piece(new boolean[][] {
            { true, true, false, false },
            { false, true, true, true }
    }, "N", true);

    private static Piece Z = new Piece(new boolean[][] {
            { true, true, false },
            { false, true, false },
            { false, true, true }
    }, "Z", true);

    private static Piece L = new Piece(new boolean[][] {
            { true, false, false, false},
            { true, true, true, true }
    }, "L", true);

    private static Piece F = new Piece(new boolean[][] {
            { false, true, false },
            { false, true, true },
            { true, true, false },
    }, "F", true);

    private static Piece P = new Piece(new boolean[][] {
            { false, true },
            { true, true },
            { true, true },
    }, "P", true);

    private static Piece Y = new Piece(new boolean[][]
    {
            { false, true, false, false },
            { true, true, true, true },
    }, "Y", true);

    private static Piece E = new Piece(new boolean[][]
    {
            { false }
    }, "E", true);

    public static void main(String[] args) {

        int rows = 6;
        int cols = 10;

        // Create a board object for anything, could even write a custom array
        boolean[][] emptyBoard = new boolean[rows][cols];

        switch (args.length) {
            case 1:
                try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {

                    // Read lines into list
                    List<String> lines = new ArrayList<>();
                    String line;
                    while ((line = br.readLine()) != null) {
                        line = line.replace("\n", "").replace("\r", "");
                        lines.add(line);
                    }

                    // Get longest line of the list
                    int largest = 0;
                    for (String l : lines)
                        if (l.length() > largest)
                            largest = l.length();

                    rows = lines.size();
                    cols = largest;

                    emptyBoard = new boolean[rows][cols];
                    int numTrue = 0;

                    // Read in + and - from the file data
                    for (int j = 0; j < lines.size(); j++) {
                        String l = lines.get(j);
                        for (int i = 0; i < largest; i++) {
                            if (l.charAt(i) != '+' && l.charAt(i) != '-') {
                                System.err.println("Board file must be only '+' and '-'!");
                                System.exit(1);
                            } else if (i > l.length() || l.charAt(i) == '+') {
                                emptyBoard[j][i] = true;
                            } else if (l.charAt(i) == '-') {
                                emptyBoard[j][i] = false;
                                numTrue++;
                            }
                        }
                    }

                    // Check if the board is valid 
                    if (numTrue != 60) {
                        System.err.println("Need 60 +'s for file to work!");
                        System.exit(1);
                    }

                } catch (Exception e) {
                    System.err.println("File input error! Check if file exists.");
                    System.exit(1);
                }
                break;
            case 2:
                    // Parse integer for board size
                try {
                    rows = Integer.parseInt(args[0]);
                    cols = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Non integer input!");
                    System.exit(1);
                }
                if (rows * cols != 60) {
                    System.err.println("Board must be of area 60 and lengths must be nonnegative!");
                    System.exit(1);
                }
                break;
            case 0:
                break;
            default:
                System.err.println("Usage: java Tessellate [filename | rows cols]*\n\tDefault runs on a 6 * 10 board.");
                System.exit(1);
        }

        // Select the 12 Pentominoes
        List<Piece> pieces = new ArrayList<>(Arrays.asList(V, X, I, Z, T, F, U, W, N, L, P, Y));

        // Shuffle pieces for different solution each time
        Collections.shuffle(pieces);

        // Blank placeholder so doesn't fail on the first call of tessellate
        pieces.add(0, E);

        // Print the board spec and how it works
        System.out.println("Pieces are printed out in rectangles, the coordinates (zero index) correspond to the top left corner of each rectangle.");
        System.out.println(String.format("Board size: %d rows, %d, columns (with possible holes)", rows, cols));
        System.out.println();

        // Run the tessellation
        new Board(emptyBoard, pieces);
    }

}
