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
                "\tprivate String name;\n" +
                "\tprivate ArrayList<Transition> transitions;\n" +
                "\tprivate ArrayList<Action> onentry;\n" +
                "\tprivate ArrayList<Action> onexit;\n" );

        // Constructor
        System.out.println("\tpublic State(String name, ArrayList<Transition> transitions, ArrayList<Action> onentry, ArrayList<Action> onexit) {\n" +
                "\t\tthis.name = name;\n" +
                "\t\tthis.transitions = transitions;\n" +
                "\t\tthis.onentry = onentry;\n" +
                "\t\tthis.onexit = onexit;\n" +
                "\t\t}");

        // Onentry
        System.out.println("\tpublic ArrayList<String> onentry(){\n" +
                "\t\tSystem.out.println(\"*Onentry*\");\n" +
                "\t\tArrayList<String> ret = new ArrayList<String>();\n" +
                "\t\tSystem.out.println(\"Entering state \" + this.name);\n" +
                "\t\tEvent event = null;\n" +
                "\t\tfor(Action e : onentry){event = e.execute();if(event != null){ret.add(event.send);} event = null;}\n" +
                "\t\tSystem.out.println(\"*End of Onentry*\");\n" +
                "\t\treturn ret;\n" +
                "\t}");

        //Onexit
        System.out.println("\tpublic ArrayList<String> onexit(){\n" +
                "\t\tArrayList<String> ret = new ArrayList<String>();\n" +
                "\t\tSystem.out.println(\"*Onexit*\");\n" +
                "\t\t Event event = null;" +
                "\t\tSystem.out.println(\"Exiting state \" + this.name);\n" +
                "\t\tfor(Action e : onexit){event = e.execute();if(event != null){ret.add(event.send);} event = null;}\n" +
                "\t\tSystem.out.println(\"*End of Onexit*\");\n" +
                "\t\treturn ret;\n" +
                "\t}");

        // Test for transitions trigger
        System.out.println("\tpublic String trigger(String input){\n" +
                "\t\tString next = \"\";\n" +
                "\t\tfor(Transition t : transitions) {\n" +
                "\t\t\tnext = t.submit(input);\n" +
                "\t\t\tif (next != null || next != \"\") return next;\n" +
                "\t\t}\n" +
                "\t\treturn next;\n" +
                "\t}\n}");


    }
}
