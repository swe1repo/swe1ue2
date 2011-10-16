package swe3ue;

import java.util.concurrent.Callable;

/**
 * A Callable defining the task of processing a single WorkItem.
 * 
 * @author patrick
 *
 */
public class WorkItemProcessor implements Callable<WorkItem> {
	WorkItem it_ = null;
	
	/**
	 * Constructor.
	 * 
	 * @param it The WorkItem to be processed.
	 */
	WorkItemProcessor(WorkItem it) {
		it_ = it;
	}
	
	/**
	 * Sets length and number of vocals for the processed WorkItem.
	 * 
	 * @return The processed WorkItem.
	 */
	@Override
	public WorkItem call() throws Exception {
		String str = it_.getWord();
		int len    = str.length();
		
		it_.setLength(len);
		
		int numVocals = 0;
		
		for(int i = 0; i < len; i++) {
			char c = str.charAt(i);
			
			// if isVocal increase count by 1
			if(c == 'a' ||
			   c == 'e' ||
			   c == 'i' ||
			   c == 'o' ||
			   c == 'u'
			   ) {
				numVocals++;
			}
		}
		
		it_.setVocals(numVocals);
		
		return it_;
	}
}
