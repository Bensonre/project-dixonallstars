package cs361.battleships.models;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
Follows SRP by only performing responsibilities logically connected to a game of Battleship IRL.
This includes: the creating of the two boards, the game actions of ship placement and attacks (which occur on the board
so the Game class simply *facilitates* board actions).

Game and Board classes do not inherit, but they have been created with minimalism (ISP) and (OCP) in mind. A third
board could easily be added, for instance. Or more ships or a larger grid (although the UI would take a bit of a hit
from a larger grid)
 */

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();

    static private int numSonarAI = 0;
    private boolean sonarAI = false;

    // seed random number generator
    Random r;
    public Game() {
        r = new Random(System.currentTimeMillis());
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.

	gets a valid ship placement from both the player and the AI
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {

        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;
        do {
            // create ship object
            Ship new_ship;

            // get random ship type
            String rkind = randKind();
            if (rkind.equals("MINESWEEPER")) {
                new_ship = new Minesweeper();
            } else if (rkind.equals("DESTROYER")) {
                new_ship = new Destroyer();
            } else {
                new_ship = new Battleship();
            }

            // get random grid location and ship orientation
            int row = randRow();
            char col = randCol();
            boolean vert = randVertical();

            // try to place; repeat if unsuccessful
            opponentPlacedSuccessfully = opponentsBoard.placeShip(new_ship, row, col, vert);
        } while (!opponentPlacedSuccessfully);
        return true;
    }

    public boolean oneShipSunk(){
        List<Ship> ships = playersBoard.getShips();

        // loop through player ships to see if one is sunk
        for(int i = 0; i < ships.size(); i++) {
            if ( playersBoard.sunkShip(ships.get(i)) )
                return true;
        }

        return false;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.

	gets a valid attack from both the player and the AI
	 */
    public boolean attack(int x, char y, boolean SonarPlayer) {

        if (playersBoard.getAttacks().isEmpty()) {
            numSonarAI = 0;
        }

        // get an action from the user
        Result playerAttack = opponentsBoard.attack(x, y, SonarPlayer);
        if (playerAttack.getResult() == AttackStatus.INVALID) {
            return false;
        }

        // get an action from the rng AI
        Result opponentAttackResult;
        do {
            sonarAI = false; // reset

            // if AI can fire a sonar
            if(numSonarAI < 2 && oneShipSunk()) {

                // randomly choose if it will fire sonar
                sonarAI = randVertical();
            }
            opponentAttackResult = playersBoard.attack(randRow(), randCol(), sonarAI);

            // if opponent attack was successful and was a sonar attack
            if(opponentAttackResult.getResult() != AttackStatus.INVALID && sonarAI == true){
                numSonarAI++;
            }

        } while (opponentAttackResult.getResult() == AttackStatus.INVALID);

        return true;
    }

    // returns a random String for ship type
    public String randKind() {
        // generate int between 2 and 4 inclusive
        // nextInt() is exclusive of the upper bound
        int rand = (Math.abs(r.nextInt()) % 3) + 2;

        if (rand == 2) {
            return "MINESWEEPER";
        }
        else if (rand == 3) {
            return "DESTROYER";
        }
        else {
            return "BATTLESHIP";
        }
    }

    // returns a random int for row
    public int randRow() {
        // generate int between 1 and 10 inclusive
        int rand = (Math.abs(r.nextInt()) % 10) + 1;

        return rand;
    }

    // returns a random char for column
    public char randCol() {
        // generate int between 65 and 74 inclusive, corresponding with
        // the ascii table
        int rand = (Math.abs(r.nextInt()) % 10) + 65;

        return (char)rand;
    }

    // return a random bool for whether ship is vertical
    public boolean randVertical() {
        // generate int between 0 and 1 inclusive
        int rand = Math.abs(r.nextInt()) % 2;

        if (rand == 0) {
            return false;
        }
        else {
            return true;
        }
    }

}
