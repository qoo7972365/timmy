/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XRenderDirectFormat
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
/*    */   public XRenderDirectFormat(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XRenderDirectFormat() {
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
/* 40 */   public short get_red() { log.finest(""); return Native.getShort(this.pData + 0L); }
/* 41 */   public void set_red(short paramShort) { log.finest(""); Native.putShort(this.pData + 0L, paramShort); }
/* 42 */   public short get_redMask() { log.finest(""); return Native.getShort(this.pData + 2L); }
/* 43 */   public void set_redMask(short paramShort) { log.finest(""); Native.putShort(this.pData + 2L, paramShort); }
/* 44 */   public short get_green() { log.finest(""); return Native.getShort(this.pData + 4L); }
/* 45 */   public void set_green(short paramShort) { log.finest(""); Native.putShort(this.pData + 4L, paramShort); }
/* 46 */   public short get_greenMask() { log.finest(""); return Native.getShort(this.pData + 6L); }
/* 47 */   public void set_greenMask(short paramShort) { log.finest(""); Native.putShort(this.pData + 6L, paramShort); }
/* 48 */   public short get_blue() { log.finest(""); return Native.getShort(this.pData + 8L); }
/* 49 */   public void set_blue(short paramShort) { log.finest(""); Native.putShort(this.pData + 8L, paramShort); }
/* 50 */   public short get_blueMask() { log.finest(""); return Native.getShort(this.pData + 10L); }
/* 51 */   public void set_blueMask(short paramShort) { log.finest(""); Native.putShort(this.pData + 10L, paramShort); }
/* 52 */   public short get_alpha() { log.finest(""); return Native.getShort(this.pData + 12L); }
/* 53 */   public void set_alpha(short paramShort) { log.finest(""); Native.putShort(this.pData + 12L, paramShort); }
/* 54 */   public short get_alphaMask() { log.finest(""); return Native.getShort(this.pData + 14L); } public void set_alphaMask(short paramShort) {
/* 55 */     log.finest(""); Native.putShort(this.pData + 14L, paramShort);
/*    */   }
/*    */   
/*    */   String getName() {
/* 59 */     return "XRenderDirectFormat";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 64 */     StringBuilder stringBuilder = new StringBuilder(320);
/*    */     
/* 66 */     stringBuilder.append("red = ").append(get_red()).append(", ");
/* 67 */     stringBuilder.append("redMask = ").append(get_redMask()).append(", ");
/* 68 */     stringBuilder.append("green = ").append(get_green()).append(", ");
/* 69 */     stringBuilder.append("greenMask = ").append(get_greenMask()).append(", ");
/* 70 */     stringBuilder.append("blue = ").append(get_blue()).append(", ");
/* 71 */     stringBuilder.append("blueMask = ").append(get_blueMask()).append(", ");
/* 72 */     stringBuilder.append("alpha = ").append(get_alpha()).append(", ");
/* 73 */     stringBuilder.append("alphaMask = ").append(get_alphaMask()).append(", ");
/* 74 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XRenderDirectFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */