/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Point;
/*     */ import java.awt.Window;
/*     */ import java.awt.peer.MouseInfoPeer;
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
/*     */ public class XMouseInfoPeer
/*     */   implements MouseInfoPeer
/*     */ {
/*     */   public int fillPointWithCoords(Point paramPoint) {
/*  43 */     long l = XToolkit.getDisplay();
/*     */     
/*  45 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  46 */     GraphicsDevice[] arrayOfGraphicsDevice = graphicsEnvironment.getScreenDevices();
/*  47 */     int i = arrayOfGraphicsDevice.length;
/*     */     
/*  49 */     XToolkit.awtLock();
/*     */     try {
/*  51 */       for (byte b = 0; b < i; b++) {
/*  52 */         long l1 = XlibWrapper.RootWindow(l, b);
/*  53 */         boolean bool = XlibWrapper.XQueryPointer(l, l1, XlibWrapper.larg1, XlibWrapper.larg2, XlibWrapper.larg3, XlibWrapper.larg4, XlibWrapper.larg5, XlibWrapper.larg6, XlibWrapper.larg7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  62 */         if (bool) {
/*  63 */           paramPoint.x = Native.getInt(XlibWrapper.larg3);
/*  64 */           paramPoint.y = Native.getInt(XlibWrapper.larg4);
/*  65 */           return b;
/*     */         } 
/*     */       } 
/*     */     } finally {
/*  69 */       XToolkit.awtUnlock();
/*     */     } 
/*     */ 
/*     */     
/*  73 */     assert false : "No pointer found in the system.";
/*  74 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isWindowUnderMouse(Window paramWindow) {
/*  78 */     if (paramWindow == null) {
/*  79 */       return false;
/*     */     }
/*  81 */     XWindow xWindow = (XWindow)paramWindow.getPeer();
/*  82 */     if (xWindow == null) {
/*  83 */       return false;
/*     */     }
/*  85 */     long l1 = XToolkit.getDisplay();
/*  86 */     long l2 = xWindow.getContentWindow();
/*  87 */     long l3 = XlibUtil.getParentWindow(l2);
/*     */     
/*  89 */     XToolkit.awtLock();
/*     */     
/*     */     try {
/*  92 */       boolean bool = XlibWrapper.XQueryPointer(l1, l3, XlibWrapper.larg1, XlibWrapper.larg8, XlibWrapper.larg3, XlibWrapper.larg4, XlibWrapper.larg5, XlibWrapper.larg6, XlibWrapper.larg7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 100 */       long l = Native.getWindow(XlibWrapper.larg8);
/* 101 */       return (l == l2 && bool);
/*     */     }
/*     */     finally {
/*     */       
/* 105 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XMouseInfoPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */