/*     */ package com.sun.rowset;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Locale;
/*     */ import java.util.PropertyResourceBundle;
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
/*     */ public class JdbcRowSetResourceBundle
/*     */   implements Serializable
/*     */ {
/*     */   private static String fileName;
/*     */   private transient PropertyResourceBundle propResBundle;
/*     */   private static volatile JdbcRowSetResourceBundle jpResBundle;
/*     */   private static final String PROPERTIES = "properties";
/*     */   private static final String UNDERSCORE = "_";
/*     */   private static final String DOT = ".";
/*     */   private static final String SLASH = "/";
/*     */   private static final String PATH = "com/sun/rowset/RowSetResourceBundle";
/*     */   static final long serialVersionUID = 436199386225359954L;
/*     */   
/*     */   private JdbcRowSetResourceBundle() throws IOException {
/* 102 */     Locale locale = Locale.getDefault();
/*     */ 
/*     */     
/* 105 */     this.propResBundle = (PropertyResourceBundle)ResourceBundle.getBundle("com/sun/rowset/RowSetResourceBundle", locale, 
/* 106 */         Thread.currentThread().getContextClassLoader());
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
/*     */   public static JdbcRowSetResourceBundle getJdbcRowSetResourceBundle() throws IOException {
/* 121 */     if (jpResBundle == null) {
/* 122 */       synchronized (JdbcRowSetResourceBundle.class) {
/* 123 */         if (jpResBundle == null) {
/* 124 */           jpResBundle = new JdbcRowSetResourceBundle();
/*     */         }
/*     */       } 
/*     */     }
/* 128 */     return jpResBundle;
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
/*     */   public Enumeration getKeys() {
/* 140 */     return this.propResBundle.getKeys();
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
/*     */   public Object handleGetObject(String paramString) {
/* 153 */     return this.propResBundle.handleGetObject(paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/JdbcRowSetResourceBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */