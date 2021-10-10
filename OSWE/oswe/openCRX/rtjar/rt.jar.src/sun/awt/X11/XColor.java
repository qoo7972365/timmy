/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XColor
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 16; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public XColor(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XColor() {
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
/* 40 */   public long get_pixel() { log.finest(""); return Native.getLong(this.pData + 0L); }
/* 41 */   public void set_pixel(long paramLong) { log.finest(""); Native.putLong(this.pData + 0L, paramLong); }
/* 42 */   public short get_red() { log.finest(""); return Native.getShort(this.pData + 8L); }
/* 43 */   public void set_red(short paramShort) { log.finest(""); Native.putShort(this.pData + 8L, paramShort); }
/* 44 */   public short get_green() { log.finest(""); return Native.getShort(this.pData + 10L); }
/* 45 */   public void set_green(short paramShort) { log.finest(""); Native.putShort(this.pData + 10L, paramShort); }
/* 46 */   public short get_blue() { log.finest(""); return Native.getShort(this.pData + 12L); }
/* 47 */   public void set_blue(short paramShort) { log.finest(""); Native.putShort(this.pData + 12L, paramShort); }
/* 48 */   public byte get_flags() { log.finest(""); return Native.getByte(this.pData + 14L); }
/* 49 */   public void set_flags(byte paramByte) { log.finest(""); Native.putByte(this.pData + 14L, paramByte); }
/* 50 */   public byte get_pad() { log.finest(""); return Native.getByte(this.pData + 15L); } public void set_pad(byte paramByte) {
/* 51 */     log.finest(""); Native.putByte(this.pData + 15L, paramByte);
/*    */   }
/*    */   
/*    */   String getName() {
/* 55 */     return "XColor";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 60 */     StringBuilder stringBuilder = new StringBuilder(240);
/*    */     
/* 62 */     stringBuilder.append("pixel = ").append(get_pixel()).append(", ");
/* 63 */     stringBuilder.append("red = ").append(get_red()).append(", ");
/* 64 */     stringBuilder.append("green = ").append(get_green()).append(", ");
/* 65 */     stringBuilder.append("blue = ").append(get_blue()).append(", ");
/* 66 */     stringBuilder.append("flags = ").append(get_flags()).append(", ");
/* 67 */     stringBuilder.append("pad = ").append(get_pad()).append(", ");
/* 68 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */