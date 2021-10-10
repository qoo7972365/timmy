/*    */ package com.sun.corba.se.impl.presentation.rmi;
/*    */ 
/*    */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*    */ import java.io.SerializablePermission;
/*    */ import org.omg.CORBA.Object;
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
/*    */ 
/*    */ 
/*    */ public abstract class StubFactoryDynamicBase
/*    */   extends StubFactoryBase
/*    */ {
/*    */   protected final ClassLoader loader;
/*    */   
/*    */   private static Void checkPermission() {
/* 43 */     SecurityManager securityManager = System.getSecurityManager();
/* 44 */     if (securityManager != null) {
/* 45 */       securityManager.checkPermission(new SerializablePermission("enableSubclassImplementation"));
/*    */     }
/*    */     
/* 48 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   private StubFactoryDynamicBase(Void paramVoid, PresentationManager.ClassData paramClassData, ClassLoader paramClassLoader) {
/* 53 */     super(paramClassData);
/*    */ 
/*    */     
/* 56 */     if (paramClassLoader == null) {
/* 57 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 58 */       if (classLoader == null)
/* 59 */         classLoader = ClassLoader.getSystemClassLoader(); 
/* 60 */       this.loader = classLoader;
/*    */     } else {
/* 62 */       this.loader = paramClassLoader;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public StubFactoryDynamicBase(PresentationManager.ClassData paramClassData, ClassLoader paramClassLoader) {
/* 69 */     this(checkPermission(), paramClassData, paramClassLoader);
/*    */   }
/*    */   
/*    */   public abstract Object makeStub();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/StubFactoryDynamicBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */