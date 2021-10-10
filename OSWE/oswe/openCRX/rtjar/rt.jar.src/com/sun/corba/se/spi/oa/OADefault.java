/*    */ package com.sun.corba.se.spi.oa;
/*    */ 
/*    */ import com.sun.corba.se.impl.oa.poa.POAFactory;
/*    */ import com.sun.corba.se.impl.oa.toa.TOAFactory;
/*    */ import com.sun.corba.se.spi.orb.ORB;
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
/*    */ public class OADefault
/*    */ {
/*    */   public static ObjectAdapterFactory makePOAFactory(ORB paramORB) {
/* 39 */     POAFactory pOAFactory = new POAFactory();
/* 40 */     pOAFactory.init(paramORB);
/* 41 */     return (ObjectAdapterFactory)pOAFactory;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ObjectAdapterFactory makeTOAFactory(ORB paramORB) {
/* 46 */     TOAFactory tOAFactory = new TOAFactory();
/* 47 */     tOAFactory.init(paramORB);
/* 48 */     return (ObjectAdapterFactory)tOAFactory;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/oa/OADefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */