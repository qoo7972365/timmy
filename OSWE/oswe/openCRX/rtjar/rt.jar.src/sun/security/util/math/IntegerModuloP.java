/*     */ package sun.security.util.math;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface IntegerModuloP
/*     */ {
/*     */   IntegerFieldModuloP getField();
/*     */   
/*     */   BigInteger asBigInteger();
/*     */   
/*     */   ImmutableIntegerModuloP fixed();
/*     */   
/*     */   MutableIntegerModuloP mutable();
/*     */   
/*     */   ImmutableIntegerModuloP add(IntegerModuloP paramIntegerModuloP);
/*     */   
/*     */   ImmutableIntegerModuloP additiveInverse();
/*     */   
/*     */   ImmutableIntegerModuloP multiply(IntegerModuloP paramIntegerModuloP);
/*     */   
/*     */   default byte[] addModPowerTwo(IntegerModuloP paramIntegerModuloP, int paramInt) {
/* 115 */     byte[] arrayOfByte = new byte[paramInt];
/* 116 */     addModPowerTwo(paramIntegerModuloP, arrayOfByte);
/* 117 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addModPowerTwo(IntegerModuloP paramIntegerModuloP, byte[] paramArrayOfbyte);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default byte[] asByteArray(int paramInt) {
/* 139 */     byte[] arrayOfByte = new byte[paramInt];
/* 140 */     asByteArray(arrayOfByte);
/* 141 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void asByteArray(byte[] paramArrayOfbyte);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ImmutableIntegerModuloP multiplicativeInverse() {
/* 159 */     return pow(getField().getSize().subtract(BigInteger.valueOf(2L)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ImmutableIntegerModuloP subtract(IntegerModuloP paramIntegerModuloP) {
/* 169 */     return add(paramIntegerModuloP.additiveInverse());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ImmutableIntegerModuloP square() {
/* 180 */     return multiply(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ImmutableIntegerModuloP pow(BigInteger paramBigInteger) {
/* 191 */     MutableIntegerModuloP mutableIntegerModuloP1 = getField().get1().mutable();
/* 192 */     MutableIntegerModuloP mutableIntegerModuloP2 = mutable();
/* 193 */     int i = paramBigInteger.bitLength();
/* 194 */     for (byte b = 0; b < i; b++) {
/* 195 */       if (paramBigInteger.testBit(b))
/*     */       {
/* 197 */         mutableIntegerModuloP1.setProduct(mutableIntegerModuloP2);
/*     */       }
/* 199 */       mutableIntegerModuloP2.setSquare();
/*     */     } 
/* 201 */     return mutableIntegerModuloP1.fixed();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/math/IntegerModuloP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */