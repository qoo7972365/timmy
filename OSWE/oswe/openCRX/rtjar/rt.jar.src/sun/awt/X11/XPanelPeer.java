/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Insets;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.awt.peer.PanelPeer;
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
/*     */ public class XPanelPeer
/*     */   extends XCanvasPeer
/*     */   implements PanelPeer
/*     */ {
/*  34 */   XEmbeddingContainer embedder = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public void xembed(long paramLong) {
/*  39 */     if (this.embedder != null) {
/*  40 */       this.embedder.add(paramLong);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   XPanelPeer(XCreateWindowParams paramXCreateWindowParams) {
/*  46 */     super(paramXCreateWindowParams);
/*     */   }
/*     */   
/*     */   XPanelPeer(Component paramComponent) {
/*  50 */     super(paramComponent);
/*     */   }
/*     */   
/*     */   void postInit(XCreateWindowParams paramXCreateWindowParams) {
/*  54 */     super.postInit(paramXCreateWindowParams);
/*  55 */     if (this.embedder != null) {
/*  56 */       this.embedder.install(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public Insets getInsets() {
/*  61 */     return new Insets(0, 0, 0, 0);
/*     */   }
/*     */   public void paint(Graphics paramGraphics) {
/*  64 */     super.paint(paramGraphics);
/*  65 */     SunGraphicsCallback.PaintHeavyweightComponentsCallback.getInstance()
/*  66 */       .runComponents(((Container)this.target).getComponents(), paramGraphics, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void print(Graphics paramGraphics) {
/*  71 */     super.print(paramGraphics);
/*  72 */     SunGraphicsCallback.PrintHeavyweightComponentsCallback.getInstance()
/*  73 */       .runComponents(((Container)this.target).getComponents(), paramGraphics, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackground(Color paramColor) {
/*  83 */     Container container = (Container)this.target;
/*  84 */     synchronized (this.target.getTreeLock()) {
/*  85 */       int i = container.getComponentCount();
/*  86 */       for (byte b = 0; b < i; b++) {
/*  87 */         Component component = container.getComponent(b);
/*  88 */         ComponentPeer componentPeer = component.getPeer();
/*  89 */         if (componentPeer != null) {
/*  90 */           Color color = component.getBackground();
/*  91 */           if (color == null || color.equals(paramColor)) {
/*  92 */             componentPeer.setBackground(paramColor);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*  97 */     super.setBackground(paramColor);
/*     */   }
/*     */   
/*     */   public void setForeground(Color paramColor) {
/* 101 */     setForegroundForHierarchy((Container)this.target, paramColor);
/*     */   }
/*     */   
/*     */   private void setForegroundForHierarchy(Container paramContainer, Color paramColor) {
/* 105 */     synchronized (this.target.getTreeLock()) {
/* 106 */       int i = paramContainer.getComponentCount();
/* 107 */       for (byte b = 0; b < i; b++) {
/* 108 */         Component component = paramContainer.getComponent(b);
/* 109 */         Color color = component.getForeground();
/* 110 */         if (color == null || color.equals(paramColor)) {
/* 111 */           ComponentPeer componentPeer = component.getPeer();
/* 112 */           if (componentPeer != null) {
/* 113 */             componentPeer.setForeground(paramColor);
/*     */           }
/* 115 */           if (componentPeer instanceof java.awt.peer.LightweightPeer && component instanceof Container)
/*     */           {
/*     */             
/* 118 */             setForegroundForHierarchy((Container)component, paramColor);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Insets insets() {
/* 129 */     return getInsets();
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 133 */     if (this.embedder != null) {
/* 134 */       this.embedder.deinstall();
/*     */     }
/* 136 */     super.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean shouldFocusOnClick() {
/* 142 */     return (((Container)this.target).getComponentCount() == 0);
/*     */   }
/*     */   
/*     */   XPanelPeer() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XPanelPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */