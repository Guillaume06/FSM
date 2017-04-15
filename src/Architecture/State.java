package Architecture;

import java.util.ArrayList;

/**
 * Created by guillaume on 03/03/17.
 */
public class State {
    public State(){}

    public void print(){

        // Class declaration + vars
        System.out.print("\tclass State {\n" +
                "\t\tprivate String name;\n" +
                "\t\tprivate ArrayList<Transition> transitions;\n" +
                "\t\tprivate ArrayList<Action> onentry;\n" +
                "\t\tprivate ArrayList<Action> onexit;\n" );

        // Constructor
        System.out.println("\t\tpublic State(String name, ArrayList<Transition> transitions, ArrayList<Action> onentry, ArrayList<Action> onexit) {\n" +
                "\t\t\tthis.name = name;\n" +
                "\t\t\tthis.transitions = transitions;\n" +
                "\t\t\tthis.onentry = onentry;\n" +
                "\t\t\tthis.onexit = onexit;\n" +
                "\t\t}");

        // Onentry
        System.out.println("\t\tpublic ArrayList<String> onentry(){\n" +
                "\t\t\tSystem.out.println(\"*Onentry*\");\n" +
                "\t\t\tArrayList<String> ret = new ArrayList<String>();\n" +
                "\t\t\tSystem.out.println(\"Entering state \" + this.name);\n" +
                "\t\t\tEvent event = null;\n" +
                "\t\t\tfor(Action e : onentry){\n" +
                "\t\t\t\tevent = e.execute();\n" +
                "\t\t\t\tif(event != null){ret.add(event.send);} \n" +
                "\t\t\t\tevent = null;\n" +
                "\t\t\t}\n" +
                "\t\t\tSystem.out.println(\"*End of Onentry*\");\n" +
                "\t\t\treturn ret;\n" +
                "\t\t}");

        //Onexit
        System.out.println("\t\tpublic ArrayList<String> onexit(){\n" +
                "\t\t\tArrayList<String> ret = new ArrayList<String>();\n" +
                "\t\t\tSystem.out.println(\"*Onexit*\");\n" +
                "\t\t\tEvent event = null;\n" +
                "\t\t\tSystem.out.println(\"Exiting state \" + this.name);\n" +
                "\t\t\tfor(Action e : onexit){\n" +
                "\t\t\t\tevent = e.execute();\n" +
                "\t\t\t\tif(event != null){ret.add(event.send);}\n" +
                "\t\t\t\tevent = null;\n" +
                "\t\t\t}\n" +
                "\t\t\tSystem.out.println(\"*End of Onexit*\");\n" +
                "\t\t\treturn ret;\n" +
                "\t\t}");

        // Test for transitions trigger
        System.out.println("\t\tpublic String trigger(String input){\n" +
                "\t\t\tString next = \"\";\n" +
                "\t\t\tfor(Transition t : transitions) {\n" +
                "\t\t\t\tnext = t.submit(input);\n" +
                "\t\t\t\tif (next != null || next != \"\") return next;\n" +
                "\t\t\t}\n" +
                "\t\t\treturn next;\n" +
                "\t\t}\n" +
                "\t}");


    }
}
