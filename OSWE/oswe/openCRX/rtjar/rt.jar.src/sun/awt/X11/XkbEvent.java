/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XkbEvent
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 192; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public XkbEvent(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XkbEvent() {
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
/* 42 */   public XkbAnyEvent get_any() { log.finest(""); return new XkbAnyEvent(this.pData + 0L); }
/* 43 */   public XkbNewKeyboardNotifyEvent get_new_kbd() { log.finest(""); return new XkbNewKeyboardNotifyEvent(this.pData + 0L); }
/* 44 */   public XkbMapNotifyEvent get_map() { log.finest(""); return new XkbMapNotifyEvent(this.pData + 0L); }
/* 45 */   public XkbStateNotifyEvent get_state() { log.finest(""); return new XkbStateNotifyEvent(this.pData + 0L); }
/* 46 */   public XkbControlsNotifyEvent get_ctrls() { log.finest(""); return new XkbControlsNotifyEvent(this.pData + 0L); }
/* 47 */   public XkbIndicatorNotifyEvent get_indicators() { log.finest(""); return new XkbIndicatorNotifyEvent(this.pData + 0L); }
/* 48 */   public XkbNamesNotifyEvent get_names() { log.finest(""); return new XkbNamesNotifyEvent(this.pData + 0L); }
/* 49 */   public XkbCompatMapNotifyEvent get_compat() { log.finest(""); return new XkbCompatMapNotifyEvent(this.pData + 0L); }
/* 50 */   public XkbBellNotifyEvent get_bell() { log.finest(""); return new XkbBellNotifyEvent(this.pData + 0L); }
/* 51 */   public XkbActionMessageEvent get_message() { log.finest(""); return new XkbActionMessageEvent(this.pData + 0L); }
/* 52 */   public XkbAccessXNotifyEvent get_accessx() { log.finest(""); return new XkbAccessXNotifyEvent(this.pData + 0L); }
/* 53 */   public XkbExtensionDeviceNotifyEvent get_device() { log.finest(""); return new XkbExtensionDeviceNotifyEvent(this.pData + 0L); } public XEvent get_core() {
/* 54 */     log.finest(""); return new XEvent(this.pData + 0L);
/*    */   }
/*    */   
/*    */   String getName() {
/* 58 */     return "XkbEvent";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 63 */     StringBuilder stringBuilder = new StringBuilder(560);
/*    */     
/* 65 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/* 66 */     stringBuilder.append("any = ").append(get_any()).append(", ");
/* 67 */     stringBuilder.append("new_kbd = ").append(get_new_kbd()).append(", ");
/* 68 */     stringBuilder.append("map = ").append(get_map()).append(", ");
/* 69 */     stringBuilder.append("state = ").append(get_state()).append(", ");
/* 70 */     stringBuilder.append("ctrls = ").append(get_ctrls()).append(", ");
/* 71 */     stringBuilder.append("indicators = ").append(get_indicators()).append(", ");
/* 72 */     stringBuilder.append("names = ").append(get_names()).append(", ");
/* 73 */     stringBuilder.append("compat = ").append(get_compat()).append(", ");
/* 74 */     stringBuilder.append("bell = ").append(get_bell()).append(", ");
/* 75 */     stringBuilder.append("message = ").append(get_message()).append(", ");
/* 76 */     stringBuilder.append("accessx = ").append(get_accessx()).append(", ");
/* 77 */     stringBuilder.append("device = ").append(get_device()).append(", ");
/* 78 */     stringBuilder.append("core = ").append(get_core()).append(", ");
/* 79 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XkbEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */