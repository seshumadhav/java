import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * $ javac -cp rabbitmq-client.jar Send.java Recv.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar Recv
 * 
 * $ rabbitmqctl list_queues
 * 
 * @author smc
 */
public class Recv {

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
    // Add yourself as a consumer of the queue.
    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(QUEUE_NAME, true, consumer);

    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    while (true) {
      // This appears to be an infinite loop; but it is not
      // QueueingConsumer.nextDelivery() blocks until another message has been
      // delivered from the server.
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      System.out.println(" [x] Received '" + message + "'");
    }

  }
}
