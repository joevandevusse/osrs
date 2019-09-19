package osrs.tasks;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import osrs.AntiBan;
import osrs.Task;

public class Chop extends Task {

    final static int TREE_IDS[] = {11360, 11361, 10943, 11161};
    Tile treeLocation = Tile.NIL;
    AntiBan antiBan = new AntiBan();

    public Chop(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return false;
    }

    @Override
    public void execute() {

    }
}
