import java.util.Random;

public class GeneralUtils {
	public int[] generateTestCase(int testSize, int maxRange, boolean includeOffset){
		int[] testCase = new int[testSize];
		int offset = includeOffset? 0 : 1;
		Random rand = new Random();
		
		System.out.printf("generateTestCase(): Creating test cases...\n"
				+ "\tTest Size: %d\n\tMax Value: %d\n\tOffset: %d\n", testSize, maxRange, offset);
		for(int i = 0; i < testCase.length; i++)
		{
			testCase[i] = rand.nextInt(maxRange) + offset;
		}
		
		System.out.println("generateTestCase(): Done creating test cases!");
		
		return testCase;
	}
}
