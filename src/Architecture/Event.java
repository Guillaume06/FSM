package Architecture;


/**
 * Created by guillaume on 03/03/17.
 */
public class Event {

    public void print(){
        System.out.println("\tclass Event extends Action {\n" +
                "\t\tString send = \"\";\n" +
                "\t\tpublic Event(String send){this.send = send;}\n" +
                "\t\t@Override\n" +
                "\t\tpublic Event execute(){ \n" +
                "\t\t\tSystem.out.println(\"\\tSending event \"  + send);\n" +
                "\t\t\tif (registered.get(send) != null) registered.get(send).run();\n" +
                "\t\t\treturn this;\n" +
                "\t\t}\n" +
                "\t}");

    }

}
