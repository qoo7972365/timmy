/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.FontFormatException;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.ref.Reference;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
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
/*     */ public abstract class FileFont
/*     */   extends PhysicalFont
/*     */ {
/*     */   protected boolean useJavaRasterizer = true;
/*     */   protected int fileSize;
/*     */   protected FontScaler scaler;
/*     */   protected boolean checkedNatives;
/*     */   protected boolean useNatives;
/*     */   protected NativeFont[] nativeFonts;
/*     */   protected char[] glyphToCharMap;
/*     */   
/*     */   FileFont(String paramString, Object paramObject) throws FontFormatException {
/*  88 */     super(paramString, paramObject);
/*     */   }
/*     */   
/*     */   FontStrike createStrike(FontStrikeDesc paramFontStrikeDesc) {
/*  92 */     if (!this.checkedNatives) {
/*  93 */       checkUseNatives();
/*     */     }
/*  95 */     return new FileFontStrike(this, paramFontStrikeDesc);
/*     */   }
/*     */   
/*     */   protected boolean checkUseNatives() {
/*  99 */     this.checkedNatives = true;
/* 100 */     return this.useNatives;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void close();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract ByteBuffer readBlock(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDoStyle(int paramInt) {
/* 116 */     return true;
/*     */   }
/*     */   
/*     */   void setFileToRemove(File paramFile, CreatedFontTracker paramCreatedFontTracker) {
/* 120 */     Disposer.addObjectRecord(this, new CreatedFontFileDisposerRecord(paramFile, paramCreatedFontTracker));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void setFileToRemove(Object paramObject, File paramFile, CreatedFontTracker paramCreatedFontTracker) {
/* 126 */     Disposer.addObjectRecord(paramObject, new CreatedFontFileDisposerRecord(paramFile, paramCreatedFontTracker));
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
/*     */   synchronized void deregisterFontAndClearStrikeCache() {
/* 159 */     SunFontManager sunFontManager = SunFontManager.getInstance();
/* 160 */     sunFontManager.deRegisterBadFont(this);
/*     */     
/* 162 */     for (Reference<FileFontStrike> reference : this.strikeCache.values()) {
/* 163 */       if (reference != null) {
/*     */ 
/*     */ 
/*     */         
/* 167 */         FileFontStrike fileFontStrike = reference.get();
/* 168 */         if (fileFontStrike != null && fileFontStrike.pScalerContext != 0L) {
/* 169 */           this.scaler.invalidateScalerContext(fileFontStrike.pScalerContext);
/*     */         }
/*     */       } 
/*     */     } 
/* 173 */     if (this.scaler != null) {
/* 174 */       this.scaler.disposeScaler();
/*     */     }
/* 176 */     this.scaler = FontScaler.getNullScaler();
/*     */   }
/*     */   
/*     */   StrikeMetrics getFontMetrics(long paramLong) {
/*     */     try {
/* 181 */       return getScaler().getFontMetrics(paramLong);
/* 182 */     } catch (FontScalerException fontScalerException) {
/* 183 */       this.scaler = FontScaler.getNullScaler();
/* 184 */       return getFontMetrics(paramLong);
/*     */     } 
/*     */   }
/*     */   
/*     */   float getGlyphAdvance(long paramLong, int paramInt) {
/*     */     try {
/* 190 */       return getScaler().getGlyphAdvance(paramLong, paramInt);
/* 191 */     } catch (FontScalerException fontScalerException) {
/* 192 */       this.scaler = FontScaler.getNullScaler();
/* 193 */       return getGlyphAdvance(paramLong, paramInt);
/*     */     } 
/*     */   }
/*     */   
/*     */   void getGlyphMetrics(long paramLong, int paramInt, Point2D.Float paramFloat) {
/*     */     try {
/* 199 */       getScaler().getGlyphMetrics(paramLong, paramInt, paramFloat);
/* 200 */     } catch (FontScalerException fontScalerException) {
/* 201 */       this.scaler = FontScaler.getNullScaler();
/* 202 */       getGlyphMetrics(paramLong, paramInt, paramFloat);
/*     */     } 
/*     */   }
/*     */   
/*     */   long getGlyphImage(long paramLong, int paramInt) {
/*     */     try {
/* 208 */       return getScaler().getGlyphImage(paramLong, paramInt);
/* 209 */     } catch (FontScalerException fontScalerException) {
/* 210 */       this.scaler = FontScaler.getNullScaler();
/* 211 */       return getGlyphImage(paramLong, paramInt);
/*     */     } 
/*     */   }
/*     */   
/*     */   Rectangle2D.Float getGlyphOutlineBounds(long paramLong, int paramInt) {
/*     */     try {
/* 217 */       return getScaler().getGlyphOutlineBounds(paramLong, paramInt);
/* 218 */     } catch (FontScalerException fontScalerException) {
/* 219 */       this.scaler = FontScaler.getNullScaler();
/* 220 */       return getGlyphOutlineBounds(paramLong, paramInt);
/*     */     } 
/*     */   }
/*     */   
/*     */   GeneralPath getGlyphOutline(long paramLong, int paramInt, float paramFloat1, float paramFloat2) {
/*     */     try {
/* 226 */       return getScaler().getGlyphOutline(paramLong, paramInt, paramFloat1, paramFloat2);
/* 227 */     } catch (FontScalerException fontScalerException) {
/* 228 */       this.scaler = FontScaler.getNullScaler();
/* 229 */       return getGlyphOutline(paramLong, paramInt, paramFloat1, paramFloat2);
/*     */     } 
/*     */   }
/*     */   
/*     */   GeneralPath getGlyphVectorOutline(long paramLong, int[] paramArrayOfint, int paramInt, float paramFloat1, float paramFloat2) {
/*     */     try {
/* 235 */       return getScaler().getGlyphVectorOutline(paramLong, paramArrayOfint, paramInt, paramFloat1, paramFloat2);
/* 236 */     } catch (FontScalerException fontScalerException) {
/* 237 */       this.scaler = FontScaler.getNullScaler();
/* 238 */       return getGlyphVectorOutline(paramLong, paramArrayOfint, paramInt, paramFloat1, paramFloat2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract FontScaler getScaler();
/*     */ 
/*     */   
/*     */   protected long getUnitsPerEm() {
/* 247 */     return getScaler().getUnitsPerEm();
/*     */   }
/*     */   
/*     */   private static class CreatedFontFileDisposerRecord
/*     */     implements DisposerRecord
/*     */   {
/* 253 */     File fontFile = null;
/*     */     
/*     */     CreatedFontTracker tracker;
/*     */     
/*     */     private CreatedFontFileDisposerRecord(File param1File, CreatedFontTracker param1CreatedFontTracker) {
/* 258 */       this.fontFile = param1File;
/* 259 */       this.tracker = param1CreatedFontTracker;
/*     */     }
/*     */     
/*     */     public void dispose() {
/* 263 */       AccessController.doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public Object run() {
/* 266 */               if (FileFont.CreatedFontFileDisposerRecord.this.fontFile != null) {
/*     */                 try {
/* 268 */                   if (FileFont.CreatedFontFileDisposerRecord.this.tracker != null) {
/* 269 */                     FileFont.CreatedFontFileDisposerRecord.this.tracker.subBytes((int)FileFont.CreatedFontFileDisposerRecord.this.fontFile.length());
/*     */                   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 277 */                   FileFont.CreatedFontFileDisposerRecord.this.fontFile.delete();
/*     */ 
/*     */                   
/* 280 */                   (SunFontManager.getInstance()).tmpFontFiles.remove(FileFont.CreatedFontFileDisposerRecord.this.fontFile);
/* 281 */                 } catch (Exception exception) {}
/*     */               }
/*     */               
/* 284 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   protected String getPublicFileName() {
/* 291 */     SecurityManager securityManager = System.getSecurityManager();
/* 292 */     if (securityManager == null) {
/* 293 */       return this.platName;
/*     */     }
/* 295 */     boolean bool = true;
/*     */     
/*     */     try {
/* 298 */       securityManager.checkPropertyAccess("java.io.tmpdir");
/* 299 */     } catch (SecurityException securityException) {
/* 300 */       bool = false;
/*     */     } 
/*     */     
/* 303 */     if (bool) {
/* 304 */       return this.platName;
/*     */     }
/*     */     
/* 307 */     final File f = new File(this.platName);
/*     */     
/* 309 */     Boolean bool1 = Boolean.FALSE;
/*     */     try {
/* 311 */       bool1 = AccessController.<Boolean>doPrivileged(new PrivilegedExceptionAction<Boolean>()
/*     */           {
/*     */             public Boolean run() {
/* 314 */               File file = new File(System.getProperty("java.io.tmpdir"));
/*     */               try {
/* 316 */                 String str1 = file.getCanonicalPath();
/* 317 */                 String str2 = f.getCanonicalPath();
/*     */                 
/* 319 */                 return Boolean.valueOf((str2 == null || str2.startsWith(str1)));
/* 320 */               } catch (IOException iOException) {
/* 321 */                 return Boolean.TRUE;
/*     */               }
/*     */             
/*     */             }
/*     */           });
/* 326 */     } catch (PrivilegedActionException privilegedActionException) {
/*     */ 
/*     */       
/* 329 */       bool1 = Boolean.TRUE;
/*     */     } 
/*     */     
/* 332 */     return bool1.booleanValue() ? "temp file" : this.platName;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/FileFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */