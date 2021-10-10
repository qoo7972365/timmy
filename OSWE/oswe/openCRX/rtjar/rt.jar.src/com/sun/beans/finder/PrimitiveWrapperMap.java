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
/*    */ public final class PrimitiveWrapperMap
/*    */ {
/*    */   static void replacePrimitivesWithWrappers(Class<?>[] paramArrayOfClass) {
/* 47 */     for (byte b = 0; b < paramArrayOfClass.length; b++) {
/* 48 */       if (paramArrayOfClass[b] != null && 
/* 49 */         paramArrayOfClass[b].isPrimitive()) {
/* 50 */         paramArrayOfClass[b] = getType(paramArrayOfClass[b].getName());
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Class<?> getType(String paramString) {
/* 64 */     return map.get(paramString);
/*    */   }
/*    */   
/* 67 */   private static final Map<String, Class<?>> map = new HashMap<>(9);
/*    */   
/*    */   static {
/* 70 */     map.put(boolean.class.getName(), Boolean.class);
/* 71 */     map.put(char.class.getName(), Character.class);
/* 72 */     map.put(byte.class.getName(), Byte.class);
/* 73 */     map.put(short.class.getName(), Short.class);
/* 74 */     map.put(int.class.getName(), Integer.class);
/* 75 */     map.put(long.class.getName(), Long.class);
/* 76 */     map.put(float.class.getName(), Float.class);
/* 77 */     map.put(double.class.getName(), Double.class);
/* 78 */     map.put(void.class.getName(), Void.class);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/finder/PrimitiveWrapperMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */