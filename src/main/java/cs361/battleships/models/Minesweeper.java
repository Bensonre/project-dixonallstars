package cs361.battleships.models;

import java.util.ArrayList;

// S.O.L.I.D. evaluation contained in Ship class

public class Minesweeper extends Ship {

    public Minesweeper() {
        this.occupiedSquares= new ArrayList<Square>();// constructs list
        this.kind="MINESWEEPER";// sets kind equal to that passed in
        this.occupiedSquares.add(new Square(0, 'A'));
        this.occupiedSquares.add(new Square(0, 'A'));
        this.length=2;
    }

    public void setCaptainsQuarters(int row, char column, boolean vertical){
        this.captainsQuarters = new Square(row, column);  // Set it on the origin
        this.armor = 1;
    }
}
