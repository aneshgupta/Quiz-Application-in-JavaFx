
package listners;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;


public interface NewScreenListner extends EventHandler {

    public void ChangeScreen(Node node); 
    public void removeTopScreen();
    
}
