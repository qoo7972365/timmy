/*     */ package sun.awt.im;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.InputMethodEvent;
/*     */ import java.awt.event.InputMethodListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.TextHitInfo;
/*     */ import java.awt.font.TextLayout;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.im.InputMethodRequests;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.border.LineBorder;
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
/*     */ public final class CompositionArea
/*     */   extends JPanel
/*     */   implements InputMethodListener
/*     */ {
/*     */   private CompositionAreaHandler handler;
/*     */   private TextLayout composedTextLayout;
/*  65 */   private TextHitInfo caret = null;
/*     */   private JFrame compositionWindow;
/*     */   private static final int TEXT_ORIGIN_X = 5;
/*     */   private static final int TEXT_ORIGIN_Y = 15;
/*     */   private static final int PASSIVE_WIDTH = 480;
/*     */   private static final int WIDTH_MARGIN = 10;
/*     */   private static final int HEIGHT_MARGIN = 3;
/*     */   private static final long serialVersionUID = -1057247068746557444L;
/*     */   
/*     */   CompositionArea() {
/*  75 */     String str = Toolkit.getProperty("AWT.CompositionWindowTitle", "Input Window");
/*  76 */     this
/*  77 */       .compositionWindow = (JFrame)InputMethodContext.createInputMethodWindow(str, null, true);
/*     */     
/*  79 */     setOpaque(true);
/*  80 */     setBorder(LineBorder.createGrayLineBorder());
/*  81 */     setForeground(Color.black);
/*  82 */     setBackground(Color.white);
/*     */ 
/*     */ 
/*     */     
/*  86 */     enableInputMethods(true);
/*  87 */     enableEvents(8L);
/*     */     
/*  89 */     this.compositionWindow.getContentPane().add(this);
/*  90 */     this.compositionWindow.addWindowListener(new FrameWindowAdapter());
/*  91 */     addInputMethodListener(this);
/*  92 */     this.compositionWindow.enableInputMethods(false);
/*  93 */     this.compositionWindow.pack();
/*  94 */     Dimension dimension1 = this.compositionWindow.getSize();
/*  95 */     Dimension dimension2 = getToolkit().getScreenSize();
/*  96 */     this.compositionWindow.setLocation(dimension2.width - dimension1.width - 20, dimension2.height - dimension1.height - 100);
/*     */     
/*  98 */     this.compositionWindow.setVisible(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void setHandlerInfo(CompositionAreaHandler paramCompositionAreaHandler, InputContext paramInputContext) {
/* 106 */     this.handler = paramCompositionAreaHandler;
/* 107 */     ((InputMethodWindow)this.compositionWindow).setInputContext(paramInputContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputMethodRequests getInputMethodRequests() {
/* 114 */     return this.handler;
/*     */   }
/*     */ 
/*     */   
/*     */   private Rectangle getCaretRectangle(TextHitInfo paramTextHitInfo) {
/* 119 */     int i = 0;
/* 120 */     TextLayout textLayout = this.composedTextLayout;
/* 121 */     if (textLayout != null) {
/* 122 */       i = Math.round(textLayout.getCaretInfo(paramTextHitInfo)[0]);
/*     */     }
/* 124 */     Graphics graphics = getGraphics();
/* 125 */     FontMetrics fontMetrics = null;
/*     */     try {
/* 127 */       fontMetrics = graphics.getFontMetrics();
/*     */     } finally {
/* 129 */       graphics.dispose();
/*     */     } 
/* 131 */     return new Rectangle(5 + i, 15 - fontMetrics
/* 132 */         .getAscent(), 0, fontMetrics
/* 133 */         .getAscent() + fontMetrics.getDescent());
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/* 137 */     super.paint(paramGraphics);
/* 138 */     paramGraphics.setColor(getForeground());
/* 139 */     TextLayout textLayout = this.composedTextLayout;
/* 140 */     if (textLayout != null) {
/* 141 */       textLayout.draw((Graphics2D)paramGraphics, 5.0F, 15.0F);
/*     */     }
/* 143 */     if (this.caret != null) {
/* 144 */       Rectangle rectangle = getCaretRectangle(this.caret);
/* 145 */       paramGraphics.setXORMode(getBackground());
/* 146 */       paramGraphics.fillRect(rectangle.x, rectangle.y, 1, rectangle.height);
/* 147 */       paramGraphics.setPaintMode();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void setCompositionAreaVisible(boolean paramBoolean) {
/* 153 */     this.compositionWindow.setVisible(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isCompositionAreaVisible() {
/* 158 */     return this.compositionWindow.isVisible();
/*     */   }
/*     */   
/*     */   class FrameWindowAdapter
/*     */     extends WindowAdapter {
/*     */     public void windowActivated(WindowEvent param1WindowEvent) {
/* 164 */       CompositionArea.this.requestFocus();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void inputMethodTextChanged(InputMethodEvent paramInputMethodEvent) {
/* 170 */     this.handler.inputMethodTextChanged(paramInputMethodEvent);
/*     */   }
/*     */   
/*     */   public void caretPositionChanged(InputMethodEvent paramInputMethodEvent) {
/* 174 */     this.handler.caretPositionChanged(paramInputMethodEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setText(AttributedCharacterIterator paramAttributedCharacterIterator, TextHitInfo paramTextHitInfo) {
/* 182 */     this.composedTextLayout = null;
/* 183 */     if (paramAttributedCharacterIterator == null) {
/*     */       
/* 185 */       this.compositionWindow.setVisible(false);
/* 186 */       this.caret = null;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 191 */       if (!this.compositionWindow.isVisible()) {
/* 192 */         this.compositionWindow.setVisible(true);
/*     */       }
/*     */       
/* 195 */       Graphics graphics = getGraphics();
/*     */       
/* 197 */       if (graphics == null) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 202 */         updateWindowLocation();
/*     */         
/* 204 */         FontRenderContext fontRenderContext = ((Graphics2D)graphics).getFontRenderContext();
/* 205 */         this.composedTextLayout = new TextLayout(paramAttributedCharacterIterator, fontRenderContext);
/* 206 */         Rectangle2D rectangle2D1 = this.composedTextLayout.getBounds();
/*     */         
/* 208 */         this.caret = paramTextHitInfo;
/*     */ 
/*     */         
/* 211 */         FontMetrics fontMetrics = graphics.getFontMetrics();
/* 212 */         Rectangle2D rectangle2D2 = fontMetrics.getMaxCharBounds(graphics);
/* 213 */         int i = (int)rectangle2D2.getHeight() + 3;
/*     */         
/* 215 */         int j = i + (this.compositionWindow.getInsets()).top + (this.compositionWindow.getInsets()).bottom;
/*     */         
/* 217 */         InputMethodRequests inputMethodRequests = this.handler.getClientInputMethodRequests();
/* 218 */         byte b = (inputMethodRequests == null) ? 480 : ((int)rectangle2D1.getWidth() + 10);
/*     */         
/* 220 */         int k = b + (this.compositionWindow.getInsets()).left + (this.compositionWindow.getInsets()).right;
/* 221 */         setPreferredSize(new Dimension(b, i));
/* 222 */         this.compositionWindow.setSize(new Dimension(k, j));
/*     */ 
/*     */         
/* 225 */         paint(graphics);
/*     */       } finally {
/*     */         
/* 228 */         graphics.dispose();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setCaret(TextHitInfo paramTextHitInfo) {
/* 238 */     this.caret = paramTextHitInfo;
/* 239 */     if (this.compositionWindow.isVisible()) {
/* 240 */       Graphics graphics = getGraphics();
/*     */       try {
/* 242 */         paint(graphics);
/*     */       } finally {
/* 244 */         graphics.dispose();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateWindowLocation() {
/* 255 */     InputMethodRequests inputMethodRequests = this.handler.getClientInputMethodRequests();
/* 256 */     if (inputMethodRequests == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 261 */     Point point = new Point();
/*     */     
/* 263 */     Rectangle rectangle = inputMethodRequests.getTextLocation(null);
/* 264 */     Dimension dimension1 = Toolkit.getDefaultToolkit().getScreenSize();
/* 265 */     Dimension dimension2 = this.compositionWindow.getSize();
/*     */ 
/*     */     
/* 268 */     if (rectangle.x + dimension2.width > dimension1.width) {
/* 269 */       point.x = dimension1.width - dimension2.width;
/*     */     } else {
/* 271 */       point.x = rectangle.x;
/*     */     } 
/*     */     
/* 274 */     if (rectangle.y + rectangle.height + 2 + dimension2.height > dimension1.height) {
/* 275 */       point.y = rectangle.y - 2 - dimension2.height;
/*     */     } else {
/* 277 */       point.y = rectangle.y + rectangle.height + 2;
/*     */     } 
/*     */     
/* 280 */     this.compositionWindow.setLocation(point);
/*     */   }
/*     */ 
/*     */   
/*     */   Rectangle getTextLocation(TextHitInfo paramTextHitInfo) {
/* 285 */     Rectangle rectangle = getCaretRectangle(paramTextHitInfo);
/* 286 */     Point point = getLocationOnScreen();
/* 287 */     rectangle.translate(point.x, point.y);
/* 288 */     return rectangle;
/*     */   }
/*     */   
/*     */   TextHitInfo getLocationOffset(int paramInt1, int paramInt2) {
/* 292 */     TextLayout textLayout = this.composedTextLayout;
/* 293 */     if (textLayout == null) {
/* 294 */       return null;
/*     */     }
/* 296 */     Point point = getLocationOnScreen();
/* 297 */     paramInt1 -= point.x + 5;
/* 298 */     paramInt2 -= point.y + 15;
/* 299 */     if (textLayout.getBounds().contains(paramInt1, paramInt2)) {
/* 300 */       return textLayout.hitTestChar(paramInt1, paramInt2);
/*     */     }
/* 302 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setCompositionAreaUndecorated(boolean paramBoolean) {
/* 309 */     if (this.compositionWindow.isDisplayable()) {
/* 310 */       this.compositionWindow.removeNotify();
/*     */     }
/* 312 */     this.compositionWindow.setUndecorated(paramBoolean);
/* 313 */     this.compositionWindow.pack();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/im/CompositionArea.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */