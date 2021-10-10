/*    */ package com.sun.xml.internal.bind.v2.util;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TypeCast
/*    */ {
/*    */   public static <K, V> Map<K, V> checkedCast(Map<?, ?> m, Class<K> keyType, Class<V> valueType) {
/* 38 */     if (m == null)
/* 39 */       return null; 
/* 40 */     for (Map.Entry<?, ?> e : m.entrySet()) {
/* 41 */       if (!keyType.isInstance(e.getKey()))
/* 42 */         throw new ClassCastException(e.getKey().getClass().toString()); 
/* 43 */       if (!valueType.isInstance(e.getValue()))
/* 44 */         throw new ClassCastException(e.getValue().getClass().toString()); 
/*    */     } 
/* 46 */     return (Map)m;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/util/TypeCast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */