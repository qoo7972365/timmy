/*     */ package com.sun.xml.internal.ws.dump;
/*     */ 
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
/*     */ final class MessageDumper
/*     */ {
/*     */   private final String tubeName;
/*     */   private final Logger logger;
/*     */   private Level loggingLevel;
/*     */   
/*     */   enum MessageType
/*     */   {
/*  38 */     Request("Request message"),
/*  39 */     Response("Response message"),
/*  40 */     Exception("Response exception");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     MessageType(String name) {
/*  45 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  50 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   enum ProcessingState {
/*  55 */     Received("received"),
/*  56 */     Processed("processed");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     ProcessingState(String name) {
/*  61 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  66 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageDumper(String tubeName, Logger logger, Level loggingLevel) {
/*  77 */     this.tubeName = tubeName;
/*  78 */     this.logger = logger;
/*  79 */     this.loggingLevel = loggingLevel;
/*     */   }
/*     */   
/*     */   final boolean isLoggable() {
/*  83 */     return this.logger.isLoggable(this.loggingLevel);
/*     */   }
/*     */   
/*     */   final void setLoggingLevel(Level level) {
/*  87 */     this.loggingLevel = level;
/*     */   }
/*     */   
/*     */   final String createLogMessage(MessageType messageType, ProcessingState processingState, int tubeId, String engineId, String message) {
/*  91 */     return String.format("%s %s in Tube [ %s ] Instance [ %d ] Engine [ %s ] Thread [ %s ]:%n%s", new Object[] { messageType, processingState, this.tubeName, 
/*     */ 
/*     */ 
/*     */           
/*  95 */           Integer.valueOf(tubeId), engineId, 
/*     */           
/*  97 */           Thread.currentThread().getName(), message });
/*     */   }
/*     */ 
/*     */   
/*     */   final String dump(MessageType messageType, ProcessingState processingState, String message, int tubeId, String engineId) {
/* 102 */     String logMessage = createLogMessage(messageType, processingState, tubeId, engineId, message);
/* 103 */     this.logger.log(this.loggingLevel, logMessage);
/*     */     
/* 105 */     return logMessage;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/dump/MessageDumper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */