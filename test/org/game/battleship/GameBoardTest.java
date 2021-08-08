package org.game.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    @Test
    public void createBoardOfSize20by21With0Ships(){
        GameBoard board = new GameBoard(20, 21);
        assertEquals(20,board.length());
        assertEquals(21,board.height());
        assertEquals(0,board.numberOfShipsAfloat());
    }

    @Test
    public void placeAShipsOnBoardAndEnsureTheyAreAfloat() throws ShipPlacementOnBoardException {
        GameBoard board = new GameBoard(20,20);
        Ship destroyerShip = new Ship("Destroyer", 4);
        board.placeShip(new Coordinate(1,1), Orientation.Horizontal, destroyerShip);
        assertEquals(1, board.numberOfShipsAfloat());

        Ship smallShip = new Ship("Small Ship", 2);
        board.placeShip(new Coordinate(8,8), Orientation.Vertical, smallShip);
        assertEquals(2, board.numberOfShipsAfloat());
    }

    @Test
    public void shipCannotBePlacedOutsideOfBoardForY(){
        GameBoard board = new GameBoard(5,5);
        Ship destroyerShip = new Ship("Destroyer", 4);

        assertThrows(ShipPlacementOnBoardException.class, () -> {
            board.placeShip(new Coordinate(1,6), Orientation.Horizontal, destroyerShip);
        });
    }

    @Test
    public void shipCannotBePlacedOutsideOfBoardForX(){
        GameBoard board = new GameBoard(5,4);
        Ship destroyerShip = new Ship("Destroyer", 4);

        assertThrows(ShipPlacementOnBoardException.class, () -> {
            board.placeShip(new Coordinate(6,3), Orientation.Horizontal, destroyerShip);
        });
    }

    @Test
    public void shipCannotBePlacedOnZeroX(){
        GameBoard board = new GameBoard(5,4);
        Ship destroyerShip = new Ship("Destroyer", 4);

        assertThrows(ShipPlacementOnBoardException.class, () -> {
            board.placeShip(new Coordinate(0,3), Orientation.Horizontal, destroyerShip);
        });
    }

    @Test
    public void shipCannotBePlacedOnZeroY(){
        GameBoard board = new GameBoard(5,4);
        Ship destroyerShip = new Ship("Destroyer", 4);

        assertThrows(ShipPlacementOnBoardException.class, () -> {
            board.placeShip(new Coordinate(1,0), Orientation.Horizontal, destroyerShip);
        });
    }

    @Test
    public void shipCannotBePlacedHorizontallySuchThatItGoesOffTheBoard(){
        GameBoard board = new GameBoard(10,5);
        Ship destroyerShip = new Ship("Destroyer", 4);

        assertThrows(ShipPlacementOnBoardException.class, () -> {
            board.placeShip(new Coordinate(8,1), Orientation.Horizontal, destroyerShip);
        });
    }

    @Test
    public void shipCannotBePlacedVerticallySuchThatItGoesOffTheBoard(){
        GameBoard board = new GameBoard(10,10);
        Ship destroyerShip = new Ship("Destroyer", 4);

        ShipPlacementOnBoardException ex = assertThrows(ShipPlacementOnBoardException.class, () -> {
            board.placeShip(new Coordinate(7,8), Orientation.Vertical, destroyerShip);
        });

        assertEquals("Ship Destroyer is not fitting within the board. End Y Coordinate = 12", ex.getMessage());
    }

    @Test
    public void shipsMustNotOverlap() {
        GameBoard board = new GameBoard(10,10);
        Ship ship1 = new Ship("Destroyer", 4);
        Ship ship2 = new Ship("LongShip", 5);

        ShipPlacementOnBoardException ex = assertThrows(ShipPlacementOnBoardException.class, () -> {
            board.placeShip(new Coordinate(1,1), Orientation.Horizontal, ship1);
            board.placeShip(new Coordinate(2, 1), Orientation.Vertical, ship2);
        });

        assertEquals("This new ship LongShip overlaps with existing ship: Destroyer", ex.getMessage());
    }

    @Test
    public void shipsMustNotOverlapEvenIfStartCoordinatesAreDifferent(){
        GameBoard board = new GameBoard(10,10);
        Ship ship1 = new Ship("Destroyer", 4);
        Ship ship2 = new Ship("LongShip", 5);

        ShipPlacementOnBoardException ex = assertThrows(ShipPlacementOnBoardException.class, () -> {
            board.placeShip(new Coordinate(3,5), Orientation.Horizontal, ship1);
            board.placeShip(new Coordinate(5, 5), Orientation.Vertical, ship2);
        });

        assertEquals("This new ship LongShip overlaps with existing ship: Destroyer", ex.getMessage());
    }

    @Test
    public void ships3MustNotOverlapEvenIfStartCoordinatesAreDifferent() throws ShipPlacementOnBoardException {
        GameBoard board = new GameBoard(10,20);
        Ship ship1 = new Ship("Destroyer", 4);
        Ship ship2 = new Ship("LongShip", 5);
        Ship ship3 = new Ship("SomeNiceShip", 5);

        board.placeShip(new Coordinate(3,5), Orientation.Horizontal, ship1);
        board.placeShip(new Coordinate(7, 7), Orientation.Vertical, ship2);

        ShipPlacementOnBoardException ex = assertThrows(ShipPlacementOnBoardException.class, () ->
                board.placeShip(new Coordinate(5, 5), Orientation.Vertical, ship3));

        assertEquals("This new ship SomeNiceShip overlaps with existing ship: Destroyer", ex.getMessage());
    }

    @Test
    public void shouldBeAbleToPlace4ShipsIfTheyDontOverlap() throws ShipPlacementOnBoardException {
        GameBoard board = new GameBoard(10,15);
        Ship ship1 = new Ship("Ship1", 4);
        Ship ship2 = new Ship("Ship2", 5);
        Ship ship3 = new Ship("Ship3", 5);
        Ship ship4 = new Ship("Ship4", 2);

        board.placeShip(new Coordinate(3,5), Orientation.Horizontal, ship1);
        board.placeShip(new Coordinate(7, 7), Orientation.Vertical, ship2);
        board.placeShip(new Coordinate(9, 1), Orientation.Vertical, ship3);
        board.placeShip(new Coordinate(1, 1), Orientation.Horizontal, ship4);

        board.shootAt(new Coordinate(3,5));
        board.shootAt(new Coordinate(4,5));
        board.shootAt(new Coordinate(5,5));
        board.shootAt(new Coordinate(6,5));
        board.shootAt(new Coordinate(9,2));
        board.shootAt(new Coordinate(9,3));
        board.shootAt(new Coordinate(9,4));

        board.printBoard();
    }

    @Test
    public void throwsErrorIfDuplicateShipName() throws ShipPlacementOnBoardException {
        GameBoard board = new GameBoard(10,10);
        Ship ship1 = new Ship("Destroyer", 4);
        Ship ship2 = new Ship("LongShip", 5);
        Ship ship3 = new Ship("DESTROYER", 3);

        board.placeShip(new Coordinate(1,1), Orientation.Horizontal, ship1);
        board.placeShip(new Coordinate(2, 2), Orientation.Horizontal, ship2);

        ShipPlacementOnBoardException ex = assertThrows(ShipPlacementOnBoardException.class, () ->
                board.placeShip(new Coordinate(3, 3), Orientation.Vertical, ship3));

        assertEquals("This new ship DESTROYER has same name as existing ship: Destroyer", ex.getMessage());
    }

    @Test
    public void shouldGiveTrueForCoordinateWhereShipIsPresent() throws ShipPlacementOnBoardException {
        GameBoard board = new GameBoard(10,10);
        Ship ship1 = new Ship("Destroyer", 4);
        board.placeShip(new Coordinate(1,1), Orientation.Horizontal, ship1);
        assertTrue(board.isShipPresent(new Coordinate(2,1)));
        assertFalse(board.isShipPresent(new Coordinate(5,5)));
    }

    @Test
    public void shouldSayShipNotShot() throws ShipPlacementOnBoardException {
        GameBoard board = new GameBoard(10,10);
        Ship ship1 = new Ship("Destroyer", 4);
        board.placeShip(new Coordinate(1,1), Orientation.Horizontal, ship1);
        assertFalse(board.shootAt(new Coordinate(2,2)));
    }

    @Test
    public void shouldSayShipShot() throws ShipPlacementOnBoardException {
        GameBoard board = new GameBoard(10,10);
        Ship ship1 = new Ship("Destroyer", 4);
        board.placeShip(new Coordinate(1,1), Orientation.Horizontal, ship1);
        assertTrue(board.shootAt(new Coordinate(2,1)));
    }

    @Test
    public void shouldSayShipIsSunkIfFullyBombed() throws ShipPlacementOnBoardException {
        GameBoard board = new GameBoard(10,10);
        Ship ship = new Ship("Destroyer", 4);
        board.placeShip(new Coordinate(1,1), Orientation.Horizontal, ship);

        assertTrue(board.shootAt(new Coordinate(1,1)));
        assertTrue(board.shootAt(new Coordinate(2,1)));
        assertTrue(board.shootAt(new Coordinate(3,1)));
        assertTrue(board.shootAt(new Coordinate(4,1)));

        assertTrue(ship.isSunk());
    }


}