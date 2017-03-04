package producerconsumer;

import java.util.ArrayList;
import java.util.List;

public class Queue {

	private int queueCapacity;
	private boolean toAddDelay;
	private List<Runnable> queueList;

	public Queue(final int queueCapacity, final boolean toAddDelay) {
		if (queueCapacity <= 0) {
			throw new IllegalArgumentException(
					"Queue capacity must be greater than 0.\n");
		}

		this.queueCapacity = queueCapacity;
		this.toAddDelay = toAddDelay;
		queueList = new ArrayList<Runnable>(queueCapacity);
	}

	public void put(final Runnable element) {
		synchronized (queueList) {
			while (queueList.size() >= queueCapacity) {
				try {
//					System.out.println("Mr Q says: no more! i am full! -> "
//							+ queueList.size() + " elements" + "\n");
					queueList.wait();
//					System.out.println("Mr Q says: kakatte koi! i am ready!\n");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			queueList.add(element);
			queueList.notifyAll();
		}
		System.out.println("[Queue] says: current queue size is " + size());
		
		if (toAddDelay){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Runnable pull() {
		synchronized (queueList) {
			while (queueList.isEmpty()) {
				try {
//					System.out.println("Mr Q says: I am lonely and empty... huhu\n");
					queueList.wait();
//					System.out.println("Mr Q says: Hai! I'm ready to receive! \n");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {
				showCurrentQueue();
				return queueList.remove(0);
			} finally {
				queueList.notifyAll();
			}
		}
	}

	public int size() {
		synchronized (queueList) {
			return queueList.size();
		}
	}
	
	public void showCurrentQueue() {
		int currentElement = 1;
		System.out.println();
		StringBuilder currentQueueStr = new StringBuilder();
		currentQueueStr.append("Le current queue: {\n");
		for (Runnable element : queueList) {
			currentQueueStr.append("\t" + currentElement + " : '" + element.toString() + "',\n ");
			currentElement++;
		}
		currentQueueStr.append("}");
		System.out.println(currentQueueStr.toString() + "\n");
	}
}
