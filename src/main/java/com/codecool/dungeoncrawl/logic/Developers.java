package com.codecool.dungeoncrawl.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Developers {

    private List<String> developers = new LinkedList<>();
    private String playerName;

    public Developers(String name) {
        this.playerName = name.toLowerCase(Locale.ROOT);
        fillDevelopers();
    }

    public boolean isDeveloperName(){
        return developers.contains(playerName);
    }

    private void fillDevelopers(){
        developers.add("justyna");
        developers.add("tetiana");
        developers.add("wladek");
        developers.add("michal");
    }
}
