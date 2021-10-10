/*     */ package com.sun.tracing;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.HashSet;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.tracing.MultiplexProviderFactory;
/*     */ import sun.tracing.NullProviderFactory;
/*     */ import sun.tracing.PrintStreamProviderFactory;
/*     */ import sun.tracing.dtrace.DTraceProviderFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ProviderFactory
/*     */ {
/*     */   public abstract <T extends Provider> T createProvider(Class<T> paramClass);
/*     */   
/*     */   public static ProviderFactory getDefaultFactory() {
/*  79 */     HashSet<DTraceProviderFactory> hashSet = new HashSet();
/*     */ 
/*     */     
/*  82 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("com.sun.tracing.dtrace"));
/*     */ 
/*     */     
/*  85 */     if ((str == null || !str.equals("disable")) && 
/*  86 */       DTraceProviderFactory.isSupported()) {
/*  87 */       hashSet.add(new DTraceProviderFactory());
/*     */     }
/*     */ 
/*     */     
/*  91 */     str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.tracing.stream"));
/*     */     
/*  93 */     if (str != null) {
/*  94 */       for (String str1 : str.split(",")) {
/*  95 */         PrintStream printStream = getPrintStreamFromSpec(str1);
/*  96 */         if (printStream != null) {
/*  97 */           hashSet.add(new PrintStreamProviderFactory(printStream));
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 104 */     if (hashSet.size() == 0)
/* 105 */       return (ProviderFactory)new NullProviderFactory(); 
/* 106 */     if (hashSet.size() == 1) {
/* 107 */       return ((ProviderFactory[])hashSet.toArray((T[])new ProviderFactory[1]))[0];
/*     */     }
/* 109 */     return (ProviderFactory)new MultiplexProviderFactory(hashSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PrintStream getPrintStreamFromSpec(final String spec) {
/*     */     try {
/* 119 */       final int fieldpos = spec.lastIndexOf('.');
/* 120 */       final Class<?> cls = Class.forName(spec.substring(0, i));
/*     */       
/* 122 */       Field field = AccessController.<Field>doPrivileged(new PrivilegedExceptionAction<Field>() {
/*     */             public Field run() throws NoSuchFieldException {
/* 124 */               return cls.getField(spec.substring(fieldpos + 1));
/*     */             }
/*     */           });
/*     */       
/* 128 */       return (PrintStream)field.get(null);
/* 129 */     } catch (ClassNotFoundException classNotFoundException) {
/* 130 */       throw new AssertionError(classNotFoundException);
/* 131 */     } catch (IllegalAccessException illegalAccessException) {
/* 132 */       throw new AssertionError(illegalAccessException);
/* 133 */     } catch (PrivilegedActionException privilegedActionException) {
/* 134 */       throw new AssertionError(privilegedActionException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/tracing/ProviderFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */