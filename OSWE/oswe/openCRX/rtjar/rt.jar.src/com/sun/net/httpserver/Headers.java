/*     */ package com.sun.net.httpserver;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import jdk.Exported;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported
/*     */ public class Headers
/*     */   implements Map<String, List<String>>
/*     */ {
/*  66 */   HashMap<String, List<String>> map = new HashMap<>(32);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String normalize(String paramString) {
/*  73 */     if (paramString == null) {
/*  74 */       return null;
/*     */     }
/*  76 */     int i = paramString.length();
/*  77 */     if (i == 0) {
/*  78 */       return paramString;
/*     */     }
/*  80 */     char[] arrayOfChar = paramString.toCharArray();
/*  81 */     if (arrayOfChar[0] >= 'a' && arrayOfChar[0] <= 'z') {
/*  82 */       arrayOfChar[0] = (char)(arrayOfChar[0] - 32);
/*     */     }
/*  84 */     for (byte b = 1; b < i; b++) {
/*  85 */       if (arrayOfChar[b] >= 'A' && arrayOfChar[b] <= 'Z') {
/*  86 */         arrayOfChar[b] = (char)(arrayOfChar[b] + 32);
/*     */       }
/*     */     } 
/*  89 */     return new String(arrayOfChar);
/*     */   }
/*     */   public int size() {
/*  92 */     return this.map.size();
/*     */   } public boolean isEmpty() {
/*  94 */     return this.map.isEmpty();
/*     */   }
/*     */   public boolean containsKey(Object paramObject) {
/*  97 */     if (paramObject == null) {
/*  98 */       return false;
/*     */     }
/* 100 */     if (!(paramObject instanceof String)) {
/* 101 */       return false;
/*     */     }
/* 103 */     return this.map.containsKey(normalize((String)paramObject));
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object paramObject) {
/* 107 */     return this.map.containsValue(paramObject);
/*     */   }
/*     */   
/*     */   public List<String> get(Object paramObject) {
/* 111 */     return this.map.get(normalize((String)paramObject));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFirst(String paramString) {
/* 121 */     List<String> list = this.map.get(normalize(paramString));
/* 122 */     if (list == null) {
/* 123 */       return null;
/*     */     }
/* 125 */     return list.get(0);
/*     */   }
/*     */   
/*     */   public List<String> put(String paramString, List<String> paramList) {
/* 129 */     return this.map.put(normalize(paramString), paramList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String paramString1, String paramString2) {
/* 140 */     String str = normalize(paramString1);
/* 141 */     List<String> list = this.map.get(str);
/* 142 */     if (list == null) {
/* 143 */       list = new LinkedList();
/* 144 */       this.map.put(str, list);
/*     */     } 
/* 146 */     list.add(paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString1, String paramString2) {
/* 157 */     LinkedList<String> linkedList = new LinkedList();
/* 158 */     linkedList.add(paramString2);
/* 159 */     put(paramString1, linkedList);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> remove(Object paramObject) {
/* 164 */     return this.map.remove(normalize((String)paramObject));
/*     */   }
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends List<String>> paramMap) {
/* 168 */     this.map.putAll(paramMap);
/*     */   }
/*     */   public void clear() {
/* 171 */     this.map.clear();
/*     */   } public Set<String> keySet() {
/* 173 */     return this.map.keySet();
/*     */   } public Collection<List<String>> values() {
/* 175 */     return this.map.values();
/*     */   }
/*     */   public Set<Map.Entry<String, List<String>>> entrySet() {
/* 178 */     return this.map.entrySet();
/*     */   }
/*     */   public boolean equals(Object paramObject) {
/* 181 */     return this.map.equals(paramObject);
/*     */   } public int hashCode() {
/* 183 */     return this.map.hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/httpserver/Headers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */