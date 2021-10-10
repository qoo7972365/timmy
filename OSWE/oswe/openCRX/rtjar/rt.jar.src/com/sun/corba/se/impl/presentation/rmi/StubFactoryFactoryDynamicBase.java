/*    */ package com.sun.corba.se.impl.presentation.rmi;
/*    */ 
/*    */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*    */ import java.rmi.Remote;
/*    */ import javax.rmi.CORBA.Tie;
/*    */ import javax.rmi.CORBA.Util;
/*    */ import org.omg.CORBA.CompletionStatus;
/*    */ import org.omg.CORBA.portable.IDLEntity;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class StubFactoryFactoryDynamicBase
/*    */   extends StubFactoryFactoryBase
/*    */ {
/* 53 */   protected final ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.presentation");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PresentationManager.StubFactory createStubFactory(String paramString1, boolean paramBoolean, String paramString2, Class paramClass, ClassLoader paramClassLoader) {
/* 61 */     Class<?> clazz = null;
/*    */     
/*    */     try {
/* 64 */       clazz = Util.loadClass(paramString1, paramString2, paramClassLoader);
/* 65 */     } catch (ClassNotFoundException classNotFoundException) {
/* 66 */       throw this.wrapper.classNotFound3(CompletionStatus.COMPLETED_MAYBE, classNotFoundException, paramString1);
/*    */     } 
/*    */ 
/*    */     
/* 70 */     PresentationManager presentationManager = ORB.getPresentationManager();
/*    */     
/* 72 */     if (IDLEntity.class.isAssignableFrom(clazz) && 
/* 73 */       !Remote.class.isAssignableFrom(clazz)) {
/*    */ 
/*    */       
/* 76 */       PresentationManager.StubFactoryFactory stubFactoryFactory = presentationManager.getStubFactoryFactory(false);
/*    */       
/* 78 */       return stubFactoryFactory.createStubFactory(paramString1, true, paramString2, paramClass, paramClassLoader);
/*    */     } 
/*    */ 
/*    */     
/* 82 */     PresentationManager.ClassData classData = presentationManager.getClassData(clazz);
/* 83 */     return makeDynamicStubFactory(presentationManager, classData, paramClassLoader);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract PresentationManager.StubFactory makeDynamicStubFactory(PresentationManager paramPresentationManager, PresentationManager.ClassData paramClassData, ClassLoader paramClassLoader);
/*    */ 
/*    */ 
/*    */   
/*    */   public Tie getTie(Class paramClass) {
/* 93 */     PresentationManager presentationManager = ORB.getPresentationManager();
/* 94 */     return new ReflectiveTie(presentationManager, this.wrapper);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean createsDynamicStubs() {
/* 99 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/StubFactoryFactoryDynamicBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */