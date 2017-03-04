package producerconsumer;


public class ProducerConsumer {

	public static void main(String[] args) {

		boolean toAddDelay = false;
		
		System.out.println("Konnichiwa Minasan! Produsha Conshuma, Hajime!\n");
		
		// Mr Q
		final Queue queue = new Queue(10, toAddDelay);
		
		// Producer-san ichi
		final Thread producer = new Thread(new Producer(queue, "[PRODUCER-san 1]"));
		producer.start();
		
		// Consumer-chan ichi
		final Thread consumer = new Thread(new Consumer(queue, "[CONSUMER-chan 1]"));
		consumer.start();
		
		// Producer-san ni
		final Thread producer2 = new Thread(new Producer(queue, "[PRODUCER-san 2]"));
		producer2.start();
		
	}

}
