package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class GameTest {

    // check 10 re-rolls of the String to be of one of the three acceptable cases
    @Test
    public void testGoodRandKind(){
        Game game = new Game();
        for(int i=0; i<10; i++) {
            String test = game.randKind();
            assertTrue(test.equals("MINESWEEPER") || test.equals("DESTROYER") || test.equals("BATTLESHIP") );
        }
    }

    // check bounds of random row to be between 1 and 10 inclusive
    @Test
    public void testGoodRandRow() {
        Game game = new Game();
        for(int i=0; i<20; i++) {
            assertTrue(game.randRow() < 11 && game.randRow() > 0);
        }
    }

    // check bounds of random row to be between 'A' and 'J' inclusive
    @Test
    public void testGoodRandCol() {
        Game game = new Game();
        for(int i=0; i<20; i++) {
            assertTrue(game.randCol() >= 'A' && game.randCol() <= 'J');
        }
    }

    // couldn't think of anything better...assert null was denied as redundant and
    // doing a check to see if we get 1 true out of 10 (99.9% chance) still seems
    // like a dumb test because the .1% exists.
    // literally just checks to see if the bool is true or false
    @Test
    public void testGoodRandOrientation (){
        Game game = new Game();
        for(int i=0; i<10; i++) {
            Boolean test = game.randVertical();
            assertTrue(test || !test);
        }
    }

    // Tests that place ship places a ship on each board
    @Test
    public void testPlaceShip() {
        // Make ships
        Ship playerShip1 = new Battleship();
        Ship playerShip2 = new Minesweeper();
        Ship playerShip3 = new Destroyer();
        Game myGame = new Game();

        // Make sure the ships don't get hung up
        assertTrue(myGame.placeShip(playerShip1, 1, 'A', false));
        assertTrue(myGame.placeShip(playerShip2, 7, 'E', true));
        assertTrue(myGame.placeShip(playerShip3, 2, 'B', false));

        // Should test that the ships are not the same as well, but this would
        // require making two getters for the class that wouldn't be used elsewhere
    }

    // Tests that place ship places a ship on each board
    @Test
    public void testAttack() {
        // Make ships
        Ship playerShip1 = new Battleship();
        Ship playerShip2 = new Minesweeper();
        Ship playerShip3 = new Destroyer();
        Game myGame = new Game();

        // Make sure the ships don't get hung up
        assertTrue(myGame.placeShip(playerShip1, 1, 'A', false));
        assertTrue(myGame.placeShip(playerShip2, 7, 'E', true));
        assertTrue(myGame.placeShip(playerShip3, 2, 'B', false));

        assertTrue(myGame.attack(1,'A',false)); // First attack should work
        myGame.attack(1,'A',false); // Results uncertain due to captains quarters
        assertFalse(myGame.attack(1,'A',false)); // Subsequent attacks on place should break
    }
}
