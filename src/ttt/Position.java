package ttt;

import java.util.LinkedList;

public class Position {
	
	public char[] board;
	public char currentPlayer;
	public int dim = 3;
	
	public Position() {
		this.board = "         ".toCharArray();
		this.currentPlayer = 'x';
	}
	
	public Position(char[] board, char currentPlayer) {
		this.board = board;
		this.currentPlayer = currentPlayer;
	}
	
	public Position(String string) {
		this.board = string.toCharArray();
		this.currentPlayer = 'x';
	}

	public Position(String string, char turn) {
		this.board = string.toCharArray();
		this.currentPlayer = turn;
	}

	public String toString() {
		return new String(board);
	}
	
	public Position move(int ind) {
		char[] newBoard = board.clone();
		newBoard[ind] = currentPlayer;
		return new Position(newBoard, currentPlayer == 'x' ? 'o' : 'x');
	}

	public Integer[] possibleMoves() {
		LinkedList<Integer> list = new LinkedList<Integer>();
		for(int i=0; i<board.length; i++) {
			if(board[i] == ' ')
				list.add(i);
		}
		Integer[] array = new Integer[list.size()];
		list.toArray(array);
		return array;
	}

	public boolean win(char turn) {
		for(int i=0; i<dim; i++) {
			/* if we a winner in a row or column */
			if(winLine(turn,i*dim,1) || winLine(turn,i,dim)) {
				return true;
			}
		}
		/* if we have a winner in a diagonal */
		if(winLine(turn,dim-1,dim-1) || winLine(turn,0,dim+1))
			return true;
		
		return false;
	}
	
	private boolean winLine(char turn, int start, int step) {
		for(int i=0; i<dim; i++) {
			if(board[start + step*i] != turn) {
				return false;
			}
		}
		return true;
	}

	public int minimax() {
		if (win('x')) { return 100; }
		if (win('o')) { return -100; }
		if (possibleMoves().length == 0) { return 0; }
		Integer mm = null;
		for(Integer idx:possibleMoves()) {
			Integer value = move(idx).minimax();
			if(mm == null || (currentPlayer == 'x' && mm <value) || (currentPlayer == 'o' && value < mm))
				mm = value;
		}
		return mm + (currentPlayer == 'x' ? -1 : 1);
	}

	public int bestMove() {
		Integer mm = null;
		int best = -1;
		for (Integer idx : possibleMoves()) {
			Integer value = move(idx).minimax();
			if(mm == null || (currentPlayer == 'x' && mm <value) || (currentPlayer == 'o' && value < mm)) {
				mm = value;
				best = idx;
			}
		}
		return best;
	}

	public boolean gameEnd() {
		return win('x') || win('o') || possibleMoves().length == 0;
	}
}