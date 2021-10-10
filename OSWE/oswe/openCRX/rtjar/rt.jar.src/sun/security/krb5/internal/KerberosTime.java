/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.time.Instant;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.Config;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
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
/*     */ public class KerberosTime
/*     */ {
/*     */   private final long kerberosTime;
/*     */   private final int microSeconds;
/*  74 */   private static long initMilli = System.currentTimeMillis();
/*  75 */   private static long initMicro = System.nanoTime() / 1000L;
/*     */   
/*  77 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */ 
/*     */   
/*     */   private KerberosTime(long paramLong, int paramInt) {
/*  82 */     this.kerberosTime = paramLong;
/*  83 */     this.microSeconds = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosTime(long paramLong) {
/*  90 */     this(paramLong, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosTime(String paramString) throws Asn1Exception {
/*  96 */     this(toKerberosTime(paramString), 0);
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
/*     */   private static long toKerberosTime(String paramString) throws Asn1Exception {
/* 109 */     if (paramString.length() != 15)
/* 110 */       throw new Asn1Exception(900); 
/* 111 */     if (paramString.charAt(14) != 'Z')
/* 112 */       throw new Asn1Exception(900); 
/* 113 */     int i = Integer.parseInt(paramString.substring(0, 4));
/* 114 */     Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
/* 115 */     calendar.clear();
/* 116 */     calendar.set(i, 
/* 117 */         Integer.parseInt(paramString.substring(4, 6)) - 1, 
/* 118 */         Integer.parseInt(paramString.substring(6, 8)), 
/* 119 */         Integer.parseInt(paramString.substring(8, 10)), 
/* 120 */         Integer.parseInt(paramString.substring(10, 12)), 
/* 121 */         Integer.parseInt(paramString.substring(12, 14)));
/* 122 */     return calendar.getTimeInMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosTime(Date paramDate) {
/* 129 */     this(paramDate.getTime(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosTime(Instant paramInstant) {
/* 136 */     this(paramInstant.getEpochSecond() * 1000L + paramInstant.getNano() / 1000000L, paramInstant
/* 137 */         .getNano() / 1000 % 1000);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KerberosTime now() {
/* 145 */     long l1 = System.currentTimeMillis();
/* 146 */     long l2 = System.nanoTime() / 1000L;
/* 147 */     long l3 = l2 - initMicro;
/* 148 */     long l4 = initMilli + l3 / 1000L;
/* 149 */     if (l4 - l1 > 100L || l1 - l4 > 100L) {
/* 150 */       if (DEBUG) {
/* 151 */         System.out.println("System time adjusted");
/*     */       }
/* 153 */       initMilli = l1;
/* 154 */       initMicro = l2;
/* 155 */       return new KerberosTime(l1, 0);
/*     */     } 
/* 157 */     return new KerberosTime(l4, (int)(l3 % 1000L));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toGeneralizedTimeString() {
/* 166 */     Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
/* 167 */     calendar.clear();
/*     */     
/* 169 */     calendar.setTimeInMillis(this.kerberosTime);
/* 170 */     return String.format("%04d%02d%02d%02d%02d%02dZ", new Object[] {
/* 171 */           Integer.valueOf(calendar.get(1)), 
/* 172 */           Integer.valueOf(calendar.get(2) + 1), 
/* 173 */           Integer.valueOf(calendar.get(5)), 
/* 174 */           Integer.valueOf(calendar.get(11)), 
/* 175 */           Integer.valueOf(calendar.get(12)), 
/* 176 */           Integer.valueOf(calendar.get(13))
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 186 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 187 */     derOutputStream.putGeneralizedTime(toDate());
/* 188 */     return derOutputStream.toByteArray();
/*     */   }
/*     */   
/*     */   public long getTime() {
/* 192 */     return this.kerberosTime;
/*     */   }
/*     */   
/*     */   public Date toDate() {
/* 196 */     return new Date(this.kerberosTime);
/*     */   }
/*     */   
/*     */   public int getMicroSeconds() {
/* 200 */     Long long_ = new Long(this.kerberosTime % 1000L * 1000L);
/* 201 */     return long_.intValue() + this.microSeconds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosTime withMicroSeconds(int paramInt) {
/* 209 */     return new KerberosTime(this.kerberosTime - this.kerberosTime % 1000L + paramInt / 1000L, paramInt % 1000);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean inClockSkew(int paramInt) {
/* 215 */     return (Math.abs(this.kerberosTime - System.currentTimeMillis()) <= paramInt * 1000L);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inClockSkew() {
/* 220 */     return inClockSkew(getDefaultSkew());
/*     */   }
/*     */   
/*     */   public boolean greaterThanWRTClockSkew(KerberosTime paramKerberosTime, int paramInt) {
/* 224 */     if (this.kerberosTime - paramKerberosTime.kerberosTime > paramInt * 1000L)
/* 225 */       return true; 
/* 226 */     return false;
/*     */   }
/*     */   
/*     */   public boolean greaterThanWRTClockSkew(KerberosTime paramKerberosTime) {
/* 230 */     return greaterThanWRTClockSkew(paramKerberosTime, getDefaultSkew());
/*     */   }
/*     */   
/*     */   public boolean greaterThan(KerberosTime paramKerberosTime) {
/* 234 */     return (this.kerberosTime > paramKerberosTime.kerberosTime || (this.kerberosTime == paramKerberosTime.kerberosTime && this.microSeconds > paramKerberosTime.microSeconds));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 240 */     if (this == paramObject) {
/* 241 */       return true;
/*     */     }
/*     */     
/* 244 */     if (!(paramObject instanceof KerberosTime)) {
/* 245 */       return false;
/*     */     }
/*     */     
/* 248 */     return (this.kerberosTime == ((KerberosTime)paramObject).kerberosTime && this.microSeconds == ((KerberosTime)paramObject).microSeconds);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 253 */     int i = 629 + (int)(this.kerberosTime ^ this.kerberosTime >>> 32L);
/* 254 */     return i * 17 + this.microSeconds;
/*     */   }
/*     */   
/*     */   public boolean isZero() {
/* 258 */     return (this.kerberosTime == 0L && this.microSeconds == 0);
/*     */   }
/*     */   
/*     */   public int getSeconds() {
/* 262 */     Long long_ = new Long(this.kerberosTime / 1000L);
/* 263 */     return long_.intValue();
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
/*     */   public static KerberosTime parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 282 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/* 283 */       return null; 
/* 284 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 285 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 286 */       throw new Asn1Exception(906);
/*     */     }
/*     */     
/* 289 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 290 */     Date date = derValue2.getGeneralizedTime();
/* 291 */     return new KerberosTime(date.getTime(), 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getDefaultSkew() {
/* 296 */     int i = 300;
/*     */     try {
/* 298 */       if ((i = Config.getInstance().getIntValue(new String[] { "libdefaults", "clockskew" })) == Integer.MIN_VALUE)
/*     */       {
/*     */         
/* 301 */         i = 300;
/*     */       }
/* 303 */     } catch (KrbException krbException) {
/* 304 */       if (DEBUG) {
/* 305 */         System.out.println("Exception in getting clockskew from Configuration using default value " + krbException
/*     */ 
/*     */             
/* 308 */             .getMessage());
/*     */       }
/*     */     } 
/* 311 */     return i;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 315 */     return toGeneralizedTimeString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/KerberosTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */