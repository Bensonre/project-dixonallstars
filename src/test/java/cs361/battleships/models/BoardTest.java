package cs361.battleships.models;

import org.apache.commons.io.input.BOMInputStream;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Minesweeper(), 11, 'C', true));
    }

    @Test
    public void testShipHangingOverEdgeOfBoard() {
        Board board = new Board();
        assertFalse(board.placeShip(new Minesweeper(), 10, 'C', true));
    }

    @Test
    public void testShipHangingOverEdgeOfBoardHorizontally() {
        Board board = new Board();
        assertFalse(board.placeShip(new Minesweeper(), 10, 'J', false));
    }

    @Test
    public void testSetandGetShips() {
        Board board = new Board();
        Board board2 = new Board();
        assertTrue(board.placeShip(new Minesweeper(), 9, 'C', true));
        assertTrue(board.placeShip(new Destroyer(), 8, 'D', true));
        assertTrue(board2.placeShip(new Minesweeper(), 9, 'D', true));
        assertTrue(board2.placeShip(new Destroyer(), 8, 'C', true));
        assertFalse(board.getShips() == board2.getShips());
        board2.setShips(board.getShips());
        assertTrue(board.getShips() == board2.getShips());
    }

    @Test
    public void testIfShipsOnBoard() {
        Board board = new Board();
        assertTrue(board.placeShip(new Minesweeper(), 9, 'C', true));
        assertTrue(board.placeShip(new Destroyer(), 8, 'D', true));
        List<Ship> ships = new ArrayList();
        ships = board.getShips();
        Ship ship1 = ships.get(0);
        assertEquals("MINESWEEPER", ship1.getKind());
        assertEquals(9, ship1.getOccupiedSquares().get(0).getRow());
        assertEquals(10, ship1.getOccupiedSquares().get(1).getRow());
        assertEquals('C', ship1.getOccupiedSquares().get(0).getColumn());
    }

    @Test
    public void testIfShipsOverlap() {
        Board board = new Board();
        assertTrue(board.placeShip(new Minesweeper(), 9, 'C', true));
        assertFalse(board.placeShip(new Destroyer(), 8, 'C', true));
    }

    @Test
    public void testIfShipsOverlapHorizontal() {
        Board board = new Board();
        assertTrue(board.placeShip(new Minesweeper(), 9, 'C', true));
        assertFalse(board.placeShip(new Destroyer(), 10, 'B', false));
    }

    @Test
    public void testSetAttacks() {
        Board board = new Board();
        List<Result> attacks = new ArrayList<Result>();
        Result res = new Result();
        res.setResult(AttackStatus.INVALID);
        attacks.add(res);

        board.setAttacks(attacks);
        assertSame(res, board.getAttacks().get(0));
    }

    @Test
    public void testAttackOffBoard() {
        Board board = new Board();
        Result res = board.attack(-3, 'R');

        Result expected = new Result();
        expected.setResult(AttackStatus.INVALID);

        assertSame(expected.getResult(), res.getResult());
    }

    @Test
    public void testAttackAlreadyAttacked() {
        Board board = new Board();

        List<Result> attacks = new ArrayList<Result>();
        Result adder = new Result();
        adder.setResult(AttackStatus.MISS);
        adder.setLocation(new Square(1, 'A'));
        attacks.add(adder);
        board.setAttacks(attacks);

        Result res = board.attack(1, 'A');

        Result expected = new Result();
        expected.setResult(AttackStatus.INVALID);

        assertSame(expected.getResult(), res.getResult());
    }

    @Test
    public void testAttackMisses() {
        Board board = new Board();

        Result expected = new Result();
        expected.setResult(AttackStatus.MISS);

        List<Result> attacks = new ArrayList<Result>();
        Result adder = new Result();
        adder.setResult(AttackStatus.MISS);
        adder.setLocation(new Square(1, 'A'));
        attacks.add(adder);
        board.setAttacks(attacks);

        Result res = board.attack(2, 'C');  // Test miss with no ships on
        assertSame(expected.getResult(), res.getResult());

        Ship ship = new Minesweeper();
        board.placeShip(ship, 7, 'E', false);

        res = board.attack(2, 'D');  // Test miss w ship on board.
        assertSame(expected.getResult(), res.getResult());

        expected.setResult(AttackStatus.INVALID); // Test that attacks can't be repeated.
        res = board.attack(2, 'D');
        assertSame(expected.getResult(), res.getResult());
    }

    @Test
    public void testAttackHitsNoSinks() {
        Board board = new Board();

        Result expected = new Result();
        expected.setResult(AttackStatus.HIT);

        List<Result> attacks = new ArrayList<Result>();  // Add a miss on to the board
        Result adder = new Result();
        adder.setResult(AttackStatus.MISS);
        adder.setLocation(new Square(1, 'A'));
        attacks.add(adder);
        board.setAttacks(attacks);

        Result res = new Result();

        Ship ship = new Minesweeper(); // Put ship on board
        board.placeShip(ship, 7, 'E', true);

        List<Ship> shiplist = board.getShips(); // Make sure ship is on board
        Ship checkship = shiplist.get(0);
        Square l = checkship.getOccupiedSquares().get(1);
        assertSame(8, l.getRow());
        assertSame('E', l.getColumn());


        res = board.attack(8, 'E');  // Test hits ship on board.
        assertSame(expected.getResult(), res.getResult());

        expected.setResult(AttackStatus.INVALID); // Test that attacks can't be repeated.
        res = board.attack(8, 'E');
        assertSame(expected.getResult(), res.getResult());
    }

    @Test
    public void testAttackHitsAndSinks() {
        Board board = new Board();

        Result expected = new Result();
        expected.setResult(AttackStatus.HIT);

        List<Result> attacks = new ArrayList<Result>();  // Add a miss to the board.
        Result adder = new Result();
        adder.setResult(AttackStatus.MISS);
        adder.setLocation(new Square(1, 'A'));
        attacks.add(adder);
        board.setAttacks(attacks);

        Result res = new Result();

        Ship ship = new Minesweeper(); // Put ship on board
        board.placeShip(ship, 7, 'E', false);

        List<Ship> shiplist = board.getShips(); // Make sure ship is on board
        Ship checkship = shiplist.get(0);
        Square l = checkship.getOccupiedSquares().get(1);
        assertSame(7, l.getRow());
        assertSame('F', l.getColumn());


        res = board.attack(7, 'F');  // Test hits ship on board.
        assertSame(expected.getResult(), res.getResult());

        expected.setResult(AttackStatus.SUNK); // Tests that final blow sinks the ship.
        res = board.attack(7, 'E');
        assertSame(expected.getResult(), res.getResult());

        expected.setResult(AttackStatus.INVALID); // Test that attacks can't be repeated.
        res = board.attack(7, 'F');
        assertSame(expected.getResult(), res.getResult());
    }

    @Test
    public void testShipAlreadyPlaced() {
        Board board = new Board();
        Ship ship = new Minesweeper(); // Put ship on board
        board.placeShip(ship, 7, 'E', false);
        Ship ship2 = new Minesweeper(); // create ship2
        assertFalse(board.placeShip(ship2, 8, 'C', true));//since ship of same type already placed
        // on board should return false


    }

    @Test
    public void testPlaceShipOfEveryType() {
        Board board = new Board();
        Ship ship = new Minesweeper(); // Put ship on board
        board.placeShip(ship, 7, 'E', false);
        Ship ship2 = new Destroyer(); // create ship2
        Ship ship3 = new Battleship(); // create ship2
        assertTrue(board.placeShip(ship2, 5, 'C', true));//should ne able to place a ship of a new type;
        assertTrue(board.placeShip(ship3, 3, 'B', true));//should be able to place a ship of a new kind;
    }

    @Test
    public void testPlaceShipOfNewTypeAfterFailingPlacement() {
        Board board = new Board();
        Ship ship = new Minesweeper(); // Put ship on board
        board.placeShip(ship, 7, 'E', false);
        Ship ship2 = new Minesweeper(); // create ship2
        Ship ship3 = new Destroyer(); // create ship2
        assertFalse(board.placeShip(ship2, 5, 'C', true));//shouldn't be able to place a ship of same kind
        assertTrue(board.placeShip(ship3, 5, 'C', true));//should be able to place a ship of a new kind;
    }

    @Test
    public void testSurrenderOnLastSunkShip() {
        Board board = new Board();

        Result expected = new Result();
        expected.setResult(AttackStatus.SURRENDER);

        Ship shipM = new Minesweeper(); // Put ship on board
        board.placeShip(shipM, 1, 'A', false);
        Ship shipD = new Destroyer(); // Put ship on board
        board.placeShip(shipD, 2, 'A', false);
        Ship shipB = new Battleship(); // Put ship on board
        board.placeShip(shipB, 3, 'A', false);

        List<Ship> shiplist = board.getShips(); // Make sure ship is on board

        // Attack all ships to death
        board.attack(1, 'A');
        board.attack(1, 'B');

        board.attack(2, 'A');
        board.attack(2, 'B');
        board.attack(2, 'B');
        board.attack(2, 'C');

        board.attack(3, 'A');
        board.attack(3, 'B');
        board.attack(3, 'C');
        board.attack(3, 'D');
        Result res = board.attack(3, 'C');

        // Make sure the game is a surrender
        assertSame(expected.getResult(), res.getResult());

    }

    @Test
    public void testAlreadyCQHit() {
        Board board = new Board();

        Result placing = new Result();
        placing.setResult(AttackStatus.CQHIT);
        placing.setLocation(new Square(1, 'A'));
        List<Result> attacks = new ArrayList<Result>();  // Add a CQHit to the board.
        attacks.add(placing);

        board.setAttacks(attacks);

        assertTrue(board.alreadyCQHit(1, 'A'));
        assertFalse(board.alreadyCQHit(3, 'E'));
    }

    @Test
    public void testAttackingCQ() {
        Board board = new Board();

        Ship ship = new Minesweeper();
        board.placeShip(ship, 1, 'A', false);

        assertTrue(board.attackingCQ(ship, 1, 'A'));
        assertFalse(board.attackingCQ(ship, 2, 'A'));
        assertFalse(board.attackingCQ(ship, 3, 'A'));
    }

    @Test
    public void testRemoveShip() {
        Board board = new Board();
        List<Result> attacks = new ArrayList<Result>();

        // Place 3 attacks on to the board, 2 of them on the ship
        Result placing1 = new Result();
        placing1.setResult(AttackStatus.HIT);
        placing1.setLocation(new Square(1, 'A'));
        attacks.add(placing1);

        Result placing2 = new Result();
        placing2.setResult(AttackStatus.HIT);
        placing2.setLocation(new Square(1, 'B'));
        attacks.add(placing2);

        Result placing3 = new Result();
        placing3.setResult(AttackStatus.HIT);
        placing3.setLocation(new Square(3, 'E'));
        attacks.add(placing3);

        board.setAttacks(attacks);

        // Set the ship so its on 2 of the 3 attacks
        Ship ship = new Battleship();
        board.placeShip(ship, 1, 'A', false);

        assertEquals(3, board.getAttacks().size()); // Check that there are 3 attacks
        board.removeShip(ship); // Remove the attacks on the ship
        assertEquals(1, board.getAttacks().size()); // Check that there is one attack
    }

    @Test
    public void testHitAllNonCQ() {
        Board board = new Board();

        // Set the ship so its on 2 of the 3 attacks
        Ship ship = new Battleship();
        board.placeShip(ship, 1, 'A', false);

        assertEquals(0, board.getAttacks().size()); // Check that there are 3 attacks
        board.hitAllNonCQ(ship); // Remove the attacks on the ship
        assertEquals(3, board.getAttacks().size()); // Check that there is one attack
    }

    @Test
    public void testCQInstaSink() {
        Board board = new Board();

        Result expected = new Result();
        expected.setResult(AttackStatus.SUNK);

        Ship shipM = new Minesweeper(); // Put ship on board
        board.placeShip(shipM, 1, 'A', false);
        Ship shipD = new Destroyer(); // Put ship on board
        board.placeShip(shipD, 2, 'A', false);
        Ship shipB = new Battleship(); // Put ship on board
        board.placeShip(shipB, 3, 'A', false);

        List<Ship> shiplist = board.getShips(); // Make sure ship is on board

        Result res;


        // Make sure the CQ returns a SUNK correctly for all ships
        res = board.attack(1, 'A');
        assertSame(expected.getResult(), res.getResult());

        res = board.attack(2, 'B');
        res = board.attack(2, 'B');
        assertSame(expected.getResult(), res.getResult());


        expected.setResult(AttackStatus.SURRENDER);
        board.attack(3, 'C');
        res = board.attack(3, 'C');

        assertSame(expected.getResult(), res.getResult());
    }

    @Test
    public void testCQReturnsMissMarksHit() {
        Board board = new Board();

        Result expected = new Result();
        expected.setResult(AttackStatus.MISS);

        Ship shipD = new Destroyer(); // Put ship on board
        board.placeShip(shipD, 2, 'A', false);


        List<Ship> shiplist = board.getShips(); // Make sure ship is on board

        Result res;

        res = board.attack(2, 'B'); // Attack CQ and make sure its a miss
        assertSame(expected.getResult(), res.getResult());

        assertSame(board.getAttacks().get(0).getResult(), AttackStatus.CQHIT); // Make sure its actually a CQHIT on backend

        expected.setResult(AttackStatus.SUNK); // Make sure second hit is a sunk
        res = board.attack(2, 'B');
        assertSame(expected.getResult(), res.getResult());
    }
}

