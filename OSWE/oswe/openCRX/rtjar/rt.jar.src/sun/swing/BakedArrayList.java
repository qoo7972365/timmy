/*    */ package sun.swing;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ public class BakedArrayList
/*    */   extends ArrayList
/*    */ {
/*    */   private int _hashCode;
/*    */   
/*    */   public BakedArrayList(int paramInt) {
/* 53 */     super(paramInt);
/*    */   }
/*    */   
/*    */   public BakedArrayList(List<E> paramList) {
/* 57 */     this(paramList.size()); byte b; int i;
/* 58 */     for (b = 0, i = paramList.size(); b < i; b++) {
/* 59 */       add(paramList.get(b));
/*    */     }
/* 61 */     cacheHashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void cacheHashCode() {
/* 69 */     this._hashCode = 1;
/* 70 */     for (int i = size() - 1; i >= 0; i--) {
/* 71 */       this._hashCode = 31 * this._hashCode + get(i).hashCode();
/*    */     }
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 76 */     return this._hashCode;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 80 */     BakedArrayList bakedArrayList = (BakedArrayList)paramObject;
/* 81 */     int i = size();
/*    */     
/* 83 */     if (bakedArrayList.size() != i) {
/* 84 */       return false;
/*    */     }
/* 86 */     while (i-- > 0) {
/* 87 */       if (!get(i).equals(bakedArrayList.get(i))) {
/* 88 */         return false;
/*    */       }
/*    */     } 
/* 91 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/BakedArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */