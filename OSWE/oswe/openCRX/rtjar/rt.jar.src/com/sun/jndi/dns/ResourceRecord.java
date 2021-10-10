/*     */ package com.sun.jndi.dns;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import javax.naming.CommunicationException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceRecord
/*     */ {
/*     */   static final int TYPE_A = 1;
/*     */   static final int TYPE_NS = 2;
/*     */   static final int TYPE_CNAME = 5;
/*     */   static final int TYPE_SOA = 6;
/*     */   static final int TYPE_PTR = 12;
/*     */   static final int TYPE_HINFO = 13;
/*     */   static final int TYPE_MX = 15;
/*     */   static final int TYPE_TXT = 16;
/*     */   static final int TYPE_AAAA = 28;
/*     */   static final int TYPE_SRV = 33;
/*     */   static final int TYPE_NAPTR = 35;
/*     */   static final int QTYPE_AXFR = 252;
/*     */   static final int QTYPE_STAR = 255;
/*  67 */   static final String[] rrTypeNames = new String[] { null, "A", "NS", null, null, "CNAME", "SOA", null, null, null, null, null, "PTR", "HINFO", null, "MX", "TXT", null, null, null, null, null, null, null, null, null, null, null, "AAAA", null, null, null, null, "SRV", null, "NAPTR" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int CLASS_INTERNET = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int CLASS_HESIOD = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int QCLASS_STAR = 255;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   static final String[] rrClassNames = new String[] { null, "IN", null, null, "HS" };
/*     */   
/*     */   private static final int MAXIMUM_COMPRESSION_REFERENCES = 16;
/*     */   
/*     */   byte[] msg;
/*     */   
/*     */   int msgLen;
/*     */   
/*     */   boolean qSection;
/*     */   
/*     */   int offset;
/*     */   
/*     */   int rrlen;
/*     */   
/*     */   DnsName name;
/*     */   
/*     */   int rrtype;
/*     */   
/*     */   String rrtypeName;
/*     */   int rrclass;
/*     */   String rrclassName;
/* 109 */   int ttl = 0;
/* 110 */   int rdlen = 0;
/* 111 */   Object rdata = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean debug = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ResourceRecord(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) throws CommunicationException {
/* 130 */     this.msg = paramArrayOfbyte;
/* 131 */     this.msgLen = paramInt1;
/* 132 */     this.offset = paramInt2;
/* 133 */     this.qSection = paramBoolean1;
/* 134 */     decode(paramBoolean2);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 138 */     String str = this.name + " " + this.rrclassName + " " + this.rrtypeName;
/* 139 */     if (!this.qSection) {
/* 140 */       str = str + " " + this.ttl + " " + ((this.rdata != null) ? (String)this.rdata : "[n/a]");
/*     */     }
/*     */     
/* 143 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DnsName getName() {
/* 150 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 157 */     return this.rrlen;
/*     */   }
/*     */   
/*     */   public int getType() {
/* 161 */     return this.rrtype;
/*     */   }
/*     */   
/*     */   public int getRrclass() {
/* 165 */     return this.rrclass;
/*     */   }
/*     */   
/*     */   public Object getRdata() {
/* 169 */     return this.rdata;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getTypeName(int paramInt) {
/* 174 */     return valueToName(paramInt, rrTypeNames);
/*     */   }
/*     */   
/*     */   public static int getType(String paramString) {
/* 178 */     return nameToValue(paramString, rrTypeNames);
/*     */   }
/*     */   
/*     */   public static String getRrclassName(int paramInt) {
/* 182 */     return valueToName(paramInt, rrClassNames);
/*     */   }
/*     */   
/*     */   public static int getRrclass(String paramString) {
/* 186 */     return nameToValue(paramString, rrClassNames);
/*     */   }
/*     */   
/*     */   private static String valueToName(int paramInt, String[] paramArrayOfString) {
/* 190 */     String str = null;
/* 191 */     if (paramInt > 0 && paramInt < paramArrayOfString.length) {
/* 192 */       str = paramArrayOfString[paramInt];
/* 193 */     } else if (paramInt == 255) {
/* 194 */       str = "*";
/*     */     } 
/* 196 */     if (str == null) {
/* 197 */       str = Integer.toString(paramInt);
/*     */     }
/* 199 */     return str;
/*     */   }
/*     */   
/*     */   private static int nameToValue(String paramString, String[] paramArrayOfString) {
/* 203 */     if (paramString.equals(""))
/* 204 */       return -1; 
/* 205 */     if (paramString.equals("*")) {
/* 206 */       return 255;
/*     */     }
/* 208 */     if (Character.isDigit(paramString.charAt(0))) {
/*     */       try {
/* 210 */         return Integer.parseInt(paramString);
/* 211 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */     
/* 214 */     for (byte b = 1; b < paramArrayOfString.length; b++) {
/* 215 */       if (paramArrayOfString[b] != null && paramString
/* 216 */         .equalsIgnoreCase(paramArrayOfString[b])) {
/* 217 */         return b;
/*     */       }
/*     */     } 
/* 220 */     return -1;
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
/*     */   public static int compareSerialNumbers(long paramLong1, long paramLong2) {
/* 232 */     long l = paramLong2 - paramLong1;
/* 233 */     if (l == 0L)
/* 234 */       return 0; 
/* 235 */     if ((l > 0L && l <= 2147483647L) || (l < 0L && -l > 2147483647L))
/*     */     {
/* 237 */       return -1;
/*     */     }
/* 239 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void decode(boolean paramBoolean) throws CommunicationException {
/* 249 */     int i = this.offset;
/*     */     
/* 251 */     this.name = new DnsName();
/* 252 */     i = decodeName(i, this.name);
/*     */     
/* 254 */     this.rrtype = getUShort(i);
/* 255 */     this.rrtypeName = (this.rrtype < rrTypeNames.length) ? rrTypeNames[this.rrtype] : null;
/*     */ 
/*     */     
/* 258 */     if (this.rrtypeName == null) {
/* 259 */       this.rrtypeName = Integer.toString(this.rrtype);
/*     */     }
/* 261 */     i += 2;
/*     */     
/* 263 */     this.rrclass = getUShort(i);
/* 264 */     this.rrclassName = (this.rrclass < rrClassNames.length) ? rrClassNames[this.rrclass] : null;
/*     */ 
/*     */     
/* 267 */     if (this.rrclassName == null) {
/* 268 */       this.rrclassName = Integer.toString(this.rrclass);
/*     */     }
/* 270 */     i += 2;
/*     */     
/* 272 */     if (!this.qSection) {
/* 273 */       this.ttl = getInt(i);
/* 274 */       i += 4;
/*     */       
/* 276 */       this.rdlen = getUShort(i);
/* 277 */       i += 2;
/*     */       
/* 279 */       this
/*     */         
/* 281 */         .rdata = (paramBoolean || this.rrtype == 6) ? decodeRdata(i) : null;
/*     */       
/* 283 */       if (this.rdata instanceof DnsName) {
/* 284 */         this.rdata = this.rdata.toString();
/*     */       }
/* 286 */       i += this.rdlen;
/*     */     } 
/*     */     
/* 289 */     this.rrlen = i - this.offset;
/*     */     
/* 291 */     this.msg = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getUByte(int paramInt) {
/* 298 */     return this.msg[paramInt] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getUShort(int paramInt) {
/* 306 */     return (this.msg[paramInt] & 0xFF) << 8 | this.msg[paramInt + 1] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getInt(int paramInt) {
/* 315 */     return getUShort(paramInt) << 16 | getUShort(paramInt + 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long getUInt(int paramInt) {
/* 323 */     return getInt(paramInt) & 0xFFFFFFFFL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DnsName decodeName(int paramInt) throws CommunicationException {
/* 330 */     DnsName dnsName = new DnsName();
/* 331 */     decodeName(paramInt, dnsName);
/* 332 */     return dnsName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int decodeName(int paramInt, DnsName paramDnsName) throws CommunicationException {
/* 340 */     int i = -1;
/* 341 */     byte b = 0;
/*     */     try {
/*     */       while (true) {
/* 344 */         if (b > 16)
/* 345 */           throw new IOException("Too many compression references"); 
/* 346 */         int j = this.msg[paramInt] & 0xFF;
/* 347 */         if (j == 0) {
/* 348 */           paramInt++;
/* 349 */           paramDnsName.add(0, ""); break;
/*     */         } 
/* 351 */         if (j <= 63) {
/* 352 */           paramInt++;
/* 353 */           paramDnsName.add(0, new String(this.msg, paramInt, j, StandardCharsets.ISO_8859_1));
/*     */           
/* 355 */           paramInt += j; continue;
/* 356 */         }  if ((j & 0xC0) == 192) {
/* 357 */           b++;
/*     */ 
/*     */ 
/*     */           
/* 361 */           int k = paramInt;
/* 362 */           if (i == -1) i = paramInt + 2; 
/* 363 */           paramInt = getUShort(paramInt) & 0x3FFF;
/*     */ 
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 372 */         throw new IOException("Invalid label type: " + j);
/*     */       } 
/* 374 */     } catch (IOException|javax.naming.InvalidNameException iOException) {
/* 375 */       CommunicationException communicationException = new CommunicationException("DNS error: malformed packet");
/*     */       
/* 377 */       communicationException.initCause(iOException);
/* 378 */       throw communicationException;
/*     */     } 
/* 380 */     if (i == -1)
/* 381 */       i = paramInt; 
/* 382 */     return i;
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
/*     */   private Object decodeRdata(int paramInt) throws CommunicationException {
/* 394 */     if (this.rrclass == 1) {
/* 395 */       switch (this.rrtype) {
/*     */         case 1:
/* 397 */           return decodeA(paramInt);
/*     */         case 28:
/* 399 */           return decodeAAAA(paramInt);
/*     */         case 2:
/*     */         case 5:
/*     */         case 12:
/* 403 */           return decodeName(paramInt);
/*     */         case 15:
/* 405 */           return decodeMx(paramInt);
/*     */         case 6:
/* 407 */           return decodeSoa(paramInt);
/*     */         case 33:
/* 409 */           return decodeSrv(paramInt);
/*     */         case 35:
/* 411 */           return decodeNaptr(paramInt);
/*     */         case 16:
/* 413 */           return decodeTxt(paramInt);
/*     */         case 13:
/* 415 */           return decodeHinfo(paramInt);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 424 */     byte[] arrayOfByte = new byte[this.rdlen];
/* 425 */     System.arraycopy(this.msg, paramInt, arrayOfByte, 0, this.rdlen);
/* 426 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String decodeMx(int paramInt) throws CommunicationException {
/* 433 */     int i = getUShort(paramInt);
/* 434 */     paramInt += 2;
/* 435 */     DnsName dnsName = decodeName(paramInt);
/* 436 */     return i + " " + dnsName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String decodeSoa(int paramInt) throws CommunicationException {
/* 443 */     DnsName dnsName1 = new DnsName();
/* 444 */     paramInt = decodeName(paramInt, dnsName1);
/* 445 */     DnsName dnsName2 = new DnsName();
/* 446 */     paramInt = decodeName(paramInt, dnsName2);
/*     */     
/* 448 */     long l1 = getUInt(paramInt);
/* 449 */     paramInt += 4;
/* 450 */     long l2 = getUInt(paramInt);
/* 451 */     paramInt += 4;
/* 452 */     long l3 = getUInt(paramInt);
/* 453 */     paramInt += 4;
/* 454 */     long l4 = getUInt(paramInt);
/* 455 */     paramInt += 4;
/* 456 */     long l5 = getUInt(paramInt);
/* 457 */     paramInt += 4;
/*     */     
/* 459 */     return dnsName1 + " " + dnsName2 + " " + l1 + " " + l2 + " " + l3 + " " + l4 + " " + l5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String decodeSrv(int paramInt) throws CommunicationException {
/* 468 */     int i = getUShort(paramInt);
/* 469 */     paramInt += 2;
/* 470 */     int j = getUShort(paramInt);
/* 471 */     paramInt += 2;
/* 472 */     int k = getUShort(paramInt);
/* 473 */     paramInt += 2;
/* 474 */     DnsName dnsName = decodeName(paramInt);
/* 475 */     return i + " " + j + " " + k + " " + dnsName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String decodeNaptr(int paramInt) throws CommunicationException {
/* 483 */     int i = getUShort(paramInt);
/* 484 */     paramInt += 2;
/* 485 */     int j = getUShort(paramInt);
/* 486 */     paramInt += 2;
/* 487 */     StringBuffer stringBuffer1 = new StringBuffer();
/* 488 */     paramInt += decodeCharString(paramInt, stringBuffer1);
/* 489 */     StringBuffer stringBuffer2 = new StringBuffer();
/* 490 */     paramInt += decodeCharString(paramInt, stringBuffer2);
/* 491 */     StringBuffer stringBuffer3 = new StringBuffer(this.rdlen);
/* 492 */     paramInt += decodeCharString(paramInt, stringBuffer3);
/* 493 */     DnsName dnsName = decodeName(paramInt);
/*     */     
/* 495 */     return i + " " + j + " " + stringBuffer1 + " " + stringBuffer2 + " " + stringBuffer3 + " " + dnsName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String decodeTxt(int paramInt) {
/* 504 */     StringBuffer stringBuffer = new StringBuffer(this.rdlen);
/* 505 */     int i = paramInt + this.rdlen;
/* 506 */     while (paramInt < i) {
/* 507 */       paramInt += decodeCharString(paramInt, stringBuffer);
/* 508 */       if (paramInt < i) {
/* 509 */         stringBuffer.append(' ');
/*     */       }
/*     */     } 
/* 512 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String decodeHinfo(int paramInt) {
/* 520 */     StringBuffer stringBuffer = new StringBuffer(this.rdlen);
/* 521 */     paramInt += decodeCharString(paramInt, stringBuffer);
/* 522 */     stringBuffer.append(' ');
/* 523 */     paramInt += decodeCharString(paramInt, stringBuffer);
/* 524 */     return stringBuffer.toString();
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
/*     */   private int decodeCharString(int paramInt, StringBuffer paramStringBuffer) {
/* 536 */     int i = paramStringBuffer.length();
/* 537 */     int j = getUByte(paramInt++);
/* 538 */     int k = (j == 0) ? 1 : 0;
/* 539 */     for (byte b = 0; b < j; b++) {
/* 540 */       int m = getUByte(paramInt++);
/* 541 */       k |= (m == 32) ? 1 : 0;
/* 542 */       if (m == 92 || m == 34) {
/* 543 */         k = 1;
/* 544 */         paramStringBuffer.append('\\');
/*     */       } 
/* 546 */       paramStringBuffer.append((char)m);
/*     */     } 
/* 548 */     if (k != 0) {
/* 549 */       paramStringBuffer.insert(i, '"');
/* 550 */       paramStringBuffer.append('"');
/*     */     } 
/* 552 */     return j + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String decodeA(int paramInt) {
/* 560 */     return (this.msg[paramInt] & 0xFF) + "." + (this.msg[paramInt + 1] & 0xFF) + "." + (this.msg[paramInt + 2] & 0xFF) + "." + (this.msg[paramInt + 3] & 0xFF);
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
/*     */   private String decodeAAAA(int paramInt) {
/* 572 */     int[] arrayOfInt = new int[8]; byte b1;
/* 573 */     for (b1 = 0; b1 < 8; b1++) {
/* 574 */       arrayOfInt[b1] = getUShort(paramInt);
/* 575 */       paramInt += 2;
/*     */     } 
/*     */ 
/*     */     
/* 579 */     b1 = -1;
/* 580 */     byte b2 = 0;
/* 581 */     byte b3 = -1;
/* 582 */     byte b4 = 0; byte b5;
/* 583 */     for (b5 = 0; b5 < 8; b5++) {
/* 584 */       if (arrayOfInt[b5] == 0) {
/* 585 */         if (b1 == -1) {
/* 586 */           b1 = b5;
/* 587 */           b2 = 1;
/*     */         } else {
/* 589 */           b2++;
/* 590 */           if (b2 >= 2 && b2 > b4) {
/* 591 */             b3 = b1;
/* 592 */             b4 = b2;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 596 */         b1 = -1;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 603 */     if (b3 == 0) {
/* 604 */       if (b4 == 6 || (b4 == 7 && arrayOfInt[7] > 1))
/*     */       {
/* 606 */         return "::" + decodeA(paramInt - 4); } 
/* 607 */       if (b4 == 5 && arrayOfInt[5] == 65535) {
/* 608 */         return "::ffff:" + decodeA(paramInt - 4);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 613 */     b5 = (b3 != -1) ? 1 : 0;
/*     */     
/* 615 */     StringBuffer stringBuffer = new StringBuffer(40);
/* 616 */     if (b3 == 0) {
/* 617 */       stringBuffer.append(':');
/*     */     }
/* 619 */     for (byte b6 = 0; b6 < 8; b6++) {
/* 620 */       if (b5 == 0 || b6 < b3 || b6 >= b3 + b4) {
/* 621 */         stringBuffer.append(Integer.toHexString(arrayOfInt[b6]));
/* 622 */         if (b6 < 7) {
/* 623 */           stringBuffer.append(':');
/*     */         }
/* 625 */       } else if (b5 != 0 && b6 == b3) {
/* 626 */         stringBuffer.append(':');
/*     */       } 
/*     */     } 
/*     */     
/* 630 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   private static void dprint(String paramString) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/dns/ResourceRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */