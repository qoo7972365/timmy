/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.internal.util.KerberosFlags;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerValue;
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
/*     */ public class APOptions
/*     */   extends KerberosFlags
/*     */ {
/*     */   public APOptions() {
/*  61 */     super(32);
/*     */   }
/*     */   
/*     */   public APOptions(int paramInt) throws Asn1Exception {
/*  65 */     super(32);
/*  66 */     set(paramInt, true);
/*     */   }
/*     */   public APOptions(int paramInt, byte[] paramArrayOfbyte) throws Asn1Exception {
/*  69 */     super(paramInt, paramArrayOfbyte);
/*  70 */     if (paramInt > paramArrayOfbyte.length * 8 || paramInt > 32) {
/*  71 */       throw new Asn1Exception(502);
/*     */     }
/*     */   }
/*     */   
/*     */   public APOptions(boolean[] paramArrayOfboolean) throws Asn1Exception {
/*  76 */     super(paramArrayOfboolean);
/*  77 */     if (paramArrayOfboolean.length > 32) {
/*  78 */       throw new Asn1Exception(502);
/*     */     }
/*     */   }
/*     */   
/*     */   public APOptions(DerValue paramDerValue) throws IOException, Asn1Exception {
/*  83 */     this(paramDerValue.getUnalignedBitString(true).toBooleanArray());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static APOptions parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 100 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/* 101 */       return null; 
/* 102 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 103 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 104 */       throw new Asn1Exception(906);
/*     */     }
/* 106 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 107 */     return new APOptions(derValue2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/APOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */