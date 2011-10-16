package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import swe3ue.*;

public class WorkItemInitializerTest {

	@Test
	public void testCall() {
		WorkItemInitializer wip = new WorkItemInitializer();
		
		WorkItem result = null;
		try {
			result = wip.call();
		} catch (Exception e) {
			System.err.println("Processing should never throw!");
			assertTrue(false);
		}
		
		assertTrue(result.getID() == GlobalIdProvider.getInstance().getGlobalId() - 1 );
		testResult(result);
	}
	
	public void testResult(WorkItem result) {
		assertTrue(result.getWord().length() < 256);
		
		for(char c : result.getWord().toCharArray()) {
			if((int)c > 122 || (int)c < 97) {
				assertTrue(false);
			}
		}
	}

}
