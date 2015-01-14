import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * $ javac -cp rabbitmq-client.jar Send41.java Recv41.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar Send41
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
 * (2) If message to send does not have substring sticky, sender must send with routing key: ""
 * (3) If message to send has the substring sticky, sender must send with routing key: "sticky"
 * 
 * @author smc
 * 
 */
public class Send41 {

  private static final String EXCHANGE_DIRECT = "be-1431";

  public static void main(String[] argv) throws java.io.IOException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // nameless default exchange
    channel.queueDeclare("sticky-1431", false, false, false, null);

    // Direct exchange
    channel.exchangeDeclare(EXCHANGE_DIRECT, "direct");

    String message = getMessage(argv);
    if (isSticky(message)) {
      channel.basicPublish("", "sticky-1431", null, message.getBytes());
      System.out.println(" [x] Sent sticky message: '" + message + "'");
    } else {
      channel.basicPublish(EXCHANGE_DIRECT, "", null, message.getBytes());
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
