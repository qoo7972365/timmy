/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.AWTKeyStroke;
/*     */ import java.awt.Toolkit;
/*     */ import java.util.logging.Logger;
/*     */ import sun.awt.EmbeddedFrame;
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
/*     */ public class XEmbeddedFrame
/*     */   extends EmbeddedFrame
/*     */ {
/*  35 */   private static final Logger log = Logger.getLogger(XEmbeddedFrame.class.getName());
/*     */   
/*     */   long handle;
/*     */ 
/*     */   
/*     */   public XEmbeddedFrame() {}
/*     */   
/*     */   public XEmbeddedFrame(long paramLong) {
/*  43 */     this(paramLong, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public XEmbeddedFrame(long paramLong, boolean paramBoolean1, boolean paramBoolean2) {
/*  48 */     super(paramLong, paramBoolean1);
/*     */     
/*  50 */     if (paramBoolean2) {
/*  51 */       XTrayIconPeer.suppressWarningString(this);
/*     */     }
/*     */     
/*  54 */     this.handle = paramLong;
/*  55 */     if (paramLong != 0L) {
/*  56 */       addNotify();
/*  57 */       if (!paramBoolean2) {
/*  58 */         show();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addNotify() {
/*  65 */     if (getPeer() == null) {
/*  66 */       XToolkit xToolkit = (XToolkit)Toolkit.getDefaultToolkit();
/*  67 */       setPeer(xToolkit.createEmbeddedFrame(this));
/*     */     } 
/*  69 */     super.addNotify();
/*     */   }
/*     */   
/*     */   public XEmbeddedFrame(long paramLong, boolean paramBoolean) {
/*  73 */     this(paramLong, paramBoolean, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean traverseIn(boolean paramBoolean) {
/*  80 */     XEmbeddedFramePeer xEmbeddedFramePeer = (XEmbeddedFramePeer)getPeer();
/*  81 */     if (xEmbeddedFramePeer != null) {
/*  82 */       if (xEmbeddedFramePeer.supportsXEmbed() && xEmbeddedFramePeer.isXEmbedActive()) {
/*  83 */         log.fine("The method shouldn't be called when XEmbed is active!");
/*     */       } else {
/*  85 */         return super.traverseIn(paramBoolean);
/*     */       } 
/*     */     }
/*  88 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean traverseOut(boolean paramBoolean) {
/*  92 */     XEmbeddedFramePeer xEmbeddedFramePeer = (XEmbeddedFramePeer)getPeer();
/*  93 */     if (paramBoolean == true) {
/*  94 */       xEmbeddedFramePeer.traverseOutForward();
/*     */     } else {
/*     */       
/*  97 */       xEmbeddedFramePeer.traverseOutBackward();
/*     */     } 
/*  99 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void synthesizeWindowActivation(boolean paramBoolean) {
/* 106 */     XEmbeddedFramePeer xEmbeddedFramePeer = (XEmbeddedFramePeer)getPeer();
/* 107 */     if (xEmbeddedFramePeer != null) {
/* 108 */       if (xEmbeddedFramePeer.supportsXEmbed() && xEmbeddedFramePeer.isXEmbedActive()) {
/* 109 */         log.fine("The method shouldn't be called when XEmbed is active!");
/*     */       } else {
/* 111 */         xEmbeddedFramePeer.synthesizeFocusInOut(paramBoolean);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void registerAccelerator(AWTKeyStroke paramAWTKeyStroke) {
/* 117 */     XEmbeddedFramePeer xEmbeddedFramePeer = (XEmbeddedFramePeer)getPeer();
/* 118 */     if (xEmbeddedFramePeer != null)
/* 119 */       xEmbeddedFramePeer.registerAccelerator(paramAWTKeyStroke); 
/*     */   }
/*     */   
/*     */   public void unregisterAccelerator(AWTKeyStroke paramAWTKeyStroke) {
/* 123 */     XEmbeddedFramePeer xEmbeddedFramePeer = (XEmbeddedFramePeer)getPeer();
/* 124 */     if (xEmbeddedFramePeer != null)
/* 125 */       xEmbeddedFramePeer.unregisterAccelerator(paramAWTKeyStroke); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XEmbeddedFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */