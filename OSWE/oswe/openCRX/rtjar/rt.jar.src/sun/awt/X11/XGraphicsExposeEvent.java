/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XGraphicsExposeEvent
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 72; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public XGraphicsExposeEvent(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XGraphicsExposeEvent() {
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
/* 48 */   public long get_drawable() { log.finest(""); return Native.getLong(this.pData + 32L); }
/* 49 */   public void set_drawable(long paramLong) { log.finest(""); Native.putLong(this.pData + 32L, paramLong); }
/* 50 */   public int get_x() { log.finest(""); return Native.getInt(this.pData + 40L); }
/* 51 */   public void set_x(int paramInt) { log.finest(""); Native.putInt(this.pData + 40L, paramInt); }
/* 52 */   public int get_y() { log.finest(""); return Native.getInt(this.pData + 44L); }
/* 53 */   public void set_y(int paramInt) { log.finest(""); Native.putInt(this.pData + 44L, paramInt); }
/* 54 */   public int get_width() { log.finest(""); return Native.getInt(this.pData + 48L); }
/* 55 */   public void set_width(int paramInt) { log.finest(""); Native.putInt(this.pData + 48L, paramInt); }
/* 56 */   public int get_height() { log.finest(""); return Native.getInt(this.pData + 52L); }
/* 57 */   public void set_height(int paramInt) { log.finest(""); Native.putInt(this.pData + 52L, paramInt); }
/* 58 */   public int get_count() { log.finest(""); return Native.getInt(this.pData + 56L); }
/* 59 */   public void set_count(int paramInt) { log.finest(""); Native.putInt(this.pData + 56L, paramInt); }
/* 60 */   public int get_major_code() { log.finest(""); return Native.getInt(this.pData + 60L); }
/* 61 */   public void set_major_code(int paramInt) { log.finest(""); Native.putInt(this.pData + 60L, paramInt); }
/* 62 */   public int get_minor_code() { log.finest(""); return Native.getInt(this.pData + 64L); } public void set_minor_code(int paramInt) {
/* 63 */     log.finest(""); Native.putInt(this.pData + 64L, paramInt);
/*    */   }
/*    */   
/*    */   String getName() {
/* 67 */     return "XGraphicsExposeEvent";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 72 */     StringBuilder stringBuilder = new StringBuilder(480);
/*    */     
/* 74 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/* 75 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/* 76 */     stringBuilder.append("send_event = ").append(get_send_event()).append(", ");
/* 77 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/* 78 */     stringBuilder.append("drawable = ").append(get_drawable()).append(", ");
/* 79 */     stringBuilder.append("x = ").append(get_x()).append(", ");
/* 80 */     stringBuilder.append("y = ").append(get_y()).append(", ");
/* 81 */     stringBuilder.append("width = ").append(get_width()).append(", ");
/* 82 */     stringBuilder.append("height = ").append(get_height()).append(", ");
/* 83 */     stringBuilder.append("count = ").append(get_count()).append(", ");
/* 84 */     stringBuilder.append("major_code = ").append(get_major_code()).append(", ");
/* 85 */     stringBuilder.append("minor_code = ").append(get_minor_code()).append(", ");
/* 86 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XGraphicsExposeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */