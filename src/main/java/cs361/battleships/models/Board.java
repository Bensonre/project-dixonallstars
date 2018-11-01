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
		this.ships = new ArrayList<Ship>();
	}

	//returns weather or not a ship of this type has been placed on the board
	public boolean alreadyPlaced(Ship ship) {
		for (int i = 0; i < this.ships.size(); i++) {
			if (this.ships.get(i).getKind().equals(ship.getKind()))
				return true;
		}
		return false;
	}


	public boolean placeVertical(Ship ship, int shipLength, int x, char y, boolean isVertical) {
		if (x + (shipLength) > 11 || x < 1)
			return false; // ensures ship cant go over the edge of board
		if (y >= 'K' || y < 'A') {
			return false; // ships can't go over edge of board 74 == J
		}
		List<Ship> shipsList = this.ships;
		for (int i = 0; i < shipsList.size(); i++) {// this loop ensure a ships isn't placed over a different one
			//currentShip is the ship being acessed in ships list, the loop itterates through the number of ships
			 Ship currentShip = shipsList.get(i);
			 for (int j = 0; j < currentShip.getLength(); j++) {//j represents the the square being checked in current ships square list
			 	for (int k = 0; k < shipLength; k++) {// k represents the squares that the newly placed ships will take
			 		if (x + k == currentShip.getOccupiedSquares().get(j).getRow()) {// tests to see if the ship being placed is in the same row as the current ship
			 			if (y == currentShip.getOccupiedSquares().get(j).getColumn()) {// tests to see if the ship being [laced is in the same col as the current ship
			 				return false;// if both the above cases are true the ship is in the same square as the current ship and can't be placed.
							}
						}
					}
				}
			}
			ship.setOccupiedSquares(x, y, isVertical); // ship passed so add it's squares to its list and added it to the boards list
			this.ships.add(ship);
			return true;
	}

	public boolean placeHorizontal(Ship ship, int shipLength, int x, char y, boolean isVertical) {
		if (y + (shipLength) > 'K' || y < 'A') {
			return false;
		}
		if (x > 10 || x < 1) {
			return false;
		}

		List<Ship> shipsList = this.ships;
		for (int i = 0; i < shipsList.size(); i++) {// this loop ensure a ships isn't placed over a different one
			// currentship represents the ship being acessed in ships list, the loop itterates through the number of ships
			Ship currentShip = shipsList.get(i);
			for (int j = 0; j < currentShip.getLength(); j++) {//j represents the the square being checked in current ships square list
				for (int k = 0; k < shipLength; k++) {// k represents the squares that the newly placed ships will take
					if (y + k == currentShip.getOccupiedSquares().get(j).getColumn()) {// tests to see if the ship being placed is in the same row as the current ship
						if (x == currentShip.getOccupiedSquares().get(j).getRow()) {// tests to see if the ship being placed is in the same col as the current ship
							return false;
						}
					}
				}
			}
		}
			ship.setOccupiedSquares(x, y, isVertical);// ship passed so add it's squares to its list and added it to the boards list
			this.ships.add(ship);
			return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		if (alreadyPlaced(ship))
			return false;
		int shipLength = ship.getLength(); // store length in var for easier access
		if (isVertical) { // test cases if the ship is vertical
			return (placeVertical(ship, shipLength, x, y, isVertical));
		}
			return (placeHorizontal(ship, shipLength, x, y, isVertical));

	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */

	// Returns the result object of the attack AND adds it to the boards attacks.
	public Result attack(int x, char y) {
		Result res = new Result();

		if (y > 'J' || y < 'A' || x > 10 || x <= 0) { // Invalid if attack is off the board.
			res.setResult(AtackStatus.INVALID);
			return res;
		}

		if (previouslyAttacked(x, y)) { // Invalid if the attack has already been attempted.
			res.setResult(AtackStatus.INVALID);
			return res;
		}

		if (!shipOnSpot(x, y)) { // If not a ship on the spot, record a miss.
			res.setResult(AtackStatus.MISS);
			res.setLocation(new Square(x, y));

			List<Result> attacks = getAttacks();
			attacks.add(res);
			setAttacks(attacks);

			return res;
		}

		Ship attackedShip = hitShip(x, y);  // Get the ship that is being hit

		res.setResult(AtackStatus.HIT); // Make a hit result
		res.setLocation(new Square(x, y));
		res.setShip(attackedShip);

		List<Result> attacks = getAttacks(); // Set the attack as a hit on the board
		attacks.add(res);
		setAttacks(attacks);

		if (sunkShip(attackedShip)) {  // If the ship has been sunk
			attacks.remove(res);  // Remove the HIT from the board and replace it with SUNK
			res.setResult(AtackStatus.SUNK);
			attacks.add(res);
			setAttacks(attacks);
		}


		if (gameOver()) { //if ship has been sunk
			attacks.remove(res);  // Remove the HIT from the board and replace it with SUNK
			res.setResult(AtackStatus.SURRENDER);
			attacks.add(res);
			setAttacks(attacks);
		}


		return res;
	}


	public List<Ship> getShips() {
		return this.ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}

	public List<Result> getAttacks() {
		return attacks;
	}

	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
	}

	// True if a coordinate has been attacked before.
	public boolean previouslyAttacked(int x, char y) {
		List<Result> attacks = getAttacks();
		for (int i = 0; i < attacks.size(); i++) { // For all previous attacks
			Square loc = attacks.get(i).getLocation(); // Get location
			if (loc != null) {
				if (loc.getRow() == x && loc.getColumn() == y) { // If that is the location we are trying to attack return true.
					return true;
				}
			}
		}
		return false;
	}

	// True if there is a ship on that coordinate
	public boolean shipOnSpot(int x, char y) {
		for (int i = 0; i < ships.size(); i++) { // For all ships
			List<Square> occupiedSquares = ships.get(i).getOccupiedSquares();  // Get ships squares
			for (int j = 0; j < occupiedSquares.size(); j++) { // For each square
				Square loc = occupiedSquares.get(j);
				if (loc != null) {
					if (loc.getRow() == x && loc.getColumn() == y) { // If that is the location we are trying to attack return true.
						return true;
					}
				}
			}
		}
		return false;
	}

	// Returns the ship being hit at that coordinate
	public Ship hitShip(int x, char y) {
		for (int i = 0; i < ships.size(); i++) { // For all ships
			List<Square> occupiedSquares = ships.get(i).getOccupiedSquares();  // Get ships squares
			for (int j = 0; j < occupiedSquares.size(); j++) { // For each square
				Square loc = occupiedSquares.get(j);
				if (loc != null) {
					if (loc.getRow() == x && loc.getColumn() == y) { // If that is the location we are trying to attack return the ship.
						return ships.get(i);
					}
				}
			}
		}
		return null;
	}

	// True if the ship is sunk
	public boolean sunkShip(Ship ship) {
		List<Square> occupiedSquares = ship.getOccupiedSquares(); //
		for (int i = 0; i < occupiedSquares.size(); i++) {  // For all of the ships squares
			boolean attacked = false;
			Square loc = occupiedSquares.get(i);
			int row = loc.getRow();
			char col = loc.getColumn();

			for (int j = 0; j < attacks.size(); j++) {  // For all of the attacked squares
				Square attacked_loc = attacks.get(j).getLocation();
				if (attacked_loc != null) {
					if (row == attacked_loc.getRow() && col == attacked_loc.getColumn()) {  // If that location is the same as the ship
						if (attacks.get(j).getResult() == AtackStatus.HIT || attacks.get(j).getResult() == AtackStatus.SUNK) {
							attacked = true;  // Mark the ship as attacked
						}
					}
				}
			}
			if (!attacked) {  // Return false if this spot on ship has not been attacked.
				return false;
			}
		}
		return true;
	}

	//returns true if 3 ships are sunk
	boolean gameOver() {
		int sunkShips = 0;
		for (int i = 0; i < attacks.size(); i++) { //for all results
			if (attacks.get(i).getResult() == AtackStatus.SUNK) { //if a result is sunk, add to sunkShips
				sunkShips++;
			}
		}
		if (sunkShips >= 3){
			return true;
		}
		return false;
	}

}