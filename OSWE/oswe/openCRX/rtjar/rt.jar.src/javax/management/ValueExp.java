package javax.management;

import java.io.Serializable;

public interface ValueExp extends Serializable {
  ValueExp apply(ObjectName paramObjectName) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException;
  
  @Deprecated
  void setMBeanServer(MBeanServer paramMBeanServer);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/ValueExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */