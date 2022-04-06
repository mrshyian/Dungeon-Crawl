package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameItemsModel;

import java.util.List;

public interface GameItemsDao {
    void add(GameItemsModel item);
    void update(GameItemsModel item);
    GameItemsModel get(int id);
    List<GameItemsModel> getAll();
}
