package sortlib;

public class ParallelMergeSort extends SequentialMergeSort {
	private Integer[] listToSort;
	
	public void sort(Integer[] listToSort) throws Exception{
		if(listToSort.length <= 1){
			throw new IllegalArgumentException("[WARNING] List has less than or equal to one item.");
		}
		
//		System.out.println("[Sequential Merge Sort] Initialization...");
		parallelSort(listToSort, 0, listToSort.length-1);
//		System.out.println("[Sequential Merge Sort] Sorting Complete!\n");
	}
	
	private void parallelSort(Integer[] listToSort, int startIndex, int endIndex) throws InterruptedException{
		if(endIndex - startIndex < 1){
			return;
		}
		
		// get midpoint then identify left & right half to sort, finally merge values in the end
		this.listToSort = listToSort;
		int midpoint = (int)Math.floor(startIndex + endIndex) / 2;
		
		// less costly since only two main threads are used as seen below
		// cheaper recursion calls are made to the sequential version of merge sort
		Thread leftThread = new Thread(new Runnable(){
			public void run(){
				sort(listToSort, startIndex, midpoint);
			}
		});
		
		Thread rightThread = new Thread(new Runnable(){
			public void run(){
				sort(listToSort, midpoint + 1, endIndex);
			}
		});
		
		// begin sorting both halves simultaneously
		leftThread.start();
		rightThread.start();
		
		// allow both threads to finish until completion
		try {
			leftThread.join();
			rightThread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		merge(listToSort, startIndex, midpoint, endIndex);
	}
	
	/**
	 *	Costly version of mini-threads below... (the dark side)
	 *
	 *	(Beware) summoning these threads below would awaken the stackoverflow monsterrr... like, for real..
	 *
	 */
	// instantiating threads within recursions are costly
	private class ThreadLeft extends Thread{
		public void run(){
			try {
				int startIndex = 0;
				int endIndex = listToSort.length - 1;
				int midpoint = (int)Math.floor(startIndex + endIndex) / 2;
				parallelSort(listToSort, startIndex, midpoint);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// instantiating threads within recursions are costly
	private class ThreadRight extends Thread{
		public void run(){
			try {
				int startIndex = 0;
				int endIndex = listToSort.length - 1;
				int midpoint = (int)Math.floor(startIndex + endIndex) / 2;
				parallelSort(listToSort, midpoint + 1, endIndex);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
