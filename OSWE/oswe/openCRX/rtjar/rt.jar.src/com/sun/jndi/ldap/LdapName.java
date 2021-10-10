/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.BasicAttributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LdapName
/*     */   implements Name
/*     */ {
/*     */   private transient String unparsed;
/*     */   private transient Vector<Rdn> rdns;
/*     */   private transient boolean valuesCaseSensitive = false;
/*     */   static final long serialVersionUID = -1595520034788997356L;
/*     */   
/*     */   public LdapName(String paramString) throws InvalidNameException {
/*  93 */     this.unparsed = paramString;
/*  94 */     parse();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LdapName(String paramString, Vector<Rdn> paramVector) {
/* 103 */     this.unparsed = paramString;
/* 104 */     this.rdns = (Vector<Rdn>)paramVector.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LdapName(String paramString, Vector<Rdn> paramVector, int paramInt1, int paramInt2) {
/* 113 */     this.unparsed = paramString;
/* 114 */     this.rdns = new Vector<>();
/* 115 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 116 */       this.rdns.addElement(paramVector.elementAt(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 122 */     return new LdapName(this.unparsed, this.rdns);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 126 */     if (this.unparsed != null) {
/* 127 */       return this.unparsed;
/*     */     }
/*     */     
/* 130 */     StringBuffer stringBuffer = new StringBuffer();
/* 131 */     for (int i = this.rdns.size() - 1; i >= 0; i--) {
/* 132 */       if (i < this.rdns.size() - 1) {
/* 133 */         stringBuffer.append(',');
/*     */       }
/* 135 */       Rdn rdn = this.rdns.elementAt(i);
/* 136 */       stringBuffer.append(rdn);
/*     */     } 
/*     */     
/* 139 */     this.unparsed = new String(stringBuffer);
/* 140 */     return this.unparsed;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 144 */     return (paramObject instanceof LdapName && 
/* 145 */       compareTo(paramObject) == 0);
/*     */   }
/*     */   
/*     */   public int compareTo(Object paramObject) {
/* 149 */     LdapName ldapName = (LdapName)paramObject;
/*     */     
/* 151 */     if (paramObject == this || (this.unparsed != null && this.unparsed
/* 152 */       .equals(ldapName.unparsed))) {
/* 153 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 157 */     int i = Math.min(this.rdns.size(), ldapName.rdns.size());
/* 158 */     for (byte b = 0; b < i; b++) {
/*     */       
/* 160 */       Rdn rdn1 = this.rdns.elementAt(b);
/* 161 */       Rdn rdn2 = ldapName.rdns.elementAt(b);
/*     */       
/* 163 */       int j = rdn1.compareTo(rdn2);
/* 164 */       if (j != 0) {
/* 165 */         return j;
/*     */       }
/*     */     } 
/* 168 */     return this.rdns.size() - ldapName.rdns.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 173 */     int i = 0;
/*     */ 
/*     */     
/* 176 */     for (byte b = 0; b < this.rdns.size(); b++) {
/* 177 */       Rdn rdn = this.rdns.elementAt(b);
/* 178 */       i += rdn.hashCode();
/*     */     } 
/* 180 */     return i;
/*     */   }
/*     */   
/*     */   public int size() {
/* 184 */     return this.rdns.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 188 */     return this.rdns.isEmpty();
/*     */   }
/*     */   
/*     */   public Enumeration<String> getAll() {
/* 192 */     final Enumeration<Rdn> enum_ = this.rdns.elements();
/*     */     
/* 194 */     return new Enumeration<String>() {
/*     */         public boolean hasMoreElements() {
/* 196 */           return enum_.hasMoreElements();
/*     */         }
/*     */         public String nextElement() {
/* 199 */           return ((LdapName.Rdn)enum_.nextElement()).toString();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public String get(int paramInt) {
/* 205 */     return ((Rdn)this.rdns.elementAt(paramInt)).toString();
/*     */   }
/*     */   
/*     */   public Name getPrefix(int paramInt) {
/* 209 */     return new LdapName(null, this.rdns, 0, paramInt);
/*     */   }
/*     */   
/*     */   public Name getSuffix(int paramInt) {
/* 213 */     return new LdapName(null, this.rdns, paramInt, this.rdns.size());
/*     */   }
/*     */   
/*     */   public boolean startsWith(Name paramName) {
/* 217 */     int i = this.rdns.size();
/* 218 */     int j = paramName.size();
/* 219 */     return (i >= j && 
/* 220 */       matches(0, j, paramName));
/*     */   }
/*     */   
/*     */   public boolean endsWith(Name paramName) {
/* 224 */     int i = this.rdns.size();
/* 225 */     int j = paramName.size();
/* 226 */     return (i >= j && 
/* 227 */       matches(i - j, i, paramName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValuesCaseSensitive(boolean paramBoolean) {
/* 236 */     toString();
/* 237 */     this.rdns = null;
/*     */     try {
/* 239 */       parse();
/* 240 */     } catch (InvalidNameException invalidNameException) {
/*     */       
/* 242 */       throw new IllegalStateException("Cannot parse name: " + this.unparsed);
/*     */     } 
/* 244 */     this.valuesCaseSensitive = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matches(int paramInt1, int paramInt2, Name paramName) {
/* 255 */     for (int i = paramInt1; i < paramInt2; i++) {
/*     */       Rdn rdn;
/* 257 */       if (paramName instanceof LdapName) {
/* 258 */         LdapName ldapName = (LdapName)paramName;
/* 259 */         rdn = ldapName.rdns.elementAt(i - paramInt1);
/*     */       } else {
/* 261 */         String str = paramName.get(i - paramInt1);
/*     */         try {
/* 263 */           rdn = (new DnParser(str, this.valuesCaseSensitive)).getRdn();
/* 264 */         } catch (InvalidNameException invalidNameException) {
/* 265 */           return false;
/*     */         } 
/*     */       } 
/*     */       
/* 269 */       if (!rdn.equals(this.rdns.elementAt(i))) {
/* 270 */         return false;
/*     */       }
/*     */     } 
/* 273 */     return true;
/*     */   }
/*     */   
/*     */   public Name addAll(Name paramName) throws InvalidNameException {
/* 277 */     return addAll(size(), paramName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Name addAll(int paramInt, Name paramName) throws InvalidNameException {
/* 285 */     if (paramName instanceof LdapName) {
/* 286 */       LdapName ldapName = (LdapName)paramName;
/* 287 */       for (byte b = 0; b < ldapName.rdns.size(); b++) {
/* 288 */         this.rdns.insertElementAt(ldapName.rdns.elementAt(b), paramInt++);
/*     */       }
/*     */     } else {
/* 291 */       Enumeration<String> enumeration = paramName.getAll();
/* 292 */       while (enumeration.hasMoreElements()) {
/* 293 */         DnParser dnParser = new DnParser(enumeration.nextElement(), this.valuesCaseSensitive);
/*     */         
/* 295 */         this.rdns.insertElementAt(dnParser.getRdn(), paramInt++);
/*     */       } 
/*     */     } 
/* 298 */     this.unparsed = null;
/* 299 */     return this;
/*     */   }
/*     */   
/*     */   public Name add(String paramString) throws InvalidNameException {
/* 303 */     return add(size(), paramString);
/*     */   }
/*     */   
/*     */   public Name add(int paramInt, String paramString) throws InvalidNameException {
/* 307 */     Rdn rdn = (new DnParser(paramString, this.valuesCaseSensitive)).getRdn();
/* 308 */     this.rdns.insertElementAt(rdn, paramInt);
/* 309 */     this.unparsed = null;
/* 310 */     return this;
/*     */   }
/*     */   
/*     */   public Object remove(int paramInt) throws InvalidNameException {
/* 314 */     String str = get(paramInt);
/* 315 */     this.rdns.removeElementAt(paramInt);
/* 316 */     this.unparsed = null;
/* 317 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   private void parse() throws InvalidNameException {
/* 322 */     this.rdns = (new DnParser(this.unparsed, this.valuesCaseSensitive)).getDn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isWhitespace(char paramChar) {
/* 329 */     return (paramChar == ' ' || paramChar == '\r');
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
/*     */   public static String escapeAttributeValue(Object paramObject) {
/* 345 */     return TypeAndValue.escapeValue(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object unescapeAttributeValue(String paramString) {
/* 354 */     return TypeAndValue.unescapeValue(paramString);
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
/* 366 */     paramObjectOutputStream.writeObject(toString());
/* 367 */     paramObjectOutputStream.writeBoolean(this.valuesCaseSensitive);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 372 */     this.unparsed = (String)paramObjectInputStream.readObject();
/* 373 */     this.valuesCaseSensitive = paramObjectInputStream.readBoolean();
/*     */     try {
/* 375 */       parse();
/* 376 */     } catch (InvalidNameException invalidNameException) {
/*     */       
/* 378 */       throw new StreamCorruptedException("Invalid name: " + this.unparsed);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class DnParser
/*     */   {
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     private final char[] chars;
/*     */ 
/*     */     
/*     */     private final int len;
/*     */     
/* 394 */     private int cur = 0;
/*     */ 
/*     */     
/*     */     private boolean valuesCaseSensitive;
/*     */ 
/*     */ 
/*     */     
/*     */     DnParser(String param1String, boolean param1Boolean) throws InvalidNameException {
/* 402 */       this.name = param1String;
/* 403 */       this.len = param1String.length();
/* 404 */       this.chars = param1String.toCharArray();
/* 405 */       this.valuesCaseSensitive = param1Boolean;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Vector<LdapName.Rdn> getDn() throws InvalidNameException {
/* 412 */       this.cur = 0;
/* 413 */       Vector<LdapName.Rdn> vector = new Vector(this.len / 3 + 10);
/*     */       
/* 415 */       if (this.len == 0) {
/* 416 */         return vector;
/*     */       }
/*     */       
/* 419 */       vector.addElement(parseRdn());
/* 420 */       while (this.cur < this.len) {
/* 421 */         if (this.chars[this.cur] == ',' || this.chars[this.cur] == ';') {
/* 422 */           this.cur++;
/* 423 */           vector.insertElementAt(parseRdn(), 0); continue;
/*     */         } 
/* 425 */         throw new InvalidNameException("Invalid name: " + this.name);
/*     */       } 
/*     */       
/* 428 */       return vector;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LdapName.Rdn getRdn() throws InvalidNameException {
/* 435 */       LdapName.Rdn rdn = parseRdn();
/* 436 */       if (this.cur < this.len) {
/* 437 */         throw new InvalidNameException("Invalid RDN: " + this.name);
/*     */       }
/* 439 */       return rdn;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private LdapName.Rdn parseRdn() throws InvalidNameException {
/* 448 */       LdapName.Rdn rdn = new LdapName.Rdn();
/* 449 */       while (this.cur < this.len) {
/* 450 */         consumeWhitespace();
/* 451 */         String str1 = parseAttrType();
/* 452 */         consumeWhitespace();
/* 453 */         if (this.cur >= this.len || this.chars[this.cur] != '=') {
/* 454 */           throw new InvalidNameException("Invalid name: " + this.name);
/*     */         }
/* 456 */         this.cur++;
/* 457 */         consumeWhitespace();
/* 458 */         String str2 = parseAttrValue();
/* 459 */         consumeWhitespace();
/*     */         
/* 461 */         rdn.add(new LdapName.TypeAndValue(str1, str2, this.valuesCaseSensitive));
/* 462 */         if (this.cur >= this.len || this.chars[this.cur] != '+') {
/*     */           break;
/*     */         }
/* 465 */         this.cur++;
/*     */       } 
/* 467 */       return rdn;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String parseAttrType() throws InvalidNameException {
/* 479 */       int i = this.cur;
/* 480 */       while (this.cur < this.len) {
/* 481 */         char c = this.chars[this.cur];
/* 482 */         if (Character.isLetterOrDigit(c) || c == '.' || c == '-' || c == ' ')
/*     */         {
/*     */ 
/*     */           
/* 486 */           this.cur++;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 492 */       while (this.cur > i && this.chars[this.cur - 1] == ' ') {
/* 493 */         this.cur--;
/*     */       }
/*     */       
/* 496 */       if (i == this.cur) {
/* 497 */         throw new InvalidNameException("Invalid name: " + this.name);
/*     */       }
/* 499 */       return new String(this.chars, i, this.cur - i);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String parseAttrValue() throws InvalidNameException {
/* 508 */       if (this.cur < this.len && this.chars[this.cur] == '#')
/* 509 */         return parseBinaryAttrValue(); 
/* 510 */       if (this.cur < this.len && this.chars[this.cur] == '"') {
/* 511 */         return parseQuotedAttrValue();
/*     */       }
/* 513 */       return parseStringAttrValue();
/*     */     }
/*     */ 
/*     */     
/*     */     private String parseBinaryAttrValue() throws InvalidNameException {
/* 518 */       int i = this.cur;
/* 519 */       this.cur++;
/* 520 */       while (this.cur < this.len && 
/* 521 */         Character.isLetterOrDigit(this.chars[this.cur])) {
/* 522 */         this.cur++;
/*     */       }
/* 524 */       return new String(this.chars, i, this.cur - i);
/*     */     }
/*     */ 
/*     */     
/*     */     private String parseQuotedAttrValue() throws InvalidNameException {
/* 529 */       int i = this.cur;
/* 530 */       this.cur++;
/*     */       
/* 532 */       while (this.cur < this.len && this.chars[this.cur] != '"') {
/* 533 */         if (this.chars[this.cur] == '\\') {
/* 534 */           this.cur++;
/*     */         }
/* 536 */         this.cur++;
/*     */       } 
/* 538 */       if (this.cur >= this.len) {
/* 539 */         throw new InvalidNameException("Invalid name: " + this.name);
/*     */       }
/* 541 */       this.cur++;
/*     */       
/* 543 */       return new String(this.chars, i, this.cur - i);
/*     */     }
/*     */ 
/*     */     
/*     */     private String parseStringAttrValue() throws InvalidNameException {
/* 548 */       int i = this.cur;
/* 549 */       int j = -1;
/*     */       
/* 551 */       while (this.cur < this.len && !atTerminator()) {
/* 552 */         if (this.chars[this.cur] == '\\')
/*     */         {
/* 554 */           j = ++this.cur;
/*     */         }
/* 556 */         this.cur++;
/*     */       } 
/* 558 */       if (this.cur > this.len) {
/* 559 */         throw new InvalidNameException("Invalid name: " + this.name);
/*     */       }
/*     */       
/*     */       int k;
/*     */       
/* 564 */       for (k = this.cur; k > i && LdapName
/* 565 */         .isWhitespace(this.chars[k - 1]) && j != k - 1; k--);
/*     */ 
/*     */ 
/*     */       
/* 569 */       return new String(this.chars, i, k - i);
/*     */     }
/*     */     
/*     */     private void consumeWhitespace() {
/* 573 */       while (this.cur < this.len && LdapName.isWhitespace(this.chars[this.cur])) {
/* 574 */         this.cur++;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean atTerminator() {
/* 583 */       return (this.cur < this.len && (this.chars[this.cur] == ',' || this.chars[this.cur] == ';' || this.chars[this.cur] == '+'));
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
/*     */   
/*     */   static class Rdn
/*     */   {
/* 600 */     private final Vector<LdapName.TypeAndValue> tvs = new Vector<>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void add(LdapName.TypeAndValue param1TypeAndValue) {
/*     */       byte b;
/* 607 */       for (b = 0; b < this.tvs.size(); b++) {
/* 608 */         int i = param1TypeAndValue.compareTo(this.tvs.elementAt(b));
/* 609 */         if (i == 0)
/*     */           return; 
/* 611 */         if (i < 0) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 616 */       this.tvs.insertElementAt(param1TypeAndValue, b);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 620 */       StringBuffer stringBuffer = new StringBuffer();
/* 621 */       for (byte b = 0; b < this.tvs.size(); b++) {
/* 622 */         if (b > 0) {
/* 623 */           stringBuffer.append('+');
/*     */         }
/* 625 */         stringBuffer.append(this.tvs.elementAt(b));
/*     */       } 
/* 627 */       return new String(stringBuffer);
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 631 */       return (param1Object instanceof Rdn && 
/* 632 */         compareTo(param1Object) == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(Object param1Object) {
/* 637 */       Rdn rdn = (Rdn)param1Object;
/* 638 */       int i = Math.min(this.tvs.size(), rdn.tvs.size());
/* 639 */       for (byte b = 0; b < i; b++) {
/*     */         
/* 641 */         LdapName.TypeAndValue typeAndValue = this.tvs.elementAt(b);
/* 642 */         int j = typeAndValue.compareTo(rdn.tvs.elementAt(b));
/* 643 */         if (j != 0) {
/* 644 */           return j;
/*     */         }
/*     */       } 
/* 647 */       return this.tvs.size() - rdn.tvs.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 652 */       int i = 0;
/*     */ 
/*     */       
/* 655 */       for (byte b = 0; b < this.tvs.size(); b++) {
/* 656 */         i += ((LdapName.TypeAndValue)this.tvs.elementAt(b)).hashCode();
/*     */       }
/* 658 */       return i;
/*     */     }
/*     */     
/*     */     Attributes toAttributes() {
/* 662 */       BasicAttributes basicAttributes = new BasicAttributes(true);
/*     */ 
/*     */ 
/*     */       
/* 666 */       for (byte b = 0; b < this.tvs.size(); b++) {
/* 667 */         LdapName.TypeAndValue typeAndValue = this.tvs.elementAt(b); Attribute attribute;
/* 668 */         if ((attribute = basicAttributes.get(typeAndValue.getType())) == null) {
/* 669 */           basicAttributes.put(typeAndValue.getType(), typeAndValue.getUnescapedValue());
/*     */         } else {
/* 671 */           attribute.add(typeAndValue.getUnescapedValue());
/*     */         } 
/*     */       } 
/* 674 */       return basicAttributes;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class TypeAndValue
/*     */   {
/*     */     private final String type;
/*     */ 
/*     */     
/*     */     private final String value;
/*     */ 
/*     */     
/*     */     private final boolean binary;
/*     */     
/*     */     private final boolean valueCaseSensitive;
/*     */     
/* 692 */     private String comparable = null;
/*     */     
/*     */     TypeAndValue(String param1String1, String param1String2, boolean param1Boolean) {
/* 695 */       this.type = param1String1;
/* 696 */       this.value = param1String2;
/* 697 */       this.binary = param1String2.startsWith("#");
/* 698 */       this.valueCaseSensitive = param1Boolean;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 702 */       return this.type + "=" + this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(Object param1Object) {
/* 709 */       TypeAndValue typeAndValue = (TypeAndValue)param1Object;
/*     */       
/* 711 */       int i = this.type.compareToIgnoreCase(typeAndValue.type);
/* 712 */       if (i != 0) {
/* 713 */         return i;
/*     */       }
/* 715 */       if (this.value.equals(typeAndValue.value)) {
/* 716 */         return 0;
/*     */       }
/* 718 */       return getValueComparable().compareTo(typeAndValue.getValueComparable());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 723 */       if (!(param1Object instanceof TypeAndValue)) {
/* 724 */         return false;
/*     */       }
/* 726 */       TypeAndValue typeAndValue = (TypeAndValue)param1Object;
/* 727 */       return (this.type.equalsIgnoreCase(typeAndValue.type) && (this.value
/* 728 */         .equals(typeAndValue.value) || 
/* 729 */         getValueComparable().equals(typeAndValue.getValueComparable())));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 734 */       return this.type.toUpperCase(Locale.ENGLISH).hashCode() + 
/* 735 */         getValueComparable().hashCode();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String getType() {
/* 742 */       return this.type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object getUnescapedValue() {
/* 749 */       return unescapeValue(this.value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String getValueComparable() {
/* 762 */       if (this.comparable != null) {
/* 763 */         return this.comparable;
/*     */       }
/*     */ 
/*     */       
/* 767 */       if (this.binary) {
/* 768 */         this.comparable = this.value.toUpperCase(Locale.ENGLISH);
/*     */       } else {
/* 770 */         this.comparable = (String)unescapeValue(this.value);
/* 771 */         if (!this.valueCaseSensitive)
/*     */         {
/* 773 */           this.comparable = this.comparable.toUpperCase(Locale.ENGLISH);
/*     */         }
/*     */       } 
/* 776 */       return this.comparable;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static String escapeValue(Object param1Object) {
/* 784 */       return (param1Object instanceof byte[]) ? 
/* 785 */         escapeBinaryValue((byte[])param1Object) : 
/* 786 */         escapeStringValue((String)param1Object);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static String escapeStringValue(String param1String) {
/* 799 */       char[] arrayOfChar = param1String.toCharArray();
/* 800 */       StringBuffer stringBuffer = new StringBuffer(2 * param1String.length());
/*     */       
/*     */       byte b1;
/*     */       
/* 804 */       for (b1 = 0; b1 < arrayOfChar.length && LdapName
/* 805 */         .isWhitespace(arrayOfChar[b1]); b1++);
/*     */ 
/*     */       
/*     */       int i;
/*     */       
/* 810 */       for (i = arrayOfChar.length - 1; i >= 0 && LdapName
/* 811 */         .isWhitespace(arrayOfChar[i]); i--);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 816 */       for (byte b2 = 0; b2 < arrayOfChar.length; b2++) {
/* 817 */         char c = arrayOfChar[b2];
/* 818 */         if (b2 < b1 || b2 > i || ",=+<>#;\"\\".indexOf(c) >= 0) {
/* 819 */           stringBuffer.append('\\');
/*     */         }
/* 821 */         stringBuffer.append(c);
/*     */       } 
/* 823 */       return new String(stringBuffer);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static String escapeBinaryValue(byte[] param1ArrayOfbyte) {
/* 832 */       StringBuffer stringBuffer = new StringBuffer(1 + 2 * param1ArrayOfbyte.length);
/* 833 */       stringBuffer.append("#");
/*     */       
/* 835 */       for (byte b = 0; b < param1ArrayOfbyte.length; b++) {
/* 836 */         byte b1 = param1ArrayOfbyte[b];
/* 837 */         stringBuffer.append(Character.forDigit(0xF & b1 >>> 4, 16));
/* 838 */         stringBuffer.append(Character.forDigit(0xF & b1, 16));
/*     */       } 
/*     */       
/* 841 */       return (new String(stringBuffer)).toUpperCase(Locale.ENGLISH);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Object unescapeValue(String param1String) {
/* 853 */       char[] arrayOfChar = param1String.toCharArray();
/* 854 */       byte b = 0;
/* 855 */       int i = arrayOfChar.length;
/*     */ 
/*     */       
/* 858 */       while (b < i && LdapName.isWhitespace(arrayOfChar[b])) {
/* 859 */         b++;
/*     */       }
/* 861 */       while (b < i && LdapName.isWhitespace(arrayOfChar[i - 1])) {
/* 862 */         i--;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 869 */       if (i != arrayOfChar.length && b < i && arrayOfChar[i - 1] == '\\')
/*     */       {
/*     */         
/* 872 */         i++;
/*     */       }
/* 874 */       if (b >= i) {
/* 875 */         return "";
/*     */       }
/*     */       
/* 878 */       if (arrayOfChar[b] == '#')
/*     */       {
/* 880 */         return decodeHexPairs(arrayOfChar, ++b, i);
/*     */       }
/*     */ 
/*     */       
/* 884 */       if (arrayOfChar[b] == '"' && arrayOfChar[i - 1] == '"') {
/* 885 */         b++;
/* 886 */         i--;
/*     */       } 
/*     */       
/* 889 */       StringBuffer stringBuffer = new StringBuffer(i - b);
/* 890 */       int j = -1;
/*     */       int k;
/* 892 */       for (k = b; k < i; k++) {
/* 893 */         if (arrayOfChar[k] == '\\' && k + 1 < i) {
/* 894 */           if (!Character.isLetterOrDigit(arrayOfChar[k + 1])) {
/* 895 */             k++;
/* 896 */             stringBuffer.append(arrayOfChar[k]);
/* 897 */             j = k;
/*     */           }
/*     */           else {
/*     */             
/* 901 */             byte[] arrayOfByte = getUtf8Octets(arrayOfChar, k, i);
/* 902 */             if (arrayOfByte.length > 0) {
/*     */               try {
/* 904 */                 stringBuffer.append(new String(arrayOfByte, "UTF8"));
/* 905 */               } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */ 
/*     */               
/* 908 */               k += arrayOfByte.length * 3 - 1;
/*     */             } else {
/* 910 */               throw new IllegalArgumentException("Not a valid attribute string value:" + param1String + ", improper usage of backslash");
/*     */             }
/*     */           
/*     */           } 
/*     */         } else {
/*     */           
/* 916 */           stringBuffer.append(arrayOfChar[k]);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 922 */       k = stringBuffer.length();
/* 923 */       if (LdapName.isWhitespace(stringBuffer.charAt(k - 1)) && j != i - 1) {
/* 924 */         stringBuffer.setLength(k - 1);
/*     */       }
/*     */       
/* 927 */       return new String(stringBuffer);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static byte[] decodeHexPairs(char[] param1ArrayOfchar, int param1Int1, int param1Int2) {
/* 937 */       byte[] arrayOfByte = new byte[(param1Int2 - param1Int1) / 2];
/* 938 */       for (byte b = 0; param1Int1 + 1 < param1Int2; b++) {
/* 939 */         int i = Character.digit(param1ArrayOfchar[param1Int1], 16);
/* 940 */         int j = Character.digit(param1ArrayOfchar[param1Int1 + 1], 16);
/* 941 */         if (i < 0 || j < 0) {
/*     */           break;
/*     */         }
/* 944 */         arrayOfByte[b] = (byte)((i << 4) + j);
/* 945 */         param1Int1 += 2;
/*     */       } 
/* 947 */       if (param1Int1 != param1Int2) {
/* 948 */         throw new IllegalArgumentException("Illegal attribute value: #" + new String(param1ArrayOfchar));
/*     */       }
/*     */       
/* 951 */       return arrayOfByte;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static byte[] getUtf8Octets(char[] param1ArrayOfchar, int param1Int1, int param1Int2) {
/* 963 */       byte[] arrayOfByte1 = new byte[(param1Int2 - param1Int1) / 3];
/* 964 */       byte b = 0;
/*     */       
/* 966 */       while (param1Int1 + 2 < param1Int2 && param1ArrayOfchar[param1Int1++] == '\\') {
/*     */         
/* 968 */         int i = Character.digit(param1ArrayOfchar[param1Int1++], 16);
/* 969 */         int j = Character.digit(param1ArrayOfchar[param1Int1++], 16);
/* 970 */         if (i < 0 || j < 0) {
/*     */           break;
/*     */         }
/* 973 */         arrayOfByte1[b++] = (byte)((i << 4) + j);
/*     */       } 
/*     */       
/* 976 */       if (b == arrayOfByte1.length) {
/* 977 */         return arrayOfByte1;
/*     */       }
/* 979 */       byte[] arrayOfByte2 = new byte[b];
/* 980 */       System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, b);
/* 981 */       return arrayOfByte2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */