/*    */ package sun.rmi.server;
/*    */ 
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.util.Map;
/*    */ import java.util.WeakHashMap;
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
/*    */ public abstract class WeakClassHashMap<V>
/*    */ {
/* 49 */   private Map<Class<?>, ValueCell<V>> internalMap = new WeakHashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public V get(Class<?> paramClass) {
/*    */     ValueCell<V> valueCell;
/* 61 */     synchronized (this.internalMap) {
/* 62 */       valueCell = this.internalMap.get(paramClass);
/* 63 */       if (valueCell == null) {
/* 64 */         valueCell = new ValueCell();
/* 65 */         this.internalMap.put(paramClass, valueCell);
/*    */       } 
/*    */     } 
/* 68 */     synchronized (valueCell) {
/* 69 */       V v = null;
/* 70 */       if (valueCell.ref != null) {
/* 71 */         v = valueCell.ref.get();
/*    */       }
/* 73 */       if (v == null) {
/* 74 */         v = computeValue(paramClass);
/* 75 */         valueCell.ref = new SoftReference<>(v);
/*    */       } 
/* 77 */       return v;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract V computeValue(Class<?> paramClass);
/*    */   
/*    */   private static class ValueCell<T> {
/* 84 */     Reference<T> ref = null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/server/WeakClassHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */