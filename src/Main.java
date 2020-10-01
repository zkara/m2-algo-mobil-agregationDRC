import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.core.event.CommandListener;
import io.jbotsim.ui.JViewer;

public class Main {
    public static void main(String[] args) {
        Topology tp = new Topology();
        tp.setDefaultNodeModel(SensorNode.class);
        tp.setTimeUnit(500);
        tp.addCommand("Update values");
        tp.addCommandListener(new CommandListener() {
            @Override
            public void onCommand(String command) {
                if (command.equals("Update values"))
                    for (Node node : tp.getNodes())
                        ((SensorNode)node).sense();
            }
        });
        new JViewer(tp);
        tp.start();
    }
}
