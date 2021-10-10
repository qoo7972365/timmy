/*     */ package sun.awt.X11;
/*     */ 
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ public class XKeymapEvent
/*     */   extends XWrapperBase
/*     */ {
/*     */   private final boolean should_free_memory;
/*   9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*     */   public static int getSize() {
/*  11 */     return 72; } public int getDataSize() {
/*  12 */     return getSize();
/*     */   }
/*     */   
/*     */   public long getPData() {
/*  16 */     return this.pData;
/*     */   }
/*     */   
/*     */   public XKeymapEvent(long paramLong) {
/*  20 */     log.finest("Creating");
/*  21 */     this.pData = paramLong;
/*  22 */     this.should_free_memory = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public XKeymapEvent() {
/*  27 */     log.finest("Creating");
/*  28 */     this.pData = this.unsafe.allocateMemory(getSize());
/*  29 */     this.should_free_memory = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/*  34 */     log.finest("Disposing");
/*  35 */     if (this.should_free_memory) {
/*  36 */       log.finest("freeing memory");
/*  37 */       this.unsafe.freeMemory(this.pData);
/*     */     } 
/*     */   }
/*  40 */   public int get_type() { log.finest(""); return Native.getInt(this.pData + 0L); }
/*  41 */   public void set_type(int paramInt) { log.finest(""); Native.putInt(this.pData + 0L, paramInt); }
/*  42 */   public long get_serial() { log.finest(""); return Native.getLong(this.pData + 8L); }
/*  43 */   public void set_serial(long paramLong) { log.finest(""); Native.putLong(this.pData + 8L, paramLong); }
/*  44 */   public boolean get_send_event() { log.finest(""); return Native.getBool(this.pData + 16L); }
/*  45 */   public void set_send_event(boolean paramBoolean) { log.finest(""); Native.putBool(this.pData + 16L, paramBoolean); }
/*  46 */   public long get_display() { log.finest(""); return Native.getLong(this.pData + 24L); }
/*  47 */   public void set_display(long paramLong) { log.finest(""); Native.putLong(this.pData + 24L, paramLong); }
/*  48 */   public long get_window() { log.finest(""); return Native.getLong(this.pData + 32L); }
/*  49 */   public void set_window(long paramLong) { log.finest(""); Native.putLong(this.pData + 32L, paramLong); }
/*  50 */   public byte get_key_vector(int paramInt) { log.finest(""); return Native.getByte(this.pData + 40L + (paramInt * 1)); }
/*  51 */   public void set_key_vector(int paramInt, byte paramByte) { log.finest(""); Native.putByte(this.pData + 40L + (paramInt * 1), paramByte); } public long get_key_vector() {
/*  52 */     log.finest(""); return this.pData + 40L;
/*     */   }
/*     */   
/*     */   String getName() {
/*  56 */     return "XKeymapEvent";
/*     */   }
/*     */ 
/*     */   
/*     */   String getFieldsAsString() {
/*  61 */     StringBuilder stringBuilder = new StringBuilder(240);
/*     */     
/*  63 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/*  64 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/*  65 */     stringBuilder.append("send_event = ").append(get_send_event()).append(", ");
/*  66 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/*  67 */     stringBuilder.append("window = ").append(getWindow(get_window())).append(", ");
/*  68 */     stringBuilder.append("{")
/*  69 */       .append(get_key_vector(0)).append(" ")
/*  70 */       .append(get_key_vector(1)).append(" ")
/*  71 */       .append(get_key_vector(2)).append(" ")
/*  72 */       .append(get_key_vector(3)).append(" ")
/*  73 */       .append(get_key_vector(4)).append(" ")
/*  74 */       .append(get_key_vector(5)).append(" ")
/*  75 */       .append(get_key_vector(6)).append(" ")
/*  76 */       .append(get_key_vector(7)).append(" ")
/*  77 */       .append(get_key_vector(8)).append(" ")
/*  78 */       .append(get_key_vector(9)).append(" ")
/*  79 */       .append(get_key_vector(10)).append(" ")
/*  80 */       .append(get_key_vector(11)).append(" ")
/*  81 */       .append(get_key_vector(12)).append(" ")
/*  82 */       .append(get_key_vector(13)).append(" ")
/*  83 */       .append(get_key_vector(14)).append(" ")
/*  84 */       .append(get_key_vector(15)).append(" ")
/*  85 */       .append(get_key_vector(16)).append(" ")
/*  86 */       .append(get_key_vector(17)).append(" ")
/*  87 */       .append(get_key_vector(18)).append(" ")
/*  88 */       .append(get_key_vector(19)).append(" ")
/*  89 */       .append(get_key_vector(20)).append(" ")
/*  90 */       .append(get_key_vector(21)).append(" ")
/*  91 */       .append(get_key_vector(22)).append(" ")
/*  92 */       .append(get_key_vector(23)).append(" ")
/*  93 */       .append(get_key_vector(24)).append(" ")
/*  94 */       .append(get_key_vector(25)).append(" ")
/*  95 */       .append(get_key_vector(26)).append(" ")
/*  96 */       .append(get_key_vector(27)).append(" ")
/*  97 */       .append(get_key_vector(28)).append(" ")
/*  98 */       .append(get_key_vector(29)).append(" ")
/*  99 */       .append(get_key_vector(30)).append(" ")
/* 100 */       .append(get_key_vector(31)).append(" ").append("}");
/* 101 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XKeymapEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */