package Architecture;


/**
 * Created by guillaume on 03/03/17.
 */
public class Event {

    public void print(){
        System.out.println("class Event{\n" +
                "   String send = \"\";\n" +
                "   public Event(String send){this.send = send;}\n" +
                "   public String execute(){ System.out.println(\"Sending event \"  + send); return send;}\n" +
                "}");

    }

}
