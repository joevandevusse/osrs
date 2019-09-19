package osrs.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;
import osrs.Task;

import java.util.concurrent.Callable;

public class Drop extends Task {

   final static int COPPER_ORE = 436;
   final static int TIN_ORE = 438;

    public Drop(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.inventory.count() > 27;
    }

    @Override
    public void execute() {
        for (Item tinOre : ctx.inventory.select().id(TIN_ORE)) {
            // If user clicks stop, break
            if (ctx.controller.isStopping()) {
                break;
            }
            final int startAmtTin = ctx.inventory.select().id(TIN_ORE).count();
            tinOre.interact("Drop", "Tin");

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    // Break out when player is acting, don't spam click
                    return ctx.inventory.select().id(TIN_ORE).count() != startAmtTin;
                }
            }, 50, 25);
        }

        for (Item copperOre : ctx.inventory.select().id(COPPER_ORE)) {
            // If user clicks stop, break
            if (ctx.controller.isStopping()) {
                break;
            }
            final int startAmtCopper = ctx.inventory.select().id(COPPER_ORE).count();
            copperOre.interact("Drop", "Copper");

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    // Break out when player is acting, don't spam click
                    return ctx.inventory.select().id(COPPER_ORE).count() != startAmtCopper;
                }
            }, 50, 25);
        }

    }
}
