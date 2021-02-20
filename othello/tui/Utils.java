package othello.tui;

/**
 * @author  Emma Campbell & Emma Schechter
 * @since   02-09-2021
 */
public class Utils {

    /**
     * Clear the output from the terminal
     */
    public final static void clear() {
        System.out.println("\033[2J\033[1;1H");
    }
    
    /**
     * Wait the specified amount of seconds
     * 
     * @param time      amount of time to wait
     */
    public final static void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
