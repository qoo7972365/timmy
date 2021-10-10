/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class XModifierKeymap
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
/*    */   public XModifierKeymap(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public XModifierKeymap() {
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
/* 40 */   public int get_max_keypermod() { log.finest(""); return Native.getInt(this.pData + 0L); }
/* 41 */   public void set_max_keypermod(int paramInt) { log.finest(""); Native.putInt(this.pData + 0L, paramInt); }
/* 42 */   public long get_modifiermap(int paramInt) { log.finest(""); return Native.getLong(this.pData + 8L) + (paramInt * Native.getLongSize()); }
/* 43 */   public long get_modifiermap() { log.finest(""); return Native.getLong(this.pData + 8L); } public void set_modifiermap(long paramLong) {
/* 44 */     log.finest(""); Native.putLong(this.pData + 8L, paramLong);
/*    */   }
/*    */   
/*    */   String getName() {
/* 48 */     return "XModifierKeymap";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 53 */     StringBuilder stringBuilder = new StringBuilder(80);
/*    */     
/* 55 */     stringBuilder.append("max_keypermod = ").append(get_max_keypermod()).append(", ");
/* 56 */     stringBuilder.append("modifiermap = ").append(get_modifiermap()).append(", ");
/* 57 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XModifierKeymap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */