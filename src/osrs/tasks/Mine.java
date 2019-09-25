package osrs.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import osrs.Task;
import osrs.AntiBan;

import java.util.concurrent.Callable;

public class Mine extends Task {

    final static int ROCK_IDS[] = {11360, 11361, 10943, 11161};
    Tile rockLocation = Tile.NIL;
    AntiBan antiBan = new AntiBan();

    public Mine(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        // If rock is open and player isn't doing anything
        if (ctx.objects.select().at(rockLocation).id(ROCK_IDS).poll().equals(ctx.objects.nil()) ||
                ctx.players.local().animation() == -1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void execute() {
        GameObject rockToMine = ctx.objects.select().id(ROCK_IDS).nearest().poll();
        rockLocation = rockToMine.tile();
        rockToMine.interact("Mine");

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                // Break out when player is acting, don't spam click
                return ctx.players.local().animation() != -1;
            }
        }, antiBan.randomizeWait(280, 320), antiBan.randomizeWait(8, 12));
    }
}
