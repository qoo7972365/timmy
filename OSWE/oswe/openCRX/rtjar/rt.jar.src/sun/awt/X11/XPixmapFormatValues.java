/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XPixmapFormatValues
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 12; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public XPixmapFormatValues(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XPixmapFormatValues() {
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
/* 40 */   public int get_depth() { log.finest(""); return Native.getInt(this.pData + 0L); }
/* 41 */   public void set_depth(int paramInt) { log.finest(""); Native.putInt(this.pData + 0L, paramInt); }
/* 42 */   public int get_bits_per_pixel() { log.finest(""); return Native.getInt(this.pData + 4L); }
/* 43 */   public void set_bits_per_pixel(int paramInt) { log.finest(""); Native.putInt(this.pData + 4L, paramInt); }
/* 44 */   public int get_scanline_pad() { log.finest(""); return Native.getInt(this.pData + 8L); } public void set_scanline_pad(int paramInt) {
/* 45 */     log.finest(""); Native.putInt(this.pData + 8L, paramInt);
/*    */   }
/*    */   
/*    */   String getName() {
/* 49 */     return "XPixmapFormatValues";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 54 */     StringBuilder stringBuilder = new StringBuilder(120);
/*    */     
/* 56 */     stringBuilder.append("depth = ").append(get_depth()).append(", ");
/* 57 */     stringBuilder.append("bits_per_pixel = ").append(get_bits_per_pixel()).append(", ");
/* 58 */     stringBuilder.append("scanline_pad = ").append(get_scanline_pad()).append(", ");
/* 59 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XPixmapFormatValues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */