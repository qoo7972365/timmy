/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XIconSize
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 24; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public XIconSize(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XIconSize() {
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
/* 40 */   public int get_min_width() { log.finest(""); return Native.getInt(this.pData + 0L); }
/* 41 */   public void set_min_width(int paramInt) { log.finest(""); Native.putInt(this.pData + 0L, paramInt); }
/* 42 */   public int get_min_height() { log.finest(""); return Native.getInt(this.pData + 4L); }
/* 43 */   public void set_min_height(int paramInt) { log.finest(""); Native.putInt(this.pData + 4L, paramInt); }
/* 44 */   public int get_max_width() { log.finest(""); return Native.getInt(this.pData + 8L); }
/* 45 */   public void set_max_width(int paramInt) { log.finest(""); Native.putInt(this.pData + 8L, paramInt); }
/* 46 */   public int get_max_height() { log.finest(""); return Native.getInt(this.pData + 12L); }
/* 47 */   public void set_max_height(int paramInt) { log.finest(""); Native.putInt(this.pData + 12L, paramInt); }
/* 48 */   public int get_width_inc() { log.finest(""); return Native.getInt(this.pData + 16L); }
/* 49 */   public void set_width_inc(int paramInt) { log.finest(""); Native.putInt(this.pData + 16L, paramInt); }
/* 50 */   public int get_height_inc() { log.finest(""); return Native.getInt(this.pData + 20L); } public void set_height_inc(int paramInt) {
/* 51 */     log.finest(""); Native.putInt(this.pData + 20L, paramInt);
/*    */   }
/*    */   
/*    */   String getName() {
/* 55 */     return "XIconSize";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 60 */     StringBuilder stringBuilder = new StringBuilder(240);
/*    */     
/* 62 */     stringBuilder.append("min_width = ").append(get_min_width()).append(", ");
/* 63 */     stringBuilder.append("min_height = ").append(get_min_height()).append(", ");
/* 64 */     stringBuilder.append("max_width = ").append(get_max_width()).append(", ");
/* 65 */     stringBuilder.append("max_height = ").append(get_max_height()).append(", ");
/* 66 */     stringBuilder.append("width_inc = ").append(get_width_inc()).append(", ");
/* 67 */     stringBuilder.append("height_inc = ").append(get_height_inc()).append(", ");
/* 68 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XIconSize.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */