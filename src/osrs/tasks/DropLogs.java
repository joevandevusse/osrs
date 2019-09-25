package osrs.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;
import osrs.Task;

import java.util.concurrent.Callable;

public class DropLogs extends Task {

    final static int NORMAL_LOGS = 1511;

    public DropLogs(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        System.out.println(ctx.inventory.count());
        return ctx.inventory.select().count() > 27;
    }

    @Override
    public void execute() {
        //System.out.println("Dropping");
        for (Item logs : ctx.inventory.select().id(NORMAL_LOGS)) {
            // If user clicks stop, break
            if (ctx.controller.isStopping()) {
                break;
            }
            final int startNumLogs = ctx.inventory.select().id(NORMAL_LOGS).count();
            logs.interact("Drop", "Logs");

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    // Break out when player is acting, don't spam click
                    return ctx.inventory.select().id(NORMAL_LOGS).count() != startNumLogs;
                }
            }, 50, 25);
        }
    }
}
