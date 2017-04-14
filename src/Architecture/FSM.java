package Architecture;

import org.jdom2.Attribute;
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
        System.out.println("\tpublic  ArrayList<String> event = new ArrayList<String>();");


        try {

            // Methods
            System.out.println("\tpublic void start(String state){\n" +
                    "\t\ttry{ \n" +
                    "\t\t\tString nextState = state;\n" +
                    "\t\t\tdo{ \n" +
                    "\t\t\t\tcurrent = states.get(nextState);\n" +
                    "\t\t\t\tnextState = \"\";\n" +
                    "\t\t\t\tevent.addAll(current.onentry());" +
                    "\t\t\t\twhile(event.size() != 0){\n" +
                    "\t\t\t\t\tString tmp = current.trigger(event.get(0));\n" +
                    "\t\t\t\t\tif(tmp != \"\" && tmp != null){\n" +
                    "\t\t\t\t\t\tnextState = tmp;\n" +
                    "\t\t\t\t\t\tevent.addAll(current.onexit());\n" +
                    "\t\t\t\t\t\tcurrent = states.get(nextState);\n" +
                    "\t\t\t\t\t\tevent.addAll(current.onentry());\n" +
                    "\t\t\t\t\t\tnextState = \"\";\n" +
                    "\t\t\t\t\t}\n" +
                    "\t\t\t\t\tevent.remove(0);\n" +
                    "\t\t\t\t}" +
                    "\t\t\t}while (!(nextState == null) && !nextState.equals(\"\"));\n" +
                    "\t\t}catch(Exception e){\n" +
                    "\t\t\tSystem.out.println(\"Exception : \" + e);\n" +
                    "\t\t}\n" +
                    "\t}");

            System.out.println("\tpublic void submitEvent(String eve){\n" +
                    "\t\tif(eve != \"\" && eve != null)System.out.println(\"\t\t ** Submitting event : \" + eve + \" **\");");
            System.out.println("\t\tArrayList<String> eventRet = new ArrayList<String>();\n" +
                    "\t\t\ttry{\n" +
                    "\t\t\t\tString nextState = current.trigger(eve);\n" +
                    "\t\t\t\twhile (!(nextState == null) && !nextState.equals(\"\")){\n" +
                    "\t\t\t\t\tevent.addAll(current.onexit());\n" +
                    "\t\t\t\t\tcurrent = states.get(nextState);\n" +
                    "\t\t\t\t\tnextState = \"\";\n" +
                    "\t\t\t\t\tevent.addAll(current.onentry());\n" +
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
                    "\t\t\t\tSystem.out.println(\"Exception : \" + e);\n" +
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
                    "\t\tArrayList<Action> onentry = new ArrayList<Action>();\n" +
                    "\t\tArrayList<Action> onexit = new ArrayList<Action>();\n");
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
                                for(Element e :  elem.getChildren()) {
                                    if (e.getName().equals("log")) {
                                        System.out.println("\t\tonentry.add(new Log(" + "\"" + e.getAttribute("label").getValue() + "\"));");
                                    }
                                    if (e.getName().equals("send")) {
                                        System.out.println("\t\tonentry.add(new Event(" + "\"" + e.getAttribute("event").getValue() + "\"));");
                                    }
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
                                for(Element e :  elem.getChildren()) {
                                    if (e.getName().equals("log")) {
                                        System.out.println("\t\tonexit.add(new Log(" + "\"" + e.getAttribute("label").getValue() + "\"));");
                                    }
                                    if (e.getName().equals("send")) {
                                        System.out.println("\t\tonexit.add(new Event(" + "\"" + e.getAttribute("event").getValue() + "\"));");
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    System.out.println("\t\tstates.put(name, new State(name, transitions, onentry, onexit));");
                    System.out.println("\t\tname = \"\"; transitions = new ArrayList<Transition>();\n\t\tonentry = new ArrayList<Action>();\n\t\tonexit = new ArrayList<Action>();\n");
                }
            }
            System.out.println("\t\tstart(\""+ initState +"\");");
            System.out.println("\t\tsubmitEvent(\"\");");


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("}");

        // Object print
        Action a = new Action();
        a.print();
        Event e = new Event();
        e.print();
        Transition t = new Transition();
        t.print();
        State s = new State();
        s.print();
        Log l = new Log();
        l.print();
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

