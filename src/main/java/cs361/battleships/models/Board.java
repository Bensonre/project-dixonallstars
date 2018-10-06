package cs361.battleships.models;

import controllers.AttackGameAction;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Ship> ships;// holds the locations on the board the is present at
	private List<Result> attacks;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		this.attacks = new ArrayList<Result>();
		this.ships= new ArrayList<Ship>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		int shipLength =ship.getLength(); // store length in var for easier access
		if (isVertical) { // test cases if the ship is vertical
			if (x + (shipLength) > 11 || x < 1)
				return false; // ensures ship cant go over the edge of board
			else {
				if (y >= 'K' || y < 'A') {
					return false; // ships can't go over edge of board 74 == J
				}
				for (int i =0; i<this.ships.size();i++){// this loop ensure a ships isn't placed over a different one
					//i represent the ship being acessed in ships list, the loop itterates through the number of ships
				    for (int j=0;j<this.ships.get(i).getLength();j++){//j represents the the square being checked in ship i's square list
				        for(int k=0;k<shipLength;k++){// k represents the squares that the newly placed ships will take
				            if (x+k==this.ships.get(i).getOccupiedSquares().get(j).getRow()){// tests to see if the ship is in the same row as the ith ship
								if (y==this.ships.get(i).getOccupiedSquares().get(j).getColumn()){// tests to see if the ship is in the same col as the ith ship
									return false;// if both the above cases are true the ship is in the same square as the ith ship and can't be placed.
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
			if (y + (shipLength) > 'K' || y < 'A') {
				return false;
			} else {
				if (x > 10 || x < 1) {
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
		Result res = new Result();

		if (y > 'J' || y < 'A' || x > 10 || x < 0){ // Invalid if attack is off the board.
			res.setResult(AtackStatus.INVALID);
			return res;
		}

		if (previouslyAttacked(x,y)){ // Invalid if the attack has already been attempted.
			res.setResult(AtackStatus.INVALID);
			return res;
		}

		if (!shipOnSpot(x,y)){ // If not a ship on the spot, record a miss.
			res.setResult(AtackStatus.MISS);
			res.setLocation(new Square(x,y));

			List<Result> attacks = getAttacks();
			attacks.add(res);
			setAttacks(attacks);

			return res;
		}

		return null;
	}

	public List<Ship> getShips() {
		return this.ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships=ships;
	}

	public List<Result> getAttacks() {
		return attacks;
	}

	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
	}

	public boolean previouslyAttacked(int x, char y){
		List<Result> attacks = getAttacks();
		for (int i = 0; i < attacks.size(); i++) { // For all previous attacks
			Square loc = attacks.get(i).getLocation(); // Get location
			if (loc != null){
				if (loc.getRow() == x && loc.getColumn() == y){ // If that is the location we are trying to attack return true.
					return true;
				}
			}
		}
		return false;
	}

	public boolean shipOnSpot(int x, char y){
		List<Ship> ships = getShips();
		for (int i = 0; i < ships.size(); i++){ // For all ships
			List<Square> occupiedSquares = ships.get(i).getOccupiedSquares();  // Get ships squares
			for (int j = 0; j < occupiedSquares.size(); j++){ // For each square
				Square loc = occupiedSquares.get(i);
				if (loc != null) {
					if (loc.getRow() == x && loc.getColumn() == y) { // If that is the location we are trying to attack return true.
						return true;
					}
				}
			}
		}
		return false;
	}

}
