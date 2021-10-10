package java.beans;

import java.util.EventListener;

public interface VetoableChangeListener extends EventListener {
  void vetoableChange(PropertyChangeEvent paramPropertyChangeEvent) throws PropertyVetoException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/VetoableChangeListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */