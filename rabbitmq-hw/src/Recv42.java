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
 * (2) If sticky message, all2 workers receive (via "fanout" exchange)
 * (3) If not, only one worker receives
 * 
 * Run 2 receivers
 * 
 * @author smc
 */
public class Recv42 {

  private static final String QUEUE_NAME      = "all3";
  private static final String EXCHANGE_FANOUT = "fq3";

  public static void main(String[] argv) throws java.io.IOException,
      java.lang.InterruptedException {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    //  SETUP
    // Declare queue
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    // Fanout exchange
    channel.exchangeDeclare(EXCHANGE_FANOUT, "fanout");
    channel.queueBind(QUEUE_NAME, EXCHANGE_FANOUT, "");

    // Bind by queue name / routing key
    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(QUEUE_NAME, true, consumer);

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
