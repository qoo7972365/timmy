/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XkbNewKeyboardNotifyEvent
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 80; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public XkbNewKeyboardNotifyEvent(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XkbNewKeyboardNotifyEvent() {
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
/* 42 */   public long get_serial() { log.finest(""); return Native.getULong(this.pData + 8L); }
/* 43 */   public void set_serial(long paramLong) { log.finest(""); Native.putULong(this.pData + 8L, paramLong); }
/* 44 */   public boolean get_send_event() { log.finest(""); return Native.getBool(this.pData + 16L); }
/* 45 */   public void set_send_event(boolean paramBoolean) { log.finest(""); Native.putBool(this.pData + 16L, paramBoolean); }
/* 46 */   public long get_display() { log.finest(""); return Native.getLong(this.pData + 24L); }
/* 47 */   public void set_display(long paramLong) { log.finest(""); Native.putLong(this.pData + 24L, paramLong); }
/* 48 */   public long get_time() { log.finest(""); return Native.getULong(this.pData + 32L); }
/* 49 */   public void set_time(long paramLong) { log.finest(""); Native.putULong(this.pData + 32L, paramLong); }
/* 50 */   public int get_xkb_type() { log.finest(""); return Native.getInt(this.pData + 40L); }
/* 51 */   public void set_xkb_type(int paramInt) { log.finest(""); Native.putInt(this.pData + 40L, paramInt); }
/* 52 */   public int get_device() { log.finest(""); return Native.getInt(this.pData + 44L); }
/* 53 */   public void set_device(int paramInt) { log.finest(""); Native.putInt(this.pData + 44L, paramInt); }
/* 54 */   public int get_old_device() { log.finest(""); return Native.getInt(this.pData + 48L); }
/* 55 */   public void set_old_device(int paramInt) { log.finest(""); Native.putInt(this.pData + 48L, paramInt); }
/* 56 */   public int get_min_key_code() { log.finest(""); return Native.getInt(this.pData + 52L); }
/* 57 */   public void set_min_key_code(int paramInt) { log.finest(""); Native.putInt(this.pData + 52L, paramInt); }
/* 58 */   public int get_max_key_code() { log.finest(""); return Native.getInt(this.pData + 56L); }
/* 59 */   public void set_max_key_code(int paramInt) { log.finest(""); Native.putInt(this.pData + 56L, paramInt); }
/* 60 */   public int get_old_min_key_code() { log.finest(""); return Native.getInt(this.pData + 60L); }
/* 61 */   public void set_old_min_key_code(int paramInt) { log.finest(""); Native.putInt(this.pData + 60L, paramInt); }
/* 62 */   public int get_old_max_key_code() { log.finest(""); return Native.getInt(this.pData + 64L); }
/* 63 */   public void set_old_max_key_code(int paramInt) { log.finest(""); Native.putInt(this.pData + 64L, paramInt); }
/* 64 */   public int get_changed() { log.finest(""); return Native.getInt(this.pData + 68L); }
/* 65 */   public void set_changed(int paramInt) { log.finest(""); Native.putInt(this.pData + 68L, paramInt); }
/* 66 */   public byte get_req_major() { log.finest(""); return Native.getByte(this.pData + 72L); }
/* 67 */   public void set_req_major(byte paramByte) { log.finest(""); Native.putByte(this.pData + 72L, paramByte); }
/* 68 */   public byte get_req_minor() { log.finest(""); return Native.getByte(this.pData + 73L); } public void set_req_minor(byte paramByte) {
/* 69 */     log.finest(""); Native.putByte(this.pData + 73L, paramByte);
/*    */   }
/*    */   
/*    */   String getName() {
/* 73 */     return "XkbNewKeyboardNotifyEvent";
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
/* 84 */     stringBuilder.append("time = ").append(get_time()).append(", ");
/* 85 */     stringBuilder.append("xkb_type = ").append(get_xkb_type()).append(", ");
/* 86 */     stringBuilder.append("device = ").append(get_device()).append(", ");
/* 87 */     stringBuilder.append("old_device = ").append(get_old_device()).append(", ");
/* 88 */     stringBuilder.append("min_key_code = ").append(get_min_key_code()).append(", ");
/* 89 */     stringBuilder.append("max_key_code = ").append(get_max_key_code()).append(", ");
/* 90 */     stringBuilder.append("old_min_key_code = ").append(get_old_min_key_code()).append(", ");
/* 91 */     stringBuilder.append("old_max_key_code = ").append(get_old_max_key_code()).append(", ");
/* 92 */     stringBuilder.append("changed = ").append(get_changed()).append(", ");
/* 93 */     stringBuilder.append("req_major = ").append(get_req_major()).append(", ");
/* 94 */     stringBuilder.append("req_minor = ").append(get_req_minor()).append(", ");
/* 95 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XkbNewKeyboardNotifyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */