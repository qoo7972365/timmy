/*    */ package com.sun.xml.internal.fastinfoset;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
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
/*    */ public class CommonResourceBundle
/*    */   extends AbstractResourceBundle
/*    */ {
/*    */   public static final String BASE_NAME = "com.sun.xml.internal.fastinfoset.resources.ResourceBundle";
/* 38 */   private static volatile CommonResourceBundle instance = null;
/* 39 */   private static Locale locale = null;
/* 40 */   private ResourceBundle bundle = null;
/*    */ 
/*    */   
/*    */   protected CommonResourceBundle() {
/* 44 */     this.bundle = ResourceBundle.getBundle("com.sun.xml.internal.fastinfoset.resources.ResourceBundle");
/*    */   }
/*    */ 
/*    */   
/*    */   protected CommonResourceBundle(Locale locale) {
/* 49 */     this.bundle = ResourceBundle.getBundle("com.sun.xml.internal.fastinfoset.resources.ResourceBundle", locale);
/*    */   }
/*    */   
/*    */   public static CommonResourceBundle getInstance() {
/* 53 */     if (instance == null) {
/* 54 */       synchronized (CommonResourceBundle.class) {
/* 55 */         instance = new CommonResourceBundle();
/*    */ 
/*    */ 
/*    */         
/* 59 */         locale = parseLocale(null);
/*    */       } 
/*    */     }
/*    */     
/* 63 */     return instance;
/*    */   }
/*    */   
/*    */   public static CommonResourceBundle getInstance(Locale locale) {
/* 67 */     if (instance == null) {
/* 68 */       synchronized (CommonResourceBundle.class) {
/* 69 */         instance = new CommonResourceBundle(locale);
/*    */       } 
/*    */     } else {
/* 72 */       synchronized (CommonResourceBundle.class) {
/* 73 */         if (CommonResourceBundle.locale != locale) {
/* 74 */           instance = new CommonResourceBundle(locale);
/*    */         }
/*    */       } 
/*    */     } 
/* 78 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceBundle getBundle() {
/* 83 */     return this.bundle;
/*    */   }
/*    */   public ResourceBundle getBundle(Locale locale) {
/* 86 */     return ResourceBundle.getBundle("com.sun.xml.internal.fastinfoset.resources.ResourceBundle", locale);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/CommonResourceBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */