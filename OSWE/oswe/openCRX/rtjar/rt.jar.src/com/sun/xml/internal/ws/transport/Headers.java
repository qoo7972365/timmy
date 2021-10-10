/*     */ package com.sun.xml.internal.ws.transport;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Headers
/*     */   extends TreeMap<String, List<String>>
/*     */ {
/*     */   public Headers() {
/*  72 */     super(INSTANCE);
/*     */   }
/*     */   
/*  75 */   private static final InsensitiveComparator INSTANCE = new InsensitiveComparator();
/*     */   
/*     */   private static final class InsensitiveComparator
/*     */     implements Comparator<String>, Serializable {
/*     */     public int compare(String o1, String o2) {
/*  80 */       if (o1 == null && o2 == null)
/*  81 */         return 0; 
/*  82 */       if (o1 == null)
/*  83 */         return -1; 
/*  84 */       if (o2 == null)
/*  85 */         return 1; 
/*  86 */       return o1.compareToIgnoreCase(o2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private InsensitiveComparator() {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String key, String value) {
/*  98 */     List<String> list = get(key);
/*  99 */     if (list == null) {
/* 100 */       list = new LinkedList<>();
/* 101 */       put(key, list);
/*     */     } 
/* 103 */     list.add(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFirst(String key) {
/* 114 */     List<String> l = get(key);
/* 115 */     return (l == null) ? null : l.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String key, String value) {
/* 126 */     LinkedList<String> l = new LinkedList<>();
/* 127 */     l.add(value);
/* 128 */     put(key, l);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/Headers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */