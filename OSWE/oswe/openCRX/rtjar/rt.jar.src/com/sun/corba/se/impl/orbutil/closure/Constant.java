/*    */ package com.sun.corba.se.impl.orbutil.closure;
/*    */ 
/*    */ import com.sun.corba.se.spi.orbutil.closure.Closure;
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
/*    */ public class Constant
/*    */   implements Closure
/*    */ {
/*    */   private Object value;
/*    */   
/*    */   public Constant(Object paramObject) {
/* 35 */     this.value = paramObject;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object evaluate() {
/* 40 */     return this.value;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/closure/Constant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */