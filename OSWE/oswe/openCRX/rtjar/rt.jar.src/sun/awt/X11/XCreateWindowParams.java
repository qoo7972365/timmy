/*    */ package sun.awt.X11;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
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
/*    */ public class XCreateWindowParams
/*    */   extends HashMap
/*    */ {
/*    */   public XCreateWindowParams() {}
/*    */   
/*    */   public XCreateWindowParams(Object[] paramArrayOfObject) {
/* 36 */     init(paramArrayOfObject);
/*    */   }
/*    */   private void init(Object[] paramArrayOfObject) {
/* 39 */     if (paramArrayOfObject.length % 2 != 0) {
/* 40 */       throw new IllegalArgumentException("Map size should be devisible by two");
/*    */     }
/* 42 */     for (byte b = 0; b < paramArrayOfObject.length; b += 2) {
/* 43 */       put((K)paramArrayOfObject[b], (V)paramArrayOfObject[b + 1]);
/*    */     }
/*    */   }
/*    */   
/*    */   public XCreateWindowParams putIfNull(Object paramObject1, Object paramObject2) {
/* 48 */     if (!containsKey(paramObject1)) {
/* 49 */       put((K)paramObject1, (V)paramObject2);
/*    */     }
/* 51 */     return this;
/*    */   }
/*    */   public XCreateWindowParams putIfNull(Object paramObject, int paramInt) {
/* 54 */     if (!containsKey(paramObject)) {
/* 55 */       put((K)paramObject, (V)Integer.valueOf(paramInt));
/*    */     }
/* 57 */     return this;
/*    */   }
/*    */   public XCreateWindowParams putIfNull(Object paramObject, long paramLong) {
/* 60 */     if (!containsKey(paramObject)) {
/* 61 */       put((K)paramObject, (V)Long.valueOf(paramLong));
/*    */     }
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public XCreateWindowParams add(Object paramObject1, Object paramObject2) {
/* 67 */     put((K)paramObject1, (V)paramObject2);
/* 68 */     return this;
/*    */   }
/*    */   public XCreateWindowParams add(Object paramObject, int paramInt) {
/* 71 */     put((K)paramObject, (V)Integer.valueOf(paramInt));
/* 72 */     return this;
/*    */   }
/*    */   public XCreateWindowParams add(Object paramObject, long paramLong) {
/* 75 */     put((K)paramObject, (V)Long.valueOf(paramLong));
/* 76 */     return this;
/*    */   }
/*    */   public XCreateWindowParams delete(Object paramObject) {
/* 79 */     remove(paramObject);
/* 80 */     return this;
/*    */   }
/*    */   public String toString() {
/* 83 */     StringBuffer stringBuffer = new StringBuffer();
/* 84 */     Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();
/* 85 */     while (iterator.hasNext()) {
/* 86 */       Map.Entry entry = iterator.next();
/* 87 */       stringBuffer.append((new StringBuilder()).append(entry.getKey()).append(": ").append(entry.getValue()).append("\n").toString());
/*    */     } 
/* 89 */     return stringBuffer.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XCreateWindowParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */