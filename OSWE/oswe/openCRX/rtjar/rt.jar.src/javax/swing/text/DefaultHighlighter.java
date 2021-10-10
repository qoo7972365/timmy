/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.util.Vector;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.plaf.TextUI;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultHighlighter
/*     */   extends LayeredHighlighter
/*     */ {
/*     */   private boolean drawsLayeredHighlights = true;
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/*  57 */     int i = this.highlights.size();
/*  58 */     for (byte b = 0; b < i; b++) {
/*  59 */       HighlightInfo highlightInfo = this.highlights.elementAt(b);
/*  60 */       if (!(highlightInfo instanceof LayeredHighlightInfo)) {
/*     */         
/*  62 */         Rectangle rectangle = this.component.getBounds();
/*  63 */         Insets insets = this.component.getInsets();
/*  64 */         rectangle.x = insets.left;
/*  65 */         rectangle.y = insets.top;
/*  66 */         rectangle.width -= insets.left + insets.right;
/*  67 */         rectangle.height -= insets.top + insets.bottom;
/*  68 */         for (; b < i; b++) {
/*  69 */           highlightInfo = this.highlights.elementAt(b);
/*  70 */           if (!(highlightInfo instanceof LayeredHighlightInfo)) {
/*  71 */             Highlighter.HighlightPainter highlightPainter = highlightInfo.getPainter();
/*  72 */             highlightPainter.paint(paramGraphics, highlightInfo.getStartOffset(), highlightInfo.getEndOffset(), rectangle, this.component);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
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
/*     */   public void install(JTextComponent paramJTextComponent) {
/*  89 */     this.component = paramJTextComponent;
/*  90 */     removeAllHighlights();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deinstall(JTextComponent paramJTextComponent) {
/* 101 */     this.component = null;
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
/*     */   public Object addHighlight(int paramInt1, int paramInt2, Highlighter.HighlightPainter paramHighlightPainter) throws BadLocationException {
/* 116 */     if (paramInt1 < 0) {
/* 117 */       throw new BadLocationException("Invalid start offset", paramInt1);
/*     */     }
/*     */     
/* 120 */     if (paramInt2 < paramInt1) {
/* 121 */       throw new BadLocationException("Invalid end offset", paramInt2);
/*     */     }
/*     */     
/* 124 */     Document document = this.component.getDocument();
/* 125 */     HighlightInfo highlightInfo = (getDrawsLayeredHighlights() && paramHighlightPainter instanceof LayeredHighlighter.LayerPainter) ? new LayeredHighlightInfo() : new HighlightInfo();
/*     */ 
/*     */     
/* 128 */     highlightInfo.painter = paramHighlightPainter;
/* 129 */     highlightInfo.p0 = document.createPosition(paramInt1);
/* 130 */     highlightInfo.p1 = document.createPosition(paramInt2);
/* 131 */     this.highlights.addElement(highlightInfo);
/* 132 */     safeDamageRange(paramInt1, paramInt2);
/* 133 */     return highlightInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeHighlight(Object paramObject) {
/* 142 */     if (paramObject instanceof LayeredHighlightInfo) {
/* 143 */       LayeredHighlightInfo layeredHighlightInfo = (LayeredHighlightInfo)paramObject;
/* 144 */       if (layeredHighlightInfo.width > 0 && layeredHighlightInfo.height > 0) {
/* 145 */         this.component.repaint(layeredHighlightInfo.x, layeredHighlightInfo.y, layeredHighlightInfo.width, layeredHighlightInfo.height);
/*     */       }
/*     */     } else {
/*     */       
/* 149 */       HighlightInfo highlightInfo = (HighlightInfo)paramObject;
/* 150 */       safeDamageRange(highlightInfo.p0, highlightInfo.p1);
/*     */     } 
/* 152 */     this.highlights.removeElement(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllHighlights() {
/* 159 */     TextUI textUI = this.component.getUI();
/* 160 */     if (getDrawsLayeredHighlights()) {
/* 161 */       int i = this.highlights.size();
/* 162 */       if (i != 0) {
/* 163 */         int j = 0;
/* 164 */         int k = 0;
/* 165 */         int m = 0;
/* 166 */         int n = 0;
/* 167 */         int i1 = -1;
/* 168 */         int i2 = -1;
/* 169 */         for (byte b = 0; b < i; b++) {
/* 170 */           HighlightInfo highlightInfo = this.highlights.elementAt(b);
/* 171 */           if (highlightInfo instanceof LayeredHighlightInfo) {
/* 172 */             LayeredHighlightInfo layeredHighlightInfo = (LayeredHighlightInfo)highlightInfo;
/* 173 */             j = Math.min(j, layeredHighlightInfo.x);
/* 174 */             k = Math.min(k, layeredHighlightInfo.y);
/* 175 */             m = Math.max(m, layeredHighlightInfo.x + layeredHighlightInfo.width);
/* 176 */             n = Math.max(n, layeredHighlightInfo.y + layeredHighlightInfo.height);
/*     */           
/*     */           }
/* 179 */           else if (i1 == -1) {
/* 180 */             i1 = highlightInfo.p0.getOffset();
/* 181 */             i2 = highlightInfo.p1.getOffset();
/*     */           } else {
/*     */             
/* 184 */             i1 = Math.min(i1, highlightInfo.p0.getOffset());
/* 185 */             i2 = Math.max(i2, highlightInfo.p1.getOffset());
/*     */           } 
/*     */         } 
/*     */         
/* 189 */         if (j != m && k != n) {
/* 190 */           this.component.repaint(j, k, m - j, n - k);
/*     */         }
/* 192 */         if (i1 != -1) {
/*     */           try {
/* 194 */             safeDamageRange(i1, i2);
/* 195 */           } catch (BadLocationException badLocationException) {}
/*     */         }
/* 197 */         this.highlights.removeAllElements();
/*     */       }
/*     */     
/* 200 */     } else if (textUI != null) {
/* 201 */       int i = this.highlights.size();
/* 202 */       if (i != 0) {
/* 203 */         int j = Integer.MAX_VALUE;
/* 204 */         int k = 0;
/* 205 */         for (byte b = 0; b < i; b++) {
/* 206 */           HighlightInfo highlightInfo = this.highlights.elementAt(b);
/* 207 */           j = Math.min(j, highlightInfo.p0.getOffset());
/* 208 */           k = Math.max(k, highlightInfo.p1.getOffset());
/*     */         } 
/*     */         try {
/* 211 */           safeDamageRange(j, k);
/* 212 */         } catch (BadLocationException badLocationException) {}
/*     */         
/* 214 */         this.highlights.removeAllElements();
/*     */       } 
/*     */     } 
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
/*     */   public void changeHighlight(Object paramObject, int paramInt1, int paramInt2) throws BadLocationException {
/* 228 */     if (paramInt1 < 0) {
/* 229 */       throw new BadLocationException("Invalid beginning of the range", paramInt1);
/*     */     }
/*     */     
/* 232 */     if (paramInt2 < paramInt1) {
/* 233 */       throw new BadLocationException("Invalid end of the range", paramInt2);
/*     */     }
/*     */     
/* 236 */     Document document = this.component.getDocument();
/* 237 */     if (paramObject instanceof LayeredHighlightInfo) {
/* 238 */       LayeredHighlightInfo layeredHighlightInfo = (LayeredHighlightInfo)paramObject;
/* 239 */       if (layeredHighlightInfo.width > 0 && layeredHighlightInfo.height > 0) {
/* 240 */         this.component.repaint(layeredHighlightInfo.x, layeredHighlightInfo.y, layeredHighlightInfo.width, layeredHighlightInfo.height);
/*     */       }
/*     */ 
/*     */       
/* 244 */       layeredHighlightInfo.width = layeredHighlightInfo.height = 0;
/* 245 */       layeredHighlightInfo.p0 = document.createPosition(paramInt1);
/* 246 */       layeredHighlightInfo.p1 = document.createPosition(paramInt2);
/* 247 */       safeDamageRange(Math.min(paramInt1, paramInt2), Math.max(paramInt1, paramInt2));
/*     */     } else {
/*     */       
/* 250 */       HighlightInfo highlightInfo = (HighlightInfo)paramObject;
/* 251 */       int i = highlightInfo.p0.getOffset();
/* 252 */       int j = highlightInfo.p1.getOffset();
/* 253 */       if (paramInt1 == i) {
/* 254 */         safeDamageRange(Math.min(j, paramInt2), 
/* 255 */             Math.max(j, paramInt2));
/* 256 */       } else if (paramInt2 == j) {
/* 257 */         safeDamageRange(Math.min(paramInt1, i), 
/* 258 */             Math.max(paramInt1, i));
/*     */       } else {
/* 260 */         safeDamageRange(i, j);
/* 261 */         safeDamageRange(paramInt1, paramInt2);
/*     */       } 
/* 263 */       highlightInfo.p0 = document.createPosition(paramInt1);
/* 264 */       highlightInfo.p1 = document.createPosition(paramInt2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Highlighter.Highlight[] getHighlights() {
/* 276 */     int i = this.highlights.size();
/* 277 */     if (i == 0) {
/* 278 */       return noHighlights;
/*     */     }
/* 280 */     Highlighter.Highlight[] arrayOfHighlight = new Highlighter.Highlight[i];
/* 281 */     this.highlights.copyInto((Object[])arrayOfHighlight);
/* 282 */     return arrayOfHighlight;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintLayeredHighlights(Graphics paramGraphics, int paramInt1, int paramInt2, Shape paramShape, JTextComponent paramJTextComponent, View paramView) {
/* 300 */     for (int i = this.highlights.size() - 1; i >= 0; i--) {
/* 301 */       HighlightInfo highlightInfo = this.highlights.elementAt(i);
/* 302 */       if (highlightInfo instanceof LayeredHighlightInfo) {
/* 303 */         LayeredHighlightInfo layeredHighlightInfo = (LayeredHighlightInfo)highlightInfo;
/* 304 */         int j = layeredHighlightInfo.getStartOffset();
/* 305 */         int k = layeredHighlightInfo.getEndOffset();
/* 306 */         if ((paramInt1 < j && paramInt2 > j) || (paramInt1 >= j && paramInt1 < k))
/*     */         {
/* 308 */           layeredHighlightInfo.paintLayeredHighlights(paramGraphics, paramInt1, paramInt2, paramShape, paramJTextComponent, paramView);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void safeDamageRange(Position paramPosition1, Position paramPosition2) {
/* 320 */     this.safeDamager.damageRange(paramPosition1, paramPosition2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void safeDamageRange(int paramInt1, int paramInt2) throws BadLocationException {
/* 328 */     Document document = this.component.getDocument();
/* 329 */     safeDamageRange(document.createPosition(paramInt1), document.createPosition(paramInt2));
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
/*     */   public void setDrawsLayeredHighlights(boolean paramBoolean) {
/* 341 */     this.drawsLayeredHighlights = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean getDrawsLayeredHighlights() {
/* 345 */     return this.drawsLayeredHighlights;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 350 */   private static final Highlighter.Highlight[] noHighlights = new Highlighter.Highlight[0];
/*     */   
/* 352 */   private Vector<HighlightInfo> highlights = new Vector<>();
/*     */ 
/*     */   
/* 355 */   private SafeDamager safeDamager = new SafeDamager();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 364 */   public static final LayeredHighlighter.LayerPainter DefaultPainter = new DefaultHighlightPainter(null);
/*     */ 
/*     */ 
/*     */   
/*     */   private JTextComponent component;
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DefaultHighlightPainter
/*     */     extends LayeredHighlighter.LayerPainter
/*     */   {
/*     */     private Color color;
/*     */ 
/*     */ 
/*     */     
/*     */     public DefaultHighlightPainter(Color param1Color) {
/* 380 */       this.color = param1Color;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Color getColor() {
/* 389 */       return this.color;
/*     */     }
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
/*     */     public void paint(Graphics param1Graphics, int param1Int1, int param1Int2, Shape param1Shape, JTextComponent param1JTextComponent) {
/* 404 */       Rectangle rectangle = param1Shape.getBounds();
/*     */       
/*     */       try {
/* 407 */         TextUI textUI = param1JTextComponent.getUI();
/* 408 */         Rectangle rectangle1 = textUI.modelToView(param1JTextComponent, param1Int1);
/* 409 */         Rectangle rectangle2 = textUI.modelToView(param1JTextComponent, param1Int2);
/*     */ 
/*     */         
/* 412 */         Color color = getColor();
/*     */         
/* 414 */         if (color == null) {
/* 415 */           param1Graphics.setColor(param1JTextComponent.getSelectionColor());
/*     */         } else {
/*     */           
/* 418 */           param1Graphics.setColor(color);
/*     */         } 
/* 420 */         if (rectangle1.y == rectangle2.y) {
/*     */           
/* 422 */           Rectangle rectangle3 = rectangle1.union(rectangle2);
/* 423 */           param1Graphics.fillRect(rectangle3.x, rectangle3.y, rectangle3.width, rectangle3.height);
/*     */         } else {
/*     */           
/* 426 */           int i = rectangle.x + rectangle.width - rectangle1.x;
/* 427 */           param1Graphics.fillRect(rectangle1.x, rectangle1.y, i, rectangle1.height);
/* 428 */           if (rectangle1.y + rectangle1.height != rectangle2.y) {
/* 429 */             param1Graphics.fillRect(rectangle.x, rectangle1.y + rectangle1.height, rectangle.width, rectangle2.y - rectangle1.y + rectangle1.height);
/*     */           }
/*     */           
/* 432 */           param1Graphics.fillRect(rectangle.x, rectangle2.y, rectangle2.x - rectangle.x, rectangle2.height);
/*     */         } 
/* 434 */       } catch (BadLocationException badLocationException) {}
/*     */     }
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
/*     */     public Shape paintLayer(Graphics param1Graphics, int param1Int1, int param1Int2, Shape param1Shape, JTextComponent param1JTextComponent, View param1View) {
/*     */       Shape shape;
/* 454 */       Color color = getColor();
/*     */       
/* 456 */       if (color == null) {
/* 457 */         param1Graphics.setColor(param1JTextComponent.getSelectionColor());
/*     */       } else {
/*     */         
/* 460 */         param1Graphics.setColor(color);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 465 */       if (param1Int1 == param1View.getStartOffset() && param1Int2 == param1View
/* 466 */         .getEndOffset()) {
/*     */         
/* 468 */         if (param1Shape instanceof Rectangle) {
/* 469 */           shape = param1Shape;
/*     */         } else {
/*     */           
/* 472 */           shape = param1Shape.getBounds();
/*     */         } 
/*     */       } else {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 479 */           Shape shape1 = param1View.modelToView(param1Int1, Position.Bias.Forward, param1Int2, Position.Bias.Backward, param1Shape);
/*     */ 
/*     */ 
/*     */           
/* 483 */           shape = (shape1 instanceof Rectangle) ? (Rectangle)shape1 : shape1.getBounds();
/* 484 */         } catch (BadLocationException badLocationException) {
/*     */           
/* 486 */           shape = null;
/*     */         } 
/*     */       } 
/*     */       
/* 490 */       if (shape != null) {
/*     */ 
/*     */         
/* 493 */         ((Rectangle)shape).width = Math.max(((Rectangle)shape).width, 1);
/*     */         
/* 495 */         param1Graphics.fillRect(((Rectangle)shape).x, ((Rectangle)shape).y, ((Rectangle)shape).width, ((Rectangle)shape).height);
/*     */       } 
/*     */       
/* 498 */       return shape;
/*     */     }
/*     */   }
/*     */   
/*     */   class HighlightInfo
/*     */     implements Highlighter.Highlight {
/*     */     Position p0;
/*     */     Position p1;
/*     */     Highlighter.HighlightPainter painter;
/*     */     
/*     */     public int getStartOffset() {
/* 509 */       return this.p0.getOffset();
/*     */     }
/*     */     
/*     */     public int getEndOffset() {
/* 513 */       return this.p1.getOffset();
/*     */     }
/*     */     
/*     */     public Highlighter.HighlightPainter getPainter() {
/* 517 */       return this.painter;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class LayeredHighlightInfo
/*     */     extends HighlightInfo
/*     */   {
/*     */     int x;
/*     */     
/*     */     int y;
/*     */     int width;
/*     */     int height;
/*     */     
/*     */     void union(Shape param1Shape) {
/*     */       Rectangle rectangle;
/* 533 */       if (param1Shape == null) {
/*     */         return;
/*     */       }
/*     */       
/* 537 */       if (param1Shape instanceof Rectangle) {
/* 538 */         rectangle = (Rectangle)param1Shape;
/*     */       } else {
/*     */         
/* 541 */         rectangle = param1Shape.getBounds();
/*     */       } 
/* 543 */       if (this.width == 0 || this.height == 0) {
/* 544 */         this.x = rectangle.x;
/* 545 */         this.y = rectangle.y;
/* 546 */         this.width = rectangle.width;
/* 547 */         this.height = rectangle.height;
/*     */       } else {
/*     */         
/* 550 */         this.width = Math.max(this.x + this.width, rectangle.x + rectangle.width);
/* 551 */         this.height = Math.max(this.y + this.height, rectangle.y + rectangle.height);
/* 552 */         this.x = Math.min(this.x, rectangle.x);
/* 553 */         this.width -= this.x;
/* 554 */         this.y = Math.min(this.y, rectangle.y);
/* 555 */         this.height -= this.y;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void paintLayeredHighlights(Graphics param1Graphics, int param1Int1, int param1Int2, Shape param1Shape, JTextComponent param1JTextComponent, View param1View) {
/* 566 */       int i = getStartOffset();
/* 567 */       int j = getEndOffset();
/*     */       
/* 569 */       param1Int1 = Math.max(i, param1Int1);
/* 570 */       param1Int2 = Math.min(j, param1Int2);
/*     */ 
/*     */       
/* 573 */       union(((LayeredHighlighter.LayerPainter)this.painter)
/* 574 */           .paintLayer(param1Graphics, param1Int1, param1Int2, param1Shape, param1JTextComponent, param1View));
/*     */     }
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
/*     */   class SafeDamager
/*     */     implements Runnable
/*     */   {
/* 592 */     private Vector<Position> p0 = new Vector<>(10);
/* 593 */     private Vector<Position> p1 = new Vector<>(10);
/* 594 */     private Document lastDoc = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void run() {
/* 600 */       if (DefaultHighlighter.this.component != null) {
/* 601 */         TextUI textUI = DefaultHighlighter.this.component.getUI();
/* 602 */         if (textUI != null && this.lastDoc == DefaultHighlighter.this.component.getDocument()) {
/*     */ 
/*     */           
/* 605 */           int i = this.p0.size();
/* 606 */           for (byte b = 0; b < i; b++) {
/* 607 */             textUI.damageRange(DefaultHighlighter.this.component, ((Position)this.p0
/* 608 */                 .get(b)).getOffset(), ((Position)this.p1
/* 609 */                 .get(b)).getOffset());
/*     */           }
/*     */         } 
/*     */       } 
/* 613 */       this.p0.clear();
/* 614 */       this.p1.clear();
/*     */ 
/*     */       
/* 617 */       this.lastDoc = null;
/*     */     }
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
/*     */     public synchronized void damageRange(Position param1Position1, Position param1Position2) {
/* 631 */       if (DefaultHighlighter.this.component == null) {
/* 632 */         this.p0.clear();
/* 633 */         this.lastDoc = null;
/*     */         
/*     */         return;
/*     */       } 
/* 637 */       boolean bool = this.p0.isEmpty();
/* 638 */       Document document = DefaultHighlighter.this.component.getDocument();
/* 639 */       if (document != this.lastDoc) {
/* 640 */         if (!this.p0.isEmpty()) {
/* 641 */           this.p0.clear();
/* 642 */           this.p1.clear();
/*     */         } 
/* 644 */         this.lastDoc = document;
/*     */       } 
/* 646 */       this.p0.add(param1Position1);
/* 647 */       this.p1.add(param1Position2);
/*     */       
/* 649 */       if (bool)
/* 650 */         SwingUtilities.invokeLater(this); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/DefaultHighlighter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */