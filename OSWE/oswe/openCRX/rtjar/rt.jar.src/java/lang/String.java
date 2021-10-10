/*      */ package java.lang;
/*      */ 
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.Formatter;
/*      */ import java.util.Locale;
/*      */ import java.util.Objects;
/*      */ import java.util.StringJoiner;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class String
/*      */   implements Serializable, Comparable<String>, CharSequence
/*      */ {
/*      */   private final char[] value;
/*      */   private int hash;
/*      */   private static final long serialVersionUID = -6849794470754667710L;
/*  129 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String() {
/*  138 */     this.value = "".value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String(String paramString) {
/*  152 */     this.value = paramString.value;
/*  153 */     this.hash = paramString.hash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String(char[] paramArrayOfchar) {
/*  166 */     this.value = Arrays.copyOf(paramArrayOfchar, paramArrayOfchar.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  191 */     if (paramInt1 < 0) {
/*  192 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*      */     }
/*  194 */     if (paramInt2 <= 0) {
/*  195 */       if (paramInt2 < 0) {
/*  196 */         throw new StringIndexOutOfBoundsException(paramInt2);
/*      */       }
/*  198 */       if (paramInt1 <= paramArrayOfchar.length) {
/*  199 */         this.value = "".value;
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  204 */     if (paramInt1 > paramArrayOfchar.length - paramInt2) {
/*  205 */       throw new StringIndexOutOfBoundsException(paramInt1 + paramInt2);
/*      */     }
/*  207 */     this.value = Arrays.copyOfRange(paramArrayOfchar, paramInt1, paramInt1 + paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/*  239 */     if (paramInt1 < 0) {
/*  240 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*      */     }
/*  242 */     if (paramInt2 <= 0) {
/*  243 */       if (paramInt2 < 0) {
/*  244 */         throw new StringIndexOutOfBoundsException(paramInt2);
/*      */       }
/*  246 */       if (paramInt1 <= paramArrayOfint.length) {
/*  247 */         this.value = "".value;
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  252 */     if (paramInt1 > paramArrayOfint.length - paramInt2) {
/*  253 */       throw new StringIndexOutOfBoundsException(paramInt1 + paramInt2);
/*      */     }
/*      */     
/*  256 */     int i = paramInt1 + paramInt2;
/*      */ 
/*      */     
/*  259 */     int j = paramInt2;
/*  260 */     for (int k = paramInt1; k < i; k++) {
/*  261 */       int n = paramArrayOfint[k];
/*  262 */       if (!Character.isBmpCodePoint(n))
/*      */       {
/*  264 */         if (Character.isValidCodePoint(n))
/*  265 */         { j++; }
/*  266 */         else { throw new IllegalArgumentException(Integer.toString(n)); }
/*      */       
/*      */       }
/*      */     } 
/*  270 */     char[] arrayOfChar = new char[j]; int m;
/*      */     byte b;
/*  272 */     for (m = paramInt1, b = 0; m < i; m++, b++) {
/*  273 */       int n = paramArrayOfint[m];
/*  274 */       if (Character.isBmpCodePoint(n)) {
/*  275 */         arrayOfChar[b] = (char)n;
/*      */       } else {
/*  277 */         Character.toSurrogates(n, arrayOfChar, b++);
/*      */       } 
/*      */     } 
/*  280 */     this.value = arrayOfChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public String(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) {
/*  324 */     checkBounds(paramArrayOfbyte, paramInt2, paramInt3);
/*  325 */     char[] arrayOfChar = new char[paramInt3];
/*      */     
/*  327 */     if (paramInt1 == 0) {
/*  328 */       for (int i = paramInt3; i-- > 0;) {
/*  329 */         arrayOfChar[i] = (char)(paramArrayOfbyte[i + paramInt2] & 0xFF);
/*      */       }
/*      */     } else {
/*  332 */       paramInt1 <<= 8;
/*  333 */       for (int i = paramInt3; i-- > 0;) {
/*  334 */         arrayOfChar[i] = (char)(paramInt1 | paramArrayOfbyte[i + paramInt2] & 0xFF);
/*      */       }
/*      */     } 
/*  337 */     this.value = arrayOfChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public String(byte[] paramArrayOfbyte, int paramInt) {
/*  372 */     this(paramArrayOfbyte, paramInt, 0, paramArrayOfbyte.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkBounds(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  380 */     if (paramInt2 < 0)
/*  381 */       throw new StringIndexOutOfBoundsException(paramInt2); 
/*  382 */     if (paramInt1 < 0)
/*  383 */       throw new StringIndexOutOfBoundsException(paramInt1); 
/*  384 */     if (paramInt1 > paramArrayOfbyte.length - paramInt2) {
/*  385 */       throw new StringIndexOutOfBoundsException(paramInt1 + paramInt2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, String paramString) throws UnsupportedEncodingException {
/*  423 */     if (paramString == null)
/*  424 */       throw new NullPointerException("charsetName"); 
/*  425 */     checkBounds(paramArrayOfbyte, paramInt1, paramInt2);
/*  426 */     this.value = StringCoding.decode(paramString, paramArrayOfbyte, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, Charset paramCharset) {
/*  460 */     if (paramCharset == null)
/*  461 */       throw new NullPointerException("charset"); 
/*  462 */     checkBounds(paramArrayOfbyte, paramInt1, paramInt2);
/*  463 */     this.value = StringCoding.decode(paramCharset, paramArrayOfbyte, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String(byte[] paramArrayOfbyte, String paramString) throws UnsupportedEncodingException {
/*  491 */     this(paramArrayOfbyte, 0, paramArrayOfbyte.length, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String(byte[] paramArrayOfbyte, Charset paramCharset) {
/*  515 */     this(paramArrayOfbyte, 0, paramArrayOfbyte.length, paramCharset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  545 */     checkBounds(paramArrayOfbyte, paramInt1, paramInt2);
/*  546 */     this.value = StringCoding.decode(paramArrayOfbyte, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String(byte[] paramArrayOfbyte) {
/*  566 */     this(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String(StringBuffer paramStringBuffer) {
/*  579 */     synchronized (paramStringBuffer) {
/*  580 */       this.value = Arrays.copyOf(paramStringBuffer.getValue(), paramStringBuffer.length());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String(StringBuilder paramStringBuilder) {
/*  600 */     this.value = Arrays.copyOf(paramStringBuilder.getValue(), paramStringBuilder.length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String(char[] paramArrayOfchar, boolean paramBoolean) {
/*  611 */     this.value = paramArrayOfchar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*  623 */     return this.value.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  635 */     return (this.value.length == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char charAt(int paramInt) {
/*  657 */     if (paramInt < 0 || paramInt >= this.value.length) {
/*  658 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  660 */     return this.value[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int codePointAt(int paramInt) {
/*  686 */     if (paramInt < 0 || paramInt >= this.value.length) {
/*  687 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  689 */     return Character.codePointAtImpl(this.value, paramInt, this.value.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int codePointBefore(int paramInt) {
/*  715 */     int i = paramInt - 1;
/*  716 */     if (i < 0 || i >= this.value.length) {
/*  717 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  719 */     return Character.codePointBeforeImpl(this.value, paramInt, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int codePointCount(int paramInt1, int paramInt2) {
/*  744 */     if (paramInt1 < 0 || paramInt2 > this.value.length || paramInt1 > paramInt2) {
/*  745 */       throw new IndexOutOfBoundsException();
/*      */     }
/*  747 */     return Character.codePointCountImpl(this.value, paramInt1, paramInt2 - paramInt1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int offsetByCodePoints(int paramInt1, int paramInt2) {
/*  771 */     if (paramInt1 < 0 || paramInt1 > this.value.length) {
/*  772 */       throw new IndexOutOfBoundsException();
/*      */     }
/*  774 */     return Character.offsetByCodePointsImpl(this.value, 0, this.value.length, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void getChars(char[] paramArrayOfchar, int paramInt) {
/*  783 */     System.arraycopy(this.value, 0, paramArrayOfchar, paramInt, this.value.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getChars(int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3) {
/*  817 */     if (paramInt1 < 0) {
/*  818 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*      */     }
/*  820 */     if (paramInt2 > this.value.length) {
/*  821 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*      */     }
/*  823 */     if (paramInt1 > paramInt2) {
/*  824 */       throw new StringIndexOutOfBoundsException(paramInt2 - paramInt1);
/*      */     }
/*  826 */     System.arraycopy(this.value, paramInt1, paramArrayOfchar, paramInt3, paramInt2 - paramInt1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void getBytes(int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3) {
/*  874 */     if (paramInt1 < 0) {
/*  875 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*      */     }
/*  877 */     if (paramInt2 > this.value.length) {
/*  878 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*      */     }
/*  880 */     if (paramInt1 > paramInt2) {
/*  881 */       throw new StringIndexOutOfBoundsException(paramInt2 - paramInt1);
/*      */     }
/*  883 */     Objects.requireNonNull(paramArrayOfbyte);
/*      */     
/*  885 */     int i = paramInt3;
/*  886 */     int j = paramInt2;
/*  887 */     int k = paramInt1;
/*  888 */     char[] arrayOfChar = this.value;
/*      */     
/*  890 */     while (k < j) {
/*  891 */       paramArrayOfbyte[i++] = (byte)arrayOfChar[k++];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBytes(String paramString) throws UnsupportedEncodingException {
/*  917 */     if (paramString == null) throw new NullPointerException(); 
/*  918 */     return StringCoding.encode(paramString, this.value, 0, this.value.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBytes(Charset paramCharset) {
/*  940 */     if (paramCharset == null) throw new NullPointerException(); 
/*  941 */     return StringCoding.encode(paramCharset, this.value, 0, this.value.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBytes() {
/*  958 */     return StringCoding.encode(this.value, 0, this.value.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  977 */     if (this == paramObject) {
/*  978 */       return true;
/*      */     }
/*  980 */     if (paramObject instanceof String) {
/*  981 */       String str = (String)paramObject;
/*  982 */       int i = this.value.length;
/*  983 */       if (i == str.value.length) {
/*  984 */         char[] arrayOfChar1 = this.value;
/*  985 */         char[] arrayOfChar2 = str.value;
/*  986 */         byte b = 0;
/*  987 */         while (i-- != 0) {
/*  988 */           if (arrayOfChar1[b] != arrayOfChar2[b])
/*  989 */             return false; 
/*  990 */           b++;
/*      */         } 
/*  992 */         return true;
/*      */       } 
/*      */     } 
/*  995 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contentEquals(StringBuffer paramStringBuffer) {
/* 1014 */     return contentEquals(paramStringBuffer);
/*      */   }
/*      */   
/*      */   private boolean nonSyncContentEquals(AbstractStringBuilder paramAbstractStringBuilder) {
/* 1018 */     char[] arrayOfChar1 = this.value;
/* 1019 */     char[] arrayOfChar2 = paramAbstractStringBuilder.getValue();
/* 1020 */     int i = arrayOfChar1.length;
/* 1021 */     if (i != paramAbstractStringBuilder.length()) {
/* 1022 */       return false;
/*      */     }
/* 1024 */     for (byte b = 0; b < i; b++) {
/* 1025 */       if (arrayOfChar1[b] != arrayOfChar2[b]) {
/* 1026 */         return false;
/*      */       }
/*      */     } 
/* 1029 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contentEquals(CharSequence paramCharSequence) {
/* 1050 */     if (paramCharSequence instanceof AbstractStringBuilder) {
/* 1051 */       if (paramCharSequence instanceof StringBuffer) {
/* 1052 */         synchronized (paramCharSequence) {
/* 1053 */           return nonSyncContentEquals((AbstractStringBuilder)paramCharSequence);
/*      */         } 
/*      */       }
/* 1056 */       return nonSyncContentEquals((AbstractStringBuilder)paramCharSequence);
/*      */     } 
/*      */ 
/*      */     
/* 1060 */     if (paramCharSequence instanceof String) {
/* 1061 */       return equals(paramCharSequence);
/*      */     }
/*      */     
/* 1064 */     char[] arrayOfChar = this.value;
/* 1065 */     int i = arrayOfChar.length;
/* 1066 */     if (i != paramCharSequence.length()) {
/* 1067 */       return false;
/*      */     }
/* 1069 */     for (byte b = 0; b < i; b++) {
/* 1070 */       if (arrayOfChar[b] != paramCharSequence.charAt(b)) {
/* 1071 */         return false;
/*      */       }
/*      */     } 
/* 1074 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equalsIgnoreCase(String paramString) {
/* 1106 */     return (this == paramString) ? true : ((paramString != null && paramString.value.length == this.value.length && 
/*      */ 
/*      */       
/* 1109 */       regionMatches(true, 0, paramString, 0, this.value.length)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(String paramString) {
/* 1154 */     int i = this.value.length;
/* 1155 */     int j = paramString.value.length;
/* 1156 */     int k = Math.min(i, j);
/* 1157 */     char[] arrayOfChar1 = this.value;
/* 1158 */     char[] arrayOfChar2 = paramString.value;
/*      */     
/* 1160 */     byte b = 0;
/* 1161 */     while (b < k) {
/* 1162 */       char c1 = arrayOfChar1[b];
/* 1163 */       char c2 = arrayOfChar2[b];
/* 1164 */       if (c1 != c2) {
/* 1165 */         return c1 - c2;
/*      */       }
/* 1167 */       b++;
/*      */     } 
/* 1169 */     return i - j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1184 */   public static final Comparator<String> CASE_INSENSITIVE_ORDER = new CaseInsensitiveComparator();
/*      */   
/*      */   private static class CaseInsensitiveComparator implements Comparator<String>, Serializable {
/*      */     private static final long serialVersionUID = 8575799808933029326L;
/*      */     
/*      */     private CaseInsensitiveComparator() {}
/*      */     
/*      */     public int compare(String param1String1, String param1String2) {
/* 1192 */       int i = param1String1.length();
/* 1193 */       int j = param1String2.length();
/* 1194 */       int k = Math.min(i, j);
/* 1195 */       for (byte b = 0; b < k; b++) {
/* 1196 */         char c1 = param1String1.charAt(b);
/* 1197 */         char c2 = param1String2.charAt(b);
/* 1198 */         if (c1 != c2) {
/* 1199 */           c1 = Character.toUpperCase(c1);
/* 1200 */           c2 = Character.toUpperCase(c2);
/* 1201 */           if (c1 != c2) {
/* 1202 */             c1 = Character.toLowerCase(c1);
/* 1203 */             c2 = Character.toLowerCase(c2);
/* 1204 */             if (c1 != c2)
/*      */             {
/* 1206 */               return c1 - c2;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/* 1211 */       return i - j;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/* 1215 */       return String.CASE_INSENSITIVE_ORDER;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareToIgnoreCase(String paramString) {
/* 1239 */     return CASE_INSENSITIVE_ORDER.compare(this, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean regionMatches(int paramInt1, String paramString, int paramInt2, int paramInt3) {
/* 1276 */     char[] arrayOfChar1 = this.value;
/* 1277 */     int i = paramInt1;
/* 1278 */     char[] arrayOfChar2 = paramString.value;
/* 1279 */     int j = paramInt2;
/*      */     
/* 1281 */     if (paramInt2 < 0 || paramInt1 < 0 || paramInt1 > this.value.length - paramInt3 || paramInt2 > paramString.value.length - paramInt3)
/*      */     {
/*      */       
/* 1284 */       return false;
/*      */     }
/* 1286 */     while (paramInt3-- > 0) {
/* 1287 */       if (arrayOfChar1[i++] != arrayOfChar2[j++]) {
/* 1288 */         return false;
/*      */       }
/*      */     } 
/* 1291 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean regionMatches(boolean paramBoolean, int paramInt1, String paramString, int paramInt2, int paramInt3) {
/* 1346 */     char[] arrayOfChar1 = this.value;
/* 1347 */     int i = paramInt1;
/* 1348 */     char[] arrayOfChar2 = paramString.value;
/* 1349 */     int j = paramInt2;
/*      */     
/* 1351 */     if (paramInt2 < 0 || paramInt1 < 0 || paramInt1 > this.value.length - paramInt3 || paramInt2 > paramString.value.length - paramInt3)
/*      */     {
/*      */       
/* 1354 */       return false;
/*      */     }
/* 1356 */     while (paramInt3-- > 0) {
/* 1357 */       char c1 = arrayOfChar1[i++];
/* 1358 */       char c2 = arrayOfChar2[j++];
/* 1359 */       if (c1 == c2) {
/*      */         continue;
/*      */       }
/* 1362 */       if (paramBoolean) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1367 */         char c3 = Character.toUpperCase(c1);
/* 1368 */         char c4 = Character.toUpperCase(c2);
/* 1369 */         if (c3 == c4) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1376 */         if (Character.toLowerCase(c3) == Character.toLowerCase(c4)) {
/*      */           continue;
/*      */         }
/*      */       } 
/* 1380 */       return false;
/*      */     } 
/* 1382 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean startsWith(String paramString, int paramInt) {
/* 1403 */     char[] arrayOfChar1 = this.value;
/* 1404 */     int i = paramInt;
/* 1405 */     char[] arrayOfChar2 = paramString.value;
/* 1406 */     byte b = 0;
/* 1407 */     int j = paramString.value.length;
/*      */     
/* 1409 */     if (paramInt < 0 || paramInt > this.value.length - j) {
/* 1410 */       return false;
/*      */     }
/* 1412 */     while (--j >= 0) {
/* 1413 */       if (arrayOfChar1[i++] != arrayOfChar2[b++]) {
/* 1414 */         return false;
/*      */       }
/*      */     } 
/* 1417 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean startsWith(String paramString) {
/* 1434 */     return startsWith(paramString, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean endsWith(String paramString) {
/* 1449 */     return startsWith(paramString, this.value.length - paramString.value.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1466 */     int i = this.hash;
/* 1467 */     if (i == 0 && this.value.length > 0) {
/* 1468 */       char[] arrayOfChar = this.value;
/*      */       
/* 1470 */       for (byte b = 0; b < this.value.length; b++) {
/* 1471 */         i = 31 * i + arrayOfChar[b];
/*      */       }
/* 1473 */       this.hash = i;
/*      */     } 
/* 1475 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(int paramInt) {
/* 1503 */     return indexOf(paramInt, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(int paramInt1, int paramInt2) {
/* 1546 */     int i = this.value.length;
/* 1547 */     if (paramInt2 < 0) {
/* 1548 */       paramInt2 = 0;
/* 1549 */     } else if (paramInt2 >= i) {
/*      */       
/* 1551 */       return -1;
/*      */     } 
/*      */     
/* 1554 */     if (paramInt1 < 65536) {
/*      */ 
/*      */       
/* 1557 */       char[] arrayOfChar = this.value;
/* 1558 */       for (int j = paramInt2; j < i; j++) {
/* 1559 */         if (arrayOfChar[j] == paramInt1) {
/* 1560 */           return j;
/*      */         }
/*      */       } 
/* 1563 */       return -1;
/*      */     } 
/* 1565 */     return indexOfSupplementary(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int indexOfSupplementary(int paramInt1, int paramInt2) {
/* 1573 */     if (Character.isValidCodePoint(paramInt1)) {
/* 1574 */       char[] arrayOfChar = this.value;
/* 1575 */       char c1 = Character.highSurrogate(paramInt1);
/* 1576 */       char c2 = Character.lowSurrogate(paramInt1);
/* 1577 */       int i = arrayOfChar.length - 1;
/* 1578 */       for (int j = paramInt2; j < i; j++) {
/* 1579 */         if (arrayOfChar[j] == c1 && arrayOfChar[j + 1] == c2) {
/* 1580 */           return j;
/*      */         }
/*      */       } 
/*      */     } 
/* 1584 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(int paramInt) {
/* 1611 */     return lastIndexOf(paramInt, this.value.length - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(int paramInt1, int paramInt2) {
/* 1649 */     if (paramInt1 < 65536) {
/*      */ 
/*      */       
/* 1652 */       char[] arrayOfChar = this.value;
/* 1653 */       int i = Math.min(paramInt2, arrayOfChar.length - 1);
/* 1654 */       for (; i >= 0; i--) {
/* 1655 */         if (arrayOfChar[i] == paramInt1) {
/* 1656 */           return i;
/*      */         }
/*      */       } 
/* 1659 */       return -1;
/*      */     } 
/* 1661 */     return lastIndexOfSupplementary(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int lastIndexOfSupplementary(int paramInt1, int paramInt2) {
/* 1669 */     if (Character.isValidCodePoint(paramInt1)) {
/* 1670 */       char[] arrayOfChar = this.value;
/* 1671 */       char c1 = Character.highSurrogate(paramInt1);
/* 1672 */       char c2 = Character.lowSurrogate(paramInt1);
/* 1673 */       int i = Math.min(paramInt2, arrayOfChar.length - 2);
/* 1674 */       for (; i >= 0; i--) {
/* 1675 */         if (arrayOfChar[i] == c1 && arrayOfChar[i + 1] == c2) {
/* 1676 */           return i;
/*      */         }
/*      */       } 
/*      */     } 
/* 1680 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(String paramString) {
/* 1698 */     return indexOf(paramString, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(String paramString, int paramInt) {
/* 1718 */     return indexOf(this.value, 0, this.value.length, paramString.value, 0, paramString.value.length, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int indexOf(char[] paramArrayOfchar, int paramInt1, int paramInt2, String paramString, int paramInt3) {
/* 1735 */     return indexOf(paramArrayOfchar, paramInt1, paramInt2, paramString.value, 0, paramString.value.length, paramInt3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int indexOf(char[] paramArrayOfchar1, int paramInt1, int paramInt2, char[] paramArrayOfchar2, int paramInt3, int paramInt4, int paramInt5) {
/* 1756 */     if (paramInt5 >= paramInt2) {
/* 1757 */       return (paramInt4 == 0) ? paramInt2 : -1;
/*      */     }
/* 1759 */     if (paramInt5 < 0) {
/* 1760 */       paramInt5 = 0;
/*      */     }
/* 1762 */     if (paramInt4 == 0) {
/* 1763 */       return paramInt5;
/*      */     }
/*      */     
/* 1766 */     char c = paramArrayOfchar2[paramInt3];
/* 1767 */     int i = paramInt1 + paramInt2 - paramInt4;
/*      */     
/* 1769 */     for (int j = paramInt1 + paramInt5; j <= i; j++) {
/*      */       
/* 1771 */       if (paramArrayOfchar1[j] != c) {
/* 1772 */         while (++j <= i && paramArrayOfchar1[j] != c);
/*      */       }
/*      */ 
/*      */       
/* 1776 */       if (j <= i) {
/* 1777 */         int k = j + 1;
/* 1778 */         int m = k + paramInt4 - 1;
/* 1779 */         for (int n = paramInt3 + 1; k < m && paramArrayOfchar1[k] == paramArrayOfchar2[n]; ) {
/* 1780 */           k++; n++;
/*      */         } 
/* 1782 */         if (k == m)
/*      */         {
/* 1784 */           return j - paramInt1;
/*      */         }
/*      */       } 
/*      */     } 
/* 1788 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(String paramString) {
/* 1807 */     return lastIndexOf(paramString, this.value.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(String paramString, int paramInt) {
/* 1827 */     return lastIndexOf(this.value, 0, this.value.length, paramString.value, 0, paramString.value.length, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int lastIndexOf(char[] paramArrayOfchar, int paramInt1, int paramInt2, String paramString, int paramInt3) {
/* 1844 */     return lastIndexOf(paramArrayOfchar, paramInt1, paramInt2, paramString.value, 0, paramString.value.length, paramInt3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int lastIndexOf(char[] paramArrayOfchar1, int paramInt1, int paramInt2, char[] paramArrayOfchar2, int paramInt3, int paramInt4, int paramInt5) {
/* 1869 */     int n, i = paramInt2 - paramInt4;
/* 1870 */     if (paramInt5 < 0) {
/* 1871 */       return -1;
/*      */     }
/* 1873 */     if (paramInt5 > i) {
/* 1874 */       paramInt5 = i;
/*      */     }
/*      */     
/* 1877 */     if (paramInt4 == 0) {
/* 1878 */       return paramInt5;
/*      */     }
/*      */     
/* 1881 */     int j = paramInt3 + paramInt4 - 1;
/* 1882 */     char c = paramArrayOfchar2[j];
/* 1883 */     int k = paramInt1 + paramInt4 - 1;
/* 1884 */     int m = k + paramInt5;
/*      */ 
/*      */     
/*      */     while (true) {
/* 1888 */       if (m >= k && paramArrayOfchar1[m] != c) {
/* 1889 */         m--; continue;
/*      */       } 
/* 1891 */       if (m < k) {
/* 1892 */         return -1;
/*      */       }
/* 1894 */       int i1 = m - 1;
/* 1895 */       n = i1 - paramInt4 - 1;
/* 1896 */       int i2 = j - 1;
/*      */       
/* 1898 */       while (i1 > n) {
/* 1899 */         if (paramArrayOfchar1[i1--] != paramArrayOfchar2[i2--])
/* 1900 */           m--; 
/*      */       } 
/*      */       break;
/*      */     } 
/* 1904 */     return n - paramInt1 + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String substring(int paramInt) {
/* 1926 */     if (paramInt < 0) {
/* 1927 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/* 1929 */     int i = this.value.length - paramInt;
/* 1930 */     if (i < 0) {
/* 1931 */       throw new StringIndexOutOfBoundsException(i);
/*      */     }
/* 1933 */     return (paramInt == 0) ? this : new String(this.value, paramInt, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String substring(int paramInt1, int paramInt2) {
/* 1959 */     if (paramInt1 < 0) {
/* 1960 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*      */     }
/* 1962 */     if (paramInt2 > this.value.length) {
/* 1963 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*      */     }
/* 1965 */     int i = paramInt2 - paramInt1;
/* 1966 */     if (i < 0) {
/* 1967 */       throw new StringIndexOutOfBoundsException(i);
/*      */     }
/* 1969 */     return (paramInt1 == 0 && paramInt2 == this.value.length) ? this : new String(this.value, paramInt1, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharSequence subSequence(int paramInt1, int paramInt2) {
/* 2003 */     return substring(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String concat(String paramString) {
/* 2027 */     int i = paramString.length();
/* 2028 */     if (i == 0) {
/* 2029 */       return this;
/*      */     }
/* 2031 */     int j = this.value.length;
/* 2032 */     char[] arrayOfChar = Arrays.copyOf(this.value, j + i);
/* 2033 */     paramString.getChars(arrayOfChar, j);
/* 2034 */     return new String(arrayOfChar, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replace(char paramChar1, char paramChar2) {
/* 2067 */     if (paramChar1 != paramChar2) {
/* 2068 */       int i = this.value.length;
/* 2069 */       byte b = -1;
/* 2070 */       char[] arrayOfChar = this.value; do {
/*      */       
/* 2072 */       } while (++b < i && 
/* 2073 */         arrayOfChar[b] != paramChar1);
/*      */ 
/*      */ 
/*      */       
/* 2077 */       if (b < i) {
/* 2078 */         char[] arrayOfChar1 = new char[i]; char c;
/* 2079 */         for (c = Character.MIN_VALUE; c < b; c++) {
/* 2080 */           arrayOfChar1[c] = arrayOfChar[c];
/*      */         }
/* 2082 */         while (b < i) {
/* 2083 */           c = arrayOfChar[b];
/* 2084 */           arrayOfChar1[b] = (c == paramChar1) ? paramChar2 : c;
/* 2085 */           b++;
/*      */         } 
/* 2087 */         return new String(arrayOfChar1, true);
/*      */       } 
/*      */     } 
/* 2090 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matches(String paramString) {
/* 2121 */     return Pattern.matches(paramString, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(CharSequence paramCharSequence) {
/* 2133 */     return (indexOf(paramCharSequence.toString()) > -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replaceFirst(String paramString1, String paramString2) {
/* 2178 */     return Pattern.compile(paramString1).matcher(this).replaceFirst(paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replaceAll(String paramString1, String paramString2) {
/* 2223 */     return Pattern.compile(paramString1).matcher(this).replaceAll(paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String replace(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
/* 2239 */     return Pattern.compile(paramCharSequence1.toString(), 16).matcher(this)
/* 2240 */       .replaceAll(Matcher.quoteReplacement(paramCharSequence2.toString()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] split(String paramString, int paramInt) {
/* 2336 */     char c = Character.MIN_VALUE;
/* 2337 */     if (((paramString.value.length == 1 && ".$|()[{^?*+\\"
/* 2338 */       .indexOf(c = paramString.charAt(0)) == -1) || (paramString
/* 2339 */       .length() == 2 && paramString
/* 2340 */       .charAt(0) == '\\' && ((
/* 2341 */       c = paramString.charAt(1)) - 48 | 57 - c) < 0 && (c - 97 | 122 - c) < 0 && (c - 65 | 90 - c) < 0)) && (c < '?' || c > '?')) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2347 */       int i = 0;
/* 2348 */       int j = 0;
/* 2349 */       boolean bool = (paramInt > 0) ? true : false;
/* 2350 */       ArrayList<String> arrayList = new ArrayList();
/* 2351 */       while ((j = indexOf(c, i)) != -1) {
/* 2352 */         if (!bool || arrayList.size() < paramInt - 1) {
/* 2353 */           arrayList.add(substring(i, j));
/* 2354 */           i = j + 1;
/*      */           continue;
/*      */         } 
/* 2357 */         arrayList.add(substring(i, this.value.length));
/* 2358 */         i = this.value.length;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2363 */       if (i == 0) {
/* 2364 */         return new String[] { this };
/*      */       }
/*      */       
/* 2367 */       if (!bool || arrayList.size() < paramInt) {
/* 2368 */         arrayList.add(substring(i, this.value.length));
/*      */       }
/*      */       
/* 2371 */       int k = arrayList.size();
/* 2372 */       if (paramInt == 0) {
/* 2373 */         while (k > 0 && ((String)arrayList.get(k - 1)).length() == 0) {
/* 2374 */           k--;
/*      */         }
/*      */       }
/* 2377 */       String[] arrayOfString = new String[k];
/* 2378 */       return (String[])arrayList.subList(0, k).toArray((Object[])arrayOfString);
/*      */     } 
/* 2380 */     return Pattern.compile(paramString).split(this, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] split(String paramString) {
/* 2422 */     return split(paramString, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String join(CharSequence paramCharSequence, CharSequence... paramVarArgs) {
/* 2451 */     Objects.requireNonNull(paramCharSequence);
/* 2452 */     Objects.requireNonNull(paramVarArgs);
/*      */     
/* 2454 */     StringJoiner stringJoiner = new StringJoiner(paramCharSequence);
/* 2455 */     for (CharSequence charSequence : paramVarArgs) {
/* 2456 */       stringJoiner.add(charSequence);
/*      */     }
/* 2458 */     return stringJoiner.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String join(CharSequence paramCharSequence, Iterable<? extends CharSequence> paramIterable) {
/* 2500 */     Objects.requireNonNull(paramCharSequence);
/* 2501 */     Objects.requireNonNull(paramIterable);
/* 2502 */     StringJoiner stringJoiner = new StringJoiner(paramCharSequence);
/* 2503 */     for (CharSequence charSequence : paramIterable) {
/* 2504 */       stringJoiner.add(charSequence);
/*      */     }
/* 2506 */     return stringJoiner.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toLowerCase(Locale paramLocale) {
/* 2562 */     if (paramLocale == null) {
/* 2563 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*      */     
/* 2567 */     int j = this.value.length;
/*      */ 
/*      */ 
/*      */     
/* 2571 */     for (int i = 0; i < j; ) {
/* 2572 */       char c = this.value[i];
/* 2573 */       if (c >= '?' && c <= '?') {
/*      */         
/* 2575 */         int n = codePointAt(i);
/* 2576 */         if (n == Character.toLowerCase(n)) {
/*      */ 
/*      */           
/* 2579 */           i += Character.charCount(n); continue;
/*      */         } 
/* 2581 */       } else if (c == Character.toLowerCase(c)) {
/*      */ 
/*      */         
/* 2584 */         i++;
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 2590 */       char[] arrayOfChar = new char[j];
/* 2591 */       int k = 0;
/*      */ 
/*      */ 
/*      */       
/* 2595 */       System.arraycopy(this.value, 0, arrayOfChar, 0, i);
/*      */       
/* 2597 */       String str = paramLocale.getLanguage();
/* 2598 */       boolean bool = (str == "tr" || str == "az" || str == "lt") ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2604 */       int m = i; while (true) { char[] arrayOfChar1; if (m < j)
/* 2605 */         { int i1; byte b; int i2 = this.value[m];
/* 2606 */           if ((char)i2 >= '?' && (char)i2 <= '?') {
/*      */             
/* 2608 */             i2 = codePointAt(m);
/* 2609 */             b = Character.charCount(i2);
/*      */           } else {
/* 2611 */             b = 1;
/*      */           } 
/* 2613 */           if (bool || i2 == 931 || i2 == 304) {
/*      */ 
/*      */             
/* 2616 */             i1 = ConditionalSpecialCasing.toLowerCaseEx(this, m, paramLocale);
/*      */           } else {
/* 2618 */             i1 = Character.toLowerCase(i2);
/*      */           } 
/* 2620 */           if (i1 == -1 || i1 >= 65536)
/*      */           
/* 2622 */           { if (i1 == -1)
/*      */             
/* 2624 */             { arrayOfChar1 = ConditionalSpecialCasing.toLowerCaseCharArray(this, m, paramLocale); }
/* 2625 */             else { if (b == 2) {
/* 2626 */                 k += Character.toChars(i1, arrayOfChar, m + k) - b;
/*      */               } else {
/*      */                 
/* 2629 */                 arrayOfChar1 = Character.toChars(i1);
/*      */ 
/*      */ 
/*      */                 
/* 2633 */                 int i3 = arrayOfChar1.length;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               m += b; }
/*      */              }
/*      */           else
/* 2644 */           { arrayOfChar[m + k] = (char)i1; m += b; }  }
/*      */         else { break; }
/*      */          int n = arrayOfChar1.length; }
/* 2647 */        return new String(arrayOfChar, 0, j + k);
/*      */     } 
/*      */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toLowerCase() {
/* 2670 */     return toLowerCase(Locale.getDefault());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toUpperCase(Locale paramLocale) {
/* 2722 */     if (paramLocale == null) {
/* 2723 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*      */     
/* 2727 */     int j = this.value.length;
/*      */ 
/*      */ 
/*      */     
/* 2731 */     for (int i = 0; i < j; ) {
/* 2732 */       char[] arrayOfChar; int k = this.value[i];
/*      */       
/* 2734 */       if (k >= 55296 && k <= 56319) {
/*      */         
/* 2736 */         k = codePointAt(i);
/* 2737 */         int n = Character.charCount(k);
/*      */       } else {
/* 2739 */         boolean bool = true;
/*      */       } 
/* 2741 */       int m = Character.toUpperCaseEx(k);
/* 2742 */       if (m == -1 || k != m) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2752 */         k = 0;
/* 2753 */         arrayOfChar = new char[j];
/*      */ 
/*      */         
/* 2756 */         System.arraycopy(this.value, 0, arrayOfChar, 0, i);
/*      */         
/* 2758 */         String str = paramLocale.getLanguage();
/* 2759 */         boolean bool = (str == "tr" || str == "az" || str == "lt") ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2765 */         int n = i; while (true) { char[] arrayOfChar1; if (n < j)
/* 2766 */           { int i2; byte b; int i3 = this.value[n];
/* 2767 */             if ((char)i3 >= '?' && (char)i3 <= '?') {
/*      */               
/* 2769 */               i3 = codePointAt(n);
/* 2770 */               b = Character.charCount(i3);
/*      */             } else {
/* 2772 */               b = 1;
/*      */             } 
/* 2774 */             if (bool) {
/* 2775 */               i2 = ConditionalSpecialCasing.toUpperCaseEx(this, n, paramLocale);
/*      */             } else {
/* 2777 */               i2 = Character.toUpperCaseEx(i3);
/*      */             } 
/* 2779 */             if (i2 == -1 || i2 >= 65536)
/*      */             
/* 2781 */             { if (i2 == -1)
/* 2782 */               { if (bool) {
/*      */                   
/* 2784 */                   arrayOfChar1 = ConditionalSpecialCasing.toUpperCaseCharArray(this, n, paramLocale);
/*      */                 } else {
/* 2786 */                   arrayOfChar1 = Character.toUpperCaseCharArray(i3);
/*      */                 }  }
/* 2788 */               else { if (b == 2) {
/* 2789 */                   k += Character.toChars(i2, arrayOfChar, n + k) - b;
/*      */                 } else {
/*      */                   
/* 2792 */                   arrayOfChar1 = Character.toChars(i2);
/*      */ 
/*      */ 
/*      */                   
/* 2796 */                   int i4 = arrayOfChar1.length;
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 n += b; }
/*      */                }
/*      */             else
/* 2807 */             { arrayOfChar[n + k] = (char)i2; n += b; }  }
/*      */           else { break; }
/*      */            int i1 = arrayOfChar1.length; }
/* 2810 */          return new String(arrayOfChar, 0, j + k);
/*      */       } 
/*      */       i += arrayOfChar;
/*      */     } 
/*      */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toUpperCase() {
/* 2833 */     return toUpperCase(Locale.getDefault());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trim() {
/* 2868 */     int i = this.value.length;
/* 2869 */     byte b = 0;
/* 2870 */     char[] arrayOfChar = this.value;
/*      */     
/* 2872 */     while (b < i && arrayOfChar[b] <= ' ') {
/* 2873 */       b++;
/*      */     }
/* 2875 */     while (b < i && arrayOfChar[i - 1] <= ' ') {
/* 2876 */       i--;
/*      */     }
/* 2878 */     return (b > 0 || i < this.value.length) ? substring(b, i) : this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2887 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] toCharArray() {
/* 2899 */     char[] arrayOfChar = new char[this.value.length];
/* 2900 */     System.arraycopy(this.value, 0, arrayOfChar, 0, this.value.length);
/* 2901 */     return arrayOfChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String format(String paramString, Object... paramVarArgs) {
/* 2940 */     return (new Formatter()).format(paramString, paramVarArgs).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String format(Locale paramLocale, String paramString, Object... paramVarArgs) {
/* 2981 */     return (new Formatter(paramLocale)).format(paramString, paramVarArgs).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String valueOf(Object paramObject) {
/* 2994 */     return (paramObject == null) ? "null" : paramObject.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String valueOf(char[] paramArrayOfchar) {
/* 3008 */     return new String(paramArrayOfchar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String valueOf(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 3032 */     return new String(paramArrayOfchar, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String copyValueOf(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 3049 */     return new String(paramArrayOfchar, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String copyValueOf(char[] paramArrayOfchar) {
/* 3060 */     return new String(paramArrayOfchar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String valueOf(boolean paramBoolean) {
/* 3072 */     return paramBoolean ? "true" : "false";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String valueOf(char paramChar) {
/* 3084 */     char[] arrayOfChar = { paramChar };
/* 3085 */     return new String(arrayOfChar, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String valueOf(int paramInt) {
/* 3099 */     return Integer.toString(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String valueOf(long paramLong) {
/* 3113 */     return Long.toString(paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String valueOf(float paramFloat) {
/* 3127 */     return Float.toString(paramFloat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String valueOf(double paramDouble) {
/* 3141 */     return Double.toString(paramDouble);
/*      */   }
/*      */   
/*      */   public native String intern();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/String.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */