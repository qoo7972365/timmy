/*    */ package com.sun.istack.internal.localization;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ public final class LocalizableMessage
/*    */   implements Localizable
/*    */ {
/*    */   private final String _bundlename;
/*    */   private final String _key;
/*    */   private final Object[] _args;
/*    */   
/*    */   public LocalizableMessage(String bundlename, String key, Object... args) {
/* 40 */     this._bundlename = bundlename;
/* 41 */     this._key = key;
/* 42 */     if (args == null)
/* 43 */       args = new Object[0]; 
/* 44 */     this._args = args;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 48 */     return this._key;
/*    */   }
/*    */   
/*    */   public Object[] getArguments() {
/* 52 */     return Arrays.copyOf(this._args, this._args.length);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 56 */     return this._bundlename;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/istack/internal/localization/LocalizableMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */