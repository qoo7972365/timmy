/*    */ package com.sun.xml.internal.bind.v2.runtime.property;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*    */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*    */ import com.sun.xml.internal.bind.v2.runtime.JaxBeanInfo;
/*    */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*    */ import java.io.IOException;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import org.xml.sax.SAXException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ArrayElementNodeProperty<BeanT, ListT, ItemT>
/*    */   extends ArrayElementProperty<BeanT, ListT, ItemT>
/*    */ {
/*    */   public ArrayElementNodeProperty(JAXBContextImpl p, RuntimeElementPropertyInfo prop) {
/* 47 */     super(p, prop);
/*    */   }
/*    */   
/*    */   public void serializeItem(JaxBeanInfo expected, ItemT item, XMLSerializer w) throws SAXException, IOException, XMLStreamException {
/* 51 */     if (item == null) {
/* 52 */       w.writeXsiNilTrue();
/*    */     } else {
/* 54 */       w.childAsXsiType(item, this.fieldName, expected, false);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/property/ArrayElementNodeProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */