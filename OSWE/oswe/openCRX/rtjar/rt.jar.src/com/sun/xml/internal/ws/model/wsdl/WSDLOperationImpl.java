/*     */ package com.sun.xml.internal.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLInput;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLFault;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLInput;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLMessage;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOutput;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPart;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPortType;
/*     */ import com.sun.xml.internal.ws.util.QNameMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.Locator;
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
/*     */ public final class WSDLOperationImpl
/*     */   extends AbstractExtensibleImpl
/*     */   implements EditableWSDLOperation
/*     */ {
/*     */   private final QName name;
/*     */   private String parameterOrder;
/*     */   private EditableWSDLInput input;
/*     */   private EditableWSDLOutput output;
/*     */   private final List<EditableWSDLFault> faults;
/*     */   private final QNameMap<EditableWSDLFault> faultMap;
/*     */   protected Iterable<EditableWSDLMessage> messages;
/*     */   private final EditableWSDLPortType owner;
/*     */   
/*     */   public WSDLOperationImpl(XMLStreamReader xsr, EditableWSDLPortType owner, QName name) {
/*  62 */     super(xsr);
/*  63 */     this.name = name;
/*  64 */     this.faults = new ArrayList<>();
/*  65 */     this.faultMap = new QNameMap();
/*  66 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  70 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getParameterOrder() {
/*  74 */     return this.parameterOrder;
/*     */   }
/*     */   
/*     */   public void setParameterOrder(String parameterOrder) {
/*  78 */     this.parameterOrder = parameterOrder;
/*     */   }
/*     */   
/*     */   public EditableWSDLInput getInput() {
/*  82 */     return this.input;
/*     */   }
/*     */   
/*     */   public void setInput(EditableWSDLInput input) {
/*  86 */     this.input = input;
/*     */   }
/*     */   
/*     */   public EditableWSDLOutput getOutput() {
/*  90 */     return this.output;
/*     */   }
/*     */   
/*     */   public boolean isOneWay() {
/*  94 */     return (this.output == null);
/*     */   }
/*     */   
/*     */   public void setOutput(EditableWSDLOutput output) {
/*  98 */     this.output = output;
/*     */   }
/*     */   
/*     */   public Iterable<EditableWSDLFault> getFaults() {
/* 102 */     return this.faults;
/*     */   }
/*     */   
/*     */   public EditableWSDLFault getFault(QName faultDetailName) {
/* 106 */     EditableWSDLFault fault = (EditableWSDLFault)this.faultMap.get(faultDetailName);
/* 107 */     if (fault != null) {
/* 108 */       return fault;
/*     */     }
/* 110 */     for (EditableWSDLFault fi : this.faults) {
/* 111 */       assert fi.getMessage().parts().iterator().hasNext();
/* 112 */       EditableWSDLPart part = fi.getMessage().parts().iterator().next();
/* 113 */       if (part.getDescriptor().name().equals(faultDetailName)) {
/* 114 */         this.faultMap.put(faultDetailName, fi);
/* 115 */         return fi;
/*     */       } 
/*     */     } 
/* 118 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public QName getPortTypeName() {
/* 123 */     return this.owner.getName();
/*     */   }
/*     */   
/*     */   public void addFault(EditableWSDLFault fault) {
/* 127 */     this.faults.add(fault);
/*     */   }
/*     */   
/*     */   public void freeze(EditableWSDLModel root) {
/* 131 */     assert this.input != null;
/* 132 */     this.input.freeze(root);
/* 133 */     if (this.output != null)
/* 134 */       this.output.freeze(root); 
/* 135 */     for (EditableWSDLFault fault : this.faults)
/* 136 */       fault.freeze(root); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLOperationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */