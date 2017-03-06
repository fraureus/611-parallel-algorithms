package sortlib;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import utils.GeneralUtils;

public class Main {
	
	/**
	 *  Constants
	 */
	private static final int BEST_CASE = 0;
	private static final int AVERAGE_CASE = 1;
	private static final int WORST_CASE = 2;
	private static final String BEST_CASE_LBL = "BEST";
	private static final String AVERAGE_CASE_LBL = "AVG";
	private static final String WORST_CASE_LBL = "WORST";
	private static final String BEST_CASE_STR = "Best Cases";
	private static final String AVERAGE_CASE_STR = "Average Cases";
	private static final String WORST_CASE_STR = "Worst Cases";
	
	/**
	 *  Private Settings
	 */
	private static final boolean TO_PRINT = false;
	private static final boolean TO_SAVE = true;
	
	/**
	 *  Private Variables
	 */
	private static GeneralUtils util;
	private static SequentialMergeSort seqMerge;
	private static ParallelMergeSort parMerge;
	
	private static Integer[] sequentialTestCase;
	private static Integer[] parallelTestCase;
	private static int testCaseNum;
	private static HashMap<String, String> resultsMap;
	
	/**
	 * Test Cases
	 */
	private static final int[] tinyTestCases = {10, 20, 30, 40, 50};
	private static final int[] defaultTestCases = {50000, 100000, 500000, 1000000, 5000000};
	private static final int[] extremeTestCases = {5000000, 10000000, 50000000, 100000000, 500000000};
	private static final int[] finalTestCases = {1000, 5000,
		10000, 50000,
		100000, 500000,
		1000000, 5000000,
		10000000, 50000000};
	
	
	public static void main(String[] args) {
		init();
		
		executeTestCases(finalTestCases, BEST_CASE, BEST_CASE_STR);
		executeTestCases(finalTestCases, AVERAGE_CASE, AVERAGE_CASE_STR);
		executeTestCases(finalTestCases, WORST_CASE, WORST_CASE_STR);
		
		System.out.println("Test Cases Done!");
		
		try {
			if(TO_SAVE)
				/* FYI: Second argument takes in an optional filename
				 * if it is blank, the file would be saved with a generated filename
				 * else, it uses your provided filename
				 */
				util.saveResultsToCsvFile(resultsMap, ""); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void init(){
		util = new GeneralUtils();
		seqMerge = new SequentialMergeSort();
		parMerge = new ParallelMergeSort();
		resultsMap = new HashMap<String, String>();
		testCaseNum = 0;
	}
	
	private static void executeTestCases(int[] testCases, int mode, String testName){
		testCaseNum = 0;
		System.out.println(testName + ", hajime!\n");
		
		for(int test: testCases){
			sequentialTestCase = util.generateTestCase(test, 200, false, mode);
			parallelTestCase = Arrays.copyOf(sequentialTestCase, sequentialTestCase.length);
			
			runTestCase(sequentialTestCase, "Sequential Test Case " + testCaseNum + ": " + test, true, mode);
			runTestCase(parallelTestCase, "[Parallel] Test Case " + testCaseNum + ": " + test, false, mode);
			
			testCaseNum++;
		}
		
		System.out.println(testName + " are done.\n");
	}
	
	private static void runTestCase(Integer[] testCase, String testCaseName, boolean isSequential, int mode){
		long startTime =  0L, runTime = 0L;
		String lblPrefix = "";
		System.out.println("Test Case:\n" + util.prettifyOutput(testCase, 10));
		try {
			if(isSequential){
				lblPrefix = "SEQ";
				startTime = System.currentTimeMillis();
				seqMerge.sort(testCase);
			}
			else{
				lblPrefix = "PAR";
				startTime = System.currentTimeMillis(); // added duplicate to get actual start time from this point
				parMerge.sort(testCase);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			runTime = util.getTimeDifference(startTime);
			System.out.printf("Execution Time of %s: %s milliseconds(s)\n", testCaseName, runTime);
			resultsMap.put(lblPrefix + "_" + getLabel(mode) + "_" + testCaseNum + "_" + String.valueOf(testCase.length), String.valueOf(runTime));
		}
		
		if(TO_PRINT)
			System.out.println("Output:\n" + util.prettifyOutput(testCase, 10));
	}
	
	private static String getLabel(int mode){
		if(mode == 0){
			return BEST_CASE_LBL;
		}else if(mode == 1){
			return AVERAGE_CASE_LBL;
		}else{
			return WORST_CASE_LBL;
		}
	}

}
