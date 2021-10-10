/*    */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*    */ 
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
/*    */ public final class DefaultValueLoaderDecorator
/*    */   extends Loader
/*    */ {
/*    */   private final Loader l;
/*    */   private final String defaultValue;
/*    */   
/*    */   public DefaultValueLoaderDecorator(Loader l, String defaultValue) {
/* 40 */     this.l = l;
/* 41 */     this.defaultValue = defaultValue;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 47 */     if (state.getElementDefaultValue() == null) {
/* 48 */       state.setElementDefaultValue(this.defaultValue);
/*    */     }
/* 50 */     state.setLoader(this.l);
/* 51 */     this.l.startElement(state, ea);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/DefaultValueLoaderDecorator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */