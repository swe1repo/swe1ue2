package swe3ue;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provides a global, unique identifier for the WorkItems in a thread-safe manner.
 * 
 * @author patrick
 *
 */
public class GlobalIdProvider {
	private AtomicInteger globalId_ = null;
	static GlobalIdProvider instance_ = null;
	
	private GlobalIdProvider() {
		globalId_ = new AtomicInteger();
		globalId_.set(0);
	}
	
	static synchronized public GlobalIdProvider getInstance() {
		if(instance_ == null) {
			return instance_ = new GlobalIdProvider();
		}
		
		return instance_;
	}
	
	/**
	 * Obtains a glboal, unique Identifier.
	 * 
	 * @return The global, unique Identifier
	 */
	public int getGlobalId() {
		return globalId_.getAndAdd(1);
	}
}
