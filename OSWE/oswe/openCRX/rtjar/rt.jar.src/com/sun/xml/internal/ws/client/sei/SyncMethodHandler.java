/*     */ package com.sun.xml.internal.ws.client.sei;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.databinding.JavaCallInfo;
/*     */ import com.oracle.webservices.internal.api.message.MessageContext;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.client.RequestContext;
/*     */ import com.sun.xml.internal.ws.client.ResponseContextReceiver;
/*     */ import com.sun.xml.internal.ws.encoding.soap.DeserializationException;
/*     */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.internal.ws.resources.DispatchMessages;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ final class SyncMethodHandler
/*     */   extends MethodHandler
/*     */ {
/*     */   final boolean isVoid;
/*     */   final boolean isOneway;
/*     */   final JavaMethodImpl javaMethod;
/*     */   
/*     */   SyncMethodHandler(SEIStub owner, JavaMethodImpl jm) {
/*  70 */     super(owner, jm.getMethod());
/*  71 */     this.javaMethod = jm;
/*  72 */     this.isVoid = void.class.equals(jm.getMethod().getReturnType());
/*  73 */     this.isOneway = jm.getMEP().isOneWay();
/*     */   }
/*     */   
/*     */   Object invoke(Object proxy, Object[] args) throws Throwable {
/*  77 */     return invoke(proxy, args, this.owner.requestContext, (ResponseContextReceiver)this.owner);
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
/*     */   Object invoke(Object proxy, Object[] args, RequestContext rc, ResponseContextReceiver receiver) throws Throwable {
/*  90 */     JavaCallInfo call = this.owner.databinding.createJavaCallInfo(this.method, args);
/*  91 */     Packet req = (Packet)this.owner.databinding.serializeRequest(call);
/*     */     
/*  93 */     Packet reply = this.owner.doProcess(req, rc, receiver);
/*     */     
/*  95 */     Message msg = reply.getMessage();
/*  96 */     if (msg == null) {
/*  97 */       if (!this.isOneway || !this.isVoid) {
/*  98 */         throw new WebServiceException(DispatchMessages.INVALID_RESPONSE());
/*     */       }
/* 100 */       return null;
/*     */     } 
/*     */     
/*     */     try {
/* 104 */       call = this.owner.databinding.deserializeResponse((MessageContext)reply, call);
/* 105 */       if (call.getException() != null) {
/* 106 */         throw call.getException();
/*     */       }
/* 108 */       return call.getReturnValue();
/*     */     }
/* 110 */     catch (JAXBException e) {
/* 111 */       throw new DeserializationException(DispatchMessages.INVALID_RESPONSE_DESERIALIZATION(), new Object[] { e });
/* 112 */     } catch (XMLStreamException e) {
/* 113 */       throw new DeserializationException(DispatchMessages.INVALID_RESPONSE_DESERIALIZATION(), new Object[] { e });
/*     */     } finally {
/* 115 */       if (reply.transportBackChannel != null)
/* 116 */         reply.transportBackChannel.close(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   ValueGetterFactory getValueGetterFactory() {
/* 121 */     return ValueGetterFactory.SYNC;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/SyncMethodHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */