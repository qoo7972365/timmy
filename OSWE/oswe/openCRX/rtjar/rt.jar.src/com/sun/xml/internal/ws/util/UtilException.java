/*    */ package com.sun.xml.internal.ws.util;
/*    */ 
/*    */ import com.sun.istack.internal.localization.Localizable;
/*    */ import com.sun.xml.internal.ws.util.exception.JAXWSExceptionBase;
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
/*    */ public class UtilException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public UtilException(String key, Object... args) {
/* 41 */     super(key, args);
/*    */   }
/*    */   
/*    */   public UtilException(Throwable throwable) {
/* 45 */     super(throwable);
/*    */   }
/*    */   
/*    */   public UtilException(Localizable arg) {
/* 49 */     super("nestedUtilError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 53 */     return "com.sun.xml.internal.ws.resources.util";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/UtilException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */