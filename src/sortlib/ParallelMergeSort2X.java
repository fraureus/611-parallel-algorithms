package sortlib;

import java.util.Arrays;
import java.util.concurrent.*;

public class ParallelMergeSort2X extends SequentialMergeSort {
	
	
	public void sort(Integer[] listToSort) throws Exception{
		if(listToSort.length <= 1 || listToSort == null){
			throw new IllegalArgumentException("[WARNING] List has less than or equal to one item.");
		}
		
		ForkJoinPool esPool = new ForkJoinPool();	
		
//		System.out.println("[Sequential Merge Sort] Initialization...");
		esPool.invoke(new InnerMergeSort(listToSort, true));
//		parallelSort(listToSort, 0, listToSort.length-1);
//		System.out.println("[Sequential Merge Sort] Sorting Complete!\n");
	}
	
	private class InnerMergeSort extends RecursiveAction{
		private boolean isOneLevelDeep = true;
		private Integer[] listToSort;
		private static final int NON_PARALLEL_SORT_LENGTH = 100_000;
		
		public InnerMergeSort(Integer[] listToSort, boolean isOneLevelDeep){
			this.listToSort = listToSort;
			this.isOneLevelDeep = isOneLevelDeep;
		}
		
		@Override
		protected void compute() {
			if(listToSort.length <= 1){
				return;
			}
			if(isOneLevelDeep){
				isOneLevelDeep = false;
				Integer[] leftHalf = leftHalf(listToSort);
				Integer[] rightHalf = rightHalf(listToSort);
				
				invokeAll(new InnerMergeSort(leftHalf, false), new InnerMergeSort(rightHalf, false));
				merge(leftHalf, rightHalf, listToSort);
			}
			else{
				sort(listToSort, 0, listToSort.length-1);
			}
		}
		
		private Integer[] leftHalf(Integer[] array) {
			int size1 = array.length / 2;
			Integer[] left = new Integer[size1];
			for (int i = 0; i < size1; i++) {
				left[i] = array[i];
			}
			return left;
		}
		
		private Integer[] rightHalf(Integer[] array) {
			int size1 = array.length / 2;
			int size2 = array.length - size1;
			Integer[] right = new Integer[size2];
			for (int i = 0; i < size2; i++) {
				right[i] = array[i + size1];
			}
			return right;
		}
	}
	
	public static void merge(Integer[] left, Integer[] right, Integer[] a) {
		int i1 = 0;
		int i2 = 0;
		for (int i = 0; i < a.length; i++) {
			if (i2 >= right.length || (i1 < left.length && left[i1] < right[i2])) {
				a[i] = left[i1];
				i1++;
			} else {
				a[i] = right[i2];
				i2++;
			}
		}
	}
	
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
}
