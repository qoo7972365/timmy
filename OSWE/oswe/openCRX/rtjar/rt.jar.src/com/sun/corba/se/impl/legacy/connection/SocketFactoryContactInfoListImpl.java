/*    */ package com.sun.corba.se.impl.legacy.connection;
/*    */ 
/*    */ import com.sun.corba.se.impl.transport.CorbaContactInfoListImpl;
/*    */ import com.sun.corba.se.spi.ior.IOR;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*    */ import java.util.Iterator;
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
/*    */ public class SocketFactoryContactInfoListImpl
/*    */   extends CorbaContactInfoListImpl
/*    */ {
/*    */   public SocketFactoryContactInfoListImpl(ORB paramORB) {
/* 45 */     super(paramORB);
/*    */   }
/*    */ 
/*    */   
/*    */   public SocketFactoryContactInfoListImpl(ORB paramORB, IOR paramIOR) {
/* 50 */     super(paramORB, paramIOR);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator iterator() {
/* 60 */     return (Iterator)new SocketFactoryContactInfoListIteratorImpl(this.orb, (CorbaContactInfoList)this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/legacy/connection/SocketFactoryContactInfoListImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */