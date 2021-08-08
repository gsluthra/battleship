package org.game.battleship;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        System.out.println("Battleship Game");
        System.out.println("---------------------");

        GameBoard board = new GameBoard(10,10);
        randomlyPlaceShipsOnBoard(board, 4);
        board.printBoard();
        randomlyShootOnBoardUntilMaxTriesDoneOrShipsSunk(board);
        System.out.println("--- GAME OVER ---");
        board.printBoard();
    }

    private static void randomlyShootOnBoardUntilMaxTriesDoneOrShipsSunk(GameBoard board) {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int x = 1+random.nextInt(board.length()-1);
            int y = 1+random.nextInt(board.height()-1);
            Coordinate c = new Coordinate(x,y);
            try {
                boolean hit = board.shootAt(c);
                System.out.println("Shooting at: "+ c + " STATUS = "+ ((hit)?"-":"HIT"));
            } catch (IllegalMoveException ex) {
                System.out.println(ex.getMessage());
            }
            if(board.getNumberOfShipsAfloat()<=0)
                break;
        }

    }

    private static void randomlyPlaceShipsOnBoard(GameBoard board, int numberOfShipsToPlace) {
        int maxTries = 0;
        for(int i=1; i<= numberOfShipsToPlace && maxTries <100; i++, maxTries++){
            Ship ship = new Ship("Ship-"+i, getRandomSize());
            Orientation randomOrientation = getRandomOrientation();
            Coordinate randomCoordinates = randomLocation(board.length(), board.height(), randomOrientation, ship.size());
            System.out.println("Generated Random ar Try: "+ maxTries + ": "+ randomCoordinates );
            try {
                board.placeShip(randomCoordinates, randomOrientation, ship);
            } catch (ShipPlacementOnBoardException ex) {
                i--; //try again
                maxTries++;
                System.out.println(ex.getMessage());
            }
        }
    }

    private static int getRandomSize() {
        Random random = new Random();
        return 2+ random.nextInt(4);
    }

    private static Orientation getRandomOrientation() {
        Random random = new Random();
        return (random.nextBoolean())? Orientation.Horizontal: Orientation.Vertical;
    }

    private static Coordinate randomLocation(int maxLength, int maxHeight, Orientation orientation, int sizeOfShip) {
        Random random = new Random();
        if(orientation == Orientation.Vertical)
            return new Coordinate(1+random.nextInt(maxLength-1), 1+random.nextInt(maxHeight-1-sizeOfShip));
        else
            return new Coordinate(1+random.nextInt(maxLength-1-sizeOfShip), 1+random.nextInt(maxHeight-1));
    }
}
