/*    */ package sun.java2d.xr;
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
/*    */ public class MutableInteger
/*    */ {
/*    */   private int value;
/*    */   
/*    */   public MutableInteger(int paramInt) {
/* 38 */     setValue(paramInt);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 42 */     return getValue();
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 46 */     return (paramObject instanceof MutableInteger && ((MutableInteger)paramObject)
/* 47 */       .getValue() == getValue());
/*    */   }
/*    */   
/*    */   public void setValue(int paramInt) {
/* 51 */     this.value = paramInt;
/*    */   }
/*    */   
/*    */   public int getValue() {
/* 55 */     return this.value;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/MutableInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */