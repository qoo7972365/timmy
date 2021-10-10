/*    */ package org.omg.PortableServer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IdUniquenessPolicyValue
/*    */   implements IDLEntity
/*    */ {
/*    */   private int __value;
/* 22 */   private static int __size = 2;
/* 23 */   private static IdUniquenessPolicyValue[] __array = new IdUniquenessPolicyValue[__size];
/*    */   
/*    */   public static final int _UNIQUE_ID = 0;
/* 26 */   public static final IdUniquenessPolicyValue UNIQUE_ID = new IdUniquenessPolicyValue(0);
/*    */   public static final int _MULTIPLE_ID = 1;
/* 28 */   public static final IdUniquenessPolicyValue MULTIPLE_ID = new IdUniquenessPolicyValue(1);
/*    */ 
/*    */   
/*    */   public int value() {
/* 32 */     return this.__value;
/*    */   }
/*    */ 
/*    */   
/*    */   public static IdUniquenessPolicyValue from_int(int paramInt) {
/* 37 */     if (paramInt >= 0 && paramInt < __size) {
/* 38 */       return __array[paramInt];
/*    */     }
/* 40 */     throw new BAD_PARAM();
/*    */   }
/*    */ 
/*    */   
/*    */   protected IdUniquenessPolicyValue(int paramInt) {
/* 45 */     this.__value = paramInt;
/* 46 */     __array[this.__value] = this;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/IdUniquenessPolicyValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */