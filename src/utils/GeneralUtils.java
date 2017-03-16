package utils;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GeneralUtils{
	private static final String EMPTY_STRING = "";
	private static final String NEW_LINE = "\n";
	private static final String TAB = "\t";
	
	// Generate test case based on given parameters
	public Integer[] generateTestCase(int testSize, int maxRange, boolean includeOffset, int mode){
		Integer[] testCase = new Integer[testSize];
		int offset = includeOffset ? 0 : 1;
		Random rand = new Random();
		
		System.out.printf("generateTestCase(): Creating test cases...\n"
				+ "\tTest Size: %d\n\tMax Value: %d\n\tOffset: %d\n", testSize, maxRange, offset);
		for(int i = 0; i < testCase.length; i++)
		{
			testCase[i] = rand.nextInt(maxRange) + offset;
		}
		
		System.out.println("generateTestCase(): Done creating test cases!\n");
		
		if(mode == 0){
			Arrays.sort(testCase);
			return testCase;
		}else if(mode == 1){
			return testCase;
		}else{
			Arrays.sort(testCase, Collections.reverseOrder());
			return testCase;
		}
	}
	
	public String prettifyOutput(Integer[] testResult, int maxOutput){
		StringBuilder pwettyOutput = new StringBuilder(EMPTY_STRING);
		
		for(int i = 0; i < testResult.length; i++){
			if(i == maxOutput){
				break;
			}
			
			pwettyOutput.append(testResult[i] + TAB);
			if((i + 1) % 10 == 0){
				pwettyOutput.append(NEW_LINE);
			}
		}
		
		return isNotNullOrEmpty(pwettyOutput.toString()) ? pwettyOutput.toString() : EMPTY_STRING;
	}
	
	// formats the output of a sorting algorithm
	public String prettifyOutput(Integer[] testResult){
		return prettifyOutput(testResult, -1);
	}
	
	// checks for null or empty strings
	public boolean isNotNullOrEmpty(String testString){
		
		if(testString.length() > 0 && testString != null){
			return true;
		}
		
		return false;
	}
	
	// Computes time difference
	public long getTimeDifference(long startTime){
		return System.currentTimeMillis() - startTime;
	}
	
	// Save results to a csv file
	/**
	 * @param hashMap : hashmap containing the results to be written to file
	 * @param optionalFileName : if blank, results are saved in a file with a auto-generated name
	 * @throws IOException : throws this if there is an error in writing to the file
	 */
	public void saveResultsToCsvFile(HashMap<String, String> hashMap, String optionalFileName, long testSize, long offset) throws IOException {
		String fileName = EMPTY_STRING;
		
		String experimentEndTime = LocalDateTime.now().toString();
		System.out.println("Experiment End Time: " + experimentEndTime);
		hashMap.put("END_TIME", experimentEndTime);
		
		if(optionalFileName.length() > 0){
			fileName = optionalFileName + ".csv";
		}else{
			fileName = "output-" + String.valueOf(System.currentTimeMillis()) + ".csv";
		}
		FileWriter writer = new FileWriter(fileName);
		List<String> keys = new ArrayList<String>(hashMap.keySet());
		long finalValue = 0L;
		Collections.sort(keys, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		String[] splitKey;
		writer.write("START_TIME," + hashMap.get("START_TIME").toString() + ",\n");
		writer.write("END_TIME," + hashMap.get("END_TIME").toString() + ",\n");
		writer.write("MAX_ITER," + testSize + ",\n");
		writer.write("TRIM_OFFSET," + offset + ",\n\n");
		writer.write("test_key,run_time (ms),test_size (n)\n");
		for (String key : keys) {
			if(!key.equalsIgnoreCase("START_TIME") && !key.equalsIgnoreCase("END_TIME")){
				splitKey = key.split("_");
				finalValue =  Long.parseLong(hashMap.get(key).toString());
				writer.write(key + "," + String.valueOf(finalValue) + "," + splitKey[3] + "\n");
			}
		}
		writer.close();
	}
	
}
