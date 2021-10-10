/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ import java.util.Properties;
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
/*     */ final class PropUtil
/*     */ {
/*     */   public static boolean getBooleanSystemProperty(String name, boolean def) {
/*     */     try {
/*  48 */       return getBoolean(getProp(System.getProperties(), name), def);
/*  49 */     } catch (SecurityException securityException) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  58 */         String value = System.getProperty(name);
/*  59 */         if (value == null) {
/*  60 */           return def;
/*     */         }
/*  62 */         if (def) {
/*  63 */           return !value.equalsIgnoreCase("false");
/*     */         }
/*  65 */         return value.equalsIgnoreCase("true");
/*     */       }
/*  67 */       catch (SecurityException sex) {
/*  68 */         return def;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object getProp(Properties props, String name) {
/*  78 */     Object val = props.get(name);
/*  79 */     if (val != null) {
/*  80 */       return val;
/*     */     }
/*  82 */     return props.getProperty(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean getBoolean(Object value, boolean def) {
/*  91 */     if (value == null) {
/*  92 */       return def;
/*     */     }
/*  94 */     if (value instanceof String) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  99 */       if (def) {
/* 100 */         return !((String)value).equalsIgnoreCase("false");
/*     */       }
/* 102 */       return ((String)value).equalsIgnoreCase("true");
/*     */     } 
/*     */     
/* 105 */     if (value instanceof Boolean) {
/* 106 */       return ((Boolean)value).booleanValue();
/*     */     }
/* 108 */     return def;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/PropUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */