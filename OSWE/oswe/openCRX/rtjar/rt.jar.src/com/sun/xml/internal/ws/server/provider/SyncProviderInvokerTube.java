/*    */ package com.sun.xml.internal.ws.server.provider;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*    */ import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;
/*    */ import com.sun.xml.internal.ws.api.server.Invoker;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
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
/*    */ public class SyncProviderInvokerTube<T>
/*    */   extends ProviderInvokerTube<T>
/*    */ {
/* 48 */   private static final Logger LOGGER = Logger.getLogger("com.sun.xml.internal.ws.server.SyncProviderInvokerTube");
/*    */ 
/*    */   
/*    */   public SyncProviderInvokerTube(Invoker invoker, ProviderArgumentsBuilder<T> argsBuilder) {
/* 52 */     super(invoker, argsBuilder);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NextAction processRequest(Packet request) {
/*    */     T returnValue;
/* 62 */     WSDLPort port = getEndpoint().getPort();
/* 63 */     WSBinding binding = getEndpoint().getBinding();
/* 64 */     T param = this.argsBuilder.getParameter(request);
/*    */     
/* 66 */     LOGGER.fine("Invoking Provider Endpoint");
/*    */ 
/*    */     
/*    */     try {
/* 70 */       returnValue = (T)getInvoker(request).invokeProvider(request, param);
/* 71 */     } catch (Exception e) {
/* 72 */       LOGGER.log(Level.SEVERE, e.getMessage(), e);
/* 73 */       Packet packet = this.argsBuilder.getResponse(request, e, port, binding);
/* 74 */       return doReturnWith(packet);
/*    */     } 
/* 76 */     if (returnValue == null)
/*    */     {
/*    */       
/* 79 */       if (request.transportBackChannel != null) {
/* 80 */         request.transportBackChannel.close();
/*    */       }
/*    */     }
/* 83 */     Packet response = this.argsBuilder.getResponse(request, returnValue, port, binding);
/*    */ 
/*    */ 
/*    */     
/* 87 */     ThrowableContainerPropertySet tc = (ThrowableContainerPropertySet)response.getSatellite(ThrowableContainerPropertySet.class);
/* 88 */     Throwable t = (tc != null) ? tc.getThrowable() : null;
/*    */     
/* 90 */     return (t != null) ? doThrow(response, t) : doReturnWith(response);
/*    */   }
/*    */   @NotNull
/*    */   public NextAction processResponse(@NotNull Packet response) {
/* 94 */     return doReturnWith(response);
/*    */   }
/*    */   @NotNull
/*    */   public NextAction processException(@NotNull Throwable t) {
/* 98 */     return doThrow(t);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/provider/SyncProviderInvokerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */