package cs361.battleships.models;

import org.junit.Test;

import javax.xml.stream.Location;
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

    @Test
    public void testSetAttacks() {
        Board board = new Board();
        List<Result> attacks = new ArrayList<Result>();
        Result res = new Result();
        res.setResult(AtackStatus.INVALID);
        attacks.add(res);

        board.setAttacks(attacks);
        assertSame(res,board.getAttacks().get(0));
    }

    @Test
    public void testAttackOffBoard() {
        Board board = new Board();
        Result res = board.attack(-3, 'R');

        Result expected = new Result();
        expected.setResult(AtackStatus.INVALID);

        assertSame(expected.getResult(), res.getResult());
    }

    @Test
    public void testAttackAlreadyAttacked() {
        Board board = new Board();

        List<Result> attacks = new ArrayList<Result>();
        Result adder = new Result();
        adder.setResult(AtackStatus.MISS);
        adder.setLocation(new Square(1,'A'));
        attacks.add(adder);
        board.setAttacks(attacks);

        Result res = board.attack(1, 'A');

        Result expected = new Result();
        expected.setResult(AtackStatus.INVALID);

        assertSame(expected.getResult(), res.getResult());
    }

    @Test
    public void testAttackMisses() {
        Board board = new Board();

        Result expected = new Result();
        expected.setResult(AtackStatus.MISS);

        List<Result> attacks = new ArrayList<Result>();
        Result adder = new Result();
        adder.setResult(AtackStatus.MISS);
        adder.setLocation(new Square(1,'A'));
        attacks.add(adder);
        board.setAttacks(attacks);

        Result res = board.attack(2, 'C');  // Test miss with no ships on
        assertSame(expected.getResult(), res.getResult());

        Ship ship = new Ship("MINESWEEPER");
        board.placeShip(ship,7, 'E', false);

        res = board.attack(2, 'D');  // Test miss w ship on board.
        assertSame(expected.getResult(), res.getResult());

        expected.setResult(AtackStatus.INVALID); // Test that attacks can't be repeated.
        res = board.attack(2, 'D');
        assertSame(expected.getResult(), res.getResult());
    }

    @Test
    public void testAttackHitsNoSinks() {
        Board board = new Board();

        Result expected = new Result();
        expected.setResult(AtackStatus.HIT);

        List<Result> attacks = new ArrayList<Result>();  // Add a miss on to the board
        Result adder = new Result();
        adder.setResult(AtackStatus.MISS);
        adder.setLocation(new Square(1,'A'));
        attacks.add(adder);
        board.setAttacks(attacks);

        Result res = new Result();

        Ship ship = new Ship("MINESWEEPER"); // Put ship on board
        board.placeShip(ship,7, 'E', true);

        List<Ship> shiplist = board.getShips(); // Make sure ship is on board
        Ship checkship = shiplist.get(0);
        Square l = checkship.getOccupiedSquares().get(1);
        assertSame(8,l.getRow());
        assertSame('E', l.getColumn());


        res = board.attack(8, 'E');  // Test hits ship on board.
        assertSame(expected.getResult(), res.getResult());

        expected.setResult(AtackStatus.INVALID); // Test that attacks can't be repeated.
        res = board.attack(8, 'E');
        assertSame(expected.getResult(), res.getResult());
    }

    @Test
    public void testAttackHitsAndSinks() {
        Board board = new Board();

        Result expected = new Result();
        expected.setResult(AtackStatus.HIT);

        List<Result> attacks = new ArrayList<Result>();  // Add a miss to the board.
        Result adder = new Result();
        adder.setResult(AtackStatus.MISS);
        adder.setLocation(new Square(1,'A'));
        attacks.add(adder);
        board.setAttacks(attacks);

        Result res = new Result();

        Ship ship = new Ship("MINESWEEPER"); // Put ship on board
        board.placeShip(ship,7, 'E', false);

        List<Ship> shiplist = board.getShips(); // Make sure ship is on board
        Ship checkship = shiplist.get(0);
        Square l = checkship.getOccupiedSquares().get(1);
        assertSame(7,l.getRow());
        assertSame('F', l.getColumn());


        res = board.attack(7, 'F');  // Test hits ship on board.
        assertSame(expected.getResult(), res.getResult());

        expected.setResult(AtackStatus.SUNK); // Tests that final blow sinks the ship.
        res = board.attack(7,'E');
        assertSame(expected.getResult(),res.getResult());

        expected.setResult(AtackStatus.INVALID); // Test that attacks can't be repeated.
        res = board.attack(7, 'F');
        assertSame(expected.getResult(), res.getResult());
    }
    @Test
    public void testShipAlreadyPlaced(){
        Board board = new Board();
        Ship ship = new Ship("MINESWEEPER"); // Put ship on board
        board.placeShip(ship,7, 'E', false);
        Ship ship2 = new Ship("MINESWEEPER"); // create ship2
        assertFalse(board.placeShip(ship2,8, 'C', true));//since ship of same type already placed
                                                                        // on board should return false


    }
    @Test
    public void testPlaceShipOfEveryType(){
        Board board = new Board();
        Ship ship = new Ship("MINESWEEPER"); // Put ship on board
        board.placeShip(ship,7, 'E', false);
        Ship ship2 = new Ship("DESTROYER"); // create ship2
        Ship ship3 = new Ship("BATTLESHIP"); // create ship2
        assertTrue(board.placeShip(ship2,5, 'C', true));//should ne able to place a ship of a new type;
        assertTrue(board.placeShip(ship3,3,'B',true));//should be able to place a ship of a new kind;
    }
    @Test
    public void testPlaceShipOfNewTypeAfterFailingPlacement(){
        Board board = new Board();
        Ship ship = new Ship("MINESWEEPER"); // Put ship on board
        board.placeShip(ship,7, 'E', false);
        Ship ship2 = new Ship("MINESWEEPER"); // create ship2
        Ship ship3 = new Ship("DESTROYER"); // create ship2
        assertFalse(board.placeShip(ship2,5, 'C', true));//shouldn't be able to place a ship of same kind
        assertTrue(board.placeShip(ship3,5,'C',true));//should be able to place a ship of a new kind;
    }
}
