/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.Config;
/*     */ import sun.security.krb5.KrbException;
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
/*     */ public class KDCOptions
/*     */   extends KerberosFlags
/*     */ {
/*     */   private static final int KDC_OPT_PROXIABLE = 268435456;
/*     */   private static final int KDC_OPT_RENEWABLE_OK = 16;
/*     */   private static final int KDC_OPT_FORWARDABLE = 1073741824;
/*     */   public static final int RESERVED = 0;
/*     */   public static final int FORWARDABLE = 1;
/*     */   public static final int FORWARDED = 2;
/*     */   public static final int PROXIABLE = 3;
/*     */   public static final int PROXY = 4;
/*     */   public static final int ALLOW_POSTDATE = 5;
/*     */   public static final int POSTDATED = 6;
/*     */   public static final int UNUSED7 = 7;
/*     */   public static final int RENEWABLE = 8;
/*     */   public static final int UNUSED9 = 9;
/*     */   public static final int UNUSED10 = 10;
/*     */   public static final int UNUSED11 = 11;
/*     */   public static final int CNAME_IN_ADDL_TKT = 14;
/*     */   public static final int CANONICALIZE = 15;
/*     */   public static final int RENEWABLE_OK = 27;
/*     */   public static final int ENC_TKT_IN_SKEY = 28;
/*     */   public static final int RENEW = 30;
/*     */   public static final int VALIDATE = 31;
/* 149 */   private static final String[] names = new String[] { "RESERVED", "FORWARDABLE", "FORWARDED", "PROXIABLE", "PROXY", "ALLOW_POSTDATE", "POSTDATED", "UNUSED7", "RENEWABLE", "UNUSED9", "UNUSED10", "UNUSED11", null, null, "CNAME_IN_ADDL_TKT", "CANONICALIZE", null, null, null, null, null, null, null, null, null, null, null, "RENEWABLE_OK", "ENC_TKT_IN_SKEY", null, "RENEW", "VALIDATE" };
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
/* 173 */   private boolean DEBUG = Krb5.DEBUG;
/*     */   
/*     */   public static KDCOptions with(int... paramVarArgs) {
/* 176 */     KDCOptions kDCOptions = new KDCOptions();
/* 177 */     for (int i : paramVarArgs) {
/* 178 */       kDCOptions.set(i, true);
/*     */     }
/* 180 */     return kDCOptions;
/*     */   }
/*     */   
/*     */   public KDCOptions() {
/* 184 */     super(32);
/* 185 */     setDefault();
/*     */   }
/*     */   
/*     */   public KDCOptions(int paramInt, byte[] paramArrayOfbyte) throws Asn1Exception {
/* 189 */     super(paramInt, paramArrayOfbyte);
/* 190 */     if (paramInt > paramArrayOfbyte.length * 8 || paramInt > 32) {
/* 191 */       throw new Asn1Exception(502);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KDCOptions(boolean[] paramArrayOfboolean) throws Asn1Exception {
/* 203 */     super(paramArrayOfboolean);
/* 204 */     if (paramArrayOfboolean.length > 32) {
/* 205 */       throw new Asn1Exception(502);
/*     */     }
/*     */   }
/*     */   
/*     */   public KDCOptions(DerValue paramDerValue) throws Asn1Exception, IOException {
/* 210 */     this(paramDerValue.getUnalignedBitString(true).toBooleanArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KDCOptions(byte[] paramArrayOfbyte) {
/* 220 */     super(paramArrayOfbyte.length * 8, paramArrayOfbyte);
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
/*     */ 
/*     */   
/*     */   public static KDCOptions parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 239 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/* 240 */       return null; 
/* 241 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 242 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 243 */       throw new Asn1Exception(906);
/*     */     }
/* 245 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 246 */     return new KDCOptions(derValue2);
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
/*     */   public void set(int paramInt, boolean paramBoolean) throws ArrayIndexOutOfBoundsException {
/* 259 */     super.set(paramInt, paramBoolean);
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
/*     */   public boolean get(int paramInt) throws ArrayIndexOutOfBoundsException {
/* 272 */     return super.get(paramInt);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 276 */     StringBuilder stringBuilder = new StringBuilder();
/* 277 */     stringBuilder.append("KDCOptions: ");
/* 278 */     for (byte b = 0; b < 32; b++) {
/* 279 */       if (get(b)) {
/* 280 */         if (names[b] != null) {
/* 281 */           stringBuilder.append(names[b]).append(",");
/*     */         } else {
/* 283 */           stringBuilder.append(b).append(",");
/*     */         } 
/*     */       }
/*     */     } 
/* 287 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setDefault() {
/*     */     try {
/* 293 */       Config config = Config.getInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 298 */       int i = config.getIntValue(new String[] { "libdefaults", "kdc_default_options" });
/*     */ 
/*     */       
/* 301 */       if ((i & 0x10) == 16) {
/* 302 */         set(27, true);
/*     */       }
/* 304 */       else if (config.getBooleanValue(new String[] { "libdefaults", "renewable" })) {
/* 305 */         set(27, true);
/*     */       } 
/*     */       
/* 308 */       if ((i & 0x10000000) == 268435456) {
/* 309 */         set(3, true);
/*     */       }
/* 311 */       else if (config.getBooleanValue(new String[] { "libdefaults", "proxiable" })) {
/* 312 */         set(3, true);
/*     */       } 
/*     */ 
/*     */       
/* 316 */       if ((i & 0x40000000) == 1073741824) {
/* 317 */         set(1, true);
/*     */       }
/* 319 */       else if (config.getBooleanValue(new String[] { "libdefaults", "forwardable" })) {
/* 320 */         set(1, true);
/*     */       }
/*     */     
/* 323 */     } catch (KrbException krbException) {
/* 324 */       if (this.DEBUG) {
/* 325 */         System.out.println("Exception in getting default values for KDC Options from the configuration ");
/*     */         
/* 327 */         krbException.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/KDCOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */