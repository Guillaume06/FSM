package Architecture;

import java.util.ArrayList;

/**
 * Created by guillaume on 03/03/17.
 */
public class State {
    private String name;
    private ArrayList<Transition> transitions;
    private ArrayList<Event> onentry;
    private ArrayList<Event> onexit;
}
