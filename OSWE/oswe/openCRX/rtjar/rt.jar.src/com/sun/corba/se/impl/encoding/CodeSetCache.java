/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CodeSetCache
/*     */ {
/*  47 */   private ThreadLocal converterCaches = new ThreadLocal() {
/*     */       public Object initialValue() {
/*  49 */         return new Map[] { new WeakHashMap<>(), new WeakHashMap<>() };
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int BTC_CACHE_MAP = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int CTB_CACHE_MAP = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CharsetDecoder getByteToCharConverter(Object paramObject) {
/*  71 */     Map map = ((Map[])this.converterCaches.get())[0];
/*     */     
/*  73 */     return (CharsetDecoder)map.get(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CharsetEncoder getCharToByteConverter(Object paramObject) {
/*  80 */     Map map = ((Map[])this.converterCaches.get())[1];
/*     */     
/*  82 */     return (CharsetEncoder)map.get(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CharsetDecoder setConverter(Object paramObject, CharsetDecoder paramCharsetDecoder) {
/*  90 */     Map<Object, CharsetDecoder> map = ((Map[])this.converterCaches.get())[0];
/*     */     
/*  92 */     map.put(paramObject, paramCharsetDecoder);
/*     */     
/*  94 */     return paramCharsetDecoder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CharsetEncoder setConverter(Object paramObject, CharsetEncoder paramCharsetEncoder) {
/* 103 */     Map<Object, CharsetEncoder> map = ((Map[])this.converterCaches.get())[1];
/*     */     
/* 105 */     map.put(paramObject, paramCharsetEncoder);
/*     */     
/* 107 */     return paramCharsetEncoder;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/CodeSetCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */