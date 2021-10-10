/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class awtImageData
/*    */   extends XWrapperBase
/*    */ {
/*    */   private final boolean should_free_memory;
/*  9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*    */   public static int getSize() {
/* 11 */     return 560; } public int getDataSize() {
/* 12 */     return getSize();
/*    */   }
/*    */   
/*    */   public long getPData() {
/* 16 */     return this.pData;
/*    */   }
/*    */   
/*    */   public awtImageData(long paramLong) {
/* 20 */     log.finest("Creating");
/* 21 */     this.pData = paramLong;
/* 22 */     this.should_free_memory = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public awtImageData() {
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
/* 40 */   public int get_Depth() { log.finest(""); return Native.getInt(this.pData + 0L); }
/* 41 */   public void set_Depth(int paramInt) { log.finest(""); Native.putInt(this.pData + 0L, paramInt); }
/* 42 */   public XPixmapFormatValues get_wsImageFormat() { log.finest(""); return new XPixmapFormatValues(this.pData + 4L); }
/* 43 */   public long get_clrdata(int paramInt) { log.finest(""); return Native.getLong(this.pData + 16L) + (paramInt * Native.getLongSize()); }
/* 44 */   public long get_clrdata() { log.finest(""); return Native.getLong(this.pData + 16L); }
/* 45 */   public void set_clrdata(long paramLong) { log.finest(""); Native.putLong(this.pData + 16L, paramLong); }
/* 46 */   public long get_convert(int paramInt) { log.finest(""); return Native.getLong(this.pData + 48L + (paramInt * Native.getLongSize())); }
/* 47 */   public void set_convert(int paramInt, long paramLong) { log.finest(""); Native.putLong(this.pData + 48L + (paramInt * Native.getLongSize()), paramLong); } public long get_convert() {
/* 48 */     log.finest(""); return this.pData + 48L;
/*    */   }
/*    */   
/*    */   String getName() {
/* 52 */     return "awtImageData";
/*    */   }
/*    */ 
/*    */   
/*    */   String getFieldsAsString() {
/* 57 */     StringBuilder stringBuilder = new StringBuilder(160);
/*    */     
/* 59 */     stringBuilder.append("Depth = ").append(get_Depth()).append(", ");
/* 60 */     stringBuilder.append("wsImageFormat = ").append(get_wsImageFormat()).append(", ");
/* 61 */     stringBuilder.append("clrdata = ").append(get_clrdata()).append(", ");
/* 62 */     stringBuilder.append("{")
/* 63 */       .append(get_convert(0)).append(" ")
/* 64 */       .append(get_convert(1)).append(" ")
/* 65 */       .append(get_convert(2)).append(" ")
/* 66 */       .append(get_convert(3)).append(" ")
/* 67 */       .append(get_convert(4)).append(" ")
/* 68 */       .append(get_convert(5)).append(" ")
/* 69 */       .append(get_convert(6)).append(" ")
/* 70 */       .append(get_convert(7)).append(" ")
/* 71 */       .append(get_convert(8)).append(" ")
/* 72 */       .append(get_convert(9)).append(" ")
/* 73 */       .append(get_convert(10)).append(" ")
/* 74 */       .append(get_convert(11)).append(" ")
/* 75 */       .append(get_convert(12)).append(" ")
/* 76 */       .append(get_convert(13)).append(" ")
/* 77 */       .append(get_convert(14)).append(" ")
/* 78 */       .append(get_convert(15)).append(" ")
/* 79 */       .append(get_convert(16)).append(" ")
/* 80 */       .append(get_convert(17)).append(" ")
/* 81 */       .append(get_convert(18)).append(" ")
/* 82 */       .append(get_convert(19)).append(" ")
/* 83 */       .append(get_convert(20)).append(" ")
/* 84 */       .append(get_convert(21)).append(" ")
/* 85 */       .append(get_convert(22)).append(" ")
/* 86 */       .append(get_convert(23)).append(" ")
/* 87 */       .append(get_convert(24)).append(" ")
/* 88 */       .append(get_convert(25)).append(" ")
/* 89 */       .append(get_convert(26)).append(" ")
/* 90 */       .append(get_convert(27)).append(" ")
/* 91 */       .append(get_convert(28)).append(" ")
/* 92 */       .append(get_convert(29)).append(" ")
/* 93 */       .append(get_convert(30)).append(" ")
/* 94 */       .append(get_convert(31)).append(" ").append("}");
/* 95 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/awtImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */