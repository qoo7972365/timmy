/*    */ package com.sun.xml.internal.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLOperation;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOperation;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPortType;
/*    */ import java.util.Hashtable;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WSDLPortTypeImpl
/*    */   extends AbstractExtensibleImpl
/*    */   implements EditableWSDLPortType
/*    */ {
/*    */   private QName name;
/*    */   private final Map<String, EditableWSDLOperation> portTypeOperations;
/*    */   private EditableWSDLModel owner;
/*    */   
/*    */   public WSDLPortTypeImpl(XMLStreamReader xsr, EditableWSDLModel owner, QName name) {
/* 51 */     super(xsr);
/* 52 */     this.name = name;
/* 53 */     this.owner = owner;
/* 54 */     this.portTypeOperations = new Hashtable<>();
/*    */   }
/*    */   
/*    */   public QName getName() {
/* 58 */     return this.name;
/*    */   }
/*    */   
/*    */   public EditableWSDLOperation get(String operationName) {
/* 62 */     return this.portTypeOperations.get(operationName);
/*    */   }
/*    */   
/*    */   public Iterable<EditableWSDLOperation> getOperations() {
/* 66 */     return this.portTypeOperations.values();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void put(String opName, EditableWSDLOperation ptOp) {
/* 76 */     this.portTypeOperations.put(opName, ptOp);
/*    */   }
/*    */   
/*    */   EditableWSDLModel getOwner() {
/* 80 */     return this.owner;
/*    */   }
/*    */   
/*    */   public void freeze() {
/* 84 */     for (EditableWSDLOperation op : this.portTypeOperations.values())
/* 85 */       op.freeze(this.owner); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLPortTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */