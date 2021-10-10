/*     */ package com.sun.xml.internal.ws.api.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundFault;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLFault;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLInput;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLMessage;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOutput;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPort;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLService;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSDLParserExtension
/*     */ {
/*     */   public void start(WSDLParserExtensionContext context) {}
/*     */   
/*     */   public void serviceAttributes(EditableWSDLService service, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean serviceElements(EditableWSDLService service, XMLStreamReader reader) {
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portAttributes(EditableWSDLPort port, XMLStreamReader reader) {}
/*     */ 
/*     */   
/*     */   public boolean portElements(EditableWSDLPort port, XMLStreamReader reader) {
/* 169 */     return false;
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationInput(EditableWSDLOperation op, XMLStreamReader reader) {
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationOutput(EditableWSDLOperation op, XMLStreamReader reader) {
/* 177 */     return false;
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationFault(EditableWSDLOperation op, XMLStreamReader reader) {
/* 181 */     return false;
/*     */   }
/*     */   
/*     */   public boolean definitionsElements(XMLStreamReader reader) {
/* 185 */     return false;
/*     */   }
/*     */   
/*     */   public boolean bindingElements(EditableWSDLBoundPortType binding, XMLStreamReader reader) {
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindingAttributes(EditableWSDLBoundPortType binding, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean portTypeElements(EditableWSDLPortType portType, XMLStreamReader reader) {
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeAttributes(EditableWSDLPortType portType, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean portTypeOperationElements(EditableWSDLOperation operation, XMLStreamReader reader) {
/* 203 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationAttributes(EditableWSDLOperation operation, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean bindingOperationElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindingOperationAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean messageElements(EditableWSDLMessage msg, XMLStreamReader reader) {
/* 217 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void messageAttributes(EditableWSDLMessage msg, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean portTypeOperationInputElements(EditableWSDLInput input, XMLStreamReader reader) {
/* 224 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationInputAttributes(EditableWSDLInput input, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean portTypeOperationOutputElements(EditableWSDLOutput output, XMLStreamReader reader) {
/* 231 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationOutputAttributes(EditableWSDLOutput output, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean portTypeOperationFaultElements(EditableWSDLFault fault, XMLStreamReader reader) {
/* 238 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationFaultAttributes(EditableWSDLFault fault, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean bindingOperationInputElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 245 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindingOperationInputAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean bindingOperationOutputElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 252 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindingOperationOutputAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean bindingOperationFaultElements(EditableWSDLBoundFault fault, XMLStreamReader reader) {
/* 259 */     return false;
/*     */   }
/*     */   
/*     */   public void bindingOperationFaultAttributes(EditableWSDLBoundFault fault, XMLStreamReader reader) {}
/*     */   
/*     */   public void finished(WSDLParserExtensionContext context) {}
/*     */   
/*     */   public void postFinished(WSDLParserExtensionContext context) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/wsdl/parser/WSDLParserExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */