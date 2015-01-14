import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * $ javac -cp rabbitmq-client.jar Send22.java Recv22.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar
 * Recv22
 * 
 * $ rabbitmqctl list_queues
 * 
 * This class symbolically is a WORKER
 * 
 * To know list of unacknowledged message $ sudo rabbitmqctl list_queues name
 * messages_ready messages_unacknowledged
 * 
 * @author smc
 */
public class Recv22 {

  private final static String QUEUE_NAME = "hello_queue";

  public static void main(String[] argv) throws java.io.IOException,
	  java.lang.InterruptedException {

	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");
	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();

	// We declare queue here to ensure there a queue receiver can wait on, even
	// if producer was not started by that time
	channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

	// Add yourself as a consumer of the queue.
	QueueingConsumer consumer = new QueueingConsumer(channel);
	channel.basicConsume(QUEUE_NAME, false /* autoAck */, consumer);
	// autoAck = true means; the moment consumer/worker consumes a message, ack
	// is sent to RabbitMQ, and RabbitMQ thus goes and deletes message from
	// queue

	// If you want RabbitMQ to wait till consumer explicitly acks a given
	// message, set autoAck = false;

	while (true) {
	  // This appears to be an infinite loop; but it is not
	  // QueueingConsumer.nextDelivery() blocks until another message has been
	  // delivered from the server.
	  QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	  String message = new String(delivery.getBody());
	  System.out.println(" [x] Received '" + message + "'");
	  doWork(message);
	  // multiple = false: just this message
	  // multiple = true: all msgs so far
	  channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	  System.out.println(" [x] Done");
	}

  }

  private static void doWork(String task) throws InterruptedException {
	for (char ch : task.toCharArray()) {
	  if (ch == '.') {
		Thread.sleep(1000);
	  }
	}
  }

}
