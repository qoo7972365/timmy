/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XConfigureRequestEvent
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 96; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public XConfigureRequestEvent(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XConfigureRequestEvent() {
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
/* 40 */   public int get_type() { log.finest(""); return Native.getInt(this.pData + 0L); }
/* 41 */   public void set_type(int paramInt) { log.finest(""); Native.putInt(this.pData + 0L, paramInt); }
/* 42 */   public long get_serial() { log.finest(""); return Native.getLong(this.pData + 8L); }
/* 43 */   public void set_serial(long paramLong) { log.finest(""); Native.putLong(this.pData + 8L, paramLong); }
/* 44 */   public boolean get_send_event() { log.finest(""); return Native.getBool(this.pData + 16L); }
/* 45 */   public void set_send_event(boolean paramBoolean) { log.finest(""); Native.putBool(this.pData + 16L, paramBoolean); }
/* 46 */   public long get_display() { log.finest(""); return Native.getLong(this.pData + 24L); }
/* 47 */   public void set_display(long paramLong) { log.finest(""); Native.putLong(this.pData + 24L, paramLong); }
/* 48 */   public long get_parent() { log.finest(""); return Native.getLong(this.pData + 32L); }
/* 49 */   public void set_parent(long paramLong) { log.finest(""); Native.putLong(this.pData + 32L, paramLong); }
/* 50 */   public long get_window() { log.finest(""); return Native.getLong(this.pData + 40L); }
/* 51 */   public void set_window(long paramLong) { log.finest(""); Native.putLong(this.pData + 40L, paramLong); }
/* 52 */   public int get_x() { log.finest(""); return Native.getInt(this.pData + 48L); }
/* 53 */   public void set_x(int paramInt) { log.finest(""); Native.putInt(this.pData + 48L, paramInt); }
/* 54 */   public int get_y() { log.finest(""); return Native.getInt(this.pData + 52L); }
/* 55 */   public void set_y(int paramInt) { log.finest(""); Native.putInt(this.pData + 52L, paramInt); }
/* 56 */   public int get_width() { log.finest(""); return Native.getInt(this.pData + 56L); }
/* 57 */   public void set_width(int paramInt) { log.finest(""); Native.putInt(this.pData + 56L, paramInt); }
/* 58 */   public int get_height() { log.finest(""); return Native.getInt(this.pData + 60L); }
/* 59 */   public void set_height(int paramInt) { log.finest(""); Native.putInt(this.pData + 60L, paramInt); }
/* 60 */   public int get_border_width() { log.finest(""); return Native.getInt(this.pData + 64L); }
/* 61 */   public void set_border_width(int paramInt) { log.finest(""); Native.putInt(this.pData + 64L, paramInt); }
/* 62 */   public long get_above() { log.finest(""); return Native.getLong(this.pData + 72L); }
/* 63 */   public void set_above(long paramLong) { log.finest(""); Native.putLong(this.pData + 72L, paramLong); }
/* 64 */   public int get_detail() { log.finest(""); return Native.getInt(this.pData + 80L); }
/* 65 */   public void set_detail(int paramInt) { log.finest(""); Native.putInt(this.pData + 80L, paramInt); }
/* 66 */   public long get_value_mask() { log.finest(""); return Native.getLong(this.pData + 88L); } public void set_value_mask(long paramLong) {
/* 67 */     log.finest(""); Native.putLong(this.pData + 88L, paramLong);
/*    */   }
/*    */   
/*    */   String getName() {
/* 71 */     return "XConfigureRequestEvent";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 76 */     StringBuilder stringBuilder = new StringBuilder(560);
/*    */     
/* 78 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/* 79 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/* 80 */     stringBuilder.append("send_event = ").append(get_send_event()).append(", ");
/* 81 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/* 82 */     stringBuilder.append("parent = ").append(get_parent()).append(", ");
/* 83 */     stringBuilder.append("window = ").append(getWindow(get_window())).append(", ");
/* 84 */     stringBuilder.append("x = ").append(get_x()).append(", ");
/* 85 */     stringBuilder.append("y = ").append(get_y()).append(", ");
/* 86 */     stringBuilder.append("width = ").append(get_width()).append(", ");
/* 87 */     stringBuilder.append("height = ").append(get_height()).append(", ");
/* 88 */     stringBuilder.append("border_width = ").append(get_border_width()).append(", ");
/* 89 */     stringBuilder.append("above = ").append(get_above()).append(", ");
/* 90 */     stringBuilder.append("detail = ").append(get_detail()).append(", ");
/* 91 */     stringBuilder.append("value_mask = ").append(get_value_mask()).append(", ");
/* 92 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XConfigureRequestEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */