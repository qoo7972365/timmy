/*    */ package sun.misc;
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
/*    */ public abstract class LRUCache<N, V>
/*    */ {
/* 35 */   private V[] oa = null;
/*    */   private final int size;
/*    */   
/*    */   public LRUCache(int paramInt) {
/* 39 */     this.size = paramInt;
/*    */   }
/*    */   
/*    */   protected abstract V create(N paramN);
/*    */   
/*    */   protected abstract boolean hasName(V paramV, N paramN);
/*    */   
/*    */   public static void moveToFront(Object[] paramArrayOfObject, int paramInt) {
/* 47 */     Object object = paramArrayOfObject[paramInt];
/* 48 */     for (int i = paramInt; i > 0; i--)
/* 49 */       paramArrayOfObject[i] = paramArrayOfObject[i - 1]; 
/* 50 */     paramArrayOfObject[0] = object;
/*    */   }
/*    */   
/*    */   public V forName(N paramN) {
/* 54 */     if (this.oa == null) {
/*    */       
/* 56 */       Object[] arrayOfObject = new Object[this.size];
/* 57 */       this.oa = (V[])arrayOfObject;
/*    */     } else {
/* 59 */       for (byte b = 0; b < this.oa.length; b++) {
/* 60 */         V v1 = this.oa[b];
/* 61 */         if (v1 != null)
/*    */         {
/* 63 */           if (hasName(v1, paramN)) {
/* 64 */             if (b > 0)
/* 65 */               moveToFront((Object[])this.oa, b); 
/* 66 */             return v1;
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 72 */     V v = create(paramN);
/* 73 */     this.oa[this.oa.length - 1] = v;
/* 74 */     moveToFront((Object[])this.oa, this.oa.length - 1);
/* 75 */     return v;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/LRUCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */