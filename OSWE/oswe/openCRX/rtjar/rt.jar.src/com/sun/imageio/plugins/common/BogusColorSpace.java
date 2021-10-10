/*     */ package com.sun.imageio.plugins.common;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BogusColorSpace
/*     */   extends ColorSpace
/*     */ {
/*     */   private static int getType(int paramInt) {
/*  44 */     if (paramInt < 1) {
/*  45 */       throw new IllegalArgumentException("numComponents < 1!");
/*     */     }
/*     */ 
/*     */     
/*  49 */     switch (paramInt)
/*     */     { case 1:
/*  51 */         i = 6;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  60 */         return i; }  int i = paramInt + 10; return i;
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
/*     */   public BogusColorSpace(int paramInt) {
/*  72 */     super(getType(paramInt), paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] toRGB(float[] paramArrayOffloat) {
/*  82 */     if (paramArrayOffloat.length < getNumComponents()) {
/*  83 */       throw new ArrayIndexOutOfBoundsException("colorvalue.length < getNumComponents()");
/*     */     }
/*     */ 
/*     */     
/*  87 */     float[] arrayOfFloat = new float[3];
/*     */     
/*  89 */     System.arraycopy(paramArrayOffloat, 0, arrayOfFloat, 0, 
/*  90 */         Math.min(3, getNumComponents()));
/*     */     
/*  92 */     return paramArrayOffloat;
/*     */   }
/*     */   
/*     */   public float[] fromRGB(float[] paramArrayOffloat) {
/*  96 */     if (paramArrayOffloat.length < 3) {
/*  97 */       throw new ArrayIndexOutOfBoundsException("rgbvalue.length < 3");
/*     */     }
/*     */ 
/*     */     
/* 101 */     float[] arrayOfFloat = new float[getNumComponents()];
/*     */     
/* 103 */     System.arraycopy(paramArrayOffloat, 0, arrayOfFloat, 0, 
/* 104 */         Math.min(3, arrayOfFloat.length));
/*     */     
/* 106 */     return paramArrayOffloat;
/*     */   }
/*     */   
/*     */   public float[] toCIEXYZ(float[] paramArrayOffloat) {
/* 110 */     if (paramArrayOffloat.length < getNumComponents()) {
/* 111 */       throw new ArrayIndexOutOfBoundsException("colorvalue.length < getNumComponents()");
/*     */     }
/*     */ 
/*     */     
/* 115 */     float[] arrayOfFloat = new float[3];
/*     */     
/* 117 */     System.arraycopy(paramArrayOffloat, 0, arrayOfFloat, 0, 
/* 118 */         Math.min(3, getNumComponents()));
/*     */     
/* 120 */     return paramArrayOffloat;
/*     */   }
/*     */   
/*     */   public float[] fromCIEXYZ(float[] paramArrayOffloat) {
/* 124 */     if (paramArrayOffloat.length < 3) {
/* 125 */       throw new ArrayIndexOutOfBoundsException("xyzvalue.length < 3");
/*     */     }
/*     */ 
/*     */     
/* 129 */     float[] arrayOfFloat = new float[getNumComponents()];
/*     */     
/* 131 */     System.arraycopy(paramArrayOffloat, 0, arrayOfFloat, 0, 
/* 132 */         Math.min(3, arrayOfFloat.length));
/*     */     
/* 134 */     return paramArrayOffloat;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/common/BogusColorSpace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */