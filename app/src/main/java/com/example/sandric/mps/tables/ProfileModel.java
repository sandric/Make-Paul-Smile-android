package com.example.sandric.mps.tables;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by sandric on 15.02.16.
 */

public class ProfileModel
{

    @Expose
    public String name;

    @Expose
    public GameModel best_game;

    @Expose
    public List<GameModel> best_games_by_group;
}
