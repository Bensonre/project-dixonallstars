package cs361.battleships.models;

import java.util.Random;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful)
            return false;

        Ship new_ship = new Ship(randKind());
        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(new_ship, randRow(), randCol(), randVertical());
        } while (!opponentPlacedSuccessfully);

        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char y) {
        Result playerAttack = opponentsBoard.attack(x, y);
        if (playerAttack.getResult() == INVALID) {
            return false;
        }

        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(randRow(), randCol());
        } while (opponentAttackResult.getResult() == INVALID);

        return true;
    }

    public String randKind() {
        // generate int between 2 and 4 inclusive
        // nextInt() is exclusive of the upper bound
        int rand = ThreadLocalRandom.current().nextInt(2, 5);

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

    public int randRow() {
        // generate int between 1 and 10 inclusive
        int rand = ThreadLocalRandom.current().nextInt(1, 11);
        return rand;
    }

    public char randCol() {
        // generate int between 65 and 74 inclusive, corresponding with
        // the ascii table
        int rand = ThreadLocalRandom.current().nextInt(65, 75);
        return (char)rand;
    }

    public boolean randVertical() {
        // generate int between 0 and 1 inclusive
        int rand = ThreadLocalRandom.current().nextInt(0, 2);

        if (rand == 0) {
            return false;
        }
        else {
            return true;
        }
    }

}
