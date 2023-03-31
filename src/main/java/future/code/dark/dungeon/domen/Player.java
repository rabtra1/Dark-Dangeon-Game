package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

public class Player extends DynamicObject {
    private static final int stepSize = 1;

    public Player(int xPosition, int yPosition) {
        super(xPosition, yPosition, Configuration.PLAYER_SPRITE);
    }

    public void move(Direction direction) {
        super.move(direction, stepSize);
    }

    @Override
    public String toString() {
        return "Player{[" + xPosition + ":" + yPosition + "]}";
    }

    protected boolean exitOpen(int x, int y){

//        boolean c = GameMaster.getInstance().getStaticObjects().stream().noneMatch(s->s instanceof Exit && s.getXPosition() == x && s.getYPosition() == y);
//        boolean c = GameMaster.getInstance().getMap().getMap()[y][x] == Configuration.EXIT_CHARACTER && getXPosition() == x && getYPosition() == y;
        return GameMaster.getInstance().getMap().getMap()[y][x] == Configuration.EXIT_CHARACTER && GameMaster.scoreCoin != 9;

    }

}
