/*    */ package org.omg.PortableServer.POAManagerPackage;
/*    */ 
/*    */ import org.omg.CORBA.BAD_PARAM;
/*    */ import org.omg.CORBA.portable.IDLEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class State
/*    */   implements IDLEntity
/*    */ {
/*    */   private int __value;
/* 18 */   private static int __size = 4;
/* 19 */   private static State[] __array = new State[__size];
/*    */   
/*    */   public static final int _HOLDING = 0;
/* 22 */   public static final State HOLDING = new State(0);
/*    */   public static final int _ACTIVE = 1;
/* 24 */   public static final State ACTIVE = new State(1);
/*    */   public static final int _DISCARDING = 2;
/* 26 */   public static final State DISCARDING = new State(2);
/*    */   public static final int _INACTIVE = 3;
/* 28 */   public static final State INACTIVE = new State(3);
/*    */ 
/*    */   
/*    */   public int value() {
/* 32 */     return this.__value;
/*    */   }
/*    */ 
/*    */   
/*    */   public static State from_int(int paramInt) {
/* 37 */     if (paramInt >= 0 && paramInt < __size) {
/* 38 */       return __array[paramInt];
/*    */     }
/* 40 */     throw new BAD_PARAM();
/*    */   }
/*    */ 
/*    */   
/*    */   protected State(int paramInt) {
/* 45 */     this.__value = paramInt;
/* 46 */     __array[this.__value] = this;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/POAManagerPackage/State.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */