/*    */ package com.sun.corba.se.spi.protocol;
/*    */ 
/*    */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*    */ import com.sun.corba.se.spi.ior.IOR;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import org.omg.CORBA.BAD_PARAM;
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
/*    */ public class ForwardException
/*    */   extends RuntimeException
/*    */ {
/*    */   private ORB orb;
/*    */   private Object obj;
/*    */   private IOR ior;
/*    */   
/*    */   public ForwardException(ORB paramORB, IOR paramIOR) {
/* 47 */     this.orb = paramORB;
/* 48 */     this.obj = null;
/* 49 */     this.ior = paramIOR;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ForwardException(ORB paramORB, Object paramObject) {
/* 59 */     if (paramObject instanceof org.omg.CORBA.LocalObject) {
/* 60 */       throw new BAD_PARAM();
/*    */     }
/* 62 */     this.orb = paramORB;
/* 63 */     this.obj = paramObject;
/* 64 */     this.ior = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Object getObject() {
/* 69 */     if (this.obj == null) {
/* 70 */       this.obj = ORBUtility.makeObjectReference(this.ior);
/*    */     }
/*    */     
/* 73 */     return this.obj;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized IOR getIOR() {
/* 78 */     if (this.ior == null) {
/* 79 */       this.ior = ORBUtility.getIOR(this.obj);
/*    */     }
/*    */     
/* 82 */     return this.ior;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/protocol/ForwardException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */