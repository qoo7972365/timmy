/*    */ package com.sun.jmx.remote.util;
/*    */ 
/*    */ import javax.management.loading.ClassLoaderRepository;
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
/*    */ public class ClassLoaderWithRepository
/*    */   extends ClassLoader
/*    */ {
/*    */   private ClassLoaderRepository repository;
/*    */   private ClassLoader cl2;
/*    */   
/*    */   public ClassLoaderWithRepository(ClassLoaderRepository paramClassLoaderRepository, ClassLoader paramClassLoader) {
/* 34 */     if (paramClassLoaderRepository == null) throw new IllegalArgumentException("Null ClassLoaderRepository object.");
/*    */ 
/*    */     
/* 37 */     this.repository = paramClassLoaderRepository;
/* 38 */     this.cl2 = paramClassLoader;
/*    */   }
/*    */   
/*    */   protected Class<?> findClass(String paramString) throws ClassNotFoundException {
/*    */     Class<?> clazz;
/*    */     try {
/* 44 */       clazz = this.repository.loadClass(paramString);
/* 45 */     } catch (ClassNotFoundException classNotFoundException) {
/* 46 */       if (this.cl2 != null) {
/* 47 */         return this.cl2.loadClass(paramString);
/*    */       }
/* 49 */       throw classNotFoundException;
/*    */     } 
/*    */ 
/*    */     
/* 53 */     if (!clazz.getName().equals(paramString)) {
/* 54 */       if (this.cl2 != null) {
/* 55 */         return this.cl2.loadClass(paramString);
/*    */       }
/* 57 */       throw new ClassNotFoundException(paramString);
/*    */     } 
/*    */     
/* 60 */     return clazz;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/util/ClassLoaderWithRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */