/*      */ package javax.swing.text;
/*      */ 
/*      */ import java.awt.Graphics;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.datatransfer.Clipboard;
/*      */ import java.awt.datatransfer.ClipboardOwner;
/*      */ import java.awt.datatransfer.StringSelection;
/*      */ import java.awt.datatransfer.Transferable;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.JPasswordField;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.Timer;
/*      */ import javax.swing.TransferHandler;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.event.DocumentListener;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import javax.swing.plaf.TextUI;
/*      */ import sun.swing.SwingUtilities2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DefaultCaret
/*      */   extends Rectangle
/*      */   implements Caret, FocusListener, MouseListener, MouseMotionListener
/*      */ {
/*      */   public static final int UPDATE_WHEN_ON_EDT = 0;
/*      */   public static final int NEVER_UPDATE = 1;
/*      */   public static final int ALWAYS_UPDATE = 2;
/*      */   
/*      */   public void setUpdatePolicy(int paramInt) {
/*  203 */     this.updatePolicy = paramInt;
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
/*      */   public int getUpdatePolicy() {
/*  220 */     return this.updatePolicy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final JTextComponent getComponent() {
/*  230 */     return this.component;
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
/*      */   protected final synchronized void repaint() {
/*  244 */     if (this.component != null) {
/*  245 */       this.component.repaint(this.x, this.y, this.width, this.height);
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
/*      */   protected synchronized void damage(Rectangle paramRectangle) {
/*  260 */     if (paramRectangle != null) {
/*  261 */       int i = getCaretWidth(paramRectangle.height);
/*  262 */       this.x = paramRectangle.x - 4 - (i >> 1);
/*  263 */       this.y = paramRectangle.y;
/*  264 */       this.width = 9 + i;
/*  265 */       this.height = paramRectangle.height;
/*  266 */       repaint();
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
/*      */   protected void adjustVisibility(Rectangle paramRectangle) {
/*  281 */     if (this.component == null) {
/*      */       return;
/*      */     }
/*  284 */     if (SwingUtilities.isEventDispatchThread()) {
/*  285 */       this.component.scrollRectToVisible(paramRectangle);
/*      */     } else {
/*  287 */       SwingUtilities.invokeLater(new SafeScroller(paramRectangle));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Highlighter.HighlightPainter getSelectionPainter() {
/*  297 */     return DefaultHighlighter.DefaultPainter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void positionCaret(MouseEvent paramMouseEvent) {
/*  307 */     Point point = new Point(paramMouseEvent.getX(), paramMouseEvent.getY());
/*  308 */     Position.Bias[] arrayOfBias = new Position.Bias[1];
/*  309 */     int i = this.component.getUI().viewToModel(this.component, point, arrayOfBias);
/*  310 */     if (arrayOfBias[0] == null)
/*  311 */       arrayOfBias[0] = Position.Bias.Forward; 
/*  312 */     if (i >= 0) {
/*  313 */       setDot(i, arrayOfBias[0]);
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
/*      */   protected void moveCaret(MouseEvent paramMouseEvent) {
/*  326 */     Point point = new Point(paramMouseEvent.getX(), paramMouseEvent.getY());
/*  327 */     Position.Bias[] arrayOfBias = new Position.Bias[1];
/*  328 */     int i = this.component.getUI().viewToModel(this.component, point, arrayOfBias);
/*  329 */     if (arrayOfBias[0] == null)
/*  330 */       arrayOfBias[0] = Position.Bias.Forward; 
/*  331 */     if (i >= 0) {
/*  332 */       moveDot(i, arrayOfBias[0]);
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
/*      */   public void focusGained(FocusEvent paramFocusEvent) {
/*  347 */     if (this.component.isEnabled()) {
/*  348 */       if (this.component.isEditable()) {
/*  349 */         setVisible(true);
/*      */       }
/*  351 */       setSelectionVisible(true);
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
/*      */   public void focusLost(FocusEvent paramFocusEvent) {
/*  364 */     setVisible(false);
/*  365 */     setSelectionVisible((this.ownsSelection || paramFocusEvent.isTemporary()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void selectWord(MouseEvent paramMouseEvent) {
/*  373 */     if (this.selectedWordEvent != null && this.selectedWordEvent
/*  374 */       .getX() == paramMouseEvent.getX() && this.selectedWordEvent
/*  375 */       .getY() == paramMouseEvent.getY()) {
/*      */       return;
/*      */     }
/*      */     
/*  379 */     Action action = null;
/*  380 */     ActionMap actionMap = getComponent().getActionMap();
/*  381 */     if (actionMap != null) {
/*  382 */       action = actionMap.get("select-word");
/*      */     }
/*  384 */     if (action == null) {
/*  385 */       if (selectWord == null) {
/*  386 */         selectWord = new DefaultEditorKit.SelectWordAction();
/*      */       }
/*  388 */       action = selectWord;
/*      */     } 
/*  390 */     action.actionPerformed(new ActionEvent(getComponent(), 1001, null, paramMouseEvent
/*  391 */           .getWhen(), paramMouseEvent.getModifiers()));
/*  392 */     this.selectedWordEvent = paramMouseEvent;
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
/*      */   public void mouseClicked(MouseEvent paramMouseEvent) {
/*  406 */     if (getComponent() == null) {
/*      */       return;
/*      */     }
/*      */     
/*  410 */     int i = SwingUtilities2.getAdjustedClickCount(getComponent(), paramMouseEvent);
/*      */     
/*  412 */     if (!paramMouseEvent.isConsumed()) {
/*  413 */       if (SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
/*      */         
/*  415 */         if (i == 1) {
/*  416 */           this.selectedWordEvent = null;
/*  417 */         } else if (i == 2 && 
/*  418 */           SwingUtilities2.canEventAccessSystemClipboard(paramMouseEvent)) {
/*  419 */           selectWord(paramMouseEvent);
/*  420 */           this.selectedWordEvent = null;
/*  421 */         } else if (i == 3 && 
/*  422 */           SwingUtilities2.canEventAccessSystemClipboard(paramMouseEvent)) {
/*  423 */           Action action = null;
/*  424 */           ActionMap actionMap = getComponent().getActionMap();
/*  425 */           if (actionMap != null) {
/*  426 */             action = actionMap.get("select-line");
/*      */           }
/*  428 */           if (action == null) {
/*  429 */             if (selectLine == null) {
/*  430 */               selectLine = new DefaultEditorKit.SelectLineAction();
/*      */             }
/*  432 */             action = selectLine;
/*      */           } 
/*  434 */           action.actionPerformed(new ActionEvent(getComponent(), 1001, null, paramMouseEvent
/*  435 */                 .getWhen(), paramMouseEvent.getModifiers()));
/*      */         } 
/*  437 */       } else if (SwingUtilities.isMiddleMouseButton(paramMouseEvent)) {
/*      */         
/*  439 */         if (i == 1 && this.component.isEditable() && this.component.isEnabled() && 
/*  440 */           SwingUtilities2.canEventAccessSystemClipboard(paramMouseEvent)) {
/*      */           
/*  442 */           JTextComponent jTextComponent = (JTextComponent)paramMouseEvent.getSource();
/*  443 */           if (jTextComponent != null) {
/*      */             try {
/*  445 */               Toolkit toolkit = jTextComponent.getToolkit();
/*  446 */               Clipboard clipboard = toolkit.getSystemSelection();
/*  447 */               if (clipboard != null) {
/*      */                 
/*  449 */                 adjustCaret(paramMouseEvent);
/*  450 */                 TransferHandler transferHandler = jTextComponent.getTransferHandler();
/*  451 */                 if (transferHandler != null) {
/*  452 */                   Transferable transferable = null;
/*      */                   
/*      */                   try {
/*  455 */                     transferable = clipboard.getContents(null);
/*  456 */                   } catch (IllegalStateException illegalStateException) {
/*      */                     
/*  458 */                     UIManager.getLookAndFeel().provideErrorFeedback(jTextComponent);
/*      */                   } 
/*      */                   
/*  461 */                   if (transferable != null) {
/*  462 */                     transferHandler.importData(jTextComponent, transferable);
/*      */                   }
/*      */                 } 
/*  465 */                 adjustFocus(true);
/*      */               } 
/*  467 */             } catch (HeadlessException headlessException) {}
/*      */           }
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
/*      */   public void mousePressed(MouseEvent paramMouseEvent) {
/*  489 */     int i = SwingUtilities2.getAdjustedClickCount(getComponent(), paramMouseEvent);
/*      */     
/*  491 */     if (SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
/*  492 */       if (paramMouseEvent.isConsumed()) {
/*  493 */         this.shouldHandleRelease = true;
/*      */       } else {
/*  495 */         this.shouldHandleRelease = false;
/*  496 */         adjustCaretAndFocus(paramMouseEvent);
/*  497 */         if (i == 2 && 
/*  498 */           SwingUtilities2.canEventAccessSystemClipboard(paramMouseEvent)) {
/*  499 */           selectWord(paramMouseEvent);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   void adjustCaretAndFocus(MouseEvent paramMouseEvent) {
/*  506 */     adjustCaret(paramMouseEvent);
/*  507 */     adjustFocus(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void adjustCaret(MouseEvent paramMouseEvent) {
/*  514 */     if ((paramMouseEvent.getModifiers() & 0x1) != 0 && 
/*  515 */       getDot() != -1) {
/*  516 */       moveCaret(paramMouseEvent);
/*  517 */     } else if (!paramMouseEvent.isPopupTrigger()) {
/*  518 */       positionCaret(paramMouseEvent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void adjustFocus(boolean paramBoolean) {
/*  528 */     if (this.component != null && this.component.isEnabled() && this.component
/*  529 */       .isRequestFocusEnabled()) {
/*  530 */       if (paramBoolean) {
/*  531 */         this.component.requestFocusInWindow();
/*      */       } else {
/*      */         
/*  534 */         this.component.requestFocus();
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
/*      */   public void mouseReleased(MouseEvent paramMouseEvent) {
/*  546 */     if (!paramMouseEvent.isConsumed() && this.shouldHandleRelease && 
/*      */       
/*  548 */       SwingUtilities.isLeftMouseButton(paramMouseEvent))
/*      */     {
/*  550 */       adjustCaretAndFocus(paramMouseEvent);
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
/*      */   public void mouseEntered(MouseEvent paramMouseEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mouseExited(MouseEvent paramMouseEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mouseDragged(MouseEvent paramMouseEvent) {
/*  585 */     if (!paramMouseEvent.isConsumed() && SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
/*  586 */       moveCaret(paramMouseEvent);
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
/*      */   public void mouseMoved(MouseEvent paramMouseEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paint(Graphics paramGraphics) {
/*  618 */     if (isVisible()) {
/*      */       try {
/*  620 */         TextUI textUI = this.component.getUI();
/*  621 */         Rectangle rectangle = textUI.modelToView(this.component, this.dot, this.dotBias);
/*      */         
/*  623 */         if (rectangle == null || (rectangle.width == 0 && rectangle.height == 0)) {
/*      */           return;
/*      */         }
/*  626 */         if (this.width > 0 && this.height > 0 && 
/*  627 */           !_contains(rectangle.x, rectangle.y, rectangle.width, rectangle.height)) {
/*      */ 
/*      */           
/*  630 */           Rectangle rectangle1 = paramGraphics.getClipBounds();
/*      */           
/*  632 */           if (rectangle1 != null && !rectangle1.contains(this))
/*      */           {
/*      */             
/*  635 */             repaint();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  640 */           damage(rectangle);
/*      */         } 
/*  642 */         paramGraphics.setColor(this.component.getCaretColor());
/*  643 */         int i = getCaretWidth(rectangle.height);
/*  644 */         rectangle.x -= i >> 1;
/*  645 */         paramGraphics.fillRect(rectangle.x, rectangle.y, i, rectangle.height);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  652 */         Document document = this.component.getDocument();
/*  653 */         if (document instanceof AbstractDocument) {
/*  654 */           Element element = ((AbstractDocument)document).getBidiRootElement();
/*  655 */           if (element != null && element.getElementCount() > 1) {
/*      */             
/*  657 */             this.flagXPoints[0] = rectangle.x + (this.dotLTR ? i : 0);
/*  658 */             this.flagYPoints[0] = rectangle.y;
/*  659 */             this.flagXPoints[1] = this.flagXPoints[0];
/*  660 */             this.flagYPoints[1] = this.flagYPoints[0] + 4;
/*  661 */             this.flagXPoints[2] = this.flagXPoints[0] + (this.dotLTR ? 4 : -4);
/*  662 */             this.flagYPoints[2] = this.flagYPoints[0];
/*  663 */             paramGraphics.fillPolygon(this.flagXPoints, this.flagYPoints, 3);
/*      */           } 
/*      */         } 
/*  666 */       } catch (BadLocationException badLocationException) {}
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
/*      */   public void install(JTextComponent paramJTextComponent) {
/*  685 */     this.component = paramJTextComponent;
/*  686 */     Document document = paramJTextComponent.getDocument();
/*  687 */     this.dot = this.mark = 0;
/*  688 */     this.dotLTR = this.markLTR = true;
/*  689 */     this.dotBias = this.markBias = Position.Bias.Forward;
/*  690 */     if (document != null) {
/*  691 */       document.addDocumentListener(this.handler);
/*      */     }
/*  693 */     paramJTextComponent.addPropertyChangeListener(this.handler);
/*  694 */     paramJTextComponent.addFocusListener(this);
/*  695 */     paramJTextComponent.addMouseListener(this);
/*  696 */     paramJTextComponent.addMouseMotionListener(this);
/*      */ 
/*      */ 
/*      */     
/*  700 */     if (this.component.hasFocus()) {
/*  701 */       focusGained((FocusEvent)null);
/*      */     }
/*      */     
/*  704 */     Number number = (Number)paramJTextComponent.getClientProperty("caretAspectRatio");
/*  705 */     if (number != null) {
/*  706 */       this.aspectRatio = number.floatValue();
/*      */     } else {
/*  708 */       this.aspectRatio = -1.0F;
/*      */     } 
/*      */     
/*  711 */     Integer integer = (Integer)paramJTextComponent.getClientProperty("caretWidth");
/*  712 */     if (integer != null) {
/*  713 */       this.caretWidth = integer.intValue();
/*      */     } else {
/*  715 */       this.caretWidth = -1;
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
/*      */   public void deinstall(JTextComponent paramJTextComponent) {
/*  728 */     paramJTextComponent.removeMouseListener(this);
/*  729 */     paramJTextComponent.removeMouseMotionListener(this);
/*  730 */     paramJTextComponent.removeFocusListener(this);
/*  731 */     paramJTextComponent.removePropertyChangeListener(this.handler);
/*  732 */     Document document = paramJTextComponent.getDocument();
/*  733 */     if (document != null) {
/*  734 */       document.removeDocumentListener(this.handler);
/*      */     }
/*  736 */     synchronized (this) {
/*  737 */       this.component = null;
/*      */     } 
/*  739 */     if (this.flasher != null) {
/*  740 */       this.flasher.stop();
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
/*      */   public void addChangeListener(ChangeListener paramChangeListener) {
/*  754 */     this.listenerList.add(ChangeListener.class, paramChangeListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeChangeListener(ChangeListener paramChangeListener) {
/*  764 */     this.listenerList.remove(ChangeListener.class, paramChangeListener);
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
/*      */   public ChangeListener[] getChangeListeners() {
/*  781 */     return this.listenerList.<ChangeListener>getListeners(ChangeListener.class);
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
/*      */   protected void fireStateChanged() {
/*  794 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*      */ 
/*      */     
/*  797 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/*  798 */       if (arrayOfObject[i] == ChangeListener.class) {
/*      */         
/*  800 */         if (this.changeEvent == null)
/*  801 */           this.changeEvent = new ChangeEvent(this); 
/*  802 */         ((ChangeListener)arrayOfObject[i + 1]).stateChanged(this.changeEvent);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) {
/*  844 */     return this.listenerList.getListeners(paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectionVisible(boolean paramBoolean) {
/*  853 */     if (paramBoolean != this.selectionVisible) {
/*  854 */       this.selectionVisible = paramBoolean;
/*  855 */       if (this.selectionVisible) {
/*      */         
/*  857 */         Highlighter highlighter = this.component.getHighlighter();
/*  858 */         if (this.dot != this.mark && highlighter != null && this.selectionTag == null) {
/*  859 */           int i = Math.min(this.dot, this.mark);
/*  860 */           int j = Math.max(this.dot, this.mark);
/*  861 */           Highlighter.HighlightPainter highlightPainter = getSelectionPainter();
/*      */           try {
/*  863 */             this.selectionTag = highlighter.addHighlight(i, j, highlightPainter);
/*  864 */           } catch (BadLocationException badLocationException) {
/*  865 */             this.selectionTag = null;
/*      */           }
/*      */         
/*      */         }
/*      */       
/*  870 */       } else if (this.selectionTag != null) {
/*  871 */         Highlighter highlighter = this.component.getHighlighter();
/*  872 */         highlighter.removeHighlight(this.selectionTag);
/*  873 */         this.selectionTag = null;
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
/*      */   public boolean isSelectionVisible() {
/*  885 */     return this.selectionVisible;
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
/*      */   public boolean isActive() {
/*  903 */     return this.active;
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
/*      */   public boolean isVisible() {
/*  924 */     return this.visible;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVisible(boolean paramBoolean) {
/*  965 */     this.active = paramBoolean;
/*  966 */     if (this.component != null) {
/*  967 */       TextUI textUI = this.component.getUI();
/*  968 */       if (this.visible != paramBoolean) {
/*  969 */         this.visible = paramBoolean;
/*      */         
/*      */         try {
/*  972 */           Rectangle rectangle = textUI.modelToView(this.component, this.dot, this.dotBias);
/*  973 */           damage(rectangle);
/*  974 */         } catch (BadLocationException badLocationException) {}
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  979 */     if (this.flasher != null) {
/*  980 */       if (this.visible) {
/*  981 */         this.flasher.start();
/*      */       } else {
/*  983 */         this.flasher.stop();
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
/*      */   public void setBlinkRate(int paramInt) {
/*  995 */     if (paramInt != 0) {
/*  996 */       if (this.flasher == null) {
/*  997 */         this.flasher = new Timer(paramInt, this.handler);
/*      */       }
/*  999 */       this.flasher.setDelay(paramInt);
/*      */     }
/* 1001 */     else if (this.flasher != null) {
/* 1002 */       this.flasher.stop();
/* 1003 */       this.flasher.removeActionListener(this.handler);
/* 1004 */       this.flasher = null;
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
/*      */   public int getBlinkRate() {
/* 1017 */     return (this.flasher == null) ? 0 : this.flasher.getDelay();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDot() {
/* 1027 */     return this.dot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMark() {
/* 1038 */     return this.mark;
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
/*      */   public void setDot(int paramInt) {
/* 1051 */     setDot(paramInt, Position.Bias.Forward);
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
/*      */   public void moveDot(int paramInt) {
/* 1063 */     moveDot(paramInt, Position.Bias.Forward);
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
/*      */   public void moveDot(int paramInt, Position.Bias paramBias) {
/* 1079 */     if (paramBias == null) {
/* 1080 */       throw new IllegalArgumentException("null bias");
/*      */     }
/*      */     
/* 1083 */     if (!this.component.isEnabled()) {
/*      */       
/* 1085 */       setDot(paramInt, paramBias);
/*      */       return;
/*      */     } 
/* 1088 */     if (paramInt != this.dot) {
/* 1089 */       NavigationFilter navigationFilter = this.component.getNavigationFilter();
/*      */       
/* 1091 */       if (navigationFilter != null) {
/* 1092 */         navigationFilter.moveDot(getFilterBypass(), paramInt, paramBias);
/*      */       } else {
/*      */         
/* 1095 */         handleMoveDot(paramInt, paramBias);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   void handleMoveDot(int paramInt, Position.Bias paramBias) {
/* 1101 */     changeCaretPosition(paramInt, paramBias);
/*      */     
/* 1103 */     if (this.selectionVisible) {
/* 1104 */       Highlighter highlighter = this.component.getHighlighter();
/* 1105 */       if (highlighter != null) {
/* 1106 */         int i = Math.min(paramInt, this.mark);
/* 1107 */         int j = Math.max(paramInt, this.mark);
/*      */ 
/*      */         
/* 1110 */         if (i == j) {
/* 1111 */           if (this.selectionTag != null) {
/* 1112 */             highlighter.removeHighlight(this.selectionTag);
/* 1113 */             this.selectionTag = null;
/*      */           } 
/*      */         } else {
/*      */           
/*      */           try {
/* 1118 */             if (this.selectionTag != null) {
/* 1119 */               highlighter.changeHighlight(this.selectionTag, i, j);
/*      */             } else {
/* 1121 */               Highlighter.HighlightPainter highlightPainter = getSelectionPainter();
/* 1122 */               this.selectionTag = highlighter.addHighlight(i, j, highlightPainter);
/*      */             } 
/* 1124 */           } catch (BadLocationException badLocationException) {
/* 1125 */             throw new StateInvariantError("Bad caret position");
/*      */           } 
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
/*      */   public void setDot(int paramInt, Position.Bias paramBias) {
/* 1144 */     if (paramBias == null) {
/* 1145 */       throw new IllegalArgumentException("null bias");
/*      */     }
/*      */     
/* 1148 */     NavigationFilter navigationFilter = this.component.getNavigationFilter();
/*      */     
/* 1150 */     if (navigationFilter != null) {
/* 1151 */       navigationFilter.setDot(getFilterBypass(), paramInt, paramBias);
/*      */     } else {
/*      */       
/* 1154 */       handleSetDot(paramInt, paramBias);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void handleSetDot(int paramInt, Position.Bias paramBias) {
/* 1160 */     Document document = this.component.getDocument();
/* 1161 */     if (document != null) {
/* 1162 */       paramInt = Math.min(paramInt, document.getLength());
/*      */     }
/* 1164 */     paramInt = Math.max(paramInt, 0);
/*      */ 
/*      */     
/* 1167 */     if (paramInt == 0) {
/* 1168 */       paramBias = Position.Bias.Forward;
/*      */     }
/* 1170 */     this.mark = paramInt;
/* 1171 */     if (this.dot != paramInt || this.dotBias != paramBias || this.selectionTag != null || this.forceCaretPositionChange)
/*      */     {
/* 1173 */       changeCaretPosition(paramInt, paramBias);
/*      */     }
/* 1175 */     this.markBias = this.dotBias;
/* 1176 */     this.markLTR = this.dotLTR;
/* 1177 */     Highlighter highlighter = this.component.getHighlighter();
/* 1178 */     if (highlighter != null && this.selectionTag != null) {
/* 1179 */       highlighter.removeHighlight(this.selectionTag);
/* 1180 */       this.selectionTag = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Position.Bias getDotBias() {
/* 1191 */     return this.dotBias;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Position.Bias getMarkBias() {
/* 1201 */     return this.markBias;
/*      */   }
/*      */   
/*      */   boolean isDotLeftToRight() {
/* 1205 */     return this.dotLTR;
/*      */   }
/*      */   
/*      */   boolean isMarkLeftToRight() {
/* 1209 */     return this.markLTR;
/*      */   }
/*      */   
/*      */   boolean isPositionLTR(int paramInt, Position.Bias paramBias) {
/* 1213 */     Document document = this.component.getDocument();
/* 1214 */     if (paramBias == Position.Bias.Backward && --paramInt < 0)
/* 1215 */       paramInt = 0; 
/* 1216 */     return AbstractDocument.isLeftToRight(document, paramInt, paramInt);
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
/*      */   Position.Bias guessBiasForOffset(int paramInt, Position.Bias paramBias, boolean paramBoolean) {
/* 1230 */     if (paramBoolean != isPositionLTR(paramInt, paramBias)) {
/* 1231 */       paramBias = Position.Bias.Backward;
/*      */     }
/* 1233 */     else if (paramBias != Position.Bias.Backward && paramBoolean != 
/* 1234 */       isPositionLTR(paramInt, Position.Bias.Backward)) {
/* 1235 */       paramBias = Position.Bias.Backward;
/*      */     } 
/* 1237 */     if (paramBias == Position.Bias.Backward && paramInt > 0) {
/*      */       try {
/* 1239 */         Segment segment = new Segment();
/* 1240 */         this.component.getDocument().getText(paramInt - 1, 1, segment);
/* 1241 */         if (segment.count > 0 && segment.array[segment.offset] == '\n') {
/* 1242 */           paramBias = Position.Bias.Forward;
/*      */         }
/*      */       }
/* 1245 */       catch (BadLocationException badLocationException) {}
/*      */     }
/* 1247 */     return paramBias;
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
/*      */   void changeCaretPosition(int paramInt, Position.Bias paramBias) {
/* 1261 */     repaint();
/*      */ 
/*      */ 
/*      */     
/* 1265 */     if (this.flasher != null && this.flasher.isRunning()) {
/* 1266 */       this.visible = true;
/* 1267 */       this.flasher.restart();
/*      */     } 
/*      */ 
/*      */     
/* 1271 */     this.dot = paramInt;
/* 1272 */     this.dotBias = paramBias;
/* 1273 */     this.dotLTR = isPositionLTR(paramInt, paramBias);
/* 1274 */     fireStateChanged();
/*      */     
/* 1276 */     updateSystemSelection();
/*      */     
/* 1278 */     setMagicCaretPosition((Point)null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1285 */     Runnable runnable = new Runnable() {
/*      */         public void run() {
/* 1287 */           DefaultCaret.this.repaintNewCaret();
/*      */         }
/*      */       };
/* 1290 */     SwingUtilities.invokeLater(runnable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void repaintNewCaret() {
/* 1300 */     if (this.component != null) {
/* 1301 */       TextUI textUI = this.component.getUI();
/* 1302 */       Document document = this.component.getDocument();
/* 1303 */       if (textUI != null && document != null) {
/*      */         Rectangle rectangle;
/*      */ 
/*      */         
/*      */         try {
/* 1308 */           rectangle = textUI.modelToView(this.component, this.dot, this.dotBias);
/* 1309 */         } catch (BadLocationException badLocationException) {
/* 1310 */           rectangle = null;
/*      */         } 
/* 1312 */         if (rectangle != null) {
/* 1313 */           adjustVisibility(rectangle);
/*      */           
/* 1315 */           if (getMagicCaretPosition() == null) {
/* 1316 */             setMagicCaretPosition(new Point(rectangle.x, rectangle.y));
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1321 */         damage(rectangle);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateSystemSelection() {
/* 1327 */     if (!SwingUtilities2.canCurrentEventAccessSystemClipboard()) {
/*      */       return;
/*      */     }
/* 1330 */     if (this.dot != this.mark && this.component != null && this.component.hasFocus()) {
/* 1331 */       Clipboard clipboard = getSystemSelection();
/* 1332 */       if (clipboard != null) {
/*      */         String str;
/* 1334 */         if (this.component instanceof JPasswordField && this.component
/* 1335 */           .getClientProperty("JPasswordField.cutCopyAllowed") != Boolean.TRUE) {
/*      */ 
/*      */           
/* 1338 */           StringBuilder stringBuilder = null;
/* 1339 */           char c = ((JPasswordField)this.component).getEchoChar();
/* 1340 */           int i = Math.min(getDot(), getMark());
/* 1341 */           int j = Math.max(getDot(), getMark());
/* 1342 */           for (int k = i; k < j; k++) {
/* 1343 */             if (stringBuilder == null) {
/* 1344 */               stringBuilder = new StringBuilder();
/*      */             }
/* 1346 */             stringBuilder.append(c);
/*      */           } 
/* 1348 */           str = (stringBuilder != null) ? stringBuilder.toString() : null;
/*      */         } else {
/* 1350 */           str = this.component.getSelectedText();
/*      */         } 
/*      */         try {
/* 1353 */           clipboard.setContents(new StringSelection(str), 
/* 1354 */               getClipboardOwner());
/*      */           
/* 1356 */           this.ownsSelection = true;
/* 1357 */         } catch (IllegalStateException illegalStateException) {}
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Clipboard getSystemSelection() {
/*      */     try {
/* 1368 */       return this.component.getToolkit().getSystemSelection();
/* 1369 */     } catch (HeadlessException headlessException) {
/*      */     
/* 1371 */     } catch (SecurityException securityException) {}
/*      */ 
/*      */     
/* 1374 */     return null;
/*      */   }
/*      */   
/*      */   private ClipboardOwner getClipboardOwner() {
/* 1378 */     return this.handler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ensureValidPosition() {
/* 1388 */     int i = this.component.getDocument().getLength();
/* 1389 */     if (this.dot > i || this.mark > i)
/*      */     {
/*      */ 
/*      */       
/* 1393 */       handleSetDot(i, Position.Bias.Forward);
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
/*      */   public void setMagicCaretPosition(Point paramPoint) {
/* 1407 */     this.magicCaretPosition = paramPoint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point getMagicCaretPosition() {
/* 1417 */     return this.magicCaretPosition;
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
/*      */   public boolean equals(Object paramObject) {
/* 1431 */     return (this == paramObject);
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1435 */     String str = "Dot=(" + this.dot + ", " + this.dotBias + ")";
/* 1436 */     str = str + " Mark=(" + this.mark + ", " + this.markBias + ")";
/* 1437 */     return str;
/*      */   }
/*      */   
/*      */   private NavigationFilter.FilterBypass getFilterBypass() {
/* 1441 */     if (this.filterBypass == null) {
/* 1442 */       this.filterBypass = new DefaultFilterBypass();
/*      */     }
/* 1444 */     return this.filterBypass;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _contains(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1450 */     int i = this.width;
/* 1451 */     int j = this.height;
/* 1452 */     if ((i | j | paramInt3 | paramInt4) < 0)
/*      */     {
/* 1454 */       return false;
/*      */     }
/*      */     
/* 1457 */     int k = this.x;
/* 1458 */     int m = this.y;
/* 1459 */     if (paramInt1 < k || paramInt2 < m) {
/* 1460 */       return false;
/*      */     }
/* 1462 */     if (paramInt3 > 0) {
/* 1463 */       i += k;
/* 1464 */       paramInt3 += paramInt1;
/* 1465 */       if (paramInt3 <= paramInt1)
/*      */       
/*      */       { 
/*      */ 
/*      */         
/* 1470 */         if (i >= k || paramInt3 > i) return false;
/*      */         
/*      */         
/*      */          }
/*      */       
/* 1475 */       else if (i >= k && paramInt3 > i) { return false; }
/*      */ 
/*      */     
/* 1478 */     } else if (k + i < paramInt1) {
/* 1479 */       return false;
/*      */     } 
/* 1481 */     if (paramInt4 > 0) {
/* 1482 */       j += m;
/* 1483 */       paramInt4 += paramInt2;
/* 1484 */       if (paramInt4 <= paramInt2)
/* 1485 */       { if (j >= m || paramInt4 > j) return false;
/*      */          }
/* 1487 */       else if (j >= m && paramInt4 > j) { return false; }
/*      */ 
/*      */     
/* 1490 */     } else if (m + j < paramInt2) {
/* 1491 */       return false;
/*      */     } 
/* 1493 */     return true;
/*      */   }
/*      */   
/*      */   int getCaretWidth(int paramInt) {
/* 1497 */     if (this.aspectRatio > -1.0F) {
/* 1498 */       return (int)(this.aspectRatio * paramInt) + 1;
/*      */     }
/*      */     
/* 1501 */     if (this.caretWidth > -1) {
/* 1502 */       return this.caretWidth;
/*      */     }
/* 1504 */     Object object = UIManager.get("Caret.width");
/* 1505 */     if (object instanceof Integer) {
/* 1506 */       return ((Integer)object).intValue();
/*      */     }
/* 1508 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 1518 */     paramObjectInputStream.defaultReadObject();
/* 1519 */     this.handler = new Handler();
/* 1520 */     if (!paramObjectInputStream.readBoolean()) {
/* 1521 */       this.dotBias = Position.Bias.Forward;
/*      */     } else {
/*      */       
/* 1524 */       this.dotBias = Position.Bias.Backward;
/*      */     } 
/* 1526 */     if (!paramObjectInputStream.readBoolean()) {
/* 1527 */       this.markBias = Position.Bias.Forward;
/*      */     } else {
/*      */       
/* 1530 */       this.markBias = Position.Bias.Backward;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1535 */     paramObjectOutputStream.defaultWriteObject();
/* 1536 */     paramObjectOutputStream.writeBoolean((this.dotBias == Position.Bias.Backward));
/* 1537 */     paramObjectOutputStream.writeBoolean((this.markBias == Position.Bias.Backward));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1545 */   protected EventListenerList listenerList = new EventListenerList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1553 */   protected transient ChangeEvent changeEvent = null;
/*      */ 
/*      */   
/*      */   JTextComponent component;
/*      */ 
/*      */   
/* 1559 */   int updatePolicy = 0;
/*      */   boolean visible;
/*      */   boolean active;
/*      */   int dot;
/*      */   int mark;
/*      */   Object selectionTag;
/*      */   boolean selectionVisible;
/*      */   Timer flasher;
/*      */   Point magicCaretPosition;
/*      */   transient Position.Bias dotBias;
/*      */   transient Position.Bias markBias;
/*      */   boolean dotLTR;
/*      */   boolean markLTR;
/* 1572 */   transient Handler handler = new Handler();
/* 1573 */   private transient int[] flagXPoints = new int[3];
/* 1574 */   private transient int[] flagYPoints = new int[3];
/*      */   private transient NavigationFilter.FilterBypass filterBypass;
/* 1576 */   private static transient Action selectWord = null;
/* 1577 */   private static transient Action selectLine = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ownsSelection;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean forceCaretPositionChange;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean shouldHandleRelease;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1604 */   private transient MouseEvent selectedWordEvent = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1609 */   private int caretWidth = -1;
/* 1610 */   private float aspectRatio = -1.0F;
/*      */   
/*      */   class SafeScroller implements Runnable { Rectangle r;
/*      */     
/*      */     SafeScroller(Rectangle param1Rectangle) {
/* 1615 */       this.r = param1Rectangle;
/*      */     }
/*      */     
/*      */     public void run() {
/* 1619 */       if (DefaultCaret.this.component != null) {
/* 1620 */         DefaultCaret.this.component.scrollRectToVisible(this.r);
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
/*      */   class Handler
/*      */     implements PropertyChangeListener, DocumentListener, ActionListener, ClipboardOwner
/*      */   {
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1640 */       if (DefaultCaret.this.width == 0 || DefaultCaret.this.height == 0)
/*      */       {
/*      */         
/* 1643 */         if (DefaultCaret.this.component != null) {
/* 1644 */           TextUI textUI = DefaultCaret.this.component.getUI();
/*      */           try {
/* 1646 */             Rectangle rectangle = textUI.modelToView(DefaultCaret.this.component, DefaultCaret.this.dot, DefaultCaret.this.dotBias);
/*      */             
/* 1648 */             if (rectangle != null && rectangle.width != 0 && rectangle.height != 0) {
/* 1649 */               DefaultCaret.this.damage(rectangle);
/*      */             }
/* 1651 */           } catch (BadLocationException badLocationException) {}
/*      */         } 
/*      */       }
/*      */       
/* 1655 */       DefaultCaret.this.visible = !DefaultCaret.this.visible;
/* 1656 */       DefaultCaret.this.repaint();
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
/*      */     public void insertUpdate(DocumentEvent param1DocumentEvent) {
/* 1669 */       if (DefaultCaret.this.getUpdatePolicy() == 1 || (DefaultCaret.this
/* 1670 */         .getUpdatePolicy() == 0 && 
/* 1671 */         !SwingUtilities.isEventDispatchThread())) {
/*      */         
/* 1673 */         if ((param1DocumentEvent.getOffset() <= DefaultCaret.this.dot || param1DocumentEvent.getOffset() <= DefaultCaret.this.mark) && DefaultCaret.this.selectionTag != null) {
/*      */           
/*      */           try {
/* 1676 */             DefaultCaret.this.component.getHighlighter().changeHighlight(DefaultCaret.this.selectionTag, 
/* 1677 */                 Math.min(DefaultCaret.this.dot, DefaultCaret.this.mark), Math.max(DefaultCaret.this.dot, DefaultCaret.this.mark));
/* 1678 */           } catch (BadLocationException badLocationException) {
/* 1679 */             badLocationException.printStackTrace();
/*      */           } 
/*      */         }
/*      */         return;
/*      */       } 
/* 1684 */       int i = param1DocumentEvent.getOffset();
/* 1685 */       int j = param1DocumentEvent.getLength();
/* 1686 */       int k = DefaultCaret.this.dot;
/* 1687 */       short s = 0;
/*      */       
/* 1689 */       if (param1DocumentEvent instanceof AbstractDocument.UndoRedoDocumentEvent) {
/* 1690 */         DefaultCaret.this.setDot(i + j);
/*      */         return;
/*      */       } 
/* 1693 */       if (k >= i) {
/* 1694 */         k += j;
/* 1695 */         s = (short)(s | 0x1);
/*      */       } 
/* 1697 */       int m = DefaultCaret.this.mark;
/* 1698 */       if (m >= i) {
/* 1699 */         m += j;
/* 1700 */         s = (short)(s | 0x2);
/*      */       } 
/*      */       
/* 1703 */       if (s != 0) {
/* 1704 */         Position.Bias bias = DefaultCaret.this.dotBias;
/* 1705 */         if (DefaultCaret.this.dot == i) {
/* 1706 */           boolean bool; Document document = DefaultCaret.this.component.getDocument();
/*      */           
/*      */           try {
/* 1709 */             Segment segment = new Segment();
/* 1710 */             document.getText(k - 1, 1, segment);
/* 1711 */             bool = (segment.count > 0 && segment.array[segment.offset] == '\n') ? true : false;
/*      */           }
/* 1713 */           catch (BadLocationException badLocationException) {
/* 1714 */             bool = false;
/*      */           } 
/* 1716 */           if (bool) {
/* 1717 */             bias = Position.Bias.Forward;
/*      */           } else {
/* 1719 */             bias = Position.Bias.Backward;
/*      */           } 
/*      */         } 
/* 1722 */         if (m == k) {
/* 1723 */           DefaultCaret.this.setDot(k, bias);
/* 1724 */           DefaultCaret.this.ensureValidPosition();
/*      */         } else {
/*      */           
/* 1727 */           DefaultCaret.this.setDot(m, DefaultCaret.this.markBias);
/* 1728 */           if (DefaultCaret.this.getDot() == m)
/*      */           {
/*      */ 
/*      */             
/* 1732 */             DefaultCaret.this.moveDot(k, bias);
/*      */           }
/* 1734 */           DefaultCaret.this.ensureValidPosition();
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
/*      */     public void removeUpdate(DocumentEvent param1DocumentEvent) {
/* 1747 */       if (DefaultCaret.this.getUpdatePolicy() == 1 || (DefaultCaret.this
/* 1748 */         .getUpdatePolicy() == 0 && 
/* 1749 */         !SwingUtilities.isEventDispatchThread())) {
/*      */         
/* 1751 */         int n = DefaultCaret.this.component.getDocument().getLength();
/* 1752 */         DefaultCaret.this.dot = Math.min(DefaultCaret.this.dot, n);
/* 1753 */         DefaultCaret.this.mark = Math.min(DefaultCaret.this.mark, n);
/* 1754 */         if ((param1DocumentEvent.getOffset() < DefaultCaret.this.dot || param1DocumentEvent.getOffset() < DefaultCaret.this.mark) && DefaultCaret.this.selectionTag != null) {
/*      */           
/*      */           try {
/* 1757 */             DefaultCaret.this.component.getHighlighter().changeHighlight(DefaultCaret.this.selectionTag, 
/* 1758 */                 Math.min(DefaultCaret.this.dot, DefaultCaret.this.mark), Math.max(DefaultCaret.this.dot, DefaultCaret.this.mark));
/* 1759 */           } catch (BadLocationException badLocationException) {
/* 1760 */             badLocationException.printStackTrace();
/*      */           } 
/*      */         }
/*      */         return;
/*      */       } 
/* 1765 */       int i = param1DocumentEvent.getOffset();
/* 1766 */       int j = i + param1DocumentEvent.getLength();
/* 1767 */       int k = DefaultCaret.this.dot;
/* 1768 */       boolean bool1 = false;
/* 1769 */       int m = DefaultCaret.this.mark;
/* 1770 */       boolean bool2 = false;
/*      */       
/* 1772 */       if (param1DocumentEvent instanceof AbstractDocument.UndoRedoDocumentEvent) {
/* 1773 */         DefaultCaret.this.setDot(i);
/*      */         return;
/*      */       } 
/* 1776 */       if (k >= j) {
/* 1777 */         k -= j - i;
/* 1778 */         if (k == j) {
/* 1779 */           bool1 = true;
/*      */         }
/* 1781 */       } else if (k >= i) {
/* 1782 */         k = i;
/* 1783 */         bool1 = true;
/*      */       } 
/* 1785 */       if (m >= j) {
/* 1786 */         m -= j - i;
/* 1787 */         if (m == j) {
/* 1788 */           bool2 = true;
/*      */         }
/* 1790 */       } else if (m >= i) {
/* 1791 */         m = i;
/* 1792 */         bool2 = true;
/*      */       } 
/* 1794 */       if (m == k) {
/* 1795 */         DefaultCaret.this.forceCaretPositionChange = true;
/*      */         try {
/* 1797 */           DefaultCaret.this.setDot(k, DefaultCaret.this.guessBiasForOffset(k, DefaultCaret.this.dotBias, DefaultCaret.this.dotLTR));
/*      */         } finally {
/*      */           
/* 1800 */           DefaultCaret.this.forceCaretPositionChange = false;
/*      */         } 
/* 1802 */         DefaultCaret.this.ensureValidPosition();
/*      */       } else {
/* 1804 */         Position.Bias bias1 = DefaultCaret.this.dotBias;
/* 1805 */         Position.Bias bias2 = DefaultCaret.this.markBias;
/* 1806 */         if (bool1) {
/* 1807 */           bias1 = DefaultCaret.this.guessBiasForOffset(k, bias1, DefaultCaret.this.dotLTR);
/*      */         }
/* 1809 */         if (bool2) {
/* 1810 */           bias2 = DefaultCaret.this.guessBiasForOffset(DefaultCaret.this.mark, bias2, DefaultCaret.this.markLTR);
/*      */         }
/* 1812 */         DefaultCaret.this.setDot(m, bias2);
/* 1813 */         if (DefaultCaret.this.getDot() == m)
/*      */         {
/*      */           
/* 1816 */           DefaultCaret.this.moveDot(k, bias1);
/*      */         }
/* 1818 */         DefaultCaret.this.ensureValidPosition();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void changedUpdate(DocumentEvent param1DocumentEvent) {
/* 1829 */       if (DefaultCaret.this.getUpdatePolicy() == 1 || (DefaultCaret.this
/* 1830 */         .getUpdatePolicy() == 0 && 
/* 1831 */         !SwingUtilities.isEventDispatchThread())) {
/*      */         return;
/*      */       }
/* 1834 */       if (param1DocumentEvent instanceof AbstractDocument.UndoRedoDocumentEvent) {
/* 1835 */         DefaultCaret.this.setDot(param1DocumentEvent.getOffset() + param1DocumentEvent.getLength());
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1846 */       Object object1 = param1PropertyChangeEvent.getOldValue();
/* 1847 */       Object object2 = param1PropertyChangeEvent.getNewValue();
/* 1848 */       if (object1 instanceof Document || object2 instanceof Document) {
/* 1849 */         DefaultCaret.this.setDot(0);
/* 1850 */         if (object1 != null) {
/* 1851 */           ((Document)object1).removeDocumentListener(this);
/*      */         }
/* 1853 */         if (object2 != null) {
/* 1854 */           ((Document)object2).addDocumentListener(this);
/*      */         }
/* 1856 */       } else if ("enabled".equals(param1PropertyChangeEvent.getPropertyName())) {
/* 1857 */         Boolean bool = (Boolean)param1PropertyChangeEvent.getNewValue();
/* 1858 */         if (DefaultCaret.this.component.isFocusOwner()) {
/* 1859 */           if (bool == Boolean.TRUE) {
/* 1860 */             if (DefaultCaret.this.component.isEditable()) {
/* 1861 */               DefaultCaret.this.setVisible(true);
/*      */             }
/* 1863 */             DefaultCaret.this.setSelectionVisible(true);
/*      */           } else {
/* 1865 */             DefaultCaret.this.setVisible(false);
/* 1866 */             DefaultCaret.this.setSelectionVisible(false);
/*      */           } 
/*      */         }
/* 1869 */       } else if ("caretWidth".equals(param1PropertyChangeEvent.getPropertyName())) {
/* 1870 */         Integer integer = (Integer)param1PropertyChangeEvent.getNewValue();
/* 1871 */         if (integer != null) {
/* 1872 */           DefaultCaret.this.caretWidth = integer.intValue();
/*      */         } else {
/* 1874 */           DefaultCaret.this.caretWidth = -1;
/*      */         } 
/* 1876 */         DefaultCaret.this.repaint();
/* 1877 */       } else if ("caretAspectRatio".equals(param1PropertyChangeEvent.getPropertyName())) {
/* 1878 */         Number number = (Number)param1PropertyChangeEvent.getNewValue();
/* 1879 */         if (number != null) {
/* 1880 */           DefaultCaret.this.aspectRatio = number.floatValue();
/*      */         } else {
/* 1882 */           DefaultCaret.this.aspectRatio = -1.0F;
/*      */         } 
/* 1884 */         DefaultCaret.this.repaint();
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
/*      */     public void lostOwnership(Clipboard param1Clipboard, Transferable param1Transferable) {
/* 1897 */       if (DefaultCaret.this.ownsSelection) {
/* 1898 */         DefaultCaret.this.ownsSelection = false;
/* 1899 */         if (DefaultCaret.this.component != null && !DefaultCaret.this.component.hasFocus())
/* 1900 */           DefaultCaret.this.setSelectionVisible(false); 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class DefaultFilterBypass extends NavigationFilter.FilterBypass {
/*      */     private DefaultFilterBypass() {}
/*      */     
/*      */     public Caret getCaret() {
/* 1909 */       return DefaultCaret.this;
/*      */     }
/*      */     
/*      */     public void setDot(int param1Int, Position.Bias param1Bias) {
/* 1913 */       DefaultCaret.this.handleSetDot(param1Int, param1Bias);
/*      */     }
/*      */     
/*      */     public void moveDot(int param1Int, Position.Bias param1Bias) {
/* 1917 */       DefaultCaret.this.handleMoveDot(param1Int, param1Bias);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/DefaultCaret.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */