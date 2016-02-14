package com.example.sandric.mps.models;

/**
 * Created by sandric on 18.11.15.
 */
public class Move {

    public Cell moveFrom;
    public Cell moveTo;
    public String side;
    public int number;


    public Move(Cell moveFrom, Cell moveTo, int number) {
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
        this.side = this.moveFrom.piece.side;

        this.number = number;
    }

    public String getNotation() {
        return  this.moveFrom.piece.getNotation() +
                this.moveFrom.getNotation() +
                (this.moveTo.isEmpty() ? " - " : " x ") +
                this.moveTo.getNotation();
    }

    public String getRelativeNotation() {
        if (this.number % 2 == 1)
            return (int)Math.ceil(this.number / 2d) + ". " + this.getNotation() + "; ";
        else
            return this.getNotation() + ". ";
    }

    public static String[] getCellPositionsFromNotation (String moveNotation) {
        String[] cellNotationsWithPiece = moveNotation.split(" - | x ", 2);

        String fromNotation = cellNotationsWithPiece[0].substring(Math.max(cellNotationsWithPiece[0].length() - 2, 0));
        String toNotation = cellNotationsWithPiece[1].substring(Math.max(cellNotationsWithPiece[1].length() - 2, 0));

        String[] cellNotations = {fromNotation, toNotation};

        return cellNotations;
    }
}
