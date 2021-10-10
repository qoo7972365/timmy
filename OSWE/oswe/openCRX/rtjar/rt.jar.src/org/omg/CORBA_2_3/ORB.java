/*    */ package org.omg.CORBA_2_3;
/*    */ 
/*    */ import org.omg.CORBA.BAD_PARAM;
/*    */ import org.omg.CORBA.NO_IMPLEMENT;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CORBA.portable.ValueFactory;
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
/*    */ public abstract class ORB
/*    */   extends ORB
/*    */ {
/*    */   public ValueFactory register_value_factory(String paramString, ValueFactory paramValueFactory) {
/* 46 */     throw new NO_IMPLEMENT();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void unregister_value_factory(String paramString) {
/* 55 */     throw new NO_IMPLEMENT();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ValueFactory lookup_value_factory(String paramString) {
/* 64 */     throw new NO_IMPLEMENT();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object get_value_def(String paramString) throws BAD_PARAM {
/* 76 */     throw new NO_IMPLEMENT();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void set_delegate(Object paramObject) {
/* 85 */     throw new NO_IMPLEMENT();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA_2_3/ORB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */