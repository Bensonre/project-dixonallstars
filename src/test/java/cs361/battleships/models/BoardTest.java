package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));
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
}
