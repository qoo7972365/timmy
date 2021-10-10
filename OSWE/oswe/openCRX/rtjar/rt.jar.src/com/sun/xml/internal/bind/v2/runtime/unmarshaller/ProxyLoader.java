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
/*    */ 
/*    */ public abstract class ProxyLoader
/*    */   extends Loader
/*    */ {
/*    */   public ProxyLoader() {
/* 38 */     super(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public final void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 43 */     Loader loader = selectLoader(state, ea);
/* 44 */     state.setLoader(loader);
/* 45 */     loader.startElement(state, ea);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract Loader selectLoader(UnmarshallingContext.State paramState, TagName paramTagName) throws SAXException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void leaveElement(UnmarshallingContext.State state, TagName ea) {
/* 59 */     throw new IllegalStateException();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/ProxyLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */