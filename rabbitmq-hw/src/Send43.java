import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * $ javac -cp rabbitmq-client.jar Send43.java Recv43.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar Send43
 * .....
 * 
 * $ rabbitmqctl list_queues
 * $ rabbitmqctl list_bindings
 * @ rabbitmqctl list_exchanges
 * 
 * This class symbolically is a TASK
 * 
 * New changes:
 * (1) One FanEx. N randomQueues, 1 standardQueue
 * 
 * @author smc
 * 
 */
public class Send43 {

  public static class Constants {

    public static final String EXCHANGE_TYPE_FANOUT = "fanout";
    public static final String fanEx                = "fanEx-1650";
  }

  public static Channel getChannel() throws IOException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    return channel;
  }

  public static void setUp(Channel channel, String fanExName)
      throws IOException {
    channel.exchangeDeclare(fanExName, Constants.EXCHANGE_TYPE_FANOUT);
  }

  public static void main(String[] argv) throws java.io.IOException {
    Channel channel = getChannel();
    String fanExName = Constants.fanEx;
    setUp(channel, fanExName);

    String message = getMessage(argv);
    boolean isSticky = isSticky(message);
    System.out.println("Message sent: '" + message + "' [isSticky = '"
        + isSticky + "']");
    channel.basicPublish(fanExName, "", null, message.getBytes());
    channel.close();
  }

  private static boolean isSticky(String message) {
    return message.contains("sticky");
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
