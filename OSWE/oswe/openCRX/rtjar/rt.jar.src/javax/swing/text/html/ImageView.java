/*      */ package javax.swing.text.html;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Image;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.util.Dictionary;
/*      */ import javax.swing.GrayFilter;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.text.AbstractDocument;
/*      */ import javax.swing.text.AttributeSet;
/*      */ import javax.swing.text.BadLocationException;
/*      */ import javax.swing.text.Document;
/*      */ import javax.swing.text.Element;
/*      */ import javax.swing.text.GlyphView;
/*      */ import javax.swing.text.Highlighter;
/*      */ import javax.swing.text.JTextComponent;
/*      */ import javax.swing.text.LayeredHighlighter;
/*      */ import javax.swing.text.Position;
/*      */ import javax.swing.text.Segment;
/*      */ import javax.swing.text.StyledDocument;
/*      */ import javax.swing.text.View;
/*      */ import javax.swing.text.ViewFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ImageView
/*      */   extends View
/*      */ {
/*      */   private static boolean sIsInc = false;
/*   61 */   private static int sIncRate = 100;
/*      */ 
/*      */   
/*      */   private static final String PENDING_IMAGE = "html.pendingImage";
/*      */ 
/*      */   
/*      */   private static final String MISSING_IMAGE = "html.missingImage";
/*      */ 
/*      */   
/*      */   private static final String IMAGE_CACHE_PROPERTY = "imageCache";
/*      */ 
/*      */   
/*      */   private static final int DEFAULT_WIDTH = 38;
/*      */ 
/*      */   
/*      */   private static final int DEFAULT_HEIGHT = 38;
/*      */ 
/*      */   
/*      */   private static final int DEFAULT_BORDER = 2;
/*      */ 
/*      */   
/*      */   private static final int LOADING_FLAG = 1;
/*      */ 
/*      */   
/*      */   private static final int LINK_FLAG = 2;
/*      */ 
/*      */   
/*      */   private static final int WIDTH_FLAG = 4;
/*      */ 
/*      */   
/*      */   private static final int HEIGHT_FLAG = 8;
/*      */ 
/*      */   
/*      */   private static final int RELOAD_FLAG = 16;
/*      */ 
/*      */   
/*      */   private static final int RELOAD_IMAGE_FLAG = 32;
/*      */   
/*      */   private static final int SYNC_LOAD_FLAG = 64;
/*      */   
/*      */   private AttributeSet attr;
/*      */   
/*      */   private Image image;
/*      */   
/*      */   private Image disabledImage;
/*      */   
/*      */   private int width;
/*      */   
/*      */   private int height;
/*      */   
/*      */   private int state;
/*      */   
/*      */   private Container container;
/*      */   
/*      */   private Rectangle fBounds;
/*      */   
/*      */   private Color borderColor;
/*      */   
/*      */   private short borderSize;
/*      */   
/*      */   private short leftInset;
/*      */   
/*      */   private short rightInset;
/*      */   
/*      */   private short topInset;
/*      */   
/*      */   private short bottomInset;
/*      */   
/*      */   private ImageObserver imageObserver;
/*      */   
/*      */   private View altView;
/*      */   
/*      */   private float vAlign;
/*      */ 
/*      */   
/*      */   public ImageView(Element paramElement) {
/*  137 */     super(paramElement);
/*  138 */     this.fBounds = new Rectangle();
/*  139 */     this.imageObserver = new ImageHandler();
/*  140 */     this.state = 48;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAltText() {
/*  149 */     return (String)getElement().getAttributes()
/*  150 */       .getAttribute(HTML.Attribute.ALT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL getImageURL() {
/*  159 */     String str = (String)getElement().getAttributes().getAttribute(HTML.Attribute.SRC);
/*  160 */     if (str == null) {
/*  161 */       return null;
/*      */     }
/*      */     
/*  164 */     URL uRL = ((HTMLDocument)getDocument()).getBase();
/*      */     try {
/*  166 */       return new URL(uRL, str);
/*      */     }
/*  168 */     catch (MalformedURLException malformedURLException) {
/*  169 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Icon getNoImageIcon() {
/*  177 */     return (Icon)UIManager.getLookAndFeelDefaults().get("html.missingImage");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Icon getLoadingImageIcon() {
/*  184 */     return (Icon)UIManager.getLookAndFeelDefaults().get("html.pendingImage");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image getImage() {
/*  191 */     sync();
/*  192 */     return this.image;
/*      */   }
/*      */   
/*      */   private Image getImage(boolean paramBoolean) {
/*  196 */     Image image = getImage();
/*  197 */     if (!paramBoolean) {
/*  198 */       if (this.disabledImage == null) {
/*  199 */         this.disabledImage = GrayFilter.createDisabledImage(image);
/*      */       }
/*  201 */       image = this.disabledImage;
/*      */     } 
/*  203 */     return image;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadsSynchronously(boolean paramBoolean) {
/*  213 */     synchronized (this) {
/*  214 */       if (paramBoolean) {
/*  215 */         this.state |= 0x40;
/*      */       } else {
/*      */         
/*  218 */         this.state = (this.state | 0x40) ^ 0x40;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getLoadsSynchronously() {
/*  227 */     return ((this.state & 0x40) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected StyleSheet getStyleSheet() {
/*  234 */     HTMLDocument hTMLDocument = (HTMLDocument)getDocument();
/*  235 */     return hTMLDocument.getStyleSheet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeSet getAttributes() {
/*  244 */     sync();
/*  245 */     return this.attr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getToolTipText(float paramFloat1, float paramFloat2, Shape paramShape) {
/*  256 */     return getAltText();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setPropertiesFromAttributes() {
/*  263 */     StyleSheet styleSheet = getStyleSheet();
/*  264 */     this.attr = styleSheet.getViewAttributes(this);
/*      */ 
/*      */     
/*  267 */     this.borderSize = (short)getIntAttr(HTML.Attribute.BORDER, isLink() ? 2 : 0);
/*      */ 
/*      */     
/*  270 */     this.leftInset = this.rightInset = (short)(getIntAttr(HTML.Attribute.HSPACE, 0) + this.borderSize);
/*      */     
/*  272 */     this.topInset = this.bottomInset = (short)(getIntAttr(HTML.Attribute.VSPACE, 0) + this.borderSize);
/*      */ 
/*      */     
/*  275 */     this
/*  276 */       .borderColor = ((StyledDocument)getDocument()).getForeground(getAttributes());
/*      */     
/*  278 */     AttributeSet attributeSet1 = getElement().getAttributes();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  283 */     Object object = attributeSet1.getAttribute(HTML.Attribute.ALIGN);
/*      */     
/*  285 */     this.vAlign = 1.0F;
/*  286 */     if (object != null) {
/*  287 */       object = object.toString();
/*  288 */       if ("top".equals(object)) {
/*  289 */         this.vAlign = 0.0F;
/*      */       }
/*  291 */       else if ("middle".equals(object)) {
/*  292 */         this.vAlign = 0.5F;
/*      */       } 
/*      */     } 
/*      */     
/*  296 */     AttributeSet attributeSet2 = (AttributeSet)attributeSet1.getAttribute(HTML.Tag.A);
/*  297 */     if (attributeSet2 != null && attributeSet2
/*  298 */       .isDefined(HTML.Attribute.HREF)) {
/*  299 */       synchronized (this) {
/*  300 */         this.state |= 0x2;
/*      */       } 
/*      */     } else {
/*      */       
/*  304 */       synchronized (this) {
/*  305 */         this.state = (this.state | 0x2) ^ 0x2;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setParent(View paramView) {
/*  315 */     View view = getParent();
/*  316 */     super.setParent(paramView);
/*  317 */     this.container = (paramView != null) ? getContainer() : null;
/*  318 */     if (view != paramView) {
/*  319 */       synchronized (this) {
/*  320 */         this.state |= 0x10;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void changedUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/*  329 */     super.changedUpdate(paramDocumentEvent, paramShape, paramViewFactory);
/*      */     
/*  331 */     synchronized (this) {
/*  332 */       this.state |= 0x30;
/*      */     } 
/*      */ 
/*      */     
/*  336 */     preferenceChanged(null, true, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paint(Graphics paramGraphics, Shape paramShape) {
/*  347 */     sync();
/*      */ 
/*      */     
/*  350 */     Rectangle rectangle1 = (paramShape instanceof Rectangle) ? (Rectangle)paramShape : paramShape.getBounds();
/*  351 */     Rectangle rectangle2 = paramGraphics.getClipBounds();
/*      */     
/*  353 */     this.fBounds.setBounds(rectangle1);
/*  354 */     paintHighlights(paramGraphics, paramShape);
/*  355 */     paintBorder(paramGraphics, rectangle1);
/*  356 */     if (rectangle2 != null) {
/*  357 */       paramGraphics.clipRect(rectangle1.x + this.leftInset, rectangle1.y + this.topInset, rectangle1.width - this.leftInset - this.rightInset, rectangle1.height - this.topInset - this.bottomInset);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  362 */     Container container = getContainer();
/*  363 */     Image image = getImage((container == null || container.isEnabled()));
/*  364 */     if (image != null) {
/*  365 */       if (!hasPixels(image))
/*      */       {
/*  367 */         Icon icon = getLoadingImageIcon();
/*  368 */         if (icon != null) {
/*  369 */           icon.paintIcon(container, paramGraphics, rectangle1.x + this.leftInset, rectangle1.y + this.topInset);
/*      */         
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  375 */         paramGraphics.drawImage(image, rectangle1.x + this.leftInset, rectangle1.y + this.topInset, this.width, this.height, this.imageObserver);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  380 */       Icon icon = getNoImageIcon();
/*  381 */       if (icon != null) {
/*  382 */         icon.paintIcon(container, paramGraphics, rectangle1.x + this.leftInset, rectangle1.y + this.topInset);
/*      */       }
/*      */       
/*  385 */       View view = getAltView();
/*      */       
/*  387 */       if (view != null && ((this.state & 0x4) == 0 || this.width > 38)) {
/*      */ 
/*      */         
/*  390 */         Rectangle rectangle = new Rectangle(rectangle1.x + this.leftInset + 38, rectangle1.y + this.topInset, rectangle1.width - this.leftInset - this.rightInset - 38, rectangle1.height - this.topInset - this.bottomInset);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  395 */         view.paint(paramGraphics, rectangle);
/*      */       } 
/*      */     } 
/*  398 */     if (rectangle2 != null)
/*      */     {
/*  400 */       paramGraphics.setClip(rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height);
/*      */     }
/*      */   }
/*      */   
/*      */   private void paintHighlights(Graphics paramGraphics, Shape paramShape) {
/*  405 */     if (this.container instanceof JTextComponent) {
/*  406 */       JTextComponent jTextComponent = (JTextComponent)this.container;
/*  407 */       Highlighter highlighter = jTextComponent.getHighlighter();
/*  408 */       if (highlighter instanceof LayeredHighlighter) {
/*  409 */         ((LayeredHighlighter)highlighter)
/*  410 */           .paintLayeredHighlights(paramGraphics, getStartOffset(), getEndOffset(), paramShape, jTextComponent, this);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void paintBorder(Graphics paramGraphics, Rectangle paramRectangle) {
/*  416 */     Color color = this.borderColor;
/*      */     
/*  418 */     if ((this.borderSize > 0 || this.image == null) && color != null) {
/*  419 */       int i = this.leftInset - this.borderSize;
/*  420 */       int j = this.topInset - this.borderSize;
/*  421 */       paramGraphics.setColor(color);
/*  422 */       byte b1 = (this.image == null) ? 1 : this.borderSize;
/*  423 */       for (byte b2 = 0; b2 < b1; b2++) {
/*  424 */         paramGraphics.drawRect(paramRectangle.x + i + b2, paramRectangle.y + j + b2, paramRectangle.width - b2 - b2 - i - i - 1, paramRectangle.height - b2 - b2 - j - j - 1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getPreferredSpan(int paramInt) {
/*  443 */     sync();
/*      */ 
/*      */     
/*  446 */     if (paramInt == 0 && (this.state & 0x4) == 4) {
/*  447 */       getPreferredSpanFromAltView(paramInt);
/*  448 */       return (this.width + this.leftInset + this.rightInset);
/*      */     } 
/*  450 */     if (paramInt == 1 && (this.state & 0x8) == 8) {
/*  451 */       getPreferredSpanFromAltView(paramInt);
/*  452 */       return (this.height + this.topInset + this.bottomInset);
/*      */     } 
/*      */     
/*  455 */     Image image = getImage();
/*      */     
/*  457 */     if (image != null) {
/*  458 */       switch (paramInt) {
/*      */         case 0:
/*  460 */           return (this.width + this.leftInset + this.rightInset);
/*      */         case 1:
/*  462 */           return (this.height + this.topInset + this.bottomInset);
/*      */       } 
/*  464 */       throw new IllegalArgumentException("Invalid axis: " + paramInt);
/*      */     } 
/*      */ 
/*      */     
/*  468 */     View view = getAltView();
/*  469 */     float f = 0.0F;
/*      */     
/*  471 */     if (view != null) {
/*  472 */       f = view.getPreferredSpan(paramInt);
/*      */     }
/*  474 */     switch (paramInt) {
/*      */       case 0:
/*  476 */         return f + (this.width + this.leftInset + this.rightInset);
/*      */       case 1:
/*  478 */         return f + (this.height + this.topInset + this.bottomInset);
/*      */     } 
/*  480 */     throw new IllegalArgumentException("Invalid axis: " + paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAlignment(int paramInt) {
/*  499 */     switch (paramInt) {
/*      */       case 1:
/*  501 */         return this.vAlign;
/*      */     } 
/*  503 */     return super.getAlignment(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape modelToView(int paramInt, Shape paramShape, Position.Bias paramBias) throws BadLocationException {
/*  519 */     int i = getStartOffset();
/*  520 */     int j = getEndOffset();
/*  521 */     if (paramInt >= i && paramInt <= j) {
/*  522 */       Rectangle rectangle = paramShape.getBounds();
/*  523 */       if (paramInt == j) {
/*  524 */         rectangle.x += rectangle.width;
/*      */       }
/*  526 */       rectangle.width = 0;
/*  527 */       return rectangle;
/*      */     } 
/*  529 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int viewToModel(float paramFloat1, float paramFloat2, Shape paramShape, Position.Bias[] paramArrayOfBias) {
/*  544 */     Rectangle rectangle = (Rectangle)paramShape;
/*  545 */     if (paramFloat1 < (rectangle.x + rectangle.width)) {
/*  546 */       paramArrayOfBias[0] = Position.Bias.Forward;
/*  547 */       return getStartOffset();
/*      */     } 
/*  549 */     paramArrayOfBias[0] = Position.Bias.Backward;
/*  550 */     return getEndOffset();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSize(float paramFloat1, float paramFloat2) {
/*  561 */     sync();
/*      */     
/*  563 */     if (getImage() == null) {
/*  564 */       View view = getAltView();
/*      */       
/*  566 */       if (view != null) {
/*  567 */         view.setSize(Math.max(0.0F, paramFloat1 - (38 + this.leftInset + this.rightInset)), 
/*  568 */             Math.max(0.0F, paramFloat2 - (this.topInset + this.bottomInset)));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLink() {
/*  577 */     return ((this.state & 0x2) == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasPixels(Image paramImage) {
/*  584 */     return (paramImage != null && paramImage
/*  585 */       .getHeight(this.imageObserver) > 0 && paramImage
/*  586 */       .getWidth(this.imageObserver) > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getPreferredSpanFromAltView(int paramInt) {
/*  594 */     if (getImage() == null) {
/*  595 */       View view = getAltView();
/*      */       
/*  597 */       if (view != null) {
/*  598 */         return view.getPreferredSpan(paramInt);
/*      */       }
/*      */     } 
/*  601 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void repaint(long paramLong) {
/*  609 */     if (this.container != null && this.fBounds != null) {
/*  610 */       this.container.repaint(paramLong, this.fBounds.x, this.fBounds.y, this.fBounds.width, this.fBounds.height);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getIntAttr(HTML.Attribute paramAttribute, int paramInt) {
/*  620 */     AttributeSet attributeSet = getElement().getAttributes();
/*  621 */     if (attributeSet.isDefined(paramAttribute)) {
/*      */       int i;
/*  623 */       String str = (String)attributeSet.getAttribute(paramAttribute);
/*  624 */       if (str == null) {
/*  625 */         i = paramInt;
/*      */       } else {
/*      */         
/*      */         try {
/*  629 */           i = Math.max(0, Integer.parseInt(str));
/*  630 */         } catch (NumberFormatException numberFormatException) {
/*  631 */           i = paramInt;
/*      */         } 
/*      */       } 
/*  634 */       return i;
/*      */     } 
/*  636 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sync() {
/*  643 */     int i = this.state;
/*  644 */     if ((i & 0x20) != 0) {
/*  645 */       refreshImage();
/*      */     }
/*  647 */     i = this.state;
/*  648 */     if ((i & 0x10) != 0) {
/*  649 */       synchronized (this) {
/*  650 */         this.state = (this.state | 0x10) ^ 0x10;
/*      */       } 
/*  652 */       setPropertiesFromAttributes();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void refreshImage() {
/*  662 */     synchronized (this) {
/*      */       
/*  664 */       this.state = (this.state | 0x1 | 0x20 | 0x4 | 0x8) ^ 0x2C;
/*      */ 
/*      */       
/*  667 */       this.image = null;
/*  668 */       this.width = this.height = 0;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  673 */       loadImage();
/*      */ 
/*      */       
/*  676 */       updateImageSize();
/*      */     } finally {
/*      */       
/*  679 */       synchronized (this) {
/*      */         
/*  681 */         this.state = (this.state | 0x1) ^ 0x1;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadImage() {
/*  691 */     URL uRL = getImageURL();
/*  692 */     Image image = null;
/*  693 */     if (uRL != null) {
/*      */       
/*  695 */       Dictionary dictionary = (Dictionary)getDocument().getProperty("imageCache");
/*  696 */       if (dictionary != null) {
/*  697 */         image = (Image)dictionary.get(uRL);
/*      */       } else {
/*      */         
/*  700 */         image = Toolkit.getDefaultToolkit().createImage(uRL);
/*  701 */         if (image != null && getLoadsSynchronously()) {
/*      */           
/*  703 */           ImageIcon imageIcon = new ImageIcon();
/*  704 */           imageIcon.setImage(image);
/*      */         } 
/*      */       } 
/*      */     } 
/*  708 */     this.image = image;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateImageSize() {
/*  716 */     int i = 0;
/*  717 */     int j = 0;
/*  718 */     int k = 0;
/*  719 */     Image image = getImage();
/*      */     
/*  721 */     if (image != null) {
/*  722 */       Element element = getElement();
/*  723 */       AttributeSet attributeSet = element.getAttributes();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  728 */       i = getIntAttr(HTML.Attribute.WIDTH, -1);
/*  729 */       if (i > 0) {
/*  730 */         k |= 0x4;
/*      */       }
/*  732 */       j = getIntAttr(HTML.Attribute.HEIGHT, -1);
/*  733 */       if (j > 0) {
/*  734 */         k |= 0x8;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  743 */       if (getLoadsSynchronously()) {
/*  744 */         Dimension dimension = adjustWidthHeight(this.image.getWidth(this.imageObserver), this.image
/*  745 */             .getHeight(this.imageObserver));
/*  746 */         i = dimension.width;
/*  747 */         j = dimension.height;
/*  748 */         k |= 0xC;
/*      */       } 
/*      */ 
/*      */       
/*  752 */       if ((k & 0xC) != 0) {
/*  753 */         Toolkit.getDefaultToolkit().prepareImage(image, i, j, this.imageObserver);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  758 */         Toolkit.getDefaultToolkit().prepareImage(image, -1, -1, this.imageObserver);
/*      */       } 
/*      */ 
/*      */       
/*  762 */       boolean bool = false;
/*  763 */       synchronized (this) {
/*      */ 
/*      */ 
/*      */         
/*  767 */         if (this.image != null) {
/*  768 */           if ((k & 0x4) == 4 || this.width == 0) {
/*  769 */             this.width = i;
/*      */           }
/*  771 */           if ((k & 0x8) == 8 || this.height == 0)
/*      */           {
/*  773 */             this.height = j;
/*      */           }
/*      */         } else {
/*      */           
/*  777 */           bool = true;
/*  778 */           if ((k & 0x4) == 4) {
/*  779 */             this.width = i;
/*      */           }
/*  781 */           if ((k & 0x8) == 8) {
/*  782 */             this.height = j;
/*      */           }
/*      */         } 
/*  785 */         this.state |= k;
/*  786 */         this.state = (this.state | 0x1) ^ 0x1;
/*      */       } 
/*  788 */       if (bool)
/*      */       {
/*  790 */         updateAltTextView();
/*      */       }
/*      */     } else {
/*      */       
/*  794 */       this.width = this.height = 38;
/*  795 */       updateAltTextView();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateAltTextView() {
/*  803 */     String str = getAltText();
/*      */     
/*  805 */     if (str != null) {
/*      */ 
/*      */       
/*  808 */       ImageLabelView imageLabelView = new ImageLabelView(getElement(), str);
/*  809 */       synchronized (this) {
/*  810 */         this.altView = imageLabelView;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private View getAltView() {
/*      */     View view;
/*  821 */     synchronized (this) {
/*  822 */       view = this.altView;
/*      */     } 
/*  824 */     if (view != null && view.getParent() == null) {
/*  825 */       view.setParent(getParent());
/*      */     }
/*  827 */     return view;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void safePreferenceChanged() {
/*  835 */     if (SwingUtilities.isEventDispatchThread()) {
/*  836 */       Document document = getDocument();
/*  837 */       if (document instanceof AbstractDocument) {
/*  838 */         ((AbstractDocument)document).readLock();
/*      */       }
/*  840 */       preferenceChanged(null, true, true);
/*  841 */       if (document instanceof AbstractDocument) {
/*  842 */         ((AbstractDocument)document).readUnlock();
/*      */       }
/*      */     } else {
/*      */       
/*  846 */       SwingUtilities.invokeLater(new Runnable() {
/*      */             public void run() {
/*  848 */               ImageView.this.safePreferenceChanged();
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   private Dimension adjustWidthHeight(int paramInt1, int paramInt2) {
/*  855 */     Dimension dimension = new Dimension();
/*  856 */     double d = 0.0D;
/*  857 */     int i = getIntAttr(HTML.Attribute.WIDTH, -1);
/*  858 */     int j = getIntAttr(HTML.Attribute.HEIGHT, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  865 */     if (i != -1 && j != -1) {
/*  866 */       paramInt1 = i;
/*  867 */       paramInt2 = j;
/*  868 */     } else if ((((i != -1) ? 1 : 0) ^ ((j != -1) ? 1 : 0)) != 0) {
/*  869 */       if (i <= 0) {
/*  870 */         d = j / paramInt2;
/*  871 */         paramInt1 = (int)(d * paramInt1);
/*  872 */         paramInt2 = j;
/*      */       } 
/*      */       
/*  875 */       if (j <= 0) {
/*  876 */         d = i / paramInt1;
/*  877 */         paramInt2 = (int)(d * paramInt2);
/*  878 */         paramInt1 = i;
/*      */       } 
/*      */     } 
/*      */     
/*  882 */     dimension.width = paramInt1;
/*  883 */     dimension.height = paramInt2;
/*      */     
/*  885 */     return dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class ImageHandler
/*      */     implements ImageObserver
/*      */   {
/*      */     private ImageHandler() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean imageUpdate(Image param1Image, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  900 */       if ((param1Image != ImageView.this.image && param1Image != ImageView.this.disabledImage) || ImageView.this
/*  901 */         .image == null || ImageView.this.getParent() == null)
/*      */       {
/*  903 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  907 */       if ((param1Int1 & 0xC0) != 0) {
/*  908 */         ImageView.this.repaint(0L);
/*  909 */         synchronized (ImageView.this) {
/*  910 */           if (ImageView.this.image == param1Image) {
/*      */ 
/*      */             
/*  913 */             ImageView.this.image = null;
/*  914 */             if ((ImageView.this.state & 0x4) != 4) {
/*  915 */               ImageView.this.width = 38;
/*      */             }
/*  917 */             if ((ImageView.this.state & 0x8) != 8) {
/*  918 */               ImageView.this.height = 38;
/*      */             }
/*      */           } else {
/*  921 */             ImageView.this.disabledImage = null;
/*      */           } 
/*  923 */           if ((ImageView.this.state & 0x1) == 1)
/*      */           {
/*      */             
/*  926 */             return false;
/*      */           }
/*      */         } 
/*  929 */         ImageView.this.updateAltTextView();
/*  930 */         ImageView.this.safePreferenceChanged();
/*  931 */         return false;
/*      */       } 
/*      */       
/*  934 */       if (ImageView.this.image == param1Image) {
/*      */         
/*  936 */         short s = 0;
/*  937 */         if ((param1Int1 & 0x2) != 0 && 
/*  938 */           !ImageView.this.getElement().getAttributes().isDefined(HTML.Attribute.HEIGHT)) {
/*  939 */           s = (short)(s | 0x1);
/*      */         }
/*  941 */         if ((param1Int1 & 0x1) != 0 && 
/*  942 */           !ImageView.this.getElement().getAttributes().isDefined(HTML.Attribute.WIDTH)) {
/*  943 */           s = (short)(s | 0x2);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  951 */         if ((param1Int1 & 0x2) != 0 && (param1Int1 & 0x1) != 0) {
/*      */           
/*  953 */           Dimension dimension = ImageView.this.adjustWidthHeight(param1Int4, param1Int5);
/*  954 */           param1Int4 = dimension.width;
/*  955 */           param1Int5 = dimension.height;
/*  956 */           s = (short)(s | 0x3);
/*      */         } 
/*  958 */         synchronized (ImageView.this) {
/*  959 */           if ((s & 0x1) == 1 && (ImageView.this.state & 0x8) == 0) {
/*  960 */             ImageView.this.height = param1Int5;
/*      */           }
/*  962 */           if ((s & 0x2) == 2 && (ImageView.this.state & 0x4) == 0) {
/*  963 */             ImageView.this.width = param1Int4;
/*      */           }
/*  965 */           if ((ImageView.this.state & 0x1) == 1)
/*      */           {
/*      */             
/*  968 */             return true;
/*      */           }
/*      */         } 
/*  971 */         if (s != 0) {
/*      */           
/*  973 */           ImageView.this.safePreferenceChanged();
/*  974 */           return true;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  979 */       if ((param1Int1 & 0x30) != 0) {
/*  980 */         ImageView.this.repaint(0L);
/*      */       }
/*  982 */       else if ((param1Int1 & 0x8) != 0 && ImageView.sIsInc) {
/*  983 */         ImageView.this.repaint(ImageView.sIncRate);
/*      */       } 
/*  985 */       return ((param1Int1 & 0x20) == 0);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class ImageLabelView
/*      */     extends InlineView
/*      */   {
/*      */     private Segment segment;
/*      */     
/*      */     private Color fg;
/*      */ 
/*      */     
/*      */     ImageLabelView(Element param1Element, String param1String) {
/* 1000 */       super(param1Element);
/* 1001 */       reset(param1String);
/*      */     }
/*      */     
/*      */     public void reset(String param1String) {
/* 1005 */       this.segment = new Segment(param1String.toCharArray(), 0, param1String.length());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paint(Graphics param1Graphics, Shape param1Shape) {
/* 1011 */       GlyphView.GlyphPainter glyphPainter = getGlyphPainter();
/*      */       
/* 1013 */       if (glyphPainter != null) {
/* 1014 */         param1Graphics.setColor(getForeground());
/* 1015 */         glyphPainter.paint(this, param1Graphics, param1Shape, getStartOffset(), getEndOffset());
/*      */       } 
/*      */     }
/*      */     
/*      */     public Segment getText(int param1Int1, int param1Int2) {
/* 1020 */       if (param1Int1 < 0 || param1Int2 > this.segment.array.length) {
/* 1021 */         throw new RuntimeException("ImageLabelView: Stale view");
/*      */       }
/* 1023 */       this.segment.offset = param1Int1;
/* 1024 */       this.segment.count = param1Int2 - param1Int1;
/* 1025 */       return this.segment;
/*      */     }
/*      */     
/*      */     public int getStartOffset() {
/* 1029 */       return 0;
/*      */     }
/*      */     
/*      */     public int getEndOffset() {
/* 1033 */       return this.segment.array.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public View breakView(int param1Int1, int param1Int2, float param1Float1, float param1Float2) {
/* 1038 */       return this;
/*      */     }
/*      */     
/*      */     public Color getForeground() {
/*      */       View view;
/* 1043 */       if (this.fg == null && (view = getParent()) != null) {
/* 1044 */         Document document = getDocument();
/* 1045 */         AttributeSet attributeSet = view.getAttributes();
/*      */         
/* 1047 */         if (attributeSet != null && document instanceof StyledDocument) {
/* 1048 */           this.fg = ((StyledDocument)document).getForeground(attributeSet);
/*      */         }
/*      */       } 
/* 1051 */       return this.fg;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/ImageView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */