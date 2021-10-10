/*    */ package com.sun.naming.internal;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
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
/*    */ class NamedWeakReference<T>
/*    */   extends WeakReference<T>
/*    */ {
/*    */   private final String name;
/*    */   
/*    */   NamedWeakReference(T paramT, String paramString) {
/* 41 */     super(paramT);
/* 42 */     this.name = paramString;
/*    */   }
/*    */   
/*    */   String getName() {
/* 46 */     return this.name;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/naming/internal/NamedWeakReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */