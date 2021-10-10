/*     */ package sun.print;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import javax.print.PrintService;
/*     */ import javax.print.attribute.HashPrintServiceAttributeSet;
/*     */ import javax.print.attribute.PrintServiceAttributeSet;
/*     */ import javax.print.event.PrintServiceAttributeEvent;
/*     */ import javax.print.event.PrintServiceAttributeListener;
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
/*     */ class ServiceNotifier
/*     */   extends Thread
/*     */ {
/*     */   private PrintService service;
/*     */   private Vector listeners;
/*     */   private boolean stop = false;
/*     */   private PrintServiceAttributeSet lastSet;
/*     */   
/*     */   ServiceNotifier(PrintService paramPrintService) {
/*  51 */     super(paramPrintService.getName() + " notifier");
/*  52 */     this.service = paramPrintService;
/*  53 */     this.listeners = new Vector();
/*     */     try {
/*  55 */       setPriority(4);
/*  56 */       setDaemon(true);
/*  57 */       start();
/*  58 */     } catch (SecurityException securityException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   void addListener(PrintServiceAttributeListener paramPrintServiceAttributeListener) {
/*  63 */     synchronized (this) {
/*  64 */       if (paramPrintServiceAttributeListener == null || this.listeners == null) {
/*     */         return;
/*     */       }
/*  67 */       this.listeners.add(paramPrintServiceAttributeListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   void removeListener(PrintServiceAttributeListener paramPrintServiceAttributeListener) {
/*  72 */     synchronized (this) {
/*  73 */       if (paramPrintServiceAttributeListener == null || this.listeners == null) {
/*     */         return;
/*     */       }
/*  76 */       this.listeners.remove(paramPrintServiceAttributeListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isEmpty() {
/*  81 */     return (this.listeners == null || this.listeners.isEmpty());
/*     */   }
/*     */   
/*     */   void stopNotifier() {
/*  85 */     this.stop = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void wake() {
/*     */     try {
/*  93 */       interrupt();
/*  94 */     } catch (SecurityException securityException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 105 */     long l1 = 15000L;
/* 106 */     long l2 = 2000L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     while (!this.stop) {
/*     */       try {
/* 114 */         Thread.sleep(l2);
/* 115 */       } catch (InterruptedException interruptedException) {}
/*     */       
/* 117 */       synchronized (this) {
/* 118 */         if (this.listeners == null) {
/*     */           continue;
/*     */         }
/* 121 */         long l = System.currentTimeMillis();
/* 122 */         if (this.listeners != null) {
/* 123 */           PrintServiceAttributeSet printServiceAttributeSet; if (this.service instanceof AttributeUpdater) {
/*     */             
/* 125 */             printServiceAttributeSet = ((AttributeUpdater)this.service).getUpdatedAttributes();
/*     */           } else {
/* 127 */             printServiceAttributeSet = this.service.getAttributes();
/*     */           } 
/* 129 */           if (printServiceAttributeSet != null && !printServiceAttributeSet.isEmpty()) {
/* 130 */             for (byte b = 0; b < this.listeners.size(); b++) {
/*     */               
/* 132 */               PrintServiceAttributeListener printServiceAttributeListener = this.listeners.elementAt(b);
/* 133 */               HashPrintServiceAttributeSet hashPrintServiceAttributeSet = new HashPrintServiceAttributeSet(printServiceAttributeSet);
/*     */               
/* 135 */               PrintServiceAttributeEvent printServiceAttributeEvent = new PrintServiceAttributeEvent(this.service, hashPrintServiceAttributeSet);
/*     */               
/* 137 */               printServiceAttributeListener.attributeUpdate(printServiceAttributeEvent);
/*     */             } 
/*     */           }
/*     */         } 
/* 141 */         l2 = (System.currentTimeMillis() - l) * 10L;
/* 142 */         if (l2 < l1)
/* 143 */           l2 = l1; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/ServiceNotifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */