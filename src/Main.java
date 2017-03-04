import sortlib.ParallelMergeSort;
import sortlib.SequentialMergeSort;

public class Main {

	private static final String BLANK = " ";
	private static final int[] defaultTestCases = {50000, 100000, 500000, 1000000, 5000000};
	private static final int[] extremeTestCases = {5000000, 10000000, 50000000, 100000000, 500000000};
	private static GeneralUtils util;
	private static SequentialMergeSort seqMerge;
	private static ParallelMergeSort parMerge;
	
	// sample use case
	// TODO: Refactor unit test structure
	public static void main(String[] args) {
		util = new GeneralUtils();
		seqMerge = new SequentialMergeSort();
		parMerge = new ParallelMergeSort();
		int[] testCase;
		
		/**
		 * PART 1
		 */
		int testCaseNum = 0;
		System.out.println("Default Test Cases, Hajime!");
		for(int test: defaultTestCases){
			testCase = util.generateTestCase(test, 200, false);
			runTestCase(testCase, "Sequential Test Case " + testCaseNum, true);
			runTestCase(testCase, "\tParallel Test Case " + testCaseNum, false);
			testCaseNum++;
		}
		/**
		 * END OF PART 1
		 */
		
		/**
		 * PART 2 (Uncomment to test below)
		 */
//		testCaseNum = 0;
//		System.out.println("Extreme Test Cases, Hajime!");
//		for(int test: extremeTestCases){
//			testCase = util.generateTestCase(test, 200, false);
//			runTestCase(testCase, "Sequential Test Case " + testCaseNum, true);
//			runTestCase(testCase, "\tParallel Test Case " + testCaseNum, false);
//			testCaseNum++;
//		}
		/**
		 * END OF PART 2
		 */
	}
	
	public static void runTestCase(int[] testCase, String testCaseName, boolean isSequential){
		long startTime =  0L, runTime = 0L;
		
		try {
			if(isSequential){
				startTime = System.currentTimeMillis();
				seqMerge.sort(testCase);
			}
			else{
				startTime = System.currentTimeMillis();
				parMerge.sort(testCase);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			runTime = util.getTimeDifference(startTime);
			System.out.printf("Execution Time of %s: %s milliseconds(s)\n", testCaseName, runTime);
		}
		
//		System.out.println("Output:\n" + util.prettifyOutput(testCase, 10));
	}

}
