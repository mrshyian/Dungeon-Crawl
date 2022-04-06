package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameDoorsModel;

import java.util.List;

public interface GameDoorsDao {
    void add(GameDoorsModel door);
    void update(GameDoorsModel door);
    GameDoorsModel get(int id);
    List<GameDoorsModel> getAll();
}
