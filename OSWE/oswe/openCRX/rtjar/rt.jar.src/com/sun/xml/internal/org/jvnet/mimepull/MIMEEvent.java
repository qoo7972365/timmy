/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class MIMEEvent
/*     */ {
/*     */   enum EVENT_TYPE
/*     */   {
/*  35 */     START_MESSAGE, START_PART, HEADERS, CONTENT, END_PART, END_MESSAGE;
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
/*  56 */   static final StartMessage START_MESSAGE = new StartMessage();
/*  57 */   static final StartPart START_PART = new StartPart();
/*  58 */   static final EndPart END_PART = new EndPart(); abstract EVENT_TYPE getEventType();
/*  59 */   static final EndMessage END_MESSAGE = new EndMessage();
/*     */   
/*     */   static final class StartMessage extends MIMEEvent {
/*     */     MIMEEvent.EVENT_TYPE getEventType() {
/*  63 */       return MIMEEvent.EVENT_TYPE.START_MESSAGE;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class StartPart extends MIMEEvent {
/*     */     MIMEEvent.EVENT_TYPE getEventType() {
/*  69 */       return MIMEEvent.EVENT_TYPE.START_PART;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class EndPart extends MIMEEvent {
/*     */     MIMEEvent.EVENT_TYPE getEventType() {
/*  75 */       return MIMEEvent.EVENT_TYPE.END_PART;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class Headers extends MIMEEvent {
/*     */     InternetHeaders ih;
/*     */     
/*     */     Headers(InternetHeaders ih) {
/*  83 */       this.ih = ih;
/*     */     }
/*     */     
/*     */     MIMEEvent.EVENT_TYPE getEventType() {
/*  87 */       return MIMEEvent.EVENT_TYPE.HEADERS;
/*     */     }
/*     */     
/*     */     InternetHeaders getHeaders() {
/*  91 */       return this.ih;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class Content extends MIMEEvent {
/*     */     private final ByteBuffer buf;
/*     */     
/*     */     Content(ByteBuffer buf) {
/*  99 */       this.buf = buf;
/*     */     }
/*     */     
/*     */     MIMEEvent.EVENT_TYPE getEventType() {
/* 103 */       return MIMEEvent.EVENT_TYPE.CONTENT;
/*     */     }
/*     */     
/*     */     ByteBuffer getData() {
/* 107 */       return this.buf;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class EndMessage extends MIMEEvent {
/*     */     MIMEEvent.EVENT_TYPE getEventType() {
/* 113 */       return MIMEEvent.EVENT_TYPE.END_MESSAGE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/MIMEEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */