/*     */ package com.sun.jndi.toolkit.url;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URLDecoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UrlUtil
/*     */ {
/*     */   public static final String decode(String paramString) throws MalformedURLException {
/*     */     try {
/*  48 */       return decode(paramString, "8859_1");
/*  49 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */       
/*  51 */       throw new MalformedURLException("ISO-Latin-1 decoder unavailable");
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
/*     */   public static final String decode(String paramString1, String paramString2) throws MalformedURLException, UnsupportedEncodingException {
/*     */     try {
/*  67 */       return URLDecoder.decode(paramString1, paramString2);
/*  68 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  69 */       MalformedURLException malformedURLException = new MalformedURLException("Invalid URI encoding: " + paramString1);
/*  70 */       malformedURLException.initCause(illegalArgumentException);
/*  71 */       throw malformedURLException;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String encode(String paramString1, String paramString2) throws UnsupportedEncodingException {
/*  90 */     byte[] arrayOfByte = paramString1.getBytes(paramString2);
/*  91 */     int i = arrayOfByte.length;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     char[] arrayOfChar = new char[3 * i];
/* 101 */     byte b1 = 0;
/*     */     
/* 103 */     for (byte b2 = 0; b2 < i; b2++) {
/* 104 */       if ((arrayOfByte[b2] >= 97 && arrayOfByte[b2] <= 122) || (arrayOfByte[b2] >= 65 && arrayOfByte[b2] <= 90) || (arrayOfByte[b2] >= 48 && arrayOfByte[b2] <= 57) || "=,+;.'-@&/$_()!~*:"
/*     */ 
/*     */         
/* 107 */         .indexOf(arrayOfByte[b2]) >= 0) {
/* 108 */         arrayOfChar[b1++] = (char)arrayOfByte[b2];
/*     */       } else {
/* 110 */         arrayOfChar[b1++] = '%';
/* 111 */         arrayOfChar[b1++] = Character.forDigit(0xF & arrayOfByte[b2] >>> 4, 16);
/* 112 */         arrayOfChar[b1++] = Character.forDigit(0xF & arrayOfByte[b2], 16);
/*     */       } 
/*     */     } 
/* 115 */     return new String(arrayOfChar, 0, b1);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/url/UrlUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */