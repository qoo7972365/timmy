/*     */ package com.sun.jndi.toolkit.dir;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.OperationNotSupportedException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.BasicAttributes;
/*     */ import javax.naming.directory.InvalidSearchFilterException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SearchFilter
/*     */   implements AttrFilter
/*     */ {
/*     */   String filter;
/*     */   int pos;
/*     */   private StringFilter rootFilter;
/*     */   protected static final boolean debug = false;
/*     */   protected static final char BEGIN_FILTER_TOKEN = '(';
/*     */   protected static final char END_FILTER_TOKEN = ')';
/*     */   protected static final char AND_TOKEN = '&';
/*     */   protected static final char OR_TOKEN = '|';
/*     */   protected static final char NOT_TOKEN = '!';
/*     */   protected static final char EQUAL_TOKEN = '=';
/*     */   protected static final char APPROX_TOKEN = '~';
/*     */   protected static final char LESS_TOKEN = '<';
/*     */   protected static final char GREATER_TOKEN = '>';
/*     */   protected static final char EXTEND_TOKEN = ':';
/*     */   protected static final char WILDCARD_TOKEN = '*';
/*     */   static final int EQUAL_MATCH = 1;
/*     */   static final int APPROX_MATCH = 2;
/*     */   static final int GREATER_MATCH = 3;
/*     */   static final int LESS_MATCH = 4;
/*     */   
/*     */   public SearchFilter(String paramString) throws InvalidSearchFilterException {
/*  66 */     this.filter = paramString;
/*  67 */     this.pos = 0;
/*  68 */     normalizeFilter();
/*  69 */     this.rootFilter = createNextFilter();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean check(Attributes paramAttributes) throws NamingException {
/*  74 */     if (paramAttributes == null) {
/*  75 */       return false;
/*     */     }
/*  77 */     return this.rootFilter.check(paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void normalizeFilter() {
/*  87 */     skipWhiteSpace();
/*     */ 
/*     */     
/*  90 */     if (getCurrentChar() != '(') {
/*  91 */       this.filter = '(' + this.filter + ')';
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void skipWhiteSpace() {
/* 100 */     while (Character.isWhitespace(getCurrentChar())) {
/* 101 */       consumeChar();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StringFilter createNextFilter() throws InvalidSearchFilterException {
/*     */     AtomicFilter atomicFilter;
/* 109 */     skipWhiteSpace();
/*     */     try {
/*     */       CompoundFilter compoundFilter;
/*     */       NotFilter notFilter;
/* 113 */       if (getCurrentChar() != '(') {
/* 114 */         throw new InvalidSearchFilterException("expected \"(\" at position " + this.pos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       consumeChar();
/*     */       
/* 123 */       skipWhiteSpace();
/*     */ 
/*     */       
/* 126 */       switch (getCurrentChar()) {
/*     */         
/*     */         case '&':
/* 129 */           compoundFilter = new CompoundFilter(true);
/* 130 */           compoundFilter.parse();
/*     */           break;
/*     */         
/*     */         case '|':
/* 134 */           compoundFilter = new CompoundFilter(false);
/* 135 */           compoundFilter.parse();
/*     */           break;
/*     */         
/*     */         case '!':
/* 139 */           notFilter = new NotFilter();
/* 140 */           notFilter.parse();
/*     */           break;
/*     */         
/*     */         default:
/* 144 */           atomicFilter = new AtomicFilter();
/* 145 */           atomicFilter.parse();
/*     */           break;
/*     */       } 
/*     */       
/* 149 */       skipWhiteSpace();
/*     */ 
/*     */       
/* 152 */       if (getCurrentChar() != ')') {
/* 153 */         throw new InvalidSearchFilterException("expected \")\" at position " + this.pos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 160 */       consumeChar();
/* 161 */     } catch (InvalidSearchFilterException invalidSearchFilterException) {
/*     */       
/* 163 */       throw invalidSearchFilterException;
/*     */     
/*     */     }
/* 166 */     catch (Exception exception) {
/*     */       
/* 168 */       throw new InvalidSearchFilterException("Unable to parse character " + this.pos + " in \"" + this.filter + "\"");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 173 */     return atomicFilter;
/*     */   }
/*     */   
/*     */   protected char getCurrentChar() {
/* 177 */     return this.filter.charAt(this.pos);
/*     */   }
/*     */   
/*     */   protected char relCharAt(int paramInt) {
/* 181 */     return this.filter.charAt(this.pos + paramInt);
/*     */   }
/*     */   
/*     */   protected void consumeChar() {
/* 185 */     this.pos++;
/*     */   }
/*     */   
/*     */   protected void consumeChars(int paramInt) {
/* 189 */     this.pos += paramInt;
/*     */   }
/*     */   
/*     */   protected int relIndexOf(int paramInt) {
/* 193 */     return this.filter.indexOf(paramInt, this.pos) - this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String relSubstring(int paramInt1, int paramInt2) {
/* 199 */     return this.filter.substring(paramInt1 + this.pos, paramInt2 + this.pos);
/*     */   }
/*     */   
/*     */   static interface StringFilter extends AttrFilter {
/*     */     void parse() throws InvalidSearchFilterException;
/*     */   }
/*     */   
/*     */   final class CompoundFilter implements StringFilter {
/*     */     private Vector<SearchFilter.StringFilter> subFilters;
/*     */     private boolean polarity;
/*     */     
/*     */     CompoundFilter(boolean param1Boolean) {
/* 211 */       this.subFilters = new Vector<>();
/* 212 */       this.polarity = param1Boolean;
/*     */     }
/*     */     
/*     */     public void parse() throws InvalidSearchFilterException {
/* 216 */       SearchFilter.this.consumeChar();
/* 217 */       while (SearchFilter.this.getCurrentChar() != ')') {
/*     */         
/* 219 */         SearchFilter.StringFilter stringFilter = SearchFilter.this.createNextFilter();
/* 220 */         this.subFilters.addElement(stringFilter);
/* 221 */         SearchFilter.this.skipWhiteSpace();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean check(Attributes param1Attributes) throws NamingException {
/* 226 */       for (byte b = 0; b < this.subFilters.size(); b++) {
/* 227 */         SearchFilter.StringFilter stringFilter = this.subFilters.elementAt(b);
/* 228 */         if (stringFilter.check(param1Attributes) != this.polarity) {
/* 229 */           return !this.polarity;
/*     */         }
/*     */       } 
/* 232 */       return this.polarity;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   final class NotFilter
/*     */     implements StringFilter
/*     */   {
/*     */     private SearchFilter.StringFilter filter;
/*     */     
/*     */     public void parse() throws InvalidSearchFilterException {
/* 243 */       SearchFilter.this.consumeChar();
/* 244 */       this.filter = SearchFilter.this.createNextFilter();
/*     */     }
/*     */     
/*     */     public boolean check(Attributes param1Attributes) throws NamingException {
/* 248 */       return !this.filter.check(param1Attributes);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final class AtomicFilter
/*     */     implements StringFilter
/*     */   {
/*     */     private String attrID;
/*     */ 
/*     */     
/*     */     private String value;
/*     */ 
/*     */     
/*     */     private int matchType;
/*     */ 
/*     */ 
/*     */     
/*     */     public void parse() throws InvalidSearchFilterException {
/* 268 */       SearchFilter.this.skipWhiteSpace();
/*     */ 
/*     */       
/*     */       try {
/* 272 */         int i = SearchFilter.this.relIndexOf(41);
/*     */ 
/*     */         
/* 275 */         int j = SearchFilter.this.relIndexOf(61);
/*     */         
/* 277 */         char c = SearchFilter.this.relCharAt(j - 1);
/* 278 */         switch (c) {
/*     */           
/*     */           case '~':
/* 281 */             this.matchType = 2;
/* 282 */             this.attrID = SearchFilter.this.relSubstring(0, j - 1);
/* 283 */             this.value = SearchFilter.this.relSubstring(j + 1, i);
/*     */             break;
/*     */ 
/*     */           
/*     */           case '>':
/* 288 */             this.matchType = 3;
/* 289 */             this.attrID = SearchFilter.this.relSubstring(0, j - 1);
/* 290 */             this.value = SearchFilter.this.relSubstring(j + 1, i);
/*     */             break;
/*     */ 
/*     */           
/*     */           case '<':
/* 295 */             this.matchType = 4;
/* 296 */             this.attrID = SearchFilter.this.relSubstring(0, j - 1);
/* 297 */             this.value = SearchFilter.this.relSubstring(j + 1, i);
/*     */             break;
/*     */ 
/*     */           
/*     */           case ':':
/* 302 */             throw new OperationNotSupportedException("Extensible match not supported");
/*     */ 
/*     */           
/*     */           default:
/* 306 */             this.matchType = 1;
/* 307 */             this.attrID = SearchFilter.this.relSubstring(0, j);
/* 308 */             this.value = SearchFilter.this.relSubstring(j + 1, i);
/*     */             break;
/*     */         } 
/*     */         
/* 312 */         this.attrID = this.attrID.trim();
/* 313 */         this.value = this.value.trim();
/*     */ 
/*     */         
/* 316 */         SearchFilter.this.consumeChars(i);
/*     */       }
/* 318 */       catch (Exception exception) {
/*     */ 
/*     */         
/* 321 */         InvalidSearchFilterException invalidSearchFilterException = new InvalidSearchFilterException("Unable to parse character " + SearchFilter.this.pos + " in \"" + SearchFilter.this.filter + "\"");
/*     */ 
/*     */ 
/*     */         
/* 325 */         invalidSearchFilterException.setRootCause(exception);
/* 326 */         throw invalidSearchFilterException;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean check(Attributes param1Attributes) {
/*     */       NamingEnumeration<?> namingEnumeration;
/*     */       try {
/* 337 */         Attribute attribute = param1Attributes.get(this.attrID);
/* 338 */         if (attribute == null) {
/* 339 */           return false;
/*     */         }
/* 341 */         namingEnumeration = attribute.getAll();
/* 342 */       } catch (NamingException namingException) {
/*     */ 
/*     */         
/* 345 */         return false;
/*     */       } 
/*     */       
/* 348 */       while (namingEnumeration.hasMoreElements()) {
/* 349 */         String str = namingEnumeration.nextElement().toString();
/*     */         
/* 351 */         switch (this.matchType) {
/*     */           case 1:
/*     */           case 2:
/* 354 */             if (substringMatch(this.value, str))
/*     */             {
/* 356 */               return true;
/*     */             }
/*     */ 
/*     */           
/*     */           case 3:
/* 361 */             if (str.compareTo(this.value) >= 0) {
/* 362 */               return true;
/*     */             }
/*     */ 
/*     */           
/*     */           case 4:
/* 367 */             if (str.compareTo(this.value) <= 0) {
/* 368 */               return true;
/*     */             }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/* 376 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean substringMatch(String param1String1, String param1String2) {
/* 382 */       if (param1String1.equals((new Character('*')).toString()))
/*     */       {
/* 384 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 388 */       if (param1String1.indexOf('*') == -1) {
/* 389 */         return param1String1.equalsIgnoreCase(param1String2);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 394 */       int i = 0;
/* 395 */       StringTokenizer stringTokenizer = new StringTokenizer(param1String1, "*", false);
/*     */ 
/*     */       
/* 398 */       if (param1String1.charAt(0) != '*' && 
/* 399 */         !param1String2.toLowerCase(Locale.ENGLISH).startsWith(stringTokenizer
/* 400 */           .nextToken().toLowerCase(Locale.ENGLISH)))
/*     */       {
/*     */ 
/*     */         
/* 404 */         return false;
/*     */       }
/*     */       
/* 407 */       while (stringTokenizer.hasMoreTokens()) {
/* 408 */         String str = stringTokenizer.nextToken();
/*     */ 
/*     */         
/* 411 */         i = param1String2.toLowerCase(Locale.ENGLISH).indexOf(str
/* 412 */             .toLowerCase(Locale.ENGLISH), i);
/*     */         
/* 414 */         if (i == -1) {
/* 415 */           return false;
/*     */         }
/* 417 */         i += str.length();
/*     */       } 
/*     */ 
/*     */       
/* 421 */       if (param1String1.charAt(param1String1.length() - 1) != '*' && i != param1String2
/* 422 */         .length())
/*     */       {
/* 424 */         return false;
/*     */       }
/*     */       
/* 427 */       return true;
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
/*     */   public static String format(Attributes paramAttributes) throws NamingException {
/* 440 */     if (paramAttributes == null || paramAttributes.size() == 0) {
/* 441 */       return "objectClass=*";
/*     */     }
/*     */ 
/*     */     
/* 445 */     String str = "(& ";
/*     */     
/* 447 */     NamingEnumeration<? extends Attribute> namingEnumeration = paramAttributes.getAll();
/* 448 */     while (namingEnumeration.hasMore()) {
/* 449 */       Attribute attribute = namingEnumeration.next();
/* 450 */       if (attribute.size() == 0 || (attribute.size() == 1 && attribute.get() == null)) {
/*     */         
/* 452 */         str = str + "(" + attribute.getID() + "=*)"; continue;
/*     */       } 
/* 454 */       NamingEnumeration<?> namingEnumeration1 = attribute.getAll();
/* 455 */       while (namingEnumeration1.hasMore()) {
/* 456 */         String str1 = getEncodedStringRep(namingEnumeration1.next());
/* 457 */         if (str1 != null) {
/* 458 */           str = str + "(" + attribute.getID() + "=" + str1 + ")";
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 464 */     str = str + ")";
/*     */     
/* 466 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void hexDigit(StringBuffer paramStringBuffer, byte paramByte) {
/* 473 */     char c = (char)(paramByte >> 4 & 0xF);
/* 474 */     if (c > '\t') {
/* 475 */       c = (char)(c - 10 + 65);
/*     */     } else {
/* 477 */       c = (char)(c + 48);
/*     */     } 
/* 479 */     paramStringBuffer.append(c);
/* 480 */     c = (char)(paramByte & 0xF);
/* 481 */     if (c > '\t') {
/* 482 */       c = (char)(c - 10 + 65);
/*     */     } else {
/* 484 */       c = (char)(c + 48);
/* 485 */     }  paramStringBuffer.append(c);
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
/*     */   private static String getEncodedStringRep(Object paramObject) throws NamingException {
/*     */     String str;
/* 504 */     if (paramObject == null) {
/* 505 */       return null;
/*     */     }
/* 507 */     if (paramObject instanceof byte[]) {
/*     */       
/* 509 */       byte[] arrayOfByte = (byte[])paramObject;
/* 510 */       StringBuffer stringBuffer1 = new StringBuffer(arrayOfByte.length * 3);
/* 511 */       for (byte b1 = 0; b1 < arrayOfByte.length; b1++) {
/* 512 */         stringBuffer1.append('\\');
/* 513 */         hexDigit(stringBuffer1, arrayOfByte[b1]);
/*     */       } 
/* 515 */       return stringBuffer1.toString();
/*     */     } 
/* 517 */     if (!(paramObject instanceof String)) {
/* 518 */       str = paramObject.toString();
/*     */     } else {
/* 520 */       str = (String)paramObject;
/*     */     } 
/* 522 */     int i = str.length();
/* 523 */     StringBuffer stringBuffer = new StringBuffer(i);
/*     */     
/* 525 */     for (byte b = 0; b < i; b++) {
/* 526 */       char c; switch (c = str.charAt(b)) {
/*     */         case '*':
/* 528 */           stringBuffer.append("\\2a");
/*     */           break;
/*     */         case '(':
/* 531 */           stringBuffer.append("\\28");
/*     */           break;
/*     */         case ')':
/* 534 */           stringBuffer.append("\\29");
/*     */           break;
/*     */         case '\\':
/* 537 */           stringBuffer.append("\\5c");
/*     */           break;
/*     */         case '\000':
/* 540 */           stringBuffer.append("\\00");
/*     */           break;
/*     */         default:
/* 543 */           stringBuffer.append(c); break;
/*     */       } 
/*     */     } 
/* 546 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int findUnescaped(char paramChar, String paramString, int paramInt) {
/* 556 */     int i = paramString.length();
/*     */     
/* 558 */     while (paramInt < i) {
/* 559 */       int j = paramString.indexOf(paramChar, paramInt);
/*     */       
/* 561 */       if (j == paramInt || j == -1 || paramString.charAt(j - 1) != '\\') {
/* 562 */         return j;
/*     */       }
/*     */       
/* 565 */       paramInt = j + 1;
/*     */     } 
/* 567 */     return -1;
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
/*     */   public static String format(String paramString, Object[] paramArrayOfObject) throws NamingException {
/* 587 */     int i = 0, j = 0;
/* 588 */     StringBuffer stringBuffer = new StringBuffer(paramString.length());
/*     */     
/* 590 */     while ((i = findUnescaped('{', paramString, j)) >= 0) {
/* 591 */       int k, m = i + 1;
/* 592 */       int n = paramString.indexOf('}', m);
/*     */       
/* 594 */       if (n < 0) {
/* 595 */         throw new InvalidSearchFilterException("unbalanced {: " + paramString);
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 600 */         k = Integer.parseInt(paramString.substring(m, n));
/* 601 */       } catch (NumberFormatException numberFormatException) {
/* 602 */         throw new InvalidSearchFilterException("integer expected inside {}: " + paramString);
/*     */       } 
/*     */ 
/*     */       
/* 606 */       if (k >= paramArrayOfObject.length) {
/* 607 */         throw new InvalidSearchFilterException("number exceeds argument list: " + k);
/*     */       }
/*     */ 
/*     */       
/* 611 */       stringBuffer.append(paramString.substring(j, i)).append(getEncodedStringRep(paramArrayOfObject[k]));
/* 612 */       j = n + 1;
/*     */     } 
/*     */     
/* 615 */     if (j < paramString.length()) {
/* 616 */       stringBuffer.append(paramString.substring(j));
/*     */     }
/* 618 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Attributes selectAttributes(Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/* 628 */     if (paramArrayOfString == null) {
/* 629 */       return paramAttributes;
/*     */     }
/* 631 */     BasicAttributes basicAttributes = new BasicAttributes();
/*     */     
/* 633 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 634 */       Attribute attribute = paramAttributes.get(paramArrayOfString[b]);
/* 635 */       if (attribute != null) {
/* 636 */         basicAttributes.put(attribute);
/*     */       }
/*     */     } 
/*     */     
/* 640 */     return basicAttributes;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/dir/SearchFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */