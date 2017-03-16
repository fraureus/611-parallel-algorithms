package sortlib;

public class ParallelMergeSort2 extends SequentialMergeSort {
	
	// helper method to initialize actual parallel merge sort
	public void sort(Integer[] listToSort) throws Exception{
		if(listToSort.length <= 1 || listToSort == null){
			throw new IllegalArgumentException("[WARNING] List has less than or equal to one item.");
		}
		
		System.out.println("[Parallel Merge Sort 2] Initialization...");
		parallelSort(listToSort, 0, listToSort.length-1);
		System.out.println("[Parallel Merge Sort 2] Sorting Complete!\n");
	}
	
	// main parallel merge sort implementation with 2 threads
	private void parallelSort(Integer[] listToSort, int startIndex, int endIndex) throws InterruptedException{
		if(endIndex - startIndex < 1){
			return;
		}
		
		// get midpoint then identify left & right half to sort, finally merge values in the end
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
		
		// call parent merge routine using sentinels
		merge(listToSort, startIndex, midpoint, endIndex);
	}
}
