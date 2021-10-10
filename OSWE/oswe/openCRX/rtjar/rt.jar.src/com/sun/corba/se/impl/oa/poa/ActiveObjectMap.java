/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.omg.PortableServer.POAPackage.WrongPolicy;
/*     */ import org.omg.PortableServer.Servant;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ActiveObjectMap
/*     */ {
/*     */   protected POAImpl poa;
/*     */   private Map keyToEntry;
/*     */   private Map entryToServant;
/*     */   private Map servantToEntry;
/*     */   
/*     */   public static class Key
/*     */   {
/*     */     public byte[] id;
/*     */     
/*     */     Key(byte[] param1ArrayOfbyte) {
/*  51 */       this.id = param1ArrayOfbyte;
/*     */     }
/*     */     
/*     */     public String toString() {
/*  55 */       StringBuffer stringBuffer = new StringBuffer();
/*  56 */       for (byte b = 0; b < this.id.length; b++) {
/*  57 */         stringBuffer.append(Integer.toString(this.id[b], 16));
/*  58 */         if (b != this.id.length - 1)
/*  59 */           stringBuffer.append(":"); 
/*     */       } 
/*  61 */       return stringBuffer.toString();
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/*  65 */       if (!(param1Object instanceof Key))
/*  66 */         return false; 
/*  67 */       Key key = (Key)param1Object;
/*  68 */       if (key.id.length != this.id.length)
/*  69 */         return false; 
/*  70 */       for (byte b = 0; b < this.id.length; b++) {
/*  71 */         if (this.id[b] != key.id[b])
/*  72 */           return false; 
/*  73 */       }  return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  78 */       int i = 0;
/*  79 */       for (byte b = 0; b < this.id.length; b++)
/*  80 */         i = 31 * i + this.id[b]; 
/*  81 */       return i;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ActiveObjectMap(POAImpl paramPOAImpl) {
/* 100 */     this.keyToEntry = new HashMap<>();
/* 101 */     this.entryToServant = new HashMap<>();
/* 102 */     this.servantToEntry = new HashMap<>();
/*     */     this.poa = paramPOAImpl;
/*     */   }
/*     */   
/* 106 */   public final boolean contains(Servant paramServant) { return this.servantToEntry.containsKey(paramServant); }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean containsKey(Key paramKey) {
/* 111 */     return this.keyToEntry.containsKey(paramKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final AOMEntry get(Key paramKey) {
/* 119 */     AOMEntry aOMEntry = (AOMEntry)this.keyToEntry.get(paramKey);
/* 120 */     if (aOMEntry == null) {
/* 121 */       aOMEntry = new AOMEntry(this.poa);
/* 122 */       putEntry(paramKey, aOMEntry);
/*     */     } 
/*     */     
/* 125 */     return aOMEntry; } public static ActiveObjectMap create(POAImpl paramPOAImpl, boolean paramBoolean) {
/*     */     if (paramBoolean)
/*     */       return new MultipleObjectMap(paramPOAImpl); 
/*     */     return new SingleObjectMap(paramPOAImpl);
/*     */   } public final Servant getServant(AOMEntry paramAOMEntry) {
/* 130 */     return (Servant)this.entryToServant.get(paramAOMEntry);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Key getKey(AOMEntry paramAOMEntry) throws WrongPolicy;
/*     */   
/*     */   public Key getKey(Servant paramServant) throws WrongPolicy {
/* 137 */     AOMEntry aOMEntry = (AOMEntry)this.servantToEntry.get(paramServant);
/* 138 */     return getKey(aOMEntry);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void putEntry(Key paramKey, AOMEntry paramAOMEntry) {
/* 143 */     this.keyToEntry.put(paramKey, paramAOMEntry);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void putServant(Servant paramServant, AOMEntry paramAOMEntry) {
/* 148 */     this.entryToServant.put(paramAOMEntry, paramServant);
/* 149 */     this.servantToEntry.put(paramServant, paramAOMEntry);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void removeEntry(AOMEntry paramAOMEntry, Key paramKey);
/*     */   
/*     */   public final void remove(Key paramKey) {
/* 156 */     AOMEntry aOMEntry = (AOMEntry)this.keyToEntry.remove(paramKey);
/* 157 */     Servant servant = (Servant)this.entryToServant.remove(aOMEntry);
/* 158 */     if (servant != null) {
/* 159 */       this.servantToEntry.remove(servant);
/*     */     }
/* 161 */     removeEntry(aOMEntry, paramKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract boolean hasMultipleIDs(AOMEntry paramAOMEntry);
/*     */   
/*     */   protected void clear() {
/* 168 */     this.keyToEntry.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public final Set keySet() {
/* 173 */     return this.keyToEntry.keySet();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/ActiveObjectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */