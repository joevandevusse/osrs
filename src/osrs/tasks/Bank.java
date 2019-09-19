package osrs.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import osrs.AntiBan;
import osrs.Task;

import java.util.concurrent.Callable;

public class Bank extends Task {

    AntiBan antiBan = new AntiBan();

    public Bank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        // Activate banker if inventory full and close to bank
        if (ctx.inventory.select().count() > 27 && ctx.bank.nearest().tile().distanceTo(ctx.players.local()) < 5) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void execute() {
        if (ctx.bank.opened()) {
            final int inventoryCount = ctx.inventory.select().count();
            if (ctx.bank.depositInventory()) {
                // Wait until inventory has been deposited
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.inventory.select().count() != inventoryCount;
                    }
                }, antiBan.randomizeWait(230, 270), antiBan.randomizeWait(15, 25));
            }
        } else {
            // If it isn't in view, turn camera
            if (ctx.bank.inViewport()) {
                if (ctx.bank.open()) {
                    // Wait until bank is open
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.bank.opened();
                        }
                    }, antiBan.randomizeWait(230, 270), antiBan.randomizeWait(15, 25));
                }
            } else {
                ctx.camera.turnTo(ctx.bank.nearest());
            }
        }
    }
}
