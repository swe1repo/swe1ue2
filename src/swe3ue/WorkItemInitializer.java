package swe3ue;

import java.util.concurrent.Callable;
import java.util.Random;

/**
 * A Callable defining the task of initializing a single WorkItem.
 * 
 * @author patrick
 *
 */
public class WorkItemInitializer implements Callable<WorkItem> {
	WorkItemInitializer() {
	}
	
	/**
	 * Constructs a WorkItem with a word of random length initialized with random letters in the range of a-z
	 * and a global, unique identifier.
	 * 
	 * @return The constructed WorkItem.
	 */
	@Override
	public WorkItem call() throws Exception {
		int currentId = GlobalIdProvider.getInstance().getGlobalId();
		
		WorkItem workItem = new WorkItem();
		Random random_ = new Random(currentId);
		
		// random number of chars in the range of 0-255
		int numChars = random_.nextInt(255);
		
		byte[] bytes = new byte[numChars];
		
		random_.nextBytes(bytes);
		
		random_ = null;
		
		for(int i = 0; i < numChars; i++) {
			// convert byte to number in ASCII range a-z
			bytes[i] = (byte) ( (bytes[i] & 0x7fffffff) % 26 + 97);
		}
		
		String word = new String(bytes);
		
		workItem.setID(currentId);
		workItem.setWord(word);
		
		return workItem;
	}
}
