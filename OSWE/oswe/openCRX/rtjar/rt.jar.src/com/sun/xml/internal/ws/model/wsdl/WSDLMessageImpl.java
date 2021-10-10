/*    */ package com.sun.xml.internal.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLMessage;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPart;
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WSDLMessageImpl
/*    */   extends AbstractExtensibleImpl
/*    */   implements EditableWSDLMessage
/*    */ {
/*    */   private final QName name;
/*    */   private final ArrayList<EditableWSDLPart> parts;
/*    */   
/*    */   public WSDLMessageImpl(XMLStreamReader xsr, QName name) {
/* 48 */     super(xsr);
/* 49 */     this.name = name;
/* 50 */     this.parts = new ArrayList<>();
/*    */   }
/*    */   
/*    */   public QName getName() {
/* 54 */     return this.name;
/*    */   }
/*    */   
/*    */   public void add(EditableWSDLPart part) {
/* 58 */     this.parts.add(part);
/*    */   }
/*    */   
/*    */   public Iterable<EditableWSDLPart> parts() {
/* 62 */     return this.parts;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLMessageImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */