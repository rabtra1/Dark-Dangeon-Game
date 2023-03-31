package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.GameFrame;
import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

public abstract class DynamicObject extends GameObject {

    public DynamicObject(int xPosition, int yPosition, String imagePath) {
        super(xPosition, yPosition, imagePath);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected void move(Direction direction, int distance) {
        int tmpXPosition = getXPosition();
        int tmpYPosition = getYPosition();

        switch (direction) {
            case UP -> tmpYPosition -= distance;
            case DOWN -> tmpYPosition += distance;
            case LEFT -> tmpXPosition -= distance;
            case RIGHT -> tmpXPosition += distance;
        }



        if (isAllowedSurface(tmpXPosition, tmpYPosition)) {
            xPosition = tmpXPosition;
            yPosition = tmpYPosition;
//            if (checkStatus(tmpXPosition, tmpYPosition)) {
//                GameMaster.status_game = true;
//            }

        }

        if (checkStatus(tmpXPosition, tmpYPosition)) GameMaster.status_game = true;


        deleteCoinIf(tmpXPosition, tmpYPosition);

//        if (isPlayerOnCoin(tmpXPosition, tmpYPosition)) GameMaster.scoreCoin += 1;

//        if (new Player(tmpXPosition, tmpYPosition).exitOpen(tmpXPosition, tmpYPosition)) {
//            GameMaster.status_game = true;
//        } else {
//            GameMaster.status_game = false;
//        }

    }


    private Boolean checkStatus(int x, int y) {
        return GameMaster.getInstance().getMap().getMap()[y][x] == Configuration.EXIT_CHARACTER && GameMaster.scoreCoin == 9;
    }


    private Boolean isAllowedSurface(int x, int y) {
        return GameMaster.getInstance().getMap().getMap()[y][x] != Configuration.WALL_CHARACTER && !new Player(x, y).exitOpen(x, y);
    }


    private Boolean isPlayerOnCoin(int x, int y) {
        return GameMaster.getInstance().getStaticObjects().stream()
                .anyMatch(s -> s instanceof Coin && s.getXPosition() == x && s.getYPosition() == y);
    }

    private void deleteCoinIf(int x, int y) {

        if (isPlayerOnCoin(x, y)) GameMaster.scoreCoin += 1;

        GameMaster.getInstance().getGameObjects().removeIf(s -> s instanceof Coin && s.getYPosition() == y && s.getXPosition() == x);
    }

}
