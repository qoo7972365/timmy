/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Constructor;
/*     */ import sun.java2d.Disposer;
/*     */ import sun.java2d.DisposerRecord;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FontScaler
/*     */   implements DisposerRecord
/*     */ {
/*  84 */   private static FontScaler nullScaler = null;
/*  85 */   private static Constructor<FontScaler> scalerConstructor = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  92 */     Class<?> clazz = null;
/*  93 */     Class[] arrayOfClass = { Font2D.class, int.class, boolean.class, int.class };
/*     */ 
/*     */     
/*     */     try {
/*  97 */       if (FontUtilities.isOpenJDK) {
/*  98 */         clazz = Class.forName("sun.font.FreetypeFontScaler");
/*     */       } else {
/* 100 */         clazz = Class.forName("sun.font.T2KFontScaler");
/*     */       } 
/* 102 */     } catch (ClassNotFoundException classNotFoundException) {
/* 103 */       clazz = NullFontScaler.class;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 108 */       scalerConstructor = (Constructor)clazz.getConstructor(arrayOfClass);
/* 109 */     } catch (NoSuchMethodException noSuchMethodException) {}
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
/*     */   public static FontScaler getScaler(Font2D paramFont2D, int paramInt1, boolean paramBoolean, int paramInt2) {
/* 123 */     FontScaler fontScaler = null;
/*     */ 
/*     */     
/*     */     try {
/* 127 */       Object[] arrayOfObject = { paramFont2D, Integer.valueOf(paramInt1), Boolean.valueOf(paramBoolean), Integer.valueOf(paramInt2) };
/* 128 */       fontScaler = scalerConstructor.newInstance(arrayOfObject);
/* 129 */       Disposer.addObjectRecord(paramFont2D, fontScaler);
/* 130 */     } catch (Throwable throwable) {
/* 131 */       fontScaler = nullScaler;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       FontManager fontManager = FontManagerFactory.getInstance();
/* 137 */       fontManager.deRegisterBadFont(paramFont2D);
/*     */     } 
/* 139 */     return fontScaler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized FontScaler getNullScaler() {
/* 150 */     if (nullScaler == null) {
/* 151 */       nullScaler = new NullFontScaler();
/*     */     }
/* 153 */     return nullScaler;
/*     */   }
/*     */   
/* 156 */   protected WeakReference<Font2D> font = null;
/* 157 */   protected long nativeScaler = 0L;
/*     */   protected boolean disposed = false;
/*     */   
/*     */   public void dispose() {}
/*     */   
/*     */   public void disposeScaler() {}
/*     */   
/*     */   abstract StrikeMetrics getFontMetrics(long paramLong) throws FontScalerException;
/*     */   
/*     */   abstract float getGlyphAdvance(long paramLong, int paramInt) throws FontScalerException;
/*     */   
/*     */   abstract void getGlyphMetrics(long paramLong, int paramInt, Point2D.Float paramFloat) throws FontScalerException;
/*     */   
/*     */   abstract long getGlyphImage(long paramLong, int paramInt) throws FontScalerException;
/*     */   
/*     */   abstract Rectangle2D.Float getGlyphOutlineBounds(long paramLong, int paramInt) throws FontScalerException;
/*     */   
/*     */   abstract GeneralPath getGlyphOutline(long paramLong, int paramInt, float paramFloat1, float paramFloat2) throws FontScalerException;
/*     */   
/*     */   abstract GeneralPath getGlyphVectorOutline(long paramLong, int[] paramArrayOfint, int paramInt, float paramFloat1, float paramFloat2) throws FontScalerException;
/*     */   
/*     */   abstract int getNumGlyphs() throws FontScalerException;
/*     */   
/*     */   abstract int getMissingGlyphCode() throws FontScalerException;
/*     */   
/*     */   abstract int getGlyphCode(char paramChar) throws FontScalerException;
/*     */   
/*     */   abstract long getLayoutTableCache() throws FontScalerException;
/*     */   
/*     */   abstract Point2D.Float getGlyphPoint(long paramLong, int paramInt1, int paramInt2) throws FontScalerException;
/*     */   
/*     */   abstract long getUnitsPerEm();
/*     */   
/*     */   abstract long createScalerContext(double[] paramArrayOfdouble, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, boolean paramBoolean);
/*     */   
/*     */   abstract void invalidateScalerContext(long paramLong);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/FontScaler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */