package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty protected List<Square> occupiedSquares;// holds the locations on the board the is present at
	protected String kind; // holds the kind of ship that is present
	protected int length;// holds the number of spaces the ship takes up

	public Ship() {
		occupiedSquares = new ArrayList<>();
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
