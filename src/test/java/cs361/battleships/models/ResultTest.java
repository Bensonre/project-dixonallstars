package cs361.battleships.models;

import org.junit.Test;
import static org.junit.Assert.assertSame;


public class ResultTest {

    @Test
    public void testResult() {
        Result resultObject = new Result();
        AtackStatus as = AtackStatus.HIT;

        resultObject.setResult(as);
        AtackStatus returned_as = resultObject.getResult();

        assertSame(as, returned_as);
    }

    @Test
    public void testShip() {
        Result resultObject = new Result();
        Ship ship = new Minesweeper();

        resultObject.setShip(ship);
        Ship returned_ship = resultObject.getShip();

        assertSame(ship, returned_ship);
    }

    @Test
    public void testLocation() {
        Result resultObject = new Result();
        Square square = new Square(1, 'a');

        resultObject.setLocation(square);
        Square returned_square = resultObject.getLocation();

        assertSame(square, returned_square);
    }
}
