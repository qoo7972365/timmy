/*    */ package com.sun.xml.internal.messaging.saaj.util;
/*    */ 
/*    */ import java.security.AccessControlException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SAAJUtil
/*    */ {
/*    */   public static boolean getSystemBoolean(String arg) {
/*    */     try {
/* 38 */       return Boolean.getBoolean(arg);
/* 39 */     } catch (AccessControlException ex) {
/* 40 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String getSystemProperty(String arg) {
/*    */     try {
/* 46 */       return System.getProperty(arg);
/* 47 */     } catch (SecurityException ex) {
/* 48 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/util/SAAJUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */