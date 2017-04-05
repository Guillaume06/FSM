package Architecture;


/**
 * Created by guillaume on 03/03/17.
 */
public class Event {

    public void print(){
        System.out.println("class Event{\n" +
                "\tString send = \"\";\n" +
                "\tpublic Event(String send){this.send = send;}\n" +
                "\tpublic Event execute(){ \n" +
                "\t\tSystem.out.println(\"\\tSending event \"  + send);\n" +
                "\t\tif (registered.get(send) != null) registered.get(send).run();\n" +
                "\t\treturn this;\n" +
                "\t}\n" +
                "}");

    }

}
