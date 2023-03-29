package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

import javax.swing.*;
import java.awt.*;

public class DynamicObject extends GameObject {

    public DynamicObject(int xPosition, int yPosition, String imagePath) {
        super(xPosition, yPosition, imagePath);
    }

    private Image win_image = new ImageIcon(Configuration.VICTORY_SPRITE).getImage();

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }


    protected int countCoin = 0;

    public void setCountCoin(int countCoin) {
        this.countCoin = countCoin;
    }

    public int tmpXPosition = getXPosition();
    public int tmpYPosition = getYPosition();

    protected void move(Direction direction, int distance) {



        switch (direction) {
            case UP -> tmpYPosition -= distance;
            case DOWN -> tmpYPosition += distance;
            case LEFT -> tmpXPosition -= distance;
            case RIGHT -> tmpXPosition += distance;
        }

        if (isAllowedSurface(tmpXPosition, tmpYPosition)) {
            xPosition = tmpXPosition;
            yPosition = tmpYPosition;
        }

        if (Configuration.MAP_FILE_PATH.contains("small.ber")) {
            if (isAllowedSurfaceSmall(tmpXPosition, tmpYPosition)) {
                yPosition = tmpYPosition;
                xPosition = tmpXPosition;
            }
        }

        if (isAllowedCoin(tmpXPosition, tmpYPosition)) setCountCoin(countCoin+=1);

        deleteCoin(tmpXPosition, tmpYPosition);



    }

    public int getCountCoin() {
        return countCoin;
    }

    protected Boolean isAllowedSurface(int x, int y) {
        return GameMaster.getInstance().getMap().getMap()[y][x] != Configuration.WALL_CHARACTER && !isExitOpen(x,y);
    }

    private Boolean isAllowedCoin(int x, int y) {
        return GameMaster.getInstance().getStaticObjects().stream()
                .anyMatch(s -> s instanceof Coin && s.getXPosition() == x && s.getYPosition() == y);
    }

    private void deleteCoin(int x, int y) {
        GameMaster.getInstance().getGameObjects().removeIf(s -> s instanceof Coin && s.getYPosition() == y && s.getXPosition() == x);
    }

    public Boolean isExitOpen(int x, int y) {
        return GameMaster.getInstance().getMap().getMap()[y][x] == Configuration.EXIT_CHARACTER && getCountCoin() != 9;
    }

    public Boolean isExitOpenSmall(int x, int y) {
        return GameMaster.getInstance().getMap().getMap()[y][x] == Configuration.EXIT_CHARACTER && getCountCoin() != 4;
    }

    private Boolean isAllowedSurfaceSmall(int x, int y) {
        return GameMaster.getInstance().getMap().getMap()[y][x] != Configuration.WALL_CHARACTER && !isExitOpenSmall(x,y);
    } // dsdssd



}
