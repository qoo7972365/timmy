/*     */ package com.sun.jndi.dns;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DnsName
/*     */   implements Name
/*     */ {
/* 108 */   private String domain = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   private ArrayList<String> labels = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private short octets = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 7040187611324710271L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DnsName() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DnsName(String paramString) throws InvalidNameException {
/* 135 */     parse(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DnsName(DnsName paramDnsName, int paramInt1, int paramInt2) {
/* 146 */     int i = paramDnsName.size() - paramInt2;
/* 147 */     int j = paramDnsName.size() - paramInt1;
/* 148 */     this.labels.addAll(paramDnsName.labels.subList(i, j));
/*     */     
/* 150 */     if (size() == paramDnsName.size()) {
/* 151 */       this.domain = paramDnsName.domain;
/* 152 */       this.octets = paramDnsName.octets;
/*     */     } else {
/* 154 */       for (String str : this.labels) {
/* 155 */         if (str.length() > 0) {
/* 156 */           this.octets = (short)(this.octets + (short)(str.length() + 1));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 164 */     if (this.domain == null) {
/* 165 */       StringBuilder stringBuilder = new StringBuilder();
/* 166 */       for (String str : this.labels) {
/* 167 */         if (stringBuilder.length() > 0 || str.length() == 0) {
/* 168 */           stringBuilder.append('.');
/*     */         }
/* 170 */         escape(stringBuilder, str);
/*     */       } 
/* 172 */       this.domain = stringBuilder.toString();
/*     */     } 
/* 174 */     return this.domain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHostName() {
/* 181 */     for (String str : this.labels) {
/* 182 */       if (!isHostNameLabel(str)) {
/* 183 */         return false;
/*     */       }
/*     */     } 
/* 186 */     return true;
/*     */   }
/*     */   
/*     */   public short getOctets() {
/* 190 */     return this.octets;
/*     */   }
/*     */   
/*     */   public int size() {
/* 194 */     return this.labels.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 198 */     return (size() == 0);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 202 */     int i = 0;
/* 203 */     for (byte b = 0; b < size(); b++) {
/* 204 */       i = 31 * i + getKey(b).hashCode();
/*     */     }
/* 206 */     return i;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 210 */     if (!(paramObject instanceof Name) || paramObject instanceof javax.naming.CompositeName) {
/* 211 */       return false;
/*     */     }
/* 213 */     Name name = (Name)paramObject;
/* 214 */     return (size() == name.size() && 
/* 215 */       compareTo(paramObject) == 0);
/*     */   }
/*     */   
/*     */   public int compareTo(Object paramObject) {
/* 219 */     Name name = (Name)paramObject;
/* 220 */     return compareRange(0, size(), name);
/*     */   }
/*     */   
/*     */   public boolean startsWith(Name paramName) {
/* 224 */     return (size() >= paramName.size() && 
/* 225 */       compareRange(0, paramName.size(), paramName) == 0);
/*     */   }
/*     */   
/*     */   public boolean endsWith(Name paramName) {
/* 229 */     return (size() >= paramName.size() && 
/* 230 */       compareRange(size() - paramName.size(), size(), paramName) == 0);
/*     */   }
/*     */   
/*     */   public String get(int paramInt) {
/* 234 */     if (paramInt < 0 || paramInt >= size()) {
/* 235 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 237 */     int i = size() - paramInt - 1;
/* 238 */     return this.labels.get(i);
/*     */   }
/*     */   
/*     */   public Enumeration<String> getAll() {
/* 242 */     return new Enumeration<String>() {
/* 243 */         int pos = 0;
/*     */         public boolean hasMoreElements() {
/* 245 */           return (this.pos < DnsName.this.size());
/*     */         }
/*     */         public String nextElement() {
/* 248 */           if (this.pos < DnsName.this.size()) {
/* 249 */             return DnsName.this.get(this.pos++);
/*     */           }
/* 251 */           throw new NoSuchElementException();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public Name getPrefix(int paramInt) {
/* 257 */     return new DnsName(this, 0, paramInt);
/*     */   }
/*     */   
/*     */   public Name getSuffix(int paramInt) {
/* 261 */     return new DnsName(this, paramInt, size());
/*     */   }
/*     */   
/*     */   public Object clone() {
/* 265 */     return new DnsName(this, 0, size());
/*     */   }
/*     */   
/*     */   public Object remove(int paramInt) {
/* 269 */     if (paramInt < 0 || paramInt >= size()) {
/* 270 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 272 */     int i = size() - paramInt - 1;
/* 273 */     String str = this.labels.remove(i);
/* 274 */     int j = str.length();
/* 275 */     if (j > 0) {
/* 276 */       this.octets = (short)(this.octets - (short)(j + 1));
/*     */     }
/* 278 */     this.domain = null;
/* 279 */     return str;
/*     */   }
/*     */   
/*     */   public Name add(String paramString) throws InvalidNameException {
/* 283 */     return add(size(), paramString);
/*     */   }
/*     */   
/*     */   public Name add(int paramInt, String paramString) throws InvalidNameException {
/* 287 */     if (paramInt < 0 || paramInt > size()) {
/* 288 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/*     */     
/* 291 */     int i = paramString.length();
/* 292 */     if ((paramInt > 0 && i == 0) || (paramInt == 0 && 
/* 293 */       hasRootLabel())) {
/* 294 */       throw new InvalidNameException("Empty label must be the last label in a domain name");
/*     */     }
/*     */ 
/*     */     
/* 298 */     if (i > 0) {
/* 299 */       if (this.octets + i + 1 >= 256) {
/* 300 */         throw new InvalidNameException("Name too long");
/*     */       }
/* 302 */       this.octets = (short)(this.octets + (short)(i + 1));
/*     */     } 
/*     */     
/* 305 */     int j = size() - paramInt;
/* 306 */     verifyLabel(paramString);
/* 307 */     this.labels.add(j, paramString);
/*     */     
/* 309 */     this.domain = null;
/* 310 */     return this;
/*     */   }
/*     */   
/*     */   public Name addAll(Name paramName) throws InvalidNameException {
/* 314 */     return addAll(size(), paramName);
/*     */   }
/*     */   
/*     */   public Name addAll(int paramInt, Name paramName) throws InvalidNameException {
/* 318 */     if (paramName instanceof DnsName) {
/*     */ 
/*     */ 
/*     */       
/* 322 */       DnsName dnsName = (DnsName)paramName;
/*     */       
/* 324 */       if (dnsName.isEmpty()) {
/* 325 */         return this;
/*     */       }
/*     */       
/* 328 */       if ((paramInt > 0 && dnsName.hasRootLabel()) || (paramInt == 0 && 
/* 329 */         hasRootLabel())) {
/* 330 */         throw new InvalidNameException("Empty label must be the last label in a domain name");
/*     */       }
/*     */ 
/*     */       
/* 334 */       short s = (short)(this.octets + dnsName.octets - 1);
/* 335 */       if (s > 255) {
/* 336 */         throw new InvalidNameException("Name too long");
/*     */       }
/* 338 */       this.octets = s;
/* 339 */       int i = size() - paramInt;
/* 340 */       this.labels.addAll(i, dnsName.labels);
/*     */ 
/*     */ 
/*     */       
/* 344 */       if (isEmpty()) {
/* 345 */         this.domain = dnsName.domain;
/* 346 */       } else if (this.domain == null || dnsName.domain == null) {
/* 347 */         this.domain = null;
/* 348 */       } else if (paramInt == 0) {
/* 349 */         this.domain += (dnsName.domain.equals(".") ? "" : ".") + dnsName.domain;
/* 350 */       } else if (paramInt == size()) {
/* 351 */         dnsName.domain += (this.domain.equals(".") ? "" : ".") + this.domain;
/*     */       } else {
/* 353 */         this.domain = null;
/*     */       }
/*     */     
/* 356 */     } else if (paramName instanceof javax.naming.CompositeName) {
/* 357 */       paramName = paramName;
/*     */     }
/*     */     else {
/*     */       
/* 361 */       for (int i = paramName.size() - 1; i >= 0; i--) {
/* 362 */         add(paramInt, paramName.get(i));
/*     */       }
/*     */     } 
/* 365 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean hasRootLabel() {
/* 370 */     return (!isEmpty() && 
/* 371 */       get(0).equals(""));
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
/*     */   private int compareRange(int paramInt1, int paramInt2, Name paramName) {
/* 383 */     if (paramName instanceof javax.naming.CompositeName) {
/* 384 */       paramName = paramName;
/*     */     }
/*     */     
/* 387 */     int i = Math.min(paramInt2 - paramInt1, paramName.size());
/* 388 */     for (byte b = 0; b < i; b++) {
/* 389 */       String str1 = get(b + paramInt1);
/* 390 */       String str2 = paramName.get(b);
/*     */       
/* 392 */       int j = size() - b + paramInt1 - 1;
/*     */ 
/*     */       
/* 395 */       int k = compareLabels(str1, str2);
/* 396 */       if (k != 0) {
/* 397 */         return k;
/*     */       }
/*     */     } 
/* 400 */     return paramInt2 - paramInt1 - paramName.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getKey(int paramInt) {
/* 409 */     return keyForLabel(get(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parse(String paramString) throws InvalidNameException {
/* 418 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 420 */     for (byte b = 0; b < paramString.length(); b++) {
/* 421 */       char c = paramString.charAt(b);
/*     */       
/* 423 */       if (c == '\\') {
/* 424 */         c = getEscapedOctet(paramString, b++);
/* 425 */         if (isDigit(paramString.charAt(b))) {
/* 426 */           b += 2;
/*     */         }
/* 428 */         stringBuffer.append(c);
/*     */       }
/* 430 */       else if (c != '.') {
/* 431 */         stringBuffer.append(c);
/*     */       } else {
/*     */         
/* 434 */         add(0, stringBuffer.toString());
/*     */         
/* 436 */         stringBuffer.delete(0, b);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 445 */     if (!paramString.equals("") && !paramString.equals(".")) {
/* 446 */       add(0, stringBuffer.toString());
/*     */     }
/*     */     
/* 449 */     this.domain = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static char getEscapedOctet(String paramString, int paramInt) throws InvalidNameException {
/*     */     try {
/* 461 */       char c = paramString.charAt(++paramInt);
/* 462 */       if (isDigit(c)) {
/* 463 */         char c1 = paramString.charAt(++paramInt);
/* 464 */         char c2 = paramString.charAt(++paramInt);
/* 465 */         if (isDigit(c1) && isDigit(c2)) {
/* 466 */           return (char)((c - 48) * 100 + (c1 - 48) * 10 + c2 - 48);
/*     */         }
/*     */         
/* 469 */         throw new InvalidNameException("Invalid escape sequence in " + paramString);
/*     */       } 
/*     */ 
/*     */       
/* 473 */       return c;
/*     */     }
/* 475 */     catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 476 */       throw new InvalidNameException("Invalid escape sequence in " + paramString);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void verifyLabel(String paramString) throws InvalidNameException {
/* 486 */     if (paramString.length() > 63) {
/* 487 */       throw new InvalidNameException("Label exceeds 63 octets: " + paramString);
/*     */     }
/*     */ 
/*     */     
/* 491 */     for (byte b = 0; b < paramString.length(); b++) {
/* 492 */       char c = paramString.charAt(b);
/* 493 */       if ((c & 0xFF00) != 0) {
/* 494 */         throw new InvalidNameException("Label has two-byte char: " + paramString);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isHostNameLabel(String paramString) {
/* 504 */     for (byte b = 0; b < paramString.length(); b++) {
/* 505 */       char c = paramString.charAt(b);
/* 506 */       if (!isHostNameChar(c)) {
/* 507 */         return false;
/*     */       }
/*     */     } 
/* 510 */     return (!paramString.startsWith("-") && !paramString.endsWith("-"));
/*     */   }
/*     */   
/*     */   private static boolean isHostNameChar(char paramChar) {
/* 514 */     return (paramChar == '-' || (paramChar >= 'a' && paramChar <= 'z') || (paramChar >= 'A' && paramChar <= 'Z') || (paramChar >= '0' && paramChar <= '9'));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isDigit(char paramChar) {
/* 521 */     return (paramChar >= '0' && paramChar <= '9');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void escape(StringBuilder paramStringBuilder, String paramString) {
/* 528 */     for (byte b = 0; b < paramString.length(); b++) {
/* 529 */       char c = paramString.charAt(b);
/* 530 */       if (c == '.' || c == '\\') {
/* 531 */         paramStringBuilder.append('\\');
/*     */       }
/* 533 */       paramStringBuilder.append(c);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int compareLabels(String paramString1, String paramString2) {
/* 544 */     int i = Math.min(paramString1.length(), paramString2.length());
/* 545 */     for (byte b = 0; b < i; b++) {
/* 546 */       char c1 = paramString1.charAt(b);
/* 547 */       char c2 = paramString2.charAt(b);
/* 548 */       if (c1 >= 'A' && c1 <= 'Z') {
/* 549 */         c1 = (char)(c1 + 32);
/*     */       }
/* 551 */       if (c2 >= 'A' && c2 <= 'Z') {
/* 552 */         c2 = (char)(c2 + 32);
/*     */       }
/* 554 */       if (c1 != c2) {
/* 555 */         return c1 - c2;
/*     */       }
/*     */     } 
/* 558 */     return paramString1.length() - paramString2.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String keyForLabel(String paramString) {
/* 567 */     StringBuffer stringBuffer = new StringBuffer(paramString.length());
/* 568 */     for (byte b = 0; b < paramString.length(); b++) {
/* 569 */       char c = paramString.charAt(b);
/* 570 */       if (c >= 'A' && c <= 'Z') {
/* 571 */         c = (char)(c + 32);
/*     */       }
/* 573 */       stringBuffer.append(c);
/*     */     } 
/* 575 */     return stringBuffer.toString();
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 587 */     paramObjectOutputStream.writeObject(toString());
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*     */     try {
/* 593 */       parse((String)paramObjectInputStream.readObject());
/* 594 */     } catch (InvalidNameException invalidNameException) {
/*     */       
/* 596 */       throw new StreamCorruptedException("Invalid name: " + this.domain);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/dns/DnsName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */