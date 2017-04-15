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
	public ArrayList<String> event = new ArrayList<String>();
	public void start(String state){
		try{ 
			String nextState = state;
			do{ 
				current = states.get(nextState);
				nextState = "";
				event.addAll(current.onentry());
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
			}while (!(nextState == null) && !nextState.equals(""));
		}catch(Exception e){
			System.out.println("Exception : " + e);
		}
	}
	public void submitEvent(String eve){
		if(eve != "" && eve != null)System.out.println("** Submitting event : " + eve + " **");
		try{
			String nextState = current.trigger(eve);
			while (!(nextState == null) && !nextState.equals("")){
				event.addAll(current.onexit());
				current = states.get(nextState);
				nextState = "";
				event.addAll(current.onentry());
				String tmp = current.trigger("");
				if(tmp != "" && tmp != null){
					nextState = tmp;
					event.addAll(current.onexit());
					current = states.get(nextState);
					event.addAll(current.onentry());
					nextState = "";
				}
				while(event.size() != 0){
					tmp = current.trigger(event.get(0));
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
			System.out.println("Exception : " + e);
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
		});
	}
	public FSM(){}
	public void init() {
		String name;
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		ArrayList<Action> onentry = new ArrayList<Action>();
		ArrayList<Action> onexit = new ArrayList<Action>();


		name = "Pass";
		onentry.add(new Log("Entering state pass"));
		states.put(name, new State(name, transitions, onentry, onexit));
		name = ""; transitions = new ArrayList<Transition>();
		onentry = new ArrayList<Action>();
		onexit = new ArrayList<Action>();

		start("Pass");
		submitEvent("");
	}
	class Action{
		public Event execute(){return null;} 
	}
	class Event extends Action {
		String send = "";
		public Event(String send){this.send = send;}
		@Override
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
			if (event == "" || event == null) return target;
			if(trig.equals(event)){
				return target;
			}
			return null;
		}
	}
	class State {
		private String name;
		private ArrayList<Transition> transitions;
		private ArrayList<Action> onentry;
		private ArrayList<Action> onexit;
		public State(String name, ArrayList<Transition> transitions, ArrayList<Action> onentry, ArrayList<Action> onexit) {
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
			for(Action e : onentry){
				event = e.execute();
				if(event != null){ret.add(event.send);} 
				event = null;
			}
			System.out.println("*End of Onentry*");
			return ret;
		}
		public ArrayList<String> onexit(){
			ArrayList<String> ret = new ArrayList<String>();
			System.out.println("*Onexit*");
			Event event = null;
			System.out.println("Exiting state " + this.name);
			for(Action e : onexit){
				event = e.execute();
				if(event != null){ret.add(event.send);}
				event = null;
			}
			System.out.println("*End of Onexit*");
			return ret;
		}
		public String trigger(String input){
			String next = "";
			for(Transition t : transitions) {
				next = t.submit(input);
				if (next != null || next != "") return next;
			}
			return next;
		}
	}
	class Log extends Action {
		String log = "";
		public Log(String log){this.log = log;}
		@Override
		public Event execute(){ 
			System.out.println("Log : " + log); return null;
		}
	}
}
