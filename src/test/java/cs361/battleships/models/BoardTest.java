package cs361.battleships.models;

import org.apache.commons.io.input.BOMInputStream;
import org.junit.Test;

import javax.validation.constraints.AssertFalse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testSinkBattleship(){
        Board board = new Board();
        Ship b = new Battleship();

        board.placeShip(b, 1,'A', false);

        Result res= new Result();
        Result expected= new Result();


        expected.setResult(AttackStatus.CQHIT);
        // hit CQ
        board.attack(1,'C',false);
        res.setResult(b.getHitSquares().get(0).getResult());
        assertEquals(expected.getResult(), res.getResult());

        //Hit CQ again should sink
        board.attack(1,'C',false);
        res.setResult(b.getHitSquares().get(3).getResult());

        expected.setResult(AttackStatus.SUNK);
        assertEquals(expected.getResult(), res.getResult());

    }
    @Test
    public void testShipMoveOffBoardLeft(){
        Board board = new Board();
        Ship ship = new Destroyer();
        board.placeShip(ship, 1, 'A', false);
        assertFalse(board.moveShipLeft(ship));
    }

    @Test
    public void testShipMoveOffBoardRight(){
        Board board = new Board();
        Ship ship = new Destroyer();
        board.placeShip(ship, 1, 'H', false);
        assertFalse(board.moveShipRight(ship));
    }

    @Test
    public void testShipMoveOffBoardTop(){
        Board board = new Board();
        Ship ship = new Destroyer();
        board.placeShip(ship, 1, 'A', true);
        assertFalse(board.moveShipUp(ship));
    }

    @Test
    public void testShipMoveOffBoardBottom(){
        Board board = new Board();
        Ship ship = new Destroyer();
        board.placeShip(ship, 8, 'A', true);
        assertFalse(board.moveShipDown(ship));
    }

    @Test
    public void testShipMoveCollisionsHorizontal(){
        Board board = new Board();
        Ship ship = new Destroyer();
        Ship m = new Minesweeper();
        board.placeShip(ship, 8, 'A', true);
        board.placeShip(m, 8,'B', false );

        assertFalse(board.moveShipLeft(m));
        assertFalse(board.moveShipRight(ship));
    }
    @Test
    public void testShipMoveCollisionsVertical(){
        Board board = new Board();
        Ship ship = new Destroyer();
        Ship m = new Minesweeper();
        board.placeShip(ship, 8, 'A', true);
        board.placeShip(m, 7,'A', false );

        assertFalse(board.moveShipDown(m));
        assertFalse(board.moveShipUp(ship));
    }
    @Test
    public void testShipsWillCollide(){
        Board board= new Board();
        Ship ship = new Destroyer();
        Ship m = new Minesweeper();
        board.placeShip(ship, 8, 'A', true);
        board.placeShip(m, 7,'A', false );

        assertTrue(board.shipWillCollide(8,'A',m));
        assertTrue(board.shipWillCollide(7,'A',ship));

    }
     @Test
    public void testShipsWillCollideSubmergedFirst(){
        Board board= new Board();
        Ship sub= new Submarine(true);
        Ship ship = new Destroyer();
        Ship m = new Minesweeper();
        board.placeShip(sub, 9, 'E', false);
        board.placeShip(ship, 8, 'A', true);
        board.placeShip(m, 7,'A', false );

        assertTrue(board.shipWillCollide(8,'A',m));
        assertTrue(board.shipWillCollide(7,'A',ship));
        assertFalse(board.shipWillCollide(9,'E',ship));
        assertFalse(board.shipWillCollide(9,'E',m));
        assertFalse(board.shipWillCollide(8,'A',sub));
    }
    @Test
    public void testValidShipMove(){
        Board board= new Board();
        Ship ship = new Destroyer();
        Ship m = new Minesweeper();
        board.placeShip(ship, 8, 'A', true);
        board.placeShip(m, 7,'A', false );

        assertTrue(board.moveShipRight(ship));
        assertTrue(board.moveShipUp(m));
    }

    @Test
    public void testSubmergedCollisionTrue(){
        Board board= new Board();
        Ship ship = new Destroyer();
        Ship sub = new Submarine(true);
        board.placeShip(sub, 5, 'C', true);
        board.placeShip(ship, 5,'D', false);

        assertTrue(board.moveShipLeft(ship));
        assertTrue(board.moveShipRight(sub));
    }

    @Test
    public void testSubmergedCollisionFalse(){
        Board board= new Board();
        Ship ship = new Destroyer();
        Ship sub = new Submarine(false);
        board.placeShip(sub, 5, 'C', true);
        board.placeShip(ship, 5,'D', false);

        assertFalse(board.moveShipLeft(ship));
        assertFalse(board.moveShipRight(sub));
    }

    @Test
    public void testMoveFleetSuccess(){
        Board board= new Board();
        Ship ship = new Destroyer();
        Ship sub = new Submarine(false);
        board.placeShip(sub, 5, 'C', true);
        board.placeShip(ship, 5,'D', false);

        // attack ships
        board.attack(5, 'D', false);

        board.moveFleet('u');
        assertTrue(ship.getOccupiedSquares().get(0).getRow()== 4);
        assertTrue(sub.getOccupiedSquares().get(0).getRow()== 4);

        // check if hitSquares moved properly
        assertTrue(ship.getHitSquares().get(0).getLocation().getRow() == 4);
    }

    @Test
    public void testMoveFleetAgainstBorder(){
        Board board = new Board();
        Ship d = new Destroyer();
        Ship b = new Battleship();
        Ship m = new Minesweeper();
        Ship s = new Submarine(true);
        board.placeShip(d, 1, 'A', false);
        board.placeShip(b, 2,'A', false);
        board.placeShip(m, 4,'A', false);
        board.placeShip(s, 3,'A', false);

        // attack three ships once
        board.attack(1, 'A', false);
        board.attack(2, 'A', false);
        board.attack(4, 'B', false); // don't hit CQ

        board.moveFleet('u');
        assertTrue(d.getOccupiedSquares().get(0).getRow()== 1);
        assertTrue(b.getOccupiedSquares().get(0).getRow()== 2);
        assertTrue(m.getOccupiedSquares().get(0).getRow()== 3);
        assertTrue(s.getOccupiedSquares().get(0).getRow()== 2);

        // check if hitSquares moved properly
        assertTrue(d.getHitSquares().get(0).getLocation().getRow() == 1);
        assertTrue(b.getHitSquares().get(0).getLocation().getRow() == 2);
        assertTrue(m.getHitSquares().get(0).getLocation().getRow() == 3);
        // check if hitSquares moved properly
        assertTrue(d.getHitSquares().get(0).getResult() == AttackStatus.HIT);
        assertTrue(b.getHitSquares().get(0).getResult() == AttackStatus.HIT);
        assertTrue(m.getHitSquares().get(0).getResult() == AttackStatus.HIT);

        // check if boards hit results didn't move
        assertTrue(board.getAttacks().get(0).getLocation().getRow()==1);
        assertTrue(board.getAttacks().get(1).getLocation().getRow()==2);
        assertTrue(board.getAttacks().get(2).getLocation().getRow()==4);//important One!

        // check if boards attacks results  are Hits
        assertTrue(board.getAttacks().get(0).getResult() == AttackStatus.HIT);
        assertTrue(board.getAttacks().get(1).getResult() == AttackStatus.HIT);
        assertTrue(board.getAttacks().get(2).getResult() == AttackStatus.HIT);
    }

    @Test
    public void testAttacksOnMovedFleet(){
        Board board = new Board();
        Ship d = new Destroyer();
        Ship b = new Battleship();
        Ship m = new Minesweeper();
        Ship s = new Submarine(true);
        board.placeShip(d, 1, 'A', false);
        board.placeShip(b, 2,'A', false);
        board.placeShip(m, 4,'A', false);
        board.placeShip(s, 6,'A', false);

        // attack destoryer cq before moving;
        board.attack(1,'B', false);
        assertTrue(d.getHitSquares().get(0).getResult().equals(AttackStatus.CQHIT));

        board.moveFleet('u');
        assertTrue(d.getOccupiedSquares().get(0).getRow()== 1);
        assertTrue(b.getOccupiedSquares().get(0).getRow()== 2);
        assertTrue(m.getOccupiedSquares().get(0).getRow()== 3);
        assertTrue(s.getOccupiedSquares().get(0).getRow()== 5);

        // attack three moved ships once
        board.attack(1, 'A', false);
        board.attack(2, 'A', false);
        board.attack(3, 'B', false); // don't hit CQ

        // check if attacks hit on the moved ship properly
        assertTrue(d.getHitSquares().get(1).getResult() == AttackStatus.HIT);
        assertTrue(b.getHitSquares().get(0).getResult() == AttackStatus.HIT);
        assertTrue(m.getHitSquares().get(0).getResult() == AttackStatus.HIT);

        // attack cq of minesweeper should sink
        board.attack(3,'A', false);
        //check for sink
        assertTrue(m.getHitSquares().get(1).getResult().equals(AttackStatus.SUNK));

        // attack cq of destroyer ship 2nd time should  sink
        board.attack(1,'B', false);
        assertTrue(d.getHitSquares().get(2).getResult().equals(AttackStatus.SUNK));


        // attack cq of battle ship should return cq hit
        board.attack(2,'C', false);
        assertTrue(b.getHitSquares().get(1).getResult().equals(AttackStatus.CQHIT));
        // attack cq of battle ship 2nd time should  sink
        board.attack(2,'C', false);
        assertEquals(AttackStatus.SUNK, b.getHitSquares().get(3).getResult());

        //attack submarine
        board.attack(5, 'D', false);
        assertEquals(AttackStatus.CQHIT, s.getHitSquares().get(0).getResult());
        //attack submarine to sink
        board.attack(5, 'D', false);
        assertEquals(AttackStatus.SURRENDER, s.getHitSquares().get(4).getResult());

    }


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
        Result res = board.attack(-3, 'R', false);

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

        Result res = board.attack(1, 'A', false);

        Result expected = new Result();
        expected.setResult(AttackStatus.MISS);

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

        Result res = board.attack(2, 'C', false);  // Test miss with no ships on
        assertSame(expected.getResult(), res.getResult());

        Ship ship = new Minesweeper();
        board.placeShip(ship, 7, 'E', false);

        res = board.attack(2, 'D', false);  // Test miss w ship on board.
        assertSame(expected.getResult(), res.getResult());

        expected.setResult(AttackStatus.MISS); // Test that attacks can't be repeated.
        res = board.attack(2, 'D', false);
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


        res = board.attack(8, 'E', false);  // Test hits ship on board.
        assertSame(expected.getResult(), res.getResult());

        // Test that attacks can be repeated.
        res = board.attack(8, 'E', false);
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


        res = board.attack(7, 'F', false);  // Test hits ship on board.
        assertSame(expected.getResult(), res.getResult());

        expected.setResult(AttackStatus.SUNK); // Tests that final blow sinks the ship.
        res = board.attack(7, 'E', false);
        assertSame(expected.getResult(), res.getResult());

        expected.setResult(AttackStatus.HIT);// Test that attacks can be repeated.
        res = board.attack(7, 'F', false);
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
        Ship ship4 = new Submarine(true);
        assertTrue(board.placeShip(ship2, 5, 'C', true));//should ne able to place a ship of a new type;
        assertTrue(board.placeShip(ship3, 3, 'B', true));//should be able to place a ship of a new kind;
        assertTrue(board.placeShip(ship4, 3, 'B', true));
        assertTrue(board.getShips().get(3).getOccupiedSquares().get(4).getColumn() == 'C');
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
        Ship shipS = new Submarine(false);
        board.placeShip(shipS, 5, 'A', false);

        List<Ship> shiplist = board.getShips(); // Make sure ship is on board

        // Attack all ships to death
        board.attack(1, 'A', false);
        board.attack(1, 'B',false);

        board.attack(2, 'A',false);
        board.attack(2, 'B',false);
        board.attack(2, 'B',false);
        board.attack(2, 'C',false);

        board.attack(5, 'A',false);
        board.attack(5, 'B',false);
        board.attack(5, 'B',false);
        board.attack(5, 'C',false);
        board.attack(5, 'D', false);
        board.attack(5, 'D', false);
        board.attack(6, 'D', false);


        board.attack(3, 'A',false);
        board.attack(3, 'B',false);
        board.attack(3, 'C',false);
        board.attack(3, 'D',false);
        Result res = board.attack(3, 'C',false);

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
        Battleship b = new Battleship();
        b.addHit(placing);
        board.getShips().add(b);

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
        res = board.attack(1, 'A',false);
        assertSame(expected.getResult(), res.getResult());

        res = board.attack(2, 'B',false);
        res = board.attack(2, 'B',false);
        assertSame(expected.getResult(), res.getResult());


        expected.setResult(AttackStatus.SUNK);
        board.attack(3, 'C',false);
        res = board.attack(3, 'C',false);

        assertSame(expected.getResult(), res.getResult());
    }

    @Test
    public void  testCQReturnsMissMarksHit() {
        Board board = new Board();

        Result expected = new Result();
        expected.setResult(AttackStatus.CQHIT);

        Ship shipD = new Destroyer(); // Put ship on board
        board.placeShip(shipD, 2, 'A', false);


        List<Ship> shiplist = board.getShips(); // Make sure ship is on board

        Result res;

        res = board.attack(2, 'B',false); // Attack CQ and make sure its a miss
        assertSame(expected.getResult(), res.getResult());

        assertSame(board.getAttacks().get(0).getResult(), AttackStatus.CQHIT); // Make sure its actually a CQHIT on backend

        expected.setResult(AttackStatus.SUNK); // Make sure second hit is a sunk
        res = board.attack(2, 'B',false);
        assertSame(expected.getResult(), res.getResult());
    }
    @Test
    public void testSonarOnEmptyBoard() {
        Board board = new Board();
        List<Result> hardResults= new ArrayList<Result>();// hard coded list of expected results;

        Result r1= new Result();
        r1.setLocation(new Square(5,'c') );
        r1.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r1);

        Result r2= new Result();
        r2.setLocation(new Square(4,'D') );
        r2.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r2);

        Result r3= new Result();
        r3.setLocation(new Square(5,'D') );
        r3.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r3);

        Result r4= new Result();
        r4.setLocation(new Square(6,'D') );
        r4.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r4);


        Result r5= new Result();
        r5.setLocation(new Square(3,'E') );
        r5.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r5);

        Result r6= new Result();
        r6.setLocation(new Square(4,'E') );
        r6.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r6);

        Result r7= new Result();
        r7.setLocation(new Square(5,'E') );
        r7.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r7);

        Result r8= new Result();
        r8.setLocation(new Square(6,'E') );
        r8.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r8);

        Result r9= new Result();
        r9.setLocation(new Square(7,'E') );
        r9.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r9);

        Result r10= new Result();
        r10.setLocation(new Square(4,'F') );
        r10.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r10);

        Result r11= new Result();
        r11.setLocation(new Square(5,'F') );
        r11.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r11);

        Result r12= new Result();
        r12.setLocation(new Square(6,'F') );
        r12.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r12);

        Result r13= new Result();
        r13.setLocation(new Square(5,'G') );
        r13.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r13);

        board.attack(5,'E',true);
        for (Result hardResult : hardResults){
          for (Result boardResult : board.getAttacks()) {
              if(hardResult.getLocation().getColumn()==boardResult.getLocation().getColumn() && hardResult.getLocation().getRow()==boardResult.getLocation().getRow()){
                  assertSame(hardResult.getResult(),boardResult.getResult());
              }
              assertFalse(boardResult.getLocation().getRow()<3 && boardResult.getLocation().getColumn()<'C');
              assertFalse(boardResult.getLocation().getRow()>7 && boardResult.getLocation().getColumn()>'G');
          }
        }
        // 13 sonar squares
        assertEquals(13, board.getAttacks().size());
    }
    @Test
    public void testSonarOnBoardWithShips() {
        Board board = new Board();
        List<Result> hardResults= new ArrayList<Result>();// hard coded list of expected results;

        Ship shipM = new Minesweeper(); // Put ship on board
        board.placeShip(shipM, 5, 'C', false);

        Result r1= new Result();
        r1.setLocation(new Square(5,'c') );
        r1.setResult(AttackStatus.SONAR_OCCUPIED);
        hardResults.add(r1);

        Result r2= new Result();
        r2.setLocation(new Square(4,'D') );
        r2.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r2);

        Result r3= new Result();
        r3.setLocation(new Square(5,'D') );
        r3.setResult(AttackStatus.SONAR_OCCUPIED);
        hardResults.add(r3);

        Result r4= new Result();
        r4.setLocation(new Square(6,'D') );
        r4.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r4);


        Result r5= new Result();
        r5.setLocation(new Square(3,'E') );
        r5.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r5);

        Result r6= new Result();
        r6.setLocation(new Square(4,'E') );
        r6.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r6);

        Result r7= new Result();
        r7.setLocation(new Square(5,'E') );
        r7.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r7);

        Result r8= new Result();
        r8.setLocation(new Square(6,'E') );
        r8.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r8);

        Result r9= new Result();
        r9.setLocation(new Square(7,'E') );
        r9.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r9);

        Result r10= new Result();
        r10.setLocation(new Square(4,'F') );
        r10.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r10);

        Result r11= new Result();
        r11.setLocation(new Square(5,'F') );
        r11.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r11);

        Result r12= new Result();
        r12.setLocation(new Square(6,'F') );
        r12.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r12);

        Result r13= new Result();
        r13.setLocation(new Square(5,'G') );
        r13.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r13);

        board.attack(5,'E',true);

        for (Result hardResult : hardResults){
          for (Result boardResult : board.getAttacks()) {
              if(hardResult.getLocation().getColumn()==boardResult.getLocation().getColumn() && hardResult.getLocation().getRow()==boardResult.getLocation().getRow()){
                  assertSame(hardResult.getResult(),boardResult.getResult());
              }
              assertFalse(boardResult.getLocation().getRow()<3 && boardResult.getLocation().getColumn()<'C');
              assertFalse(boardResult.getLocation().getRow()>7 && boardResult.getLocation().getColumn()>'G');
          }
        }
        // 13 sonar squares
        assertEquals(13, board.getAttacks().size());

    }
    @Test
    public void testSonarOnAlreadyGuessedSpot() {
        Board board = new Board();
        List<Result> hardResults= new ArrayList<Result>();// hard coded list of expected results;

        Ship shipD = new Destroyer(); // Put ship on board
        board.placeShip(shipD, 5, 'C', false);
        board.attack(5,'C',false);
        assertSame(AttackStatus.INVALID,board.attack(5,'C',true).getResult());
    }
     @Test
    public void testSonarDoesNotOverWrightOldAttacks() {
        Board board = new Board();
        List<Result> hardResults= new ArrayList<Result>();// hard coded list of expected results;

        Ship shipM = new Minesweeper(); // Put ship on board
        board.placeShip(shipM, 5, 'C', false);
        board.attack(5,'C',false);
         board.attack(5,'F',false);
        Result r1= new Result();
        r1.setLocation(new Square(5,'c') );
        r1.setResult(AttackStatus.SUNK);
        hardResults.add(r1);

        Result r2= new Result();
        r2.setLocation(new Square(4,'D') );
        r2.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r2);

        Result r3= new Result();
        r3.setLocation(new Square(5,'D') );
        r3.setResult(AttackStatus.HIT);
        hardResults.add(r3);

        Result r4= new Result();
        r4.setLocation(new Square(6,'D') );
        r4.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r4);


        Result r5= new Result();
        r5.setLocation(new Square(3,'E') );
        r5.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r5);

        Result r6= new Result();
        r6.setLocation(new Square(4,'E') );
        r6.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r6);

        Result r7= new Result();
        r7.setLocation(new Square(5,'E') );
        r7.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r7);

        Result r8= new Result();
        r8.setLocation(new Square(6,'E') );
        r8.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r8);

        Result r9= new Result();
        r9.setLocation(new Square(7,'E') );
        r9.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r9);

        Result r10= new Result();
        r10.setLocation(new Square(4,'F') );
        r10.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r10);

        Result r11= new Result();
        r11.setLocation(new Square(5,'F') );
        r11.setResult(AttackStatus.MISS);
        hardResults.add(r11);

        Result r12= new Result();
        r12.setLocation(new Square(6,'F') );
        r12.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r12);

        Result r13= new Result();
        r13.setLocation(new Square(5,'G') );
        r13.setResult(AttackStatus.SONAR_EMPTY);
        hardResults.add(r13);

        board.attack(5,'E',true);

        for (Result hardResult : hardResults){
          for (Result boardResult : board.getAttacks()) {
              if(hardResult.getLocation().getColumn()==boardResult.getLocation().getColumn() && hardResult.getLocation().getRow()==boardResult.getLocation().getRow()){
                  assertSame(hardResult.getResult(),boardResult.getResult());
              }
              assertFalse(boardResult.getLocation().getRow()<3 && boardResult.getLocation().getColumn()<'C');
              assertFalse(boardResult.getLocation().getRow()>7 && boardResult.getLocation().getColumn()>'G');
          }
        }

        // 13 sonar squares
        assertEquals(13, board.getAttacks().size());
    }

    @Test
    public void testHitSquares() {
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
        res = board.attack(1, 'A',false);
        assertSame(expected.getResult(), shipM.getHitSquares().get(1).getResult());

        res = board.attack(2, 'B',false);
        expected.setResult(AttackStatus.CQHIT);
        assertSame(expected.getResult(), shipD.getHitSquares().get(0).getResult());
        expected.setResult(AttackStatus.SUNK);
        res = board.attack(2, 'B',false);
        assertSame(expected.getResult(), shipD.getHitSquares().get(2).getResult());

        expected.setResult(AttackStatus.HIT);
        res = board.attack(3,'A',false);
        assertSame(expected.getResult(), shipB.getHitSquares().get(0).getResult());

        expected.setResult(AttackStatus.SUNK);
        board.attack(3, 'C',false);
        res = board.attack(3, 'C',false);

        assertSame(expected.getResult(), shipB.getHitSquares().get(3).getResult());
    }

    @Test
    public void testSideOffBoard() {
        Board board = new Board();
        Ship ship = new Submarine(false); // Put ship on board
        assertFalse(board.placeShip(ship, 10, 'E', false));

        Ship ship2 = new Submarine(true); // create ship2
        Ship ship3 = new Destroyer(); // create ship2

        assertTrue(board.placeShip(ship3, 5, 'C', false));//should be able to place a ship of a new kind;
        assertFalse(board.placeShip(ship, 4, 'C', false));
    }

    @Test
    public void testDoubleHit() {
        Board board = new Board();
        Ship shipS = new Submarine(true); // Put ship on board
        Ship shipM = new Minesweeper();
        Ship shipD = new Destroyer();

        board.placeShip(shipM, 1, 'A', false);
        board.placeShip(shipD, 5, 'C', false);
        board.placeShip(shipS, 5, 'D', false);

        board.attack(5, 'D', false);
        assertSame(1, board.getAttacks().size());
        assertSame(AttackStatus.CQHIT, board.getAttacks().get(0).getResult());
        assertSame(0, shipS.getHitSquares().size());
        board.attack(1, 'A', false);
        board.attack(5, 'D', false);
        assertSame(5, board.getAttacks().size());
        assertSame(AttackStatus.SUNK, board.getAttacks().get(4).getResult());
        assertSame(AttackStatus.HIT, shipS.getHitSquares().get(0).getResult());
    }

    @Test
    public void testDoublePlace() {
        Board board = new Board();
        Ship shipS = new Submarine(true); // Put ship on board
        Ship shipM = new Minesweeper();
        Ship shipD = new Destroyer();

        board.placeShip(shipM, 1, 'A', false);
        assertTrue(board.placeShip(shipS, 5, 'D', false));
        assertTrue(board.placeShip(shipD, 5, 'C', false));
    }

}

