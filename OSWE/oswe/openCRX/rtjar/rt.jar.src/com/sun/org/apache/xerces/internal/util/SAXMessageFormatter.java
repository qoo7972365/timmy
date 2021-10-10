/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
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
/*     */ public class SAXMessageFormatter
/*     */ {
/*     */   public static String formatMessage(Locale locale, String key, Object[] arguments) throws MissingResourceException {
/*     */     String msg;
/*  55 */     ResourceBundle resourceBundle = null;
/*  56 */     if (locale != null) {
/*     */       
/*  58 */       resourceBundle = SecuritySupport.getResourceBundle("com.sun.org.apache.xerces.internal.impl.msg.SAXMessages", locale);
/*     */     }
/*     */     else {
/*     */       
/*  62 */       resourceBundle = SecuritySupport.getResourceBundle("com.sun.org.apache.xerces.internal.impl.msg.SAXMessages");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  68 */       msg = resourceBundle.getString(key);
/*  69 */       if (arguments != null) {
/*     */         try {
/*  71 */           msg = MessageFormat.format(msg, arguments);
/*     */         }
/*  73 */         catch (Exception e) {
/*  74 */           msg = resourceBundle.getString("FormatFailed");
/*  75 */           msg = msg + " " + resourceBundle.getString(key);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     }
/*  81 */     catch (MissingResourceException e) {
/*  82 */       msg = resourceBundle.getString("BadMessageKey");
/*  83 */       throw new MissingResourceException(key, msg, key);
/*     */     } 
/*     */ 
/*     */     
/*  87 */     if (msg == null) {
/*  88 */       msg = key;
/*  89 */       if (arguments.length > 0) {
/*  90 */         StringBuffer str = new StringBuffer(msg);
/*  91 */         str.append('?');
/*  92 */         for (int i = 0; i < arguments.length; i++) {
/*  93 */           if (i > 0) {
/*  94 */             str.append('&');
/*     */           }
/*  96 */           str.append(String.valueOf(arguments[i]));
/*     */         } 
/*     */       } 
/*     */     } 
/* 100 */     return msg;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/SAXMessageFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */