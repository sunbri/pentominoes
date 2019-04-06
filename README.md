###### pentominoes

This is a program that attempts to tessellate any 60-squared board with the 12 unique pentominoes using a simple backtracking algorithm. 

##### Usage
To run the program with a default 6x10 board, use
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
