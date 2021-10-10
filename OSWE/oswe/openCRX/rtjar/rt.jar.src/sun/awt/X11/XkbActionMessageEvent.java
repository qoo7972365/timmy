/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XkbActionMessageEvent
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
/*    */   public XkbActionMessageEvent(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XkbActionMessageEvent() {
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
/* 54 */   public int get_keycode() { log.finest(""); return Native.getInt(this.pData + 48L); }
/* 55 */   public void set_keycode(int paramInt) { log.finest(""); Native.putInt(this.pData + 48L, paramInt); }
/* 56 */   public boolean get_press() { log.finest(""); return Native.getBool(this.pData + 52L); }
/* 57 */   public void set_press(boolean paramBoolean) { log.finest(""); Native.putBool(this.pData + 52L, paramBoolean); }
/* 58 */   public boolean get_key_event_follows() { log.finest(""); return Native.getBool(this.pData + 56L); }
/* 59 */   public void set_key_event_follows(boolean paramBoolean) { log.finest(""); Native.putBool(this.pData + 56L, paramBoolean); }
/* 60 */   public int get_group() { log.finest(""); return Native.getInt(this.pData + 60L); }
/* 61 */   public void set_group(int paramInt) { log.finest(""); Native.putInt(this.pData + 60L, paramInt); }
/* 62 */   public int get_mods() { log.finest(""); return Native.getInt(this.pData + 64L); }
/* 63 */   public void set_mods(int paramInt) { log.finest(""); Native.putInt(this.pData + 64L, paramInt); }
/* 64 */   public byte get_message(int paramInt) { log.finest(""); return Native.getByte(this.pData + 68L + (paramInt * 1)); }
/* 65 */   public void set_message(int paramInt, byte paramByte) { log.finest(""); Native.putByte(this.pData + 68L + (paramInt * 1), paramByte); } public long get_message() {
/* 66 */     log.finest(""); return this.pData + 68L;
/*    */   }
/*    */   
/*    */   String getName() {
/* 70 */     return "XkbActionMessageEvent";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 75 */     StringBuilder stringBuilder = new StringBuilder(520);
/*    */     
/* 77 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/* 78 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/* 79 */     stringBuilder.append("send_event = ").append(get_send_event()).append(", ");
/* 80 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/* 81 */     stringBuilder.append("time = ").append(get_time()).append(", ");
/* 82 */     stringBuilder.append("xkb_type = ").append(get_xkb_type()).append(", ");
/* 83 */     stringBuilder.append("device = ").append(get_device()).append(", ");
/* 84 */     stringBuilder.append("keycode = ").append(get_keycode()).append(", ");
/* 85 */     stringBuilder.append("press = ").append(get_press()).append(", ");
/* 86 */     stringBuilder.append("key_event_follows = ").append(get_key_event_follows()).append(", ");
/* 87 */     stringBuilder.append("group = ").append(get_group()).append(", ");
/* 88 */     stringBuilder.append("mods = ").append(get_mods()).append(", ");
/* 89 */     stringBuilder.append("{")
/* 90 */       .append(get_message(0)).append(" ")
/* 91 */       .append(get_message(1)).append(" ")
/* 92 */       .append(get_message(2)).append(" ")
/* 93 */       .append(get_message(3)).append(" ")
/* 94 */       .append(get_message(4)).append(" ")
/* 95 */       .append(get_message(5)).append(" ")
/* 96 */       .append(get_message(6)).append(" ").append("}");
/* 97 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XkbActionMessageEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */