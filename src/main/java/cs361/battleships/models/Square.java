package cs361.battleships.models;
@SuppressWarnings("unused")

/*
In following SRP, this class functions more like the physical index of the grid, and the Result class functions
more like the actual contents of that grid location.
 */

public class Square {

	private int row;
	private char column;

	// DEFAULT CONSTRUCTOR
	public Square(){}

	// CONSTRUCTOR
	public Square(int row, char column) {
		this.row = row;
		this.column = column;
	}

	// GETTERS
	public char getColumn() {
		return column;
	}
	public int getRow() {
		return row;
	}

	// SETTERS
	public void setColumn(char column) {
		this.column = column;
	}
	public void setRow(int row) {
		this.row = row;
	}
}
