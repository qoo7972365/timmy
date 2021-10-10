/*    */ package com.sun.xml.internal.ws.protocol.soap;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*    */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*    */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*    */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*    */ import com.sun.xml.internal.ws.client.HandlerConfiguration;
/*    */ import java.util.Set;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public class ClientMUTube
/*    */   extends MUTube
/*    */ {
/*    */   public ClientMUTube(WSBinding binding, Tube next) {
/* 48 */     super(binding, next);
/*    */   }
/*    */   
/*    */   protected ClientMUTube(ClientMUTube that, TubeCloner cloner) {
/* 52 */     super(that, cloner);
/*    */   }
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
/*    */   @NotNull
/*    */   public NextAction processResponse(Packet response) {
/* 66 */     if (response.getMessage() == null) {
/* 67 */       return super.processResponse(response);
/*    */     }
/* 69 */     HandlerConfiguration handlerConfig = response.handlerConfig;
/*    */     
/* 71 */     if (handlerConfig == null)
/*    */     {
/*    */       
/* 74 */       handlerConfig = this.binding.getHandlerConfig();
/*    */     }
/* 76 */     Set<QName> misUnderstoodHeaders = getMisUnderstoodHeaders(response.getMessage().getHeaders(), handlerConfig.getRoles(), this.binding.getKnownHeaders());
/* 77 */     if (misUnderstoodHeaders == null || misUnderstoodHeaders.isEmpty()) {
/* 78 */       return super.processResponse(response);
/*    */     }
/* 80 */     throw createMUSOAPFaultException(misUnderstoodHeaders);
/*    */   }
/*    */   
/*    */   public ClientMUTube copy(TubeCloner cloner) {
/* 84 */     return new ClientMUTube(this, cloner);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/protocol/soap/ClientMUTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */