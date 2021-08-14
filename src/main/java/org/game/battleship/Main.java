package org.game.battleship;

import org.game.battleship.exceptions.IllegalMoveException;
import org.game.battleship.exceptions.ShipPlacementOnBoardException;

import java.util.Random;

import static org.game.battleship.Colorize.inRed;
import static org.game.battleship.Colorize.inYellow;

public class Main {

    public static void main(String[] args) {
        System.out.println("Battleship Game");
        System.out.println("---------------------");

        GameBoard board = new GameBoard(10,10);
        randomlyPlaceShipsOnBoard(board, 4);
        BattleshipConsolePrinter printer = new BattleshipConsolePrinter(board);
        printer.printBoard();

        randomlyShootOnBoardUntilMaxTriesDoneOrShipsSunk(board);
        System.out.println("--- FINAL BOARD POSITION ---");
        printer.printBoardSummary();
        System.out.println("--- GAME OVER ---");
    }

    //------- ALL PRIVATE
    private static void randomlyPlaceShipsOnBoard(GameBoard board, int numberOfShipsToPlace) {
        int maxTries = 0;
        for(int i=1; i<= numberOfShipsToPlace && maxTries <100; i++, maxTries++){
            Ship ship = new Ship("Ship-"+i, getRandomSize());
            Orientation randomOrientation = getRandomOrientation();
            Coordinate randomCoordinates = randomLocation(board.length(), board.height(), randomOrientation, ship.size());
            System.out.println("Generated Random Coordinate For Ship Placement at Try: "+ maxTries + ": "+ randomCoordinates );
            try {
                board.placeShip(randomCoordinates, randomOrientation, ship);
            } catch (ShipPlacementOnBoardException ex) {
                i--; //try again
                maxTries++;
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void randomlyShootOnBoardUntilMaxTriesDoneOrShipsSunk(GameBoard board) {
        Random random = new Random();
        int MAX_TRIES = 50;
        for (int i = 0; i < MAX_TRIES; i++) {
            int x = 1+random.nextInt(board.length()-1);
            int y = 1+random.nextInt(board.height()-1);
            Coordinate c = new Coordinate(x,y);
            try {
                boolean hit = board.shootAt(c);
                System.out.println("["+(i+1)+"]"+"Shot at: "+ c + " STATUS = "+ ((hit)?inRed("Hit!"):inYellow("Missed")));
            } catch (IllegalMoveException ex) {
                System.out.println(ex.getMessage());
                i--; //If this move has already been done, then retry. For random algo, this will happen a lot!
            }
            if(board.getNumberOfShipsAfloat()<=0) //All Sunk
                break;
        }
    }

    private static Coordinate randomLocation(int maxLength, int maxHeight, Orientation orientation, int sizeOfShip) {
        Random random = new Random();
        if(orientation == Orientation.Vertical)
            return new Coordinate(1+random.nextInt(maxLength-1), 1+random.nextInt(maxHeight-1-sizeOfShip));
        else
            return new Coordinate(1+random.nextInt(maxLength-1-sizeOfShip), 1+random.nextInt(maxHeight-1));
    }

    private static Orientation getRandomOrientation() {
        Random random = new Random();
        return (random.nextBoolean())? Orientation.Horizontal: Orientation.Vertical;
    }

    private static int getRandomSize() {
        Random random = new Random();
        return 2+ random.nextInt(4);
    }
}
