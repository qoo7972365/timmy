/*    */ package com.sun.corba.se.spi.ior;
/*    */ 
/*    */ import com.sun.corba.se.impl.ior.EncapsulationUtility;
/*    */ import org.omg.CORBA_2_3.portable.InputStream;
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
/*    */ public abstract class EncapsulationFactoryBase
/*    */   implements IdentifiableFactory
/*    */ {
/*    */   private int id;
/*    */   
/*    */   public int getId() {
/* 37 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public EncapsulationFactoryBase(int paramInt) {
/* 42 */     this.id = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public final Identifiable create(InputStream paramInputStream) {
/* 47 */     InputStream inputStream = EncapsulationUtility.getEncapsulationStream(paramInputStream);
/* 48 */     return readContents(inputStream);
/*    */   }
/*    */   
/*    */   protected abstract Identifiable readContents(InputStream paramInputStream);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/EncapsulationFactoryBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */