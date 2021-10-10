package org.omg.PortableInterceptor;

public interface IORInterceptor_3_0Operations extends IORInterceptorOperations {
  void components_established(IORInfo paramIORInfo);
  
  void adapter_manager_state_changed(int paramInt, short paramShort);
  
  void adapter_state_changed(ObjectReferenceTemplate[] paramArrayOfObjectReferenceTemplate, short paramShort);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/IORInterceptor_3_0Operations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */