/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.IllegalComponentStateException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import sun.util.logging.PlatformLogger;
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
/*     */ 
/*     */ 
/*     */ public class SunDisplayChanger
/*     */ {
/*  59 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.multiscreen.SunDisplayChanger");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private Map<DisplayChangedListener, Void> listeners = Collections.synchronizedMap(new WeakHashMap<>(1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(DisplayChangedListener paramDisplayChangedListener) {
/*  74 */     if (log.isLoggable(PlatformLogger.Level.FINE) && 
/*  75 */       paramDisplayChangedListener == null) {
/*  76 */       log.fine("Assertion (theListener != null) failed");
/*     */     }
/*     */     
/*  79 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  80 */       log.finer("Adding listener: " + paramDisplayChangedListener);
/*     */     }
/*  82 */     this.listeners.put(paramDisplayChangedListener, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(DisplayChangedListener paramDisplayChangedListener) {
/*  89 */     if (log.isLoggable(PlatformLogger.Level.FINE) && 
/*  90 */       paramDisplayChangedListener == null) {
/*  91 */       log.fine("Assertion (theListener != null) failed");
/*     */     }
/*     */     
/*  94 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  95 */       log.finer("Removing listener: " + paramDisplayChangedListener);
/*     */     }
/*  97 */     this.listeners.remove(paramDisplayChangedListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyListeners() {
/*     */     HashSet hashSet;
/* 105 */     if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 106 */       log.finest("notifyListeners");
/*     */     }
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
/* 121 */     synchronized (this.listeners) {
/* 122 */       hashSet = new HashSet(this.listeners.keySet());
/*     */     } 
/*     */     
/* 125 */     Iterator<DisplayChangedListener> iterator = hashSet.iterator();
/* 126 */     while (iterator.hasNext()) {
/* 127 */       DisplayChangedListener displayChangedListener = iterator.next();
/*     */       try {
/* 129 */         if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 130 */           log.finest("displayChanged for listener: " + displayChangedListener);
/*     */         }
/* 132 */         displayChangedListener.displayChanged();
/* 133 */       } catch (IllegalComponentStateException illegalComponentStateException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 139 */         this.listeners.remove(displayChangedListener);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyPaletteChanged() {
/*     */     HashSet hashSet;
/* 149 */     if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 150 */       log.finest("notifyPaletteChanged");
/*     */     }
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
/* 165 */     synchronized (this.listeners) {
/* 166 */       hashSet = new HashSet(this.listeners.keySet());
/*     */     } 
/* 168 */     Iterator<DisplayChangedListener> iterator = hashSet.iterator();
/* 169 */     while (iterator.hasNext()) {
/* 170 */       DisplayChangedListener displayChangedListener = iterator.next();
/*     */       try {
/* 172 */         if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 173 */           log.finest("paletteChanged for listener: " + displayChangedListener);
/*     */         }
/* 175 */         displayChangedListener.paletteChanged();
/* 176 */       } catch (IllegalComponentStateException illegalComponentStateException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 182 */         this.listeners.remove(displayChangedListener);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/SunDisplayChanger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */