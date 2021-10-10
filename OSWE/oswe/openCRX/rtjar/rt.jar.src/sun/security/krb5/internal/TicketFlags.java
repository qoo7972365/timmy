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
/*     */ public class TicketFlags
/*     */   extends KerberosFlags
/*     */ {
/*     */   public TicketFlags() {
/*  60 */     super(32);
/*     */   }
/*     */   
/*     */   public TicketFlags(boolean[] paramArrayOfboolean) throws Asn1Exception {
/*  64 */     super(paramArrayOfboolean);
/*  65 */     if (paramArrayOfboolean.length > 32) {
/*  66 */       throw new Asn1Exception(502);
/*     */     }
/*     */   }
/*     */   
/*     */   public TicketFlags(int paramInt, byte[] paramArrayOfbyte) throws Asn1Exception {
/*  71 */     super(paramInt, paramArrayOfbyte);
/*  72 */     if (paramInt > paramArrayOfbyte.length * 8 || paramInt > 32)
/*  73 */       throw new Asn1Exception(502); 
/*     */   }
/*     */   
/*     */   public TicketFlags(DerValue paramDerValue) throws IOException, Asn1Exception {
/*  77 */     this(paramDerValue.getUnalignedBitString(true).toBooleanArray());
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
/*     */   public static TicketFlags parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/*  93 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/*  94 */       return null; 
/*  95 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/*  96 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/*  97 */       throw new Asn1Exception(906);
/*     */     }
/*     */     
/* 100 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 101 */     return new TicketFlags(derValue2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 107 */       return new TicketFlags(toBooleanArray());
/*     */     }
/* 109 */     catch (Exception exception) {
/* 110 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean match(LoginOptions paramLoginOptions) {
/* 115 */     boolean bool = false;
/*     */     
/* 117 */     if (get(1) == paramLoginOptions.get(1) && 
/* 118 */       get(3) == paramLoginOptions.get(3) && 
/* 119 */       get(8) == paramLoginOptions.get(8)) {
/* 120 */       bool = true;
/*     */     }
/*     */ 
/*     */     
/* 124 */     return bool;
/*     */   }
/*     */   public boolean match(TicketFlags paramTicketFlags) {
/* 127 */     boolean bool = true;
/* 128 */     for (byte b = 0; b <= 31; b++) {
/* 129 */       if (get(b) != paramTicketFlags.get(b)) {
/* 130 */         return false;
/*     */       }
/*     */     } 
/* 133 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 141 */     StringBuffer stringBuffer = new StringBuffer();
/* 142 */     boolean[] arrayOfBoolean = toBooleanArray();
/* 143 */     for (byte b = 0; b < arrayOfBoolean.length; b++) {
/* 144 */       if (arrayOfBoolean[b] == true) {
/* 145 */         switch (b) {
/*     */           case 0:
/* 147 */             stringBuffer.append("RESERVED;");
/*     */             break;
/*     */           case 1:
/* 150 */             stringBuffer.append("FORWARDABLE;");
/*     */             break;
/*     */           case 2:
/* 153 */             stringBuffer.append("FORWARDED;");
/*     */             break;
/*     */           case 3:
/* 156 */             stringBuffer.append("PROXIABLE;");
/*     */             break;
/*     */           case 4:
/* 159 */             stringBuffer.append("PROXY;");
/*     */             break;
/*     */           case 5:
/* 162 */             stringBuffer.append("MAY-POSTDATE;");
/*     */             break;
/*     */           case 6:
/* 165 */             stringBuffer.append("POSTDATED;");
/*     */             break;
/*     */           case 7:
/* 168 */             stringBuffer.append("INVALID;");
/*     */             break;
/*     */           case 8:
/* 171 */             stringBuffer.append("RENEWABLE;");
/*     */             break;
/*     */           case 9:
/* 174 */             stringBuffer.append("INITIAL;");
/*     */             break;
/*     */           case 10:
/* 177 */             stringBuffer.append("PRE-AUTHENT;");
/*     */             break;
/*     */           case 11:
/* 180 */             stringBuffer.append("HW-AUTHENT;");
/*     */             break;
/*     */           case 15:
/* 183 */             stringBuffer.append("ENC-PA-REP;");
/*     */             break;
/*     */         } 
/*     */       }
/*     */     } 
/* 188 */     String str = stringBuffer.toString();
/* 189 */     if (str.length() > 0) {
/* 190 */       str = str.substring(0, str.length() - 1);
/*     */     }
/* 192 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/TicketFlags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */