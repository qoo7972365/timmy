/*     */ package com.sun.xml.internal.ws.dump;
/*     */ 
/*     */ import com.sun.org.glassfish.gmbal.ManagedAttribute;
/*     */ import com.sun.org.glassfish.gmbal.ManagedData;
/*     */ import com.sun.xml.internal.ws.api.FeatureConstructor;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ManagedData
/*     */ public final class MessageDumpingFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "com.sun.xml.internal.ws.messagedump.MessageDumpingFeature";
/*  46 */   private static final Level DEFAULT_MSG_LOG_LEVEL = Level.FINE;
/*     */   
/*     */   private final Queue<String> messageQueue;
/*     */   private final AtomicBoolean messageLoggingStatus;
/*     */   private final String messageLoggingRoot;
/*     */   private final Level messageLoggingLevel;
/*     */   
/*     */   public MessageDumpingFeature() {
/*  54 */     this(null, null, true);
/*     */   }
/*     */   
/*     */   public MessageDumpingFeature(String msgLogRoot, Level msgLogLevel, boolean storeMessages) {
/*  58 */     this.messageQueue = storeMessages ? new ConcurrentLinkedQueue<>() : null;
/*  59 */     this.messageLoggingStatus = new AtomicBoolean(true);
/*  60 */     this.messageLoggingRoot = (msgLogRoot != null && msgLogRoot.length() > 0) ? msgLogRoot : "com.sun.xml.internal.ws.messagedump";
/*  61 */     this.messageLoggingLevel = (msgLogLevel != null) ? msgLogLevel : DEFAULT_MSG_LOG_LEVEL;
/*     */     
/*  63 */     this.enabled = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageDumpingFeature(boolean enabled) {
/*  68 */     this();
/*  69 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   @FeatureConstructor({"enabled", "messageLoggingRoot", "messageLoggingLevel", "storeMessages"})
/*     */   public MessageDumpingFeature(boolean enabled, String msgLogRoot, String msgLogLevel, boolean storeMessages) {
/*  75 */     this(msgLogRoot, Level.parse(msgLogLevel), storeMessages);
/*     */     
/*  77 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/*  83 */     return "com.sun.xml.internal.ws.messagedump.MessageDumpingFeature";
/*     */   }
/*     */   
/*     */   public String nextMessage() {
/*  87 */     return (this.messageQueue != null) ? this.messageQueue.poll() : null;
/*     */   }
/*     */   
/*     */   public void enableMessageLogging() {
/*  91 */     this.messageLoggingStatus.set(true);
/*     */   }
/*     */   
/*     */   public void disableMessageLogging() {
/*  95 */     this.messageLoggingStatus.set(false);
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public boolean getMessageLoggingStatus() {
/* 100 */     return this.messageLoggingStatus.get();
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getMessageLoggingRoot() {
/* 105 */     return this.messageLoggingRoot;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public Level getMessageLoggingLevel() {
/* 110 */     return this.messageLoggingLevel;
/*     */   }
/*     */   
/*     */   boolean offerMessage(String message) {
/* 114 */     return (this.messageQueue != null) ? this.messageQueue.offer(message) : false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/dump/MessageDumpingFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */