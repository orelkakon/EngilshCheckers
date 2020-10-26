package orel;

import java.util.Scanner;


public class EnglishCheckers {

	// Global constants
	public static final int RED   = 1;
	public static final int BLUE  = -1;
	public static final int EMPTY = 0;

	public static final int SIZE  = 8;

	// You can ignore these constants
	public static final int MARK  = 3;
	public static EnglishCheckersGUI grid;

	public static Scanner getPlayerFullMoveScanner = null;
	public static Scanner getStrategyScanner = null;

	public static final int RANDOM		= 1;
	public static final int DEFENSIVE	= 2;
	public static final int SIDES		= 3;
	public static final int CUSTOM		= 4;


	public static void main(String[] args) {

		// ******** Don't delete ********* 
		// CREATE THE GRAPHICAL GRID
		grid = new EnglishCheckersGUI(SIZE);
		// ******************************* 


		//showBoard(example);
		//printMatrix(example);

		//interactivePlay();
		twoPlayers();


		/* ******** Don't delete ********* */    
		if (getPlayerFullMoveScanner != null){
			getPlayerFullMoveScanner.close();
		}
		if (getStrategyScanner != null){
			getStrategyScanner.close();
		}
		/* ******************************* */

	}


	public static int[][] createBoard() {
		int[][] board = null;
		board = new int [8][8]; //default value in array is 0
		for(int i=0;i<3;i++){ //red discs
			for(int j=0;j<8;j=j+2){
				if(i%2==0) //even row
				board[i][j]=1; //odd row 
				else
					board[i][j+1]=1;
			}	
		}	
		for(int i=5;i<8;i++){ //blue discs
			for(int j=0;j<8;j=j+2){
				if(i%2==0)//even row
				board[i][j]=-1;
				else //odd row
					board[i][j+1]=-1; 
			}	
		}	

		
		return board;
	}


	public static int[][] playerDiscs(int[][] board, int player) {
		int[][] positions = null;
		int x=0; // will be run on "positions"
		positions =new int [numOfDiscs(board,player)][2]; //init the array
			if(player==1){ //red
				for(int i=0;i<8;i++){
					for(int j=0;j<8;j++){
						if(board[i][j]==1| board[i][j]==2){
							positions[x][0]=i; //init row
							positions[x][1]=j; //init column
							x=x+1; //update x
						}
					}
				}
			}
			else
				for(int i=0;i<8;i++){
					for(int j=0;j<8;j++){
						if(board[i][j]==-1| board[i][j]==-2){
							positions[x][0]=i;  //init row
							positions[x][1]=j; //init column
							x=x+1; //update x
						}
					}
				}
		return positions;
	}
	
	public static int numOfDiscs(int[][] board, int player) {//Reference Method
		int sum=0;
		if(player==1){ //red
		   for(int i=0;i<8;i++){
			  for(int j=0;j<8;j++){
			  if(board[i][j]==1| board[i][j]==2) //count how many times 1,2 was
			    	sum++;
			  }
		   }
		}
		else //blue
			   for(int i=0;i<8;i++){
				   for(int j=0;j<8;j++){
					  if(board[i][j]==-1| board[i][j]==-2)//count how many times -1,-2 was
					    	sum++;
				   }
			   }
		
		return sum;
	}



	public static boolean isBasicMoveValid(int[][] board, int player, int fromRow, int fromCol, int toRow, int toCol) {
		boolean ans = false;
		if((fromRow>-1 & fromRow<8)&(fromCol>-1 & fromCol<8)&(toRow>-1 & toRow<8)&(toCol>-1 & toCol<8)){ //check valid of parameters
			if(board[toRow][toCol]==0){ // check if coordinate target was clear
				if(player==1){ 
					if(board[fromRow][fromCol]==1){ //simple red
						if(toRow>fromRow){ //check if go in the true way
							if(moveValidSimpleDisc(player,fromRow,fromCol,toRow,toCol)==true) //check valid move
								ans=true;
						}	
					}
					if(board[fromRow][fromCol]==2){ //queen of red
						if(moveValidQueen(fromRow,fromCol,toRow,toCol)==true) //check valid move
							ans=true;
					}
				}	
				if(player==-1){ 
					if(board[fromRow][fromCol]==-1){ //simple blue
						if(toRow<fromRow){ //check if go in the true way
							if(moveValidSimpleDisc(player,fromRow,fromCol,toRow,toCol)==true) //check valid move
								ans=true;
						}	
					}
					if(board[fromRow][fromCol]==-2){ //queen of blue
						if(moveValidQueen(fromRow,fromCol,toRow,toCol)==true) //check valid move
							ans=true;
					}
				}
			}	
		}	
			return ans;
	}	
	public static boolean moveValidQueen  (int fromRow, int fromCol, int toRow, int toCol) { //Reference Method
		boolean a =false;
		if(Math.abs((fromRow+fromCol)-(toRow+toCol))==0| Math.abs((fromRow+fromCol)-(toRow+toCol))==2){ //queen just need the solution be 0 or 2 
			if(Math.abs(fromRow-toRow)==1) // check that the space is 1 step
				a=true;
		}
		return a;
	}
		
	public static boolean moveValidSimpleDisc(int player, int fromRow, int fromCol, int toRow, int toCol) {//reference Method
		boolean a =false;
		if(player==1){ //red go up
			if((((fromRow+fromCol)-(toRow+toCol))==0|((fromRow+fromCol)-(toRow+toCol))==-2)){ // simple red need 'up' sum of 0 or -2
				if(toRow-1==fromRow) // check that the space is 1 step
					a=true;	
				
			}
		}
		else { //blue go down
				if((((fromRow+fromCol)-(toRow+toCol))==0|((fromRow+fromCol)-(toRow+toCol))==2)){ // simple blue need 'down' sum of 0 or 2
					if(fromRow-1==toRow) // check that the space is 1 step
						a=true;
				}
			}
			return a;	
	}	
	
	


	public static int[][] getAllBasicMoves(int[][] board, int player) {
		int x=0;
		int[][] moves = null;
		moves=new int[numOfMovesAcceptibale(board,player)][4];
		if(player==1){ //red
			int[][] discdOf1 = playerDiscs(board,player);
			for(int i=0;i<discdOf1.length;i++){
				if(board[(discdOf1[i][0])][(discdOf1[i][1])]==2){
					if(isBasicMoveValid(board,player,discdOf1[i][0],discdOf1[i][1],discdOf1[i][0]+1,discdOf1[i][1]-1)==true ){ //check if the move is valid
						moves[x][0]=discdOf1[i][0];   //update
						moves[x][1]=discdOf1[i][1];    //here
						moves[x][2]=discdOf1[i][0]+1;   //the
						moves[x][3]=discdOf1[i][1]-1;    //array "moves" 
						x++;
					}
					if(isBasicMoveValid(board,player,discdOf1[i][0],discdOf1[i][1],discdOf1[i][0]+1,discdOf1[i][1]+1)==true){ //check if the move is valid
						moves[x][0]=discdOf1[i][0];   //update
						moves[x][1]=discdOf1[i][1];    //here
						moves[x][2]=discdOf1[i][0]+1;   //the
						moves[x][3]=discdOf1[i][1]+1;    //array "moves" 
						x++;
					}
					if(isBasicMoveValid(board,player,discdOf1[i][0],discdOf1[i][1],discdOf1[i][0]-1,discdOf1[i][1]-1)==true){ //check if the move is valid
						moves[x][0]=discdOf1[i][0];   //update
						moves[x][1]=discdOf1[i][1];    //here
						moves[x][2]=discdOf1[i][0]-1;   //the
						moves[x][3]=discdOf1[i][1]-1;    //array "moves" 
						x++;
					}
					if(isBasicMoveValid(board,player,discdOf1[i][0],discdOf1[i][1],discdOf1[i][0]-1,discdOf1[i][1]+1)==true){ //check if the move is valid
						moves[x][0]=discdOf1[i][0];   //update
						moves[x][1]=discdOf1[i][1];    //here
						moves[x][2]=discdOf1[i][0]-1;   //the
						moves[x][3]=discdOf1[i][1]+1;    //array "moves" 
						x++;
					}
				}
				else{
					if(isBasicMoveValid(board,player,discdOf1[i][0],discdOf1[i][1],discdOf1[i][0]+1,discdOf1[i][1]-1)==true){ //check if the move is valid
						moves[x][0]=discdOf1[i][0];   //update
						moves[x][1]=discdOf1[i][1];    //here
						moves[x][2]=discdOf1[i][0]+1;   //the
						moves[x][3]=discdOf1[i][1]-1;    //array "moves" 
						x++;
					}
					if(isBasicMoveValid(board,player,discdOf1[i][0],discdOf1[i][1],discdOf1[i][0]+1,discdOf1[i][1]+1)==true){ //check if the move is valid
						moves[x][0]=discdOf1[i][0];   //update
						moves[x][1]=discdOf1[i][1];    //here
						moves[x][2]=discdOf1[i][0]+1;   //the
						moves[x][3]=discdOf1[i][1]+1;    //array "moves" 
						x++;
					}
				}
			}
		}
		if(player==-1){
			int[][] discdOfMinus1 = playerDiscs(board,player);
			for(int i=0;i<discdOfMinus1.length;i++){
				if(board[(discdOfMinus1[i][0])][(discdOfMinus1[i][1])]==-2){
					if(isBasicMoveValid(board,player,discdOfMinus1[i][0],discdOfMinus1[i][1],discdOfMinus1[i][0]+1,discdOfMinus1[i][1]-1)==true){ //check if the move is valid
						moves[x][0]=discdOfMinus1[i][0];   //update
						moves[x][1]=discdOfMinus1[i][1];    //here
						moves[x][2]=discdOfMinus1[i][0]+1;   //the
						moves[x][3]=discdOfMinus1[i][1]-1;    //array "moves" 
						x++;
					}
					if(isBasicMoveValid(board,player,discdOfMinus1[i][0],discdOfMinus1[i][1],discdOfMinus1[i][0]+1,discdOfMinus1[i][1]+1)==true){ //check if the move is valid
						moves[x][0]=discdOfMinus1[i][0];   //update
						moves[x][1]=discdOfMinus1[i][1];    //here
						moves[x][2]=discdOfMinus1[i][0]+1;   //the
						moves[x][3]=discdOfMinus1[i][1]+1;    //array "moves" 
						x++;
					}
					if(isBasicMoveValid(board,player,discdOfMinus1[i][0],discdOfMinus1[i][1],discdOfMinus1[i][0]-1,discdOfMinus1[i][1]-1)==true){ //check if the move is valid
						moves[x][0]=discdOfMinus1[i][0];   //update
						moves[x][1]=discdOfMinus1[i][1];    //here
						moves[x][2]=discdOfMinus1[i][0]-1;   //the
						moves[x][3]=discdOfMinus1[i][1]-1;    //array "moves" 
						x++;
					}
					if(isBasicMoveValid(board,player,discdOfMinus1[i][0],discdOfMinus1[i][1],discdOfMinus1[i][0]-1,discdOfMinus1[i][1]+1)==true){ //check if the move is valid
						moves[x][0]=discdOfMinus1[i][0];   //update
						moves[x][1]=discdOfMinus1[i][1];    //here
						moves[x][2]=discdOfMinus1[i][0]-1;   //the
						moves[x][3]=discdOfMinus1[i][1]+1;    //array "moves" 
						x++;
					}
				}
					else{
					if(isBasicMoveValid(board,player,discdOfMinus1[i][0],discdOfMinus1[i][1],discdOfMinus1[i][0]-1,discdOfMinus1[i][1]-1)==true){ //check if the move is valid
						moves[x][0]=discdOfMinus1[i][0];  //update
						moves[x][1]=discdOfMinus1[i][1];   //here
						moves[x][2]=discdOfMinus1[i][0]-1;  //the
						moves[x][3]=discdOfMinus1[i][1]-1;   //array "moves"
						x++;
					}
					if(isBasicMoveValid(board,player,discdOfMinus1[i][0],discdOfMinus1[i][1],discdOfMinus1[i][0]-1,discdOfMinus1[i][1]+1)==true){ //check if the move is valid
						moves[x][0]=discdOfMinus1[i][0];  //update
						moves[x][1]=discdOfMinus1[i][1];   //here
						moves[x][2]=discdOfMinus1[i][0]-1;  //the
						moves[x][3]=discdOfMinus1[i][1]+1;   //array "moves"
						x++;	
					}
				}
			}
		}
		return moves;
	}

	public static int numOfMovesAcceptibale(int[][] board, int player){ // Auxiliary operation

		int sum=0;
		if(player==1){ // red 
			int[][] discdOf_1 = playerDiscs(board,player);
			for(int i=0;i<discdOf_1.length;i++){
				if(board[(discdOf_1[i][0])][(discdOf_1[i][1])]==2){
					if(isBasicMoveValid(board,player,discdOf_1[i][0],discdOf_1[i][1],discdOf_1[i][0]+1,discdOf_1[i][1]-1)==true) //check one way to move
						sum++; //update x if the move is valid
					if(isBasicMoveValid(board,player,discdOf_1[i][0],discdOf_1[i][1],discdOf_1[i][0]+1,discdOf_1[i][1]+1)==true) //check second way to move
						sum++;	//update x if the move is valid
					if(isBasicMoveValid(board,player,discdOf_1[i][0],discdOf_1[i][1],discdOf_1[i][0]-1,discdOf_1[i][1]-1)==true) //check one way to move
						sum++; //update x if the move is valid
					if(isBasicMoveValid(board,player,discdOf_1[i][0],discdOf_1[i][1],discdOf_1[i][0]-1,discdOf_1[i][1]+1)==true) //check second way to move
						sum++;	//update x if the move is valid
				}
				else{
					if(isBasicMoveValid(board,player,discdOf_1[i][0],discdOf_1[i][1],discdOf_1[i][0]+1,discdOf_1[i][1]-1)==true) //check one way to move
						sum++; //update x if the move is valid
					if(isBasicMoveValid(board,player,discdOf_1[i][0],discdOf_1[i][1],discdOf_1[i][0]+1,discdOf_1[i][1]+1)==true) //check second way to move
						sum++;	//update x if the move is valid
				}
			}
		}	
		
		if(player==-1){ // blue
			int[][] discdOf_minus1 = playerDiscs(board,player);
			for(int i=0;i<discdOf_minus1.length;i++){
				if(board[(discdOf_minus1[i][0])][(discdOf_minus1[i][1])]==-2){
					if(isBasicMoveValid(board,player,discdOf_minus1[i][0],discdOf_minus1[i][1],discdOf_minus1[i][0]-1,discdOf_minus1[i][1]-1)==true)//check one way to move
						sum++; //update x if the move is valid
					if(isBasicMoveValid(board,player,discdOf_minus1[i][0],discdOf_minus1[i][1],discdOf_minus1[i][0]-1,discdOf_minus1[i][1]+1)==true) //check second way to move
						sum++;	//update x if the move is valid
					if(isBasicMoveValid(board,player,discdOf_minus1[i][0],discdOf_minus1[i][1],discdOf_minus1[i][0]+1,discdOf_minus1[i][1]-1)==true)//check one way to move
						sum++; //update x if the move is valid
					if(isBasicMoveValid(board,player,discdOf_minus1[i][0],discdOf_minus1[i][1],discdOf_minus1[i][0]+1,discdOf_minus1[i][1]+1)==true) //check second way to move
						sum++;	//update x if the move is valid
				}
				else{
					if(isBasicMoveValid(board,player,discdOf_minus1[i][0],discdOf_minus1[i][1],discdOf_minus1[i][0]-1,discdOf_minus1[i][1]-1)==true)//check one way to move
						sum++; //update x if the move is valid
					if(isBasicMoveValid(board,player,discdOf_minus1[i][0],discdOf_minus1[i][1],discdOf_minus1[i][0]-1,discdOf_minus1[i][1]+1)==true) //check second way to move
						sum++;	//update x if the move is valid
				}
			}	
		}
		
		
		return sum;
	}
	
	public static boolean isBasicJumpValid(int[][] board, int player, int fromRow, int fromCol, int toRow, int toCol) {
		boolean ans = false;
		if((fromRow>-1 & fromRow<8)&(fromCol>-1 & fromCol<8)&(toRow>-1 & toRow<8)&(toCol>-1 & toCol<8)){ //check valid of parameters
			if(board[toRow][toCol]==0){ //the destination is clear
				if(player==1){ //red
					if(board[fromRow][fromCol]==1){ //has a simple disc
						if(((toCol-fromCol)==(toRow-fromRow))&(toRow-fromRow==2)){ // check Gradient and distance, that say the jump is valid
							if(board[fromRow+1][fromCol+1]==-1 | board[fromRow+1][fromCol+1]==-2) // check that has a enemy disc in right place
								ans=true;
						}
						if(((toCol-fromCol)==-(toRow-fromRow))&(toRow-fromRow==2)){ // check Gradient and distance, that say the jump is valid
							if(board[fromRow+1][fromCol-1]==-1 | board[fromRow+1][fromCol-1]==-2) // check that has a enemy disc in right place
								ans=true;
						}
					}
					if(board[fromRow][fromCol]==2){ //has a red queen disc
						   if((((toRow-fromRow))==(toCol-fromCol))& (Math.abs(toRow-fromRow)== 2)){ // check Gradient and distance, that say the jump is valid
								if((fromRow<6 &fromRow>1 & fromCol<6 & fromCol>1)){
									
									
									if((board[fromRow+1][fromCol+1]==-1 | board[fromRow+1][fromCol+1]== -2) & (toRow>fromRow) ) // check that has a enemy disc in right place
										ans=true;
									if((board[fromRow-1][fromCol-1]==-1 | board[fromRow-1][fromCol-1]== -2) & (toRow<fromRow) ) // check that has a enemy disc in right place
										ans=true;
									
									
								}
								else {
									
									if((fromCol==1|fromCol==0)&(fromRow==1|fromRow==0))
										if(board[fromRow+1][fromCol+1]==-1 | board[fromRow+1][fromCol+1]==-2) // check that has a enemy disc in right place
											return true;
									if((fromCol==7|fromCol==6) &(fromRow==7|fromRow==6))
										if(board[fromRow-1][fromCol-1]==-1 | board[fromRow-1][fromCol-1]==-2) // check that has a enemy disc in right place
											return true;
									if((fromCol==1|fromCol==0) &(fromRow==7|fromRow==6))
										return false;
									if((fromCol==7|fromCol==6) &(fromRow==1|fromRow==0))
										return false;
									
									else{
										if(fromRow==7|fromRow==6){
											if(board[fromRow-1][fromCol-1]==-1 | board[fromRow-1][fromCol-1]==-2) // check that has a enemy disc in right place
												return true;
										}
										if(fromCol==7|fromCol==6){
											if(board[fromRow-1][fromCol-1]==-1 | board[fromRow-1][fromCol-1]==-2) // check that has a enemy disc in right place
												return true;
										}
										if(fromCol==0|fromCol==1){
											if(board[fromRow+1][fromCol+1]==-1 | board[fromRow+1][fromCol+1]==-2) // check that has a enemy disc in right place
												return true;
										}
										if(fromRow==0|fromRow==1){
											if(board[fromRow+1][fromCol+1]==-1 | board[fromRow+1][fromCol+1]==-2) // check that has a enemy disc in right place
												return true;
										}
									}
								}
							}
						
					if((((toCol-fromCol))==-(toRow-fromRow))&(Math.abs(toRow-fromRow)== 2)){ // check Gradient and distance, that say the jump is valid
							if(fromRow<6 & fromRow>1 & fromCol<6 & fromCol>1){
								if((board[fromRow+1][fromCol-1]==-1 | board[fromRow+1][fromCol-1]==-2) & (toRow>fromRow) ) // check that has a enemy disc in right place
									ans=true;
								if((board[fromRow-1][fromCol+1]==-1 | board[fromRow-1][fromCol+1]==-2) & (toRow<fromRow) )
									ans=true;
							}
							else {
								
								if((fromCol==1|fromCol==0)&(fromRow==1|fromRow==0))
									return false;
								if((fromCol==7|fromCol==6) &(fromRow==7|fromRow==6))
									return false;
								if((fromCol==1|fromCol==0) &(fromRow==7|fromRow==6))
									if(board[fromRow-1][fromCol+1]==-1 | board[fromRow-1][fromCol+1]==-2) // check that has a enemy disc in right place
										return true;
								if((fromCol==7|fromCol==6) &(fromRow==1|fromRow==0)){
									if(board[fromRow+1][fromCol-1]==-1 | board[fromRow+1][fromCol-1]==-2){ // check that has a enemy disc in right place
										return true;
									}
								}
								//---check---//
								else{
									if(fromRow==7|fromRow==6){
										if(board[fromRow-1][fromCol+1]==-1 | board[fromRow-1][fromCol+1]==-2) // check that has a enemy disc in right place
											return true;
									}
									if(fromCol==7|fromCol==6){
										if(board[fromRow+1][fromCol-1]==-1 | board[fromRow+1][fromCol-1]==-2) // check that has a enemy disc in right place
											return true;
									}
									if(fromCol==0|fromCol==1){
										if(board[fromRow-1][fromCol+1]==-1 | board[fromRow-1][fromCol+1]==-2) // check that has a enemy disc in right place
											return true;
									}
									if(fromRow==0|fromRow==1){
										if(board[fromRow+1][fromCol-1]==-1 | board[fromRow+1][fromCol-1]==-2) // check that has a enemy disc in right place
											return true;
									}
								}
							}
						}
					}
				}
				if(player==-1){ //blue
					if(board[fromRow][fromCol]==-1){ //has a simple disc
						if((((toCol-fromCol))==(toRow-fromRow))&(fromRow-toRow==2)){ // check Gradient and distance, that say the jump is valid
							if(board[fromRow-1][fromCol-1]==1 | board[fromRow-1][fromCol-1]==2) // check that has a enemy disc in right place
								ans=true;
						}
						if((((toCol-fromCol))==-(toRow-fromRow))&(fromRow-toRow==2)){ // check Gradient and distance, that say the jump is valid
							if(board[fromRow-1][fromCol+1]==1 | board[fromRow-1][fromCol+1]==2) // check that has a enemy disc in right place
								ans=true;
						}
					}
					if(board[fromRow][fromCol]==-2){ //has a blue queen disc
						if((((toCol-fromCol))==(toRow-fromRow))& (Math.abs(toRow-fromRow)== 2)){ // check Gradient and distance, that say the jump is valid
							if((fromRow<6 &fromRow>1 & fromCol<6 & fromCol>1)){
								
								
								if((board[fromRow+1][fromCol+1]==1 | board[fromRow+1][fromCol+1]== 2) & (toRow>fromRow) ) // check that has a enemy disc in right place
									ans=true;
								if((board[fromRow-1][fromCol-1]==1 | board[fromRow-1][fromCol-1]== 2) & (toRow<fromRow) ) // check that has a enemy disc in right place
									ans=true;
								
								
							}
							else {
								
								if((fromCol==1|fromCol==0)&(fromRow==1|fromRow==0)){
									if(board[fromRow+1][fromCol+1]==1 | board[fromRow+1][fromCol+1]==2) // check that has a enemy disc in right place
										return true;
								}
								if((fromCol==7|fromCol==6) &(fromRow==7|fromRow==6)){
									if(board[fromRow-1][fromCol-1]==1 | board[fromRow-1][fromCol-1]==2) // check that has a enemy disc in right place
										return true;
								}
								if((fromCol==1|fromCol==0) &(fromRow==7|fromRow==6))
									return false;
								if((fromCol==7|fromCol==6) &(fromRow==1|fromRow==0))
									return false;
								
								else{
									if(fromRow==7|fromRow==6){
										if(board[fromRow-1][fromCol-1]==1 | board[fromRow-1][fromCol-1]==2) // check that has a enemy disc in right place
											return true;
									}
									if(fromCol==7|fromCol==6){
										if(board[fromRow-1][fromCol-1]==1 | board[fromRow-1][fromCol-1]==2) // check that has a enemy disc in right place
											return true;
									}
									if(fromCol==0|fromCol==1){
										if(board[fromRow+1][fromCol+1]==1 | board[fromRow+1][fromCol+1]==2) // check that has a enemy disc in right place
											return true;
									}
									if(fromRow==0|fromRow==1){
										if(board[fromRow+1][fromCol+1]==1 | board[fromRow+1][fromCol+1]==2) // check that has a enemy disc in right place
											return true;
									}
								}
							}
						}
					 
						if((((toCol-fromCol))==-(toRow-fromRow))&(Math.abs(toRow-fromRow)== 2)){ // check Gradient and distance, that say the jump is valid
							if(fromRow<6 & fromRow>1 & fromCol<6 & fromCol>1){
								if((board[fromRow+1][fromCol-1]==1 | board[fromRow+1][fromCol-1]==2) & (toRow>fromRow) ) // check that has a enemy disc in right place
									ans=true;
								if((board[fromRow-1][fromCol+1]==1 | board[fromRow-1][fromCol+1]==2) & (toRow<fromRow) )
									ans=true;
							}
							else {
								
								if((fromCol==1|fromCol==0)&(fromRow==1|fromRow==0))
									return false;
								if((fromCol==7|fromCol==6) &(fromRow==7|fromRow==6))
									return false;
								if((fromCol==1|fromCol==0) &(fromRow==7|fromRow==6))
									if(board[fromRow-1][fromCol+1]==1 | board[fromRow-1][fromCol+1]==2) // check that has a enemy disc in right place
										return true;
								if((fromCol==7|fromCol==6) &(fromRow==1|fromRow==0)){
									if(board[fromRow+1][fromCol-1]==1 | board[fromRow+1][fromCol-1]==2){ // check that has a enemy disc in right place
										return true;
									}
								}
								//---check---//
								else{
									if(fromRow==7|fromRow==6){
										if(board[fromRow-1][fromCol+1]==1 | board[fromRow-1][fromCol+1]==2) // check that has a enemy disc in right place
											return true;
									}
									if(fromCol==7|fromCol==6){
										if(board[fromRow+1][fromCol-1]==1 | board[fromRow+1][fromCol-1]==2) // check that has a enemy disc in right place
											return true;
									}
									if(fromCol==0|fromCol==1){
										if(board[fromRow-1][fromCol+1]==1 | board[fromRow-1][fromCol+1]==2) // check that has a enemy disc in right place
											return true;
									}
									if(fromRow==0|fromRow==1){
										if(board[fromRow+1][fromCol-1]==1 | board[fromRow+1][fromCol-1]==2) // check that has a enemy disc in right place
											return true;
									}
								}
							}
						}
					}
				}
			}
		}
		return ans;
	}
	public static int [][] getRestrictedBasicJumps(int[][] board, int player, int row, int col) {
		int[][] moves = null;
		int x=0; //will the pointer that run on the array "moves"
		moves = new int [numOfBasicJumps(board,player,row,col)][4];
		if(player==1){//red discs go up
			if(board[row][col]==1){ //simple disc
				if(isBasicJumpValid(board,player,row,col,row+2,col+2)){ // if the jump is valid
					putIntoTheValue(moves,row,col,row+2,col+2,x);
					x++;//update x;
				}
				if(isBasicJumpValid(board,player,row,col,row+2,col-2)) // if the jump is valid
					putIntoTheValue(moves,row,col,row+2,col-2,x);
			}
			if(board[row][col]==2){ //queen disc
				if(isBasicJumpValid(board,player,row,col,row+2,col+2)){ // if the jump is valid
					putIntoTheValue(moves,row,col,row+2,col+2,x);
					x++;//update x;
				}
				if(isBasicJumpValid(board,player,row,col,row+2,col-2)){ // if the jump is valid
					putIntoTheValue(moves,row,col,row+2,col-2,x);
					x++;//update x;
				}
				if(isBasicJumpValid(board,player,row,col,row-2,col+2)){ // if the jump is valid
					putIntoTheValue(moves,row,col,row-2,col+2,x);
					x++;//update x;
				}
				if(isBasicJumpValid(board,player,row,col,row-2,col-2)) // if the jump is valid
					putIntoTheValue(moves,row,col,row-2,col-2,x);
			}
		}
		if(player==-1){//blue discs go down 
			if(board[row][col]==-1){ //simple disc
				if(isBasicJumpValid(board,player,row,col,row-2,col-2)){ // if the jump is valid
					putIntoTheValue(moves,row,col,row-2,col-2,x);
					x++;//update x;
				}
				if(isBasicJumpValid(board,player,row,col,row-2,col+2)) // if the jump is valid
					putIntoTheValue(moves,row,col,row-2,col+2,x);
			}
			if(board[row][col]==-2){ //queen disc
				if(isBasicJumpValid(board,player,row,col,row+2,col+2)){ // if the jump is valid
					putIntoTheValue(moves,row,col,row+2,col+2,x);
					x++;//update x;
				}
				if(isBasicJumpValid(board,player,row,col,row+2,col-2)){ // if the jump is valid
					putIntoTheValue(moves,row,col,row+2,col-2,x);
					x++;//update x;
				}
				if(isBasicJumpValid(board,player,row,col,row-2,col+2)){ // if the jump is valid
					putIntoTheValue(moves,row,col,row-2,col+2,x);
					x++;//update x;
				}
				if(isBasicJumpValid(board,player,row,col,row-2,col-2)) // if the jump is valid
					putIntoTheValue(moves,row,col,row-2,col-2,x);
			}
		}

		
		return moves;
	}
	
	public static void putIntoTheValue(int[][]a ,int x,int y, int m,int n, int pointer) {//Reference Method --> puts values in array
		if(a[pointer].length==4){ //my function need to work just in case of 4 cells in the array
			a[pointer][0]=x;
			a[pointer][1]=y;
			a[pointer][2]=m;
			a[pointer][3]=n;
		}
	}
	public static int numOfBasicJumps(int[][] board, int player, int row, int col) {//Reference Method
		int sum=0;
		if(player==1){//red discs go up
			if(board[row][col]==1){ //simple disc
				if(isBasicJumpValid(board,player,row,col,row+2,col+2)) // the jump is valid
					sum++; //update sum
				if(isBasicJumpValid(board,player,row,col,row+2,col-2)) // the jump is valid
					sum++; //update sum
			}
			if(board[row][col]==2){ //queen disc
				if(isBasicJumpValid(board,player,row,col,row+2,col+2)) // the jump is valid
					sum++; //update sum
				if(isBasicJumpValid(board,player,row,col,row+2,col-2)) // the jump is valid
					sum++; //update sum
				if(isBasicJumpValid(board,player,row,col,row-2,col+2)) // the jump is valid
					sum++; //update sum
				if(isBasicJumpValid(board,player,row,col,row-2,col-2)) // the jump is valid
					sum++; //update sum
			}
		}
		if(player==-1){//blue discs go down 
			if(board[row][col]==-1){ //simple disc
				if(isBasicJumpValid(board,player,row,col,row-2,col-2)) // the jump is valid
					sum++; //update sum
				if(isBasicJumpValid(board,player,row,col,row-2,col+2)) // the jump is valid
					sum++; //update sum
			}
			if(board[row][col]==-2){ //queen disc
				if(isBasicJumpValid(board,player,row,col,row+2,col+2)) // the jump is valid
					sum++; //update sum
				if(isBasicJumpValid(board,player,row,col,row+2,col-2)) // the jump is valid
					sum++; //update sum
				if(isBasicJumpValid(board,player,row,col,row-2,col+2)) // the jump is valid
					sum++; //update sum
				if(isBasicJumpValid(board,player,row,col,row-2,col-2)) // the jump is valid
					sum++; //update sum
			}
		}
		
		return sum;
	}

	
	public static int[][] getAllBasicJumps(int[][] board, int player) {
		int[][] moves = null;
		int[][]temp;
		int x=0; //pointer in moves
		moves = new int [numOfBasicJumps(board,player)][4];
		int[][]PlayerDiscs=playerDiscs(board,player); // build a array discs of player
		for(int i=0;i<PlayerDiscs.length;i++){ 
			temp=getRestrictedBasicJumps(board,player,PlayerDiscs[i][0],PlayerDiscs[i][1]); //put the jumps of every disc
			if(temp.length != 0){ //has a jumps 
				for(int j=0;j<temp.length;j++){
					putIntoTheValue(moves,temp[j][0],temp[j][1],temp[j][2],temp[j][3],x); //put the values to their correct positions
					x++;//update the pointer after the insert 
				}
			}
		}
		return moves;
	}
	
	public static int numOfBasicJumps(int[][] board, int player){ //reference method
		int sum=0;
		int[][]temp;
		int[][]PlayerDiscs=playerDiscs(board,player); // build a array discs of player
		for(int i=0;i<PlayerDiscs.length;i++){ 
			temp=getRestrictedBasicJumps(board,player,PlayerDiscs[i][0],PlayerDiscs[i][1]); //put the jumps of every disc
			sum=sum+temp.length; //length of the array is the amount of jumps of disc
		}
		return sum; 
	}


	public static boolean canJump(int[][] board, int player){
		boolean ans = false;
		int[][]X = null; // init a array 'X' 
		X = getAllBasicJumps(board,player); // X is array of jumps that can do 
		if(X.length != 0) //this say that has no jumps to do
			ans = true;			
		return ans;
	}

		
	public static boolean isMoveValid(int[][] board, int player, int fromRow, int fromCol, int toRow, int toCol) {
		boolean ans = false;
		if(isBasicMoveValid(board,player,fromRow,fromCol,toRow,toCol)){ // simple move
			if(getAllBasicJumps(board,player).length == 0) // has no jumps that must to do before
				ans=true; // valid move
		}
		if(isBasicJumpValid(board,player,fromRow,fromCol,toRow,toCol)){ //is not move so it is jump
			ans=true;
		}
		return ans;
	}
	
	public static boolean hasValidMoves(int[][] board, int player) {
		boolean ans = false;
		if(getAllBasicJumps(board,player).length != 0 || getAllBasicMoves(board,player).length != 0){ //has a jump or move to do
			ans=true;
		}
		return ans;
	}

	public static int[][] playMove(int[][] board, int player, int fromRow, int fromCol, int toRow, int toCol) {
		if(isBasicJumpValid(board,player,fromRow,fromCol,toRow,toCol)){
			board[(fromRow+toRow)/2][(fromCol+toCol)/2] = 0;// 'Mid section' will give --> the disc that was 'eaten' so now the cell is empty
		}
		if(board[fromRow][fromCol]==2) //was a queen before 
			board[toRow][toCol]=2;
		else{
			if(player==1){	//red
				if(toRow == 7) //found in row of queens
					board[toRow][toCol] = 2; //became to a queen
				else
					board[toRow][toCol] = 1; //simple move 
			}
		}
		if(board[fromRow][fromCol]==-2) //was a queen before 
			board[toRow][toCol]=-2;
		else{
			if(player==-1){ //blue		
		
				if(toRow == 0) //found in row of queens
					board[toRow][toCol]= -2; //became to a queen	
				else
					board[toRow][toCol]= -1; //simple move
			}
		}
		board[fromRow][fromCol] = 0; //the disc go from here so the place is empty
		return board;
	}


	public static boolean gameOver(int[][] board, int player) {
		boolean ans = false;
		if(numOfDiscs(board,player)==0 // number of discs of player is  0
			                |(!(hasValidMoves(board,player))))// has no more move/s that the player can do
			ans=true; // THE GAME IS OVER !
		return ans;
	}


	public static int findTheLeader(int[][] board) {
		int ans = 0; // situation that have a tie between them 
		int sumOfOne=0; // sum of "1"
		int sumOfMinusOne=0; // sum of "-1"
		for(int i=0;i<8;i++){ // run on -->
			for(int j=0;j<8;j++){  // --> the board
				if(board[i][j]==1)  // simple red disc
					sumOfOne++;
				if(board[i][j]==2)  // red queen
					sumOfOne=sumOfOne+2;
				if(board[i][j]==-1) // simple blue disc
					sumOfMinusOne++;
				if(board[i][j]==-2) // blue queen 
					sumOfMinusOne=sumOfMinusOne+2;
			}
		}
		if(sumOfMinusOne>sumOfOne){ // has more sumOfMinusOne from sumOfOne
			return -1;
		}
	    if(sumOfMinusOne<sumOfOne){ // has more sumOfOne from sumOfMinusOne
			return 1;
	    }
		return ans; 
	}
	
	public static int[][] randomPlayer(int[][] board, int player) {
		boolean wasDone=true;
		int[][]temp1 = getAllBasicJumps(board,player); //all jumps
		int[][]temp2 = getAllBasicMoves(board,player); //all moves
		int i=(int)(Math.random()*temp1.length ); // random number in the domain "[0,temp1.length-1]"
		int j=(int)(Math.random()*temp2.length ); // random number in the domain "[0,temp2.length-1]"
		if(hasValidMoves(board,player)){ //has more moves to do
			if(temp1.length != 0){ // has a jump that previous 
				int row=temp1[i][2];
				int col=temp1[i][3];		
				board=playMove(board,player,temp1[i][0],temp1[i][1],temp1[i][2],temp1[i][3]); // does a random 'jump' and update 'board'
					while(getRestrictedBasicJumps(board,player,row,col).length !=0){
						temp1=getRestrictedBasicJumps(board,player,row,col); //all jumps after last jump
						for(int m=0;m<temp1.length;m++){
							if(temp1[m][0]==row & temp1[m][1]==col & wasDone){ //check if has a continue jump
								board=playMove(board,player,temp1[m][0],temp1[m][1],temp1[m][2],temp1[m][3]); // does a random 'jump' and update 'board'
								if(wasDone){
									row=temp1[m][2];
									col=temp1[m][3];
									wasDone=false;
								}			
							}			
						}
						wasDone=true;
					}
			}
		}
			else
				board=playMove(board,player,temp2[j][0],temp2[j][1],temp2[j][2],temp2[j][3]); // does a random 'move' and update 'board'
		
		return board;
	}

		
	public static int[][] defensivePlayer(int[][] board, int player) {
		if(canJump(board,player)){ //has a jump
			board=randomPlayer(board,player); //does the jump into the function and update the board
		}
		else{
			if(getAllBasicMoves(board,player).length != 0){//has a moves to do 
				int[][]moves=getAllBasicMoves(board,player);//all moves
				int[]move=defendMove(moves,board,player);
				if((move[0]+move[1]+move[2]+move[3])!=0){//was a change in "defendMove"
					board=playMove(board,player,move[0],move[1],move[2],move[3]); //do the move and update the board
				}
				else{
					board=randomPlayer(board,player);//do random move
				}
			}
		}
		return board;
	}
	
	public static int[] defendMove(int[][]moves,int[][] board,int player) { //reference method
		int[]move=new int[4];
		int[][]temp=copyBoard(board); //build a copy of board
		for(int i=0;i<moves.length;i++){
			temp=playMove(temp,player,moves[i][0],moves[i][1],moves[i][2],moves[i][3]); //do the moves
			if(canJump(temp,1)==false){ //no has jumps to enemy after the move
				move[0]=moves[i][0];
				move[1]=moves[i][1];
				move[2]=moves[i][2];
				move[3]=moves[i][3];
				return move;
			}
			temp=copyBoard(board);//reset temp	
		}	
		return move;
	}
	
    public static int[][] copyBoard(int[][]board){//reference method
    int [][] copy=new int [8][8]; //build copy
    for(int i=0;i<8;i++){
    	for(int j=0;j<8;j++){
    		copy[i][j]=board[i][j]; //copy board
    	}
    }
    	return copy;
    }
    
	public static int[][] sidesPlayer(int[][] board, int player) {
			if(canJump(board,player)){ //has a jump
					board=randomPlayer(board,player); //does the jump into the function and update the board
		}
		else{
			if(getAllBasicMoves(board,player).length != 0){//has a moves to do 
			int[][]moves = getAllBasicMoves(board,player); //all moves
			int[]moveDo = closeToWall(moves); // the move that most closet to wall was insert to moveDo
			board=playMove(board,player,moveDo[0],moveDo[1],moveDo[2],moveDo[3]); //do the move and update the board
			}
		}
		return board;
	}
                           
	public static int[] closeToWall(int[][]moves) { //reference method 
		int[] output = new int [4]; //array that [0]-->Row ,[1]-->Column of the place they was and [2],[3] is the destination place
		int[] Col = new int [moves.length];
		int pointer1=0;
		int pointer2=0;
		for(int i=0;i<moves.length; i++) //put the information of column in array []
				Col[i]=moves[i][3];
		int max=Col[0];
		int min=Col[0];
		for(int i=1;i<Col.length;i++){ //find the maximum in array
			if(max<Col[i]){
				max=Col[i];
				pointer1=i;
			}
		}
		for(int i=1;i<Col.length;i++){ //find the minimum in array
			if(min>Col[i]){
				min=Col[i];
				pointer2=i;
			}
		}
		if(max==7){   // from here and down its insert the correctly values to output   
			output[0]=moves[pointer1][0];
			output[1]=moves[pointer1][1];
			output[2]=moves[pointer1][2];
			output[3]=max;
			return output;
		}
		if(min==0){
			output[0]=moves[pointer2][0];
			output[1]=moves[pointer2][1];
			output[2]=moves[pointer2][2];
			output[3]=min;
			return output;
		}
		if(max==6){
			output[0]=moves[pointer1][0];
			output[1]=moves[pointer1][1];
			output[2]=moves[pointer1][2];
			output[3]=max;
			return output;
		}
		if(min==1){
			output[0]=moves[pointer2][0];
			output[1]=moves[pointer2][1];
			output[2]=moves[pointer2][2];
			output[3]=min;
			return output;
		}
		if(max==5){
			output[0]=moves[pointer1][0];
			output[1]=moves[pointer1][1];
			output[2]=moves[pointer1][2];
			output[3]=max;
			return output;
		}
		if(min==2){
			output[0]=moves[pointer2][0];
			output[1]=moves[pointer2][1];
			output[2]=moves[pointer2][2];
			output[3]=min;
			return output;
		}
		if(max==4){
			output[0]=moves[pointer1][0];
			output[1]=moves[pointer1][1];
			output[2]=moves[pointer1][2];
			output[3]=max;
			return output;
		}
		if(min==3){
			output[0]=moves[pointer2][0];
			output[1]=moves[pointer2][1];
			output[2]=moves[pointer2][2];
			output[3]=min;
			return output;
		}
		
		return output;
	}


	
	
	
	
	//******************************************************************************//

	/* ---------------------------------------------------------- *
	 * Play an interactive game between the computer and you      *
	 * ---------------------------------------------------------- */
	public static void interactivePlay() {
		int[][] board = createBoard();
		showBoard(board);

		System.out.println("Welcome to the interactive Checkers Game !");

		int strategy = getStrategyChoice();
		System.out.println("You are the first player (RED discs)");

		boolean oppGameOver = false;
		while (!gameOver(board, RED) && !oppGameOver) {
			board = getPlayerFullMove(board, RED);

			oppGameOver = gameOver(board, BLUE);
			if (!oppGameOver) {
				EnglishCheckersGUI.sleep(200);

				board = getStrategyFullMove(board, BLUE, strategy);
			}
		}

		int winner = 0;
		if (playerDiscs(board, RED).length == 0  |  playerDiscs(board, BLUE).length == 0){
			winner = findTheLeader(board);
		}

		if (winner == RED) {
			System.out.println();
			System.out.println("\t *************************");
			System.out.println("\t * You are the winner !! *");
			System.out.println("\t *************************");
		}
		else if (winner == BLUE) {
			System.out.println("\n======= You lost :( =======");
		}
		else
			System.out.println("\n======= DRAW =======");
	}


	/* --------------------------------------------------------- *
	 * A game between two players                                *
	 * --------------------------------------------------------- */
	public static void twoPlayers() {
		int[][] board = createBoard();
		showBoard(board);

		System.out.println("Welcome to the 2-player Checkers Game !");

		boolean oppGameOver = false;
		while (!gameOver(board, RED)  &  !oppGameOver) {
			System.out.println("\nRED's turn");
			board = getPlayerFullMove(board, RED);

			oppGameOver = gameOver(board, BLUE);
			if (!oppGameOver) {
				System.out.println("\nBLUE's turn");
				board = getPlayerFullMove(board, BLUE);
			}
		}

		int winner = 0;
		if (playerDiscs(board, RED).length == 0  |  playerDiscs(board, BLUE).length == 0)
			winner = findTheLeader(board);

		System.out.println();
		System.out.println("\t ************************************");
		if (winner == RED)
			System.out.println("\t * The red player is the winner !!  *");
		else if (winner == BLUE)
			System.out.println("\t * The blue player is the winner !! *");
		else
			System.out.println("\t * DRAW !! *");
		System.out.println("\t ************************************");
	}


	/* --------------------------------------------------------- *
	 * Get a complete (possibly a sequence of jumps) move        *
	 * from a human player.                                      *
	 * --------------------------------------------------------- */
	public static int[][] getPlayerFullMove(int[][] board, int player) {
		// Get first move/jump
		int fromRow = -1, fromCol = -1, toRow = -1, toCol = -1;
		boolean jumpingMove = canJump(board, player);
		boolean badMove   = true;
		getPlayerFullMoveScanner = new Scanner(System.in);//I've modified it
		while (badMove) {
			if (player == 1){
				System.out.println("Red, Please play:");
			} else {
				System.out.println("Blue, Please play:");
			}

			fromRow = getPlayerFullMoveScanner.nextInt();
			fromCol = getPlayerFullMoveScanner.nextInt();

			int[][] moves = jumpingMove ? getAllBasicJumps(board, player) : getAllBasicMoves(board, player);
			markPossibleMoves(board, moves, fromRow, fromCol, MARK);
			toRow   = getPlayerFullMoveScanner.nextInt();
			toCol   = getPlayerFullMoveScanner.nextInt();
			markPossibleMoves(board, moves, fromRow, fromCol, EMPTY);

			badMove = !isMoveValid(board, player, fromRow, fromCol, toRow, toCol); 
			if (badMove)
				System.out.println("\nThis is an illegal move");
		}

		// Apply move/jump
		board = playMove(board, player, fromRow, fromCol, toRow, toCol);
		showBoard(board);

		// Get extra jumps
		if (jumpingMove) {
			boolean longMove = (getRestrictedBasicJumps(board, player, toRow, toCol).length > 0);
			while (longMove) {
				fromRow = toRow;
				fromCol = toCol;

				int[][] moves = getRestrictedBasicJumps(board, player, fromRow, fromCol);

				boolean badExtraMove = true;
				while (badExtraMove) {
					markPossibleMoves(board, moves, fromRow, fromCol, MARK);
					System.out.println("Continue jump:");
					toRow = getPlayerFullMoveScanner.nextInt();
					toCol = getPlayerFullMoveScanner.nextInt();
					markPossibleMoves(board, moves, fromRow, fromCol, EMPTY);

					badExtraMove = !isMoveValid(board, player, fromRow, fromCol, toRow, toCol); 
					if (badExtraMove)
						System.out.println("\nThis is an illegal jump destination :(");
				}

				// Apply extra jump
				board = playMove(board, player, fromRow, fromCol, toRow, toCol);
				showBoard(board);

				longMove = (getRestrictedBasicJumps(board, player, toRow, toCol).length > 0);
			}
		}
		return board;
	}


	/* --------------------------------------------------------- *
	 * Get a complete (possibly a sequence of jumps) move        *
	 * from a strategy.                                          *
	 * --------------------------------------------------------- */
	public static int[][] getStrategyFullMove(int[][] board, int player, int strategy) {
		if (strategy == RANDOM)
			board = randomPlayer(board, player);
		else if (strategy == DEFENSIVE)
			board = defensivePlayer(board, player);
		else if (strategy == SIDES)
			board = sidesPlayer(board, player);

		showBoard(board);
		return board;
	}


	/* --------------------------------------------------------- *
	 * Get a strategy choice before the game.                    *
	 * --------------------------------------------------------- */
	public static int getStrategyChoice() {
		int strategy = -1;
		getStrategyScanner = new Scanner(System.in);
		System.out.println("Choose the strategy of your opponent:" +
				"\n\t(" + RANDOM + ") - Random player" +
				"\n\t(" + DEFENSIVE + ") - Defensive player" +
				"\n\t(" + SIDES + ") - To-the-Sides player player");
		while (strategy != RANDOM  &  strategy != DEFENSIVE
				&  strategy != SIDES) {
			strategy=getStrategyScanner.nextInt();
		}
		return strategy;
	}


	/* --------------------------------------- *
	 * Print the possible moves                *
	 * --------------------------------------- */
	public static void printMoves(int[][] possibleMoves) {
		for (int i = 0;  i < 4;  i = i+1) {
			for (int j = 0;  j < possibleMoves.length;  j = j+1)
				System.out.print(" " + possibleMoves[j][i]);
			System.out.println();
		}
	}


	/* --------------------------------------- *
	 * Mark/unmark the possible moves          *
	 * --------------------------------------- */
	public static void markPossibleMoves(int[][] board, int[][] moves, int fromRow, int fromColumn, int value) {
		for (int i = 0;  i < moves.length;  i = i+1)
			if (moves[i][0] == fromRow  &  moves[i][1] == fromColumn)
				board[moves[i][2]][moves[i][3]] = value;

		showBoard(board);
	}


	/* --------------------------------------------------------------------------- *
	 * Shows the board in a graphic window                                         *
	 * you can use it without understanding how it works.                          *                                                     
	 * --------------------------------------------------------------------------- */
	public static void showBoard(int[][] board) {
		grid.showBoard(board);
	}


	/* --------------------------------------------------------------------------- *
	 * Print the board              					                           *
	 * you can use it without understanding how it works.                          *                                                     
	 * --------------------------------------------------------------------------- */
	public static void printMatrix(int[][] matrix){
		for (int i = matrix.length-1; i >= 0; i = i-1){
			for (int j = 0; j < matrix.length; j = j+1){
				System.out.format("%4d", matrix[i][j]);
			}
			System.out.println();
		}
	}

}

