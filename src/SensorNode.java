import io.jbotsim.core.Color;
import io.jbotsim.core.Message;
import io.jbotsim.ui.icons.Icons;

public class SensorNode extends TreeNode {

    private double value;
    private int cpt = 0;

    public SensorNode() {
        this.value = 0.0;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public void onSelection() {
        parent = this; // becomes root
        sendAll(new Message(this)); // send own reference to neighbors (see explanations)
        setIcon(Icons.STATION);
        setIconSize(16);
    }

    public void sense() {
        this.value = (Math.random()*255);
        System.out.println(value);
        setColor(new Color((int) value, 0, 0));

        if (children.isEmpty())
            send(parent, new Message(value));
        //Color color = new Color((int)(this.value), (int)(this.value), (int)(this.value));
    }

    @Override
    public void onMessage(Message message) {
        super.onMessage(message);

        if (message.getContent().getClass() == Double.class) {
            value = Math.max(this.value, (double)message.getContent());
            this.cpt++;
            if (cpt == children.size()) {
                if (parent == this)
                    System.out.println("La valeur max est : " + value);
                else {
                    send(parent, new Message(value));
                    cpt = 0;
                }
            }
        }
    }

    public void agregation() {
        if (children.isEmpty()) {
            if (parent != this) {
                getCommonLinkWith(parent).setColor(Color.blue);
                if (this.value > 55)
                    setColor(Color.red);
                else
                    setColor(Color.green);

                send(parent, new Message(value));
            }
        }
    }
}
