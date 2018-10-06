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
		int shipLength =ship.getLength();
		if (isVertical) {
			if (x + (shipLength) > 11 || x < 1)
				return false;
			else {
				if (y >= 75 || y < 65) {
					return false;
				}
				for (int i =0; i<this.ships.size();i++){
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
				ship.setOccupiedSquares(x, y, isVertical);
				this.ships.add(ship);
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
