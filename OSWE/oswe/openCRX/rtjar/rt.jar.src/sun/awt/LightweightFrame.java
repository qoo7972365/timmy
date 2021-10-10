/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.MenuBar;
/*     */ import java.awt.MenuComponent;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.dnd.DragGestureEvent;
/*     */ import java.awt.dnd.DragGestureListener;
/*     */ import java.awt.dnd.DragSource;
/*     */ import java.awt.dnd.DropTarget;
/*     */ import java.awt.dnd.InvalidDnDOperationException;
/*     */ import java.awt.dnd.peer.DragSourceContextPeer;
/*     */ import java.awt.peer.FramePeer;
/*     */ import java.util.List;
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
/*     */ public abstract class LightweightFrame
/*     */   extends Frame
/*     */ {
/*     */   private int hostX;
/*     */   private int hostY;
/*     */   private int hostW;
/*     */   private int hostH;
/*     */   
/*     */   public LightweightFrame() {
/*  63 */     setUndecorated(true);
/*  64 */     setResizable(true);
/*  65 */     setEnabled(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Container getParent() {
/*  73 */     return null;
/*     */   } public Graphics getGraphics() {
/*  75 */     return null;
/*     */   } public final boolean isResizable() {
/*  77 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setTitle(String paramString) {}
/*     */ 
/*     */   
/*     */   public final void setIconImage(Image paramImage) {}
/*     */ 
/*     */   
/*     */   public final void setIconImages(List<? extends Image> paramList) {}
/*     */   
/*     */   public final void setMenuBar(MenuBar paramMenuBar) {}
/*     */   
/*     */   public void addNotify() {
/*  92 */     synchronized (getTreeLock()) {
/*  93 */       if (getPeer() == null) {
/*  94 */         SunToolkit sunToolkit = (SunToolkit)Toolkit.getDefaultToolkit();
/*     */         try {
/*  96 */           setPeer(sunToolkit.createLightweightFrame(this));
/*  97 */         } catch (Exception exception) {
/*  98 */           throw new RuntimeException(exception);
/*     */         } 
/*     */       } 
/* 101 */       super.addNotify();
/*     */     } 
/*     */   } public final void setResizable(boolean paramBoolean) {} public final void remove(MenuComponent paramMenuComponent) {} public final void toFront() {}
/*     */   public final void toBack() {}
/*     */   private void setPeer(FramePeer paramFramePeer) {
/* 106 */     AWTAccessor.getComponentAccessor().setPeer(this, paramFramePeer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void emulateActivation(boolean paramBoolean) {
/* 118 */     ((FramePeer)getPeer()).emulateActivation(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void grabFocus();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void ungrabFocus();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getScaleFactor();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void notifyDisplayChanged(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getHostBounds() {
/* 163 */     if (this.hostX == 0 && this.hostY == 0 && this.hostW == 0 && this.hostH == 0)
/*     */     {
/*     */       
/* 166 */       return getBounds();
/*     */     }
/* 168 */     return new Rectangle(this.hostX, this.hostY, this.hostW, this.hostH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHostBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 175 */     this.hostX = paramInt1;
/* 176 */     this.hostY = paramInt2;
/* 177 */     this.hostW = paramInt3;
/* 178 */     this.hostH = paramInt4;
/*     */   }
/*     */   
/*     */   public abstract <T extends java.awt.dnd.DragGestureRecognizer> T createDragGestureRecognizer(Class<T> paramClass, DragSource paramDragSource, Component paramComponent, int paramInt, DragGestureListener paramDragGestureListener);
/*     */   
/*     */   public abstract DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent paramDragGestureEvent) throws InvalidDnDOperationException;
/*     */   
/*     */   public abstract void addDropTarget(DropTarget paramDropTarget);
/*     */   
/*     */   public abstract void removeDropTarget(DropTarget paramDropTarget);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/LightweightFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */