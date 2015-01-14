import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * $ javac -cp rabbitmq-client.jar Send42.java Recv42.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar Send42
 * .....
 * 
 * $ rabbitmqctl list_queues
 * $ rabbitmqctl list_bindings
 * @ rabbitmqctl list_exchanges
 * 
 * This class symbolically is a TASK
 * 
 * New changes:
 * (1) We are trying routing w.t.h.o Direct exchange
 * (2) If sticky message, sender to "fanout" exchange
 * (3) If not, send to "queue"
 * 
 * @author smc
 * 
 */
public class Send42 {

  private static final String QUEUE_NAME      = "all3";
  private static final String EXCHANGE_FANOUT = "fq3";

  public static void main(String[] argv) throws java.io.IOException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // SETUP
    // Declare queue
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    // Fanout exchange
    channel.exchangeDeclare(EXCHANGE_FANOUT, "fanout");

    String message = getMessage(argv);
    if (isSticky(message)) {
      channel.basicPublish(EXCHANGE_FANOUT, "", null, message.getBytes());
      System.out.println(" [x] Sent sticky message: '" + message + "'");
    } else {
      channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
      System.out.println(" [x] Sent normal message: '" + message + "'");
    }

    channel.close();
    connection.close();
  }

  private static boolean isSticky(String message) {
    return message.contains("sticky");
  }

  private static String getSeverity(String[] strings) {
    if (strings.length < 1) {
      return "info";
    }
    return strings[0];
  }

  private static String getMessage(String[] strings) {
    if (strings.length < 1) {
      return "Hello World!";
    }
    return joinStrings(strings, " ");
  }

  private static String joinStrings(String[] strings, String delimiter) {
    int length = strings.length;
    if (length == 0) {
      return "";
    }
    StringBuilder words = new StringBuilder(strings[0]);
    for (int i = 1; i < length; i++) {
      words.append(delimiter).append(strings[i]);
    }
    return words.toString();
  }

}
