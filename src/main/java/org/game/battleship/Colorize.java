package org.game.battleship;

public class Colorize {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";


    public static String inGreen(String s){
        return ANSI_GREEN+s+ANSI_RESET;
    }

    public static String inRed(String s){
        return ANSI_RED+s+ANSI_RESET;
    }

    public static String inYellow(String s){
        return ANSI_YELLOW+s+ANSI_RESET;
    }

    public static String inBlue(String s){
        return ANSI_BLUE+s+ANSI_RESET;
    }

    public static void printlnInRed(String s){
        System.out.println(inRed(s));
    }
    public static void printlnInGreen(String s){
        System.out.println(inGreen(s));
    }
    public static void printlnInBlue(String s){
        System.out.println(inBlue(s));
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void printCellInRed(String s){
        System.out.printf(ANSI_RED+"%-3s"+ANSI_RESET, s);
    }
    public static void printCellInGreen(String s){
        System.out.printf(ANSI_GREEN+"%-3s"+ANSI_RESET, s);
    }
    public static void printCellInBlue(String s){
        System.out.printf(ANSI_BLUE+"%-3s"+ANSI_RESET, s);
    }
    public static void printCellInYellow(String s){
        System.out.printf(ANSI_YELLOW+"%-3s"+ANSI_RESET, s);
    }
    public static void printCell(int i){
        System.out.printf("%-3s", i+"");
    }

    public static void printCell(String s){
        System.out.printf("%-3s", s);
    }

    public static void printEmptyLine() {
        System.out.println();
    }
}
