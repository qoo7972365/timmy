package com.sun.xml.internal.ws.api.wsdl.writer;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.ws.api.WSBinding;
import com.sun.xml.internal.ws.api.model.CheckedException;
import com.sun.xml.internal.ws.api.model.JavaMethod;
import com.sun.xml.internal.ws.api.model.SEIModel;
import com.sun.xml.internal.ws.api.server.Container;

public abstract class WSDLGeneratorExtension {
  public void start(@NotNull TypedXmlWriter root, @NotNull SEIModel model, @NotNull WSBinding binding, @NotNull Container container) {}
  
  public void end(@NotNull WSDLGenExtnContext ctxt) {}
  
  public void start(WSDLGenExtnContext ctxt) {}
  
  public void addDefinitionsExtension(TypedXmlWriter definitions) {}
  
  public void addServiceExtension(TypedXmlWriter service) {}
  
  public void addPortExtension(TypedXmlWriter port) {}
  
  public void addPortTypeExtension(TypedXmlWriter portType) {}
  
  public void addBindingExtension(TypedXmlWriter binding) {}
  
  public void addOperationExtension(TypedXmlWriter operation, JavaMethod method) {}
  
  public void addBindingOperationExtension(TypedXmlWriter operation, JavaMethod method) {}
  
  public void addInputMessageExtension(TypedXmlWriter message, JavaMethod method) {}
  
  public void addOutputMessageExtension(TypedXmlWriter message, JavaMethod method) {}
  
  public void addOperationInputExtension(TypedXmlWriter input, JavaMethod method) {}
  
  public void addOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {}
  
  public void addBindingOperationInputExtension(TypedXmlWriter input, JavaMethod method) {}
  
  public void addBindingOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {}
  
  public void addBindingOperationFaultExtension(TypedXmlWriter fault, JavaMethod method, CheckedException ce) {}
  
  public void addFaultMessageExtension(TypedXmlWriter message, JavaMethod method, CheckedException ce) {}
  
  public void addOperationFaultExtension(TypedXmlWriter fault, JavaMethod method, CheckedException ce) {}
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/wsdl/writer/WSDLGeneratorExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */