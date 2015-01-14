import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * $ javac -cp rabbitmq-client.jar Send2.java Recv2.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar Send2
 * .....
 * 
 * $ rabbitmqctl list_queues
 * 
 * This class symbolically is a TASK
 * 
 * @author smc
 */
public class Send2 {

  private final static String QUEUE_NAME = "hello_queue";

  public static void main(String[] argv) throws java.io.IOException {

	// Location of broker; localhost/ip:port
	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");
	// factory.setUsername("rabbitmq");
	// factory.setPassword(")

	// Connection abstracts the socket
	Connection connection = factory.newConnection();

	// Channel has the API to send a receive messages
	Channel channel = connection.createChannel();

	// To send a message, declare a queue. Declaring queue is idempotent. No
	// matter how many times it is called, it creates queue if the queue does
	// not exist
	channel.queueDeclare(QUEUE_NAME, false, false, false, null);

	// Get message content from command line; Message content will be sent as
	// byte array.
	String message = getMessage(argv);

	// Publish the message to the queue.
	channel.basicPublish("", "hello_queue", null, message.getBytes());
	System.out.println(" [x] Sent '" + message + "'");

	channel.close();
	connection.close();
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
