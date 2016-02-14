package com.example.sandric.mps.models;

/**
 * Created by sandric on 18.11.15.
 */
public class Piece {

    public String side;
    public String type;
    public Boolean isMoved;


    public Piece(String side, String type) {
        this.side = side;
        this.type = type;

        this.isMoved = false;
    }


    public void move() {
        this.isMoved = true;
    }

    public String getNotation() {
        switch(this.type) {
            case "knight":  return "N";
            case "bishop":  return "B";
            case "rook":    return "R";
            case "queen":   return "Q";
            case "king":    return "K";
        }

        return "";
    }

    public String getImagePath () {
        return this.side + "_" + this.type;
    }
}
