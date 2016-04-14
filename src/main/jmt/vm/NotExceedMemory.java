package jmt.vm;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.GenericProperty;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.VM;

public class NotExceedMemory extends GenericProperty {
  Long memory_limit;
  Long original_limit;
  boolean has_exceeded;
  String memory_units;

  public NotExceedMemory (Config config) {
    has_exceeded = false;

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
      case "%":
        Runtime r = Runtime.getRuntime();
        memory_limit = (r.totalMemory() - r.freeMemory()) * (memory_limit / 100);
    }
  }

  @Override
  public String getExplanation () {
    return "must not exceed memory limit";
  }

  @Override
  public String getErrorMessage () {
    if (has_exceeded == true) {
      return "Memory exceeded " + original_limit + " " + memory_units;
    }

    return null;
  }

  @Override
  public boolean check (Search search, VM vm) {
    Runtime r = Runtime.getRuntime();
    long heap_used = r.totalMemory() - r.freeMemory();
    has_exceeded = heap_used > memory_limit;

    return !has_exceeded;
  }
}
