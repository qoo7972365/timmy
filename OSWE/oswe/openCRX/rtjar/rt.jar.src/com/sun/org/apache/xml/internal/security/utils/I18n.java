/*     */ package com.sun.org.apache.xml.internal.security.utils;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.Init;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class I18n
/*     */ {
/*     */   public static final String NOT_INITIALIZED_MSG = "You must initialize the xml-security library correctly before you use it. Call the static method \"com.sun.org.apache.xml.internal.security.Init.init();\" to do that before you use any functionality from that library.";
/*     */   private static ResourceBundle resourceBundle;
/*     */   private static boolean alreadyInitialized = false;
/*     */   
/*     */   public static String translate(String paramString, Object[] paramArrayOfObject) {
/*  70 */     return getExceptionMessage(paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String translate(String paramString) {
/*  83 */     return getExceptionMessage(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getExceptionMessage(String paramString) {
/*     */     try {
/*  95 */       return resourceBundle.getString(paramString);
/*  96 */     } catch (Throwable throwable) {
/*  97 */       if (Init.isInitialized()) {
/*  98 */         return "No message with ID \"" + paramString + "\" found in resource bundle \"" + "com/sun/org/apache/xml/internal/security/resource/xmlsecurity" + "\"";
/*     */       }
/*     */ 
/*     */       
/* 102 */       return "You must initialize the xml-security library correctly before you use it. Call the static method \"com.sun.org.apache.xml.internal.security.Init.init();\" to do that before you use any functionality from that library.";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getExceptionMessage(String paramString, Exception paramException) {
/*     */     try {
/* 115 */       Object[] arrayOfObject = { paramException.getMessage() };
/* 116 */       return MessageFormat.format(resourceBundle.getString(paramString), arrayOfObject);
/* 117 */     } catch (Throwable throwable) {
/* 118 */       if (Init.isInitialized()) {
/* 119 */         return "No message with ID \"" + paramString + "\" found in resource bundle \"" + "com/sun/org/apache/xml/internal/security/resource/xmlsecurity" + "\". Original Exception was a " + paramException
/*     */ 
/*     */ 
/*     */           
/* 123 */           .getClass().getName() + " and message " + paramException
/* 124 */           .getMessage();
/*     */       }
/* 126 */       return "You must initialize the xml-security library correctly before you use it. Call the static method \"com.sun.org.apache.xml.internal.security.Init.init();\" to do that before you use any functionality from that library.";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getExceptionMessage(String paramString, Object[] paramArrayOfObject) {
/*     */     try {
/* 139 */       return MessageFormat.format(resourceBundle.getString(paramString), paramArrayOfObject);
/* 140 */     } catch (Throwable throwable) {
/* 141 */       if (Init.isInitialized()) {
/* 142 */         return "No message with ID \"" + paramString + "\" found in resource bundle \"" + "com/sun/org/apache/xml/internal/security/resource/xmlsecurity" + "\"";
/*     */       }
/*     */ 
/*     */       
/* 146 */       return "You must initialize the xml-security library correctly before you use it. Call the static method \"com.sun.org.apache.xml.internal.security.Init.init();\" to do that before you use any functionality from that library.";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void init(String paramString1, String paramString2) {
/* 157 */     if (alreadyInitialized) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 162 */     resourceBundle = ResourceBundle.getBundle("com/sun/org/apache/xml/internal/security/resource/xmlsecurity", new Locale(paramString1, paramString2));
/*     */ 
/*     */ 
/*     */     
/* 166 */     alreadyInitialized = true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/I18n.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */