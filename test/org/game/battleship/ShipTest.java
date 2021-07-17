package org.game.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {
    @Test
    public void newShipIsValid() {
        String shipName = "DESTROYER";
        Ship ship = new Ship(shipName, 5);

        assertEquals(shipName, ship.getName());
        assertEquals(5, ship.getSize());
        assertFalse(ship.isSunk());
        assertTrue(ship.isAfloat());
    }

    @Test
    public void placeShipOnCoordinatesVerticallyAndCheckEndLocation() {
        Ship ship = new Ship("DESTROYER", 5);

        Coordinate coordinates = new Coordinate(10,17);
        ship.setLocation(coordinates, Orientation.Vertical);

        assertEquals(new Coordinate(10,21), ship.getEndLocation());
    }


    @Test
    public void placeShipOnCoordinatesHorizontallyAndCheckEndLocation() {
        Ship ship = new Ship("DESTROYER", 4);

        Coordinate coordinates = new Coordinate(10,17);
        ship.setLocation(coordinates, Orientation.Horizontal);

        assertEquals(new Coordinate(13,17), ship.getEndLocation());
    }

    @Test
    public void ensureShipOfSize1Works() {
        Ship ship = new Ship("DESTROYER", 1);

        Coordinate coordinates = new Coordinate(10,4);
        ship.setLocation(coordinates, Orientation.Horizontal);

        assertEquals(new Coordinate(10,4), ship.getEndLocation()); //Same as passed coordinate
    }

    @Test
    public  void shouldReturnTrueIfShotAt(){
        Ship ship = new Ship("battleship", 3);
        ship.setLocation(new Coordinate(1,3), Orientation.Horizontal);
        assertFalse(ship.isSunk());

        assertTrue(ship.shootAt(new Coordinate(1,3)));
        assertTrue(ship.shootAt(new Coordinate(2,3)));

    }

    @Test
    public  void shouldReturnFalseIfNotShotAt(){
        Ship ship = new Ship("battleship", 3);
        ship.setLocation(new Coordinate(1,3), Orientation.Horizontal);
        assertFalse(ship.isSunk());

        assertFalse(ship.shootAt(new Coordinate(1,7)));
        assertTrue(ship.shootAt(new Coordinate(2,3)));
    }

    @Test
    public  void shouldSinkIfAllBlocksAreShot(){
        Ship ship = new Ship("battleship", 3);
        ship.setLocation(new Coordinate(1,3), Orientation.Horizontal);
        assertFalse(ship.isSunk());

        assertTrue(ship.shootAt(new Coordinate(1,3)));
        assertTrue(ship.shootAt(new Coordinate(2,3)));
        assertFalse(ship.isSunk()); //Still not sunk
        assertTrue(ship.shootAt(new Coordinate(3,3)));
        assertTrue(ship.isSunk()); //Now its sunk
    }

    @Test
    public  void shouldSinkIfAllBlocksAreShotForShipSize1(){
        Ship ship = new Ship("miniship", 1);
        ship.setLocation(new Coordinate(10,3), Orientation.Horizontal);
        assertFalse(ship.isSunk());

        assertTrue(ship.shootAt(new Coordinate(10,3)));
        assertTrue(ship.isSunk()); //Now its sunk
    }

    @Test
    public void shipNotSunkWhenAllShotsAreFiredOutsideCoordinates(){
        Ship ship = new Ship("ship4", 4);
        ship.setLocation(new Coordinate(10,3), Orientation.Vertical);
        ship.shootAt(new Coordinate(20,21));
        ship.shootAt(new Coordinate(20,22));
        ship.shootAt(new Coordinate(10,23));
        ship.shootAt(new Coordinate(10,24));

        assertFalse(ship.isSunk());
        assertTrue(ship.isAfloat());
    }
}