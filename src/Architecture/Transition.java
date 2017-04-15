package Architecture;

/**
 * Created by guillaume on 03/03/17.
 */
public class Transition {
    private String name;

    public void print(){
        System.out.println("\tclass Transition{\n" +
                "\t\tString type;\n" +
                "\t\tString target;\n" +
                "\t\tString event;\n" +
                "\t\tpublic Transition(String type, String event, String target){\n" +
                "\t\t\tthis.type = type;\n" +
                "\t\t\tthis.event = event;\n" +
                "\t\t\tthis.target = target;\n" +
                "\t\t}\n" +
                "\t\tpublic String submit(String trig){\n" +
                "\t\t\tif (event == \"\" || event == null) return target;\n" +
                "\t\t\tif(trig.equals(event)){\n" +
                "\t\t\t\treturn target;\n" +
                "\t\t\t}\n" +
                "\t\t\treturn null;\n" +
                "\t\t}\n" +
                "\t}");
    }

}
