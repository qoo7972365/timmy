/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.io.StringReader;
/*     */ import java.net.URL;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.Position;
/*     */ import javax.swing.text.StyleConstants;
/*     */ import javax.swing.text.View;
/*     */ import javax.swing.text.ViewFactory;
/*     */ import javax.swing.text.html.HTML;
/*     */ import javax.swing.text.html.HTMLDocument;
/*     */ import javax.swing.text.html.HTMLEditorKit;
/*     */ import javax.swing.text.html.ImageView;
/*     */ import javax.swing.text.html.StyleSheet;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicHTML
/*     */ {
/*     */   private static final String htmlDisable = "html.disable";
/*     */   public static final String propertyKey = "html";
/*     */   public static final String documentBaseKey = "html.base";
/*     */   private static BasicEditorKit basicHTMLFactory;
/*     */   private static ViewFactory basicHTMLViewFactory;
/*     */   private static final String styleChanges = "p { margin-top: 0; margin-bottom: 0; margin-left: 0; margin-right: 0 }body { margin-top: 0; margin-bottom: 0; margin-left: 0; margin-right: 0 }";
/*     */   
/*     */   public static View createHTMLView(JComponent paramJComponent, String paramString) {
/*  53 */     BasicEditorKit basicEditorKit = getFactory();
/*  54 */     Document document = basicEditorKit.createDefaultDocument(paramJComponent.getFont(), paramJComponent
/*  55 */         .getForeground());
/*  56 */     Object object = paramJComponent.getClientProperty("html.base");
/*  57 */     if (object instanceof URL) {
/*  58 */       ((HTMLDocument)document).setBase((URL)object);
/*     */     }
/*  60 */     StringReader stringReader = new StringReader(paramString);
/*     */     try {
/*  62 */       basicEditorKit.read(stringReader, document, 0);
/*  63 */     } catch (Throwable throwable) {}
/*     */     
/*  65 */     ViewFactory viewFactory = basicEditorKit.getViewFactory();
/*  66 */     View view = viewFactory.create(document.getDefaultRootElement());
/*  67 */     return new Renderer(paramJComponent, viewFactory, view);
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
/*     */   public static int getHTMLBaseline(View paramView, int paramInt1, int paramInt2) {
/*  85 */     if (paramInt1 < 0 || paramInt2 < 0) {
/*  86 */       throw new IllegalArgumentException("Width and height must be >= 0");
/*     */     }
/*     */     
/*  89 */     if (paramView instanceof Renderer) {
/*  90 */       return getBaseline(paramView.getView(0), paramInt1, paramInt2);
/*     */     }
/*  92 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 102 */     View view = (View)paramJComponent.getClientProperty("html");
/* 103 */     if (view != null) {
/* 104 */       int i = getHTMLBaseline(view, paramInt3, paramInt4);
/* 105 */       if (i < 0) {
/* 106 */         return i;
/*     */       }
/* 108 */       return paramInt1 + i;
/*     */     } 
/* 110 */     return paramInt1 + paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getBaseline(View paramView, int paramInt1, int paramInt2) {
/* 117 */     if (hasParagraph(paramView)) {
/* 118 */       paramView.setSize(paramInt1, paramInt2);
/* 119 */       return getBaseline(paramView, new Rectangle(0, 0, paramInt1, paramInt2));
/*     */     } 
/* 121 */     return -1;
/*     */   }
/*     */   
/*     */   private static int getBaseline(View paramView, Shape paramShape) {
/* 125 */     if (paramView.getViewCount() == 0) {
/* 126 */       return -1;
/*     */     }
/* 128 */     AttributeSet attributeSet = paramView.getElement().getAttributes();
/* 129 */     Object object = null;
/* 130 */     if (attributeSet != null) {
/* 131 */       object = attributeSet.getAttribute(StyleConstants.NameAttribute);
/*     */     }
/* 133 */     byte b = 0;
/* 134 */     if (object == HTML.Tag.HTML && paramView.getViewCount() > 1)
/*     */     {
/* 136 */       b++;
/*     */     }
/* 138 */     paramShape = paramView.getChildAllocation(b, paramShape);
/* 139 */     if (paramShape == null) {
/* 140 */       return -1;
/*     */     }
/* 142 */     View view = paramView.getView(b);
/* 143 */     if (paramView instanceof javax.swing.text.ParagraphView) {
/*     */       Rectangle rectangle;
/* 145 */       if (paramShape instanceof Rectangle) {
/* 146 */         rectangle = (Rectangle)paramShape;
/*     */       } else {
/*     */         
/* 149 */         rectangle = paramShape.getBounds();
/*     */       } 
/* 151 */       return rectangle.y + 
/* 152 */         (int)(rectangle.height * view.getAlignment(1));
/*     */     } 
/* 154 */     return getBaseline(view, paramShape);
/*     */   }
/*     */   
/*     */   private static boolean hasParagraph(View paramView) {
/* 158 */     if (paramView instanceof javax.swing.text.ParagraphView) {
/* 159 */       return true;
/*     */     }
/* 161 */     if (paramView.getViewCount() == 0) {
/* 162 */       return false;
/*     */     }
/* 164 */     AttributeSet attributeSet = paramView.getElement().getAttributes();
/* 165 */     Object object = null;
/* 166 */     if (attributeSet != null) {
/* 167 */       object = attributeSet.getAttribute(StyleConstants.NameAttribute);
/*     */     }
/* 169 */     boolean bool = false;
/* 170 */     if (object == HTML.Tag.HTML && paramView.getViewCount() > 1)
/*     */     {
/* 172 */       bool = true;
/*     */     }
/* 174 */     return hasParagraph(paramView.getView(bool));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isHTMLString(String paramString) {
/* 183 */     if (paramString != null && 
/* 184 */       paramString.length() >= 6 && paramString.charAt(0) == '<' && paramString.charAt(5) == '>') {
/* 185 */       String str = paramString.substring(1, 5);
/* 186 */       return str.equalsIgnoreCase("html");
/*     */     } 
/*     */     
/* 189 */     return false;
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
/*     */   public static void updateRenderer(JComponent paramJComponent, String paramString) {
/* 203 */     View view1 = null;
/* 204 */     View view2 = (View)paramJComponent.getClientProperty("html");
/* 205 */     Boolean bool = (Boolean)paramJComponent.getClientProperty("html.disable");
/* 206 */     if (bool != Boolean.TRUE && isHTMLString(paramString)) {
/* 207 */       view1 = createHTMLView(paramJComponent, paramString);
/*     */     }
/* 209 */     if (view1 != view2 && view2 != null) {
/* 210 */       for (byte b = 0; b < view2.getViewCount(); b++) {
/* 211 */         view2.getView(b).setParent(null);
/*     */       }
/*     */     }
/* 214 */     paramJComponent.putClientProperty("html", view1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static BasicEditorKit getFactory() {
/* 242 */     if (basicHTMLFactory == null) {
/* 243 */       basicHTMLViewFactory = new BasicHTMLViewFactory();
/* 244 */       basicHTMLFactory = new BasicEditorKit();
/*     */     } 
/* 246 */     return basicHTMLFactory;
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
/*     */   static class BasicEditorKit
/*     */     extends HTMLEditorKit
/*     */   {
/*     */     private static StyleSheet defaultStyles;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StyleSheet getStyleSheet() {
/* 287 */       if (defaultStyles == null) {
/* 288 */         defaultStyles = new StyleSheet();
/* 289 */         StringReader stringReader = new StringReader("p { margin-top: 0; margin-bottom: 0; margin-left: 0; margin-right: 0 }body { margin-top: 0; margin-bottom: 0; margin-left: 0; margin-right: 0 }");
/*     */         try {
/* 291 */           defaultStyles.loadRules(stringReader, null);
/* 292 */         } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */         
/* 296 */         stringReader.close();
/* 297 */         defaultStyles.addStyleSheet(super.getStyleSheet());
/*     */       } 
/* 299 */       return defaultStyles;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Document createDefaultDocument(Font param1Font, Color param1Color) {
/* 308 */       StyleSheet styleSheet1 = getStyleSheet();
/* 309 */       StyleSheet styleSheet2 = new StyleSheet();
/* 310 */       styleSheet2.addStyleSheet(styleSheet1);
/* 311 */       BasicHTML.BasicDocument basicDocument = new BasicHTML.BasicDocument(styleSheet2, param1Font, param1Color);
/* 312 */       basicDocument.setAsynchronousLoadPriority(2147483647);
/* 313 */       basicDocument.setPreservesUnknownTags(false);
/* 314 */       return basicDocument;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ViewFactory getViewFactory() {
/* 322 */       return BasicHTML.basicHTMLViewFactory;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class BasicHTMLViewFactory
/*     */     extends HTMLEditorKit.HTMLFactory
/*     */   {
/*     */     public View create(Element param1Element) {
/* 333 */       View view = super.create(param1Element);
/*     */       
/* 335 */       if (view instanceof ImageView) {
/* 336 */         ((ImageView)view).setLoadsSynchronously(true);
/*     */       }
/* 338 */       return view;
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
/*     */   static class BasicDocument
/*     */     extends HTMLDocument
/*     */   {
/*     */     BasicDocument(StyleSheet param1StyleSheet, Font param1Font, Color param1Color) {
/* 353 */       super(param1StyleSheet);
/* 354 */       setPreservesUnknownTags(false);
/* 355 */       setFontAndColor(param1Font, param1Color);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void setFontAndColor(Font param1Font, Color param1Color) {
/* 365 */       getStyleSheet().addRule(
/* 366 */           SwingUtilities2.displayPropertiesToCSS(param1Font, param1Color));
/*     */     }
/*     */   }
/*     */   
/*     */   static class Renderer extends View {
/*     */     private int width;
/*     */     private View view;
/*     */     private ViewFactory factory;
/*     */     private JComponent host;
/*     */     
/*     */     Renderer(JComponent param1JComponent, ViewFactory param1ViewFactory, View param1View) {
/* 377 */       super(null);
/* 378 */       this.host = param1JComponent;
/* 379 */       this.factory = param1ViewFactory;
/* 380 */       this.view = param1View;
/* 381 */       this.view.setParent(this);
/*     */       
/* 383 */       setSize(this.view.getPreferredSpan(0), this.view.getPreferredSpan(1));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AttributeSet getAttributes() {
/* 392 */       return null;
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
/*     */     public float getPreferredSpan(int param1Int) {
/* 405 */       if (param1Int == 0)
/*     */       {
/* 407 */         return this.width;
/*     */       }
/* 409 */       return this.view.getPreferredSpan(param1Int);
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
/*     */     public float getMinimumSpan(int param1Int) {
/* 422 */       return this.view.getMinimumSpan(param1Int);
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
/*     */     public float getMaximumSpan(int param1Int) {
/* 435 */       return 2.14748365E9F;
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
/*     */ 
/*     */ 
/*     */     
/*     */     public void preferenceChanged(View param1View, boolean param1Boolean1, boolean param1Boolean2) {
/* 457 */       this.host.revalidate();
/* 458 */       this.host.repaint();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getAlignment(int param1Int) {
/* 469 */       return this.view.getAlignment(param1Int);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paint(Graphics param1Graphics, Shape param1Shape) {
/* 479 */       Rectangle rectangle = param1Shape.getBounds();
/* 480 */       this.view.setSize(rectangle.width, rectangle.height);
/* 481 */       this.view.paint(param1Graphics, param1Shape);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setParent(View param1View) {
/* 490 */       throw new Error("Can't set parent on root view");
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
/*     */     public int getViewCount() {
/* 502 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public View getView(int param1Int) {
/* 512 */       return this.view;
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
/*     */     public Shape modelToView(int param1Int, Shape param1Shape, Position.Bias param1Bias) throws BadLocationException {
/* 524 */       return this.view.modelToView(param1Int, param1Shape, param1Bias);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Shape modelToView(int param1Int1, Position.Bias param1Bias1, int param1Int2, Position.Bias param1Bias2, Shape param1Shape) throws BadLocationException {
/* 548 */       return this.view.modelToView(param1Int1, param1Bias1, param1Int2, param1Bias2, param1Shape);
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
/*     */     public int viewToModel(float param1Float1, float param1Float2, Shape param1Shape, Position.Bias[] param1ArrayOfBias) {
/* 562 */       return this.view.viewToModel(param1Float1, param1Float2, param1Shape, param1ArrayOfBias);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Document getDocument() {
/* 571 */       return this.view.getDocument();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getStartOffset() {
/* 580 */       return this.view.getStartOffset();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getEndOffset() {
/* 589 */       return this.view.getEndOffset();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Element getElement() {
/* 598 */       return this.view.getElement();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setSize(float param1Float1, float param1Float2) {
/* 608 */       this.width = (int)param1Float1;
/* 609 */       this.view.setSize(param1Float1, param1Float2);
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
/*     */     public Container getContainer() {
/* 621 */       return this.host;
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
/*     */     public ViewFactory getViewFactory() {
/* 635 */       return this.factory;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicHTML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */