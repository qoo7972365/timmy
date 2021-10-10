/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.RenderingHints;
/*     */ 
/*     */ public class SunHints {
/*     */   private static final int NUM_KEYS = 10;
/*     */   private static final int VALS_PER_KEY = 8;
/*     */   public static final int INTKEY_RENDERING = 0;
/*     */   public static final int INTVAL_RENDER_DEFAULT = 0;
/*     */   public static final int INTVAL_RENDER_SPEED = 1;
/*     */   public static final int INTVAL_RENDER_QUALITY = 2;
/*     */   public static final int INTKEY_ANTIALIASING = 1;
/*     */   public static final int INTVAL_ANTIALIAS_DEFAULT = 0;
/*     */   public static final int INTVAL_ANTIALIAS_OFF = 1;
/*     */   public static final int INTVAL_ANTIALIAS_ON = 2;
/*     */   public static final int INTKEY_TEXT_ANTIALIASING = 2;
/*     */   public static final int INTVAL_TEXT_ANTIALIAS_DEFAULT = 0;
/*     */   public static final int INTVAL_TEXT_ANTIALIAS_OFF = 1;
/*     */   public static final int INTVAL_TEXT_ANTIALIAS_ON = 2;
/*     */   public static final int INTVAL_TEXT_ANTIALIAS_GASP = 3;
/*     */   public static final int INTVAL_TEXT_ANTIALIAS_LCD_HRGB = 4;
/*     */   public static final int INTVAL_TEXT_ANTIALIAS_LCD_HBGR = 5;
/*     */   public static final int INTVAL_TEXT_ANTIALIAS_LCD_VRGB = 6;
/*     */   public static final int INTVAL_TEXT_ANTIALIAS_LCD_VBGR = 7;
/*     */   public static final int INTKEY_FRACTIONALMETRICS = 3;
/*     */   public static final int INTVAL_FRACTIONALMETRICS_DEFAULT = 0;
/*     */   public static final int INTVAL_FRACTIONALMETRICS_OFF = 1;
/*     */   public static final int INTVAL_FRACTIONALMETRICS_ON = 2;
/*     */   public static final int INTKEY_DITHERING = 4;
/*     */   public static final int INTVAL_DITHER_DEFAULT = 0;
/*     */   public static final int INTVAL_DITHER_DISABLE = 1;
/*     */   public static final int INTVAL_DITHER_ENABLE = 2;
/*     */   public static final int INTKEY_INTERPOLATION = 5;
/*     */   public static final int INTVAL_INTERPOLATION_NEAREST_NEIGHBOR = 0;
/*     */   public static final int INTVAL_INTERPOLATION_BILINEAR = 1;
/*     */   public static final int INTVAL_INTERPOLATION_BICUBIC = 2;
/*     */   public static final int INTKEY_ALPHA_INTERPOLATION = 6;
/*     */   public static final int INTVAL_ALPHA_INTERPOLATION_DEFAULT = 0;
/*     */   public static final int INTVAL_ALPHA_INTERPOLATION_SPEED = 1;
/*     */   public static final int INTVAL_ALPHA_INTERPOLATION_QUALITY = 2;
/*     */   public static final int INTKEY_COLOR_RENDERING = 7;
/*     */   public static final int INTVAL_COLOR_RENDER_DEFAULT = 0;
/*     */   public static final int INTVAL_COLOR_RENDER_SPEED = 1;
/*     */   public static final int INTVAL_COLOR_RENDER_QUALITY = 2;
/*     */   public static final int INTKEY_STROKE_CONTROL = 8;
/*     */   public static final int INTVAL_STROKE_DEFAULT = 0;
/*     */   public static final int INTVAL_STROKE_NORMALIZE = 1;
/*     */   public static final int INTVAL_STROKE_PURE = 2;
/*     */   public static final int INTKEY_RESOLUTION_VARIANT = 9;
/*     */   public static final int INTVAL_RESOLUTION_VARIANT_DEFAULT = 0;
/*     */   public static final int INTVAL_RESOLUTION_VARIANT_OFF = 1;
/*     */   public static final int INTVAL_RESOLUTION_VARIANT_ON = 2;
/*     */   public static final int INTKEY_AATEXT_LCD_CONTRAST = 100;
/*     */   
/*     */   public static class Key
/*     */     extends RenderingHints.Key {
/*     */     public Key(int param1Int, String param1String) {
/*  58 */       super(param1Int);
/*  59 */       this.description = param1String;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     String description;
/*     */ 
/*     */     
/*     */     public final int getIndex() {
/*  68 */       return intKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final String toString() {
/*  75 */       return this.description;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isCompatibleValue(Object param1Object) {
/*  83 */       if (param1Object instanceof SunHints.Value) {
/*  84 */         return ((SunHints.Value)param1Object).isCompatibleKey(this);
/*     */       }
/*  86 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Value
/*     */   {
/*     */     private SunHints.Key myKey;
/*     */ 
/*     */     
/*     */     private int index;
/*     */ 
/*     */     
/*     */     private String description;
/*     */     
/* 102 */     private static Value[][] ValueObjects = new Value[10][8];
/*     */ 
/*     */ 
/*     */     
/*     */     private static synchronized void register(SunHints.Key param1Key, Value param1Value) {
/* 107 */       int i = param1Key.getIndex();
/* 108 */       int j = param1Value.getIndex();
/* 109 */       if (ValueObjects[i][j] != null) {
/* 110 */         throw new InternalError("duplicate index: " + j);
/*     */       }
/* 112 */       ValueObjects[i][j] = param1Value;
/*     */     }
/*     */     
/*     */     public static Value get(int param1Int1, int param1Int2) {
/* 116 */       return ValueObjects[param1Int1][param1Int2];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value(SunHints.Key param1Key, int param1Int, String param1String) {
/* 126 */       this.myKey = param1Key;
/* 127 */       this.index = param1Int;
/* 128 */       this.description = param1String;
/*     */       
/* 130 */       register(param1Key, this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int getIndex() {
/* 139 */       return this.index;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final String toString() {
/* 146 */       return this.description;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean isCompatibleKey(SunHints.Key param1Key) {
/* 154 */       return (this.myKey == param1Key);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int hashCode() {
/* 163 */       return System.identityHashCode(this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean equals(Object param1Object) {
/* 171 */       return (this == param1Object);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 272 */   public static final Key KEY_RENDERING = new Key(0, "Global rendering quality key");
/*     */ 
/*     */   
/* 275 */   public static final Object VALUE_RENDER_SPEED = new Value(KEY_RENDERING, 1, "Fastest rendering methods");
/*     */ 
/*     */ 
/*     */   
/* 279 */   public static final Object VALUE_RENDER_QUALITY = new Value(KEY_RENDERING, 2, "Highest quality rendering methods");
/*     */ 
/*     */ 
/*     */   
/* 283 */   public static final Object VALUE_RENDER_DEFAULT = new Value(KEY_RENDERING, 0, "Default rendering methods");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 291 */   public static final Key KEY_ANTIALIASING = new Key(1, "Global antialiasing enable key");
/*     */ 
/*     */   
/* 294 */   public static final Object VALUE_ANTIALIAS_ON = new Value(KEY_ANTIALIASING, 2, "Antialiased rendering mode");
/*     */ 
/*     */ 
/*     */   
/* 298 */   public static final Object VALUE_ANTIALIAS_OFF = new Value(KEY_ANTIALIASING, 1, "Nonantialiased rendering mode");
/*     */ 
/*     */ 
/*     */   
/* 302 */   public static final Object VALUE_ANTIALIAS_DEFAULT = new Value(KEY_ANTIALIASING, 0, "Default antialiasing rendering mode");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 310 */   public static final Key KEY_TEXT_ANTIALIASING = new Key(2, "Text-specific antialiasing enable key");
/*     */ 
/*     */   
/* 313 */   public static final Object VALUE_TEXT_ANTIALIAS_ON = new Value(KEY_TEXT_ANTIALIASING, 2, "Antialiased text mode");
/*     */ 
/*     */ 
/*     */   
/* 317 */   public static final Object VALUE_TEXT_ANTIALIAS_OFF = new Value(KEY_TEXT_ANTIALIASING, 1, "Nonantialiased text mode");
/*     */ 
/*     */ 
/*     */   
/* 321 */   public static final Object VALUE_TEXT_ANTIALIAS_DEFAULT = new Value(KEY_TEXT_ANTIALIASING, 0, "Default antialiasing text mode");
/*     */ 
/*     */ 
/*     */   
/* 325 */   public static final Object VALUE_TEXT_ANTIALIAS_GASP = new Value(KEY_TEXT_ANTIALIASING, 3, "gasp antialiasing text mode");
/*     */ 
/*     */ 
/*     */   
/* 329 */   public static final Object VALUE_TEXT_ANTIALIAS_LCD_HRGB = new Value(KEY_TEXT_ANTIALIASING, 4, "LCD HRGB antialiasing text mode");
/*     */ 
/*     */ 
/*     */   
/* 333 */   public static final Object VALUE_TEXT_ANTIALIAS_LCD_HBGR = new Value(KEY_TEXT_ANTIALIASING, 5, "LCD HBGR antialiasing text mode");
/*     */ 
/*     */ 
/*     */   
/* 337 */   public static final Object VALUE_TEXT_ANTIALIAS_LCD_VRGB = new Value(KEY_TEXT_ANTIALIASING, 6, "LCD VRGB antialiasing text mode");
/*     */ 
/*     */ 
/*     */   
/* 341 */   public static final Object VALUE_TEXT_ANTIALIAS_LCD_VBGR = new Value(KEY_TEXT_ANTIALIASING, 7, "LCD VBGR antialiasing text mode");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 349 */   public static final Key KEY_FRACTIONALMETRICS = new Key(3, "Fractional metrics enable key");
/*     */ 
/*     */   
/* 352 */   public static final Object VALUE_FRACTIONALMETRICS_ON = new Value(KEY_FRACTIONALMETRICS, 2, "Fractional text metrics mode");
/*     */ 
/*     */ 
/*     */   
/* 356 */   public static final Object VALUE_FRACTIONALMETRICS_OFF = new Value(KEY_FRACTIONALMETRICS, 1, "Integer text metrics mode");
/*     */ 
/*     */ 
/*     */   
/* 360 */   public static final Object VALUE_FRACTIONALMETRICS_DEFAULT = new Value(KEY_FRACTIONALMETRICS, 0, "Default fractional text metrics mode");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 368 */   public static final Key KEY_DITHERING = new Key(4, "Dithering quality key");
/*     */ 
/*     */   
/* 371 */   public static final Object VALUE_DITHER_ENABLE = new Value(KEY_DITHERING, 2, "Dithered rendering mode");
/*     */ 
/*     */ 
/*     */   
/* 375 */   public static final Object VALUE_DITHER_DISABLE = new Value(KEY_DITHERING, 1, "Nondithered rendering mode");
/*     */ 
/*     */ 
/*     */   
/* 379 */   public static final Object VALUE_DITHER_DEFAULT = new Value(KEY_DITHERING, 0, "Default dithering mode");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 387 */   public static final Key KEY_INTERPOLATION = new Key(5, "Image interpolation method key");
/*     */ 
/*     */   
/* 390 */   public static final Object VALUE_INTERPOLATION_NEAREST_NEIGHBOR = new Value(KEY_INTERPOLATION, 0, "Nearest Neighbor image interpolation mode");
/*     */ 
/*     */ 
/*     */   
/* 394 */   public static final Object VALUE_INTERPOLATION_BILINEAR = new Value(KEY_INTERPOLATION, 1, "Bilinear image interpolation mode");
/*     */ 
/*     */ 
/*     */   
/* 398 */   public static final Object VALUE_INTERPOLATION_BICUBIC = new Value(KEY_INTERPOLATION, 2, "Bicubic image interpolation mode");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 406 */   public static final Key KEY_ALPHA_INTERPOLATION = new Key(6, "Alpha blending interpolation method key");
/*     */ 
/*     */   
/* 409 */   public static final Object VALUE_ALPHA_INTERPOLATION_SPEED = new Value(KEY_ALPHA_INTERPOLATION, 1, "Fastest alpha blending methods");
/*     */ 
/*     */ 
/*     */   
/* 413 */   public static final Object VALUE_ALPHA_INTERPOLATION_QUALITY = new Value(KEY_ALPHA_INTERPOLATION, 2, "Highest quality alpha blending methods");
/*     */ 
/*     */ 
/*     */   
/* 417 */   public static final Object VALUE_ALPHA_INTERPOLATION_DEFAULT = new Value(KEY_ALPHA_INTERPOLATION, 0, "Default alpha blending methods");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 425 */   public static final Key KEY_COLOR_RENDERING = new Key(7, "Color rendering quality key");
/*     */ 
/*     */   
/* 428 */   public static final Object VALUE_COLOR_RENDER_SPEED = new Value(KEY_COLOR_RENDERING, 1, "Fastest color rendering mode");
/*     */ 
/*     */ 
/*     */   
/* 432 */   public static final Object VALUE_COLOR_RENDER_QUALITY = new Value(KEY_COLOR_RENDERING, 2, "Highest quality color rendering mode");
/*     */ 
/*     */ 
/*     */   
/* 436 */   public static final Object VALUE_COLOR_RENDER_DEFAULT = new Value(KEY_COLOR_RENDERING, 0, "Default color rendering mode");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 444 */   public static final Key KEY_STROKE_CONTROL = new Key(8, "Stroke normalization control key");
/*     */ 
/*     */   
/* 447 */   public static final Object VALUE_STROKE_DEFAULT = new Value(KEY_STROKE_CONTROL, 0, "Default stroke normalization");
/*     */ 
/*     */ 
/*     */   
/* 451 */   public static final Object VALUE_STROKE_NORMALIZE = new Value(KEY_STROKE_CONTROL, 1, "Normalize strokes for consistent rendering");
/*     */ 
/*     */ 
/*     */   
/* 455 */   public static final Object VALUE_STROKE_PURE = new Value(KEY_STROKE_CONTROL, 2, "Pure stroke conversion for accurate paths");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 463 */   public static final Key KEY_RESOLUTION_VARIANT = new Key(9, "Global image resolution variant key");
/*     */ 
/*     */   
/* 466 */   public static final Object VALUE_RESOLUTION_VARIANT_DEFAULT = new Value(KEY_RESOLUTION_VARIANT, 0, "Choose image resolutions based on a default heuristic");
/*     */ 
/*     */ 
/*     */   
/* 470 */   public static final Object VALUE_RESOLUTION_VARIANT_OFF = new Value(KEY_RESOLUTION_VARIANT, 1, "Use only the standard resolution of an image");
/*     */ 
/*     */ 
/*     */   
/* 474 */   public static final Object VALUE_RESOLUTION_VARIANT_ON = new Value(KEY_RESOLUTION_VARIANT, 2, "Always use resolution-specific variants of images");
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LCDContrastKey
/*     */     extends Key
/*     */   {
/*     */     public LCDContrastKey(int param1Int, String param1String) {
/* 482 */       super(param1Int, param1String);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean isCompatibleValue(Object param1Object) {
/* 490 */       if (param1Object instanceof Integer) {
/* 491 */         int i = ((Integer)param1Object).intValue();
/* 492 */         return (i >= 100 && i <= 250);
/*     */       } 
/* 494 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 503 */   public static final RenderingHints.Key KEY_TEXT_ANTIALIAS_LCD_CONTRAST = new LCDContrastKey(100, "Text-specific LCD contrast key");
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/SunHints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */