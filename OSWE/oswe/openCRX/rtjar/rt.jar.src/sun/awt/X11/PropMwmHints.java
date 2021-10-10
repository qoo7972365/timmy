/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class PropMwmHints
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
/*    */   public PropMwmHints(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public PropMwmHints() {
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
/* 40 */   public long get_flags() { log.finest(""); return Native.getLong(this.pData + 0L); }
/* 41 */   public void set_flags(long paramLong) { log.finest(""); Native.putLong(this.pData + 0L, paramLong); }
/* 42 */   public long get_functions() { log.finest(""); return Native.getLong(this.pData + 8L); }
/* 43 */   public void set_functions(long paramLong) { log.finest(""); Native.putLong(this.pData + 8L, paramLong); }
/* 44 */   public long get_decorations() { log.finest(""); return Native.getLong(this.pData + 16L); }
/* 45 */   public void set_decorations(long paramLong) { log.finest(""); Native.putLong(this.pData + 16L, paramLong); }
/* 46 */   public long get_inputMode() { log.finest(""); return Native.getLong(this.pData + 24L); }
/* 47 */   public void set_inputMode(long paramLong) { log.finest(""); Native.putLong(this.pData + 24L, paramLong); }
/* 48 */   public long get_status() { log.finest(""); return Native.getLong(this.pData + 32L); } public void set_status(long paramLong) {
/* 49 */     log.finest(""); Native.putLong(this.pData + 32L, paramLong);
/*    */   }
/*    */   
/*    */   String getName() {
/* 53 */     return "PropMwmHints";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 58 */     StringBuilder stringBuilder = new StringBuilder(200);
/*    */     
/* 60 */     stringBuilder.append("flags = ").append(get_flags()).append(", ");
/* 61 */     stringBuilder.append("functions = ").append(get_functions()).append(", ");
/* 62 */     stringBuilder.append("decorations = ").append(get_decorations()).append(", ");
/* 63 */     stringBuilder.append("inputMode = ").append(get_inputMode()).append(", ");
/* 64 */     stringBuilder.append("status = ").append(get_status()).append(", ");
/* 65 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/PropMwmHints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */