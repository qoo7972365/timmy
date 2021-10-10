/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XkbAccessXNotifyEvent
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
/*    */   public XkbAccessXNotifyEvent(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XkbAccessXNotifyEvent() {
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
/* 54 */   public int get_detail() { log.finest(""); return Native.getInt(this.pData + 48L); }
/* 55 */   public void set_detail(int paramInt) { log.finest(""); Native.putInt(this.pData + 48L, paramInt); }
/* 56 */   public int get_keycode() { log.finest(""); return Native.getInt(this.pData + 52L); }
/* 57 */   public void set_keycode(int paramInt) { log.finest(""); Native.putInt(this.pData + 52L, paramInt); }
/* 58 */   public int get_sk_delay() { log.finest(""); return Native.getInt(this.pData + 56L); }
/* 59 */   public void set_sk_delay(int paramInt) { log.finest(""); Native.putInt(this.pData + 56L, paramInt); }
/* 60 */   public int get_debounce_delay() { log.finest(""); return Native.getInt(this.pData + 60L); } public void set_debounce_delay(int paramInt) {
/* 61 */     log.finest(""); Native.putInt(this.pData + 60L, paramInt);
/*    */   }
/*    */   
/*    */   String getName() {
/* 65 */     return "XkbAccessXNotifyEvent";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 70 */     StringBuilder stringBuilder = new StringBuilder(440);
/*    */     
/* 72 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/* 73 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/* 74 */     stringBuilder.append("send_event = ").append(get_send_event()).append(", ");
/* 75 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/* 76 */     stringBuilder.append("time = ").append(get_time()).append(", ");
/* 77 */     stringBuilder.append("xkb_type = ").append(get_xkb_type()).append(", ");
/* 78 */     stringBuilder.append("device = ").append(get_device()).append(", ");
/* 79 */     stringBuilder.append("detail = ").append(get_detail()).append(", ");
/* 80 */     stringBuilder.append("keycode = ").append(get_keycode()).append(", ");
/* 81 */     stringBuilder.append("sk_delay = ").append(get_sk_delay()).append(", ");
/* 82 */     stringBuilder.append("debounce_delay = ").append(get_debounce_delay()).append(", ");
/* 83 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XkbAccessXNotifyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */