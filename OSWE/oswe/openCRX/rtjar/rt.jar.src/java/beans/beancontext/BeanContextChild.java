package java.beans.beancontext;

import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public interface BeanContextChild {
  void setBeanContext(BeanContext paramBeanContext) throws PropertyVetoException;
  
  BeanContext getBeanContext();
  
  void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener);
  
  void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener);
  
  void addVetoableChangeListener(String paramString, VetoableChangeListener paramVetoableChangeListener);
  
  void removeVetoableChangeListener(String paramString, VetoableChangeListener paramVetoableChangeListener);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/beancontext/BeanContextChild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */