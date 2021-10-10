/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.util.EventObject;
/*     */ import java.util.Vector;
/*     */ import javax.naming.event.NamingEvent;
/*     */ import javax.naming.event.NamingExceptionEvent;
/*     */ import javax.naming.event.NamingListener;
/*     */ import javax.naming.ldap.UnsolicitedNotificationEvent;
/*     */ import javax.naming.ldap.UnsolicitedNotificationListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class EventQueue
/*     */   implements Runnable
/*     */ {
/*     */   private static final boolean debug = false;
/*     */   
/*     */   private static class QueueElement
/*     */   {
/*  51 */     QueueElement next = null;
/*  52 */     QueueElement prev = null;
/*  53 */     EventObject event = null;
/*  54 */     Vector<NamingListener> vector = null;
/*     */     
/*     */     QueueElement(EventObject param1EventObject, Vector<NamingListener> param1Vector) {
/*  57 */       this.event = param1EventObject;
/*  58 */       this.vector = param1Vector;
/*     */     }
/*     */   }
/*     */   
/*  62 */   private QueueElement head = null;
/*  63 */   private QueueElement tail = null;
/*     */   
/*     */   private Thread qThread;
/*     */   
/*     */   EventQueue() {
/*  68 */     this.qThread = Obj.helper.createThread(this);
/*  69 */     this.qThread.setDaemon(true);
/*  70 */     this.qThread.start();
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
/*     */   synchronized void enqueue(EventObject paramEventObject, Vector<NamingListener> paramVector) {
/*  90 */     QueueElement queueElement = new QueueElement(paramEventObject, paramVector);
/*     */     
/*  92 */     if (this.head == null) {
/*  93 */       this.head = queueElement;
/*  94 */       this.tail = queueElement;
/*     */     } else {
/*  96 */       queueElement.next = this.head;
/*  97 */       this.head.prev = queueElement;
/*  98 */       this.head = queueElement;
/*     */     } 
/* 100 */     notify();
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
/*     */   private synchronized QueueElement dequeue() throws InterruptedException {
/* 113 */     while (this.tail == null)
/* 114 */       wait(); 
/* 115 */     QueueElement queueElement = this.tail;
/* 116 */     this.tail = queueElement.prev;
/* 117 */     if (this.tail == null) {
/* 118 */       this.head = null;
/*     */     } else {
/* 120 */       this.tail.next = null;
/*     */     } 
/* 122 */     queueElement.prev = queueElement.next = null;
/* 123 */     return queueElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*     */       QueueElement queueElement;
/* 133 */       while ((queueElement = dequeue()) != null) {
/* 134 */         EventObject eventObject = queueElement.event;
/* 135 */         Vector<NamingListener> vector = queueElement.vector;
/*     */         
/* 137 */         for (byte b = 0; b < vector.size(); b++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 146 */           if (eventObject instanceof NamingEvent) {
/* 147 */             ((NamingEvent)eventObject).dispatch(vector.elementAt(b));
/*     */           
/*     */           }
/* 150 */           else if (eventObject instanceof NamingExceptionEvent) {
/* 151 */             ((NamingExceptionEvent)eventObject).dispatch(vector.elementAt(b));
/* 152 */           } else if (eventObject instanceof UnsolicitedNotificationEvent) {
/* 153 */             ((UnsolicitedNotificationEvent)eventObject).dispatch((UnsolicitedNotificationListener)vector
/* 154 */                 .elementAt(b));
/*     */           } 
/*     */         } 
/*     */         
/* 158 */         queueElement = null; eventObject = null; vector = null;
/*     */       } 
/* 160 */     } catch (InterruptedException interruptedException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void stop() {
/* 171 */     if (this.qThread != null) {
/* 172 */       this.qThread.interrupt();
/* 173 */       this.qThread = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/EventQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */