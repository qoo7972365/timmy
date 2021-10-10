/*     */ package com.sun.corba.se.impl.ior;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteBuffer
/*     */ {
/*     */   protected byte[] elementData;
/*     */   protected int elementCount;
/*     */   protected int capacityIncrement;
/*     */   
/*     */   public ByteBuffer(int paramInt1, int paramInt2) {
/*  70 */     if (paramInt1 < 0) {
/*  71 */       throw new IllegalArgumentException("Illegal Capacity: " + paramInt1);
/*     */     }
/*  73 */     this.elementData = new byte[paramInt1];
/*  74 */     this.capacityIncrement = paramInt2;
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
/*     */   public ByteBuffer(int paramInt) {
/*  86 */     this(paramInt, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer() {
/*  95 */     this(200);
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
/*     */   public void trimToSize() {
/* 107 */     int i = this.elementData.length;
/* 108 */     if (this.elementCount < i) {
/* 109 */       byte[] arrayOfByte = this.elementData;
/* 110 */       this.elementData = new byte[this.elementCount];
/* 111 */       System.arraycopy(arrayOfByte, 0, this.elementData, 0, this.elementCount);
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
/*     */   private void ensureCapacityHelper(int paramInt) {
/* 124 */     int i = this.elementData.length;
/* 125 */     if (paramInt > i) {
/* 126 */       byte[] arrayOfByte = this.elementData;
/* 127 */       int j = (this.capacityIncrement > 0) ? (i + this.capacityIncrement) : (i * 2);
/*     */       
/* 129 */       if (j < paramInt) {
/* 130 */         j = paramInt;
/*     */       }
/* 132 */       this.elementData = new byte[j];
/* 133 */       System.arraycopy(arrayOfByte, 0, this.elementData, 0, this.elementCount);
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
/*     */   public int capacity() {
/* 145 */     return this.elementData.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 154 */     return this.elementCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 165 */     return (this.elementCount == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void append(byte paramByte) {
/* 170 */     ensureCapacityHelper(this.elementCount + 1);
/* 171 */     this.elementData[this.elementCount++] = paramByte;
/*     */   }
/*     */ 
/*     */   
/*     */   public void append(int paramInt) {
/* 176 */     ensureCapacityHelper(this.elementCount + 4);
/* 177 */     doAppend(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   private void doAppend(int paramInt) {
/* 182 */     int i = paramInt;
/* 183 */     for (byte b = 0; b < 4; b++) {
/* 184 */       this.elementData[this.elementCount + b] = (byte)(i & 0xFF);
/* 185 */       i >>= 8;
/*     */     } 
/* 187 */     this.elementCount += 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void append(String paramString) {
/* 192 */     byte[] arrayOfByte = paramString.getBytes();
/* 193 */     ensureCapacityHelper(this.elementCount + arrayOfByte.length + 4);
/* 194 */     doAppend(arrayOfByte.length);
/* 195 */     System.arraycopy(arrayOfByte, 0, this.elementData, this.elementCount, arrayOfByte.length);
/* 196 */     this.elementCount += arrayOfByte.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] toArray() {
/* 206 */     return this.elementData;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/ByteBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */