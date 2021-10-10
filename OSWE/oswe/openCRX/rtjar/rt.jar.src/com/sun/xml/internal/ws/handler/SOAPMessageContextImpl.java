/*     */ package com.sun.xml.internal.ws.handler;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.message.saaj.SAAJFactory;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.handler.soap.SOAPMessageContext;
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
/*     */ public class SOAPMessageContextImpl
/*     */   extends MessageUpdatableContext
/*     */   implements SOAPMessageContext
/*     */ {
/*     */   private Set<String> roles;
/*  57 */   private SOAPMessage soapMsg = null;
/*     */   private WSBinding binding;
/*     */   
/*     */   public SOAPMessageContextImpl(WSBinding binding, Packet packet, Set<String> roles) {
/*  61 */     super(packet);
/*  62 */     this.binding = binding;
/*  63 */     this.roles = roles;
/*     */   }
/*     */   
/*     */   public SOAPMessage getMessage() {
/*  67 */     if (this.soapMsg == null) {
/*     */       try {
/*  69 */         Message m = this.packet.getMessage();
/*  70 */         this.soapMsg = (m != null) ? m.readAsSOAPMessage() : null;
/*  71 */       } catch (SOAPException e) {
/*  72 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*  75 */     return this.soapMsg;
/*     */   }
/*     */   
/*     */   public void setMessage(SOAPMessage soapMsg) {
/*     */     try {
/*  80 */       this.soapMsg = soapMsg;
/*  81 */     } catch (Exception e) {
/*  82 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   void setPacketMessage(Message newMessage) {
/*  87 */     if (newMessage != null) {
/*  88 */       this.packet.setMessage(newMessage);
/*  89 */       this.soapMsg = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateMessage() {
/*  96 */     if (this.soapMsg != null) {
/*  97 */       this.packet.setMessage(SAAJFactory.create(this.soapMsg));
/*  98 */       this.soapMsg = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object[] getHeaders(QName header, JAXBContext jaxbContext, boolean allRoles) {
/* 103 */     SOAPVersion soapVersion = this.binding.getSOAPVersion();
/*     */     
/* 105 */     List<Object> beanList = new ArrayList();
/*     */     try {
/* 107 */       Iterator<Header> itr = this.packet.getMessage().getHeaders().getHeaders(header, false);
/* 108 */       if (allRoles) {
/* 109 */         while (itr.hasNext()) {
/* 110 */           beanList.add(((Header)itr.next()).readAsJAXB(jaxbContext.createUnmarshaller()));
/*     */         }
/*     */       } else {
/* 113 */         while (itr.hasNext()) {
/* 114 */           Header soapHeader = itr.next();
/*     */           
/* 116 */           String role = soapHeader.getRole(soapVersion);
/* 117 */           if (getRoles().contains(role)) {
/* 118 */             beanList.add(soapHeader.readAsJAXB(jaxbContext.createUnmarshaller()));
/*     */           }
/*     */         } 
/*     */       } 
/* 122 */       return beanList.toArray();
/* 123 */     } catch (Exception e) {
/* 124 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<String> getRoles() {
/* 129 */     return this.roles;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/SOAPMessageContextImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */