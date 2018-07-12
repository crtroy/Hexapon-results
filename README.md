# Hexapon-results
Gives the winner based on a best-play Hexapon game.

## Usage
This program takes a string based on a Hexapon grid and returns a 1 if the side<br>
currently moving will win or a -1 if they will lose. This calculates all possible moves<br>
from the input position to find if the current player will win or lose if the current<br>
player is playing a perfect game.<br>

To run:<br>
\>java Hexapawn < textfile.txt<br>

To use, place a single line of data in a text file in this format:<br>
&nbsp;&nbsp;&nbsp;(player on move)(board layout)<br>
Player on move with either be B for black or W for white<br>
Lower case p is for black pawns.<br>
Uppercase P is for white pawns.<br>
A period is for an empty space.<br>
The contents of the textfile only requires a single line, like the example below.<br>
&nbsp;&nbsp;Bppp.P.P.P<br>
This sample represents that it's the Black players turns with the board configuration below<br>
&nbsp;&nbsp;ppp<br>
&nbsp;&nbsp;. P .<br>
&nbsp;&nbsp;P . P<br>

The resulting output will be 1 signifying that the Black player wins.
