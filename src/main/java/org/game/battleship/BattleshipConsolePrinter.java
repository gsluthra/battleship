package org.game.battleship;

public class BattleshipConsolePrinter {
    private final GameBoard board;

    private static final String OPEN_SEA = "~";
    private static final String SHIP_AFLOAT = "S";
    private static final String SHIP_HIT_SHOT = "$";
    private static final String SHIP_SUNK_SHOT = "X";
    private static final String SHOT_IN_SEA = "o";

    public BattleshipConsolePrinter(GameBoard gameBoard) {
        this.board = gameBoard;
    }

    public void printBoard() {
        Colorize.println("**************** BOARD *********************");
        printBoardRowHeader();

        for (int i = 1; i <= this.board.height(); i++) {
            Colorize.printCell(i);

            for (int j = 1; j <= this.board.length(); j++) {
                Coordinate coordinate = new Coordinate(j, i);
                if (!this.board.isShipPresent(coordinate)) {
                    printNoShipHereSymbols(coordinate);
                    continue;
                }
                printShipSymbols(coordinate);
            }
            Colorize.printEmptyLine();
        }

    }

    private void printShipSymbols(Coordinate coordinate) {
        Ship s = this.board.getShipAt(coordinate);
        if (s.isSunk())
            Colorize.printCellInRed(SHIP_SUNK_SHOT);
        else if (s.isHitAt(coordinate))
            Colorize.printCellInGreen(SHIP_HIT_SHOT);
        else
            Colorize.printCellInBlue(SHIP_AFLOAT);
    }

    private void printNoShipHereSymbols(Coordinate coordinate) {
        if(this.board.isCoordinateAlreadyHit(coordinate))
            Colorize.printCellInYellow(SHOT_IN_SEA);
        else
            Colorize.printCell(OPEN_SEA);
    }

    private void printBoardRowHeader() {
        for(int i=0; i<=this.board.length(); i++){
            Colorize.printCell(i);
        }
        System.out.println("");
    }

    public void printBoardSummary(){
        printBoard();
        Colorize.printlnInRed("Ships Sunk: " + this.board.getNumberOfShipsSunk());
        Colorize.printlnInGreen("Ships Not Sunk: "+ this.board.getNumberOfShipsAfloat());
    }

}
