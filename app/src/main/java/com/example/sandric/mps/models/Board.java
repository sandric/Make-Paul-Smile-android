package com.example.sandric.mps.models;

import android.os.Handler;

import com.example.sandric.mps.interfaces.BoardActivityInterface;

import java.util.ArrayList;

/**
 * Created by sandric on 18.11.15.
 */
public class Board {

    public static int CURRENT_MOVE_NUMBER;

    public String type;

    BoardActivityInterface boardActivityDelegate;

    private Cell moveFrom;
    private Cell moveTo;

    public ArrayList<Cell> cells;

    public Ruler ruler;
    public Opening opening;

    private Handler timerHandler;


    public Board(String type, Opening opening, BoardActivityInterface boardActivityDelegate) {
        Board.CURRENT_MOVE_NUMBER = 1;

        this.type = type;

        this.boardActivityDelegate = boardActivityDelegate;

        this.opening = opening;

        this.timerHandler = new Handler();

        this.generate();
    }

    public void generateCells() {
        String cellColor, firstCellColor, secondCellColor;

        this.cells = new ArrayList<Cell>();

        for (int i = 7; i >= 0; i--) {
            if (i % 2 == 0) {
                firstCellColor = "black";
                secondCellColor = "white";
            } else {
                firstCellColor = "white";
                secondCellColor = "black";
            }

            for (int j = 0; j < 8; j++) {
                if(j % 2 == 0)
                    cellColor = firstCellColor;
                else
                    cellColor = secondCellColor;

                Cell cell = new Cell(cellColor, i + 1, j + 1, null);

                this.cells.add(cell);
            }
        }
    }

    public void generatePieces() {
        this.getCell(1, 1).piece = new Piece("white", "rook");
        this.getCell(1, 2).piece = new Piece("white", "knight");
        this.getCell(1, 3).piece = new Piece("white", "bishop");
        this.getCell(1, 4).piece = new Piece("white", "queen");
        this.getCell(1, 5).piece = new Piece("white", "king");
        this.getCell(1, 6).piece = new Piece("white", "bishop");
        this.getCell(1, 7).piece = new Piece("white", "knight");
        this.getCell(1, 8).piece = new Piece("white", "rook");

        this.getCell(8, 1).piece = new Piece("black", "rook");
        this.getCell(8, 2).piece = new Piece("black", "knight");
        this.getCell(8, 3).piece = new Piece("black", "bishop");
        this.getCell(8, 4).piece = new Piece("black", "queen");
        this.getCell(8, 5).piece = new Piece("black", "king");
        this.getCell(8, 6).piece = new Piece("black", "bishop");
        this.getCell(8, 7).piece = new Piece("black", "knight");
        this.getCell(8, 8).piece = new Piece("black", "rook");

        for (int i = 1; i < 9; i++) {
            this.getCell(2, i).piece = new Piece("white", "pawn");
            this.getCell(7, i).piece = new Piece("black", "pawn");
        }
    }

    public void generate () {
        this.generateCells();
        this.generatePieces();

        this.ruler = new Ruler(this);
    }


    public void simulateMove () {
        if (!this.isEnded()) {
            String[] answer = this.opening.getHint();
            this.move(this.getCell(answer[0]), this.getCell(answer[1]));
        }
    }

    public void startMovesSimulation () {
        this.timerHandler.postDelayed(this.updateTimerThread, 1000);
    }

    public void stopMovesSimulation () {
        this.timerHandler.removeCallbacks(this.updateTimerThread);
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            Board.this.simulateMove();
            timerHandler.postDelayed(this, 1000);
        }
    };


    public void selectCell(Cell cell) {
        if (this.moveFrom != null)
            this.setMoveTo(cell);
        else
            this.setMoveFrom(cell);
    }


    private void setMoveFrom (Cell moveFrom) {
        this.removeSelection();
        this.removeValidation();
        this.removeHint();

        if (moveFrom == null)
            this.moveFrom = moveFrom;
        else
            if (!moveFrom.isEmpty())
                if (this.isTurnsPiece(moveFrom.piece)) {
                    this.moveFrom = moveFrom;
                    this.moveFrom.select();
                    this.ruler.check(this.moveFrom);
                } else
                    this.boardActivityDelegate.displayMessage("Its other's side turn now");
            else
                this.boardActivityDelegate.displayMessage("You need to select piece, silly!");
    }

    private void setMoveTo (Cell moveTo) {
        this.removeHint();

        if (this.moveFrom == moveTo)
            this.moveFrom = null;
        else
            if (this.moveFrom.isFriend(moveTo))
                this.setMoveFrom(moveTo);
            else
                if (moveTo.isValid) {
                    this.moveTo = moveTo;
                    this.move(this.moveFrom, this.moveTo);
                    this.setMoveFrom(null);
                    this.moveTo = null;
                } else
                    this.boardActivityDelegate.displayMessage("No, you can't move that way, darling.");
    }


    private void move(Cell moveFrom, Cell moveTo) {
        Move move = new Move(moveFrom, moveTo, Board.CURRENT_MOVE_NUMBER);

        if (this.opening.isValid(move)) {
            this.boardActivityDelegate.displayMoveNotation(move.getRelativeNotation());

            this.movePiece(moveFrom, moveTo);

            Board.CURRENT_MOVE_NUMBER++;

            if (this.isTrainingGame()) {
                this.boardActivityDelegate.displayMessage("Yep, good job!");
                this.boardActivityDelegate.onPlayerMadeMove();
            } else
                this.boardActivityDelegate.onComputerMadeMove();

            if (this.isEnded())
                this.boardActivityDelegate.onGameEnded();
        } else {
            this.boardActivityDelegate.displayMessage("Nope, its not that.");
            this.boardActivityDelegate.onPlayerMadeMistake();
        }
    }


    private void movePiece (Cell moveFrom, Cell moveTo) {
        moveTo.piece = moveFrom.piece;
        moveFrom.piece.move();
        moveFrom.piece = null;
    }


    public void removeSelection() {
        for (Cell cell : this.cells)
            cell.deselect();
    }

    public void removeValidation() {
        for (Cell cell : this.cells)
            cell.unvalidate();
    }

    public void highlightHint() {
        String[] expectation = this.opening.getHint();

        this.getCell(expectation[0]).expect();
        this.getCell(expectation[1]).expect();
    }

    public void removeHint() {
        for (Cell cell : this.cells)
            cell.unexpect();
    }



    public Cell getCell(int row, int column) {
        if (row > 0 && row < 9 && column > 0 && column < 9)
            return this.cells.get(((8 - row) * 8) + column - 1);
        else
            return null;
    }

    public Cell getCell(String position) {
        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

        char rowLetter  = position.charAt(0);

        int row = Character.getNumericValue(position.charAt(1));
        int column = 1;
        for (char letter : letters) {
            if (letter == rowLetter)
                break;
            column++;
        }

        if (row > 0 && row < 9 && column > 0 && column < 9)
            return this.cells.get(((8 - row) * 8) + column - 1);
        else
            return null;
    }



    private Boolean isLearningGame () {
        return this.type.equals("learning");
    }

    private Boolean isTrainingGame () {
        return this.type.equals("training");
    }

    private Boolean isEnded () {
        return Board.CURRENT_MOVE_NUMBER > this.opening.movesCount;
    }


    private boolean isTurnsPiece (Piece piece) {
        if (    (Board.CURRENT_MOVE_NUMBER % 2 == 0 && piece.side.equals("black")) ||
                (Board.CURRENT_MOVE_NUMBER % 2 == 1 && piece.side.equals("white")))
            return true;
        else
            return false;
    }
}
