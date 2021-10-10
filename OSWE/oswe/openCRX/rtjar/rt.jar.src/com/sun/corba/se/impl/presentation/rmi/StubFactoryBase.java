/*    */ package com.sun.corba.se.impl.presentation.rmi;
/*    */ 
/*    */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*    */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
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
/*    */ 
/*    */ 
/*    */ public abstract class StubFactoryBase
/*    */   implements PresentationManager.StubFactory
/*    */ {
/* 42 */   private String[] typeIds = null;
/*    */   
/*    */   protected final PresentationManager.ClassData classData;
/*    */ 
/*    */   
/*    */   protected StubFactoryBase(PresentationManager.ClassData paramClassData) {
/* 48 */     this.classData = paramClassData;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized String[] getTypeIds() {
/* 53 */     if (this.typeIds == null) {
/* 54 */       if (this.classData == null) {
/* 55 */         Object object = makeStub();
/* 56 */         this.typeIds = StubAdapter.getTypeIds(object);
/*    */       } else {
/* 58 */         this.typeIds = this.classData.getTypeIds();
/*    */       } 
/*    */     }
/*    */     
/* 62 */     return this.typeIds;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/StubFactoryBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */