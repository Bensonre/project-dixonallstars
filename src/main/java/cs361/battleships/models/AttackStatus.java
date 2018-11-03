package cs361.battleships.models;

public enum AttackStatus {

	/**
	 * Attack results in a miss
	 */
	MISS,

	/**
	 * Attack results in an enemy ship hit
	 */
	HIT,

	/**
	 * Attack results in sinking of the ship
	 */
	SUNK,

	/**
	 * Attack results in defeat of opponent (a surrender)
	 */
	SURRENDER,
	
	/**
	 * The result if the coordinates given are invalid
	 */
	INVALID,

}
