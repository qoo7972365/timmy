/*     */ package sun.awt.X11;
/*     */ 
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ public class XEvent
/*     */   extends XWrapperBase
/*     */ {
/*     */   private final boolean should_free_memory;
/*   9 */   private Unsafe unsafe = XlibWrapper.unsafe; long pData;
/*     */   public static int getSize() {
/*  11 */     return 192; } public int getDataSize() {
/*  12 */     return getSize();
/*     */   }
/*     */   
/*     */   public long getPData() {
/*  16 */     return this.pData;
/*     */   }
/*     */   
/*     */   public XEvent(long paramLong) {
/*  20 */     log.finest("Creating");
/*  21 */     this.pData = paramLong;
/*  22 */     this.should_free_memory = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public XEvent() {
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
/*  42 */   public XAnyEvent get_xany() { log.finest(""); return new XAnyEvent(this.pData + 0L); }
/*  43 */   public XKeyEvent get_xkey() { log.finest(""); return new XKeyEvent(this.pData + 0L); }
/*  44 */   public XButtonEvent get_xbutton() { log.finest(""); return new XButtonEvent(this.pData + 0L); }
/*  45 */   public XMotionEvent get_xmotion() { log.finest(""); return new XMotionEvent(this.pData + 0L); }
/*  46 */   public XCrossingEvent get_xcrossing() { log.finest(""); return new XCrossingEvent(this.pData + 0L); }
/*  47 */   public XFocusChangeEvent get_xfocus() { log.finest(""); return new XFocusChangeEvent(this.pData + 0L); }
/*  48 */   public XExposeEvent get_xexpose() { log.finest(""); return new XExposeEvent(this.pData + 0L); }
/*  49 */   public XGraphicsExposeEvent get_xgraphicsexpose() { log.finest(""); return new XGraphicsExposeEvent(this.pData + 0L); }
/*  50 */   public XNoExposeEvent get_xnoexpose() { log.finest(""); return new XNoExposeEvent(this.pData + 0L); }
/*  51 */   public XVisibilityEvent get_xvisibility() { log.finest(""); return new XVisibilityEvent(this.pData + 0L); }
/*  52 */   public XCreateWindowEvent get_xcreatewindow() { log.finest(""); return new XCreateWindowEvent(this.pData + 0L); }
/*  53 */   public XDestroyWindowEvent get_xdestroywindow() { log.finest(""); return new XDestroyWindowEvent(this.pData + 0L); }
/*  54 */   public XUnmapEvent get_xunmap() { log.finest(""); return new XUnmapEvent(this.pData + 0L); }
/*  55 */   public XMapEvent get_xmap() { log.finest(""); return new XMapEvent(this.pData + 0L); }
/*  56 */   public XMapRequestEvent get_xmaprequest() { log.finest(""); return new XMapRequestEvent(this.pData + 0L); }
/*  57 */   public XReparentEvent get_xreparent() { log.finest(""); return new XReparentEvent(this.pData + 0L); }
/*  58 */   public XConfigureEvent get_xconfigure() { log.finest(""); return new XConfigureEvent(this.pData + 0L); }
/*  59 */   public XGravityEvent get_xgravity() { log.finest(""); return new XGravityEvent(this.pData + 0L); }
/*  60 */   public XResizeRequestEvent get_xresizerequest() { log.finest(""); return new XResizeRequestEvent(this.pData + 0L); }
/*  61 */   public XConfigureRequestEvent get_xconfigurerequest() { log.finest(""); return new XConfigureRequestEvent(this.pData + 0L); }
/*  62 */   public XCirculateEvent get_xcirculate() { log.finest(""); return new XCirculateEvent(this.pData + 0L); }
/*  63 */   public XCirculateRequestEvent get_xcirculaterequest() { log.finest(""); return new XCirculateRequestEvent(this.pData + 0L); }
/*  64 */   public XPropertyEvent get_xproperty() { log.finest(""); return new XPropertyEvent(this.pData + 0L); }
/*  65 */   public XSelectionClearEvent get_xselectionclear() { log.finest(""); return new XSelectionClearEvent(this.pData + 0L); }
/*  66 */   public XSelectionRequestEvent get_xselectionrequest() { log.finest(""); return new XSelectionRequestEvent(this.pData + 0L); }
/*  67 */   public XSelectionEvent get_xselection() { log.finest(""); return new XSelectionEvent(this.pData + 0L); }
/*  68 */   public XColormapEvent get_xcolormap() { log.finest(""); return new XColormapEvent(this.pData + 0L); }
/*  69 */   public XClientMessageEvent get_xclient() { log.finest(""); return new XClientMessageEvent(this.pData + 0L); }
/*  70 */   public XMappingEvent get_xmapping() { log.finest(""); return new XMappingEvent(this.pData + 0L); }
/*  71 */   public XErrorEvent get_xerror() { log.finest(""); return new XErrorEvent(this.pData + 0L); }
/*  72 */   public XKeymapEvent get_xkeymap() { log.finest(""); return new XKeymapEvent(this.pData + 0L); }
/*  73 */   public long get_pad(int paramInt) { log.finest(""); return Native.getLong(this.pData + 0L + (paramInt * Native.getLongSize())); }
/*  74 */   public void set_pad(int paramInt, long paramLong) { log.finest(""); Native.putLong(this.pData + 0L + (paramInt * Native.getLongSize()), paramLong); } public long get_pad() {
/*  75 */     log.finest(""); return this.pData + 0L;
/*     */   }
/*     */   
/*     */   String getName() {
/*  79 */     return "XEvent";
/*     */   }
/*     */ 
/*     */   
/*     */   String getFieldsAsString() {
/*  84 */     StringBuilder stringBuilder = new StringBuilder(1320);
/*     */     
/*  86 */     stringBuilder.append("type = ").append(XlibWrapper.eventToString[get_type()]).append(", ");
/*  87 */     stringBuilder.append("xany = ").append(get_xany()).append(", ");
/*  88 */     stringBuilder.append("xkey = ").append(get_xkey()).append(", ");
/*  89 */     stringBuilder.append("xbutton = ").append(get_xbutton()).append(", ");
/*  90 */     stringBuilder.append("xmotion = ").append(get_xmotion()).append(", ");
/*  91 */     stringBuilder.append("xcrossing = ").append(get_xcrossing()).append(", ");
/*  92 */     stringBuilder.append("xfocus = ").append(get_xfocus()).append(", ");
/*  93 */     stringBuilder.append("xexpose = ").append(get_xexpose()).append(", ");
/*  94 */     stringBuilder.append("xgraphicsexpose = ").append(get_xgraphicsexpose()).append(", ");
/*  95 */     stringBuilder.append("xnoexpose = ").append(get_xnoexpose()).append(", ");
/*  96 */     stringBuilder.append("xvisibility = ").append(get_xvisibility()).append(", ");
/*  97 */     stringBuilder.append("xcreatewindow = ").append(get_xcreatewindow()).append(", ");
/*  98 */     stringBuilder.append("xdestroywindow = ").append(get_xdestroywindow()).append(", ");
/*  99 */     stringBuilder.append("xunmap = ").append(get_xunmap()).append(", ");
/* 100 */     stringBuilder.append("xmap = ").append(get_xmap()).append(", ");
/* 101 */     stringBuilder.append("xmaprequest = ").append(get_xmaprequest()).append(", ");
/* 102 */     stringBuilder.append("xreparent = ").append(get_xreparent()).append(", ");
/* 103 */     stringBuilder.append("xconfigure = ").append(get_xconfigure()).append(", ");
/* 104 */     stringBuilder.append("xgravity = ").append(get_xgravity()).append(", ");
/* 105 */     stringBuilder.append("xresizerequest = ").append(get_xresizerequest()).append(", ");
/* 106 */     stringBuilder.append("xconfigurerequest = ").append(get_xconfigurerequest()).append(", ");
/* 107 */     stringBuilder.append("xcirculate = ").append(get_xcirculate()).append(", ");
/* 108 */     stringBuilder.append("xcirculaterequest = ").append(get_xcirculaterequest()).append(", ");
/* 109 */     stringBuilder.append("xproperty = ").append(get_xproperty()).append(", ");
/* 110 */     stringBuilder.append("xselectionclear = ").append(get_xselectionclear()).append(", ");
/* 111 */     stringBuilder.append("xselectionrequest = ").append(get_xselectionrequest()).append(", ");
/* 112 */     stringBuilder.append("xselection = ").append(get_xselection()).append(", ");
/* 113 */     stringBuilder.append("xcolormap = ").append(get_xcolormap()).append(", ");
/* 114 */     stringBuilder.append("xclient = ").append(get_xclient()).append(", ");
/* 115 */     stringBuilder.append("xmapping = ").append(get_xmapping()).append(", ");
/* 116 */     stringBuilder.append("xerror = ").append(get_xerror()).append(", ");
/* 117 */     stringBuilder.append("xkeymap = ").append(get_xkeymap()).append(", ");
/* 118 */     stringBuilder.append("{")
/* 119 */       .append(get_pad(0)).append(" ")
/* 120 */       .append(get_pad(1)).append(" ")
/* 121 */       .append(get_pad(2)).append(" ")
/* 122 */       .append(get_pad(3)).append(" ")
/* 123 */       .append(get_pad(4)).append(" ")
/* 124 */       .append(get_pad(5)).append(" ")
/* 125 */       .append(get_pad(6)).append(" ")
/* 126 */       .append(get_pad(7)).append(" ")
/* 127 */       .append(get_pad(8)).append(" ")
/* 128 */       .append(get_pad(9)).append(" ")
/* 129 */       .append(get_pad(10)).append(" ")
/* 130 */       .append(get_pad(11)).append(" ")
/* 131 */       .append(get_pad(12)).append(" ")
/* 132 */       .append(get_pad(13)).append(" ")
/* 133 */       .append(get_pad(14)).append(" ")
/* 134 */       .append(get_pad(15)).append(" ")
/* 135 */       .append(get_pad(16)).append(" ")
/* 136 */       .append(get_pad(17)).append(" ")
/* 137 */       .append(get_pad(18)).append(" ")
/* 138 */       .append(get_pad(19)).append(" ")
/* 139 */       .append(get_pad(20)).append(" ")
/* 140 */       .append(get_pad(21)).append(" ")
/* 141 */       .append(get_pad(22)).append(" ")
/* 142 */       .append(get_pad(23)).append(" ").append("}");
/* 143 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */