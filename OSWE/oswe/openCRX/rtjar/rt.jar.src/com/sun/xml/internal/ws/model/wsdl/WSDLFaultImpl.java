/*    */ package com.sun.xml.internal.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLMessage;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLOperation;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLFault;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLMessage;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOperation;
/*    */ import java.util.List;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import org.xml.sax.Locator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WSDLFaultImpl
/*    */   extends AbstractExtensibleImpl
/*    */   implements EditableWSDLFault
/*    */ {
/*    */   private final String name;
/*    */   private final QName messageName;
/*    */   private EditableWSDLMessage message;
/*    */   private EditableWSDLOperation operation;
/* 45 */   private String action = "";
/*    */   private boolean defaultAction = true;
/*    */   
/*    */   public WSDLFaultImpl(XMLStreamReader xsr, String name, QName messageName, EditableWSDLOperation operation) {
/* 49 */     super(xsr);
/* 50 */     this.name = name;
/* 51 */     this.messageName = messageName;
/* 52 */     this.operation = operation;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 56 */     return this.name;
/*    */   }
/*    */   
/*    */   public EditableWSDLMessage getMessage() {
/* 60 */     return this.message;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public EditableWSDLOperation getOperation() {
/* 65 */     return this.operation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public QName getQName() {
/* 70 */     return new QName(this.operation.getName().getNamespaceURI(), this.name);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String getAction() {
/* 75 */     return this.action;
/*    */   }
/*    */   
/*    */   public void setAction(String action) {
/* 79 */     this.action = action;
/*    */   }
/*    */   
/*    */   public boolean isDefaultAction() {
/* 83 */     return this.defaultAction;
/*    */   }
/*    */   
/*    */   public void setDefaultAction(boolean defaultAction) {
/* 87 */     this.defaultAction = defaultAction;
/*    */   }
/*    */   
/*    */   public void freeze(EditableWSDLModel root) {
/* 91 */     this.message = root.getMessage(this.messageName);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLFaultImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */