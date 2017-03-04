import sortlib.SequentialMergeSort;

public class Main {

	private static final String BLANK = " ";
	private static final int[] defaultTestCases = {50000, 100000, 500000, 1000000, 5000000};
	private static GeneralUtils util;
	private static SequentialMergeSort seqMerge;
	
	// sample use case
	public static void main(String[] args) {
		util = new GeneralUtils();
		seqMerge = new SequentialMergeSort();
		
		int testCaseNum = 0;
		for(int test: defaultTestCases){
			runTestCase(test, 2000, false, "Sequential Test Case " + testCaseNum);
			testCaseNum++;
		}
	}
	
	public static void runTestCase(int testSize, int testRange, boolean testOffset, String testCaseName){
		long startTime =  0L, runTime = 0L;
		int[] testCase = util.generateTestCase(testSize, testRange, testOffset);
		
		try {
			startTime = System.currentTimeMillis();
			seqMerge.sort(testCase);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			runTime = util.getTimeDifference(startTime);
			System.out.printf("Execution Time of %s: %s milliseconds(s)\n", testCaseName, runTime);
		}
		
//		System.out.println("Output:\n" + util.prettifyOutput(testCase, 10));
	}

}
