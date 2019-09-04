# pentominoes
This is a program that attempts to tessellate any 60-squared board with the 12 unique pentominoes using a backtracking algorithm (along with a few speed-enhancing heuristics). 

## Usage
To compile the program, use
```
javac Tessellate.java
```
Then, to run the program with a default 6x10 board, use
```
java Tessellate
```
To specify a board size (must by of size 60), use
```
java Tessellate [rows] [cols]
```
To specify a custom board (no need to be a rectangle), edit the `board` file and use '-' to indicate the empty spots of the board and '+' to indeicate the filled spots. For example, an 8x8 board with a 2x2 hole cut out of it (which amounts to a grid of size 60) could look like
```
--------
--------
--------
---++---
---++---
--------
--------
--------
```
Once the board file is edited, run
```
java Tessellate [board file]
```
The output will be in the format of 
```
W (0, 0)
T T ·   
· T T   
· · T   
```
where the piece is drawn using T's and the coordinates shown are the place on the board to position the top left corner of the rectangle containing the piece. 

## Sample output
Here's an output for the 6x10 board
```
W (0, 0)   Y (0, 2)   X (0, 5)   U (0, 7)  I (0, 9)   N (1, 0)   T (1, 3)   V (3, 0)   F (3, 2)
T T ·      T T T T    · T ·      T T       T          T ·        T · ·      T · ·      T · ·
· T T      · · T ·    T T T      · T       T          T T        T T T      T · ·      T T T
· · T                 · T ·      T T       T          · T        T · ·      T T T      · T ·
                                           T          · T
                                           T

P (3, 4)   Z (3, 7)   L (4, 4)
T T T      T T ·      · · · T
· T T      · T ·      T T T T
           · T T
```
Put together, this solution yields the following tessellation:
```
    0   1   2   3   4   5   6   7   8   9
  +---+---+---+---+---+---+---+---+---+---+
0 |       |               |   |       |   |
  +---+   +---+---+   +---+   +---+   +   +
1 |   |       |   |   |           |   |   |
  +   +---+   +   +---+---+   +---+   +   +
2 |       |   |           |   |       |   |
  +---+   +---+   +---+---+---+---+---+   +
3 |   |   |   |   |           |       |   |
  +   +   +   +---+---+       +---+   +   +
4 |   |   |           |       |   |   |   |
  +   +---+---+   +---+---+---+   +   +---+
5 |           |   |               |       |
  +---+---+---+---+---+---+---+---+---+---+
```
