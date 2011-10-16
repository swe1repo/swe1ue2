package swe3ue;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.lang.Long;

/**
 * The StatisticsProvider class provides utility methods to create runtime statistics
 * for an application.
 * 
 * @author patrick
 */
public class StatisticsProvider {
	HashMap<String, Long> times_ = null;
	
	StatisticsProvider() {
		times_ = new HashMap<String, Long>();
	}
	
	/**
	 * Takes the start time of a task identified by key.
	 * 
	 * @param key Key by which the start time of the task is identified.
	 */
	public void startTimeForKey(String key) {
		if(times_.containsKey(key))
		{
			throw new IllegalArgumentException();
		}
		
		times_.put(key, Calendar.getInstance().getTimeInMillis());
	}
	
	/**
	 * Takes the end time of a task identified by key.
	 * 
	 * @param key Key by which the end time of the task is identified.
	 */
	public void endTimeForKey(String key) {
		if(!times_.containsKey(key))
		{
			throw new IllegalArgumentException();
		}
		
		long tmp = times_.get(key);
		long delta = Calendar.getInstance().getTimeInMillis() - tmp;
		
		times_.put(key, delta);
	}
	
	/**
	 * Prints out the collected statistics.
	 * 
	 * @param numThreads Number of threads that have been used to process the tasks.
	 * @param numWorkItems Number of WorkItems that have been constructed.
	 */
	public void printStatistics(int numThreads, int numWorkItems) {
		long total = 0;
		
		System.out.println("\n============== STATISTICS ==============\n");
		System.out.println("Using " + numThreads + " thread(s).");
		System.out.println("Created " + numWorkItems + " workitem(s).\n");
		
		for(Map.Entry<String, Long> e : times_.entrySet()) {
			total += e.getValue();
			
			System.out.println(e.getKey() + " took " + e.getValue() + " ms.");
		}

		System.out.println("-----------------------------------------");
		System.out.println("Total is " + total + " ms.");
		System.out.println("\n========================================\n");
	}
}
