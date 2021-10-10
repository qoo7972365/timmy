/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import javax.management.MalformedObjectNameException;
/*     */ import javax.management.ObjectName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Util
/*     */ {
/*     */   public static ObjectName newObjectName(String paramString) {
/*     */     try {
/*  48 */       return new ObjectName(paramString);
/*  49 */     } catch (MalformedObjectNameException malformedObjectNameException) {
/*  50 */       throw new IllegalArgumentException(malformedObjectNameException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static <K, V> Map<K, V> newMap() {
/*  55 */     return new HashMap<>();
/*     */   }
/*     */   
/*     */   static <K, V> Map<K, V> newSynchronizedMap() {
/*  59 */     return Collections.synchronizedMap(newMap());
/*     */   }
/*     */   
/*     */   static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
/*  63 */     return new IdentityHashMap<>();
/*     */   }
/*     */   
/*     */   static <K, V> Map<K, V> newSynchronizedIdentityHashMap() {
/*  67 */     IdentityHashMap<?, ?> identityHashMap = newIdentityHashMap();
/*  68 */     return Collections.synchronizedMap((Map)identityHashMap);
/*     */   }
/*     */   
/*     */   static <K, V> SortedMap<K, V> newSortedMap() {
/*  72 */     return new TreeMap<>();
/*     */   }
/*     */   
/*     */   static <K, V> SortedMap<K, V> newSortedMap(Comparator<? super K> paramComparator) {
/*  76 */     return new TreeMap<>(paramComparator);
/*     */   }
/*     */   
/*     */   static <K, V> Map<K, V> newInsertionOrderMap() {
/*  80 */     return new LinkedHashMap<>();
/*     */   }
/*     */   
/*     */   static <E> Set<E> newSet() {
/*  84 */     return new HashSet<>();
/*     */   }
/*     */   
/*     */   static <E> Set<E> newSet(Collection<E> paramCollection) {
/*  88 */     return new HashSet<>(paramCollection);
/*     */   }
/*     */   
/*     */   static <E> List<E> newList() {
/*  92 */     return new ArrayList<>();
/*     */   }
/*     */   
/*     */   static <E> List<E> newList(Collection<E> paramCollection) {
/*  96 */     return new ArrayList<>(paramCollection);
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
/*     */   public static <T> T cast(Object paramObject) {
/* 110 */     return (T)paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int hashCode(String[] paramArrayOfString, Object[] paramArrayOfObject) {
/* 120 */     int i = 0;
/* 121 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 122 */       int j; Object object = paramArrayOfObject[b];
/*     */       
/* 124 */       if (object == null) {
/* 125 */         j = 0;
/* 126 */       } else if (object instanceof Object[]) {
/* 127 */         j = Arrays.deepHashCode((Object[])object);
/* 128 */       } else if (object.getClass().isArray()) {
/* 129 */         j = Arrays.deepHashCode(new Object[] { object }) - 31;
/*     */       }
/*     */       else {
/*     */         
/* 133 */         j = object.hashCode();
/*     */       } 
/* 135 */       i += paramArrayOfString[b].toLowerCase().hashCode() ^ j;
/*     */     } 
/* 137 */     return i;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean wildmatch(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 186 */     int j = -1, i = j;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 192 */       if (paramInt3 < paramInt4) {
/* 193 */         char c = paramString2.charAt(paramInt3);
/* 194 */         switch (c) {
/*     */           case '?':
/* 196 */             if (paramInt1 == paramInt2)
/*     */               break; 
/* 198 */             paramInt1++;
/* 199 */             paramInt3++;
/*     */             continue;
/*     */           
/*     */           case '*':
/* 203 */             j = ++paramInt3;
/* 204 */             i = paramInt1;
/*     */             continue;
/*     */           default:
/* 207 */             if (paramInt1 < paramInt2 && paramString1.charAt(paramInt1) == c) {
/* 208 */               paramInt1++;
/* 209 */               paramInt3++;
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */         } 
/* 214 */       } else if (paramInt1 == paramInt2) {
/* 215 */         return true;
/*     */       } 
/*     */       
/* 218 */       if (j < 0 || i == paramInt2) {
/* 219 */         return false;
/*     */       }
/*     */       
/* 222 */       paramInt3 = j;
/*     */       
/* 224 */       paramInt1 = ++i;
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
/*     */   public static boolean wildmatch(String paramString1, String paramString2) {
/* 239 */     return wildmatch(paramString1, paramString2, 0, paramString1.length(), 0, paramString2.length());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */