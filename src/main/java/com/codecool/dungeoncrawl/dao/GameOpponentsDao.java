package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameOpponentsModel;

import java.util.List;

public interface GameOpponentsDao {
    void add(GameOpponentsModel opponent);
    void update(GameOpponentsModel opponent);
    GameOpponentsModel get(int id);
    List<GameOpponentsModel> getAll();
}
