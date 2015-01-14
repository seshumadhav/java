import java.util.logging.Level;
import java.util.logging.Logger;

public class LOG {

  private static final Logger LOG = Logger.getLogger("MongoDB");

  public static void log(String message) {
    LOG.info(message);
  }

  public static void log(String message, Throwable t) {
    LOG.log(Level.SEVERE, message, t);
  }

}
