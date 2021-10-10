/*    */ package com.sun.corba.se.impl.protocol;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.IOR;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import javax.rmi.CORBA.Tie;
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CORBA.portable.ServantObject;
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
/*    */ public class JIDLLocalCRDImpl
/*    */   extends LocalClientRequestDispatcherBase
/*    */ {
/*    */   protected ServantObject servant;
/*    */   
/*    */   public JIDLLocalCRDImpl(ORB paramORB, int paramInt, IOR paramIOR) {
/* 47 */     super(paramORB, paramInt, paramIOR);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ServantObject servant_preinvoke(Object paramObject, String paramString, Class paramClass) {
/* 56 */     if (!checkForCompatibleServant(this.servant, paramClass)) {
/* 57 */       return null;
/*    */     }
/* 59 */     return this.servant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void servant_postinvoke(Object paramObject, ServantObject paramServantObject) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setServant(Object paramObject) {
/* 71 */     if (paramObject != null && paramObject instanceof Tie) {
/* 72 */       this.servant = new ServantObject();
/* 73 */       this.servant.servant = ((Tie)paramObject).getTarget();
/*    */     } else {
/* 75 */       this.servant = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void unexport() {
/* 86 */     this.servant = null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/JIDLLocalCRDImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */