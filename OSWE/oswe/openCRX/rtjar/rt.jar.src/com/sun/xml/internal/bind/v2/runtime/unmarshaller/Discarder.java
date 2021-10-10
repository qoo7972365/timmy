/*    */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
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
/*    */ 
/*    */ public final class Discarder
/*    */   extends Loader
/*    */ {
/* 39 */   public static final Loader INSTANCE = new Discarder();
/*    */   
/*    */   private Discarder() {
/* 42 */     super(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void childElement(UnmarshallingContext.State state, TagName ea) {
/* 47 */     state.setTarget(null);
/*    */     
/* 49 */     state.setLoader(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/Discarder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */