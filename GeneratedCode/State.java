import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
class Event{
   String send;
   public Event(String send){this.send = send;}
   public String execute(){return send;}
}
class Transition{
   String type;
   String event;
   String target;
   public Transition(String type, String event, String target){
       this.type = type;
       this.event = event;
       this.target = target;
   }
   public String submit(String trig){
       if(trig.equals(event)){
           return target;
       }
       return null;
   }
}
class State {
   private String name;
   private ArrayList<Transition> transitions;
   private ArrayList<Event> onentry;
   private ArrayList<Event> onexit;
    public State(String name, ArrayList<Transition> transitions, ArrayList<Event> onentry, ArrayList<Event> onexit) {
       this.name = name;
       this.transitions = transitions;
       this.onentry = onentry;
       this.onexit = onexit;
   }
    public void onentry(){
       System.out.println("Entering state " + this.name);
       for(Event e : onentry){e.execute();}
   }
    public void onexit(){
       System.out.println("Exiting state " + this.name);
       for(Event e : onexit){e.execute();}
   }
    public String trigger(String input){
       String next = null;
       for(Transition t : transitions) {
           next = t.submit(input);
           if (next != null) break;
       }
       return next;
   }
public static void main(String[] args) {
    HashMap<String, State> states = new HashMap<String, State>();
   String name;
   ArrayList<Transition> transitions = new ArrayList<Transition>();
   ArrayList<Event> onentry = new ArrayList<Event>();
   ArrayList<Event> onexit = new ArrayList<Event>();


    name = "Start2";
    transitions.add(new Transition("external", "Next", "State_4"));
    states.put(name, new State(name, transitions, onentry, onexit));
    name = ""; transitions = new ArrayList<Transition>(); onentry = new ArrayList<Event>(); onexit = new ArrayList<Event>();


    name = "End";
    states.put(name, new State(name, transitions, onentry, onexit));
    name = ""; transitions = new ArrayList<Transition>(); onentry = new ArrayList<Event>(); onexit = new ArrayList<Event>();


    name = "State_4";
    transitions.add(new Transition("external", "End", "End"));
    transitions.add(new Transition("external", "Back", "Start2"));
    states.put(name, new State(name, transitions, onentry, onexit));
    name = ""; transitions = new ArrayList<Transition>(); onentry = new ArrayList<Event>(); onexit = new ArrayList<Event>();

    State current = states.get("Start2");
    current.onentry();
    while(true){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            String s = br.readLine();
            String nextState = current.trigger(s);
            if (!nextState.equals("")){
               current.onexit();
               current = states.get(nextState);
               current.onentry();
            }
        }catch(Exception e){
        }}
}}
