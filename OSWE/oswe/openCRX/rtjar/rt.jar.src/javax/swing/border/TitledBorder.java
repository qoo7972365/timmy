/*     */ package javax.swing.border;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.Path2D;
/*     */ import java.beans.ConstructorProperties;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TitledBorder
/*     */   extends AbstractBorder
/*     */ {
/*     */   protected String title;
/*     */   protected Border border;
/*     */   protected int titlePosition;
/*     */   protected int titleJustification;
/*     */   protected Font titleFont;
/*     */   protected Color titleColor;
/*     */   private final JLabel label;
/*     */   public static final int DEFAULT_POSITION = 0;
/*     */   public static final int ABOVE_TOP = 1;
/*     */   public static final int TOP = 2;
/*     */   public static final int BELOW_TOP = 3;
/*     */   public static final int ABOVE_BOTTOM = 4;
/*     */   public static final int BOTTOM = 5;
/*     */   public static final int BELOW_BOTTOM = 6;
/*     */   public static final int DEFAULT_JUSTIFICATION = 0;
/*     */   public static final int LEFT = 1;
/*     */   public static final int CENTER = 2;
/*     */   public static final int RIGHT = 3;
/*     */   public static final int LEADING = 4;
/*     */   public static final int TRAILING = 5;
/*     */   protected static final int EDGE_SPACING = 2;
/*     */   protected static final int TEXT_SPACING = 2;
/*     */   protected static final int TEXT_INSET_H = 5;
/*     */   
/*     */   public TitledBorder(String paramString) {
/* 135 */     this(null, paramString, 4, 0, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TitledBorder(Border paramBorder) {
/* 145 */     this(paramBorder, "", 4, 0, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TitledBorder(Border paramBorder, String paramString) {
/* 156 */     this(paramBorder, paramString, 4, 0, null, null);
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
/*     */   public TitledBorder(Border paramBorder, String paramString, int paramInt1, int paramInt2) {
/* 172 */     this(paramBorder, paramString, paramInt1, paramInt2, null, null);
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
/*     */   
/*     */   public TitledBorder(Border paramBorder, String paramString, int paramInt1, int paramInt2, Font paramFont) {
/* 191 */     this(paramBorder, paramString, paramInt1, paramInt2, paramFont, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ConstructorProperties({"border", "title", "titleJustification", "titlePosition", "titleFont", "titleColor"})
/*     */   public TitledBorder(Border paramBorder, String paramString, int paramInt1, int paramInt2, Font paramFont, Color paramColor) {
/* 214 */     this.title = paramString;
/* 215 */     this.border = paramBorder;
/* 216 */     this.titleFont = paramFont;
/* 217 */     this.titleColor = paramColor;
/*     */     
/* 219 */     setTitleJustification(paramInt1);
/* 220 */     setTitlePosition(paramInt2);
/*     */     
/* 222 */     this.label = new JLabel();
/* 223 */     this.label.setOpaque(false);
/* 224 */     this.label.putClientProperty("html", (Object)null);
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
/*     */   public void paintBorder(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 238 */     Border border = getBorder();
/* 239 */     String str = getTitle();
/* 240 */     if (str != null && !str.isEmpty()) {
/* 241 */       byte b = (border instanceof TitledBorder) ? 0 : 2;
/* 242 */       JLabel jLabel = getLabel(paramComponent);
/* 243 */       Dimension dimension = jLabel.getPreferredSize();
/* 244 */       Insets insets = getBorderInsets(border, paramComponent, new Insets(0, 0, 0, 0));
/*     */       
/* 246 */       int i = paramInt1 + b;
/* 247 */       int j = paramInt2 + b;
/* 248 */       int k = paramInt3 - b - b;
/* 249 */       int m = paramInt4 - b - b;
/*     */       
/* 251 */       int n = paramInt2;
/* 252 */       int i1 = dimension.height;
/* 253 */       int i2 = getPosition();
/* 254 */       switch (i2) {
/*     */         case 1:
/* 256 */           insets.left = 0;
/* 257 */           insets.right = 0;
/* 258 */           j += i1 - b;
/* 259 */           m -= i1 - b;
/*     */           break;
/*     */         case 2:
/* 262 */           insets.top = b + insets.top / 2 - i1 / 2;
/* 263 */           if (insets.top < b) {
/* 264 */             j -= insets.top;
/* 265 */             m += insets.top;
/*     */             break;
/*     */           } 
/* 268 */           n += insets.top;
/*     */           break;
/*     */         
/*     */         case 3:
/* 272 */           n += insets.top + b;
/*     */           break;
/*     */         case 4:
/* 275 */           n += paramInt4 - i1 - insets.bottom - b;
/*     */           break;
/*     */         case 5:
/* 278 */           n += paramInt4 - i1;
/* 279 */           insets.bottom = b + (insets.bottom - i1) / 2;
/* 280 */           if (insets.bottom < b) {
/* 281 */             m += insets.bottom;
/*     */             break;
/*     */           } 
/* 284 */           n -= insets.bottom;
/*     */           break;
/*     */         
/*     */         case 6:
/* 288 */           insets.left = 0;
/* 289 */           insets.right = 0;
/* 290 */           n += paramInt4 - i1;
/* 291 */           m -= i1 - b;
/*     */           break;
/*     */       } 
/* 294 */       insets.left += b + 5;
/* 295 */       insets.right += b + 5;
/*     */       
/* 297 */       int i3 = paramInt1;
/* 298 */       int i4 = paramInt3 - insets.left - insets.right;
/* 299 */       if (i4 > dimension.width) {
/* 300 */         i4 = dimension.width;
/*     */       }
/* 302 */       switch (getJustification(paramComponent)) {
/*     */         case 1:
/* 304 */           i3 += insets.left;
/*     */           break;
/*     */         case 3:
/* 307 */           i3 += paramInt3 - insets.right - i4;
/*     */           break;
/*     */         case 2:
/* 310 */           i3 += (paramInt3 - i4) / 2;
/*     */           break;
/*     */       } 
/*     */       
/* 314 */       if (border != null) {
/* 315 */         if (i2 != 2 && i2 != 5) {
/* 316 */           border.paintBorder(paramComponent, paramGraphics, i, j, k, m);
/*     */         } else {
/*     */           
/* 319 */           Graphics graphics = paramGraphics.create();
/* 320 */           if (graphics instanceof Graphics2D) {
/* 321 */             Graphics2D graphics2D = (Graphics2D)graphics;
/* 322 */             Path2D.Float float_ = new Path2D.Float();
/* 323 */             float_.append(new Rectangle(i, j, k, n - j), false);
/* 324 */             float_.append(new Rectangle(i, n, i3 - i - 2, i1), false);
/* 325 */             float_.append(new Rectangle(i3 + i4 + 2, n, i - i3 + k - i4 - 2, i1), false);
/* 326 */             float_.append(new Rectangle(i, n + i1, k, j - n + m - i1), false);
/* 327 */             graphics2D.clip(float_);
/*     */           } 
/* 329 */           border.paintBorder(paramComponent, graphics, i, j, k, m);
/* 330 */           graphics.dispose();
/*     */         } 
/*     */       }
/* 333 */       paramGraphics.translate(i3, n);
/* 334 */       jLabel.setSize(i4, i1);
/* 335 */       jLabel.paint(paramGraphics);
/* 336 */       paramGraphics.translate(-i3, -n);
/*     */     }
/* 338 */     else if (border != null) {
/* 339 */       border.paintBorder(paramComponent, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Insets getBorderInsets(Component paramComponent, Insets paramInsets) {
/* 349 */     Border border = getBorder();
/* 350 */     paramInsets = getBorderInsets(border, paramComponent, paramInsets);
/*     */     
/* 352 */     String str = getTitle();
/* 353 */     if (str != null && !str.isEmpty()) {
/* 354 */       byte b = (border instanceof TitledBorder) ? 0 : 2;
/* 355 */       JLabel jLabel = getLabel(paramComponent);
/* 356 */       Dimension dimension = jLabel.getPreferredSize();
/*     */       
/* 358 */       switch (getPosition()) {
/*     */         case 1:
/* 360 */           paramInsets.top += dimension.height - b;
/*     */           break;
/*     */         case 2:
/* 363 */           if (paramInsets.top < dimension.height) {
/* 364 */             paramInsets.top = dimension.height - b;
/*     */           }
/*     */           break;
/*     */         
/*     */         case 3:
/* 369 */           paramInsets.top += dimension.height;
/*     */           break;
/*     */         case 4:
/* 372 */           paramInsets.bottom += dimension.height;
/*     */           break;
/*     */         case 5:
/* 375 */           if (paramInsets.bottom < dimension.height) {
/* 376 */             paramInsets.bottom = dimension.height - b;
/*     */           }
/*     */           break;
/*     */         
/*     */         case 6:
/* 381 */           paramInsets.bottom += dimension.height - b;
/*     */           break;
/*     */       } 
/* 384 */       paramInsets.top += b + 2;
/* 385 */       paramInsets.left += b + 2;
/* 386 */       paramInsets.right += b + 2;
/* 387 */       paramInsets.bottom += b + 2;
/*     */     } 
/* 389 */     return paramInsets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBorderOpaque() {
/* 396 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/* 405 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Border getBorder() {
/* 414 */     return (this.border != null) ? this.border : 
/*     */       
/* 416 */       UIManager.getBorder("TitledBorder.border");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTitlePosition() {
/* 425 */     return this.titlePosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTitleJustification() {
/* 434 */     return this.titleJustification;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getTitleFont() {
/* 443 */     return (this.titleFont == null) ? UIManager.getFont("TitledBorder.font") : this.titleFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getTitleColor() {
/* 452 */     return (this.titleColor == null) ? UIManager.getColor("TitledBorder.titleColor") : this.titleColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitle(String paramString) {
/* 463 */     this.title = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorder(Border paramBorder) {
/* 471 */     this.border = paramBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitlePosition(int paramInt) {
/* 479 */     switch (paramInt) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/* 487 */         this.titlePosition = paramInt;
/*     */         return;
/*     */     } 
/* 490 */     throw new IllegalArgumentException(paramInt + " is not a valid title position.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitleJustification(int paramInt) {
/* 500 */     switch (paramInt) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/* 507 */         this.titleJustification = paramInt;
/*     */         return;
/*     */     } 
/* 510 */     throw new IllegalArgumentException(paramInt + " is not a valid title justification.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitleFont(Font paramFont) {
/* 520 */     this.titleFont = paramFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitleColor(Color paramColor) {
/* 528 */     this.titleColor = paramColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(Component paramComponent) {
/* 538 */     Insets insets = getBorderInsets(paramComponent);
/* 539 */     Dimension dimension = new Dimension(insets.right + insets.left, insets.top + insets.bottom);
/*     */     
/* 541 */     String str = getTitle();
/* 542 */     if (str != null && !str.isEmpty()) {
/* 543 */       JLabel jLabel = getLabel(paramComponent);
/* 544 */       Dimension dimension1 = jLabel.getPreferredSize();
/*     */       
/* 546 */       int i = getPosition();
/* 547 */       if (i != 1 && i != 6) {
/* 548 */         dimension.width += dimension1.width;
/*     */       }
/* 550 */       else if (dimension.width < dimension1.width) {
/* 551 */         dimension.width += dimension1.width;
/*     */       } 
/*     */     } 
/* 554 */     return dimension;
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
/*     */   public int getBaseline(Component paramComponent, int paramInt1, int paramInt2) {
/* 566 */     if (paramComponent == null) {
/* 567 */       throw new NullPointerException("Must supply non-null component");
/*     */     }
/* 569 */     if (paramInt1 < 0) {
/* 570 */       throw new IllegalArgumentException("Width must be >= 0");
/*     */     }
/* 572 */     if (paramInt2 < 0) {
/* 573 */       throw new IllegalArgumentException("Height must be >= 0");
/*     */     }
/* 575 */     Border border = getBorder();
/* 576 */     String str = getTitle();
/* 577 */     if (str != null && !str.isEmpty()) {
/* 578 */       byte b = (border instanceof TitledBorder) ? 0 : 2;
/* 579 */       JLabel jLabel = getLabel(paramComponent);
/* 580 */       Dimension dimension = jLabel.getPreferredSize();
/* 581 */       Insets insets = getBorderInsets(border, paramComponent, new Insets(0, 0, 0, 0));
/*     */       
/* 583 */       int i = jLabel.getBaseline(dimension.width, dimension.height);
/* 584 */       switch (getPosition()) {
/*     */         case 1:
/* 586 */           return i;
/*     */         case 2:
/* 588 */           insets.top = b + (insets.top - dimension.height) / 2;
/* 589 */           return (insets.top < b) ? i : (i + insets.top);
/*     */ 
/*     */         
/*     */         case 3:
/* 593 */           return i + insets.top + b;
/*     */         case 4:
/* 595 */           return i + paramInt2 - dimension.height - insets.bottom - b;
/*     */         case 5:
/* 597 */           insets.bottom = b + (insets.bottom - dimension.height) / 2;
/* 598 */           return (insets.bottom < b) ? (i + paramInt2 - dimension.height) : (i + paramInt2 - dimension.height + insets.bottom);
/*     */ 
/*     */         
/*     */         case 6:
/* 602 */           return i + paramInt2 - dimension.height;
/*     */       } 
/*     */     } 
/* 605 */     return -1;
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
/*     */   public Component.BaselineResizeBehavior getBaselineResizeBehavior(Component paramComponent) {
/* 618 */     super.getBaselineResizeBehavior(paramComponent);
/* 619 */     switch (getPosition()) {
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/* 623 */         return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/* 627 */         return Component.BaselineResizeBehavior.CONSTANT_DESCENT;
/*     */     } 
/* 629 */     return Component.BaselineResizeBehavior.OTHER;
/*     */   }
/*     */   
/*     */   private int getPosition() {
/* 633 */     int i = getTitlePosition();
/* 634 */     if (i != 0) {
/* 635 */       return i;
/*     */     }
/* 637 */     Object object = UIManager.get("TitledBorder.position");
/* 638 */     if (object instanceof Integer) {
/* 639 */       int j = ((Integer)object).intValue();
/* 640 */       if (0 < j && j <= 6) {
/* 641 */         return j;
/*     */       }
/*     */     }
/* 644 */     else if (object instanceof String) {
/* 645 */       String str = (String)object;
/* 646 */       if (str.equalsIgnoreCase("ABOVE_TOP")) {
/* 647 */         return 1;
/*     */       }
/* 649 */       if (str.equalsIgnoreCase("TOP")) {
/* 650 */         return 2;
/*     */       }
/* 652 */       if (str.equalsIgnoreCase("BELOW_TOP")) {
/* 653 */         return 3;
/*     */       }
/* 655 */       if (str.equalsIgnoreCase("ABOVE_BOTTOM")) {
/* 656 */         return 4;
/*     */       }
/* 658 */       if (str.equalsIgnoreCase("BOTTOM")) {
/* 659 */         return 5;
/*     */       }
/* 661 */       if (str.equalsIgnoreCase("BELOW_BOTTOM")) {
/* 662 */         return 6;
/*     */       }
/*     */     } 
/* 665 */     return 2;
/*     */   }
/*     */   
/*     */   private int getJustification(Component paramComponent) {
/* 669 */     int i = getTitleJustification();
/* 670 */     if (i == 4 || i == 0) {
/* 671 */       return paramComponent.getComponentOrientation().isLeftToRight() ? 1 : 3;
/*     */     }
/* 673 */     if (i == 5) {
/* 674 */       return paramComponent.getComponentOrientation().isLeftToRight() ? 3 : 1;
/*     */     }
/* 676 */     return i;
/*     */   }
/*     */   
/*     */   protected Font getFont(Component paramComponent) {
/* 680 */     Font font = getTitleFont();
/* 681 */     if (font != null) {
/* 682 */       return font;
/*     */     }
/* 684 */     if (paramComponent != null) {
/* 685 */       font = paramComponent.getFont();
/* 686 */       if (font != null) {
/* 687 */         return font;
/*     */       }
/*     */     } 
/* 690 */     return new Font("Dialog", 0, 12);
/*     */   }
/*     */   
/*     */   private Color getColor(Component paramComponent) {
/* 694 */     Color color = getTitleColor();
/* 695 */     if (color != null) {
/* 696 */       return color;
/*     */     }
/* 698 */     return (paramComponent != null) ? paramComponent
/* 699 */       .getForeground() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private JLabel getLabel(Component paramComponent) {
/* 704 */     this.label.setText(getTitle());
/* 705 */     this.label.setFont(getFont(paramComponent));
/* 706 */     this.label.setForeground(getColor(paramComponent));
/* 707 */     this.label.setComponentOrientation(paramComponent.getComponentOrientation());
/* 708 */     this.label.setEnabled(paramComponent.isEnabled());
/* 709 */     return this.label;
/*     */   }
/*     */   
/*     */   private static Insets getBorderInsets(Border paramBorder, Component paramComponent, Insets paramInsets) {
/* 713 */     if (paramBorder == null) {
/* 714 */       paramInsets.set(0, 0, 0, 0);
/*     */     }
/* 716 */     else if (paramBorder instanceof AbstractBorder) {
/* 717 */       AbstractBorder abstractBorder = (AbstractBorder)paramBorder;
/* 718 */       paramInsets = abstractBorder.getBorderInsets(paramComponent, paramInsets);
/*     */     } else {
/*     */       
/* 721 */       Insets insets = paramBorder.getBorderInsets(paramComponent);
/* 722 */       paramInsets.set(insets.top, insets.left, insets.bottom, insets.right);
/*     */     } 
/* 724 */     return paramInsets;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/border/TitledBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */