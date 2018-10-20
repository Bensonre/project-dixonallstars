package cs361.battleships.models;

import java.util.Random;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();
    Random r;

    public Game() {
        r = new Random(System.currentTimeMillis());
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            String rkind = randKind();
            Ship new_ship = new Ship(rkind);
            int row = randRow();
            char col = randCol();
            boolean vert = randVertical();
            opponentPlacedSuccessfully = opponentsBoard.placeShip(new_ship, row, col, vert);
        } while (!opponentPlacedSuccessfully);
        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char y) {
        Result playerAttack = opponentsBoard.attack(x, y);
        if (playerAttack.getResult() == AtackStatus.INVALID) {
            return false;
        }

        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(randRow(), randCol());
        } while (opponentAttackResult.getResult() == AtackStatus.INVALID);

        return true;
    }

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

    public int randRow() {
        // generate int between 1 and 10 inclusive
        int rand = (Math.abs(r.nextInt()) % 10) + 1;

        return rand;
    }

    public char randCol() {
        // generate int between 65 and 74 inclusive, corresponding with
        // the ascii table
        int rand = (Math.abs(r.nextInt()) % 10) + 65;

        return (char)rand;
    }

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
