/*    */ package com.sun.corba.se.impl.protocol;
/*    */ 
/*    */ import com.sun.corba.se.spi.protocol.LocalClientRequestDispatcher;
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
/*    */ public class NotLocalLocalCRDImpl
/*    */   implements LocalClientRequestDispatcher
/*    */ {
/*    */   public boolean useLocalInvocation(Object paramObject) {
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean is_local(Object paramObject) {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ServantObject servant_preinvoke(Object paramObject, String paramString, Class paramClass) {
/* 56 */     return null;
/*    */   }
/*    */   
/*    */   public void servant_postinvoke(Object paramObject, ServantObject paramServantObject) {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/NotLocalLocalCRDImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */