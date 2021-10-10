/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Arrays;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.util.BitArray;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IPAddressName
/*     */   implements GeneralNameInterface
/*     */ {
/*     */   private byte[] address;
/*     */   private boolean isIPv4;
/*     */   private String name;
/*     */   private static final int MASKSIZE = 16;
/*     */   
/*     */   public IPAddressName(DerValue paramDerValue) throws IOException {
/*  82 */     this(paramDerValue.getOctetString());
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
/*     */   public IPAddressName(byte[] paramArrayOfbyte) throws IOException {
/*  97 */     if (paramArrayOfbyte.length == 4 || paramArrayOfbyte.length == 8) {
/*  98 */       this.isIPv4 = true;
/*  99 */     } else if (paramArrayOfbyte.length == 16 || paramArrayOfbyte.length == 32) {
/* 100 */       this.isIPv4 = false;
/*     */     } else {
/* 102 */       throw new IOException("Invalid IPAddressName");
/*     */     } 
/* 104 */     this.address = paramArrayOfbyte;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IPAddressName(String paramString) throws IOException {
/* 128 */     if (paramString == null || paramString.length() == 0) {
/* 129 */       throw new IOException("IPAddress cannot be null or empty");
/*     */     }
/* 131 */     if (paramString.charAt(paramString.length() - 1) == '/') {
/* 132 */       throw new IOException("Invalid IPAddress: " + paramString);
/*     */     }
/*     */     
/* 135 */     if (paramString.indexOf(':') >= 0) {
/*     */ 
/*     */ 
/*     */       
/* 139 */       parseIPv6(paramString);
/* 140 */       this.isIPv4 = false;
/* 141 */     } else if (paramString.indexOf('.') >= 0) {
/*     */       
/* 143 */       parseIPv4(paramString);
/* 144 */       this.isIPv4 = true;
/*     */     } else {
/* 146 */       throw new IOException("Invalid IPAddress: " + paramString);
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
/*     */   
/*     */   private void parseIPv4(String paramString) throws IOException {
/* 159 */     int i = paramString.indexOf('/');
/* 160 */     if (i == -1) {
/* 161 */       this.address = InetAddress.getByName(paramString).getAddress();
/*     */     } else {
/* 163 */       this.address = new byte[8];
/*     */ 
/*     */ 
/*     */       
/* 167 */       byte[] arrayOfByte1 = InetAddress.getByName(paramString.substring(i + 1)).getAddress();
/*     */ 
/*     */ 
/*     */       
/* 171 */       byte[] arrayOfByte2 = InetAddress.getByName(paramString.substring(0, i)).getAddress();
/*     */       
/* 173 */       System.arraycopy(arrayOfByte2, 0, this.address, 0, 4);
/* 174 */       System.arraycopy(arrayOfByte1, 0, this.address, 4, 4);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseIPv6(String paramString) throws IOException {
/* 189 */     int i = paramString.indexOf('/');
/* 190 */     if (i == -1) {
/* 191 */       this.address = InetAddress.getByName(paramString).getAddress();
/*     */     } else {
/* 193 */       this.address = new byte[32];
/*     */       
/* 195 */       byte[] arrayOfByte1 = InetAddress.getByName(paramString.substring(0, i)).getAddress();
/* 196 */       System.arraycopy(arrayOfByte1, 0, this.address, 0, 16);
/*     */ 
/*     */       
/* 199 */       int j = Integer.parseInt(paramString.substring(i + 1));
/* 200 */       if (j < 0 || j > 128) {
/* 201 */         throw new IOException("IPv6Address prefix length (" + j + ") in out of valid range [0,128]");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 206 */       BitArray bitArray = new BitArray(128);
/*     */ 
/*     */       
/* 209 */       for (byte b1 = 0; b1 < j; b1++)
/* 210 */         bitArray.set(b1, true); 
/* 211 */       byte[] arrayOfByte2 = bitArray.toByteArray();
/*     */ 
/*     */       
/* 214 */       for (byte b2 = 0; b2 < 16; b2++) {
/* 215 */         this.address[16 + b2] = arrayOfByte2[b2];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 223 */     return 7;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 233 */     paramDerOutputStream.putOctetString(this.address);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     try {
/* 241 */       return "IPAddress: " + getName();
/* 242 */     } catch (IOException iOException) {
/*     */       
/* 244 */       HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/* 245 */       return "IPAddress: " + hexDumpEncoder.encodeBuffer(this.address);
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
/*     */   public String getName() throws IOException {
/* 257 */     if (this.name != null) {
/* 258 */       return this.name;
/*     */     }
/* 260 */     if (this.isIPv4) {
/*     */       
/* 262 */       byte[] arrayOfByte = new byte[4];
/* 263 */       System.arraycopy(this.address, 0, arrayOfByte, 0, 4);
/* 264 */       this.name = InetAddress.getByAddress(arrayOfByte).getHostAddress();
/* 265 */       if (this.address.length == 8) {
/* 266 */         byte[] arrayOfByte1 = new byte[4];
/* 267 */         System.arraycopy(this.address, 4, arrayOfByte1, 0, 4);
/* 268 */         this
/* 269 */           .name = this.name + "/" + InetAddress.getByAddress(arrayOfByte1).getHostAddress();
/*     */       } 
/*     */     } else {
/*     */       
/* 273 */       byte[] arrayOfByte = new byte[16];
/* 274 */       System.arraycopy(this.address, 0, arrayOfByte, 0, 16);
/* 275 */       this.name = InetAddress.getByAddress(arrayOfByte).getHostAddress();
/* 276 */       if (this.address.length == 32) {
/*     */ 
/*     */ 
/*     */         
/* 280 */         byte[] arrayOfByte1 = new byte[16];
/* 281 */         for (byte b1 = 16; b1 < 32; b1++)
/* 282 */           arrayOfByte1[b1 - 16] = this.address[b1]; 
/* 283 */         BitArray bitArray = new BitArray(128, arrayOfByte1);
/*     */         
/* 285 */         byte b2 = 0;
/* 286 */         for (; b2 < '' && 
/* 287 */           bitArray.get(b2); b2++);
/*     */ 
/*     */         
/* 290 */         this.name += "/" + b2;
/*     */         
/* 292 */         for (; b2 < ''; b2++) {
/* 293 */           if (bitArray.get(b2)) {
/* 294 */             throw new IOException("Invalid IPv6 subdomain - set bit " + b2 + " not contiguous");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 300 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBytes() {
/* 307 */     return (byte[])this.address.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 316 */     if (this == paramObject) {
/* 317 */       return true;
/*     */     }
/* 319 */     if (!(paramObject instanceof IPAddressName)) {
/* 320 */       return false;
/*     */     }
/* 322 */     IPAddressName iPAddressName = (IPAddressName)paramObject;
/* 323 */     byte[] arrayOfByte = iPAddressName.address;
/*     */     
/* 325 */     if (arrayOfByte.length != this.address.length) {
/* 326 */       return false;
/*     */     }
/* 328 */     if (this.address.length == 8 || this.address.length == 32) {
/*     */ 
/*     */       
/* 331 */       int i = this.address.length / 2; int j;
/* 332 */       for (j = 0; j < i; j++) {
/* 333 */         byte b1 = (byte)(this.address[j] & this.address[j + i]);
/* 334 */         byte b2 = (byte)(arrayOfByte[j] & arrayOfByte[j + i]);
/* 335 */         if (b1 != b2) {
/* 336 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 340 */       for (j = i; j < this.address.length; j++) {
/* 341 */         if (this.address[j] != arrayOfByte[j])
/* 342 */           return false; 
/* 343 */       }  return true;
/*     */     } 
/*     */ 
/*     */     
/* 347 */     return Arrays.equals(arrayOfByte, this.address);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 357 */     int i = 0;
/*     */     
/* 359 */     for (byte b = 0; b < this.address.length; b++) {
/* 360 */       i += this.address[b] * b;
/*     */     }
/* 362 */     return i;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int constrains(GeneralNameInterface paramGeneralNameInterface) throws UnsupportedOperationException {
/*     */     byte b;
/* 397 */     if (paramGeneralNameInterface == null) {
/* 398 */       b = -1;
/* 399 */     } else if (paramGeneralNameInterface.getType() != 7) {
/* 400 */       b = -1;
/* 401 */     } else if (((IPAddressName)paramGeneralNameInterface).equals(this)) {
/* 402 */       b = 0;
/*     */     } else {
/* 404 */       IPAddressName iPAddressName = (IPAddressName)paramGeneralNameInterface;
/* 405 */       byte[] arrayOfByte = iPAddressName.address;
/* 406 */       if (arrayOfByte.length == 4 && this.address.length == 4)
/*     */       
/* 408 */       { b = 3; }
/* 409 */       else if ((arrayOfByte.length == 8 && this.address.length == 8) || (arrayOfByte.length == 32 && this.address.length == 32))
/*     */       
/*     */       { 
/*     */         
/* 413 */         boolean bool1 = true;
/* 414 */         boolean bool2 = true;
/* 415 */         boolean bool3 = false;
/* 416 */         boolean bool4 = false;
/* 417 */         int i = this.address.length / 2;
/* 418 */         for (byte b1 = 0; b1 < i; b1++) {
/* 419 */           if ((byte)(this.address[b1] & this.address[b1 + i]) != this.address[b1])
/* 420 */             bool3 = true; 
/* 421 */           if ((byte)(arrayOfByte[b1] & arrayOfByte[b1 + i]) != arrayOfByte[b1])
/* 422 */             bool4 = true; 
/* 423 */           if ((byte)(this.address[b1 + i] & arrayOfByte[b1 + i]) != this.address[b1 + i] || (byte)(this.address[b1] & this.address[b1 + i]) != (byte)(arrayOfByte[b1] & this.address[b1 + i]))
/*     */           {
/* 425 */             bool1 = false;
/*     */           }
/* 427 */           if ((byte)(arrayOfByte[b1 + i] & this.address[b1 + i]) != arrayOfByte[b1 + i] || (byte)(arrayOfByte[b1] & arrayOfByte[b1 + i]) != (byte)(this.address[b1] & arrayOfByte[b1 + i]))
/*     */           {
/* 429 */             bool2 = false;
/*     */           }
/*     */         } 
/* 432 */         if (bool3 || bool4)
/* 433 */         { if (bool3 && bool4)
/* 434 */           { b = 0; }
/* 435 */           else if (bool3)
/* 436 */           { b = 2; }
/*     */           else
/* 438 */           { b = 1; }  }
/* 439 */         else if (bool1)
/* 440 */         { b = 1; }
/* 441 */         else if (bool2)
/* 442 */         { b = 2; }
/*     */         else
/* 444 */         { b = 3; }  }
/* 445 */       else if (arrayOfByte.length == 8 || arrayOfByte.length == 32)
/*     */       
/* 447 */       { byte b1 = 0;
/* 448 */         int i = arrayOfByte.length / 2;
/* 449 */         for (; b1 < i; b1++) {
/*     */ 
/*     */           
/* 452 */           if ((this.address[b1] & arrayOfByte[b1 + i]) != arrayOfByte[b1])
/*     */             break; 
/*     */         } 
/* 455 */         if (b1 == i)
/* 456 */         { b = 2; }
/*     */         else
/* 458 */         { b = 3; }  }
/* 459 */       else if (this.address.length == 8 || this.address.length == 32)
/*     */       
/* 461 */       { byte b1 = 0;
/* 462 */         int i = this.address.length / 2;
/* 463 */         for (; b1 < i; b1++) {
/*     */           
/* 465 */           if ((arrayOfByte[b1] & this.address[b1 + i]) != this.address[b1])
/*     */             break; 
/*     */         } 
/* 468 */         if (b1 == i) {
/* 469 */           b = 1;
/*     */         } else {
/* 471 */           b = 3;
/*     */         }  }
/* 473 */       else { b = 3; }
/*     */     
/*     */     } 
/* 476 */     return b;
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
/*     */   public int subtreeDepth() throws UnsupportedOperationException {
/* 488 */     throw new UnsupportedOperationException("subtreeDepth() not defined for IPAddressName");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/IPAddressName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */