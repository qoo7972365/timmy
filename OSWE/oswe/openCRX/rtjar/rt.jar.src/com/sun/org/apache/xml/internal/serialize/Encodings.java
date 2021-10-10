/*     */ package com.sun.org.apache.xml.internal.serialize;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.EncodingMap;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Encodings
/*     */ {
/*     */   static final int DEFAULT_LAST_PRINTABLE = 127;
/*     */   static final int LAST_PRINTABLE_UNICODE = 65535;
/*  52 */   static final String[] UNICODE_ENCODINGS = new String[] { "Unicode", "UnicodeBig", "UnicodeLittle", "GB2312", "UTF8", "UTF-16" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String DEFAULT_ENCODING = "UTF8";
/*     */ 
/*     */ 
/*     */   
/*  61 */   private static final Map<String, EncodingInfo> _encodings = new ConcurrentHashMap<>();
/*     */   
/*     */   static final String JIS_DANGER_CHARS = "\\~¢£¥¬—―‖…‾‾∥∯〜＼～￠￡￢￣";
/*     */ 
/*     */   
/*     */   static EncodingInfo getEncodingInfo(String encoding, boolean allowJavaNames) throws UnsupportedEncodingException {
/*  67 */     EncodingInfo eInfo = null;
/*  68 */     if (encoding == null) {
/*  69 */       if ((eInfo = _encodings.get("UTF8")) != null)
/*  70 */         return eInfo; 
/*  71 */       eInfo = new EncodingInfo(EncodingMap.getJava2IANAMapping("UTF8"), "UTF8", 65535);
/*  72 */       _encodings.put("UTF8", eInfo);
/*  73 */       return eInfo;
/*     */     } 
/*     */     
/*  76 */     encoding = encoding.toUpperCase(Locale.ENGLISH);
/*  77 */     String jName = EncodingMap.getIANA2JavaMapping(encoding);
/*  78 */     if (jName == null) {
/*     */       
/*  80 */       if (allowJavaNames) {
/*  81 */         EncodingInfo.testJavaEncodingName(encoding);
/*  82 */         if ((eInfo = _encodings.get(encoding)) != null) {
/*  83 */           return eInfo;
/*     */         }
/*  85 */         int j = 0;
/*  86 */         for (; j < UNICODE_ENCODINGS.length; j++) {
/*  87 */           if (UNICODE_ENCODINGS[j].equalsIgnoreCase(encoding)) {
/*  88 */             eInfo = new EncodingInfo(EncodingMap.getJava2IANAMapping(encoding), encoding, 65535);
/*     */             break;
/*     */           } 
/*     */         } 
/*  92 */         if (j == UNICODE_ENCODINGS.length) {
/*  93 */           eInfo = new EncodingInfo(EncodingMap.getJava2IANAMapping(encoding), encoding, 127);
/*     */         }
/*  95 */         _encodings.put(encoding, eInfo);
/*  96 */         return eInfo;
/*     */       } 
/*  98 */       throw new UnsupportedEncodingException(encoding);
/*     */     } 
/*     */     
/* 101 */     if ((eInfo = _encodings.get(jName)) != null) {
/* 102 */       return eInfo;
/*     */     }
/*     */     
/* 105 */     int i = 0;
/* 106 */     for (; i < UNICODE_ENCODINGS.length; i++) {
/* 107 */       if (UNICODE_ENCODINGS[i].equalsIgnoreCase(jName)) {
/* 108 */         eInfo = new EncodingInfo(encoding, jName, 65535);
/*     */         break;
/*     */       } 
/*     */     } 
/* 112 */     if (i == UNICODE_ENCODINGS.length) {
/* 113 */       eInfo = new EncodingInfo(encoding, jName, 127);
/*     */     }
/* 115 */     _encodings.put(jName, eInfo);
/* 116 */     return eInfo;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serialize/Encodings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */