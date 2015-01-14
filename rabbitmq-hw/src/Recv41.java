import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * $ javac -cp rabbitmq-client.jar Send41.java Recv41.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar Recv41
 * 
 * $ rabbitmqctl list_queues
 * $ rabbitmqctl list_bindings
 * @ rabbitmqctl list_exchanges
 * 
 * This class symbolically is a WORKER
 * 
 * New changes:
 * (1) We are trying routing w.t.h.o Direct exchange
 * (2) If message has the word-sticky, all workers must receive it
 * (3) Else only worker must receive it
 * (4) Each worker subscribes to both "sticky" as well as "" routing key
 * 
 * Run 2 receivers
 * 
 * @author smc
 */
public class Recv41 {

  private static final String EXCHANGE_DIRECT = "be-1431";

  public static void main(String[] argv) throws java.io.IOException,
      java.lang.InterruptedException {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // Direct exchange
    channel.exchangeDeclare(EXCHANGE_DIRECT, "direct");
    String queueName = channel.queueDeclare().getQueue();
    channel.queueBind(queueName, EXCHANGE_DIRECT, "");

    // Nameless default exchange
    channel.queueDeclare("sticky-1431", false, false, false, null);
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

  private static void doWork(String task) throws InterruptedException {
    for (char ch : task.toCharArray()) {
      if (ch == '.') {
        Thread.sleep(1000);
      }
    }
  }

}
