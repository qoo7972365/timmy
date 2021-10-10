/*    */ package com.sun.xml.internal.bind.v2.model.core;
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
/*    */ public enum WildcardMode
/*    */ {
/* 34 */   STRICT(false, true), SKIP(true, false), LAX(true, true);
/*    */   
/*    */   public final boolean allowTypedObject;
/*    */   public final boolean allowDom;
/*    */   
/*    */   WildcardMode(boolean allowDom, boolean allowTypedObject) {
/* 40 */     this.allowDom = allowDom;
/* 41 */     this.allowTypedObject = allowTypedObject;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/core/WildcardMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */