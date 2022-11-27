package raytracer;

/**
 * The Log class
 *
 * Logging tasks, can extend to print to a txt file
 */

public class Log {
    /**
     *
     * @param   message
     */
    public static void error(String message) {
        System.err.println("ERROR: " + message);
    }

    /**
     *
     * @param   message
     */
    public static void info(String message) {
        System.out.println(message);
    }
}
