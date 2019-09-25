package osrs;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import osrs.tasks.*;

import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name="QuickWoodcutting", description="Tutorial", properties="client=4; author=Joe; top=997;")

public class QuickWoodcutting extends PollingScript<ClientContext> {

    List<Task> taskList = new ArrayList<>();

    @Override
    public void start() {
        taskList.add(new DropLogs(ctx));
        //taskList.add(new Bank(ctx));
        //taskList.add(new Walk(ctx));
        taskList.add(new Chop(ctx));
    }

    @Override
    public void poll() {
        for (Task task : taskList) {
            // If user clicks stop, break
            if (ctx.controller.isStopping()) {
                break;
            }
            if (task.activate()) {
                task.execute();
                // Break will help in the future for prioritizing tasks
                break;
            }
        }
    }
}
