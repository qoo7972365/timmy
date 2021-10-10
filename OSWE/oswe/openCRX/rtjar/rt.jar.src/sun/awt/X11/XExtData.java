/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XExtData
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 32; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public XExtData(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XExtData() {
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
/* 40 */   public int get_number() { log.finest(""); return Native.getInt(this.pData + 0L); }
/* 41 */   public void set_number(int paramInt) { log.finest(""); Native.putInt(this.pData + 0L, paramInt); }
/* 42 */   public XExtData get_next(int paramInt) { log.finest(""); return (Native.getLong(this.pData + 8L) != 0L) ? new XExtData(Native.getLong(this.pData + 8L) + (paramInt * 32)) : null; }
/* 43 */   public long get_next() { log.finest(""); return Native.getLong(this.pData + 8L); }
/* 44 */   public void set_next(long paramLong) { log.finest(""); Native.putLong(this.pData + 8L, paramLong); }
/* 45 */   public long get_free_private(int paramInt) { log.finest(""); return Native.getLong(this.pData + 16L) + (paramInt * Native.getLongSize()); }
/* 46 */   public long get_free_private() { log.finest(""); return Native.getLong(this.pData + 16L); }
/* 47 */   public void set_free_private(long paramLong) { log.finest(""); Native.putLong(this.pData + 16L, paramLong); }
/* 48 */   public long get_private_data(int paramInt) { log.finest(""); return Native.getLong(this.pData + 24L) + (paramInt * Native.getLongSize()); }
/* 49 */   public long get_private_data() { log.finest(""); return Native.getLong(this.pData + 24L); } public void set_private_data(long paramLong) {
/* 50 */     log.finest(""); Native.putLong(this.pData + 24L, paramLong);
/*    */   }
/*    */   
/*    */   String getName() {
/* 54 */     return "XExtData";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 59 */     StringBuilder stringBuilder = new StringBuilder(160);
/*    */     
/* 61 */     stringBuilder.append("number = ").append(get_number()).append(", ");
/* 62 */     stringBuilder.append("next = ").append(get_next()).append(", ");
/* 63 */     stringBuilder.append("free_private = ").append(get_free_private()).append(", ");
/* 64 */     stringBuilder.append("private_data = ").append(get_private_data()).append(", ");
/* 65 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XExtData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */