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
/*     */ import com.sun.xml.internal.ws.api.wsdl.parser.WSDLParserExtensionContext;
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
/*     */ class DelegatingParserExtension
/*     */   extends WSDLParserExtension
/*     */ {
/*     */   protected final WSDLParserExtension core;
/*     */   
/*     */   public DelegatingParserExtension(WSDLParserExtension core) {
/*  44 */     this.core = core;
/*     */   }
/*     */   
/*     */   public void start(WSDLParserExtensionContext context) {
/*  48 */     this.core.start(context);
/*     */   }
/*     */   
/*     */   public void serviceAttributes(EditableWSDLService service, XMLStreamReader reader) {
/*  52 */     this.core.serviceAttributes(service, reader);
/*     */   }
/*     */   
/*     */   public boolean serviceElements(EditableWSDLService service, XMLStreamReader reader) {
/*  56 */     return this.core.serviceElements(service, reader);
/*     */   }
/*     */   
/*     */   public void portAttributes(EditableWSDLPort port, XMLStreamReader reader) {
/*  60 */     this.core.portAttributes(port, reader);
/*     */   }
/*     */   
/*     */   public boolean portElements(EditableWSDLPort port, XMLStreamReader reader) {
/*  64 */     return this.core.portElements(port, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationInput(EditableWSDLOperation op, XMLStreamReader reader) {
/*  68 */     return this.core.portTypeOperationInput(op, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationOutput(EditableWSDLOperation op, XMLStreamReader reader) {
/*  72 */     return this.core.portTypeOperationOutput(op, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationFault(EditableWSDLOperation op, XMLStreamReader reader) {
/*  76 */     return this.core.portTypeOperationFault(op, reader);
/*     */   }
/*     */   
/*     */   public boolean definitionsElements(XMLStreamReader reader) {
/*  80 */     return this.core.definitionsElements(reader);
/*     */   }
/*     */   
/*     */   public boolean bindingElements(EditableWSDLBoundPortType binding, XMLStreamReader reader) {
/*  84 */     return this.core.bindingElements(binding, reader);
/*     */   }
/*     */   
/*     */   public void bindingAttributes(EditableWSDLBoundPortType binding, XMLStreamReader reader) {
/*  88 */     this.core.bindingAttributes(binding, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeElements(EditableWSDLPortType portType, XMLStreamReader reader) {
/*  92 */     return this.core.portTypeElements(portType, reader);
/*     */   }
/*     */   
/*     */   public void portTypeAttributes(EditableWSDLPortType portType, XMLStreamReader reader) {
/*  96 */     this.core.portTypeAttributes(portType, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationElements(EditableWSDLOperation operation, XMLStreamReader reader) {
/* 100 */     return this.core.portTypeOperationElements(operation, reader);
/*     */   }
/*     */   
/*     */   public void portTypeOperationAttributes(EditableWSDLOperation operation, XMLStreamReader reader) {
/* 104 */     this.core.portTypeOperationAttributes(operation, reader);
/*     */   }
/*     */   
/*     */   public boolean bindingOperationElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 108 */     return this.core.bindingOperationElements(operation, reader);
/*     */   }
/*     */   
/*     */   public void bindingOperationAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 112 */     this.core.bindingOperationAttributes(operation, reader);
/*     */   }
/*     */   
/*     */   public boolean messageElements(EditableWSDLMessage msg, XMLStreamReader reader) {
/* 116 */     return this.core.messageElements(msg, reader);
/*     */   }
/*     */   
/*     */   public void messageAttributes(EditableWSDLMessage msg, XMLStreamReader reader) {
/* 120 */     this.core.messageAttributes(msg, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationInputElements(EditableWSDLInput input, XMLStreamReader reader) {
/* 124 */     return this.core.portTypeOperationInputElements(input, reader);
/*     */   }
/*     */   
/*     */   public void portTypeOperationInputAttributes(EditableWSDLInput input, XMLStreamReader reader) {
/* 128 */     this.core.portTypeOperationInputAttributes(input, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationOutputElements(EditableWSDLOutput output, XMLStreamReader reader) {
/* 132 */     return this.core.portTypeOperationOutputElements(output, reader);
/*     */   }
/*     */   
/*     */   public void portTypeOperationOutputAttributes(EditableWSDLOutput output, XMLStreamReader reader) {
/* 136 */     this.core.portTypeOperationOutputAttributes(output, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationFaultElements(EditableWSDLFault fault, XMLStreamReader reader) {
/* 140 */     return this.core.portTypeOperationFaultElements(fault, reader);
/*     */   }
/*     */   
/*     */   public void portTypeOperationFaultAttributes(EditableWSDLFault fault, XMLStreamReader reader) {
/* 144 */     this.core.portTypeOperationFaultAttributes(fault, reader);
/*     */   }
/*     */   
/*     */   public boolean bindingOperationInputElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 148 */     return this.core.bindingOperationInputElements(operation, reader);
/*     */   }
/*     */   
/*     */   public void bindingOperationInputAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 152 */     this.core.bindingOperationInputAttributes(operation, reader);
/*     */   }
/*     */   
/*     */   public boolean bindingOperationOutputElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 156 */     return this.core.bindingOperationOutputElements(operation, reader);
/*     */   }
/*     */   
/*     */   public void bindingOperationOutputAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 160 */     this.core.bindingOperationOutputAttributes(operation, reader);
/*     */   }
/*     */   
/*     */   public boolean bindingOperationFaultElements(EditableWSDLBoundFault fault, XMLStreamReader reader) {
/* 164 */     return this.core.bindingOperationFaultElements(fault, reader);
/*     */   }
/*     */   
/*     */   public void bindingOperationFaultAttributes(EditableWSDLBoundFault fault, XMLStreamReader reader) {
/* 168 */     this.core.bindingOperationFaultAttributes(fault, reader);
/*     */   }
/*     */   
/*     */   public void finished(WSDLParserExtensionContext context) {
/* 172 */     this.core.finished(context);
/*     */   }
/*     */   
/*     */   public void postFinished(WSDLParserExtensionContext context) {
/* 176 */     this.core.postFinished(context);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/parser/DelegatingParserExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */