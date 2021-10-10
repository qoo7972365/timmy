/*     */ package com.sun.xml.internal.fastinfoset;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Enumeration;
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
/*     */ public abstract class AbstractResourceBundle
/*     */   extends ResourceBundle
/*     */ {
/*     */   public static final String LOCALE = "com.sun.xml.internal.fastinfoset.locale";
/*     */   
/*     */   public String getString(String key, Object[] args) {
/*  61 */     String pattern = getBundle().getString(key);
/*  62 */     return MessageFormat.format(pattern, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale parseLocale(String localeString) {
/*  73 */     Locale locale = null;
/*  74 */     if (localeString == null) {
/*  75 */       locale = Locale.getDefault();
/*     */     } else {
/*     */       try {
/*  78 */         String[] args = localeString.split("_");
/*  79 */         if (args.length == 1) {
/*  80 */           locale = new Locale(args[0]);
/*  81 */         } else if (args.length == 2) {
/*  82 */           locale = new Locale(args[0], args[1]);
/*  83 */         } else if (args.length == 3) {
/*  84 */           locale = new Locale(args[0], args[1], args[2]);
/*     */         } 
/*  86 */       } catch (Throwable t) {
/*  87 */         locale = Locale.getDefault();
/*     */       } 
/*     */     } 
/*  90 */     return locale;
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
/*     */ 
/*     */   
/*     */   public abstract ResourceBundle getBundle();
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
/*     */   protected Object handleGetObject(String key) {
/* 117 */     return getBundle().getObject(key);
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
/*     */   public final Enumeration getKeys() {
/* 130 */     return getBundle().getKeys();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/AbstractResourceBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */