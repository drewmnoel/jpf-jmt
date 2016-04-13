package jmt.vm;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.GenericProperty;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.VM;

public class NotExceedMemory extends GenericProperty {
  Long memory_limit;
  boolean has_exceeded;

  public NotExceedMemory (Config config) {
    has_exceeded = false;
    memory_limit = config.getLong("jmt.memory_limit", 0);
  }

  @Override
  public String getExplanation () {
    return "must not exceed memory limit";
  }

  @Override
  public String getErrorMessage () {
    if (has_exceeded == true) {
      return "Memory exceeded " + memory_limit + " bytes";
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
