/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DerivedColor
/*     */   extends Color
/*     */ {
/*     */   private final String uiDefaultParentName;
/*     */   private final float hOffset;
/*     */   private final float sOffset;
/*     */   private final float bOffset;
/*     */   private final int aOffset;
/*     */   private int argbValue;
/*     */   
/*     */   DerivedColor(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt) {
/*  48 */     super(0);
/*  49 */     this.uiDefaultParentName = paramString;
/*  50 */     this.hOffset = paramFloat1;
/*  51 */     this.sOffset = paramFloat2;
/*  52 */     this.bOffset = paramFloat3;
/*  53 */     this.aOffset = paramInt;
/*     */   }
/*     */   
/*     */   public String getUiDefaultParentName() {
/*  57 */     return this.uiDefaultParentName;
/*     */   }
/*     */   
/*     */   public float getHueOffset() {
/*  61 */     return this.hOffset;
/*     */   }
/*     */   
/*     */   public float getSaturationOffset() {
/*  65 */     return this.sOffset;
/*     */   }
/*     */   
/*     */   public float getBrightnessOffset() {
/*  69 */     return this.bOffset;
/*     */   }
/*     */   
/*     */   public int getAlphaOffset() {
/*  73 */     return this.aOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rederiveColor() {
/*  80 */     Color color = UIManager.getColor(this.uiDefaultParentName);
/*  81 */     if (color != null) {
/*  82 */       float[] arrayOfFloat = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
/*     */       
/*  84 */       arrayOfFloat[0] = clamp(arrayOfFloat[0] + this.hOffset);
/*  85 */       arrayOfFloat[1] = clamp(arrayOfFloat[1] + this.sOffset);
/*  86 */       arrayOfFloat[2] = clamp(arrayOfFloat[2] + this.bOffset);
/*  87 */       int i = clamp(color.getAlpha() + this.aOffset);
/*  88 */       this.argbValue = Color.HSBtoRGB(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2]) & 0xFFFFFF | i << 24;
/*     */     } else {
/*  90 */       float[] arrayOfFloat = new float[3];
/*  91 */       arrayOfFloat[0] = clamp(this.hOffset);
/*  92 */       arrayOfFloat[1] = clamp(this.sOffset);
/*  93 */       arrayOfFloat[2] = clamp(this.bOffset);
/*  94 */       int i = clamp(this.aOffset);
/*  95 */       this.argbValue = Color.HSBtoRGB(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2]) & 0xFFFFFF | i << 24;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRGB() {
/* 111 */     return this.argbValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 116 */     if (this == paramObject) return true; 
/* 117 */     if (!(paramObject instanceof DerivedColor)) return false; 
/* 118 */     DerivedColor derivedColor = (DerivedColor)paramObject;
/* 119 */     if (this.aOffset != derivedColor.aOffset) return false; 
/* 120 */     if (Float.compare(derivedColor.bOffset, this.bOffset) != 0) return false; 
/* 121 */     if (Float.compare(derivedColor.hOffset, this.hOffset) != 0) return false; 
/* 122 */     if (Float.compare(derivedColor.sOffset, this.sOffset) != 0) return false; 
/* 123 */     if (!this.uiDefaultParentName.equals(derivedColor.uiDefaultParentName)) return false; 
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     int i = this.uiDefaultParentName.hashCode();
/*     */     
/* 131 */     i = ((31 * i) + this.hOffset != 0.0F) ? Float.floatToIntBits(this.hOffset) : 0;
/*     */     
/* 133 */     i = ((31 * i) + this.sOffset != 0.0F) ? Float.floatToIntBits(this.sOffset) : 0;
/*     */     
/* 135 */     i = ((31 * i) + this.bOffset != 0.0F) ? Float.floatToIntBits(this.bOffset) : 0;
/* 136 */     i = 31 * i + this.aOffset;
/* 137 */     return i;
/*     */   }
/*     */   
/*     */   private float clamp(float paramFloat) {
/* 141 */     if (paramFloat < 0.0F) {
/* 142 */       paramFloat = 0.0F;
/* 143 */     } else if (paramFloat > 1.0F) {
/* 144 */       paramFloat = 1.0F;
/*     */     } 
/* 146 */     return paramFloat;
/*     */   }
/*     */   
/*     */   private int clamp(int paramInt) {
/* 150 */     if (paramInt < 0) {
/* 151 */       paramInt = 0;
/* 152 */     } else if (paramInt > 255) {
/* 153 */       paramInt = 255;
/*     */     } 
/* 155 */     return paramInt;
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
/*     */   public String toString() {
/* 168 */     Color color = UIManager.getColor(this.uiDefaultParentName);
/*     */ 
/*     */ 
/*     */     
/* 172 */     String str = "DerivedColor(color=" + getRed() + "," + getGreen() + "," + getBlue() + " parent=" + this.uiDefaultParentName + " offsets=" + getHueOffset() + "," + getSaturationOffset() + "," + getBrightnessOffset() + "," + getAlphaOffset();
/* 173 */     return (color == null) ? str : (str + " pColor=" + color.getRed() + "," + color.getGreen() + "," + color.getBlue());
/*     */   }
/*     */   
/*     */   static class UIResource
/*     */     extends DerivedColor implements javax.swing.plaf.UIResource {
/*     */     UIResource(String param1String, float param1Float1, float param1Float2, float param1Float3, int param1Int) {
/* 179 */       super(param1String, param1Float1, param1Float2, param1Float3, param1Int);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 184 */       return (param1Object instanceof UIResource && super.equals(param1Object));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 189 */       return super.hashCode() + 7;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/DerivedColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */