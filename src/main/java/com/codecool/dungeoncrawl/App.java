//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.codecool.dungeoncrawl;


import com.codecool.dungeoncrawl.dao.JSONextract;
import com.codecool.dungeoncrawl.dao.JSONsave;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class App {
    public App() {
    }

    public static void main(String[] args) {
        JSONsave savedTest = new JSONsave();
        savedTest.saveToJSON();
        JSONextract lastSaveTest = new JSONextract();
        lastSaveTest.extractObjectsFromJSON("savedGame.json");
        ArrayList<Object> allObjects = JSONextract.getAllObjects();

        PlayerInput.start(args);
    }
}