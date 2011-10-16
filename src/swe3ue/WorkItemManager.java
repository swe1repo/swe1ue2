package swe3ue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The WorkItemManager class initializes, processes and prints out a given number of WorkItems
 * in a multithreaded environment.
 * 
 * @author patrick
 */
public class WorkItemManager {
	int globalId_ = 0;
	final int numWorkItems_;
	final int numThreads_;
	final ExecutorService executor_;
	List<Future<WorkItem>> queueRawItems_ = null;
	List<Future<WorkItem>> queueCalculatedItems_ = null;
	
	StatisticsProvider statProv_ = null;
	
	/**
	 * WorkItemManager constructor.
	 * 
	 * @param numThreads Number of threads that will be used.
	 * @param numWorkItems Number of WorkItems that will be created.
	 */
	public WorkItemManager(int numThreads, int numWorkItems) {
		numThreads_ = numThreads;
		numWorkItems_ = numWorkItems;
		executor_ = Executors.newFixedThreadPool(numThreads);
		statProv_ = new StatisticsProvider();
		
		// Task to run on exit()
		Thread exitHook = new Thread( 
										new Runnable() {
											public void run() {
												shutdown();
											}
										}
								     );
		
		Runtime.getRuntime().addShutdownHook(exitHook);
	}
	
	/**
	 * Exit hook.
	 */
	public void shutdown() {
		executor_.shutdownNow();
	}
	
	/**
	 * WorkItemManager destructor.
	 */
	protected void finalize() throws Throwable {
		executor_.shutdownNow();
	}
	
	/**
	 * Prints out all of the constructed WorkItems' IDs and Words
	 */
	public void print() {
		statProv_.startTimeForKey("Printing");
		
		for(Future<WorkItem> f : queueCalculatedItems_) {
			WorkItem it = null;
			
			try {
				it = f.get();
			} catch (InterruptedException e) {
				
				System.out.println("ERROR: Thread execution was interrupted. " + e.getMessage());
				
				System.exit(1);
			} catch (ExecutionException e) {
				
				System.out.println("ERROR: Failed to initialize WorkItem. " + e.getMessage());
				
				System.exit(1);
			}
			
			System.out.println( "ID: " + it.getID() + " :: Word: " + it.getWord() );
		}
		
		queueCalculatedItems_ = null;
		
		statProv_.endTimeForKey("Printing");
		
		statProv_.printStatistics(numThreads_, numWorkItems_);
	}
	
	/**
	 * Calculates all WorkItems' number of vocals and length.
	 */
	public void process() {
		statProv_.startTimeForKey("Processing");
		
		ArrayList<WorkItemProcessor> procList = createListWorkItemProcess(queueRawItems_, numWorkItems_);
		
		queueRawItems_ = null;
		
		try {
			queueCalculatedItems_ = executor_.invokeAll(procList);
		} catch (InterruptedException e) {
			
			System.out.println("ERROR: Thread execution was interrupted. " + e.getMessage());
			
			System.exit(1);
		}
		
		queueRawItems_ = null;
		executor_.shutdown();
		
		statProv_.endTimeForKey("Processing");
	}
	
	/**
	 * Initializes all WorkItems by setting their respective IDs and words.
	 */
	public void init() {
		statProv_.startTimeForKey("Initializing");
		
		ArrayList<WorkItemInitializer> initList = createListWorkItemInit(numWorkItems_);
		
		try {
			queueRawItems_ = executor_.invokeAll(initList);
		} catch (InterruptedException e) {
			
			System.out.println("ERROR: Thread execution was interrupted. " + e.getMessage());
			
			System.exit(1);
		}
		
		statProv_.endTimeForKey("Initializing");
	}
	
	/**
	 * Helper method to construct an array of WorkItemInitializers.
	 * 
	 * @param numWorkItems Number of WorkItemInitializers to create.
	 * @return An ArrayList of WorkItemInitializers to be invoked by an ExecutionService.
	 */
	public ArrayList<WorkItemInitializer> createListWorkItemInit(int numWorkItems) {
		ArrayList<WorkItemInitializer> initList = new ArrayList<WorkItemInitializer>(numWorkItems);
		
		for(int i = 0; i < numWorkItems; i++) {
			initList.add(new WorkItemInitializer());
		}
		
		return initList;
	}
	
	/**
	 * Helper method to construct an array of WorkItemProcessors.
	 * 
	 * @param workItems List of WorkItems to construct the WorkItemProcessors with.
	 * @param numWorkItems Number of WorkItemProcessors to be constructed.
	 * @return An ArrayList of WorkItemProcesors to be invoked by an ExecutionService.
	 */
	public ArrayList<WorkItemProcessor> createListWorkItemProcess(List<Future<WorkItem>> workItems, int numWorkItems) {
		ArrayList<WorkItemProcessor> procList = new ArrayList<WorkItemProcessor>(numWorkItems);
		
		for(Future<WorkItem> f : workItems) {
			try {
				procList.add( new WorkItemProcessor( f.get() ) );
			} catch (InterruptedException e) {
				
				System.out.println("ERROR: Thread execution was interrupted. " + e.getMessage());
				
				System.exit(1);
			} catch (ExecutionException e) {
				
				System.out.println("ERROR: Failed to process WorkItem. " + e.getMessage());
				
				System.exit(1);
			}
		}
		
		return procList;
	}
	
	
	
	/**
	 * Application entry point.
	 * 
	 * @param args Command line arguments. Not used.
	 */
	public static void main(String[] args) {
		
		WorkItemManager wim = new WorkItemManager(4, 300000);

		wim.init();
		wim.process();
		wim.print();
	}
}
