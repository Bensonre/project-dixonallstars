package cs361.battleships.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
Follows SRP by only performing responsibilities logically connected to a physical board.
This includes: the creating of the ships array, the creating of the results array, the placing of ships,
			   the attacking of ships, and several checks regarding the status of the BOARD.

All actions have a logical equivalent with a game of battleship being played IRL.
 */

public class Board {

	private List<Ship> ships; // holds the locations on the board the is present at
	private List<Result> attacks;
	private boolean enemyHasLazer;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		this.attacks = new ArrayList<Result>();
		this.ships = new ArrayList<Ship>();
	}

	// function takes in u, d, l, or r from the front end user input
	public void moveFleet(char direction){
		boolean sorted = false;
		boolean moveShipSuccess;

		// sort ships array in TOP-ORDER
		// loop through while sorted = false. if any swap happens, change sorted to false and run loop again
		if(direction == 'u' || direction == 'd') {
			while (sorted == false) {
				sorted = true;

				// start at the 2nd element
				for (int i = 1; i < ships.size(); i++) {
					// first square of each occupiedSquares List will always be top-most
					// swap if previous ship in list is of higher row
					if (this.ships.get(i).getOccupiedSquares().get(0).getRow() < this.ships.get(i - 1).getOccupiedSquares().get(0).getRow()) {
						// will it blend?
						Collections.swap(ships, i, i - 1);
						sorted = false;
					}
				}
			}
		}

		// sort ships array in LEFT-ORDER
		// loop through while sorted = false. if any swap happens, change sorted to false and run loop again
		else if(direction == 'l' || direction == 'r') {
			while (sorted == false) {
				sorted = true;

				// start at the 2nd element
				for (int i = 1; i < ships.size(); i++) {
					// first square of each occupiedSquares List will always be left-most
					// swap if previous ship in list is of higher row
					if (this.ships.get(i).getOccupiedSquares().get(0).getColumn() < this.ships.get(i - 1).getOccupiedSquares().get(0).getColumn()) {
						// will it blend?
						Collections.swap(ships, i, i - 1);
						sorted = false;
					}
				}
			}
		}

		// before entering this switcher statement, ships array is sorted in top-order/left-order
		if(direction == 'u'){
			for(int i = 0; i < ships.size(); i++){
				moveShipSuccess = moveShipUp(ships.get(i));
			}
		}
		else if(direction == 'd'){
			for(int i = (ships.size() - 1); i >= 0; i--){
				moveShipSuccess = moveShipDown(ships.get(i));
			}
		}
		else if(direction == 'l'){
			for(int i = 0; i < ships.size(); i++){
				moveShipSuccess = moveShipLeft(ships.get(i));
			}
		}
		else if(direction == 'r'){
			for(int i = (ships.size() - 1); i >= 0; i--){
				moveShipSuccess = moveShipRight(ships.get(i));
			}
		}
	}

	public boolean moveShipUp(Ship ship){
		List <Square> testSquares = ship.getOccupiedSquares();
        List <Result> hits = ship.getHitSquares();

		// loop through each square, checking if square can be moved up
		for(int i=0; i < testSquares.size(); i++) {

			// check for off board
			if (testSquares.get(i).getRow()-1 < 1)
				return false;
			// check for non-submerged ship occupying possible new space
			if (shipWillCollide(testSquares.get(i).getRow()-1, testSquares.get(i).getColumn(), ship))
				return false;
			// move not possible if ship is sunk
            if(ship.sunkCaptainsQuarters() == true)
                return false;
		}

		// if function has progressed to this point, move was valid, move all positions
		for (int i=0; i < ship.length; i++) {
            ship.occupiedSquares.set(i, new Square(testSquares.get(i).getRow() - 1, testSquares.get(i).getColumn()));
        }
        for (int i=0; i < hits.size(); i++) {
            hits.get(i).setLocation(new Square(hits.get(i).getLocation().getRow()-1,hits.get(i).getLocation().getColumn()));
        }
        	ship.getCaptainsQuarters().setRow(ship.getCaptainsQuarters().getRow()-1);
		return true;
	}

	public boolean moveShipDown(Ship ship){
		List <Square> testSquares = ship.getOccupiedSquares();
        List <Result> hits = ship.getHitSquares();

		// loop through each square, checking if square can be moved down
		for(int i=0; i < testSquares.size(); i++) {

			// check for off board
			if (testSquares.get(i).getRow()+1 > 10)
				return false;
			// check for non-submerged ship occupying possible new space
			if (shipWillCollide(testSquares.get(i).getRow()+1, testSquares.get(i).getColumn(), ship))
				return false;
            // move not possible if ship is sunk
            if(ship.sunkCaptainsQuarters() == true)
                return false;
		}

        // if function has progressed to this point, move was valid, move all positions
        for (int i=0; i < ship.length; i++) {
            ship.occupiedSquares.set(i, new Square(testSquares.get(i).getRow() + 1, testSquares.get(i).getColumn()));
        }
        for (int i=0; i < hits.size(); i++) {
            hits.get(i).setLocation(new Square(hits.get(i).getLocation().getRow()+1,hits.get(i).getLocation().getColumn()));
        }
			ship.getCaptainsQuarters().setRow(ship.getCaptainsQuarters().getRow()+1);
		return true;
	}

	public boolean moveShipLeft(Ship ship){
		List <Square> testSquares = ship.getOccupiedSquares();
        List <Result> hits = ship.getHitSquares();

		// loop through each square, checking if square can be moved down
		for(int i=0; i < testSquares.size(); i++) {

			// check for off board
			if ((char)(testSquares.get(i).getColumn()-1) < 'A')
				return false;
			// check for non-submerged ship occupying possible new space
			if (shipWillCollide(testSquares.get(i).getRow(), (char)(testSquares.get(i).getColumn()-1), ship))
				return false;
            // move not possible if ship is sunk
            if(ship.sunkCaptainsQuarters() == true)
                return false;
		}

		// if function has progressed to this point, move was valid, move all positions
		for (int i=0; i< ship.length; i++) {
            ship.occupiedSquares.set(i, new Square(testSquares.get(i).getRow(), (char) (testSquares.get(i).getColumn() - 1)));
        }
        for (int i=0; i < hits.size(); i++) {
            hits.get(i).setLocation(new Square(hits.get(i).getLocation().getRow(),(char)(hits.get(i).getLocation().getColumn()-1)));
        }
			ship.getCaptainsQuarters().setColumn((char)(ship.getCaptainsQuarters().getColumn()-1));
		return true;
	}

	public boolean moveShipRight(Ship ship){
		List <Square> testSquares = ship.getOccupiedSquares();
        List <Result> hits = ship.getHitSquares();

		// loop through each square, checking if square can be moved right
		for(int i=0; i < testSquares.size(); i++) {

			// check for off board
			if ((char)(testSquares.get(i).getColumn()+1) > 'J')
				return false;
			// check for non-submerged ship occupying possible new space
			if (shipWillCollide(testSquares.get(i).getRow(), (char)(testSquares.get(i).getColumn()+1), ship))
				return false;
            // move not possible if ship is sunk
            if(ship.sunkCaptainsQuarters() == true)
                return false;
		}

		// if function has progressed to this point, hypothetical move was valid, move all positions
		for (int i=0; i< ship.length; i++) {
            ship.occupiedSquares.set(i, new Square(testSquares.get(i).getRow(), (char) (testSquares.get(i).getColumn() + 1)));
        }
        for (int i=0; i < hits.size(); i++) {
            hits.get(i).setLocation(new Square(hits.get(i).getLocation().getRow(),(char)(hits.get(i).getLocation().getColumn()+1)));
        }
		ship.getCaptainsQuarters().setColumn((char)(ship.getCaptainsQuarters().getColumn()+1));
		return true;
	}

	// returns whether or not a ship of this type has been placed on the board
	public boolean alreadyPlaced(Ship ship) {
		for (int i = 0; i < this.ships.size(); i++) {
			if (this.ships.get(i).getKind().equals(ship.getKind()))
				return true;
		}
		return false;
	}

	public boolean cantPlaceSubSideSpot(int x, char y, boolean vert, Ship Placing) {
		int checkRow;
		char checkCol;

		String col = "ABCDEFGHIJ"; // makes a string of characters to get cols
		int c = 0; // used for index of cols
		for (int i = 0; i < col.length(); i++) {//selects the right index to use for col
			if (col.charAt(i) == y) {
				c = i;
				break;
			}
		}
		if (vert) {
			checkRow = x + 2;
			checkCol = col.charAt(c + 1);
		} else {
			checkRow = x + 1;
			checkCol = col.charAt(c + 2);
		}
		if (offBoard(checkRow, checkCol)) {
			return true;
		}
		List<Ship> shipsList = this.ships;
		for (int i = 0; i < shipsList.size(); i++) { // this loop ensure a ships isn't placed over a different one
			//currentShip is the ship being acessed in ships list, the loop itterates through the number of ships
			Ship currentShip = shipsList.get(i);
			for (int j = 0; j < currentShip.getLength(); j++) { //j represents the the square being checked in current ships square list
				if (checkRow == currentShip.getOccupiedSquares().get(j).getRow()) { // tests to see if the ship being placed is in the same row as the current ship
					if (checkCol == currentShip.getOccupiedSquares().get(j).getColumn()) { // tests to see if the ship being [laced is in the same col as the current ship
						if (!Placing.isSubmerged()) {
							return true; // if both the above cases are true the ship is in the same square as the current ship and can't be placed.
						}
					}
				}
			}
		}
		return false;
	}

	// place a ship vertically on the board, in a north-south/top-down manner
	public boolean placeVertical(Ship ship, int shipLength, int x, char y, boolean isVertical) {
		if (x + (shipLength) > 11 || x < 1)
			return false; // ensures ship cant go over the edge of board
		if (y >= 'K' || y < 'A') {
			return false; // ships can't go over edge of board 74 == J
		}
		List<Ship> shipsList = this.ships;
		for (int i = 0; i < shipsList.size(); i++) { // this loop ensure a ships isn't placed over a different one
			//currentShip is the ship being acessed in ships list, the loop itterates through the number of ships
			 Ship currentShip = shipsList.get(i);
			 for (int j = 0; j < currentShip.getLength(); j++) { //j represents the the square being checked in current ships square list
			 	for (int k = 0; k < shipLength; k++) { // k represents the squares that the newly placed ships will take
			 		if (x + k == currentShip.getOccupiedSquares().get(j).getRow()) { // tests to see if the ship being placed is in the same row as the current ship
			 			if (y == currentShip.getOccupiedSquares().get(j).getColumn()) { // tests to see if the ship being [laced is in the same col as the current ship
			 				if (!(ship.isSubmerged() || currentShip.isSubmerged())) {
								return false; // if both the above cases are true the ship is in the same square as the current ship and can't be placed.
							}
			 			}
			 		}
			 	}
			 }
		}
			if (ship.getKind().equals("SUBMARINE")) {
				if (cantPlaceSubSideSpot(x, y, isVertical, ship)) {
					return false;
				}
			}
			ship.setOccupiedSquares(x, y, isVertical); // ship passed so add it's squares to its list and added it to the boards list
			ship.setCaptainsQuarters(x, y, isVertical);
			this.ships.add(ship);
			return true;
	}

	// place a ship horizontally on the board, in a west-east/left-right manner
	public boolean placeHorizontal(Ship ship, int shipLength, int x, char y, boolean isVertical) {
		if (y + (shipLength) > 'K' || y < 'A') {
			return false;
		}
		if (x > 10 || x < 1) {
			return false;
		}

		List<Ship> shipsList = this.ships;
		for (int i = 0; i < shipsList.size(); i++) {// this loop ensure a ships isn't placed over a different one
			// currentship represents the ship being accessed in ships list, the loop iterates through the number of ships
			Ship currentShip = shipsList.get(i);
			for (int j = 0; j < currentShip.getLength(); j++) {//j represents the the square being checked in current ships square list
				for (int k = 0; k < shipLength; k++) {// k represents the squares that the newly placed ships will take
					if (y + k == currentShip.getOccupiedSquares().get(j).getColumn()) {// tests to see if the ship being placed is in the same row as the current ship
						if (x == currentShip.getOccupiedSquares().get(j).getRow()) {// tests to see if the ship being placed is in the same col as the current ship
							if (!(ship.isSubmerged() || currentShip.isSubmerged())) {
								return false; // if both the above cases are true the ship is in the same square as the current ship and can't be placed.
							}
						}
					}
				}
			}
		}
		if (ship.getKind().equals("SUBMARINE")) {
			if (cantPlaceSubSideSpot(x, y, isVertical, ship)) {
				return false;
			}
		}
		ship.setOccupiedSquares(x, y, isVertical);// ship passed so add it's squares to its list and added it to the boards list
		ship.setCaptainsQuarters(x, y, isVertical);
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

		if (isVertical) {
			return (placeVertical(ship, shipLength, x, y, isVertical));
		}
			return (placeHorizontal(ship, shipLength, x, y, isVertical));
	}
	/* ensure parts of the sonar area that extend off the board do not cause errors */
	public boolean offBoard(int x, char y) {
		if (y > 'J' || y < 'A' || x > 10 || x < 1) { // Invalid if attack is off the board
			return true;
		}
		return false;
	}
	// Checks to see if the attack is not on a ship or is invalid.  Returns null if its a valid attack on a ship.
	// Returns the result of the attack otherwise and alters the attack array appropriately.
	public Result checkNoShips(int x, char y) {
		Result res = new Result();

		if (y > 'J' || y < 'A' || x > 10 || x < 1) { // Invalid if attack is off the board.
			res.setResult(AttackStatus.INVALID);
			return res;
		}

		// Remove previous attack on the spot
		if (previouslyAttacked(x, y)) {
			Result rem = getAttack(x,y);
			attacks.remove(rem);
		}

		if (!shipOnSpot(x, y)) { // If not a ship on the spot, record a miss.
			res.setResult(AttackStatus.MISS);
			res.setLocation(new Square(x, y));

			List<Result> attacks = getAttacks();
			attacks.add(res);
			setAttacks(attacks);

			return res;
		}
		return null;
	}

	public boolean checkSunkShip(){
			int sunkShips = 0;
			for (int j = 0; j < ships.size(); j++) {
				for (int i = 0; i < ships.get(j).getHitSquares().size(); i++) { //for all results
					if (ships.get(j).getHitSquares().get(i).getResult() == AttackStatus.SUNK) { //if a result is sunk, add to sunkShips
						sunkShips++;
					}
				}
			}
			if (sunkShips >= 1){
				return true;
			}
			return false;
	}


	// Controls the logic if a ship is attacked.
	// Returns the result of the attack and alters the attack array appropriately.
	public Result attackOnShip(int x, char y) {
		List<Result> resList = new ArrayList<Result>();
		Result res = new Result();
		Result shipres;
		List<Ship> attackedShips = hitShips(x, y);  // Get the ship that is being hit
		for (int i = 0; i < attackedShips.size(); i++) {
			res = new Result();
			Ship attackedShip = attackedShips.get(i);
			res.setLocation(new Square(x, y));
			if (attackedShip.isSubmerged() && !enemyHasLazer) {
				res.setResult(AttackStatus.MISS);
				this.attacks.add(res);
				resList.add(res);
				continue;
			}

			res.setShip(attackedShip);

			if (attackingCQ(attackedShip, x, y)) {
				res.setResult(AttackStatus.CQHIT); // Make a CQhit result

				List<Result> attacks = getAttacks(); // Set the attack as a hit on the board
				attacks.add(res);
				setAttacks(attacks);

				shipres = new Result();
				shipres.setLocation(res.getLocation());
				shipres.setResult(res.getResult());
				attackedShip.addHit(shipres);


				attackedShip.hitCaptainsQuarters();
				if (attackedShip.sunkCaptainsQuarters()) {
					this.attacks.remove(res); // remove cqhit from the board
					attackedShip.removeHit(shipres);
					removeShip(attackedShip); // remove all ships squares from the board
					hitAllNonCQ(attackedShip); // set all ships squares to hit except cq

					// Make the result of the attack a sunk
					enemyHasLazer = true;
					res.setResult(AttackStatus.SUNK);
					attacks = getAttacks();
					this.attacks.add(res);
					setAttacks(attacks);
					shipres = new Result();
					shipres.setLocation(res.getLocation());
					shipres.setResult(res.getResult());
					attackedShip.addHit(shipres);
				}
			} else {
				res.setResult(AttackStatus.HIT); // Make a hit result

				List<Result> attacks = getAttacks(); // Set the attack as a hit on the board
				attacks.add(res);
				setAttacks(attacks);
				shipres = new Result();
				shipres.setLocation(res.getLocation());
				shipres.setResult(res.getResult());
				attackedShip.addHit(shipres);

				if (sunkShip(attackedShip)) {  // If the ship has been sunk
					enemyHasLazer = true;
					this.attacks.remove(res);  // Remove the HIT from the board and replace it with SUNK
					attackedShip.removeHit(res);
					res.setResult(AttackStatus.SUNK);
					attacks = getAttacks();
					attacks.add(res);
					setAttacks(attacks);
					shipres = new Result();
					shipres.setLocation(res.getLocation());
					shipres.setResult(res.getResult());
					attackedShip.addHit(shipres);
				}
			}

			if (gameOver()) { // If all ships are sunk
				this.attacks.remove(res);  // Remove the SUNK from the board and replace it with SURRENDER
				attackedShip.removeHit(res);
				res.setResult(AttackStatus.SURRENDER);
				attacks.add(res);
				setAttacks(attacks);
				shipres = new Result();
				shipres.setLocation(res.getLocation());
				shipres.setResult(res.getResult());
				attackedShip.addHit(shipres);
			}

			resList.add(res);
		}

		if (resList.size() == 1) {
			return resList.get(0);
		}
		else {
			int num1 = getNum(resList.get(0).getResult());
			int num2 = getNum(resList.get(1).getResult());
			if (num1 >= num2) {
				attacks.remove(resList.get(1));
				return resList.get(0);
			}
			else {
				attacks.remove(resList.get(0));
				return resList.get(1);
			}
		}
	}

	public Result SonarAttack (int x, char y){
		Result PrimarySquare= new Result();
		List<Square> squares= new ArrayList<Square>();

		/* from -2 spaces to positive 2 spaces (top to bottom) */
		for(int i = -2; i < 3; i++){
			int width; /* int var for determining how many boxes from left to right */

			/* set num rows according to what col the loop is in */
			if (i==0){ /* left to right range of 5 */
				width = 2;
			}
			else if (i == -2 || i == 2){ /* left to right range of 1 */
				width = 0;
			}
			else{ /* left to right range of 3 */
				width = 1;
			}

			/* from top to bottom of the column */
			for (int j = -width; j < (width+1); j++){
				Result currentSquare = new Result();

				/* tag occupied spaces */
				if (!previouslyAttacked(x+i, (char)(y+j)) && shipOnSpot(x+i, (char)(y+j)) && !offBoard(x+i, (char)(y+j))) {
					currentSquare.setResult(AttackStatus.SONAR_OCCUPIED);
					squares.add(new Square(x+i, (char)(y+j)));
					currentSquare.setLocation(squares.get(squares.size()-1));
					attacks = getAttacks();
					this.attacks.add(currentSquare);
					setAttacks(attacks);
				}

				/* tag unoccupied spaces */
				else if (!previouslyAttacked(x+i, (char)(y+j)) && !offBoard(x+i, (char)(y+j))) {
					currentSquare.setResult(AttackStatus.SONAR_EMPTY);
					squares.add(new Square(x + i, (char)(y + j)));
					currentSquare.setLocation(squares.get(squares.size()-1));
					attacks = getAttacks();
					this.attacks.add(currentSquare);
					setAttacks(attacks);
				}
				if (i==0 && j==0){
					PrimarySquare.setResult(currentSquare.getResult());
					PrimarySquare.setLocation(currentSquare.getLocation());
				}

			}
		}
		return PrimarySquare;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.

	--Returns the result object of the attack AND adds it to the board's attacks
	 */
	public Result attack(int x, char y, boolean Sonar) {

		if (checkSunkShip()) {
			enemyHasLazer = true;
		}
		else{
			enemyHasLazer = false;
		}

		// Checks cases for no ships
		Result res;
		if (Sonar){// if attack was a valid hit and sonar was used
			if (!previouslyAttacked(x,y)){
				res = SonarAttack(x, y);
			}
			else {
				res = new Result();
				res.setResult(AttackStatus.INVALID);
			}
		}
		else{
			res = checkNoShips(x,y);

			// Return the result if the attack isn't on a ship
			if (res != null) {

				return res;
			}

			// Continue if it is on a ship


			res = attackOnShip(x, y);
			}
		return res;
	}

	// GETTERS
	public List<Ship> getShips() {
		return this.ships;
	}
	public List<Result> getAttacks() {
		return attacks;
	}

	// SETTERS
	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}
	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
	}

	// True if a coordinate has been attacked before.
	public boolean previouslyAttacked(int x, char y) {
		List<Result> attacks = getAttacks();
		for (int i = 0; i < attacks.size(); i++) { // For all previous attacks
			// if spot is not tagged by sonar
			if(!attacks.get(i).getResult().equals(AttackStatus.SONAR_EMPTY) && !attacks.get(i).getResult().equals(AttackStatus.SONAR_OCCUPIED)) {
				Square loc = attacks.get(i).getLocation(); // Get location
				if (loc != null) {
					if (loc.getRow() == x && loc.getColumn() == y) { // If that is the location we are trying to attack return true.
						return true;
					}
				}
			}
		}
		return false;
	}

	// True if there is a non-sub at passed in coordinate
	public boolean shipWillCollide(int x, char y, Ship ship) {

		for (int i = 0; i < ships.size(); i++) { // For all ships
			if(ship.isSubmerged() == true)
				return false;

			// if the ship is above water and ship we'd move into is not the same exact ship
			if(ships.get(i).kind.equals(ship.kind) == false) {
				List<Square> occupiedSquares = ships.get(i).getOccupiedSquares();  // Get ships squares
				for (int j = 0; j < occupiedSquares.size(); j++) { // For each square
					Square loc = occupiedSquares.get(j);
					if (loc != null) {
						if (loc.getRow() == x && loc.getColumn() == y) { // If that is the location we are trying to move to return true.
							if(ships.get(i).isSubmerged()==false) {
								return true;
							}
						}
					}
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
	public List<Ship> hitShips(int x, char y) {
		List <Ship> shipList = new ArrayList<Ship>();
		for (int i = 0; i < ships.size(); i++) { // For all ships
			List<Square> occupiedSquares = ships.get(i).getOccupiedSquares();  // Get ships squares
			for (int j = 0; j < occupiedSquares.size(); j++) { // For each square
				Square loc = occupiedSquares.get(j);
				if (loc != null) {
					if (loc.getRow() == x && loc.getColumn() == y) { // If that is the location we are trying to attack return the ship.
						shipList.add(ships.get(i));
					}
				}
			}
		}
		return shipList;
	}

	// True if the ship is sunk
	public boolean sunkShip(Ship ship) {
		List<Square> occupiedSquares = ship.getOccupiedSquares(); //
		for (int i = 0; i < occupiedSquares.size(); i++) {  // For all of the ships squares
			boolean attacked = false;
			Square loc = occupiedSquares.get(i);
			int row = loc.getRow();
			char col = loc.getColumn();

			for (int j = 0; j < ship.getHitSquares().size(); j++) {  // For all of the attacked squares
				Square attacked_loc = ship.getHitSquares().get(j).getLocation();
				if (attacked_loc != null) {
					if (row == attacked_loc.getRow() && col == attacked_loc.getColumn()) {  // If that location is the same as the ship
						if (ship.getHitSquares().get(j).getResult() == AttackStatus.HIT) {
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
		for (int j = 0; j < ships.size(); j++) {
			for (int i = 0; i < ships.get(j).getHitSquares().size(); i++) { //for all results
				if (ships.get(j).getHitSquares().get(i).getResult() == AttackStatus.SUNK) { //if a result is sunk, add to sunkShips
					sunkShips++;
				}
			}
		}
		if (sunkShips >= 4){
			return true;
		}
		return false;
	}

	// True if this already hit square is an unsunk captains quarters
	public boolean alreadyCQHit(int x, char y) {
		for (int j = 0; j < ships.size(); j++) {
			List<Result> attacks = ships.get(j).getHitSquares();
			for (int i = 0; i < attacks.size(); i++) { // For all previous attacks
				Square loc = attacks.get(i).getLocation(); // Get location
				if (loc != null) {
					if (loc.getRow() == x && loc.getColumn() == y) { // If that is the location we are trying to attack return true.
						if (attacks.get(i).getResult().equals(AttackStatus.CQHIT)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	// returns true if the input coordinates are the ships captains quarters
	public boolean attackingCQ(Ship attackedShip, int x, char y) {
		if (attackedShip.getCaptainsQuarters().getColumn() == y && attackedShip.getCaptainsQuarters().getRow() == x) {
			return true;
		}
		return false;
	}

	// Removes all attacks on a given ship from the attacks array
	public void removeShip(Ship ship) {
		List<Square> occupiedSquares = ship.getOccupiedSquares(); //
		for (int i = 0; i < occupiedSquares.size(); i++) {  // For all of the ships squares
			Square loc = occupiedSquares.get(i);
			int row = loc.getRow();
			char col = loc.getColumn();

			for (int j = 0; j < attacks.size(); j++) {  // For all of the attacked squares
				Square attacked_loc = attacks.get(j).getLocation();
				if (attacked_loc != null) {
					if (row == attacked_loc.getRow() && col == attacked_loc.getColumn()) {  // If that location is the same as the ship
						attacks.remove(attacks.get(j));
					}
				}
			}
		}
		ship.hitSquares.clear();
	}

	// Adds a hit to all spots that are not the captains quarter
	public void hitAllNonCQ(Ship ship) {
		List<Square> occupiedSquares = ship.getOccupiedSquares(); //
		for (int i = 0; i < occupiedSquares.size(); i++) {  // For all of the ships squares
			Square loc = occupiedSquares.get(i);
			int row = loc.getRow();
			char col = loc.getColumn();

			// If this not the captain's quarter
			if (row != ship.getCaptainsQuarters().getRow() || col != ship.getCaptainsQuarters().getColumn()) {
				// Add it as a hit to the attacks array
				Result res = new Result();
				res.setResult(AttackStatus.HIT);
				res.setLocation(new Square(row,col));
				res.setShip(ship);

				List<Result> attacks = getAttacks(); // Set the attack on the board
				attacks.add(res);
				setAttacks(attacks);
				Result shipres = new Result();
				shipres.setLocation(res.getLocation());
				shipres.setResult(res.getResult());
				ship.addHit(shipres);
			}
		}
	}

	public Result getAttack(int x, char y) {
		for (int i = 0; i < attacks.size(); i++) {
			if (attacks.get(i).getLocation().getRow() == x && attacks.get(i).getLocation().getColumn() == y) {
				return attacks.get(i);
			}
		}
		return null;
	}




	public int getNum(AttackStatus result) {
		if (result.equals(AttackStatus.HIT)){
			return 1;
		}
		else if (result.equals(AttackStatus.SUNK)) {
			return 3;
		}
		else if (result.equals(AttackStatus.SURRENDER)){
			return 4;
		}
		else if (result.equals(AttackStatus.MISS)){
			return 0;
		}
		else {
			return 2;
		}
	}



}