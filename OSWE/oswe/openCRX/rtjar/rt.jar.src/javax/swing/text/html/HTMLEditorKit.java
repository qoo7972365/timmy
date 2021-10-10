/*      */ package javax.swing.text.html;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.ComponentListener;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.StringReader;
/*      */ import java.io.Writer;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Enumeration;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleAction;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JEditorPane;
/*      */ import javax.swing.JViewport;
/*      */ import javax.swing.SizeRequirements;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.event.CaretEvent;
/*      */ import javax.swing.event.CaretListener;
/*      */ import javax.swing.event.HyperlinkEvent;
/*      */ import javax.swing.plaf.TextUI;
/*      */ import javax.swing.text.AbstractDocument;
/*      */ import javax.swing.text.AttributeSet;
/*      */ import javax.swing.text.BadLocationException;
/*      */ import javax.swing.text.BoxView;
/*      */ import javax.swing.text.ComponentView;
/*      */ import javax.swing.text.DefaultHighlighter;
/*      */ import javax.swing.text.Document;
/*      */ import javax.swing.text.EditorKit;
/*      */ import javax.swing.text.Element;
/*      */ import javax.swing.text.ElementIterator;
/*      */ import javax.swing.text.Highlighter;
/*      */ import javax.swing.text.IconView;
/*      */ import javax.swing.text.JTextComponent;
/*      */ import javax.swing.text.LabelView;
/*      */ import javax.swing.text.MutableAttributeSet;
/*      */ import javax.swing.text.Position;
/*      */ import javax.swing.text.StyleConstants;
/*      */ import javax.swing.text.StyledDocument;
/*      */ import javax.swing.text.StyledEditorKit;
/*      */ import javax.swing.text.TextAction;
/*      */ import javax.swing.text.View;
/*      */ import javax.swing.text.ViewFactory;
/*      */ import sun.awt.AppContext;
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
/*      */ public class HTMLEditorKit
/*      */   extends StyledEditorKit
/*      */   implements Accessible
/*      */ {
/*      */   private JEditorPane theEditor;
/*      */   public static final String DEFAULT_CSS = "default.css";
/*      */   private AccessibleContext accessibleContext;
/*      */   
/*      */   public String getContentType() {
/*  183 */     return "text/html";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ViewFactory getViewFactory() {
/*  194 */     return defaultFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document createDefaultDocument() {
/*  204 */     StyleSheet styleSheet1 = getStyleSheet();
/*  205 */     StyleSheet styleSheet2 = new StyleSheet();
/*      */     
/*  207 */     styleSheet2.addStyleSheet(styleSheet1);
/*      */     
/*  209 */     HTMLDocument hTMLDocument = new HTMLDocument(styleSheet2);
/*  210 */     hTMLDocument.setParser(getParser());
/*  211 */     hTMLDocument.setAsynchronousLoadPriority(4);
/*  212 */     hTMLDocument.setTokenThreshold(100);
/*  213 */     return hTMLDocument;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Parser ensureParser(HTMLDocument paramHTMLDocument) throws IOException {
/*  222 */     Parser parser = paramHTMLDocument.getParser();
/*  223 */     if (parser == null) {
/*  224 */       parser = getParser();
/*      */     }
/*  226 */     if (parser == null) {
/*  227 */       throw new IOException("Can't load parser");
/*      */     }
/*  229 */     return parser;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void read(Reader paramReader, Document paramDocument, int paramInt) throws IOException, BadLocationException {
/*  252 */     if (paramDocument instanceof HTMLDocument) {
/*  253 */       HTMLDocument hTMLDocument = (HTMLDocument)paramDocument;
/*  254 */       if (paramInt > paramDocument.getLength()) {
/*  255 */         throw new BadLocationException("Invalid location", paramInt);
/*      */       }
/*      */       
/*  258 */       Parser parser = ensureParser(hTMLDocument);
/*  259 */       ParserCallback parserCallback = hTMLDocument.getReader(paramInt);
/*  260 */       Boolean bool = (Boolean)paramDocument.getProperty("IgnoreCharsetDirective");
/*  261 */       parser.parse(paramReader, parserCallback, (bool == null) ? false : bool.booleanValue());
/*  262 */       parserCallback.flush();
/*      */     } else {
/*  264 */       super.read(paramReader, paramDocument, paramInt);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insertHTML(HTMLDocument paramHTMLDocument, int paramInt1, String paramString, int paramInt2, int paramInt3, HTML.Tag paramTag) throws BadLocationException, IOException {
/*  286 */     if (paramInt1 > paramHTMLDocument.getLength()) {
/*  287 */       throw new BadLocationException("Invalid location", paramInt1);
/*      */     }
/*      */     
/*  290 */     Parser parser = ensureParser(paramHTMLDocument);
/*  291 */     ParserCallback parserCallback = paramHTMLDocument.getReader(paramInt1, paramInt2, paramInt3, paramTag);
/*      */ 
/*      */     
/*  294 */     Boolean bool = (Boolean)paramHTMLDocument.getProperty("IgnoreCharsetDirective");
/*  295 */     parser.parse(new StringReader(paramString), parserCallback, (bool == null) ? false : bool
/*  296 */         .booleanValue());
/*  297 */     parserCallback.flush();
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
/*      */   public void write(Writer paramWriter, Document paramDocument, int paramInt1, int paramInt2) throws IOException, BadLocationException {
/*  316 */     if (paramDocument instanceof HTMLDocument) {
/*  317 */       HTMLWriter hTMLWriter = new HTMLWriter(paramWriter, (HTMLDocument)paramDocument, paramInt1, paramInt2);
/*  318 */       hTMLWriter.write();
/*  319 */     } else if (paramDocument instanceof StyledDocument) {
/*  320 */       MinimalHTMLWriter minimalHTMLWriter = new MinimalHTMLWriter(paramWriter, (StyledDocument)paramDocument, paramInt1, paramInt2);
/*  321 */       minimalHTMLWriter.write();
/*      */     } else {
/*  323 */       super.write(paramWriter, paramDocument, paramInt1, paramInt2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void install(JEditorPane paramJEditorPane) {
/*  334 */     paramJEditorPane.addMouseListener(this.linkHandler);
/*  335 */     paramJEditorPane.addMouseMotionListener(this.linkHandler);
/*  336 */     paramJEditorPane.addCaretListener(nextLinkAction);
/*  337 */     super.install(paramJEditorPane);
/*  338 */     this.theEditor = paramJEditorPane;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deinstall(JEditorPane paramJEditorPane) {
/*  349 */     paramJEditorPane.removeMouseListener(this.linkHandler);
/*  350 */     paramJEditorPane.removeMouseMotionListener(this.linkHandler);
/*  351 */     paramJEditorPane.removeCaretListener(nextLinkAction);
/*  352 */     super.deinstall(paramJEditorPane);
/*  353 */     this.theEditor = null;
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
/*      */   
/*      */   public void setStyleSheet(StyleSheet paramStyleSheet) {
/*  373 */     if (paramStyleSheet == null) {
/*  374 */       AppContext.getAppContext().remove(DEFAULT_STYLES_KEY);
/*      */     } else {
/*  376 */       AppContext.getAppContext().put(DEFAULT_STYLES_KEY, paramStyleSheet);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StyleSheet getStyleSheet() {
/*  387 */     AppContext appContext = AppContext.getAppContext();
/*  388 */     StyleSheet styleSheet = (StyleSheet)appContext.get(DEFAULT_STYLES_KEY);
/*      */     
/*  390 */     if (styleSheet == null) {
/*  391 */       styleSheet = new StyleSheet();
/*  392 */       appContext.put(DEFAULT_STYLES_KEY, styleSheet);
/*      */       try {
/*  394 */         InputStream inputStream = getResourceAsStream("default.css");
/*  395 */         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
/*      */         
/*  397 */         styleSheet.loadRules(bufferedReader, (URL)null);
/*  398 */         bufferedReader.close();
/*  399 */       } catch (Throwable throwable) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  404 */     return styleSheet;
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
/*      */   static InputStream getResourceAsStream(final String name) {
/*  418 */     return AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>()
/*      */         {
/*      */           public InputStream run() {
/*  421 */             return HTMLEditorKit.class.getResourceAsStream(name);
/*      */           }
/*      */         });
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
/*      */   public Action[] getActions() {
/*  435 */     this; return TextAction.augmentList(super.getActions(), defaultActions);
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
/*      */   protected void createInputAttributes(Element paramElement, MutableAttributeSet paramMutableAttributeSet) {
/*  449 */     paramMutableAttributeSet.removeAttributes(paramMutableAttributeSet);
/*  450 */     paramMutableAttributeSet.addAttributes(paramElement.getAttributes());
/*  451 */     paramMutableAttributeSet.removeAttribute(StyleConstants.ComposedTextAttribute);
/*      */     
/*  453 */     Object object = paramMutableAttributeSet.getAttribute(StyleConstants.NameAttribute);
/*  454 */     if (object instanceof HTML.Tag) {
/*  455 */       HTML.Tag tag = (HTML.Tag)object;
/*      */ 
/*      */       
/*  458 */       if (tag == HTML.Tag.IMG) {
/*      */         
/*  460 */         paramMutableAttributeSet.removeAttribute(HTML.Attribute.SRC);
/*  461 */         paramMutableAttributeSet.removeAttribute(HTML.Attribute.HEIGHT);
/*  462 */         paramMutableAttributeSet.removeAttribute(HTML.Attribute.WIDTH);
/*  463 */         paramMutableAttributeSet.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
/*      */       
/*      */       }
/*  466 */       else if (tag == HTML.Tag.HR || tag == HTML.Tag.BR) {
/*      */         
/*  468 */         paramMutableAttributeSet.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
/*      */       
/*      */       }
/*  471 */       else if (tag == HTML.Tag.COMMENT) {
/*      */         
/*  473 */         paramMutableAttributeSet.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
/*      */         
/*  475 */         paramMutableAttributeSet.removeAttribute(HTML.Attribute.COMMENT);
/*      */       }
/*  477 */       else if (tag == HTML.Tag.INPUT) {
/*      */         
/*  479 */         paramMutableAttributeSet.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
/*      */         
/*  481 */         paramMutableAttributeSet.removeAttribute(HTML.Tag.INPUT);
/*      */       }
/*  483 */       else if (tag instanceof HTML.UnknownTag) {
/*      */         
/*  485 */         paramMutableAttributeSet.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
/*      */         
/*  487 */         paramMutableAttributeSet.removeAttribute(HTML.Attribute.ENDTAG);
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
/*      */   public MutableAttributeSet getInputAttributes() {
/*  499 */     if (this.input == null) {
/*  500 */       this.input = getStyleSheet().addStyle(null, null);
/*      */     }
/*  502 */     return this.input;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultCursor(Cursor paramCursor) {
/*  511 */     this.defaultCursor = paramCursor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Cursor getDefaultCursor() {
/*  520 */     return this.defaultCursor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLinkCursor(Cursor paramCursor) {
/*  529 */     this.linkCursor = paramCursor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Cursor getLinkCursor() {
/*  537 */     return this.linkCursor;
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
/*      */   public boolean isAutoFormSubmission() {
/*  551 */     return this.isAutoFormSubmission;
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
/*      */   public void setAutoFormSubmission(boolean paramBoolean) {
/*  564 */     this.isAutoFormSubmission = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*  573 */     HTMLEditorKit hTMLEditorKit = (HTMLEditorKit)super.clone();
/*  574 */     if (hTMLEditorKit != null) {
/*  575 */       hTMLEditorKit.input = null;
/*  576 */       hTMLEditorKit.linkHandler = new LinkController();
/*      */     } 
/*  578 */     return hTMLEditorKit;
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
/*      */   protected Parser getParser() {
/*  590 */     if (defaultParser == null) {
/*      */       try {
/*  592 */         Class<?> clazz = Class.forName("javax.swing.text.html.parser.ParserDelegator");
/*  593 */         defaultParser = (Parser)clazz.newInstance();
/*  594 */       } catch (Throwable throwable) {}
/*      */     }
/*      */     
/*  597 */     return defaultParser;
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
/*      */   public AccessibleContext getAccessibleContext() {
/*  610 */     if (this.theEditor == null) {
/*  611 */       return null;
/*      */     }
/*  613 */     if (this.accessibleContext == null) {
/*  614 */       AccessibleHTML accessibleHTML = new AccessibleHTML(this.theEditor);
/*  615 */       this.accessibleContext = accessibleHTML.getAccessibleContext();
/*      */     } 
/*  617 */     return this.accessibleContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  623 */   private static final Cursor MoveCursor = Cursor.getPredefinedCursor(12);
/*      */   
/*  625 */   private static final Cursor DefaultCursor = Cursor.getPredefinedCursor(0);
/*      */ 
/*      */   
/*  628 */   private static final ViewFactory defaultFactory = new HTMLFactory();
/*      */   
/*      */   MutableAttributeSet input;
/*  631 */   private static final Object DEFAULT_STYLES_KEY = new Object();
/*  632 */   private LinkController linkHandler = new LinkController();
/*  633 */   private static Parser defaultParser = null;
/*  634 */   private Cursor defaultCursor = DefaultCursor; private boolean isAutoFormSubmission = true; public static final String BOLD_ACTION = "html-bold-action"; public static final String ITALIC_ACTION = "html-italic-action"; public static final String PARA_INDENT_LEFT = "html-para-indent-left"; public static final String PARA_INDENT_RIGHT = "html-para-indent-right"; public static final String FONT_CHANGE_BIGGER = "html-font-bigger"; public static final String FONT_CHANGE_SMALLER = "html-font-smaller"; public static final String COLOR_ACTION = "html-color-action"; public static final String LOGICAL_STYLE_ACTION = "html-logical-style-action";
/*  635 */   private Cursor linkCursor = MoveCursor; public static final String IMG_ALIGN_TOP = "html-image-align-top"; public static final String IMG_ALIGN_MIDDLE = "html-image-align-middle"; public static final String IMG_ALIGN_BOTTOM = "html-image-align-bottom";
/*      */   public static final String IMG_BORDER = "html-image-border";
/*      */   private static final String INSERT_TABLE_HTML = "<table border=1><tr><td></td></tr></table>";
/*      */   private static final String INSERT_UL_HTML = "<ul><li></li></ul>";
/*      */   private static final String INSERT_OL_HTML = "<ol><li></li></ol>";
/*      */   private static final String INSERT_HR_HTML = "<hr>";
/*      */   private static final String INSERT_PRE_HTML = "<pre></pre>";
/*      */   
/*  643 */   public static class LinkController extends MouseAdapter implements MouseMotionListener, Serializable { private Element curElem = null;
/*      */ 
/*      */     
/*      */     private boolean curElemImage = false;
/*      */     
/*  648 */     private String href = null;
/*      */ 
/*      */     
/*  651 */     private transient Position.Bias[] bias = new Position.Bias[1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int curOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {
/*  667 */       JEditorPane jEditorPane = (JEditorPane)param1MouseEvent.getSource();
/*      */       
/*  669 */       if (!jEditorPane.isEditable() && jEditorPane.isEnabled() && 
/*  670 */         SwingUtilities.isLeftMouseButton(param1MouseEvent)) {
/*  671 */         Point point = new Point(param1MouseEvent.getX(), param1MouseEvent.getY());
/*  672 */         int i = jEditorPane.viewToModel(point);
/*  673 */         if (i >= 0) {
/*  674 */           activateLink(i, jEditorPane, param1MouseEvent);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {
/*  685 */       JEditorPane jEditorPane = (JEditorPane)param1MouseEvent.getSource();
/*  686 */       if (!jEditorPane.isEnabled()) {
/*      */         return;
/*      */       }
/*      */       
/*  690 */       HTMLEditorKit hTMLEditorKit = (HTMLEditorKit)jEditorPane.getEditorKit();
/*  691 */       boolean bool = true;
/*  692 */       Cursor cursor = hTMLEditorKit.getDefaultCursor();
/*  693 */       if (!jEditorPane.isEditable()) {
/*  694 */         Point point = new Point(param1MouseEvent.getX(), param1MouseEvent.getY());
/*  695 */         int i = jEditorPane.getUI().viewToModel(jEditorPane, point, this.bias);
/*  696 */         if (this.bias[0] == Position.Bias.Backward && i > 0) {
/*  697 */           i--;
/*      */         }
/*  699 */         if (i >= 0 && jEditorPane.getDocument() instanceof HTMLDocument) {
/*  700 */           HTMLDocument hTMLDocument = (HTMLDocument)jEditorPane.getDocument();
/*  701 */           Element element = hTMLDocument.getCharacterElement(i);
/*  702 */           if (!doesElementContainLocation(jEditorPane, element, i, param1MouseEvent
/*  703 */               .getX(), param1MouseEvent.getY())) {
/*  704 */             element = null;
/*      */           }
/*  706 */           if (this.curElem != element || this.curElemImage) {
/*  707 */             Element element1 = this.curElem;
/*  708 */             this.curElem = element;
/*  709 */             String str = null;
/*  710 */             this.curElemImage = false;
/*  711 */             if (element != null) {
/*  712 */               AttributeSet attributeSet1 = element.getAttributes();
/*      */               
/*  714 */               AttributeSet attributeSet2 = (AttributeSet)attributeSet1.getAttribute(HTML.Tag.A);
/*  715 */               if (attributeSet2 == null) {
/*  716 */                 this.curElemImage = (attributeSet1.getAttribute(StyleConstants.NameAttribute) == HTML.Tag.IMG);
/*      */                 
/*  718 */                 if (this.curElemImage) {
/*  719 */                   str = getMapHREF(jEditorPane, hTMLDocument, element, attributeSet1, i, param1MouseEvent
/*  720 */                       .getX(), param1MouseEvent.getY());
/*      */                 }
/*      */               }
/*      */               else {
/*      */                 
/*  725 */                 str = (String)attributeSet2.getAttribute(HTML.Attribute.HREF);
/*      */               } 
/*      */             } 
/*      */             
/*  729 */             if (str != this.href) {
/*      */               
/*  731 */               fireEvents(jEditorPane, hTMLDocument, str, element1, param1MouseEvent);
/*  732 */               this.href = str;
/*  733 */               if (str != null) {
/*  734 */                 cursor = hTMLEditorKit.getLinkCursor();
/*      */               }
/*      */             } else {
/*      */               
/*  738 */               bool = false;
/*      */             } 
/*      */           } else {
/*      */             
/*  742 */             bool = false;
/*      */           } 
/*  744 */           this.curOffset = i;
/*      */         } 
/*      */       } 
/*  747 */       if (bool && jEditorPane.getCursor() != cursor) {
/*  748 */         jEditorPane.setCursor(cursor);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String getMapHREF(JEditorPane param1JEditorPane, HTMLDocument param1HTMLDocument, Element param1Element, AttributeSet param1AttributeSet, int param1Int1, int param1Int2, int param1Int3) {
/*  759 */       Object object = param1AttributeSet.getAttribute(HTML.Attribute.USEMAP);
/*  760 */       if (object != null && object instanceof String) {
/*  761 */         Map map = param1HTMLDocument.getMap((String)object);
/*  762 */         if (map != null && param1Int1 < param1HTMLDocument.getLength()) {
/*      */           Object object1;
/*  764 */           TextUI textUI = param1JEditorPane.getUI();
/*      */           try {
/*  766 */             Rectangle rectangle1 = textUI.modelToView(param1JEditorPane, param1Int1, Position.Bias.Forward);
/*      */             
/*  768 */             Rectangle rectangle2 = textUI.modelToView(param1JEditorPane, param1Int1 + 1, Position.Bias.Backward);
/*      */             
/*  770 */             object1 = rectangle1.getBounds();
/*  771 */             object1.add((rectangle2 instanceof Rectangle) ? rectangle2 : rectangle2
/*  772 */                 .getBounds());
/*  773 */           } catch (BadLocationException badLocationException) {
/*  774 */             object1 = null;
/*      */           } 
/*  776 */           if (object1 != null) {
/*  777 */             AttributeSet attributeSet = map.getArea(param1Int2 - ((Rectangle)object1).x, param1Int3 - ((Rectangle)object1).y, ((Rectangle)object1).width, ((Rectangle)object1).height);
/*      */ 
/*      */ 
/*      */             
/*  781 */             if (attributeSet != null) {
/*  782 */               return (String)attributeSet.getAttribute(HTML.Attribute.HREF);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  788 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean doesElementContainLocation(JEditorPane param1JEditorPane, Element param1Element, int param1Int1, int param1Int2, int param1Int3) {
/*  799 */       if (param1Element != null && param1Int1 > 0 && param1Element.getStartOffset() == param1Int1) {
/*      */         try {
/*  801 */           TextUI textUI = param1JEditorPane.getUI();
/*  802 */           Rectangle rectangle1 = textUI.modelToView(param1JEditorPane, param1Int1, Position.Bias.Forward);
/*      */           
/*  804 */           if (rectangle1 == null) {
/*  805 */             return false;
/*      */           }
/*      */           
/*  808 */           Rectangle rectangle2 = (rectangle1 instanceof Rectangle) ? rectangle1 : rectangle1.getBounds();
/*  809 */           Rectangle rectangle3 = textUI.modelToView(param1JEditorPane, param1Element.getEndOffset(), Position.Bias.Backward);
/*      */           
/*  811 */           if (rectangle3 != null) {
/*      */             
/*  813 */             Rectangle rectangle = (rectangle3 instanceof Rectangle) ? rectangle3 : rectangle3.getBounds();
/*  814 */             rectangle2.add(rectangle);
/*      */           } 
/*  816 */           return rectangle2.contains(param1Int2, param1Int3);
/*  817 */         } catch (BadLocationException badLocationException) {}
/*      */       }
/*      */       
/*  820 */       return true;
/*      */     }
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
/*      */     protected void activateLink(int param1Int, JEditorPane param1JEditorPane) {
/*  833 */       activateLink(param1Int, param1JEditorPane, null);
/*      */     }
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
/*      */     void activateLink(int param1Int, JEditorPane param1JEditorPane, MouseEvent param1MouseEvent) {
/*  847 */       Document document = param1JEditorPane.getDocument();
/*  848 */       if (document instanceof HTMLDocument) {
/*  849 */         HTMLDocument hTMLDocument = (HTMLDocument)document;
/*  850 */         Element element = hTMLDocument.getCharacterElement(param1Int);
/*  851 */         AttributeSet attributeSet1 = element.getAttributes();
/*  852 */         AttributeSet attributeSet2 = (AttributeSet)attributeSet1.getAttribute(HTML.Tag.A);
/*  853 */         HyperlinkEvent hyperlinkEvent = null;
/*      */         
/*  855 */         int i = -1;
/*  856 */         int j = -1;
/*      */         
/*  858 */         if (param1MouseEvent != null) {
/*  859 */           i = param1MouseEvent.getX();
/*  860 */           j = param1MouseEvent.getY();
/*      */         } 
/*      */         
/*  863 */         if (attributeSet2 == null) {
/*  864 */           this.href = getMapHREF(param1JEditorPane, hTMLDocument, element, attributeSet1, param1Int, i, j);
/*      */         } else {
/*      */           
/*  867 */           this.href = (String)attributeSet2.getAttribute(HTML.Attribute.HREF);
/*      */         } 
/*      */         
/*  870 */         if (this.href != null) {
/*  871 */           hyperlinkEvent = createHyperlinkEvent(param1JEditorPane, hTMLDocument, this.href, attributeSet2, element, param1MouseEvent);
/*      */         }
/*      */         
/*  874 */         if (hyperlinkEvent != null) {
/*  875 */           param1JEditorPane.fireHyperlinkUpdate(hyperlinkEvent);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     HyperlinkEvent createHyperlinkEvent(JEditorPane param1JEditorPane, HTMLDocument param1HTMLDocument, String param1String, AttributeSet param1AttributeSet, Element param1Element, MouseEvent param1MouseEvent) {
/*      */       URL uRL;
/*      */       HyperlinkEvent hyperlinkEvent;
/*      */       try {
/*  892 */         URL uRL1 = param1HTMLDocument.getBase();
/*  893 */         uRL = new URL(uRL1, param1String);
/*      */ 
/*      */ 
/*      */         
/*  897 */         if (param1String != null && "file".equals(uRL.getProtocol()) && param1String
/*  898 */           .startsWith("#")) {
/*  899 */           String str1 = uRL1.getFile();
/*  900 */           String str2 = uRL.getFile();
/*  901 */           if (str1 != null && str2 != null && 
/*  902 */             !str2.startsWith(str1)) {
/*  903 */             uRL = new URL(uRL1, str1 + param1String);
/*      */           }
/*      */         } 
/*  906 */       } catch (MalformedURLException malformedURLException) {
/*  907 */         uRL = null;
/*      */       } 
/*      */ 
/*      */       
/*  911 */       if (!param1HTMLDocument.isFrameDocument()) {
/*  912 */         hyperlinkEvent = new HyperlinkEvent(param1JEditorPane, HyperlinkEvent.EventType.ACTIVATED, uRL, param1String, param1Element, param1MouseEvent);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  917 */         String str = (param1AttributeSet != null) ? (String)param1AttributeSet.getAttribute(HTML.Attribute.TARGET) : null;
/*  918 */         if (str == null || str.equals("")) {
/*  919 */           str = param1HTMLDocument.getBaseTarget();
/*      */         }
/*  921 */         if (str == null || str.equals("")) {
/*  922 */           str = "_self";
/*      */         }
/*  924 */         hyperlinkEvent = new HTMLFrameHyperlinkEvent(param1JEditorPane, HyperlinkEvent.EventType.ACTIVATED, uRL, param1String, param1Element, param1MouseEvent, str);
/*      */       } 
/*      */ 
/*      */       
/*  928 */       return hyperlinkEvent;
/*      */     }
/*      */ 
/*      */     
/*      */     void fireEvents(JEditorPane param1JEditorPane, HTMLDocument param1HTMLDocument, String param1String, Element param1Element, MouseEvent param1MouseEvent) {
/*  933 */       if (this.href != null) {
/*      */         URL uRL;
/*      */         
/*      */         try {
/*  937 */           uRL = new URL(param1HTMLDocument.getBase(), this.href);
/*  938 */         } catch (MalformedURLException malformedURLException) {
/*  939 */           uRL = null;
/*      */         } 
/*  941 */         HyperlinkEvent hyperlinkEvent = new HyperlinkEvent(param1JEditorPane, HyperlinkEvent.EventType.EXITED, uRL, this.href, param1Element, param1MouseEvent);
/*      */ 
/*      */         
/*  944 */         param1JEditorPane.fireHyperlinkUpdate(hyperlinkEvent);
/*      */       } 
/*  946 */       if (param1String != null) {
/*      */         URL uRL;
/*      */         
/*      */         try {
/*  950 */           uRL = new URL(param1HTMLDocument.getBase(), param1String);
/*  951 */         } catch (MalformedURLException malformedURLException) {
/*  952 */           uRL = null;
/*      */         } 
/*  954 */         HyperlinkEvent hyperlinkEvent = new HyperlinkEvent(param1JEditorPane, HyperlinkEvent.EventType.ENTERED, uRL, param1String, this.curElem, param1MouseEvent);
/*      */ 
/*      */         
/*  957 */         param1JEditorPane.fireHyperlinkUpdate(hyperlinkEvent);
/*      */       } 
/*      */     } }
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
/*      */   public static abstract class Parser
/*      */   {
/*      */     public abstract void parse(Reader param1Reader, HTMLEditorKit.ParserCallback param1ParserCallback, boolean param1Boolean) throws IOException;
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
/*      */   public static class ParserCallback
/*      */   {
/*  997 */     public static final Object IMPLIED = "_implied_";
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
/*      */     public void flush() throws BadLocationException {}
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
/*      */     public void handleText(char[] param1ArrayOfchar, int param1Int) {}
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
/*      */     public void handleComment(char[] param1ArrayOfchar, int param1Int) {}
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
/*      */     public void handleStartTag(HTML.Tag param1Tag, MutableAttributeSet param1MutableAttributeSet, int param1Int) {}
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
/*      */     public void handleEndTag(HTML.Tag param1Tag, int param1Int) {}
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
/*      */     public void handleSimpleTag(HTML.Tag param1Tag, MutableAttributeSet param1MutableAttributeSet, int param1Int) {}
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
/*      */     public void handleError(String param1String, int param1Int) {}
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
/*      */     public void handleEndOfLineString(String param1String) {}
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
/*      */   public static class HTMLFactory
/*      */     implements ViewFactory
/*      */   {
/*      */     public View create(Element param1Element) {
/* 1123 */       AttributeSet attributeSet = param1Element.getAttributes();
/*      */       
/* 1125 */       Object object1 = attributeSet.getAttribute("$ename");
/*      */       
/* 1127 */       Object object2 = (object1 != null) ? null : attributeSet.getAttribute(StyleConstants.NameAttribute);
/* 1128 */       if (object2 instanceof HTML.Tag) {
/* 1129 */         HTML.Tag tag = (HTML.Tag)object2;
/* 1130 */         if (tag == HTML.Tag.CONTENT)
/* 1131 */           return new InlineView(param1Element); 
/* 1132 */         if (tag == HTML.Tag.IMPLIED) {
/* 1133 */           String str1 = (String)param1Element.getAttributes().getAttribute(CSS.Attribute.WHITE_SPACE);
/*      */           
/* 1135 */           if (str1 != null && str1.equals("pre")) {
/* 1136 */             return new LineView(param1Element);
/*      */           }
/* 1138 */           return new ParagraphView(param1Element);
/* 1139 */         }  if (tag == HTML.Tag.P || tag == HTML.Tag.H1 || tag == HTML.Tag.H2 || tag == HTML.Tag.H3 || tag == HTML.Tag.H4 || tag == HTML.Tag.H5 || tag == HTML.Tag.H6 || tag == HTML.Tag.DT)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1148 */           return new ParagraphView(param1Element); } 
/* 1149 */         if (tag == HTML.Tag.MENU || tag == HTML.Tag.DIR || tag == HTML.Tag.UL || tag == HTML.Tag.OL)
/*      */         {
/*      */ 
/*      */           
/* 1153 */           return new ListView(param1Element); } 
/* 1154 */         if (tag == HTML.Tag.BODY)
/* 1155 */           return new BodyBlockView(param1Element); 
/* 1156 */         if (tag == HTML.Tag.HTML)
/* 1157 */           return new BlockView(param1Element, 1); 
/* 1158 */         if (tag == HTML.Tag.LI || tag == HTML.Tag.CENTER || tag == HTML.Tag.DL || tag == HTML.Tag.DD || tag == HTML.Tag.DIV || tag == HTML.Tag.BLOCKQUOTE || tag == HTML.Tag.PRE || tag == HTML.Tag.FORM)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1167 */           return new BlockView(param1Element, 1); } 
/* 1168 */         if (tag == HTML.Tag.NOFRAMES)
/* 1169 */           return new NoFramesView(param1Element, 1); 
/* 1170 */         if (tag == HTML.Tag.IMG)
/* 1171 */           return new ImageView(param1Element); 
/* 1172 */         if (tag == HTML.Tag.ISINDEX)
/* 1173 */           return new IsindexView(param1Element); 
/* 1174 */         if (tag == HTML.Tag.HR)
/* 1175 */           return new HRuleView(param1Element); 
/* 1176 */         if (tag == HTML.Tag.BR)
/* 1177 */           return new BRView(param1Element); 
/* 1178 */         if (tag == HTML.Tag.TABLE)
/* 1179 */           return new TableView(param1Element); 
/* 1180 */         if (tag == HTML.Tag.INPUT || tag == HTML.Tag.SELECT || tag == HTML.Tag.TEXTAREA)
/*      */         {
/*      */           
/* 1183 */           return new FormView(param1Element); } 
/* 1184 */         if (tag == HTML.Tag.OBJECT)
/* 1185 */           return new ObjectView(param1Element); 
/* 1186 */         if (tag == HTML.Tag.FRAMESET) {
/* 1187 */           if (param1Element.getAttributes().isDefined(HTML.Attribute.ROWS))
/* 1188 */             return new FrameSetView(param1Element, 1); 
/* 1189 */           if (param1Element.getAttributes().isDefined(HTML.Attribute.COLS)) {
/* 1190 */             return new FrameSetView(param1Element, 0);
/*      */           }
/* 1192 */           throw new RuntimeException("Can't build a" + tag + ", " + param1Element + ":no ROWS or COLS defined.");
/*      */         } 
/* 1194 */         if (tag == HTML.Tag.FRAME)
/* 1195 */           return new FrameView(param1Element); 
/* 1196 */         if (tag instanceof HTML.UnknownTag)
/* 1197 */           return new HiddenTagView(param1Element); 
/* 1198 */         if (tag == HTML.Tag.COMMENT)
/* 1199 */           return new CommentView(param1Element); 
/* 1200 */         if (tag == HTML.Tag.HEAD)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1205 */           return new BlockView(param1Element, 0) {
/*      */               public float getPreferredSpan(int param2Int) {
/* 1207 */                 return 0.0F;
/*      */               }
/*      */               public float getMinimumSpan(int param2Int) {
/* 1210 */                 return 0.0F;
/*      */               }
/*      */               public float getMaximumSpan(int param2Int) {
/* 1213 */                 return 0.0F;
/*      */               }
/*      */               
/*      */               protected void loadChildren(ViewFactory param2ViewFactory) {}
/*      */               
/*      */               public Shape modelToView(int param2Int, Shape param2Shape, Position.Bias param2Bias) throws BadLocationException {
/* 1219 */                 return param2Shape;
/*      */               }
/*      */ 
/*      */               
/*      */               public int getNextVisualPositionFrom(int param2Int1, Position.Bias param2Bias, Shape param2Shape, int param2Int2, Position.Bias[] param2ArrayOfBias) {
/* 1224 */                 return getElement().getEndOffset();
/*      */               }
/*      */             }; } 
/* 1227 */         if (tag == HTML.Tag.TITLE || tag == HTML.Tag.META || tag == HTML.Tag.LINK || tag == HTML.Tag.STYLE || tag == HTML.Tag.SCRIPT || tag == HTML.Tag.AREA || tag == HTML.Tag.MAP || tag == HTML.Tag.PARAM || tag == HTML.Tag.APPLET)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1236 */           return new HiddenTagView(param1Element);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1242 */       String str = (object1 != null) ? (String)object1 : param1Element.getName();
/* 1243 */       if (str != null) {
/* 1244 */         if (str.equals("content"))
/* 1245 */           return new LabelView(param1Element); 
/* 1246 */         if (str.equals("paragraph"))
/* 1247 */           return new ParagraphView(param1Element); 
/* 1248 */         if (str.equals("section"))
/* 1249 */           return new BoxView(param1Element, 1); 
/* 1250 */         if (str.equals("component"))
/* 1251 */           return new ComponentView(param1Element); 
/* 1252 */         if (str.equals("icon")) {
/* 1253 */           return new IconView(param1Element);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1258 */       return new LabelView(param1Element);
/*      */     }
/*      */     static class BodyBlockView extends BlockView implements ComponentListener { private Reference<JViewport> cachedViewPort; private boolean isListening; private int viewVisibleWidth; private int componentVisibleWidth;
/*      */       
/*      */       public BodyBlockView(Element param2Element) {
/* 1263 */         super(param2Element, 1);
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
/* 1362 */         this.cachedViewPort = null;
/* 1363 */         this.isListening = false;
/* 1364 */         this.viewVisibleWidth = Integer.MAX_VALUE;
/* 1365 */         this.componentVisibleWidth = Integer.MAX_VALUE;
/*      */       }
/*      */       protected SizeRequirements calculateMajorAxisRequirements(int param2Int, SizeRequirements param2SizeRequirements) {
/*      */         param2SizeRequirements = super.calculateMajorAxisRequirements(param2Int, param2SizeRequirements);
/*      */         param2SizeRequirements.maximum = Integer.MAX_VALUE;
/*      */         return param2SizeRequirements;
/*      */       }
/*      */       protected void layoutMinorAxis(int param2Int1, int param2Int2, int[] param2ArrayOfint1, int[] param2ArrayOfint2) {
/*      */         Container container1 = getContainer();
/*      */         Container container2;
/*      */         if (container1 != null && container1 instanceof JEditorPane && (container2 = container1.getParent()) != null && container2 instanceof JViewport) {
/*      */           JViewport jViewport = (JViewport)container2;
/*      */           if (this.cachedViewPort != null) {
/*      */             JViewport jViewport1 = this.cachedViewPort.get();
/*      */             if (jViewport1 != null) {
/*      */               if (jViewport1 != jViewport)
/*      */                 jViewport1.removeComponentListener(this); 
/*      */             } else {
/*      */               this.cachedViewPort = null;
/*      */             } 
/*      */           } 
/*      */           if (this.cachedViewPort == null) {
/*      */             jViewport.addComponentListener(this);
/*      */             this.cachedViewPort = new WeakReference<>(jViewport);
/*      */           } 
/*      */           this.componentVisibleWidth = (jViewport.getExtentSize()).width;
/*      */           if (this.componentVisibleWidth > 0) {
/*      */             Insets insets = container1.getInsets();
/*      */             this.viewVisibleWidth = this.componentVisibleWidth - insets.left - getLeftInset();
/*      */             param2Int1 = Math.min(param2Int1, this.viewVisibleWidth);
/*      */           } 
/*      */         } else if (this.cachedViewPort != null) {
/*      */           JViewport jViewport = this.cachedViewPort.get();
/*      */           if (jViewport != null)
/*      */             jViewport.removeComponentListener(this); 
/*      */           this.cachedViewPort = null;
/*      */         } 
/*      */         super.layoutMinorAxis(param2Int1, param2Int2, param2ArrayOfint1, param2ArrayOfint2);
/*      */       }
/*      */       public void setParent(View param2View) {
/*      */         if (param2View == null && this.cachedViewPort != null) {
/*      */           JComponent jComponent;
/*      */           if ((jComponent = (JComponent)this.cachedViewPort.get()) != null)
/*      */             ((JComponent)jComponent).removeComponentListener(this); 
/*      */           this.cachedViewPort = null;
/*      */         } 
/*      */         super.setParent(param2View);
/*      */       }
/*      */       public void componentResized(ComponentEvent param2ComponentEvent) {
/*      */         if (!(param2ComponentEvent.getSource() instanceof JViewport))
/*      */           return; 
/*      */         JViewport jViewport = (JViewport)param2ComponentEvent.getSource();
/*      */         if (this.componentVisibleWidth != (jViewport.getExtentSize()).width) {
/*      */           Document document = getDocument();
/*      */           if (document instanceof AbstractDocument) {
/*      */             AbstractDocument abstractDocument = (AbstractDocument)getDocument();
/*      */             abstractDocument.readLock();
/*      */             try {
/*      */               layoutChanged(0);
/*      */               preferenceChanged((View)null, true, true);
/*      */             } finally {
/*      */               abstractDocument.readUnlock();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */       public void componentHidden(ComponentEvent param2ComponentEvent) {}
/*      */       public void componentMoved(ComponentEvent param2ComponentEvent) {}
/*      */       public void componentShown(ComponentEvent param2ComponentEvent) {} } }
/* 1434 */   private static final NavigateLinkAction nextLinkAction = new NavigateLinkAction("next-link-action");
/*      */ 
/*      */   
/* 1437 */   private static final NavigateLinkAction previousLinkAction = new NavigateLinkAction("previous-link-action");
/*      */ 
/*      */   
/* 1440 */   private static final ActivateLinkAction activateLinkAction = new ActivateLinkAction("activate-link-action");
/*      */ 
/*      */   
/* 1443 */   private static final Action[] defaultActions = new Action[] { new InsertHTMLTextAction("InsertTable", "<table border=1><tr><td></td></tr></table>", HTML.Tag.BODY, HTML.Tag.TABLE), new InsertHTMLTextAction("InsertTableRow", "<table border=1><tr><td></td></tr></table>", HTML.Tag.TABLE, HTML.Tag.TR, HTML.Tag.BODY, HTML.Tag.TABLE), new InsertHTMLTextAction("InsertTableDataCell", "<table border=1><tr><td></td></tr></table>", HTML.Tag.TR, HTML.Tag.TD, HTML.Tag.BODY, HTML.Tag.TABLE), new InsertHTMLTextAction("InsertUnorderedList", "<ul><li></li></ul>", HTML.Tag.BODY, HTML.Tag.UL), new InsertHTMLTextAction("InsertUnorderedListItem", "<ul><li></li></ul>", HTML.Tag.UL, HTML.Tag.LI, HTML.Tag.BODY, HTML.Tag.UL), new InsertHTMLTextAction("InsertOrderedList", "<ol><li></li></ol>", HTML.Tag.BODY, HTML.Tag.OL), new InsertHTMLTextAction("InsertOrderedListItem", "<ol><li></li></ol>", HTML.Tag.OL, HTML.Tag.LI, HTML.Tag.BODY, HTML.Tag.OL), new InsertHRAction(), new InsertHTMLTextAction("InsertPre", "<pre></pre>", HTML.Tag.BODY, HTML.Tag.PRE), nextLinkAction, previousLinkAction, activateLinkAction, new BeginAction("caret-begin", false), new BeginAction("selection-begin", true) };
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
/*      */   private boolean foundLink = false;
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
/* 1473 */   private int prevHypertextOffset = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   private Object linkNavigationTag;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class HTMLTextAction
/*      */     extends StyledEditorKit.StyledTextAction
/*      */   {
/*      */     public HTMLTextAction(String param1String) {
/* 1486 */       super(param1String);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected HTMLDocument getHTMLDocument(JEditorPane param1JEditorPane) {
/* 1493 */       Document document = param1JEditorPane.getDocument();
/* 1494 */       if (document instanceof HTMLDocument) {
/* 1495 */         return (HTMLDocument)document;
/*      */       }
/* 1497 */       throw new IllegalArgumentException("document must be HTMLDocument");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected HTMLEditorKit getHTMLEditorKit(JEditorPane param1JEditorPane) {
/* 1504 */       EditorKit editorKit = param1JEditorPane.getEditorKit();
/* 1505 */       if (editorKit instanceof HTMLEditorKit) {
/* 1506 */         return (HTMLEditorKit)editorKit;
/*      */       }
/* 1508 */       throw new IllegalArgumentException("EditorKit must be HTMLEditorKit");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Element[] getElementsAt(HTMLDocument param1HTMLDocument, int param1Int) {
/* 1516 */       return getElementsAt(param1HTMLDocument.getDefaultRootElement(), param1Int, 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Element[] getElementsAt(Element param1Element, int param1Int1, int param1Int2) {
/* 1524 */       if (param1Element.isLeaf()) {
/* 1525 */         Element[] arrayOfElement1 = new Element[param1Int2 + 1];
/* 1526 */         arrayOfElement1[param1Int2] = param1Element;
/* 1527 */         return arrayOfElement1;
/*      */       } 
/* 1529 */       Element[] arrayOfElement = getElementsAt(param1Element
/* 1530 */           .getElement(param1Element.getElementIndex(param1Int1)), param1Int1, param1Int2 + 1);
/* 1531 */       arrayOfElement[param1Int2] = param1Element;
/* 1532 */       return arrayOfElement;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int elementCountToTag(HTMLDocument param1HTMLDocument, int param1Int, HTML.Tag param1Tag) {
/* 1544 */       byte b = -1;
/* 1545 */       Element element = param1HTMLDocument.getCharacterElement(param1Int);
/* 1546 */       while (element != null && element.getAttributes()
/* 1547 */         .getAttribute(StyleConstants.NameAttribute) != param1Tag) {
/* 1548 */         element = element.getParentElement();
/* 1549 */         b++;
/*      */       } 
/* 1551 */       if (element == null) {
/* 1552 */         return -1;
/*      */       }
/* 1554 */       return b;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Element findElementMatchingTag(HTMLDocument param1HTMLDocument, int param1Int, HTML.Tag param1Tag) {
/* 1563 */       Element element1 = param1HTMLDocument.getDefaultRootElement();
/* 1564 */       Element element2 = null;
/* 1565 */       while (element1 != null) {
/* 1566 */         if (element1.getAttributes()
/* 1567 */           .getAttribute(StyleConstants.NameAttribute) == param1Tag) {
/* 1568 */           element2 = element1;
/*      */         }
/* 1570 */         element1 = element1.getElement(element1.getElementIndex(param1Int));
/*      */       } 
/* 1572 */       return element2;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class InsertHTMLTextAction
/*      */     extends HTMLTextAction
/*      */   {
/*      */     protected String html;
/*      */ 
/*      */     
/*      */     protected HTML.Tag parentTag;
/*      */ 
/*      */     
/*      */     protected HTML.Tag addTag;
/*      */     
/*      */     protected HTML.Tag alternateParentTag;
/*      */     
/*      */     protected HTML.Tag alternateAddTag;
/*      */     
/*      */     boolean adjustSelection;
/*      */ 
/*      */     
/*      */     public InsertHTMLTextAction(String param1String1, String param1String2, HTML.Tag param1Tag1, HTML.Tag param1Tag2) {
/* 1597 */       this(param1String1, param1String2, param1Tag1, param1Tag2, (HTML.Tag)null, (HTML.Tag)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public InsertHTMLTextAction(String param1String1, String param1String2, HTML.Tag param1Tag1, HTML.Tag param1Tag2, HTML.Tag param1Tag3, HTML.Tag param1Tag4) {
/* 1605 */       this(param1String1, param1String2, param1Tag1, param1Tag2, param1Tag3, param1Tag4, true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     InsertHTMLTextAction(String param1String1, String param1String2, HTML.Tag param1Tag1, HTML.Tag param1Tag2, HTML.Tag param1Tag3, HTML.Tag param1Tag4, boolean param1Boolean) {
/* 1616 */       super(param1String1);
/* 1617 */       this.html = param1String2;
/* 1618 */       this.parentTag = param1Tag1;
/* 1619 */       this.addTag = param1Tag2;
/* 1620 */       this.alternateParentTag = param1Tag3;
/* 1621 */       this.alternateAddTag = param1Tag4;
/* 1622 */       this.adjustSelection = param1Boolean;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void insertHTML(JEditorPane param1JEditorPane, HTMLDocument param1HTMLDocument, int param1Int1, String param1String, int param1Int2, int param1Int3, HTML.Tag param1Tag) {
/*      */       try {
/* 1633 */         getHTMLEditorKit(param1JEditorPane).insertHTML(param1HTMLDocument, param1Int1, param1String, param1Int2, param1Int3, param1Tag);
/*      */       
/*      */       }
/* 1636 */       catch (IOException iOException) {
/* 1637 */         throw new RuntimeException("Unable to insert: " + iOException);
/* 1638 */       } catch (BadLocationException badLocationException) {
/* 1639 */         throw new RuntimeException("Unable to insert: " + badLocationException);
/*      */       } 
/*      */     }
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
/*      */     protected void insertAtBoundary(JEditorPane param1JEditorPane, HTMLDocument param1HTMLDocument, int param1Int, Element param1Element, String param1String, HTML.Tag param1Tag1, HTML.Tag param1Tag2) {
/* 1653 */       insertAtBoundry(param1JEditorPane, param1HTMLDocument, param1Int, param1Element, param1String, param1Tag1, param1Tag2);
/*      */     }
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
/*      */     @Deprecated
/*      */     protected void insertAtBoundry(JEditorPane param1JEditorPane, HTMLDocument param1HTMLDocument, int param1Int, Element param1Element, String param1String, HTML.Tag param1Tag1, HTML.Tag param1Tag2) {
/*      */       Element element;
/* 1671 */       boolean bool = (param1Int == 0) ? true : false;
/*      */       
/* 1673 */       if (param1Int > 0 || param1Element == null) {
/* 1674 */         Element element1 = param1HTMLDocument.getDefaultRootElement();
/* 1675 */         while (element1 != null && element1.getStartOffset() != param1Int && 
/* 1676 */           !element1.isLeaf()) {
/* 1677 */           element1 = element1.getElement(element1.getElementIndex(param1Int));
/*      */         }
/* 1679 */         element = (element1 != null) ? element1.getParentElement() : null;
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1684 */         element = param1Element;
/*      */       } 
/* 1686 */       if (element != null) {
/*      */         
/* 1688 */         int i = 0;
/* 1689 */         byte b = 0;
/* 1690 */         if (bool && param1Element != null) {
/* 1691 */           Element element1 = element;
/* 1692 */           while (element1 != null && !element1.isLeaf()) {
/* 1693 */             element1 = element1.getElement(element1.getElementIndex(param1Int));
/* 1694 */             i++;
/*      */           } 
/*      */         } else {
/*      */           
/* 1698 */           Element element1 = element;
/* 1699 */           param1Int--;
/* 1700 */           while (element1 != null && !element1.isLeaf()) {
/* 1701 */             element1 = element1.getElement(element1.getElementIndex(param1Int));
/* 1702 */             i++;
/*      */           } 
/*      */ 
/*      */           
/* 1706 */           element1 = element;
/* 1707 */           param1Int++;
/* 1708 */           while (element1 != null && element1 != param1Element) {
/* 1709 */             element1 = element1.getElement(element1.getElementIndex(param1Int));
/* 1710 */             b++;
/*      */           } 
/*      */         } 
/* 1713 */         i = Math.max(0, i - 1);
/*      */ 
/*      */         
/* 1716 */         insertHTML(param1JEditorPane, param1HTMLDocument, param1Int, param1String, i, b, param1Tag2);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean insertIntoTag(JEditorPane param1JEditorPane, HTMLDocument param1HTMLDocument, int param1Int, HTML.Tag param1Tag1, HTML.Tag param1Tag2) {
/* 1729 */       Element element = findElementMatchingTag(param1HTMLDocument, param1Int, param1Tag1);
/* 1730 */       if (element != null && element.getStartOffset() == param1Int) {
/* 1731 */         insertAtBoundary(param1JEditorPane, param1HTMLDocument, param1Int, element, this.html, param1Tag1, param1Tag2);
/*      */         
/* 1733 */         return true;
/*      */       } 
/* 1735 */       if (param1Int > 0) {
/* 1736 */         int i = elementCountToTag(param1HTMLDocument, param1Int - 1, param1Tag1);
/* 1737 */         if (i != -1) {
/* 1738 */           insertHTML(param1JEditorPane, param1HTMLDocument, param1Int, this.html, i, 0, param1Tag2);
/* 1739 */           return true;
/*      */         } 
/*      */       } 
/* 1742 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void adjustSelection(JEditorPane param1JEditorPane, HTMLDocument param1HTMLDocument, int param1Int1, int param1Int2) {
/* 1751 */       int i = param1HTMLDocument.getLength();
/* 1752 */       if (i != param1Int2 && param1Int1 < i) {
/* 1753 */         if (param1Int1 > 0) {
/*      */           String str;
/*      */           try {
/* 1756 */             str = param1HTMLDocument.getText(param1Int1 - 1, 1);
/* 1757 */           } catch (BadLocationException badLocationException) {
/* 1758 */             str = null;
/*      */           } 
/* 1760 */           if (str != null && str.length() > 0 && str
/* 1761 */             .charAt(0) == '\n') {
/* 1762 */             param1JEditorPane.select(param1Int1, param1Int1);
/*      */           } else {
/*      */             
/* 1765 */             param1JEditorPane.select(param1Int1 + 1, param1Int1 + 1);
/*      */           } 
/*      */         } else {
/*      */           
/* 1769 */           param1JEditorPane.select(1, 1);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1780 */       JEditorPane jEditorPane = getEditor(param1ActionEvent);
/* 1781 */       if (jEditorPane != null) {
/* 1782 */         boolean bool; HTMLDocument hTMLDocument = getHTMLDocument(jEditorPane);
/* 1783 */         int i = jEditorPane.getSelectionStart();
/* 1784 */         int j = hTMLDocument.getLength();
/*      */ 
/*      */         
/* 1787 */         if (!insertIntoTag(jEditorPane, hTMLDocument, i, this.parentTag, this.addTag) && this.alternateParentTag != null) {
/*      */ 
/*      */           
/* 1790 */           bool = insertIntoTag(jEditorPane, hTMLDocument, i, this.alternateParentTag, this.alternateAddTag);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1795 */           bool = true;
/*      */         } 
/* 1797 */         if (this.adjustSelection && bool) {
/* 1798 */           adjustSelection(jEditorPane, hTMLDocument, i, j);
/*      */         }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class InsertHRAction
/*      */     extends InsertHTMLTextAction
/*      */   {
/*      */     InsertHRAction() {
/* 1827 */       super("InsertHR", "<hr>", (HTML.Tag)null, HTML.Tag.IMPLIED, (HTML.Tag)null, (HTML.Tag)null, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1837 */       JEditorPane jEditorPane = getEditor(param1ActionEvent);
/* 1838 */       if (jEditorPane != null) {
/* 1839 */         HTMLDocument hTMLDocument = getHTMLDocument(jEditorPane);
/* 1840 */         int i = jEditorPane.getSelectionStart();
/* 1841 */         Element element = hTMLDocument.getParagraphElement(i);
/* 1842 */         if (element.getParentElement() != null) {
/* 1843 */           this
/*      */             
/* 1845 */             .parentTag = (HTML.Tag)element.getParentElement().getAttributes().getAttribute(StyleConstants.NameAttribute);
/* 1846 */           super.actionPerformed(param1ActionEvent);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Object getAttrValue(AttributeSet paramAttributeSet, HTML.Attribute paramAttribute) {
/* 1857 */     Enumeration<?> enumeration = paramAttributeSet.getAttributeNames();
/* 1858 */     while (enumeration.hasMoreElements()) {
/* 1859 */       Object object1 = enumeration.nextElement();
/* 1860 */       Object object2 = paramAttributeSet.getAttribute(object1);
/* 1861 */       if (object2 instanceof AttributeSet) {
/* 1862 */         Object object = getAttrValue((AttributeSet)object2, paramAttribute);
/* 1863 */         if (object != null)
/* 1864 */           return object;  continue;
/*      */       } 
/* 1866 */       if (object1 == paramAttribute) {
/* 1867 */         return object2;
/*      */       }
/*      */     } 
/* 1870 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class NavigateLinkAction
/*      */     extends TextAction
/*      */     implements CaretListener
/*      */   {
/* 1881 */     private static final FocusHighlightPainter focusPainter = new FocusHighlightPainter(null);
/*      */ 
/*      */     
/*      */     private final boolean focusBack;
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigateLinkAction(String param1String) {
/* 1889 */       super(param1String);
/* 1890 */       this.focusBack = "previous-link-action".equals(param1String);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void caretUpdate(CaretEvent param1CaretEvent) {
/* 1899 */       Object object = param1CaretEvent.getSource();
/* 1900 */       if (object instanceof JTextComponent) {
/* 1901 */         JTextComponent jTextComponent = (JTextComponent)object;
/* 1902 */         HTMLEditorKit hTMLEditorKit = getHTMLEditorKit(jTextComponent);
/* 1903 */         if (hTMLEditorKit != null && hTMLEditorKit.foundLink) {
/* 1904 */           hTMLEditorKit.foundLink = false;
/*      */ 
/*      */ 
/*      */           
/* 1908 */           jTextComponent.getAccessibleContext().firePropertyChange("AccessibleHypertextOffset", 
/*      */               
/* 1910 */               Integer.valueOf(hTMLEditorKit.prevHypertextOffset), 
/* 1911 */               Integer.valueOf(param1CaretEvent.getDot()));
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1920 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 1921 */       if (jTextComponent == null || jTextComponent.isEditable()) {
/*      */         return;
/*      */       }
/*      */       
/* 1925 */       Document document = jTextComponent.getDocument();
/* 1926 */       HTMLEditorKit hTMLEditorKit = getHTMLEditorKit(jTextComponent);
/* 1927 */       if (document == null || hTMLEditorKit == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1933 */       ElementIterator elementIterator = new ElementIterator(document);
/* 1934 */       int i = jTextComponent.getCaretPosition();
/* 1935 */       int j = -1;
/* 1936 */       int k = -1;
/*      */       
/*      */       Element element;
/*      */       
/* 1940 */       while ((element = elementIterator.next()) != null) {
/* 1941 */         String str = element.getName();
/* 1942 */         AttributeSet attributeSet = element.getAttributes();
/*      */         
/* 1944 */         Object object = HTMLEditorKit.getAttrValue(attributeSet, HTML.Attribute.HREF);
/* 1945 */         if (!str.equals(HTML.Tag.OBJECT.toString()) && object == null) {
/*      */           continue;
/*      */         }
/*      */         
/* 1949 */         int m = element.getStartOffset();
/* 1950 */         if (this.focusBack) {
/* 1951 */           if (m >= i && j >= 0) {
/*      */ 
/*      */             
/* 1954 */             hTMLEditorKit.foundLink = true;
/* 1955 */             jTextComponent.setCaretPosition(j);
/* 1956 */             moveCaretPosition(jTextComponent, hTMLEditorKit, j, k);
/*      */             
/* 1958 */             hTMLEditorKit.prevHypertextOffset = j;
/*      */             
/*      */             return;
/*      */           } 
/* 1962 */         } else if (m > i) {
/*      */           
/* 1964 */           hTMLEditorKit.foundLink = true;
/* 1965 */           jTextComponent.setCaretPosition(m);
/* 1966 */           moveCaretPosition(jTextComponent, hTMLEditorKit, m, element
/* 1967 */               .getEndOffset());
/* 1968 */           hTMLEditorKit.prevHypertextOffset = m;
/*      */           
/*      */           return;
/*      */         } 
/* 1972 */         j = element.getStartOffset();
/* 1973 */         k = element.getEndOffset();
/*      */       } 
/* 1975 */       if (this.focusBack && j >= 0) {
/* 1976 */         hTMLEditorKit.foundLink = true;
/* 1977 */         jTextComponent.setCaretPosition(j);
/* 1978 */         moveCaretPosition(jTextComponent, hTMLEditorKit, j, k);
/* 1979 */         hTMLEditorKit.prevHypertextOffset = j;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void moveCaretPosition(JTextComponent param1JTextComponent, HTMLEditorKit param1HTMLEditorKit, int param1Int1, int param1Int2) {
/* 1988 */       Highlighter highlighter = param1JTextComponent.getHighlighter();
/* 1989 */       if (highlighter != null) {
/* 1990 */         int i = Math.min(param1Int2, param1Int1);
/* 1991 */         int j = Math.max(param1Int2, param1Int1);
/*      */         try {
/* 1993 */           if (param1HTMLEditorKit.linkNavigationTag != null) {
/* 1994 */             highlighter.changeHighlight(param1HTMLEditorKit.linkNavigationTag, i, j);
/*      */           } else {
/* 1996 */             param1HTMLEditorKit.linkNavigationTag = highlighter
/* 1997 */               .addHighlight(i, j, focusPainter);
/*      */           } 
/* 1999 */         } catch (BadLocationException badLocationException) {}
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private HTMLEditorKit getHTMLEditorKit(JTextComponent param1JTextComponent) {
/* 2005 */       if (param1JTextComponent instanceof JEditorPane) {
/* 2006 */         EditorKit editorKit = ((JEditorPane)param1JTextComponent).getEditorKit();
/* 2007 */         if (editorKit instanceof HTMLEditorKit) {
/* 2008 */           return (HTMLEditorKit)editorKit;
/*      */         }
/*      */       } 
/* 2011 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static class FocusHighlightPainter
/*      */       extends DefaultHighlighter.DefaultHighlightPainter
/*      */     {
/*      */       FocusHighlightPainter(Color param2Color) {
/* 2022 */         super(param2Color);
/*      */       }
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
/*      */       public Shape paintLayer(Graphics param2Graphics, int param2Int1, int param2Int2, Shape param2Shape, JTextComponent param2JTextComponent, View param2View) {
/* 2040 */         Color color = getColor();
/*      */         
/* 2042 */         if (color == null) {
/* 2043 */           param2Graphics.setColor(param2JTextComponent.getSelectionColor());
/*      */         } else {
/*      */           
/* 2046 */           param2Graphics.setColor(color);
/*      */         } 
/* 2048 */         if (param2Int1 == param2View.getStartOffset() && param2Int2 == param2View
/* 2049 */           .getEndOffset()) {
/*      */           Rectangle rectangle;
/*      */           
/* 2052 */           if (param2Shape instanceof Rectangle) {
/* 2053 */             rectangle = (Rectangle)param2Shape;
/*      */           } else {
/*      */             
/* 2056 */             rectangle = param2Shape.getBounds();
/*      */           } 
/* 2058 */           param2Graphics.drawRect(rectangle.x, rectangle.y, rectangle.width - 1, rectangle.height);
/* 2059 */           return rectangle;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 2065 */           Shape shape = param2View.modelToView(param2Int1, Position.Bias.Forward, param2Int2, Position.Bias.Backward, param2Shape);
/*      */ 
/*      */ 
/*      */           
/* 2069 */           Rectangle rectangle = (shape instanceof Rectangle) ? (Rectangle)shape : shape.getBounds();
/* 2070 */           param2Graphics.drawRect(rectangle.x, rectangle.y, rectangle.width - 1, rectangle.height);
/* 2071 */           return rectangle;
/* 2072 */         } catch (BadLocationException badLocationException) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2077 */           return null;
/*      */         } 
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
/*      */   static class ActivateLinkAction
/*      */     extends TextAction
/*      */   {
/*      */     public ActivateLinkAction(String param1String) {
/* 2094 */       super(param1String);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void activateLink(String param1String, HTMLDocument param1HTMLDocument, JEditorPane param1JEditorPane, int param1Int) {
/*      */       try {
/* 2104 */         URL uRL1 = (URL)param1HTMLDocument.getProperty("stream");
/* 2105 */         URL uRL2 = new URL(uRL1, param1String);
/*      */ 
/*      */ 
/*      */         
/* 2109 */         HyperlinkEvent hyperlinkEvent = new HyperlinkEvent(param1JEditorPane, HyperlinkEvent.EventType.ACTIVATED, uRL2, uRL2.toExternalForm(), param1HTMLDocument.getCharacterElement(param1Int));
/* 2110 */         param1JEditorPane.fireHyperlinkUpdate(hyperlinkEvent);
/* 2111 */       } catch (MalformedURLException malformedURLException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void doObjectAction(JEditorPane param1JEditorPane, Element param1Element) {
/* 2119 */       View view = getView(param1JEditorPane, param1Element);
/* 2120 */       if (view != null && view instanceof ObjectView) {
/* 2121 */         Component component = ((ObjectView)view).getComponent();
/* 2122 */         if (component != null && component instanceof Accessible) {
/* 2123 */           AccessibleContext accessibleContext = component.getAccessibleContext();
/* 2124 */           if (accessibleContext != null) {
/* 2125 */             AccessibleAction accessibleAction = accessibleContext.getAccessibleAction();
/* 2126 */             if (accessibleAction != null) {
/* 2127 */               accessibleAction.doAccessibleAction(0);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private View getRootView(JEditorPane param1JEditorPane) {
/* 2138 */       return param1JEditorPane.getUI().getRootView(param1JEditorPane);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private View getView(JEditorPane param1JEditorPane, Element param1Element) {
/* 2145 */       Object object = lock(param1JEditorPane);
/*      */       try {
/* 2147 */         View view = getRootView(param1JEditorPane);
/* 2148 */         int i = param1Element.getStartOffset();
/* 2149 */         if (view != null) {
/* 2150 */           return getView(view, param1Element, i);
/*      */         }
/* 2152 */         return null;
/*      */       } finally {
/* 2154 */         unlock(object);
/*      */       } 
/*      */     }
/*      */     
/*      */     private View getView(View param1View, Element param1Element, int param1Int) {
/* 2159 */       if (param1View.getElement() == param1Element) {
/* 2160 */         return param1View;
/*      */       }
/* 2162 */       int i = param1View.getViewIndex(param1Int, Position.Bias.Forward);
/*      */       
/* 2164 */       if (i != -1 && i < param1View.getViewCount()) {
/* 2165 */         return getView(param1View.getView(i), param1Element, param1Int);
/*      */       }
/* 2167 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object lock(JEditorPane param1JEditorPane) {
/* 2176 */       Document document = param1JEditorPane.getDocument();
/*      */       
/* 2178 */       if (document instanceof AbstractDocument) {
/* 2179 */         ((AbstractDocument)document).readLock();
/* 2180 */         return document;
/*      */       } 
/* 2182 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void unlock(Object param1Object) {
/* 2189 */       if (param1Object != null) {
/* 2190 */         ((AbstractDocument)param1Object).readUnlock();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2199 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 2200 */       if (jTextComponent.isEditable() || !(jTextComponent instanceof JEditorPane)) {
/*      */         return;
/*      */       }
/* 2203 */       JEditorPane jEditorPane = (JEditorPane)jTextComponent;
/*      */       
/* 2205 */       Document document = jEditorPane.getDocument();
/* 2206 */       if (document == null || !(document instanceof HTMLDocument)) {
/*      */         return;
/*      */       }
/* 2209 */       HTMLDocument hTMLDocument = (HTMLDocument)document;
/*      */       
/* 2211 */       ElementIterator elementIterator = new ElementIterator(hTMLDocument);
/* 2212 */       int i = jEditorPane.getCaretPosition();
/*      */ 
/*      */       
/* 2215 */       Object object1 = null;
/* 2216 */       Object object2 = null;
/*      */       Element element;
/* 2218 */       while ((element = elementIterator.next()) != null) {
/* 2219 */         String str = element.getName();
/* 2220 */         AttributeSet attributeSet = element.getAttributes();
/*      */         
/* 2222 */         Object object = HTMLEditorKit.getAttrValue(attributeSet, HTML.Attribute.HREF);
/* 2223 */         if (object != null) {
/* 2224 */           if (i >= element.getStartOffset() && i <= element
/* 2225 */             .getEndOffset()) {
/*      */             
/* 2227 */             activateLink((String)object, hTMLDocument, jEditorPane, i); return;
/*      */           }  continue;
/*      */         } 
/* 2230 */         if (str.equals(HTML.Tag.OBJECT.toString())) {
/* 2231 */           Object object3 = HTMLEditorKit.getAttrValue(attributeSet, HTML.Attribute.CLASSID);
/* 2232 */           if (object3 != null && 
/* 2233 */             i >= element.getStartOffset() && i <= element
/* 2234 */             .getEndOffset()) {
/*      */             
/* 2236 */             doObjectAction(jEditorPane, element);
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getBodyElementStart(JTextComponent paramJTextComponent) {
/* 2246 */     Element element = paramJTextComponent.getDocument().getRootElements()[0];
/* 2247 */     for (byte b = 0; b < element.getElementCount(); b++) {
/* 2248 */       Element element1 = element.getElement(b);
/* 2249 */       if ("body".equals(element1.getName())) {
/* 2250 */         return element1.getStartOffset();
/*      */       }
/*      */     } 
/* 2253 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class BeginAction
/*      */     extends TextAction
/*      */   {
/*      */     private boolean select;
/*      */ 
/*      */ 
/*      */     
/*      */     BeginAction(String param1String, boolean param1Boolean) {
/* 2266 */       super(param1String);
/* 2267 */       this.select = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2272 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 2273 */       int i = HTMLEditorKit.getBodyElementStart(jTextComponent);
/*      */       
/* 2275 */       if (jTextComponent != null)
/* 2276 */         if (this.select) {
/* 2277 */           jTextComponent.moveCaretPosition(i);
/*      */         } else {
/* 2279 */           jTextComponent.setCaretPosition(i);
/*      */         }  
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/HTMLEditorKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */