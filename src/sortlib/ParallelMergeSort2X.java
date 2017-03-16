package sortlib;

import java.util.concurrent.*;

public class ParallelMergeSort2X extends SequentialMergeSort {
	
	// helper method to initialize ForkJoinPool and merge sorting process
	public void sort(Integer[] listToSort) throws Exception{
		if(listToSort.length <= 1 || listToSort == null){
			throw new IllegalArgumentException("[WARNING] List has less than or equal to one item.");
		}
		
		ForkJoinPool esPool = new ForkJoinPool();	
		
		System.out.println("[Parallel Merge Sort - Fork] Initialization...");
		esPool.invoke(new InnerMergeSort(listToSort, true));
		System.out.println("[Parallel Merge Sort - Fork] Sorting Complete!\n");
	}
	
	// inner class to support recursive calls to parallel merge sort via ForkJoinPool
	private class InnerMergeSort extends RecursiveAction{
		private boolean isOneLevelDeep = true;
		private Integer[] listToSort;
		public InnerMergeSort(Integer[] listToSort, boolean isOneLevelDeep){
			this.listToSort = listToSort;
			this.isOneLevelDeep = isOneLevelDeep;
		}
		
		// normal merging process defined in ForkJoinPool's compute()
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
		
		// returns the left half
		private Integer[] leftHalf(Integer[] array) {
			int size1 = array.length / 2;
			Integer[] left = new Integer[size1];
			for (int i = 0; i < size1; i++) {
				left[i] = array[i];
			}
			return left;
		}
		
		// returns the right half
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
	
	// merge routine specific for this implementation
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
}
