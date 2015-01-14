import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * $ javac -cp rabbitmq-client.jar Send3.java Recv3.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar Recv3
 * 
 * $ rabbitmqctl list_queues
 * $ rabbitmqctl list_bindings
 * @ rabbitmqctl list_exchanges
 * 
 * This class symbolically is a WORKER
 * 
 * New changes:
 * (1) Declares a channel
 * (2) Declares a queue with a random name
 * (3) Binds channel to a queue
 * 
 * Run as many receivers as possible. Separate queues all tied to same exchange are created
 * All receivers/consumers/workers will get same request
 * 
 * @author smc
 */
public class Recv3 {

  private static final String EXCHANGE_NAME = "logs";

  public static void main(String[] argv) throws java.io.IOException,
      java.lang.InterruptedException {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    String queueName = channel.queueDeclare().getQueue();
    channel.queueBind(queueName, EXCHANGE_NAME, "");

    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(queueName, true, consumer);

    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());

      System.out.println(" [x] Received '" + message + "'");
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
