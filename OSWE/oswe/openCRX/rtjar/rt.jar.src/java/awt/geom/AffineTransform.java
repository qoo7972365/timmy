/*      */ package java.awt.geom;
/*      */ 
/*      */ import java.awt.Shape;
/*      */ import java.beans.ConstructorProperties;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AffineTransform
/*      */   implements Cloneable, Serializable
/*      */ {
/*      */   private static final int TYPE_UNKNOWN = -1;
/*      */   public static final int TYPE_IDENTITY = 0;
/*      */   public static final int TYPE_TRANSLATION = 1;
/*      */   public static final int TYPE_UNIFORM_SCALE = 2;
/*      */   public static final int TYPE_GENERAL_SCALE = 4;
/*      */   public static final int TYPE_MASK_SCALE = 6;
/*      */   public static final int TYPE_FLIP = 64;
/*      */   public static final int TYPE_QUADRANT_ROTATION = 8;
/*      */   public static final int TYPE_GENERAL_ROTATION = 16;
/*      */   public static final int TYPE_MASK_ROTATION = 24;
/*      */   public static final int TYPE_GENERAL_TRANSFORM = 32;
/*      */   static final int APPLY_IDENTITY = 0;
/*      */   static final int APPLY_TRANSLATE = 1;
/*      */   static final int APPLY_SCALE = 2;
/*      */   static final int APPLY_SHEAR = 4;
/*      */   private static final int HI_SHIFT = 3;
/*      */   private static final int HI_IDENTITY = 0;
/*      */   private static final int HI_TRANSLATE = 8;
/*      */   private static final int HI_SCALE = 16;
/*      */   private static final int HI_SHEAR = 32;
/*      */   double m00;
/*      */   double m10;
/*      */   double m01;
/*      */   double m11;
/*      */   double m02;
/*      */   double m12;
/*      */   transient int state;
/*      */   private transient int type;
/*      */   
/*      */   private AffineTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt) {
/*  459 */     this.m00 = paramDouble1;
/*  460 */     this.m10 = paramDouble2;
/*  461 */     this.m01 = paramDouble3;
/*  462 */     this.m11 = paramDouble4;
/*  463 */     this.m02 = paramDouble5;
/*  464 */     this.m12 = paramDouble6;
/*  465 */     this.state = paramInt;
/*  466 */     this.type = -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AffineTransform() {
/*  475 */     this.m00 = this.m11 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AffineTransform(AffineTransform paramAffineTransform) {
/*  488 */     this.m00 = paramAffineTransform.m00;
/*  489 */     this.m10 = paramAffineTransform.m10;
/*  490 */     this.m01 = paramAffineTransform.m01;
/*  491 */     this.m11 = paramAffineTransform.m11;
/*  492 */     this.m02 = paramAffineTransform.m02;
/*  493 */     this.m12 = paramAffineTransform.m12;
/*  494 */     this.state = paramAffineTransform.state;
/*  495 */     this.type = paramAffineTransform.type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @ConstructorProperties({"scaleX", "shearY", "shearX", "scaleY", "translateX", "translateY"})
/*      */   public AffineTransform(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
/*  515 */     this.m00 = paramFloat1;
/*  516 */     this.m10 = paramFloat2;
/*  517 */     this.m01 = paramFloat3;
/*  518 */     this.m11 = paramFloat4;
/*  519 */     this.m02 = paramFloat5;
/*  520 */     this.m12 = paramFloat6;
/*  521 */     updateState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AffineTransform(float[] paramArrayOffloat) {
/*  538 */     this.m00 = paramArrayOffloat[0];
/*  539 */     this.m10 = paramArrayOffloat[1];
/*  540 */     this.m01 = paramArrayOffloat[2];
/*  541 */     this.m11 = paramArrayOffloat[3];
/*  542 */     if (paramArrayOffloat.length > 5) {
/*  543 */       this.m02 = paramArrayOffloat[4];
/*  544 */       this.m12 = paramArrayOffloat[5];
/*      */     } 
/*  546 */     updateState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AffineTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*  565 */     this.m00 = paramDouble1;
/*  566 */     this.m10 = paramDouble2;
/*  567 */     this.m01 = paramDouble3;
/*  568 */     this.m11 = paramDouble4;
/*  569 */     this.m02 = paramDouble5;
/*  570 */     this.m12 = paramDouble6;
/*  571 */     updateState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AffineTransform(double[] paramArrayOfdouble) {
/*  588 */     this.m00 = paramArrayOfdouble[0];
/*  589 */     this.m10 = paramArrayOfdouble[1];
/*  590 */     this.m01 = paramArrayOfdouble[2];
/*  591 */     this.m11 = paramArrayOfdouble[3];
/*  592 */     if (paramArrayOfdouble.length > 5) {
/*  593 */       this.m02 = paramArrayOfdouble[4];
/*  594 */       this.m12 = paramArrayOfdouble[5];
/*      */     } 
/*  596 */     updateState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AffineTransform getTranslateInstance(double paramDouble1, double paramDouble2) {
/*  616 */     AffineTransform affineTransform = new AffineTransform();
/*  617 */     affineTransform.setToTranslation(paramDouble1, paramDouble2);
/*  618 */     return affineTransform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AffineTransform getRotateInstance(double paramDouble) {
/*  640 */     AffineTransform affineTransform = new AffineTransform();
/*  641 */     affineTransform.setToRotation(paramDouble);
/*  642 */     return affineTransform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AffineTransform getRotateInstance(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  684 */     AffineTransform affineTransform = new AffineTransform();
/*  685 */     affineTransform.setToRotation(paramDouble1, paramDouble2, paramDouble3);
/*  686 */     return affineTransform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AffineTransform getRotateInstance(double paramDouble1, double paramDouble2) {
/*  710 */     AffineTransform affineTransform = new AffineTransform();
/*  711 */     affineTransform.setToRotation(paramDouble1, paramDouble2);
/*  712 */     return affineTransform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AffineTransform getRotateInstance(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  745 */     AffineTransform affineTransform = new AffineTransform();
/*  746 */     affineTransform.setToRotation(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*  747 */     return affineTransform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AffineTransform getQuadrantRotateInstance(int paramInt) {
/*  765 */     AffineTransform affineTransform = new AffineTransform();
/*  766 */     affineTransform.setToQuadrantRotation(paramInt);
/*  767 */     return affineTransform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AffineTransform getQuadrantRotateInstance(int paramInt, double paramDouble1, double paramDouble2) {
/*  793 */     AffineTransform affineTransform = new AffineTransform();
/*  794 */     affineTransform.setToQuadrantRotation(paramInt, paramDouble1, paramDouble2);
/*  795 */     return affineTransform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AffineTransform getScaleInstance(double paramDouble1, double paramDouble2) {
/*  815 */     AffineTransform affineTransform = new AffineTransform();
/*  816 */     affineTransform.setToScale(paramDouble1, paramDouble2);
/*  817 */     return affineTransform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AffineTransform getShearInstance(double paramDouble1, double paramDouble2) {
/*  837 */     AffineTransform affineTransform = new AffineTransform();
/*  838 */     affineTransform.setToShear(paramDouble1, paramDouble2);
/*  839 */     return affineTransform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getType() {
/*  867 */     if (this.type == -1) {
/*  868 */       calculateType();
/*      */     }
/*  870 */     return this.type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void calculateType() {
/*      */     boolean bool1, bool2;
/*      */     double d1, d2, d3, d4;
/*  880 */     int i = 0;
/*      */ 
/*      */     
/*  883 */     updateState();
/*  884 */     switch (this.state) {
/*      */       default:
/*  886 */         stateError();
/*      */       
/*      */       case 7:
/*  889 */         i = 1;
/*      */       
/*      */       case 6:
/*  892 */         if ((d1 = this.m00) * (d3 = this.m01) + (d4 = this.m10) * (d2 = this.m11) != 0.0D) {
/*      */           
/*  894 */           this.type = 32;
/*      */           return;
/*      */         } 
/*  897 */         bool1 = (d1 >= 0.0D) ? true : false;
/*  898 */         bool2 = (d2 >= 0.0D) ? true : false;
/*  899 */         if (bool1 == bool2) {
/*      */ 
/*      */           
/*  902 */           if (d1 != d2 || d3 != -d4) {
/*  903 */             i |= 0x14; break;
/*  904 */           }  if (d1 * d2 - d3 * d4 != 1.0D) {
/*  905 */             i |= 0x12; break;
/*      */           } 
/*  907 */           i |= 0x10;
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/*  912 */         if (d1 != -d2 || d3 != d4) {
/*  913 */           i |= 0x54;
/*      */           break;
/*      */         } 
/*  916 */         if (d1 * d2 - d3 * d4 != 1.0D) {
/*  917 */           i |= 0x52;
/*      */           
/*      */           break;
/*      */         } 
/*  921 */         i |= 0x50;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 5:
/*  926 */         i = 1;
/*      */       
/*      */       case 4:
/*  929 */         bool1 = ((d1 = this.m01) >= 0.0D) ? true : false;
/*  930 */         bool2 = ((d2 = this.m10) >= 0.0D) ? true : false;
/*  931 */         if (bool1 != bool2) {
/*      */           
/*  933 */           if (d1 != -d2) {
/*  934 */             i |= 0xC; break;
/*  935 */           }  if (d1 != 1.0D && d1 != -1.0D) {
/*  936 */             i |= 0xA; break;
/*      */           } 
/*  938 */           i |= 0x8;
/*      */           
/*      */           break;
/*      */         } 
/*  942 */         if (d1 == d2) {
/*  943 */           i |= 0x4A;
/*      */           
/*      */           break;
/*      */         } 
/*  947 */         i |= 0x4C;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  954 */         i = 1;
/*      */       
/*      */       case 2:
/*  957 */         bool1 = ((d1 = this.m00) >= 0.0D) ? true : false;
/*  958 */         bool2 = ((d2 = this.m11) >= 0.0D) ? true : false;
/*  959 */         if (bool1 == bool2) {
/*  960 */           if (bool1) {
/*      */ 
/*      */             
/*  963 */             if (d1 == d2) {
/*  964 */               i |= 0x2; break;
/*      */             } 
/*  966 */             i |= 0x4;
/*      */             
/*      */             break;
/*      */           } 
/*  970 */           if (d1 != d2) {
/*  971 */             i |= 0xC; break;
/*  972 */           }  if (d1 != -1.0D) {
/*  973 */             i |= 0xA; break;
/*      */           } 
/*  975 */           i |= 0x8;
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/*  980 */         if (d1 == -d2) {
/*  981 */           if (d1 == 1.0D || d1 == -1.0D) {
/*  982 */             i |= 0x40; break;
/*      */           } 
/*  984 */           i |= 0x42;
/*      */           break;
/*      */         } 
/*  987 */         i |= 0x44;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*  992 */         i = 1;
/*      */         break;
/*      */       case 0:
/*      */         break;
/*      */     } 
/*  997 */     this.type = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDeterminant() {
/* 1043 */     switch (this.state)
/*      */     { default:
/* 1045 */         stateError();
/*      */       
/*      */       case 6:
/*      */       case 7:
/* 1049 */         return this.m00 * this.m11 - this.m01 * this.m10;
/*      */       case 4:
/*      */       case 5:
/* 1052 */         return -(this.m01 * this.m10);
/*      */       case 2:
/*      */       case 3:
/* 1055 */         return this.m00 * this.m11;
/*      */       case 0:
/*      */       case 1:
/* 1058 */         break; }  return 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void updateState() {
/* 1085 */     if (this.m01 == 0.0D && this.m10 == 0.0D) {
/* 1086 */       if (this.m00 == 1.0D && this.m11 == 1.0D) {
/* 1087 */         if (this.m02 == 0.0D && this.m12 == 0.0D) {
/* 1088 */           this.state = 0;
/* 1089 */           this.type = 0;
/*      */         } else {
/* 1091 */           this.state = 1;
/* 1092 */           this.type = 1;
/*      */         }
/*      */       
/* 1095 */       } else if (this.m02 == 0.0D && this.m12 == 0.0D) {
/* 1096 */         this.state = 2;
/* 1097 */         this.type = -1;
/*      */       } else {
/* 1099 */         this.state = 3;
/* 1100 */         this.type = -1;
/*      */       }
/*      */     
/*      */     }
/* 1104 */     else if (this.m00 == 0.0D && this.m11 == 0.0D) {
/* 1105 */       if (this.m02 == 0.0D && this.m12 == 0.0D) {
/* 1106 */         this.state = 4;
/* 1107 */         this.type = -1;
/*      */       } else {
/* 1109 */         this.state = 5;
/* 1110 */         this.type = -1;
/*      */       }
/*      */     
/* 1113 */     } else if (this.m02 == 0.0D && this.m12 == 0.0D) {
/* 1114 */       this.state = 6;
/* 1115 */       this.type = -1;
/*      */     } else {
/* 1117 */       this.state = 7;
/* 1118 */       this.type = -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void stateError() {
/* 1129 */     throw new InternalError("missing case in transform state switch");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getMatrix(double[] paramArrayOfdouble) {
/* 1152 */     paramArrayOfdouble[0] = this.m00;
/* 1153 */     paramArrayOfdouble[1] = this.m10;
/* 1154 */     paramArrayOfdouble[2] = this.m01;
/* 1155 */     paramArrayOfdouble[3] = this.m11;
/* 1156 */     if (paramArrayOfdouble.length > 5) {
/* 1157 */       paramArrayOfdouble[4] = this.m02;
/* 1158 */       paramArrayOfdouble[5] = this.m12;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getScaleX() {
/* 1171 */     return this.m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getScaleY() {
/* 1183 */     return this.m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getShearX() {
/* 1195 */     return this.m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getShearY() {
/* 1207 */     return this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getTranslateX() {
/* 1219 */     return this.m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getTranslateY() {
/* 1231 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void translate(double paramDouble1, double paramDouble2) {
/* 1250 */     switch (this.state) {
/*      */       default:
/* 1252 */         stateError();
/*      */         return;
/*      */       
/*      */       case 7:
/* 1256 */         this.m02 = paramDouble1 * this.m00 + paramDouble2 * this.m01 + this.m02;
/* 1257 */         this.m12 = paramDouble1 * this.m10 + paramDouble2 * this.m11 + this.m12;
/* 1258 */         if (this.m02 == 0.0D && this.m12 == 0.0D) {
/* 1259 */           this.state = 6;
/* 1260 */           if (this.type != -1) {
/* 1261 */             this.type--;
/*      */           }
/*      */         } 
/*      */         return;
/*      */       case 6:
/* 1266 */         this.m02 = paramDouble1 * this.m00 + paramDouble2 * this.m01;
/* 1267 */         this.m12 = paramDouble1 * this.m10 + paramDouble2 * this.m11;
/* 1268 */         if (this.m02 != 0.0D || this.m12 != 0.0D) {
/* 1269 */           this.state = 7;
/* 1270 */           this.type |= 0x1;
/*      */         } 
/*      */         return;
/*      */       case 5:
/* 1274 */         this.m02 = paramDouble2 * this.m01 + this.m02;
/* 1275 */         this.m12 = paramDouble1 * this.m10 + this.m12;
/* 1276 */         if (this.m02 == 0.0D && this.m12 == 0.0D) {
/* 1277 */           this.state = 4;
/* 1278 */           if (this.type != -1) {
/* 1279 */             this.type--;
/*      */           }
/*      */         } 
/*      */         return;
/*      */       case 4:
/* 1284 */         this.m02 = paramDouble2 * this.m01;
/* 1285 */         this.m12 = paramDouble1 * this.m10;
/* 1286 */         if (this.m02 != 0.0D || this.m12 != 0.0D) {
/* 1287 */           this.state = 5;
/* 1288 */           this.type |= 0x1;
/*      */         } 
/*      */         return;
/*      */       case 3:
/* 1292 */         this.m02 = paramDouble1 * this.m00 + this.m02;
/* 1293 */         this.m12 = paramDouble2 * this.m11 + this.m12;
/* 1294 */         if (this.m02 == 0.0D && this.m12 == 0.0D) {
/* 1295 */           this.state = 2;
/* 1296 */           if (this.type != -1) {
/* 1297 */             this.type--;
/*      */           }
/*      */         } 
/*      */         return;
/*      */       case 2:
/* 1302 */         this.m02 = paramDouble1 * this.m00;
/* 1303 */         this.m12 = paramDouble2 * this.m11;
/* 1304 */         if (this.m02 != 0.0D || this.m12 != 0.0D) {
/* 1305 */           this.state = 3;
/* 1306 */           this.type |= 0x1;
/*      */         } 
/*      */         return;
/*      */       case 1:
/* 1310 */         this.m02 = paramDouble1 + this.m02;
/* 1311 */         this.m12 = paramDouble2 + this.m12;
/* 1312 */         if (this.m02 == 0.0D && this.m12 == 0.0D) {
/* 1313 */           this.state = 0;
/* 1314 */           this.type = 0;
/*      */         }  return;
/*      */       case 0:
/*      */         break;
/* 1318 */     }  this.m02 = paramDouble1;
/* 1319 */     this.m12 = paramDouble2;
/* 1320 */     if (paramDouble1 != 0.0D || paramDouble2 != 0.0D) {
/* 1321 */       this.state = 1;
/* 1322 */       this.type = 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1331 */   private static final int[] rot90conversion = new int[] { 4, 5, 4, 5, 2, 3, 6, 7 };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 1330973210523860834L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void rotate90() {
/* 1342 */     double d = this.m00;
/* 1343 */     this.m00 = this.m01;
/* 1344 */     this.m01 = -d;
/* 1345 */     d = this.m10;
/* 1346 */     this.m10 = this.m11;
/* 1347 */     this.m11 = -d;
/* 1348 */     int i = rot90conversion[this.state];
/* 1349 */     if ((i & 0x6) == 2 && this.m00 == 1.0D && this.m11 == 1.0D)
/*      */     {
/*      */       
/* 1352 */       i -= 2;
/*      */     }
/* 1354 */     this.state = i;
/* 1355 */     this.type = -1;
/*      */   }
/*      */   private final void rotate180() {
/* 1358 */     this.m00 = -this.m00;
/* 1359 */     this.m11 = -this.m11;
/* 1360 */     int i = this.state;
/* 1361 */     if ((i & 0x4) != 0) {
/*      */ 
/*      */       
/* 1364 */       this.m01 = -this.m01;
/* 1365 */       this.m10 = -this.m10;
/*      */ 
/*      */     
/*      */     }
/* 1369 */     else if (this.m00 == 1.0D && this.m11 == 1.0D) {
/* 1370 */       this.state = i & 0xFFFFFFFD;
/*      */     } else {
/* 1372 */       this.state = i | 0x2;
/*      */     } 
/*      */     
/* 1375 */     this.type = -1;
/*      */   }
/*      */   private final void rotate270() {
/* 1378 */     double d = this.m00;
/* 1379 */     this.m00 = -this.m01;
/* 1380 */     this.m01 = d;
/* 1381 */     d = this.m10;
/* 1382 */     this.m10 = -this.m11;
/* 1383 */     this.m11 = d;
/* 1384 */     int i = rot90conversion[this.state];
/* 1385 */     if ((i & 0x6) == 2 && this.m00 == 1.0D && this.m11 == 1.0D)
/*      */     {
/*      */       
/* 1388 */       i -= 2;
/*      */     }
/* 1390 */     this.state = i;
/* 1391 */     this.type = -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rotate(double paramDouble) {
/* 1412 */     double d = Math.sin(paramDouble);
/* 1413 */     if (d == 1.0D) {
/* 1414 */       rotate90();
/* 1415 */     } else if (d == -1.0D) {
/* 1416 */       rotate270();
/*      */     } else {
/* 1418 */       double d1 = Math.cos(paramDouble);
/* 1419 */       if (d1 == -1.0D) {
/* 1420 */         rotate180();
/* 1421 */       } else if (d1 != 1.0D) {
/*      */         
/* 1423 */         double d2 = this.m00;
/* 1424 */         double d3 = this.m01;
/* 1425 */         this.m00 = d1 * d2 + d * d3;
/* 1426 */         this.m01 = -d * d2 + d1 * d3;
/* 1427 */         d2 = this.m10;
/* 1428 */         d3 = this.m11;
/* 1429 */         this.m10 = d1 * d2 + d * d3;
/* 1430 */         this.m11 = -d * d2 + d1 * d3;
/* 1431 */         updateState();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rotate(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 1464 */     translate(paramDouble2, paramDouble3);
/* 1465 */     rotate(paramDouble1);
/* 1466 */     translate(-paramDouble2, -paramDouble3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rotate(double paramDouble1, double paramDouble2) {
/* 1488 */     if (paramDouble2 == 0.0D) {
/* 1489 */       if (paramDouble1 < 0.0D) {
/* 1490 */         rotate180();
/*      */       
/*      */       }
/*      */     }
/* 1494 */     else if (paramDouble1 == 0.0D) {
/* 1495 */       if (paramDouble2 > 0.0D) {
/* 1496 */         rotate90();
/*      */       } else {
/* 1498 */         rotate270();
/*      */       } 
/*      */     } else {
/* 1501 */       double d1 = Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2);
/* 1502 */       double d2 = paramDouble2 / d1;
/* 1503 */       double d3 = paramDouble1 / d1;
/*      */       
/* 1505 */       double d4 = this.m00;
/* 1506 */       double d5 = this.m01;
/* 1507 */       this.m00 = d3 * d4 + d2 * d5;
/* 1508 */       this.m01 = -d2 * d4 + d3 * d5;
/* 1509 */       d4 = this.m10;
/* 1510 */       d5 = this.m11;
/* 1511 */       this.m10 = d3 * d4 + d2 * d5;
/* 1512 */       this.m11 = -d2 * d4 + d3 * d5;
/* 1513 */       updateState();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rotate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1543 */     translate(paramDouble3, paramDouble4);
/* 1544 */     rotate(paramDouble1, paramDouble2);
/* 1545 */     translate(-paramDouble3, -paramDouble4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void quadrantRotate(int paramInt) {
/* 1561 */     switch (paramInt & 0x3) {
/*      */ 
/*      */       
/*      */       case 1:
/* 1565 */         rotate90();
/*      */         break;
/*      */       case 2:
/* 1568 */         rotate180();
/*      */         break;
/*      */       case 3:
/* 1571 */         rotate270();
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void quadrantRotate(int paramInt, double paramDouble1, double paramDouble2) {
/* 1595 */     switch (paramInt & 0x3) {
/*      */       case 0:
/*      */         return;
/*      */       case 1:
/* 1599 */         this.m02 += paramDouble1 * (this.m00 - this.m01) + paramDouble2 * (this.m01 + this.m00);
/* 1600 */         this.m12 += paramDouble1 * (this.m10 - this.m11) + paramDouble2 * (this.m11 + this.m10);
/* 1601 */         rotate90();
/*      */         break;
/*      */       case 2:
/* 1604 */         this.m02 += paramDouble1 * (this.m00 + this.m00) + paramDouble2 * (this.m01 + this.m01);
/* 1605 */         this.m12 += paramDouble1 * (this.m10 + this.m10) + paramDouble2 * (this.m11 + this.m11);
/* 1606 */         rotate180();
/*      */         break;
/*      */       case 3:
/* 1609 */         this.m02 += paramDouble1 * (this.m00 + this.m01) + paramDouble2 * (this.m01 - this.m00);
/* 1610 */         this.m12 += paramDouble1 * (this.m10 + this.m11) + paramDouble2 * (this.m11 - this.m10);
/* 1611 */         rotate270();
/*      */         break;
/*      */     } 
/* 1614 */     if (this.m02 == 0.0D && this.m12 == 0.0D) {
/* 1615 */       this.state &= 0xFFFFFFFE;
/*      */     } else {
/* 1617 */       this.state |= 0x1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void scale(double paramDouble1, double paramDouble2) {
/* 1638 */     int i = this.state;
/* 1639 */     switch (i) {
/*      */       default:
/* 1641 */         stateError();
/*      */       
/*      */       case 6:
/*      */       case 7:
/* 1645 */         this.m00 *= paramDouble1;
/* 1646 */         this.m11 *= paramDouble2;
/*      */       
/*      */       case 4:
/*      */       case 5:
/* 1650 */         this.m01 *= paramDouble2;
/* 1651 */         this.m10 *= paramDouble1;
/* 1652 */         if (this.m01 == 0.0D && this.m10 == 0.0D) {
/* 1653 */           i &= 0x1;
/* 1654 */           if (this.m00 == 1.0D && this.m11 == 1.0D) {
/* 1655 */             this.type = (i == 0) ? 0 : 1;
/*      */           }
/*      */           else {
/*      */             
/* 1659 */             i |= 0x2;
/* 1660 */             this.type = -1;
/*      */           } 
/* 1662 */           this.state = i;
/*      */         } 
/*      */         return;
/*      */       case 2:
/*      */       case 3:
/* 1667 */         this.m00 *= paramDouble1;
/* 1668 */         this.m11 *= paramDouble2;
/* 1669 */         if (this.m00 == 1.0D && this.m11 == 1.0D) {
/* 1670 */           this.state = i &= 0x1;
/* 1671 */           this.type = (i == 0) ? 0 : 1;
/*      */         }
/*      */         else {
/*      */           
/* 1675 */           this.type = -1;
/*      */         }  return;
/*      */       case 0:
/*      */       case 1:
/*      */         break;
/* 1680 */     }  this.m00 = paramDouble1;
/* 1681 */     this.m11 = paramDouble2;
/* 1682 */     if (paramDouble1 != 1.0D || paramDouble2 != 1.0D) {
/* 1683 */       this.state = i | 0x2;
/* 1684 */       this.type = -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shear(double paramDouble1, double paramDouble2) {
/*      */     double d1, d2;
/* 1706 */     int i = this.state;
/* 1707 */     switch (i) {
/*      */       default:
/* 1709 */         stateError();
/*      */         return;
/*      */ 
/*      */       
/*      */       case 6:
/*      */       case 7:
/* 1715 */         d1 = this.m00;
/* 1716 */         d2 = this.m01;
/* 1717 */         this.m00 = d1 + d2 * paramDouble2;
/* 1718 */         this.m01 = d1 * paramDouble1 + d2;
/*      */         
/* 1720 */         d1 = this.m10;
/* 1721 */         d2 = this.m11;
/* 1722 */         this.m10 = d1 + d2 * paramDouble2;
/* 1723 */         this.m11 = d1 * paramDouble1 + d2;
/* 1724 */         updateState();
/*      */         return;
/*      */       case 4:
/*      */       case 5:
/* 1728 */         this.m00 = this.m01 * paramDouble2;
/* 1729 */         this.m11 = this.m10 * paramDouble1;
/* 1730 */         if (this.m00 != 0.0D || this.m11 != 0.0D) {
/* 1731 */           this.state = i | 0x2;
/*      */         }
/* 1733 */         this.type = -1;
/*      */         return;
/*      */       case 2:
/*      */       case 3:
/* 1737 */         this.m01 = this.m00 * paramDouble1;
/* 1738 */         this.m10 = this.m11 * paramDouble2;
/* 1739 */         if (this.m01 != 0.0D || this.m10 != 0.0D) {
/* 1740 */           this.state = i | 0x4;
/*      */         }
/* 1742 */         this.type = -1; return;
/*      */       case 0:
/*      */       case 1:
/*      */         break;
/* 1746 */     }  this.m01 = paramDouble1;
/* 1747 */     this.m10 = paramDouble2;
/* 1748 */     if (this.m01 != 0.0D || this.m10 != 0.0D) {
/* 1749 */       this.state = i | 0x2 | 0x4;
/* 1750 */       this.type = -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToIdentity() {
/* 1761 */     this.m00 = this.m11 = 1.0D;
/* 1762 */     this.m10 = this.m01 = this.m02 = this.m12 = 0.0D;
/* 1763 */     this.state = 0;
/* 1764 */     this.type = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToTranslation(double paramDouble1, double paramDouble2) {
/* 1782 */     this.m00 = 1.0D;
/* 1783 */     this.m10 = 0.0D;
/* 1784 */     this.m01 = 0.0D;
/* 1785 */     this.m11 = 1.0D;
/* 1786 */     this.m02 = paramDouble1;
/* 1787 */     this.m12 = paramDouble2;
/* 1788 */     if (paramDouble1 != 0.0D || paramDouble2 != 0.0D) {
/* 1789 */       this.state = 1;
/* 1790 */       this.type = 1;
/*      */     } else {
/* 1792 */       this.state = 0;
/* 1793 */       this.type = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToRotation(double paramDouble) {
/* 1814 */     double d2, d1 = Math.sin(paramDouble);
/*      */     
/* 1816 */     if (d1 == 1.0D || d1 == -1.0D) {
/* 1817 */       d2 = 0.0D;
/* 1818 */       this.state = 4;
/* 1819 */       this.type = 8;
/*      */     } else {
/* 1821 */       d2 = Math.cos(paramDouble);
/* 1822 */       if (d2 == -1.0D) {
/* 1823 */         d1 = 0.0D;
/* 1824 */         this.state = 2;
/* 1825 */         this.type = 8;
/* 1826 */       } else if (d2 == 1.0D) {
/* 1827 */         d1 = 0.0D;
/* 1828 */         this.state = 0;
/* 1829 */         this.type = 0;
/*      */       } else {
/* 1831 */         this.state = 6;
/* 1832 */         this.type = 16;
/*      */       } 
/*      */     } 
/* 1835 */     this.m00 = d2;
/* 1836 */     this.m10 = d1;
/* 1837 */     this.m01 = -d1;
/* 1838 */     this.m11 = d2;
/* 1839 */     this.m02 = 0.0D;
/* 1840 */     this.m12 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToRotation(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 1875 */     setToRotation(paramDouble1);
/* 1876 */     double d1 = this.m10;
/* 1877 */     double d2 = 1.0D - this.m00;
/* 1878 */     this.m02 = paramDouble2 * d2 + paramDouble3 * d1;
/* 1879 */     this.m12 = paramDouble3 * d2 - paramDouble2 * d1;
/* 1880 */     if (this.m02 != 0.0D || this.m12 != 0.0D) {
/* 1881 */       this.state |= 0x1;
/* 1882 */       this.type |= 0x1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToRotation(double paramDouble1, double paramDouble2) {
/*      */     double d1, d2;
/* 1906 */     if (paramDouble2 == 0.0D) {
/* 1907 */       d1 = 0.0D;
/* 1908 */       if (paramDouble1 < 0.0D) {
/* 1909 */         d2 = -1.0D;
/* 1910 */         this.state = 2;
/* 1911 */         this.type = 8;
/*      */       } else {
/* 1913 */         d2 = 1.0D;
/* 1914 */         this.state = 0;
/* 1915 */         this.type = 0;
/*      */       } 
/* 1917 */     } else if (paramDouble1 == 0.0D) {
/* 1918 */       d2 = 0.0D;
/* 1919 */       d1 = (paramDouble2 > 0.0D) ? 1.0D : -1.0D;
/* 1920 */       this.state = 4;
/* 1921 */       this.type = 8;
/*      */     } else {
/* 1923 */       double d = Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2);
/* 1924 */       d2 = paramDouble1 / d;
/* 1925 */       d1 = paramDouble2 / d;
/* 1926 */       this.state = 6;
/* 1927 */       this.type = 16;
/*      */     } 
/* 1929 */     this.m00 = d2;
/* 1930 */     this.m10 = d1;
/* 1931 */     this.m01 = -d1;
/* 1932 */     this.m11 = d2;
/* 1933 */     this.m02 = 0.0D;
/* 1934 */     this.m12 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToRotation(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1962 */     setToRotation(paramDouble1, paramDouble2);
/* 1963 */     double d1 = this.m10;
/* 1964 */     double d2 = 1.0D - this.m00;
/* 1965 */     this.m02 = paramDouble3 * d2 + paramDouble4 * d1;
/* 1966 */     this.m12 = paramDouble4 * d2 - paramDouble3 * d1;
/* 1967 */     if (this.m02 != 0.0D || this.m12 != 0.0D) {
/* 1968 */       this.state |= 0x1;
/* 1969 */       this.type |= 0x1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToQuadrantRotation(int paramInt) {
/* 1986 */     switch (paramInt & 0x3) {
/*      */       case 0:
/* 1988 */         this.m00 = 1.0D;
/* 1989 */         this.m10 = 0.0D;
/* 1990 */         this.m01 = 0.0D;
/* 1991 */         this.m11 = 1.0D;
/* 1992 */         this.m02 = 0.0D;
/* 1993 */         this.m12 = 0.0D;
/* 1994 */         this.state = 0;
/* 1995 */         this.type = 0;
/*      */         break;
/*      */       case 1:
/* 1998 */         this.m00 = 0.0D;
/* 1999 */         this.m10 = 1.0D;
/* 2000 */         this.m01 = -1.0D;
/* 2001 */         this.m11 = 0.0D;
/* 2002 */         this.m02 = 0.0D;
/* 2003 */         this.m12 = 0.0D;
/* 2004 */         this.state = 4;
/* 2005 */         this.type = 8;
/*      */         break;
/*      */       case 2:
/* 2008 */         this.m00 = -1.0D;
/* 2009 */         this.m10 = 0.0D;
/* 2010 */         this.m01 = 0.0D;
/* 2011 */         this.m11 = -1.0D;
/* 2012 */         this.m02 = 0.0D;
/* 2013 */         this.m12 = 0.0D;
/* 2014 */         this.state = 2;
/* 2015 */         this.type = 8;
/*      */         break;
/*      */       case 3:
/* 2018 */         this.m00 = 0.0D;
/* 2019 */         this.m10 = -1.0D;
/* 2020 */         this.m01 = 1.0D;
/* 2021 */         this.m11 = 0.0D;
/* 2022 */         this.m02 = 0.0D;
/* 2023 */         this.m12 = 0.0D;
/* 2024 */         this.state = 4;
/* 2025 */         this.type = 8;
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToQuadrantRotation(int paramInt, double paramDouble1, double paramDouble2) {
/* 2049 */     switch (paramInt & 0x3) {
/*      */       case 0:
/* 2051 */         this.m00 = 1.0D;
/* 2052 */         this.m10 = 0.0D;
/* 2053 */         this.m01 = 0.0D;
/* 2054 */         this.m11 = 1.0D;
/* 2055 */         this.m02 = 0.0D;
/* 2056 */         this.m12 = 0.0D;
/* 2057 */         this.state = 0;
/* 2058 */         this.type = 0;
/*      */         break;
/*      */       case 1:
/* 2061 */         this.m00 = 0.0D;
/* 2062 */         this.m10 = 1.0D;
/* 2063 */         this.m01 = -1.0D;
/* 2064 */         this.m11 = 0.0D;
/* 2065 */         this.m02 = paramDouble1 + paramDouble2;
/* 2066 */         this.m12 = paramDouble2 - paramDouble1;
/* 2067 */         if (this.m02 == 0.0D && this.m12 == 0.0D) {
/* 2068 */           this.state = 4;
/* 2069 */           this.type = 8; break;
/*      */         } 
/* 2071 */         this.state = 5;
/* 2072 */         this.type = 9;
/*      */         break;
/*      */       
/*      */       case 2:
/* 2076 */         this.m00 = -1.0D;
/* 2077 */         this.m10 = 0.0D;
/* 2078 */         this.m01 = 0.0D;
/* 2079 */         this.m11 = -1.0D;
/* 2080 */         this.m02 = paramDouble1 + paramDouble1;
/* 2081 */         this.m12 = paramDouble2 + paramDouble2;
/* 2082 */         if (this.m02 == 0.0D && this.m12 == 0.0D) {
/* 2083 */           this.state = 2;
/* 2084 */           this.type = 8; break;
/*      */         } 
/* 2086 */         this.state = 3;
/* 2087 */         this.type = 9;
/*      */         break;
/*      */       
/*      */       case 3:
/* 2091 */         this.m00 = 0.0D;
/* 2092 */         this.m10 = -1.0D;
/* 2093 */         this.m01 = 1.0D;
/* 2094 */         this.m11 = 0.0D;
/* 2095 */         this.m02 = paramDouble1 - paramDouble2;
/* 2096 */         this.m12 = paramDouble2 + paramDouble1;
/* 2097 */         if (this.m02 == 0.0D && this.m12 == 0.0D) {
/* 2098 */           this.state = 4;
/* 2099 */           this.type = 8; break;
/*      */         } 
/* 2101 */         this.state = 5;
/* 2102 */         this.type = 9;
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToScale(double paramDouble1, double paramDouble2) {
/* 2123 */     this.m00 = paramDouble1;
/* 2124 */     this.m10 = 0.0D;
/* 2125 */     this.m01 = 0.0D;
/* 2126 */     this.m11 = paramDouble2;
/* 2127 */     this.m02 = 0.0D;
/* 2128 */     this.m12 = 0.0D;
/* 2129 */     if (paramDouble1 != 1.0D || paramDouble2 != 1.0D) {
/* 2130 */       this.state = 2;
/* 2131 */       this.type = -1;
/*      */     } else {
/* 2133 */       this.state = 0;
/* 2134 */       this.type = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToShear(double paramDouble1, double paramDouble2) {
/* 2153 */     this.m00 = 1.0D;
/* 2154 */     this.m01 = paramDouble1;
/* 2155 */     this.m10 = paramDouble2;
/* 2156 */     this.m11 = 1.0D;
/* 2157 */     this.m02 = 0.0D;
/* 2158 */     this.m12 = 0.0D;
/* 2159 */     if (paramDouble1 != 0.0D || paramDouble2 != 0.0D) {
/* 2160 */       this.state = 6;
/* 2161 */       this.type = -1;
/*      */     } else {
/* 2163 */       this.state = 0;
/* 2164 */       this.type = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransform(AffineTransform paramAffineTransform) {
/* 2176 */     this.m00 = paramAffineTransform.m00;
/* 2177 */     this.m10 = paramAffineTransform.m10;
/* 2178 */     this.m01 = paramAffineTransform.m01;
/* 2179 */     this.m11 = paramAffineTransform.m11;
/* 2180 */     this.m02 = paramAffineTransform.m02;
/* 2181 */     this.m12 = paramAffineTransform.m12;
/* 2182 */     this.state = paramAffineTransform.state;
/* 2183 */     this.type = paramAffineTransform.type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/* 2201 */     this.m00 = paramDouble1;
/* 2202 */     this.m10 = paramDouble2;
/* 2203 */     this.m01 = paramDouble3;
/* 2204 */     this.m11 = paramDouble4;
/* 2205 */     this.m02 = paramDouble5;
/* 2206 */     this.m12 = paramDouble6;
/* 2207 */     updateState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void concatenate(AffineTransform paramAffineTransform) {
/*      */     double d1, d2;
/* 2236 */     int i = this.state;
/* 2237 */     int j = paramAffineTransform.state;
/* 2238 */     switch (j << 3 | i) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */         return;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 56:
/* 2253 */         this.m01 = paramAffineTransform.m01;
/* 2254 */         this.m10 = paramAffineTransform.m10;
/*      */       
/*      */       case 24:
/* 2257 */         this.m00 = paramAffineTransform.m00;
/* 2258 */         this.m11 = paramAffineTransform.m11;
/*      */       
/*      */       case 8:
/* 2261 */         this.m02 = paramAffineTransform.m02;
/* 2262 */         this.m12 = paramAffineTransform.m12;
/* 2263 */         this.state = j;
/* 2264 */         this.type = paramAffineTransform.type;
/*      */         return;
/*      */       case 48:
/* 2267 */         this.m01 = paramAffineTransform.m01;
/* 2268 */         this.m10 = paramAffineTransform.m10;
/*      */       
/*      */       case 16:
/* 2271 */         this.m00 = paramAffineTransform.m00;
/* 2272 */         this.m11 = paramAffineTransform.m11;
/* 2273 */         this.state = j;
/* 2274 */         this.type = paramAffineTransform.type;
/*      */         return;
/*      */       case 40:
/* 2277 */         this.m02 = paramAffineTransform.m02;
/* 2278 */         this.m12 = paramAffineTransform.m12;
/*      */       
/*      */       case 32:
/* 2281 */         this.m01 = paramAffineTransform.m01;
/* 2282 */         this.m10 = paramAffineTransform.m10;
/* 2283 */         this.m00 = this.m11 = 0.0D;
/* 2284 */         this.state = j;
/* 2285 */         this.type = paramAffineTransform.type;
/*      */         return;
/*      */ 
/*      */       
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/* 2296 */         translate(paramAffineTransform.m02, paramAffineTransform.m12);
/*      */         return;
/*      */ 
/*      */       
/*      */       case 17:
/*      */       case 18:
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/* 2307 */         scale(paramAffineTransform.m00, paramAffineTransform.m11);
/*      */         return;
/*      */ 
/*      */       
/*      */       case 38:
/*      */       case 39:
/* 2313 */         d4 = paramAffineTransform.m01; d5 = paramAffineTransform.m10;
/* 2314 */         d1 = this.m00;
/* 2315 */         this.m00 = this.m01 * d5;
/* 2316 */         this.m01 = d1 * d4;
/* 2317 */         d1 = this.m10;
/* 2318 */         this.m10 = this.m11 * d5;
/* 2319 */         this.m11 = d1 * d4;
/* 2320 */         this.type = -1;
/*      */         return;
/*      */       case 36:
/*      */       case 37:
/* 2324 */         this.m00 = this.m01 * paramAffineTransform.m10;
/* 2325 */         this.m01 = 0.0D;
/* 2326 */         this.m11 = this.m10 * paramAffineTransform.m01;
/* 2327 */         this.m10 = 0.0D;
/* 2328 */         this.state = i ^ 0x6;
/* 2329 */         this.type = -1;
/*      */         return;
/*      */       case 34:
/*      */       case 35:
/* 2333 */         this.m01 = this.m00 * paramAffineTransform.m01;
/* 2334 */         this.m00 = 0.0D;
/* 2335 */         this.m10 = this.m11 * paramAffineTransform.m10;
/* 2336 */         this.m11 = 0.0D;
/* 2337 */         this.state = i ^ 0x6;
/* 2338 */         this.type = -1;
/*      */         return;
/*      */       case 33:
/* 2341 */         this.m00 = 0.0D;
/* 2342 */         this.m01 = paramAffineTransform.m01;
/* 2343 */         this.m10 = paramAffineTransform.m10;
/* 2344 */         this.m11 = 0.0D;
/* 2345 */         this.state = 5;
/* 2346 */         this.type = -1;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/* 2351 */     double d3 = paramAffineTransform.m00, d4 = paramAffineTransform.m01, d7 = paramAffineTransform.m02;
/* 2352 */     double d5 = paramAffineTransform.m10, d6 = paramAffineTransform.m11, d8 = paramAffineTransform.m12;
/* 2353 */     switch (i) {
/*      */       default:
/* 2355 */         stateError();
/*      */       
/*      */       case 6:
/* 2358 */         this.state = i | j;
/*      */       
/*      */       case 7:
/* 2361 */         d1 = this.m00;
/* 2362 */         d2 = this.m01;
/* 2363 */         this.m00 = d3 * d1 + d5 * d2;
/* 2364 */         this.m01 = d4 * d1 + d6 * d2;
/* 2365 */         this.m02 += d7 * d1 + d8 * d2;
/*      */         
/* 2367 */         d1 = this.m10;
/* 2368 */         d2 = this.m11;
/* 2369 */         this.m10 = d3 * d1 + d5 * d2;
/* 2370 */         this.m11 = d4 * d1 + d6 * d2;
/* 2371 */         this.m12 += d7 * d1 + d8 * d2;
/* 2372 */         this.type = -1;
/*      */         return;
/*      */       
/*      */       case 4:
/*      */       case 5:
/* 2377 */         d1 = this.m01;
/* 2378 */         this.m00 = d5 * d1;
/* 2379 */         this.m01 = d6 * d1;
/* 2380 */         this.m02 += d8 * d1;
/*      */         
/* 2382 */         d1 = this.m10;
/* 2383 */         this.m10 = d3 * d1;
/* 2384 */         this.m11 = d4 * d1;
/* 2385 */         this.m12 += d7 * d1;
/*      */         break;
/*      */       
/*      */       case 2:
/*      */       case 3:
/* 2390 */         d1 = this.m00;
/* 2391 */         this.m00 = d3 * d1;
/* 2392 */         this.m01 = d4 * d1;
/* 2393 */         this.m02 += d7 * d1;
/*      */         
/* 2395 */         d1 = this.m11;
/* 2396 */         this.m10 = d5 * d1;
/* 2397 */         this.m11 = d6 * d1;
/* 2398 */         this.m12 += d8 * d1;
/*      */         break;
/*      */       
/*      */       case 1:
/* 2402 */         this.m00 = d3;
/* 2403 */         this.m01 = d4;
/* 2404 */         this.m02 += d7;
/*      */         
/* 2406 */         this.m10 = d5;
/* 2407 */         this.m11 = d6;
/* 2408 */         this.m12 += d8;
/* 2409 */         this.state = j | 0x1;
/* 2410 */         this.type = -1;
/*      */         return;
/*      */     } 
/* 2413 */     updateState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preConcatenate(AffineTransform paramAffineTransform) {
/*      */     double d1, d2;
/* 2445 */     int i = this.state;
/* 2446 */     int j = paramAffineTransform.state;
/* 2447 */     switch (j << 3 | i) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */         return;
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/*      */       case 10:
/*      */       case 12:
/*      */       case 14:
/* 2464 */         this.m02 = paramAffineTransform.m02;
/* 2465 */         this.m12 = paramAffineTransform.m12;
/* 2466 */         this.state = i | 0x1;
/* 2467 */         this.type |= 0x1;
/*      */         return;
/*      */ 
/*      */       
/*      */       case 9:
/*      */       case 11:
/*      */       case 13:
/*      */       case 15:
/* 2475 */         this.m02 += paramAffineTransform.m02;
/* 2476 */         this.m12 += paramAffineTransform.m12;
/*      */         return;
/*      */ 
/*      */       
/*      */       case 16:
/*      */       case 17:
/* 2482 */         this.state = i | 0x2;
/*      */ 
/*      */       
/*      */       case 18:
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/* 2491 */         d3 = paramAffineTransform.m00;
/* 2492 */         d6 = paramAffineTransform.m11;
/* 2493 */         if ((i & 0x4) != 0) {
/* 2494 */           this.m01 *= d3;
/* 2495 */           this.m10 *= d6;
/* 2496 */           if ((i & 0x2) != 0) {
/* 2497 */             this.m00 *= d3;
/* 2498 */             this.m11 *= d6;
/*      */           } 
/*      */         } else {
/* 2501 */           this.m00 *= d3;
/* 2502 */           this.m11 *= d6;
/*      */         } 
/* 2504 */         if ((i & 0x1) != 0) {
/* 2505 */           this.m02 *= d3;
/* 2506 */           this.m12 *= d6;
/*      */         } 
/* 2508 */         this.type = -1;
/*      */         return;
/*      */       case 36:
/*      */       case 37:
/* 2512 */         i |= 0x2;
/*      */       
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/* 2518 */         this.state = i ^ 0x4;
/*      */ 
/*      */       
/*      */       case 38:
/*      */       case 39:
/* 2523 */         d4 = paramAffineTransform.m01;
/* 2524 */         d5 = paramAffineTransform.m10;
/*      */         
/* 2526 */         d1 = this.m00;
/* 2527 */         this.m00 = this.m10 * d4;
/* 2528 */         this.m10 = d1 * d5;
/*      */         
/* 2530 */         d1 = this.m01;
/* 2531 */         this.m01 = this.m11 * d4;
/* 2532 */         this.m11 = d1 * d5;
/*      */         
/* 2534 */         d1 = this.m02;
/* 2535 */         this.m02 = this.m12 * d4;
/* 2536 */         this.m12 = d1 * d5;
/* 2537 */         this.type = -1;
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/* 2542 */     double d3 = paramAffineTransform.m00, d4 = paramAffineTransform.m01, d7 = paramAffineTransform.m02;
/* 2543 */     double d5 = paramAffineTransform.m10, d6 = paramAffineTransform.m11, d8 = paramAffineTransform.m12;
/* 2544 */     switch (i) {
/*      */       default:
/* 2546 */         stateError();
/*      */       
/*      */       case 7:
/* 2549 */         d1 = this.m02;
/* 2550 */         d2 = this.m12;
/* 2551 */         d7 += d1 * d3 + d2 * d4;
/* 2552 */         d8 += d1 * d5 + d2 * d6;
/*      */ 
/*      */       
/*      */       case 6:
/* 2556 */         this.m02 = d7;
/* 2557 */         this.m12 = d8;
/*      */         
/* 2559 */         d1 = this.m00;
/* 2560 */         d2 = this.m10;
/* 2561 */         this.m00 = d1 * d3 + d2 * d4;
/* 2562 */         this.m10 = d1 * d5 + d2 * d6;
/*      */         
/* 2564 */         d1 = this.m01;
/* 2565 */         d2 = this.m11;
/* 2566 */         this.m01 = d1 * d3 + d2 * d4;
/* 2567 */         this.m11 = d1 * d5 + d2 * d6;
/*      */         break;
/*      */       
/*      */       case 5:
/* 2571 */         d1 = this.m02;
/* 2572 */         d2 = this.m12;
/* 2573 */         d7 += d1 * d3 + d2 * d4;
/* 2574 */         d8 += d1 * d5 + d2 * d6;
/*      */ 
/*      */       
/*      */       case 4:
/* 2578 */         this.m02 = d7;
/* 2579 */         this.m12 = d8;
/*      */         
/* 2581 */         d1 = this.m10;
/* 2582 */         this.m00 = d1 * d4;
/* 2583 */         this.m10 = d1 * d6;
/*      */         
/* 2585 */         d1 = this.m01;
/* 2586 */         this.m01 = d1 * d3;
/* 2587 */         this.m11 = d1 * d5;
/*      */         break;
/*      */       
/*      */       case 3:
/* 2591 */         d1 = this.m02;
/* 2592 */         d2 = this.m12;
/* 2593 */         d7 += d1 * d3 + d2 * d4;
/* 2594 */         d8 += d1 * d5 + d2 * d6;
/*      */ 
/*      */       
/*      */       case 2:
/* 2598 */         this.m02 = d7;
/* 2599 */         this.m12 = d8;
/*      */         
/* 2601 */         d1 = this.m00;
/* 2602 */         this.m00 = d1 * d3;
/* 2603 */         this.m10 = d1 * d5;
/*      */         
/* 2605 */         d1 = this.m11;
/* 2606 */         this.m01 = d1 * d4;
/* 2607 */         this.m11 = d1 * d6;
/*      */         break;
/*      */       
/*      */       case 1:
/* 2611 */         d1 = this.m02;
/* 2612 */         d2 = this.m12;
/* 2613 */         d7 += d1 * d3 + d2 * d4;
/* 2614 */         d8 += d1 * d5 + d2 * d6;
/*      */ 
/*      */       
/*      */       case 0:
/* 2618 */         this.m02 = d7;
/* 2619 */         this.m12 = d8;
/*      */         
/* 2621 */         this.m00 = d3;
/* 2622 */         this.m10 = d5;
/*      */         
/* 2624 */         this.m01 = d4;
/* 2625 */         this.m11 = d6;
/*      */         
/* 2627 */         this.state = i | j;
/* 2628 */         this.type = -1;
/*      */         return;
/*      */     } 
/* 2631 */     updateState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AffineTransform createInverse() throws NoninvertibleTransformException {
/*      */     double d;
/* 2660 */     switch (this.state) {
/*      */       default:
/* 2662 */         stateError();
/*      */         
/* 2664 */         return null;
/*      */       case 7:
/* 2666 */         d = this.m00 * this.m11 - this.m01 * this.m10;
/* 2667 */         if (Math.abs(d) <= Double.MIN_VALUE) {
/* 2668 */           throw new NoninvertibleTransformException("Determinant is " + d);
/*      */         }
/*      */         
/* 2671 */         return new AffineTransform(this.m11 / d, -this.m10 / d, -this.m01 / d, this.m00 / d, (this.m01 * this.m12 - this.m11 * this.m02) / d, (this.m10 * this.m02 - this.m00 * this.m12) / d, 7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/* 2679 */         d = this.m00 * this.m11 - this.m01 * this.m10;
/* 2680 */         if (Math.abs(d) <= Double.MIN_VALUE) {
/* 2681 */           throw new NoninvertibleTransformException("Determinant is " + d);
/*      */         }
/*      */         
/* 2684 */         return new AffineTransform(this.m11 / d, -this.m10 / d, -this.m01 / d, this.m00 / d, 0.0D, 0.0D, 6);
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/* 2689 */         if (this.m01 == 0.0D || this.m10 == 0.0D) {
/* 2690 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/* 2692 */         return new AffineTransform(0.0D, 1.0D / this.m01, 1.0D / this.m10, 0.0D, -this.m12 / this.m10, -this.m02 / this.m01, 5);
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/* 2697 */         if (this.m01 == 0.0D || this.m10 == 0.0D) {
/* 2698 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/* 2700 */         return new AffineTransform(0.0D, 1.0D / this.m01, 1.0D / this.m10, 0.0D, 0.0D, 0.0D, 4);
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 2705 */         if (this.m00 == 0.0D || this.m11 == 0.0D) {
/* 2706 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/* 2708 */         return new AffineTransform(1.0D / this.m00, 0.0D, 0.0D, 1.0D / this.m11, -this.m02 / this.m00, -this.m12 / this.m11, 3);
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 2713 */         if (this.m00 == 0.0D || this.m11 == 0.0D) {
/* 2714 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/* 2716 */         return new AffineTransform(1.0D / this.m00, 0.0D, 0.0D, 1.0D / this.m11, 0.0D, 0.0D, 2);
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 2721 */         return new AffineTransform(1.0D, 0.0D, 0.0D, 1.0D, -this.m02, -this.m12, 1);
/*      */       
/*      */       case 0:
/*      */         break;
/*      */     } 
/* 2726 */     return new AffineTransform();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void invert() throws NoninvertibleTransformException {
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*      */     double d5;
/*      */     double d6;
/*      */     double d7;
/* 2757 */     switch (this.state) {
/*      */       default:
/* 2759 */         stateError();
/*      */         return;
/*      */       
/*      */       case 7:
/* 2763 */         d1 = this.m00; d2 = this.m01; d3 = this.m02;
/* 2764 */         d4 = this.m10; d5 = this.m11; d6 = this.m12;
/* 2765 */         d7 = d1 * d5 - d2 * d4;
/* 2766 */         if (Math.abs(d7) <= Double.MIN_VALUE) {
/* 2767 */           throw new NoninvertibleTransformException("Determinant is " + d7);
/*      */         }
/*      */         
/* 2770 */         this.m00 = d5 / d7;
/* 2771 */         this.m10 = -d4 / d7;
/* 2772 */         this.m01 = -d2 / d7;
/* 2773 */         this.m11 = d1 / d7;
/* 2774 */         this.m02 = (d2 * d6 - d5 * d3) / d7;
/* 2775 */         this.m12 = (d4 * d3 - d1 * d6) / d7;
/*      */         break;
/*      */       case 6:
/* 2778 */         d1 = this.m00; d2 = this.m01;
/* 2779 */         d4 = this.m10; d5 = this.m11;
/* 2780 */         d7 = d1 * d5 - d2 * d4;
/* 2781 */         if (Math.abs(d7) <= Double.MIN_VALUE) {
/* 2782 */           throw new NoninvertibleTransformException("Determinant is " + d7);
/*      */         }
/*      */         
/* 2785 */         this.m00 = d5 / d7;
/* 2786 */         this.m10 = -d4 / d7;
/* 2787 */         this.m01 = -d2 / d7;
/* 2788 */         this.m11 = d1 / d7;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 5:
/* 2793 */         d2 = this.m01; d3 = this.m02;
/* 2794 */         d4 = this.m10; d6 = this.m12;
/* 2795 */         if (d2 == 0.0D || d4 == 0.0D) {
/* 2796 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/*      */         
/* 2799 */         this.m10 = 1.0D / d2;
/* 2800 */         this.m01 = 1.0D / d4;
/*      */         
/* 2802 */         this.m02 = -d6 / d4;
/* 2803 */         this.m12 = -d3 / d2;
/*      */         break;
/*      */       case 4:
/* 2806 */         d2 = this.m01;
/* 2807 */         d4 = this.m10;
/* 2808 */         if (d2 == 0.0D || d4 == 0.0D) {
/* 2809 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/*      */         
/* 2812 */         this.m10 = 1.0D / d2;
/* 2813 */         this.m01 = 1.0D / d4;
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 2819 */         d1 = this.m00; d3 = this.m02;
/* 2820 */         d5 = this.m11; d6 = this.m12;
/* 2821 */         if (d1 == 0.0D || d5 == 0.0D) {
/* 2822 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/* 2824 */         this.m00 = 1.0D / d1;
/*      */ 
/*      */         
/* 2827 */         this.m11 = 1.0D / d5;
/* 2828 */         this.m02 = -d3 / d1;
/* 2829 */         this.m12 = -d6 / d5;
/*      */         break;
/*      */       case 2:
/* 2832 */         d1 = this.m00;
/* 2833 */         d5 = this.m11;
/* 2834 */         if (d1 == 0.0D || d5 == 0.0D) {
/* 2835 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/* 2837 */         this.m00 = 1.0D / d1;
/*      */ 
/*      */         
/* 2840 */         this.m11 = 1.0D / d5;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 2849 */         this.m02 = -this.m02;
/* 2850 */         this.m12 = -this.m12;
/*      */         break;
/*      */       case 0:
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point2D transform(Point2D paramPoint2D1, Point2D paramPoint2D2) {
/* 2882 */     if (paramPoint2D2 == null) {
/* 2883 */       if (paramPoint2D1 instanceof Point2D.Double) {
/* 2884 */         paramPoint2D2 = new Point2D.Double();
/*      */       } else {
/* 2886 */         paramPoint2D2 = new Point2D.Float();
/*      */       } 
/*      */     }
/*      */     
/* 2890 */     double d1 = paramPoint2D1.getX();
/* 2891 */     double d2 = paramPoint2D1.getY();
/* 2892 */     switch (this.state)
/*      */     { default:
/* 2894 */         stateError();
/*      */         
/* 2896 */         return null;
/*      */       case 7:
/* 2898 */         paramPoint2D2.setLocation(d1 * this.m00 + d2 * this.m01 + this.m02, d1 * this.m10 + d2 * this.m11 + this.m12);
/*      */         
/* 2900 */         return paramPoint2D2;
/*      */       case 6:
/* 2902 */         paramPoint2D2.setLocation(d1 * this.m00 + d2 * this.m01, d1 * this.m10 + d2 * this.m11);
/* 2903 */         return paramPoint2D2;
/*      */       case 5:
/* 2905 */         paramPoint2D2.setLocation(d2 * this.m01 + this.m02, d1 * this.m10 + this.m12);
/* 2906 */         return paramPoint2D2;
/*      */       case 4:
/* 2908 */         paramPoint2D2.setLocation(d2 * this.m01, d1 * this.m10);
/* 2909 */         return paramPoint2D2;
/*      */       case 3:
/* 2911 */         paramPoint2D2.setLocation(d1 * this.m00 + this.m02, d2 * this.m11 + this.m12);
/* 2912 */         return paramPoint2D2;
/*      */       case 2:
/* 2914 */         paramPoint2D2.setLocation(d1 * this.m00, d2 * this.m11);
/* 2915 */         return paramPoint2D2;
/*      */       case 1:
/* 2917 */         paramPoint2D2.setLocation(d1 + this.m02, d2 + this.m12);
/* 2918 */         return paramPoint2D2;
/*      */       case 0:
/* 2920 */         break; }  paramPoint2D2.setLocation(d1, d2);
/* 2921 */     return paramPoint2D2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transform(Point2D[] paramArrayOfPoint2D1, int paramInt1, Point2D[] paramArrayOfPoint2D2, int paramInt2, int paramInt3) {
/* 2961 */     int i = this.state;
/* 2962 */     while (--paramInt3 >= 0) {
/*      */       
/* 2964 */       Point2D point2D1 = paramArrayOfPoint2D1[paramInt1++];
/* 2965 */       double d1 = point2D1.getX();
/* 2966 */       double d2 = point2D1.getY();
/* 2967 */       Point2D point2D2 = paramArrayOfPoint2D2[paramInt2++];
/* 2968 */       if (point2D2 == null) {
/* 2969 */         if (point2D1 instanceof Point2D.Double) {
/* 2970 */           point2D2 = new Point2D.Double();
/*      */         } else {
/* 2972 */           point2D2 = new Point2D.Float();
/*      */         } 
/* 2974 */         paramArrayOfPoint2D2[paramInt2 - 1] = point2D2;
/*      */       } 
/* 2976 */       switch (i) {
/*      */         default:
/* 2978 */           stateError();
/*      */           return;
/*      */         
/*      */         case 7:
/* 2982 */           point2D2.setLocation(d1 * this.m00 + d2 * this.m01 + this.m02, d1 * this.m10 + d2 * this.m11 + this.m12);
/*      */           continue;
/*      */         
/*      */         case 6:
/* 2986 */           point2D2.setLocation(d1 * this.m00 + d2 * this.m01, d1 * this.m10 + d2 * this.m11);
/*      */           continue;
/*      */         case 5:
/* 2989 */           point2D2.setLocation(d2 * this.m01 + this.m02, d1 * this.m10 + this.m12);
/*      */           continue;
/*      */         case 4:
/* 2992 */           point2D2.setLocation(d2 * this.m01, d1 * this.m10);
/*      */           continue;
/*      */         case 3:
/* 2995 */           point2D2.setLocation(d1 * this.m00 + this.m02, d2 * this.m11 + this.m12);
/*      */           continue;
/*      */         case 2:
/* 2998 */           point2D2.setLocation(d1 * this.m00, d2 * this.m11);
/*      */           continue;
/*      */         case 1:
/* 3001 */           point2D2.setLocation(d1 + this.m02, d2 + this.m12); continue;
/*      */         case 0:
/*      */           break;
/* 3004 */       }  point2D2.setLocation(d1, d2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transform(float[] paramArrayOffloat1, int paramInt1, float[] paramArrayOffloat2, int paramInt2, int paramInt3) {
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*      */     double d5;
/*      */     double d6;
/* 3037 */     if (paramArrayOffloat2 == paramArrayOffloat1 && paramInt2 > paramInt1 && paramInt2 < paramInt1 + paramInt3 * 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3048 */       System.arraycopy(paramArrayOffloat1, paramInt1, paramArrayOffloat2, paramInt2, paramInt3 * 2);
/*      */       
/* 3050 */       paramInt1 = paramInt2;
/*      */     } 
/* 3052 */     switch (this.state) {
/*      */       default:
/* 3054 */         stateError();
/*      */         return;
/*      */       
/*      */       case 7:
/* 3058 */         d1 = this.m00; d2 = this.m01; d3 = this.m02;
/* 3059 */         d4 = this.m10; d5 = this.m11; d6 = this.m12;
/* 3060 */         while (--paramInt3 >= 0) {
/* 3061 */           double d7 = paramArrayOffloat1[paramInt1++];
/* 3062 */           double d8 = paramArrayOffloat1[paramInt1++];
/* 3063 */           paramArrayOffloat2[paramInt2++] = (float)(d1 * d7 + d2 * d8 + d3);
/* 3064 */           paramArrayOffloat2[paramInt2++] = (float)(d4 * d7 + d5 * d8 + d6);
/*      */         } 
/*      */         return;
/*      */       case 6:
/* 3068 */         d1 = this.m00; d2 = this.m01;
/* 3069 */         d4 = this.m10; d5 = this.m11;
/* 3070 */         while (--paramInt3 >= 0) {
/* 3071 */           double d7 = paramArrayOffloat1[paramInt1++];
/* 3072 */           double d8 = paramArrayOffloat1[paramInt1++];
/* 3073 */           paramArrayOffloat2[paramInt2++] = (float)(d1 * d7 + d2 * d8);
/* 3074 */           paramArrayOffloat2[paramInt2++] = (float)(d4 * d7 + d5 * d8);
/*      */         } 
/*      */         return;
/*      */       case 5:
/* 3078 */         d2 = this.m01; d3 = this.m02;
/* 3079 */         d4 = this.m10; d6 = this.m12;
/* 3080 */         while (--paramInt3 >= 0) {
/* 3081 */           double d = paramArrayOffloat1[paramInt1++];
/* 3082 */           paramArrayOffloat2[paramInt2++] = (float)(d2 * paramArrayOffloat1[paramInt1++] + d3);
/* 3083 */           paramArrayOffloat2[paramInt2++] = (float)(d4 * d + d6);
/*      */         } 
/*      */         return;
/*      */       case 4:
/* 3087 */         d2 = this.m01; d4 = this.m10;
/* 3088 */         while (--paramInt3 >= 0) {
/* 3089 */           double d = paramArrayOffloat1[paramInt1++];
/* 3090 */           paramArrayOffloat2[paramInt2++] = (float)(d2 * paramArrayOffloat1[paramInt1++]);
/* 3091 */           paramArrayOffloat2[paramInt2++] = (float)(d4 * d);
/*      */         } 
/*      */         return;
/*      */       case 3:
/* 3095 */         d1 = this.m00; d3 = this.m02;
/* 3096 */         d5 = this.m11; d6 = this.m12;
/* 3097 */         while (--paramInt3 >= 0) {
/* 3098 */           paramArrayOffloat2[paramInt2++] = (float)(d1 * paramArrayOffloat1[paramInt1++] + d3);
/* 3099 */           paramArrayOffloat2[paramInt2++] = (float)(d5 * paramArrayOffloat1[paramInt1++] + d6);
/*      */         } 
/*      */         return;
/*      */       case 2:
/* 3103 */         d1 = this.m00; d5 = this.m11;
/* 3104 */         while (--paramInt3 >= 0) {
/* 3105 */           paramArrayOffloat2[paramInt2++] = (float)(d1 * paramArrayOffloat1[paramInt1++]);
/* 3106 */           paramArrayOffloat2[paramInt2++] = (float)(d5 * paramArrayOffloat1[paramInt1++]);
/*      */         } 
/*      */         return;
/*      */       case 1:
/* 3110 */         d3 = this.m02; d6 = this.m12;
/* 3111 */         while (--paramInt3 >= 0) {
/* 3112 */           paramArrayOffloat2[paramInt2++] = (float)(paramArrayOffloat1[paramInt1++] + d3);
/* 3113 */           paramArrayOffloat2[paramInt2++] = (float)(paramArrayOffloat1[paramInt1++] + d6);
/*      */         }  return;
/*      */       case 0:
/*      */         break;
/* 3117 */     }  if (paramArrayOffloat1 != paramArrayOffloat2 || paramInt1 != paramInt2) {
/* 3118 */       System.arraycopy(paramArrayOffloat1, paramInt1, paramArrayOffloat2, paramInt2, paramInt3 * 2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transform(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, int paramInt3) {
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*      */     double d5;
/*      */     double d6;
/* 3152 */     if (paramArrayOfdouble2 == paramArrayOfdouble1 && paramInt2 > paramInt1 && paramInt2 < paramInt1 + paramInt3 * 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3163 */       System.arraycopy(paramArrayOfdouble1, paramInt1, paramArrayOfdouble2, paramInt2, paramInt3 * 2);
/*      */       
/* 3165 */       paramInt1 = paramInt2;
/*      */     } 
/* 3167 */     switch (this.state) {
/*      */       default:
/* 3169 */         stateError();
/*      */         return;
/*      */       
/*      */       case 7:
/* 3173 */         d1 = this.m00; d2 = this.m01; d3 = this.m02;
/* 3174 */         d4 = this.m10; d5 = this.m11; d6 = this.m12;
/* 3175 */         while (--paramInt3 >= 0) {
/* 3176 */           double d7 = paramArrayOfdouble1[paramInt1++];
/* 3177 */           double d8 = paramArrayOfdouble1[paramInt1++];
/* 3178 */           paramArrayOfdouble2[paramInt2++] = d1 * d7 + d2 * d8 + d3;
/* 3179 */           paramArrayOfdouble2[paramInt2++] = d4 * d7 + d5 * d8 + d6;
/*      */         } 
/*      */         return;
/*      */       case 6:
/* 3183 */         d1 = this.m00; d2 = this.m01;
/* 3184 */         d4 = this.m10; d5 = this.m11;
/* 3185 */         while (--paramInt3 >= 0) {
/* 3186 */           double d7 = paramArrayOfdouble1[paramInt1++];
/* 3187 */           double d8 = paramArrayOfdouble1[paramInt1++];
/* 3188 */           paramArrayOfdouble2[paramInt2++] = d1 * d7 + d2 * d8;
/* 3189 */           paramArrayOfdouble2[paramInt2++] = d4 * d7 + d5 * d8;
/*      */         } 
/*      */         return;
/*      */       case 5:
/* 3193 */         d2 = this.m01; d3 = this.m02;
/* 3194 */         d4 = this.m10; d6 = this.m12;
/* 3195 */         while (--paramInt3 >= 0) {
/* 3196 */           double d = paramArrayOfdouble1[paramInt1++];
/* 3197 */           paramArrayOfdouble2[paramInt2++] = d2 * paramArrayOfdouble1[paramInt1++] + d3;
/* 3198 */           paramArrayOfdouble2[paramInt2++] = d4 * d + d6;
/*      */         } 
/*      */         return;
/*      */       case 4:
/* 3202 */         d2 = this.m01; d4 = this.m10;
/* 3203 */         while (--paramInt3 >= 0) {
/* 3204 */           double d = paramArrayOfdouble1[paramInt1++];
/* 3205 */           paramArrayOfdouble2[paramInt2++] = d2 * paramArrayOfdouble1[paramInt1++];
/* 3206 */           paramArrayOfdouble2[paramInt2++] = d4 * d;
/*      */         } 
/*      */         return;
/*      */       case 3:
/* 3210 */         d1 = this.m00; d3 = this.m02;
/* 3211 */         d5 = this.m11; d6 = this.m12;
/* 3212 */         while (--paramInt3 >= 0) {
/* 3213 */           paramArrayOfdouble2[paramInt2++] = d1 * paramArrayOfdouble1[paramInt1++] + d3;
/* 3214 */           paramArrayOfdouble2[paramInt2++] = d5 * paramArrayOfdouble1[paramInt1++] + d6;
/*      */         } 
/*      */         return;
/*      */       case 2:
/* 3218 */         d1 = this.m00; d5 = this.m11;
/* 3219 */         while (--paramInt3 >= 0) {
/* 3220 */           paramArrayOfdouble2[paramInt2++] = d1 * paramArrayOfdouble1[paramInt1++];
/* 3221 */           paramArrayOfdouble2[paramInt2++] = d5 * paramArrayOfdouble1[paramInt1++];
/*      */         } 
/*      */         return;
/*      */       case 1:
/* 3225 */         d3 = this.m02; d6 = this.m12;
/* 3226 */         while (--paramInt3 >= 0) {
/* 3227 */           paramArrayOfdouble2[paramInt2++] = paramArrayOfdouble1[paramInt1++] + d3;
/* 3228 */           paramArrayOfdouble2[paramInt2++] = paramArrayOfdouble1[paramInt1++] + d6;
/*      */         }  return;
/*      */       case 0:
/*      */         break;
/* 3232 */     }  if (paramArrayOfdouble1 != paramArrayOfdouble2 || paramInt1 != paramInt2) {
/* 3233 */       System.arraycopy(paramArrayOfdouble1, paramInt1, paramArrayOfdouble2, paramInt2, paramInt3 * 2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transform(float[] paramArrayOffloat, int paramInt1, double[] paramArrayOfdouble, int paramInt2, int paramInt3) {
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*      */     double d5;
/*      */     double d6;
/* 3263 */     switch (this.state) {
/*      */       default:
/* 3265 */         stateError();
/*      */         return;
/*      */       
/*      */       case 7:
/* 3269 */         d1 = this.m00; d2 = this.m01; d3 = this.m02;
/* 3270 */         d4 = this.m10; d5 = this.m11; d6 = this.m12;
/* 3271 */         while (--paramInt3 >= 0) {
/* 3272 */           double d7 = paramArrayOffloat[paramInt1++];
/* 3273 */           double d8 = paramArrayOffloat[paramInt1++];
/* 3274 */           paramArrayOfdouble[paramInt2++] = d1 * d7 + d2 * d8 + d3;
/* 3275 */           paramArrayOfdouble[paramInt2++] = d4 * d7 + d5 * d8 + d6;
/*      */         } 
/*      */         return;
/*      */       case 6:
/* 3279 */         d1 = this.m00; d2 = this.m01;
/* 3280 */         d4 = this.m10; d5 = this.m11;
/* 3281 */         while (--paramInt3 >= 0) {
/* 3282 */           double d7 = paramArrayOffloat[paramInt1++];
/* 3283 */           double d8 = paramArrayOffloat[paramInt1++];
/* 3284 */           paramArrayOfdouble[paramInt2++] = d1 * d7 + d2 * d8;
/* 3285 */           paramArrayOfdouble[paramInt2++] = d4 * d7 + d5 * d8;
/*      */         } 
/*      */         return;
/*      */       case 5:
/* 3289 */         d2 = this.m01; d3 = this.m02;
/* 3290 */         d4 = this.m10; d6 = this.m12;
/* 3291 */         while (--paramInt3 >= 0) {
/* 3292 */           double d = paramArrayOffloat[paramInt1++];
/* 3293 */           paramArrayOfdouble[paramInt2++] = d2 * paramArrayOffloat[paramInt1++] + d3;
/* 3294 */           paramArrayOfdouble[paramInt2++] = d4 * d + d6;
/*      */         } 
/*      */         return;
/*      */       case 4:
/* 3298 */         d2 = this.m01; d4 = this.m10;
/* 3299 */         while (--paramInt3 >= 0) {
/* 3300 */           double d = paramArrayOffloat[paramInt1++];
/* 3301 */           paramArrayOfdouble[paramInt2++] = d2 * paramArrayOffloat[paramInt1++];
/* 3302 */           paramArrayOfdouble[paramInt2++] = d4 * d;
/*      */         } 
/*      */         return;
/*      */       case 3:
/* 3306 */         d1 = this.m00; d3 = this.m02;
/* 3307 */         d5 = this.m11; d6 = this.m12;
/* 3308 */         while (--paramInt3 >= 0) {
/* 3309 */           paramArrayOfdouble[paramInt2++] = d1 * paramArrayOffloat[paramInt1++] + d3;
/* 3310 */           paramArrayOfdouble[paramInt2++] = d5 * paramArrayOffloat[paramInt1++] + d6;
/*      */         } 
/*      */         return;
/*      */       case 2:
/* 3314 */         d1 = this.m00; d5 = this.m11;
/* 3315 */         while (--paramInt3 >= 0) {
/* 3316 */           paramArrayOfdouble[paramInt2++] = d1 * paramArrayOffloat[paramInt1++];
/* 3317 */           paramArrayOfdouble[paramInt2++] = d5 * paramArrayOffloat[paramInt1++];
/*      */         } 
/*      */         return;
/*      */       case 1:
/* 3321 */         d3 = this.m02; d6 = this.m12;
/* 3322 */         while (--paramInt3 >= 0) {
/* 3323 */           paramArrayOfdouble[paramInt2++] = paramArrayOffloat[paramInt1++] + d3;
/* 3324 */           paramArrayOfdouble[paramInt2++] = paramArrayOffloat[paramInt1++] + d6;
/*      */         }  return;
/*      */       case 0:
/*      */         break;
/* 3328 */     }  while (--paramInt3 >= 0) {
/* 3329 */       paramArrayOfdouble[paramInt2++] = paramArrayOffloat[paramInt1++];
/* 3330 */       paramArrayOfdouble[paramInt2++] = paramArrayOffloat[paramInt1++];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transform(double[] paramArrayOfdouble, int paramInt1, float[] paramArrayOffloat, int paramInt2, int paramInt3) {
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*      */     double d5;
/*      */     double d6;
/* 3359 */     switch (this.state) {
/*      */       default:
/* 3361 */         stateError();
/*      */         return;
/*      */       
/*      */       case 7:
/* 3365 */         d1 = this.m00; d2 = this.m01; d3 = this.m02;
/* 3366 */         d4 = this.m10; d5 = this.m11; d6 = this.m12;
/* 3367 */         while (--paramInt3 >= 0) {
/* 3368 */           double d7 = paramArrayOfdouble[paramInt1++];
/* 3369 */           double d8 = paramArrayOfdouble[paramInt1++];
/* 3370 */           paramArrayOffloat[paramInt2++] = (float)(d1 * d7 + d2 * d8 + d3);
/* 3371 */           paramArrayOffloat[paramInt2++] = (float)(d4 * d7 + d5 * d8 + d6);
/*      */         } 
/*      */         return;
/*      */       case 6:
/* 3375 */         d1 = this.m00; d2 = this.m01;
/* 3376 */         d4 = this.m10; d5 = this.m11;
/* 3377 */         while (--paramInt3 >= 0) {
/* 3378 */           double d7 = paramArrayOfdouble[paramInt1++];
/* 3379 */           double d8 = paramArrayOfdouble[paramInt1++];
/* 3380 */           paramArrayOffloat[paramInt2++] = (float)(d1 * d7 + d2 * d8);
/* 3381 */           paramArrayOffloat[paramInt2++] = (float)(d4 * d7 + d5 * d8);
/*      */         } 
/*      */         return;
/*      */       case 5:
/* 3385 */         d2 = this.m01; d3 = this.m02;
/* 3386 */         d4 = this.m10; d6 = this.m12;
/* 3387 */         while (--paramInt3 >= 0) {
/* 3388 */           double d = paramArrayOfdouble[paramInt1++];
/* 3389 */           paramArrayOffloat[paramInt2++] = (float)(d2 * paramArrayOfdouble[paramInt1++] + d3);
/* 3390 */           paramArrayOffloat[paramInt2++] = (float)(d4 * d + d6);
/*      */         } 
/*      */         return;
/*      */       case 4:
/* 3394 */         d2 = this.m01; d4 = this.m10;
/* 3395 */         while (--paramInt3 >= 0) {
/* 3396 */           double d = paramArrayOfdouble[paramInt1++];
/* 3397 */           paramArrayOffloat[paramInt2++] = (float)(d2 * paramArrayOfdouble[paramInt1++]);
/* 3398 */           paramArrayOffloat[paramInt2++] = (float)(d4 * d);
/*      */         } 
/*      */         return;
/*      */       case 3:
/* 3402 */         d1 = this.m00; d3 = this.m02;
/* 3403 */         d5 = this.m11; d6 = this.m12;
/* 3404 */         while (--paramInt3 >= 0) {
/* 3405 */           paramArrayOffloat[paramInt2++] = (float)(d1 * paramArrayOfdouble[paramInt1++] + d3);
/* 3406 */           paramArrayOffloat[paramInt2++] = (float)(d5 * paramArrayOfdouble[paramInt1++] + d6);
/*      */         } 
/*      */         return;
/*      */       case 2:
/* 3410 */         d1 = this.m00; d5 = this.m11;
/* 3411 */         while (--paramInt3 >= 0) {
/* 3412 */           paramArrayOffloat[paramInt2++] = (float)(d1 * paramArrayOfdouble[paramInt1++]);
/* 3413 */           paramArrayOffloat[paramInt2++] = (float)(d5 * paramArrayOfdouble[paramInt1++]);
/*      */         } 
/*      */         return;
/*      */       case 1:
/* 3417 */         d3 = this.m02; d6 = this.m12;
/* 3418 */         while (--paramInt3 >= 0) {
/* 3419 */           paramArrayOffloat[paramInt2++] = (float)(paramArrayOfdouble[paramInt1++] + d3);
/* 3420 */           paramArrayOffloat[paramInt2++] = (float)(paramArrayOfdouble[paramInt1++] + d6);
/*      */         }  return;
/*      */       case 0:
/*      */         break;
/* 3424 */     }  while (--paramInt3 >= 0) {
/* 3425 */       paramArrayOffloat[paramInt2++] = (float)paramArrayOfdouble[paramInt1++];
/* 3426 */       paramArrayOffloat[paramInt2++] = (float)paramArrayOfdouble[paramInt1++];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point2D inverseTransform(Point2D paramPoint2D1, Point2D paramPoint2D2) throws NoninvertibleTransformException {
/*      */     double d3;
/* 3457 */     if (paramPoint2D2 == null) {
/* 3458 */       if (paramPoint2D1 instanceof Point2D.Double) {
/* 3459 */         paramPoint2D2 = new Point2D.Double();
/*      */       } else {
/* 3461 */         paramPoint2D2 = new Point2D.Float();
/*      */       } 
/*      */     }
/*      */     
/* 3465 */     double d1 = paramPoint2D1.getX();
/* 3466 */     double d2 = paramPoint2D1.getY();
/* 3467 */     switch (this.state)
/*      */     { default:
/* 3469 */         stateError();
/*      */       
/*      */       case 7:
/* 3472 */         d1 -= this.m02;
/* 3473 */         d2 -= this.m12;
/*      */       
/*      */       case 6:
/* 3476 */         d3 = this.m00 * this.m11 - this.m01 * this.m10;
/* 3477 */         if (Math.abs(d3) <= Double.MIN_VALUE) {
/* 3478 */           throw new NoninvertibleTransformException("Determinant is " + d3);
/*      */         }
/*      */         
/* 3481 */         paramPoint2D2.setLocation((d1 * this.m11 - d2 * this.m01) / d3, (d2 * this.m00 - d1 * this.m10) / d3);
/*      */         
/* 3483 */         return paramPoint2D2;
/*      */       case 5:
/* 3485 */         d1 -= this.m02;
/* 3486 */         d2 -= this.m12;
/*      */       
/*      */       case 4:
/* 3489 */         if (this.m01 == 0.0D || this.m10 == 0.0D) {
/* 3490 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/* 3492 */         paramPoint2D2.setLocation(d2 / this.m10, d1 / this.m01);
/* 3493 */         return paramPoint2D2;
/*      */       case 3:
/* 3495 */         d1 -= this.m02;
/* 3496 */         d2 -= this.m12;
/*      */       
/*      */       case 2:
/* 3499 */         if (this.m00 == 0.0D || this.m11 == 0.0D) {
/* 3500 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/* 3502 */         paramPoint2D2.setLocation(d1 / this.m00, d2 / this.m11);
/* 3503 */         return paramPoint2D2;
/*      */       case 1:
/* 3505 */         paramPoint2D2.setLocation(d1 - this.m02, d2 - this.m12);
/* 3506 */         return paramPoint2D2;
/*      */       case 0:
/* 3508 */         break; }  paramPoint2D2.setLocation(d1, d2);
/* 3509 */     return paramPoint2D2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void inverseTransform(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, int paramInt3) throws NoninvertibleTransformException {
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/*      */     double d5;
/*      */     double d6;
/*      */     double d7;
/* 3546 */     if (paramArrayOfdouble2 == paramArrayOfdouble1 && paramInt2 > paramInt1 && paramInt2 < paramInt1 + paramInt3 * 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3557 */       System.arraycopy(paramArrayOfdouble1, paramInt1, paramArrayOfdouble2, paramInt2, paramInt3 * 2);
/*      */       
/* 3559 */       paramInt1 = paramInt2;
/*      */     } 
/* 3561 */     switch (this.state) {
/*      */       default:
/* 3563 */         stateError();
/*      */         return;
/*      */       
/*      */       case 7:
/* 3567 */         d1 = this.m00; d2 = this.m01; d3 = this.m02;
/* 3568 */         d4 = this.m10; d5 = this.m11; d6 = this.m12;
/* 3569 */         d7 = d1 * d5 - d2 * d4;
/* 3570 */         if (Math.abs(d7) <= Double.MIN_VALUE) {
/* 3571 */           throw new NoninvertibleTransformException("Determinant is " + d7);
/*      */         }
/*      */         
/* 3574 */         while (--paramInt3 >= 0) {
/* 3575 */           double d8 = paramArrayOfdouble1[paramInt1++] - d3;
/* 3576 */           double d9 = paramArrayOfdouble1[paramInt1++] - d6;
/* 3577 */           paramArrayOfdouble2[paramInt2++] = (d8 * d5 - d9 * d2) / d7;
/* 3578 */           paramArrayOfdouble2[paramInt2++] = (d9 * d1 - d8 * d4) / d7;
/*      */         } 
/*      */         return;
/*      */       case 6:
/* 3582 */         d1 = this.m00; d2 = this.m01;
/* 3583 */         d4 = this.m10; d5 = this.m11;
/* 3584 */         d7 = d1 * d5 - d2 * d4;
/* 3585 */         if (Math.abs(d7) <= Double.MIN_VALUE) {
/* 3586 */           throw new NoninvertibleTransformException("Determinant is " + d7);
/*      */         }
/*      */         
/* 3589 */         while (--paramInt3 >= 0) {
/* 3590 */           double d8 = paramArrayOfdouble1[paramInt1++];
/* 3591 */           double d9 = paramArrayOfdouble1[paramInt1++];
/* 3592 */           paramArrayOfdouble2[paramInt2++] = (d8 * d5 - d9 * d2) / d7;
/* 3593 */           paramArrayOfdouble2[paramInt2++] = (d9 * d1 - d8 * d4) / d7;
/*      */         } 
/*      */         return;
/*      */       case 5:
/* 3597 */         d2 = this.m01; d3 = this.m02;
/* 3598 */         d4 = this.m10; d6 = this.m12;
/* 3599 */         if (d2 == 0.0D || d4 == 0.0D) {
/* 3600 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/* 3602 */         while (--paramInt3 >= 0) {
/* 3603 */           double d = paramArrayOfdouble1[paramInt1++] - d3;
/* 3604 */           paramArrayOfdouble2[paramInt2++] = (paramArrayOfdouble1[paramInt1++] - d6) / d4;
/* 3605 */           paramArrayOfdouble2[paramInt2++] = d / d2;
/*      */         } 
/*      */         return;
/*      */       case 4:
/* 3609 */         d2 = this.m01; d4 = this.m10;
/* 3610 */         if (d2 == 0.0D || d4 == 0.0D) {
/* 3611 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/* 3613 */         while (--paramInt3 >= 0) {
/* 3614 */           double d = paramArrayOfdouble1[paramInt1++];
/* 3615 */           paramArrayOfdouble2[paramInt2++] = paramArrayOfdouble1[paramInt1++] / d4;
/* 3616 */           paramArrayOfdouble2[paramInt2++] = d / d2;
/*      */         } 
/*      */         return;
/*      */       case 3:
/* 3620 */         d1 = this.m00; d3 = this.m02;
/* 3621 */         d5 = this.m11; d6 = this.m12;
/* 3622 */         if (d1 == 0.0D || d5 == 0.0D) {
/* 3623 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/* 3625 */         while (--paramInt3 >= 0) {
/* 3626 */           paramArrayOfdouble2[paramInt2++] = (paramArrayOfdouble1[paramInt1++] - d3) / d1;
/* 3627 */           paramArrayOfdouble2[paramInt2++] = (paramArrayOfdouble1[paramInt1++] - d6) / d5;
/*      */         } 
/*      */         return;
/*      */       case 2:
/* 3631 */         d1 = this.m00; d5 = this.m11;
/* 3632 */         if (d1 == 0.0D || d5 == 0.0D) {
/* 3633 */           throw new NoninvertibleTransformException("Determinant is 0");
/*      */         }
/* 3635 */         while (--paramInt3 >= 0) {
/* 3636 */           paramArrayOfdouble2[paramInt2++] = paramArrayOfdouble1[paramInt1++] / d1;
/* 3637 */           paramArrayOfdouble2[paramInt2++] = paramArrayOfdouble1[paramInt1++] / d5;
/*      */         } 
/*      */         return;
/*      */       case 1:
/* 3641 */         d3 = this.m02; d6 = this.m12;
/* 3642 */         while (--paramInt3 >= 0) {
/* 3643 */           paramArrayOfdouble2[paramInt2++] = paramArrayOfdouble1[paramInt1++] - d3;
/* 3644 */           paramArrayOfdouble2[paramInt2++] = paramArrayOfdouble1[paramInt1++] - d6;
/*      */         }  return;
/*      */       case 0:
/*      */         break;
/* 3648 */     }  if (paramArrayOfdouble1 != paramArrayOfdouble2 || paramInt1 != paramInt2) {
/* 3649 */       System.arraycopy(paramArrayOfdouble1, paramInt1, paramArrayOfdouble2, paramInt2, paramInt3 * 2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point2D deltaTransform(Point2D paramPoint2D1, Point2D paramPoint2D2) {
/* 3684 */     if (paramPoint2D2 == null) {
/* 3685 */       if (paramPoint2D1 instanceof Point2D.Double) {
/* 3686 */         paramPoint2D2 = new Point2D.Double();
/*      */       } else {
/* 3688 */         paramPoint2D2 = new Point2D.Float();
/*      */       } 
/*      */     }
/*      */     
/* 3692 */     double d1 = paramPoint2D1.getX();
/* 3693 */     double d2 = paramPoint2D1.getY();
/* 3694 */     switch (this.state)
/*      */     { default:
/* 3696 */         stateError();
/*      */         
/* 3698 */         return null;
/*      */       case 6:
/*      */       case 7:
/* 3701 */         paramPoint2D2.setLocation(d1 * this.m00 + d2 * this.m01, d1 * this.m10 + d2 * this.m11);
/* 3702 */         return paramPoint2D2;
/*      */       case 4:
/*      */       case 5:
/* 3705 */         paramPoint2D2.setLocation(d2 * this.m01, d1 * this.m10);
/* 3706 */         return paramPoint2D2;
/*      */       case 2:
/*      */       case 3:
/* 3709 */         paramPoint2D2.setLocation(d1 * this.m00, d2 * this.m11);
/* 3710 */         return paramPoint2D2;
/*      */       case 0:
/*      */       case 1:
/* 3713 */         break; }  paramPoint2D2.setLocation(d1, d2);
/* 3714 */     return paramPoint2D2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deltaTransform(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, int paramInt3) {
/*      */     double d1;
/*      */     double d2;
/*      */     double d3;
/*      */     double d4;
/* 3755 */     if (paramArrayOfdouble2 == paramArrayOfdouble1 && paramInt2 > paramInt1 && paramInt2 < paramInt1 + paramInt3 * 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3766 */       System.arraycopy(paramArrayOfdouble1, paramInt1, paramArrayOfdouble2, paramInt2, paramInt3 * 2);
/*      */       
/* 3768 */       paramInt1 = paramInt2;
/*      */     } 
/* 3770 */     switch (this.state) {
/*      */       default:
/* 3772 */         stateError();
/*      */         return;
/*      */       
/*      */       case 6:
/*      */       case 7:
/* 3777 */         d1 = this.m00; d2 = this.m01;
/* 3778 */         d3 = this.m10; d4 = this.m11;
/* 3779 */         while (--paramInt3 >= 0) {
/* 3780 */           double d5 = paramArrayOfdouble1[paramInt1++];
/* 3781 */           double d6 = paramArrayOfdouble1[paramInt1++];
/* 3782 */           paramArrayOfdouble2[paramInt2++] = d5 * d1 + d6 * d2;
/* 3783 */           paramArrayOfdouble2[paramInt2++] = d5 * d3 + d6 * d4;
/*      */         } 
/*      */         return;
/*      */       case 4:
/*      */       case 5:
/* 3788 */         d2 = this.m01; d3 = this.m10;
/* 3789 */         while (--paramInt3 >= 0) {
/* 3790 */           double d = paramArrayOfdouble1[paramInt1++];
/* 3791 */           paramArrayOfdouble2[paramInt2++] = paramArrayOfdouble1[paramInt1++] * d2;
/* 3792 */           paramArrayOfdouble2[paramInt2++] = d * d3;
/*      */         } 
/*      */         return;
/*      */       case 2:
/*      */       case 3:
/* 3797 */         d1 = this.m00; d4 = this.m11;
/* 3798 */         while (--paramInt3 >= 0) {
/* 3799 */           paramArrayOfdouble2[paramInt2++] = paramArrayOfdouble1[paramInt1++] * d1;
/* 3800 */           paramArrayOfdouble2[paramInt2++] = paramArrayOfdouble1[paramInt1++] * d4;
/*      */         }  return;
/*      */       case 0:
/*      */       case 1:
/*      */         break;
/* 3805 */     }  if (paramArrayOfdouble1 != paramArrayOfdouble2 || paramInt1 != paramInt2) {
/* 3806 */       System.arraycopy(paramArrayOfdouble1, paramInt1, paramArrayOfdouble2, paramInt2, paramInt3 * 2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape createTransformedShape(Shape paramShape) {
/* 3826 */     if (paramShape == null) {
/* 3827 */       return null;
/*      */     }
/* 3829 */     return new Path2D.Double(paramShape, this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static double _matround(double paramDouble) {
/* 3835 */     return Math.rint(paramDouble * 1.0E15D) / 1.0E15D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 3846 */     return "AffineTransform[[" + 
/* 3847 */       _matround(this.m00) + ", " + 
/* 3848 */       _matround(this.m01) + ", " + 
/* 3849 */       _matround(this.m02) + "], [" + 
/* 3850 */       _matround(this.m10) + ", " + 
/* 3851 */       _matround(this.m11) + ", " + 
/* 3852 */       _matround(this.m12) + "]]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isIdentity() {
/* 3863 */     return (this.state == 0 || getType() == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*      */     try {
/* 3874 */       return super.clone();
/* 3875 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/* 3877 */       throw new InternalError(cloneNotSupportedException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 3887 */     long l = Double.doubleToLongBits(this.m00);
/* 3888 */     l = l * 31L + Double.doubleToLongBits(this.m01);
/* 3889 */     l = l * 31L + Double.doubleToLongBits(this.m02);
/* 3890 */     l = l * 31L + Double.doubleToLongBits(this.m10);
/* 3891 */     l = l * 31L + Double.doubleToLongBits(this.m11);
/* 3892 */     l = l * 31L + Double.doubleToLongBits(this.m12);
/* 3893 */     return (int)l ^ (int)(l >> 32L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/* 3907 */     if (!(paramObject instanceof AffineTransform)) {
/* 3908 */       return false;
/*      */     }
/*      */     
/* 3911 */     AffineTransform affineTransform = (AffineTransform)paramObject;
/*      */     
/* 3913 */     return (this.m00 == affineTransform.m00 && this.m01 == affineTransform.m01 && this.m02 == affineTransform.m02 && this.m10 == affineTransform.m10 && this.m11 == affineTransform.m11 && this.m12 == affineTransform.m12);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws ClassNotFoundException, IOException {
/* 3932 */     paramObjectOutputStream.defaultWriteObject();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 3938 */     paramObjectInputStream.defaultReadObject();
/* 3939 */     updateState();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/AffineTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */