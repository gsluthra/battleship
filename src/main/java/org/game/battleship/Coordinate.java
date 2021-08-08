package org.game.battleship;

import java.util.Objects;

public class Coordinate {

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        validate(x,y);
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate c) {
        this.x = c.getX();
        this.y = c.getY();
    }

    private void validate(int x, int y) {
        if(x<0 || y<0)
            throw new RuntimeException("Invalid -ve co-ordinates: (" + x + "," + y + ")");
    }

    public Coordinate incrementY() {
        return new Coordinate(this.x, this.y+1);
    }

    public Coordinate incrementX() {
        return new Coordinate(this.x + 1, this.y);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
