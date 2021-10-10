/*    */ package com.sun.corba.se.spi.orbutil.closure;
/*    */ 
/*    */ import com.sun.corba.se.impl.orbutil.closure.Constant;
/*    */ import com.sun.corba.se.impl.orbutil.closure.Future;
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
/*    */ public abstract class ClosureFactory
/*    */ {
/*    */   public static Closure makeConstant(Object paramObject) {
/* 36 */     return (Closure)new Constant(paramObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Closure makeFuture(Closure paramClosure) {
/* 41 */     return (Closure)new Future(paramClosure);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orbutil/closure/ClosureFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */