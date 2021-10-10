/*     */ package javax.swing.colorchooser;
/*     */ 
/*     */ import java.awt.Component;
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
/*     */ class ColorModel
/*     */ {
/*     */   private final String prefix;
/*     */   private final String[] labels;
/*     */   
/*     */   ColorModel(String paramString, String... paramVarArgs) {
/*  37 */     this.prefix = "ColorChooser." + paramString;
/*  38 */     this.labels = paramVarArgs;
/*     */   }
/*     */   
/*     */   ColorModel() {
/*  42 */     this("rgb", new String[] { "Red", "Green", "Blue", "Alpha" });
/*     */   }
/*     */   
/*     */   void setColor(int paramInt, float[] paramArrayOffloat) {
/*  46 */     paramArrayOffloat[0] = normalize(paramInt >> 16);
/*  47 */     paramArrayOffloat[1] = normalize(paramInt >> 8);
/*  48 */     paramArrayOffloat[2] = normalize(paramInt);
/*  49 */     paramArrayOffloat[3] = normalize(paramInt >> 24);
/*     */   }
/*     */   
/*     */   int getColor(float[] paramArrayOffloat) {
/*  53 */     return to8bit(paramArrayOffloat[2]) | to8bit(paramArrayOffloat[1]) << 8 | to8bit(paramArrayOffloat[0]) << 16 | to8bit(paramArrayOffloat[3]) << 24;
/*     */   }
/*     */   
/*     */   int getCount() {
/*  57 */     return this.labels.length;
/*     */   }
/*     */   
/*     */   int getMinimum(int paramInt) {
/*  61 */     return 0;
/*     */   }
/*     */   
/*     */   int getMaximum(int paramInt) {
/*  65 */     return 255;
/*     */   }
/*     */   
/*     */   float getDefault(int paramInt) {
/*  69 */     return 0.0F;
/*     */   }
/*     */   
/*     */   final String getLabel(Component paramComponent, int paramInt) {
/*  73 */     return getText(paramComponent, this.labels[paramInt]);
/*     */   }
/*     */   
/*     */   private static float normalize(int paramInt) {
/*  77 */     return (paramInt & 0xFF) / 255.0F;
/*     */   }
/*     */   
/*     */   private static int to8bit(float paramFloat) {
/*  81 */     return (int)(255.0F * paramFloat);
/*     */   }
/*     */   
/*     */   final String getText(Component paramComponent, String paramString) {
/*  85 */     return UIManager.getString(this.prefix + paramString + "Text", paramComponent.getLocale());
/*     */   }
/*     */   
/*     */   final int getInteger(Component paramComponent, String paramString) {
/*  89 */     Object object = UIManager.get(this.prefix + paramString, paramComponent.getLocale());
/*  90 */     if (object instanceof Integer) {
/*  91 */       return ((Integer)object).intValue();
/*     */     }
/*  93 */     if (object instanceof String) {
/*     */       try {
/*  95 */         return Integer.parseInt((String)object);
/*     */       }
/*  97 */       catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */     
/* 100 */     return -1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/ColorModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */