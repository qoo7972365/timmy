/*     */ package sun.awt.X11;
/*     */ 
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ public class XkbMapNotifyEvent
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
/*     */   public XkbMapNotifyEvent(long paramLong) {
/*  20 */     log.finest("Creating");
/*  21 */     this.pData = paramLong;
/*  22 */     this.should_free_memory = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public XkbMapNotifyEvent() {
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
/*  56 */   public int get_flags() { log.finest(""); return Native.getInt(this.pData + 52L); }
/*  57 */   public void set_flags(int paramInt) { log.finest(""); Native.putInt(this.pData + 52L, paramInt); }
/*  58 */   public int get_first_type() { log.finest(""); return Native.getInt(this.pData + 56L); }
/*  59 */   public void set_first_type(int paramInt) { log.finest(""); Native.putInt(this.pData + 56L, paramInt); }
/*  60 */   public int get_num_types() { log.finest(""); return Native.getInt(this.pData + 60L); }
/*  61 */   public void set_num_types(int paramInt) { log.finest(""); Native.putInt(this.pData + 60L, paramInt); }
/*  62 */   public int get_min_key_code() { log.finest(""); return Native.getInt(this.pData + 64L); }
/*  63 */   public void set_min_key_code(int paramInt) { log.finest(""); Native.putInt(this.pData + 64L, paramInt); }
/*  64 */   public int get_max_key_code() { log.finest(""); return Native.getInt(this.pData + 65L); }
/*  65 */   public void set_max_key_code(int paramInt) { log.finest(""); Native.putInt(this.pData + 65L, paramInt); }
/*  66 */   public int get_first_key_sym() { log.finest(""); return Native.getInt(this.pData + 66L); }
/*  67 */   public void set_first_key_sym(int paramInt) { log.finest(""); Native.putInt(this.pData + 66L, paramInt); }
/*  68 */   public int get_first_key_act() { log.finest(""); return Native.getInt(this.pData + 67L); }
/*  69 */   public void set_first_key_act(int paramInt) { log.finest(""); Native.putInt(this.pData + 67L, paramInt); }
/*  70 */   public int get_first_key_behavior() { log.finest(""); return Native.getInt(this.pData + 68L); }
/*  71 */   public void set_first_key_behavior(int paramInt) { log.finest(""); Native.putInt(this.pData + 68L, paramInt); }
/*  72 */   public int get_first_key_explicit() { log.finest(""); return Native.getInt(this.pData + 69L); }
/*  73 */   public void set_first_key_explicit(int paramInt) { log.finest(""); Native.putInt(this.pData + 69L, paramInt); }
/*  74 */   public int get_first_modmap_key() { log.finest(""); return Native.getInt(this.pData + 70L); }
/*  75 */   public void set_first_modmap_key(int paramInt) { log.finest(""); Native.putInt(this.pData + 70L, paramInt); }
/*  76 */   public int get_first_vmodmap_key() { log.finest(""); return Native.getInt(this.pData + 71L); }
/*  77 */   public void set_first_vmodmap_key(int paramInt) { log.finest(""); Native.putInt(this.pData + 71L, paramInt); }
/*  78 */   public int get_num_key_syms() { log.finest(""); return Native.getInt(this.pData + 72L); }
/*  79 */   public void set_num_key_syms(int paramInt) { log.finest(""); Native.putInt(this.pData + 72L, paramInt); }
/*  80 */   public int get_num_key_acts() { log.finest(""); return Native.getInt(this.pData + 76L); }
/*  81 */   public void set_num_key_acts(int paramInt) { log.finest(""); Native.putInt(this.pData + 76L, paramInt); }
/*  82 */   public int get_num_key_behaviors() { log.finest(""); return Native.getInt(this.pData + 80L); }
/*  83 */   public void set_num_key_behaviors(int paramInt) { log.finest(""); Native.putInt(this.pData + 80L, paramInt); }
/*  84 */   public int get_num_key_explicit() { log.finest(""); return Native.getInt(this.pData + 84L); }
/*  85 */   public void set_num_key_explicit(int paramInt) { log.finest(""); Native.putInt(this.pData + 84L, paramInt); }
/*  86 */   public int get_num_modmap_keys() { log.finest(""); return Native.getInt(this.pData + 88L); }
/*  87 */   public void set_num_modmap_keys(int paramInt) { log.finest(""); Native.putInt(this.pData + 88L, paramInt); }
/*  88 */   public int get_num_vmodmap_keys() { log.finest(""); return Native.getInt(this.pData + 92L); }
/*  89 */   public void set_num_vmodmap_keys(int paramInt) { log.finest(""); Native.putInt(this.pData + 92L, paramInt); }
/*  90 */   public int get_vmods() { log.finest(""); return Native.getInt(this.pData + 96L); } public void set_vmods(int paramInt) {
/*  91 */     log.finest(""); Native.putInt(this.pData + 96L, paramInt);
/*     */   }
/*     */   
/*     */   String getName() {
/*  95 */     return "XkbMapNotifyEvent";
/*     */   }
/*     */ 
/*     */   
/*     */   String getFieldsAsString() {
/* 100 */     StringBuilder stringBuilder = new StringBuilder(1040);
/*     */     
/* 102 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/* 103 */     stringBuilder.append("serial = ").append(get_serial()).append(", ");
/* 104 */     stringBuilder.append("send_event = ").append(get_send_event()).append(", ");
/* 105 */     stringBuilder.append("display = ").append(get_display()).append(", ");
/* 106 */     stringBuilder.append("time = ").append(get_time()).append(", ");
/* 107 */     stringBuilder.append("xkb_type = ").append(get_xkb_type()).append(", ");
/* 108 */     stringBuilder.append("device = ").append(get_device()).append(", ");
/* 109 */     stringBuilder.append("changed = ").append(get_changed()).append(", ");
/* 110 */     stringBuilder.append("flags = ").append(get_flags()).append(", ");
/* 111 */     stringBuilder.append("first_type = ").append(get_first_type()).append(", ");
/* 112 */     stringBuilder.append("num_types = ").append(get_num_types()).append(", ");
/* 113 */     stringBuilder.append("min_key_code = ").append(get_min_key_code()).append(", ");
/* 114 */     stringBuilder.append("max_key_code = ").append(get_max_key_code()).append(", ");
/* 115 */     stringBuilder.append("first_key_sym = ").append(get_first_key_sym()).append(", ");
/* 116 */     stringBuilder.append("first_key_act = ").append(get_first_key_act()).append(", ");
/* 117 */     stringBuilder.append("first_key_behavior = ").append(get_first_key_behavior()).append(", ");
/* 118 */     stringBuilder.append("first_key_explicit = ").append(get_first_key_explicit()).append(", ");
/* 119 */     stringBuilder.append("first_modmap_key = ").append(get_first_modmap_key()).append(", ");
/* 120 */     stringBuilder.append("first_vmodmap_key = ").append(get_first_vmodmap_key()).append(", ");
/* 121 */     stringBuilder.append("num_key_syms = ").append(get_num_key_syms()).append(", ");
/* 122 */     stringBuilder.append("num_key_acts = ").append(get_num_key_acts()).append(", ");
/* 123 */     stringBuilder.append("num_key_behaviors = ").append(get_num_key_behaviors()).append(", ");
/* 124 */     stringBuilder.append("num_key_explicit = ").append(get_num_key_explicit()).append(", ");
/* 125 */     stringBuilder.append("num_modmap_keys = ").append(get_num_modmap_keys()).append(", ");
/* 126 */     stringBuilder.append("num_vmodmap_keys = ").append(get_num_vmodmap_keys()).append(", ");
/* 127 */     stringBuilder.append("vmods = ").append(get_vmods()).append(", ");
/* 128 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XkbMapNotifyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */