package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import swe3ue.*;

public class WorkItemProcessorTest {

	@Test
	public void testCall() {
		WorkItem it = new WorkItem();
		
		String testWord = new String("aeiou");
		
		it.setWord(testWord);
		
		WorkItemProcessor wip = new WorkItemProcessor(it);
		
		WorkItem result = null;
		try {
			result = wip.call();
		} catch (Exception e) {
			System.err.println("Processing should never throw!");
			assertTrue(false);
		}
		
		assertTrue(result.getWord() == testWord);
		assertTrue(result.getLength() == testWord.length());
		assertTrue(result.getVocals() == testWord.length());
		
		testResult(result);
	}
	
	public void testResult(WorkItem result) {
		assertTrue(result.getLength() == result.getWord().length());
		
		int numVocals = 0;
		for(char c : result.getWord().toCharArray()) {
			if(c == 'a' ||
			   c == 'e' ||
			   c == 'i' ||
			   c == 'o' ||
			   c == 'u'
			   ) {
				numVocals++;
			}
		}
		
		assertTrue(result.getVocals() == numVocals);
	}

}
