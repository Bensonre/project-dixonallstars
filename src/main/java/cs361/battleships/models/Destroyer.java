package cs361.battleships.models;

import java.util.ArrayList;

// S.O.L.I.D. evaluation contained in Ship class

public class Destroyer extends Ship {

    public Destroyer() {
        this.occupiedSquares= new ArrayList<Square>();// constructs list
        this.kind="DESTROYER";// sets kind equal to that passed in
        this.occupiedSquares.add(new Square(0, 'A'));
        this.occupiedSquares.add(new Square(0, 'A'));
        this.occupiedSquares.add(new Square(0, 'A'));
        this.length=3;
    }
}
