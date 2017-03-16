package sortlib;

public class SequentialMergeSort {
	protected Integer[] listToSort;
	
	// helper method to initialize main sequential sort
	public void sort(Integer[] listToSort) throws Exception{
		if(listToSort.length <= 1){
			throw new IllegalArgumentException("[WARNING] List has less than or equal to one item.");
		}
		
		System.out.println("[Sequential Merge Sort] Initialization...");
		sort(listToSort, 0, listToSort.length-1);
		System.out.println("[Sequential Merge Sort] Sorting Complete!\n");
	}
	
	// main sequential merge sort implementation
	// sorts left half first, before starting the right half
	protected void sort(Integer[] listToSort, int startIndex, int endIndex){
		if(endIndex - startIndex < 1){
			return;
		}
		
		// get midpoint then identify left & right half to sort, finally merge values in the end
		int midpoint = (int)Math.floor(startIndex + endIndex) / 2;
		
		// left half
		sort(listToSort, startIndex, midpoint);
		// right half
		sort(listToSort, midpoint + 1, endIndex);
		
		// merge routine to combine the left and right halves
		merge(listToSort, startIndex, midpoint, endIndex);
	}
	
	
	// merge routine using a sentinel
	protected static void merge(Integer[] listToMerge, int startIndex, int midpoint, int endIndex){
		
		// size of each half
		int leftSublistSize = midpoint - startIndex + 1;
		int rightSublistSize = endIndex - midpoint;

		// initializing left & right half
		Integer[] leftSublist = new Integer[leftSublistSize+1];
		Integer[] rightSublist = new Integer[rightSublistSize+1];
		
		// copy to left and right half values from initial list
		for(int i = 0; i < leftSublistSize; i++){
			leftSublist[i] = listToMerge[startIndex + i];
		}
		for(int i = 0; i < rightSublistSize; i++){
			rightSublist[i] = listToMerge[midpoint + i + 1];
		}
		
		// assign sentinels at the end of the list
		leftSublist[leftSublist.length - 1] = Integer.MAX_VALUE;
		rightSublist[rightSublist.length - 1] = Integer.MAX_VALUE;
		
		// initialize pointer head positions for each half
		int leftIndex = 0;
		int rightIndex = 0;
		for(int i = startIndex; i <= endIndex; i++){
			// if left item is less than or equal to right item (or right item equals the sentinel value), 
			// then assign left item & move pointer to next left index
			if(leftSublist[leftIndex] <= rightSublist[rightIndex]
					|| rightSublist.equals(Integer.MAX_VALUE))
			{
				listToMerge[i] = leftSublist[leftIndex];
				leftIndex++;
			} 
			// if right item is less than left item, 
			// then assign right item and move pointer to next right item
			else
			{
				listToMerge[i] = rightSublist[rightIndex];
				rightIndex++;
			}
		}
	}
}
