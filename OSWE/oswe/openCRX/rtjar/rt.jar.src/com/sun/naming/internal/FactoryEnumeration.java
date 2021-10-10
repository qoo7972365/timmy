/*     */ package com.sun.naming.internal;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.naming.NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FactoryEnumeration
/*     */ {
/*     */   private List<NamedWeakReference<Object>> factories;
/*  42 */   private int posn = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassLoader loader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FactoryEnumeration(List<NamedWeakReference<Object>> paramList, ClassLoader paramClassLoader) {
/*  68 */     this.factories = paramList;
/*  69 */     this.loader = paramClassLoader;
/*     */   }
/*     */   
/*     */   public Object next() throws NamingException {
/*  73 */     synchronized (this.factories) {
/*     */       
/*  75 */       NamedWeakReference<Object> namedWeakReference = (NamedWeakReference)this.factories.get(this.posn++);
/*  76 */       Class<?> clazz = (Class<?>)namedWeakReference.get();
/*  77 */       if (clazz != null && !(clazz instanceof Class)) {
/*  78 */         return clazz;
/*     */       }
/*     */       
/*  81 */       String str = namedWeakReference.getName();
/*     */       
/*     */       try {
/*  84 */         if (clazz == null) {
/*  85 */           Class<?> clazz1 = Class.forName(str, true, this.loader);
/*  86 */           clazz = clazz1;
/*     */         } 
/*     */         
/*  89 */         clazz = (Class<?>)clazz.newInstance();
/*  90 */         namedWeakReference = new NamedWeakReference<>(clazz, str);
/*  91 */         this.factories.set(this.posn - 1, namedWeakReference);
/*  92 */         return clazz;
/*  93 */       } catch (ClassNotFoundException classNotFoundException) {
/*  94 */         NamingException namingException = new NamingException("No longer able to load " + str);
/*     */         
/*  96 */         namingException.setRootCause(classNotFoundException);
/*  97 */         throw namingException;
/*  98 */       } catch (InstantiationException instantiationException) {
/*  99 */         NamingException namingException = new NamingException("Cannot instantiate " + clazz);
/*     */         
/* 101 */         namingException.setRootCause(instantiationException);
/* 102 */         throw namingException;
/* 103 */       } catch (IllegalAccessException illegalAccessException) {
/* 104 */         NamingException namingException = new NamingException("Cannot access " + clazz);
/* 105 */         namingException.setRootCause(illegalAccessException);
/* 106 */         throw namingException;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasMore() {
/* 112 */     synchronized (this.factories) {
/* 113 */       return (this.posn < this.factories.size());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/naming/internal/FactoryEnumeration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */