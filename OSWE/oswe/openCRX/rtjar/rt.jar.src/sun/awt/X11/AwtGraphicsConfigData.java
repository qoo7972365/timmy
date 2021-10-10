/*     */ package sun.awt.X11;
/*     */ 
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ public class AwtGraphicsConfigData
/*     */   extends XWrapperBase
/*     */ {
/*     */   private final boolean should_free_memory;
/*   9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*     */   public static int getSize() {
/*  11 */     return 208; } public int getDataSize() {
/*  12 */     return getSize();
/*     */   }
/*     */   
/*     */   public long getPData() {
/*  16 */     return this.pData;
/*     */   }
/*     */   
/*     */   public AwtGraphicsConfigData(long paramLong) {
/*  20 */     log.finest("Creating");
/*  21 */     this.pData = paramLong;
/*  22 */     this.should_free_memory = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AwtGraphicsConfigData() {
/*  27 */     log.finest("Creating");
/*  28 */     this.pData = this.unsafe.allocateMemory(getSize());
/*  29 */     this.should_free_memory = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/*  34 */     log.finest("Disposing");
/*  35 */     if (this.should_free_memory) {
/*  36 */       log.finest("freeing memory");
/*  37 */       this.unsafe.freeMemory(this.pData);
/*     */     } 
/*     */   }
/*  40 */   public int get_awt_depth() { log.finest(""); return Native.getInt(this.pData + 0L); }
/*  41 */   public void set_awt_depth(int paramInt) { log.finest(""); Native.putInt(this.pData + 0L, paramInt); }
/*  42 */   public long get_awt_cmap() { log.finest(""); return Native.getLong(this.pData + 8L); }
/*  43 */   public void set_awt_cmap(long paramLong) { log.finest(""); Native.putLong(this.pData + 8L, paramLong); }
/*  44 */   public XVisualInfo get_awt_visInfo() { log.finest(""); return new XVisualInfo(this.pData + 16L); }
/*  45 */   public int get_awt_num_colors() { log.finest(""); return Native.getInt(this.pData + 80L); }
/*  46 */   public void set_awt_num_colors(int paramInt) { log.finest(""); Native.putInt(this.pData + 80L, paramInt); }
/*  47 */   public awtImageData get_awtImage(int paramInt) { log.finest(""); return (Native.getLong(this.pData + 88L) != 0L) ? new awtImageData(Native.getLong(this.pData + 88L) + (paramInt * 560)) : null; }
/*  48 */   public long get_awtImage() { log.finest(""); return Native.getLong(this.pData + 88L); }
/*  49 */   public void set_awtImage(long paramLong) { log.finest(""); Native.putLong(this.pData + 88L, paramLong); }
/*  50 */   public long get_AwtColorMatch(int paramInt) { log.finest(""); return Native.getLong(this.pData + 96L) + (paramInt * Native.getLongSize()); }
/*  51 */   public long get_AwtColorMatch() { log.finest(""); return Native.getLong(this.pData + 96L); }
/*  52 */   public void set_AwtColorMatch(long paramLong) { log.finest(""); Native.putLong(this.pData + 96L, paramLong); }
/*  53 */   public long get_monoImage(int paramInt) { log.finest(""); return Native.getLong(this.pData + 104L) + (paramInt * Native.getLongSize()); }
/*  54 */   public long get_monoImage() { log.finest(""); return Native.getLong(this.pData + 104L); }
/*  55 */   public void set_monoImage(long paramLong) { log.finest(""); Native.putLong(this.pData + 104L, paramLong); }
/*  56 */   public long get_monoPixmap() { log.finest(""); return Native.getLong(this.pData + 112L); }
/*  57 */   public void set_monoPixmap(long paramLong) { log.finest(""); Native.putLong(this.pData + 112L, paramLong); }
/*  58 */   public int get_monoPixmapWidth() { log.finest(""); return Native.getInt(this.pData + 120L); }
/*  59 */   public void set_monoPixmapWidth(int paramInt) { log.finest(""); Native.putInt(this.pData + 120L, paramInt); }
/*  60 */   public int get_monoPixmapHeight() { log.finest(""); return Native.getInt(this.pData + 124L); }
/*  61 */   public void set_monoPixmapHeight(int paramInt) { log.finest(""); Native.putInt(this.pData + 124L, paramInt); }
/*  62 */   public long get_monoPixmapGC() { log.finest(""); return Native.getLong(this.pData + 128L); }
/*  63 */   public void set_monoPixmapGC(long paramLong) { log.finest(""); Native.putLong(this.pData + 128L, paramLong); }
/*  64 */   public int get_pixelStride() { log.finest(""); return Native.getInt(this.pData + 136L); }
/*  65 */   public void set_pixelStride(int paramInt) { log.finest(""); Native.putInt(this.pData + 136L, paramInt); }
/*  66 */   public ColorData get_color_data(int paramInt) { log.finest(""); return (Native.getLong(this.pData + 144L) != 0L) ? new ColorData(Native.getLong(this.pData + 144L) + (paramInt * 88)) : null; }
/*  67 */   public long get_color_data() { log.finest(""); return Native.getLong(this.pData + 144L); }
/*  68 */   public void set_color_data(long paramLong) { log.finest(""); Native.putLong(this.pData + 144L, paramLong); }
/*  69 */   public long get_glxInfo(int paramInt) { log.finest(""); return Native.getLong(this.pData + 152L) + (paramInt * Native.getLongSize()); }
/*  70 */   public long get_glxInfo() { log.finest(""); return Native.getLong(this.pData + 152L); }
/*  71 */   public void set_glxInfo(long paramLong) { log.finest(""); Native.putLong(this.pData + 152L, paramLong); }
/*  72 */   public int get_isTranslucencySupported() { log.finest(""); return Native.getInt(this.pData + 160L); }
/*  73 */   public void set_isTranslucencySupported(int paramInt) { log.finest(""); Native.putInt(this.pData + 160L, paramInt); } public XRenderPictFormat get_renderPictFormat() {
/*  74 */     log.finest(""); return new XRenderPictFormat(this.pData + 168L);
/*     */   }
/*     */   
/*     */   String getName() {
/*  78 */     return "AwtGraphicsConfigData";
/*     */   }
/*     */ 
/*     */   
/*     */   String getFieldsAsString() {
/*  83 */     StringBuilder stringBuilder = new StringBuilder(640);
/*     */     
/*  85 */     stringBuilder.append("awt_depth = ").append(get_awt_depth()).append(", ");
/*  86 */     stringBuilder.append("awt_cmap = ").append(get_awt_cmap()).append(", ");
/*  87 */     stringBuilder.append("awt_visInfo = ").append(get_awt_visInfo()).append(", ");
/*  88 */     stringBuilder.append("awt_num_colors = ").append(get_awt_num_colors()).append(", ");
/*  89 */     stringBuilder.append("awtImage = ").append(get_awtImage()).append(", ");
/*  90 */     stringBuilder.append("AwtColorMatch = ").append(get_AwtColorMatch()).append(", ");
/*  91 */     stringBuilder.append("monoImage = ").append(get_monoImage()).append(", ");
/*  92 */     stringBuilder.append("monoPixmap = ").append(get_monoPixmap()).append(", ");
/*  93 */     stringBuilder.append("monoPixmapWidth = ").append(get_monoPixmapWidth()).append(", ");
/*  94 */     stringBuilder.append("monoPixmapHeight = ").append(get_monoPixmapHeight()).append(", ");
/*  95 */     stringBuilder.append("monoPixmapGC = ").append(get_monoPixmapGC()).append(", ");
/*  96 */     stringBuilder.append("pixelStride = ").append(get_pixelStride()).append(", ");
/*  97 */     stringBuilder.append("color_data = ").append(get_color_data()).append(", ");
/*  98 */     stringBuilder.append("glxInfo = ").append(get_glxInfo()).append(", ");
/*  99 */     stringBuilder.append("isTranslucencySupported = ").append(get_isTranslucencySupported()).append(", ");
/* 100 */     stringBuilder.append("renderPictFormat = ").append(get_renderPictFormat()).append(", ");
/* 101 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/AwtGraphicsConfigData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */