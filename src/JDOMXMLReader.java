
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import Architecture.Event;
import Architecture.Transition;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.StAXEventBuilder;
import org.jdom2.input.StAXStreamBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import Architecture.State;


public class JDOMXMLReader {

    public static void main(String[] args) {
        final String fileName = "data/StateChart.scxml";
        org.jdom2.Document jdomDoc;
        try {
            // Includes
            System.out.println("import java.util.ArrayList;");
            System.out.println("import java.util.HashMap;");
            System.out.println("import java.io.BufferedReader;");
            System.out.println("import java.io.IOException;");
            System.out.println("import java.io.InputStreamReader;");


            // Object print
            Event e = new Event();
            e.print();
            Transition t = new Transition();
            t.print();
            State s = new State();
            s.print();


            // Main print
            System.out.println("public static void main(String[] args) {");

            // Global var
            System.out.println("    HashMap<String, State> states = new HashMap<String, State>();");

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

            System.out.println("   String name;\n" +
                    "   ArrayList<Transition> transitions = new ArrayList<Transition>();\n" +
                    "   ArrayList<Event> onentry = new ArrayList<Event>();\n" +
                    "   ArrayList<Event> onexit = new ArrayList<Event>();\n");
            for (int i = 0; i < empListElements.size(); i++) {
                if (empListElements.get(i).getAttribute("id") != null) {
                    if (i == 0) {
                        if (initState == "") {
                            initState = empListElements.get(i).getName();
                        }
                    }
                    System.out.println("\n    name = " + "\"" + empListElements.get(i).getAttribute("id").getValue() + "\";");

                    for (Element elem : empListElements.get(i).getChildren()) {
                        switch (elem.getName()) {
                            case "onentry":
                                System.out.println("    onentry.add(new Event(" + "\"" + elem.getChildren().get(0).getAttribute("event").getValue() + "\"));");
                                break;
                            case "transition":
                                System.out.println("    transitions.add(new Transition(\"" + elem.getAttribute("type").getValue() + "\", \"" + elem.getAttribute("event").getValue() + "\", \"" + elem.getAttribute("target").getValue() + "\"));");
                                break;
                            case "onexit":
                                System.out.println("    onexit.add(new Event(" + "\"" + elem.getChildren().get(0).getAttribute("event").getValue() + "\"));");
                                break;
                            default:
                                break;
                        }
                    }
                    System.out.println("    states.put(name, new State(name, transitions, onentry, onexit));");
                    System.out.println("    name = \"\"; transitions = new ArrayList<Transition>(); onentry = new ArrayList<Event>(); onexit = new ArrayList<Event>();\n");
                }
            }
            System.out.println("    State current = states.get(\""+ initState +"\");");
            System.out.println("    current.onentry();");


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("    while(true){\n" +
                "        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));\n" +
                "        try{\n" +
                "            String s = br.readLine();\n" +
                "            String nextState = current.trigger(s);\n" +
                "            if (!nextState.equals(\"\")){\n" +
                "               current.onexit();\n" +
                "               current = states.get(nextState);\n" +
                "               current.onentry();\n" +
                "            }\n" +
                "        }catch(Exception e){\n" +
                "        }}");
        System.out.println("}}");

    }


    //Get JDOM document from DOM Parser
    private static org.jdom2.Document useDOMParser(String fileName)
            throws ParserConfigurationException, SAXException, IOException {
        //creating DOM Document
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(fileName));
        DOMBuilder domBuilder = new DOMBuilder();
        return domBuilder.build(doc);

    }
}