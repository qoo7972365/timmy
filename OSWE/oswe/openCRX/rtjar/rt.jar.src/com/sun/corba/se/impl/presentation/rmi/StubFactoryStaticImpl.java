/*    */ package com.sun.corba.se.impl.presentation.rmi;
/*    */ 
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
/*    */ public class StubFactoryStaticImpl
/*    */   extends StubFactoryBase
/*    */ {
/*    */   private Class stubClass;
/*    */   
/*    */   public StubFactoryStaticImpl(Class paramClass) {
/* 38 */     super(null);
/* 39 */     this.stubClass = paramClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object makeStub() {
/* 44 */     Object object = null;
/*    */     try {
/* 46 */       object = this.stubClass.newInstance();
/* 47 */     } catch (InstantiationException instantiationException) {
/* 48 */       throw new RuntimeException(instantiationException);
/* 49 */     } catch (IllegalAccessException illegalAccessException) {
/* 50 */       throw new RuntimeException(illegalAccessException);
/*    */     } 
/* 52 */     return object;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/StubFactoryStaticImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */