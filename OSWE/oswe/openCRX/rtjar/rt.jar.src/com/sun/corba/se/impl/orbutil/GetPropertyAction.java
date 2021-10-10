/*    */ package com.sun.corba.se.impl.orbutil;
/*    */ 
/*    */ import java.security.PrivilegedAction;
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
/*    */ public class GetPropertyAction
/*    */   implements PrivilegedAction
/*    */ {
/*    */   private String theProp;
/*    */   private String defaultVal;
/*    */   
/*    */   public GetPropertyAction(String paramString) {
/* 62 */     this.theProp = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GetPropertyAction(String paramString1, String paramString2) {
/* 73 */     this.theProp = paramString1;
/* 74 */     this.defaultVal = paramString2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object run() {
/* 85 */     String str = System.getProperty(this.theProp);
/* 86 */     return (str == null) ? this.defaultVal : str;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/GetPropertyAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */