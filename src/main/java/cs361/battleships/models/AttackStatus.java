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

	/**
	 * The result if the captain's quarter is hit but still has armor
	 */
	CQHIT,
	/**
	 * Sonar is used and found a Ship in the space
	 */
	SONAR_OCCUPIED,

	/**
	 * Sonar is used and found nothing in the space
	 */
	SONAR_EMPTY,

}
