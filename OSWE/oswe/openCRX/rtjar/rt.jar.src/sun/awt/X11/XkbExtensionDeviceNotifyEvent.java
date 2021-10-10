/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XkbExtensionDeviceNotifyEvent
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 88; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public XkbExtensionDeviceNotifyEvent(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XkbExtensionDeviceNotifyEvent() {
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
/* 54 */   public int get_reason() { log.finest(""); return Native.getInt(this.pData + 48L); }
/* 55 */   public void set_reason(int paramInt) { log.finest(""); Native.putInt(this.pData + 48L, paramInt); }
/* 56 */   public int get_supported() { log.finest(""); return Native.getInt(this.pData + 52L); }
/* 57 */   public void set_supported(int paramInt) { log.finest(""); Native.putInt(this.pData + 52L, paramInt); }
/* 58 */   public int get_unsupported() { log.finest(""); return Native.getInt(this.pData + 56L); }
/* 59 */   public void set_unsupported(int paramInt) { log.finest(""); Native.putInt(this.pData + 56L, paramInt); }
/* 60 */   public int get_first_btn() { log.finest(""); return Native.getInt(this.pData + 60L); }
/* 61 */   public void set_first_btn(int paramInt) { log.finest(""); Native.putInt(this.pData + 60L, paramInt); }
/* 62 */   public int get_num_btns() { log.finest(""); return Native.getInt(this.pData + 64L); }
/* 63 */   public void set_num_btns(int paramInt) { log.finest(""); Native.putInt(this.pData + 64L, paramInt); }
/* 64 */   public int get_leds_defined() { log.finest(""); return Native.getInt(this.pData + 68L); }
/* 65 */   public void set_leds_defined(int paramInt) { log.finest(""); Native.putInt(this.pData + 68L, paramInt); }
/* 66 */   public int get_led_state() { log.finest(""); return Native.getInt(this.pData + 72L); }
/* 67 */   public void set_led_state(int paramInt) { log.finest(""); Native.putInt(this.pData + 72L, paramInt); }
/* 68 */   public int get_led_class() { log.finest(""); return Native.getInt(this.pData + 76L); }
/* 69 */   public void set_led_class(int paramInt) { log.finest(""); Native.putInt(this.pData + 76L, paramInt); }
/* 70 */   public int get_led_id() { log.finest(""); return Native.getInt(this.pData + 80L); } public void set_led_id(int paramInt) {
/* 71 */     log.finest(""); Native.putInt(this.pData + 80L, paramInt);
/*    */   }
/*    */   
/*    */   String getName() {
/* 75 */     return "XkbExtensionDeviceNotifyEvent";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 80 */     StringBuilder stringBuilder = new StringBuilder(640);
/*    */     
/* 82 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/* 83 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/* 84 */     stringBuilder.append("send_event = ").append(get_send_event()).append(", ");
/* 85 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/* 86 */     stringBuilder.append("time = ").append(get_time()).append(", ");
/* 87 */     stringBuilder.append("xkb_type = ").append(get_xkb_type()).append(", ");
/* 88 */     stringBuilder.append("device = ").append(get_device()).append(", ");
/* 89 */     stringBuilder.append("reason = ").append(get_reason()).append(", ");
/* 90 */     stringBuilder.append("supported = ").append(get_supported()).append(", ");
/* 91 */     stringBuilder.append("unsupported = ").append(get_unsupported()).append(", ");
/* 92 */     stringBuilder.append("first_btn = ").append(get_first_btn()).append(", ");
/* 93 */     stringBuilder.append("num_btns = ").append(get_num_btns()).append(", ");
/* 94 */     stringBuilder.append("leds_defined = ").append(get_leds_defined()).append(", ");
/* 95 */     stringBuilder.append("led_state = ").append(get_led_state()).append(", ");
/* 96 */     stringBuilder.append("led_class = ").append(get_led_class()).append(", ");
/* 97 */     stringBuilder.append("led_id = ").append(get_led_id()).append(", ");
/* 98 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XkbExtensionDeviceNotifyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */