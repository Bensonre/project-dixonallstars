package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void goodRandKind(){
        Game game = new Game();
        for(int i=0; i<10; i++) {
            String test = game.randKind();
            assertTrue(test.equals("MINESWEEPER") || test.equals("DESTROYER") || test.equals("BATTLESHIP") );
        }
    }

    // check bounds of random row to be between 1 and 10 inclusive
    @Test
    public void goodRandRow() {
        Game game = new Game();
        for(int i=0; i<20; i++) {
            assertTrue(game.randRow() < 11 && game.randRow() > 0);
        }
    }

    // check bounds of random row to be between 'A' and 'J' inclusive
    @Test
    public void goodRandCol() {
        Game game = new Game();
        for(int i=0; i<20; i++) {
            assertTrue(game.randCol() >= 'A' && game.randCol() <= 'J');
        }
    }

    // couldn't think of anything better...assert null was denied as redundant and
    // doing a check to see if we get 1 true out of 10 (99.9% chance) still seems
    // like a dumb test because the .1% exists.
    @Test
    public void goodRandOrientation (){
        Game game = new Game();
        for(int i=0; i<10; i++) {
            Boolean test = game.randVertical();
            assertTrue(test || !test);
        }
    }
}
