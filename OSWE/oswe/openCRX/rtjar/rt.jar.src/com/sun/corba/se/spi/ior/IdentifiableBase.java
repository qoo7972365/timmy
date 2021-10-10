/*    */ package com.sun.corba.se.spi.ior;
/*    */ 
/*    */ import com.sun.corba.se.impl.ior.EncapsulationUtility;
/*    */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class IdentifiableBase
/*    */   implements Identifiable, WriteContents
/*    */ {
/*    */   public final void write(OutputStream paramOutputStream) {
/* 51 */     EncapsulationUtility.writeEncapsulation(this, paramOutputStream);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/IdentifiableBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */