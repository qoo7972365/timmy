/*    */ package sun.security.util.math;
/*    */ 
/*    */ import java.math.BigInteger;
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
/*    */ public interface IntegerFieldModuloP
/*    */ {
/*    */   BigInteger getSize();
/*    */   
/*    */   ImmutableIntegerModuloP get0();
/*    */   
/*    */   ImmutableIntegerModuloP get1();
/*    */   
/*    */   ImmutableIntegerModuloP getElement(BigInteger paramBigInteger);
/*    */   
/*    */   SmallValue getSmallValue(int paramInt);
/*    */   
/*    */   default ImmutableIntegerModuloP getElement(byte[] paramArrayOfbyte) {
/* 95 */     return getElement(paramArrayOfbyte, 0, paramArrayOfbyte.length, (byte)0);
/*    */   }
/*    */   
/*    */   ImmutableIntegerModuloP getElement(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, byte paramByte);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/math/IntegerFieldModuloP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */