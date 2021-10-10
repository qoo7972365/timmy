/*     */ package com.sun.xml.internal.ws.server.provider;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ProviderArgumentsBuilder<T>
/*     */ {
/*     */   protected abstract Message getResponseMessage(Exception paramException);
/*     */   
/*     */   protected Packet getResponse(Packet request, Exception e, WSDLPort port, WSBinding binding) {
/*  54 */     Message message = getResponseMessage(e);
/*  55 */     Packet response = request.createServerResponse(message, port, null, binding);
/*  56 */     return response;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract T getParameter(Packet paramPacket);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Message getResponseMessage(T paramT);
/*     */ 
/*     */ 
/*     */   
/*     */   protected Packet getResponse(Packet request, @Nullable T returnValue, WSDLPort port, WSBinding binding) {
/*  71 */     Message message = null;
/*  72 */     if (returnValue != null) {
/*  73 */       message = getResponseMessage(returnValue);
/*     */     }
/*  75 */     Packet response = request.createServerResponse(message, port, null, binding);
/*  76 */     return response;
/*     */   }
/*     */   
/*     */   public static ProviderArgumentsBuilder<?> create(ProviderEndpointModel model, WSBinding binding) {
/*  80 */     if (model.datatype == Packet.class)
/*  81 */       return new PacketProviderArgumentsBuilder(binding.getSOAPVersion()); 
/*  82 */     return (binding instanceof javax.xml.ws.soap.SOAPBinding) ? SOAPProviderArgumentBuilder.create(model, binding.getSOAPVersion()) : 
/*  83 */       XMLProviderArgumentBuilder.createBuilder(model, binding);
/*     */   }
/*     */   
/*     */   private static class PacketProviderArgumentsBuilder extends ProviderArgumentsBuilder<Packet> {
/*     */     private final SOAPVersion soapVersion;
/*     */     
/*     */     public PacketProviderArgumentsBuilder(SOAPVersion soapVersion) {
/*  90 */       this.soapVersion = soapVersion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected Message getResponseMessage(Exception e) {
/*  96 */       return SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, e);
/*     */     }
/*     */ 
/*     */     
/*     */     public Packet getParameter(Packet packet) {
/* 101 */       return packet;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected Message getResponseMessage(Packet returnValue) {
/* 107 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */     
/*     */     protected Packet getResponse(Packet request, @Nullable Packet returnValue, WSDLPort port, WSBinding binding) {
/* 112 */       return returnValue;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/provider/ProviderArgumentsBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */