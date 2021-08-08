package org.game.battleship.exceptions;

public class ShipPlacementOnBoardException extends Exception{
    public ShipPlacementOnBoardException(String message) {
        super(message);
    }
}
