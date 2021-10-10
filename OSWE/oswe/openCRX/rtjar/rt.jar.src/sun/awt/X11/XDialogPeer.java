/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Window;
/*     */ import java.awt.peer.DialogPeer;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import sun.awt.AWTAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XDialogPeer
/*     */   extends XDecoratedPeer
/*     */   implements DialogPeer
/*     */ {
/*     */   private Boolean undecorated;
/*     */   
/*     */   XDialogPeer(Dialog paramDialog) {
/*  40 */     super(paramDialog);
/*     */   }
/*     */   
/*     */   public void preInit(XCreateWindowParams paramXCreateWindowParams) {
/*  44 */     super.preInit(paramXCreateWindowParams);
/*     */     
/*  46 */     Dialog dialog = (Dialog)this.target;
/*  47 */     this.undecorated = Boolean.valueOf(dialog.isUndecorated());
/*  48 */     this.winAttr.nativeDecor = !dialog.isUndecorated();
/*  49 */     if (this.winAttr.nativeDecor) {
/*  50 */       this.winAttr.decorations = XWindowAttributesData.AWT_DECOR_ALL;
/*     */     } else {
/*  52 */       this.winAttr.decorations = XWindowAttributesData.AWT_DECOR_NONE;
/*     */     } 
/*  54 */     this.winAttr.functions = 1;
/*  55 */     this.winAttr.isResizable = true;
/*  56 */     this.winAttr.initialResizability = dialog.isResizable();
/*  57 */     this.winAttr.title = dialog.getTitle();
/*  58 */     this.winAttr.initialState = XWindowAttributesData.NORMAL;
/*     */   }
/*     */   
/*     */   public void setVisible(boolean paramBoolean) {
/*  62 */     XToolkit.awtLock();
/*     */     try {
/*  64 */       Dialog dialog = (Dialog)this.target;
/*  65 */       if (paramBoolean) {
/*  66 */         if (dialog.getModalityType() != Dialog.ModalityType.MODELESS && 
/*  67 */           !isModalBlocked()) {
/*  68 */           XBaseWindow.ungrabInput();
/*     */         }
/*     */       } else {
/*     */         
/*  72 */         restoreTransientFor(this);
/*  73 */         this.prevTransientFor = null;
/*  74 */         this.nextTransientFor = null;
/*     */       } 
/*     */     } finally {
/*  77 */       XToolkit.awtUnlock();
/*     */     } 
/*     */     
/*  80 */     super.setVisible(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isTargetUndecorated() {
/*  85 */     if (this.undecorated != null) {
/*  86 */       return this.undecorated.booleanValue();
/*     */     }
/*  88 */     return ((Dialog)this.target).isUndecorated();
/*     */   }
/*     */ 
/*     */   
/*     */   int getDecorations() {
/*  93 */     int i = super.getDecorations();
/*     */     
/*  95 */     if ((i & 0x1) != 0) {
/*  96 */       i |= 0x60;
/*     */     } else {
/*  98 */       i &= 0xFFFFFF9F;
/*     */     } 
/* 100 */     return i;
/*     */   }
/*     */   
/*     */   int getFunctions() {
/* 104 */     int i = super.getFunctions();
/*     */     
/* 106 */     if ((i & 0x1) != 0) {
/* 107 */       i |= 0x18;
/*     */     } else {
/* 109 */       i &= 0xFFFFFFE7;
/*     */     } 
/* 111 */     return i;
/*     */   }
/*     */   
/*     */   public void blockWindows(List<Window> paramList) {
/* 115 */     Vector<XWindowPeer> vector = null;
/* 116 */     XToolkit.awtLock();
/*     */     try {
/* 118 */       vector = XWindowPeer.collectJavaToplevels();
/* 119 */       for (Window window : paramList) {
/* 120 */         XWindowPeer xWindowPeer = AWTAccessor.getComponentAccessor().<XWindowPeer>getPeer(window);
/* 121 */         if (xWindowPeer != null) {
/* 122 */           xWindowPeer.setModalBlocked((Dialog)this.target, true, vector);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 126 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isFocusedWindowModalBlocker() {
/* 138 */     Window window = XKeyboardFocusManagerPeer.getInstance().getCurrentFocusedWindow();
/* 139 */     XWindowPeer xWindowPeer = null;
/*     */     
/* 141 */     if (window != null) {
/* 142 */       xWindowPeer = AWTAccessor.getComponentAccessor().<XWindowPeer>getPeer(window);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 149 */       xWindowPeer = getNativeFocusedWindowPeer();
/*     */     } 
/* 151 */     synchronized (getStateLock()) {
/* 152 */       if (xWindowPeer != null && xWindowPeer.modalBlocker == this.target) {
/* 153 */         return true;
/*     */       }
/*     */     } 
/* 156 */     return super.isFocusedWindowModalBlocker();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XDialogPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */