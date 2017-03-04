package utils;
import java.util.Random;

public class GeneralUtils {
	private static final String BLANK = " ";
	private static final String EMPTY_STRING = "";
	private static final String NEW_LINE = "\n";
	private static final String TAB = "\t";
	
	// generate test case based on given parameters
	public int[] generateTestCase(int testSize, int maxRange, boolean includeOffset){
		int[] testCase = new int[testSize];
		int offset = includeOffset ? 0 : 1;
		Random rand = new Random();
		
//		System.out.printf("generateTestCase(): Creating test cases...\n"
//				+ "\tTest Size: %d\n\tMax Value: %d\n\tOffset: %d\n", testSize, maxRange, offset);
		for(int i = 0; i < testCase.length; i++)
		{
			testCase[i] = rand.nextInt(maxRange) + offset;
		}
		
//		System.out.println("generateTestCase(): Done creating test cases!\n");
		
		return testCase;
	}
	
	public String prettifyOutput(int[] testResult, int maxOutput){
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
	public String prettifyOutput(int[] testResult){
		return prettifyOutput(testResult, -1);
	}
	
	// checks for null or empty strings
	public boolean isNotNullOrEmpty(String testString){
		
		if(testString.length() > 0 && testString != null){
			return true;
		}
		
		return false;
	}
	
	// computes time difference
	public long getTimeDifference(long startTime){
		return System.currentTimeMillis() - startTime;
	}
}
