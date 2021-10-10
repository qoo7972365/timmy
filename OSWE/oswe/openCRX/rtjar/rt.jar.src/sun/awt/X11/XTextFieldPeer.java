/*     */ package sun.awt.X11;
/*     */ 
/*     */ import com.sun.java.swing.plaf.motif.MotifPasswordFieldUI;
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.SystemColor;
/*     */ import java.awt.TextField;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.InputMethodEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.TextEvent;
/*     */ import java.awt.im.InputMethodRequests;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.awt.peer.TextFieldPeer;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.TransferHandler;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.text.Caret;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.CausedFocusEvent;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class XTextFieldPeer
/*     */   extends XComponentPeer
/*     */   implements TextFieldPeer
/*     */ {
/*  61 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XTextField");
/*     */   private String text;
/*     */   private final XAWTTextField xtext;
/*     */   private final boolean firstChangeSkipped;
/*     */   private static final int PADDING = 16;
/*     */   
/*     */   XTextFieldPeer(TextField paramTextField) {
/*  68 */     super(paramTextField);
/*  69 */     this.text = paramTextField.getText();
/*  70 */     this.xtext = new XAWTTextField(this.text, this, paramTextField.getParent());
/*  71 */     this.xtext.getDocument().addDocumentListener(this.xtext);
/*  72 */     this.xtext.setCursor(paramTextField.getCursor());
/*  73 */     XToolkit.specialPeerMap.put(this.xtext, this);
/*     */     
/*  75 */     initTextField();
/*  76 */     setText(paramTextField.getText());
/*  77 */     if (paramTextField.echoCharIsSet()) {
/*  78 */       setEchoChar(paramTextField.getEchoChar());
/*     */     } else {
/*  80 */       setEchoChar(false);
/*     */     } 
/*  82 */     int i = paramTextField.getSelectionStart();
/*  83 */     int j = paramTextField.getSelectionEnd();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     setCaretPosition(Math.min(j, this.text.length()));
/*  89 */     if (j > i)
/*     */     {
/*  91 */       select(i, j);
/*     */     }
/*     */     
/*  94 */     setEditable(paramTextField.isEditable());
/*     */ 
/*     */     
/*  97 */     this.firstChangeSkipped = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 102 */     XToolkit.specialPeerMap.remove(this.xtext);
/*     */     
/* 104 */     this.xtext.getCaret().setVisible(false);
/* 105 */     this.xtext.removeNotify();
/* 106 */     super.dispose();
/*     */   }
/*     */   
/*     */   void initTextField() {
/* 110 */     setVisible(this.target.isVisible());
/*     */     
/* 112 */     setBounds(this.x, this.y, this.width, this.height, 3);
/*     */     
/* 114 */     AWTAccessor.ComponentAccessor componentAccessor = AWTAccessor.getComponentAccessor();
/* 115 */     this.foreground = componentAccessor.getForeground(this.target);
/* 116 */     if (this.foreground == null) {
/* 117 */       this.foreground = SystemColor.textText;
/*     */     }
/* 119 */     setForeground(this.foreground);
/*     */     
/* 121 */     this.background = componentAccessor.getBackground(this.target);
/* 122 */     if (this.background == null)
/* 123 */       if (((TextField)this.target).isEditable()) { this.background = SystemColor.text; }
/* 124 */       else { this.background = SystemColor.control; }
/*     */        
/* 126 */     setBackground(this.background);
/*     */     
/* 128 */     if (!this.target.isBackgroundSet())
/*     */     {
/*     */       
/* 131 */       componentAccessor.setBackground(this.target, this.background);
/*     */     }
/* 133 */     if (!this.target.isForegroundSet()) {
/* 134 */       this.target.setForeground(SystemColor.textText);
/*     */     }
/*     */     
/* 137 */     setFont(this.font);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEditable(boolean paramBoolean) {
/* 145 */     if (this.xtext != null) {
/* 146 */       this.xtext.setEditable(paramBoolean);
/* 147 */       this.xtext.repaint();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean paramBoolean) {
/* 156 */     super.setEnabled(paramBoolean);
/* 157 */     if (this.xtext != null) {
/* 158 */       this.xtext.setEnabled(paramBoolean);
/* 159 */       this.xtext.repaint();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputMethodRequests getInputMethodRequests() {
/* 168 */     if (this.xtext != null) return this.xtext.getInputMethodRequests(); 
/* 169 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void handleJavaInputMethodEvent(InputMethodEvent paramInputMethodEvent) {
/* 175 */     if (this.xtext != null) {
/* 176 */       this.xtext.processInputMethodEventImpl(paramInputMethodEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEchoChar(char paramChar) {
/* 184 */     if (this.xtext != null) {
/* 185 */       this.xtext.setEchoChar(paramChar);
/* 186 */       this.xtext.putClientProperty("JPasswordField.cutCopyAllowed", 
/* 187 */           this.xtext.echoCharIsSet() ? Boolean.FALSE : Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSelectionStart() {
/* 196 */     return this.xtext.getSelectionStart();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSelectionEnd() {
/* 204 */     return this.xtext.getSelectionEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 212 */     return this.xtext.getText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String paramString) {
/* 220 */     setXAWTTextField(paramString);
/* 221 */     repaint();
/*     */   }
/*     */   
/*     */   private void setXAWTTextField(String paramString) {
/* 225 */     this.text = paramString;
/* 226 */     if (this.xtext != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 231 */       this.xtext.getDocument().removeDocumentListener(this.xtext);
/* 232 */       this.xtext.setText(paramString);
/* 233 */       if (this.firstChangeSkipped) {
/* 234 */         postEvent(new TextEvent(this.target, 900));
/*     */       }
/* 236 */       this.xtext.getDocument().addDocumentListener(this.xtext);
/* 237 */       this.xtext.setCaretPosition(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCaretPosition(int paramInt) {
/* 247 */     if (this.xtext != null) this.xtext.setCaretPosition(paramInt); 
/*     */   }
/*     */   
/*     */   void repaintText() {
/* 251 */     this.xtext.repaintNow();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBackground(Color paramColor) {
/* 256 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 257 */       log.fine("target=" + this.target + ", old=" + this.background + ", new=" + paramColor);
/*     */     }
/* 259 */     this.background = paramColor;
/* 260 */     if (this.xtext != null) {
/* 261 */       this.xtext.setBackground(paramColor);
/* 262 */       this.xtext.setSelectedTextColor(paramColor);
/*     */     } 
/* 264 */     repaintText();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setForeground(Color paramColor) {
/* 269 */     this.foreground = paramColor;
/* 270 */     if (this.xtext != null) {
/* 271 */       this.xtext.setForeground(this.foreground);
/* 272 */       this.xtext.setSelectionColor(this.foreground);
/* 273 */       this.xtext.setCaretColor(this.foreground);
/*     */     } 
/* 275 */     repaintText();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font paramFont) {
/* 280 */     synchronized (getStateLock()) {
/* 281 */       this.font = paramFont;
/* 282 */       if (this.xtext != null) {
/* 283 */         this.xtext.setFont(this.font);
/*     */       }
/*     */     } 
/* 286 */     this.xtext.validate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deselect() {
/* 293 */     int i = this.xtext.getSelectionStart();
/* 294 */     int j = this.xtext.getSelectionEnd();
/* 295 */     if (i != j) {
/* 296 */       this.xtext.select(i, i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCaretPosition() {
/* 306 */     return this.xtext.getCaretPosition();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void select(int paramInt1, int paramInt2) {
/* 314 */     this.xtext.select(paramInt1, paramInt2);
/*     */ 
/*     */     
/* 317 */     this.xtext.repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 322 */     return this.xtext.getMinimumSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 327 */     return this.xtext.getPreferredSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(int paramInt) {
/* 332 */     return getMinimumSize(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(int paramInt) {
/* 339 */     Font font = this.xtext.getFont();
/* 340 */     FontMetrics fontMetrics = this.xtext.getFontMetrics(font);
/* 341 */     return new Dimension(fontMetrics.charWidth('0') * paramInt + 10, fontMetrics
/* 342 */         .getMaxDescent() + fontMetrics.getMaxAscent() + 16);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFocusable() {
/* 347 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void action(long paramLong, int paramInt) {
/* 353 */     postEvent(new ActionEvent(this.target, 1001, this.text, paramLong, paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void disposeImpl() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void repaint() {
/* 363 */     if (this.xtext != null) this.xtext.repaint(); 
/*     */   }
/*     */   
/*     */   void paintPeer(Graphics paramGraphics) {
/* 367 */     if (this.xtext != null) this.xtext.paint(paramGraphics);
/*     */   
/*     */   }
/*     */   
/*     */   public void print(Graphics paramGraphics) {
/* 372 */     if (this.xtext != null) {
/* 373 */       this.xtext.print(paramGraphics);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void focusLost(FocusEvent paramFocusEvent) {
/* 379 */     super.focusLost(paramFocusEvent);
/* 380 */     this.xtext.forwardFocusLost(paramFocusEvent);
/*     */   }
/*     */ 
/*     */   
/*     */   public void focusGained(FocusEvent paramFocusEvent) {
/* 385 */     super.focusGained(paramFocusEvent);
/* 386 */     this.xtext.forwardFocusGained(paramFocusEvent);
/*     */   }
/*     */ 
/*     */   
/*     */   void handleJavaKeyEvent(KeyEvent paramKeyEvent) {
/* 391 */     AWTAccessor.getComponentAccessor().processEvent(this.xtext, paramKeyEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleJavaMouseEvent(MouseEvent paramMouseEvent) {
/* 397 */     super.handleJavaMouseEvent(paramMouseEvent);
/* 398 */     if (this.xtext != null) {
/* 399 */       paramMouseEvent.setSource(this.xtext);
/* 400 */       int i = paramMouseEvent.getID();
/* 401 */       if (i == 506 || i == 503) {
/* 402 */         this.xtext.processMouseMotionEventImpl(paramMouseEvent);
/*     */       } else {
/* 404 */         this.xtext.processMouseEventImpl(paramMouseEvent);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension minimumSize() {
/* 413 */     return getMinimumSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean paramBoolean) {
/* 418 */     super.setVisible(paramBoolean);
/* 419 */     if (this.xtext != null) this.xtext.setVisible(paramBoolean);
/*     */   
/*     */   }
/*     */   
/*     */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 424 */     super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/* 425 */     if (this.xtext != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 434 */       int i = paramInt1;
/* 435 */       int j = paramInt2;
/* 436 */       Container container = this.target.getParent();
/*     */ 
/*     */       
/* 439 */       while (container.isLightweight()) {
/* 440 */         i -= container.getX();
/* 441 */         j -= container.getY();
/* 442 */         container = container.getParent();
/*     */       } 
/* 444 */       this.xtext.setBounds(i, j, paramInt3, paramInt4);
/* 445 */       this.xtext.validate();
/*     */     } 
/*     */   }
/*     */   
/*     */   final class AWTTextFieldUI
/*     */     extends MotifPasswordFieldUI
/*     */   {
/*     */     private JTextField jtf;
/*     */     
/*     */     protected String getPropertyPrefix() {
/* 455 */       JTextComponent jTextComponent = getComponent();
/* 456 */       if (jTextComponent instanceof JPasswordField && ((JPasswordField)jTextComponent).echoCharIsSet()) {
/* 457 */         return "PasswordField";
/*     */       }
/* 459 */       return "TextField";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void installUI(JComponent param1JComponent) {
/* 465 */       super.installUI(param1JComponent);
/*     */       
/* 467 */       this.jtf = (JTextField)param1JComponent;
/*     */       
/* 469 */       JTextField jTextField = this.jtf;
/*     */       
/* 471 */       UIDefaults uIDefaults = XToolkit.getUIDefaults();
/*     */       
/* 473 */       String str = getPropertyPrefix();
/* 474 */       Font font = jTextField.getFont();
/* 475 */       if (font == null || font instanceof javax.swing.plaf.UIResource) {
/* 476 */         jTextField.setFont(uIDefaults.getFont(str + ".font"));
/*     */       }
/*     */       
/* 479 */       Color color1 = jTextField.getBackground();
/* 480 */       if (color1 == null || color1 instanceof javax.swing.plaf.UIResource) {
/* 481 */         jTextField.setBackground(uIDefaults.getColor(str + ".background"));
/*     */       }
/*     */       
/* 484 */       Color color2 = jTextField.getForeground();
/* 485 */       if (color2 == null || color2 instanceof javax.swing.plaf.UIResource) {
/* 486 */         jTextField.setForeground(uIDefaults.getColor(str + ".foreground"));
/*     */       }
/*     */       
/* 489 */       Color color3 = jTextField.getCaretColor();
/* 490 */       if (color3 == null || color3 instanceof javax.swing.plaf.UIResource) {
/* 491 */         jTextField.setCaretColor(uIDefaults.getColor(str + ".caretForeground"));
/*     */       }
/*     */       
/* 494 */       Color color4 = jTextField.getSelectionColor();
/* 495 */       if (color4 == null || color4 instanceof javax.swing.plaf.UIResource) {
/* 496 */         jTextField.setSelectionColor(uIDefaults.getColor(str + ".selectionBackground"));
/*     */       }
/*     */       
/* 499 */       Color color5 = jTextField.getSelectedTextColor();
/* 500 */       if (color5 == null || color5 instanceof javax.swing.plaf.UIResource) {
/* 501 */         jTextField.setSelectedTextColor(uIDefaults.getColor(str + ".selectionForeground"));
/*     */       }
/*     */       
/* 504 */       Color color6 = jTextField.getDisabledTextColor();
/* 505 */       if (color6 == null || color6 instanceof javax.swing.plaf.UIResource) {
/* 506 */         jTextField.setDisabledTextColor(uIDefaults.getColor(str + ".inactiveForeground"));
/*     */       }
/*     */       
/* 509 */       Border border = jTextField.getBorder();
/* 510 */       if (border == null || border instanceof javax.swing.plaf.UIResource) {
/* 511 */         jTextField.setBorder(uIDefaults.getBorder(str + ".border"));
/*     */       }
/*     */       
/* 514 */       Insets insets = jTextField.getMargin();
/* 515 */       if (insets == null || insets instanceof javax.swing.plaf.UIResource) {
/* 516 */         jTextField.setMargin(uIDefaults.getInsets(str + ".margin"));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected void installKeyboardActions() {
/* 522 */       super.installKeyboardActions();
/*     */       
/* 524 */       JTextComponent jTextComponent = getComponent();
/*     */       
/* 526 */       UIDefaults uIDefaults = XToolkit.getUIDefaults();
/*     */       
/* 528 */       String str = getPropertyPrefix();
/*     */       
/* 530 */       InputMap inputMap = (InputMap)uIDefaults.get(str + ".focusInputMap");
/*     */       
/* 532 */       if (inputMap != null) {
/* 533 */         SwingUtilities.replaceUIInputMap(jTextComponent, 0, inputMap);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected Caret createCaret() {
/* 540 */       return new XTextAreaPeer.XAWTCaret();
/*     */     }
/*     */   }
/*     */   
/*     */   final class XAWTTextField
/*     */     extends JPasswordField
/*     */     implements ActionListener, DocumentListener {
/*     */     private boolean isFocused = false;
/*     */     private final XComponentPeer peer;
/*     */     
/*     */     XAWTTextField(String param1String, XComponentPeer param1XComponentPeer, Container param1Container) {
/* 551 */       super(param1String);
/* 552 */       this.peer = param1XComponentPeer;
/* 553 */       setDoubleBuffered(true);
/* 554 */       setFocusable(false);
/* 555 */       AWTAccessor.getComponentAccessor().setParent(this, param1Container);
/* 556 */       setBackground(param1XComponentPeer.getPeerBackground());
/* 557 */       setForeground(param1XComponentPeer.getPeerForeground());
/* 558 */       setFont(param1XComponentPeer.getPeerFont());
/* 559 */       setCaretPosition(0);
/* 560 */       addActionListener(this);
/* 561 */       addNotify();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 567 */       this.peer.postEvent(new ActionEvent(this.peer.target, 1001, 
/*     */             
/* 569 */             getText(), param1ActionEvent
/* 570 */             .getWhen(), param1ActionEvent
/* 571 */             .getModifiers()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void insertUpdate(DocumentEvent param1DocumentEvent) {
/* 577 */       if (this.peer != null) {
/* 578 */         this.peer.postEvent(new TextEvent(this.peer.target, 900));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void removeUpdate(DocumentEvent param1DocumentEvent) {
/* 585 */       if (this.peer != null) {
/* 586 */         this.peer.postEvent(new TextEvent(this.peer.target, 900));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void changedUpdate(DocumentEvent param1DocumentEvent) {
/* 593 */       if (this.peer != null) {
/* 594 */         this.peer.postEvent(new TextEvent(this.peer.target, 900));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ComponentPeer getPeer() {
/* 601 */       return this.peer;
/*     */     }
/*     */     
/*     */     public void repaintNow() {
/* 605 */       paintImmediately(getBounds());
/*     */     }
/*     */ 
/*     */     
/*     */     public Graphics getGraphics() {
/* 610 */       return this.peer.getGraphics();
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateUI() {
/* 615 */       XTextFieldPeer.AWTTextFieldUI aWTTextFieldUI = new XTextFieldPeer.AWTTextFieldUI();
/* 616 */       setUI(aWTTextFieldUI);
/*     */     }
/*     */     
/*     */     void forwardFocusGained(FocusEvent param1FocusEvent) {
/* 620 */       this.isFocused = true;
/* 621 */       FocusEvent focusEvent = CausedFocusEvent.retarget(param1FocusEvent, this);
/* 622 */       processFocusEvent(focusEvent);
/*     */     }
/*     */     
/*     */     void forwardFocusLost(FocusEvent param1FocusEvent) {
/* 626 */       this.isFocused = false;
/* 627 */       FocusEvent focusEvent = CausedFocusEvent.retarget(param1FocusEvent, this);
/* 628 */       processFocusEvent(focusEvent);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasFocus() {
/* 634 */       return this.isFocused;
/*     */     }
/*     */     
/*     */     public void processInputMethodEventImpl(InputMethodEvent param1InputMethodEvent) {
/* 638 */       processInputMethodEvent(param1InputMethodEvent);
/*     */     }
/*     */     
/*     */     public void processMouseEventImpl(MouseEvent param1MouseEvent) {
/* 642 */       processMouseEvent(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void processMouseMotionEventImpl(MouseEvent param1MouseEvent) {
/* 646 */       processMouseMotionEvent(param1MouseEvent);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setTransferHandler(TransferHandler param1TransferHandler) {
/* 654 */       TransferHandler transferHandler = (TransferHandler)getClientProperty(AWTAccessor.getClientPropertyKeyAccessor()
/* 655 */           .getJComponent_TRANSFER_HANDLER());
/* 656 */       putClientProperty(AWTAccessor.getClientPropertyKeyAccessor()
/* 657 */           .getJComponent_TRANSFER_HANDLER(), param1TransferHandler);
/*     */ 
/*     */       
/* 660 */       firePropertyChange("transferHandler", transferHandler, param1TransferHandler);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setEchoChar(char param1Char) {
/* 665 */       super.setEchoChar(param1Char);
/* 666 */       ((XTextFieldPeer.AWTTextFieldUI)this.ui).installKeyboardActions();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XTextFieldPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */