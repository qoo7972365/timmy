/*     */ package java.awt.color;
/*     */ 
/*     */ import sun.java2d.cmm.Profile;
/*     */ import sun.java2d.cmm.ProfileDeferralInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ICC_ProfileRGB
/*     */   extends ICC_Profile
/*     */ {
/*     */   static final long serialVersionUID = 8505067385152579334L;
/*     */   public static final int REDCOMPONENT = 0;
/*     */   public static final int GREENCOMPONENT = 1;
/*     */   public static final int BLUECOMPONENT = 2;
/*     */   
/*     */   ICC_ProfileRGB(Profile paramProfile) {
/* 118 */     super(paramProfile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ICC_ProfileRGB(ProfileDeferralInfo paramProfileDeferralInfo) {
/* 128 */     super(paramProfileDeferralInfo);
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
/*     */   public float[] getMediaWhitePoint() {
/* 140 */     return super.getMediaWhitePoint();
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
/*     */   public float[][] getMatrix() {
/* 158 */     float[][] arrayOfFloat = new float[3][3];
/*     */ 
/*     */     
/* 161 */     float[] arrayOfFloat1 = getXYZTag(1918392666);
/* 162 */     arrayOfFloat[0][0] = arrayOfFloat1[0];
/* 163 */     arrayOfFloat[1][0] = arrayOfFloat1[1];
/* 164 */     arrayOfFloat[2][0] = arrayOfFloat1[2];
/* 165 */     arrayOfFloat1 = getXYZTag(1733843290);
/* 166 */     arrayOfFloat[0][1] = arrayOfFloat1[0];
/* 167 */     arrayOfFloat[1][1] = arrayOfFloat1[1];
/* 168 */     arrayOfFloat[2][1] = arrayOfFloat1[2];
/* 169 */     arrayOfFloat1 = getXYZTag(1649957210);
/* 170 */     arrayOfFloat[0][2] = arrayOfFloat1[0];
/* 171 */     arrayOfFloat[1][2] = arrayOfFloat1[1];
/* 172 */     arrayOfFloat[2][2] = arrayOfFloat1[2];
/* 173 */     return arrayOfFloat;
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
/*     */   public float getGamma(int paramInt) {
/*     */     float f;
/*     */     int i;
/* 204 */     switch (paramInt) {
/*     */       case 0:
/* 206 */         i = 1918128707;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 221 */         f = super.getGamma(i);
/*     */         
/* 223 */         return f;case 1: i = 1733579331; f = super.getGamma(i); return f;case 2: i = 1649693251; f = super.getGamma(i); return f;
/*     */     } 
/*     */     throw new IllegalArgumentException("Must be Red, Green, or Blue");
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
/*     */   public short[] getTRC(int paramInt) {
/*     */     short[] arrayOfShort;
/*     */     int i;
/* 260 */     switch (paramInt) {
/*     */       case 0:
/* 262 */         i = 1918128707;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 277 */         arrayOfShort = super.getTRC(i);
/*     */         
/* 279 */         return arrayOfShort;case 1: i = 1733579331; arrayOfShort = super.getTRC(i); return arrayOfShort;case 2: i = 1649693251; arrayOfShort = super.getTRC(i); return arrayOfShort;
/*     */     } 
/*     */     throw new IllegalArgumentException("Must be Red, Green, or Blue");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/color/ICC_ProfileRGB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */