/*     */ package sun.font;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CompositeFont
/*     */   extends Font2D
/*     */ {
/*     */   private boolean[] deferredInitialisation;
/*     */   String[] componentFileNames;
/*     */   String[] componentNames;
/*     */   private PhysicalFont[] components;
/*     */   int numSlots;
/*     */   int numMetricsSlots;
/*     */   int[] exclusionRanges;
/*     */   int[] maxIndices;
/*  54 */   int numGlyphs = 0;
/*  55 */   int localeSlot = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isStdComposite = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompositeFont(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt, int[] paramArrayOfint1, int[] paramArrayOfint2, boolean paramBoolean, SunFontManager paramSunFontManager) {
/*  65 */     this.handle = new Font2DHandle(this);
/*  66 */     this.fullName = paramString;
/*  67 */     this.componentFileNames = paramArrayOfString1;
/*  68 */     this.componentNames = paramArrayOfString2;
/*  69 */     if (paramArrayOfString2 == null) {
/*  70 */       this.numSlots = this.componentFileNames.length;
/*     */     } else {
/*  72 */       this.numSlots = this.componentNames.length;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     this.numSlots = (this.numSlots <= 254) ? this.numSlots : 254;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     this.numMetricsSlots = paramInt;
/*  86 */     this.exclusionRanges = paramArrayOfint1;
/*  87 */     this.maxIndices = paramArrayOfint2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     if (paramSunFontManager.getEUDCFont() != null) {
/*  96 */       int j = this.numMetricsSlots;
/*  97 */       int k = this.numSlots - j;
/*  98 */       this.numSlots++;
/*  99 */       if (this.componentNames != null) {
/* 100 */         this.componentNames = new String[this.numSlots];
/* 101 */         System.arraycopy(paramArrayOfString2, 0, this.componentNames, 0, j);
/* 102 */         this.componentNames[j] = paramSunFontManager.getEUDCFont().getFontName(null);
/* 103 */         System.arraycopy(paramArrayOfString2, j, this.componentNames, j + 1, k);
/*     */       } 
/*     */       
/* 106 */       if (this.componentFileNames != null) {
/* 107 */         this.componentFileNames = new String[this.numSlots];
/* 108 */         System.arraycopy(paramArrayOfString1, 0, this.componentFileNames, 0, j);
/*     */         
/* 110 */         System.arraycopy(paramArrayOfString1, j, this.componentFileNames, j + 1, k);
/*     */       } 
/*     */       
/* 113 */       this.components = new PhysicalFont[this.numSlots];
/* 114 */       this.components[j] = paramSunFontManager.getEUDCFont();
/* 115 */       this.deferredInitialisation = new boolean[this.numSlots];
/* 116 */       if (paramBoolean) {
/* 117 */         for (byte b = 0; b < this.numSlots - 1; b++) {
/* 118 */           this.deferredInitialisation[b] = true;
/*     */         }
/*     */       }
/*     */     } else {
/* 122 */       this.components = new PhysicalFont[this.numSlots];
/* 123 */       this.deferredInitialisation = new boolean[this.numSlots];
/* 124 */       if (paramBoolean) {
/* 125 */         for (byte b = 0; b < this.numSlots; b++) {
/* 126 */           this.deferredInitialisation[b] = true;
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 131 */     this.fontRank = 2;
/*     */     
/* 133 */     int i = this.fullName.indexOf('.');
/* 134 */     if (i > 0) {
/* 135 */       this.familyName = this.fullName.substring(0, i);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 140 */       if (i + 1 < this.fullName.length()) {
/* 141 */         String str = this.fullName.substring(i + 1);
/* 142 */         if ("plain".equals(str)) {
/* 143 */           this.style = 0;
/* 144 */         } else if ("bold".equals(str)) {
/* 145 */           this.style = 1;
/* 146 */         } else if ("italic".equals(str)) {
/* 147 */           this.style = 2;
/* 148 */         } else if ("bolditalic".equals(str)) {
/* 149 */           this.style = 3;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 153 */       this.familyName = this.fullName;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CompositeFont(PhysicalFont[] paramArrayOfPhysicalFont) {
/* 162 */     this.isStdComposite = false;
/* 163 */     this.handle = new Font2DHandle(this);
/* 164 */     this.fullName = (paramArrayOfPhysicalFont[0]).fullName;
/* 165 */     this.familyName = (paramArrayOfPhysicalFont[0]).familyName;
/* 166 */     this.style = (paramArrayOfPhysicalFont[0]).style;
/*     */     
/* 168 */     this.numMetricsSlots = 1;
/* 169 */     this.numSlots = paramArrayOfPhysicalFont.length;
/*     */     
/* 171 */     this.components = new PhysicalFont[this.numSlots];
/* 172 */     System.arraycopy(paramArrayOfPhysicalFont, 0, this.components, 0, this.numSlots);
/* 173 */     this.deferredInitialisation = new boolean[this.numSlots];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CompositeFont(PhysicalFont paramPhysicalFont, CompositeFont paramCompositeFont) {
/* 183 */     this.isStdComposite = false;
/* 184 */     this.handle = new Font2DHandle(this);
/* 185 */     this.fullName = paramPhysicalFont.fullName;
/* 186 */     this.familyName = paramPhysicalFont.familyName;
/* 187 */     this.style = paramPhysicalFont.style;
/*     */     
/* 189 */     this.numMetricsSlots = 1;
/* 190 */     paramCompositeFont.numSlots++;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     synchronized (FontManagerFactory.getInstance()) {
/* 200 */       this.components = new PhysicalFont[this.numSlots];
/* 201 */       this.components[0] = paramPhysicalFont;
/* 202 */       System.arraycopy(paramCompositeFont.components, 0, this.components, 1, paramCompositeFont.numSlots);
/*     */ 
/*     */       
/* 205 */       if (paramCompositeFont.componentNames != null) {
/* 206 */         this.componentNames = new String[this.numSlots];
/* 207 */         this.componentNames[0] = paramPhysicalFont.fullName;
/* 208 */         System.arraycopy(paramCompositeFont.componentNames, 0, this.componentNames, 1, paramCompositeFont.numSlots);
/*     */       } 
/*     */       
/* 211 */       if (paramCompositeFont.componentFileNames != null) {
/* 212 */         this.componentFileNames = new String[this.numSlots];
/* 213 */         this.componentFileNames[0] = null;
/* 214 */         System.arraycopy(paramCompositeFont.componentFileNames, 0, this.componentFileNames, 1, paramCompositeFont.numSlots);
/*     */       } 
/*     */       
/* 217 */       this.deferredInitialisation = new boolean[this.numSlots];
/* 218 */       this.deferredInitialisation[0] = false;
/* 219 */       System.arraycopy(paramCompositeFont.deferredInitialisation, 0, this.deferredInitialisation, 1, paramCompositeFont.numSlots);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doDeferredInitialisation(int paramInt) {
/* 260 */     if (!this.deferredInitialisation[paramInt]) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     SunFontManager sunFontManager = SunFontManager.getInstance();
/* 270 */     synchronized (sunFontManager) {
/* 271 */       if (this.componentNames == null) {
/* 272 */         this.componentNames = new String[this.numSlots];
/*     */       }
/* 274 */       if (this.components[paramInt] == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 284 */         if (this.componentFileNames != null && this.componentFileNames[paramInt] != null)
/*     */         {
/* 286 */           this.components[paramInt] = sunFontManager
/* 287 */             .initialiseDeferredFont(this.componentFileNames[paramInt]);
/*     */         }
/*     */         
/* 290 */         if (this.components[paramInt] == null) {
/* 291 */           this.components[paramInt] = sunFontManager.getDefaultPhysicalFont();
/*     */         }
/* 293 */         String str = this.components[paramInt].getFontName(null);
/* 294 */         if (this.componentNames[paramInt] == null) {
/* 295 */           this.componentNames[paramInt] = str;
/* 296 */         } else if (!this.componentNames[paramInt].equalsIgnoreCase(str)) {
/*     */ 
/*     */           
/*     */           try {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 304 */             this.components[paramInt] = (PhysicalFont)sunFontManager
/* 305 */               .findFont2D(this.componentNames[paramInt], this.style, 1);
/*     */           
/*     */           }
/* 308 */           catch (ClassCastException classCastException) {
/*     */             
/* 310 */             this.components[paramInt] = sunFontManager.getDefaultPhysicalFont();
/*     */           } 
/*     */         } 
/*     */       } 
/* 314 */       this.deferredInitialisation[paramInt] = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void replaceComponentFont(PhysicalFont paramPhysicalFont1, PhysicalFont paramPhysicalFont2) {
/* 320 */     if (this.components == null) {
/*     */       return;
/*     */     }
/* 323 */     for (byte b = 0; b < this.numSlots; b++) {
/* 324 */       if (this.components[b] == paramPhysicalFont1) {
/* 325 */         this.components[b] = paramPhysicalFont2;
/* 326 */         if (this.componentNames != null) {
/* 327 */           this.componentNames[b] = paramPhysicalFont2.getFontName(null);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExcludedChar(int paramInt1, int paramInt2) {
/* 335 */     if (this.exclusionRanges == null || this.maxIndices == null || paramInt1 >= this.numMetricsSlots)
/*     */     {
/* 337 */       return false;
/*     */     }
/*     */     
/* 340 */     int i = 0;
/* 341 */     int j = this.maxIndices[paramInt1];
/* 342 */     if (paramInt1 > 0) {
/* 343 */       i = this.maxIndices[paramInt1 - 1];
/*     */     }
/* 345 */     int k = i;
/* 346 */     while (j > k) {
/* 347 */       if (paramInt2 >= this.exclusionRanges[k] && paramInt2 <= this.exclusionRanges[k + 1])
/*     */       {
/* 349 */         return true;
/*     */       }
/* 351 */       k += 2;
/*     */     } 
/* 353 */     return false;
/*     */   }
/*     */   
/*     */   public void getStyleMetrics(float paramFloat, float[] paramArrayOffloat, int paramInt) {
/* 357 */     PhysicalFont physicalFont = getSlotFont(0);
/* 358 */     if (physicalFont == null) {
/* 359 */       super.getStyleMetrics(paramFloat, paramArrayOffloat, paramInt);
/*     */     } else {
/* 361 */       physicalFont.getStyleMetrics(paramFloat, paramArrayOffloat, paramInt);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getNumSlots() {
/* 366 */     return this.numSlots;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PhysicalFont getSlotFont(int paramInt) {
/* 375 */     if (this.deferredInitialisation[paramInt]) {
/* 376 */       doDeferredInitialisation(paramInt);
/*     */     }
/* 378 */     SunFontManager sunFontManager = SunFontManager.getInstance();
/*     */     try {
/* 380 */       PhysicalFont physicalFont = this.components[paramInt];
/* 381 */       if (physicalFont == null) {
/*     */         
/*     */         try {
/* 384 */           physicalFont = (PhysicalFont)sunFontManager.findFont2D(this.componentNames[paramInt], this.style, 1);
/*     */           
/* 386 */           this.components[paramInt] = physicalFont;
/* 387 */         } catch (ClassCastException classCastException) {
/* 388 */           physicalFont = sunFontManager.getDefaultPhysicalFont();
/*     */         } 
/*     */       }
/* 391 */       return physicalFont;
/* 392 */     } catch (Exception exception) {
/* 393 */       return sunFontManager.getDefaultPhysicalFont();
/*     */     } 
/*     */   }
/*     */   
/*     */   FontStrike createStrike(FontStrikeDesc paramFontStrikeDesc) {
/* 398 */     return new CompositeStrike(this, paramFontStrikeDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStdComposite() {
/* 407 */     return this.isStdComposite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getValidatedGlyphCode(int paramInt) {
/* 416 */     int i = paramInt >>> 24;
/* 417 */     if (i >= this.numSlots) {
/* 418 */       return getMapper().getMissingGlyphCode();
/*     */     }
/*     */     
/* 421 */     int j = paramInt & 0xFFFFFF;
/* 422 */     PhysicalFont physicalFont = getSlotFont(i);
/* 423 */     if (physicalFont.getValidatedGlyphCode(j) == physicalFont
/* 424 */       .getMissingGlyphCode()) {
/* 425 */       return getMapper().getMissingGlyphCode();
/*     */     }
/* 427 */     return paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public CharToGlyphMapper getMapper() {
/* 432 */     if (this.mapper == null) {
/* 433 */       this.mapper = new CompositeGlyphMapper(this);
/*     */     }
/* 435 */     return this.mapper;
/*     */   }
/*     */   
/*     */   public boolean hasSupplementaryChars() {
/* 439 */     for (byte b = 0; b < this.numSlots; b++) {
/* 440 */       if (getSlotFont(b).hasSupplementaryChars()) {
/* 441 */         return true;
/*     */       }
/*     */     } 
/* 444 */     return false;
/*     */   }
/*     */   
/*     */   public int getNumGlyphs() {
/* 448 */     if (this.numGlyphs == 0) {
/* 449 */       this.numGlyphs = getMapper().getNumGlyphs();
/*     */     }
/* 451 */     return this.numGlyphs;
/*     */   }
/*     */   
/*     */   public int getMissingGlyphCode() {
/* 455 */     return getMapper().getMissingGlyphCode();
/*     */   }
/*     */   
/*     */   public boolean canDisplay(char paramChar) {
/* 459 */     return getMapper().canDisplay(paramChar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useAAForPtSize(int paramInt) {
/* 469 */     if (this.localeSlot == -1) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 474 */       int i = this.numMetricsSlots;
/* 475 */       if (i == 1 && !isStdComposite()) {
/* 476 */         i = this.numSlots;
/*     */       }
/* 478 */       for (byte b = 0; b < i; b++) {
/* 479 */         if (getSlotFont(b).supportsEncoding(null)) {
/* 480 */           this.localeSlot = b;
/*     */           break;
/*     */         } 
/*     */       } 
/* 484 */       if (this.localeSlot == -1) {
/* 485 */         this.localeSlot = 0;
/*     */       }
/*     */     } 
/* 488 */     return getSlotFont(this.localeSlot).useAAForPtSize(paramInt);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 492 */     String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("line.separator"));
/*     */     
/* 494 */     String str2 = "";
/* 495 */     for (byte b = 0; b < this.numSlots; b++) {
/* 496 */       str2 = str2 + "    Slot[" + b + "]=" + getSlotFont(b) + str1;
/*     */     }
/* 498 */     return "** Composite Font: Family=" + this.familyName + " Name=" + this.fullName + " style=" + this.style + str1 + str2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/CompositeFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */