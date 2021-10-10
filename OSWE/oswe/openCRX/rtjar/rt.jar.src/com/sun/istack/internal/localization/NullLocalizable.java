/*    */ package com.sun.istack.internal.localization;
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
/*    */ public final class NullLocalizable
/*    */   implements Localizable
/*    */ {
/*    */   private final String msg;
/*    */   
/*    */   public NullLocalizable(String msg) {
/* 37 */     if (msg == null)
/* 38 */       throw new IllegalArgumentException(); 
/* 39 */     this.msg = msg;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 43 */     return "\000";
/*    */   }
/*    */   public Object[] getArguments() {
/* 46 */     return new Object[] { this.msg };
/*    */   }
/*    */   public String getResourceBundleName() {
/* 49 */     return "";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/istack/internal/localization/NullLocalizable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */