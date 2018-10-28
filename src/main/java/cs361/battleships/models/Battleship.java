package cs361.battleships.models;

import java.util.ArrayList;

public class Battleship extends Ship {

    public Battleship() {
        this.occupiedSquares= new ArrayList<Square>();// constructs list
        this.kind="BATTLESHIP";// sets kind equal to that passed in
        this.occupiedSquares.add(new Square(0, 'A'));
        this.occupiedSquares.add(new Square(0, 'A'));
        this.occupiedSquares.add(new Square(0, 'A'));
        this.occupiedSquares.add(new Square(0, 'A'));
        this.length=4;
    }
}
