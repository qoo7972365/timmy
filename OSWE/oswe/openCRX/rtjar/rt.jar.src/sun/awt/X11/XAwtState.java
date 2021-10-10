/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.lang.ref.WeakReference;
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
/*     */ class XAwtState
/*     */ {
/*  43 */   private static WeakReference componentMouseEnteredRef = null;
/*     */   
/*     */   static void setComponentMouseEntered(Component paramComponent) {
/*  46 */     XToolkit.awtLock();
/*     */     try {
/*  48 */       if (paramComponent == null) {
/*  49 */         componentMouseEnteredRef = null;
/*     */         return;
/*     */       } 
/*  52 */       if (paramComponent != getComponentMouseEntered()) {
/*  53 */         componentMouseEnteredRef = new WeakReference<>(paramComponent);
/*     */       }
/*     */     } finally {
/*  56 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   static Component getComponentMouseEntered() {
/*  61 */     XToolkit.awtLock();
/*     */     try {
/*  63 */       if (componentMouseEnteredRef == null) {
/*  64 */         return null;
/*     */       }
/*  66 */       return componentMouseEnteredRef.get();
/*     */     } finally {
/*  68 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean inManualGrab = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isManualGrab() {
/*  83 */     return inManualGrab;
/*     */   }
/*     */   
/*  86 */   private static WeakReference grabWindowRef = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void setGrabWindow(XBaseWindow paramXBaseWindow) {
/*  93 */     setGrabWindow(paramXBaseWindow, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void setAutoGrabWindow(XBaseWindow paramXBaseWindow) {
/* 100 */     setGrabWindow(paramXBaseWindow, true);
/*     */   }
/*     */   
/*     */   private static void setGrabWindow(XBaseWindow paramXBaseWindow, boolean paramBoolean) {
/* 104 */     XToolkit.awtLock();
/*     */     try {
/* 106 */       if (inManualGrab && paramBoolean) {
/*     */         return;
/*     */       }
/* 109 */       inManualGrab = (paramXBaseWindow != null && !paramBoolean);
/* 110 */       if (paramXBaseWindow == null) {
/* 111 */         grabWindowRef = null;
/*     */         return;
/*     */       } 
/* 114 */       if (paramXBaseWindow != getGrabWindow()) {
/* 115 */         grabWindowRef = new WeakReference<>(paramXBaseWindow);
/*     */       }
/*     */     } finally {
/* 118 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   static XBaseWindow getGrabWindow() {
/* 123 */     XToolkit.awtLock();
/*     */     try {
/* 125 */       if (grabWindowRef == null) {
/* 126 */         return null;
/*     */       }
/* 128 */       XBaseWindow xBaseWindow = grabWindowRef.get();
/* 129 */       if (xBaseWindow != null && xBaseWindow.isDisposed()) {
/* 130 */         xBaseWindow = null;
/* 131 */         grabWindowRef = null;
/* 132 */       } else if (xBaseWindow == null) {
/* 133 */         grabWindowRef = null;
/*     */       } 
/* 135 */       return xBaseWindow;
/*     */     } finally {
/* 137 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XAwtState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */