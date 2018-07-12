# Hexapon-results
Gives the winner based on a best-play Hexapon game.

## Usage
This program takes a string based on a Hexapon grid and returns a 1 if the side
currently moving will win or a -1 if they will lose. This calculates all possible moves
from the input position to find if the current player will win or lose if the current
player is playing a perfect game.

To run:

\>java Hexapawn < textfile.txt

To use, place a single line of data in a text file in this format:
  (player on move)(board layout)
player on move with either be B for black or W for white
lower case p is for black pawns.
uppercase P is for white pawns.
a period is for an empty space.
The contents of the textfile only requires a single line, like the example below.
Bppp.P.P.P
This sample represents that it's the Black players turns with the board configuration below
  ppp
  .P.
  P.P

The resulting output will be 1 signifying that the Black player wins.
