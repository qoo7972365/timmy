/*     */ package sun.security.krb5.internal.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import sun.security.util.BitArray;
/*     */ import sun.security.util.DerOutputStream;
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
/*     */ public class KerberosFlags
/*     */ {
/*     */   BitArray bits;
/*     */   protected static final int BITS_PER_UNIT = 8;
/*     */   
/*     */   public KerberosFlags(int paramInt) throws IllegalArgumentException {
/*  64 */     this.bits = new BitArray(paramInt);
/*     */   }
/*     */   
/*     */   public KerberosFlags(int paramInt, byte[] paramArrayOfbyte) throws IllegalArgumentException {
/*  68 */     this.bits = new BitArray(paramInt, paramArrayOfbyte);
/*  69 */     if (paramInt != 32) {
/*  70 */       this.bits = new BitArray(Arrays.copyOf(this.bits.toBooleanArray(), 32));
/*     */     }
/*     */   }
/*     */   
/*     */   public KerberosFlags(boolean[] paramArrayOfboolean) {
/*  75 */     this
/*     */       
/*  77 */       .bits = new BitArray((paramArrayOfboolean.length == 32) ? paramArrayOfboolean : Arrays.copyOf(paramArrayOfboolean, 32));
/*     */   }
/*     */   
/*     */   public void set(int paramInt, boolean paramBoolean) {
/*  81 */     this.bits.set(paramInt, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean get(int paramInt) {
/*  85 */     return this.bits.get(paramInt);
/*     */   }
/*     */   
/*     */   public boolean[] toBooleanArray() {
/*  89 */     return this.bits.toBooleanArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws IOException {
/*  99 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 100 */     derOutputStream.putUnalignedBitString(this.bits);
/* 101 */     return derOutputStream.toByteArray();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 105 */     return this.bits.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/util/KerberosFlags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */