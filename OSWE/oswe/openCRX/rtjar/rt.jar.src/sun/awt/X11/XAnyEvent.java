/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XAnyEvent
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 40; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public XAnyEvent(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XAnyEvent() {
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
/* 48 */   public long get_window() { log.finest(""); return Native.getLong(this.pData + 32L); } public void set_window(long paramLong) {
/* 49 */     log.finest(""); Native.putLong(this.pData + 32L, paramLong);
/*    */   }
/*    */   
/*    */   String getName() {
/* 53 */     return "XAnyEvent";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 58 */     StringBuilder stringBuilder = new StringBuilder(200);
/*    */     
/* 60 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/* 61 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/* 62 */     stringBuilder.append("send_event = ").append(get_send_event()).append(", ");
/* 63 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/* 64 */     stringBuilder.append("window = ").append(getWindow(get_window())).append(", ");
/* 65 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XAnyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */