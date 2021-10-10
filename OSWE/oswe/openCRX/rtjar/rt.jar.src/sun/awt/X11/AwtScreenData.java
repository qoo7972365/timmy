/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class AwtScreenData
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 48; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public AwtScreenData(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public AwtScreenData() {
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
/* 40 */   public int get_numConfigs() { log.finest(""); return Native.getInt(this.pData + 0L); }
/* 41 */   public void set_numConfigs(int paramInt) { log.finest(""); Native.putInt(this.pData + 0L, paramInt); }
/* 42 */   public long get_root() { log.finest(""); return Native.getLong(this.pData + 8L); }
/* 43 */   public void set_root(long paramLong) { log.finest(""); Native.putLong(this.pData + 8L, paramLong); }
/* 44 */   public long get_whitepixel() { log.finest(""); return Native.getLong(this.pData + 16L); }
/* 45 */   public void set_whitepixel(long paramLong) { log.finest(""); Native.putLong(this.pData + 16L, paramLong); }
/* 46 */   public long get_blackpixel() { log.finest(""); return Native.getLong(this.pData + 24L); }
/* 47 */   public void set_blackpixel(long paramLong) { log.finest(""); Native.putLong(this.pData + 24L, paramLong); }
/* 48 */   public AwtGraphicsConfigData get_defaultConfig(int paramInt) { log.finest(""); return (Native.getLong(this.pData + 32L) != 0L) ? new AwtGraphicsConfigData(Native.getLong(this.pData + 32L) + (paramInt * 208)) : null; }
/* 49 */   public long get_defaultConfig() { log.finest(""); return Native.getLong(this.pData + 32L); }
/* 50 */   public void set_defaultConfig(long paramLong) { log.finest(""); Native.putLong(this.pData + 32L, paramLong); }
/* 51 */   public long get_configs(int paramInt) { log.finest(""); return Native.getLong(this.pData + 40L) + (paramInt * Native.getLongSize()); }
/* 52 */   public long get_configs() { log.finest(""); return Native.getLong(this.pData + 40L); } public void set_configs(long paramLong) {
/* 53 */     log.finest(""); Native.putLong(this.pData + 40L, paramLong);
/*    */   }
/*    */   
/*    */   String getName() {
/* 57 */     return "AwtScreenData";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 62 */     StringBuilder stringBuilder = new StringBuilder(240);
/*    */     
/* 64 */     stringBuilder.append("numConfigs = ").append(get_numConfigs()).append(", ");
/* 65 */     stringBuilder.append("root = ").append(get_root()).append(", ");
/* 66 */     stringBuilder.append("whitepixel = ").append(get_whitepixel()).append(", ");
/* 67 */     stringBuilder.append("blackpixel = ").append(get_blackpixel()).append(", ");
/* 68 */     stringBuilder.append("defaultConfig = ").append(get_defaultConfig()).append(", ");
/* 69 */     stringBuilder.append("configs = ").append(get_configs()).append(", ");
/* 70 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/AwtScreenData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */