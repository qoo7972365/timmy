/*     */ package javax.swing.tree;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.plaf.basic.BasicGraphicsUtils;
/*     */ import sun.swing.DefaultLookup;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultTreeCellRenderer
/*     */   extends JLabel
/*     */   implements TreeCellRenderer
/*     */ {
/*     */   private JTree tree;
/*     */   protected boolean selected;
/*     */   protected boolean hasFocus;
/*     */   private boolean drawsFocusBorderAroundIcon;
/*     */   private boolean drawDashedFocusIndicator;
/*     */   private Color treeBGColor;
/*     */   private Color focusBGColor;
/*     */   protected transient Icon closedIcon;
/*     */   protected transient Icon leafIcon;
/*     */   protected transient Icon openIcon;
/*     */   protected Color textSelectionColor;
/*     */   protected Color textNonSelectionColor;
/*     */   protected Color backgroundSelectionColor;
/*     */   protected Color backgroundNonSelectionColor;
/*     */   protected Color borderSelectionColor;
/*     */   private boolean isDropCell;
/*     */   private boolean fillBackground;
/*     */   private boolean inited = true;
/*     */   
/*     */   public void updateUI() {
/* 179 */     super.updateUI();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (!this.inited || getLeafIcon() instanceof javax.swing.plaf.UIResource) {
/* 188 */       setLeafIcon(DefaultLookup.getIcon(this, this.ui, "Tree.leafIcon"));
/*     */     }
/* 190 */     if (!this.inited || getClosedIcon() instanceof javax.swing.plaf.UIResource) {
/* 191 */       setClosedIcon(DefaultLookup.getIcon(this, this.ui, "Tree.closedIcon"));
/*     */     }
/* 193 */     if (!this.inited || getOpenIcon() instanceof UIManager) {
/* 194 */       setOpenIcon(DefaultLookup.getIcon(this, this.ui, "Tree.openIcon"));
/*     */     }
/* 196 */     if (!this.inited || getTextSelectionColor() instanceof javax.swing.plaf.UIResource) {
/* 197 */       setTextSelectionColor(
/* 198 */           DefaultLookup.getColor(this, this.ui, "Tree.selectionForeground"));
/*     */     }
/* 200 */     if (!this.inited || getTextNonSelectionColor() instanceof javax.swing.plaf.UIResource) {
/* 201 */       setTextNonSelectionColor(
/* 202 */           DefaultLookup.getColor(this, this.ui, "Tree.textForeground"));
/*     */     }
/* 204 */     if (!this.inited || getBackgroundSelectionColor() instanceof javax.swing.plaf.UIResource) {
/* 205 */       setBackgroundSelectionColor(
/* 206 */           DefaultLookup.getColor(this, this.ui, "Tree.selectionBackground"));
/*     */     }
/* 208 */     if (!this.inited || 
/* 209 */       getBackgroundNonSelectionColor() instanceof javax.swing.plaf.UIResource) {
/* 210 */       setBackgroundNonSelectionColor(
/* 211 */           DefaultLookup.getColor(this, this.ui, "Tree.textBackground"));
/*     */     }
/* 213 */     if (!this.inited || getBorderSelectionColor() instanceof javax.swing.plaf.UIResource) {
/* 214 */       setBorderSelectionColor(
/* 215 */           DefaultLookup.getColor(this, this.ui, "Tree.selectionBorderColor"));
/*     */     }
/* 217 */     this.drawsFocusBorderAroundIcon = DefaultLookup.getBoolean(this, this.ui, "Tree.drawsFocusBorderAroundIcon", false);
/*     */     
/* 219 */     this.drawDashedFocusIndicator = DefaultLookup.getBoolean(this, this.ui, "Tree.drawDashedFocusIndicator", false);
/*     */ 
/*     */     
/* 222 */     this.fillBackground = DefaultLookup.getBoolean(this, this.ui, "Tree.rendererFillBackground", true);
/* 223 */     Insets insets = DefaultLookup.getInsets(this, this.ui, "Tree.rendererMargins");
/* 224 */     if (insets != null) {
/* 225 */       setBorder(new EmptyBorder(insets.top, insets.left, insets.bottom, insets.right));
/*     */     }
/*     */ 
/*     */     
/* 229 */     setName("Tree.cellRenderer");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getDefaultOpenIcon() {
/* 238 */     return DefaultLookup.getIcon(this, this.ui, "Tree.openIcon");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getDefaultClosedIcon() {
/* 246 */     return DefaultLookup.getIcon(this, this.ui, "Tree.closedIcon");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getDefaultLeafIcon() {
/* 254 */     return DefaultLookup.getIcon(this, this.ui, "Tree.leafIcon");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOpenIcon(Icon paramIcon) {
/* 261 */     this.openIcon = paramIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getOpenIcon() {
/* 268 */     return this.openIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClosedIcon(Icon paramIcon) {
/* 275 */     this.closedIcon = paramIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getClosedIcon() {
/* 283 */     return this.closedIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLeafIcon(Icon paramIcon) {
/* 290 */     this.leafIcon = paramIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getLeafIcon() {
/* 297 */     return this.leafIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextSelectionColor(Color paramColor) {
/* 304 */     this.textSelectionColor = paramColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getTextSelectionColor() {
/* 311 */     return this.textSelectionColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextNonSelectionColor(Color paramColor) {
/* 318 */     this.textNonSelectionColor = paramColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getTextNonSelectionColor() {
/* 325 */     return this.textNonSelectionColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundSelectionColor(Color paramColor) {
/* 332 */     this.backgroundSelectionColor = paramColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getBackgroundSelectionColor() {
/* 340 */     return this.backgroundSelectionColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundNonSelectionColor(Color paramColor) {
/* 347 */     this.backgroundNonSelectionColor = paramColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getBackgroundNonSelectionColor() {
/* 354 */     return this.backgroundNonSelectionColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderSelectionColor(Color paramColor) {
/* 361 */     this.borderSelectionColor = paramColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getBorderSelectionColor() {
/* 368 */     return this.borderSelectionColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font paramFont) {
/* 379 */     if (paramFont instanceof javax.swing.plaf.FontUIResource)
/* 380 */       paramFont = null; 
/* 381 */     super.setFont(paramFont);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 390 */     Font font = super.getFont();
/*     */     
/* 392 */     if (font == null && this.tree != null)
/*     */     {
/*     */       
/* 395 */       font = this.tree.getFont();
/*     */     }
/* 397 */     return font;
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
/*     */   public void setBackground(Color paramColor) {
/* 409 */     if (paramColor instanceof javax.swing.plaf.ColorUIResource)
/* 410 */       paramColor = null; 
/* 411 */     super.setBackground(paramColor);
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
/*     */   public Component getTreeCellRendererComponent(JTree paramJTree, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt, boolean paramBoolean4) {
/* 428 */     String str = paramJTree.convertValueToText(paramObject, paramBoolean1, paramBoolean2, paramBoolean3, paramInt, paramBoolean4);
/*     */ 
/*     */     
/* 431 */     this.tree = paramJTree;
/* 432 */     this.hasFocus = paramBoolean4;
/* 433 */     setText(str);
/*     */     
/* 435 */     Color color = null;
/* 436 */     this.isDropCell = false;
/*     */     
/* 438 */     JTree.DropLocation dropLocation = paramJTree.getDropLocation();
/* 439 */     if (dropLocation != null && dropLocation
/* 440 */       .getChildIndex() == -1 && paramJTree
/* 441 */       .getRowForPath(dropLocation.getPath()) == paramInt) {
/*     */       
/* 443 */       Color color1 = DefaultLookup.getColor(this, this.ui, "Tree.dropCellForeground");
/* 444 */       if (color1 != null) {
/* 445 */         color = color1;
/*     */       } else {
/* 447 */         color = getTextSelectionColor();
/*     */       } 
/*     */       
/* 450 */       this.isDropCell = true;
/* 451 */     } else if (paramBoolean1) {
/* 452 */       color = getTextSelectionColor();
/*     */     } else {
/* 454 */       color = getTextNonSelectionColor();
/*     */     } 
/*     */     
/* 457 */     setForeground(color);
/*     */     
/* 459 */     Icon icon = null;
/* 460 */     if (paramBoolean3) {
/* 461 */       icon = getLeafIcon();
/* 462 */     } else if (paramBoolean2) {
/* 463 */       icon = getOpenIcon();
/*     */     } else {
/* 465 */       icon = getClosedIcon();
/*     */     } 
/*     */     
/* 468 */     if (!paramJTree.isEnabled()) {
/* 469 */       setEnabled(false);
/* 470 */       LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
/* 471 */       Icon icon1 = lookAndFeel.getDisabledIcon(paramJTree, icon);
/* 472 */       if (icon1 != null) icon = icon1; 
/* 473 */       setDisabledIcon(icon);
/*     */     } else {
/* 475 */       setEnabled(true);
/* 476 */       setIcon(icon);
/*     */     } 
/* 478 */     setComponentOrientation(paramJTree.getComponentOrientation());
/*     */     
/* 480 */     this.selected = paramBoolean1;
/*     */     
/* 482 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/*     */     Color color;
/* 491 */     if (this.isDropCell) {
/* 492 */       color = DefaultLookup.getColor(this, this.ui, "Tree.dropCellBackground");
/* 493 */       if (color == null) {
/* 494 */         color = getBackgroundSelectionColor();
/*     */       }
/* 496 */     } else if (this.selected) {
/* 497 */       color = getBackgroundSelectionColor();
/*     */     } else {
/* 499 */       color = getBackgroundNonSelectionColor();
/* 500 */       if (color == null) {
/* 501 */         color = getBackground();
/*     */       }
/*     */     } 
/*     */     
/* 505 */     int i = -1;
/* 506 */     if (color != null && this.fillBackground) {
/* 507 */       i = getLabelStart();
/* 508 */       paramGraphics.setColor(color);
/* 509 */       if (getComponentOrientation().isLeftToRight()) {
/* 510 */         paramGraphics.fillRect(i, 0, getWidth() - i, 
/* 511 */             getHeight());
/*     */       } else {
/* 513 */         paramGraphics.fillRect(0, 0, getWidth() - i, 
/* 514 */             getHeight());
/*     */       } 
/*     */     } 
/*     */     
/* 518 */     if (this.hasFocus) {
/* 519 */       if (this.drawsFocusBorderAroundIcon) {
/* 520 */         i = 0;
/*     */       }
/* 522 */       else if (i == -1) {
/* 523 */         i = getLabelStart();
/*     */       } 
/* 525 */       if (getComponentOrientation().isLeftToRight()) {
/* 526 */         paintFocus(paramGraphics, i, 0, getWidth() - i, 
/* 527 */             getHeight(), color);
/*     */       } else {
/* 529 */         paintFocus(paramGraphics, 0, 0, getWidth() - i, getHeight(), color);
/*     */       } 
/*     */     } 
/* 532 */     super.paint(paramGraphics);
/*     */   }
/*     */   
/*     */   private void paintFocus(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor) {
/* 536 */     Color color = getBorderSelectionColor();
/*     */     
/* 538 */     if (color != null && (this.selected || !this.drawDashedFocusIndicator)) {
/* 539 */       paramGraphics.setColor(color);
/* 540 */       paramGraphics.drawRect(paramInt1, paramInt2, paramInt3 - 1, paramInt4 - 1);
/*     */     } 
/* 542 */     if (this.drawDashedFocusIndicator && paramColor != null) {
/* 543 */       if (this.treeBGColor != paramColor) {
/* 544 */         this.treeBGColor = paramColor;
/* 545 */         this.focusBGColor = new Color(paramColor.getRGB() ^ 0xFFFFFFFF);
/*     */       } 
/* 547 */       paramGraphics.setColor(this.focusBGColor);
/* 548 */       BasicGraphicsUtils.drawDashedRect(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getLabelStart() {
/* 553 */     Icon icon = getIcon();
/* 554 */     if (icon != null && getText() != null) {
/* 555 */       return icon.getIconWidth() + Math.max(0, getIconTextGap() - 1);
/*     */     }
/* 557 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 565 */     Dimension dimension = super.getPreferredSize();
/*     */     
/* 567 */     if (dimension != null) {
/* 568 */       dimension = new Dimension(dimension.width + 3, dimension.height);
/*     */     }
/* 570 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void revalidate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void repaint(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void repaint(Rectangle paramRectangle) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void repaint() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) {
/* 626 */     if (paramString == "text" || ((paramString == "font" || paramString == "foreground") && paramObject1 != paramObject2 && 
/*     */ 
/*     */       
/* 629 */       getClientProperty("html") != null))
/*     */     {
/* 631 */       super.firePropertyChange(paramString, paramObject1, paramObject2);
/*     */     }
/*     */   }
/*     */   
/*     */   public void firePropertyChange(String paramString, byte paramByte1, byte paramByte2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, char paramChar1, char paramChar2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, short paramShort1, short paramShort2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, int paramInt1, int paramInt2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, long paramLong1, long paramLong2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, float paramFloat1, float paramFloat2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, double paramDouble1, double paramDouble2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, boolean paramBoolean1, boolean paramBoolean2) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/tree/DefaultTreeCellRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */