/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class Depth
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 16; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public Depth(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public Depth() {
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
/* 40 */   public int get_depth() { log.finest(""); return Native.getInt(this.pData + 0L); }
/* 41 */   public void set_depth(int paramInt) { log.finest(""); Native.putInt(this.pData + 0L, paramInt); }
/* 42 */   public int get_nvisuals() { log.finest(""); return Native.getInt(this.pData + 4L); }
/* 43 */   public void set_nvisuals(int paramInt) { log.finest(""); Native.putInt(this.pData + 4L, paramInt); }
/* 44 */   public Visual get_visuals(int paramInt) { log.finest(""); return (Native.getLong(this.pData + 8L) != 0L) ? new Visual(Native.getLong(this.pData + 8L) + (paramInt * 56)) : null; }
/* 45 */   public long get_visuals() { log.finest(""); return Native.getLong(this.pData + 8L); } public void set_visuals(long paramLong) {
/* 46 */     log.finest(""); Native.putLong(this.pData + 8L, paramLong);
/*    */   }
/*    */   
/*    */   String getName() {
/* 50 */     return "Depth";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 55 */     StringBuilder stringBuilder = new StringBuilder(120);
/*    */     
/* 57 */     stringBuilder.append("depth = ").append(get_depth()).append(", ");
/* 58 */     stringBuilder.append("nvisuals = ").append(get_nvisuals()).append(", ");
/* 59 */     stringBuilder.append("visuals = ").append(get_visuals()).append(", ");
/* 60 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/Depth.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */