package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	private String kind;
	private int length;
	
	public Ship(String kind) {
	    this.occupiedSquares= new ArrayList<Square>();
		this.kind=kind;
		if (kind == "DESTROYER"){
			this.occupiedSquares.add(new Square(0, 'A'));
			this.occupiedSquares.add(new Square(0, 'A'));
			this.occupiedSquares.add(new Square(0, 'A'));
			this.length=3;
		}
		else if (kind == "BATTLESHIP"){
            this.occupiedSquares.add(new Square(0, 'A'));
            this.occupiedSquares.add(new Square(0, 'A'));
            this.occupiedSquares.add(new Square(0, 'A'));
            this.occupiedSquares.add(new Square(0, 'A'));
			this.length=4;
		}
		else{
			this.occupiedSquares.add(new Square(0, 'A'));
			this.occupiedSquares.add(new Square(0, 'A'));
			this.length=2;
		}
	}

	public List<Square> getOccupiedSquares() {
		return this.occupiedSquares;
	}

	public void setOccupiedSquares(int row, char column, boolean vertical){
	    String col= "ABCDEFGHIJ";
		int c=0;
		if (vertical){
			for (int i=0; i<this.length; i++){
				this.occupiedSquares.set(i,new Square (row+i,column));
			}
		}
		else {
			for (int i=0; i<col.length(); i++){
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
