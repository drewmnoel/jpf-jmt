package jmt.vm;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.GenericProperty;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.VM;

/**
 * A property which limits the heap usage at each state in the
 * JPF model. The property behaviour is governed by two configs:
 * jmt.search.memory_units and jmt.search.memory_limit
 *
 * @author Drew Noel - cse23217 - 212513784
 *
 */
public class NotExceedMemory extends GenericProperty {
  /** Track the memory limit, converted into bytes */
  Long memory_limit;
  /** Track the original input memory limit */
  Long original_limit;
  /** Track if the current search state has exceeded the limit */
  boolean has_exceeded;
  /** Remember the input memory units */
  String memory_units;

  /**
   * Constructor, called automatically by JPF. Sets up the memory
   * threshold
   *
   * @param  config JPF Config parameter
   */
  public NotExceedMemory (Config config) {
    has_exceeded = false;

    // Read the configs and track the original limit
    memory_units = config.getString("jmt.search.memory_units", "B");
    memory_limit = config.getLong("jmt.search.memory_limit", 0);
    original_limit = memory_limit;

    // Update the limit to be in bytes
    // Intention fallthrough to avoid tricky numbers
    switch (memory_units) {
      case "GB":
        memory_limit *= 1024;
      case "MB":
        memory_limit *= 1024;
      case "KB":
        memory_limit *= 1024;
      case "B":
        break;
      // Percentages are converted into bytes immediately
      case "%":
        Runtime r = Runtime.getRuntime();
        memory_limit = (r.totalMemory() - r.freeMemory()) * (memory_limit / 100);
        break;
    }
  }

  /**
   * Return an explanation for this property
   *
   * @return The string explanation of this property
   */
  @Override
  public String getExplanation () {
    return "must not exceed memory limit";
  }

  /**
   * Returns the error message, if any
   *
   * @return The exact reason of the error, or null if no error
   */
  @Override
  public String getErrorMessage () {
    if (has_exceeded == true) {
      return "Memory exceeded " + original_limit + " " + memory_units;
    }

    return null;
  }

  /**
   * Determine if the property has been violated
   *
   * @param   search  The search object
   * @param   vm      The VM object
   *
   * @return  True if the heap usage is higher than the
   *               threshold, otherwise false
   */
  @Override
  public boolean check (Search search, VM vm) {
    // Calculate the current memory usage
    Runtime r = Runtime.getRuntime();
    long heap_used = r.totalMemory() - r.freeMemory();

    // Important to remember that 0 is infinity!
    has_exceeded = heap_used > memory_limit && memory_limit > 0;

    return !has_exceeded;
  }
}
