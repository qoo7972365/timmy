/*     */ package sun.awt.X11;
/*     */ 
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ public class XSizeHints
/*     */   extends XWrapperBase
/*     */ {
/*     */   private final boolean should_free_memory;
/*   9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*     */   public static int getSize() {
/*  11 */     return 80; } public int getDataSize() {
/*  12 */     return getSize();
/*     */   }
/*     */   
/*     */   public long getPData() {
/*  16 */     return this.pData;
/*     */   }
/*     */   
/*     */   public XSizeHints(long paramLong) {
/*  20 */     log.finest("Creating");
/*  21 */     this.pData = paramLong;
/*  22 */     this.should_free_memory = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public XSizeHints() {
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
/*  40 */   public long get_flags() { log.finest(""); return Native.getLong(this.pData + 0L); }
/*  41 */   public void set_flags(long paramLong) { log.finest(""); Native.putLong(this.pData + 0L, paramLong); }
/*  42 */   public int get_x() { log.finest(""); return Native.getInt(this.pData + 8L); }
/*  43 */   public void set_x(int paramInt) { log.finest(""); Native.putInt(this.pData + 8L, paramInt); }
/*  44 */   public int get_y() { log.finest(""); return Native.getInt(this.pData + 12L); }
/*  45 */   public void set_y(int paramInt) { log.finest(""); Native.putInt(this.pData + 12L, paramInt); }
/*  46 */   public int get_width() { log.finest(""); return Native.getInt(this.pData + 16L); }
/*  47 */   public void set_width(int paramInt) { log.finest(""); Native.putInt(this.pData + 16L, paramInt); }
/*  48 */   public int get_height() { log.finest(""); return Native.getInt(this.pData + 20L); }
/*  49 */   public void set_height(int paramInt) { log.finest(""); Native.putInt(this.pData + 20L, paramInt); }
/*  50 */   public int get_min_width() { log.finest(""); return Native.getInt(this.pData + 24L); }
/*  51 */   public void set_min_width(int paramInt) { log.finest(""); Native.putInt(this.pData + 24L, paramInt); }
/*  52 */   public int get_min_height() { log.finest(""); return Native.getInt(this.pData + 28L); }
/*  53 */   public void set_min_height(int paramInt) { log.finest(""); Native.putInt(this.pData + 28L, paramInt); }
/*  54 */   public int get_max_width() { log.finest(""); return Native.getInt(this.pData + 32L); }
/*  55 */   public void set_max_width(int paramInt) { log.finest(""); Native.putInt(this.pData + 32L, paramInt); }
/*  56 */   public int get_max_height() { log.finest(""); return Native.getInt(this.pData + 36L); }
/*  57 */   public void set_max_height(int paramInt) { log.finest(""); Native.putInt(this.pData + 36L, paramInt); }
/*  58 */   public int get_width_inc() { log.finest(""); return Native.getInt(this.pData + 40L); }
/*  59 */   public void set_width_inc(int paramInt) { log.finest(""); Native.putInt(this.pData + 40L, paramInt); }
/*  60 */   public int get_height_inc() { log.finest(""); return Native.getInt(this.pData + 44L); }
/*  61 */   public void set_height_inc(int paramInt) { log.finest(""); Native.putInt(this.pData + 44L, paramInt); }
/*  62 */   public int get_min_aspect_x() { log.finest(""); return Native.getInt(this.pData + 48L); }
/*  63 */   public void set_min_aspect_x(int paramInt) { log.finest(""); Native.putInt(this.pData + 48L, paramInt); }
/*  64 */   public int get_min_aspect_y() { log.finest(""); return Native.getInt(this.pData + 52L); }
/*  65 */   public void set_min_aspect_y(int paramInt) { log.finest(""); Native.putInt(this.pData + 52L, paramInt); }
/*  66 */   public int get_max_aspect_x() { log.finest(""); return Native.getInt(this.pData + 56L); }
/*  67 */   public void set_max_aspect_x(int paramInt) { log.finest(""); Native.putInt(this.pData + 56L, paramInt); }
/*  68 */   public int get_max_aspect_y() { log.finest(""); return Native.getInt(this.pData + 60L); }
/*  69 */   public void set_max_aspect_y(int paramInt) { log.finest(""); Native.putInt(this.pData + 60L, paramInt); }
/*  70 */   public int get_base_width() { log.finest(""); return Native.getInt(this.pData + 64L); }
/*  71 */   public void set_base_width(int paramInt) { log.finest(""); Native.putInt(this.pData + 64L, paramInt); }
/*  72 */   public int get_base_height() { log.finest(""); return Native.getInt(this.pData + 68L); }
/*  73 */   public void set_base_height(int paramInt) { log.finest(""); Native.putInt(this.pData + 68L, paramInt); }
/*  74 */   public int get_win_gravity() { log.finest(""); return Native.getInt(this.pData + 72L); } public void set_win_gravity(int paramInt) {
/*  75 */     log.finest(""); Native.putInt(this.pData + 72L, paramInt);
/*     */   }
/*     */   
/*     */   String getName() {
/*  79 */     return "XSizeHints";
/*     */   }
/*     */ 
/*     */   
/*     */   String getFieldsAsString() {
/*  84 */     StringBuilder stringBuilder = new StringBuilder(720);
/*     */     
/*  86 */     stringBuilder.append("flags = ").append(get_flags()).append(", ");
/*  87 */     stringBuilder.append("x = ").append(get_x()).append(", ");
/*  88 */     stringBuilder.append("y = ").append(get_y()).append(", ");
/*  89 */     stringBuilder.append("width = ").append(get_width()).append(", ");
/*  90 */     stringBuilder.append("height = ").append(get_height()).append(", ");
/*  91 */     stringBuilder.append("min_width = ").append(get_min_width()).append(", ");
/*  92 */     stringBuilder.append("min_height = ").append(get_min_height()).append(", ");
/*  93 */     stringBuilder.append("max_width = ").append(get_max_width()).append(", ");
/*  94 */     stringBuilder.append("max_height = ").append(get_max_height()).append(", ");
/*  95 */     stringBuilder.append("width_inc = ").append(get_width_inc()).append(", ");
/*  96 */     stringBuilder.append("height_inc = ").append(get_height_inc()).append(", ");
/*  97 */     stringBuilder.append("min_aspect_x = ").append(get_min_aspect_x()).append(", ");
/*  98 */     stringBuilder.append("min_aspect_y = ").append(get_min_aspect_y()).append(", ");
/*  99 */     stringBuilder.append("max_aspect_x = ").append(get_max_aspect_x()).append(", ");
/* 100 */     stringBuilder.append("max_aspect_y = ").append(get_max_aspect_y()).append(", ");
/* 101 */     stringBuilder.append("base_width = ").append(get_base_width()).append(", ");
/* 102 */     stringBuilder.append("base_height = ").append(get_base_height()).append(", ");
/* 103 */     stringBuilder.append("win_gravity = ").append(get_win_gravity()).append(", ");
/* 104 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XSizeHints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */