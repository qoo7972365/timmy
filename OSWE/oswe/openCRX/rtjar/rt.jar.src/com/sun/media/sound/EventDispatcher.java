/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.sound.midi.ControllerEventListener;
/*     */ import javax.sound.midi.MetaEventListener;
/*     */ import javax.sound.midi.MetaMessage;
/*     */ import javax.sound.midi.ShortMessage;
/*     */ import javax.sound.sampled.LineEvent;
/*     */ import javax.sound.sampled.LineListener;
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
/*     */ final class EventDispatcher
/*     */   implements Runnable
/*     */ {
/*     */   private static final int AUTO_CLOSE_TIME = 5000;
/*  60 */   private final ArrayList eventQueue = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private Thread thread = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private final ArrayList<ClipInfo> autoClosingClips = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private final ArrayList<LineMonitor> lineMonitors = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int LINE_MONITOR_TIME = 400;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void start() {
/*  90 */     if (this.thread == null) {
/*  91 */       this.thread = JSSecurityManager.createThread(this, "Java Sound Event Dispatcher", true, -1, true);
/*     */     }
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
/*     */   void processEvent(EventInfo paramEventInfo) {
/* 105 */     int i = paramEventInfo.getListenerCount();
/*     */ 
/*     */     
/* 108 */     if (paramEventInfo.getEvent() instanceof LineEvent) {
/* 109 */       LineEvent lineEvent = (LineEvent)paramEventInfo.getEvent();
/*     */       
/* 111 */       for (byte b = 0; b < i; b++) {
/*     */         try {
/* 113 */           ((LineListener)paramEventInfo.getListener(b)).update(lineEvent);
/* 114 */         } catch (Throwable throwable) {}
/*     */       } 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 122 */     if (paramEventInfo.getEvent() instanceof MetaMessage) {
/* 123 */       MetaMessage metaMessage = (MetaMessage)paramEventInfo.getEvent();
/* 124 */       for (byte b = 0; b < i; b++) {
/*     */         try {
/* 126 */           ((MetaEventListener)paramEventInfo.getListener(b)).meta(metaMessage);
/* 127 */         } catch (Throwable throwable) {}
/*     */       } 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 135 */     if (paramEventInfo.getEvent() instanceof ShortMessage) {
/* 136 */       ShortMessage shortMessage = (ShortMessage)paramEventInfo.getEvent();
/* 137 */       int j = shortMessage.getStatus();
/*     */ 
/*     */ 
/*     */       
/* 141 */       if ((j & 0xF0) == 176) {
/* 142 */         for (byte b = 0; b < i; b++) {
/*     */           try {
/* 144 */             ((ControllerEventListener)paramEventInfo.getListener(b)).controlChange(shortMessage);
/* 145 */           } catch (Throwable throwable) {}
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 153 */     Printer.err("Unknown event type: " + paramEventInfo.getEvent());
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
/*     */   void dispatchEvents() {
/* 167 */     EventInfo eventInfo = null;
/*     */     
/* 169 */     synchronized (this) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 174 */         if (this.eventQueue.size() == 0) {
/* 175 */           if (this.autoClosingClips.size() > 0 || this.lineMonitors.size() > 0) {
/* 176 */             char c = 'ᎈ';
/* 177 */             if (this.lineMonitors.size() > 0) {
/* 178 */               c = 'Ɛ';
/*     */             }
/* 180 */             wait(c);
/*     */           } else {
/* 182 */             wait();
/*     */           } 
/*     */         }
/* 185 */       } catch (InterruptedException interruptedException) {}
/*     */       
/* 187 */       if (this.eventQueue.size() > 0)
/*     */       {
/* 189 */         eventInfo = this.eventQueue.remove(0);
/*     */       }
/*     */     } 
/*     */     
/* 193 */     if (eventInfo != null) {
/* 194 */       processEvent(eventInfo);
/*     */     } else {
/* 196 */       if (this.autoClosingClips.size() > 0) {
/* 197 */         closeAutoClosingClips();
/*     */       }
/* 199 */       if (this.lineMonitors.size() > 0) {
/* 200 */         monitorLines();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void postEvent(EventInfo paramEventInfo) {
/* 210 */     this.eventQueue.add(paramEventInfo);
/* 211 */     notifyAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     while (true) {
/*     */       try {
/*     */         while (true)
/* 222 */           dispatchEvents();  break;
/* 223 */       } catch (Throwable throwable) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void sendAudioEvents(Object paramObject, List paramList) {
/* 234 */     if (paramList == null || paramList
/* 235 */       .size() == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 240 */     start();
/*     */     
/* 242 */     EventInfo eventInfo = new EventInfo(paramObject, paramList);
/* 243 */     postEvent(eventInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeAutoClosingClips() {
/* 254 */     synchronized (this.autoClosingClips) {
/*     */       
/* 256 */       long l = System.currentTimeMillis();
/* 257 */       for (int i = this.autoClosingClips.size() - 1; i >= 0; i--) {
/* 258 */         ClipInfo clipInfo = this.autoClosingClips.get(i);
/* 259 */         if (clipInfo.isExpired(l)) {
/* 260 */           AutoClosingClip autoClosingClip = clipInfo.getClip();
/*     */           
/* 262 */           if (!autoClosingClip.isOpen() || !autoClosingClip.isAutoClosing()) {
/*     */             
/* 264 */             this.autoClosingClips.remove(i);
/*     */           }
/* 266 */           else if (!autoClosingClip.isRunning() && !autoClosingClip.isActive() && autoClosingClip.isAutoClosing()) {
/*     */             
/* 268 */             autoClosingClip.close();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getAutoClosingClipIndex(AutoClosingClip paramAutoClosingClip) {
/* 283 */     synchronized (this.autoClosingClips) {
/* 284 */       for (int i = this.autoClosingClips.size() - 1; i >= 0; i--) {
/* 285 */         if (paramAutoClosingClip.equals(((ClipInfo)this.autoClosingClips.get(i)).getClip())) {
/* 286 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/* 290 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void autoClosingClipOpened(AutoClosingClip paramAutoClosingClip) {
/* 298 */     int i = 0;
/* 299 */     synchronized (this.autoClosingClips) {
/* 300 */       i = getAutoClosingClipIndex(paramAutoClosingClip);
/* 301 */       if (i == -1)
/*     */       {
/* 303 */         this.autoClosingClips.add(new ClipInfo(paramAutoClosingClip));
/*     */       }
/*     */     } 
/* 306 */     if (i == -1) {
/* 307 */       synchronized (this) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 312 */         notifyAll();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void autoClosingClipClosed(AutoClosingClip paramAutoClosingClip) {
/* 322 */     synchronized (this.autoClosingClips) {
/* 323 */       int i = getAutoClosingClipIndex(paramAutoClosingClip);
/* 324 */       if (i != -1) {
/* 325 */         this.autoClosingClips.remove(i);
/*     */       }
/*     */     } 
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
/*     */   private void monitorLines() {
/* 339 */     synchronized (this.lineMonitors) {
/*     */       
/* 341 */       for (byte b = 0; b < this.lineMonitors.size(); b++) {
/* 342 */         ((LineMonitor)this.lineMonitors.get(b)).checkLine();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addLineMonitor(LineMonitor paramLineMonitor) {
/* 354 */     synchronized (this.lineMonitors) {
/* 355 */       if (this.lineMonitors.indexOf(paramLineMonitor) >= 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 360 */       this.lineMonitors.add(paramLineMonitor);
/*     */     } 
/* 362 */     synchronized (this) {
/*     */       
/* 364 */       notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeLineMonitor(LineMonitor paramLineMonitor) {
/* 374 */     synchronized (this.lineMonitors) {
/* 375 */       if (this.lineMonitors.indexOf(paramLineMonitor) < 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 380 */       this.lineMonitors.remove(paramLineMonitor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class EventInfo
/*     */   {
/*     */     private final Object event;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Object[] listeners;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     EventInfo(Object param1Object, List param1List) {
/* 401 */       this.event = param1Object;
/* 402 */       this.listeners = param1List.toArray();
/*     */     }
/*     */     
/*     */     Object getEvent() {
/* 406 */       return this.event;
/*     */     }
/*     */     
/*     */     int getListenerCount() {
/* 410 */       return this.listeners.length;
/*     */     }
/*     */     
/*     */     Object getListener(int param1Int) {
/* 414 */       return this.listeners[param1Int];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ClipInfo
/*     */   {
/*     */     private final AutoClosingClip clip;
/*     */ 
/*     */ 
/*     */     
/*     */     private final long expiration;
/*     */ 
/*     */ 
/*     */     
/*     */     ClipInfo(AutoClosingClip param1AutoClosingClip) {
/* 432 */       this.clip = param1AutoClosingClip;
/* 433 */       this.expiration = System.currentTimeMillis() + 5000L;
/*     */     }
/*     */     
/*     */     AutoClosingClip getClip() {
/* 437 */       return this.clip;
/*     */     }
/*     */     
/*     */     boolean isExpired(long param1Long) {
/* 441 */       return (param1Long > this.expiration);
/*     */     }
/*     */   }
/*     */   
/*     */   static interface LineMonitor {
/*     */     void checkLine();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/EventDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */