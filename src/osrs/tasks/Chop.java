package osrs.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import osrs.AntiBan;
import osrs.Task;

import java.util.concurrent.Callable;

public class Chop extends Task {

    final static int NORMAL_TREE_IDS[] = {1276, 1278};
    Tile treeLocation = Tile.NIL;
    AntiBan antiBan = new AntiBan();

    public Chop(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        // If tree is open and player isn't doing anything
        if (ctx.objects.select().at(treeLocation).id(NORMAL_TREE_IDS).poll().equals(ctx.objects.nil()) ||
                ctx.players.local().animation() == -1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void execute() {
        GameObject treeToChop = ctx.objects.select().id(NORMAL_TREE_IDS).nearest().poll();
        treeLocation = treeToChop.tile();
        treeToChop.interact("Chop down");

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                // Break out when player is acting, don't spam click
                return ctx.players.local().animation() != -1;
            }
        }, antiBan.randomizeWait(360, 690), antiBan.randomizeWait(12, 15));
    }
}
