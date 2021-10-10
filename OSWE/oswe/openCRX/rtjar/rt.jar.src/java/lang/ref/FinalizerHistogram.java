/*    */ package java.lang.ref;
/*    */ 
/*    */ import java.lang.ref.Finalizer;
/*    */ import java.lang.ref.FinalizerHistogram;
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.ReferenceQueue;
/*    */ import java.util.Arrays;
/*    */ import java.util.Comparator;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
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
/*    */ final class FinalizerHistogram
/*    */ {
/*    */   private static final class Entry
/*    */   {
/*    */     private int instanceCount;
/*    */     private final String className;
/*    */     
/*    */     int getInstanceCount() {
/* 46 */       return this.instanceCount;
/*    */     }
/*    */     
/*    */     void increment() {
/* 50 */       this.instanceCount++;
/*    */     }
/*    */     
/*    */     Entry(String param1String) {
/* 54 */       this.className = param1String;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static Entry[] getFinalizerHistogram() {
/* 62 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 63 */     ReferenceQueue<Object> referenceQueue = Finalizer.getQueue();
/* 64 */     referenceQueue.forEach(paramReference -> {
/*    */           Object object = paramReference.get();
/*    */ 
/*    */           
/*    */           if (object != null) {
/*    */             ((Entry)paramMap.computeIfAbsent(object.getClass().getName(), Entry::new)).increment();
/*    */             
/*    */             object = null;
/*    */           } 
/*    */         });
/*    */     
/* 75 */     Entry[] arrayOfEntry = (Entry[])hashMap.values().toArray((Object[])new Entry[hashMap.size()]);
/* 76 */     Arrays.sort(arrayOfEntry, 
/* 77 */         Comparator.<Entry>comparingInt(Entry::getInstanceCount).reversed());
/* 78 */     return arrayOfEntry;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/ref/FinalizerHistogram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */