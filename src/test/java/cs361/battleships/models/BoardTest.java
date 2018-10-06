package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));
    }
    @Test
    public void testShipHangingOverEdgeOfBoard(){
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 10, 'C', true));
    }
    @Test
    public void testShipHangingOverEdgeOfBoardHorizontally(){
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 10, 'J', false));
    }
    @Test
    public void testSetandGetShips() {
        Board board = new Board();
        Board board2 = new Board();
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 9, 'C', true));
        assertTrue(board.placeShip(new Ship("DESTROYER"), 8, 'D', true));
        assertTrue(board2.placeShip(new Ship("MINESWEEPER"), 9, 'D', true));
        assertTrue(board2.placeShip(new Ship("DESTROYER"), 8, 'C', true));
        assertFalse(board.getShips() == board2.getShips());
        board2.setShips(board.getShips());
        assertTrue(board.getShips() == board2.getShips());
    }
    @Test
    public void testIfShipsOnBoard(){
        Board board=new Board();
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 9, 'C', true));
        assertTrue(board.placeShip(new Ship("DESTROYER"), 8, 'D', true));
        List <Ship> ships = new ArrayList();
             ships   = board.getShips();
        Ship ship1=ships.get(0);
        assertEquals("MINESWEEPER",ship1.getKind() );
        assertEquals(9,ship1.getOccupiedSquares().get(0).getRow());
        assertEquals(10,ship1.getOccupiedSquares().get(1).getRow());
        assertEquals('C',ship1.getOccupiedSquares().get(0).getColumn());
    }
   @Test
   public void testIfShipsOverlap(){
       Board board=new Board();
       assertTrue(board.placeShip(new Ship("MINESWEEPER"), 9, 'C', true));
       assertFalse(board.placeShip(new Ship("DESTROYER"), 8, 'C', true));
   }
   @Test
   public void testIfShipsOverlapHorizontal(){
        Board board=new Board();
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 9, 'C', true));
        assertFalse(board.placeShip(new Ship("DESTROYER"), 10, 'B', false));
    }
}
