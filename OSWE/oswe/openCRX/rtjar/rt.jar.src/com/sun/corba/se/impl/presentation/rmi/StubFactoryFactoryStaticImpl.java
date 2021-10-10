/*     */ package com.sun.corba.se.impl.presentation.rmi;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.util.PackagePrefixChecker;
/*     */ import com.sun.corba.se.impl.util.Utility;
/*     */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*     */ import javax.rmi.CORBA.Tie;
/*     */ import javax.rmi.CORBA.Util;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StubFactoryFactoryStaticImpl
/*     */   extends StubFactoryFactoryBase
/*     */ {
/*  45 */   private ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.presentation");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PresentationManager.StubFactory createStubFactory(String paramString1, boolean paramBoolean, String paramString2, Class paramClass, ClassLoader paramClassLoader) {
/*  52 */     String str1 = null;
/*     */     
/*  54 */     if (paramBoolean) {
/*  55 */       str1 = Utility.idlStubName(paramString1);
/*     */     } else {
/*  57 */       str1 = Utility.stubNameForCompiler(paramString1);
/*     */     } 
/*     */ 
/*     */     
/*  61 */     ClassLoader classLoader = (paramClass == null) ? paramClassLoader : paramClass.getClassLoader();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     String str2 = str1;
/*  71 */     String str3 = str1;
/*     */     
/*  73 */     if (PackagePrefixChecker.hasOffendingPrefix(str1)) {
/*  74 */       str2 = PackagePrefixChecker.packagePrefix() + str1;
/*     */     } else {
/*  76 */       str3 = PackagePrefixChecker.packagePrefix() + str1;
/*     */     } 
/*  78 */     Class<?> clazz = null;
/*     */     
/*     */     try {
/*  81 */       clazz = Util.loadClass(str2, paramString2, classLoader);
/*     */     }
/*  83 */     catch (ClassNotFoundException classNotFoundException) {
/*     */       
/*  85 */       this.wrapper.classNotFound1(CompletionStatus.COMPLETED_MAYBE, classNotFoundException, str2);
/*     */       
/*     */       try {
/*  88 */         clazz = Util.loadClass(str3, paramString2, classLoader);
/*     */       }
/*  90 */       catch (ClassNotFoundException classNotFoundException1) {
/*  91 */         throw this.wrapper.classNotFound2(CompletionStatus.COMPLETED_MAYBE, classNotFoundException1, str3);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     if (clazz == null || (paramClass != null && 
/* 101 */       !paramClass.isAssignableFrom(clazz))) {
/*     */       try {
/* 103 */         ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();
/* 104 */         if (classLoader1 == null) {
/* 105 */           classLoader1 = ClassLoader.getSystemClassLoader();
/*     */         }
/* 107 */         clazz = classLoader1.loadClass(paramString1);
/* 108 */       } catch (Exception exception) {
/*     */         
/* 110 */         IllegalStateException illegalStateException = new IllegalStateException("Could not load class " + str1);
/*     */         
/* 112 */         illegalStateException.initCause(exception);
/* 113 */         throw illegalStateException;
/*     */       } 
/*     */     }
/*     */     
/* 117 */     return new StubFactoryStaticImpl(clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   public Tie getTie(Class paramClass) {
/* 122 */     Class<Tie> clazz = null;
/* 123 */     String str = Utility.tieName(paramClass.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 130 */       clazz = Utility.loadClassForClass(str, Util.getCodebase(paramClass), null, paramClass, paramClass
/* 131 */           .getClassLoader());
/* 132 */       return clazz.newInstance();
/* 133 */     } catch (Exception exception) {
/* 134 */       clazz = Utility.loadClassForClass(
/* 135 */           PackagePrefixChecker.packagePrefix() + str, 
/* 136 */           Util.getCodebase(paramClass), null, paramClass, paramClass.getClassLoader());
/* 137 */       return clazz.newInstance();
/*     */     }
/* 139 */     catch (Exception exception) {
/* 140 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean createsDynamicStubs() {
/* 147 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/StubFactoryFactoryStaticImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */