package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ghost extends Actor{

    private Random random = new Random();
    private List<int[]> moves = new ArrayList<>();

    public Ghost(Cell cell) {
        super(cell);
        this.setHealth(2);
        this.setAttackPower(1);
        this.setShield(1);
        createMoves();
    }

    @Override
    public void move(int dx, int dy, Stage primaryStage) {
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        this.getCell().setCellContent(null);
        nextCell.setCellContent(this);
        this.setCell(nextCell);
    }

    private void createMoves(){
        moves.add(new int[]{0, 1});
        moves.add(new int[]{0, -1});
        moves.add(new int[]{-1, 0});
        moves.add(new int[]{1, 0});
    }

    public void randomMove(Stage primaryStage){
        while (true){
            int move = random.nextInt(4);
            int dx = moves.get(move)[0];
            int dy = moves.get(move)[1];
            Cell nextCell = this.getCell().getNeighbor(dx, dy);
            if (nextCell.getX() > nextCell.getGameMap().getWidth()
                    || nextCell.getY() > nextCell.getGameMap().getHeight()){
                continue;
            }
            if (((nextCell.getType() == CellType.FLOOR) && nextCell.getCellContent() == null)
                    || (nextCell.getType() == CellType.WALL) || (nextCell.getType() == CellType.OAKS)
                    || (nextCell.getType() == CellType.PINES) || (nextCell.getType() == CellType.RIVERBODY)){
                move(dx, dy, primaryStage);
                break;
            }
        }
    }

    public String getTileName() {
        return "ghost";
    }
}
