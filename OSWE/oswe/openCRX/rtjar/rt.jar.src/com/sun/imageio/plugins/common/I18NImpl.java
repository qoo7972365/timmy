/*    */ package com.sun.imageio.plugins.common;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.PropertyResourceBundle;
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
/*    */ public class I18NImpl
/*    */ {
/*    */   protected static final String getString(String paramString1, String paramString2, String paramString3) {
/* 51 */     PropertyResourceBundle propertyResourceBundle = null;
/*    */     
/*    */     try {
/* 54 */       InputStream inputStream = Class.forName(paramString1).getResourceAsStream(paramString2);
/* 55 */       propertyResourceBundle = new PropertyResourceBundle(inputStream);
/* 56 */     } catch (Throwable throwable) {
/* 57 */       throw new RuntimeException(throwable);
/*    */     } 
/*    */     
/* 60 */     return (String)propertyResourceBundle.handleGetObject(paramString3);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/common/I18NImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */