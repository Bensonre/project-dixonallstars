package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		// TODO Implement
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		int shipLength =ship.getLength();
		if (isVertical) {
			if (x + (shipLength) > 11 || x < 1)
				return false;
			else {
				if (y >= 75 || y < 65) {
					return false;
				}
				ship.setOccupiedSquares(x, y, isVertical);
//				this.ships.append(ship);
				return true;
			}
		}
		else {
			if (y + (shipLength) > 75 || y < 65) {
				return false;
			} else {
				if (x >= 10 || x < 1) {
					return false;
				}
				ship.setOccupiedSquares(x, y, isVertical);
//				this.ships.append(ship);
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
		//TODO implement
		return null;
	}

	public void setShips(List<Ship> ships) {
		//TODO implement
	}

	public List<Result> getAttacks() {
		//TODO implement
		return null;
	}

	public void setAttacks(List<Result> attacks) {
		//TODO implement
	}
}
