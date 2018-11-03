package cs361.battleships.models;

/*
In following SRP, this Square Class functions more like the physical index of the grid, and this class functions
more like the actual contents of that grid location.
 */

public class Result {

	private AttackStatus result;  // The result of the attack
	private Ship ship;  // The ship that was hit by the attack
	private Square location; // The location that was attacked.

	// GETTERS
	public AttackStatus getResult() {
		return this.result;
	}
	public Ship getShip() {
		return this.ship;
	}
	public Square getLocation() {
		return this.location;
	}

	// SETTERS
	public void setResult(AttackStatus result) {
		this.result = result;
	}
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	public void setLocation(Square square) {
		this.location = square;
	}
}
