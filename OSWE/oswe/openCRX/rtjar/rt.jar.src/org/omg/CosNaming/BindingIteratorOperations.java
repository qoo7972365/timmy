package org.omg.CosNaming;

public interface BindingIteratorOperations {
  boolean next_one(BindingHolder paramBindingHolder);
  
  boolean next_n(int paramInt, BindingListHolder paramBindingListHolder);
  
  void destroy();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/BindingIteratorOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */