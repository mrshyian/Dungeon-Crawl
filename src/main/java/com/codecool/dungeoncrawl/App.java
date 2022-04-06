//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.codecool.dungeoncrawl;


import com.codecool.dungeoncrawl.dao.JSONextract;
import com.codecool.dungeoncrawl.dao.JSONsave;

public class App {
    public App() {
    }

    public static void main(String[] args) {
        JSONsave savedTest = new JSONsave();
        savedTest.saveToJSON();
        JSONextract lastSaveTest = new JSONextract();
        lastSaveTest.extractObjectsFromJSON("savedGame.json");
        lastSaveTest.printTest();

        PlayerInput.start(args);
    }
}
//
//[{"Player":"naked player",
//        "BackPack":["Crown","Cheese","Cheese","Sword","Sword1","Key","Helmet"],
//        "Shield":1,
//        "X":3,
//        "HP":5,
//        "Y":4,
//        "Attack":1},
// [
//         {"X":3,"HP":2,"Y":4,"Actor Type":"Ghost"},
//        {"X":3,"HP":2,"Y":4,"Actor Type":"Ghost"},
//        {"X":3,"HP":1,"Y":4,"Actor Type":"Goblin"},
//        {"X":3,"HP":1,"Y":4,"Actor Type":"Goblin"},
//        {"X":3,"HP":3,"Y":4,"Actor Type":"Monster"},
//        {"X":3,"HP":1,"Y":4,"Actor Type":"Skeleton"},
//        {"X":3,"HP":1,"Y":4,"Actor Type":"Skeleton"},
//        {"X":3,"HP":1,"Y":4,"Actor Type":"Skeleton"}
//],
//[
//        {"X":3,"Y":4,"Item Type":"Cheese"},
//        {"X":3,"Y":4,"Item Type":"Cheese"},
//        {"X":3,"Y":4,"Item Type":"Helmet"},
//        {"X":3,"Y":4,"Item Type":"Sword"},
//        {"X":3,"Y":4,"Item Type":"Sword1"},
//        {"X":3,"Y":4,"Item Type":"Crown"},
//        {"X":3,"Y":4,"Item Type":"Key"}],
//        {"Map":"some Map Link"}
//]
