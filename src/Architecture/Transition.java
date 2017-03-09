package Architecture;

/**
 * Created by guillaume on 03/03/17.
 */
public class Transition {
    private String name;

    public void print(){
        System.out.println("class Transition{\n" +
                "   String type;\n" +
                "   String event;\n" +
                "   String target;\n" +
                "   public Transition(String type, String event, String target){\n" +
                "       this.type = type;\n" +
                "       this.event = event;\n" +
                "       this.target = target;\n" +
                "   }\n" +
                "   public String submit(String trig){\n" +
                "       if(trig.equals(event)){\n" +
                "           return target;\n" +
                "       }\n" +
                "       return null;\n" +
                "   }\n" +
                "}");
    }

}
