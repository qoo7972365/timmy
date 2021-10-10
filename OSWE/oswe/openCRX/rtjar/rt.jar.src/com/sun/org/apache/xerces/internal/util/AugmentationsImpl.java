/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AugmentationsImpl
/*     */   implements Augmentations
/*     */ {
/*  41 */   private AugmentationsItemsContainer fAugmentationsContainer = new SmallContainer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object putItem(String key, Object item) {
/*  54 */     Object oldValue = this.fAugmentationsContainer.putItem(key, item);
/*     */     
/*  56 */     if (oldValue == null && this.fAugmentationsContainer.isFull()) {
/*  57 */       this.fAugmentationsContainer = this.fAugmentationsContainer.expand();
/*     */     }
/*     */     
/*  60 */     return oldValue;
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
/*     */   public Object getItem(String key) {
/*  73 */     return this.fAugmentationsContainer.getItem(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object removeItem(String key) {
/*  83 */     return this.fAugmentationsContainer.removeItem(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration keys() {
/*  91 */     return this.fAugmentationsContainer.keys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllItems() {
/*  98 */     this.fAugmentationsContainer.clear();
/*     */   } abstract class AugmentationsItemsContainer {
/*     */     public abstract Object putItem(Object param1Object1, Object param1Object2); public abstract Object getItem(Object param1Object); public abstract Object removeItem(Object param1Object); public abstract Enumeration keys(); public abstract void clear(); public abstract boolean isFull(); public abstract AugmentationsItemsContainer expand(); }
/*     */   public String toString() {
/* 102 */     return this.fAugmentationsContainer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   class SmallContainer
/*     */     extends AugmentationsItemsContainer
/*     */   {
/*     */     static final int SIZE_LIMIT = 10;
/*     */     
/*     */     final Object[] fAugmentations;
/*     */     
/*     */     int fNumEntries;
/*     */ 
/*     */     
/*     */     SmallContainer() {
/* 117 */       this.fAugmentations = new Object[20];
/* 118 */       this.fNumEntries = 0;
/*     */     }
/*     */     public Enumeration keys() {
/* 121 */       return new SmallContainerKeyEnumeration();
/*     */     }
/*     */     
/*     */     public Object getItem(Object key) {
/* 125 */       for (int i = 0; i < this.fNumEntries * 2; i += 2) {
/* 126 */         if (this.fAugmentations[i].equals(key)) {
/* 127 */           return this.fAugmentations[i + 1];
/*     */         }
/*     */       } 
/*     */       
/* 131 */       return null;
/*     */     }
/*     */     
/*     */     public Object putItem(Object key, Object item) {
/* 135 */       for (int i = 0; i < this.fNumEntries * 2; i += 2) {
/* 136 */         if (this.fAugmentations[i].equals(key)) {
/* 137 */           Object oldValue = this.fAugmentations[i + 1];
/* 138 */           this.fAugmentations[i + 1] = item;
/*     */           
/* 140 */           return oldValue;
/*     */         } 
/*     */       } 
/*     */       
/* 144 */       this.fAugmentations[this.fNumEntries * 2] = key;
/* 145 */       this.fAugmentations[this.fNumEntries * 2 + 1] = item;
/* 146 */       this.fNumEntries++;
/*     */       
/* 148 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object removeItem(Object key) {
/* 153 */       for (int i = 0; i < this.fNumEntries * 2; i += 2) {
/* 154 */         if (this.fAugmentations[i].equals(key)) {
/* 155 */           Object oldValue = this.fAugmentations[i + 1];
/*     */           int j;
/* 157 */           for (j = i; j < this.fNumEntries * 2 - 2; j += 2) {
/* 158 */             this.fAugmentations[j] = this.fAugmentations[j + 2];
/* 159 */             this.fAugmentations[j + 1] = this.fAugmentations[j + 3];
/*     */           } 
/*     */           
/* 162 */           this.fAugmentations[this.fNumEntries * 2 - 2] = null;
/* 163 */           this.fAugmentations[this.fNumEntries * 2 - 1] = null;
/* 164 */           this.fNumEntries--;
/*     */           
/* 166 */           return oldValue;
/*     */         } 
/*     */       } 
/*     */       
/* 170 */       return null;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 174 */       for (int i = 0; i < this.fNumEntries * 2; i += 2) {
/* 175 */         this.fAugmentations[i] = null;
/* 176 */         this.fAugmentations[i + 1] = null;
/*     */       } 
/*     */       
/* 179 */       this.fNumEntries = 0;
/*     */     }
/*     */     
/*     */     public boolean isFull() {
/* 183 */       return (this.fNumEntries == 10);
/*     */     }
/*     */     
/*     */     public AugmentationsImpl.AugmentationsItemsContainer expand() {
/* 187 */       AugmentationsImpl.LargeContainer expandedContainer = new AugmentationsImpl.LargeContainer();
/*     */       
/* 189 */       for (int i = 0; i < this.fNumEntries * 2; i += 2) {
/* 190 */         expandedContainer.putItem(this.fAugmentations[i], this.fAugmentations[i + 1]);
/*     */       }
/*     */ 
/*     */       
/* 194 */       return expandedContainer;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 198 */       StringBuilder buff = new StringBuilder();
/* 199 */       buff.append("SmallContainer - fNumEntries == ").append(this.fNumEntries);
/*     */       
/* 201 */       for (int i = 0; i < 20; i += 2) {
/* 202 */         buff.append("\nfAugmentations[")
/* 203 */           .append(i)
/* 204 */           .append("] == ")
/* 205 */           .append(this.fAugmentations[i])
/* 206 */           .append("; fAugmentations[")
/* 207 */           .append(i + 1)
/* 208 */           .append("] == ")
/* 209 */           .append(this.fAugmentations[i + 1]);
/*     */       }
/*     */       
/* 212 */       return buff.toString();
/*     */     }
/*     */     
/*     */     class SmallContainerKeyEnumeration implements Enumeration {
/* 216 */       Object[] enumArray = new Object[AugmentationsImpl.SmallContainer.this.fNumEntries];
/* 217 */       int next = 0;
/*     */       
/*     */       SmallContainerKeyEnumeration() {
/* 220 */         for (int i = 0; i < AugmentationsImpl.SmallContainer.this.fNumEntries; i++) {
/* 221 */           this.enumArray[i] = AugmentationsImpl.SmallContainer.this.fAugmentations[i * 2];
/*     */         }
/*     */       }
/*     */       
/*     */       public boolean hasMoreElements() {
/* 226 */         return (this.next < this.enumArray.length);
/*     */       }
/*     */       
/*     */       public Object nextElement() {
/* 230 */         if (this.next >= this.enumArray.length) {
/* 231 */           throw new NoSuchElementException();
/*     */         }
/*     */         
/* 234 */         Object nextVal = this.enumArray[this.next];
/* 235 */         this.enumArray[this.next] = null;
/* 236 */         this.next++;
/*     */         
/* 238 */         return nextVal;
/*     */       } } }
/*     */   
/*     */   class LargeContainer extends AugmentationsItemsContainer { final Map<Object, Object> fAugmentations;
/*     */     
/*     */     LargeContainer() {
/* 244 */       this.fAugmentations = new HashMap<>();
/*     */     }
/*     */     public Object getItem(Object key) {
/* 247 */       return this.fAugmentations.get(key);
/*     */     }
/*     */     
/*     */     public Object putItem(Object key, Object item) {
/* 251 */       return this.fAugmentations.put(key, item);
/*     */     }
/*     */     
/*     */     public Object removeItem(Object key) {
/* 255 */       return this.fAugmentations.remove(key);
/*     */     }
/*     */     
/*     */     public Enumeration keys() {
/* 259 */       return Collections.enumeration(this.fAugmentations.keySet());
/*     */     }
/*     */     
/*     */     public void clear() {
/* 263 */       this.fAugmentations.clear();
/*     */     }
/*     */     
/*     */     public boolean isFull() {
/* 267 */       return false;
/*     */     }
/*     */     
/*     */     public AugmentationsImpl.AugmentationsItemsContainer expand() {
/* 271 */       return this;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 275 */       StringBuilder buff = new StringBuilder();
/* 276 */       buff.append("LargeContainer");
/* 277 */       for (Object key : this.fAugmentations.keySet()) {
/* 278 */         buff.append("\nkey == ");
/* 279 */         buff.append(key);
/* 280 */         buff.append("; value == ");
/* 281 */         buff.append(this.fAugmentations.get(key));
/*     */       } 
/* 283 */       return buff.toString();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/AugmentationsImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */