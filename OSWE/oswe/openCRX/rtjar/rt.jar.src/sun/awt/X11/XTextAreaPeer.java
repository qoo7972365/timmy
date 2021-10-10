/*      */ package sun.awt.X11;
/*      */ 
/*      */ import com.sun.java.swing.plaf.motif.MotifTextAreaUI;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.SystemColor;
/*      */ import java.awt.TextArea;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.InputMethodEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseWheelEvent;
/*      */ import java.awt.event.TextEvent;
/*      */ import java.awt.im.InputMethodRequests;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.awt.peer.TextAreaPeer;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JScrollBar;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.JViewport;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.TransferHandler;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.border.AbstractBorder;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.border.CompoundBorder;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.event.DocumentListener;
/*      */ import javax.swing.plaf.BorderUIResource;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.plaf.basic.BasicArrowButton;
/*      */ import javax.swing.plaf.basic.BasicScrollBarUI;
/*      */ import javax.swing.plaf.basic.BasicScrollPaneUI;
/*      */ import javax.swing.text.Caret;
/*      */ import javax.swing.text.DefaultCaret;
/*      */ import javax.swing.text.JTextComponent;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.CausedFocusEvent;
/*      */ import sun.awt.SunToolkit;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class XTextAreaPeer
/*      */   extends XComponentPeer
/*      */   implements TextAreaPeer
/*      */ {
/*      */   private final AWTTextPane textPane;
/*      */   private final AWTTextArea jtext;
/*      */   private final boolean firstChangeSkipped;
/*   73 */   private final JavaMouseEventHandler javaMouseEventHandler = new JavaMouseEventHandler(this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XTextAreaPeer(TextArea paramTextArea) {
/*   80 */     super(paramTextArea);
/*      */ 
/*      */ 
/*      */     
/*   84 */     this.target = paramTextArea;
/*      */ 
/*      */ 
/*      */     
/*   88 */     String str = paramTextArea.getText();
/*   89 */     this.jtext = new AWTTextArea(str, this);
/*   90 */     this.jtext.setWrapStyleWord(true);
/*   91 */     this.jtext.getDocument().addDocumentListener(this.jtext);
/*   92 */     XToolkit.specialPeerMap.put(this.jtext, this);
/*   93 */     this.textPane = new AWTTextPane(this.jtext, this, paramTextArea.getParent());
/*      */     
/*   95 */     setBounds(this.x, this.y, this.width, this.height, 3);
/*   96 */     this.textPane.setVisible(true);
/*   97 */     this.textPane.validate();
/*      */     
/*   99 */     AWTAccessor.ComponentAccessor componentAccessor = AWTAccessor.getComponentAccessor();
/*  100 */     this.foreground = componentAccessor.getForeground(paramTextArea);
/*  101 */     if (this.foreground == null) {
/*  102 */       this.foreground = SystemColor.textText;
/*      */     }
/*  104 */     setForeground(this.foreground);
/*      */     
/*  106 */     this.background = componentAccessor.getBackground(paramTextArea);
/*  107 */     if (this.background == null)
/*  108 */       if (paramTextArea.isEditable()) { this.background = SystemColor.text; }
/*  109 */       else { this.background = SystemColor.control; }
/*      */        
/*  111 */     setBackground(this.background);
/*      */     
/*  113 */     if (!paramTextArea.isBackgroundSet())
/*      */     {
/*      */       
/*  116 */       componentAccessor.setBackground(paramTextArea, this.background);
/*      */     }
/*  118 */     if (!paramTextArea.isForegroundSet()) {
/*  119 */       paramTextArea.setForeground(SystemColor.textText);
/*      */     }
/*      */     
/*  122 */     setFont(this.font);
/*      */ 
/*      */     
/*  125 */     setTextImpl(paramTextArea.getText());
/*      */     
/*  127 */     int i = paramTextArea.getSelectionStart();
/*  128 */     int j = paramTextArea.getSelectionEnd();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  133 */     setCaretPosition(Math.min(j, str.length()));
/*  134 */     if (j > i)
/*      */     {
/*  136 */       select(i, j);
/*      */     }
/*  138 */     setEditable(paramTextArea.isEditable());
/*  139 */     setScrollBarVisibility();
/*      */     
/*  141 */     this.firstChangeSkipped = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void dispose() {
/*  146 */     XToolkit.specialPeerMap.remove(this.jtext);
/*      */     
/*  148 */     this.jtext.getCaret().setVisible(false);
/*  149 */     this.jtext.removeNotify();
/*  150 */     this.textPane.removeNotify();
/*  151 */     super.dispose();
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
/*      */   public void pSetCursor(Cursor paramCursor, boolean paramBoolean) {
/*  163 */     if (paramBoolean || this.javaMouseEventHandler == null) {
/*      */       
/*  165 */       super.pSetCursor(paramCursor, true);
/*      */       
/*      */       return;
/*      */     } 
/*  169 */     Point point1 = new Point();
/*  170 */     ((XGlobalCursorManager)XGlobalCursorManager.getCursorManager()).getCursorPos(point1);
/*      */     
/*  172 */     Point point2 = getLocationOnScreen();
/*  173 */     Point point3 = new Point(point1.x - point2.x, point1.y - point2.y);
/*      */     
/*  175 */     this.javaMouseEventHandler.setPointerToUnderPoint(point3);
/*  176 */     this.javaMouseEventHandler.setCursor();
/*      */   }
/*      */   
/*      */   private void setScrollBarVisibility() {
/*  180 */     int i = ((TextArea)this.target).getScrollbarVisibility();
/*  181 */     this.jtext.setLineWrap(false);
/*      */     
/*  183 */     if (i == 3) {
/*  184 */       this.textPane.setHorizontalScrollBarPolicy(31);
/*  185 */       this.textPane.setVerticalScrollBarPolicy(21);
/*  186 */       this.jtext.setLineWrap(true);
/*      */     }
/*  188 */     else if (i == 0) {
/*      */       
/*  190 */       this.textPane.setHorizontalScrollBarPolicy(32);
/*  191 */       this.textPane.setVerticalScrollBarPolicy(22);
/*      */     }
/*  193 */     else if (i == 1) {
/*  194 */       this.textPane.setHorizontalScrollBarPolicy(31);
/*  195 */       this.textPane.setVerticalScrollBarPolicy(22);
/*  196 */       this.jtext.setLineWrap(true);
/*      */     }
/*  198 */     else if (i == 2) {
/*  199 */       this.textPane.setVerticalScrollBarPolicy(21);
/*  200 */       this.textPane.setHorizontalScrollBarPolicy(32);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize() {
/*  209 */     return getMinimumSize(10, 60);
/*      */   }
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize(int paramInt1, int paramInt2) {
/*  214 */     return getMinimumSize(paramInt1, paramInt2);
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
/*      */   public Dimension getMinimumSize(int paramInt1, int paramInt2) {
/*  229 */     int i = 0;
/*  230 */     int j = 0;
/*      */     
/*  232 */     JScrollBar jScrollBar1 = this.textPane.getVerticalScrollBar();
/*  233 */     if (jScrollBar1 != null) {
/*  234 */       i = (jScrollBar1.getMinimumSize()).width;
/*      */     }
/*      */     
/*  237 */     JScrollBar jScrollBar2 = this.textPane.getHorizontalScrollBar();
/*  238 */     if (jScrollBar2 != null) {
/*  239 */       j = (jScrollBar2.getMinimumSize()).height;
/*      */     }
/*      */     
/*  242 */     Font font = this.jtext.getFont();
/*  243 */     FontMetrics fontMetrics = this.jtext.getFontMetrics(font);
/*      */     
/*  245 */     return new Dimension(fontMetrics.charWidth('0') * paramInt2 + i, fontMetrics
/*  246 */         .getHeight() * paramInt1 + j);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFocusable() {
/*  251 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setVisible(boolean paramBoolean) {
/*  256 */     super.setVisible(paramBoolean);
/*  257 */     if (this.textPane != null)
/*  258 */       this.textPane.setVisible(paramBoolean); 
/*      */   }
/*      */   
/*      */   void repaintText() {
/*  262 */     this.jtext.repaintNow();
/*      */   }
/*      */ 
/*      */   
/*      */   public void focusGained(FocusEvent paramFocusEvent) {
/*  267 */     super.focusGained(paramFocusEvent);
/*  268 */     this.jtext.forwardFocusGained(paramFocusEvent);
/*      */   }
/*      */ 
/*      */   
/*      */   public void focusLost(FocusEvent paramFocusEvent) {
/*  273 */     super.focusLost(paramFocusEvent);
/*  274 */     this.jtext.forwardFocusLost(paramFocusEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void repaint() {
/*  283 */     if (this.textPane != null)
/*      */     {
/*  285 */       this.textPane.repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   void paintPeer(Graphics paramGraphics) {
/*  291 */     if (this.textPane != null) {
/*  292 */       this.textPane.paint(paramGraphics);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  298 */     super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*  299 */     if (this.textPane != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  308 */       int i = paramInt1;
/*  309 */       int j = paramInt2;
/*  310 */       Container container = this.target.getParent();
/*      */ 
/*      */       
/*  313 */       while (container.isLightweight()) {
/*  314 */         i -= container.getX();
/*  315 */         j -= container.getY();
/*  316 */         container = container.getParent();
/*      */       } 
/*  318 */       this.textPane.setBounds(i, j, paramInt3, paramInt4);
/*  319 */       this.textPane.validate();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void handleJavaKeyEvent(KeyEvent paramKeyEvent) {
/*  325 */     AWTAccessor.getComponentAccessor().processEvent(this.jtext, paramKeyEvent);
/*      */   }
/*      */   
/*      */   public boolean handlesWheelScrolling() {
/*  329 */     return true;
/*      */   }
/*      */   
/*      */   void handleJavaMouseWheelEvent(MouseWheelEvent paramMouseWheelEvent) {
/*  333 */     AWTAccessor.getComponentAccessor().processEvent(this.textPane, paramMouseWheelEvent);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleJavaMouseEvent(MouseEvent paramMouseEvent) {
/*  338 */     super.handleJavaMouseEvent(paramMouseEvent);
/*  339 */     this.javaMouseEventHandler.handle(paramMouseEvent);
/*      */   }
/*      */ 
/*      */   
/*      */   void handleJavaInputMethodEvent(InputMethodEvent paramInputMethodEvent) {
/*  344 */     if (this.jtext != null) {
/*  345 */       this.jtext.processInputMethodEventPublic(paramInputMethodEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void select(int paramInt1, int paramInt2) {
/*  353 */     this.jtext.select(paramInt1, paramInt2);
/*      */ 
/*      */     
/*  356 */     this.jtext.repaint();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBackground(Color paramColor) {
/*  361 */     super.setBackground(paramColor);
/*      */ 
/*      */ 
/*      */     
/*  365 */     if (this.jtext != null) {
/*  366 */       this.jtext.setBackground(paramColor);
/*  367 */       this.jtext.setSelectedTextColor(paramColor);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setForeground(Color paramColor) {
/*  374 */     super.setForeground(paramColor);
/*      */ 
/*      */ 
/*      */     
/*  378 */     if (this.jtext != null) {
/*  379 */       this.jtext.setForeground(this.foreground);
/*  380 */       this.jtext.setSelectionColor(this.foreground);
/*  381 */       this.jtext.setCaretColor(this.foreground);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFont(Font paramFont) {
/*  388 */     super.setFont(paramFont);
/*      */ 
/*      */ 
/*      */     
/*  392 */     if (this.jtext != null) {
/*  393 */       this.jtext.setFont(this.font);
/*      */     }
/*  395 */     this.textPane.validate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEditable(boolean paramBoolean) {
/*  403 */     if (this.jtext != null) this.jtext.setEditable(paramBoolean); 
/*  404 */     repaintText();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnabled(boolean paramBoolean) {
/*  412 */     super.setEnabled(paramBoolean);
/*  413 */     if (this.jtext != null) {
/*  414 */       this.jtext.setEnabled(paramBoolean);
/*  415 */       this.jtext.repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputMethodRequests getInputMethodRequests() {
/*  424 */     if (this.jtext != null) return this.jtext.getInputMethodRequests(); 
/*  425 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectionStart() {
/*  433 */     return this.jtext.getSelectionStart();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectionEnd() {
/*  441 */     return this.jtext.getSelectionEnd();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getText() {
/*  449 */     return this.jtext.getText();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setText(String paramString) {
/*  457 */     setTextImpl(paramString);
/*  458 */     repaintText();
/*      */   }
/*      */   
/*      */   private void setTextImpl(String paramString) {
/*  462 */     if (this.jtext != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  467 */       this.jtext.getDocument().removeDocumentListener(this.jtext);
/*  468 */       this.jtext.setText(paramString);
/*  469 */       if (this.firstChangeSkipped) {
/*  470 */         postEvent(new TextEvent(this.target, 900));
/*      */       }
/*  472 */       this.jtext.getDocument().addDocumentListener(this.jtext);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert(String paramString, int paramInt) {
/*  482 */     if (this.jtext != null) {
/*  483 */       boolean bool = (paramInt >= this.jtext.getDocument().getLength() && this.jtext.getDocument().getLength() != 0) ? true : false;
/*  484 */       this.jtext.insert(paramString, paramInt);
/*  485 */       this.textPane.validate();
/*  486 */       if (bool) {
/*  487 */         JScrollBar jScrollBar = this.textPane.getVerticalScrollBar();
/*  488 */         if (jScrollBar != null) {
/*  489 */           jScrollBar.setValue(jScrollBar.getMaximum() - jScrollBar.getVisibleAmount());
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
/*      */   public void replaceRange(String paramString, int paramInt1, int paramInt2) {
/*  501 */     if (this.jtext != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  506 */       this.jtext.getDocument().removeDocumentListener(this.jtext);
/*  507 */       this.jtext.replaceRange(paramString, paramInt1, paramInt2);
/*  508 */       postEvent(new TextEvent(this.target, 900));
/*  509 */       this.jtext.getDocument().addDocumentListener(this.jtext);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCaretPosition(int paramInt) {
/*  519 */     this.jtext.setCaretPosition(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCaretPosition() {
/*  528 */     return this.jtext.getCaretPosition();
/*      */   }
/*      */   
/*      */   final class AWTTextAreaUI
/*      */     extends MotifTextAreaUI {
/*      */     private JTextArea jta;
/*      */     
/*      */     protected String getPropertyPrefix() {
/*  536 */       return "TextArea";
/*      */     }
/*      */     
/*      */     public void installUI(JComponent param1JComponent) {
/*  540 */       super.installUI(param1JComponent);
/*      */       
/*  542 */       this.jta = (JTextArea)param1JComponent;
/*      */       
/*  544 */       JTextArea jTextArea = this.jta;
/*      */       
/*  546 */       UIDefaults uIDefaults = XToolkit.getUIDefaults();
/*      */       
/*  548 */       String str = getPropertyPrefix();
/*  549 */       Font font = jTextArea.getFont();
/*  550 */       if (font == null || font instanceof UIResource) {
/*  551 */         jTextArea.setFont(uIDefaults.getFont(str + ".font"));
/*      */       }
/*      */       
/*  554 */       Color color1 = jTextArea.getBackground();
/*  555 */       if (color1 == null || color1 instanceof UIResource) {
/*  556 */         jTextArea.setBackground(uIDefaults.getColor(str + ".background"));
/*      */       }
/*      */       
/*  559 */       Color color2 = jTextArea.getForeground();
/*  560 */       if (color2 == null || color2 instanceof UIResource) {
/*  561 */         jTextArea.setForeground(uIDefaults.getColor(str + ".foreground"));
/*      */       }
/*      */       
/*  564 */       Color color3 = jTextArea.getCaretColor();
/*  565 */       if (color3 == null || color3 instanceof UIResource) {
/*  566 */         jTextArea.setCaretColor(uIDefaults.getColor(str + ".caretForeground"));
/*      */       }
/*      */       
/*  569 */       Color color4 = jTextArea.getSelectionColor();
/*  570 */       if (color4 == null || color4 instanceof UIResource) {
/*  571 */         jTextArea.setSelectionColor(uIDefaults.getColor(str + ".selectionBackground"));
/*      */       }
/*      */       
/*  574 */       Color color5 = jTextArea.getSelectedTextColor();
/*  575 */       if (color5 == null || color5 instanceof UIResource) {
/*  576 */         jTextArea.setSelectedTextColor(uIDefaults.getColor(str + ".selectionForeground"));
/*      */       }
/*      */       
/*  579 */       Color color6 = jTextArea.getDisabledTextColor();
/*  580 */       if (color6 == null || color6 instanceof UIResource) {
/*  581 */         jTextArea.setDisabledTextColor(uIDefaults.getColor(str + ".inactiveForeground"));
/*      */       }
/*      */       
/*  584 */       XTextAreaPeer.BevelBorder bevelBorder = new XTextAreaPeer.BevelBorder(false, SystemColor.controlDkShadow, SystemColor.controlLtHighlight);
/*  585 */       jTextArea.setBorder(new BorderUIResource.CompoundBorderUIResource(bevelBorder, new EmptyBorder(2, 2, 2, 2)));
/*      */ 
/*      */       
/*  588 */       Insets insets = jTextArea.getMargin();
/*  589 */       if (insets == null || insets instanceof UIResource) {
/*  590 */         jTextArea.setMargin(uIDefaults.getInsets(str + ".margin"));
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected void installKeyboardActions() {
/*  596 */       super.installKeyboardActions();
/*      */       
/*  598 */       JTextComponent jTextComponent = getComponent();
/*      */       
/*  600 */       UIDefaults uIDefaults = XToolkit.getUIDefaults();
/*      */       
/*  602 */       String str = getPropertyPrefix();
/*      */       
/*  604 */       InputMap inputMap = (InputMap)uIDefaults.get(str + ".focusInputMap");
/*      */       
/*  606 */       if (inputMap != null) {
/*  607 */         SwingUtilities.replaceUIInputMap(jTextComponent, 0, inputMap);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected Caret createCaret() {
/*  614 */       return new XTextAreaPeer.XAWTCaret();
/*      */     }
/*      */   }
/*      */   
/*      */   static final class XAWTCaret
/*      */     extends DefaultCaret
/*      */   {
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/*  622 */       super.focusGained(param1FocusEvent);
/*  623 */       if (getComponent().isEnabled())
/*      */       {
/*  625 */         setVisible(true);
/*      */       }
/*  627 */       getComponent().repaint();
/*      */     }
/*      */ 
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/*  632 */       super.focusLost(param1FocusEvent);
/*  633 */       getComponent().repaint();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSelectionVisible(boolean param1Boolean) {
/*  641 */       if (param1Boolean) {
/*  642 */         super.setSelectionVisible(param1Boolean);
/*      */       } else {
/*      */         
/*  645 */         setDot(getDot());
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   final class XAWTScrollBarButton
/*      */     extends BasicArrowButton {
/*  652 */     private UIDefaults uidefaults = XToolkit.getUIDefaults();
/*  653 */     private Color darkShadow = SystemColor.controlShadow;
/*  654 */     private Color lightShadow = SystemColor.controlLtHighlight;
/*  655 */     private Color buttonBack = this.uidefaults.getColor("ScrollBar.track");
/*      */     
/*      */     XAWTScrollBarButton(int param1Int) {
/*  658 */       super(param1Int);
/*      */       
/*  660 */       switch (param1Int) {
/*      */         case 1:
/*      */         case 3:
/*      */         case 5:
/*      */         case 7:
/*  665 */           this.direction = param1Int;
/*      */           break;
/*      */         default:
/*  668 */           throw new IllegalArgumentException("invalid direction");
/*      */       } 
/*      */       
/*  671 */       setRequestFocusEnabled(false);
/*  672 */       setOpaque(true);
/*  673 */       setBackground(this.uidefaults.getColor("ScrollBar.thumb"));
/*  674 */       setForeground(this.uidefaults.getColor("ScrollBar.foreground"));
/*      */     }
/*      */ 
/*      */     
/*      */     public Dimension getPreferredSize() {
/*  679 */       switch (this.direction) {
/*      */         case 1:
/*      */         case 5:
/*  682 */           return new Dimension(11, 12);
/*      */       } 
/*      */ 
/*      */       
/*  686 */       return new Dimension(12, 11);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Dimension getMinimumSize() {
/*  692 */       return getPreferredSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public Dimension getMaximumSize() {
/*  697 */       return getPreferredSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isFocusTraversable() {
/*  702 */       return false;
/*      */     }
/*      */     
/*      */     public void paint(Graphics param1Graphics) {
/*      */       int i1, i2;
/*      */       byte b;
/*  708 */       int i = getWidth();
/*  709 */       int j = getHeight();
/*      */       
/*  711 */       if (isOpaque()) {
/*  712 */         param1Graphics.setColor(this.buttonBack);
/*  713 */         param1Graphics.fillRect(0, 0, i, j);
/*      */       } 
/*      */       
/*  716 */       boolean bool = getModel().isPressed();
/*  717 */       Color color1 = bool ? this.darkShadow : this.lightShadow;
/*  718 */       Color color2 = bool ? this.lightShadow : this.darkShadow;
/*  719 */       Color color3 = getBackground();
/*      */       
/*  721 */       int k = i / 2;
/*  722 */       int m = j / 2;
/*  723 */       int n = Math.min(i, j);
/*      */       
/*  725 */       switch (this.direction) {
/*      */         case 1:
/*  727 */           param1Graphics.setColor(color1);
/*  728 */           param1Graphics.drawLine(k, 0, k, 0);
/*  729 */           for (i1 = k - 1, i2 = 1, b = 1; i2 <= n - 2; i2 += 2) {
/*  730 */             param1Graphics.setColor(color1);
/*  731 */             param1Graphics.drawLine(i1, i2, i1, i2);
/*  732 */             if (i2 >= n - 2) {
/*  733 */               param1Graphics.drawLine(i1, i2 + 1, i1, i2 + 1);
/*      */             }
/*  735 */             param1Graphics.setColor(color3);
/*  736 */             param1Graphics.drawLine(i1 + 1, i2, i1 + b, i2);
/*  737 */             if (i2 < n - 2) {
/*  738 */               param1Graphics.drawLine(i1, i2 + 1, i1 + b + 1, i2 + 1);
/*      */             }
/*  740 */             param1Graphics.setColor(color2);
/*  741 */             param1Graphics.drawLine(i1 + b + 1, i2, i1 + b + 1, i2);
/*  742 */             if (i2 >= n - 2) {
/*  743 */               param1Graphics.drawLine(i1 + 1, i2 + 1, i1 + b + 1, i2 + 1);
/*      */             }
/*  745 */             b += 2;
/*  746 */             i1--;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 5:
/*  751 */           param1Graphics.setColor(color2);
/*  752 */           param1Graphics.drawLine(k, n, k, n);
/*  753 */           for (i1 = k - 1, i2 = n - 1, b = 1; i2 >= 1; i2 -= 2) {
/*  754 */             param1Graphics.setColor(color1);
/*  755 */             param1Graphics.drawLine(i1, i2, i1, i2);
/*  756 */             if (i2 <= 2) {
/*  757 */               param1Graphics.drawLine(i1, i2 - 1, i1 + b + 1, i2 - 1);
/*      */             }
/*  759 */             param1Graphics.setColor(color3);
/*  760 */             param1Graphics.drawLine(i1 + 1, i2, i1 + b, i2);
/*  761 */             if (i2 > 2) {
/*  762 */               param1Graphics.drawLine(i1, i2 - 1, i1 + b + 1, i2 - 1);
/*      */             }
/*  764 */             param1Graphics.setColor(color2);
/*  765 */             param1Graphics.drawLine(i1 + b + 1, i2, i1 + b + 1, i2);
/*      */             
/*  767 */             b += 2;
/*  768 */             i1--;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 3:
/*  773 */           param1Graphics.setColor(color1);
/*  774 */           param1Graphics.drawLine(n, m, n, m);
/*  775 */           for (i1 = m - 1, i2 = n - 1, b = 1; i2 >= 1; i2 -= 2) {
/*  776 */             param1Graphics.setColor(color1);
/*  777 */             param1Graphics.drawLine(i2, i1, i2, i1);
/*  778 */             if (i2 <= 2) {
/*  779 */               param1Graphics.drawLine(i2 - 1, i1, i2 - 1, i1 + b + 1);
/*      */             }
/*  781 */             param1Graphics.setColor(color3);
/*  782 */             param1Graphics.drawLine(i2, i1 + 1, i2, i1 + b);
/*  783 */             if (i2 > 2) {
/*  784 */               param1Graphics.drawLine(i2 - 1, i1, i2 - 1, i1 + b + 1);
/*      */             }
/*  786 */             param1Graphics.setColor(color2);
/*  787 */             param1Graphics.drawLine(i2, i1 + b + 1, i2, i1 + b + 1);
/*      */             
/*  789 */             b += 2;
/*  790 */             i1--;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 7:
/*  795 */           param1Graphics.setColor(color2);
/*  796 */           param1Graphics.drawLine(0, m, 0, m);
/*  797 */           for (i1 = m - 1, i2 = 1, b = 1; i2 <= n - 2; i2 += 2) {
/*  798 */             param1Graphics.setColor(color1);
/*  799 */             param1Graphics.drawLine(i2, i1, i2, i1);
/*  800 */             if (i2 >= n - 2) {
/*  801 */               param1Graphics.drawLine(i2 + 1, i1, i2 + 1, i1);
/*      */             }
/*  803 */             param1Graphics.setColor(color3);
/*  804 */             param1Graphics.drawLine(i2, i1 + 1, i2, i1 + b);
/*  805 */             if (i2 < n - 2) {
/*  806 */               param1Graphics.drawLine(i2 + 1, i1, i2 + 1, i1 + b + 1);
/*      */             }
/*  808 */             param1Graphics.setColor(color2);
/*  809 */             param1Graphics.drawLine(i2, i1 + b + 1, i2, i1 + b + 1);
/*  810 */             if (i2 >= n - 2) {
/*  811 */               param1Graphics.drawLine(i2 + 1, i1 + 1, i2 + 1, i1 + b + 1);
/*      */             }
/*  813 */             b += 2;
/*  814 */             i1--;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final class XAWTScrollBarUI
/*      */     extends BasicScrollBarUI
/*      */   {
/*      */     protected void installDefaults() {
/*  826 */       super.installDefaults();
/*  827 */       this.scrollbar.setBorder(new XTextAreaPeer.BevelBorder(false, SystemColor.controlDkShadow, SystemColor.controlLtHighlight));
/*      */     }
/*      */ 
/*      */     
/*      */     protected void configureScrollBarColors() {
/*  832 */       UIDefaults uIDefaults = XToolkit.getUIDefaults();
/*  833 */       Color color1 = this.scrollbar.getBackground();
/*  834 */       if (color1 == null || color1 instanceof UIResource) {
/*  835 */         this.scrollbar.setBackground(uIDefaults.getColor("ScrollBar.background"));
/*      */       }
/*      */       
/*  838 */       Color color2 = this.scrollbar.getForeground();
/*  839 */       if (color2 == null || color2 instanceof UIResource) {
/*  840 */         this.scrollbar.setForeground(uIDefaults.getColor("ScrollBar.foreground"));
/*      */       }
/*      */       
/*  843 */       this.thumbHighlightColor = uIDefaults.getColor("ScrollBar.thumbHighlight");
/*  844 */       this.thumbLightShadowColor = uIDefaults.getColor("ScrollBar.thumbShadow");
/*  845 */       this.thumbDarkShadowColor = uIDefaults.getColor("ScrollBar.thumbDarkShadow");
/*  846 */       this.thumbColor = uIDefaults.getColor("ScrollBar.thumb");
/*  847 */       this.trackColor = uIDefaults.getColor("ScrollBar.track");
/*      */       
/*  849 */       this.trackHighlightColor = uIDefaults.getColor("ScrollBar.trackHighlight");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected JButton createDecreaseButton(int param1Int) {
/*  855 */       return new XTextAreaPeer.XAWTScrollBarButton(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected JButton createIncreaseButton(int param1Int) {
/*  862 */       return new XTextAreaPeer.XAWTScrollBarButton(param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     public JButton getDecreaseButton() {
/*  867 */       return this.decrButton;
/*      */     }
/*      */     
/*      */     public JButton getIncreaseButton() {
/*  871 */       return this.incrButton;
/*      */     }
/*      */ 
/*      */     
/*      */     public void paint(Graphics param1Graphics, JComponent param1JComponent) {
/*  876 */       paintTrack(param1Graphics, param1JComponent, getTrackBounds());
/*  877 */       Rectangle rectangle = getThumbBounds();
/*  878 */       paintThumb(param1Graphics, param1JComponent, rectangle);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintThumb(Graphics param1Graphics, JComponent param1JComponent, Rectangle param1Rectangle) {
/*  884 */       if (!this.scrollbar.isEnabled()) {
/*      */         return;
/*      */       }
/*      */       
/*  888 */       if (param1Rectangle.isEmpty()) {
/*  889 */         param1Rectangle = getTrackBounds();
/*      */       }
/*  891 */       int i = param1Rectangle.width;
/*  892 */       int j = param1Rectangle.height;
/*      */       
/*  894 */       param1Graphics.translate(param1Rectangle.x, param1Rectangle.y);
/*  895 */       param1Graphics.setColor(this.thumbColor);
/*  896 */       param1Graphics.fillRect(0, 0, i - 1, j - 1);
/*      */       
/*  898 */       param1Graphics.setColor(this.thumbHighlightColor);
/*  899 */       param1Graphics.drawLine(0, 0, 0, j - 1);
/*  900 */       param1Graphics.drawLine(1, 0, i - 1, 0);
/*      */       
/*  902 */       param1Graphics.setColor(this.thumbLightShadowColor);
/*  903 */       param1Graphics.drawLine(1, j - 1, i - 1, j - 1);
/*  904 */       param1Graphics.drawLine(i - 1, 1, i - 1, j - 2);
/*      */       
/*  906 */       param1Graphics.translate(-param1Rectangle.x, -param1Rectangle.y);
/*      */     }
/*      */   }
/*      */   
/*      */   final class AWTTextArea
/*      */     extends JTextArea implements DocumentListener {
/*      */     private boolean isFocused = false;
/*      */     private final XTextAreaPeer peer;
/*      */     
/*      */     AWTTextArea(String param1String, XTextAreaPeer param1XTextAreaPeer1) {
/*  916 */       super(param1String);
/*  917 */       setFocusable(false);
/*  918 */       this.peer = param1XTextAreaPeer1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void insertUpdate(DocumentEvent param1DocumentEvent) {
/*  923 */       if (this.peer != null) {
/*  924 */         this.peer.postEvent(new TextEvent(this.peer.target, 900));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeUpdate(DocumentEvent param1DocumentEvent) {
/*  931 */       if (this.peer != null) {
/*  932 */         this.peer.postEvent(new TextEvent(this.peer.target, 900));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void changedUpdate(DocumentEvent param1DocumentEvent) {
/*  939 */       if (this.peer != null) {
/*  940 */         this.peer.postEvent(new TextEvent(this.peer.target, 900));
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     void forwardFocusGained(FocusEvent param1FocusEvent) {
/*  946 */       this.isFocused = true;
/*  947 */       FocusEvent focusEvent = CausedFocusEvent.retarget(param1FocusEvent, this);
/*  948 */       processFocusEvent(focusEvent);
/*      */     }
/*      */ 
/*      */     
/*      */     void forwardFocusLost(FocusEvent param1FocusEvent) {
/*  953 */       this.isFocused = false;
/*  954 */       FocusEvent focusEvent = CausedFocusEvent.retarget(param1FocusEvent, this);
/*  955 */       processFocusEvent(focusEvent);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasFocus() {
/*  960 */       return this.isFocused;
/*      */     }
/*      */     
/*      */     public void repaintNow() {
/*  964 */       paintImmediately(getBounds());
/*      */     }
/*      */     
/*      */     public void processMouseEventPublic(MouseEvent param1MouseEvent) {
/*  968 */       processMouseEvent(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void processMouseMotionEventPublic(MouseEvent param1MouseEvent) {
/*  972 */       processMouseMotionEvent(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void processInputMethodEventPublic(InputMethodEvent param1InputMethodEvent) {
/*  976 */       processInputMethodEvent(param1InputMethodEvent);
/*      */     }
/*      */ 
/*      */     
/*      */     public void updateUI() {
/*  981 */       XTextAreaPeer.AWTTextAreaUI aWTTextAreaUI = new XTextAreaPeer.AWTTextAreaUI();
/*  982 */       setUI(aWTTextAreaUI);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setTransferHandler(TransferHandler param1TransferHandler) {
/*  990 */       TransferHandler transferHandler = (TransferHandler)getClientProperty(AWTAccessor.getClientPropertyKeyAccessor()
/*  991 */           .getJComponent_TRANSFER_HANDLER());
/*  992 */       putClientProperty(AWTAccessor.getClientPropertyKeyAccessor()
/*  993 */           .getJComponent_TRANSFER_HANDLER(), param1TransferHandler);
/*      */ 
/*      */       
/*  996 */       firePropertyChange("transferHandler", transferHandler, param1TransferHandler);
/*      */     }
/*      */   }
/*      */   
/*      */   final class XAWTScrollPaneUI
/*      */     extends BasicScrollPaneUI {
/* 1002 */     private final Border vsbMarginBorderR = new EmptyBorder(0, 2, 0, 0);
/* 1003 */     private final Border vsbMarginBorderL = new EmptyBorder(0, 0, 0, 2);
/* 1004 */     private final Border hsbMarginBorder = new EmptyBorder(2, 0, 0, 0);
/*      */     
/*      */     private Border vsbBorder;
/*      */     
/*      */     private Border hsbBorder;
/*      */     
/*      */     private PropertyChangeListener propertyChangeHandler;
/*      */     
/*      */     protected void installListeners(JScrollPane param1JScrollPane) {
/* 1013 */       super.installListeners(param1JScrollPane);
/* 1014 */       this.propertyChangeHandler = createPropertyChangeHandler();
/* 1015 */       param1JScrollPane.addPropertyChangeListener(this.propertyChangeHandler);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paint(Graphics param1Graphics, JComponent param1JComponent) {
/* 1020 */       Border border = this.scrollpane.getViewportBorder();
/* 1021 */       if (border != null) {
/* 1022 */         Rectangle rectangle = this.scrollpane.getViewportBorderBounds();
/* 1023 */         border.paintBorder(this.scrollpane, param1Graphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void uninstallListeners(JComponent param1JComponent) {
/* 1029 */       super.uninstallListeners(param1JComponent);
/* 1030 */       param1JComponent.removePropertyChangeListener(this.propertyChangeHandler);
/*      */     }
/*      */     
/*      */     private PropertyChangeListener createPropertyChangeHandler() {
/* 1034 */       return new PropertyChangeListener()
/*      */         {
/*      */           public void propertyChange(PropertyChangeEvent param2PropertyChangeEvent) {
/* 1037 */             String str = param2PropertyChangeEvent.getPropertyName();
/*      */             
/* 1039 */             if (str.equals("componentOrientation")) {
/* 1040 */               JScrollPane jScrollPane = (JScrollPane)param2PropertyChangeEvent.getSource();
/* 1041 */               JScrollBar jScrollBar = jScrollPane.getVerticalScrollBar();
/* 1042 */               if (jScrollBar != null) {
/* 1043 */                 if (XTextAreaPeer.XAWTScrollPaneUI.this.isLeftToRight(jScrollPane)) {
/* 1044 */                   XTextAreaPeer.XAWTScrollPaneUI.this.vsbBorder = new CompoundBorder(new EmptyBorder(0, 4, 0, -4), jScrollBar
/* 1045 */                       .getBorder());
/*      */                 } else {
/* 1047 */                   XTextAreaPeer.XAWTScrollPaneUI.this.vsbBorder = new CompoundBorder(new EmptyBorder(0, -4, 0, 4), jScrollBar
/* 1048 */                       .getBorder());
/*      */                 } 
/* 1050 */                 jScrollBar.setBorder(XTextAreaPeer.XAWTScrollPaneUI.this.vsbBorder);
/*      */               } 
/*      */             } 
/*      */           }
/*      */         };
/*      */     }
/*      */     boolean isLeftToRight(Component param1Component) {
/* 1057 */       return param1Component.getComponentOrientation().isLeftToRight();
/*      */     }
/*      */ 
/*      */     
/*      */     protected void installDefaults(JScrollPane param1JScrollPane) {
/* 1062 */       Border border = param1JScrollPane.getBorder();
/* 1063 */       UIDefaults uIDefaults = XToolkit.getUIDefaults();
/* 1064 */       param1JScrollPane.setBorder(uIDefaults.getBorder("ScrollPane.border"));
/* 1065 */       param1JScrollPane.setBackground(uIDefaults.getColor("ScrollPane.background"));
/* 1066 */       param1JScrollPane.setViewportBorder(uIDefaults.getBorder("TextField.border"));
/* 1067 */       JScrollBar jScrollBar1 = param1JScrollPane.getVerticalScrollBar();
/* 1068 */       if (jScrollBar1 != null) {
/* 1069 */         if (isLeftToRight(param1JScrollPane)) {
/* 1070 */           this
/* 1071 */             .vsbBorder = new CompoundBorder(this.vsbMarginBorderR, jScrollBar1.getBorder());
/*      */         } else {
/*      */           
/* 1074 */           this
/* 1075 */             .vsbBorder = new CompoundBorder(this.vsbMarginBorderL, jScrollBar1.getBorder());
/*      */         } 
/* 1077 */         jScrollBar1.setBorder(this.vsbBorder);
/*      */       } 
/*      */       
/* 1080 */       JScrollBar jScrollBar2 = param1JScrollPane.getHorizontalScrollBar();
/* 1081 */       if (jScrollBar2 != null) {
/* 1082 */         this.hsbBorder = new CompoundBorder(this.hsbMarginBorder, jScrollBar2.getBorder());
/* 1083 */         jScrollBar2.setBorder(this.hsbBorder);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void uninstallDefaults(JScrollPane param1JScrollPane) {
/* 1089 */       super.uninstallDefaults(param1JScrollPane);
/*      */       
/* 1091 */       JScrollBar jScrollBar1 = this.scrollpane.getVerticalScrollBar();
/* 1092 */       if (jScrollBar1 != null) {
/* 1093 */         if (jScrollBar1.getBorder() == this.vsbBorder) {
/* 1094 */           jScrollBar1.setBorder((Border)null);
/*      */         }
/* 1096 */         this.vsbBorder = null;
/*      */       } 
/*      */       
/* 1099 */       JScrollBar jScrollBar2 = this.scrollpane.getHorizontalScrollBar();
/* 1100 */       if (jScrollBar2 != null) {
/* 1101 */         if (jScrollBar2.getBorder() == this.hsbBorder) {
/* 1102 */           jScrollBar2.setBorder((Border)null);
/*      */         }
/* 1104 */         this.hsbBorder = null;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class AWTTextPane
/*      */     extends JScrollPane
/*      */     implements FocusListener {
/*      */     private final JTextArea jtext;
/*      */     private final XWindow xwin;
/* 1114 */     private final Color control = SystemColor.control;
/* 1115 */     private final Color focus = SystemColor.activeCaptionBorder;
/*      */     
/*      */     AWTTextPane(JTextArea param1JTextArea, XWindow param1XWindow, Container param1Container) {
/* 1118 */       super(param1JTextArea);
/* 1119 */       this.xwin = param1XWindow;
/* 1120 */       setDoubleBuffered(true);
/* 1121 */       param1JTextArea.addFocusListener(this);
/* 1122 */       AWTAccessor.getComponentAccessor().setParent(this, param1Container);
/* 1123 */       setViewportBorder(new XTextAreaPeer.BevelBorder(false, SystemColor.controlDkShadow, SystemColor.controlLtHighlight));
/* 1124 */       this.jtext = param1JTextArea;
/* 1125 */       setFocusable(false);
/* 1126 */       addNotify();
/*      */     }
/*      */ 
/*      */     
/*      */     public void invalidate() {
/* 1131 */       synchronized (getTreeLock()) {
/* 1132 */         Container container = getParent();
/* 1133 */         AWTAccessor.getComponentAccessor().setParent(this, null);
/*      */         try {
/* 1135 */           super.invalidate();
/*      */         } finally {
/* 1137 */           AWTAccessor.getComponentAccessor().setParent(this, container);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/* 1144 */       Graphics graphics = getGraphics();
/* 1145 */       Rectangle rectangle = getViewportBorderBounds();
/* 1146 */       graphics.setColor(this.focus);
/* 1147 */       graphics.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/* 1148 */       graphics.dispose();
/*      */     }
/*      */ 
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 1153 */       Graphics graphics = getGraphics();
/* 1154 */       Rectangle rectangle = getViewportBorderBounds();
/* 1155 */       graphics.setColor(this.control);
/* 1156 */       graphics.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/* 1157 */       graphics.dispose();
/*      */     }
/*      */     
/*      */     public Window getRealParent() {
/* 1161 */       return (Window)this.xwin.target;
/*      */     }
/*      */ 
/*      */     
/*      */     public ComponentPeer getPeer() {
/* 1166 */       return (ComponentPeer)this.xwin;
/*      */     }
/*      */ 
/*      */     
/*      */     public void updateUI() {
/* 1171 */       XTextAreaPeer.XAWTScrollPaneUI xAWTScrollPaneUI = new XTextAreaPeer.XAWTScrollPaneUI();
/* 1172 */       setUI(xAWTScrollPaneUI);
/*      */     }
/*      */ 
/*      */     
/*      */     public JScrollBar createVerticalScrollBar() {
/* 1177 */       return new XAWTScrollBar(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public JScrollBar createHorizontalScrollBar() {
/* 1182 */       return new XAWTScrollBar(0);
/*      */     }
/*      */     
/*      */     public JTextArea getTextArea() {
/* 1186 */       return this.jtext;
/*      */     }
/*      */ 
/*      */     
/*      */     public Graphics getGraphics() {
/* 1191 */       return this.xwin.getGraphics();
/*      */     }
/*      */     
/*      */     final class XAWTScrollBar
/*      */       extends JScrollPane.ScrollBar {
/*      */       XAWTScrollBar(int param2Int) {
/* 1197 */         super(param2Int);
/* 1198 */         setFocusable(false);
/*      */       }
/*      */ 
/*      */       
/*      */       public void updateUI() {
/* 1203 */         XTextAreaPeer.XAWTScrollBarUI xAWTScrollBarUI = new XTextAreaPeer.XAWTScrollBarUI();
/* 1204 */         setUI(xAWTScrollBarUI);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static class BevelBorder extends AbstractBorder implements UIResource {
/* 1210 */     private Color darkShadow = SystemColor.controlDkShadow;
/* 1211 */     private Color lightShadow = SystemColor.controlLtHighlight;
/* 1212 */     private Color control = SystemColor.controlShadow;
/*      */     private boolean isRaised;
/*      */     
/*      */     BevelBorder(boolean param1Boolean, Color param1Color1, Color param1Color2) {
/* 1216 */       this.isRaised = param1Boolean;
/* 1217 */       this.darkShadow = param1Color1;
/* 1218 */       this.lightShadow = param1Color2;
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1223 */       param1Graphics.setColor(this.isRaised ? this.lightShadow : this.darkShadow);
/* 1224 */       param1Graphics.drawLine(param1Int1, param1Int2, param1Int1 + param1Int3 - 1, param1Int2);
/* 1225 */       param1Graphics.drawLine(param1Int1, param1Int2 + param1Int4 - 1, param1Int1, param1Int2 + 1);
/*      */       
/* 1227 */       param1Graphics.setColor(this.control);
/* 1228 */       param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 1, param1Int1 + param1Int3 - 2, param1Int2 + 1);
/* 1229 */       param1Graphics.drawLine(param1Int1 + 1, param1Int2 + param1Int4 - 1, param1Int1 + 1, param1Int2 + 1);
/*      */       
/* 1231 */       param1Graphics.setColor(this.isRaised ? this.darkShadow : this.lightShadow);
/* 1232 */       param1Graphics.drawLine(param1Int1 + 1, param1Int2 + param1Int4 - 1, param1Int1 + param1Int3 - 1, param1Int2 + param1Int4 - 1);
/* 1233 */       param1Graphics.drawLine(param1Int1 + param1Int3 - 1, param1Int2 + param1Int4 - 1, param1Int1 + param1Int3 - 1, param1Int2 + 1);
/*      */       
/* 1235 */       param1Graphics.setColor(this.control);
/* 1236 */       param1Graphics.drawLine(param1Int1 + 1, param1Int2 + param1Int4 - 2, param1Int1 + param1Int3 - 2, param1Int2 + param1Int4 - 2);
/* 1237 */       param1Graphics.drawLine(param1Int1 + param1Int3 - 2, param1Int2 + param1Int4 - 2, param1Int1 + param1Int3 - 2, param1Int2 + 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public Insets getBorderInsets(Component param1Component) {
/* 1242 */       return getBorderInsets(param1Component, new Insets(0, 0, 0, 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 1247 */       param1Insets.top = param1Insets.left = param1Insets.bottom = param1Insets.right = 2;
/* 1248 */       return param1Insets;
/*      */     }
/*      */     
/*      */     public boolean isOpaque(Component param1Component) {
/* 1252 */       return true;
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
/*      */   private static final class JavaMouseEventHandler
/*      */   {
/*      */     private final XTextAreaPeer outer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1273 */     private final Pointer current = new Pointer();
/*      */     private boolean grabbed = false;
/*      */     
/*      */     JavaMouseEventHandler(XTextAreaPeer param1XTextAreaPeer) {
/* 1277 */       this.outer = param1XTextAreaPeer;
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
/*      */     void handle(MouseEvent param1MouseEvent) {
/* 1294 */       if (!this.grabbed)
/*      */       {
/* 1296 */         setPointerToUnderPoint(param1MouseEvent.getPoint());
/*      */       }
/* 1298 */       dispatch(param1MouseEvent);
/* 1299 */       boolean bool = this.grabbed;
/* 1300 */       grabbed_update(param1MouseEvent);
/* 1301 */       if (bool && !this.grabbed) {
/* 1302 */         setPointerToUnderPoint(param1MouseEvent.getPoint());
/*      */       }
/* 1304 */       setCursor(); } private void dispatch(MouseEvent param1MouseEvent) {
/*      */       Point point1;
/*      */       XTextAreaPeer.AWTTextArea aWTTextArea;
/*      */       MouseEvent mouseEvent;
/*      */       int i;
/*      */       JScrollBar jScrollBar;
/*      */       JButton jButton;
/*      */       Point point2;
/* 1312 */       switch (this.current.getType()) {
/*      */         
/*      */         case TEXT:
/* 1315 */           point1 = toViewportChildLocalSpace(this.outer
/* 1316 */               .textPane.getViewport(), param1MouseEvent.getPoint());
/* 1317 */           aWTTextArea = this.outer.jtext;
/* 1318 */           mouseEvent = newMouseEvent(aWTTextArea, point1, param1MouseEvent);
/* 1319 */           i = mouseEvent.getID();
/* 1320 */           if (i == 503 || i == 506) {
/* 1321 */             aWTTextArea.processMouseMotionEventPublic(mouseEvent); break;
/*      */           } 
/* 1323 */           aWTTextArea.processMouseEventPublic(mouseEvent);
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case BAR:
/*      */         case BUTTON:
/* 1336 */           jScrollBar = this.current.getBar();
/* 1337 */           point2 = toLocalSpace(jScrollBar, param1MouseEvent.getPoint());
/* 1338 */           if (this.current.getType() == Pointer.Type.BUTTON) {
/* 1339 */             jButton = this.current.getButton();
/* 1340 */             point2 = toLocalSpace(jButton, point2);
/*      */           } 
/* 1342 */           AWTAccessor.getComponentAccessor().processEvent(jButton, newMouseEvent(jButton, point2, param1MouseEvent));
/*      */           break;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static MouseEvent newMouseEvent(Component param1Component, Point param1Point, MouseEvent param1MouseEvent) {
/* 1350 */       MouseEvent mouseEvent1 = param1MouseEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1357 */       MouseEvent mouseEvent2 = new MouseEvent(param1Component, mouseEvent1.getID(), mouseEvent1.getWhen(), mouseEvent1.getModifiersEx() | mouseEvent1.getModifiers(), param1Point.x, param1Point.y, mouseEvent1.getXOnScreen(), mouseEvent1.getYOnScreen(), mouseEvent1.getClickCount(), mouseEvent1.isPopupTrigger(), mouseEvent1.getButton());
/*      */ 
/*      */ 
/*      */       
/* 1361 */       SunToolkit.setSystemGenerated(mouseEvent2);
/* 1362 */       return mouseEvent2;
/*      */     }
/*      */     
/*      */     private void setCursor() {
/* 1366 */       if (this.current.getType() == Pointer.Type.TEXT) {
/*      */ 
/*      */ 
/*      */         
/* 1370 */         this.outer.pSetCursor(this.outer.target.getCursor(), true);
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/* 1378 */         this.outer.pSetCursor(this.outer.textPane.getCursor(), true);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void grabbed_update(MouseEvent param1MouseEvent) {
/* 1403 */       this.grabbed = ((param1MouseEvent.getModifiersEx() & 0x1C00) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static Point toLocalSpace(Component param1Component, Point param1Point) {
/* 1410 */       Point point1 = param1Point;
/* 1411 */       Point point2 = param1Component.getLocation();
/* 1412 */       return new Point(point1.x - point2.x, point1.y - point2.y);
/*      */     }
/*      */     
/*      */     private static Point toViewportChildLocalSpace(JViewport param1JViewport, Point param1Point) {
/* 1416 */       Point point1 = toLocalSpace(param1JViewport, param1Point);
/* 1417 */       Point point2 = param1JViewport.getViewPosition();
/* 1418 */       point1.x += point2.x;
/* 1419 */       point1.y += point2.y;
/* 1420 */       return point1;
/*      */     }
/*      */     
/*      */     private void setPointerToUnderPoint(Point param1Point) {
/* 1424 */       if (this.outer.textPane.getViewport().getBounds().contains(param1Point)) {
/* 1425 */         this.current.setText();
/*      */       }
/* 1427 */       else if (!setPointerIfPointOverScrollbar(this.outer
/* 1428 */           .textPane.getVerticalScrollBar(), param1Point)) {
/*      */         
/* 1430 */         if (!setPointerIfPointOverScrollbar(this.outer
/* 1431 */             .textPane.getHorizontalScrollBar(), param1Point))
/*      */         {
/* 1433 */           this.current.setNone();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     private boolean setPointerIfPointOverScrollbar(JScrollBar param1JScrollBar, Point param1Point) {
/* 1439 */       if (!param1JScrollBar.getBounds().contains(param1Point)) {
/* 1440 */         return false;
/*      */       }
/* 1442 */       this.current.setBar(param1JScrollBar);
/* 1443 */       Point point = toLocalSpace(param1JScrollBar, param1Point);
/*      */ 
/*      */       
/* 1446 */       XTextAreaPeer.XAWTScrollBarUI xAWTScrollBarUI = (XTextAreaPeer.XAWTScrollBarUI)param1JScrollBar.getUI();
/*      */       
/* 1448 */       if (!setPointerIfPointOverButton(xAWTScrollBarUI.getIncreaseButton(), point)) {
/* 1449 */         setPointerIfPointOverButton(xAWTScrollBarUI.getDecreaseButton(), point);
/*      */       }
/*      */       
/* 1452 */       return true;
/*      */     }
/*      */     
/*      */     private boolean setPointerIfPointOverButton(JButton param1JButton, Point param1Point) {
/* 1456 */       if (!param1JButton.getBounds().contains(param1Point)) {
/* 1457 */         return false;
/*      */       }
/* 1459 */       this.current.setButton(param1JButton);
/* 1460 */       return true;
/*      */     }
/*      */     private static final class Pointer { private Type type; private JScrollBar bar; private JButton button;
/*      */       private Pointer() {}
/*      */       
/* 1465 */       enum Type { NONE, TEXT, BAR, BUTTON; }
/*      */       
/*      */       Type getType() {
/* 1468 */         return this.type;
/*      */       }
/*      */       boolean isNone() {
/* 1471 */         return (this.type == Type.NONE);
/*      */       }
/*      */       JScrollBar getBar() {
/* 1474 */         boolean bool = (this.type == Type.BAR || this.type == Type.BUTTON) ? true : false;
/* 1475 */         assert bool;
/* 1476 */         return bool ? this.bar : null;
/*      */       }
/*      */       JButton getButton() {
/* 1479 */         boolean bool = (this.type == Type.BUTTON) ? true : false;
/* 1480 */         assert bool;
/* 1481 */         return bool ? this.button : null;
/*      */       }
/*      */       void setNone() {
/* 1484 */         this.type = Type.NONE;
/*      */       }
/*      */       void setText() {
/* 1487 */         this.type = Type.TEXT;
/*      */       }
/*      */       void setBar(JScrollBar param2JScrollBar) {
/* 1490 */         this.bar = param2JScrollBar;
/* 1491 */         this.type = Type.BAR;
/*      */       }
/*      */       void setButton(JButton param2JButton) {
/* 1494 */         this.button = param2JButton;
/* 1495 */         this.type = Type.BUTTON;
/*      */       } }
/*      */ 
/*      */     
/*      */     enum Type {
/*      */       NONE, TEXT, BAR, BUTTON;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XTextAreaPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */