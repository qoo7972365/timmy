/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XSetWindowAttributes
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 112; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public XSetWindowAttributes(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XSetWindowAttributes() {
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
/* 40 */   public long get_background_pixmap() { log.finest(""); return Native.getLong(this.pData + 0L); }
/* 41 */   public void set_background_pixmap(long paramLong) { log.finest(""); Native.putLong(this.pData + 0L, paramLong); }
/* 42 */   public long get_background_pixel() { log.finest(""); return Native.getLong(this.pData + 8L); }
/* 43 */   public void set_background_pixel(long paramLong) { log.finest(""); Native.putLong(this.pData + 8L, paramLong); }
/* 44 */   public long get_border_pixmap() { log.finest(""); return Native.getLong(this.pData + 16L); }
/* 45 */   public void set_border_pixmap(long paramLong) { log.finest(""); Native.putLong(this.pData + 16L, paramLong); }
/* 46 */   public long get_border_pixel() { log.finest(""); return Native.getLong(this.pData + 24L); }
/* 47 */   public void set_border_pixel(long paramLong) { log.finest(""); Native.putLong(this.pData + 24L, paramLong); }
/* 48 */   public int get_bit_gravity() { log.finest(""); return Native.getInt(this.pData + 32L); }
/* 49 */   public void set_bit_gravity(int paramInt) { log.finest(""); Native.putInt(this.pData + 32L, paramInt); }
/* 50 */   public int get_win_gravity() { log.finest(""); return Native.getInt(this.pData + 36L); }
/* 51 */   public void set_win_gravity(int paramInt) { log.finest(""); Native.putInt(this.pData + 36L, paramInt); }
/* 52 */   public int get_backing_store() { log.finest(""); return Native.getInt(this.pData + 40L); }
/* 53 */   public void set_backing_store(int paramInt) { log.finest(""); Native.putInt(this.pData + 40L, paramInt); }
/* 54 */   public long get_backing_planes() { log.finest(""); return Native.getLong(this.pData + 48L); }
/* 55 */   public void set_backing_planes(long paramLong) { log.finest(""); Native.putLong(this.pData + 48L, paramLong); }
/* 56 */   public long get_backing_pixel() { log.finest(""); return Native.getLong(this.pData + 56L); }
/* 57 */   public void set_backing_pixel(long paramLong) { log.finest(""); Native.putLong(this.pData + 56L, paramLong); }
/* 58 */   public boolean get_save_under() { log.finest(""); return Native.getBool(this.pData + 64L); }
/* 59 */   public void set_save_under(boolean paramBoolean) { log.finest(""); Native.putBool(this.pData + 64L, paramBoolean); }
/* 60 */   public long get_event_mask() { log.finest(""); return Native.getLong(this.pData + 72L); }
/* 61 */   public void set_event_mask(long paramLong) { log.finest(""); Native.putLong(this.pData + 72L, paramLong); }
/* 62 */   public long get_do_not_propagate_mask() { log.finest(""); return Native.getLong(this.pData + 80L); }
/* 63 */   public void set_do_not_propagate_mask(long paramLong) { log.finest(""); Native.putLong(this.pData + 80L, paramLong); }
/* 64 */   public boolean get_override_redirect() { log.finest(""); return Native.getBool(this.pData + 88L); }
/* 65 */   public void set_override_redirect(boolean paramBoolean) { log.finest(""); Native.putBool(this.pData + 88L, paramBoolean); }
/* 66 */   public long get_colormap() { log.finest(""); return Native.getLong(this.pData + 96L); }
/* 67 */   public void set_colormap(long paramLong) { log.finest(""); Native.putLong(this.pData + 96L, paramLong); }
/* 68 */   public long get_cursor() { log.finest(""); return Native.getLong(this.pData + 104L); } public void set_cursor(long paramLong) {
/* 69 */     log.finest(""); Native.putLong(this.pData + 104L, paramLong);
/*    */   }
/*    */   
/*    */   String getName() {
/* 73 */     return "XSetWindowAttributes";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 78 */     StringBuilder stringBuilder = new StringBuilder(600);
/*    */     
/* 80 */     stringBuilder.append("background_pixmap = ").append(get_background_pixmap()).append(", ");
/* 81 */     stringBuilder.append("background_pixel = ").append(get_background_pixel()).append(", ");
/* 82 */     stringBuilder.append("border_pixmap = ").append(get_border_pixmap()).append(", ");
/* 83 */     stringBuilder.append("border_pixel = ").append(get_border_pixel()).append(", ");
/* 84 */     stringBuilder.append("bit_gravity = ").append(get_bit_gravity()).append(", ");
/* 85 */     stringBuilder.append("win_gravity = ").append(get_win_gravity()).append(", ");
/* 86 */     stringBuilder.append("backing_store = ").append(get_backing_store()).append(", ");
/* 87 */     stringBuilder.append("backing_planes = ").append(get_backing_planes()).append(", ");
/* 88 */     stringBuilder.append("backing_pixel = ").append(get_backing_pixel()).append(", ");
/* 89 */     stringBuilder.append("save_under = ").append(get_save_under()).append(", ");
/* 90 */     stringBuilder.append("event_mask = ").append(get_event_mask()).append(", ");
/* 91 */     stringBuilder.append("do_not_propagate_mask = ").append(get_do_not_propagate_mask()).append(", ");
/* 92 */     stringBuilder.append("override_redirect = ").append(get_override_redirect()).append(", ");
/* 93 */     stringBuilder.append("colormap = ").append(get_colormap()).append(", ");
/* 94 */     stringBuilder.append("cursor = ").append(get_cursor()).append(", ");
/* 95 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XSetWindowAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */