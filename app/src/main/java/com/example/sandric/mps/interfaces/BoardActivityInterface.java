package com.example.sandric.mps.interfaces;

/**
 * Created by sandric on 29.12.15.
 */
public interface BoardActivityInterface {

    public void onBoardGenerated();

    public void onPlayerMadeMove();
    public void onPlayerMadeMistake();
    public void onComputerMadeMove();

    public void onGameEnded();


    public void displayMessage(String message);
    public void displayMoveNotation(String message);
}
