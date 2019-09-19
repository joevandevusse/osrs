package osrs.tasks;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import osrs.Task;
import osrs.Walker;

public class Walk extends Task {

    public static final Tile[] pathToBank = {new Tile(3230, 3146, 0), new Tile(3231, 3150, 0), new Tile(3232, 3154, 0), new Tile(3235, 3157, 0), new Tile(3237, 3161, 0), new Tile(3237, 3165, 0), new Tile(3237, 3169, 0), new Tile(3237, 3173, 0), new Tile(3239, 3177, 0), new Tile(3239, 3181, 0), new Tile(3241, 3185, 0), new Tile(3243, 3189, 0), new Tile(3244, 3193, 0), new Tile(3241, 3197, 0), new Tile(3238, 3201, 0), new Tile(3236, 3205, 0), new Tile(3236, 3209, 0), new Tile(3236, 3213, 0), new Tile(3234, 3217, 0), new Tile(3230, 3218, 0), new Tile(3226, 3218, 0), new Tile(3222, 3218, 0), new Tile(3218, 3218, 0), new Tile(3215, 3215, 0), new Tile(3215, 3211, 0), new Tile(3211, 3211, 0), new Tile(3207, 3210, 0), new Tile(3205, 3209, 1), new Tile(3205, 3209, 2), new Tile(3205, 3213, 2), new Tile(3206, 3217, 2)};
    public final Walker walker = new Walker(ctx);

    public Walk(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        // Activate if we need to bank or walk back
        if ((ctx.inventory.select().count() > 27) ||
                (ctx.inventory.select().count() < 28 && pathToBank[0].distanceTo(ctx.players.local()) > 5)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void execute() {
        // If not walking or have no dest or dest is very close
        if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) ||
                ctx.movement.destination().distanceTo(ctx.players.local()) < 5) {
            if (ctx.inventory.select().count() > 27) {
                walker.walkPath(pathToBank);
            } else {
                walker.walkPathReverse(pathToBank);
            }
        }
    }
}
