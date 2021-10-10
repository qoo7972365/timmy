/*     */ package sun.java2d.pipe.hw;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AccelDeviceEventNotifier
/*     */ {
/*     */   private static AccelDeviceEventNotifier theInstance;
/*     */   public static final int DEVICE_RESET = 0;
/*     */   public static final int DEVICE_DISPOSED = 1;
/*  60 */   private final Map<AccelDeviceEventListener, Integer> listeners = Collections.synchronizedMap(new HashMap<>(1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized AccelDeviceEventNotifier getInstance(boolean paramBoolean) {
/*  77 */     if (theInstance == null && paramBoolean) {
/*  78 */       theInstance = new AccelDeviceEventNotifier();
/*     */     }
/*  80 */     return theInstance;
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
/*     */   public static final void eventOccured(int paramInt1, int paramInt2) {
/*  95 */     AccelDeviceEventNotifier accelDeviceEventNotifier = getInstance(false);
/*  96 */     if (accelDeviceEventNotifier != null) {
/*  97 */       accelDeviceEventNotifier.notifyListeners(paramInt2, paramInt1);
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
/*     */   
/*     */   public static final void addListener(AccelDeviceEventListener paramAccelDeviceEventListener, int paramInt) {
/* 112 */     getInstance(true).add(paramAccelDeviceEventListener, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void removeListener(AccelDeviceEventListener paramAccelDeviceEventListener) {
/* 121 */     getInstance(true).remove(paramAccelDeviceEventListener);
/*     */   }
/*     */   
/*     */   private final void add(AccelDeviceEventListener paramAccelDeviceEventListener, int paramInt) {
/* 125 */     this.listeners.put(paramAccelDeviceEventListener, Integer.valueOf(paramInt));
/*     */   }
/*     */   private final void remove(AccelDeviceEventListener paramAccelDeviceEventListener) {
/* 128 */     this.listeners.remove(paramAccelDeviceEventListener);
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
/*     */   private final void notifyListeners(int paramInt1, int paramInt2) {
/*     */     HashMap<AccelDeviceEventListener, Integer> hashMap;
/* 148 */     synchronized (this.listeners) {
/* 149 */       hashMap = new HashMap<>(this.listeners);
/*     */     } 
/*     */ 
/*     */     
/* 153 */     Set<AccelDeviceEventListener> set = hashMap.keySet();
/* 154 */     Iterator<AccelDeviceEventListener> iterator = set.iterator();
/* 155 */     while (iterator.hasNext()) {
/* 156 */       AccelDeviceEventListener accelDeviceEventListener = iterator.next();
/* 157 */       Integer integer = hashMap.get(accelDeviceEventListener);
/*     */       
/* 159 */       if (integer != null && integer.intValue() != paramInt2) {
/*     */         continue;
/*     */       }
/* 162 */       if (paramInt1 == 0) {
/* 163 */         accelDeviceEventListener.onDeviceReset(); continue;
/* 164 */       }  if (paramInt1 == 1)
/* 165 */         accelDeviceEventListener.onDeviceDispose(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/hw/AccelDeviceEventNotifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */