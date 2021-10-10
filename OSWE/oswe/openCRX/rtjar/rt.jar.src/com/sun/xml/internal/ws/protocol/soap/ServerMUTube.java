/*    */ package com.sun.xml.internal.ws.protocol.soap;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*    */ import com.sun.xml.internal.ws.api.pipe.ServerTubeAssemblerContext;
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
/*    */ public class ServerMUTube
/*    */   extends MUTube
/*    */ {
/*    */   private ServerTubeAssemblerContext tubeContext;
/*    */   private final Set<String> roles;
/*    */   private final Set<QName> handlerKnownHeaders;
/*    */   
/*    */   public ServerMUTube(ServerTubeAssemblerContext tubeContext, Tube next) {
/* 45 */     super(tubeContext.getEndpoint().getBinding(), next);
/*    */     
/* 47 */     this.tubeContext = tubeContext;
/*    */ 
/*    */     
/* 50 */     HandlerConfiguration handlerConfig = this.binding.getHandlerConfig();
/* 51 */     this.roles = handlerConfig.getRoles();
/* 52 */     this.handlerKnownHeaders = this.binding.getKnownHeaders();
/*    */   }
/*    */   
/*    */   protected ServerMUTube(ServerMUTube that, TubeCloner cloner) {
/* 56 */     super(that, cloner);
/* 57 */     this.tubeContext = that.tubeContext;
/* 58 */     this.roles = that.roles;
/* 59 */     this.handlerKnownHeaders = that.handlerKnownHeaders;
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
/*    */   public NextAction processRequest(Packet request) {
/* 72 */     Set<QName> misUnderstoodHeaders = getMisUnderstoodHeaders(request.getMessage().getHeaders(), this.roles, this.handlerKnownHeaders);
/* 73 */     if (misUnderstoodHeaders == null || misUnderstoodHeaders.isEmpty()) {
/* 74 */       return doInvoke(this.next, request);
/*    */     }
/* 76 */     return doReturnWith(request.createServerResponse(createMUSOAPFaultMessage(misUnderstoodHeaders), this.tubeContext
/* 77 */           .getWsdlModel(), this.tubeContext.getSEIModel(), this.tubeContext.getEndpoint().getBinding()));
/*    */   }
/*    */   
/*    */   public ServerMUTube copy(TubeCloner cloner) {
/* 81 */     return new ServerMUTube(this, cloner);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/protocol/soap/ServerMUTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */