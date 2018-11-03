package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/*
Ships follows SRP by only conducting responsibilities of a ship. It only constructs a ship and holds/returns basic
data regarding this ship.

With this format, Open-Closed Principle (OCP) is also followed, because it is very easy to add another ship, perhaps
an aircraft carrier of length 5 or a submarine of length 3. Code does not need to be modified, rather, a new Class will
simply be added which inherits from Ship.

Battleship, Destroyer, and Minesweeper classes inherit from Ship class. By Liskov Substitution Principle (LSP), each
subclass follows its "contract" to the parent class; all methods and data types of the Ship class apply to the child
classes.

By only writing functions and methods that are currently necessary, Interface Segregation Principle (ISP) has been
applied. Additional methods, data types, and classes have not been written in prediction of a future addition.

In this case, the ship class is an abstraction. We took the shared functionality from each of the three classes and
abstracted it into this Ship class, following the Dependency Inversion Principle (DIP).

NOTE: It is not a TRUE abstraction, however, because of how the Ninja Framework interacts with our objects.
 */

public class Ship {

	@JsonProperty protected List<Square> occupiedSquares; // an array of locations that the ship occupies
	protected String kind; // ship type
	protected int length; // number of spaces ship will occupy
	protected Square captainsQuarters; // The square that the captains quarter is on
	protected int armor; // How much armor the captains quarter has

	// CONSTRUCTOR
	public Ship() {
		occupiedSquares = new ArrayList<>();
	}

	// GETTERS
	public List<Square> getOccupiedSquares() {
		return this.occupiedSquares;
	}
	public int getLength(){
		return this.length;
	}
	public String getKind(){
		return this.kind;
	}

	// SETTERS
	public void setOccupiedSquares(int row, char column, boolean vertical){

	    String col= "ABCDEFGHIJ"; // makes a string of characters to get cols
		int c=0; // used for index of cols

		if (vertical){ // iterates rows up for length of ship if ship is vertical
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

	// Overridden by the child classes
	public void setCaptainsQuarters(int x, char y, boolean b) {}

	public Square getCaptainsQuarters() {
		return this.captainsQuarters;
	}

	public void hitCaptainsQuarters() {
		this.armor--;
	}

	// If it has armor, it is not sunk yet
	public boolean sunkCaptainsQuarters() {
		if (this.armor > 0) {
			return false;
		}
		return true;
	}
}
