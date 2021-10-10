/*    */ package com.sun.beans.finder;
/*    */ 
/*    */ import java.util.HashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class PrimitiveTypeMap
/*    */ {
/*    */   static Class<?> getType(String paramString) {
/* 48 */     return map.get(paramString);
/*    */   }
/*    */   
/* 51 */   private static final Map<String, Class<?>> map = new HashMap<>(9);
/*    */   
/*    */   static {
/* 54 */     map.put(boolean.class.getName(), boolean.class);
/* 55 */     map.put(char.class.getName(), char.class);
/* 56 */     map.put(byte.class.getName(), byte.class);
/* 57 */     map.put(short.class.getName(), short.class);
/* 58 */     map.put(int.class.getName(), int.class);
/* 59 */     map.put(long.class.getName(), long.class);
/* 60 */     map.put(float.class.getName(), float.class);
/* 61 */     map.put(double.class.getName(), double.class);
/* 62 */     map.put(void.class.getName(), void.class);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/finder/PrimitiveTypeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */