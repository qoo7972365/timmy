/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Arrays;
/*     */ import sun.security.krb5.Asn1Exception;
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
/*     */ public class HostAddress
/*     */   implements Cloneable
/*     */ {
/*     */   int addrType;
/*  63 */   byte[] address = null;
/*     */   
/*     */   private static InetAddress localInetAddress;
/*  66 */   private static final boolean DEBUG = Krb5.DEBUG;
/*  67 */   private volatile int hashCode = 0;
/*     */   
/*     */   private HostAddress(int paramInt) {}
/*     */   
/*     */   public Object clone() {
/*  72 */     HostAddress hostAddress = new HostAddress(0);
/*  73 */     hostAddress.addrType = this.addrType;
/*  74 */     if (this.address != null) {
/*  75 */       hostAddress.address = (byte[])this.address.clone();
/*     */     }
/*  77 */     return hostAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  82 */     if (this.hashCode == 0) {
/*  83 */       int i = 17;
/*  84 */       i = 37 * i + this.addrType;
/*  85 */       if (this.address != null) {
/*  86 */         for (byte b = 0; b < this.address.length; b++) {
/*  87 */           i = 37 * i + this.address[b];
/*     */         }
/*     */       }
/*  90 */       this.hashCode = i;
/*     */     } 
/*  92 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  97 */     if (this == paramObject) {
/*  98 */       return true;
/*     */     }
/*     */     
/* 101 */     if (!(paramObject instanceof HostAddress)) {
/* 102 */       return false;
/*     */     }
/*     */     
/* 105 */     HostAddress hostAddress = (HostAddress)paramObject;
/* 106 */     if (this.addrType != hostAddress.addrType || (this.address != null && hostAddress.address == null) || (this.address == null && hostAddress.address != null))
/*     */     {
/*     */       
/* 109 */       return false; } 
/* 110 */     if (this.address != null && hostAddress.address != null) {
/* 111 */       if (this.address.length != hostAddress.address.length)
/* 112 */         return false; 
/* 113 */       for (byte b = 0; b < this.address.length; b++) {
/* 114 */         if (this.address[b] != hostAddress.address[b])
/* 115 */           return false; 
/*     */       } 
/* 117 */     }  return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized InetAddress getLocalInetAddress() throws UnknownHostException {
/* 123 */     if (localInetAddress == null) {
/* 124 */       localInetAddress = InetAddress.getLocalHost();
/*     */     }
/* 126 */     if (localInetAddress == null) {
/* 127 */       throw new UnknownHostException();
/*     */     }
/* 129 */     return localInetAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetAddress getInetAddress() throws UnknownHostException {
/* 140 */     if (this.addrType == 2 || this.addrType == 24)
/*     */     {
/* 142 */       return InetAddress.getByAddress(this.address);
/*     */     }
/*     */     
/* 145 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getAddrType(InetAddress paramInetAddress) {
/* 150 */     byte b = 0;
/* 151 */     if (paramInetAddress instanceof java.net.Inet4Address) {
/* 152 */       b = 2;
/* 153 */     } else if (paramInetAddress instanceof java.net.Inet6Address) {
/* 154 */       b = 24;
/* 155 */     }  return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public HostAddress() throws UnknownHostException {
/* 160 */     InetAddress inetAddress = getLocalInetAddress();
/* 161 */     this.addrType = getAddrType(inetAddress);
/* 162 */     this.address = inetAddress.getAddress();
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
/*     */   public HostAddress(int paramInt, byte[] paramArrayOfbyte) throws KrbApErrException, UnknownHostException {
/* 177 */     switch (paramInt) {
/*     */       case 2:
/* 179 */         if (paramArrayOfbyte.length != 4)
/* 180 */           throw new KrbApErrException(0, "Invalid Internet address"); 
/*     */         break;
/*     */       case 5:
/* 183 */         if (paramArrayOfbyte.length != 2) {
/* 184 */           throw new KrbApErrException(0, "Invalid CHAOSnet address");
/*     */         }
/*     */         break;
/*     */       
/*     */       case 6:
/* 189 */         if (paramArrayOfbyte.length != 6)
/* 190 */           throw new KrbApErrException(0, "Invalid XNS address"); 
/*     */         break;
/*     */       case 16:
/* 193 */         if (paramArrayOfbyte.length != 3)
/* 194 */           throw new KrbApErrException(0, "Invalid DDP address"); 
/*     */         break;
/*     */       case 12:
/* 197 */         if (paramArrayOfbyte.length != 2)
/* 198 */           throw new KrbApErrException(0, "Invalid DECnet Phase IV address"); 
/*     */         break;
/*     */       case 24:
/* 201 */         if (paramArrayOfbyte.length != 16) {
/* 202 */           throw new KrbApErrException(0, "Invalid Internet IPv6 address");
/*     */         }
/*     */         break;
/*     */     } 
/* 206 */     this.addrType = paramInt;
/* 207 */     if (paramArrayOfbyte != null) {
/* 208 */       this.address = (byte[])paramArrayOfbyte.clone();
/*     */     }
/* 210 */     if (DEBUG && (
/* 211 */       this.addrType == 2 || this.addrType == 24))
/*     */     {
/* 213 */       System.out.println("Host address is " + 
/* 214 */           InetAddress.getByAddress(this.address));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public HostAddress(InetAddress paramInetAddress) {
/* 220 */     this.addrType = getAddrType(paramInetAddress);
/* 221 */     this.address = paramInetAddress.getAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HostAddress(DerValue paramDerValue) throws Asn1Exception, IOException {
/* 232 */     DerValue derValue = paramDerValue.getData().getDerValue();
/* 233 */     if ((derValue.getTag() & 0x1F) == 0) {
/* 234 */       this.addrType = derValue.getData().getBigInteger().intValue();
/*     */     } else {
/*     */       
/* 237 */       throw new Asn1Exception(906);
/* 238 */     }  derValue = paramDerValue.getData().getDerValue();
/* 239 */     if ((derValue.getTag() & 0x1F) == 1) {
/* 240 */       this.address = derValue.getData().getOctetString();
/*     */     } else {
/*     */       
/* 243 */       throw new Asn1Exception(906);
/* 244 */     }  if (paramDerValue.getData().available() > 0) {
/* 245 */       throw new Asn1Exception(906);
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
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 257 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 258 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 259 */     derOutputStream2.putInteger(this.addrType);
/* 260 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/* 261 */     derOutputStream2 = new DerOutputStream();
/* 262 */     derOutputStream2.putOctetString(this.address);
/* 263 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/* 264 */     derOutputStream2 = new DerOutputStream();
/* 265 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 266 */     return derOutputStream2.toByteArray();
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
/*     */   public static HostAddress parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 285 */     if (paramBoolean && (
/* 286 */       (byte)paramDerInputStream.peekByte() & 0x1F) != paramByte) {
/* 287 */       return null;
/*     */     }
/* 289 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 290 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 291 */       throw new Asn1Exception(906);
/*     */     }
/*     */     
/* 294 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 295 */     return new HostAddress(derValue2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 301 */     StringBuilder stringBuilder = new StringBuilder();
/* 302 */     stringBuilder.append(Arrays.toString(this.address));
/* 303 */     stringBuilder.append('(').append(this.addrType).append(')');
/* 304 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/HostAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */