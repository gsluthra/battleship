package org.game.battleship;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Ship {
    private final String name;
    private final int size;
    private boolean isAfloat;
    private ArrayList<ShipBlock> shipBlocks;

    public Ship(String shipName, int sizeOfShip) {
        this.name = shipName;
        this.size = sizeOfShip;
        this.isAfloat = true;
    }

    public void setLocation(Coordinate startingCoordinate, Orientation orientation) {
        shipBlocks = new ArrayList<>();
        Coordinate tempC = new Coordinate(startingCoordinate);

        shipBlocks.add(new ShipBlock(tempC)); //First coordinate is same as what is passed.

        for (int i = 1; i < size; i++) {
            tempC = (orientation == Orientation.Vertical) ? (tempC.incrementY()) : (tempC.incrementX());
            shipBlocks.add(new ShipBlock(tempC));
        }
    }


    public boolean isSunk() {

        return !isAfloat;
    }

    public boolean isAfloat() {
        return isAfloat;
    }

    public Coordinate getEndLocation() {
        return new Coordinate(shipBlocks.get(size - 1).coordinate);
    }

    public boolean shootAt(Coordinate coordinate) {

        for (int i = 0; i < shipBlocks.size(); i++) {
            ShipBlock block = shipBlocks.get(i);
            if (block.coordinate.equals(coordinate)) {
                block.isExploded = true;
                markAsSunkIfAllBlocksExploded();
                return true;
            }
        }

        return false; //No block of ship was hit
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    private void markAsSunkIfAllBlocksExploded() {
        Stream<ShipBlock> temp = (shipBlocks.stream().filter(block-> !block.isExploded));
        isAfloat = temp.count() > 0;
    }

    private class ShipBlock {
        Coordinate coordinate;
        boolean isExploded;

        public ShipBlock(Coordinate c) {
            coordinate = c;
            isExploded = false;
        }
    }
}
