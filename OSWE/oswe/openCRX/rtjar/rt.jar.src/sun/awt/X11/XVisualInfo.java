/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XVisualInfo
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 64; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public XVisualInfo(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XVisualInfo() {
/* 27 */     log.finest("Creating");
/* 28 */     this.pData = this.unsafe.allocateMemory(getSize());
/* 29 */     this.should_free_memory = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void dispose() {
/* 34 */     log.finest("Disposing");
/* 35 */     if (this.should_free_memory) {
/* 36 */       log.finest("freeing memory");
/* 37 */       this.unsafe.freeMemory(this.pData);
/*    */     } 
/*    */   }
/* 40 */   public long get_visual(int paramInt) { log.finest(""); return Native.getLong(this.pData + 0L) + (paramInt * Native.getLongSize()); }
/* 41 */   public long get_visual() { log.finest(""); return Native.getLong(this.pData + 0L); }
/* 42 */   public void set_visual(long paramLong) { log.finest(""); Native.putLong(this.pData + 0L, paramLong); }
/* 43 */   public long get_visualid() { log.finest(""); return Native.getLong(this.pData + 8L); }
/* 44 */   public void set_visualid(long paramLong) { log.finest(""); Native.putLong(this.pData + 8L, paramLong); }
/* 45 */   public int get_screen() { log.finest(""); return Native.getInt(this.pData + 16L); }
/* 46 */   public void set_screen(int paramInt) { log.finest(""); Native.putInt(this.pData + 16L, paramInt); }
/* 47 */   public int get_depth() { log.finest(""); return Native.getInt(this.pData + 20L); }
/* 48 */   public void set_depth(int paramInt) { log.finest(""); Native.putInt(this.pData + 20L, paramInt); }
/* 49 */   public int get_class() { log.finest(""); return Native.getInt(this.pData + 24L); }
/* 50 */   public void set_class(int paramInt) { log.finest(""); Native.putInt(this.pData + 24L, paramInt); }
/* 51 */   public long get_red_mask() { log.finest(""); return Native.getLong(this.pData + 32L); }
/* 52 */   public void set_red_mask(long paramLong) { log.finest(""); Native.putLong(this.pData + 32L, paramLong); }
/* 53 */   public long get_green_mask() { log.finest(""); return Native.getLong(this.pData + 40L); }
/* 54 */   public void set_green_mask(long paramLong) { log.finest(""); Native.putLong(this.pData + 40L, paramLong); }
/* 55 */   public long get_blue_mask() { log.finest(""); return Native.getLong(this.pData + 48L); }
/* 56 */   public void set_blue_mask(long paramLong) { log.finest(""); Native.putLong(this.pData + 48L, paramLong); }
/* 57 */   public int get_colormap_size() { log.finest(""); return Native.getInt(this.pData + 56L); }
/* 58 */   public void set_colormap_size(int paramInt) { log.finest(""); Native.putInt(this.pData + 56L, paramInt); }
/* 59 */   public int get_bits_per_rgb() { log.finest(""); return Native.getInt(this.pData + 60L); } public void set_bits_per_rgb(int paramInt) {
/* 60 */     log.finest(""); Native.putInt(this.pData + 60L, paramInt);
/*    */   }
/*    */   
/*    */   String getName() {
/* 64 */     return "XVisualInfo";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 69 */     StringBuilder stringBuilder = new StringBuilder(400);
/*    */     
/* 71 */     stringBuilder.append("visual = ").append(get_visual()).append(", ");
/* 72 */     stringBuilder.append("visualid = ").append(get_visualid()).append(", ");
/* 73 */     stringBuilder.append("screen = ").append(get_screen()).append(", ");
/* 74 */     stringBuilder.append("depth = ").append(get_depth()).append(", ");
/* 75 */     stringBuilder.append("class = ").append(get_class()).append(", ");
/* 76 */     stringBuilder.append("red_mask = ").append(get_red_mask()).append(", ");
/* 77 */     stringBuilder.append("green_mask = ").append(get_green_mask()).append(", ");
/* 78 */     stringBuilder.append("blue_mask = ").append(get_blue_mask()).append(", ");
/* 79 */     stringBuilder.append("colormap_size = ").append(get_colormap_size()).append(", ");
/* 80 */     stringBuilder.append("bits_per_rgb = ").append(get_bits_per_rgb()).append(", ");
/* 81 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XVisualInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */