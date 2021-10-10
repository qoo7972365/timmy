/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.omg.PortableServer.POAPackage.WrongPolicy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MultipleObjectMap
/*     */   extends ActiveObjectMap
/*     */ {
/* 218 */   private Map entryToKeys = new HashMap<>();
/*     */ 
/*     */   
/*     */   public MultipleObjectMap(POAImpl paramPOAImpl) {
/* 222 */     super(paramPOAImpl);
/*     */   }
/*     */ 
/*     */   
/*     */   public ActiveObjectMap.Key getKey(AOMEntry paramAOMEntry) throws WrongPolicy {
/* 227 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void putEntry(ActiveObjectMap.Key paramKey, AOMEntry paramAOMEntry) {
/* 232 */     super.putEntry(paramKey, paramAOMEntry);
/*     */     
/* 234 */     Set<ActiveObjectMap.Key> set = (Set)this.entryToKeys.get(paramAOMEntry);
/* 235 */     if (set == null) {
/* 236 */       set = new HashSet();
/* 237 */       this.entryToKeys.put(paramAOMEntry, set);
/*     */     } 
/* 239 */     set.add(paramKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMultipleIDs(AOMEntry paramAOMEntry) {
/* 244 */     Set set = (Set)this.entryToKeys.get(paramAOMEntry);
/* 245 */     if (set == null)
/* 246 */       return false; 
/* 247 */     return (set.size() > 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void removeEntry(AOMEntry paramAOMEntry, ActiveObjectMap.Key paramKey) {
/* 252 */     Set set = (Set)this.entryToKeys.get(paramAOMEntry);
/* 253 */     if (set != null) {
/* 254 */       set.remove(paramKey);
/* 255 */       if (set.isEmpty()) {
/* 256 */         this.entryToKeys.remove(paramAOMEntry);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 262 */     super.clear();
/* 263 */     this.entryToKeys.clear();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/MultipleObjectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */