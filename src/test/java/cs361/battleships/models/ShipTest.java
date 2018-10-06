package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ShipTest {
    @Test
    public void testMineSweeperSetShip() {
        Ship mine = new Ship("MINESWEEPER");
        assertTrue("MINESWEEPER"==mine.getKind());
        assertTrue(2 ==mine.getLength());
    }
    @Test
    public void testDestroyersetShip(){
        Ship destroyer = new Ship("DESTROYER");
        assertTrue("DESTROYER"==destroyer.getKind());
        assertTrue(3 ==destroyer.getLength());
    }
    @Test
    public void testBattleShipsetShip(){
        Ship battleship = new Ship("BATTLESHIP");
        assertTrue("BATTLESHIP"==battleship.getKind());
        assertTrue(4 ==battleship.getLength());
    }
    @Test
    public void testSetOccupiedSquares(){
        Ship mine = new Ship("MINESWEEPER");
        mine.setOccupiedSquares(1,'A',false);
        List<Square> squares = new ArrayList<>();
        squares.add(new Square(1,'A'));
        squares.add(new Square(1,'B'));
        assertEquals(mine.getOccupiedSquares().get(0).getRow(),squares.get(0).getRow());
        assertEquals(mine.getOccupiedSquares().get(0).getColumn(),squares.get(0).getColumn());
        assertEquals(mine.getOccupiedSquares().get(1).getRow(),squares.get(1).getRow());
        assertEquals(mine.getOccupiedSquares().get(1).getColumn(),squares.get(1).getColumn());
    }
    @Test
    public void testSetOccupiedSquaresVertical(){
        Ship mine = new Ship("MINESWEEPER");
        mine.setOccupiedSquares(1,'A',true);
        List<Square> squares = new ArrayList<>();
        squares.add(new Square(1,'A'));
        squares.add(new Square(2,'A'));
        assertEquals(mine.getOccupiedSquares().get(0).getRow(),squares.get(0).getRow());
        assertEquals(mine.getOccupiedSquares().get(0).getColumn(),squares.get(0).getColumn());
        assertEquals(mine.getOccupiedSquares().get(1).getRow(),squares.get(1).getRow());
        assertEquals(mine.getOccupiedSquares().get(1).getColumn(),squares.get(1).getColumn());
    }
    @Test
    public void testSetOccupiedSquaresVerticalDestroyer() {
        Ship mine = new Ship("DESTROYER");
        mine.setOccupiedSquares(1, 'A', true);
        List<Square> squares = new ArrayList<>();
        squares.add(new Square(1, 'A'));
        squares.add(new Square(2, 'A'));
        squares.add(new Square(3, 'A'));
        assertEquals(mine.getOccupiedSquares().get(0).getRow(), squares.get(0).getRow());
        assertEquals(mine.getOccupiedSquares().get(0).getColumn(), squares.get(0).getColumn());
        assertEquals(mine.getOccupiedSquares().get(1).getRow(), squares.get(1).getRow());
        assertEquals(mine.getOccupiedSquares().get(1).getColumn(), squares.get(1).getColumn());
        assertEquals(mine.getOccupiedSquares().get(2).getRow(), squares.get(2).getRow());
        assertEquals(mine.getOccupiedSquares().get(2).getColumn(), squares.get(2).getColumn());
    }
    @Test
    public void testSetOccupiedSquaresDestroyer(){
        Ship mine = new Ship("DESTROYER");
        mine.setOccupiedSquares(1,'A',false);
        List<Square> squares = new ArrayList<>();
        squares.add(new Square(1,'A'));
        squares.add(new Square(1,'B'));
        squares.add(new Square(1,'C'));
        assertEquals(mine.getOccupiedSquares().get(0).getRow(),squares.get(0).getRow());
        assertEquals(mine.getOccupiedSquares().get(0).getColumn(),squares.get(0).getColumn());
        assertEquals(mine.getOccupiedSquares().get(1).getRow(),squares.get(1).getRow());
        assertEquals(mine.getOccupiedSquares().get(1).getColumn(),squares.get(1).getColumn());
        assertEquals(mine.getOccupiedSquares().get(2).getRow(),squares.get(2).getRow());
        assertEquals(mine.getOccupiedSquares().get(2).getColumn(),squares.get(2).getColumn());
    }
    @Test
    public void testSetOccupiedSquaresVerticalBattleShip(){
        Ship mine = new Ship("BATTLESHIP");
        mine.setOccupiedSquares(1,'A',true);
        List<Square> squares = new ArrayList<>();
        squares.add(new Square(1,'A'));
        squares.add(new Square(2,'A'));
        squares.add(new Square(3,'A'));
        squares.add(new Square(4,'A'));
        assertEquals(mine.getOccupiedSquares().get(0).getRow(),squares.get(0).getRow());
        assertEquals(mine.getOccupiedSquares().get(0).getColumn(),squares.get(0).getColumn());
        assertEquals(mine.getOccupiedSquares().get(1).getRow(),squares.get(1).getRow());
        assertEquals(mine.getOccupiedSquares().get(1).getColumn(),squares.get(1).getColumn());
        assertEquals(mine.getOccupiedSquares().get(2).getRow(),squares.get(2).getRow());
        assertEquals(mine.getOccupiedSquares().get(2).getColumn(),squares.get(2).getColumn());
        assertEquals(mine.getOccupiedSquares().get(3).getRow(),squares.get(3).getRow());
        assertEquals(mine.getOccupiedSquares().get(3).getColumn(),squares.get(3).getColumn());
    }
    @Test
    public void testSetOccupiedSquaresBattleShip(){
        Ship mine = new Ship("BATTLESHIP");
        mine.setOccupiedSquares(1,'A',false);
        List<Square> squares = new ArrayList<>();
        squares.add(new Square(1,'A'));
        squares.add(new Square(1,'B'));
        squares.add(new Square(1,'C'));
        squares.add(new Square(1,'D'));
        assertEquals(mine.getOccupiedSquares().get(0).getRow(),squares.get(0).getRow());
        assertEquals(mine.getOccupiedSquares().get(0).getColumn(),squares.get(0).getColumn());
        assertEquals(mine.getOccupiedSquares().get(1).getRow(),squares.get(1).getRow());
        assertEquals(mine.getOccupiedSquares().get(1).getColumn(),squares.get(1).getColumn());
        assertEquals(mine.getOccupiedSquares().get(2).getRow(),squares.get(2).getRow());
        assertEquals(mine.getOccupiedSquares().get(2).getColumn(),squares.get(2).getColumn());
        assertEquals(mine.getOccupiedSquares().get(3).getRow(),squares.get(3).getRow());
        assertEquals(mine.getOccupiedSquares().get(3).getColumn(),squares.get(3).getColumn());
    }

}
