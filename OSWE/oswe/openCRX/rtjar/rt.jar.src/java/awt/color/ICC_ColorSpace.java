/*     */ package java.awt.color;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import sun.java2d.cmm.CMSManager;
/*     */ import sun.java2d.cmm.ColorTransform;
/*     */ import sun.java2d.cmm.PCMM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ICC_ColorSpace
/*     */   extends ColorSpace
/*     */ {
/*     */   static final long serialVersionUID = 3455889114070431483L;
/*     */   private ICC_Profile thisProfile;
/*     */   private float[] minVal;
/*     */   private float[] maxVal;
/*     */   private float[] diffMinMax;
/*     */   private float[] invDiffMinMax;
/*     */   private boolean needScaleInit = true;
/*     */   private transient ColorTransform this2srgb;
/*     */   private transient ColorTransform srgb2this;
/*     */   private transient ColorTransform this2xyz;
/*     */   private transient ColorTransform xyz2this;
/*     */   
/*     */   public ICC_ColorSpace(ICC_Profile paramICC_Profile) {
/* 112 */     super(paramICC_Profile.getColorSpaceType(), paramICC_Profile.getNumComponents());
/*     */     
/* 114 */     int i = paramICC_Profile.getProfileClass();
/*     */ 
/*     */     
/* 117 */     if (i != 0 && i != 1 && i != 2 && i != 4 && i != 6 && i != 5)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 123 */       throw new IllegalArgumentException("Invalid profile type");
/*     */     }
/*     */     
/* 126 */     this.thisProfile = paramICC_Profile;
/* 127 */     setMinMax();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 136 */     paramObjectInputStream.defaultReadObject();
/* 137 */     if (this.thisProfile == null) {
/* 138 */       this.thisProfile = ICC_Profile.getInstance(1000);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ICC_Profile getProfile() {
/* 147 */     return this.thisProfile;
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
/*     */   public float[] toRGB(float[] paramArrayOffloat) {
/* 173 */     if (this.this2srgb == null) {
/* 174 */       ColorTransform[] arrayOfColorTransform = new ColorTransform[2];
/*     */       
/* 176 */       ICC_ColorSpace iCC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1000);
/* 177 */       PCMM pCMM = CMSManager.getModule();
/* 178 */       arrayOfColorTransform[0] = pCMM.createTransform(this.thisProfile, -1, 1);
/*     */       
/* 180 */       arrayOfColorTransform[1] = pCMM.createTransform(iCC_ColorSpace
/* 181 */           .getProfile(), -1, 2);
/* 182 */       this.this2srgb = pCMM.createTransform(arrayOfColorTransform);
/* 183 */       if (this.needScaleInit) {
/* 184 */         setComponentScaling();
/*     */       }
/*     */     } 
/*     */     
/* 188 */     int i = getNumComponents();
/* 189 */     short[] arrayOfShort = new short[i];
/* 190 */     for (byte b1 = 0; b1 < i; b1++) {
/* 191 */       arrayOfShort[b1] = (short)(int)((paramArrayOffloat[b1] - this.minVal[b1]) * this.invDiffMinMax[b1] + 0.5F);
/*     */     }
/*     */     
/* 194 */     arrayOfShort = this.this2srgb.colorConvert(arrayOfShort, (short[])null);
/* 195 */     float[] arrayOfFloat = new float[3];
/* 196 */     for (byte b2 = 0; b2 < 3; b2++) {
/* 197 */       arrayOfFloat[b2] = (arrayOfShort[b2] & 0xFFFF) / 65535.0F;
/*     */     }
/* 199 */     return arrayOfFloat;
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
/*     */   public float[] fromRGB(float[] paramArrayOffloat) {
/* 225 */     if (this.srgb2this == null) {
/* 226 */       ColorTransform[] arrayOfColorTransform = new ColorTransform[2];
/*     */       
/* 228 */       ICC_ColorSpace iCC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1000);
/* 229 */       PCMM pCMM = CMSManager.getModule();
/* 230 */       arrayOfColorTransform[0] = pCMM.createTransform(iCC_ColorSpace
/* 231 */           .getProfile(), -1, 1);
/* 232 */       arrayOfColorTransform[1] = pCMM.createTransform(this.thisProfile, -1, 2);
/*     */       
/* 234 */       this.srgb2this = pCMM.createTransform(arrayOfColorTransform);
/* 235 */       if (this.needScaleInit) {
/* 236 */         setComponentScaling();
/*     */       }
/*     */     } 
/*     */     
/* 240 */     short[] arrayOfShort = new short[3]; int i;
/* 241 */     for (i = 0; i < 3; i++) {
/* 242 */       arrayOfShort[i] = (short)(int)(paramArrayOffloat[i] * 65535.0F + 0.5F);
/*     */     }
/* 244 */     arrayOfShort = this.srgb2this.colorConvert(arrayOfShort, (short[])null);
/* 245 */     i = getNumComponents();
/* 246 */     float[] arrayOfFloat = new float[i];
/* 247 */     for (byte b = 0; b < i; b++) {
/* 248 */       arrayOfFloat[b] = (arrayOfShort[b] & 0xFFFF) / 65535.0F * this.diffMinMax[b] + this.minVal[b];
/*     */     }
/*     */     
/* 251 */     return arrayOfFloat;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] toCIEXYZ(float[] paramArrayOffloat) {
/* 360 */     if (this.this2xyz == null) {
/* 361 */       ColorTransform[] arrayOfColorTransform = new ColorTransform[2];
/*     */       
/* 363 */       ICC_ColorSpace iCC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1001);
/* 364 */       PCMM pCMM = CMSManager.getModule();
/*     */       try {
/* 366 */         arrayOfColorTransform[0] = pCMM.createTransform(this.thisProfile, 1, 1);
/*     */       
/*     */       }
/* 369 */       catch (CMMException cMMException) {
/* 370 */         arrayOfColorTransform[0] = pCMM.createTransform(this.thisProfile, -1, 1);
/*     */       } 
/*     */       
/* 373 */       arrayOfColorTransform[1] = pCMM.createTransform(iCC_ColorSpace
/* 374 */           .getProfile(), -1, 2);
/* 375 */       this.this2xyz = pCMM.createTransform(arrayOfColorTransform);
/* 376 */       if (this.needScaleInit) {
/* 377 */         setComponentScaling();
/*     */       }
/*     */     } 
/*     */     
/* 381 */     int i = getNumComponents();
/* 382 */     short[] arrayOfShort = new short[i];
/* 383 */     for (byte b1 = 0; b1 < i; b1++) {
/* 384 */       arrayOfShort[b1] = (short)(int)((paramArrayOffloat[b1] - this.minVal[b1]) * this.invDiffMinMax[b1] + 0.5F);
/*     */     }
/*     */     
/* 387 */     arrayOfShort = this.this2xyz.colorConvert(arrayOfShort, (short[])null);
/* 388 */     float f = 1.9999695F;
/*     */     
/* 390 */     float[] arrayOfFloat = new float[3];
/* 391 */     for (byte b2 = 0; b2 < 3; b2++) {
/* 392 */       arrayOfFloat[b2] = (arrayOfShort[b2] & 0xFFFF) / 65535.0F * f;
/*     */     }
/* 394 */     return arrayOfFloat;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] fromCIEXYZ(float[] paramArrayOffloat) {
/* 504 */     if (this.xyz2this == null) {
/* 505 */       ColorTransform[] arrayOfColorTransform = new ColorTransform[2];
/*     */       
/* 507 */       ICC_ColorSpace iCC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1001);
/* 508 */       PCMM pCMM = CMSManager.getModule();
/* 509 */       arrayOfColorTransform[0] = pCMM.createTransform(iCC_ColorSpace
/* 510 */           .getProfile(), -1, 1);
/*     */       try {
/* 512 */         arrayOfColorTransform[1] = pCMM.createTransform(this.thisProfile, 1, 2);
/*     */       
/*     */       }
/* 515 */       catch (CMMException cMMException) {
/* 516 */         arrayOfColorTransform[1] = CMSManager.getModule().createTransform(this.thisProfile, -1, 2);
/*     */       } 
/*     */       
/* 519 */       this.xyz2this = pCMM.createTransform(arrayOfColorTransform);
/* 520 */       if (this.needScaleInit) {
/* 521 */         setComponentScaling();
/*     */       }
/*     */     } 
/*     */     
/* 525 */     short[] arrayOfShort = new short[3];
/* 526 */     float f1 = 1.9999695F;
/* 527 */     float f2 = 65535.0F / f1;
/*     */     int i;
/* 529 */     for (i = 0; i < 3; i++) {
/* 530 */       arrayOfShort[i] = (short)(int)(paramArrayOffloat[i] * f2 + 0.5F);
/*     */     }
/* 532 */     arrayOfShort = this.xyz2this.colorConvert(arrayOfShort, (short[])null);
/* 533 */     i = getNumComponents();
/* 534 */     float[] arrayOfFloat = new float[i];
/* 535 */     for (byte b = 0; b < i; b++) {
/* 536 */       arrayOfFloat[b] = (arrayOfShort[b] & 0xFFFF) / 65535.0F * this.diffMinMax[b] + this.minVal[b];
/*     */     }
/*     */     
/* 539 */     return arrayOfFloat;
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
/*     */   public float getMinValue(int paramInt) {
/* 559 */     if (paramInt < 0 || paramInt > getNumComponents() - 1) {
/* 560 */       throw new IllegalArgumentException("Component index out of range: + component");
/*     */     }
/*     */     
/* 563 */     return this.minVal[paramInt];
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
/*     */   public float getMaxValue(int paramInt) {
/* 584 */     if (paramInt < 0 || paramInt > getNumComponents() - 1) {
/* 585 */       throw new IllegalArgumentException("Component index out of range: + component");
/*     */     }
/*     */     
/* 588 */     return this.maxVal[paramInt];
/*     */   }
/*     */   
/*     */   private void setMinMax() {
/* 592 */     int i = getNumComponents();
/* 593 */     int j = getType();
/* 594 */     this.minVal = new float[i];
/* 595 */     this.maxVal = new float[i];
/* 596 */     if (j == 1) {
/* 597 */       this.minVal[0] = 0.0F;
/* 598 */       this.maxVal[0] = 100.0F;
/* 599 */       this.minVal[1] = -128.0F;
/* 600 */       this.maxVal[1] = 127.0F;
/* 601 */       this.minVal[2] = -128.0F;
/* 602 */       this.maxVal[2] = 127.0F;
/* 603 */     } else if (j == 0) {
/* 604 */       this.minVal[2] = 0.0F; this.minVal[1] = 0.0F; this.minVal[0] = 0.0F;
/* 605 */       this.maxVal[2] = 1.9999695F; this.maxVal[1] = 1.9999695F; this.maxVal[0] = 1.9999695F;
/*     */     } else {
/* 607 */       for (byte b = 0; b < i; b++) {
/* 608 */         this.minVal[b] = 0.0F;
/* 609 */         this.maxVal[b] = 1.0F;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setComponentScaling() {
/* 615 */     int i = getNumComponents();
/* 616 */     this.diffMinMax = new float[i];
/* 617 */     this.invDiffMinMax = new float[i];
/* 618 */     for (byte b = 0; b < i; b++) {
/* 619 */       this.minVal[b] = getMinValue(b);
/* 620 */       this.maxVal[b] = getMaxValue(b);
/* 621 */       this.diffMinMax[b] = this.maxVal[b] - this.minVal[b];
/* 622 */       this.invDiffMinMax[b] = 65535.0F / this.diffMinMax[b];
/*     */     } 
/* 624 */     this.needScaleInit = false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/color/ICC_ColorSpace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */