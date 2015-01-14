import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * $ javac -cp rabbitmq-client.jar Send4.java Recv4.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar Send4
 * .....
 * 
 * $ rabbitmqctl list_queues
 * $ rabbitmqctl list_bindings
 * @ rabbitmqctl list_exchanges
 * 
 * This class symbolically is a TASK
 * 
 * New changes:
 * (1) We create one exchange
 * (2) Write SEVERITY MESSAGE to it
 * 
 * @author smc
 * 
 */
public class Send4 {

  private static final String EXCHANGE_DIRECT = "direct_logs";

  public static void main(String[] argv) throws java.io.IOException {
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();

	channel.exchangeDeclare(EXCHANGE_DIRECT, "direct");

	String severity = getSeverity(argv);
	String message = getMessage(argv);

	channel.basicPublish(EXCHANGE_DIRECT, severity, null, message.getBytes());
	System.out.println(" [x] Sent '" + severity + "':'" + message + "'");

	channel.close();
	connection.close();
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
