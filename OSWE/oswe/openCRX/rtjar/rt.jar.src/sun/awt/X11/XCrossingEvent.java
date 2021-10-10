/*     */ package sun.awt.X11;
/*     */ 
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ public class XCrossingEvent
/*     */   extends XWrapperBase
/*     */ {
/*     */   private final boolean should_free_memory;
/*   9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*     */   public static int getSize() {
/*  11 */     return 104; } public int getDataSize() {
/*  12 */     return getSize();
/*     */   }
/*     */   
/*     */   public long getPData() {
/*  16 */     return this.pData;
/*     */   }
/*     */   
/*     */   public XCrossingEvent(long paramLong) {
/*  20 */     log.finest("Creating");
/*  21 */     this.pData = paramLong;
/*  22 */     this.should_free_memory = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public XCrossingEvent() {
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
/*  50 */   public long get_root() { log.finest(""); return Native.getLong(this.pData + 40L); }
/*  51 */   public void set_root(long paramLong) { log.finest(""); Native.putLong(this.pData + 40L, paramLong); }
/*  52 */   public long get_subwindow() { log.finest(""); return Native.getLong(this.pData + 48L); }
/*  53 */   public void set_subwindow(long paramLong) { log.finest(""); Native.putLong(this.pData + 48L, paramLong); }
/*  54 */   public long get_time() { log.finest(""); return Native.getULong(this.pData + 56L); }
/*  55 */   public void set_time(long paramLong) { log.finest(""); Native.putULong(this.pData + 56L, paramLong); }
/*  56 */   public int get_x() { log.finest(""); return Native.getInt(this.pData + 64L); }
/*  57 */   public void set_x(int paramInt) { log.finest(""); Native.putInt(this.pData + 64L, paramInt); }
/*  58 */   public int get_y() { log.finest(""); return Native.getInt(this.pData + 68L); }
/*  59 */   public void set_y(int paramInt) { log.finest(""); Native.putInt(this.pData + 68L, paramInt); }
/*  60 */   public int get_x_root() { log.finest(""); return Native.getInt(this.pData + 72L); }
/*  61 */   public void set_x_root(int paramInt) { log.finest(""); Native.putInt(this.pData + 72L, paramInt); }
/*  62 */   public int get_y_root() { log.finest(""); return Native.getInt(this.pData + 76L); }
/*  63 */   public void set_y_root(int paramInt) { log.finest(""); Native.putInt(this.pData + 76L, paramInt); }
/*  64 */   public int get_mode() { log.finest(""); return Native.getInt(this.pData + 80L); }
/*  65 */   public void set_mode(int paramInt) { log.finest(""); Native.putInt(this.pData + 80L, paramInt); }
/*  66 */   public int get_detail() { log.finest(""); return Native.getInt(this.pData + 84L); }
/*  67 */   public void set_detail(int paramInt) { log.finest(""); Native.putInt(this.pData + 84L, paramInt); }
/*  68 */   public boolean get_same_screen() { log.finest(""); return Native.getBool(this.pData + 88L); }
/*  69 */   public void set_same_screen(boolean paramBoolean) { log.finest(""); Native.putBool(this.pData + 88L, paramBoolean); }
/*  70 */   public boolean get_focus() { log.finest(""); return Native.getBool(this.pData + 92L); }
/*  71 */   public void set_focus(boolean paramBoolean) { log.finest(""); Native.putBool(this.pData + 92L, paramBoolean); }
/*  72 */   public int get_state() { log.finest(""); return Native.getInt(this.pData + 96L); } public void set_state(int paramInt) {
/*  73 */     log.finest(""); Native.putInt(this.pData + 96L, paramInt);
/*     */   }
/*     */   
/*     */   String getName() {
/*  77 */     return "XCrossingEvent";
/*     */   }
/*     */ 
/*     */   
/*     */   String getFieldsAsString() {
/*  82 */     StringBuilder stringBuilder = new StringBuilder(680);
/*     */     
/*  84 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/*  85 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/*  86 */     stringBuilder.append("send_event = ").append(get_send_event()).append(", ");
/*  87 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/*  88 */     stringBuilder.append("window = ").append(getWindow(get_window())).append(", ");
/*  89 */     stringBuilder.append("root = ").append(get_root()).append(", ");
/*  90 */     stringBuilder.append("subwindow = ").append(get_subwindow()).append(", ");
/*  91 */     stringBuilder.append("time = ").append(get_time()).append(", ");
/*  92 */     stringBuilder.append("x = ").append(get_x()).append(", ");
/*  93 */     stringBuilder.append("y = ").append(get_y()).append(", ");
/*  94 */     stringBuilder.append("x_root = ").append(get_x_root()).append(", ");
/*  95 */     stringBuilder.append("y_root = ").append(get_y_root()).append(", ");
/*  96 */     stringBuilder.append("mode = ").append(get_mode()).append(", ");
/*  97 */     stringBuilder.append("detail = ").append(get_detail()).append(", ");
/*  98 */     stringBuilder.append("same_screen = ").append(get_same_screen()).append(", ");
/*  99 */     stringBuilder.append("focus = ").append(get_focus()).append(", ");
/* 100 */     stringBuilder.append("state = ").append(get_state()).append(", ");
/* 101 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XCrossingEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */