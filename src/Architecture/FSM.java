package Architecture;

import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by guillaume on 10/03/17.
 */
public class FSM {
    public FSM(){}
    public void print(String fileName){
        // Includes
        System.out.println("import java.util.ArrayList;");
        System.out.println("import java.util.HashMap;");
        System.out.println("import java.io.BufferedReader;");
        System.out.println("import java.io.InputStreamReader;");
        System.out.println("import java.lang.reflect.Method;");
        System.out.println("import java.lang.reflect.InvocationTargetException;");

        System.out.println("public class FSM {\n" +
                "\tpublic static HashMap<String, State> states = new HashMap<String, State>();\n" +
                "\tpublic static HashMap<String, Runnable> registered = new HashMap<String, Runnable>();\n");

        org.jdom2.Document jdomDoc;

        System.out.println("\tpublic State current;");


        try {

            // Methods
            System.out.println("\tpublic void submitEvent(String eve){\n \t\tSystem.out.println(\"\t\t ** Submitting event : \" + eve + \" **\");");
            System.out.println("\t\tArrayList<String> event = new ArrayList<String>();\n" +
                    "\t\tArrayList<String> eventRet = new ArrayList<String>();\n" +
                    "\t\t\ttry{\n" +
                    "\t\t\t\tString nextState = current.trigger(eve);\n" +
                    "\t\t\t\twhile (!nextState.equals(\"\")){\n" +
                    "\t\t\t\t\tevent.addAll(current.onexit());\n" +
                    "\t\t\t\t\tcurrent = states.get(nextState);\n" +
                    "\t\t\t\t\tevent.addAll(current.onentry());\n" +
                    "\t\t\t\t\tnextState = \"\";\n" +
                    "\t\t\t\t\twhile(event.size() != 0){\n" +
                    "\t\t\t\t\t\tString tmp = current.trigger(event.get(0));\n" +
                    "\t\t\t\t\t\tif(tmp != \"\" && tmp != null){\n" +
                    "\t\t\t\t\t\t\tnextState = tmp;\n" +
                    "\t\t\t\t\t\t\tevent.addAll(current.onexit());\n" +
                    "\t\t\t\t\t\t\tcurrent = states.get(nextState);\n" +
                    "\t\t\t\t\t\t\tevent.addAll(current.onentry());\n" +
                    "\t\t\t\t\t\t\tnextState = \"\";\n" +
                    "\t\t\t\t\t\t}\n" +
                    "\t\t\t\t\t\tevent.remove(0);\n" +
                    "\t\t\t\t\t}\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t}catch(Exception e){\n" +
                    "\t\t\t\tSystem.out.println(e);\n" +
                    "\t\t\t}\n" +
                    "\t\t}");

            System.out.print("\tpublic void register(String eve, Object c, Method method){\n" +
                    "\t\t\tregistered.put(eve,  ()-> {\n" +
                    "\t\t\t\ttry {\n" +
                    "\t\t\t\t\tmethod.invoke(c);\n" +
                    "\t\t\t\t} catch (IllegalAccessException e) {\n" +
                    "\t\t\t\t\te.printStackTrace();\n" +
                    "\t\t\t\t} catch (InvocationTargetException e) {\n" +
                    "\t\t\t\t\te.printStackTrace();\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t});" +
                    "\t}\n");

            // Constructor()
            System.out.println("\tpublic FSM(){}");
            // Init print
            System.out.println("\tpublic void init() {");

            //we can create JDOM Document from DOM, SAX and STAX Parser Builder classes
            jdomDoc = useDOMParser(fileName);
            Element root = jdomDoc.getRootElement();
            String initState="";
            if (root.getAttribute("initial") != null){
                initState = root.getAttribute("initial").getValue();
            }
            List<Element> empListElements = root.getChildren();

            // State creation
            String name;

            System.out.println("\t\tString name;\n" +
                    "\t\tArrayList<Transition> transitions = new ArrayList<Transition>();\n" +
                    "\t\tArrayList<Event> onentry = new ArrayList<Event>();\n" +
                    "\t\tArrayList<Event> onexit = new ArrayList<Event>();\n");
            for (int i = 0; i < empListElements.size(); i++) {
                if (empListElements.get(i).getAttribute("id") != null) {
                    if (i == 0) {
                        if (initState == "") {
                            initState = empListElements.get(i).getAttribute("id").getValue();
                        }
                    }
                    System.out.println("\n\t\tname = " + "\"" + empListElements.get(i).getAttribute("id").getValue() + "\";");

                    for (Element elem : empListElements.get(i).getChildren()) {
                        switch (elem.getName()) {
                            case "onentry":
                                for(int j = 0; j < elem.getChildren().size(); j++ ) {
                                    System.out.println("\t\tonentry.add(new Event(" + "\"" + elem.getChildren().get(j).getAttribute("event").getValue() + "\"));");
                                }
                                break;
                            case "transition":
                                String event = "";
                                String type = "";
                                if (elem.getAttribute("event") != null)event = elem.getAttribute("event").getValue();
                                if (elem.getAttribute("type") != null)type = elem.getAttribute("type").getValue();
                                System.out.println("\t\ttransitions.add(new Transition(\"" + type + "\", \"" + event + "\", \"" + elem.getAttribute("target").getValue() + "\"));");
                                break;
                            case "onexit":
                                for(int h = 0; h < elem.getChildren().size(); h++ ) {
                                    System.out.println("\t\tonexit.add(new Event(" + "\"" + elem.getChildren().get(h).getAttribute("event").getValue() + "\"));");
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    System.out.println("\t\tstates.put(name, new State(name, transitions, onentry, onexit));");
                    System.out.println("\t\tname = \"\"; transitions = new ArrayList<Transition>();\n\t\tonentry = new ArrayList<Event>();\n\t\tonexit = new ArrayList<Event>();\n");
                }
            }
            System.out.println("\t\tcurrent = states.get(\""+ initState +"\");");
            System.out.println("\t\tcurrent.onentry();");


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        /*
        System.out.println("\t\tArrayList<String> event = new ArrayList<String>();\n" +
                "\t\tArrayList<String> eventRet = new ArrayList<String>();\n" +
                "\t\twhile(true){\n" +
                "\t\t\tBufferedReader br = new BufferedReader(new InputStreamReader(System.in));\n" +
                "\t\t\ttry{\n" +
                "\t\t\t\tString s = br.readLine();\n" +
                "\t\t\t\tString nextState = current.trigger(s);\n" +
                "\t\t\t\twhile (!nextState.equals(\"\")){\n" +
                "\t\t\t\t\tevent.addAll(current.onexit());\n" +
                "\t\t\t\t\tcurrent = states.get(nextState);\n" +
                "\t\t\t\t\tevent.addAll(current.onentry());\n" +
                "\t\t\t\t\tnextState = \"\";\n" +
                "\t\t\t\t\twhile(event.size() != 0){\n" +
                "\t\t\t\t\t\tString tmp = current.trigger(event.get(0));\n" +
                "\t\t\t\t\t\tif(tmp != \"\" && tmp != null){\n" +
                "\t\t\t\t\t\t\tnextState = tmp;\n" +
                "\t\t\t\t\t\t\tevent.addAll(current.onexit());\n" +
                "\t\t\t\t\t\t\tcurrent = states.get(nextState);\n" +
                "\t\t\t\t\t\t\tevent.addAll(current.onentry());\n" +
                "\t\t\t\t\t\t\tnextState = \"\";\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t\tevent.remove(0);\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}catch(Exception e){\n" +
                "\t\t\t\tSystem.out.println(e);\n" +
                "\t\t\t}\n" +
                "\t\t}");
        System.out.println("\t}\n}");
        */
        System.out.println("}");

        // Object print
        Event e = new Event();
        e.print();
        Transition t = new Transition();
        t.print();
        State s = new State();
        s.print();
        System.out.println("}");
        }


        //Get JDOM document from DOM Parser
        private static org.jdom2.Document useDOMParser(String fileName) throws ParserConfigurationException, SAXException, IOException {
        //creating DOM Document
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(fileName));
        DOMBuilder domBuilder = new DOMBuilder();
        return domBuilder.build(doc);



    }
}

