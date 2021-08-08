package org.game.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {
    @Test
    public void negativeCoordinateXThrowsException() {
        assertThrows(RuntimeException.class, () -> {
                new Coordinate(-10,15);
        });
    }

    @Test
    public void negativeCoordinateYThrowsException() {
        assertThrows(RuntimeException.class, () -> {
            new Coordinate(10,-15);
        });
    }

    @Test
    public void checkValidCoordinatesCreated() {
        Coordinate c = new Coordinate(10, 15);
        assertEquals(10, c.getX());
        assertEquals(15, c.getY());
    }

    @Test
    public void addCoordinateWithYIncrement() {
        Coordinate c = new Coordinate(10, 15);
        Coordinate newCoordinate = c.incrementY();
        assertEquals(new Coordinate(10, 16),newCoordinate);
    }

    @Test
    public void addCoordinateWithXIncrement() {
        Coordinate c = new Coordinate(10, 15);
        Coordinate newCoordinate = c.incrementX();
        assertEquals(new Coordinate(11,15),newCoordinate);
    }
}