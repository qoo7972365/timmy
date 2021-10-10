/*     */ package com.sun.xml.internal.bind.v2.runtime;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RuntimeUtil
/*     */ {
/*     */   public static final Map<Class, Class> boxToPrimitive;
/*     */   public static final Map<Class, Class> primitiveToBox;
/*     */   
/*     */   public static final class ToStringAdapter
/*     */     extends XmlAdapter<String, Object>
/*     */   {
/*     */     public Object unmarshal(String s) {
/*  43 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public String marshal(Object o) {
/*  47 */       if (o == null) return null; 
/*  48 */       return o.toString();
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
/*     */   static {
/*  66 */     Map<Class<?>, Class<?>> b = new HashMap<>();
/*  67 */     b.put(byte.class, Byte.class);
/*  68 */     b.put(short.class, Short.class);
/*  69 */     b.put(int.class, Integer.class);
/*  70 */     b.put(long.class, Long.class);
/*  71 */     b.put(char.class, Character.class);
/*  72 */     b.put(boolean.class, Boolean.class);
/*  73 */     b.put(float.class, Float.class);
/*  74 */     b.put(double.class, Double.class);
/*  75 */     b.put(void.class, Void.class);
/*     */     
/*  77 */     primitiveToBox = Collections.unmodifiableMap(b);
/*     */     
/*  79 */     Map<Class<?>, Class<?>> p = new HashMap<>();
/*  80 */     for (Map.Entry<Class<?>, Class<?>> e : b.entrySet()) {
/*  81 */       p.put(e.getValue(), e.getKey());
/*     */     }
/*  83 */     boxToPrimitive = Collections.unmodifiableMap(p);
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
/*     */   private static String getTypeName(Object o) {
/* 126 */     return o.getClass().getName();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/RuntimeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */