/*     */ package com.sun.xml.internal.ws.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ASCIIUtility
/*     */ {
/*     */   public static int parseInt(byte[] b, int start, int end, int radix) throws NumberFormatException {
/*  49 */     if (b == null) {
/*  50 */       throw new NumberFormatException("null");
/*     */     }
/*  52 */     int result = 0;
/*  53 */     boolean negative = false;
/*  54 */     int i = start;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     if (end > start) {
/*  60 */       int limit; if (b[i] == 45) {
/*  61 */         negative = true;
/*  62 */         limit = Integer.MIN_VALUE;
/*  63 */         i++;
/*     */       } else {
/*  65 */         limit = -2147483647;
/*     */       } 
/*  67 */       int multmin = limit / radix;
/*  68 */       if (i < end) {
/*  69 */         int digit = Character.digit((char)b[i++], radix);
/*  70 */         if (digit < 0) {
/*  71 */           throw new NumberFormatException("illegal number: " + 
/*  72 */               toString(b, start, end));
/*     */         }
/*     */         
/*  75 */         result = -digit;
/*     */       } 
/*     */       
/*  78 */       while (i < end) {
/*     */         
/*  80 */         int digit = Character.digit((char)b[i++], radix);
/*  81 */         if (digit < 0) {
/*  82 */           throw new NumberFormatException("illegal number");
/*     */         }
/*  84 */         if (result < multmin) {
/*  85 */           throw new NumberFormatException("illegal number");
/*     */         }
/*  87 */         result *= radix;
/*  88 */         if (result < limit + digit) {
/*  89 */           throw new NumberFormatException("illegal number");
/*     */         }
/*  91 */         result -= digit;
/*     */       } 
/*     */     } else {
/*  94 */       throw new NumberFormatException("illegal number");
/*     */     } 
/*  96 */     if (negative) {
/*  97 */       if (i > start + 1) {
/*  98 */         return result;
/*     */       }
/* 100 */       throw new NumberFormatException("illegal number");
/*     */     } 
/*     */     
/* 103 */     return -result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(byte[] b, int start, int end) {
/* 113 */     int size = end - start;
/* 114 */     char[] theChars = new char[size];
/*     */     
/* 116 */     for (int i = 0, j = start; i < size;) {
/* 117 */       theChars[i++] = (char)(b[j++] & 0xFF);
/*     */     }
/* 119 */     return new String(theChars);
/*     */   }
/*     */   
/*     */   public static void copyStream(InputStream is, OutputStream out) throws IOException {
/* 123 */     int size = 1024;
/* 124 */     byte[] buf = new byte[size];
/*     */     
/*     */     int len;
/* 127 */     while ((len = is.read(buf, 0, size)) != -1)
/* 128 */       out.write(buf, 0, len); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/ASCIIUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */