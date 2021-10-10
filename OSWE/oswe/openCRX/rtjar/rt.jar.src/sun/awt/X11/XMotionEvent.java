/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XMotionEvent
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
/*    */   public XMotionEvent(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XMotionEvent() {
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
/* 48 */   public long get_window() { log.finest(""); return Native.getLong(this.pData + 32L); }
/* 49 */   public void set_window(long paramLong) { log.finest(""); Native.putLong(this.pData + 32L, paramLong); }
/* 50 */   public long get_root() { log.finest(""); return Native.getLong(this.pData + 40L); }
/* 51 */   public void set_root(long paramLong) { log.finest(""); Native.putLong(this.pData + 40L, paramLong); }
/* 52 */   public long get_subwindow() { log.finest(""); return Native.getLong(this.pData + 48L); }
/* 53 */   public void set_subwindow(long paramLong) { log.finest(""); Native.putLong(this.pData + 48L, paramLong); }
/* 54 */   public long get_time() { log.finest(""); return Native.getULong(this.pData + 56L); }
/* 55 */   public void set_time(long paramLong) { log.finest(""); Native.putULong(this.pData + 56L, paramLong); }
/* 56 */   public int get_x() { log.finest(""); return Native.getInt(this.pData + 64L); }
/* 57 */   public void set_x(int paramInt) { log.finest(""); Native.putInt(this.pData + 64L, paramInt); }
/* 58 */   public int get_y() { log.finest(""); return Native.getInt(this.pData + 68L); }
/* 59 */   public void set_y(int paramInt) { log.finest(""); Native.putInt(this.pData + 68L, paramInt); }
/* 60 */   public int get_x_root() { log.finest(""); return Native.getInt(this.pData + 72L); }
/* 61 */   public void set_x_root(int paramInt) { log.finest(""); Native.putInt(this.pData + 72L, paramInt); }
/* 62 */   public int get_y_root() { log.finest(""); return Native.getInt(this.pData + 76L); }
/* 63 */   public void set_y_root(int paramInt) { log.finest(""); Native.putInt(this.pData + 76L, paramInt); }
/* 64 */   public int get_state() { log.finest(""); return Native.getInt(this.pData + 80L); }
/* 65 */   public void set_state(int paramInt) { log.finest(""); Native.putInt(this.pData + 80L, paramInt); }
/* 66 */   public byte get_is_hint() { log.finest(""); return Native.getByte(this.pData + 84L); }
/* 67 */   public void set_is_hint(byte paramByte) { log.finest(""); Native.putByte(this.pData + 84L, paramByte); }
/* 68 */   public boolean get_same_screen() { log.finest(""); return Native.getBool(this.pData + 88L); } public void set_same_screen(boolean paramBoolean) {
/* 69 */     log.finest(""); Native.putBool(this.pData + 88L, paramBoolean);
/*    */   }
/*    */   
/*    */   String getName() {
/* 73 */     return "XMotionEvent";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 78 */     StringBuilder stringBuilder = new StringBuilder(600);
/*    */     
/* 80 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/* 81 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/* 82 */     stringBuilder.append("send_event = ").append(get_send_event()).append(", ");
/* 83 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/* 84 */     stringBuilder.append("window = ").append(getWindow(get_window())).append(", ");
/* 85 */     stringBuilder.append("root = ").append(get_root()).append(", ");
/* 86 */     stringBuilder.append("subwindow = ").append(get_subwindow()).append(", ");
/* 87 */     stringBuilder.append("time = ").append(get_time()).append(", ");
/* 88 */     stringBuilder.append("x = ").append(get_x()).append(", ");
/* 89 */     stringBuilder.append("y = ").append(get_y()).append(", ");
/* 90 */     stringBuilder.append("x_root = ").append(get_x_root()).append(", ");
/* 91 */     stringBuilder.append("y_root = ").append(get_y_root()).append(", ");
/* 92 */     stringBuilder.append("state = ").append(get_state()).append(", ");
/* 93 */     stringBuilder.append("is_hint = ").append(get_is_hint()).append(", ");
/* 94 */     stringBuilder.append("same_screen = ").append(get_same_screen()).append(", ");
/* 95 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XMotionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */