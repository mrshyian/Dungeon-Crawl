package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.OpponentModel;


import java.util.List;

public interface OpponentDao {
    void add(OpponentModel opponent);
    void update(OpponentModel opponent);

    OpponentModel get(int id);

    List<OpponentModel> getAll();
}
