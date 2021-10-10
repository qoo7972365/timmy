/*     */ package jdk.internal.util.xml.impl;
/*     */ 
/*     */ import jdk.internal.org.xml.sax.Attributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Attrs
/*     */   implements Attributes
/*     */ {
/*     */   String[] mItems;
/*     */   private char mLength;
/*  47 */   private char mAttrIdx = Character.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attrs() {
/*  54 */     this.mItems = new String[64];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLength(char paramChar) {
/*  64 */     if (paramChar > (char)(this.mItems.length >> 3)) {
/*  65 */       this.mItems = new String[paramChar << 3];
/*     */     }
/*  67 */     this.mLength = paramChar;
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
/*     */   public int getLength() {
/*  84 */     return this.mLength;
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
/*     */   public String getURI(int paramInt) {
/*  96 */     return (paramInt >= 0 && paramInt < this.mLength) ? this.mItems[paramInt << 3] : null;
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
/*     */   public String getLocalName(int paramInt) {
/* 110 */     return (paramInt >= 0 && paramInt < this.mLength) ? this.mItems[(paramInt << 3) + 2] : null;
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
/*     */   public String getQName(int paramInt) {
/* 124 */     if (paramInt < 0 || paramInt >= this.mLength) {
/* 125 */       return null;
/*     */     }
/* 127 */     return this.mItems[(paramInt << 3) + 1];
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
/*     */   public String getType(int paramInt) {
/* 151 */     return (paramInt >= 0 && paramInt < this.mItems.length >> 3) ? this.mItems[(paramInt << 3) + 4] : null;
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
/*     */   public String getValue(int paramInt) {
/* 169 */     return (paramInt >= 0 && paramInt < this.mLength) ? this.mItems[(paramInt << 3) + 3] : null;
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
/*     */   public int getIndex(String paramString1, String paramString2) {
/* 184 */     char c = this.mLength; char c1;
/* 185 */     for (c1 = Character.MIN_VALUE; c1 < c; c1 = (char)(c1 + 1)) {
/* 186 */       if (this.mItems[c1 << 3].equals(paramString1) && this.mItems[(c1 << 3) + 2]
/* 187 */         .equals(paramString2)) {
/* 188 */         return c1;
/*     */       }
/*     */     } 
/* 191 */     return -1;
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
/*     */   int getIndexNullNS(String paramString1, String paramString2) {
/* 205 */     char c = this.mLength;
/* 206 */     if (paramString1 != null) {
/* 207 */       char c1; for (c1 = Character.MIN_VALUE; c1 < c; c1 = (char)(c1 + 1)) {
/* 208 */         if (this.mItems[c1 << 3].equals(paramString1) && this.mItems[(c1 << 3) + 2]
/* 209 */           .equals(paramString2))
/* 210 */           return c1; 
/*     */       } 
/*     */     } else {
/*     */       char c1;
/* 214 */       for (c1 = Character.MIN_VALUE; c1 < c; c1 = (char)(c1 + 1)) {
/* 215 */         if (this.mItems[(c1 << 3) + 2].equals(paramString2)) {
/* 216 */           return c1;
/*     */         }
/*     */       } 
/*     */     } 
/* 220 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex(String paramString) {
/* 231 */     char c = this.mLength;
/* 232 */     for (char c1 = Character.MIN_VALUE; c1 < c; c1 = (char)(c1 + 1)) {
/* 233 */       if (this.mItems[(c1 << 3) + 1].equals(paramString)) {
/* 234 */         return c1;
/*     */       }
/*     */     } 
/* 237 */     return -1;
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
/*     */   public String getType(String paramString1, String paramString2) {
/* 253 */     int i = getIndex(paramString1, paramString2);
/* 254 */     return (i >= 0) ? this.mItems[(i << 3) + 4] : null;
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
/*     */   public String getType(String paramString) {
/* 268 */     int i = getIndex(paramString);
/* 269 */     return (i >= 0) ? this.mItems[(i << 3) + 4] : null;
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
/*     */   public String getValue(String paramString1, String paramString2) {
/* 285 */     int i = getIndex(paramString1, paramString2);
/* 286 */     return (i >= 0) ? this.mItems[(i << 3) + 3] : null;
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
/*     */   public String getValue(String paramString) {
/* 300 */     int i = getIndex(paramString);
/* 301 */     return (i >= 0) ? this.mItems[(i << 3) + 3] : null;
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
/*     */   public boolean isDeclared(int paramInt) {
/* 316 */     if (paramInt < 0 || paramInt >= this.mLength) {
/* 317 */       throw new ArrayIndexOutOfBoundsException("");
/*     */     }
/*     */     
/* 320 */     return (this.mItems[(paramInt << 3) + 5] != null);
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
/*     */   public boolean isDeclared(String paramString) {
/* 335 */     int i = getIndex(paramString);
/* 336 */     if (i < 0) {
/* 337 */       throw new IllegalArgumentException("");
/*     */     }
/*     */     
/* 340 */     return (this.mItems[(i << 3) + 5] != null);
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
/*     */   public boolean isDeclared(String paramString1, String paramString2) {
/* 361 */     int i = getIndex(paramString1, paramString2);
/* 362 */     if (i < 0) {
/* 363 */       throw new IllegalArgumentException("");
/*     */     }
/*     */     
/* 366 */     return (this.mItems[(i << 3) + 5] != null);
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
/*     */   public boolean isSpecified(int paramInt) {
/* 379 */     if (paramInt < 0 || paramInt >= this.mLength) {
/* 380 */       throw new ArrayIndexOutOfBoundsException("");
/*     */     }
/*     */     
/* 383 */     String str = this.mItems[(paramInt << 3) + 5];
/* 384 */     return (str != null) ? ((str.charAt(0) == 'd')) : true;
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
/*     */   public boolean isSpecified(String paramString1, String paramString2) {
/* 403 */     int i = getIndex(paramString1, paramString2);
/* 404 */     if (i < 0) {
/* 405 */       throw new IllegalArgumentException("");
/*     */     }
/*     */     
/* 408 */     String str = this.mItems[(i << 3) + 5];
/* 409 */     return (str != null) ? ((str.charAt(0) == 'd')) : true;
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
/*     */   public boolean isSpecified(String paramString) {
/* 422 */     int i = getIndex(paramString);
/* 423 */     if (i < 0) {
/* 424 */       throw new IllegalArgumentException("");
/*     */     }
/*     */     
/* 427 */     String str = this.mItems[(i << 3) + 5];
/* 428 */     return (str != null) ? ((str.charAt(0) == 'd')) : true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/util/xml/impl/Attrs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */