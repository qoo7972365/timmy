/*     */ package com.sun.corba.se.impl.activation;
/*     */ 
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProcessMonitorThread
/*     */   extends Thread
/*     */ {
/*     */   private HashMap serverTable;
/*     */   private int sleepTime;
/*  42 */   private static ProcessMonitorThread instance = null;
/*     */   
/*     */   private ProcessMonitorThread(HashMap paramHashMap, int paramInt) {
/*  45 */     this.serverTable = paramHashMap;
/*  46 */     this.sleepTime = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     while (true) {
/*     */       Iterator iterator;
/*     */       try {
/*  55 */         Thread.sleep(this.sleepTime);
/*  56 */       } catch (InterruptedException interruptedException) {
/*     */         break;
/*     */       } 
/*     */       
/*  60 */       synchronized (this.serverTable) {
/*     */ 
/*     */         
/*  63 */         iterator = this.serverTable.values().iterator();
/*     */       } 
/*     */       try {
/*  66 */         checkServerHealth(iterator);
/*  67 */       } catch (ConcurrentModificationException concurrentModificationException) {
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkServerHealth(Iterator<ServerTableEntry> paramIterator) {
/*  74 */     if (paramIterator == null)
/*  75 */       return;  while (paramIterator.hasNext()) {
/*  76 */       ServerTableEntry serverTableEntry = paramIterator.next();
/*  77 */       serverTableEntry.checkProcessHealth();
/*     */     } 
/*     */   }
/*     */   
/*     */   static void start(HashMap paramHashMap) {
/*  82 */     int i = 1000;
/*     */     
/*  84 */     String str = System.getProperties().getProperty("com.sun.CORBA.activation.ServerPollingTime");
/*     */ 
/*     */     
/*  87 */     if (str != null) {
/*     */       try {
/*  89 */         i = Integer.parseInt(str);
/*  90 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     instance = new ProcessMonitorThread(paramHashMap, i);
/*     */     
/*  98 */     instance.setDaemon(true);
/*  99 */     instance.start();
/*     */   }
/*     */   
/*     */   static void interruptThread() {
/* 103 */     instance.interrupt();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/activation/ProcessMonitorThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */