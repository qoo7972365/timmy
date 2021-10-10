/*    */ package com.sun.xml.internal.bind.util;
/*    */ 
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Which
/*    */ {
/*    */   public static String which(Class clazz) {
/* 39 */     return which(clazz.getName(), SecureLoader.getClassClassLoader(clazz));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String which(String classname, ClassLoader loader) {
/* 51 */     String classnameAsResource = classname.replace('.', '/') + ".class";
/*    */     
/* 53 */     if (loader == null) {
/* 54 */       loader = SecureLoader.getSystemClassLoader();
/*    */     }
/*    */     
/* 57 */     URL it = loader.getResource(classnameAsResource);
/* 58 */     if (it != null) {
/* 59 */       return it.toString();
/*    */     }
/* 61 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/util/Which.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */