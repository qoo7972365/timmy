/*      */ package sun.awt.im;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.AWTKeyStroke;
/*      */ import java.awt.Component;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.ComponentListener;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.event.WindowListener;
/*      */ import java.awt.im.InputContext;
/*      */ import java.awt.im.InputMethodRequests;
/*      */ import java.awt.im.spi.InputMethod;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.prefs.BackingStoreException;
/*      */ import java.util.prefs.Preferences;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class InputContext
/*      */   extends InputContext
/*      */   implements ComponentListener, WindowListener
/*      */ {
/*   70 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.im.InputContext");
/*      */ 
/*      */   
/*      */   private InputMethodLocator inputMethodLocator;
/*      */ 
/*      */   
/*      */   private InputMethod inputMethod;
/*      */ 
/*      */   
/*      */   private boolean inputMethodCreationFailed;
/*      */ 
/*      */   
/*      */   private HashMap<InputMethodLocator, InputMethod> usedInputMethods;
/*      */   
/*      */   private Component currentClientComponent;
/*      */   
/*      */   private Component awtFocussedComponent;
/*      */   
/*      */   private boolean isInputMethodActive;
/*      */   
/*   90 */   private Character.Subset[] characterSubsets = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean compositionAreaHidden = false;
/*      */ 
/*      */   
/*      */   private static InputContext inputMethodWindowContext;
/*      */ 
/*      */   
/*  100 */   private static InputMethod previousInputMethod = null;
/*      */ 
/*      */   
/*      */   private boolean clientWindowNotificationEnabled = false;
/*      */   
/*      */   private Window clientWindowListened;
/*      */   
/*  107 */   private Rectangle clientWindowLocation = null;
/*      */   
/*      */   private HashMap<InputMethod, Boolean> perInputMethodState;
/*      */   
/*      */   private static AWTKeyStroke inputMethodSelectionKey;
/*      */   
/*      */   private static boolean inputMethodSelectionKeyInitialized = false;
/*      */   
/*      */   private static final String inputMethodSelectionKeyPath = "/java/awt/im/selectionKey";
/*      */   
/*      */   private static final String inputMethodSelectionKeyCodeName = "keyCode";
/*      */   
/*      */   private static final String inputMethodSelectionKeyModifiersName = "modifiers";
/*      */   
/*      */   protected InputContext() {
/*  122 */     InputMethodManager inputMethodManager = InputMethodManager.getInstance();
/*  123 */     synchronized (InputContext.class) {
/*  124 */       if (!inputMethodSelectionKeyInitialized) {
/*  125 */         inputMethodSelectionKeyInitialized = true;
/*  126 */         if (inputMethodManager.hasMultipleInputMethods()) {
/*  127 */           initializeInputMethodSelectionKey();
/*      */         }
/*      */       } 
/*      */     } 
/*  131 */     selectInputMethod(inputMethodManager.getDefaultKeyboardLocale());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean selectInputMethod(Locale paramLocale) {
/*  139 */     if (paramLocale == null) {
/*  140 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*      */     
/*  144 */     if (this.inputMethod != null) {
/*  145 */       if (this.inputMethod.setLocale(paramLocale)) {
/*  146 */         return true;
/*      */       }
/*  148 */     } else if (this.inputMethodLocator != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  153 */       if (this.inputMethodLocator.isLocaleAvailable(paramLocale)) {
/*  154 */         this.inputMethodLocator = this.inputMethodLocator.deriveLocator(paramLocale);
/*  155 */         return true;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  160 */     InputMethodLocator inputMethodLocator = InputMethodManager.getInstance().findInputMethod(paramLocale);
/*  161 */     if (inputMethodLocator != null) {
/*  162 */       changeInputMethod(inputMethodLocator);
/*  163 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  168 */     if (this.inputMethod == null && this.inputMethodLocator != null) {
/*  169 */       this.inputMethod = getInputMethod();
/*  170 */       if (this.inputMethod != null) {
/*  171 */         return this.inputMethod.setLocale(paramLocale);
/*      */       }
/*      */     } 
/*  174 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  181 */     if (this.inputMethod != null)
/*  182 */       return this.inputMethod.getLocale(); 
/*  183 */     if (this.inputMethodLocator != null) {
/*  184 */       return this.inputMethodLocator.getLocale();
/*      */     }
/*  186 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterSubsets(Character.Subset[] paramArrayOfSubset) {
/*  194 */     if (paramArrayOfSubset == null) {
/*  195 */       this.characterSubsets = null;
/*      */     } else {
/*  197 */       this.characterSubsets = new Character.Subset[paramArrayOfSubset.length];
/*  198 */       System.arraycopy(paramArrayOfSubset, 0, this.characterSubsets, 0, this.characterSubsets.length);
/*      */     } 
/*      */     
/*  201 */     if (this.inputMethod != null) {
/*  202 */       this.inputMethod.setCharacterSubsets(paramArrayOfSubset);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void reconvert() {
/*  212 */     InputMethod inputMethod = getInputMethod();
/*  213 */     if (inputMethod == null) {
/*  214 */       throw new UnsupportedOperationException();
/*      */     }
/*  216 */     inputMethod.reconvert();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispatchEvent(AWTEvent paramAWTEvent) {
/*  225 */     if (paramAWTEvent instanceof java.awt.event.InputMethodEvent) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  231 */     if (paramAWTEvent instanceof FocusEvent) {
/*  232 */       Component component = ((FocusEvent)paramAWTEvent).getOppositeComponent();
/*  233 */       if (component != null && 
/*  234 */         getComponentWindow(component) instanceof InputMethodWindow && component
/*  235 */         .getInputContext() == this) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/*  240 */     InputMethod inputMethod = getInputMethod();
/*  241 */     int i = paramAWTEvent.getID();
/*      */     
/*  243 */     switch (i) {
/*      */       case 1004:
/*  245 */         focusGained((Component)paramAWTEvent.getSource());
/*      */         return;
/*      */       
/*      */       case 1005:
/*  249 */         focusLost((Component)paramAWTEvent.getSource(), ((FocusEvent)paramAWTEvent).isTemporary());
/*      */         return;
/*      */       
/*      */       case 401:
/*  253 */         if (checkInputMethodSelectionKey((KeyEvent)paramAWTEvent)) {
/*      */           
/*  255 */           InputMethodManager.getInstance().notifyChangeRequestByHotKey((Component)paramAWTEvent.getSource());
/*      */           return;
/*      */         } 
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  262 */     if (inputMethod != null && paramAWTEvent instanceof java.awt.event.InputEvent) {
/*  263 */       inputMethod.dispatchEvent(paramAWTEvent);
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
/*      */   private void focusGained(Component paramComponent) {
/*  299 */     synchronized (paramComponent.getTreeLock()) {
/*  300 */       synchronized (this) {
/*  301 */         if (!"sun.awt.im.CompositionArea".equals(paramComponent.getClass().getName()))
/*      */         {
/*  303 */           if (!(getComponentWindow(paramComponent) instanceof InputMethodWindow)) {
/*      */ 
/*      */             
/*  306 */             if (!paramComponent.isDisplayable()) {
/*      */               return;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  316 */             if (this.inputMethod != null && 
/*  317 */               this.currentClientComponent != null && this.currentClientComponent != paramComponent) {
/*  318 */               if (!this.isInputMethodActive) {
/*  319 */                 activateInputMethod(false);
/*      */               }
/*  321 */               endComposition();
/*  322 */               deactivateInputMethod(false);
/*      */             } 
/*      */ 
/*      */             
/*  326 */             this.currentClientComponent = paramComponent;
/*      */           } 
/*      */         }
/*  329 */         this.awtFocussedComponent = paramComponent;
/*  330 */         if (this.inputMethod instanceof InputMethodAdapter) {
/*  331 */           ((InputMethodAdapter)this.inputMethod).setAWTFocussedComponent(paramComponent);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  337 */         if (!this.isInputMethodActive) {
/*  338 */           activateInputMethod(true);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  344 */         InputMethodContext inputMethodContext = (InputMethodContext)this;
/*  345 */         if (!inputMethodContext.isCompositionAreaVisible()) {
/*  346 */           InputMethodRequests inputMethodRequests = paramComponent.getInputMethodRequests();
/*  347 */           if (inputMethodRequests != null && inputMethodContext.useBelowTheSpotInput()) {
/*  348 */             inputMethodContext.setCompositionAreaUndecorated(true);
/*      */           } else {
/*  350 */             inputMethodContext.setCompositionAreaUndecorated(false);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  355 */         if (this.compositionAreaHidden == true) {
/*  356 */           ((InputMethodContext)this).setCompositionAreaVisible(true);
/*  357 */           this.compositionAreaHidden = false;
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
/*      */   private void activateInputMethod(boolean paramBoolean) {
/*  373 */     if (inputMethodWindowContext != null && inputMethodWindowContext != this && inputMethodWindowContext.inputMethodLocator != null && 
/*      */       
/*  375 */       !inputMethodWindowContext.inputMethodLocator.sameInputMethod(this.inputMethodLocator) && inputMethodWindowContext.inputMethod != null)
/*      */     {
/*  377 */       inputMethodWindowContext.inputMethod.hideWindows();
/*      */     }
/*  379 */     inputMethodWindowContext = this;
/*      */     
/*  381 */     if (this.inputMethod != null) {
/*  382 */       if (previousInputMethod != this.inputMethod && previousInputMethod instanceof InputMethodAdapter)
/*      */       {
/*      */ 
/*      */         
/*  386 */         ((InputMethodAdapter)previousInputMethod).stopListening();
/*      */       }
/*  388 */       previousInputMethod = null;
/*      */       
/*  390 */       if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  391 */         log.fine("Current client component " + this.currentClientComponent);
/*      */       }
/*  393 */       if (this.inputMethod instanceof InputMethodAdapter) {
/*  394 */         ((InputMethodAdapter)this.inputMethod).setClientComponent(this.currentClientComponent);
/*      */       }
/*  396 */       this.inputMethod.activate();
/*  397 */       this.isInputMethodActive = true;
/*      */       
/*  399 */       if (this.perInputMethodState != null) {
/*  400 */         Boolean bool = this.perInputMethodState.remove(this.inputMethod);
/*  401 */         if (bool != null) {
/*  402 */           this.clientWindowNotificationEnabled = bool.booleanValue();
/*      */         }
/*      */       } 
/*  405 */       if (this.clientWindowNotificationEnabled) {
/*  406 */         if (!addedClientWindowListeners()) {
/*  407 */           addClientWindowListeners();
/*      */         }
/*  409 */         synchronized (this) {
/*  410 */           if (this.clientWindowListened != null) {
/*  411 */             notifyClientWindowChange(this.clientWindowListened);
/*      */           }
/*      */         }
/*      */       
/*  415 */       } else if (addedClientWindowListeners()) {
/*  416 */         removeClientWindowListeners();
/*      */       } 
/*      */     } 
/*      */     
/*  420 */     InputMethodManager.getInstance().setInputContext(this);
/*      */     
/*  422 */     ((InputMethodContext)this).grabCompositionArea(paramBoolean);
/*      */   }
/*      */   
/*      */   static Window getComponentWindow(Component paramComponent) {
/*      */     while (true) {
/*  427 */       if (paramComponent == null)
/*  428 */         return null; 
/*  429 */       if (paramComponent instanceof Window) {
/*  430 */         return (Window)paramComponent;
/*      */       }
/*  432 */       paramComponent = paramComponent.getParent();
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
/*      */   private void focusLost(Component paramComponent, boolean paramBoolean) {
/*  452 */     synchronized (paramComponent.getTreeLock()) {
/*  453 */       synchronized (this) {
/*      */ 
/*      */ 
/*      */         
/*  457 */         if (this.isInputMethodActive) {
/*  458 */           deactivateInputMethod(paramBoolean);
/*      */         }
/*      */         
/*  461 */         this.awtFocussedComponent = null;
/*  462 */         if (this.inputMethod instanceof InputMethodAdapter) {
/*  463 */           ((InputMethodAdapter)this.inputMethod).setAWTFocussedComponent(null);
/*      */         }
/*      */ 
/*      */         
/*  467 */         InputMethodContext inputMethodContext = (InputMethodContext)this;
/*  468 */         if (inputMethodContext.isCompositionAreaVisible()) {
/*  469 */           inputMethodContext.setCompositionAreaVisible(false);
/*  470 */           this.compositionAreaHidden = true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkInputMethodSelectionKey(KeyEvent paramKeyEvent) {
/*  480 */     if (inputMethodSelectionKey != null) {
/*  481 */       AWTKeyStroke aWTKeyStroke = AWTKeyStroke.getAWTKeyStrokeForEvent(paramKeyEvent);
/*  482 */       return inputMethodSelectionKey.equals(aWTKeyStroke);
/*      */     } 
/*  484 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void deactivateInputMethod(boolean paramBoolean) {
/*  489 */     InputMethodManager.getInstance().setInputContext(null);
/*  490 */     if (this.inputMethod != null) {
/*  491 */       this.isInputMethodActive = false;
/*  492 */       this.inputMethod.deactivate(paramBoolean);
/*  493 */       previousInputMethod = this.inputMethod;
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
/*      */   synchronized void changeInputMethod(InputMethodLocator paramInputMethodLocator) {
/*  511 */     if (this.inputMethodLocator == null) {
/*  512 */       this.inputMethodLocator = paramInputMethodLocator;
/*  513 */       this.inputMethodCreationFailed = false;
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  519 */     if (this.inputMethodLocator.sameInputMethod(paramInputMethodLocator)) {
/*  520 */       Locale locale1 = paramInputMethodLocator.getLocale();
/*  521 */       if (locale1 != null && this.inputMethodLocator.getLocale() != locale1) {
/*  522 */         if (this.inputMethod != null) {
/*  523 */           this.inputMethod.setLocale(locale1);
/*      */         }
/*  525 */         this.inputMethodLocator = paramInputMethodLocator;
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  531 */     Locale locale = this.inputMethodLocator.getLocale();
/*  532 */     boolean bool1 = this.isInputMethodActive;
/*  533 */     boolean bool = false;
/*  534 */     boolean bool2 = false;
/*  535 */     if (this.inputMethod != null) {
/*      */       try {
/*  537 */         bool2 = this.inputMethod.isCompositionEnabled();
/*  538 */         bool = true;
/*  539 */       } catch (UnsupportedOperationException unsupportedOperationException) {}
/*      */       
/*  541 */       if (this.currentClientComponent != null) {
/*  542 */         if (!this.isInputMethodActive) {
/*  543 */           activateInputMethod(false);
/*      */         }
/*  545 */         endComposition();
/*  546 */         deactivateInputMethod(false);
/*  547 */         if (this.inputMethod instanceof InputMethodAdapter) {
/*  548 */           ((InputMethodAdapter)this.inputMethod).setClientComponent(null);
/*      */         }
/*      */       } 
/*  551 */       locale = this.inputMethod.getLocale();
/*      */ 
/*      */       
/*  554 */       if (this.usedInputMethods == null) {
/*  555 */         this.usedInputMethods = new HashMap<>(5);
/*      */       }
/*  557 */       if (this.perInputMethodState == null) {
/*  558 */         this.perInputMethodState = new HashMap<>(5);
/*      */       }
/*  560 */       this.usedInputMethods.put(this.inputMethodLocator.deriveLocator(null), this.inputMethod);
/*  561 */       this.perInputMethodState.put(this.inputMethod, 
/*  562 */           Boolean.valueOf(this.clientWindowNotificationEnabled));
/*  563 */       enableClientWindowNotification(this.inputMethod, false);
/*  564 */       if (this == inputMethodWindowContext) {
/*  565 */         this.inputMethod.hideWindows();
/*  566 */         inputMethodWindowContext = null;
/*      */       } 
/*  568 */       this.inputMethodLocator = null;
/*  569 */       this.inputMethod = null;
/*  570 */       this.inputMethodCreationFailed = false;
/*      */     } 
/*      */ 
/*      */     
/*  574 */     if (paramInputMethodLocator.getLocale() == null && locale != null && paramInputMethodLocator
/*  575 */       .isLocaleAvailable(locale)) {
/*  576 */       paramInputMethodLocator = paramInputMethodLocator.deriveLocator(locale);
/*      */     }
/*  578 */     this.inputMethodLocator = paramInputMethodLocator;
/*  579 */     this.inputMethodCreationFailed = false;
/*      */ 
/*      */     
/*  582 */     if (bool1) {
/*  583 */       this.inputMethod = getInputMethodInstance();
/*  584 */       if (this.inputMethod instanceof InputMethodAdapter) {
/*  585 */         ((InputMethodAdapter)this.inputMethod).setAWTFocussedComponent(this.awtFocussedComponent);
/*      */       }
/*  587 */       activateInputMethod(true);
/*      */     } 
/*      */ 
/*      */     
/*  591 */     if (bool) {
/*  592 */       this.inputMethod = getInputMethod();
/*  593 */       if (this.inputMethod != null) {
/*      */         try {
/*  595 */           this.inputMethod.setCompositionEnabled(bool2);
/*  596 */         } catch (UnsupportedOperationException unsupportedOperationException) {}
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Component getClientComponent() {
/*  605 */     return this.currentClientComponent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void removeNotify(Component paramComponent) {
/*  613 */     if (paramComponent == null) {
/*  614 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  617 */     if (this.inputMethod == null) {
/*  618 */       if (paramComponent == this.currentClientComponent) {
/*  619 */         this.currentClientComponent = null;
/*      */       }
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  626 */     if (paramComponent == this.awtFocussedComponent) {
/*  627 */       focusLost(paramComponent, false);
/*      */     }
/*      */     
/*  630 */     if (paramComponent == this.currentClientComponent) {
/*  631 */       if (this.isInputMethodActive)
/*      */       {
/*  633 */         deactivateInputMethod(false);
/*      */       }
/*  635 */       this.inputMethod.removeNotify();
/*  636 */       if (this.clientWindowNotificationEnabled && addedClientWindowListeners()) {
/*  637 */         removeClientWindowListeners();
/*      */       }
/*  639 */       this.currentClientComponent = null;
/*  640 */       if (this.inputMethod instanceof InputMethodAdapter) {
/*  641 */         ((InputMethodAdapter)this.inputMethod).setClientComponent(null);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  648 */       if (EventQueue.isDispatchThread()) {
/*  649 */         ((InputMethodContext)this).releaseCompositionArea();
/*      */       } else {
/*  651 */         EventQueue.invokeLater(new Runnable() {
/*      */               public void run() {
/*  653 */                 ((InputMethodContext)InputContext.this).releaseCompositionArea();
/*      */               }
/*      */             });
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void dispose() {
/*  665 */     if (this.currentClientComponent != null) {
/*  666 */       throw new IllegalStateException("Can't dispose InputContext while it's active");
/*      */     }
/*  668 */     if (this.inputMethod != null) {
/*  669 */       if (this == inputMethodWindowContext) {
/*  670 */         this.inputMethod.hideWindows();
/*  671 */         inputMethodWindowContext = null;
/*      */       } 
/*  673 */       if (this.inputMethod == previousInputMethod) {
/*  674 */         previousInputMethod = null;
/*      */       }
/*  676 */       if (this.clientWindowNotificationEnabled) {
/*  677 */         if (addedClientWindowListeners()) {
/*  678 */           removeClientWindowListeners();
/*      */         }
/*  680 */         this.clientWindowNotificationEnabled = false;
/*      */       } 
/*  682 */       this.inputMethod.dispose();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  687 */       if (this.clientWindowNotificationEnabled) {
/*  688 */         enableClientWindowNotification(this.inputMethod, false);
/*      */       }
/*      */       
/*  691 */       this.inputMethod = null;
/*      */     } 
/*  693 */     this.inputMethodLocator = null;
/*  694 */     if (this.usedInputMethods != null && !this.usedInputMethods.isEmpty()) {
/*  695 */       Iterator<InputMethod> iterator = this.usedInputMethods.values().iterator();
/*  696 */       this.usedInputMethods = null;
/*  697 */       while (iterator.hasNext()) {
/*  698 */         ((InputMethod)iterator.next()).dispose();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  703 */     this.clientWindowNotificationEnabled = false;
/*  704 */     this.clientWindowListened = null;
/*  705 */     this.perInputMethodState = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Object getInputMethodControlObject() {
/*  712 */     InputMethod inputMethod = getInputMethod();
/*      */     
/*  714 */     if (inputMethod != null) {
/*  715 */       return inputMethod.getControlObject();
/*      */     }
/*  717 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCompositionEnabled(boolean paramBoolean) {
/*  726 */     InputMethod inputMethod = getInputMethod();
/*      */     
/*  728 */     if (inputMethod == null) {
/*  729 */       throw new UnsupportedOperationException();
/*      */     }
/*  731 */     inputMethod.setCompositionEnabled(paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCompositionEnabled() {
/*  739 */     InputMethod inputMethod = getInputMethod();
/*      */     
/*  741 */     if (inputMethod == null) {
/*  742 */       throw new UnsupportedOperationException();
/*      */     }
/*  744 */     return inputMethod.isCompositionEnabled();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getInputMethodInfo() {
/*  752 */     InputMethod inputMethod = getInputMethod();
/*      */     
/*  754 */     if (inputMethod == null) {
/*  755 */       throw new UnsupportedOperationException("Null input method");
/*      */     }
/*      */     
/*  758 */     String str = null;
/*  759 */     if (inputMethod instanceof InputMethodAdapter)
/*      */     {
/*      */       
/*  762 */       str = ((InputMethodAdapter)inputMethod).getNativeInputMethodInfo();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  767 */     if (str == null && this.inputMethodLocator != null)
/*      */     {
/*  769 */       str = this.inputMethodLocator.getDescriptor().getInputMethodDisplayName(getLocale(), 
/*  770 */           SunToolkit.getStartupLocale());
/*      */     }
/*      */     
/*  773 */     if (str != null && !str.equals("")) {
/*  774 */       return str;
/*      */     }
/*      */ 
/*      */     
/*  778 */     return inputMethod.toString() + "-" + inputMethod.getLocale().toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void disableNativeIM() {
/*  789 */     InputMethod inputMethod = getInputMethod();
/*  790 */     if (inputMethod != null && inputMethod instanceof InputMethodAdapter) {
/*  791 */       ((InputMethodAdapter)inputMethod).stopListening();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private synchronized InputMethod getInputMethod() {
/*  797 */     if (this.inputMethod != null) {
/*  798 */       return this.inputMethod;
/*      */     }
/*      */     
/*  801 */     if (this.inputMethodCreationFailed) {
/*  802 */       return null;
/*      */     }
/*      */     
/*  805 */     this.inputMethod = getInputMethodInstance();
/*  806 */     return this.inputMethod;
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
/*      */   private InputMethod getInputMethodInstance() {
/*  825 */     InputMethodLocator inputMethodLocator = this.inputMethodLocator;
/*  826 */     if (inputMethodLocator == null) {
/*  827 */       this.inputMethodCreationFailed = true;
/*  828 */       return null;
/*      */     } 
/*      */     
/*  831 */     Locale locale = inputMethodLocator.getLocale();
/*  832 */     InputMethod inputMethod = null;
/*      */ 
/*      */     
/*  835 */     if (this.usedInputMethods != null) {
/*  836 */       inputMethod = this.usedInputMethods.remove(inputMethodLocator.deriveLocator(null));
/*  837 */       if (inputMethod != null) {
/*  838 */         if (locale != null) {
/*  839 */           inputMethod.setLocale(locale);
/*      */         }
/*  841 */         inputMethod.setCharacterSubsets(this.characterSubsets);
/*  842 */         Boolean bool = this.perInputMethodState.remove(inputMethod);
/*  843 */         if (bool != null) {
/*  844 */           enableClientWindowNotification(inputMethod, bool.booleanValue());
/*      */         }
/*  846 */         ((InputMethodContext)this).setInputMethodSupportsBelowTheSpot((!(inputMethod instanceof InputMethodAdapter) || ((InputMethodAdapter)inputMethod)
/*      */             
/*  848 */             .supportsBelowTheSpot()));
/*  849 */         return inputMethod;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  855 */       inputMethod = inputMethodLocator.getDescriptor().createInputMethod();
/*      */       
/*  857 */       if (locale != null) {
/*  858 */         inputMethod.setLocale(locale);
/*      */       }
/*  860 */       inputMethod.setInputMethodContext((InputMethodContext)this);
/*  861 */       inputMethod.setCharacterSubsets(this.characterSubsets);
/*      */     }
/*  863 */     catch (Exception exception) {
/*  864 */       logCreationFailed(exception);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  869 */       this.inputMethodCreationFailed = true;
/*      */ 
/*      */ 
/*      */       
/*  873 */       if (inputMethod != null) {
/*  874 */         inputMethod = null;
/*      */       }
/*  876 */     } catch (LinkageError linkageError) {
/*  877 */       logCreationFailed(linkageError);
/*      */ 
/*      */       
/*  880 */       this.inputMethodCreationFailed = true;
/*      */     } 
/*  882 */     ((InputMethodContext)this).setInputMethodSupportsBelowTheSpot((!(inputMethod instanceof InputMethodAdapter) || ((InputMethodAdapter)inputMethod)
/*      */         
/*  884 */         .supportsBelowTheSpot()));
/*  885 */     return inputMethod;
/*      */   }
/*      */   
/*      */   private void logCreationFailed(Throwable paramThrowable) {
/*  889 */     PlatformLogger platformLogger = PlatformLogger.getLogger("sun.awt.im");
/*  890 */     if (platformLogger.isLoggable(PlatformLogger.Level.CONFIG)) {
/*  891 */       String str = Toolkit.getProperty("AWT.InputMethodCreationFailed", "Could not create {0}. Reason: {1}");
/*      */ 
/*      */ 
/*      */       
/*  895 */       Object[] arrayOfObject = { this.inputMethodLocator.getDescriptor().getInputMethodDisplayName(null, Locale.getDefault()), paramThrowable.getLocalizedMessage() };
/*  896 */       MessageFormat messageFormat = new MessageFormat(str);
/*  897 */       platformLogger.config(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */   }
/*      */   
/*      */   InputMethodLocator getInputMethodLocator() {
/*  902 */     if (this.inputMethod != null) {
/*  903 */       return this.inputMethodLocator.deriveLocator(this.inputMethod.getLocale());
/*      */     }
/*  905 */     return this.inputMethodLocator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void endComposition() {
/*  912 */     if (this.inputMethod != null) {
/*  913 */       this.inputMethod.endComposition();
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
/*      */   synchronized void enableClientWindowNotification(InputMethod paramInputMethod, boolean paramBoolean) {
/*  925 */     if (paramInputMethod != this.inputMethod) {
/*  926 */       if (this.perInputMethodState == null) {
/*  927 */         this.perInputMethodState = new HashMap<>(5);
/*      */       }
/*  929 */       this.perInputMethodState.put(paramInputMethod, Boolean.valueOf(paramBoolean));
/*      */       
/*      */       return;
/*      */     } 
/*  933 */     if (this.clientWindowNotificationEnabled != paramBoolean) {
/*  934 */       this.clientWindowLocation = null;
/*  935 */       this.clientWindowNotificationEnabled = paramBoolean;
/*      */     } 
/*  937 */     if (this.clientWindowNotificationEnabled) {
/*  938 */       if (!addedClientWindowListeners()) {
/*  939 */         addClientWindowListeners();
/*      */       }
/*  941 */       if (this.clientWindowListened != null) {
/*  942 */         this.clientWindowLocation = null;
/*  943 */         notifyClientWindowChange(this.clientWindowListened);
/*      */       }
/*      */     
/*  946 */     } else if (addedClientWindowListeners()) {
/*  947 */       removeClientWindowListeners();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private synchronized void notifyClientWindowChange(Window paramWindow) {
/*  953 */     if (this.inputMethod == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  958 */     if (!paramWindow.isVisible() || (paramWindow instanceof Frame && ((Frame)paramWindow)
/*  959 */       .getState() == 1)) {
/*  960 */       this.clientWindowLocation = null;
/*  961 */       this.inputMethod.notifyClientWindowChange(null);
/*      */       return;
/*      */     } 
/*  964 */     Rectangle rectangle = paramWindow.getBounds();
/*  965 */     if (this.clientWindowLocation == null || !this.clientWindowLocation.equals(rectangle)) {
/*  966 */       this.clientWindowLocation = rectangle;
/*  967 */       this.inputMethod.notifyClientWindowChange(this.clientWindowLocation);
/*      */     } 
/*      */   }
/*      */   
/*      */   private synchronized void addClientWindowListeners() {
/*  972 */     Component component = getClientComponent();
/*  973 */     if (component == null) {
/*      */       return;
/*      */     }
/*  976 */     Window window = getComponentWindow(component);
/*  977 */     if (window == null) {
/*      */       return;
/*      */     }
/*  980 */     window.addComponentListener(this);
/*  981 */     window.addWindowListener(this);
/*  982 */     this.clientWindowListened = window;
/*      */   }
/*      */   
/*      */   private synchronized void removeClientWindowListeners() {
/*  986 */     this.clientWindowListened.removeComponentListener(this);
/*  987 */     this.clientWindowListened.removeWindowListener(this);
/*  988 */     this.clientWindowListened = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean addedClientWindowListeners() {
/*  996 */     return (this.clientWindowListened != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void componentResized(ComponentEvent paramComponentEvent) {
/* 1003 */     notifyClientWindowChange((Window)paramComponentEvent.getComponent());
/*      */   }
/*      */   
/*      */   public void componentMoved(ComponentEvent paramComponentEvent) {
/* 1007 */     notifyClientWindowChange((Window)paramComponentEvent.getComponent());
/*      */   }
/*      */   
/*      */   public void componentShown(ComponentEvent paramComponentEvent) {
/* 1011 */     notifyClientWindowChange((Window)paramComponentEvent.getComponent());
/*      */   }
/*      */   
/*      */   public void componentHidden(ComponentEvent paramComponentEvent) {
/* 1015 */     notifyClientWindowChange((Window)paramComponentEvent.getComponent());
/*      */   }
/*      */ 
/*      */   
/*      */   public void windowOpened(WindowEvent paramWindowEvent) {}
/*      */ 
/*      */   
/*      */   public void windowIconified(WindowEvent paramWindowEvent) {
/* 1023 */     notifyClientWindowChange(paramWindowEvent.getWindow());
/*      */   } public void windowClosing(WindowEvent paramWindowEvent) {}
/*      */   public void windowClosed(WindowEvent paramWindowEvent) {}
/*      */   public void windowDeiconified(WindowEvent paramWindowEvent) {
/* 1027 */     notifyClientWindowChange(paramWindowEvent.getWindow());
/*      */   }
/*      */ 
/*      */   
/*      */   public void windowActivated(WindowEvent paramWindowEvent) {}
/*      */ 
/*      */   
/*      */   public void windowDeactivated(WindowEvent paramWindowEvent) {}
/*      */   
/*      */   private void initializeInputMethodSelectionKey() {
/* 1037 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/* 1040 */             Preferences preferences = Preferences.userRoot();
/* 1041 */             InputContext.inputMethodSelectionKey = InputContext.this.getInputMethodSelectionKeyStroke(preferences);
/*      */             
/* 1043 */             if (InputContext.inputMethodSelectionKey == null) {
/*      */               
/* 1045 */               preferences = Preferences.systemRoot();
/* 1046 */               InputContext.inputMethodSelectionKey = InputContext.this.getInputMethodSelectionKeyStroke(preferences);
/*      */             } 
/* 1048 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private AWTKeyStroke getInputMethodSelectionKeyStroke(Preferences paramPreferences) {
/*      */     try {
/* 1055 */       if (paramPreferences.nodeExists("/java/awt/im/selectionKey")) {
/* 1056 */         Preferences preferences = paramPreferences.node("/java/awt/im/selectionKey");
/* 1057 */         int i = preferences.getInt("keyCode", 0);
/* 1058 */         if (i != 0) {
/* 1059 */           int j = preferences.getInt("modifiers", 0);
/* 1060 */           return AWTKeyStroke.getAWTKeyStroke(i, j);
/*      */         } 
/*      */       } 
/* 1063 */     } catch (BackingStoreException backingStoreException) {}
/*      */ 
/*      */     
/* 1066 */     return null;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/im/InputContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */