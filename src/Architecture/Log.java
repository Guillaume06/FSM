package Architecture;


/**
 * Created by guillaume on 03/03/17.
 */
public class Log {

    public void print(){
        System.out.println("class Log extends Action {\n" +
                "\tString log = \"\";\n" +
                "\tpublic Log(String log){this.log = log;}\n" +
                "\t@Override\n" +
                "\tpublic Event execute(){ \n" +
                "\t\tSystem.out.println(\"Log : \" + log); return null;\n" +
                "\t}\n" +
                "}");

    }

}
