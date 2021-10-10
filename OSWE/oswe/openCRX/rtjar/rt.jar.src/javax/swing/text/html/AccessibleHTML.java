/*      */ package javax.swing.text.html;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.text.BreakIterator;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Locale;
/*      */ import java.util.Vector;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleIcon;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleTable;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.swing.JEditorPane;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.event.DocumentListener;
/*      */ import javax.swing.text.AbstractDocument;
/*      */ import javax.swing.text.AttributeSet;
/*      */ import javax.swing.text.BadLocationException;
/*      */ import javax.swing.text.Document;
/*      */ import javax.swing.text.Element;
/*      */ import javax.swing.text.JTextComponent;
/*      */ import javax.swing.text.PlainDocument;
/*      */ import javax.swing.text.Position;
/*      */ import javax.swing.text.Segment;
/*      */ import javax.swing.text.StyleConstants;
/*      */ import javax.swing.text.StyledDocument;
/*      */ import javax.swing.text.View;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class AccessibleHTML
/*      */   implements Accessible
/*      */ {
/*      */   private JEditorPane editor;
/*      */   private Document model;
/*      */   private DocumentListener docListener;
/*      */   private PropertyChangeListener propChangeListener;
/*      */   private ElementInfo rootElementInfo;
/*      */   private RootHTMLAccessibleContext rootHTMLAccessibleContext;
/*      */   
/*      */   public AccessibleHTML(JEditorPane paramJEditorPane) {
/*   72 */     this.editor = paramJEditorPane;
/*   73 */     this.propChangeListener = new PropertyChangeHandler();
/*   74 */     setDocument(this.editor.getDocument());
/*      */     
/*   76 */     this.docListener = new DocumentHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setDocument(Document paramDocument) {
/*   83 */     if (this.model != null) {
/*   84 */       this.model.removeDocumentListener(this.docListener);
/*      */     }
/*   86 */     if (this.editor != null) {
/*   87 */       this.editor.removePropertyChangeListener(this.propChangeListener);
/*      */     }
/*   89 */     this.model = paramDocument;
/*   90 */     if (this.model != null) {
/*   91 */       if (this.rootElementInfo != null) {
/*   92 */         this.rootElementInfo.invalidate(false);
/*      */       }
/*   94 */       buildInfo();
/*   95 */       this.model.addDocumentListener(this.docListener);
/*      */     } else {
/*      */       
/*   98 */       this.rootElementInfo = null;
/*      */     } 
/*  100 */     if (this.editor != null) {
/*  101 */       this.editor.addPropertyChangeListener(this.propChangeListener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Document getDocument() {
/*  109 */     return this.model;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JEditorPane getTextComponent() {
/*  116 */     return this.editor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ElementInfo getRootInfo() {
/*  123 */     return this.rootElementInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private View getRootView() {
/*  131 */     return getTextComponent().getUI().getRootView(getTextComponent());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rectangle getRootEditorRect() {
/*  138 */     Rectangle rectangle = getTextComponent().getBounds();
/*  139 */     if (rectangle.width > 0 && rectangle.height > 0) {
/*  140 */       rectangle.x = rectangle.y = 0;
/*  141 */       Insets insets = this.editor.getInsets();
/*  142 */       rectangle.x += insets.left;
/*  143 */       rectangle.y += insets.top;
/*  144 */       rectangle.width -= insets.left + insets.right;
/*  145 */       rectangle.height -= insets.top + insets.bottom;
/*  146 */       return rectangle;
/*      */     } 
/*  148 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object lock() {
/*  157 */     Document document = getDocument();
/*      */     
/*  159 */     if (document instanceof AbstractDocument) {
/*  160 */       ((AbstractDocument)document).readLock();
/*  161 */       return document;
/*      */     } 
/*  163 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unlock(Object paramObject) {
/*  170 */     if (paramObject != null) {
/*  171 */       ((AbstractDocument)paramObject).readUnlock();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildInfo() {
/*  179 */     Object object = lock();
/*      */     
/*      */     try {
/*  182 */       Document document = getDocument();
/*  183 */       Element element = document.getDefaultRootElement();
/*      */       
/*  185 */       this.rootElementInfo = new ElementInfo(element);
/*  186 */       this.rootElementInfo.validate();
/*      */     } finally {
/*  188 */       unlock(object);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ElementInfo createElementInfo(Element paramElement, ElementInfo paramElementInfo) {
/*  196 */     AttributeSet attributeSet = paramElement.getAttributes();
/*      */     
/*  198 */     if (attributeSet != null) {
/*  199 */       Object object = attributeSet.getAttribute(StyleConstants.NameAttribute);
/*      */       
/*  201 */       if (object == HTML.Tag.IMG) {
/*  202 */         return new IconElementInfo(paramElement, paramElementInfo);
/*      */       }
/*  204 */       if (object == HTML.Tag.CONTENT || object == HTML.Tag.CAPTION) {
/*  205 */         return new TextElementInfo(paramElement, paramElementInfo);
/*      */       }
/*  207 */       if (object == HTML.Tag.TABLE) {
/*  208 */         return new TableElementInfo(paramElement, paramElementInfo);
/*      */       }
/*      */     } 
/*  211 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AccessibleContext getAccessibleContext() {
/*  218 */     if (this.rootHTMLAccessibleContext == null) {
/*  219 */       this.rootHTMLAccessibleContext = new RootHTMLAccessibleContext(this.rootElementInfo);
/*      */     }
/*      */     
/*  222 */     return this.rootHTMLAccessibleContext;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class RootHTMLAccessibleContext
/*      */     extends HTMLAccessibleContext
/*      */   {
/*      */     public RootHTMLAccessibleContext(AccessibleHTML.ElementInfo param1ElementInfo) {
/*  231 */       super(param1ElementInfo);
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
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAccessibleName() {
/*  250 */       if (AccessibleHTML.this.model != null) {
/*  251 */         return (String)AccessibleHTML.this.model.getProperty("title");
/*      */       }
/*  253 */       return null;
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
/*      */     public String getAccessibleDescription() {
/*  268 */       return AccessibleHTML.this.editor.getContentType();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleRole getAccessibleRole() {
/*  290 */       return AccessibleRole.TEXT;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected abstract class HTMLAccessibleContext
/*      */     extends AccessibleContext
/*      */     implements Accessible, AccessibleComponent
/*      */   {
/*      */     protected AccessibleHTML.ElementInfo elementInfo;
/*      */ 
/*      */     
/*      */     public HTMLAccessibleContext(AccessibleHTML.ElementInfo param1ElementInfo) {
/*  303 */       this.elementInfo = param1ElementInfo;
/*      */     }
/*      */ 
/*      */     
/*      */     public AccessibleContext getAccessibleContext() {
/*  308 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleStateSet getAccessibleStateSet() {
/*  319 */       AccessibleStateSet accessibleStateSet = new AccessibleStateSet();
/*  320 */       JEditorPane jEditorPane = AccessibleHTML.this.getTextComponent();
/*      */       
/*  322 */       if (jEditorPane.isEnabled()) {
/*  323 */         accessibleStateSet.add(AccessibleState.ENABLED);
/*      */       }
/*  325 */       if (jEditorPane instanceof JTextComponent && jEditorPane
/*  326 */         .isEditable()) {
/*      */         
/*  328 */         accessibleStateSet.add(AccessibleState.EDITABLE);
/*  329 */         accessibleStateSet.add(AccessibleState.FOCUSABLE);
/*      */       } 
/*  331 */       if (jEditorPane.isVisible()) {
/*  332 */         accessibleStateSet.add(AccessibleState.VISIBLE);
/*      */       }
/*  334 */       if (jEditorPane.isShowing()) {
/*  335 */         accessibleStateSet.add(AccessibleState.SHOWING);
/*      */       }
/*  337 */       return accessibleStateSet;
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
/*      */     public int getAccessibleIndexInParent() {
/*  351 */       return this.elementInfo.getIndexInParent();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleChildrenCount() {
/*  360 */       return this.elementInfo.getChildCount();
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
/*      */     public Accessible getAccessibleChild(int param1Int) {
/*  374 */       AccessibleHTML.ElementInfo elementInfo = this.elementInfo.getChild(param1Int);
/*  375 */       if (elementInfo != null && elementInfo instanceof Accessible) {
/*  376 */         return (Accessible)elementInfo;
/*      */       }
/*  378 */       return null;
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
/*      */     
/*      */     public Locale getLocale() throws IllegalComponentStateException {
/*  395 */       return AccessibleHTML.this.editor.getLocale();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleComponent getAccessibleComponent() {
/*  401 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Color getBackground() {
/*  412 */       return AccessibleHTML.this.getTextComponent().getBackground();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBackground(Color param1Color) {
/*  422 */       AccessibleHTML.this.getTextComponent().setBackground(param1Color);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Color getForeground() {
/*  433 */       return AccessibleHTML.this.getTextComponent().getForeground();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setForeground(Color param1Color) {
/*  443 */       AccessibleHTML.this.getTextComponent().setForeground(param1Color);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Cursor getCursor() {
/*  453 */       return AccessibleHTML.this.getTextComponent().getCursor();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setCursor(Cursor param1Cursor) {
/*  463 */       AccessibleHTML.this.getTextComponent().setCursor(param1Cursor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Font getFont() {
/*  473 */       return AccessibleHTML.this.getTextComponent().getFont();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setFont(Font param1Font) {
/*  483 */       AccessibleHTML.this.getTextComponent().setFont(param1Font);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FontMetrics getFontMetrics(Font param1Font) {
/*  494 */       return AccessibleHTML.this.getTextComponent().getFontMetrics(param1Font);
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
/*      */     public boolean isEnabled() {
/*  509 */       return AccessibleHTML.this.getTextComponent().isEnabled();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setEnabled(boolean param1Boolean) {
/*  519 */       AccessibleHTML.this.getTextComponent().setEnabled(param1Boolean);
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
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isVisible() {
/*  538 */       return AccessibleHTML.this.getTextComponent().isVisible();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setVisible(boolean param1Boolean) {
/*  548 */       AccessibleHTML.this.getTextComponent().setVisible(param1Boolean);
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
/*      */     public boolean isShowing() {
/*  561 */       return AccessibleHTML.this.getTextComponent().isShowing();
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
/*      */     public boolean contains(Point param1Point) {
/*  574 */       Rectangle rectangle = getBounds();
/*  575 */       if (rectangle != null) {
/*  576 */         return rectangle.contains(param1Point.x, param1Point.y);
/*      */       }
/*  578 */       return false;
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
/*      */     public Point getLocationOnScreen() {
/*  591 */       Point point = AccessibleHTML.this.getTextComponent().getLocationOnScreen();
/*  592 */       Rectangle rectangle = getBounds();
/*  593 */       if (rectangle != null) {
/*  594 */         return new Point(point.x + rectangle.x, point.y + rectangle.y);
/*      */       }
/*      */       
/*  597 */       return null;
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
/*      */     public Point getLocation() {
/*  613 */       Rectangle rectangle = getBounds();
/*  614 */       if (rectangle != null) {
/*  615 */         return new Point(rectangle.x, rectangle.y);
/*      */       }
/*  617 */       return null;
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
/*      */     public void setLocation(Point param1Point) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Rectangle getBounds() {
/*  639 */       return this.elementInfo.getBounds();
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
/*      */     public void setBounds(Rectangle param1Rectangle) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Dimension getSize() {
/*  664 */       Rectangle rectangle = getBounds();
/*  665 */       if (rectangle != null) {
/*  666 */         return new Dimension(rectangle.width, rectangle.height);
/*      */       }
/*  668 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSize(Dimension param1Dimension) {
/*  679 */       JEditorPane jEditorPane = AccessibleHTML.this.getTextComponent();
/*  680 */       jEditorPane.setSize(param1Dimension);
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
/*      */     public Accessible getAccessibleAt(Point param1Point) {
/*  692 */       AccessibleHTML.ElementInfo elementInfo = getElementInfoAt(AccessibleHTML.this.rootElementInfo, param1Point);
/*  693 */       if (elementInfo instanceof Accessible) {
/*  694 */         return (Accessible)elementInfo;
/*      */       }
/*  696 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     private AccessibleHTML.ElementInfo getElementInfoAt(AccessibleHTML.ElementInfo param1ElementInfo, Point param1Point) {
/*  701 */       if (param1ElementInfo.getBounds() == null) {
/*  702 */         return null;
/*      */       }
/*  704 */       if (param1ElementInfo.getChildCount() == 0 && param1ElementInfo
/*  705 */         .getBounds().contains(param1Point)) {
/*  706 */         return param1ElementInfo;
/*      */       }
/*      */       
/*  709 */       if (param1ElementInfo instanceof AccessibleHTML.TableElementInfo) {
/*      */ 
/*      */ 
/*      */         
/*  713 */         AccessibleHTML.ElementInfo elementInfo = ((AccessibleHTML.TableElementInfo)param1ElementInfo).getCaptionInfo();
/*  714 */         if (elementInfo != null) {
/*  715 */           Rectangle rectangle = elementInfo.getBounds();
/*  716 */           if (rectangle != null && rectangle.contains(param1Point)) {
/*  717 */             return elementInfo;
/*      */           }
/*      */         } 
/*      */       } 
/*  721 */       for (byte b = 0; b < param1ElementInfo.getChildCount(); b++) {
/*      */         
/*  723 */         AccessibleHTML.ElementInfo elementInfo1 = param1ElementInfo.getChild(b);
/*  724 */         AccessibleHTML.ElementInfo elementInfo2 = getElementInfoAt(elementInfo1, param1Point);
/*  725 */         if (elementInfo2 != null) {
/*  726 */           return elementInfo2;
/*      */         }
/*      */       } 
/*      */       
/*  730 */       return null;
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
/*      */     public boolean isFocusTraversable() {
/*  745 */       JEditorPane jEditorPane = AccessibleHTML.this.getTextComponent();
/*  746 */       if (jEditorPane instanceof JTextComponent && 
/*  747 */         jEditorPane.isEditable()) {
/*  748 */         return true;
/*      */       }
/*      */       
/*  751 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void requestFocus() {
/*  762 */       if (!isFocusTraversable()) {
/*      */         return;
/*      */       }
/*      */       
/*  766 */       JEditorPane jEditorPane = AccessibleHTML.this.getTextComponent();
/*  767 */       if (jEditorPane instanceof JTextComponent) {
/*      */         
/*  769 */         jEditorPane.requestFocusInWindow();
/*      */         
/*      */         try {
/*  772 */           if (this.elementInfo.validateIfNecessary())
/*      */           {
/*  774 */             Element element = this.elementInfo.getElement();
/*  775 */             jEditorPane.setCaretPosition(element.getStartOffset());
/*      */ 
/*      */             
/*  778 */             AccessibleContext accessibleContext = AccessibleHTML.this.editor.getAccessibleContext();
/*  779 */             PropertyChangeEvent propertyChangeEvent = new PropertyChangeEvent(this, "AccessibleState", null, AccessibleState.FOCUSED);
/*      */ 
/*      */             
/*  782 */             accessibleContext.firePropertyChange("AccessibleState", (Object)null, propertyChangeEvent);
/*      */           }
/*      */         
/*      */         }
/*  786 */         catch (IllegalArgumentException illegalArgumentException) {}
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
/*      */     public void addFocusListener(FocusListener param1FocusListener) {
/*  800 */       AccessibleHTML.this.getTextComponent().addFocusListener(param1FocusListener);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeFocusListener(FocusListener param1FocusListener) {
/*  811 */       AccessibleHTML.this.getTextComponent().removeFocusListener(param1FocusListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class TextElementInfo
/*      */     extends ElementInfo
/*      */     implements Accessible
/*      */   {
/*      */     private AccessibleContext accessibleContext;
/*      */ 
/*      */     
/*      */     TextElementInfo(Element param1Element, AccessibleHTML.ElementInfo param1ElementInfo) {
/*  824 */       super(param1Element, param1ElementInfo);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleContext getAccessibleContext() {
/*  831 */       if (this.accessibleContext == null) {
/*  832 */         this.accessibleContext = new TextAccessibleContext(this);
/*      */       }
/*  834 */       return this.accessibleContext;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public class TextAccessibleContext
/*      */       extends AccessibleHTML.HTMLAccessibleContext
/*      */       implements AccessibleText
/*      */     {
/*      */       public TextAccessibleContext(AccessibleHTML.ElementInfo param2ElementInfo) {
/*  844 */         super(param2ElementInfo);
/*      */       }
/*      */       
/*      */       public AccessibleText getAccessibleText() {
/*  848 */         return this;
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
/*      */       
/*      */       public String getAccessibleName() {
/*  867 */         if (AccessibleHTML.this.model != null) {
/*  868 */           return (String)AccessibleHTML.this.model.getProperty("title");
/*      */         }
/*  870 */         return null;
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
/*      */       public String getAccessibleDescription() {
/*  885 */         return AccessibleHTML.this.editor.getContentType();
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
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleRole getAccessibleRole() {
/*  907 */         return AccessibleRole.TEXT;
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
/*      */       public int getIndexAtPoint(Point param2Point) {
/*  920 */         View view = AccessibleHTML.TextElementInfo.this.getView();
/*  921 */         if (view != null) {
/*  922 */           return view.viewToModel(param2Point.x, param2Point.y, getBounds());
/*      */         }
/*  924 */         return -1;
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
/*      */       public Rectangle getCharacterBounds(int param2Int) {
/*      */         try {
/*  940 */           return AccessibleHTML.this.editor.getUI().modelToView(AccessibleHTML.this.editor, param2Int);
/*  941 */         } catch (BadLocationException badLocationException) {
/*  942 */           return null;
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getCharCount() {
/*  952 */         if (AccessibleHTML.TextElementInfo.this.validateIfNecessary()) {
/*  953 */           Element element = this.elementInfo.getElement();
/*  954 */           return element.getEndOffset() - element.getStartOffset();
/*      */         } 
/*  956 */         return 0;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getCaretPosition() {
/*  967 */         View view = AccessibleHTML.TextElementInfo.this.getView();
/*  968 */         if (view == null) {
/*  969 */           return -1;
/*      */         }
/*  971 */         Container container = view.getContainer();
/*  972 */         if (container == null) {
/*  973 */           return -1;
/*      */         }
/*  975 */         if (container instanceof JTextComponent) {
/*  976 */           return ((JTextComponent)container).getCaretPosition();
/*      */         }
/*  978 */         return -1;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       private class IndexedSegment
/*      */         extends Segment
/*      */       {
/*      */         public int modelOffset;
/*      */ 
/*      */         
/*      */         private IndexedSegment() {}
/*      */       }
/*      */ 
/*      */       
/*      */       public String getAtIndex(int param2Int1, int param2Int2) {
/*  994 */         return getAtIndex(param2Int1, param2Int2, 0);
/*      */       }
/*      */ 
/*      */       
/*      */       public String getAfterIndex(int param2Int1, int param2Int2) {
/*  999 */         return getAtIndex(param2Int1, param2Int2, 1);
/*      */       }
/*      */       
/*      */       public String getBeforeIndex(int param2Int1, int param2Int2) {
/* 1003 */         return getAtIndex(param2Int1, param2Int2, -1);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private String getAtIndex(int param2Int1, int param2Int2, int param2Int3) {
/* 1012 */         if (AccessibleHTML.this.model instanceof AbstractDocument)
/* 1013 */           ((AbstractDocument)AccessibleHTML.this.model).readLock(); 
/*      */         
/*      */         try { IndexedSegment indexedSegment;
/* 1016 */           if (param2Int2 < 0 || param2Int2 >= AccessibleHTML.this.model.getLength()) {
/* 1017 */             return null;
/*      */           }
/* 1019 */           switch (param2Int1) {
/*      */             case 1:
/* 1021 */               if (param2Int2 + param2Int3 < AccessibleHTML.this.model.getLength() && param2Int2 + param2Int3 >= 0)
/*      */               {
/* 1023 */                 return AccessibleHTML.this.model.getText(param2Int2 + param2Int3, 1);
/*      */               }
/*      */               break;
/*      */ 
/*      */             
/*      */             case 2:
/*      */             case 3:
/* 1030 */               indexedSegment = getSegmentAt(param2Int1, param2Int2);
/* 1031 */               if (indexedSegment != null) {
/* 1032 */                 if (param2Int3 != 0) {
/*      */                   int i;
/*      */ 
/*      */                   
/* 1036 */                   if (param2Int3 < 0) {
/* 1037 */                     i = indexedSegment.modelOffset - 1;
/*      */                   } else {
/*      */                     
/* 1040 */                     i = indexedSegment.modelOffset + param2Int3 * indexedSegment.count;
/*      */                   } 
/* 1042 */                   if (i >= 0 && i <= AccessibleHTML.this.model.getLength()) {
/* 1043 */                     indexedSegment = getSegmentAt(param2Int1, i);
/*      */                   } else {
/*      */                     
/* 1046 */                     indexedSegment = null;
/*      */                   } 
/*      */                 } 
/* 1049 */                 if (indexedSegment != null) {
/* 1050 */                   return new String(indexedSegment.array, indexedSegment.offset, indexedSegment.count);
/*      */                 }
/*      */               } 
/*      */               break;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*      */            }
/* 1059 */         catch (BadLocationException badLocationException) {  }
/*      */         finally
/* 1061 */         { if (AccessibleHTML.this.model instanceof AbstractDocument) {
/* 1062 */             ((AbstractDocument)AccessibleHTML.this.model).readUnlock();
/*      */           } }
/*      */         
/* 1065 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private Element getParagraphElement(int param2Int) {
/* 1072 */         if (AccessibleHTML.this.model instanceof PlainDocument) {
/* 1073 */           PlainDocument plainDocument = (PlainDocument)AccessibleHTML.this.model;
/* 1074 */           return plainDocument.getParagraphElement(param2Int);
/* 1075 */         }  if (AccessibleHTML.this.model instanceof StyledDocument) {
/* 1076 */           StyledDocument styledDocument = (StyledDocument)AccessibleHTML.this.model;
/* 1077 */           return styledDocument.getParagraphElement(param2Int);
/*      */         } 
/*      */         Element element;
/* 1080 */         for (element = AccessibleHTML.this.model.getDefaultRootElement(); !element.isLeaf(); ) {
/* 1081 */           int i = element.getElementIndex(param2Int);
/* 1082 */           element = element.getElement(i);
/*      */         } 
/* 1084 */         if (element == null) {
/* 1085 */           return null;
/*      */         }
/* 1087 */         return element.getParentElement();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private IndexedSegment getParagraphElementText(int param2Int) throws BadLocationException {
/* 1098 */         Element element = getParagraphElement(param2Int);
/*      */ 
/*      */         
/* 1101 */         if (element != null) {
/* 1102 */           IndexedSegment indexedSegment = new IndexedSegment();
/*      */           try {
/* 1104 */             int i = element.getEndOffset() - element.getStartOffset();
/* 1105 */             AccessibleHTML.this.model.getText(element.getStartOffset(), i, indexedSegment);
/* 1106 */           } catch (BadLocationException badLocationException) {
/* 1107 */             return null;
/*      */           } 
/* 1109 */           indexedSegment.modelOffset = element.getStartOffset();
/* 1110 */           return indexedSegment;
/*      */         } 
/* 1112 */         return null;
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
/*      */       private IndexedSegment getSegmentAt(int param2Int1, int param2Int2) throws BadLocationException {
/*      */         BreakIterator breakIterator;
/* 1127 */         IndexedSegment indexedSegment = getParagraphElementText(param2Int2);
/* 1128 */         if (indexedSegment == null) {
/* 1129 */           return null;
/*      */         }
/*      */         
/* 1132 */         switch (param2Int1) {
/*      */           case 2:
/* 1134 */             breakIterator = BreakIterator.getWordInstance(getLocale());
/*      */             break;
/*      */           case 3:
/* 1137 */             breakIterator = BreakIterator.getSentenceInstance(getLocale());
/*      */             break;
/*      */           default:
/* 1140 */             return null;
/*      */         } 
/* 1142 */         indexedSegment.first();
/* 1143 */         breakIterator.setText(indexedSegment);
/* 1144 */         int i = breakIterator.following(param2Int2 - indexedSegment.modelOffset + indexedSegment.offset);
/* 1145 */         if (i == -1) {
/* 1146 */           return null;
/*      */         }
/* 1148 */         if (i > indexedSegment.offset + indexedSegment.count) {
/* 1149 */           return null;
/*      */         }
/* 1151 */         int j = breakIterator.previous();
/* 1152 */         if (j == -1 || j >= indexedSegment.offset + indexedSegment.count)
/*      */         {
/* 1154 */           return null;
/*      */         }
/* 1156 */         indexedSegment.modelOffset = indexedSegment.modelOffset + j - indexedSegment.offset;
/* 1157 */         indexedSegment.offset = j;
/* 1158 */         indexedSegment.count = i - j;
/* 1159 */         return indexedSegment;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AttributeSet getCharacterAttribute(int param2Int) {
/* 1169 */         if (AccessibleHTML.this.model instanceof StyledDocument) {
/* 1170 */           StyledDocument styledDocument = (StyledDocument)AccessibleHTML.this.model;
/* 1171 */           Element element = styledDocument.getCharacterElement(param2Int);
/* 1172 */           if (element != null) {
/* 1173 */             return element.getAttributes();
/*      */           }
/*      */         } 
/* 1176 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getSelectionStart() {
/* 1187 */         return AccessibleHTML.this.editor.getSelectionStart();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getSelectionEnd() {
/* 1198 */         return AccessibleHTML.this.editor.getSelectionEnd();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String getSelectedText() {
/* 1207 */         return AccessibleHTML.this.editor.getSelectedText();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private String getText(int param2Int1, int param2Int2) throws BadLocationException {
/* 1217 */         if (AccessibleHTML.this.model != null && AccessibleHTML.this.model instanceof StyledDocument) {
/* 1218 */           StyledDocument styledDocument = (StyledDocument)AccessibleHTML.this.model;
/* 1219 */           return AccessibleHTML.this.model.getText(param2Int1, param2Int2);
/*      */         } 
/* 1221 */         return null;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class IconElementInfo
/*      */     extends ElementInfo
/*      */     implements Accessible
/*      */   {
/* 1232 */     private int width = -1;
/* 1233 */     private int height = -1; private AccessibleContext accessibleContext;
/*      */     
/*      */     IconElementInfo(Element param1Element, AccessibleHTML.ElementInfo param1ElementInfo) {
/* 1236 */       super(param1Element, param1ElementInfo);
/*      */     }
/*      */     
/*      */     protected void invalidate(boolean param1Boolean) {
/* 1240 */       super.invalidate(param1Boolean);
/* 1241 */       this.width = this.height = -1;
/*      */     }
/*      */     
/*      */     private int getImageSize(Object param1Object) {
/* 1245 */       if (validateIfNecessary()) {
/* 1246 */         int i = getIntAttr(getAttributes(), param1Object, -1);
/*      */         
/* 1248 */         if (i == -1) {
/* 1249 */           View view = getView();
/*      */           
/* 1251 */           i = 0;
/* 1252 */           if (view instanceof ImageView) {
/* 1253 */             Image image = ((ImageView)view).getImage();
/* 1254 */             if (image != null) {
/* 1255 */               if (param1Object == HTML.Attribute.WIDTH) {
/* 1256 */                 i = image.getWidth(null);
/*      */               } else {
/*      */                 
/* 1259 */                 i = image.getHeight(null);
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/* 1264 */         return i;
/*      */       } 
/* 1266 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleContext getAccessibleContext() {
/* 1273 */       if (this.accessibleContext == null) {
/* 1274 */         this.accessibleContext = new IconAccessibleContext(this);
/*      */       }
/* 1276 */       return this.accessibleContext;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected class IconAccessibleContext
/*      */       extends AccessibleHTML.HTMLAccessibleContext
/*      */       implements AccessibleIcon
/*      */     {
/*      */       public IconAccessibleContext(AccessibleHTML.ElementInfo param2ElementInfo) {
/* 1286 */         super(param2ElementInfo);
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
/*      */       
/*      */       public String getAccessibleName() {
/* 1305 */         return getAccessibleIconDescription();
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
/*      */       public String getAccessibleDescription() {
/* 1319 */         return AccessibleHTML.this.editor.getContentType();
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
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleRole getAccessibleRole() {
/* 1341 */         return AccessibleRole.ICON;
/*      */       }
/*      */       
/*      */       public AccessibleIcon[] getAccessibleIcon() {
/* 1345 */         AccessibleIcon[] arrayOfAccessibleIcon = new AccessibleIcon[1];
/* 1346 */         arrayOfAccessibleIcon[0] = this;
/* 1347 */         return arrayOfAccessibleIcon;
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
/*      */       public String getAccessibleIconDescription() {
/* 1359 */         return ((ImageView)AccessibleHTML.IconElementInfo.this.getView()).getAltText();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleIconDescription(String param2String) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleIconWidth() {
/* 1379 */         if (AccessibleHTML.IconElementInfo.this.width == -1) {
/* 1380 */           AccessibleHTML.IconElementInfo.this.width = AccessibleHTML.IconElementInfo.this.getImageSize(HTML.Attribute.WIDTH);
/*      */         }
/* 1382 */         return AccessibleHTML.IconElementInfo.this.width;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleIconHeight() {
/* 1391 */         if (AccessibleHTML.IconElementInfo.this.height == -1) {
/* 1392 */           AccessibleHTML.IconElementInfo.this.height = AccessibleHTML.IconElementInfo.this.getImageSize(HTML.Attribute.HEIGHT);
/*      */         }
/* 1394 */         return AccessibleHTML.IconElementInfo.this.height;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class TableElementInfo
/*      */     extends ElementInfo
/*      */     implements Accessible
/*      */   {
/*      */     protected AccessibleHTML.ElementInfo caption;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private TableCellElementInfo[][] grid;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private AccessibleContext accessibleContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TableElementInfo(Element param1Element, AccessibleHTML.ElementInfo param1ElementInfo) {
/* 1424 */       super(param1Element, param1ElementInfo);
/*      */     }
/*      */     
/*      */     public AccessibleHTML.ElementInfo getCaptionInfo() {
/* 1428 */       return this.caption;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void validate() {
/* 1435 */       super.validate();
/* 1436 */       updateGrid();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void loadChildren(Element param1Element) {
/* 1444 */       for (byte b = 0; b < param1Element.getElementCount(); b++) {
/* 1445 */         Element element = param1Element.getElement(b);
/* 1446 */         AttributeSet attributeSet = element.getAttributes();
/*      */         
/* 1448 */         if (attributeSet.getAttribute(StyleConstants.NameAttribute) == HTML.Tag.TR) {
/*      */           
/* 1450 */           addChild(new TableRowElementInfo(element, this, b));
/*      */         }
/* 1452 */         else if (attributeSet.getAttribute(StyleConstants.NameAttribute) == HTML.Tag.CAPTION) {
/*      */ 
/*      */ 
/*      */           
/* 1456 */           this.caption = AccessibleHTML.this.createElementInfo(element, this);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void updateGrid() {
/* 1466 */       int i = 0;
/* 1467 */       int j = 0;
/*      */       byte b;
/* 1469 */       for (b = 0; b < getChildCount(); b++) {
/* 1470 */         TableRowElementInfo tableRowElementInfo = getRow(b);
/* 1471 */         int m = 0;
/* 1472 */         for (byte b1 = 0; b1 < i; b1++) {
/* 1473 */           m = Math.max(m, getRow(b - b1 - 1).getColumnCount(b1 + 2));
/*      */         }
/*      */         
/* 1476 */         i = Math.max(tableRowElementInfo.getRowCount(), i);
/* 1477 */         i--;
/* 1478 */         j = Math.max(j, tableRowElementInfo.getColumnCount() + m);
/*      */       } 
/* 1480 */       int k = getChildCount() + i;
/*      */ 
/*      */       
/* 1483 */       this.grid = new TableCellElementInfo[k][];
/* 1484 */       for (b = 0; b < k; b++) {
/* 1485 */         this.grid[b] = new TableCellElementInfo[j];
/*      */       }
/*      */       
/* 1488 */       for (b = 0; b < k; b++) {
/* 1489 */         getRow(b).updateGrid(b);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TableRowElementInfo getRow(int param1Int) {
/* 1497 */       return (TableRowElementInfo)getChild(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TableCellElementInfo getCell(int param1Int1, int param1Int2) {
/* 1504 */       if (validateIfNecessary() && param1Int1 < this.grid.length && param1Int2 < (this.grid[0]).length)
/*      */       {
/* 1506 */         return this.grid[param1Int1][param1Int2];
/*      */       }
/* 1508 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getRowExtentAt(int param1Int1, int param1Int2) {
/* 1515 */       TableCellElementInfo tableCellElementInfo = getCell(param1Int1, param1Int2);
/*      */       
/* 1517 */       if (tableCellElementInfo != null) {
/* 1518 */         int i = tableCellElementInfo.getRowCount();
/* 1519 */         byte b = 1;
/*      */         
/* 1521 */         while (param1Int1 - b >= 0 && this.grid[param1Int1 - b][param1Int2] == tableCellElementInfo) {
/* 1522 */           b++;
/*      */         }
/* 1524 */         return i - b + 1;
/*      */       } 
/* 1526 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getColumnExtentAt(int param1Int1, int param1Int2) {
/* 1533 */       TableCellElementInfo tableCellElementInfo = getCell(param1Int1, param1Int2);
/*      */       
/* 1535 */       if (tableCellElementInfo != null) {
/* 1536 */         int i = tableCellElementInfo.getColumnCount();
/* 1537 */         byte b = 1;
/*      */         
/* 1539 */         while (param1Int2 - b >= 0 && this.grid[param1Int1][param1Int2 - b] == tableCellElementInfo) {
/* 1540 */           b++;
/*      */         }
/* 1542 */         return i - b + 1;
/*      */       } 
/* 1544 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getRowCount() {
/* 1551 */       if (validateIfNecessary()) {
/* 1552 */         return this.grid.length;
/*      */       }
/* 1554 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getColumnCount() {
/* 1561 */       if (validateIfNecessary() && this.grid.length > 0) {
/* 1562 */         return (this.grid[0]).length;
/*      */       }
/* 1564 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleContext getAccessibleContext() {
/* 1571 */       if (this.accessibleContext == null) {
/* 1572 */         this.accessibleContext = new TableAccessibleContext(this);
/*      */       }
/* 1574 */       return this.accessibleContext;
/*      */     }
/*      */ 
/*      */     
/*      */     public class TableAccessibleContext
/*      */       extends AccessibleHTML.HTMLAccessibleContext
/*      */       implements AccessibleTable
/*      */     {
/*      */       private AccessibleHeadersTable rowHeadersTable;
/*      */ 
/*      */       
/*      */       public TableAccessibleContext(AccessibleHTML.ElementInfo param2ElementInfo) {
/* 1586 */         super(param2ElementInfo);
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
/*      */ 
/*      */       
/*      */       public String getAccessibleName() {
/* 1606 */         return getAccessibleRole().toString();
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
/*      */       public String getAccessibleDescription() {
/* 1620 */         return AccessibleHTML.this.editor.getContentType();
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
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleRole getAccessibleRole() {
/* 1642 */         return AccessibleRole.TABLE;
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
/*      */       public int getAccessibleIndexInParent() {
/* 1656 */         return this.elementInfo.getIndexInParent();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleChildrenCount() {
/* 1665 */         return ((AccessibleHTML.TableElementInfo)this.elementInfo).getRowCount() * ((AccessibleHTML.TableElementInfo)this.elementInfo)
/* 1666 */           .getColumnCount();
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
/*      */       public Accessible getAccessibleChild(int param2Int) {
/* 1680 */         int i = ((AccessibleHTML.TableElementInfo)this.elementInfo).getRowCount();
/* 1681 */         int j = ((AccessibleHTML.TableElementInfo)this.elementInfo).getColumnCount();
/* 1682 */         int k = param2Int / i;
/* 1683 */         int m = param2Int % j;
/* 1684 */         if (k < 0 || k >= i || m < 0 || m >= j) {
/* 1685 */           return null;
/*      */         }
/* 1687 */         return getAccessibleAt(k, m);
/*      */       }
/*      */ 
/*      */       
/*      */       public AccessibleTable getAccessibleTable() {
/* 1692 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleCaption() {
/* 1701 */         AccessibleHTML.ElementInfo elementInfo = AccessibleHTML.TableElementInfo.this.getCaptionInfo();
/* 1702 */         if (elementInfo instanceof Accessible) {
/* 1703 */           return (Accessible)AccessibleHTML.TableElementInfo.this.caption;
/*      */         }
/* 1705 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleCaption(Accessible param2Accessible) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleSummary() {
/* 1723 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleSummary(Accessible param2Accessible) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleRowCount() {
/* 1740 */         return ((AccessibleHTML.TableElementInfo)this.elementInfo).getRowCount();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleColumnCount() {
/* 1749 */         return ((AccessibleHTML.TableElementInfo)this.elementInfo).getColumnCount();
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
/*      */       public Accessible getAccessibleAt(int param2Int1, int param2Int2) {
/* 1761 */         AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo = AccessibleHTML.TableElementInfo.this.getCell(param2Int1, param2Int2);
/* 1762 */         if (tableCellElementInfo != null) {
/* 1763 */           return tableCellElementInfo.getAccessible();
/*      */         }
/* 1765 */         return null;
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
/*      */       public int getAccessibleRowExtentAt(int param2Int1, int param2Int2) {
/* 1777 */         return ((AccessibleHTML.TableElementInfo)this.elementInfo).getRowExtentAt(param2Int1, param2Int2);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleColumnExtentAt(int param2Int1, int param2Int2) {
/* 1788 */         return ((AccessibleHTML.TableElementInfo)this.elementInfo).getColumnExtentAt(param2Int1, param2Int2);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleTable getAccessibleRowHeader() {
/* 1798 */         return this.rowHeadersTable;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleRowHeader(AccessibleTable param2AccessibleTable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleTable getAccessibleColumnHeader() {
/* 1817 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleColumnHeader(AccessibleTable param2AccessibleTable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleRowDescription(int param2Int) {
/* 1836 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleRowDescription(int param2Int, Accessible param2Accessible) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleColumnDescription(int param2Int) {
/* 1855 */         return null;
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
/*      */       public void setAccessibleColumnDescription(int param2Int, Accessible param2Accessible) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isAccessibleSelected(int param2Int1, int param2Int2) {
/* 1878 */         if (AccessibleHTML.TableElementInfo.this.validateIfNecessary()) {
/* 1879 */           if (param2Int1 < 0 || param2Int1 >= getAccessibleRowCount() || param2Int2 < 0 || param2Int2 >= 
/* 1880 */             getAccessibleColumnCount()) {
/* 1881 */             return false;
/*      */           }
/* 1883 */           AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo = AccessibleHTML.TableElementInfo.this.getCell(param2Int1, param2Int2);
/* 1884 */           if (tableCellElementInfo != null) {
/* 1885 */             Element element = tableCellElementInfo.getElement();
/* 1886 */             int i = element.getStartOffset();
/* 1887 */             int j = element.getEndOffset();
/* 1888 */             return (i >= AccessibleHTML.this.editor.getSelectionStart() && j <= AccessibleHTML.this
/* 1889 */               .editor.getSelectionEnd());
/*      */           } 
/*      */         } 
/* 1892 */         return false;
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
/*      */       public boolean isAccessibleRowSelected(int param2Int) {
/* 1904 */         if (AccessibleHTML.TableElementInfo.this.validateIfNecessary()) {
/* 1905 */           if (param2Int < 0 || param2Int >= getAccessibleRowCount()) {
/* 1906 */             return false;
/*      */           }
/* 1908 */           int i = getAccessibleColumnCount();
/*      */           
/* 1910 */           AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo1 = AccessibleHTML.TableElementInfo.this.getCell(param2Int, 0);
/* 1911 */           if (tableCellElementInfo1 == null) {
/* 1912 */             return false;
/*      */           }
/* 1914 */           int j = tableCellElementInfo1.getElement().getStartOffset();
/*      */           
/* 1916 */           AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo2 = AccessibleHTML.TableElementInfo.this.getCell(param2Int, i - 1);
/* 1917 */           if (tableCellElementInfo2 == null) {
/* 1918 */             return false;
/*      */           }
/* 1920 */           int k = tableCellElementInfo2.getElement().getEndOffset();
/*      */           
/* 1922 */           return (j >= AccessibleHTML.this.editor.getSelectionStart() && k <= AccessibleHTML.this
/* 1923 */             .editor.getSelectionEnd());
/*      */         } 
/* 1925 */         return false;
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
/*      */       public boolean isAccessibleColumnSelected(int param2Int) {
/* 1937 */         if (AccessibleHTML.TableElementInfo.this.validateIfNecessary()) {
/* 1938 */           if (param2Int < 0 || param2Int >= getAccessibleColumnCount()) {
/* 1939 */             return false;
/*      */           }
/* 1941 */           int i = getAccessibleRowCount();
/*      */           
/* 1943 */           AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo1 = AccessibleHTML.TableElementInfo.this.getCell(0, param2Int);
/* 1944 */           if (tableCellElementInfo1 == null) {
/* 1945 */             return false;
/*      */           }
/* 1947 */           int j = tableCellElementInfo1.getElement().getStartOffset();
/*      */           
/* 1949 */           AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo2 = AccessibleHTML.TableElementInfo.this.getCell(i - 1, param2Int);
/* 1950 */           if (tableCellElementInfo2 == null) {
/* 1951 */             return false;
/*      */           }
/* 1953 */           int k = tableCellElementInfo2.getElement().getEndOffset();
/* 1954 */           return (j >= AccessibleHTML.this.editor.getSelectionStart() && k <= AccessibleHTML.this
/* 1955 */             .editor.getSelectionEnd());
/*      */         } 
/* 1957 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int[] getSelectedAccessibleRows() {
/* 1967 */         if (AccessibleHTML.TableElementInfo.this.validateIfNecessary()) {
/* 1968 */           int i = getAccessibleRowCount();
/* 1969 */           Vector<Integer> vector = new Vector();
/*      */           
/* 1971 */           for (byte b1 = 0; b1 < i; b1++) {
/* 1972 */             if (isAccessibleRowSelected(b1)) {
/* 1973 */               vector.addElement(Integer.valueOf(b1));
/*      */             }
/*      */           } 
/* 1976 */           int[] arrayOfInt = new int[vector.size()];
/* 1977 */           for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
/* 1978 */             arrayOfInt[b2] = ((Integer)vector.elementAt(b2)).intValue();
/*      */           }
/* 1980 */           return arrayOfInt;
/*      */         } 
/* 1982 */         return new int[0];
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int[] getSelectedAccessibleColumns() {
/* 1992 */         if (AccessibleHTML.TableElementInfo.this.validateIfNecessary()) {
/* 1993 */           int i = getAccessibleRowCount();
/* 1994 */           Vector<Integer> vector = new Vector();
/*      */           
/* 1996 */           for (byte b1 = 0; b1 < i; b1++) {
/* 1997 */             if (isAccessibleColumnSelected(b1)) {
/* 1998 */               vector.addElement(Integer.valueOf(b1));
/*      */             }
/*      */           } 
/* 2001 */           int[] arrayOfInt = new int[vector.size()];
/* 2002 */           for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
/* 2003 */             arrayOfInt[b2] = ((Integer)vector.elementAt(b2)).intValue();
/*      */           }
/* 2005 */           return arrayOfInt;
/*      */         } 
/* 2007 */         return new int[0];
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
/*      */       public int getAccessibleRow(int param2Int) {
/* 2020 */         if (AccessibleHTML.TableElementInfo.this.validateIfNecessary()) {
/*      */           
/* 2022 */           int i = getAccessibleColumnCount() * getAccessibleRowCount();
/* 2023 */           if (param2Int >= i) {
/* 2024 */             return -1;
/*      */           }
/* 2026 */           return param2Int / getAccessibleColumnCount();
/*      */         } 
/*      */         
/* 2029 */         return -1;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleColumn(int param2Int) {
/* 2040 */         if (AccessibleHTML.TableElementInfo.this.validateIfNecessary()) {
/*      */           
/* 2042 */           int i = getAccessibleColumnCount() * getAccessibleRowCount();
/* 2043 */           if (param2Int >= i) {
/* 2044 */             return -1;
/*      */           }
/* 2046 */           return param2Int % getAccessibleColumnCount();
/*      */         } 
/*      */         
/* 2049 */         return -1;
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
/*      */       public int getAccessibleIndex(int param2Int1, int param2Int2) {
/* 2061 */         if (AccessibleHTML.TableElementInfo.this.validateIfNecessary()) {
/* 2062 */           if (param2Int1 >= getAccessibleRowCount() || param2Int2 >= 
/* 2063 */             getAccessibleColumnCount()) {
/* 2064 */             return -1;
/*      */           }
/* 2066 */           return param2Int1 * getAccessibleColumnCount() + param2Int2;
/*      */         } 
/*      */         
/* 2069 */         return -1;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String getAccessibleRowHeader(int param2Int) {
/* 2080 */         if (AccessibleHTML.TableElementInfo.this.validateIfNecessary()) {
/* 2081 */           AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo = AccessibleHTML.TableElementInfo.this.getCell(param2Int, 0);
/* 2082 */           if (tableCellElementInfo.isHeaderCell()) {
/* 2083 */             View view = tableCellElementInfo.getView();
/* 2084 */             if (view != null && AccessibleHTML.this.model != null) {
/*      */               try {
/* 2086 */                 return AccessibleHTML.this.model.getText(view.getStartOffset(), view
/* 2087 */                     .getEndOffset() - view
/* 2088 */                     .getStartOffset());
/* 2089 */               } catch (BadLocationException badLocationException) {
/* 2090 */                 return null;
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/* 2095 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String getAccessibleColumnHeader(int param2Int) {
/* 2106 */         if (AccessibleHTML.TableElementInfo.this.validateIfNecessary()) {
/* 2107 */           AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo = AccessibleHTML.TableElementInfo.this.getCell(0, param2Int);
/* 2108 */           if (tableCellElementInfo.isHeaderCell()) {
/* 2109 */             View view = tableCellElementInfo.getView();
/* 2110 */             if (view != null && AccessibleHTML.this.model != null) {
/*      */               try {
/* 2112 */                 return AccessibleHTML.this.model.getText(view.getStartOffset(), view
/* 2113 */                     .getEndOffset() - view
/* 2114 */                     .getStartOffset());
/* 2115 */               } catch (BadLocationException badLocationException) {
/* 2116 */                 return null;
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/* 2121 */         return null;
/*      */       }
/*      */       
/*      */       public void addRowHeader(AccessibleHTML.TableElementInfo.TableCellElementInfo param2TableCellElementInfo, int param2Int) {
/* 2125 */         if (this.rowHeadersTable == null) {
/* 2126 */           this.rowHeadersTable = new AccessibleHeadersTable();
/*      */         }
/* 2128 */         this.rowHeadersTable.addHeader(param2TableCellElementInfo, param2Int);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected class AccessibleHeadersTable
/*      */         implements AccessibleTable
/*      */       {
/* 2137 */         private Hashtable<Integer, ArrayList<AccessibleHTML.TableElementInfo.TableCellElementInfo>> headers = new Hashtable<>();
/*      */         
/* 2139 */         private int rowCount = 0;
/* 2140 */         private int columnCount = 0;
/*      */         
/*      */         public void addHeader(AccessibleHTML.TableElementInfo.TableCellElementInfo param3TableCellElementInfo, int param3Int) {
/* 2143 */           Integer integer = Integer.valueOf(param3Int);
/* 2144 */           ArrayList<AccessibleHTML.TableElementInfo.TableCellElementInfo> arrayList = this.headers.get(integer);
/* 2145 */           if (arrayList == null) {
/* 2146 */             arrayList = new ArrayList();
/* 2147 */             this.headers.put(integer, arrayList);
/*      */           } 
/* 2149 */           arrayList.add(param3TableCellElementInfo);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public Accessible getAccessibleCaption() {
/* 2158 */           return null;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void setAccessibleCaption(Accessible param3Accessible) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public Accessible getAccessibleSummary() {
/* 2175 */           return null;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void setAccessibleSummary(Accessible param3Accessible) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public int getAccessibleRowCount() {
/* 2192 */           return this.rowCount;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public int getAccessibleColumnCount() {
/* 2201 */           return this.columnCount;
/*      */         }
/*      */         
/*      */         private AccessibleHTML.TableElementInfo.TableCellElementInfo getElementInfoAt(int param3Int1, int param3Int2) {
/* 2205 */           ArrayList<AccessibleHTML.TableElementInfo.TableCellElementInfo> arrayList = this.headers.get(Integer.valueOf(param3Int1));
/* 2206 */           if (arrayList != null) {
/* 2207 */             return arrayList.get(param3Int2);
/*      */           }
/* 2209 */           return null;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public Accessible getAccessibleAt(int param3Int1, int param3Int2) {
/* 2222 */           AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo = getElementInfoAt(param3Int1, param3Int2);
/* 2223 */           if (tableCellElementInfo instanceof Accessible) {
/* 2224 */             return (Accessible)tableCellElementInfo;
/*      */           }
/* 2226 */           return null;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public int getAccessibleRowExtentAt(int param3Int1, int param3Int2) {
/* 2238 */           AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo = getElementInfoAt(param3Int1, param3Int2);
/* 2239 */           if (tableCellElementInfo != null) {
/* 2240 */             return tableCellElementInfo.getRowCount();
/*      */           }
/* 2242 */           return 0;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public int getAccessibleColumnExtentAt(int param3Int1, int param3Int2) {
/* 2254 */           AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo = getElementInfoAt(param3Int1, param3Int2);
/* 2255 */           if (tableCellElementInfo != null) {
/* 2256 */             return tableCellElementInfo.getRowCount();
/*      */           }
/* 2258 */           return 0;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public AccessibleTable getAccessibleRowHeader() {
/* 2269 */           return null;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void setAccessibleRowHeader(AccessibleTable param3AccessibleTable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public AccessibleTable getAccessibleColumnHeader() {
/* 2288 */           return null;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void setAccessibleColumnHeader(AccessibleTable param3AccessibleTable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public Accessible getAccessibleRowDescription(int param3Int) {
/* 2307 */           return null;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void setAccessibleRowDescription(int param3Int, Accessible param3Accessible) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public Accessible getAccessibleColumnDescription(int param3Int) {
/* 2326 */           return null;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void setAccessibleColumnDescription(int param3Int, Accessible param3Accessible) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public boolean isAccessibleSelected(int param3Int1, int param3Int2) {
/* 2349 */           return false;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public boolean isAccessibleRowSelected(int param3Int) {
/* 2361 */           return false;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public boolean isAccessibleColumnSelected(int param3Int) {
/* 2373 */           return false;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public int[] getSelectedAccessibleRows() {
/* 2383 */           return new int[0];
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public int[] getSelectedAccessibleColumns() {
/* 2393 */           return new int[0];
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private class TableRowElementInfo
/*      */       extends AccessibleHTML.ElementInfo
/*      */     {
/*      */       private AccessibleHTML.TableElementInfo parent;
/*      */       
/*      */       private int rowNumber;
/*      */       
/*      */       TableRowElementInfo(Element param2Element, AccessibleHTML.TableElementInfo param2TableElementInfo1, int param2Int) {
/* 2407 */         super(param2Element, param2TableElementInfo1);
/* 2408 */         this.parent = param2TableElementInfo1;
/* 2409 */         this.rowNumber = param2Int;
/*      */       }
/*      */       
/*      */       protected void loadChildren(Element param2Element) {
/* 2413 */         for (byte b = 0; b < param2Element.getElementCount(); b++) {
/* 2414 */           AttributeSet attributeSet = param2Element.getElement(b).getAttributes();
/*      */           
/* 2416 */           if (attributeSet.getAttribute(StyleConstants.NameAttribute) == HTML.Tag.TH) {
/*      */ 
/*      */             
/* 2419 */             AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo = new AccessibleHTML.TableElementInfo.TableCellElementInfo(param2Element.getElement(b), this, true);
/* 2420 */             addChild(tableCellElementInfo);
/*      */ 
/*      */             
/* 2423 */             AccessibleTable accessibleTable = this.parent.getAccessibleContext().getAccessibleTable();
/* 2424 */             AccessibleHTML.TableElementInfo.TableAccessibleContext tableAccessibleContext = (AccessibleHTML.TableElementInfo.TableAccessibleContext)accessibleTable;
/*      */             
/* 2426 */             tableAccessibleContext.addRowHeader(tableCellElementInfo, this.rowNumber);
/*      */           }
/* 2428 */           else if (attributeSet.getAttribute(StyleConstants.NameAttribute) == HTML.Tag.TD) {
/*      */             
/* 2430 */             addChild(new AccessibleHTML.TableElementInfo.TableCellElementInfo(param2Element.getElement(b), this, false));
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getRowCount() {
/* 2440 */         int i = 1;
/* 2441 */         if (validateIfNecessary()) {
/* 2442 */           for (byte b = 0; b < getChildCount(); 
/* 2443 */             b++) {
/*      */ 
/*      */             
/* 2446 */             AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo = (AccessibleHTML.TableElementInfo.TableCellElementInfo)getChild(b);
/*      */             
/* 2448 */             if (tableCellElementInfo.validateIfNecessary()) {
/* 2449 */               i = Math.max(i, tableCellElementInfo.getRowCount());
/*      */             }
/*      */           } 
/*      */         }
/* 2453 */         return i;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getColumnCount() {
/* 2461 */         int i = 0;
/* 2462 */         if (validateIfNecessary()) {
/* 2463 */           for (byte b = 0; b < getChildCount(); 
/* 2464 */             b++) {
/*      */             
/* 2466 */             AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo = (AccessibleHTML.TableElementInfo.TableCellElementInfo)getChild(b);
/*      */             
/* 2468 */             if (tableCellElementInfo.validateIfNecessary()) {
/* 2469 */               i += tableCellElementInfo.getColumnCount();
/*      */             }
/*      */           } 
/*      */         }
/* 2473 */         return i;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected void invalidate(boolean param2Boolean) {
/* 2481 */         super.invalidate(param2Boolean);
/* 2482 */         getParent().invalidate(true);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private void updateGrid(int param2Int) {
/* 2490 */         if (validateIfNecessary()) {
/* 2491 */           boolean bool = false;
/*      */           
/* 2493 */           while (!bool) {
/* 2494 */             for (byte b1 = 0; b1 < (AccessibleHTML.TableElementInfo.this.grid[param2Int]).length; 
/* 2495 */               b1++) {
/* 2496 */               if (AccessibleHTML.TableElementInfo.this.grid[param2Int][b1] == null) {
/* 2497 */                 bool = true;
/*      */                 break;
/*      */               } 
/*      */             } 
/* 2501 */             if (!bool)
/* 2502 */               param2Int++; 
/*      */           }  int i;
/*      */           byte b;
/* 2505 */           for (i = 0, b = 0; b < getChildCount(); 
/* 2506 */             b++) {
/*      */             
/* 2508 */             AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo = (AccessibleHTML.TableElementInfo.TableCellElementInfo)getChild(b);
/*      */             
/* 2510 */             while (AccessibleHTML.TableElementInfo.this.grid[param2Int][i] != null) {
/* 2511 */               i++;
/*      */             }
/* 2513 */             int j = tableCellElementInfo.getRowCount() - 1;
/* 2514 */             for (; j >= 0; j--) {
/* 2515 */               int k = tableCellElementInfo.getColumnCount() - 1;
/* 2516 */               for (; k >= 0; k--) {
/* 2517 */                 AccessibleHTML.TableElementInfo.this.grid[param2Int + j][i + k] = tableCellElementInfo;
/*      */               }
/*      */             } 
/* 2520 */             i += tableCellElementInfo.getColumnCount();
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private int getColumnCount(int param2Int) {
/* 2530 */         if (validateIfNecessary()) {
/* 2531 */           int i = 0;
/* 2532 */           for (byte b = 0; b < getChildCount(); 
/* 2533 */             b++) {
/*      */             
/* 2535 */             AccessibleHTML.TableElementInfo.TableCellElementInfo tableCellElementInfo = (AccessibleHTML.TableElementInfo.TableCellElementInfo)getChild(b);
/*      */             
/* 2537 */             if (tableCellElementInfo.getRowCount() >= param2Int) {
/* 2538 */               i += tableCellElementInfo.getColumnCount();
/*      */             }
/*      */           } 
/* 2541 */           return i;
/*      */         } 
/* 2543 */         return 0;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private class TableCellElementInfo
/*      */       extends AccessibleHTML.ElementInfo
/*      */     {
/*      */       private Accessible accessible;
/*      */       
/*      */       private boolean isHeaderCell;
/*      */ 
/*      */       
/*      */       TableCellElementInfo(Element param2Element, AccessibleHTML.ElementInfo param2ElementInfo) {
/* 2557 */         super(param2Element, param2ElementInfo);
/* 2558 */         this.isHeaderCell = false;
/*      */       }
/*      */ 
/*      */       
/*      */       TableCellElementInfo(Element param2Element, AccessibleHTML.ElementInfo param2ElementInfo, boolean param2Boolean) {
/* 2563 */         super(param2Element, param2ElementInfo);
/* 2564 */         this.isHeaderCell = param2Boolean;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isHeaderCell() {
/* 2571 */         return this.isHeaderCell;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Accessible getAccessible() {
/* 2578 */         this.accessible = null;
/* 2579 */         getAccessible(this);
/* 2580 */         return this.accessible;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private void getAccessible(AccessibleHTML.ElementInfo param2ElementInfo) {
/* 2587 */         if (param2ElementInfo instanceof Accessible) {
/* 2588 */           this.accessible = (Accessible)param2ElementInfo;
/*      */         } else {
/* 2590 */           for (byte b = 0; b < param2ElementInfo.getChildCount(); b++) {
/* 2591 */             getAccessible(param2ElementInfo.getChild(b));
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getRowCount() {
/* 2600 */         if (validateIfNecessary()) {
/* 2601 */           return Math.max(1, getIntAttr(getAttributes(), HTML.Attribute.ROWSPAN, 1));
/*      */         }
/*      */         
/* 2604 */         return 0;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getColumnCount() {
/* 2611 */         if (validateIfNecessary()) {
/* 2612 */           return Math.max(1, getIntAttr(getAttributes(), HTML.Attribute.COLSPAN, 1));
/*      */         }
/*      */         
/* 2615 */         return 0;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected void invalidate(boolean param2Boolean) {
/* 2623 */         super.invalidate(param2Boolean);
/* 2624 */         getParent().invalidate(true);
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
/*      */   private class ElementInfo
/*      */   {
/*      */     private ArrayList<ElementInfo> children;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Element element;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ElementInfo parent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isValid;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canBeValid;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ElementInfo(Element param1Element) {
/* 2669 */       this(param1Element, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ElementInfo(Element param1Element, ElementInfo param1ElementInfo) {
/* 2677 */       this.element = param1Element;
/* 2678 */       this.parent = param1ElementInfo;
/* 2679 */       this.isValid = false;
/* 2680 */       this.canBeValid = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void validate() {
/* 2689 */       this.isValid = true;
/* 2690 */       loadChildren(getElement());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void loadChildren(Element param1Element) {
/* 2697 */       if (!param1Element.isLeaf()) {
/* 2698 */         byte b = 0; int i = param1Element.getElementCount();
/* 2699 */         for (; b < i; b++) {
/* 2700 */           Element element = param1Element.getElement(b);
/* 2701 */           ElementInfo elementInfo = AccessibleHTML.this.createElementInfo(element, this);
/*      */           
/* 2703 */           if (elementInfo != null) {
/* 2704 */             addChild(elementInfo);
/*      */           } else {
/*      */             
/* 2707 */             loadChildren(element);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getIndexInParent() {
/* 2718 */       if (this.parent == null || !this.parent.isValid()) {
/* 2719 */         return -1;
/*      */       }
/* 2721 */       return this.parent.indexOf(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element getElement() {
/* 2728 */       return this.element;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ElementInfo getParent() {
/* 2735 */       return this.parent;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int indexOf(ElementInfo param1ElementInfo) {
/* 2743 */       ArrayList<ElementInfo> arrayList = this.children;
/*      */       
/* 2745 */       if (arrayList != null) {
/* 2746 */         return arrayList.indexOf(param1ElementInfo);
/*      */       }
/* 2748 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ElementInfo getChild(int param1Int) {
/* 2756 */       if (validateIfNecessary()) {
/* 2757 */         ArrayList<ElementInfo> arrayList = this.children;
/*      */         
/* 2759 */         if (arrayList != null && param1Int >= 0 && param1Int < arrayList
/* 2760 */           .size()) {
/* 2761 */           return arrayList.get(param1Int);
/*      */         }
/*      */       } 
/* 2764 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getChildCount() {
/* 2771 */       validateIfNecessary();
/* 2772 */       return (this.children == null) ? 0 : this.children.size();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void addChild(ElementInfo param1ElementInfo) {
/* 2779 */       if (this.children == null) {
/* 2780 */         this.children = new ArrayList<>();
/*      */       }
/* 2782 */       this.children.add(param1ElementInfo);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected View getView() {
/* 2790 */       if (!validateIfNecessary()) {
/* 2791 */         return null;
/*      */       }
/* 2793 */       Object object = AccessibleHTML.this.lock();
/*      */       try {
/* 2795 */         View view = AccessibleHTML.this.getRootView();
/* 2796 */         Element element = getElement();
/* 2797 */         int i = element.getStartOffset();
/*      */         
/* 2799 */         if (view != null) {
/* 2800 */           return getView(view, element, i);
/*      */         }
/* 2802 */         return null;
/*      */       } finally {
/* 2804 */         AccessibleHTML.this.unlock(object);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Rectangle getBounds() {
/* 2813 */       if (!validateIfNecessary()) {
/* 2814 */         return null;
/*      */       }
/* 2816 */       Object object = AccessibleHTML.this.lock();
/*      */       try {
/* 2818 */         Rectangle rectangle = AccessibleHTML.this.getRootEditorRect();
/* 2819 */         View view = AccessibleHTML.this.getRootView();
/* 2820 */         Element element = getElement();
/*      */         
/* 2822 */         if (rectangle != null && view != null) {
/*      */           try {
/* 2824 */             return view.modelToView(element.getStartOffset(), Position.Bias.Forward, element
/*      */                 
/* 2826 */                 .getEndOffset(), Position.Bias.Backward, rectangle)
/*      */               
/* 2828 */               .getBounds();
/* 2829 */           } catch (BadLocationException badLocationException) {}
/*      */         }
/*      */       } finally {
/* 2832 */         AccessibleHTML.this.unlock(object);
/*      */       } 
/* 2834 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean isValid() {
/* 2841 */       return this.isValid;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected AttributeSet getAttributes() {
/* 2849 */       if (validateIfNecessary()) {
/* 2850 */         return getElement().getAttributes();
/*      */       }
/* 2852 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected AttributeSet getViewAttributes() {
/* 2861 */       if (validateIfNecessary()) {
/* 2862 */         View view = getView();
/*      */         
/* 2864 */         if (view != null) {
/* 2865 */           return view.getElement().getAttributes();
/*      */         }
/* 2867 */         return getElement().getAttributes();
/*      */       } 
/* 2869 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getIntAttr(AttributeSet param1AttributeSet, Object param1Object, int param1Int) {
/* 2877 */       if (param1AttributeSet != null && param1AttributeSet.isDefined(param1Object)) {
/*      */         int i;
/* 2879 */         String str = (String)param1AttributeSet.getAttribute(param1Object);
/* 2880 */         if (str == null) {
/* 2881 */           i = param1Int;
/*      */         } else {
/*      */           
/*      */           try {
/* 2885 */             i = Math.max(0, Integer.parseInt(str));
/* 2886 */           } catch (NumberFormatException numberFormatException) {
/* 2887 */             i = param1Int;
/*      */           } 
/*      */         } 
/* 2890 */         return i;
/*      */       } 
/* 2892 */       return param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean validateIfNecessary() {
/* 2903 */       if (!isValid() && this.canBeValid) {
/* 2904 */         this.children = null;
/* 2905 */         Object object = AccessibleHTML.this.lock();
/*      */         
/*      */         try {
/* 2908 */           validate();
/*      */         } finally {
/* 2910 */           AccessibleHTML.this.unlock(object);
/*      */         } 
/*      */       } 
/* 2913 */       return isValid();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void invalidate(boolean param1Boolean) {
/* 2921 */       if (!isValid()) {
/* 2922 */         if (this.canBeValid && !param1Boolean) {
/* 2923 */           this.canBeValid = false;
/*      */         }
/*      */         return;
/*      */       } 
/* 2927 */       this.isValid = false;
/* 2928 */       this.canBeValid = param1Boolean;
/* 2929 */       if (this.children != null) {
/* 2930 */         for (ElementInfo elementInfo : this.children) {
/* 2931 */           elementInfo.invalidate(false);
/*      */         }
/* 2933 */         this.children = null;
/*      */       } 
/*      */     }
/*      */     
/*      */     private View getView(View param1View, Element param1Element, int param1Int) {
/* 2938 */       if (param1View.getElement() == param1Element) {
/* 2939 */         return param1View;
/*      */       }
/* 2941 */       int i = param1View.getViewIndex(param1Int, Position.Bias.Forward);
/*      */       
/* 2943 */       if (i != -1 && i < param1View.getViewCount()) {
/* 2944 */         return getView(param1View.getView(i), param1Element, param1Int);
/*      */       }
/* 2946 */       return null;
/*      */     }
/*      */     
/*      */     private int getClosestInfoIndex(int param1Int) {
/* 2950 */       for (byte b = 0; b < getChildCount(); b++) {
/* 2951 */         ElementInfo elementInfo = getChild(b);
/*      */         
/* 2953 */         if (param1Int < elementInfo.getElement().getEndOffset() || param1Int == elementInfo
/* 2954 */           .getElement().getStartOffset()) {
/* 2955 */           return b;
/*      */         }
/*      */       } 
/* 2958 */       return -1;
/*      */     }
/*      */     
/*      */     private void update(DocumentEvent param1DocumentEvent) {
/* 2962 */       if (!isValid()) {
/*      */         return;
/*      */       }
/* 2965 */       ElementInfo elementInfo = getParent();
/* 2966 */       Element element = getElement();
/*      */       
/*      */       do {
/* 2969 */         DocumentEvent.ElementChange elementChange = param1DocumentEvent.getChange(element);
/* 2970 */         if (elementChange != null) {
/* 2971 */           if (element == getElement()) {
/*      */             
/* 2973 */             invalidate(true);
/*      */           }
/* 2975 */           else if (elementInfo != null) {
/* 2976 */             elementInfo.invalidate((elementInfo == AccessibleHTML.this.getRootInfo()));
/*      */           } 
/*      */           return;
/*      */         } 
/* 2980 */         element = element.getParentElement();
/* 2981 */       } while (elementInfo != null && element != null && element != elementInfo
/* 2982 */         .getElement());
/*      */       
/* 2984 */       if (getChildCount() > 0) {
/* 2985 */         int k; Element element1 = getElement();
/* 2986 */         int i = param1DocumentEvent.getOffset();
/* 2987 */         int j = getClosestInfoIndex(i);
/* 2988 */         if (j == -1 && param1DocumentEvent
/* 2989 */           .getType() == DocumentEvent.EventType.REMOVE && i >= element1
/* 2990 */           .getEndOffset())
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 2995 */           j = getChildCount() - 1;
/*      */         }
/* 2997 */         ElementInfo elementInfo1 = (j >= 0) ? getChild(j) : null;
/* 2998 */         if (elementInfo1 != null && elementInfo1
/* 2999 */           .getElement().getStartOffset() == i && i > 0)
/*      */         {
/*      */           
/* 3002 */           j = Math.max(j - 1, 0);
/*      */         }
/*      */         
/* 3005 */         if (param1DocumentEvent.getType() != DocumentEvent.EventType.REMOVE) {
/* 3006 */           k = getClosestInfoIndex(i + param1DocumentEvent.getLength());
/* 3007 */           if (k < 0) {
/* 3008 */             k = getChildCount() - 1;
/*      */           }
/*      */         } else {
/*      */           
/* 3012 */           k = j;
/*      */           
/* 3014 */           while (k + 1 < getChildCount() && 
/* 3015 */             getChild(k + 1).getElement().getEndOffset() == 
/* 3016 */             getChild(k + 1).getElement().getStartOffset()) {
/* 3017 */             k++;
/*      */           }
/*      */         } 
/* 3020 */         j = Math.max(j, 0);
/*      */ 
/*      */         
/* 3023 */         for (int m = j; m <= k && isValid(); m++) {
/* 3024 */           getChild(m).update(param1DocumentEvent);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class DocumentHandler
/*      */     implements DocumentListener
/*      */   {
/*      */     private DocumentHandler() {}
/*      */     
/*      */     public void insertUpdate(DocumentEvent param1DocumentEvent) {
/* 3037 */       AccessibleHTML.this.getRootInfo().update(param1DocumentEvent);
/*      */     }
/*      */     public void removeUpdate(DocumentEvent param1DocumentEvent) {
/* 3040 */       AccessibleHTML.this.getRootInfo().update(param1DocumentEvent);
/*      */     }
/*      */     public void changedUpdate(DocumentEvent param1DocumentEvent) {
/* 3043 */       AccessibleHTML.this.getRootInfo().update(param1DocumentEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   private class PropertyChangeHandler
/*      */     implements PropertyChangeListener {
/*      */     private PropertyChangeHandler() {}
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 3052 */       if (param1PropertyChangeEvent.getPropertyName().equals("document"))
/*      */       {
/* 3054 */         AccessibleHTML.this.setDocument(AccessibleHTML.this.editor.getDocument());
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/AccessibleHTML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */