/*     */ package com.sun.corba.se.spi.orb;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ParserImplBase
/*     */ {
/*     */   private ORBUtilSystemException wrapper;
/*     */   
/*     */   protected void complete() {}
/*     */   
/*     */   public ParserImplBase() {
/*  65 */     this.wrapper = ORBUtilSystemException.get("orb.lifecycle");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(DataCollector paramDataCollector) {
/*  71 */     PropertyParser propertyParser = makeParser();
/*  72 */     paramDataCollector.setParser(propertyParser);
/*  73 */     Properties properties = paramDataCollector.getProperties();
/*  74 */     Map map = propertyParser.parse(properties);
/*  75 */     setFields(map);
/*     */   }
/*     */ 
/*     */   
/*     */   private Field getAnyField(String paramString) {
/*  80 */     Field field = null;
/*     */     
/*     */     try {
/*  83 */       Class<?> clazz = getClass();
/*  84 */       field = clazz.getDeclaredField(paramString);
/*  85 */       while (field == null) {
/*  86 */         clazz = clazz.getSuperclass();
/*  87 */         if (clazz == null) {
/*     */           break;
/*     */         }
/*  90 */         field = clazz.getDeclaredField(paramString);
/*     */       } 
/*  92 */     } catch (Exception exception) {
/*  93 */       throw this.wrapper.fieldNotFound(exception, paramString);
/*     */     } 
/*     */     
/*  96 */     if (field == null) {
/*  97 */       throw this.wrapper.fieldNotFound(paramString);
/*     */     }
/*  99 */     return field;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setFields(Map paramMap) {
/* 104 */     Set set = paramMap.entrySet();
/* 105 */     Iterator<Map.Entry> iterator = set.iterator();
/* 106 */     while (iterator.hasNext()) {
/* 107 */       Map.Entry entry = iterator.next();
/* 108 */       final String name = (String)entry.getKey();
/* 109 */       final Object value = entry.getValue();
/*     */       
/*     */       try {
/* 112 */         AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */             {
/*     */               
/*     */               public Object run() throws IllegalAccessException, IllegalArgumentException
/*     */               {
/* 117 */                 Field field = ParserImplBase.this.getAnyField(name);
/* 118 */                 field.setAccessible(true);
/* 119 */                 field.set(ParserImplBase.this, value);
/* 120 */                 return null;
/*     */               }
/*     */             });
/*     */       }
/* 124 */       catch (PrivilegedActionException privilegedActionException) {
/*     */ 
/*     */         
/* 127 */         throw this.wrapper.errorSettingField(privilegedActionException.getCause(), str, object
/* 128 */             .toString());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 134 */     complete();
/*     */   }
/*     */   
/*     */   protected abstract PropertyParser makeParser();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orb/ParserImplBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */