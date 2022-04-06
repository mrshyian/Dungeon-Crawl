package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerBackpackModel;

import java.util.List;

public interface PlayerBackpackDao {
    void add(PlayerBackpackModel backpack);
    void update(PlayerBackpackModel backpack);
    PlayerBackpackModel get(int id);
    List<PlayerBackpackModel> getAll();
}
