/*    */ package com.sun.xml.internal.ws.client.dispatch;
/*    */ 
/*    */ import com.sun.istack.internal.Nullable;
/*    */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*    */ import com.sun.xml.internal.ws.api.client.ThrowableInPacketCompletionFeature;
/*    */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.pipe.Fiber;
/*    */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*    */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*    */ import com.sun.xml.internal.ws.client.WSServiceDelegate;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.Service;
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
/*    */ public class PacketDispatch
/*    */   extends DispatchImpl<Packet>
/*    */ {
/*    */   private final boolean isDeliverThrowableInPacket;
/*    */   
/*    */   @Deprecated
/*    */   public PacketDispatch(QName port, WSServiceDelegate owner, Tube pipe, BindingImpl binding, @Nullable WSEndpointReference epr) {
/* 52 */     super(port, Service.Mode.MESSAGE, owner, pipe, binding, epr);
/* 53 */     this.isDeliverThrowableInPacket = calculateIsDeliverThrowableInPacket(binding);
/*    */   }
/*    */ 
/*    */   
/*    */   public PacketDispatch(WSPortInfo portInfo, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/* 58 */     this(portInfo, pipe, binding, epr, true);
/*    */   }
/*    */   
/*    */   public PacketDispatch(WSPortInfo portInfo, Tube pipe, BindingImpl binding, WSEndpointReference epr, boolean allowFaultResponseMsg) {
/* 62 */     super(portInfo, Service.Mode.MESSAGE, pipe, binding, epr, allowFaultResponseMsg);
/* 63 */     this.isDeliverThrowableInPacket = calculateIsDeliverThrowableInPacket(binding);
/*    */   }
/*    */   
/*    */   public PacketDispatch(WSPortInfo portInfo, BindingImpl binding, WSEndpointReference epr) {
/* 67 */     super(portInfo, Service.Mode.MESSAGE, binding, epr, true);
/* 68 */     this.isDeliverThrowableInPacket = calculateIsDeliverThrowableInPacket(binding);
/*    */   }
/*    */   
/*    */   private boolean calculateIsDeliverThrowableInPacket(BindingImpl binding) {
/* 72 */     return binding.isFeatureEnabled(ThrowableInPacketCompletionFeature.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configureFiber(Fiber fiber) {
/* 77 */     fiber.setDeliverThrowableInPacket(this.isDeliverThrowableInPacket);
/*    */   }
/*    */ 
/*    */   
/*    */   Packet toReturnValue(Packet response) {
/* 82 */     return response;
/*    */   }
/*    */ 
/*    */   
/*    */   Packet createPacket(Packet request) {
/* 87 */     return request;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/dispatch/PacketDispatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */