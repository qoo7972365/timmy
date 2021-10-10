/*    */ package com.sun.xml.internal.org.jvnet.mimepull;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
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
/*    */ final class FinalArrayList<T>
/*    */   extends ArrayList<T>
/*    */ {
/*    */   public FinalArrayList(int initialCapacity) {
/* 41 */     super(initialCapacity);
/*    */   }
/*    */ 
/*    */   
/*    */   public FinalArrayList() {}
/*    */   
/*    */   public FinalArrayList(Collection<? extends T> ts) {
/* 48 */     super(ts);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/FinalArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */