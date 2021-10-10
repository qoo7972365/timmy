/*     */ package com.sun.xml.internal.ws.dump;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.internal.ws.commons.xmlutil.Converter;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Logger;
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
/*     */ final class MessageDumpingTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   static final String DEFAULT_MSGDUMP_LOGGING_ROOT = "com.sun.xml.internal.ws.messagedump";
/*  46 */   private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MessageDumper messageDumper;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int tubeId;
/*     */ 
/*     */ 
/*     */   
/*     */   private final MessageDumpingFeature messageDumpingFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MessageDumpingTube(Tube next, MessageDumpingFeature feature) {
/*  65 */     super(next);
/*     */     
/*  67 */     this.messageDumpingFeature = feature;
/*  68 */     this.tubeId = ID_GENERATOR.incrementAndGet();
/*  69 */     this
/*     */ 
/*     */       
/*  72 */       .messageDumper = new MessageDumper("MesageDumpingTube", Logger.getLogger(feature.getMessageLoggingRoot()), feature.getMessageLoggingLevel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MessageDumpingTube(MessageDumpingTube that, TubeCloner cloner) {
/*  79 */     super(that, cloner);
/*     */ 
/*     */     
/*  82 */     this.messageDumpingFeature = that.messageDumpingFeature;
/*  83 */     this.tubeId = ID_GENERATOR.incrementAndGet();
/*  84 */     this.messageDumper = that.messageDumper;
/*     */   }
/*     */   
/*     */   public MessageDumpingTube copy(TubeCloner cloner) {
/*  88 */     return new MessageDumpingTube(this, cloner);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/*  93 */     dump(MessageDumper.MessageType.Request, Converter.toString(request), (Fiber.current()).owner.id);
/*  94 */     return super.processRequest(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/*  99 */     dump(MessageDumper.MessageType.Response, Converter.toString(response), (Fiber.current()).owner.id);
/* 100 */     return super.processResponse(response);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable t) {
/* 105 */     dump(MessageDumper.MessageType.Exception, Converter.toString(t), (Fiber.current()).owner.id);
/*     */     
/* 107 */     return super.processException(t);
/*     */   }
/*     */   
/*     */   protected final void dump(MessageDumper.MessageType messageType, String message, String engineId) {
/*     */     String logMessage;
/* 112 */     if (this.messageDumpingFeature.getMessageLoggingStatus()) {
/* 113 */       this.messageDumper.setLoggingLevel(this.messageDumpingFeature.getMessageLoggingLevel());
/* 114 */       logMessage = this.messageDumper.dump(messageType, MessageDumper.ProcessingState.Received, message, this.tubeId, engineId);
/*     */     } else {
/* 116 */       logMessage = this.messageDumper.createLogMessage(messageType, MessageDumper.ProcessingState.Received, this.tubeId, engineId, message);
/*     */     } 
/* 118 */     this.messageDumpingFeature.offerMessage(logMessage);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/dump/MessageDumpingTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */