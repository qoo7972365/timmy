package javax.management;

public interface PersistentMBean {
  void load() throws MBeanException, RuntimeOperationsException, InstanceNotFoundException;
  
  void store() throws MBeanException, RuntimeOperationsException, InstanceNotFoundException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/PersistentMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */