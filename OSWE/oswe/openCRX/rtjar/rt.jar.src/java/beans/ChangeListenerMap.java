/*     */ package java.beans;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.EventListener;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class ChangeListenerMap<L extends EventListener>
/*     */ {
/*     */   private Map<String, L[]> map;
/*     */   
/*     */   protected abstract L[] newArray(int paramInt);
/*     */   
/*     */   protected abstract L newProxy(String paramString, L paramL);
/*     */   
/*     */   public final synchronized void add(String paramString, L paramL) {
/*  78 */     if (this.map == null) {
/*  79 */       this.map = new HashMap<>();
/*     */     }
/*  81 */     EventListener[] arrayOfEventListener = (EventListener[])this.map.get(paramString);
/*  82 */     byte b = (arrayOfEventListener != null) ? arrayOfEventListener.length : 0;
/*     */ 
/*     */ 
/*     */     
/*  86 */     L[] arrayOfL = newArray(b + 1);
/*  87 */     arrayOfL[b] = paramL;
/*  88 */     if (arrayOfEventListener != null) {
/*  89 */       System.arraycopy(arrayOfEventListener, 0, arrayOfL, 0, b);
/*     */     }
/*  91 */     this.map.put(paramString, arrayOfL);
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
/*     */   public final synchronized void remove(String paramString, L paramL) {
/* 103 */     if (this.map != null) {
/* 104 */       EventListener[] arrayOfEventListener = (EventListener[])this.map.get(paramString);
/* 105 */       if (arrayOfEventListener != null) {
/* 106 */         for (byte b = 0; b < arrayOfEventListener.length; b++) {
/* 107 */           if (paramL.equals(arrayOfEventListener[b])) {
/* 108 */             int i = arrayOfEventListener.length - 1;
/* 109 */             if (i > 0) {
/* 110 */               L[] arrayOfL = newArray(i);
/* 111 */               System.arraycopy(arrayOfEventListener, 0, arrayOfL, 0, b);
/* 112 */               System.arraycopy(arrayOfEventListener, b + 1, arrayOfL, b, i - b);
/* 113 */               this.map.put(paramString, arrayOfL);
/*     */               break;
/*     */             } 
/* 116 */             this.map.remove(paramString);
/* 117 */             if (this.map.isEmpty()) {
/* 118 */               this.map = null;
/*     */             }
/*     */             break;
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
/*     */   public final synchronized L[] get(String paramString) {
/* 135 */     return (this.map != null) ? this.map
/* 136 */       .get(paramString) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(String paramString, L[] paramArrayOfL) {
/* 147 */     if (paramArrayOfL != null) {
/* 148 */       if (this.map == null) {
/* 149 */         this.map = new HashMap<>();
/*     */       }
/* 151 */       this.map.put(paramString, paramArrayOfL);
/*     */     }
/* 153 */     else if (this.map != null) {
/* 154 */       this.map.remove(paramString);
/* 155 */       if (this.map.isEmpty()) {
/* 156 */         this.map = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized L[] getListeners() {
/* 167 */     if (this.map == null) {
/* 168 */       return newArray(0);
/*     */     }
/* 170 */     ArrayList<EventListener> arrayList = new ArrayList();
/*     */     
/* 172 */     EventListener[] arrayOfEventListener = (EventListener[])this.map.get(null);
/* 173 */     if (arrayOfEventListener != null) {
/* 174 */       for (EventListener eventListener : arrayOfEventListener) {
/* 175 */         arrayList.add(eventListener);
/*     */       }
/*     */     }
/* 178 */     for (Map.Entry<String, L> entry : this.map.entrySet()) {
/* 179 */       String str = (String)entry.getKey();
/* 180 */       if (str != null) {
/* 181 */         for (EventListener eventListener : (EventListener[])entry.getValue()) {
/* 182 */           arrayList.add((EventListener)newProxy(str, (L)eventListener));
/*     */         }
/*     */       }
/*     */     } 
/* 186 */     return (L[])arrayList.<EventListener>toArray((EventListener[])newArray(arrayList.size()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final L[] getListeners(String paramString) {
/* 196 */     if (paramString != null) {
/* 197 */       L[] arrayOfL = get(paramString);
/* 198 */       if (arrayOfL != null) {
/* 199 */         return (L[])arrayOfL.clone();
/*     */       }
/*     */     } 
/* 202 */     return newArray(0);
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
/*     */   public final synchronized boolean hasListeners(String paramString) {
/* 214 */     if (this.map == null) {
/* 215 */       return false;
/*     */     }
/* 217 */     EventListener[] arrayOfEventListener = (EventListener[])this.map.get(null);
/* 218 */     return (arrayOfEventListener != null || (paramString != null && null != this.map.get(paramString)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Set<Map.Entry<String, L[]>> getEntries() {
/* 229 */     return (this.map != null) ? this.map
/* 230 */       .entrySet() : 
/* 231 */       Collections.<Map.Entry<String, L[]>>emptySet();
/*     */   }
/*     */   
/*     */   public abstract L extract(L paramL);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/ChangeListenerMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */