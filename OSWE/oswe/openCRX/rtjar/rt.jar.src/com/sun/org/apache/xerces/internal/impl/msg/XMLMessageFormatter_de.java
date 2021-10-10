/*     */ package com.sun.org.apache.xerces.internal.impl.msg;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.MessageFormatter;
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
/*     */ public class XMLMessageFormatter_de
/*     */   implements MessageFormatter
/*     */ {
/*     */   public static final String XML_DOMAIN = "http://www.w3.org/TR/1998/REC-xml-19980210";
/*     */   public static final String XMLNS_DOMAIN = "http://www.w3.org/TR/1999/REC-xml-names-19990114";
/*  49 */   private Locale fLocale = null;
/*  50 */   private ResourceBundle fResourceBundle = null;
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
/*     */   public String formatMessage(Locale locale, String key, Object[] arguments) throws MissingResourceException {
/*     */     String msg;
/*  74 */     if (this.fResourceBundle == null || locale != this.fLocale) {
/*  75 */       if (locale != null) {
/*  76 */         this.fResourceBundle = SecuritySupport.getResourceBundle("com.sun.org.apache.xerces.internal.impl.msg.XMLMessages", locale);
/*     */         
/*  78 */         this.fLocale = locale;
/*     */       } 
/*  80 */       if (this.fResourceBundle == null) {
/*  81 */         this.fResourceBundle = SecuritySupport.getResourceBundle("com.sun.org.apache.xerces.internal.impl.msg.XMLMessages");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  87 */       msg = this.fResourceBundle.getString(key);
/*  88 */       if (arguments != null) {
/*     */         try {
/*  90 */           msg = MessageFormat.format(msg, arguments);
/*     */         }
/*  92 */         catch (Exception e) {
/*  93 */           msg = this.fResourceBundle.getString("FormatFailed");
/*  94 */           msg = msg + " " + this.fResourceBundle.getString(key);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     }
/* 100 */     catch (MissingResourceException e) {
/* 101 */       msg = this.fResourceBundle.getString("BadMessageKey");
/* 102 */       throw new MissingResourceException(key, msg, key);
/*     */     } 
/*     */ 
/*     */     
/* 106 */     if (msg == null) {
/* 107 */       msg = key;
/* 108 */       if (arguments.length > 0) {
/* 109 */         StringBuffer str = new StringBuffer(msg);
/* 110 */         str.append('?');
/* 111 */         for (int i = 0; i < arguments.length; i++) {
/* 112 */           if (i > 0) {
/* 113 */             str.append('&');
/*     */           }
/* 115 */           str.append(String.valueOf(arguments[i]));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     return msg;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/msg/XMLMessageFormatter_de.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */