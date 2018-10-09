package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;// holds the locations on the board the is present at
	private String kind; // holds the kind of ship that is present
	private int length;// holds the number of spaces the ship takes up

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
	    this.occupiedSquares= new ArrayList<Square>();// constructs list
		this.kind=kind;// sets kind equal to that passed in
		if (kind.equals("DESTROYER")){// ensures destroyer has 3 square
			this.occupiedSquares.add(new Square(0, 'A'));
			this.occupiedSquares.add(new Square(0, 'A'));
			this.occupiedSquares.add(new Square(0, 'A'));
			this.length=3;// sets length to 3
		}
		else if (kind.equals("BATTLESHIP")){// makes sure battle ship has 4 squares
            this.occupiedSquares.add(new Square(0, 'A'));
            this.occupiedSquares.add(new Square(0, 'A'));
            this.occupiedSquares.add(new Square(0, 'A'));
            this.occupiedSquares.add(new Square(0, 'A'));
			this.length=4;
		}
		else{// if kind wasn't specified make MINESWEEPER or If kind was MINESWEEPER, has 2 squares
			this.occupiedSquares.add(new Square(0, 'A'));
			this.occupiedSquares.add(new Square(0, 'A'));
			this.length=2;
		}
	}

	public List<Square> getOccupiedSquares() {
		return this.occupiedSquares;
	}

	public void setOccupiedSquares(int row, char column, boolean vertical){
	    String col= "ABCDEFGHIJ";// makes a string of characters to get cols
		int c=0;// used for index of cols
		if (vertical){// iterates rows up for length of ship if ship is vertical
			for (int i=0; i<this.length; i++){
				this.occupiedSquares.set(i,new Square (row+i,column));
			}
		}
		else {// iterates up col for length of ship if ship is horizontal
			for (int i=0; i<col.length(); i++){//selects the right index to use for col
				if (col.charAt(i)== column){
					c=i;
					break;
				}
			}

			for (int i=0; i<this.length; i++){
				this.occupiedSquares.set(i, new Square (row,col.charAt(i+c)));
			}
		}
	}

	public int getLength(){
		return this.length;
	}

	public String getKind(){
		return this.kind;
	}
}
