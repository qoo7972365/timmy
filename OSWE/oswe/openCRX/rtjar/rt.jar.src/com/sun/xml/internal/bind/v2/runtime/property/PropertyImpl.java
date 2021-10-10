/*    */ package com.sun.xml.internal.bind.v2.runtime.property;
/*    */ 
/*    */ import com.sun.xml.internal.bind.api.AccessorException;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*    */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*    */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*    */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
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
/*    */ abstract class PropertyImpl<BeanT>
/*    */   implements Property<BeanT>
/*    */ {
/*    */   protected final String fieldName;
/* 48 */   private RuntimePropertyInfo propertyInfo = null;
/*    */   private boolean hiddenByOverride = false;
/*    */   
/*    */   public PropertyImpl(JAXBContextImpl context, RuntimePropertyInfo prop) {
/* 52 */     this.fieldName = prop.getName();
/* 53 */     if (context.retainPropertyInfo) {
/* 54 */       this.propertyInfo = prop;
/*    */     }
/*    */   }
/*    */   
/*    */   public RuntimePropertyInfo getInfo() {
/* 59 */     return this.propertyInfo;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serializeBody(BeanT o, XMLSerializer w, Object outerPeer) throws SAXException, AccessorException, IOException, XMLStreamException {}
/*    */ 
/*    */   
/*    */   public void serializeURIs(BeanT o, XMLSerializer w) throws SAXException, AccessorException {}
/*    */   
/*    */   public boolean hasSerializeURIAction() {
/* 69 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/* 74 */     return null;
/*    */   }
/*    */   
/*    */   public void wrapUp() {}
/*    */   
/*    */   public boolean isHiddenByOverride() {
/* 80 */     return this.hiddenByOverride;
/*    */   }
/*    */   
/*    */   public void setHiddenByOverride(boolean hidden) {
/* 84 */     this.hiddenByOverride = hidden;
/*    */   }
/*    */   
/*    */   public String getFieldName() {
/* 88 */     return this.fieldName;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/property/PropertyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */