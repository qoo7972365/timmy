/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.util.logging.PlatformLogger;
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
/*     */ public final class XContentWindow
/*     */   extends XWindow
/*     */ {
/*  46 */   private static PlatformLogger insLog = PlatformLogger.getLogger("sun.awt.X11.insets.XContentWindow"); private final XDecoratedPeer parentFrame;
/*     */   
/*     */   static XContentWindow createContent(XDecoratedPeer paramXDecoratedPeer) {
/*  49 */     WindowDimensions windowDimensions = paramXDecoratedPeer.getDimensions();
/*  50 */     Rectangle rectangle = windowDimensions.getBounds();
/*     */     
/*  52 */     Insets insets = windowDimensions.getInsets();
/*  53 */     if (insets != null) {
/*  54 */       rectangle.x = -insets.left;
/*  55 */       rectangle.y = -insets.top;
/*     */     } else {
/*  57 */       rectangle.x = 0;
/*  58 */       rectangle.y = 0;
/*     */     } 
/*  60 */     XContentWindow xContentWindow = new XContentWindow(paramXDecoratedPeer, rectangle);
/*  61 */     xContentWindow.xSetVisible(true);
/*  62 */     return xContentWindow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private final List<SavedExposeEvent> iconifiedExposeEvents = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private XContentWindow(XDecoratedPeer paramXDecoratedPeer, Rectangle paramRectangle) {
/*  72 */     super((Component)paramXDecoratedPeer.getTarget(), paramXDecoratedPeer.getShell(), paramRectangle);
/*  73 */     this.parentFrame = paramXDecoratedPeer;
/*     */   }
/*     */   
/*     */   void preInit(XCreateWindowParams paramXCreateWindowParams) {
/*  77 */     super.preInit(paramXCreateWindowParams);
/*  78 */     paramXCreateWindowParams.putIfNull("bit gravity", Integer.valueOf(1));
/*  79 */     Long long_ = (Long)paramXCreateWindowParams.get("event mask");
/*  80 */     if (long_ != null) {
/*  81 */       long_ = Long.valueOf(long_.longValue() & 0xFFFFFFFFFFFDFFFFL);
/*  82 */       paramXCreateWindowParams.put((K)"event mask", (V)long_);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getWMName() {
/*  87 */     return "Content window";
/*     */   }
/*     */   protected boolean isEventDisabled(XEvent paramXEvent) {
/*  90 */     switch (paramXEvent.get_type()) {
/*     */       
/*     */       case 7:
/*     */       case 8:
/*  94 */         return false;
/*     */       
/*     */       case 22:
/*  97 */         return true;
/*     */       
/*     */       case 18:
/*     */       case 19:
/* 101 */         return true;
/*     */     } 
/* 103 */     return (super.isEventDisabled(paramXEvent) || this.parentFrame.isEventDisabled(paramXEvent));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setContentBounds(WindowDimensions paramWindowDimensions) {
/* 109 */     XToolkit.awtLock();
/*     */ 
/*     */     
/*     */     try {
/* 113 */       Rectangle rectangle = paramWindowDimensions.getBounds();
/* 114 */       Insets insets = paramWindowDimensions.getInsets();
/* 115 */       if (insets != null) {
/* 116 */         rectangle.setLocation(-insets.left, -insets.top);
/*     */       }
/* 118 */       if (insLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 119 */         insLog.fine("Setting content bounds {0}, old bounds {1}", new Object[] { rectangle, 
/* 120 */               getBounds() });
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 125 */       boolean bool = !rectangle.equals(getBounds()) ? true : false;
/* 126 */       reshape(rectangle);
/* 127 */       if (bool) {
/* 128 */         insLog.fine("Sending RESIZED");
/* 129 */         handleResize(rectangle);
/*     */       } 
/*     */     } finally {
/* 132 */       XToolkit.awtUnlock();
/*     */     } 
/* 134 */     validateSurface();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleResize(Rectangle paramRectangle) {
/* 140 */     AWTAccessor.getComponentAccessor().setSize(this.target, paramRectangle.width, paramRectangle.height);
/* 141 */     postEvent(new ComponentEvent(this.target, 101));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void postPaintEvent(Component paramComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 156 */     if (this.parentFrame instanceof XFramePeer && (((XFramePeer)this.parentFrame)
/* 157 */       .getState() & 0x1) != 0) {
/*     */ 
/*     */       
/* 160 */       this.iconifiedExposeEvents.add(new SavedExposeEvent(paramComponent, paramInt1, paramInt2, paramInt3, paramInt4));
/*     */     } else {
/*     */       
/* 163 */       super.postPaintEvent(paramComponent, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } 
/*     */   }
/*     */   
/*     */   void purgeIconifiedExposeEvents() {
/* 168 */     for (SavedExposeEvent savedExposeEvent : this.iconifiedExposeEvents) {
/* 169 */       super.postPaintEvent(savedExposeEvent.target, savedExposeEvent.x, savedExposeEvent.y, savedExposeEvent.w, savedExposeEvent.h);
/*     */     }
/* 171 */     this.iconifiedExposeEvents.clear();
/*     */   }
/*     */   
/*     */   private static class SavedExposeEvent { Component target;
/*     */     int x;
/*     */     
/*     */     SavedExposeEvent(Component param1Component, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 178 */       this.target = param1Component;
/* 179 */       this.x = param1Int1;
/* 180 */       this.y = param1Int2;
/* 181 */       this.w = param1Int3;
/* 182 */       this.h = param1Int4;
/*     */     }
/*     */     int y; int w; int h; }
/*     */   
/*     */   public String toString() {
/* 187 */     return getClass().getName() + "[" + getBounds() + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XContentWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */