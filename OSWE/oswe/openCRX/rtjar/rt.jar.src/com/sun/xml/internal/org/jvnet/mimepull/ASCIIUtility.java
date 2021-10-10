/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ASCIIUtility
/*     */ {
/*     */   public static int parseInt(byte[] b, int start, int end, int radix) throws NumberFormatException {
/*  43 */     if (b == null) {
/*  44 */       throw new NumberFormatException("null");
/*     */     }
/*     */     
/*  47 */     int result = 0;
/*  48 */     boolean negative = false;
/*  49 */     int i = start;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     if (end > start) {
/*  55 */       int limit; if (b[i] == 45) {
/*  56 */         negative = true;
/*  57 */         limit = Integer.MIN_VALUE;
/*  58 */         i++;
/*     */       } else {
/*  60 */         limit = -2147483647;
/*     */       } 
/*  62 */       int multmin = limit / radix;
/*  63 */       if (i < end) {
/*  64 */         int digit = Character.digit((char)b[i++], radix);
/*  65 */         if (digit < 0) {
/*  66 */           throw new NumberFormatException("illegal number: " + 
/*  67 */               toString(b, start, end));
/*     */         }
/*     */         
/*  70 */         result = -digit;
/*     */       } 
/*     */       
/*  73 */       while (i < end) {
/*     */         
/*  75 */         int digit = Character.digit((char)b[i++], radix);
/*  76 */         if (digit < 0) {
/*  77 */           throw new NumberFormatException("illegal number");
/*     */         }
/*  79 */         if (result < multmin) {
/*  80 */           throw new NumberFormatException("illegal number");
/*     */         }
/*  82 */         result *= radix;
/*  83 */         if (result < limit + digit) {
/*  84 */           throw new NumberFormatException("illegal number");
/*     */         }
/*  86 */         result -= digit;
/*     */       } 
/*     */     } else {
/*  89 */       throw new NumberFormatException("illegal number");
/*     */     } 
/*  91 */     if (negative) {
/*  92 */       if (i > start + 1) {
/*  93 */         return result;
/*     */       }
/*  95 */       throw new NumberFormatException("illegal number");
/*     */     } 
/*     */     
/*  98 */     return -result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(byte[] b, int start, int end) {
/* 108 */     int size = end - start;
/* 109 */     char[] theChars = new char[size];
/*     */     
/* 111 */     for (int i = 0, j = start; i < size;) {
/* 112 */       theChars[i++] = (char)(b[j++] & 0xFF);
/*     */     }
/*     */     
/* 115 */     return new String(theChars);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/ASCIIUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */