package com.example.sandric.mps.models;

/**
 * Created by sandric on 18.11.15.
 */
public class Cell {

    public int row;
    public int column;

    public String color;
    public Piece piece;


    public Boolean isSelected;
    public Boolean isValid;
    public Boolean isExpected;

    public Cell(String color, int row, int column, Piece piece) {

        this.color = color;
        this.piece = piece;

        this.row = row;
        this.column = column;

        this.isSelected = false;
        this.isValid = false;
        this.isExpected = false;
    }


    public Boolean isEmpty() {
        return (this.piece == null);
    }

    public Boolean isFriend(Cell cell) {
        return (!this.isEmpty() && !cell.isEmpty() && this.piece.side.equals(cell.piece.side));
    }

    public Boolean isEnemy(Cell cell) {
        return (!this.isEmpty() && !cell.isEmpty() && !this.piece.side.equals(cell.piece.side));
    }



    public void select() {
        this.isSelected = true;
    }

    public void deselect() {
        this.isSelected = false;
    }

    public void validate() {
        this.isValid = true;
    }

    public void unvalidate() {
        this.isValid = false;
    }

    public void expect() {
        this.isExpected = true;
    }

    public void unexpect() { this.isExpected = false; }


    public String getNotation() {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};
        return letters[this.column - 1] + this.row;
    }
}
