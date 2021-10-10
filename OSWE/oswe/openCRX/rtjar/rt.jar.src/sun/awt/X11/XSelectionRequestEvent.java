/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XSelectionRequestEvent
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
/*    */   public XSelectionRequestEvent(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XSelectionRequestEvent() {
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
/* 48 */   public long get_owner() { log.finest(""); return Native.getLong(this.pData + 32L); }
/* 49 */   public void set_owner(long paramLong) { log.finest(""); Native.putLong(this.pData + 32L, paramLong); }
/* 50 */   public long get_requestor() { log.finest(""); return Native.getLong(this.pData + 40L); }
/* 51 */   public void set_requestor(long paramLong) { log.finest(""); Native.putLong(this.pData + 40L, paramLong); }
/* 52 */   public long get_selection() { log.finest(""); return Native.getLong(this.pData + 48L); }
/* 53 */   public void set_selection(long paramLong) { log.finest(""); Native.putLong(this.pData + 48L, paramLong); }
/* 54 */   public long get_target() { log.finest(""); return Native.getLong(this.pData + 56L); }
/* 55 */   public void set_target(long paramLong) { log.finest(""); Native.putLong(this.pData + 56L, paramLong); }
/* 56 */   public long get_property() { log.finest(""); return Native.getLong(this.pData + 64L); }
/* 57 */   public void set_property(long paramLong) { log.finest(""); Native.putLong(this.pData + 64L, paramLong); }
/* 58 */   public long get_time() { log.finest(""); return Native.getULong(this.pData + 72L); } public void set_time(long paramLong) {
/* 59 */     log.finest(""); Native.putULong(this.pData + 72L, paramLong);
/*    */   }
/*    */   
/*    */   String getName() {
/* 63 */     return "XSelectionRequestEvent";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 68 */     StringBuilder stringBuilder = new StringBuilder(400);
/*    */     
/* 70 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/* 71 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/* 72 */     stringBuilder.append("send_event = ").append(get_send_event()).append(", ");
/* 73 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/* 74 */     stringBuilder.append("owner = ").append(get_owner()).append(", ");
/* 75 */     stringBuilder.append("requestor = ").append(get_requestor()).append(", ");
/* 76 */     stringBuilder.append("selection = ").append(XAtom.get(get_selection())).append(", ");
/* 77 */     stringBuilder.append("target = ").append(XAtom.get(get_target())).append(", ");
/* 78 */     stringBuilder.append("property = ").append(XAtom.get(get_property())).append(", ");
/* 79 */     stringBuilder.append("time = ").append(get_time()).append(", ");
/* 80 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XSelectionRequestEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */