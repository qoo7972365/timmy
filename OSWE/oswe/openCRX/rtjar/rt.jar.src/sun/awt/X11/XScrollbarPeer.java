/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Scrollbar;
/*     */ import java.awt.event.AdjustmentEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.peer.ScrollbarPeer;
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
/*     */ class XScrollbarPeer
/*     */   extends XComponentPeer
/*     */   implements ScrollbarPeer, XScrollbarClient
/*     */ {
/*  34 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XScrollbarPeer");
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_LENGTH = 50;
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_WIDTH_SOLARIS = 19;
/*     */ 
/*     */   
/*  43 */   private static final int DEFAULT_WIDTH_LINUX = XToolkit.getUIDefaults().getInt("ScrollBar.defaultWidth");
/*     */   XScrollbar tsb;
/*     */   
/*     */   public void preInit(XCreateWindowParams paramXCreateWindowParams) {
/*  47 */     super.preInit(paramXCreateWindowParams);
/*  48 */     Scrollbar scrollbar = (Scrollbar)this.target;
/*  49 */     if (scrollbar.getOrientation() == 1) {
/*  50 */       this.tsb = new XVerticalScrollbar(this);
/*     */     } else {
/*  52 */       this.tsb = new XHorizontalScrollbar(this);
/*     */     } 
/*  54 */     int i = scrollbar.getMinimum();
/*  55 */     int j = scrollbar.getMaximum();
/*  56 */     int k = scrollbar.getVisibleAmount();
/*  57 */     int m = scrollbar.getValue();
/*  58 */     int n = scrollbar.getLineIncrement();
/*  59 */     int i1 = scrollbar.getPageIncrement();
/*  60 */     this.tsb.setValues(m, k, i, j, n, i1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XScrollbarPeer(Scrollbar paramScrollbar) {
/*  67 */     super(paramScrollbar);
/*  68 */     this.target = paramScrollbar;
/*  69 */     xSetVisible(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getDefaultDimension() {
/*  77 */     if (System.getProperty("os.name").equals("Linux")) {
/*  78 */       return DEFAULT_WIDTH_LINUX;
/*     */     }
/*  80 */     return 19;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize() {
/*  88 */     Scrollbar scrollbar = (Scrollbar)this.target;
/*  89 */     return (scrollbar.getOrientation() == 1) ? new Dimension(
/*  90 */         getDefaultDimension(), 50) : new Dimension(50, 
/*  91 */         getDefaultDimension());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void paintPeer(Graphics paramGraphics) {
/*  98 */     Color[] arrayOfColor = getGUIcolors();
/*  99 */     paramGraphics.setColor(arrayOfColor[0]);
/* 100 */     this.tsb.paint(paramGraphics, arrayOfColor, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void repaintScrollbarRequest(XScrollbar paramXScrollbar) {
/* 105 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyValue(XScrollbar paramXScrollbar, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 112 */     Scrollbar scrollbar = (Scrollbar)this.target;
/* 113 */     scrollbar.setValue(paramInt2);
/* 114 */     postEvent(new AdjustmentEvent(scrollbar, 601, paramInt1, paramInt2, paramBoolean));
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
/*     */   public void handleJavaMouseEvent(MouseEvent paramMouseEvent) {
/* 129 */     super.handleJavaMouseEvent(paramMouseEvent);
/*     */     
/* 131 */     int i = paramMouseEvent.getX();
/* 132 */     int j = paramMouseEvent.getY();
/* 133 */     int k = paramMouseEvent.getModifiers();
/* 134 */     int m = paramMouseEvent.getID();
/*     */ 
/*     */     
/* 137 */     if ((paramMouseEvent.getModifiers() & 0x10) == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 141 */     switch (paramMouseEvent.getID()) {
/*     */       case 501:
/* 143 */         this.target.requestFocus();
/* 144 */         this.tsb.handleMouseEvent(m, k, i, j);
/*     */         break;
/*     */       
/*     */       case 502:
/* 148 */         this.tsb.handleMouseEvent(m, k, i, j);
/*     */         break;
/*     */       
/*     */       case 506:
/* 152 */         this.tsb.handleMouseEvent(m, k, i, j);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleJavaKeyEvent(KeyEvent paramKeyEvent) {
/* 158 */     super.handleJavaKeyEvent(paramKeyEvent);
/* 159 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 160 */       log.finer("KeyEvent on scrollbar: " + paramKeyEvent);
/*     */     }
/* 162 */     if (!paramKeyEvent.isConsumed() && paramKeyEvent.getID() == 402) {
/* 163 */       switch (paramKeyEvent.getKeyCode()) {
/*     */         case 38:
/* 165 */           log.finer("Scrolling up");
/* 166 */           this.tsb.notifyValue(this.tsb.getValue() - this.tsb.getUnitIncrement());
/*     */           break;
/*     */         case 40:
/* 169 */           log.finer("Scrolling down");
/* 170 */           this.tsb.notifyValue(this.tsb.getValue() + this.tsb.getUnitIncrement());
/*     */           break;
/*     */         case 37:
/* 173 */           log.finer("Scrolling up");
/* 174 */           this.tsb.notifyValue(this.tsb.getValue() - this.tsb.getUnitIncrement());
/*     */           break;
/*     */         case 39:
/* 177 */           log.finer("Scrolling down");
/* 178 */           this.tsb.notifyValue(this.tsb.getValue() + this.tsb.getUnitIncrement());
/*     */           break;
/*     */         case 33:
/* 181 */           log.finer("Scrolling page up");
/* 182 */           this.tsb.notifyValue(this.tsb.getValue() - this.tsb.getBlockIncrement());
/*     */           break;
/*     */         case 34:
/* 185 */           log.finer("Scrolling page down");
/* 186 */           this.tsb.notifyValue(this.tsb.getValue() + this.tsb.getBlockIncrement());
/*     */           break;
/*     */         case 36:
/* 189 */           log.finer("Scrolling to home");
/* 190 */           this.tsb.notifyValue(0);
/*     */           break;
/*     */         case 35:
/* 193 */           log.finer("Scrolling to end");
/* 194 */           this.tsb.notifyValue(this.tsb.getMaximum());
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void setValue(int paramInt) {
/* 201 */     this.tsb.setValue(paramInt);
/* 202 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValues(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 207 */     this.tsb.setValues(paramInt1, paramInt2, paramInt3, paramInt4);
/* 208 */     repaint();
/*     */   }
/*     */   
/*     */   public void setLineIncrement(int paramInt) {
/* 212 */     this.tsb.setUnitIncrement(paramInt);
/*     */   }
/*     */   
/*     */   public void setPageIncrement(int paramInt) {
/* 216 */     this.tsb.setBlockIncrement(paramInt);
/*     */   }
/*     */   
/*     */   public void layout() {
/* 220 */     super.layout();
/* 221 */     this.tsb.setSize(this.width, this.height);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XScrollbarPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */