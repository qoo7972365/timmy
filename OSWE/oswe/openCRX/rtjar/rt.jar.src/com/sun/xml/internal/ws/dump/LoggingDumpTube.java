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
/*     */ import java.util.logging.Level;
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
/*     */ public class LoggingDumpTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   public enum Position
/*     */   {
/*  47 */     Before((String)MessageDumper.ProcessingState.Received, MessageDumper.ProcessingState.Processed),
/*  48 */     After((String)MessageDumper.ProcessingState.Processed, MessageDumper.ProcessingState.Received);
/*     */     
/*     */     private final MessageDumper.ProcessingState requestState;
/*     */     private final MessageDumper.ProcessingState responseState;
/*     */     
/*     */     Position(MessageDumper.ProcessingState requestState, MessageDumper.ProcessingState responseState) {
/*  54 */       this.requestState = requestState;
/*  55 */       this.responseState = responseState;
/*     */     }
/*     */   }
/*     */   
/*  59 */   private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
/*     */   
/*     */   private MessageDumper messageDumper;
/*     */   private final Level loggingLevel;
/*     */   private final Position position;
/*     */   private final int tubeId;
/*     */   
/*     */   public LoggingDumpTube(Level loggingLevel, Position position, Tube tubelineHead) {
/*  67 */     super(tubelineHead);
/*     */     
/*  69 */     this.position = position;
/*  70 */     this.loggingLevel = loggingLevel;
/*     */     
/*  72 */     this.tubeId = ID_GENERATOR.incrementAndGet();
/*     */   }
/*     */   
/*     */   public void setLoggedTubeName(String loggedTubeName) {
/*  76 */     assert this.messageDumper == null;
/*  77 */     this.messageDumper = new MessageDumper(loggedTubeName, Logger.getLogger(loggedTubeName), this.loggingLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LoggingDumpTube(LoggingDumpTube original, TubeCloner cloner) {
/*  84 */     super(original, cloner);
/*     */     
/*  86 */     this.messageDumper = original.messageDumper;
/*  87 */     this.loggingLevel = original.loggingLevel;
/*  88 */     this.position = original.position;
/*     */     
/*  90 */     this.tubeId = ID_GENERATOR.incrementAndGet();
/*     */   }
/*     */   
/*     */   public LoggingDumpTube copy(TubeCloner cloner) {
/*  94 */     return new LoggingDumpTube(this, cloner);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 100 */     if (this.messageDumper.isLoggable()) {
/* 101 */       Packet dumpPacket = (request != null) ? request.copy(true) : null;
/* 102 */       this.messageDumper.dump(MessageDumper.MessageType.Request, this.position.requestState, Converter.toString(dumpPacket), this.tubeId, (Fiber.current()).owner.id);
/*     */     } 
/*     */     
/* 105 */     return super.processRequest(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 110 */     if (this.messageDumper.isLoggable()) {
/* 111 */       Packet dumpPacket = (response != null) ? response.copy(true) : null;
/* 112 */       this.messageDumper.dump(MessageDumper.MessageType.Response, this.position.responseState, Converter.toString(dumpPacket), this.tubeId, (Fiber.current()).owner.id);
/*     */     } 
/*     */     
/* 115 */     return super.processResponse(response);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable t) {
/* 120 */     if (this.messageDumper.isLoggable()) {
/* 121 */       this.messageDumper.dump(MessageDumper.MessageType.Exception, this.position.responseState, Converter.toString(t), this.tubeId, (Fiber.current()).owner.id);
/*     */     }
/*     */     
/* 124 */     return super.processException(t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/* 129 */     super.preDestroy();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/dump/LoggingDumpTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */