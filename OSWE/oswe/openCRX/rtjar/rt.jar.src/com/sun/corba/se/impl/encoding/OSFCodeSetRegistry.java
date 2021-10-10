/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OSFCodeSetRegistry
/*     */ {
/*     */   public static final int ISO_8859_1_VALUE = 65537;
/*     */   public static final int UTF_16_VALUE = 65801;
/*     */   public static final int UTF_8_VALUE = 83951617;
/*     */   public static final int UCS_2_VALUE = 65792;
/*     */   public static final int ISO_646_VALUE = 65568;
/*     */   
/*     */   public static final class Entry
/*     */   {
/*     */     private String javaName;
/*     */     private int encodingNum;
/*     */     private boolean isFixedWidth;
/*     */     private int maxBytesPerChar;
/*     */     
/*     */     private Entry(String param1String, int param1Int1, boolean param1Boolean, int param1Int2) {
/*  65 */       this.javaName = param1String;
/*  66 */       this.encodingNum = param1Int1;
/*  67 */       this.isFixedWidth = param1Boolean;
/*  68 */       this.maxBytesPerChar = param1Int2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/*  77 */       return this.javaName;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getNumber() {
/*  84 */       return this.encodingNum;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isFixedWidth() {
/*  93 */       return this.isFixedWidth;
/*     */     }
/*     */     
/*     */     public int getMaxBytesPerChar() {
/*  97 */       return this.maxBytesPerChar;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 105 */       if (this == param1Object) {
/* 106 */         return true;
/*     */       }
/* 108 */       if (!(param1Object instanceof Entry)) {
/* 109 */         return false;
/*     */       }
/* 111 */       Entry entry = (Entry)param1Object;
/*     */ 
/*     */       
/* 114 */       return (this.javaName.equals(entry.javaName) && this.encodingNum == entry.encodingNum && this.isFixedWidth == entry.isFixedWidth && this.maxBytesPerChar == entry.maxBytesPerChar);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 124 */       return this.encodingNum;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public static final Entry ISO_8859_1 = new Entry("ISO-8859-1", 65537, true, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   static final Entry UTF_16BE = new Entry("UTF-16BE", -1, true, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   static final Entry UTF_16LE = new Entry("UTF-16LE", -2, true, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public static final Entry UTF_16 = new Entry("UTF-16", 65801, true, 4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public static final Entry UTF_8 = new Entry("UTF-8", 83951617, false, 6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public static final Entry UCS_2 = new Entry("UCS-2", 65792, true, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   public static final Entry ISO_646 = new Entry("US-ASCII", 65568, true, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entry lookupEntry(int paramInt) {
/* 215 */     switch (paramInt) {
/*     */       case 65537:
/* 217 */         return ISO_8859_1;
/*     */       case 65801:
/* 219 */         return UTF_16;
/*     */       case 83951617:
/* 221 */         return UTF_8;
/*     */       case 65568:
/* 223 */         return ISO_646;
/*     */       case 65792:
/* 225 */         return UCS_2;
/*     */     } 
/* 227 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/OSFCodeSetRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */