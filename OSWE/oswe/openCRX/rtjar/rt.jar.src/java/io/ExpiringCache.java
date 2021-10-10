/*     */ package java.io;
/*     */ 
/*     */ import java.util.LinkedHashMap;
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
/*     */ class ExpiringCache
/*     */ {
/*     */   private long millisUntilExpiration;
/*     */   private Map<String, Entry> map;
/*     */   private int queryCount;
/*  41 */   private int queryOverflow = 300;
/*  42 */   private int MAX_ENTRIES = 200;
/*     */   
/*     */   static class Entry {
/*     */     private long timestamp;
/*     */     private String val;
/*     */     
/*     */     Entry(long param1Long, String param1String) {
/*  49 */       this.timestamp = param1Long;
/*  50 */       this.val = param1String;
/*     */     }
/*     */     
/*  53 */     long timestamp() { return this.timestamp; } void setTimestamp(long param1Long) {
/*  54 */       this.timestamp = param1Long;
/*     */     }
/*  56 */     String val() { return this.val; } void setVal(String param1String) {
/*  57 */       this.val = param1String;
/*     */     } }
/*     */   
/*     */   ExpiringCache() {
/*  61 */     this(30000L);
/*     */   }
/*     */ 
/*     */   
/*     */   ExpiringCache(long paramLong) {
/*  66 */     this.millisUntilExpiration = paramLong;
/*  67 */     this.map = new LinkedHashMap<String, Entry>() {
/*     */         protected boolean removeEldestEntry(Map.Entry<String, ExpiringCache.Entry> param1Entry) {
/*  69 */           return (size() > ExpiringCache.this.MAX_ENTRIES);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   synchronized String get(String paramString) {
/*  75 */     if (++this.queryCount >= this.queryOverflow) {
/*  76 */       cleanup();
/*     */     }
/*  78 */     Entry entry = entryFor(paramString);
/*  79 */     if (entry != null) {
/*  80 */       return entry.val();
/*     */     }
/*  82 */     return null;
/*     */   }
/*     */   
/*     */   synchronized void put(String paramString1, String paramString2) {
/*  86 */     if (++this.queryCount >= this.queryOverflow) {
/*  87 */       cleanup();
/*     */     }
/*  89 */     Entry entry = entryFor(paramString1);
/*  90 */     if (entry != null) {
/*  91 */       entry.setTimestamp(System.currentTimeMillis());
/*  92 */       entry.setVal(paramString2);
/*     */     } else {
/*  94 */       this.map.put(paramString1, new Entry(System.currentTimeMillis(), paramString2));
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized void clear() {
/*  99 */     this.map.clear();
/*     */   }
/*     */   
/*     */   private Entry entryFor(String paramString) {
/* 103 */     Entry entry = this.map.get(paramString);
/* 104 */     if (entry != null) {
/* 105 */       long l = System.currentTimeMillis() - entry.timestamp();
/* 106 */       if (l < 0L || l >= this.millisUntilExpiration) {
/* 107 */         this.map.remove(paramString);
/* 108 */         entry = null;
/*     */       } 
/*     */     } 
/* 111 */     return entry;
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 115 */     Set<String> set = this.map.keySet();
/*     */     
/* 117 */     String[] arrayOfString = new String[set.size()];
/* 118 */     byte b1 = 0;
/* 119 */     for (String str : set) {
/* 120 */       arrayOfString[b1++] = str;
/*     */     }
/* 122 */     for (byte b2 = 0; b2 < arrayOfString.length; b2++) {
/* 123 */       entryFor(arrayOfString[b2]);
/*     */     }
/* 125 */     this.queryCount = 0;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/ExpiringCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */