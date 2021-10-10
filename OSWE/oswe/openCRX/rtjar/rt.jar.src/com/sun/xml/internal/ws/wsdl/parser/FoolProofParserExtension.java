/*     */ package com.sun.xml.internal.ws.wsdl.parser;
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
/*     */ import com.sun.xml.internal.ws.api.wsdl.parser.WSDLParserExtension;
/*     */ import javax.xml.namespace.QName;
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
/*     */ final class FoolProofParserExtension
/*     */   extends DelegatingParserExtension
/*     */ {
/*     */   public FoolProofParserExtension(WSDLParserExtension core) {
/*  52 */     super(core);
/*     */   }
/*     */   
/*     */   private QName pre(XMLStreamReader xsr) {
/*  56 */     return xsr.getName();
/*     */   }
/*     */   
/*     */   private boolean post(QName tagName, XMLStreamReader xsr, boolean result) {
/*  60 */     if (!tagName.equals(xsr.getName()))
/*  61 */       return foundFool(); 
/*  62 */     if (result) {
/*  63 */       if (xsr.getEventType() != 2) {
/*  64 */         foundFool();
/*     */       }
/*  66 */     } else if (xsr.getEventType() != 1) {
/*  67 */       foundFool();
/*     */     } 
/*  69 */     return result;
/*     */   }
/*     */   
/*     */   private boolean foundFool() {
/*  73 */     throw new AssertionError("XMLStreamReader is placed at the wrong place after invoking " + this.core);
/*     */   }
/*     */   
/*     */   public boolean serviceElements(EditableWSDLService service, XMLStreamReader reader) {
/*  77 */     return post(pre(reader), reader, super.serviceElements(service, reader));
/*     */   }
/*     */   
/*     */   public boolean portElements(EditableWSDLPort port, XMLStreamReader reader) {
/*  81 */     return post(pre(reader), reader, super.portElements(port, reader));
/*     */   }
/*     */   
/*     */   public boolean definitionsElements(XMLStreamReader reader) {
/*  85 */     return post(pre(reader), reader, super.definitionsElements(reader));
/*     */   }
/*     */   
/*     */   public boolean bindingElements(EditableWSDLBoundPortType binding, XMLStreamReader reader) {
/*  89 */     return post(pre(reader), reader, super.bindingElements(binding, reader));
/*     */   }
/*     */   
/*     */   public boolean portTypeElements(EditableWSDLPortType portType, XMLStreamReader reader) {
/*  93 */     return post(pre(reader), reader, super.portTypeElements(portType, reader));
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationElements(EditableWSDLOperation operation, XMLStreamReader reader) {
/*  97 */     return post(pre(reader), reader, super.portTypeOperationElements(operation, reader));
/*     */   }
/*     */   
/*     */   public boolean bindingOperationElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 101 */     return post(pre(reader), reader, super.bindingOperationElements(operation, reader));
/*     */   }
/*     */   
/*     */   public boolean messageElements(EditableWSDLMessage msg, XMLStreamReader reader) {
/* 105 */     return post(pre(reader), reader, super.messageElements(msg, reader));
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationInputElements(EditableWSDLInput input, XMLStreamReader reader) {
/* 109 */     return post(pre(reader), reader, super.portTypeOperationInputElements(input, reader));
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationOutputElements(EditableWSDLOutput output, XMLStreamReader reader) {
/* 113 */     return post(pre(reader), reader, super.portTypeOperationOutputElements(output, reader));
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationFaultElements(EditableWSDLFault fault, XMLStreamReader reader) {
/* 117 */     return post(pre(reader), reader, super.portTypeOperationFaultElements(fault, reader));
/*     */   }
/*     */   
/*     */   public boolean bindingOperationInputElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 121 */     return super.bindingOperationInputElements(operation, reader);
/*     */   }
/*     */   
/*     */   public boolean bindingOperationOutputElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 125 */     return post(pre(reader), reader, super.bindingOperationOutputElements(operation, reader));
/*     */   }
/*     */   
/*     */   public boolean bindingOperationFaultElements(EditableWSDLBoundFault fault, XMLStreamReader reader) {
/* 129 */     return post(pre(reader), reader, super.bindingOperationFaultElements(fault, reader));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/parser/FoolProofParserExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */