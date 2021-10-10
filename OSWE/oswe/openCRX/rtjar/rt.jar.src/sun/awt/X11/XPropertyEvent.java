/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XPropertyEvent
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
/*    */   public XPropertyEvent(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XPropertyEvent() {
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
/* 50 */   public long get_atom() { log.finest(""); return Native.getLong(this.pData + 40L); }
/* 51 */   public void set_atom(long paramLong) { log.finest(""); Native.putLong(this.pData + 40L, paramLong); }
/* 52 */   public long get_time() { log.finest(""); return Native.getULong(this.pData + 48L); }
/* 53 */   public void set_time(long paramLong) { log.finest(""); Native.putULong(this.pData + 48L, paramLong); }
/* 54 */   public int get_state() { log.finest(""); return Native.getInt(this.pData + 56L); } public void set_state(int paramInt) {
/* 55 */     log.finest(""); Native.putInt(this.pData + 56L, paramInt);
/*    */   }
/*    */   
/*    */   String getName() {
/* 59 */     return "XPropertyEvent";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 64 */     StringBuilder stringBuilder = new StringBuilder(320);
/*    */     
/* 66 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/* 67 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/* 68 */     stringBuilder.append("send_event = ").append(get_send_event()).append(", ");
/* 69 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/* 70 */     stringBuilder.append("window = ").append(getWindow(get_window())).append(", ");
/* 71 */     stringBuilder.append("atom = ").append(XAtom.get(get_atom())).append(", ");
/* 72 */     stringBuilder.append("time = ").append(get_time()).append(", ");
/* 73 */     stringBuilder.append("state = ").append(get_state()).append(", ");
/* 74 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XPropertyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */