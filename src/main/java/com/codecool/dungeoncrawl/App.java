//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.codecool.dungeoncrawl;


import com.codecool.dungeoncrawl.dao.JSONsave;

public class App {
    public App() {
    }

    public static void main(String[] args) {
        JSONsave savedTest = new JSONsave();
        savedTest.saveToJSON();
        PlayerInput.start(args);
    }
}
