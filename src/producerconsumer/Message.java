package producerconsumer;

/**
 * @author Justin
 *
 */
public class Message implements Runnable {

	private String message;

	public Message(final String message) {
		this.message = message;
	}

	@Override
	public void run() {
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	/** Overrode method to return string representation of the message
	 */
	public String toString(){
		return message;
	}

}
