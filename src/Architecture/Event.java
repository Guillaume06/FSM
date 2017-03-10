package Architecture;


/**
 * Created by guillaume on 03/03/17.
 */
public class Event {

    public void print(){
        System.out.println("class Event{\n" +
                "\tString send = \"\";\n" +
                "\tpublic Event(String send){this.send = send;}\n" +
                "\tpublic Event execute(){ System.out.println(\"Sending event \"  + send); return this;}\n" +
                "}");

    }

}
