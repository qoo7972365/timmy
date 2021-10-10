/*     */ package com.sun.org.apache.xerces.internal.dom.events;
/*     */ 
/*     */ import org.w3c.dom.events.Event;
/*     */ import org.w3c.dom.events.EventTarget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventImpl
/*     */   implements Event
/*     */ {
/*  38 */   public String type = null;
/*     */   public EventTarget target;
/*     */   public EventTarget currentTarget;
/*     */   public short eventPhase;
/*     */   public boolean initialized = false, bubbles = true, cancelable = false;
/*     */   public boolean stopPropagation = false;
/*     */   public boolean preventDefault = false;
/*  45 */   protected long timeStamp = System.currentTimeMillis();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg) {
/*  59 */     this.type = eventTypeArg;
/*  60 */     this.bubbles = canBubbleArg;
/*  61 */     this.cancelable = cancelableArg;
/*     */     
/*  63 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBubbles() {
/*  71 */     return this.bubbles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCancelable() {
/*  80 */     return this.cancelable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventTarget getCurrentTarget() {
/*  88 */     return this.currentTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getEventPhase() {
/*  96 */     return this.eventPhase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventTarget getTarget() {
/* 104 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 111 */     return this.type;
/*     */   }
/*     */   
/*     */   public long getTimeStamp() {
/* 115 */     return this.timeStamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopPropagation() {
/* 124 */     this.stopPropagation = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preventDefault() {
/* 132 */     this.preventDefault = true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/events/EventImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */