package cs361.battleships.models;

import java.util.ArrayList;

public class Minesweeper extends Ship {

    public Minesweeper() {
        this.occupiedSquares= new ArrayList<Square>();// constructs list
        this.kind="MINESWEEPER";// sets kind equal to that passed in
        this.occupiedSquares.add(new Square(0, 'A'));
        this.occupiedSquares.add(new Square(0, 'A'));
        this.length=2;
    }
}
