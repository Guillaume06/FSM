package Architecture;

import java.util.ArrayList;

/**
 * Created by guillaume on 03/03/17.
 */
public class State {
    public State(){}

    public void print(){

        // Class declaration + vars
        System.out.print("class State {\n" +
                "   private String name;\n" +
                "   private ArrayList<Transition> transitions;\n" +
                "   private ArrayList<Event> onentry;\n" +
                "   private ArrayList<Event> onexit;\n" );

        // Constructor
        System.out.println("    public State(String name, ArrayList<Transition> transitions, ArrayList<Event> onentry, ArrayList<Event> onexit) {\n" +
                "       this.name = name;\n" +
                "       this.transitions = transitions;\n" +
                "       this.onentry = onentry;\n" +
                "       this.onexit = onexit;\n" +
                "   }");

        // Onentry
        System.out.println("    public void onentry(){\n" +
                "       System.out.println(\"Entering state \" + this.name);\n" +
                "       for(Event e : onentry){e.execute();}\n" +
                "   }");

        //Onexit
        System.out.println("    public void onexit(){\n" +
                "       System.out.println(\"Exiting state \" + this.name);\n" +
                "       for(Event e : onexit){e.execute();}\n" +
                "   }");

        // Test for transitions trigger
        System.out.println("    public String trigger(String input){\n" +
                "       String next = null;\n" +
                "       for(Transition t : transitions) {\n" +
                "           next = t.submit(input);\n" +
                "           if (next != null) break;\n" +
                "       }\n" +
                "       return next;\n" +
                "   }");


    }
}
