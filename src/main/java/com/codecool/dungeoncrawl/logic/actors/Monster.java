package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import javafx.stage.Stage;

public class Monster extends Actor {
    public Monster(Cell cell) {
        super(cell);
        this.setHealth(3);
        this.setAttackPower(2);
        this.setShield(2);
    }

    public String getTileName() {
        return "monster";
    }

    private boolean searchPlayerUp(Cell cell, Stage primaryStage) {
        int start = cell.getY() - 1;
        int stop = 3;
        while (stop > 0) {
            if (cell.getGameMap().getCell(cell.getX(), start).getType() != CellType.FLOOR) {
                return false;
            }

            if (cell.getGameMap().getCell(cell.getX(), start).getCellContent() instanceof Player) {
                return true;
            }
            start -= 1;
            stop -= 1;

        }
        return false;
    }

    private boolean searchPlayerDown(Cell cell, Stage primaryStage) {
        int start = cell.getY() + 1;
        int stop = 3;
        while (stop > 0) {
            if (cell.getGameMap().getCell(cell.getX(), start).getType() != CellType.FLOOR) {
                return false;
            }

            if (cell.getGameMap().getCell(cell.getX(), start).getCellContent() instanceof Player) {
                return true;
            }
            start += 1;
            stop -= 1;

        }
        return false;
    }

    private boolean searchPlayerRight(Cell cell, Stage primaryStage) {
        int start = cell.getX() + 1;
        int stop = 1;
        while (stop < 4) {
            try {
                if (cell.getGameMap().getCell(start, cell.getY()).getCellContent() instanceof Player) {
                    return true;
                }
            } catch (Exception ex){}
            Cell neighbor = cell.getNeighbor(stop, 0);
            if (wallChase(neighbor, primaryStage)) {
                return false;
            }
            if (searchPlayerUp(neighbor, primaryStage) || searchPlayerDown(neighbor, primaryStage)) {
                return true;
            }
            start += 1;
            stop += 1;

        }
        return false;
    }

    private boolean searchPlayerLeft(Cell cell, Stage primaryStage) {
        int start = cell.getX() - 1;
        int stop = 1;
        while (stop < 4) {
            try {
                if (cell.getGameMap().getCell(start, cell.getY()).getCellContent() instanceof Player) {
                    return true;
                }
            }catch (Exception ex){}
            Cell neighbor = cell.getNeighbor(-stop, 0);
            if (wallChase(neighbor, primaryStage)) {
                return false;
            }
            if (searchPlayerUp(neighbor, primaryStage) || searchPlayerDown(neighbor, primaryStage)) {
                return true;
            }
            start -= 1;
            stop += 1;

        }
        return false;
    }

    private boolean wallChase(Cell neighbor, Stage primaryStage) {
        if (searchPlayerUp(neighbor, primaryStage) && (neighbor.getType() != CellType.FLOOR
                || (neighbor.getType() == CellType.FLOOR && neighbor.getCellContent() != null))) {
            move(0, -1, primaryStage);
            return true;
        }
        if (searchPlayerDown(neighbor, primaryStage) && (neighbor.getType() != CellType.FLOOR
                || (neighbor.getType() == CellType.FLOOR && neighbor.getCellContent() != null))) {
            move(0, 1, primaryStage);
            return true;
        }
        return false;
    }

    @Override
    public void move(int dx, int dy, Stage primaryStage) {
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if ((nextCell.getType() == CellType.FLOOR) && nextCell.getCellContent() == null) {
            this.getCell().setCellContent(null);
            nextCell.setCellContent(this);
            this.setCell(nextCell);
        }
    }

    public void chaseMove(Stage primaryStage) {
        if (searchPlayerDown(getCell(), primaryStage)) {
            move(0, 1, primaryStage);
        } else if (searchPlayerUp(getCell(), primaryStage)) {
            move(0, -1, primaryStage);
        } else if (searchPlayerRight(getCell(), primaryStage)) {
            move(1, 0, primaryStage);
        } else if (searchPlayerLeft(getCell(), primaryStage)) {
            move(-1, 0,  primaryStage);
        }
    }
}
