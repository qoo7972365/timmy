/*    */ package com.sun.xml.internal.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLFault;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundFault;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLFault;
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
/*    */ public class WSDLBoundFaultImpl
/*    */   extends AbstractExtensibleImpl
/*    */   implements EditableWSDLBoundFault
/*    */ {
/*    */   private final String name;
/*    */   private EditableWSDLFault fault;
/*    */   private EditableWSDLBoundOperation owner;
/*    */   
/*    */   public WSDLBoundFaultImpl(XMLStreamReader xsr, String name, EditableWSDLBoundOperation owner) {
/* 46 */     super(xsr);
/* 47 */     this.name = name;
/* 48 */     this.owner = owner;
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String getName() {
/* 54 */     return this.name;
/*    */   }
/*    */   
/*    */   public QName getQName() {
/* 58 */     if (this.owner.getOperation() != null) {
/* 59 */       return new QName(this.owner.getOperation().getName().getNamespaceURI(), this.name);
/*    */     }
/* 61 */     return null;
/*    */   }
/*    */   
/*    */   public EditableWSDLFault getFault() {
/* 65 */     return this.fault;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public EditableWSDLBoundOperation getBoundOperation() {
/* 70 */     return this.owner;
/*    */   }
/*    */   
/*    */   public void freeze(EditableWSDLBoundOperation root) {
/* 74 */     assert root != null;
/* 75 */     EditableWSDLOperation op = root.getOperation();
/* 76 */     if (op != null)
/* 77 */       for (EditableWSDLFault f : op.getFaults()) {
/* 78 */         if (f.getName().equals(this.name)) {
/* 79 */           this.fault = f;
/*    */           break;
/*    */         } 
/*    */       }  
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLBoundFaultImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */