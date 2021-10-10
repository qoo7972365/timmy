/*     */ package com.sun.org.apache.xml.internal.security.c14n.implementations;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UtfHelpper
/*     */ {
/*     */   static final void writeByte(String paramString, OutputStream paramOutputStream, Map<String, byte[]> paramMap) throws IOException {
/*  36 */     byte[] arrayOfByte = paramMap.get(paramString);
/*  37 */     if (arrayOfByte == null) {
/*  38 */       arrayOfByte = getStringInUtf8(paramString);
/*  39 */       paramMap.put(paramString, arrayOfByte);
/*     */     } 
/*     */     
/*  42 */     paramOutputStream.write(arrayOfByte);
/*     */   } static final void writeCharToUtf8(char paramChar, OutputStream paramOutputStream) throws IOException {
/*     */     byte b;
/*     */     int i;
/*  46 */     if (paramChar < '') {
/*  47 */       paramOutputStream.write(paramChar);
/*     */       return;
/*     */     } 
/*  50 */     if ((paramChar >= '?' && paramChar <= '?') || (paramChar >= '?' && paramChar <= '?')) {
/*     */       
/*  52 */       paramOutputStream.write(63);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  58 */     if (paramChar > '߿') {
/*  59 */       char c1 = (char)(paramChar >>> 12);
/*  60 */       i = 224;
/*  61 */       if (c1 > '\000') {
/*  62 */         i |= c1 & 0xF;
/*     */       }
/*  64 */       paramOutputStream.write(i);
/*  65 */       i = 128;
/*  66 */       b = 63;
/*     */     } else {
/*  68 */       i = 192;
/*  69 */       b = 31;
/*     */     } 
/*  71 */     char c = (char)(paramChar >>> 6);
/*  72 */     if (c > '\000') {
/*  73 */       i |= c & b;
/*     */     }
/*  75 */     paramOutputStream.write(i);
/*  76 */     paramOutputStream.write(0x80 | paramChar & 0x3F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final void writeStringToUtf8(String paramString, OutputStream paramOutputStream) throws IOException {
/*  84 */     int i = paramString.length();
/*  85 */     byte b = 0;
/*     */     
/*  87 */     while (b < i) {
/*  88 */       byte b1; int j; char c1 = paramString.charAt(b++);
/*  89 */       if (c1 < '') {
/*  90 */         paramOutputStream.write(c1);
/*     */         continue;
/*     */       } 
/*  93 */       if ((c1 >= '?' && c1 <= '?') || (c1 >= '?' && c1 <= '?')) {
/*     */         
/*  95 */         paramOutputStream.write(63);
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 101 */       if (c1 > '߿') {
/* 102 */         char c = (char)(c1 >>> 12);
/* 103 */         j = 224;
/* 104 */         if (c > '\000') {
/* 105 */           j |= c & 0xF;
/*     */         }
/* 107 */         paramOutputStream.write(j);
/* 108 */         j = 128;
/* 109 */         b1 = 63;
/*     */       } else {
/* 111 */         j = 192;
/* 112 */         b1 = 31;
/*     */       } 
/* 114 */       char c2 = (char)(c1 >>> 6);
/* 115 */       if (c2 > '\000') {
/* 116 */         j |= c2 & b1;
/*     */       }
/* 118 */       paramOutputStream.write(j);
/* 119 */       paramOutputStream.write(0x80 | c1 & 0x3F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final byte[] getStringInUtf8(String paramString) {
/* 126 */     int i = paramString.length();
/* 127 */     boolean bool = false;
/* 128 */     byte[] arrayOfByte = new byte[i];
/* 129 */     byte b1 = 0;
/* 130 */     byte b2 = 0;
/*     */     
/* 132 */     while (b1 < i) {
/* 133 */       byte b; byte b3; char c1 = paramString.charAt(b1++);
/* 134 */       if (c1 < '') {
/* 135 */         arrayOfByte[b2++] = (byte)c1;
/*     */         continue;
/*     */       } 
/* 138 */       if ((c1 >= '?' && c1 <= '?') || (c1 >= '?' && c1 <= '?')) {
/*     */         
/* 140 */         arrayOfByte[b2++] = 63;
/*     */         continue;
/*     */       } 
/* 143 */       if (!bool) {
/* 144 */         byte[] arrayOfByte1 = new byte[3 * i];
/* 145 */         System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, b2);
/* 146 */         arrayOfByte = arrayOfByte1;
/* 147 */         bool = true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 152 */       if (c1 > '߿') {
/* 153 */         char c = (char)(c1 >>> 12);
/* 154 */         b3 = -32;
/* 155 */         if (c > '\000') {
/* 156 */           b3 = (byte)(b3 | c & 0xF);
/*     */         }
/* 158 */         arrayOfByte[b2++] = b3;
/* 159 */         b3 = -128;
/* 160 */         b = 63;
/*     */       } else {
/* 162 */         b3 = -64;
/* 163 */         b = 31;
/*     */       } 
/* 165 */       char c2 = (char)(c1 >>> 6);
/* 166 */       if (c2 > '\000') {
/* 167 */         b3 = (byte)(b3 | c2 & b);
/*     */       }
/* 169 */       arrayOfByte[b2++] = b3;
/* 170 */       arrayOfByte[b2++] = (byte)(0x80 | c1 & 0x3F);
/*     */     } 
/* 172 */     if (bool) {
/* 173 */       byte[] arrayOfByte1 = new byte[b2];
/* 174 */       System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, b2);
/* 175 */       arrayOfByte = arrayOfByte1;
/*     */     } 
/* 177 */     return arrayOfByte;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/implementations/UtfHelpper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */