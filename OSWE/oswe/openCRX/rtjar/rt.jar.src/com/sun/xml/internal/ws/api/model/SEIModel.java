package com.sun.xml.internal.ws.api.model;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.internal.ws.util.Pool;
import java.lang.reflect.Method;
import java.util.Collection;
import javax.xml.bind.JAXBContext;
import javax.xml.namespace.QName;

public interface SEIModel {
  Pool.Marshaller getMarshallerPool();
  
  JAXBContext getJAXBContext();
  
  JavaMethod getJavaMethod(Method paramMethod);
  
  JavaMethod getJavaMethod(QName paramQName);
  
  JavaMethod getJavaMethodForWsdlOperation(QName paramQName);
  
  Collection<? extends JavaMethod> getJavaMethods();
  
  @NotNull
  String getWSDLLocation();
  
  @NotNull
  QName getServiceQName();
  
  @NotNull
  WSDLPort getPort();
  
  @NotNull
  QName getPortName();
  
  @NotNull
  QName getPortTypeName();
  
  @NotNull
  QName getBoundPortTypeName();
  
  @NotNull
  String getTargetNamespace();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/model/SEIModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */