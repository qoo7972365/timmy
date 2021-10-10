/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XErrorEvent
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
/*    */   public XErrorEvent(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XErrorEvent() {
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
/* 42 */   public long get_display() { log.finest(""); return Native.getLong(this.pData + 8L); }
/* 43 */   public void set_display(long paramLong) { log.finest(""); Native.putLong(this.pData + 8L, paramLong); }
/* 44 */   public long get_resourceid() { log.finest(""); return Native.getLong(this.pData + 16L); }
/* 45 */   public void set_resourceid(long paramLong) { log.finest(""); Native.putLong(this.pData + 16L, paramLong); }
/* 46 */   public long get_serial() { log.finest(""); return Native.getLong(this.pData + 24L); }
/* 47 */   public void set_serial(long paramLong) { log.finest(""); Native.putLong(this.pData + 24L, paramLong); }
/* 48 */   public byte get_error_code() { log.finest(""); return Native.getByte(this.pData + 32L); }
/* 49 */   public void set_error_code(byte paramByte) { log.finest(""); Native.putByte(this.pData + 32L, paramByte); }
/* 50 */   public byte get_request_code() { log.finest(""); return Native.getByte(this.pData + 33L); }
/* 51 */   public void set_request_code(byte paramByte) { log.finest(""); Native.putByte(this.pData + 33L, paramByte); }
/* 52 */   public byte get_minor_code() { log.finest(""); return Native.getByte(this.pData + 34L); } public void set_minor_code(byte paramByte) {
/* 53 */     log.finest(""); Native.putByte(this.pData + 34L, paramByte);
/*    */   }
/*    */   
/*    */   String getName() {
/* 57 */     return "XErrorEvent";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 62 */     StringBuilder stringBuilder = new StringBuilder(280);
/*    */     
/* 64 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/* 65 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/* 66 */     stringBuilder.append("resourceid = ").append(get_resourceid()).append(", ");
/* 67 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/* 68 */     stringBuilder.append("error_code = ").append(get_error_code()).append(", ");
/* 69 */     stringBuilder.append("request_code = ").append(get_request_code()).append(", ");
/* 70 */     stringBuilder.append("minor_code = ").append(get_minor_code()).append(", ");
/* 71 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XErrorEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */