/*    */ package com.sun.xml.internal.ws.util;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Method;
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
/*    */ public class FastInfosetReflection
/*    */ {
/*    */   public static final Constructor fiStAXDocumentParser_new;
/*    */   public static final Method fiStAXDocumentParser_setInputStream;
/*    */   public static final Method fiStAXDocumentParser_setStringInterning;
/*    */   
/*    */   static {
/* 54 */     Constructor<?> tmp_new = null;
/* 55 */     Method tmp_setInputStream = null;
/* 56 */     Method tmp_setStringInterning = null;
/*    */ 
/*    */     
/*    */     try {
/* 60 */       Class<?> clazz = Class.forName("com.sun.xml.internal.fastinfoset.stax.StAXDocumentParser");
/* 61 */       tmp_new = clazz.getConstructor(new Class[0]);
/*    */       
/* 63 */       tmp_setInputStream = clazz.getMethod("setInputStream", new Class[] { InputStream.class });
/*    */       
/* 65 */       tmp_setStringInterning = clazz.getMethod("setStringInterning", new Class[] { boolean.class });
/*    */     }
/* 67 */     catch (Exception exception) {}
/*    */ 
/*    */     
/* 70 */     fiStAXDocumentParser_new = tmp_new;
/* 71 */     fiStAXDocumentParser_setInputStream = tmp_setInputStream;
/* 72 */     fiStAXDocumentParser_setStringInterning = tmp_setStringInterning;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/FastInfosetReflection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */