/*    */ package com.sun.corba.se.spi.transport;
/*    */ 
/*    */ import com.sun.corba.se.impl.protocol.CorbaClientDelegateImpl;
/*    */ import com.sun.corba.se.impl.transport.CorbaContactInfoListImpl;
/*    */ import com.sun.corba.se.impl.transport.ReadTCPTimeoutsImpl;
/*    */ import com.sun.corba.se.spi.ior.IOR;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import com.sun.corba.se.spi.protocol.ClientDelegateFactory;
/*    */ import com.sun.corba.se.spi.protocol.CorbaClientDelegate;
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
/*    */ public abstract class TransportDefault
/*    */ {
/*    */   public static CorbaContactInfoListFactory makeCorbaContactInfoListFactory(final ORB broker) {
/* 51 */     return new CorbaContactInfoListFactory()
/*    */       {
/*    */         public CorbaContactInfoList create(IOR param1IOR) {
/* 54 */           return (CorbaContactInfoList)new CorbaContactInfoListImpl(broker, param1IOR);
/*    */         }
/*    */ 
/*    */         
/*    */         public void setORB(ORB param1ORB) {}
/*    */       };
/*    */   }
/*    */   
/*    */   public static ClientDelegateFactory makeClientDelegateFactory(final ORB broker) {
/* 63 */     return new ClientDelegateFactory() {
/*    */         public CorbaClientDelegate create(CorbaContactInfoList param1CorbaContactInfoList) {
/* 65 */           return (CorbaClientDelegate)new CorbaClientDelegateImpl(broker, param1CorbaContactInfoList);
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static IORTransformer makeIORTransformer(ORB paramORB) {
/* 74 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ReadTimeoutsFactory makeReadTimeoutsFactory() {
/* 79 */     return new ReadTimeoutsFactory()
/*    */       {
/*    */ 
/*    */         
/*    */         public ReadTimeouts create(int param1Int1, int param1Int2, int param1Int3, int param1Int4)
/*    */         {
/* 85 */           return (ReadTimeouts)new ReadTCPTimeoutsImpl(param1Int1, param1Int2, param1Int3, param1Int4);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/transport/TransportDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */