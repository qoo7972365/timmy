/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.AWTKeyStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.LayoutManager2;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.datatransfer.DataFlavor;
/*      */ import java.awt.datatransfer.Transferable;
/*      */ import java.awt.datatransfer.UnsupportedFlavorException;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.im.InputContext;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.StringBufferInputStream;
/*      */ import java.io.StringReader;
/*      */ import java.io.StringWriter;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Set;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.DropMode;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JEditorPane;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.TransferHandler;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.event.DocumentListener;
/*      */ import javax.swing.event.MouseInputAdapter;
/*      */ import javax.swing.plaf.ActionMapUIResource;
/*      */ import javax.swing.plaf.ComponentInputMapUIResource;
/*      */ import javax.swing.plaf.InputMapUIResource;
/*      */ import javax.swing.plaf.TextUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.text.AbstractDocument;
/*      */ import javax.swing.text.AttributeSet;
/*      */ import javax.swing.text.BadLocationException;
/*      */ import javax.swing.text.Caret;
/*      */ import javax.swing.text.DefaultCaret;
/*      */ import javax.swing.text.DefaultEditorKit;
/*      */ import javax.swing.text.DefaultHighlighter;
/*      */ import javax.swing.text.Document;
/*      */ import javax.swing.text.EditorKit;
/*      */ import javax.swing.text.Element;
/*      */ import javax.swing.text.Highlighter;
/*      */ import javax.swing.text.JTextComponent;
/*      */ import javax.swing.text.Keymap;
/*      */ import javax.swing.text.Position;
/*      */ import javax.swing.text.TextAction;
/*      */ import javax.swing.text.View;
/*      */ import javax.swing.text.ViewFactory;
/*      */ import sun.awt.AppContext;
/*      */ import sun.swing.DefaultLookup;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class BasicTextUI
/*      */   extends TextUI
/*      */   implements ViewFactory
/*      */ {
/*      */   transient boolean painted = false;
/*      */   
/*      */   protected Caret createCaret() {
/*  121 */     return new BasicCaret();
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
/*      */   protected Highlighter createHighlighter() {
/*  133 */     return new BasicHighlighter();
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
/*      */   protected String getKeymapName() {
/*  145 */     String str = getClass().getName();
/*  146 */     int i = str.lastIndexOf('.');
/*  147 */     if (i >= 0) {
/*  148 */       str = str.substring(i + 1, str.length());
/*      */     }
/*  150 */     return str;
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
/*      */   protected Keymap createKeymap() {
/*  171 */     String str = getKeymapName();
/*  172 */     Keymap keymap = JTextComponent.getKeymap(str);
/*  173 */     if (keymap == null) {
/*  174 */       Keymap keymap1 = JTextComponent.getKeymap("default");
/*  175 */       keymap = JTextComponent.addKeymap(str, keymap1);
/*  176 */       String str1 = getPropertyPrefix();
/*  177 */       Object object = DefaultLookup.get(this.editor, this, str1 + ".keyBindings");
/*      */       
/*  179 */       if (object != null && object instanceof JTextComponent.KeyBinding[]) {
/*  180 */         JTextComponent.KeyBinding[] arrayOfKeyBinding = (JTextComponent.KeyBinding[])object;
/*  181 */         JTextComponent.loadKeymap(keymap, arrayOfKeyBinding, getComponent().getActions());
/*      */       } 
/*      */     } 
/*  184 */     return keymap;
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
/*      */   protected void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/*  202 */     if (paramPropertyChangeEvent.getPropertyName().equals("editable") || paramPropertyChangeEvent
/*  203 */       .getPropertyName().equals("enabled"))
/*      */     {
/*  205 */       updateBackground((JTextComponent)paramPropertyChangeEvent.getSource());
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
/*      */   private void updateBackground(JTextComponent paramJTextComponent) {
/*  225 */     if (this instanceof javax.swing.plaf.synth.SynthUI || paramJTextComponent instanceof javax.swing.JTextArea) {
/*      */       return;
/*      */     }
/*  228 */     Color color = paramJTextComponent.getBackground();
/*  229 */     if (color instanceof UIResource) {
/*  230 */       String str = getPropertyPrefix();
/*      */ 
/*      */       
/*  233 */       Color color1 = DefaultLookup.getColor(paramJTextComponent, this, str + ".disabledBackground", null);
/*      */       
/*  235 */       Color color2 = DefaultLookup.getColor(paramJTextComponent, this, str + ".inactiveBackground", null);
/*      */       
/*  237 */       Color color3 = DefaultLookup.getColor(paramJTextComponent, this, str + ".background", null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  258 */       if ((paramJTextComponent instanceof javax.swing.JTextArea || paramJTextComponent instanceof JEditorPane) && color != color1 && color != color2 && color != color3) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  266 */       Color color4 = null;
/*  267 */       if (!paramJTextComponent.isEnabled()) {
/*  268 */         color4 = color1;
/*      */       }
/*  270 */       if (color4 == null && !paramJTextComponent.isEditable()) {
/*  271 */         color4 = color2;
/*      */       }
/*  273 */       if (color4 == null) {
/*  274 */         color4 = color3;
/*      */       }
/*  276 */       if (color4 != null && color4 != color) {
/*  277 */         paramJTextComponent.setBackground(color4);
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
/*      */ 
/*      */   
/*      */   protected void installDefaults() {
/*  304 */     String str = getPropertyPrefix();
/*  305 */     Font font = this.editor.getFont();
/*  306 */     if (font == null || font instanceof UIResource) {
/*  307 */       this.editor.setFont(UIManager.getFont(str + ".font"));
/*      */     }
/*      */     
/*  310 */     Color color1 = this.editor.getBackground();
/*  311 */     if (color1 == null || color1 instanceof UIResource) {
/*  312 */       this.editor.setBackground(UIManager.getColor(str + ".background"));
/*      */     }
/*      */     
/*  315 */     Color color2 = this.editor.getForeground();
/*  316 */     if (color2 == null || color2 instanceof UIResource) {
/*  317 */       this.editor.setForeground(UIManager.getColor(str + ".foreground"));
/*      */     }
/*      */     
/*  320 */     Color color3 = this.editor.getCaretColor();
/*  321 */     if (color3 == null || color3 instanceof UIResource) {
/*  322 */       this.editor.setCaretColor(UIManager.getColor(str + ".caretForeground"));
/*      */     }
/*      */     
/*  325 */     Color color4 = this.editor.getSelectionColor();
/*  326 */     if (color4 == null || color4 instanceof UIResource) {
/*  327 */       this.editor.setSelectionColor(UIManager.getColor(str + ".selectionBackground"));
/*      */     }
/*      */     
/*  330 */     Color color5 = this.editor.getSelectedTextColor();
/*  331 */     if (color5 == null || color5 instanceof UIResource) {
/*  332 */       this.editor.setSelectedTextColor(UIManager.getColor(str + ".selectionForeground"));
/*      */     }
/*      */     
/*  335 */     Color color6 = this.editor.getDisabledTextColor();
/*  336 */     if (color6 == null || color6 instanceof UIResource) {
/*  337 */       this.editor.setDisabledTextColor(UIManager.getColor(str + ".inactiveForeground"));
/*      */     }
/*      */     
/*  340 */     Border border = this.editor.getBorder();
/*  341 */     if (border == null || border instanceof UIResource) {
/*  342 */       this.editor.setBorder(UIManager.getBorder(str + ".border"));
/*      */     }
/*      */     
/*  345 */     Insets insets = this.editor.getMargin();
/*  346 */     if (insets == null || insets instanceof UIResource) {
/*  347 */       this.editor.setMargin(UIManager.getInsets(str + ".margin"));
/*      */     }
/*      */     
/*  350 */     updateCursor();
/*      */   }
/*      */   
/*      */   private void installDefaults2() {
/*  354 */     this.editor.addMouseListener(this.dragListener);
/*  355 */     this.editor.addMouseMotionListener(this.dragListener);
/*      */     
/*  357 */     String str = getPropertyPrefix();
/*      */     
/*  359 */     Caret caret = this.editor.getCaret();
/*  360 */     if (caret == null || caret instanceof UIResource) {
/*  361 */       caret = createCaret();
/*  362 */       this.editor.setCaret(caret);
/*      */       
/*  364 */       int i = DefaultLookup.getInt(getComponent(), this, str + ".caretBlinkRate", 500);
/*  365 */       caret.setBlinkRate(i);
/*      */     } 
/*      */     
/*  368 */     Highlighter highlighter = this.editor.getHighlighter();
/*  369 */     if (highlighter == null || highlighter instanceof UIResource) {
/*  370 */       this.editor.setHighlighter(createHighlighter());
/*      */     }
/*      */     
/*  373 */     TransferHandler transferHandler = this.editor.getTransferHandler();
/*  374 */     if (transferHandler == null || transferHandler instanceof UIResource) {
/*  375 */       this.editor.setTransferHandler(getTransferHandler());
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
/*      */   protected void uninstallDefaults() {
/*  389 */     this.editor.removeMouseListener(this.dragListener);
/*  390 */     this.editor.removeMouseMotionListener(this.dragListener);
/*      */     
/*  392 */     if (this.editor.getCaretColor() instanceof UIResource) {
/*  393 */       this.editor.setCaretColor((Color)null);
/*      */     }
/*      */     
/*  396 */     if (this.editor.getSelectionColor() instanceof UIResource) {
/*  397 */       this.editor.setSelectionColor((Color)null);
/*      */     }
/*      */     
/*  400 */     if (this.editor.getDisabledTextColor() instanceof UIResource) {
/*  401 */       this.editor.setDisabledTextColor((Color)null);
/*      */     }
/*      */     
/*  404 */     if (this.editor.getSelectedTextColor() instanceof UIResource) {
/*  405 */       this.editor.setSelectedTextColor((Color)null);
/*      */     }
/*      */     
/*  408 */     if (this.editor.getBorder() instanceof UIResource) {
/*  409 */       this.editor.setBorder((Border)null);
/*      */     }
/*      */     
/*  412 */     if (this.editor.getMargin() instanceof UIResource) {
/*  413 */       this.editor.setMargin((Insets)null);
/*      */     }
/*      */     
/*  416 */     if (this.editor.getCaret() instanceof UIResource) {
/*  417 */       this.editor.setCaret((Caret)null);
/*      */     }
/*      */     
/*  420 */     if (this.editor.getHighlighter() instanceof UIResource) {
/*  421 */       this.editor.setHighlighter((Highlighter)null);
/*      */     }
/*      */     
/*  424 */     if (this.editor.getTransferHandler() instanceof UIResource) {
/*  425 */       this.editor.setTransferHandler((TransferHandler)null);
/*      */     }
/*      */     
/*  428 */     if (this.editor.getCursor() instanceof UIResource) {
/*  429 */       this.editor.setCursor(null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installListeners() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallListeners() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installKeyboardActions() {
/*  448 */     this.editor.setKeymap(createKeymap());
/*      */     
/*  450 */     InputMap inputMap = getInputMap();
/*  451 */     if (inputMap != null) {
/*  452 */       SwingUtilities.replaceUIInputMap(this.editor, 0, inputMap);
/*      */     }
/*      */ 
/*      */     
/*  456 */     ActionMap actionMap = getActionMap();
/*  457 */     if (actionMap != null) {
/*  458 */       SwingUtilities.replaceUIActionMap(this.editor, actionMap);
/*      */     }
/*      */     
/*  461 */     updateFocusAcceleratorBinding(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   InputMap getInputMap() {
/*  468 */     InputMapUIResource inputMapUIResource = new InputMapUIResource();
/*      */ 
/*      */     
/*  471 */     InputMap inputMap = (InputMap)DefaultLookup.get(this.editor, this, 
/*  472 */         getPropertyPrefix() + ".focusInputMap");
/*  473 */     if (inputMap != null) {
/*  474 */       inputMapUIResource.setParent(inputMap);
/*      */     }
/*  476 */     return inputMapUIResource;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void updateFocusAcceleratorBinding(boolean paramBoolean) {
/*  484 */     char c = this.editor.getFocusAccelerator();
/*      */     
/*  486 */     if (paramBoolean || c != '\000') {
/*      */       
/*  488 */       InputMap inputMap = SwingUtilities.getUIInputMap(this.editor, 2);
/*      */       
/*  490 */       if (inputMap == null && c != '\000') {
/*  491 */         inputMap = new ComponentInputMapUIResource(this.editor);
/*  492 */         SwingUtilities.replaceUIInputMap(this.editor, 2, inputMap);
/*      */         
/*  494 */         ActionMap actionMap = getActionMap();
/*  495 */         SwingUtilities.replaceUIActionMap(this.editor, actionMap);
/*      */       } 
/*  497 */       if (inputMap != null) {
/*  498 */         inputMap.clear();
/*  499 */         if (c != '\000') {
/*  500 */           inputMap.put(KeyStroke.getKeyStroke(c, BasicLookAndFeel.getFocusAcceleratorKeyMask()), "requestFocus");
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
/*      */   void updateFocusTraversalKeys() {
/*  521 */     EditorKit editorKit = getEditorKit(this.editor);
/*  522 */     if (editorKit != null && editorKit instanceof DefaultEditorKit) {
/*      */ 
/*      */       
/*  525 */       Set<AWTKeyStroke> set1 = this.editor.getFocusTraversalKeys(0);
/*      */ 
/*      */       
/*  528 */       Set<AWTKeyStroke> set2 = this.editor.getFocusTraversalKeys(1);
/*      */       
/*  530 */       HashSet<AWTKeyStroke> hashSet1 = new HashSet<>(set1);
/*      */       
/*  532 */       HashSet<AWTKeyStroke> hashSet2 = new HashSet<>(set2);
/*      */       
/*  534 */       if (this.editor.isEditable()) {
/*  535 */         hashSet1
/*  536 */           .remove(KeyStroke.getKeyStroke(9, 0));
/*  537 */         hashSet2
/*  538 */           .remove(KeyStroke.getKeyStroke(9, 1));
/*      */       } else {
/*      */         
/*  541 */         hashSet1.add(
/*  542 */             KeyStroke.getKeyStroke(9, 0));
/*  543 */         hashSet2
/*  544 */           .add(
/*  545 */             KeyStroke.getKeyStroke(9, 1));
/*      */       } 
/*  547 */       LookAndFeel.installProperty(this.editor, "focusTraversalKeysForward", hashSet1);
/*      */ 
/*      */       
/*  550 */       LookAndFeel.installProperty(this.editor, "focusTraversalKeysBackward", hashSet2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateCursor() {
/*  561 */     if (!this.editor.isCursorSet() || this.editor
/*  562 */       .getCursor() instanceof UIResource) {
/*  563 */       BasicCursor basicCursor = this.editor.isEditable() ? textCursor : null;
/*  564 */       this.editor.setCursor(basicCursor);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   TransferHandler getTransferHandler() {
/*  573 */     return defaultTransferHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ActionMap getActionMap() {
/*  580 */     String str = getPropertyPrefix() + ".actionMap";
/*  581 */     ActionMap actionMap = (ActionMap)UIManager.get(str);
/*      */     
/*  583 */     if (actionMap == null) {
/*  584 */       actionMap = createActionMap();
/*  585 */       if (actionMap != null) {
/*  586 */         UIManager.getLookAndFeelDefaults().put(str, actionMap);
/*      */       }
/*      */     } 
/*  589 */     ActionMapUIResource actionMapUIResource = new ActionMapUIResource();
/*  590 */     actionMapUIResource.put("requestFocus", new FocusAction());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  600 */     if (getEditorKit(this.editor) instanceof DefaultEditorKit && 
/*  601 */       actionMap != null) {
/*  602 */       Action action = actionMap.get("insert-break");
/*  603 */       if (action != null && action instanceof DefaultEditorKit.InsertBreakAction) {
/*      */         
/*  605 */         TextActionWrapper textActionWrapper = new TextActionWrapper((TextAction)action);
/*  606 */         actionMapUIResource.put(textActionWrapper.getValue("Name"), textActionWrapper);
/*      */       } 
/*      */     } 
/*      */     
/*  610 */     if (actionMap != null) {
/*  611 */       actionMapUIResource.setParent(actionMap);
/*      */     }
/*  613 */     return actionMapUIResource;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ActionMap createActionMap() {
/*  621 */     ActionMapUIResource actionMapUIResource = new ActionMapUIResource();
/*  622 */     Action[] arrayOfAction = this.editor.getActions();
/*      */     
/*  624 */     int i = arrayOfAction.length;
/*  625 */     for (byte b = 0; b < i; b++) {
/*  626 */       Action action = arrayOfAction[b];
/*  627 */       actionMapUIResource.put(action.getValue("Name"), action);
/*      */     } 
/*      */     
/*  630 */     actionMapUIResource.put(TransferHandler.getCutAction().getValue("Name"), 
/*  631 */         TransferHandler.getCutAction());
/*  632 */     actionMapUIResource.put(TransferHandler.getCopyAction().getValue("Name"), 
/*  633 */         TransferHandler.getCopyAction());
/*  634 */     actionMapUIResource.put(TransferHandler.getPasteAction().getValue("Name"), 
/*  635 */         TransferHandler.getPasteAction());
/*  636 */     return actionMapUIResource;
/*      */   }
/*      */   
/*      */   protected void uninstallKeyboardActions() {
/*  640 */     this.editor.setKeymap((Keymap)null);
/*  641 */     SwingUtilities.replaceUIInputMap(this.editor, 2, null);
/*      */     
/*  643 */     SwingUtilities.replaceUIActionMap(this.editor, null);
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
/*      */   protected void paintBackground(Graphics paramGraphics) {
/*  655 */     paramGraphics.setColor(this.editor.getBackground());
/*  656 */     paramGraphics.fillRect(0, 0, this.editor.getWidth(), this.editor.getHeight());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JTextComponent getComponent() {
/*  667 */     return this.editor;
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
/*      */   protected void modelChanged() {
/*  679 */     ViewFactory viewFactory = this.rootView.getViewFactory();
/*  680 */     Document document = this.editor.getDocument();
/*  681 */     Element element = document.getDefaultRootElement();
/*  682 */     setView(viewFactory.create(element));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setView(View paramView) {
/*  693 */     this.rootView.setView(paramView);
/*  694 */     this.painted = false;
/*  695 */     this.editor.revalidate();
/*  696 */     this.editor.repaint();
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
/*      */   
/*      */   protected void paintSafely(Graphics paramGraphics) {
/*  720 */     this.painted = true;
/*  721 */     Highlighter highlighter = this.editor.getHighlighter();
/*  722 */     Caret caret = this.editor.getCaret();
/*      */ 
/*      */     
/*  725 */     if (this.editor.isOpaque()) {
/*  726 */       paintBackground(paramGraphics);
/*      */     }
/*      */ 
/*      */     
/*  730 */     if (highlighter != null) {
/*  731 */       highlighter.paint(paramGraphics);
/*      */     }
/*      */ 
/*      */     
/*  735 */     Rectangle rectangle = getVisibleEditorRect();
/*  736 */     if (rectangle != null) {
/*  737 */       this.rootView.paint(paramGraphics, rectangle);
/*      */     }
/*      */ 
/*      */     
/*  741 */     if (caret != null) {
/*  742 */       caret.paint(paramGraphics);
/*      */     }
/*      */     
/*  745 */     if (this.dropCaret != null) {
/*  746 */       this.dropCaret.paint(paramGraphics);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  777 */     if (paramJComponent instanceof JTextComponent) {
/*  778 */       this.editor = (JTextComponent)paramJComponent;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  783 */       LookAndFeel.installProperty(this.editor, "opaque", Boolean.TRUE);
/*  784 */       LookAndFeel.installProperty(this.editor, "autoscrolls", Boolean.TRUE);
/*      */ 
/*      */       
/*  787 */       installDefaults();
/*  788 */       installDefaults2();
/*      */ 
/*      */       
/*  791 */       this.editor.addPropertyChangeListener(this.updateHandler);
/*  792 */       Document document = this.editor.getDocument();
/*  793 */       if (document == null) {
/*      */ 
/*      */ 
/*      */         
/*  797 */         this.editor.setDocument(getEditorKit(this.editor).createDefaultDocument());
/*      */       } else {
/*  799 */         document.addDocumentListener(this.updateHandler);
/*  800 */         modelChanged();
/*      */       } 
/*      */ 
/*      */       
/*  804 */       installListeners();
/*  805 */       installKeyboardActions();
/*      */       
/*  807 */       LayoutManager layoutManager = this.editor.getLayout();
/*  808 */       if (layoutManager == null || layoutManager instanceof UIResource)
/*      */       {
/*      */         
/*  811 */         this.editor.setLayout(this.updateHandler);
/*      */       }
/*      */       
/*  814 */       updateBackground(this.editor);
/*      */     } else {
/*  816 */       throw new Error("TextUI needs JTextComponent");
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
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  829 */     this.editor.removePropertyChangeListener(this.updateHandler);
/*  830 */     this.editor.getDocument().removeDocumentListener(this.updateHandler);
/*      */ 
/*      */     
/*  833 */     this.painted = false;
/*  834 */     uninstallDefaults();
/*  835 */     this.rootView.setView(null);
/*  836 */     paramJComponent.removeAll();
/*  837 */     LayoutManager layoutManager = paramJComponent.getLayout();
/*  838 */     if (layoutManager instanceof UIResource) {
/*  839 */       paramJComponent.setLayout((LayoutManager)null);
/*      */     }
/*      */ 
/*      */     
/*  843 */     uninstallKeyboardActions();
/*  844 */     uninstallListeners();
/*      */     
/*  846 */     this.editor = null;
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
/*      */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/*  860 */     paint(paramGraphics, paramJComponent);
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
/*      */   public final void paint(Graphics paramGraphics, JComponent paramJComponent) {
/*  875 */     if (this.rootView.getViewCount() > 0 && this.rootView.getView(0) != null) {
/*  876 */       Document document = this.editor.getDocument();
/*  877 */       if (document instanceof AbstractDocument) {
/*  878 */         ((AbstractDocument)document).readLock();
/*      */       }
/*      */       try {
/*  881 */         paintSafely(paramGraphics);
/*      */       } finally {
/*  883 */         if (document instanceof AbstractDocument) {
/*  884 */           ((AbstractDocument)document).readUnlock();
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
/*      */   public Dimension getPreferredSize(JComponent paramJComponent) {
/*  903 */     Document document = this.editor.getDocument();
/*  904 */     Insets insets = paramJComponent.getInsets();
/*  905 */     Dimension dimension = paramJComponent.getSize();
/*      */     
/*  907 */     if (document instanceof AbstractDocument) {
/*  908 */       ((AbstractDocument)document).readLock();
/*      */     }
/*      */     try {
/*  911 */       if (dimension.width > insets.left + insets.right && dimension.height > insets.top + insets.bottom) {
/*  912 */         this.rootView.setSize((dimension.width - insets.left - insets.right), (dimension.height - insets.top - insets.bottom));
/*      */       }
/*  914 */       else if (dimension.width == 0 && dimension.height == 0) {
/*      */ 
/*      */         
/*  917 */         this.rootView.setSize(2.14748365E9F, 2.14748365E9F);
/*      */       } 
/*  919 */       dimension.width = (int)Math.min((long)this.rootView.getPreferredSpan(0) + insets.left + insets.right, 2147483647L);
/*      */       
/*  921 */       dimension.height = (int)Math.min((long)this.rootView.getPreferredSpan(1) + insets.top + insets.bottom, 2147483647L);
/*      */     } finally {
/*      */       
/*  924 */       if (document instanceof AbstractDocument) {
/*  925 */         ((AbstractDocument)document).readUnlock();
/*      */       }
/*      */     } 
/*  928 */     return dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize(JComponent paramJComponent) {
/*  938 */     Document document = this.editor.getDocument();
/*  939 */     Insets insets = paramJComponent.getInsets();
/*  940 */     Dimension dimension = new Dimension();
/*  941 */     if (document instanceof AbstractDocument) {
/*  942 */       ((AbstractDocument)document).readLock();
/*      */     }
/*      */     try {
/*  945 */       dimension.width = (int)this.rootView.getMinimumSpan(0) + insets.left + insets.right;
/*  946 */       dimension.height = (int)this.rootView.getMinimumSpan(1) + insets.top + insets.bottom;
/*      */     } finally {
/*  948 */       if (document instanceof AbstractDocument) {
/*  949 */         ((AbstractDocument)document).readUnlock();
/*      */       }
/*      */     } 
/*  952 */     return dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMaximumSize(JComponent paramJComponent) {
/*  962 */     Document document = this.editor.getDocument();
/*  963 */     Insets insets = paramJComponent.getInsets();
/*  964 */     Dimension dimension = new Dimension();
/*  965 */     if (document instanceof AbstractDocument) {
/*  966 */       ((AbstractDocument)document).readLock();
/*      */     }
/*      */     try {
/*  969 */       dimension.width = (int)Math.min((long)this.rootView.getMaximumSpan(0) + insets.left + insets.right, 2147483647L);
/*      */       
/*  971 */       dimension.height = (int)Math.min((long)this.rootView.getMaximumSpan(1) + insets.top + insets.bottom, 2147483647L);
/*      */     } finally {
/*      */       
/*  974 */       if (document instanceof AbstractDocument) {
/*  975 */         ((AbstractDocument)document).readUnlock();
/*      */       }
/*      */     } 
/*  978 */     return dimension;
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
/*      */   protected Rectangle getVisibleEditorRect() {
/*  995 */     Rectangle rectangle = this.editor.getBounds();
/*  996 */     if (rectangle.width > 0 && rectangle.height > 0) {
/*  997 */       rectangle.x = rectangle.y = 0;
/*  998 */       Insets insets = this.editor.getInsets();
/*  999 */       rectangle.x += insets.left;
/* 1000 */       rectangle.y += insets.top;
/* 1001 */       rectangle.width -= insets.left + insets.right;
/* 1002 */       rectangle.height -= insets.top + insets.bottom;
/* 1003 */       return rectangle;
/*      */     } 
/* 1005 */     return null;
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
/*      */   public Rectangle modelToView(JTextComponent paramJTextComponent, int paramInt) throws BadLocationException {
/* 1022 */     return modelToView(paramJTextComponent, paramInt, Position.Bias.Forward);
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
/*      */   public Rectangle modelToView(JTextComponent paramJTextComponent, int paramInt, Position.Bias paramBias) throws BadLocationException {
/* 1039 */     Document document = this.editor.getDocument();
/* 1040 */     if (document instanceof AbstractDocument) {
/* 1041 */       ((AbstractDocument)document).readLock();
/*      */     }
/*      */     try {
/* 1044 */       Rectangle rectangle = getVisibleEditorRect();
/* 1045 */       if (rectangle != null) {
/* 1046 */         this.rootView.setSize(rectangle.width, rectangle.height);
/* 1047 */         Shape shape = this.rootView.modelToView(paramInt, rectangle, paramBias);
/* 1048 */         if (shape != null) {
/* 1049 */           return shape.getBounds();
/*      */         }
/*      */       } 
/*      */     } finally {
/* 1053 */       if (document instanceof AbstractDocument) {
/* 1054 */         ((AbstractDocument)document).readUnlock();
/*      */       }
/*      */     } 
/* 1057 */     return null;
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
/*      */   public int viewToModel(JTextComponent paramJTextComponent, Point paramPoint) {
/* 1074 */     return viewToModel(paramJTextComponent, paramPoint, discardBias);
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
/*      */   public int viewToModel(JTextComponent paramJTextComponent, Point paramPoint, Position.Bias[] paramArrayOfBias) {
/* 1092 */     int i = -1;
/* 1093 */     Document document = this.editor.getDocument();
/* 1094 */     if (document instanceof AbstractDocument) {
/* 1095 */       ((AbstractDocument)document).readLock();
/*      */     }
/*      */     try {
/* 1098 */       Rectangle rectangle = getVisibleEditorRect();
/* 1099 */       if (rectangle != null) {
/* 1100 */         this.rootView.setSize(rectangle.width, rectangle.height);
/* 1101 */         i = this.rootView.viewToModel(paramPoint.x, paramPoint.y, rectangle, paramArrayOfBias);
/*      */       } 
/*      */     } finally {
/* 1104 */       if (document instanceof AbstractDocument) {
/* 1105 */         ((AbstractDocument)document).readUnlock();
/*      */       }
/*      */     } 
/* 1108 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNextVisualPositionFrom(JTextComponent paramJTextComponent, int paramInt1, Position.Bias paramBias, int paramInt2, Position.Bias[] paramArrayOfBias) throws BadLocationException {
/* 1117 */     Document document = this.editor.getDocument();
/* 1118 */     if (document instanceof AbstractDocument) {
/* 1119 */       ((AbstractDocument)document).readLock();
/*      */     }
/*      */     try {
/* 1122 */       if (this.painted) {
/* 1123 */         Rectangle rectangle = getVisibleEditorRect();
/* 1124 */         if (rectangle != null) {
/* 1125 */           this.rootView.setSize(rectangle.width, rectangle.height);
/*      */         }
/* 1127 */         return this.rootView.getNextVisualPositionFrom(paramInt1, paramBias, rectangle, paramInt2, paramArrayOfBias);
/*      */       } 
/*      */     } finally {
/*      */       
/* 1131 */       if (document instanceof AbstractDocument) {
/* 1132 */         ((AbstractDocument)document).readUnlock();
/*      */       }
/*      */     } 
/* 1135 */     return -1;
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
/*      */   public void damageRange(JTextComponent paramJTextComponent, int paramInt1, int paramInt2) {
/* 1149 */     damageRange(paramJTextComponent, paramInt1, paramInt2, Position.Bias.Forward, Position.Bias.Backward);
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
/*      */   public void damageRange(JTextComponent paramJTextComponent, int paramInt1, int paramInt2, Position.Bias paramBias1, Position.Bias paramBias2) {
/* 1161 */     if (this.painted) {
/* 1162 */       Rectangle rectangle = getVisibleEditorRect();
/* 1163 */       if (rectangle != null) {
/* 1164 */         Document document = paramJTextComponent.getDocument();
/* 1165 */         if (document instanceof AbstractDocument) {
/* 1166 */           ((AbstractDocument)document).readLock();
/*      */         }
/*      */         
/* 1169 */         try { this.rootView.setSize(rectangle.width, rectangle.height);
/* 1170 */           Shape shape = this.rootView.modelToView(paramInt1, paramBias1, paramInt2, paramBias2, rectangle);
/*      */ 
/*      */           
/* 1173 */           Rectangle rectangle1 = (shape instanceof Rectangle) ? (Rectangle)shape : shape.getBounds();
/* 1174 */           this.editor.repaint(rectangle1.x, rectangle1.y, rectangle1.width, rectangle1.height); }
/* 1175 */         catch (BadLocationException badLocationException) {  }
/*      */         finally
/* 1177 */         { if (document instanceof AbstractDocument) {
/* 1178 */             ((AbstractDocument)document).readUnlock();
/*      */           } }
/*      */       
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
/*      */   public EditorKit getEditorKit(JTextComponent paramJTextComponent) {
/* 1193 */     return defaultKit;
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
/*      */   public View getRootView(JTextComponent paramJTextComponent) {
/* 1215 */     return this.rootView;
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
/*      */   public String getToolTipText(JTextComponent paramJTextComponent, Point paramPoint) {
/* 1228 */     if (!this.painted) {
/* 1229 */       return null;
/*      */     }
/* 1231 */     Document document = this.editor.getDocument();
/* 1232 */     String str = null;
/* 1233 */     Rectangle rectangle = getVisibleEditorRect();
/*      */     
/* 1235 */     if (rectangle != null) {
/* 1236 */       if (document instanceof AbstractDocument) {
/* 1237 */         ((AbstractDocument)document).readLock();
/*      */       }
/*      */       try {
/* 1240 */         str = this.rootView.getToolTipText(paramPoint.x, paramPoint.y, rectangle);
/*      */       } finally {
/* 1242 */         if (document instanceof AbstractDocument) {
/* 1243 */           ((AbstractDocument)document).readUnlock();
/*      */         }
/*      */       } 
/*      */     } 
/* 1247 */     return str;
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
/*      */   public View create(Element paramElement) {
/* 1263 */     return null;
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
/*      */   public View create(Element paramElement, int paramInt1, int paramInt2) {
/* 1279 */     return null;
/*      */   }
/*      */   
/*      */   public static class BasicCaret extends DefaultCaret implements UIResource {}
/*      */   
/*      */   public static class BasicHighlighter extends DefaultHighlighter implements UIResource {}
/*      */   
/*      */   static class BasicCursor extends Cursor implements UIResource {
/*      */     BasicCursor(int param1Int) {
/* 1288 */       super(param1Int);
/*      */     }
/*      */     
/*      */     BasicCursor(String param1String) {
/* 1292 */       super(param1String);
/*      */     }
/*      */   }
/*      */   
/* 1296 */   private static BasicCursor textCursor = new BasicCursor(2);
/*      */ 
/*      */   
/* 1299 */   private static final EditorKit defaultKit = new DefaultEditorKit();
/*      */ 
/*      */   
/* 1302 */   transient RootView rootView = new RootView();
/* 1303 */   transient UpdateHandler updateHandler = new UpdateHandler();
/* 1304 */   private static final TransferHandler defaultTransferHandler = new TextTransferHandler();
/* 1305 */   private final DragListener dragListener = getDragListener();
/* 1306 */   private static final Position.Bias[] discardBias = new Position.Bias[1];
/*      */   transient JTextComponent editor;
/*      */   private DefaultCaret dropCaret;
/*      */   
/*      */   class RootView
/*      */     extends View
/*      */   {
/*      */     private View view;
/*      */     
/*      */     RootView() {
/* 1316 */       super(null);
/*      */     }
/*      */     
/*      */     void setView(View param1View) {
/* 1320 */       View view = this.view;
/* 1321 */       this.view = null;
/* 1322 */       if (view != null)
/*      */       {
/*      */         
/* 1325 */         view.setParent(null);
/*      */       }
/* 1327 */       if (param1View != null) {
/* 1328 */         param1View.setParent(this);
/*      */       }
/* 1330 */       this.view = param1View;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet getAttributes() {
/* 1339 */       return null;
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
/*      */     public float getPreferredSpan(int param1Int) {
/* 1352 */       if (this.view != null) {
/* 1353 */         return this.view.getPreferredSpan(param1Int);
/*      */       }
/* 1355 */       return 10.0F;
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
/*      */     public float getMinimumSpan(int param1Int) {
/* 1368 */       if (this.view != null) {
/* 1369 */         return this.view.getMinimumSpan(param1Int);
/*      */       }
/* 1371 */       return 10.0F;
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
/*      */     public float getMaximumSpan(int param1Int) {
/* 1384 */       return 2.14748365E9F;
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
/*      */     public void preferenceChanged(View param1View, boolean param1Boolean1, boolean param1Boolean2) {
/* 1406 */       BasicTextUI.this.editor.revalidate();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getAlignment(int param1Int) {
/* 1417 */       if (this.view != null) {
/* 1418 */         return this.view.getAlignment(param1Int);
/*      */       }
/* 1420 */       return 0.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paint(Graphics param1Graphics, Shape param1Shape) {
/* 1430 */       if (this.view != null) {
/*      */         
/* 1432 */         Rectangle rectangle = (param1Shape instanceof Rectangle) ? (Rectangle)param1Shape : param1Shape.getBounds();
/* 1433 */         setSize(rectangle.width, rectangle.height);
/* 1434 */         this.view.paint(param1Graphics, param1Shape);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setParent(View param1View) {
/* 1444 */       throw new Error("Can't set parent on root view");
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
/*      */     public int getViewCount() {
/* 1456 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public View getView(int param1Int) {
/* 1466 */       return this.view;
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
/*      */     public int getViewIndex(int param1Int, Position.Bias param1Bias) {
/* 1480 */       return 0;
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
/*      */     public Shape getChildAllocation(int param1Int, Shape param1Shape) {
/* 1496 */       return param1Shape;
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
/*      */     public Shape modelToView(int param1Int, Shape param1Shape, Position.Bias param1Bias) throws BadLocationException {
/* 1508 */       if (this.view != null) {
/* 1509 */         return this.view.modelToView(param1Int, param1Shape, param1Bias);
/*      */       }
/* 1511 */       return null;
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
/*      */     public Shape modelToView(int param1Int1, Position.Bias param1Bias1, int param1Int2, Position.Bias param1Bias2, Shape param1Shape) throws BadLocationException {
/* 1534 */       if (this.view != null) {
/* 1535 */         return this.view.modelToView(param1Int1, param1Bias1, param1Int2, param1Bias2, param1Shape);
/*      */       }
/* 1537 */       return null;
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
/*      */     public int viewToModel(float param1Float1, float param1Float2, Shape param1Shape, Position.Bias[] param1ArrayOfBias) {
/* 1551 */       if (this.view != null) {
/* 1552 */         return this.view.viewToModel(param1Float1, param1Float2, param1Shape, param1ArrayOfBias);
/*      */       }
/*      */       
/* 1555 */       return -1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getNextVisualPositionFrom(int param1Int1, Position.Bias param1Bias, Shape param1Shape, int param1Int2, Position.Bias[] param1ArrayOfBias) throws BadLocationException {
/* 1584 */       if (param1Int1 < -1) {
/* 1585 */         throw new BadLocationException("invalid position", param1Int1);
/*      */       }
/* 1587 */       if (this.view != null) {
/* 1588 */         int i = this.view.getNextVisualPositionFrom(param1Int1, param1Bias, param1Shape, param1Int2, param1ArrayOfBias);
/*      */         
/* 1590 */         if (i != -1) {
/* 1591 */           param1Int1 = i;
/*      */         } else {
/*      */           
/* 1594 */           param1ArrayOfBias[0] = param1Bias;
/*      */         } 
/*      */       } 
/* 1597 */       return param1Int1;
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
/*      */     public void insertUpdate(DocumentEvent param1DocumentEvent, Shape param1Shape, ViewFactory param1ViewFactory) {
/* 1609 */       if (this.view != null) {
/* 1610 */         this.view.insertUpdate(param1DocumentEvent, param1Shape, param1ViewFactory);
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
/*      */     public void removeUpdate(DocumentEvent param1DocumentEvent, Shape param1Shape, ViewFactory param1ViewFactory) {
/* 1623 */       if (this.view != null) {
/* 1624 */         this.view.removeUpdate(param1DocumentEvent, param1Shape, param1ViewFactory);
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
/*      */     public void changedUpdate(DocumentEvent param1DocumentEvent, Shape param1Shape, ViewFactory param1ViewFactory) {
/* 1637 */       if (this.view != null) {
/* 1638 */         this.view.changedUpdate(param1DocumentEvent, param1Shape, param1ViewFactory);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Document getDocument() {
/* 1648 */       return BasicTextUI.this.editor.getDocument();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getStartOffset() {
/* 1657 */       if (this.view != null) {
/* 1658 */         return this.view.getStartOffset();
/*      */       }
/* 1660 */       return getElement().getStartOffset();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getEndOffset() {
/* 1669 */       if (this.view != null) {
/* 1670 */         return this.view.getEndOffset();
/*      */       }
/* 1672 */       return getElement().getEndOffset();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element getElement() {
/* 1681 */       if (this.view != null) {
/* 1682 */         return this.view.getElement();
/*      */       }
/* 1684 */       return BasicTextUI.this.editor.getDocument().getDefaultRootElement();
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
/*      */     public View breakView(int param1Int, float param1Float, Shape param1Shape) {
/* 1697 */       throw new Error("Can't break root view");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getResizeWeight(int param1Int) {
/* 1708 */       if (this.view != null) {
/* 1709 */         return this.view.getResizeWeight(param1Int);
/*      */       }
/* 1711 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSize(float param1Float1, float param1Float2) {
/* 1721 */       if (this.view != null) {
/* 1722 */         this.view.setSize(param1Float1, param1Float2);
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
/*      */     public Container getContainer() {
/* 1735 */       return BasicTextUI.this.editor;
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
/*      */     public ViewFactory getViewFactory() {
/* 1752 */       EditorKit editorKit = BasicTextUI.this.getEditorKit(BasicTextUI.this.editor);
/* 1753 */       ViewFactory viewFactory = editorKit.getViewFactory();
/* 1754 */       if (viewFactory != null) {
/* 1755 */         return viewFactory;
/*      */       }
/* 1757 */       return BasicTextUI.this;
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
/*      */   class UpdateHandler
/*      */     implements PropertyChangeListener, DocumentListener, LayoutManager2, UIResource
/*      */   {
/*      */     private Hashtable<Component, Object> constraints;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1781 */       Object object1 = param1PropertyChangeEvent.getOldValue();
/* 1782 */       Object object2 = param1PropertyChangeEvent.getNewValue();
/* 1783 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 1784 */       if (object1 instanceof Document || object2 instanceof Document) {
/* 1785 */         if (object1 != null) {
/* 1786 */           ((Document)object1).removeDocumentListener(this);
/* 1787 */           this.i18nView = false;
/*      */         } 
/* 1789 */         if (object2 != null) {
/* 1790 */           ((Document)object2).addDocumentListener(this);
/* 1791 */           if ("document" == str) {
/* 1792 */             BasicTextUI.this.setView(null);
/* 1793 */             BasicTextUI.this.propertyChange(param1PropertyChangeEvent);
/* 1794 */             BasicTextUI.this.modelChanged();
/*      */             return;
/*      */           } 
/*      */         } 
/* 1798 */         BasicTextUI.this.modelChanged();
/*      */       } 
/* 1800 */       if ("focusAccelerator" == str) {
/* 1801 */         BasicTextUI.this.updateFocusAcceleratorBinding(true);
/* 1802 */       } else if ("componentOrientation" == str) {
/*      */ 
/*      */         
/* 1805 */         BasicTextUI.this.modelChanged();
/* 1806 */       } else if ("font" == str) {
/* 1807 */         BasicTextUI.this.modelChanged();
/* 1808 */       } else if ("dropLocation" == str) {
/* 1809 */         dropIndexChanged();
/* 1810 */       } else if ("editable" == str) {
/* 1811 */         BasicTextUI.this.updateCursor();
/* 1812 */         BasicTextUI.this.modelChanged();
/*      */       } 
/* 1814 */       BasicTextUI.this.propertyChange(param1PropertyChangeEvent);
/*      */     }
/*      */     
/*      */     private void dropIndexChanged() {
/* 1818 */       if (BasicTextUI.this.editor.getDropMode() == DropMode.USE_SELECTION) {
/*      */         return;
/*      */       }
/*      */       
/* 1822 */       JTextComponent.DropLocation dropLocation = BasicTextUI.this.editor.getDropLocation();
/*      */       
/* 1824 */       if (dropLocation == null) {
/* 1825 */         if (BasicTextUI.this.dropCaret != null) {
/* 1826 */           BasicTextUI.this.dropCaret.deinstall(BasicTextUI.this.editor);
/* 1827 */           BasicTextUI.this.editor.repaint(BasicTextUI.this.dropCaret);
/* 1828 */           BasicTextUI.this.dropCaret = null;
/*      */         } 
/*      */       } else {
/* 1831 */         if (BasicTextUI.this.dropCaret == null) {
/* 1832 */           BasicTextUI.this.dropCaret = new BasicTextUI.BasicCaret();
/* 1833 */           BasicTextUI.this.dropCaret.install(BasicTextUI.this.editor);
/* 1834 */           BasicTextUI.this.dropCaret.setVisible(true);
/*      */         } 
/*      */         
/* 1837 */         BasicTextUI.this.dropCaret.setDot(dropLocation.getIndex(), dropLocation
/* 1838 */             .getBias());
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
/*      */     public final void insertUpdate(DocumentEvent param1DocumentEvent) {
/* 1855 */       Document document = param1DocumentEvent.getDocument();
/* 1856 */       Object object = document.getProperty("i18n");
/* 1857 */       if (object instanceof Boolean) {
/* 1858 */         Boolean bool = (Boolean)object;
/* 1859 */         if (bool.booleanValue() != this.i18nView) {
/*      */           
/* 1861 */           this.i18nView = bool.booleanValue();
/* 1862 */           BasicTextUI.this.modelChanged();
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       
/* 1868 */       Rectangle rectangle = BasicTextUI.this.painted ? BasicTextUI.this.getVisibleEditorRect() : null;
/* 1869 */       BasicTextUI.this.rootView.insertUpdate(param1DocumentEvent, rectangle, BasicTextUI.this.rootView.getViewFactory());
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
/*      */     public final void removeUpdate(DocumentEvent param1DocumentEvent) {
/* 1883 */       Rectangle rectangle = BasicTextUI.this.painted ? BasicTextUI.this.getVisibleEditorRect() : null;
/* 1884 */       BasicTextUI.this.rootView.removeUpdate(param1DocumentEvent, rectangle, BasicTextUI.this.rootView.getViewFactory());
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
/*      */     public final void changedUpdate(DocumentEvent param1DocumentEvent) {
/* 1898 */       Rectangle rectangle = BasicTextUI.this.painted ? BasicTextUI.this.getVisibleEditorRect() : null;
/* 1899 */       BasicTextUI.this.rootView.changedUpdate(param1DocumentEvent, rectangle, BasicTextUI.this.rootView.getViewFactory());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeLayoutComponent(Component param1Component) {
/* 1919 */       if (this.constraints != null)
/*      */       {
/* 1921 */         this.constraints.remove(param1Component);
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
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/* 1934 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/* 1945 */       return null;
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
/*      */     public void layoutContainer(Container param1Container) {
/* 1962 */       if (this.constraints != null && !this.constraints.isEmpty()) {
/* 1963 */         Rectangle rectangle = BasicTextUI.this.getVisibleEditorRect();
/* 1964 */         if (rectangle != null) {
/* 1965 */           Document document = BasicTextUI.this.editor.getDocument();
/* 1966 */           if (document instanceof AbstractDocument) {
/* 1967 */             ((AbstractDocument)document).readLock();
/*      */           }
/*      */           try {
/* 1970 */             BasicTextUI.this.rootView.setSize(rectangle.width, rectangle.height);
/* 1971 */             Enumeration<Component> enumeration = this.constraints.keys();
/* 1972 */             while (enumeration.hasMoreElements()) {
/* 1973 */               Component component = enumeration.nextElement();
/* 1974 */               View view = (View)this.constraints.get(component);
/* 1975 */               Shape shape = calculateViewPosition(rectangle, view);
/* 1976 */               if (shape != null) {
/*      */                 
/* 1978 */                 Rectangle rectangle1 = (shape instanceof Rectangle) ? (Rectangle)shape : shape.getBounds();
/* 1979 */                 component.setBounds(rectangle1);
/*      */               } 
/*      */             } 
/*      */           } finally {
/* 1983 */             if (document instanceof AbstractDocument) {
/* 1984 */               ((AbstractDocument)document).readUnlock();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Shape calculateViewPosition(Shape param1Shape, View param1View) {
/* 1995 */       int i = param1View.getStartOffset();
/* 1996 */       View view = null;
/* 1997 */       for (BasicTextUI.RootView rootView = BasicTextUI.this.rootView; rootView != null && rootView != param1View; view1 = view) {
/* 1998 */         View view1; int j = rootView.getViewIndex(i, Position.Bias.Forward);
/* 1999 */         param1Shape = rootView.getChildAllocation(j, param1Shape);
/* 2000 */         view = rootView.getView(j);
/*      */       } 
/* 2002 */       return (view != null) ? param1Shape : null;
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
/*      */     public void addLayoutComponent(Component param1Component, Object param1Object) {
/* 2014 */       if (param1Object instanceof View) {
/* 2015 */         if (this.constraints == null) {
/* 2016 */           this.constraints = new Hashtable<>(7);
/*      */         }
/* 2018 */         this.constraints.put(param1Component, param1Object);
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
/*      */     public Dimension maximumLayoutSize(Container param1Container) {
/* 2030 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getLayoutAlignmentX(Container param1Container) {
/* 2041 */       return 0.5F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getLayoutAlignmentY(Container param1Container) {
/* 2052 */       return 0.5F;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void invalidateLayout(Container param1Container) {}
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean i18nView = false;
/*      */   }
/*      */ 
/*      */   
/*      */   class TextActionWrapper
/*      */     extends TextAction
/*      */   {
/*      */     TextAction action;
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*      */       this.action.actionPerformed(param1ActionEvent);
/*      */     }
/*      */ 
/*      */     
/*      */     public TextActionWrapper(TextAction param1TextAction) {
/* 2077 */       super((String)param1TextAction.getValue("Name"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2091 */       this.action = null;
/*      */       this.action = param1TextAction;
/*      */     }
/*      */     
/*      */     public boolean isEnabled() {
/*      */       return (BasicTextUI.this.editor == null || BasicTextUI.this.editor.isEditable()) ? this.action.isEnabled() : false;
/*      */     } }
/*      */   
/*      */   class FocusAction extends AbstractAction {
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2101 */       BasicTextUI.this.editor.requestFocus();
/*      */     }
/*      */     
/*      */     public boolean isEnabled() {
/* 2105 */       return BasicTextUI.this.editor.isEditable();
/*      */     }
/*      */   }
/*      */   
/*      */   private static DragListener getDragListener() {
/* 2110 */     synchronized (DragListener.class) {
/*      */ 
/*      */       
/* 2113 */       DragListener dragListener = (DragListener)AppContext.getAppContext().get(DragListener.class);
/*      */       
/* 2115 */       if (dragListener == null) {
/* 2116 */         dragListener = new DragListener();
/* 2117 */         AppContext.getAppContext().put(DragListener.class, dragListener);
/*      */       } 
/*      */       
/* 2120 */       return dragListener;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected abstract String getPropertyPrefix();
/*      */   
/*      */   static class DragListener
/*      */     extends MouseInputAdapter
/*      */     implements DragRecognitionSupport.BeforeDrag
/*      */   {
/*      */     private boolean dragStarted;
/*      */     
/*      */     public void dragStarting(MouseEvent param1MouseEvent) {
/* 2134 */       this.dragStarted = true;
/*      */     }
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 2138 */       JTextComponent jTextComponent = (JTextComponent)param1MouseEvent.getSource();
/* 2139 */       if (jTextComponent.getDragEnabled()) {
/* 2140 */         this.dragStarted = false;
/* 2141 */         if (isDragPossible(param1MouseEvent) && DragRecognitionSupport.mousePressed(param1MouseEvent)) {
/* 2142 */           param1MouseEvent.consume();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 2148 */       JTextComponent jTextComponent = (JTextComponent)param1MouseEvent.getSource();
/* 2149 */       if (jTextComponent.getDragEnabled()) {
/* 2150 */         if (this.dragStarted) {
/* 2151 */           param1MouseEvent.consume();
/*      */         }
/*      */         
/* 2154 */         DragRecognitionSupport.mouseReleased(param1MouseEvent);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 2159 */       JTextComponent jTextComponent = (JTextComponent)param1MouseEvent.getSource();
/* 2160 */       if (jTextComponent.getDragEnabled() && (
/* 2161 */         this.dragStarted || DragRecognitionSupport.mouseDragged(param1MouseEvent, this))) {
/* 2162 */         param1MouseEvent.consume();
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
/*      */     protected boolean isDragPossible(MouseEvent param1MouseEvent) {
/* 2175 */       JTextComponent jTextComponent = (JTextComponent)param1MouseEvent.getSource();
/* 2176 */       if (jTextComponent.isEnabled()) {
/* 2177 */         Caret caret = jTextComponent.getCaret();
/* 2178 */         int i = caret.getDot();
/* 2179 */         int j = caret.getMark();
/* 2180 */         if (i != j) {
/* 2181 */           Point point = new Point(param1MouseEvent.getX(), param1MouseEvent.getY());
/* 2182 */           int k = jTextComponent.viewToModel(point);
/*      */           
/* 2184 */           int m = Math.min(i, j);
/* 2185 */           int n = Math.max(i, j);
/* 2186 */           if (k >= m && k < n) {
/* 2187 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/* 2191 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class TextTransferHandler
/*      */     extends TransferHandler
/*      */     implements UIResource
/*      */   {
/*      */     private JTextComponent exportComp;
/*      */ 
/*      */     
/*      */     private boolean shouldRemove;
/*      */ 
/*      */     
/*      */     private int p0;
/*      */ 
/*      */     
/*      */     private int p1;
/*      */     
/*      */     private boolean modeBetween = false;
/*      */     
/*      */     private boolean isDrop = false;
/*      */     
/* 2216 */     private int dropAction = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Position.Bias dropBias;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected DataFlavor getImportFlavor(DataFlavor[] param1ArrayOfDataFlavor, JTextComponent param1JTextComponent) {
/* 2236 */       DataFlavor dataFlavor1 = null;
/* 2237 */       DataFlavor dataFlavor2 = null;
/* 2238 */       DataFlavor dataFlavor3 = null;
/*      */       
/* 2240 */       if (param1JTextComponent instanceof JEditorPane) {
/* 2241 */         for (byte b1 = 0; b1 < param1ArrayOfDataFlavor.length; b1++) {
/* 2242 */           String str = param1ArrayOfDataFlavor[b1].getMimeType();
/* 2243 */           if (str.startsWith(((JEditorPane)param1JTextComponent).getEditorKit().getContentType()))
/* 2244 */             return param1ArrayOfDataFlavor[b1]; 
/* 2245 */           if (dataFlavor1 == null && str.startsWith("text/plain")) {
/* 2246 */             dataFlavor1 = param1ArrayOfDataFlavor[b1];
/* 2247 */           } else if (dataFlavor2 == null && str.startsWith("application/x-java-jvm-local-objectref") && param1ArrayOfDataFlavor[b1]
/* 2248 */             .getRepresentationClass() == String.class) {
/* 2249 */             dataFlavor2 = param1ArrayOfDataFlavor[b1];
/* 2250 */           } else if (dataFlavor3 == null && param1ArrayOfDataFlavor[b1].equals(DataFlavor.stringFlavor)) {
/* 2251 */             dataFlavor3 = param1ArrayOfDataFlavor[b1];
/*      */           } 
/*      */         } 
/* 2254 */         if (dataFlavor1 != null)
/* 2255 */           return dataFlavor1; 
/* 2256 */         if (dataFlavor2 != null)
/* 2257 */           return dataFlavor2; 
/* 2258 */         if (dataFlavor3 != null) {
/* 2259 */           return dataFlavor3;
/*      */         }
/* 2261 */         return null;
/*      */       } 
/*      */ 
/*      */       
/* 2265 */       for (byte b = 0; b < param1ArrayOfDataFlavor.length; b++) {
/* 2266 */         String str = param1ArrayOfDataFlavor[b].getMimeType();
/* 2267 */         if (str.startsWith("text/plain"))
/* 2268 */           return param1ArrayOfDataFlavor[b]; 
/* 2269 */         if (dataFlavor2 == null && str.startsWith("application/x-java-jvm-local-objectref") && param1ArrayOfDataFlavor[b]
/* 2270 */           .getRepresentationClass() == String.class) {
/* 2271 */           dataFlavor2 = param1ArrayOfDataFlavor[b];
/* 2272 */         } else if (dataFlavor3 == null && param1ArrayOfDataFlavor[b].equals(DataFlavor.stringFlavor)) {
/* 2273 */           dataFlavor3 = param1ArrayOfDataFlavor[b];
/*      */         } 
/*      */       } 
/* 2276 */       if (dataFlavor2 != null)
/* 2277 */         return dataFlavor2; 
/* 2278 */       if (dataFlavor3 != null) {
/* 2279 */         return dataFlavor3;
/*      */       }
/* 2281 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void handleReaderImport(Reader param1Reader, JTextComponent param1JTextComponent, boolean param1Boolean) throws BadLocationException, IOException {
/* 2289 */       if (param1Boolean) {
/* 2290 */         int i = param1JTextComponent.getSelectionStart();
/* 2291 */         int j = param1JTextComponent.getSelectionEnd();
/* 2292 */         int k = j - i;
/* 2293 */         EditorKit editorKit = param1JTextComponent.getUI().getEditorKit(param1JTextComponent);
/* 2294 */         Document document = param1JTextComponent.getDocument();
/* 2295 */         if (k > 0) {
/* 2296 */           document.remove(i, k);
/*      */         }
/* 2298 */         editorKit.read(param1Reader, document, i);
/*      */       } else {
/* 2300 */         char[] arrayOfChar = new char[1024];
/*      */         
/* 2302 */         boolean bool = false;
/*      */         
/* 2304 */         StringBuffer stringBuffer = null;
/*      */         
/*      */         int i;
/*      */         
/* 2308 */         while ((i = param1Reader.read(arrayOfChar, 0, arrayOfChar.length)) != -1) {
/* 2309 */           if (stringBuffer == null) {
/* 2310 */             stringBuffer = new StringBuffer(i);
/*      */           }
/* 2312 */           byte b1 = 0;
/* 2313 */           for (byte b2 = 0; b2 < i; b2++) {
/* 2314 */             switch (arrayOfChar[b2]) {
/*      */               case '\r':
/* 2316 */                 if (bool) {
/* 2317 */                   if (b2 == 0) {
/* 2318 */                     stringBuffer.append('\n'); break;
/*      */                   } 
/* 2320 */                   arrayOfChar[b2 - 1] = '\n';
/*      */                   break;
/*      */                 } 
/* 2323 */                 bool = true;
/*      */                 break;
/*      */               
/*      */               case '\n':
/* 2327 */                 if (bool) {
/* 2328 */                   if (b2 > b1 + 1) {
/* 2329 */                     stringBuffer.append(arrayOfChar, b1, b2 - b1 - 1);
/*      */                   }
/*      */ 
/*      */                   
/* 2333 */                   bool = false;
/* 2334 */                   b1 = b2;
/*      */                 } 
/*      */                 break;
/*      */               default:
/* 2338 */                 if (bool) {
/* 2339 */                   if (b2 == 0) {
/* 2340 */                     stringBuffer.append('\n');
/*      */                   } else {
/* 2342 */                     arrayOfChar[b2 - 1] = '\n';
/*      */                   } 
/* 2344 */                   bool = false;
/*      */                 } 
/*      */                 break;
/*      */             } 
/*      */           } 
/* 2349 */           if (b1 < i) {
/* 2350 */             if (bool) {
/* 2351 */               if (b1 < i - 1)
/* 2352 */                 stringBuffer.append(arrayOfChar, b1, i - b1 - 1); 
/*      */               continue;
/*      */             } 
/* 2355 */             stringBuffer.append(arrayOfChar, b1, i - b1);
/*      */           } 
/*      */         } 
/*      */         
/* 2359 */         if (bool) {
/* 2360 */           stringBuffer.append('\n');
/*      */         }
/* 2362 */         param1JTextComponent.replaceSelection((stringBuffer != null) ? stringBuffer.toString() : "");
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
/*      */     public int getSourceActions(JComponent param1JComponent) {
/* 2381 */       if (param1JComponent instanceof javax.swing.JPasswordField && param1JComponent
/* 2382 */         .getClientProperty("JPasswordField.cutCopyAllowed") != Boolean.TRUE)
/*      */       {
/* 2384 */         return 0;
/*      */       }
/*      */       
/* 2387 */       return ((JTextComponent)param1JComponent).isEditable() ? 3 : 1;
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
/*      */     protected Transferable createTransferable(JComponent param1JComponent) {
/* 2400 */       this.exportComp = (JTextComponent)param1JComponent;
/* 2401 */       this.shouldRemove = true;
/* 2402 */       this.p0 = this.exportComp.getSelectionStart();
/* 2403 */       this.p1 = this.exportComp.getSelectionEnd();
/* 2404 */       return (this.p0 != this.p1) ? new TextTransferable(this.exportComp, this.p0, this.p1) : null;
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
/*      */     protected void exportDone(JComponent param1JComponent, Transferable param1Transferable, int param1Int) {
/* 2419 */       if (this.shouldRemove && param1Int == 2) {
/* 2420 */         TextTransferable textTransferable = (TextTransferable)param1Transferable;
/* 2421 */         textTransferable.removeText();
/*      */       } 
/*      */       
/* 2424 */       this.exportComp = null;
/*      */     }
/*      */     
/*      */     public boolean importData(TransferHandler.TransferSupport param1TransferSupport) {
/* 2428 */       this.isDrop = param1TransferSupport.isDrop();
/*      */       
/* 2430 */       if (this.isDrop) {
/* 2431 */         this
/* 2432 */           .modeBetween = (((JTextComponent)param1TransferSupport.getComponent()).getDropMode() == DropMode.INSERT);
/*      */         
/* 2434 */         this.dropBias = ((JTextComponent.DropLocation)param1TransferSupport.getDropLocation()).getBias();
/*      */         
/* 2436 */         this.dropAction = param1TransferSupport.getDropAction();
/*      */       } 
/*      */       
/*      */       try {
/* 2440 */         return super.importData(param1TransferSupport);
/*      */       } finally {
/* 2442 */         this.isDrop = false;
/* 2443 */         this.modeBetween = false;
/* 2444 */         this.dropBias = null;
/* 2445 */         this.dropAction = 2;
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
/*      */     public boolean importData(JComponent param1JComponent, Transferable param1Transferable) {
/* 2461 */       JTextComponent jTextComponent = (JTextComponent)param1JComponent;
/*      */ 
/*      */       
/* 2464 */       int i = this.modeBetween ? jTextComponent.getDropLocation().getIndex() : jTextComponent.getCaretPosition();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2470 */       if (this.dropAction == 2 && jTextComponent == this.exportComp && i >= this.p0 && i <= this.p1) {
/* 2471 */         this.shouldRemove = false;
/* 2472 */         return true;
/*      */       } 
/*      */       
/* 2475 */       boolean bool = false;
/* 2476 */       DataFlavor dataFlavor = getImportFlavor(param1Transferable.getTransferDataFlavors(), jTextComponent);
/* 2477 */       if (dataFlavor != null) {
/*      */         
/* 2479 */         try { boolean bool1 = false;
/* 2480 */           if (param1JComponent instanceof JEditorPane) {
/* 2481 */             JEditorPane jEditorPane = (JEditorPane)param1JComponent;
/* 2482 */             if (!jEditorPane.getContentType().startsWith("text/plain") && dataFlavor
/* 2483 */               .getMimeType().startsWith(jEditorPane.getContentType())) {
/* 2484 */               bool1 = true;
/*      */             }
/*      */           } 
/* 2487 */           InputContext inputContext = jTextComponent.getInputContext();
/* 2488 */           if (inputContext != null) {
/* 2489 */             inputContext.endComposition();
/*      */           }
/* 2491 */           Reader reader = dataFlavor.getReaderForText(param1Transferable);
/*      */           
/* 2493 */           if (this.modeBetween) {
/* 2494 */             Caret caret = jTextComponent.getCaret();
/* 2495 */             if (caret instanceof DefaultCaret) {
/* 2496 */               ((DefaultCaret)caret).setDot(i, this.dropBias);
/*      */             } else {
/* 2498 */               jTextComponent.setCaretPosition(i);
/*      */             } 
/*      */           } 
/*      */           
/* 2502 */           handleReaderImport(reader, jTextComponent, bool1);
/*      */           
/* 2504 */           if (this.isDrop) {
/* 2505 */             jTextComponent.requestFocus();
/* 2506 */             Caret caret = jTextComponent.getCaret();
/* 2507 */             if (caret instanceof DefaultCaret) {
/* 2508 */               int j = caret.getDot();
/* 2509 */               Position.Bias bias = ((DefaultCaret)caret).getDotBias();
/*      */               
/* 2511 */               ((DefaultCaret)caret).setDot(i, this.dropBias);
/* 2512 */               ((DefaultCaret)caret).moveDot(j, bias);
/*      */             } else {
/* 2514 */               jTextComponent.select(i, jTextComponent.getCaretPosition());
/*      */             } 
/*      */           } 
/*      */           
/* 2518 */           bool = true; }
/* 2519 */         catch (UnsupportedFlavorException unsupportedFlavorException) {  }
/* 2520 */         catch (BadLocationException badLocationException) {  }
/* 2521 */         catch (IOException iOException) {}
/*      */       }
/*      */       
/* 2524 */       return bool;
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
/*      */     public boolean canImport(JComponent param1JComponent, DataFlavor[] param1ArrayOfDataFlavor) {
/* 2538 */       JTextComponent jTextComponent = (JTextComponent)param1JComponent;
/* 2539 */       if (!jTextComponent.isEditable() || !jTextComponent.isEnabled()) {
/* 2540 */         return false;
/*      */       }
/* 2542 */       return (getImportFlavor(param1ArrayOfDataFlavor, jTextComponent) != null);
/*      */     }
/*      */ 
/*      */     
/*      */     static class TextTransferable
/*      */       extends BasicTransferable
/*      */     {
/*      */       Position p0;
/*      */       
/*      */       Position p1;
/*      */       String mimeType;
/*      */       String richText;
/*      */       JTextComponent c;
/*      */       
/*      */       TextTransferable(JTextComponent param2JTextComponent, int param2Int1, int param2Int2) {
/* 2557 */         super(null, null);
/*      */         
/* 2559 */         this.c = param2JTextComponent;
/*      */         
/* 2561 */         Document document = param2JTextComponent.getDocument();
/*      */ 
/*      */         
/* 2564 */         try { this.p0 = document.createPosition(param2Int1);
/* 2565 */           this.p1 = document.createPosition(param2Int2);
/*      */           
/* 2567 */           this.plainData = param2JTextComponent.getSelectedText();
/*      */           
/* 2569 */           if (param2JTextComponent instanceof JEditorPane) {
/* 2570 */             JEditorPane jEditorPane = (JEditorPane)param2JTextComponent;
/*      */             
/* 2572 */             this.mimeType = jEditorPane.getContentType();
/*      */             
/* 2574 */             if (this.mimeType.startsWith("text/plain")) {
/*      */               return;
/*      */             }
/*      */             
/* 2578 */             StringWriter stringWriter = new StringWriter(this.p1.getOffset() - this.p0.getOffset());
/* 2579 */             jEditorPane.getEditorKit().write(stringWriter, document, this.p0.getOffset(), this.p1.getOffset() - this.p0.getOffset());
/*      */             
/* 2581 */             if (this.mimeType.startsWith("text/html")) {
/* 2582 */               this.htmlData = stringWriter.toString();
/*      */             } else {
/* 2584 */               this.richText = stringWriter.toString();
/*      */             } 
/*      */           }  }
/* 2587 */         catch (BadLocationException badLocationException) {  }
/* 2588 */         catch (IOException iOException) {}
/*      */       }
/*      */ 
/*      */       
/*      */       void removeText() {
/* 2593 */         if (this.p0 != null && this.p1 != null && this.p0.getOffset() != this.p1.getOffset()) {
/*      */           try {
/* 2595 */             Document document = this.c.getDocument();
/* 2596 */             document.remove(this.p0.getOffset(), this.p1.getOffset() - this.p0.getOffset());
/* 2597 */           } catch (BadLocationException badLocationException) {}
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected DataFlavor[] getRicherFlavors() {
/* 2609 */         if (this.richText == null) {
/* 2610 */           return null;
/*      */         }
/*      */         
/*      */         try {
/* 2614 */           DataFlavor[] arrayOfDataFlavor = new DataFlavor[3];
/* 2615 */           arrayOfDataFlavor[0] = new DataFlavor(this.mimeType + ";class=java.lang.String");
/* 2616 */           arrayOfDataFlavor[1] = new DataFlavor(this.mimeType + ";class=java.io.Reader");
/* 2617 */           arrayOfDataFlavor[2] = new DataFlavor(this.mimeType + ";class=java.io.InputStream;charset=unicode");
/* 2618 */           return arrayOfDataFlavor;
/* 2619 */         } catch (ClassNotFoundException classNotFoundException) {
/*      */ 
/*      */ 
/*      */           
/* 2623 */           return null;
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       protected Object getRicherData(DataFlavor param2DataFlavor) throws UnsupportedFlavorException {
/* 2630 */         if (this.richText == null) {
/* 2631 */           return null;
/*      */         }
/*      */         
/* 2634 */         if (String.class.equals(param2DataFlavor.getRepresentationClass()))
/* 2635 */           return this.richText; 
/* 2636 */         if (Reader.class.equals(param2DataFlavor.getRepresentationClass()))
/* 2637 */           return new StringReader(this.richText); 
/* 2638 */         if (InputStream.class.equals(param2DataFlavor.getRepresentationClass())) {
/* 2639 */           return new StringBufferInputStream(this.richText);
/*      */         }
/* 2641 */         throw new UnsupportedFlavorException(param2DataFlavor);
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicTextUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */