/*     */ package sun.awt.X11;
/*     */ 
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ public class XkbNamesNotifyEvent
/*     */   extends XWrapperBase
/*     */ {
/*     */   private final boolean should_free_memory;
/*   9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*     */   public static int getSize() {
/*  11 */     return 96; } public int getDataSize() {
/*  12 */     return getSize();
/*     */   }
/*     */   
/*     */   public long getPData() {
/*  16 */     return this.pData;
/*     */   }
/*     */   
/*     */   public XkbNamesNotifyEvent(long paramLong) {
/*  20 */     log.finest("Creating");
/*  21 */     this.pData = paramLong;
/*  22 */     this.should_free_memory = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public XkbNamesNotifyEvent() {
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
/*  42 */   public long get_serial() { log.finest(""); return Native.getULong(this.pData + 8L); }
/*  43 */   public void set_serial(long paramLong) { log.finest(""); Native.putULong(this.pData + 8L, paramLong); }
/*  44 */   public boolean get_send_event() { log.finest(""); return Native.getBool(this.pData + 16L); }
/*  45 */   public void set_send_event(boolean paramBoolean) { log.finest(""); Native.putBool(this.pData + 16L, paramBoolean); }
/*  46 */   public long get_display() { log.finest(""); return Native.getLong(this.pData + 24L); }
/*  47 */   public void set_display(long paramLong) { log.finest(""); Native.putLong(this.pData + 24L, paramLong); }
/*  48 */   public long get_time() { log.finest(""); return Native.getULong(this.pData + 32L); }
/*  49 */   public void set_time(long paramLong) { log.finest(""); Native.putULong(this.pData + 32L, paramLong); }
/*  50 */   public int get_xkb_type() { log.finest(""); return Native.getInt(this.pData + 40L); }
/*  51 */   public void set_xkb_type(int paramInt) { log.finest(""); Native.putInt(this.pData + 40L, paramInt); }
/*  52 */   public int get_device() { log.finest(""); return Native.getInt(this.pData + 44L); }
/*  53 */   public void set_device(int paramInt) { log.finest(""); Native.putInt(this.pData + 44L, paramInt); }
/*  54 */   public int get_changed() { log.finest(""); return Native.getInt(this.pData + 48L); }
/*  55 */   public void set_changed(int paramInt) { log.finest(""); Native.putInt(this.pData + 48L, paramInt); }
/*  56 */   public int get_first_type() { log.finest(""); return Native.getInt(this.pData + 52L); }
/*  57 */   public void set_first_type(int paramInt) { log.finest(""); Native.putInt(this.pData + 52L, paramInt); }
/*  58 */   public int get_num_types() { log.finest(""); return Native.getInt(this.pData + 56L); }
/*  59 */   public void set_num_types(int paramInt) { log.finest(""); Native.putInt(this.pData + 56L, paramInt); }
/*  60 */   public int get_first_lvl() { log.finest(""); return Native.getInt(this.pData + 60L); }
/*  61 */   public void set_first_lvl(int paramInt) { log.finest(""); Native.putInt(this.pData + 60L, paramInt); }
/*  62 */   public int get_num_lvls() { log.finest(""); return Native.getInt(this.pData + 64L); }
/*  63 */   public void set_num_lvls(int paramInt) { log.finest(""); Native.putInt(this.pData + 64L, paramInt); }
/*  64 */   public int get_num_aliases() { log.finest(""); return Native.getInt(this.pData + 68L); }
/*  65 */   public void set_num_aliases(int paramInt) { log.finest(""); Native.putInt(this.pData + 68L, paramInt); }
/*  66 */   public int get_num_radio_groups() { log.finest(""); return Native.getInt(this.pData + 72L); }
/*  67 */   public void set_num_radio_groups(int paramInt) { log.finest(""); Native.putInt(this.pData + 72L, paramInt); }
/*  68 */   public int get_changed_vmods() { log.finest(""); return Native.getInt(this.pData + 76L); }
/*  69 */   public void set_changed_vmods(int paramInt) { log.finest(""); Native.putInt(this.pData + 76L, paramInt); }
/*  70 */   public int get_changed_groups() { log.finest(""); return Native.getInt(this.pData + 80L); }
/*  71 */   public void set_changed_groups(int paramInt) { log.finest(""); Native.putInt(this.pData + 80L, paramInt); }
/*  72 */   public int get_changed_indicators() { log.finest(""); return Native.getInt(this.pData + 84L); }
/*  73 */   public void set_changed_indicators(int paramInt) { log.finest(""); Native.putInt(this.pData + 84L, paramInt); }
/*  74 */   public int get_first_key() { log.finest(""); return Native.getInt(this.pData + 88L); }
/*  75 */   public void set_first_key(int paramInt) { log.finest(""); Native.putInt(this.pData + 88L, paramInt); }
/*  76 */   public int get_num_keys() { log.finest(""); return Native.getInt(this.pData + 92L); } public void set_num_keys(int paramInt) {
/*  77 */     log.finest(""); Native.putInt(this.pData + 92L, paramInt);
/*     */   }
/*     */   
/*     */   String getName() {
/*  81 */     return "XkbNamesNotifyEvent";
/*     */   }
/*     */ 
/*     */   
/*     */   String getFieldsAsString() {
/*  86 */     StringBuilder stringBuilder = new StringBuilder(760);
/*     */     
/*  88 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/*  89 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/*  90 */     stringBuilder.append("send_event = ").append(get_send_event()).append(", ");
/*  91 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/*  92 */     stringBuilder.append("time = ").append(get_time()).append(", ");
/*  93 */     stringBuilder.append("xkb_type = ").append(get_xkb_type()).append(", ");
/*  94 */     stringBuilder.append("device = ").append(get_device()).append(", ");
/*  95 */     stringBuilder.append("changed = ").append(get_changed()).append(", ");
/*  96 */     stringBuilder.append("first_type = ").append(get_first_type()).append(", ");
/*  97 */     stringBuilder.append("num_types = ").append(get_num_types()).append(", ");
/*  98 */     stringBuilder.append("first_lvl = ").append(get_first_lvl()).append(", ");
/*  99 */     stringBuilder.append("num_lvls = ").append(get_num_lvls()).append(", ");
/* 100 */     stringBuilder.append("num_aliases = ").append(get_num_aliases()).append(", ");
/* 101 */     stringBuilder.append("num_radio_groups = ").append(get_num_radio_groups()).append(", ");
/* 102 */     stringBuilder.append("changed_vmods = ").append(get_changed_vmods()).append(", ");
/* 103 */     stringBuilder.append("changed_groups = ").append(get_changed_groups()).append(", ");
/* 104 */     stringBuilder.append("changed_indicators = ").append(get_changed_indicators()).append(", ");
/* 105 */     stringBuilder.append("first_key = ").append(get_first_key()).append(", ");
/* 106 */     stringBuilder.append("num_keys = ").append(get_num_keys()).append(", ");
/* 107 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XkbNamesNotifyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */