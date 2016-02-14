package com.example.sandric.mps.models;

/**
 * Created by sandric on 18.11.15.
 */
public class Ruler {

    private Board board;

    private Cell selectedCell;


    Ruler(Board board) {
        this.board = board;
    }




    private void validOnKnight (String mainDirection, String secondaryDirection) {
        int[] mainVector = {0, 0};
        int[] secondaryVector = {0, 0};

        switch (mainDirection) {
            case "top":     mainVector = new int[]{1, 0}; break;
            case "bottom":  mainVector = new int[]{-1, 0}; break;
            case "left":    mainVector = new int[]{0, -1}; break;
            case "right":   mainVector = new int[]{0, 1}; break;
        }

        switch (secondaryDirection) {
            case "top":     secondaryVector = new int[]{1, 0}; break;
            case "bottom":  secondaryVector = new int[]{-1, 0}; break;
            case "left":    secondaryVector = new int[]{0, -1}; break;
            case "right":   secondaryVector = new int[]{0, 1}; break;
        }

        Cell first = this.board.getCell(this.selectedCell.row + mainVector[0], this.selectedCell.column + mainVector[1]);

        if (first != null) {
            Cell second = this.board.getCell(first.row + mainVector[0], first.column + mainVector[1]);

            if (second != null) {
                Cell third = this.board.getCell(second.row + secondaryVector[0], second.column + secondaryVector[1]);

                if ((third != null) && (third.isEmpty() || third.isEnemy(this.selectedCell)))
                    third.validate();
            }
        }
    }


    private void validOnVerticalUp(int length) {
        int validCellsLength = 0;

        for (int row = this.selectedCell.row + 1; row < 9; row++) {
            if (length > 0 && validCellsLength >= length) break;

            Cell cell = this.board.getCell(row, this.selectedCell.column);

            if (cell.isFriend(this.selectedCell)) break;

            cell.validate();

            validCellsLength++;

            if (cell.isEnemy(this.selectedCell)) break;
        }
    }

    private void validOnVerticalDown(int length) {
        int validCellsLength = 0;

        for (int row = this.selectedCell.row - 1; row > 0; row--) {
            if (length > 0 && validCellsLength >= length) break;

            Cell cell = this.board.getCell(row, this.selectedCell.column);

            if (cell.isFriend(this.selectedCell)) break;

            cell.validate();

            validCellsLength++;

            if (cell.isEnemy(this.selectedCell)) break;
        }
    }

    private void validOnHorizontalLeft(int length) {
        int validCellsLength = 0;

        for (int column = this.selectedCell.column - 1; column > 0; column--) {
            if (length > 0 && validCellsLength >= length) break;

            Cell cell = this.board.getCell(this.selectedCell.row, column);

            if (cell.isFriend(this.selectedCell)) break;

            cell.validate();

            validCellsLength++;

            if (cell.isEnemy(this.selectedCell)) break;
        }
    }

    private void validOnHorizontalRight(int length) {
        int validCellsLength = 0;

        for (int column = this.selectedCell.column + 1; column < 9; column++) {
            if (length > 0 && validCellsLength >= length) break;

            Cell cell = this.board.getCell(this.selectedCell.row, column);

            if (cell.isFriend(this.selectedCell)) break;

            cell.validate();

            validCellsLength++;

            if (cell.isEnemy(this.selectedCell)) break;
        }
    }



    private void validOnDiagonalTopLeft(int length) {
        int validCellsLength = 0;
        for (int i = 1, row = this.selectedCell.row + 1; row < 9; row++, i++) {
            if (length > 0 && (validCellsLength >= length)) break;
            if (this.selectedCell.column - i < 1) break;

            Cell cell = this.board.getCell(row, this.selectedCell.column - i);

            if (cell.isFriend(this.selectedCell)) break;

            cell.validate();

            validCellsLength++;

            if (cell.isEnemy(this.selectedCell)) break;
        }
    }

    private void validOnDiagonalTopRight(int length) {
        int validCellsLength = 0;
        for (int i = 1, row = this.selectedCell.row + 1; row < 9; row++, i++) {
            if (length > 0 && (validCellsLength >= length)) break;
            if (this.selectedCell.column + i > 8) break;

            Cell cell = this.board.getCell(row, this.selectedCell.column + i);

            if (cell.isFriend(this.selectedCell)) break;

            cell.validate();

            validCellsLength++;

            if (cell.isEnemy(this.selectedCell)) break;
        }
    }

    private void validOnDiagonalBottomLeft(int length) {
        int validCellsLength = 0;
        for (int i = 1, row = this.selectedCell.row - 1; row > 0; row--, i++) {
            if (length > 0 && (validCellsLength >= length)) break;
            if (this.selectedCell.column - i < 1) break;

            Cell cell = this.board.getCell(row, this.selectedCell.column - i);

            if (cell.isFriend(this.selectedCell)) break;

            cell.validate();

            validCellsLength++;

            if (cell.isEnemy(this.selectedCell)) break;
        }
    }

    private void validOnDiagonalBottomRight(int length) {
        int validCellsLength = 0;
        for (int i = 1, row = this.selectedCell.row - 1; row > 0; row--, i++) {
            if (length > 0 && (validCellsLength >= length)) break;
            if (this.selectedCell.column + i > 8) break;

            Cell cell = this.board.getCell(row, this.selectedCell.column + i);

            if (cell.isFriend(this.selectedCell)) break;

            cell.validate();

            validCellsLength++;

            if (cell.isEnemy(this.selectedCell)) break;
        }
    }



    private void validStraight (int length) {
        this.validOnVerticalUp(length);
        this.validOnVerticalDown(length);
        this.validOnHorizontalLeft(length);
        this.validOnHorizontalRight(length);
    }


    private void validDiagonal (int length) {
        this.validOnDiagonalTopLeft(length);
        this.validOnDiagonalTopRight(length);
        this.validOnDiagonalBottomLeft(length);
        this.validOnDiagonalBottomRight(length);
    }



    private void checkPawn () {
        Cell superTopCell = null;
        Cell topCell;
        Cell topLeftCell;
        Cell topRightCell;



        if (this.selectedCell.piece.side.equals("white")) {
            if (!this.selectedCell.piece.isMoved)
                superTopCell = this.board.getCell(this.selectedCell.row + 2, this.selectedCell.column);
            topCell = this.board.getCell(this.selectedCell.row + 1, this.selectedCell.column);
            topLeftCell = this.board.getCell(this.selectedCell.row + 1, this.selectedCell.column - 1);
            topRightCell = this.board.getCell(this.selectedCell.row + 1, this.selectedCell.column + 1);
        } else {
            if (!this.selectedCell.piece.isMoved)
                superTopCell = this.board.getCell(this.selectedCell.row - 2, this.selectedCell.column);
            topCell = this.board.getCell(this.selectedCell.row - 1, this.selectedCell.column);
            topLeftCell = this.board.getCell(this.selectedCell.row - 1, this.selectedCell.column - 1);
            topRightCell = this.board.getCell(this.selectedCell.row - 1, this.selectedCell.column + 1);
        }

        if (superTopCell != null && superTopCell.isEmpty())
            superTopCell.validate();
        if (topCell != null && topCell.isEmpty())
            topCell.validate();
        if (topLeftCell != null && topLeftCell.isEnemy(this.selectedCell))
            topLeftCell.validate();
        if (topRightCell != null && topRightCell.isEnemy(this.selectedCell))
            topRightCell.validate();
    }

    private void checkKnight () {
        this.validOnKnight("top", "left");
        this.validOnKnight("top", "right");
        this.validOnKnight("bottom", "left");
        this.validOnKnight("bottom", "right");
        this.validOnKnight("left", "top");
        this.validOnKnight("left", "bottom");
        this.validOnKnight("right", "top");
        this.validOnKnight("right", "bottom");
    }

    private void checkBishop () {
        this.validDiagonal(0);
    }

    private void checkRook () {
        this.validStraight(0);
    }

    private void checkQueen () {
        this.validStraight(0);
        this.validDiagonal(0);
    }

    private void checkKing () {
        this.validStraight(1);
        this.validDiagonal(1);
    }


    public void check (Cell selectedCell) {
        this.selectedCell = selectedCell;

        switch (selectedCell.piece.type) {
            case "pawn":    this.checkPawn(); break;
            case "knight":  this.checkKnight(); break;
            case "bishop":  this.checkBishop(); break;
            case "rook":    this.checkRook(); break;
            case "queen":   this.checkQueen(); break;
            case "king":    this.checkKing(); break;
        }
    }

}
