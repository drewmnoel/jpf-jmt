package jmt.listener;

import java.util.HashMap;
import java.util.Map;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.search.Search;

import jmt.util.StateInformation;


/**
 * A listener which tracks information about the heap usage at each state in the
 * JPF model. Due to how JPF tracks states, any serious heap change will trigger
 * a transition notification.
 *
 * @author Drew Noel - cse23217 - 212513784
 *
 */
public class HeapListenerDot extends BetterStateSpaceDot {
	/* Constants to represent granularity */
	/** Track usage as bytes */
	private static final long B = 1;
	/** Label for bytes */
	private static final String B_LABEL = "B";
	/** Track usage as kilobytes */
	private static final long KB = 1024;
	/** Label for kilobytes */
	private static final String KB_LABEL = "KB";
	/** Track usage as megabytes */
	private static final long MB = HeapListenerDot.KB * 1024;
	/** Label for megabytes */
	private static final String MB_LABEL = "MB";

	/** Keep a hook to the runtime */
	private static Runtime runtime = Runtime.getRuntime();

	/** Keep track of states and heap sizes */
	private final Map<Integer, Long> stateToHeap;

	/** Track the granularity (and associated label) */
	private long prefixConstant;
	private String prefixLabel;

	/**
	 * Mandatory ctor
	 *
	 * @param config
	 * @param jpf
	 */
	public HeapListenerDot(Config config, JPF jpf) {
		super(config, jpf);

		// Create the empty map
		this.stateToHeap = new HashMap<>();

		// Read the prefix from config and set appropriate vars. Default to KB.
		final String configPrefix = config.getString("memory.prefix", "KB");
		switch (configPrefix) {
		case B_LABEL:
			this.prefixConstant = HeapListenerDot.B;
			this.prefixLabel = HeapListenerDot.B_LABEL;
			break;
		case KB_LABEL:
		default:
			this.prefixConstant = HeapListenerDot.KB;
			this.prefixLabel = HeapListenerDot.KB_LABEL;
			break;
		case MB_LABEL:
			this.prefixConstant = HeapListenerDot.MB;
			this.prefixLabel = HeapListenerDot.MB_LABEL;
			break;
		}

	}

	/**
	 * Override the makeStateLabel defined in StateSpaceDot to give our custom labels
	 *
	 */
	@Override
	protected String makeStateLabel(StateInformation state) {
		return state.id + " [" + this.stateToHeap.get(state.id) + this.prefixLabel + "]";
	}


	/**
	 * Listen for the state advancement, then update the respective memory count
	 */
	@Override
	public void stateAdvanced(Search search) {
		long heapMemory = HeapListenerDot.runtime.totalMemory() - HeapListenerDot.runtime.freeMemory();

		heapMemory = heapMemory / this.prefixConstant;
		this.stateToHeap.put(search.getStateId(), heapMemory);

		super.stateAdvanced(search);
	}
}
