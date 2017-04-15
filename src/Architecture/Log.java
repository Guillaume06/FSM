package Architecture;


/**
 * Created by guillaume on 03/03/17.
 */
public class Log {

    public void print(){
        System.out.println("\tclass Log extends Action {\n" +
                "\t\tString log = \"\";\n" +
                "\t\tpublic Log(String log){this.log = log;}\n" +
                "\t\t@Override\n" +
                "\t\tpublic Event execute(){ \n" +
                "\t\t\tSystem.out.println(\"Log : \" + log); return null;\n" +
                "\t\t}\n" +
                "\t}");

    }

}
