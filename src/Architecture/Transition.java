package Architecture;

/**
 * Created by guillaume on 03/03/17.
 */
public class Transition {
    private String name;

    public void print(){
        System.out.println("class Transition{\n" +
                "\tString type;\n" +
                "\tString target;\n" +
                "\tString event;\n" +
                "\tpublic Transition(String type, String event, String target){\n" +
                "\t\tthis.type = type;\n" +
                "\t\tthis.event = event;\n" +
                "\t\tthis.target = target;\n" +
                "\t}\n" +
                "\tpublic String submit(String trig){\n" +
                "\t\t if (event == \"\") return target\n;" +
                "\t\t\tif(trig.equals(event)){\n" +
                "\t\t\t\treturn target;\n" +
                "\t\t}\n" +
                "\t\treturn null;\n" +
                "\t}\n" +
                "}");
    }

}
