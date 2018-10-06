package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Ship> ships;// holds the locations on the board the is present at
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		// TODO Implement
		this.ships= new ArrayList<Ship>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		int shipLength =ship.getLength(); // store length in var for easier access
		if (isVertical) { // test cases if the ship is vertical
			if (x + (shipLength) > 11 || x < 1)
				return false; // ship cant go over the edge of board
			else {
				if (y >= 75 || y < 65) {
					return false; // ships can't go over edge of board 74 == J
				}
				for (int i =0; i<this.ships.size();i++){// this loop ensure a ships isn't placed over a different one
				    for (int j=0;j<this.ships.get(i).getLength();j++){
				        for(int k=0;k<shipLength;k++){
				            if (x+k==this.ships.get(i).getOccupiedSquares().get(j).getRow()){
								if (y==this.ships.get(i).getOccupiedSquares().get(j).getColumn()){
									return false;
								}
                            }
                        }
                    }
                }
				ship.setOccupiedSquares(x, y, isVertical); // ship passed so add it's squares to its list and added it to the boards list
				this.ships.add(ship);
				return true;
			}
		}
		else {// the following works the same as the above code but if the ship is horizontal
			if (y + (shipLength) > 75 || y < 65) {
				return false;
			} else {
				if (x >= 10 || x < 1) {
					return false;
				}
				for (int i =0; i<this.ships.size();i++){
					for (int j=0;j<this.ships.get(i).getLength();j++){
						for(int k=0;k<shipLength;k++){
							if (y+k==this.ships.get(i).getOccupiedSquares().get(j).getColumn()){
								if (x==this.ships.get(i).getOccupiedSquares().get(j).getRow()){
									return false;
								}
							}
						}
					}
				}
				ship.setOccupiedSquares(x, y, isVertical);
				this.ships.add(ship);
				return true;
			}
		}
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		//TODO Implement
		return null;
	}

	public List<Ship> getShips() {
		return this.ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships=ships;
	}

	public List<Result> getAttacks() {
		//TODO implement
		return null;
	}

	public void setAttacks(List<Result> attacks) {
		//TODO implement
	}
}
