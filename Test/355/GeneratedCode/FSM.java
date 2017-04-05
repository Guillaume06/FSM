import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
public class FSM {
	public static HashMap<String, State> states = new HashMap<String, State>();
	public static HashMap<String, Runnable> registered = new HashMap<String, Runnable>();

	public State current;
	public void submitEvent(String eve){
 		System.out.println("		 ** Submitting event : " + eve + " **");
		ArrayList<String> event = new ArrayList<String>();
		ArrayList<String> eventRet = new ArrayList<String>();
			try{
				String nextState = current.trigger(eve);
				while (!nextState.equals("")){
					event.addAll(current.onexit());
					current = states.get(nextState);
					event.addAll(current.onentry());
					nextState = "";
					while(event.size() != 0){
						String tmp = current.trigger(event.get(0));
						if(tmp != "" && tmp != null){
							nextState = tmp;
							event.addAll(current.onexit());
							current = states.get(nextState);
							event.addAll(current.onentry());
							nextState = "";
						}
						event.remove(0);
					}
				}
			}catch(Exception e){
				System.out.println(e);
			}
		}
	public void register(String eve, Object c, Method method){
			registered.put(eve,  ()-> {
				try {
					method.invoke(c);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			});	}
	public FSM(){}
	public void init() {
		String name;
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		ArrayList<Event> onentry = new ArrayList<Event>();
		ArrayList<Event> onexit = new ArrayList<Event>();


		name = "s0";
		transitions.add(new Transition("", "", "pass"));
		states.put(name, new State(name, transitions, onentry, onexit));
		name = ""; transitions = new ArrayList<Transition>();
		onentry = new ArrayList<Event>();
		onexit = new ArrayList<Event>();


		name = "s1";
		transitions.add(new Transition("", "", "fail"));
		states.put(name, new State(name, transitions, onentry, onexit));
		name = ""; transitions = new ArrayList<Transition>();
		onentry = new ArrayList<Event>();
		onexit = new ArrayList<Event>();


		name = "pass";
		onentry.add(new Event("pass"));
		states.put(name, new State(name, transitions, onentry, onexit));
		name = ""; transitions = new ArrayList<Transition>();
		onentry = new ArrayList<Event>();
		onexit = new ArrayList<Event>();


		name = "fail";
		onentry.add(new Event("fail"));
		states.put(name, new State(name, transitions, onentry, onexit));
		name = ""; transitions = new ArrayList<Transition>();
		onentry = new ArrayList<Event>();
		onexit = new ArrayList<Event>();

		current = states.get("s0");
		current.onentry();
}
class Event{
	String send = "";
	public Event(String send){this.send = send;}
	public Event execute(){ 
		System.out.println("\tSending event "  + send);
		if (registered.get(send) != null) registered.get(send).run();
		return this;
	}
}
class Transition{
	String type;
	String target;
	String event;
	public Transition(String type, String event, String target){
		this.type = type;
		this.event = event;
		this.target = target;
	}
	public String submit(String trig){
		if (event == "") return target;
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
	public ArrayList<String> onentry(){
		System.out.println("*Onentry*");
		ArrayList<String> ret = new ArrayList<String>();
		System.out.println("Entering state " + this.name);
		Event event = null;
		for(Event e : onentry){event = e.execute();if(event != null){ret.add(event.send);} event = null;}
		System.out.println("*End of Onentry*");
		return ret;
	}
	public ArrayList<String> onexit(){
		ArrayList<String> ret = new ArrayList<String>();
		System.out.println("*Onexit*");
		 Event event = null;		System.out.println("Exiting state " + this.name);
		for(Event e : onexit){event = e.execute();if(event != null){ret.add(event.send);} event = null;}
		System.out.println("*End of Onexit*");
		return ret;
	}
	public String trigger(String input){
		String next = null;
		for(Transition t : transitions) {
			next = t.submit(input);
			if (next != null) break;
		}
		return next;
	}
}
}
