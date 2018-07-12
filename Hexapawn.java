import java.util.Scanner;

public class Hexapawn {

    public static void main(String args[]) {

        Scanner S   = new Scanner(System.in);
        String	    playerInput;
        char[]      waste;
        //player identifier:: 'W' or 'B'
        char        player;
        String[]    sinput = new String[8];
        //number of pieces
	//{White, Black}
        int[] pieceCount = {0, 0};
        int wFlag = 0;
        int bFlag = 0;
        int i = 0;
        int x = 0;
        int y = 0;

        //set the starting player
        playerInput = S.nextLine();
        waste = playerInput.toCharArray();
        player = waste[0];

        while(S.hasNextLine()){
            sinput[i] = S.nextLine();
            i++;
        }
        
        //create x value based on standard input
        x = sinput[0].length();

        //create y value based on standard input
        for(i = 0; i < sinput.length; i++){
            if(sinput[i] != null)
                y++;
        }

        //create board based on standard input dimensions
        char[][] board = new char[y][x];
        
	//copy input into board
	for(i = 0; i < board.length; i++){
	   board[i] = sinput[i].toCharArray();
	}
        
	//copy # of pawns in board to array pieceCount
        //print board layout to display
        for (i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j] == 'P')
                    pieceCount[0]++;
                else if(board[i][j] == 'p')
                    pieceCount[1]++;
            }
        }
        
	//Checks if piece counts are valid
        if(pieceCount[0] > board[0].length || pieceCount[1] > board[0].length){
		System.out.print("One side has too many pieces. Aborting.\n");
		return;
	}
        if(pieceCount[0] == 0 && pieceCount[1] == 0){
		System.out.print("There are no pieces on the board. Aborting.\n");
		return;
	}


	//Checks for pieces on opposite ends of board.
        //If one or more has been found, flags are set to decide what happens next.
        for (i = 0; i < board[0].length; i++) {
            if(board[0][i] == 'P'){
                if(player == 'W'){
		    wFlag = 1;
		}
		else if(player == 'B'){
		    wFlag = 2;
	        }
	    }
            else if(board[board.length-1][i] == 'p'){
	        if(player == 'W'){
                    bFlag = 1;
		}
		else if(player == 'B'){
                    bFlag = 2;
		}
	    }
	}
        if(wFlag > 0 && bFlag > 0){
	    System.out.print("Both sides have pawns at opposite ends of board. Unknown winner. Aborting.\n");
	    return;
	}
	else if(wFlag == 1){
	    System.out.print("1\n");
	    return;
	}
	else if(wFlag == 2){
	    System.out.print("-1\n");
	    return;
	}
	else if(bFlag == 1){
	    System.out.print("-1\n");
	    return;
	}
	else if(bFlag == 2){
	    System.out.print("1\n");
	    return;
	}
                
               
		

        ////////////////////////////////////////////////////////////////
        //Board has been made
        ////////////////////////////////////////////////////////////////
        System.out.print(game(board, player, pieceCount) + "\n");  
   }
   
     static int game(char[][] board, char player, int[] pieceCount) {

        //White player moves from bottom of board up
        if(player == 'W'){
            for(int a = board.length - 1; a > 0; a--){
                for(int b = 0; b < board[0].length; b++){
                    //checks to see if the current position ([a][b]) holds a white pawn
                    if(board[a][b] == 'P') {
                        //checks to see if we are in the first column/far left side of the board
                        if (b == 0) {
///////////////////////////////////////////////////////////////////////////////////////////////////
                            //checks to see if an opponents piece is in a position to be taken
                            //if so we will take it and update/copy the board
                            ///////////////////////////////////////////////////////////
                            //        b  b+1 b+2                    b  b+1 b+2
                            // a-1|   .   p   .   |  ----->  a-1|   .   P   .   |
                            //   a|   P   .   .   |  ----->    a|   .   .   .   |
                            ///////////////////////////////////////////////////////////
                            if (board[a - 1][b + 1] == 'p') {
                                char[][] newBoard = new char[board.length][board[0].length];
                                //copy board
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }
                                //replace their piece for yours on newBoard
                                newBoard[a - 1][b + 1] = 'P';
                                //remove your piece at old position from the board
                                newBoard[a][b] = '.';

                                //decrement black pawn
                                pieceCount[1]--;

                                //White wins due to promotion!
                                if ((a - 1) == 0)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'B', pieceCount) == -1) {
                                    return 1;
                                }

                                //move was unsuccessful
                                //return pieces
                                pieceCount[1]++;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //if there is no opponent to take and there is an empty space in front
                            //we will move into that space
                            ///////////////////////////////////////////////////////////
                            //        b  b+1 b+2                    b  b+1 b+2
                            // a-1|   .   .   .   |  ----->  a-1|   P   .   .   |
                            //   a|   P   .   .   |  ----->    a|   .   .   .   |
                            ///////////////////////////////////////////////////////////
                            if (board[a - 1][b] == '.') {
                                char[][] newBoard = new char[board.length][board[0].length];
                                //copy board
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }

                                //copy your piece to new position
                                newBoard[a - 1][b] = 'P';
                                //remove your piece from old position
                                newBoard[a][b] = '.';

                                //you win due to promotion!
                                if ((a - 1) == 0)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'B', pieceCount) == -1) {
                                    return 1;
                                }
                            }
                        }
                        else if (b == (board[0].length) - 1) {
///////////////////////////////////////////////////////////////////////////////////////////////////
                            //checks to see if an opponents piece is in a position to be taken
                            //if so we will take it and update/copy the board
                            ///////////////////////////////////////////////////////////
                            //       b-2 b-1  b                    b-2 b-1  b
                            // a-1|   .   p   .   |  ----->  a-1|   .   P   .   |
                            //   a|   .   .   P   |  ----->    a|   .   .   .   |
                            ///////////////////////////////////////////////////////////
                            if (board[a - 1][b - 1] == 'p') {
                                char[][] newBoard = new char[board.length][board[0].length];
                                //copy board
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }
                                //replace their piece for yours on newBoard
                                newBoard[a - 1][b - 1] = 'P';
                                //remove your piece at old position from the board
                                newBoard[a][b] = '.';

                                //decrement black pawn
                                pieceCount[1]--;

                                //you win due to promotion!
                                if ((a - 1) == 0)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'B', pieceCount) == -1) {
                                    return 1;
                                }

                                //move was unsuccessful
                                //return pieces
                                pieceCount[1]++;
                            }
                            ////////////////////////////////////////////////////////////////////////
                            //if there is no opponent to take and there is an empty space in front
                            //we will move into that space
                            ///////////////////////////////////////////////////////////
                            //       b-2 b-1  b                    b-2 b-1  b
                            // a-1|   .   .   .   |  ----->  a-1|   .   .   P   |
                            //   a|   .   .   P   |  ----->    a|   .   .   .   |
                            ///////////////////////////////////////////////////////////
                            if (board[a - 1][b] == '.') {
                                char[][] newBoard = new char[board.length][board[0].length];
                                //copy board
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }

                                //copy your piece to new position
                                newBoard[a - 1][b] = 'P';
                                //remove your piece from old position
                                newBoard[a][b] = '.';

                                //Black wins due to promotion!
                                if ((a - 1) == 0)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'B', pieceCount) == -1) {
                                    return 1;
                                }
                            }
                        }
                        else if (b != 0 && b != (board[0].length) - 1) {
///////////////////////////////////////////////////////////////////////////////////////////////////
                            //checks to see if a piece can be taken to the left
                            ///////////////////////////////////////////////////////////
                            //       b-1  b  b+1                   b-1  b  b+1
                            // a-1|   p   .   .   |  ----->  a-1|   P   .   .   |
                            //   a|   .   P   .   |  ----->    a|   .   .   .   |
                            ///////////////////////////////////////////////////////////
                            if (board[a - 1][b - 1] == 'p') {
                                //copy board
                                char[][] newBoard = new char[board.length][board[0].length];
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }

                                //copy your piece to new position
                                newBoard[a - 1][b - 1] = 'P';
                                //remove your piece from old position
                                newBoard[a][b] = '.';

                                //decrement black pawn
                                pieceCount[1]--;

                                //you win due to promotion!
                                if ((a - 1) == 0)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'B', pieceCount) == -1) {
                                    return 1;
                                }

                                //move was unsuccessful
                                //return pieces
                                pieceCount[1]++;

                            }

                            ///////////////////////////////////////////////////////////////////////////
                            //checks to see if a piece can be taken to the right
                            ///////////////////////////////////////////////////////////
                            //       b-1  b  b+1                   b-1  b  b+1
                            // a-1|   .   .   p   |  ----->  a-1|   .   .   P   |
                            //   a|   .   P   .   |  ----->    a|   .   .   .   |
                            ///////////////////////////////////////////////////////////
                            if (board[a - 1][b + 1] == 'p') {
                                //copy board
                                char[][] newBoard = new char[board.length][board[0].length];
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }

                                //copy your piece to new position
                                newBoard[a - 1][b + 1] = 'P';
                                //remove your piece from old position
                                newBoard[a][b] = '.';

                                //decrement black pawn
                                pieceCount[1]--;

                                //you win due to promotion!
                                if ((a - 1) == 0)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'B', pieceCount) == -1) {
                                    return 1;
                                }

                                //move was unsuccessful
                                //return pieces
                                pieceCount[1]++;
                            }
                            ///////////////////////////////////////////////////////////////////////////
                            //if there is no opponent to take and there is an empty space in front
                            //we will move into that space
                            ///////////////////////////////////////////////////////////
                            //       b-1  b  b+1                   b-1  b  b+1
                            // a-1|   .   .   .   |  ----->  a-1|   .   P   .   |
                            //   a|   .   P   .   |  ----->    a|   .   .   .   |
                            ///////////////////////////////////////////////////////////
                            if (board[a - 1][b] == '.') {
                                char[][] newBoard = new char[board.length][board[0].length];
                                //copy board
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }

                                //copy your piece to new position
                                newBoard[a - 1][b] = 'P';
                                //remove your piece from old position
                                newBoard[a][b] = '.';

                                //you win due to promotion!
                                if ((a - 1) == 0)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'B', pieceCount) == -1) {
                                    return 1;
                                }
                            }
                        }
                    }


                }
            }
            //No moves were valid or successful
            return -1;
        }


//*************************************************************************************************************
//*****    ******* *********************** ********************************************************************
//***** *** ****** *********************** ********************************************************************
//***** *** ****** *********************** ********************************************************************
//*****     ****** *********************** ********************************************************************
//***** ****  **** *****     ****     **** ** *****************************************************************
//***** ***** **** **** **** **** ********   ******************************************************************
//***** ***** **** **** **** **** ******** * ******************************************************************
//*****      ***** *****     ****     **** ** *****************************************************************
//*************************************************************************************************************

        //Black player moves from top of board down
        else if(player == 'B'){
            for(int a = 0; a < board.length-1; a++){
                for(int b = 0; b < board[0].length; b++){
                    //checks to see if the current position ([a][b]) holds a white pawn
                    if(board[a][b] == 'p') {
                        //checks to see if we are in the first column/far left side of the board
                        if (b == 0) {
///////////////////////////////////////////////////////////////////////////////////////////////////
                            //checks to see if an opponents piece is in a position to be taken
                            //if so we will take it and update/copy the board
                            ///////////////////////////////////////////////////////////
                            //        b  b+1 b+2                    b  b+1 b+2
                            //   a|   p   .   .   |  ----->    a|   .   .   .   |
                            // a+1|   .   P   .   |  ----->  a+1|   .   p   .   |
                            ///////////////////////////////////////////////////////////
                            if (board[a + 1][b + 1] == 'P') {
                                char[][] newBoard = new char[board.length][board[0].length];
                                //copy board
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }
                                //replace their piece for yours on newBoard
                                newBoard[a + 1][b + 1] = 'p';
                                //remove your piece at old position from the board
                                newBoard[a][b] = '.';

                                //decrement black pawn
                                pieceCount[0]--;

                                //Black wins due to promotion!
                                if ((a + 1) == board.length - 1)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'W', pieceCount) == -1) {
                                    return 1;
                                }

                                //move was unsuccessful
                                //return pieces
                                pieceCount[0]++;
                            }
                            ///////////////////////////////////////////////////////////////////////
                            //if there is no opponent to take and there is an empty space in front
                            //we will move into that space
                            ///////////////////////////////////////////////////////////
                            //        b  b+1 b+2                    b  b+1 b+2
                            //   a|   p   .   .   |  ----->    a|   .   .   .   |
                            // a+1|   .   .   .   |  ----->  a+1|   p   .   .   |
                            ///////////////////////////////////////////////////////////
                            if (board[a + 1][b] == '.') {
                                char[][] newBoard = new char[board.length][board[0].length];
                                //copy board
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }

                                //copy your piece to new position
                                newBoard[a + 1][b] = 'p';
                                //remove your piece from old position
                                newBoard[a][b] = '.';

                                //Black wins due to promotion!
                                if ((a + 1) == board.length - 1)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'W', pieceCount) == -1) {
                                    return 1;
                                }
                            }
                        } else if (b == (board[0].length) - 1) {
///////////////////////////////////////////////////////////////////////////////////////////////////
                            //checks to see if an opponents piece is in a position to be taken
                            //if so we will take it and update/copy the board
                            ///////////////////////////////////////////////////////////
                            //       b-2 b-1  b                    b-2 b-1  b
                            //   a|   .   .   p   |  ----->    a|   .   .   .   |
                            // a+1|   .   P   .   |  ----->  a+1|   .   p   .   |
                            ///////////////////////////////////////////////////////////
                            if (board[a + 1][b - 1] == 'P') {
                                char[][] newBoard = new char[board.length][board[0].length];
                                //copy board
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }
                                //replace their piece for yours on newBoard
                                newBoard[a + 1][b - 1] = 'p';
                                //remove your piece at old position from the board
                                newBoard[a][b] = '.';

                                //decrement black pawn
                                pieceCount[0]--;

                                //Black wins due to promotion!
                                if ((a + 1) == board.length - 1)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'W', pieceCount) == -1) {
                                    return 1;
                                }

                                //move was unsuccessful
                                //return pieces
                                pieceCount[0]++;
                            }
                            ///////////////////////////////////////////////////////////////////////
                            //if there is no opponent to take and there is an empty space in front
                            //we will move into that space
                            ///////////////////////////////////////////////////////////
                            //       b-2 b-1  b                    b-2 b-1  b
                            //   a|   .   .   p   |  ----->    a|   .   .   .   |
                            // a+1|   .   .   .   |  ----->  a+1|   .   .   p   |
                            ///////////////////////////////////////////////////////////
                            if (board[a + 1][b] == '.') {
                                char[][] newBoard = new char[board.length][board[0].length];
                                //copy board
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }

                                //copy your piece to new position
                                newBoard[a + 1][b] = 'p';
                                //remove your piece from old position
                                newBoard[a][b] = '.';

                                //you win due to promotion!
                                if ((a + 1) == board.length - 1)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'W', pieceCount) == -1) {
                                    return 1;
                                }
                            }
                        } else if (b != 0 && b != (board[0].length) - 1) {
///////////////////////////////////////////////////////////////////////////////////////////////////
                            //checks to see if a piece can be taken to the left
                            ///////////////////////////////////////////////////////////
                            //       b-1  b  b+1                   b-1  b  b+1
                            //   a|   .   p   .   |  ----->    a|   .   .   .   |
                            // a+1|   P   .   .   |  ----->  a+1|   p   .   .   |
                            ///////////////////////////////////////////////////////////
                            if (board[a + 1][b - 1] == 'P') {
                                //copy board
                                char[][] newBoard = new char[board.length][board[0].length];
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }

                                //copy your piece to new position
                                newBoard[a + 1][b - 1] = 'p';
                                //remove your piece from old position
                                newBoard[a][b] = '.';

                                //decrement black pawn
                                pieceCount[0]--;

                                //you win due to promotion!
                                if ((a + 1) == board.length - 1)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'W', pieceCount) == -1) {
                                    return 1;
                                }

                                //move was unsuccessful
                                //return pieces
                                pieceCount[0]++;

                            }

                            ///////////////////////////////////////////////////////////////////////
                            //checks to see if a piece can be taken to the right
                            ///////////////////////////////////////////////////////////
                            //       b-1  b  b+1                   b-1  b  b+1
                            //   a|   .   p   .   |  ----->    a|   .   .   .   |
                            // a+1|   .   .   P   |  ----->  a+1|   .   .   p   |
                            ///////////////////////////////////////////////////////////
                            if (board[a + 1][b + 1] == 'P') {
                                //copy board
                                char[][] newBoard = new char[board.length][board[0].length];
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }

                                //copy your piece to new position
                                newBoard[a + 1][b + 1] = 'p';
                                //remove your piece from old position
                                newBoard[a][b] = '.';

                                //decrement black pawn
                                pieceCount[0]--;

                                //you win due to promotion!
                                if ((a + 1) == board.length - 1)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'W', pieceCount) == -1) {
                                    return 1;
                                }

                                //move was unsuccessful
                                //return pieces
                                pieceCount[0]++;
                            }
                            ///////////////////////////////////////////////////////////////////////
                            //if there is no opponent to take and there is an empty space in front
                            //we will move into that space
                            ///////////////////////////////////////////////////////////
                            //       b-1  b  b+1                   b-1  b  b+1
                            //   a|   .   p   .   |  ----->    a|   .   .   .   |
                            // a+1|   .   .   .   |  ----->  a+1|   .   p   .   |
                            ///////////////////////////////////////////////////////////
                            if (board[a + 1][b] == '.') {
                                char[][] newBoard = new char[board.length][board[0].length];
                                //copy board
                                for (int i = 0; i < board.length; i++) {
                                    System.arraycopy(board[i], 0, newBoard[i], 0, board[1].length);
                                }

                                //copy your piece to new position
                                newBoard[a + 1][b] = 'p';
                                //remove your piece from old position
                                newBoard[a][b] = '.';

                                //you win due to promotion!
                                if ((a + 1) == board.length - 1)
                                    return 1;
                                //recursive call
                                if (game(newBoard, 'W', pieceCount) == -1) {
                                    return 1;
                                }
                            }
                        }

                    }

                }
            }
//*************************************************************************************************************
//*************************************************************************************************************
//*************************************************************************************************************
//*************************************************************************************************************
//*************************************************************************************************************
//*************************************************************************************************************
//*************************************************************************************************************
//*************************************************************************************************************
//*************************************************************************************************************
//*************************************************************************************************************
            //No moves were valid or successful
            return -1;
        }
        //required return value should never be reached
        return 0;
    }
}

