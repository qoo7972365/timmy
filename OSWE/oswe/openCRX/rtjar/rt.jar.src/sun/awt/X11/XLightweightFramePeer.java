/*    */ package sun.awt.X11;
/*    */ 
/*    */ import java.awt.Dialog;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Insets;
/*    */ import java.awt.MenuBar;
/*    */ import java.awt.Point;
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.Window;
/*    */ import java.awt.dnd.DropTarget;
/*    */ import java.util.List;
/*    */ import java.util.Vector;
/*    */ import sun.awt.IconInfo;
/*    */ import sun.awt.LightweightFrame;
/*    */ import sun.awt.OverrideNativeWindowHandle;
/*    */ import sun.swing.JLightweightFrame;
/*    */ import sun.swing.SwingAccessor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XLightweightFramePeer
/*    */   extends XFramePeer
/*    */   implements OverrideNativeWindowHandle
/*    */ {
/*    */   private volatile long overriddenWindowHandle;
/*    */   
/*    */   XLightweightFramePeer(LightweightFrame paramLightweightFrame) {
/* 39 */     super(paramLightweightFrame);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 85 */     this.overriddenWindowHandle = 0L;
/*    */   }
/*    */   private LightweightFrame getLwTarget() { return (LightweightFrame)this.target; }
/*    */   public Graphics getGraphics() { return getLwTarget().getGraphics(); }
/* 89 */   public void xSetVisible(boolean paramBoolean) { this.visible = paramBoolean; } protected void requestXFocus(long paramLong, boolean paramBoolean) {} public void overrideWindowHandle(long paramLong) { this.overriddenWindowHandle = paramLong; }
/*    */   public void setGrab(boolean paramBoolean) { if (paramBoolean) { getLwTarget().grabFocus(); } else { getLwTarget().ungrabFocus(); }  }
/*    */   public void updateCursorImmediately() { SwingAccessor.getJLightweightFrameAccessor().updateCursor((JLightweightFrame)getLwTarget()); }
/*    */   public void addDropTarget(DropTarget paramDropTarget) { getLwTarget().addDropTarget(paramDropTarget); }
/* 93 */   public void removeDropTarget(DropTarget paramDropTarget) { getLwTarget().removeDropTarget(paramDropTarget); } public long getOverriddenWindowHandle() { return this.overriddenWindowHandle; }
/*    */ 
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XLightweightFramePeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */