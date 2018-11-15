package cs361.battleships.models;

import java.util.ArrayList;

// S.O.L.I.D. evaluation contained in Ship class

public class Battleship extends Ship {

    public Battleship() {
        this.occupiedSquares = new ArrayList<Square>(); // constructs list
        this.hitSquares = new ArrayList<Result>();
        this.kind = "BATTLESHIP"; // sets kind equal to what was passed in
        this.occupiedSquares.add(new Square(0, 'A'));
        this.occupiedSquares.add(new Square(0, 'A'));
        this.occupiedSquares.add(new Square(0, 'A'));
        this.occupiedSquares.add(new Square(0, 'A'));
        this.length=4;
    }

    public void setCaptainsQuarters(int row, char column, boolean vertical){
        String col= "ABCDEFGHIJ";// makes a string of characters to get cols
        int c=0;// used for index of cols
        if (vertical){// Set it two above the origin
            this.captainsQuarters = new Square(row + 2, column);
        }
        else {// Set it two to the right of the origin
            for (int i=0; i<col.length(); i++){//selects the right index to use for col
                if (col.charAt(i)== column){
                    c=i;
                    break;
                }
            }
            this.captainsQuarters = new Square(row,col.charAt(2+c));
        }
        Armor armor = new Armor();
        armor.setArmor(2);
        this.armor = armor;
    }
}
