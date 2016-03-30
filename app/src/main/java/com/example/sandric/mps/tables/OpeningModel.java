package com.example.sandric.mps.tables;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sandric on 08.02.16.
 */


@Table(name = "Openings")
public class OpeningModel extends Model
{

    @Expose
    @Column(name = "Name")
    public String name;

    @Expose
    @Column(name = "GroupName")
    public String groupname;

    @Expose
    public ArrayList<String> moves;

    @Column(name = "MovesString")
    public String movesString;

    @Expose
    public ArrayList<String> annotations;

    @Column(name = "AnnotationsString")
    public String annotationsString;

    @Expose
    @Column(name = "StartingMove")
    public int startingMove;

    @Expose
    @Column(name = "Details")
    public String details;


    public OpeningModel()
    {
        super();
    }

    public ArrayList<String> movesArrayList () {
        return new ArrayList<String>(Arrays.asList(this.movesString.split("\\s*;\\s*")));
    }

    public ArrayList<String> annotationsArrayList () {
        return new ArrayList<String>(Arrays.asList(this.annotationsString.split("\\s*;\\s*")));
    }
}
