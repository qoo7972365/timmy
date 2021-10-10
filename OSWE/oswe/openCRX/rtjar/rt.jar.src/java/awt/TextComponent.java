/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.TextEvent;
/*      */ import java.awt.event.TextListener;
/*      */ import java.awt.im.InputMethodRequests;
/*      */ import java.awt.peer.TextComponentPeer;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.text.BreakIterator;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.swing.text.AttributeSet;
/*      */ import sun.awt.InputMethodSupport;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TextComponent
/*      */   extends Component
/*      */   implements Accessible
/*      */ {
/*      */   String text;
/*      */   boolean editable = true;
/*      */   int selectionStart;
/*      */   int selectionEnd;
/*      */   boolean backgroundSetByClientCode = false;
/*      */   protected transient TextListener textListener;
/*      */   private static final long serialVersionUID = -2214773872412987419L;
/*      */   private int textComponentSerializedDataVersion;
/*      */   private boolean checkForEnableIM;
/*      */   
/*      */   private void enableInputMethodsIfNecessary() {
/*  137 */     if (this.checkForEnableIM) {
/*  138 */       this.checkForEnableIM = false;
/*      */       try {
/*  140 */         Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  141 */         boolean bool = false;
/*  142 */         if (toolkit instanceof InputMethodSupport)
/*      */         {
/*  144 */           bool = ((InputMethodSupport)toolkit).enableInputMethodsForTextComponent();
/*      */         }
/*  146 */         enableInputMethods(bool);
/*  147 */       } catch (Exception exception) {}
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
/*      */   public void enableInputMethods(boolean paramBoolean) {
/*  166 */     this.checkForEnableIM = false;
/*  167 */     super.enableInputMethods(paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean areInputMethodsEnabled() {
/*  173 */     if (this.checkForEnableIM) {
/*  174 */       enableInputMethodsIfNecessary();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  179 */     return ((this.eventMask & 0x1000L) != 0L);
/*      */   }
/*      */   
/*      */   public InputMethodRequests getInputMethodRequests() {
/*  183 */     TextComponentPeer textComponentPeer = (TextComponentPeer)this.peer;
/*  184 */     if (textComponentPeer != null) return textComponentPeer.getInputMethodRequests(); 
/*  185 */     return null;
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
/*      */   public void addNotify() {
/*  198 */     super.addNotify();
/*  199 */     enableInputMethodsIfNecessary();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotify() {
/*  209 */     synchronized (getTreeLock()) {
/*  210 */       TextComponentPeer textComponentPeer = (TextComponentPeer)this.peer;
/*  211 */       if (textComponentPeer != null) {
/*  212 */         this.text = textComponentPeer.getText();
/*  213 */         this.selectionStart = textComponentPeer.getSelectionStart();
/*  214 */         this.selectionEnd = textComponentPeer.getSelectionEnd();
/*      */       } 
/*  216 */       super.removeNotify();
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
/*      */   public synchronized void setText(String paramString) {
/*  230 */     boolean bool = ((this.text == null || this.text.isEmpty()) && (paramString == null || paramString.isEmpty())) ? true : false;
/*  231 */     this.text = (paramString != null) ? paramString : "";
/*  232 */     TextComponentPeer textComponentPeer = (TextComponentPeer)this.peer;
/*      */ 
/*      */ 
/*      */     
/*  236 */     if (textComponentPeer != null && !bool) {
/*  237 */       textComponentPeer.setText(this.text);
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
/*      */   public synchronized String getText() {
/*  249 */     TextComponentPeer textComponentPeer = (TextComponentPeer)this.peer;
/*  250 */     if (textComponentPeer != null) {
/*  251 */       this.text = textComponentPeer.getText();
/*      */     }
/*  253 */     return this.text;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized String getSelectedText() {
/*  263 */     return getText().substring(getSelectionStart(), getSelectionEnd());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEditable() {
/*  274 */     return this.editable;
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
/*      */   public synchronized void setEditable(boolean paramBoolean) {
/*  294 */     if (this.editable == paramBoolean) {
/*      */       return;
/*      */     }
/*      */     
/*  298 */     this.editable = paramBoolean;
/*  299 */     TextComponentPeer textComponentPeer = (TextComponentPeer)this.peer;
/*  300 */     if (textComponentPeer != null) {
/*  301 */       textComponentPeer.setEditable(paramBoolean);
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
/*      */   public Color getBackground() {
/*  319 */     if (!this.editable && !this.backgroundSetByClientCode) {
/*  320 */       return SystemColor.control;
/*      */     }
/*      */     
/*  323 */     return super.getBackground();
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
/*      */   public void setBackground(Color paramColor) {
/*  336 */     this.backgroundSetByClientCode = true;
/*  337 */     super.setBackground(paramColor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int getSelectionStart() {
/*  348 */     TextComponentPeer textComponentPeer = (TextComponentPeer)this.peer;
/*  349 */     if (textComponentPeer != null) {
/*  350 */       this.selectionStart = textComponentPeer.getSelectionStart();
/*      */     }
/*  352 */     return this.selectionStart;
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
/*      */   public synchronized void setSelectionStart(int paramInt) {
/*  374 */     select(paramInt, getSelectionEnd());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int getSelectionEnd() {
/*  385 */     TextComponentPeer textComponentPeer = (TextComponentPeer)this.peer;
/*  386 */     if (textComponentPeer != null) {
/*  387 */       this.selectionEnd = textComponentPeer.getSelectionEnd();
/*      */     }
/*  389 */     return this.selectionEnd;
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
/*      */   public synchronized void setSelectionEnd(int paramInt) {
/*  410 */     select(getSelectionStart(), paramInt);
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
/*      */   public synchronized void select(int paramInt1, int paramInt2) {
/*  446 */     String str = getText();
/*  447 */     if (paramInt1 < 0) {
/*  448 */       paramInt1 = 0;
/*      */     }
/*  450 */     if (paramInt1 > str.length()) {
/*  451 */       paramInt1 = str.length();
/*      */     }
/*  453 */     if (paramInt2 > str.length()) {
/*  454 */       paramInt2 = str.length();
/*      */     }
/*  456 */     if (paramInt2 < paramInt1) {
/*  457 */       paramInt2 = paramInt1;
/*      */     }
/*      */     
/*  460 */     this.selectionStart = paramInt1;
/*  461 */     this.selectionEnd = paramInt2;
/*      */     
/*  463 */     TextComponentPeer textComponentPeer = (TextComponentPeer)this.peer;
/*  464 */     if (textComponentPeer != null) {
/*  465 */       textComponentPeer.select(paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void selectAll() {
/*  474 */     this.selectionStart = 0;
/*  475 */     this.selectionEnd = getText().length();
/*      */     
/*  477 */     TextComponentPeer textComponentPeer = (TextComponentPeer)this.peer;
/*  478 */     if (textComponentPeer != null) {
/*  479 */       textComponentPeer.select(this.selectionStart, this.selectionEnd);
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
/*      */   public synchronized void setCaretPosition(int paramInt) {
/*  500 */     if (paramInt < 0) {
/*  501 */       throw new IllegalArgumentException("position less than zero.");
/*      */     }
/*      */     
/*  504 */     int i = getText().length();
/*  505 */     if (paramInt > i) {
/*  506 */       paramInt = i;
/*      */     }
/*      */     
/*  509 */     TextComponentPeer textComponentPeer = (TextComponentPeer)this.peer;
/*  510 */     if (textComponentPeer != null) {
/*  511 */       textComponentPeer.setCaretPosition(paramInt);
/*      */     } else {
/*  513 */       select(paramInt, paramInt);
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
/*      */   public synchronized int getCaretPosition() {
/*  529 */     TextComponentPeer textComponentPeer = (TextComponentPeer)this.peer;
/*  530 */     int i = 0;
/*      */     
/*  532 */     if (textComponentPeer != null) {
/*  533 */       i = textComponentPeer.getCaretPosition();
/*      */     } else {
/*  535 */       i = this.selectionStart;
/*      */     } 
/*  537 */     int j = getText().length();
/*  538 */     if (i > j) {
/*  539 */       i = j;
/*      */     }
/*  541 */     return i;
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
/*      */   public synchronized void addTextListener(TextListener paramTextListener) {
/*  558 */     if (paramTextListener == null) {
/*      */       return;
/*      */     }
/*  561 */     this.textListener = AWTEventMulticaster.add(this.textListener, paramTextListener);
/*  562 */     this.newEventsOnly = true;
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
/*      */   public synchronized void removeTextListener(TextListener paramTextListener) {
/*  580 */     if (paramTextListener == null) {
/*      */       return;
/*      */     }
/*  583 */     this.textListener = AWTEventMulticaster.remove(this.textListener, paramTextListener);
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
/*      */   public synchronized TextListener[] getTextListeners() {
/*  600 */     return getListeners(TextListener.class);
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
/*      */   public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) {
/*  637 */     TextListener textListener = null;
/*  638 */     if (paramClass == TextListener.class) {
/*  639 */       textListener = this.textListener;
/*      */     } else {
/*  641 */       return super.getListeners(paramClass);
/*      */     } 
/*  643 */     return AWTEventMulticaster.getListeners(textListener, paramClass);
/*      */   }
/*      */ 
/*      */   
/*      */   boolean eventEnabled(AWTEvent paramAWTEvent) {
/*  648 */     if (paramAWTEvent.id == 900) {
/*  649 */       if ((this.eventMask & 0x400L) != 0L || this.textListener != null)
/*      */       {
/*  651 */         return true;
/*      */       }
/*  653 */       return false;
/*      */     } 
/*  655 */     return super.eventEnabled(paramAWTEvent);
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
/*      */   protected void processEvent(AWTEvent paramAWTEvent) {
/*  669 */     if (paramAWTEvent instanceof TextEvent) {
/*  670 */       processTextEvent((TextEvent)paramAWTEvent);
/*      */       return;
/*      */     } 
/*  673 */     super.processEvent(paramAWTEvent);
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
/*      */   protected void processTextEvent(TextEvent paramTextEvent) {
/*  696 */     TextListener textListener = this.textListener;
/*  697 */     if (textListener != null) {
/*  698 */       int i = paramTextEvent.getID();
/*  699 */       switch (i) {
/*      */         case 900:
/*  701 */           textListener.textValueChanged(paramTextEvent);
/*      */           break;
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
/*      */   protected String paramString() {
/*  718 */     String str = super.paramString() + ",text=" + getText();
/*  719 */     if (this.editable) {
/*  720 */       str = str + ",editable";
/*      */     }
/*  722 */     return str + ",selection=" + getSelectionStart() + "-" + getSelectionEnd();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canAccessClipboard() {
/*  729 */     SecurityManager securityManager = System.getSecurityManager();
/*  730 */     if (securityManager == null) return true; 
/*      */     try {
/*  732 */       securityManager.checkPermission(SecurityConstants.AWT.ACCESS_CLIPBOARD_PERMISSION);
/*  733 */       return true;
/*  734 */     } catch (SecurityException securityException) {
/*  735 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   TextComponent(String paramString) throws HeadlessException {
/*  746 */     this.textComponentSerializedDataVersion = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1193 */     this.checkForEnableIM = true;
/*      */     GraphicsEnvironment.checkHeadless();
/*      */     this.text = (paramString != null) ? paramString : "";
/*      */     setCursor(Cursor.getPredefinedCursor(2));
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*      */     TextComponentPeer textComponentPeer = (TextComponentPeer)this.peer;
/*      */     if (textComponentPeer != null) {
/*      */       this.text = textComponentPeer.getText();
/*      */       this.selectionStart = textComponentPeer.getSelectionStart();
/*      */       this.selectionEnd = textComponentPeer.getSelectionEnd();
/*      */     } 
/*      */     paramObjectOutputStream.defaultWriteObject();
/*      */     AWTEventMulticaster.save(paramObjectOutputStream, "textL", this.textListener);
/*      */     paramObjectOutputStream.writeObject(null);
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException, HeadlessException {
/*      */     GraphicsEnvironment.checkHeadless();
/*      */     paramObjectInputStream.defaultReadObject();
/*      */     this.text = (this.text != null) ? this.text : "";
/*      */     select(this.selectionStart, this.selectionEnd);
/*      */     Object object;
/*      */     while (null != (object = paramObjectInputStream.readObject())) {
/*      */       String str = ((String)object).intern();
/*      */       if ("textL" == str) {
/*      */         addTextListener((TextListener)paramObjectInputStream.readObject());
/*      */         continue;
/*      */       } 
/*      */       paramObjectInputStream.readObject();
/*      */     } 
/*      */     enableInputMethodsIfNecessary();
/*      */   }
/*      */   
/*      */   public AccessibleContext getAccessibleContext() {
/*      */     if (this.accessibleContext == null)
/*      */       this.accessibleContext = new AccessibleAWTTextComponent(); 
/*      */     return this.accessibleContext;
/*      */   }
/*      */   
/*      */   protected class AccessibleAWTTextComponent extends Component.AccessibleAWTComponent implements AccessibleText, TextListener {
/*      */     private static final long serialVersionUID = 3631432373506317811L;
/*      */     private static final boolean NEXT = true;
/*      */     private static final boolean PREVIOUS = false;
/*      */     
/*      */     public AccessibleAWTTextComponent() {
/*      */       TextComponent.this.addTextListener(this);
/*      */     }
/*      */     
/*      */     public void textValueChanged(TextEvent param1TextEvent) {
/*      */       Integer integer = Integer.valueOf(TextComponent.this.getCaretPosition());
/*      */       firePropertyChange("AccessibleText", null, integer);
/*      */     }
/*      */     
/*      */     public AccessibleStateSet getAccessibleStateSet() {
/*      */       AccessibleStateSet accessibleStateSet = super.getAccessibleStateSet();
/*      */       if (TextComponent.this.isEditable())
/*      */         accessibleStateSet.add(AccessibleState.EDITABLE); 
/*      */       return accessibleStateSet;
/*      */     }
/*      */     
/*      */     public AccessibleRole getAccessibleRole() {
/*      */       return AccessibleRole.TEXT;
/*      */     }
/*      */     
/*      */     public AccessibleText getAccessibleText() {
/*      */       return this;
/*      */     }
/*      */     
/*      */     public int getIndexAtPoint(Point param1Point) {
/*      */       return -1;
/*      */     }
/*      */     
/*      */     public Rectangle getCharacterBounds(int param1Int) {
/*      */       return null;
/*      */     }
/*      */     
/*      */     public int getCharCount() {
/*      */       return TextComponent.this.getText().length();
/*      */     }
/*      */     
/*      */     public int getCaretPosition() {
/*      */       return TextComponent.this.getCaretPosition();
/*      */     }
/*      */     
/*      */     public AttributeSet getCharacterAttribute(int param1Int) {
/*      */       return null;
/*      */     }
/*      */     
/*      */     public int getSelectionStart() {
/*      */       return TextComponent.this.getSelectionStart();
/*      */     }
/*      */     
/*      */     public int getSelectionEnd() {
/*      */       return TextComponent.this.getSelectionEnd();
/*      */     }
/*      */     
/*      */     public String getSelectedText() {
/*      */       String str = TextComponent.this.getSelectedText();
/*      */       if (str == null || str.equals(""))
/*      */         return null; 
/*      */       return str;
/*      */     }
/*      */     
/*      */     public String getAtIndex(int param1Int1, int param1Int2) {
/*      */       String str;
/*      */       BreakIterator breakIterator;
/*      */       int i;
/*      */       if (param1Int2 < 0 || param1Int2 >= TextComponent.this.getText().length())
/*      */         return null; 
/*      */       switch (param1Int1) {
/*      */         case 1:
/*      */           return TextComponent.this.getText().substring(param1Int2, param1Int2 + 1);
/*      */         case 2:
/*      */           str = TextComponent.this.getText();
/*      */           breakIterator = BreakIterator.getWordInstance();
/*      */           breakIterator.setText(str);
/*      */           i = breakIterator.following(param1Int2);
/*      */           return str.substring(breakIterator.previous(), i);
/*      */         case 3:
/*      */           str = TextComponent.this.getText();
/*      */           breakIterator = BreakIterator.getSentenceInstance();
/*      */           breakIterator.setText(str);
/*      */           i = breakIterator.following(param1Int2);
/*      */           return str.substring(breakIterator.previous(), i);
/*      */       } 
/*      */       return null;
/*      */     }
/*      */     
/*      */     private int findWordLimit(int param1Int, BreakIterator param1BreakIterator, boolean param1Boolean, String param1String) {
/*      */       int i = (param1Boolean == true) ? param1BreakIterator.following(param1Int) : param1BreakIterator.preceding(param1Int);
/*      */       int j = (param1Boolean == true) ? param1BreakIterator.next() : param1BreakIterator.previous();
/*      */       while (j != -1) {
/*      */         for (int k = Math.min(i, j); k < Math.max(i, j); k++) {
/*      */           if (Character.isLetter(param1String.charAt(k)))
/*      */             return i; 
/*      */         } 
/*      */         i = j;
/*      */         j = (param1Boolean == true) ? param1BreakIterator.next() : param1BreakIterator.previous();
/*      */       } 
/*      */       return -1;
/*      */     }
/*      */     
/*      */     public String getAfterIndex(int param1Int1, int param1Int2) {
/*      */       String str;
/*      */       BreakIterator breakIterator;
/*      */       int i;
/*      */       int j;
/*      */       if (param1Int2 < 0 || param1Int2 >= TextComponent.this.getText().length())
/*      */         return null; 
/*      */       switch (param1Int1) {
/*      */         case 1:
/*      */           if (param1Int2 + 1 >= TextComponent.this.getText().length())
/*      */             return null; 
/*      */           return TextComponent.this.getText().substring(param1Int2 + 1, param1Int2 + 2);
/*      */         case 2:
/*      */           str = TextComponent.this.getText();
/*      */           breakIterator = BreakIterator.getWordInstance();
/*      */           breakIterator.setText(str);
/*      */           i = findWordLimit(param1Int2, breakIterator, true, str);
/*      */           if (i == -1 || i >= str.length())
/*      */             return null; 
/*      */           j = breakIterator.following(i);
/*      */           if (j == -1 || j >= str.length())
/*      */             return null; 
/*      */           return str.substring(i, j);
/*      */         case 3:
/*      */           str = TextComponent.this.getText();
/*      */           breakIterator = BreakIterator.getSentenceInstance();
/*      */           breakIterator.setText(str);
/*      */           i = breakIterator.following(param1Int2);
/*      */           if (i == -1 || i >= str.length())
/*      */             return null; 
/*      */           j = breakIterator.following(i);
/*      */           if (j == -1 || j >= str.length())
/*      */             return null; 
/*      */           return str.substring(i, j);
/*      */       } 
/*      */       return null;
/*      */     }
/*      */     
/*      */     public String getBeforeIndex(int param1Int1, int param1Int2) {
/*      */       String str;
/*      */       BreakIterator breakIterator;
/*      */       int i;
/*      */       int j;
/*      */       if (param1Int2 < 0 || param1Int2 > TextComponent.this.getText().length() - 1)
/*      */         return null; 
/*      */       switch (param1Int1) {
/*      */         case 1:
/*      */           if (param1Int2 == 0)
/*      */             return null; 
/*      */           return TextComponent.this.getText().substring(param1Int2 - 1, param1Int2);
/*      */         case 2:
/*      */           str = TextComponent.this.getText();
/*      */           breakIterator = BreakIterator.getWordInstance();
/*      */           breakIterator.setText(str);
/*      */           i = findWordLimit(param1Int2, breakIterator, false, str);
/*      */           if (i == -1)
/*      */             return null; 
/*      */           j = breakIterator.preceding(i);
/*      */           if (j == -1)
/*      */             return null; 
/*      */           return str.substring(j, i);
/*      */         case 3:
/*      */           str = TextComponent.this.getText();
/*      */           breakIterator = BreakIterator.getSentenceInstance();
/*      */           breakIterator.setText(str);
/*      */           i = breakIterator.following(param1Int2);
/*      */           i = breakIterator.previous();
/*      */           j = breakIterator.previous();
/*      */           if (j == -1)
/*      */             return null; 
/*      */           return str.substring(j, i);
/*      */       } 
/*      */       return null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/TextComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */