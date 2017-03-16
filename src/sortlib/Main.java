package sortlib;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;

import utils.GeneralUtils;


/**
 *	@author Justin R. & Kristine A.
 *	@spec In this project, the developers are required to create 
 *		a program that sorts a x-element array of numbers using any of
 *		popular sorting algorithms. There should be two programs.
 *
 *	(1) A program that implements the sorting algorithm using sequential programming
 *	(2) A program that implements the sorting algorithm using parallel programming
 *
 *	The array to be sorted should have random x elements containing numbers 1 to y.	
 *
 *	@implementation
 *		Algorithm: Merge Sort
 *		Variants: Sequential, Parallel with 2 threads, Parallel with 4 threads
 *		Test Case: Best, Average, Worst Case
 *		Range: 1 to 1_000_000 (million)
 *		N-elements: 
 *		{1_000, 5_000, 
		10_000, 50_000, 
		100_000, 500_000, 
		1_000_000, 5_000_000, 
		10_000_000, 50_000_000}
		Max Iterations of Experiment: 50
 */

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
	private static final int SEQUENTIAL = 0;
	private static final int PARALLEL2 = 1;
	private static final int PARALLEL4 = 2;
	private static final String HBAR = "#######################################";
	
	/**
	 *  Private Settings
	 */
	// global setting to indicate whether to output results 
	private static final boolean TO_PRINT = true;
	
	// global setting to indicate whether to save results or not
	private static final boolean TO_SAVE = true;
	
	// Max number of times to repeat either of the scenario
	private static final int MAX_ITER = 50; 
	
	// Number of initial iterations to trim (for removing misleading results)
	private static final int TRIM_VALUE = 10;
	
	/**
	 *  Private Variables
	 */
	private static GeneralUtils util;
	private static SequentialMergeSort seqMerge;
	private static ParallelMergeSort2 parMerge2;
	private static ParallelMergeSort2X parMerge2x;
	private static ParallelMergeSort4 parMerge4;
	
	private static Integer[] sequentialTestCase;
	private static Integer[] parallelTestCase;
	private static Integer[] parallelTestCase1;
	private static int testCaseNum;
	public static int testCaseIter;
	private static HashMap<String, String> resultsMap;
	
	/**
	 * Test Cases
	 */
	private static final int[] tinyTestCases = {10, 20, 30, 40, 50};
	private static final int[] defaultTestCases = {50_000, 100_000, 500_000, 1_000_000};
	private static final int[] extremeTestCases = {5_000_000, 10_000_000, 50_000_000, 100_000_000, 500_000_000};
	private static final int[] finalTestCases = 
		{1_000, 5_000, 
		10_000, 50_000, 
		100_000, 500_000, 
		1_000_000, 5_000_000, 
		10_000_000, 50_000_000};
	
	
	/**
	 * 	Starting point of main experiment
	 */
	public static void main(String[] args) {
		// plug in desired test case here
		int[] chosenTestCases = finalTestCases; 
		// plug in scenario to run here: [BEST_CASE, AVERAGE_CASE, WORST_CASE]
		int chosenScenario = AVERAGE_CASE; 
		
		if(chosenScenario == BEST_CASE){
			// BEST CASE SECTION
			init();
			for (int i = 0; i < MAX_ITER; i++) {
				testCaseIter = i;
				System.out.println(HBAR + "\n");
				System.out.println(BEST_CASE_STR + "\n\nIteration: " + (i + 1) + "\n\n");
				executeTestCases(chosenTestCases, BEST_CASE, BEST_CASE_STR);
			}
			try {
				if (TO_SAVE)
					/*
					 * FYI: Second argument takes in an optional filename if it is
					 * blank, the file would be saved with a generated filename
					 * else, it uses your provided filename
					 */
					util.saveResultsToCsvFile(resultsMap, "50_iterations_best_exe", MAX_ITER, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(chosenScenario == AVERAGE_CASE){
			// AVERAGE CASE SECTION
			init();
			for (int i = 0; i < MAX_ITER; i++) {
				testCaseIter = i;
				System.out.println(HBAR + "\n");
				System.out.println(AVERAGE_CASE_STR + "\n\nIteration: " + (i + 1) + "\n\n");
				executeTestCases(chosenTestCases, AVERAGE_CASE, AVERAGE_CASE_STR);
			}
			try {
				if (TO_SAVE)
					util.saveResultsToCsvFile(resultsMap, "50_iterations_avg_exe", MAX_ITER, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			// WORST CASE SECTION
			init();
			for (int i = 0; i < MAX_ITER; i++) {
				testCaseIter = i;
				System.out.println(HBAR + "\n");
				System.out.println(WORST_CASE_STR + "\n\nIteration: " + (i + 1) + "\n\n");
				executeTestCases(chosenTestCases, WORST_CASE, WORST_CASE_STR);
			}
			try {
				if (TO_SAVE)
					util.saveResultsToCsvFile(resultsMap, "50_iterations_worst_exe", MAX_ITER, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
		System.out.println("[-END-] Test Experiment Done! " + MAX_ITER + " iterations performed.");
	}
	
	// initialization & reset method
	private static void init(){
		util = new GeneralUtils();
		seqMerge = new SequentialMergeSort();
		parMerge2 = new ParallelMergeSort2();
		parMerge2x = new ParallelMergeSort2X();
		parMerge4 = new ParallelMergeSort4();
		resultsMap = new HashMap<String, String>();
		testCaseNum = 0;
		testCaseIter = 0;
		
		String experimentStartTime = LocalDateTime.now().toString();
		System.out.println("Experiment Start Time: " + experimentStartTime);
		resultsMap.put("START_TIME", experimentStartTime);
	}
	
	// execute test script for chosen test case scenario
	// generate and run algorithms on test data
	private static void executeTestCases(int[] testCases, int mode, String testName){
		testCaseNum = 0;
		System.out.println(testName + ", hajime!\n");
		
		for(int test: testCases){
			System.out.println("[INIT] ITERATION #" + (testCaseIter));
			sequentialTestCase = util.generateTestCase(test, 1000000, false, mode);
			parallelTestCase = Arrays.copyOf(sequentialTestCase, sequentialTestCase.length);
			parallelTestCase1 = Arrays.copyOf(sequentialTestCase, sequentialTestCase.length);
			
			runTestCase(sequentialTestCase, "Sequential Test Case " + testCaseNum + ": " + test, SEQUENTIAL, mode);
			runTestCase(parallelTestCase, "(Parallel) Test Case [2 threads] " + testCaseNum + ": " + test, PARALLEL2, mode);
			runTestCase(parallelTestCase1, "(Parallel) Test Case [4 threads] " + testCaseNum + ": " + test, PARALLEL4, mode);
			
			testCaseNum++;
		}
		
		System.out.println(testName + " are done.\n");
	}
	
	// run algorithm on test case data and save results in a HashMap resultsMap
	private static void runTestCase(Integer[] testCase, String testCaseName, int sorter, int mode){
		long startTime =  0L, runTime = 0L;
		String lblPrefix = "";
		System.out.println("[SORTING] Executing Test Case: " + testCaseName);
		System.out.println("Test Data:\n" + util.prettifyOutput(testCase, 10));
		try {
			if(sorter == SEQUENTIAL){
				lblPrefix = "SEQ";
				startTime = System.currentTimeMillis();
				seqMerge.sort(testCase);
			}
			else if(sorter == PARALLEL2){
				lblPrefix = "PAR2";
				startTime = System.currentTimeMillis(); // added duplicate to get actual start time from this point
				parMerge2.sort(testCase);
			}
			else if(sorter == PARALLEL4){
				lblPrefix = "PAR4";
				startTime = System.currentTimeMillis(); // added duplicate to get actual start time from this point
				parMerge4.sort(testCase);
			}else{
				System.out.println("[WARNING] chosen algorithm does not exist!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			runTime = util.getTimeDifference(startTime);
			System.out.printf("Execution Time of %s: %s milliseconds(s)\n", testCaseName, runTime);
			
			if(testCaseIter >= TRIM_VALUE){
				String key = lblPrefix + "_" + getLabel(mode) + "_" + testCaseNum + "_" + String.valueOf(testCase.length);
				if(resultsMap.get(key) != null){
					runTime += Long.parseLong(String.valueOf(resultsMap.get(key)));
					resultsMap.put(key, String.valueOf(runTime));
				}else{
					resultsMap.put(key, String.valueOf(runTime));
				}
			}
		}
		
		if(TO_PRINT)
			System.out.println("[DONE SORTING] Output:\n" + util.prettifyOutput(testCase, 50));
	}
	
	// helper method to return string representation of test case scenario (mode)
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
