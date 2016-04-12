package jmt.util;

public class StateInformation {
  public StateInformation() {}
  public void reset(int id, boolean has_next, boolean is_new) {
    this.id = id;
    this.has_next = has_next;
    this.error = null;
    this.is_new = is_new;
  }
  public int id = -1;
  public boolean has_next = true;
  public String error = null;
  public boolean is_new = false;
}
