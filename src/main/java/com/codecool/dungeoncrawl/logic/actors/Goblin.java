package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import javafx.stage.Stage;

public class Goblin extends Actor {
    private int goblinDirection;
    private int directions[][] = {{1,0}, {0,1}, {-1,0}, {0,-1}};


    public Goblin(Cell cell) {
        super(cell);
        this.setHealth(1);
        this.setAttackPower(1);
        this.setShield(0);
    }

    @Override
    public void move(int dx, int dy, Stage primaryStage) {
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        this.getCell().setCellContent(null);
        nextCell.setCellContent(this);
        this.setCell(nextCell);
    }

    public void moveGoblin(Stage primaryStage){
        int goblinDirection = this.goblinDirection;
        int[]direction = directions[goblinDirection];
        int x = direction[0];
        int y = direction[1];
        Cell nextCell = this.getCell().getNeighbor(x, y);
        if (nextCell.getType() == CellType.FLOOR && nextCell.getCellContent() == null) {
            move(x, y, primaryStage);

        }
        else {
            while(nextCell.getType() != CellType.FLOOR || nextCell.getCellContent() != null){
                switch (this.goblinDirection) {
                    case 0:
                        this.goblinDirection = 1;
                        break;
                    case 1:
                        this.goblinDirection = 2;
                        break;
                    case 2:
                        this.goblinDirection = 3;
                        break;
                    case 3:
                        this.goblinDirection = 0;
                        break;

                }
                direction = directions[this.goblinDirection];
                x = direction[0];
                y = direction[1];
                nextCell = this.getCell().getNeighbor(x, y);
            }
            move(x,y, primaryStage);
        }
        }




    public String getTileName() {
        return "goblin";
    }
}