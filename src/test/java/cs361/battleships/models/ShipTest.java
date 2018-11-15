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
        Ship mine = new Minesweeper();
        assertTrue("MINESWEEPER"==mine.getKind());
        assertTrue(2 ==mine.getLength());
    }
    @Test
    public void testDestroyersetShip(){
        Ship destroyer = new Destroyer();
        assertTrue("DESTROYER"==destroyer.getKind());
        assertTrue(3 ==destroyer.getLength());
    }
    @Test
    public void testBattleShipsetShip(){
        Ship battleship = new Battleship();
        assertTrue("BATTLESHIP"==battleship.getKind());
        assertTrue(4 ==battleship.getLength());
    }
    @Test
    public void testSetOccupiedSquares(){
        Ship mine = new Minesweeper();
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
        Ship mine = new Minesweeper();
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
        Ship mine = new Destroyer();
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
        Ship mine = new Destroyer();
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
        Ship mine = new Battleship();
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
        Ship mine = new Battleship();
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

    @Test
    public void testSetAndHitCaptainsQuarters() {
        Board board = new Board();

        Ship mine = new Minesweeper();
        Ship battle = new Battleship();
        Ship destroyer = new Destroyer();

        assertTrue(board.placeShip(mine, 1, 'A', false));
        assertTrue(board.placeShip(destroyer, 2, 'A', false));
        assertTrue(board.placeShip(battle, 6, 'E', true));

        assertEquals(1, mine.getCaptainsQuarters().getRow());
        assertEquals('A', mine.getCaptainsQuarters().getColumn());
        assertFalse(mine.sunkCaptainsQuarters());
        mine.hitCaptainsQuarters();
        assertTrue(mine.sunkCaptainsQuarters());

        assertEquals(2, destroyer.getCaptainsQuarters().getRow());
        assertEquals('B', destroyer.getCaptainsQuarters().getColumn());
        assertFalse(destroyer.sunkCaptainsQuarters());
        destroyer.hitCaptainsQuarters();
        assertFalse(destroyer.sunkCaptainsQuarters());
        destroyer.hitCaptainsQuarters();
        assertTrue(destroyer.sunkCaptainsQuarters());

        assertEquals(8, battle.getCaptainsQuarters().getRow());
        assertEquals('E', battle.getCaptainsQuarters().getColumn());
        assertFalse(battle.sunkCaptainsQuarters());
        battle.hitCaptainsQuarters();
        assertFalse(battle.sunkCaptainsQuarters());
        battle.hitCaptainsQuarters();
        assertTrue(battle.sunkCaptainsQuarters());
    }

    @Test
    public void testGetSetHitSquares() {
        Ship mine = new Minesweeper();
        Result r = new Result();
        Square s = new Square(1, 'A');
        r.setResult(AttackStatus.HIT);
        r.setShip(mine);
        r.setLocation(s);

        Result r2 = new Result();
        Square s2 = new Square(1, 'A');

        r2.setResult(AttackStatus.HIT);
        r2.setLocation(s2);
        r2.setShip(mine);

        Result r3 = new Result();
        Square s3 = new Square(2, 'A');

        r3.setResult(AttackStatus.HIT);
        r3.setLocation(s3);
        r3.setShip(mine);

        mine.addHit(r);
        assertEquals(1, mine.getHitSquares().size());
        mine.removeHit(r3);
        assertEquals(1, mine.getHitSquares().size());
        mine.removeHit(r2);
        assertEquals(0, mine.getHitSquares().size());
    }

}
