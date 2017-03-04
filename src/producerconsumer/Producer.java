package producerconsumer;

import java.util.Random;

public class Producer implements Runnable{

	private Queue queue;
	private String producerName;
	
	public Producer(final Queue queue, final String producerName){
		
		if(queue == null){
			throw new IllegalArgumentException("Producer - [ERROR] Queue must not be null! Zettai ni! \n");
		}
		
		this.queue = queue;
		this.producerName = producerName;
	}
		
	@Override
	public void run() {
		putMessagesInQueue();
	}
	
	private void putMessagesInQueue(){
		final int randomNumberToGenerate = 10;
		
		for(int messageIndex = 1; messageIndex <= randomNumberToGenerate; messageIndex++){
			final StringBuilder message = new StringBuilder();
			message.append(new Random().nextInt(10)+1);
//			message.append(messageIndex); // Use this to append sequential data
			
			final Runnable messageToConsume = new Message(message.toString());
			
			System.out.println(producerName + " says: konosuba!! putting new message " + messageIndex 
					+" into the queue -> " + message.toString());
			
			queue.put(messageToConsume);
		}
	}
	
}
