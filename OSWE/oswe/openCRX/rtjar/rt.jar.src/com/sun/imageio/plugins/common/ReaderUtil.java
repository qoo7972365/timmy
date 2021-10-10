/*     */ package com.sun.imageio.plugins.common;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.IOException;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReaderUtil
/*     */ {
/*     */   private static void computeUpdatedPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int[] paramArrayOfint, int paramInt10) {
/*  84 */     boolean bool = false;
/*  85 */     int i = -1;
/*  86 */     int j = -1;
/*  87 */     int k = -1;
/*     */     
/*  89 */     for (byte b = 0; b < paramInt8; b++) {
/*  90 */       int m = paramInt7 + b * paramInt9;
/*  91 */       if (m >= paramInt1)
/*     */       {
/*     */         
/*  94 */         if ((m - paramInt1) % paramInt6 == 0) {
/*     */ 
/*     */           
/*  97 */           if (m >= paramInt1 + paramInt2) {
/*     */             break;
/*     */           }
/*     */           
/* 101 */           int n = paramInt3 + (m - paramInt1) / paramInt6;
/*     */           
/* 103 */           if (n >= paramInt4) {
/*     */ 
/*     */             
/* 106 */             if (n > paramInt5) {
/*     */               break;
/*     */             }
/*     */             
/* 110 */             if (!bool) {
/* 111 */               i = n;
/* 112 */               bool = true;
/* 113 */             } else if (j == -1) {
/* 114 */               j = n;
/*     */             } 
/* 116 */             k = n;
/*     */           } 
/*     */         }  } 
/* 119 */     }  paramArrayOfint[paramInt10] = i;
/*     */ 
/*     */     
/* 122 */     if (!bool) {
/* 123 */       paramArrayOfint[paramInt10 + 2] = 0;
/*     */     } else {
/* 125 */       paramArrayOfint[paramInt10 + 2] = k - i + 1;
/*     */     } 
/*     */ 
/*     */     
/* 129 */     paramArrayOfint[paramInt10 + 4] = Math.max(j - i, 1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] computeUpdatedPixels(Rectangle paramRectangle, Point paramPoint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12) {
/* 190 */     int[] arrayOfInt = new int[6];
/* 191 */     computeUpdatedPixels(paramRectangle.x, paramRectangle.width, paramPoint.x, paramInt1, paramInt3, paramInt5, paramInt7, paramInt9, paramInt11, arrayOfInt, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 196 */     computeUpdatedPixels(paramRectangle.y, paramRectangle.height, paramPoint.y, paramInt2, paramInt4, paramInt6, paramInt8, paramInt10, paramInt12, arrayOfInt, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int readMultiByteInteger(ImageInputStream paramImageInputStream) throws IOException {
/* 207 */     byte b = paramImageInputStream.readByte();
/* 208 */     int i = b & Byte.MAX_VALUE;
/* 209 */     while ((b & 0x80) == 128) {
/* 210 */       i <<= 7;
/* 211 */       b = paramImageInputStream.readByte();
/* 212 */       i |= b & Byte.MAX_VALUE;
/*     */     } 
/* 214 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/common/ReaderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */