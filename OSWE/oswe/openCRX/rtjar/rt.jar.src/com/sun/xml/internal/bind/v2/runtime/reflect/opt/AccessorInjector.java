/*     */ package com.sun.xml.internal.bind.v2.runtime.reflect.opt;
/*     */ 
/*     */ import com.sun.xml.internal.bind.Util;
/*     */ import com.sun.xml.internal.bind.v2.bytecode.ClassTailor;
/*     */ import java.io.InputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AccessorInjector
/*     */ {
/*  40 */   private static final Logger logger = Util.getClassLogger();
/*     */ 
/*     */   
/*  43 */   protected static final boolean noOptimize = (Util.getSystemProperty(ClassTailor.class.getName() + ".noOptimize") != null);
/*     */   
/*     */   static {
/*  46 */     if (noOptimize) {
/*  47 */       logger.info("The optimized code generation is disabled");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> prepare(Class beanClass, String templateClassName, String newClassName, String... replacements) {
/*  59 */     if (noOptimize) {
/*  60 */       return null;
/*     */     }
/*     */     try {
/*  63 */       ClassLoader cl = SecureLoader.getClassClassLoader(beanClass);
/*  64 */       if (cl == null) return null;
/*     */       
/*  66 */       Class<?> c = Injector.find(cl, newClassName);
/*  67 */       if (c == null) {
/*  68 */         byte[] image = tailor(templateClassName, newClassName, replacements);
/*  69 */         if (image == null) {
/*  70 */           return null;
/*     */         }
/*  72 */         c = Injector.inject(cl, newClassName, image);
/*  73 */         if (c == null) {
/*  74 */           Injector.find(cl, newClassName);
/*     */         }
/*     */       } 
/*  77 */       return c;
/*  78 */     } catch (SecurityException e) {
/*     */       
/*  80 */       logger.log(Level.INFO, "Unable to create an optimized TransducedAccessor ", e);
/*  81 */       return null;
/*     */     } 
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
/*     */ 
/*     */   
/*     */   private static byte[] tailor(String templateClassName, String newClassName, String... replacements) {
/*     */     InputStream resource;
/* 100 */     if (CLASS_LOADER != null) {
/* 101 */       resource = CLASS_LOADER.getResourceAsStream(templateClassName + ".class");
/*     */     } else {
/* 103 */       resource = ClassLoader.getSystemResourceAsStream(templateClassName + ".class");
/* 104 */     }  if (resource == null) {
/* 105 */       return null;
/*     */     }
/* 107 */     return ClassTailor.tailor(resource, templateClassName, newClassName, replacements);
/*     */   }
/*     */   
/* 110 */   private static final ClassLoader CLASS_LOADER = SecureLoader.getClassClassLoader(AccessorInjector.class);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/opt/AccessorInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */