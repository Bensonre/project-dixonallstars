package cs361.battleships.models;

public class Result {

	private AtackStatus result;  // The result of the attack
	private Ship ship;  // The ship that was hit by the attack
	private Square location; // The location that was attacked.

	public AtackStatus getResult() {
		return this.result;
	}

	public void setResult(AtackStatus result) {
		this.result = result;
	}

	public Ship getShip() {
		return this.ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Square getLocation() {
		return this.location;
	}

	public void setLocation(Square square) {
		this.location = square;
	}
}
