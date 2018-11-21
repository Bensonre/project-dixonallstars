package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.Game;

public class MoveFleetGameAction {

    @JsonProperty
    private Game game;
    @JsonProperty
    private char direction;

    public Game getGame() {
        return game;
    }

    public char getActionDirection() {
        return direction;
    }
}