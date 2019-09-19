package osrs;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import osrs.tasks.Bank;
import osrs.tasks.Drop;
import osrs.tasks.Mine;
import osrs.tasks.Walk;

import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name="QuickMining", description="Tutorial", properties="client=4; author=Joe; top=998;")

public class QuickMining extends PollingScript<ClientContext>{

    List<Task> taskList = new ArrayList<>();

    @Override
    public void start() {
        //System.out.println("Started");
        ///taskList.add(new Drop(ctx));
        taskList.add(new Bank(ctx));
        taskList.add(new Walk(ctx));
        taskList.add(new Mine(ctx));
    }

    @Override
    public void poll() {
        //System.out.println("Polling");
        for (Task task : taskList) {
            // If user clicks stop, break
            if (ctx.controller.isStopping()) {
                break;
            }
            //System.out.println("Adding Task");
            if (task.activate()) {
                task.execute();
                // Break will help in the future for prioritizing tasks
                break;
            }
        }
    }
}
