/*     */ package com.sun.xml.internal.ws.api.server;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.PropertySet;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.util.Pool;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ public abstract class AbstractServerAsyncTransport<T>
/*     */ {
/*     */   private final WSEndpoint endpoint;
/*     */   private final CodecPool codecPool;
/*     */   
/*     */   public AbstractServerAsyncTransport(WSEndpoint endpoint) {
/*  57 */     this.endpoint = endpoint;
/*  58 */     this.codecPool = new CodecPool(endpoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Packet decodePacket(T connection, @NotNull Codec codec) throws IOException {
/*  70 */     Packet packet = new Packet();
/*  71 */     packet.acceptableMimeTypes = getAcceptableMimeTypes(connection);
/*  72 */     packet.addSatellite(getPropertySet(connection));
/*  73 */     packet.transportBackChannel = getTransportBackChannel(connection);
/*  74 */     return packet;
/*     */   }
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
/*     */   protected void handle(final T connection) throws IOException {
/* 131 */     final Codec codec = (Codec)this.codecPool.take();
/* 132 */     Packet request = decodePacket(connection, codec);
/* 133 */     if (!request.getMessage().isFault())
/* 134 */       this.endpoint.schedule(request, new WSEndpoint.CompletionCallback() {
/*     */             public void onCompletion(@NotNull Packet response) {
/*     */               try {
/* 137 */                 AbstractServerAsyncTransport.this.encodePacket(connection, response, codec);
/* 138 */               } catch (IOException ioe) {
/* 139 */                 ioe.printStackTrace();
/*     */               } 
/* 141 */               AbstractServerAsyncTransport.this.codecPool.recycle(codec);
/*     */             }
/*     */           }); 
/*     */   } protected abstract void encodePacket(T paramT, @NotNull Packet paramPacket, @NotNull Codec paramCodec) throws IOException; @Nullable
/*     */   protected abstract String getAcceptableMimeTypes(T paramT); @Nullable
/*     */   protected abstract TransportBackChannel getTransportBackChannel(T paramT); @NotNull
/*     */   protected abstract PropertySet getPropertySet(T paramT);
/*     */   @NotNull
/*     */   protected abstract WebServiceContextDelegate getWebServiceContextDelegate(T paramT);
/*     */   private static final class CodecPool extends Pool<Codec> { CodecPool(WSEndpoint endpoint) {
/* 151 */       this.endpoint = endpoint;
/*     */     }
/*     */     WSEndpoint endpoint;
/*     */     protected Codec create() {
/* 155 */       return this.endpoint.createCodec();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/AbstractServerAsyncTransport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */