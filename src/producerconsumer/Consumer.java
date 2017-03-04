package producerconsumer;

public class Consumer implements Runnable {

	private Queue queue;
	private String consumerName;

	public Consumer(final Queue queue, final String consumerName) {
		if (queue == null) {
			throw new IllegalArgumentException("Consumer - [ERROR] Queue must not be null-o. Zettai ni! \n");
		}

		this.queue = queue;
		this.consumerName = consumerName;
	}

	@Override
	public void run() {
		consumeMessages();
	}

	private void consumeMessages() {
		while (true) {
			final Runnable message = queue.pull();

//			System.out.println(consumerName
//					+ " says: new message incoming! chotto matte kudasai!!!" + "\n");

			final Thread consumeMessage = new Thread(message);
			consumeMessage.start();
			
			System.out.println(consumerName + " says: Oishii desu ne! Currently nomnom-ing " + message + ".");

			try {
				consumeMessage.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
