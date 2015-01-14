import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * $ javac -cp rabbitmq-client.jar Send6.java Recv6.java
 * 
 * $ java -cp .:commons-io-1.2.jar:commons-cli-1.1.jar:rabbitmq-client.jar Recv6
 * .....
 * 
 * $ rabbitmqctl list_queues
 * $ rabbitmqctl list_bindings
 * @ rabbitmqctl list_exchanges
 * 
 * This class symbolically is a RPC Client
 * 
 * New changes:
 * (1) Creates a queue to hold RPC requests & waits on that queue
 * (2) Upon receiving a request, process and send reply with replyTo queue
 * 
 * @author smc
 * 
 */
public class Recv6 {

  private static final String RPC_QUEUE_NAME = "rpc_queue";

  public static void main(String[] argv) throws Exception {

	ConnectionFactory factory = new ConnectionFactory();
	factory.setHost("localhost");

	Connection connection = factory.newConnection();
	Channel channel = connection.createChannel();

	channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
	channel.basicQos(1);

	QueueingConsumer consumer = new QueueingConsumer(channel);
	channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

	System.out.println(" [x] Awaiting RPC requests");

	while (true) {
	  QueueingConsumer.Delivery delivery = consumer.nextDelivery();

	  BasicProperties props = delivery.getProperties();
	  BasicProperties replyProps = new BasicProperties.Builder().correlationId(
		  props.getCorrelationId()).build();

	  String message = new String(delivery.getBody());
	  int n = Integer.parseInt(message);

	  System.out.println(" [.] fib(" + message + ")");
	  String response = "" + fib(n);

	  channel.basicPublish("", props.getReplyTo(), replyProps, response
		  .getBytes());

	  channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	}
  }

  private static int fib(int n) throws Exception {
	if (n == 0) {
	  return 0;
	}
	if (n == 1) {
	  return 1;
	}
	return fib(n - 1) + fib(n - 2);
  }

}
