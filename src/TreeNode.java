import io.jbotsim.core.Message;
import io.jbotsim.core.Color;
import io.jbotsim.core.Node;

import java.util.ArrayList;
import java.util.List;

public class TreeNode extends Node {
    Node parent = null;
    List<Node> children = new ArrayList<Node>();

    @Override
    public void onSelection() {
        parent = this; // becomes root
        sendAll(new Message(this)); // send own reference to neighbors (see explanations)
        setColor(Color.red);
    }

    @Override
    public void onMessage(Message message) {
        if (parent == null) {
            parent = message.getSender();
            getCommonLinkWith(parent).setWidth(4);
            sendAll(new Message(parent)); // propagate wave to all neighbors
        } else {
            if (message.getContent() == this){
                children.add(message.getSender());
            }
        }
    }
}
