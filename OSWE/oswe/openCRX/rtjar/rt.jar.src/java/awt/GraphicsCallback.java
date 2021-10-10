/*     */ package java.awt;
/*     */ 
/*     */ import sun.awt.SunGraphicsCallback;
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
/*     */ abstract class GraphicsCallback
/*     */   extends SunGraphicsCallback
/*     */ {
/*     */   static final class PaintCallback
/*     */     extends GraphicsCallback
/*     */   {
/*  35 */     private static PaintCallback instance = new PaintCallback();
/*     */ 
/*     */     
/*     */     public void run(Component param1Component, Graphics param1Graphics) {
/*  39 */       param1Component.paint(param1Graphics);
/*     */     }
/*     */     static PaintCallback getInstance() {
/*  42 */       return instance;
/*     */     } }
/*     */   
/*     */   static final class PrintCallback extends GraphicsCallback {
/*  46 */     private static PrintCallback instance = new PrintCallback();
/*     */ 
/*     */     
/*     */     public void run(Component param1Component, Graphics param1Graphics) {
/*  50 */       param1Component.print(param1Graphics);
/*     */     }
/*     */     static PrintCallback getInstance() {
/*  53 */       return instance;
/*     */     }
/*     */   }
/*     */   
/*  57 */   static final class PaintAllCallback extends GraphicsCallback { private static PaintAllCallback instance = new PaintAllCallback();
/*     */ 
/*     */     
/*     */     public void run(Component param1Component, Graphics param1Graphics) {
/*  61 */       param1Component.paintAll(param1Graphics);
/*     */     }
/*     */     static PaintAllCallback getInstance() {
/*  64 */       return instance;
/*     */     } }
/*     */   
/*     */   static final class PrintAllCallback extends GraphicsCallback {
/*  68 */     private static PrintAllCallback instance = new PrintAllCallback();
/*     */ 
/*     */     
/*     */     public void run(Component param1Component, Graphics param1Graphics) {
/*  72 */       param1Component.printAll(param1Graphics);
/*     */     }
/*     */     static PrintAllCallback getInstance() {
/*  75 */       return instance;
/*     */     }
/*     */   }
/*     */   
/*  79 */   static final class PeerPaintCallback extends GraphicsCallback { private static PeerPaintCallback instance = new PeerPaintCallback();
/*     */ 
/*     */     
/*     */     public void run(Component param1Component, Graphics param1Graphics) {
/*  83 */       param1Component.validate();
/*  84 */       if (param1Component.peer instanceof java.awt.peer.LightweightPeer) {
/*  85 */         param1Component.lightweightPaint(param1Graphics);
/*     */       } else {
/*  87 */         param1Component.peer.paint(param1Graphics);
/*     */       } 
/*     */     }
/*     */     static PeerPaintCallback getInstance() {
/*  91 */       return instance;
/*     */     } }
/*     */   
/*     */   static final class PeerPrintCallback extends GraphicsCallback {
/*  95 */     private static PeerPrintCallback instance = new PeerPrintCallback();
/*     */ 
/*     */     
/*     */     public void run(Component param1Component, Graphics param1Graphics) {
/*  99 */       param1Component.validate();
/* 100 */       if (param1Component.peer instanceof java.awt.peer.LightweightPeer) {
/* 101 */         param1Component.lightweightPrint(param1Graphics);
/*     */       } else {
/* 103 */         param1Component.peer.print(param1Graphics);
/*     */       } 
/*     */     }
/*     */     static PeerPrintCallback getInstance() {
/* 107 */       return instance;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class PaintHeavyweightComponentsCallback
/*     */     extends GraphicsCallback {
/* 113 */     private static PaintHeavyweightComponentsCallback instance = new PaintHeavyweightComponentsCallback();
/*     */ 
/*     */ 
/*     */     
/*     */     public void run(Component param1Component, Graphics param1Graphics) {
/* 118 */       if (param1Component.peer instanceof java.awt.peer.LightweightPeer) {
/* 119 */         param1Component.paintHeavyweightComponents(param1Graphics);
/*     */       } else {
/* 121 */         param1Component.paintAll(param1Graphics);
/*     */       } 
/*     */     }
/*     */     static PaintHeavyweightComponentsCallback getInstance() {
/* 125 */       return instance;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class PrintHeavyweightComponentsCallback
/*     */     extends GraphicsCallback {
/* 131 */     private static PrintHeavyweightComponentsCallback instance = new PrintHeavyweightComponentsCallback();
/*     */ 
/*     */ 
/*     */     
/*     */     public void run(Component param1Component, Graphics param1Graphics) {
/* 136 */       if (param1Component.peer instanceof java.awt.peer.LightweightPeer) {
/* 137 */         param1Component.printHeavyweightComponents(param1Graphics);
/*     */       } else {
/* 139 */         param1Component.printAll(param1Graphics);
/*     */       } 
/*     */     }
/*     */     static PrintHeavyweightComponentsCallback getInstance() {
/* 143 */       return instance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/GraphicsCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */