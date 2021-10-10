/*    */ package com.sun.xml.internal.ws.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringUtils
/*    */ {
/*    */   public static String decapitalize(String name) {
/* 48 */     if (name == null || name.length() == 0) {
/* 49 */       return name;
/*    */     }
/* 51 */     if (name.length() > 1 && 
/* 52 */       Character.isUpperCase(name.charAt(1)) && 
/* 53 */       Character.isUpperCase(name.charAt(0)))
/*    */     {
/* 55 */       return name;
/*    */     }
/* 57 */     char[] chars = name.toCharArray();
/* 58 */     chars[0] = Character.toLowerCase(chars[0]);
/* 59 */     return new String(chars);
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
/*    */   
/*    */   public static String capitalize(String name) {
/* 72 */     if (name == null || name.length() == 0) {
/* 73 */       return name;
/*    */     }
/* 75 */     char[] chars = name.toCharArray();
/* 76 */     chars[0] = Character.toUpperCase(chars[0]);
/* 77 */     return new String(chars);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/StringUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */