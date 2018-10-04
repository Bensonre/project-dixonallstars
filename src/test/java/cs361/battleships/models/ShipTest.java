package cs361.battleships.models;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ShipTest {
    @Test
    public void testValidShip() {
        Ship mine = new Ship("MINESWEEPER");
        Ship destroyer = new Ship("DESTROYER");
        Ship battleship = new Ship("BATTLESHIP");
        assertTrue("MINESWEEPER"==mine.getKind());
        assertTrue(2 ==mine.getLength());
        assertTrue("DESTROYER"==destroyer.getKind());
        assertTrue(3 ==destroyer.getLength());
        assertTrue("BATTLESHIP"==battleship.getKind());
        assertTrue(4 ==battleship.getLength());
    }
    public void testSetOccupiedSquares(){
        Ship mine = new Ship("MINESWEEPER");
        mine.setOccupiedSquares(1,'A',false);
        mine.getOccupiedSquares();

    }

}
