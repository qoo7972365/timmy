/*     */ package javax.swing.colorchooser;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JPanel;
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
/*     */ class SwatchPanel
/*     */   extends JPanel
/*     */ {
/*     */   protected Color[] colors;
/*     */   protected Dimension swatchSize;
/*     */   protected Dimension numSwatches;
/*     */   protected Dimension gap;
/*     */   private int selRow;
/*     */   private int selCol;
/*     */   
/*     */   public SwatchPanel() {
/* 281 */     initValues();
/* 282 */     initColors();
/* 283 */     setToolTipText("");
/* 284 */     setOpaque(true);
/* 285 */     setBackground(Color.white);
/* 286 */     setFocusable(true);
/* 287 */     setInheritsPopupMenu(true);
/*     */     
/* 289 */     addFocusListener(new FocusAdapter() {
/*     */           public void focusGained(FocusEvent param1FocusEvent) {
/* 291 */             SwatchPanel.this.repaint();
/*     */           }
/*     */           
/*     */           public void focusLost(FocusEvent param1FocusEvent) {
/* 295 */             SwatchPanel.this.repaint();
/*     */           }
/*     */         });
/*     */     
/* 299 */     addKeyListener(new KeyAdapter() {
/*     */           public void keyPressed(KeyEvent param1KeyEvent) {
/* 301 */             int i = param1KeyEvent.getKeyCode();
/* 302 */             switch (i) {
/*     */               case 38:
/* 304 */                 if (SwatchPanel.this.selRow > 0) {
/* 305 */                   SwatchPanel.this.selRow--;
/* 306 */                   SwatchPanel.this.repaint();
/*     */                 } 
/*     */                 break;
/*     */               case 40:
/* 310 */                 if (SwatchPanel.this.selRow < SwatchPanel.this.numSwatches.height - 1) {
/* 311 */                   SwatchPanel.this.selRow++;
/* 312 */                   SwatchPanel.this.repaint();
/*     */                 } 
/*     */                 break;
/*     */               case 37:
/* 316 */                 if (SwatchPanel.this.selCol > 0 && SwatchPanel.this.getComponentOrientation().isLeftToRight()) {
/* 317 */                   SwatchPanel.this.selCol--;
/* 318 */                   SwatchPanel.this.repaint(); break;
/* 319 */                 }  if (SwatchPanel.this.selCol < SwatchPanel.this.numSwatches.width - 1 && 
/* 320 */                   !SwatchPanel.this.getComponentOrientation().isLeftToRight()) {
/* 321 */                   SwatchPanel.this.selCol++;
/* 322 */                   SwatchPanel.this.repaint();
/*     */                 } 
/*     */                 break;
/*     */               case 39:
/* 326 */                 if (SwatchPanel.this.selCol < SwatchPanel.this.numSwatches.width - 1 && SwatchPanel.this
/* 327 */                   .getComponentOrientation().isLeftToRight()) {
/* 328 */                   SwatchPanel.this.selCol++;
/* 329 */                   SwatchPanel.this.repaint(); break;
/* 330 */                 }  if (SwatchPanel.this.selCol > 0 && !SwatchPanel.this.getComponentOrientation().isLeftToRight()) {
/* 331 */                   SwatchPanel.this.selCol--;
/* 332 */                   SwatchPanel.this.repaint();
/*     */                 } 
/*     */                 break;
/*     */               case 36:
/* 336 */                 SwatchPanel.this.selCol = 0;
/* 337 */                 SwatchPanel.this.selRow = 0;
/* 338 */                 SwatchPanel.this.repaint();
/*     */                 break;
/*     */               case 35:
/* 341 */                 SwatchPanel.this.selCol = SwatchPanel.this.numSwatches.width - 1;
/* 342 */                 SwatchPanel.this.selRow = SwatchPanel.this.numSwatches.height - 1;
/* 343 */                 SwatchPanel.this.repaint();
/*     */                 break;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public Color getSelectedColor() {
/* 351 */     return getColorForCell(this.selCol, this.selRow);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initValues() {}
/*     */ 
/*     */   
/*     */   public void paintComponent(Graphics paramGraphics) {
/* 359 */     paramGraphics.setColor(getBackground());
/* 360 */     paramGraphics.fillRect(0, 0, getWidth(), getHeight());
/* 361 */     for (byte b = 0; b < this.numSwatches.height; b++) {
/* 362 */       int i = b * (this.swatchSize.height + this.gap.height);
/* 363 */       for (byte b1 = 0; b1 < this.numSwatches.width; b1++) {
/* 364 */         int j; Color color = getColorForCell(b1, b);
/* 365 */         paramGraphics.setColor(color);
/*     */         
/* 367 */         if (!getComponentOrientation().isLeftToRight()) {
/* 368 */           j = (this.numSwatches.width - b1 - 1) * (this.swatchSize.width + this.gap.width);
/*     */         } else {
/* 370 */           j = b1 * (this.swatchSize.width + this.gap.width);
/*     */         } 
/* 372 */         paramGraphics.fillRect(j, i, this.swatchSize.width, this.swatchSize.height);
/* 373 */         paramGraphics.setColor(Color.black);
/* 374 */         paramGraphics.drawLine(j + this.swatchSize.width - 1, i, j + this.swatchSize.width - 1, i + this.swatchSize.height - 1);
/* 375 */         paramGraphics.drawLine(j, i + this.swatchSize.height - 1, j + this.swatchSize.width - 1, i + this.swatchSize.height - 1);
/*     */         
/* 377 */         if (this.selRow == b && this.selCol == b1 && isFocusOwner()) {
/*     */ 
/*     */           
/* 380 */           Color color1 = new Color((color.getRed() < 125) ? 255 : 0, (color.getGreen() < 125) ? 255 : 0, (color.getBlue() < 125) ? 255 : 0);
/* 381 */           paramGraphics.setColor(color1);
/*     */           
/* 383 */           paramGraphics.drawLine(j, i, j + this.swatchSize.width - 1, i);
/* 384 */           paramGraphics.drawLine(j, i, j, i + this.swatchSize.height - 1);
/* 385 */           paramGraphics.drawLine(j + this.swatchSize.width - 1, i, j + this.swatchSize.width - 1, i + this.swatchSize.height - 1);
/* 386 */           paramGraphics.drawLine(j, i + this.swatchSize.height - 1, j + this.swatchSize.width - 1, i + this.swatchSize.height - 1);
/* 387 */           paramGraphics.drawLine(j, i, j + this.swatchSize.width - 1, i + this.swatchSize.height - 1);
/* 388 */           paramGraphics.drawLine(j, i + this.swatchSize.height - 1, j + this.swatchSize.width - 1, i);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 395 */     int i = this.numSwatches.width * (this.swatchSize.width + this.gap.width) - 1;
/* 396 */     int j = this.numSwatches.height * (this.swatchSize.height + this.gap.height) - 1;
/* 397 */     return new Dimension(i, j);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initColors() {}
/*     */ 
/*     */   
/*     */   public String getToolTipText(MouseEvent paramMouseEvent) {
/* 406 */     Color color = getColorForLocation(paramMouseEvent.getX(), paramMouseEvent.getY());
/* 407 */     return color.getRed() + ", " + color.getGreen() + ", " + color.getBlue();
/*     */   }
/*     */   
/*     */   public void setSelectedColorFromLocation(int paramInt1, int paramInt2) {
/* 411 */     if (!getComponentOrientation().isLeftToRight()) {
/* 412 */       this.selCol = this.numSwatches.width - paramInt1 / (this.swatchSize.width + this.gap.width) - 1;
/*     */     } else {
/* 414 */       this.selCol = paramInt1 / (this.swatchSize.width + this.gap.width);
/*     */     } 
/* 416 */     this.selRow = paramInt2 / (this.swatchSize.height + this.gap.height);
/* 417 */     repaint();
/*     */   }
/*     */   
/*     */   public Color getColorForLocation(int paramInt1, int paramInt2) {
/*     */     int i;
/* 422 */     if (!getComponentOrientation().isLeftToRight()) {
/* 423 */       i = this.numSwatches.width - paramInt1 / (this.swatchSize.width + this.gap.width) - 1;
/*     */     } else {
/* 425 */       i = paramInt1 / (this.swatchSize.width + this.gap.width);
/*     */     } 
/* 427 */     int j = paramInt2 / (this.swatchSize.height + this.gap.height);
/* 428 */     return getColorForCell(i, j);
/*     */   }
/*     */   
/*     */   private Color getColorForCell(int paramInt1, int paramInt2) {
/* 432 */     return this.colors[paramInt2 * this.numSwatches.width + paramInt1];
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/SwatchPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */