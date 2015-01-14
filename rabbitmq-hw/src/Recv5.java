import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * $ javac -cp rabbitmq-client.jar Send5.java Recv5.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar Recv5
 * 
 * $ rabbitmqctl list_queues
 * $ rabbitmqctl list_bindings
 * @ rabbitmqctl list_exchanges
 * 
 * This class symbolically is a WORKER
 * 
 * New changes:
 * (1) Creates a topic exchange
 * (2) Creates a queue and attaches it to all topics passed in command line
 * 
 * Run one receiver for one severity for best results
 * 
 * @author smc
 *
 */
public class Recv5 {

  private static final String EXCHANGE_NAME = "topic_logs";

  public static void main(String[] argv) throws Exception {

	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();

	channel.exchangeDeclare(EXCHANGE_NAME, "topic");
	String queueName = channel.queueDeclare().getQueue();

	if (argv.length < 1) {
	  System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
	  System.exit(1);
	}

	for (String bindingKey : argv) {
	  channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
	}

	System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

	QueueingConsumer consumer = new QueueingConsumer(channel);
	channel.basicConsume(queueName, true, consumer);

	while (true) {
	  QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	  String message = new String(delivery.getBody());
	  String routingKey = delivery.getEnvelope().getRoutingKey();

	  System.out
		  .println(" [x] Received '" + routingKey + "':'" + message + "'");
	}
  }
}
