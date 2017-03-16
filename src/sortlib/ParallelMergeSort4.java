package sortlib;

public class ParallelMergeSort4 extends SequentialMergeSort{
	
	// helper method to initialize actual parallel sort
	public void sort(Integer[] listToSort) throws Exception{
		if(listToSort.length <= 1){
			throw new IllegalArgumentException("[WARNING] List has less than or equal to one item.");
		}
		
		System.out.println("[Parallel Merge Sort 4] Initialization...");
		parallelSort(listToSort, 0, listToSort.length-1);
		System.out.println("[Parallel Merge Sort 4] Sorting Complete!\n");
	}
	
	// main parallel merge sort implementation with 4 threads
	private void parallelSort(Integer[] listToSort, int startIndex, int endIndex) throws InterruptedException{
		if(endIndex - startIndex < 1){
			return;
		}
		
		// get midpoint then identify left & right half to sort, finally merge values in the end
		int midpoint = (int)Math.floor(startIndex + endIndex / 2);
		int subMidpoint = (int)Math.floor(midpoint / 2);
		
		// less costly since only two main threads are used as seen below
		// cheaper recursion calls are made to the sequential version of merge sort
		Thread leftThreadA = new Thread(new Runnable(){
			public void run(){
				sort(listToSort, startIndex, subMidpoint);
			}
		});
		
		Thread leftThreadB = new Thread(new Runnable(){
			public void run(){
				sort(listToSort, subMidpoint + 1, midpoint);
			}
		});
		
		Thread rightThreadA = new Thread(new Runnable(){
			public void run(){
				sort(listToSort, midpoint + 1, (endIndex-midpoint));
			}
		});
		
		Thread rightThreadB = new Thread(new Runnable(){
			public void run(){
				sort(listToSort, (endIndex-midpoint)+1, endIndex);
			}
		});
		
		// begin sorting 4 halves simultaneously
		leftThreadA.start();
		leftThreadB.start();
		rightThreadA.start();
		rightThreadB.start();
		
		// allow all threads to finish until completion
		try {
			leftThreadA.join();
			leftThreadB.join();
			rightThreadA.join();
			rightThreadB.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// call to parent merge routine using sentinels
		merge(listToSort, startIndex, midpoint, endIndex);
	}
}
