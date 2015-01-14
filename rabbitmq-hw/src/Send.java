import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * $ javac -cp rabbitmq-client.jar Send.java Recv.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar Send
 * 
 * $ rabbitmqctl list_queues
 * 
 * @author smc
 * 
 */
public class Send {

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

	// Message content will be sent as byte array.
	String message = "Hello World!";

	// Publish the message to the queue.
	channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
	System.out.println(" [x] Sent '" + message + "'");

	channel.close();
	connection.close();
  }

}
