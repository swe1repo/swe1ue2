package tests;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;

import swe3ue.WorkItem;
import swe3ue.WorkItemManager;

public class WorkItemManagerTest {
	final int NUM_WORKITEMS = 10000;
	final int NUM_THREADS = 4;
	WorkItemManager wim = new WorkItemManager(NUM_THREADS, NUM_WORKITEMS);

	@Test
	public void testProcess() {
		wim = new WorkItemManager(NUM_THREADS, NUM_WORKITEMS);
		wim.init();
		wim.process();
		
		WorkItemProcessorTest t = new WorkItemProcessorTest();
		
		for(Future<WorkItem> wi : wim.getQueueCalculatedItems()) {
			try {
				t.testResult( wi.get() );
			} catch (InterruptedException e) {
				assertTrue(false);
			} catch (ExecutionException e) {
				assertTrue(false);
			}
		}
	}

	@Test
	public void testInit() {
		wim.init();
		
		assertTrue(wim.getQueueRawItems().size() == NUM_WORKITEMS);
		
		WorkItemInitializerTest t = new WorkItemInitializerTest();
		
		for(Future<WorkItem> wi : wim.getQueueRawItems()) {
			try {
				t.testResult( wi.get() );
			} catch (InterruptedException e) {
				assertTrue(false);
			} catch (ExecutionException e) {
				assertTrue(false);
			}
		}
	}
}
