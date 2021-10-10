/*    */ package com.sun.corba.se.impl.orbutil;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ public class DenseIntMapImpl
/*    */ {
/* 37 */   private ArrayList list = new ArrayList();
/*    */ 
/*    */   
/*    */   private void checkKey(int paramInt) {
/* 41 */     if (paramInt < 0) {
/* 42 */       throw new IllegalArgumentException("Key must be >= 0.");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object get(int paramInt) {
/* 50 */     checkKey(paramInt);
/*    */     
/* 52 */     Object object = null;
/* 53 */     if (paramInt < this.list.size()) {
/* 54 */       object = this.list.get(paramInt);
/*    */     }
/* 56 */     return object;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void set(int paramInt, Object paramObject) {
/* 64 */     checkKey(paramInt);
/* 65 */     extend(paramInt);
/* 66 */     this.list.set(paramInt, paramObject);
/*    */   }
/*    */ 
/*    */   
/*    */   private void extend(int paramInt) {
/* 71 */     if (paramInt >= this.list.size()) {
/* 72 */       this.list.ensureCapacity(paramInt + 1);
/* 73 */       int i = this.list.size();
/* 74 */       while (i++ <= paramInt)
/* 75 */         this.list.add(null); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/DenseIntMapImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */