package sortlib;

import java.util.concurrent.*;

public class ParallelMergeSort2 extends SequentialMergeSort {
	
	
	public void sort(Integer[] listToSort) throws Exception{
		if(listToSort.length <= 1 || listToSort == null){
			throw new IllegalArgumentException("[WARNING] List has less than or equal to one item.");
		}
		
		ForkJoinPool esPool = new ForkJoinPool();	
		
//		System.out.println("[Sequential Merge Sort] Initialization...");
		esPool.invoke(new InnerMergeSort(listToSort, 0, listToSort.length-1));
//		parallelSort(listToSort, 0, listToSort.length-1);
//		System.out.println("[Sequential Merge Sort] Sorting Complete!\n");
	}
	
	private class InnerMergeSort extends RecursiveAction{
		private static final int NON_PARALLEL_SORT_LENGTH = 100_000;
		private int startIndex, endIndex;
		private Integer[] listToSort;
		
		public InnerMergeSort(Integer[] listToSort, int startIndex, int endIndex){
			this.listToSort = listToSort;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}
		
		@Override
		protected void compute() {
			if(endIndex - startIndex < 1){
				return;
			}
			
	        if ((endIndex - startIndex) < NON_PARALLEL_SORT_LENGTH) {
	            // You might call this cheating, but if you continue going
	            // smaller and smaller, you get an exponential amount of
	            // new MergeActions, which makes the code slower. The sort
	            // still works if you instead have the if statement be
	            // if (rangeLength <= 1) return;
	        	try {
					parallelSort(listToSort, startIndex, endIndex);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            return;
	        }
			
			// get midpoint then identify left & right half to sort, finally merge values in the end
			int midpoint = (int)Math.floor((startIndex + endIndex) / 2);
			
			invokeAll(new InnerMergeSort(listToSort, startIndex, midpoint), new InnerMergeSort(listToSort, midpoint + 1, endIndex));
			merge(listToSort, startIndex, midpoint, endIndex);
		}
	}
	
//	private class Sequential
	
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
