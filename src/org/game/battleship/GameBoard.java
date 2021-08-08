package org.game.battleship;
import java.util.*;
import java.util.stream.Collectors;

public class GameBoard {

    private final int length;
    private final int height;
    private Map<Ship, List<Coordinate>> shipsOnBoard;

    public GameBoard(int length, int height) {
        this.length = length;
        this.height = height;
        this.shipsOnBoard = new HashMap<>();
    }

    public int length() {
        return length;
    }

    public int height() {
        return height;
    }

    public int numberOfShipsAfloat() {
        return shipsOnBoard.size();
    }

    public void placeShip(Coordinate startCoordinate, Orientation orientation, Ship ship) throws ShipPlacementOnBoardException {
        if(startCoordinate.getX() > this.length || startCoordinate.getX() <=0 )
            throw new ShipPlacementOnBoardException("For ship "+ ship.getName()+" Passed Coordinate X is out of range of board: "+ startCoordinate.getX());

        if(startCoordinate.getY() > this.height || startCoordinate.getY() <=0 )
            throw new ShipPlacementOnBoardException("For ship "+ ship.getName()+" Passed Coordinate Y is out of range of board: "+ startCoordinate.getY());

        int endX = startCoordinate.getX() + ship.size();
        if(orientation == Orientation.Horizontal && endX > this.length)
            throw new ShipPlacementOnBoardException("Ship "+ ship.getName()+" is not fitting within the board. End X Coordinate = "+ endX);

        int endY = startCoordinate.getY() + ship.size();
        if(orientation == Orientation.Vertical && endY > this.height)
            throw new ShipPlacementOnBoardException("Ship "+ ship.getName()+" is not fitting within the board. End Y Coordinate = "+ endY);

        checkIfNewShipNameIsUnique(ship.getName());
        checkIfShipOverlapsWithAnyExistingShips(getAllCoordinatesOfShip(startCoordinate, orientation, ship), ship.getName());
        saveShipToBoard(startCoordinate, orientation, ship);
    }

    public boolean isShipPresent(Coordinate coordinate) {
        List<Coordinate> coordinatesOfAllShipsOnBoardsFlattened = getAllCoordinatesOfShipFlattened();
        return containsCoordinate(coordinate, coordinatesOfAllShipsOnBoardsFlattened);
    }

    private void checkIfNewShipNameIsUnique(String newShipName) throws ShipPlacementOnBoardException {
        if(shipsOnBoard.isEmpty())
            return;

        for (Ship existingShip : shipsOnBoard.keySet()) {
            if (newShipName.trim().equalsIgnoreCase(existingShip.getName()))
                throw new ShipPlacementOnBoardException("This new ship " + newShipName + " has same name as existing ship: " + existingShip.getName());
        }
    }

    public void printBoard() {
        System.out.println("**************** BOARD *********************");
        printBoardRowHeader();

        for (int i = 1; i <= this.height; i++) {
            System.out.printf("%-3s:", i);

            for (int j = 1; j <= this.length; j++) {
                Coordinate coordinate = new Coordinate(j, i);
                if (!isShipPresent(coordinate)) {
                    System.out.printf("%-3s", "-");
                    continue;
                }
                Ship s = getShipAt(coordinate);
                if (s.isSunk())
                    System.out.printf("%-3s", "!");
                else if (s.isHitAt(coordinate))
                    System.out.printf("%-3s", "o");
                else
                    System.out.printf("%-3s", "S");
            }
            System.out.println("");
        }
    }

    public boolean shootAt(Coordinate bombCoordinates) {
        boolean isShipHit = isShipPresent(bombCoordinates);
        if(isShipHit){
            Ship ship = getShipAt(bombCoordinates);
            ship.shootAt(bombCoordinates);
        }
        return isShipHit;
    }

    //----------- PRIVATE

    private void printBoardRowHeader() {
        for(int i=0; i<=this.length; i++){
            System.out.printf("%-3s",i);
        }
        System.out.println("");
    }

    private boolean containsCoordinate(Coordinate coordinate, List<Coordinate> allCoordinates) {
        return allCoordinates.stream().anyMatch(c -> c.equals(coordinate));
    }

    private List<Coordinate> getAllCoordinatesOfShipFlattened() {
        Collection<List<Coordinate>> coordinatesOfShips = shipsOnBoard.values();
        return coordinatesOfShips.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

    }

    private void saveShipToBoard(Coordinate startCoordinate, Orientation orientation, Ship ship) {
        List<Coordinate> allShipCoordinates = getAllCoordinatesOfShip(startCoordinate, orientation, ship);
        ship.setLocation(startCoordinate, orientation);//inform the ship also of its coordinates
        this.shipsOnBoard.put(ship, allShipCoordinates);
    }

    private List<Coordinate> getAllCoordinatesOfShip(Coordinate startCoordinate, Orientation orientation, Ship ship) {
        List<Coordinate> allShipCoordinates = new ArrayList<>();
        Coordinate currentCoordinate = startCoordinate;
        for(int i = 0; i< ship.size(); i++){
            allShipCoordinates.add(currentCoordinate);
            currentCoordinate = (orientation == Orientation.Horizontal)?
                    (currentCoordinate.incrementX()):(currentCoordinate.incrementY());
        }
        return allShipCoordinates;
    }

    private void checkIfShipOverlapsWithAnyExistingShips(List<Coordinate> coordinatesToCheck, String shipName) throws ShipPlacementOnBoardException {
        if(shipsOnBoard.isEmpty())
            return;

        for (Ship existingShip : shipsOnBoard.keySet()) {
            List<Coordinate> coordOfExistingShip = shipsOnBoard.get(existingShip);
            if (!Collections.disjoint(coordOfExistingShip, coordinatesToCheck))
                throw new ShipPlacementOnBoardException("This new ship " + shipName + " overlaps with existing ship: " + existingShip.getName());
        }
    }

    private Ship getShipAt(Coordinate coordinates) {
        for (Ship s : shipsOnBoard.keySet()) {
            if (s.isAt(coordinates))
                return s;
        }
        return null; //no ship at the specified coordinates
    }
}
