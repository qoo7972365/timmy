/*     */ package com.sun.xml.internal.ws.api.pipe;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.internal.api.message.PropertySet.Property;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
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
/*     */ public class ThrowableContainerPropertySet
/*     */   extends BasePropertySet
/*     */ {
/*     */   public static final String FIBER_COMPLETION_THROWABLE = "com.sun.xml.internal.ws.api.pipe.fiber-completion-throwable";
/*     */   private Throwable throwable;
/*     */   public static final String FAULT_MESSAGE = "com.sun.xml.internal.ws.api.pipe.fiber-completion-fault-message";
/*     */   private Message faultMessage;
/*     */   public static final String RESPONSE_PACKET = "com.sun.xml.internal.ws.api.pipe.fiber-completion-response-packet";
/*     */   private Packet responsePacket;
/*     */   public static final String IS_FAULT_CREATED = "com.sun.xml.internal.ws.api.pipe.fiber-completion-is-fault-created";
/*     */   private boolean isFaultCreated;
/*     */   
/*     */   @Property({"com.sun.xml.internal.ws.api.pipe.fiber-completion-throwable"})
/*     */   public Throwable getThrowable() {
/*     */     return this.throwable;
/*     */   }
/*     */   
/*     */   public void setThrowable(Throwable throwable) {
/*     */     this.throwable = throwable;
/*     */   }
/*     */   
/*     */   @Property({"com.sun.xml.internal.ws.api.pipe.fiber-completion-fault-message"})
/*     */   public Message getFaultMessage() {
/*     */     return this.faultMessage;
/*     */   }
/*     */   
/*     */   public void setFaultMessage(Message faultMessage) {
/*     */     this.faultMessage = faultMessage;
/*     */   }
/*     */   
/*     */   @Property({"com.sun.xml.internal.ws.api.pipe.fiber-completion-response-packet"})
/*     */   public Packet getResponsePacket() {
/*     */     return this.responsePacket;
/*     */   }
/*     */   
/*     */   public void setResponsePacket(Packet responsePacket) {
/*     */     this.responsePacket = responsePacket;
/*     */   }
/*     */   
/*     */   public ThrowableContainerPropertySet(Throwable throwable) {
/*  98 */     this.isFaultCreated = false;
/*     */     this.throwable = throwable; } @Property({"com.sun.xml.internal.ws.api.pipe.fiber-completion-is-fault-created"})
/*     */   public boolean isFaultCreated() {
/* 101 */     return this.isFaultCreated;
/*     */   }
/*     */   public void setFaultCreated(boolean isFaultCreated) {
/* 104 */     this.isFaultCreated = isFaultCreated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 113 */     return model;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 118 */   private static final BasePropertySet.PropertyMap model = parse(ThrowableContainerPropertySet.class);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/ThrowableContainerPropertySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */