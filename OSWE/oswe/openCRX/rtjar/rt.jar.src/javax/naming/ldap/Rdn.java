/*     */ package javax.naming.ldap;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Locale;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Rdn
/*     */   implements Serializable, Comparable<Object>
/*     */ {
/*     */   private transient ArrayList<RdnEntry> entries;
/*     */   private static final int DEFAULT_SIZE = 1;
/*     */   private static final long serialVersionUID = -5994465067210009656L;
/*     */   private static final String escapees = ",=+<>#;\"\\";
/*     */   
/*     */   public Rdn(Attributes paramAttributes) throws InvalidNameException {
/* 130 */     if (paramAttributes.size() == 0) {
/* 131 */       throw new InvalidNameException("Attributes cannot be empty");
/*     */     }
/* 133 */     this.entries = new ArrayList<>(paramAttributes.size());
/* 134 */     NamingEnumeration<? extends Attribute> namingEnumeration = paramAttributes.getAll();
/*     */     try {
/* 136 */       for (byte b = 0; namingEnumeration.hasMore(); b++) {
/* 137 */         RdnEntry rdnEntry = new RdnEntry();
/* 138 */         Attribute attribute = namingEnumeration.next();
/* 139 */         rdnEntry.type = attribute.getID();
/* 140 */         rdnEntry.value = attribute.get();
/* 141 */         this.entries.add(b, rdnEntry);
/*     */       } 
/* 143 */     } catch (NamingException namingException) {
/*     */       
/* 145 */       InvalidNameException invalidNameException = new InvalidNameException(namingException.getMessage());
/* 146 */       invalidNameException.initCause(namingException);
/* 147 */       throw invalidNameException;
/*     */     } 
/* 149 */     sort();
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
/*     */   public Rdn(String paramString) throws InvalidNameException {
/* 164 */     this.entries = new ArrayList<>(1);
/* 165 */     (new Rfc2253Parser(paramString)).parseRdn(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rdn(Rdn paramRdn) {
/* 175 */     this.entries = new ArrayList<>(paramRdn.entries.size());
/* 176 */     this.entries.addAll(paramRdn.entries);
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
/*     */   public Rdn(String paramString, Object paramObject) throws InvalidNameException {
/* 194 */     if (paramObject == null) {
/* 195 */       throw new NullPointerException("Cannot set value to null");
/*     */     }
/* 197 */     if (paramString.equals("") || isEmptyValue(paramObject)) {
/* 198 */       throw new InvalidNameException("type or value cannot be empty, type:" + paramString + " value:" + paramObject);
/*     */     }
/*     */ 
/*     */     
/* 202 */     this.entries = new ArrayList<>(1);
/* 203 */     put(paramString, paramObject);
/*     */   }
/*     */   
/*     */   private boolean isEmptyValue(Object paramObject) {
/* 207 */     return ((paramObject instanceof String && paramObject.equals("")) || (paramObject instanceof byte[] && ((byte[])paramObject).length == 0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Rdn() {
/* 213 */     this.entries = new ArrayList<>(1);
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
/*     */   Rdn put(String paramString, Object paramObject) {
/* 231 */     RdnEntry rdnEntry = new RdnEntry();
/* 232 */     rdnEntry.type = paramString;
/* 233 */     if (paramObject instanceof byte[]) {
/* 234 */       rdnEntry.value = ((byte[])paramObject).clone();
/*     */     } else {
/* 236 */       rdnEntry.value = paramObject;
/*     */     } 
/* 238 */     this.entries.add(rdnEntry);
/* 239 */     return this;
/*     */   }
/*     */   
/*     */   void sort() {
/* 243 */     if (this.entries.size() > 1) {
/* 244 */       Collections.sort(this.entries);
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
/*     */   public Object getValue() {
/* 260 */     return ((RdnEntry)this.entries.get(0)).getValue();
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
/*     */   public String getType() {
/* 278 */     return ((RdnEntry)this.entries.get(0)).getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 289 */     StringBuilder stringBuilder = new StringBuilder();
/* 290 */     int i = this.entries.size();
/* 291 */     if (i > 0) {
/* 292 */       stringBuilder.append(this.entries.get(0));
/*     */     }
/* 294 */     for (byte b = 1; b < i; b++) {
/* 295 */       stringBuilder.append('+');
/* 296 */       stringBuilder.append(this.entries.get(b));
/*     */     } 
/* 298 */     return stringBuilder.toString();
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
/*     */   public int compareTo(Object paramObject) {
/* 320 */     if (!(paramObject instanceof Rdn)) {
/* 321 */       throw new ClassCastException("The obj is not a Rdn");
/*     */     }
/* 323 */     if (paramObject == this) {
/* 324 */       return 0;
/*     */     }
/* 326 */     Rdn rdn = (Rdn)paramObject;
/* 327 */     int i = Math.min(this.entries.size(), rdn.entries.size());
/* 328 */     for (byte b = 0; b < i; b++) {
/*     */ 
/*     */       
/* 331 */       int j = ((RdnEntry)this.entries.get(b)).compareTo(rdn.entries.get(b));
/* 332 */       if (j != 0) {
/* 333 */         return j;
/*     */       }
/*     */     } 
/* 336 */     return this.entries.size() - rdn.entries.size();
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
/*     */   public boolean equals(Object paramObject) {
/* 361 */     if (paramObject == this) {
/* 362 */       return true;
/*     */     }
/* 364 */     if (!(paramObject instanceof Rdn)) {
/* 365 */       return false;
/*     */     }
/* 367 */     Rdn rdn = (Rdn)paramObject;
/* 368 */     if (this.entries.size() != rdn.size()) {
/* 369 */       return false;
/*     */     }
/* 371 */     for (byte b = 0; b < this.entries.size(); b++) {
/* 372 */       if (!((RdnEntry)this.entries.get(b)).equals(rdn.entries.get(b))) {
/* 373 */         return false;
/*     */       }
/*     */     } 
/* 376 */     return true;
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
/*     */   public int hashCode() {
/* 390 */     int i = 0;
/*     */ 
/*     */     
/* 393 */     for (byte b = 0; b < this.entries.size(); b++) {
/* 394 */       i += ((RdnEntry)this.entries.get(b)).hashCode();
/*     */     }
/* 396 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attributes toAttributes() {
/* 407 */     BasicAttributes basicAttributes = new BasicAttributes(true);
/* 408 */     for (byte b = 0; b < this.entries.size(); b++) {
/* 409 */       RdnEntry rdnEntry = this.entries.get(b);
/* 410 */       Attribute attribute = basicAttributes.put(rdnEntry.getType(), rdnEntry.getValue());
/* 411 */       if (attribute != null) {
/* 412 */         attribute.add(rdnEntry.getValue());
/* 413 */         basicAttributes.put(attribute);
/*     */       } 
/*     */     } 
/* 416 */     return basicAttributes;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class RdnEntry
/*     */     implements Comparable<RdnEntry>
/*     */   {
/*     */     private String type;
/*     */     
/*     */     private Object value;
/* 426 */     private String comparable = null;
/*     */     
/*     */     String getType() {
/* 429 */       return this.type;
/*     */     }
/*     */     
/*     */     Object getValue() {
/* 433 */       return this.value;
/*     */     }
/*     */     
/*     */     public int compareTo(RdnEntry param1RdnEntry) {
/* 437 */       int i = this.type.compareToIgnoreCase(param1RdnEntry.type);
/* 438 */       if (i != 0) {
/* 439 */         return i;
/*     */       }
/* 441 */       if (this.value.equals(param1RdnEntry.value)) {
/* 442 */         return 0;
/*     */       }
/* 444 */       return getValueComparable().compareTo(param1RdnEntry
/* 445 */           .getValueComparable());
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 449 */       if (param1Object == this) {
/* 450 */         return true;
/*     */       }
/* 452 */       if (!(param1Object instanceof RdnEntry)) {
/* 453 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 457 */       RdnEntry rdnEntry = (RdnEntry)param1Object;
/* 458 */       return (this.type.equalsIgnoreCase(rdnEntry.type) && 
/* 459 */         getValueComparable().equals(rdnEntry
/* 460 */           .getValueComparable()));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 464 */       return this.type.toUpperCase(Locale.ENGLISH).hashCode() + 
/* 465 */         getValueComparable().hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 469 */       return this.type + "=" + Rdn.escapeValue(this.value);
/*     */     }
/*     */     
/*     */     private String getValueComparable() {
/* 473 */       if (this.comparable != null) {
/* 474 */         return this.comparable;
/*     */       }
/*     */ 
/*     */       
/* 478 */       if (this.value instanceof byte[]) {
/* 479 */         this.comparable = Rdn.escapeBinaryValue((byte[])this.value);
/*     */       } else {
/* 481 */         this.comparable = ((String)this.value).toUpperCase(Locale.ENGLISH);
/*     */       } 
/* 483 */       return this.comparable;
/*     */     }
/*     */ 
/*     */     
/*     */     private RdnEntry() {}
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 492 */     return this.entries.size();
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
/*     */   public static String escapeValue(Object paramObject) {
/* 511 */     return (paramObject instanceof byte[]) ? 
/* 512 */       escapeBinaryValue((byte[])paramObject) : 
/* 513 */       escapeStringValue((String)paramObject);
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
/*     */   private static String escapeStringValue(String paramString) {
/* 527 */     char[] arrayOfChar = paramString.toCharArray();
/* 528 */     StringBuilder stringBuilder = new StringBuilder(2 * paramString.length());
/*     */     
/*     */     byte b1;
/*     */     
/* 532 */     for (b1 = 0; b1 < arrayOfChar.length && 
/* 533 */       isWhitespace(arrayOfChar[b1]); b1++);
/*     */ 
/*     */     
/*     */     int i;
/*     */     
/* 538 */     for (i = arrayOfChar.length - 1; i >= 0 && 
/* 539 */       isWhitespace(arrayOfChar[i]); i--);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 544 */     for (byte b2 = 0; b2 < arrayOfChar.length; b2++) {
/* 545 */       char c = arrayOfChar[b2];
/* 546 */       if (b2 < b1 || b2 > i || ",=+<>#;\"\\".indexOf(c) >= 0) {
/* 547 */         stringBuilder.append('\\');
/*     */       }
/* 549 */       stringBuilder.append(c);
/*     */     } 
/* 551 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String escapeBinaryValue(byte[] paramArrayOfbyte) {
/* 562 */     StringBuilder stringBuilder = new StringBuilder(1 + 2 * paramArrayOfbyte.length);
/* 563 */     stringBuilder.append("#");
/*     */     
/* 565 */     for (byte b = 0; b < paramArrayOfbyte.length; b++) {
/* 566 */       byte b1 = paramArrayOfbyte[b];
/* 567 */       stringBuilder.append(Character.forDigit(0xF & b1 >>> 4, 16));
/* 568 */       stringBuilder.append(Character.forDigit(0xF & b1, 16));
/*     */     } 
/* 570 */     return stringBuilder.toString();
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
/*     */   public static Object unescapeValue(String paramString) {
/* 595 */     char[] arrayOfChar = paramString.toCharArray();
/* 596 */     byte b = 0;
/* 597 */     int i = arrayOfChar.length;
/*     */ 
/*     */     
/* 600 */     while (b < i && isWhitespace(arrayOfChar[b])) {
/* 601 */       b++;
/*     */     }
/*     */     
/* 604 */     while (b < i && isWhitespace(arrayOfChar[i - 1])) {
/* 605 */       i--;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 611 */     if (i != arrayOfChar.length && b < i && arrayOfChar[i - 1] == '\\')
/*     */     {
/*     */       
/* 614 */       i++;
/*     */     }
/* 616 */     if (b >= i) {
/* 617 */       return "";
/*     */     }
/*     */     
/* 620 */     if (arrayOfChar[b] == '#')
/*     */     {
/* 622 */       return decodeHexPairs(arrayOfChar, ++b, i);
/*     */     }
/*     */ 
/*     */     
/* 626 */     if (arrayOfChar[b] == '"' && arrayOfChar[i - 1] == '"') {
/* 627 */       b++;
/* 628 */       i--;
/*     */     } 
/*     */     
/* 631 */     StringBuilder stringBuilder = new StringBuilder(i - b);
/* 632 */     int j = -1;
/*     */     int k;
/* 634 */     for (k = b; k < i; k++) {
/* 635 */       if (arrayOfChar[k] == '\\' && k + 1 < i) {
/* 636 */         if (!Character.isLetterOrDigit(arrayOfChar[k + 1])) {
/* 637 */           k++;
/* 638 */           stringBuilder.append(arrayOfChar[k]);
/* 639 */           j = k;
/*     */         }
/*     */         else {
/*     */           
/* 643 */           byte[] arrayOfByte = getUtf8Octets(arrayOfChar, k, i);
/* 644 */           if (arrayOfByte.length > 0) {
/*     */             try {
/* 646 */               stringBuilder.append(new String(arrayOfByte, "UTF8"));
/* 647 */             } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */ 
/*     */             
/* 650 */             k += arrayOfByte.length * 3 - 1;
/*     */           }
/*     */           else {
/*     */             
/* 654 */             throw new IllegalArgumentException("Not a valid attribute string value:" + paramString + ",improper usage of backslash");
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 660 */         stringBuilder.append(arrayOfChar[k]);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 666 */     k = stringBuilder.length();
/* 667 */     if (isWhitespace(stringBuilder.charAt(k - 1)) && j != i - 1) {
/* 668 */       stringBuilder.setLength(k - 1);
/*     */     }
/* 670 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] decodeHexPairs(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 680 */     byte[] arrayOfByte = new byte[(paramInt2 - paramInt1) / 2];
/* 681 */     for (byte b = 0; paramInt1 + 1 < paramInt2; b++) {
/* 682 */       int i = Character.digit(paramArrayOfchar[paramInt1], 16);
/* 683 */       int j = Character.digit(paramArrayOfchar[paramInt1 + 1], 16);
/* 684 */       if (i < 0 || j < 0) {
/*     */         break;
/*     */       }
/* 687 */       arrayOfByte[b] = (byte)((i << 4) + j);
/* 688 */       paramInt1 += 2;
/*     */     } 
/* 690 */     if (paramInt1 != paramInt2) {
/* 691 */       throw new IllegalArgumentException("Illegal attribute value: " + new String(paramArrayOfchar));
/*     */     }
/*     */     
/* 694 */     return arrayOfByte;
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
/*     */   private static byte[] getUtf8Octets(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 706 */     byte[] arrayOfByte1 = new byte[(paramInt2 - paramInt1) / 3];
/* 707 */     byte b = 0;
/*     */     
/* 709 */     while (paramInt1 + 2 < paramInt2 && paramArrayOfchar[paramInt1++] == '\\') {
/*     */       
/* 711 */       int i = Character.digit(paramArrayOfchar[paramInt1++], 16);
/* 712 */       int j = Character.digit(paramArrayOfchar[paramInt1++], 16);
/* 713 */       if (i < 0 || j < 0) {
/*     */         break;
/*     */       }
/* 716 */       arrayOfByte1[b++] = (byte)((i << 4) + j);
/*     */     } 
/* 718 */     if (b == arrayOfByte1.length) {
/* 719 */       return arrayOfByte1;
/*     */     }
/* 721 */     byte[] arrayOfByte2 = new byte[b];
/* 722 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, b);
/* 723 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isWhitespace(char paramChar) {
/* 731 */     return (paramChar == ' ' || paramChar == '\r');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 742 */     paramObjectOutputStream.defaultWriteObject();
/* 743 */     paramObjectOutputStream.writeObject(toString());
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 748 */     paramObjectInputStream.defaultReadObject();
/* 749 */     this.entries = new ArrayList<>(1);
/* 750 */     String str = (String)paramObjectInputStream.readObject();
/*     */     try {
/* 752 */       (new Rfc2253Parser(str)).parseRdn(this);
/* 753 */     } catch (InvalidNameException invalidNameException) {
/*     */       
/* 755 */       throw new StreamCorruptedException("Invalid name: " + str);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/ldap/Rdn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */