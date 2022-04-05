package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Cheese;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;


public class JSONsave {
    static Player player;


    public static void main( String[] args )
    {

        JSONObject savedGame = new JSONObject();


        //-------------------------------------------------
        JSONObject playerInfo = new JSONObject();
        playerInfo.put("PlayerY",player.getY());
        playerInfo.put("PlayerX",player.getX());
        playerInfo.put("PlayerHealth",player.getHealth());
        playerInfo.put("PlayerName",player.getName());
        playerInfo.put("PlayerAttackPower",player.getAttackPower());
        playerInfo.put("PlayerShield",player.getShield());

        //---------------------------------------------------
        JSONObject backpackInfo = new JSONObject();
        int countOfCheese = 0;
        for (int i=0; i<player.backpack.getBackpackContent().toArray().length; i++){
            if (player.backpack.getBackpackContent().get(i) instanceof Cheese){
                countOfCheese++;
            }
        }
        backpackInfo.put("Cheese", countOfCheese);

        backpackInfo.put("Sword", player.backpack.containItemType("sword"));
        backpackInfo.put("Sword1", player.backpack.containItemType("sword1"));
        backpackInfo.put("Key", player.backpack.containItemType("key"));
        backpackInfo.put("Helmet", player.backpack.containItemType("helmet"));
        backpackInfo.put("Crown", player.backpack.containItemType("crown"));

        //Write JSON file
        try (FileWriter file = new FileWriter("saved_game.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(savedGame.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
