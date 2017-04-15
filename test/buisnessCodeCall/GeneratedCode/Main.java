import java.lang.reflect.Method;

/**
 * Created by guillaume on 16/03/17.
 */
public class Main {
    public static void test(){
        System.out.println("** Buisness code called **");
    }
    public static void main(String[] args){
        Main main = new Main();
        Class c = main.getClass();
        FSM m = new FSM();
        Method method = null;
        try {
            method = c.getDeclaredMethod("test");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        m.register("Test", main, method);
        m.init();
        m.submitEvent("Next");
        m.submitEvent("Next");
    }
}