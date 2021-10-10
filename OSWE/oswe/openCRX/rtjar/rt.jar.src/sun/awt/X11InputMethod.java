/*      */ package sun.awt;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.AWTException;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.InputMethodEvent;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.font.TextHitInfo;
/*      */ import java.awt.im.InputMethodHighlight;
/*      */ import java.awt.im.spi.InputMethodContext;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileReader;
/*      */ import java.io.IOException;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.AttributedString;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.regex.Pattern;
/*      */ import sun.awt.im.InputMethodAdapter;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class X11InputMethod
/*      */   extends InputMethodAdapter
/*      */ {
/*   72 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11InputMethod");
/*      */   
/*      */   private static final int XIMReverse = 1;
/*      */   
/*      */   private static final int XIMUnderline = 2;
/*      */   
/*      */   private static final int XIMHighlight = 4;
/*      */   
/*      */   private static final int XIMPrimary = 32;
/*      */   
/*      */   private static final int XIMSecondary = 64;
/*      */   
/*      */   private static final int XIMTertiary = 128;
/*      */   
/*      */   private static final int XIMVisibleToForward = 256;
/*      */   
/*      */   private static final int XIMVisibleToBackward = 512;
/*      */   
/*      */   private static final int XIMVisibleCenter = 1024;
/*      */   
/*      */   private static final int XIMVisibleMask = 1792;
/*      */   
/*      */   private Locale locale;
/*      */   private static boolean isXIMOpened = false;
/*   96 */   protected Container clientComponentWindow = null;
/*   97 */   private Component awtFocussedComponent = null;
/*   98 */   private Component lastXICFocussedComponent = null;
/*      */   
/*      */   private boolean isLastXICActive = false;
/*      */   
/*      */   private boolean isLastTemporary = false;
/*      */   private boolean isActive = false;
/*      */   private boolean isActiveClient = false;
/*      */   private static Map[] highlightStyles;
/*      */   private boolean disposed = false;
/*      */   private boolean needResetXIC = false;
/*  108 */   private WeakReference<Component> needResetXICClient = new WeakReference<>(null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean compositionEnableSupported = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean savedCompositionState = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  128 */   private String committedText = null;
/*  129 */   private StringBuffer composedText = null;
/*      */ 
/*      */   
/*      */   private IntBuffer rawFeedbacks;
/*      */ 
/*      */   
/*  135 */   private transient long pData = 0L;
/*      */   private static final int INITIAL_SIZE = 64;
/*      */   
/*      */   static {
/*  139 */     Map[] arrayOfMap = new Map[4];
/*      */ 
/*      */ 
/*      */     
/*  143 */     HashMap<Object, Object> hashMap = new HashMap<>(1);
/*  144 */     hashMap.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
/*      */     
/*  146 */     arrayOfMap[0] = Collections.unmodifiableMap(hashMap);
/*      */ 
/*      */     
/*  149 */     hashMap = new HashMap<>(1);
/*  150 */     hashMap.put(TextAttribute.SWAP_COLORS, TextAttribute.SWAP_COLORS_ON);
/*      */     
/*  152 */     arrayOfMap[1] = Collections.unmodifiableMap(hashMap);
/*      */ 
/*      */     
/*  155 */     hashMap = new HashMap<>(1);
/*  156 */     hashMap.put(TextAttribute.INPUT_METHOD_UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
/*      */     
/*  158 */     arrayOfMap[2] = Collections.unmodifiableMap(hashMap);
/*      */ 
/*      */     
/*  161 */     hashMap = new HashMap<>(1);
/*  162 */     hashMap.put(TextAttribute.SWAP_COLORS, TextAttribute.SWAP_COLORS_ON);
/*      */     
/*  164 */     arrayOfMap[3] = Collections.unmodifiableMap(hashMap);
/*      */     
/*  166 */     highlightStyles = arrayOfMap;
/*      */ 
/*      */ 
/*      */     
/*  170 */     initIDs();
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
/*      */   public X11InputMethod() throws AWTException {
/*  187 */     this.locale = X11InputMethodDescriptor.getSupportedLocale();
/*  188 */     if (!initXIM()) {
/*  189 */       throw new AWTException("Cannot open X Input Method");
/*      */     }
/*      */   }
/*      */   
/*      */   protected void finalize() throws Throwable {
/*  194 */     dispose();
/*  195 */     super.finalize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized boolean initXIM() {
/*  203 */     if (!isXIMOpened)
/*  204 */       isXIMOpened = openXIM(); 
/*  205 */     return isXIMOpened;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isDisposed() {
/*  211 */     return this.disposed;
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
/*      */   public void setInputMethodContext(InputMethodContext paramInputMethodContext) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setLocale(Locale paramLocale) {
/*  233 */     if (paramLocale.equals(this.locale)) {
/*  234 */       return true;
/*      */     }
/*      */     
/*  237 */     if ((this.locale.equals(Locale.JAPAN) && paramLocale.equals(Locale.JAPANESE)) || (this.locale
/*  238 */       .equals(Locale.KOREA) && paramLocale.equals(Locale.KOREAN))) {
/*  239 */       return true;
/*      */     }
/*  241 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  248 */     return this.locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterSubsets(Character.Subset[] paramArrayOfSubset) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispatchEvent(AWTEvent paramAWTEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void resetXICifneeded() {
/*  275 */     if (this.needResetXIC && haveActiveClient() && 
/*  276 */       getClientComponent() != this.needResetXICClient.get()) {
/*  277 */       resetXIC();
/*      */ 
/*      */       
/*  280 */       this.lastXICFocussedComponent = null;
/*  281 */       this.isLastXICActive = false;
/*      */       
/*  283 */       this.needResetXICClient.clear();
/*  284 */       this.needResetXIC = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetCompositionState() {
/*  292 */     if (this.compositionEnableSupported) {
/*      */       
/*      */       try {
/*      */         
/*  296 */         setCompositionEnabled(this.savedCompositionState);
/*  297 */       } catch (UnsupportedOperationException unsupportedOperationException) {
/*  298 */         this.compositionEnableSupported = false;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getCompositionState() {
/*  309 */     boolean bool = false;
/*  310 */     if (this.compositionEnableSupported) {
/*      */       try {
/*  312 */         bool = isCompositionEnabled();
/*  313 */       } catch (UnsupportedOperationException unsupportedOperationException) {
/*  314 */         this.compositionEnableSupported = false;
/*      */       } 
/*      */     }
/*  317 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void activate() {
/*  324 */     this.clientComponentWindow = getClientComponentWindow();
/*  325 */     if (this.clientComponentWindow == null) {
/*      */       return;
/*      */     }
/*  328 */     if (this.lastXICFocussedComponent != null && 
/*  329 */       log.isLoggable(PlatformLogger.Level.FINE)) {
/*  330 */       log.fine("XICFocused {0}, AWTFocused {1}", new Object[] { this.lastXICFocussedComponent, this.awtFocussedComponent });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  335 */     if (this.pData == 0L) {
/*  336 */       if (!createXIC()) {
/*      */         return;
/*      */       }
/*  339 */       this.disposed = false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  344 */     resetXICifneeded();
/*  345 */     ComponentPeer componentPeer1 = null;
/*  346 */     ComponentPeer componentPeer2 = getPeer(this.awtFocussedComponent);
/*      */     
/*  348 */     if (this.lastXICFocussedComponent != null) {
/*  349 */       componentPeer1 = getPeer(this.lastXICFocussedComponent);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  356 */     if (this.isLastTemporary || componentPeer1 != componentPeer2 || this.isLastXICActive != 
/*  357 */       haveActiveClient()) {
/*  358 */       if (componentPeer1 != null) {
/*  359 */         setXICFocus(componentPeer1, false, this.isLastXICActive);
/*      */       }
/*  361 */       if (componentPeer2 != null) {
/*  362 */         setXICFocus(componentPeer2, true, haveActiveClient());
/*      */       }
/*  364 */       this.lastXICFocussedComponent = this.awtFocussedComponent;
/*  365 */       this.isLastXICActive = haveActiveClient();
/*      */     } 
/*  367 */     resetCompositionState();
/*  368 */     this.isActive = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void deactivate(boolean paramBoolean) {
/*  377 */     boolean bool = haveActiveClient();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  396 */     this.savedCompositionState = getCompositionState();
/*      */     
/*  398 */     if (paramBoolean)
/*      */     {
/*  400 */       turnoffStatusWindow();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  406 */     this.lastXICFocussedComponent = this.awtFocussedComponent;
/*  407 */     this.isLastXICActive = bool;
/*  408 */     this.isLastTemporary = paramBoolean;
/*  409 */     this.isActive = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void disableInputMethod() {
/*  417 */     if (this.lastXICFocussedComponent != null) {
/*  418 */       setXICFocus(getPeer(this.lastXICFocussedComponent), false, this.isLastXICActive);
/*  419 */       this.lastXICFocussedComponent = null;
/*  420 */       this.isLastXICActive = false;
/*      */       
/*  422 */       resetXIC();
/*  423 */       this.needResetXICClient.clear();
/*  424 */       this.needResetXIC = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hideWindows() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map mapInputMethodHighlight(InputMethodHighlight paramInputMethodHighlight) {
/*      */     byte b;
/*  438 */     int i = paramInputMethodHighlight.getState();
/*  439 */     if (i == 0) {
/*  440 */       b = 0;
/*  441 */     } else if (i == 1) {
/*  442 */       b = 2;
/*      */     } else {
/*  444 */       return null;
/*      */     } 
/*  446 */     if (paramInputMethodHighlight.isSelected()) {
/*  447 */       b++;
/*      */     }
/*  449 */     return highlightStyles[b];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setAWTFocussedComponent(Component paramComponent) {
/*  456 */     if (paramComponent == null) {
/*      */       return;
/*      */     }
/*  459 */     if (this.isActive) {
/*      */ 
/*      */       
/*  462 */       boolean bool = haveActiveClient();
/*  463 */       setXICFocus(getPeer(this.awtFocussedComponent), false, bool);
/*  464 */       setXICFocus(getPeer(paramComponent), true, bool);
/*      */     } 
/*  466 */     this.awtFocussedComponent = paramComponent;
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
/*      */   protected void stopListening() {
/*  478 */     endComposition();
/*      */ 
/*      */     
/*  481 */     disableInputMethod();
/*  482 */     if (this.needResetXIC) {
/*  483 */       resetXIC();
/*  484 */       this.needResetXICClient.clear();
/*  485 */       this.needResetXIC = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Window getClientComponentWindow() {
/*      */     Container container;
/*  496 */     Component component = getClientComponent();
/*      */ 
/*      */     
/*  499 */     if (component instanceof Container) {
/*  500 */       container = (Container)component;
/*      */     } else {
/*  502 */       container = getParent(component);
/*      */     } 
/*      */     
/*  505 */     while (container != null && !(container instanceof Window)) {
/*  506 */       container = getParent(container);
/*      */     }
/*  508 */     return (Window)container;
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
/*      */   private void postInputMethodEvent(int paramInt1, AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt2, TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2, long paramLong) {
/*  538 */     Component component = getClientComponent();
/*  539 */     if (component != null) {
/*  540 */       InputMethodEvent inputMethodEvent = new InputMethodEvent(component, paramInt1, paramLong, paramAttributedCharacterIterator, paramInt2, paramTextHitInfo1, paramTextHitInfo2);
/*      */       
/*  542 */       SunToolkit.postEvent(SunToolkit.targetToAppContext(component), inputMethodEvent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void postInputMethodEvent(int paramInt1, AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt2, TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2) {
/*  551 */     postInputMethodEvent(paramInt1, paramAttributedCharacterIterator, paramInt2, paramTextHitInfo1, paramTextHitInfo2, 
/*  552 */         EventQueue.getMostRecentEventTime());
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
/*      */   void dispatchCommittedText(String paramString, long paramLong) {
/*  567 */     if (paramString == null) {
/*      */       return;
/*      */     }
/*  570 */     if (this.composedText == null) {
/*  571 */       AttributedString attributedString = new AttributedString(paramString);
/*  572 */       postInputMethodEvent(1100, attributedString
/*  573 */           .getIterator(), paramString
/*  574 */           .length(), (TextHitInfo)null, (TextHitInfo)null, paramLong);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  581 */       this.committedText = paramString;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void dispatchCommittedText(String paramString) {
/*  586 */     dispatchCommittedText(paramString, EventQueue.getMostRecentEventTime());
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
/*      */   void dispatchComposedText(String paramString, int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, long paramLong) {
/*      */     byte b;
/*      */     AttributedString attributedString;
/*  608 */     if (this.disposed) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  613 */     if (paramString == null && paramArrayOfint == null && paramInt1 == 0 && paramInt2 == 0 && paramInt3 == 0 && this.composedText == null && this.committedText == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  622 */     if (this.composedText == null) {
/*      */       
/*  624 */       this.composedText = new StringBuffer(64);
/*  625 */       this.rawFeedbacks = new IntBuffer(64);
/*      */     } 
/*  627 */     if (paramInt2 > 0) {
/*  628 */       if (paramString == null && paramArrayOfint != null) {
/*  629 */         this.rawFeedbacks.replace(paramInt1, paramArrayOfint);
/*      */       }
/*  631 */       else if (paramInt2 == this.composedText.length()) {
/*      */ 
/*      */         
/*  634 */         this.composedText = new StringBuffer(64);
/*  635 */         this.rawFeedbacks = new IntBuffer(64);
/*      */       }
/*  637 */       else if (this.composedText.length() > 0) {
/*  638 */         if (paramInt1 + paramInt2 < this.composedText.length()) {
/*      */           
/*  640 */           String str = this.composedText.toString().substring(paramInt1 + paramInt2, this.composedText
/*  641 */               .length());
/*  642 */           this.composedText.setLength(paramInt1);
/*  643 */           this.composedText.append(str);
/*      */         }
/*      */         else {
/*      */           
/*  647 */           this.composedText.setLength(paramInt1);
/*      */         } 
/*  649 */         this.rawFeedbacks.remove(paramInt1, paramInt2);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  654 */     if (paramString != null) {
/*  655 */       this.composedText.insert(paramInt1, paramString);
/*  656 */       if (paramArrayOfint != null) {
/*  657 */         this.rawFeedbacks.insert(paramInt1, paramArrayOfint);
/*      */       }
/*      */     } 
/*  660 */     if (this.composedText.length() == 0) {
/*  661 */       this.composedText = null;
/*  662 */       this.rawFeedbacks = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  667 */       if (this.committedText != null) {
/*  668 */         dispatchCommittedText(this.committedText, paramLong);
/*  669 */         this.committedText = null;
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  675 */       postInputMethodEvent(1100, (AttributedCharacterIterator)null, 0, (TextHitInfo)null, (TextHitInfo)null, paramLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  691 */     if (this.committedText != null) {
/*  692 */       b = this.committedText.length();
/*  693 */       attributedString = new AttributedString(this.committedText + this.composedText);
/*  694 */       this.committedText = null;
/*      */     } else {
/*  696 */       b = 0;
/*  697 */       attributedString = new AttributedString(this.composedText.toString());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  702 */     int k = 0;
/*      */     
/*  704 */     int n = 0;
/*  705 */     TextHitInfo textHitInfo = null;
/*      */     
/*  707 */     this.rawFeedbacks.rewind();
/*  708 */     int i = this.rawFeedbacks.getNext();
/*  709 */     this.rawFeedbacks.unget(); int j;
/*  710 */     while ((j = this.rawFeedbacks.getNext()) != -1) {
/*  711 */       if (!n) {
/*  712 */         n = j & 0x700;
/*  713 */         if (n != 0) {
/*  714 */           int i1 = this.rawFeedbacks.getOffset() - 1;
/*      */           
/*  716 */           if (n == 512) {
/*  717 */             textHitInfo = TextHitInfo.leading(i1);
/*      */           } else {
/*  719 */             textHitInfo = TextHitInfo.trailing(i1);
/*      */           } 
/*      */         } 
/*  722 */       }  j &= 0xFFFFF8FF;
/*  723 */       if (i != j) {
/*  724 */         this.rawFeedbacks.unget();
/*  725 */         int i1 = this.rawFeedbacks.getOffset();
/*  726 */         attributedString.addAttribute(TextAttribute.INPUT_METHOD_HIGHLIGHT, 
/*  727 */             convertVisualFeedbackToHighlight(i), b + k, b + i1);
/*      */ 
/*      */         
/*  730 */         k = i1;
/*  731 */         i = j;
/*      */       } 
/*      */     } 
/*  734 */     int m = this.rawFeedbacks.getOffset();
/*  735 */     if (m >= 0) {
/*  736 */       attributedString.addAttribute(TextAttribute.INPUT_METHOD_HIGHLIGHT, 
/*  737 */           convertVisualFeedbackToHighlight(i), b + k, b + m);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  742 */     postInputMethodEvent(1100, attributedString
/*  743 */         .getIterator(), b, 
/*      */         
/*  745 */         TextHitInfo.leading(paramInt3), textHitInfo, paramLong);
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
/*      */   void flushText() {
/*  760 */     String str = (this.committedText != null) ? this.committedText : "";
/*  761 */     if (this.composedText != null) {
/*  762 */       str = str + this.composedText.toString();
/*      */     }
/*      */     
/*  765 */     if (!str.equals("")) {
/*  766 */       AttributedString attributedString = new AttributedString(str);
/*  767 */       postInputMethodEvent(1100, attributedString
/*  768 */           .getIterator(), str
/*  769 */           .length(), (TextHitInfo)null, (TextHitInfo)null, 
/*      */ 
/*      */           
/*  772 */           EventQueue.getMostRecentEventTime());
/*  773 */       this.composedText = null;
/*  774 */       this.committedText = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void disposeImpl() {
/*  783 */     disposeXIC();
/*  784 */     awtLock();
/*  785 */     this.composedText = null;
/*  786 */     this.committedText = null;
/*  787 */     this.rawFeedbacks = null;
/*  788 */     awtUnlock();
/*  789 */     this.awtFocussedComponent = null;
/*  790 */     this.lastXICFocussedComponent = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void dispose() {
/*  799 */     boolean bool = false;
/*      */     
/*  801 */     if (!this.disposed) {
/*  802 */       synchronized (this) {
/*  803 */         if (!this.disposed) {
/*  804 */           this.disposed = bool = true;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  809 */     if (bool) {
/*  810 */       disposeImpl();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getControlObject() {
/*  820 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void removeNotify() {
/*  827 */     dispose();
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
/*      */   public void setCompositionEnabled(boolean paramBoolean) {
/*  842 */     if (setCompositionEnabledNative(paramBoolean)) {
/*  843 */       this.savedCompositionState = paramBoolean;
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
/*      */   public boolean isCompositionEnabled() {
/*  856 */     return isCompositionEnabledNative();
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
/*      */   public void endComposition() {
/*  873 */     if (this.disposed) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  879 */     this.savedCompositionState = getCompositionState();
/*  880 */     boolean bool = haveActiveClient();
/*  881 */     if (bool && this.composedText == null && this.committedText == null) {
/*  882 */       this.needResetXIC = true;
/*  883 */       this.needResetXICClient = new WeakReference<>(getClientComponent());
/*      */       
/*      */       return;
/*      */     } 
/*  887 */     String str = resetXIC();
/*      */ 
/*      */     
/*  890 */     if (bool) {
/*  891 */       this.needResetXIC = false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  900 */     awtLock();
/*  901 */     this.composedText = null;
/*  902 */     postInputMethodEvent(1100, (AttributedCharacterIterator)null, 0, (TextHitInfo)null, (TextHitInfo)null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  908 */     if (str != null && str.length() > 0) {
/*  909 */       dispatchCommittedText(str);
/*      */     }
/*  911 */     awtUnlock();
/*      */ 
/*      */     
/*  914 */     if (this.savedCompositionState) {
/*  915 */       resetCompositionState();
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
/*      */   public String getNativeInputMethodInfo() {
/*  936 */     String str1 = System.getenv("XMODIFIERS");
/*  937 */     String str2 = null;
/*      */ 
/*      */     
/*  940 */     if (str1 != null) {
/*  941 */       int i = str1.indexOf("@im=");
/*  942 */       if (i != -1) {
/*  943 */         str2 = str1.substring(i + 4);
/*      */       }
/*  945 */     } else if (System.getProperty("os.name").startsWith("SunOS")) {
/*  946 */       File file = new File(System.getProperty("user.home") + "/.dtprofile");
/*      */       
/*  948 */       String str = null;
/*      */       try {
/*  950 */         BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
/*  951 */         String str3 = null;
/*      */         
/*  953 */         while (str == null && (str3 = bufferedReader.readLine()) != null) {
/*  954 */           if (str3.contains("atok") || str3.contains("wnn")) {
/*  955 */             StringTokenizer stringTokenizer = new StringTokenizer(str3);
/*  956 */             label30: while (stringTokenizer.hasMoreTokens()) {
/*  957 */               String str4 = stringTokenizer.nextToken();
/*  958 */               if (!Pattern.matches("atok.*setup", str4))
/*  959 */               { if (Pattern.matches("wnn.*setup", str4))
/*  960 */                   break label30;  continue; }  str = str4.substring(0, str4.indexOf("setup"));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  967 */         bufferedReader.close();
/*  968 */       } catch (IOException iOException) {
/*      */ 
/*      */         
/*  971 */         iOException.printStackTrace();
/*      */       } 
/*      */       
/*  974 */       str2 = "htt " + str;
/*      */     } 
/*      */     
/*  977 */     return str2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InputMethodHighlight convertVisualFeedbackToHighlight(int paramInt) {
/*  988 */     switch (paramInt)
/*      */     { case 2:
/*  990 */         inputMethodHighlight = InputMethodHighlight.UNSELECTED_CONVERTED_TEXT_HIGHLIGHT;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1011 */         return inputMethodHighlight;case 1: inputMethodHighlight = InputMethodHighlight.SELECTED_CONVERTED_TEXT_HIGHLIGHT; return inputMethodHighlight;case 4: inputMethodHighlight = InputMethodHighlight.SELECTED_RAW_TEXT_HIGHLIGHT; return inputMethodHighlight;case 32: inputMethodHighlight = InputMethodHighlight.UNSELECTED_CONVERTED_TEXT_HIGHLIGHT; return inputMethodHighlight;case 64: inputMethodHighlight = InputMethodHighlight.SELECTED_CONVERTED_TEXT_HIGHLIGHT; return inputMethodHighlight;case 128: inputMethodHighlight = InputMethodHighlight.SELECTED_RAW_TEXT_HIGHLIGHT; return inputMethodHighlight; }  InputMethodHighlight inputMethodHighlight = InputMethodHighlight.SELECTED_RAW_TEXT_HIGHLIGHT; return inputMethodHighlight;
/*      */   }
/*      */   private static native void initIDs();
/*      */   protected abstract boolean openXIM();
/*      */   protected abstract void setXICFocus(ComponentPeer paramComponentPeer, boolean paramBoolean1, boolean paramBoolean2);
/*      */   protected abstract boolean createXIC();
/*      */   protected abstract Container getParent(Component paramComponent);
/*      */   protected abstract ComponentPeer getPeer(Component paramComponent);
/*      */   protected abstract void awtLock();
/*      */   protected abstract void awtUnlock();
/*      */   protected native String resetXIC();
/*      */   private native void disposeXIC();
/*      */   private native boolean setCompositionEnabledNative(boolean paramBoolean);
/*      */   private native boolean isCompositionEnabledNative();
/*      */   private native void turnoffStatusWindow();
/*      */   private final class IntBuffer { private int[] intArray;
/*      */     
/*      */     IntBuffer(int param1Int) {
/* 1029 */       this.intArray = new int[param1Int];
/* 1030 */       this.size = 0;
/* 1031 */       this.index = 0;
/*      */     }
/*      */     private int size; private int index;
/*      */     void insert(int param1Int, int[] param1ArrayOfint) {
/* 1035 */       int i = this.size + param1ArrayOfint.length;
/* 1036 */       if (this.intArray.length < i) {
/* 1037 */         int[] arrayOfInt = new int[i * 2];
/* 1038 */         System.arraycopy(this.intArray, 0, arrayOfInt, 0, this.size);
/* 1039 */         this.intArray = arrayOfInt;
/*      */       } 
/* 1041 */       System.arraycopy(this.intArray, param1Int, this.intArray, param1Int + param1ArrayOfint.length, this.size - param1Int);
/*      */       
/* 1043 */       System.arraycopy(param1ArrayOfint, 0, this.intArray, param1Int, param1ArrayOfint.length);
/* 1044 */       this.size += param1ArrayOfint.length;
/* 1045 */       if (this.index > param1Int)
/* 1046 */         this.index = param1Int; 
/*      */     }
/*      */     
/*      */     void remove(int param1Int1, int param1Int2) {
/* 1050 */       if (param1Int1 + param1Int2 != this.size) {
/* 1051 */         System.arraycopy(this.intArray, param1Int1 + param1Int2, this.intArray, param1Int1, this.size - param1Int1 - param1Int2);
/*      */       }
/* 1053 */       this.size -= param1Int2;
/* 1054 */       if (this.index > param1Int1)
/* 1055 */         this.index = param1Int1; 
/*      */     }
/*      */     
/*      */     void replace(int param1Int, int[] param1ArrayOfint) {
/* 1059 */       System.arraycopy(param1ArrayOfint, 0, this.intArray, param1Int, param1ArrayOfint.length);
/*      */     }
/*      */     
/*      */     void removeAll() {
/* 1063 */       this.size = 0;
/* 1064 */       this.index = 0;
/*      */     }
/*      */     
/*      */     void rewind() {
/* 1068 */       this.index = 0;
/*      */     }
/*      */     
/*      */     int getNext() {
/* 1072 */       if (this.index == this.size)
/* 1073 */         return -1; 
/* 1074 */       return this.intArray[this.index++];
/*      */     }
/*      */     
/*      */     void unget() {
/* 1078 */       if (this.index != 0)
/* 1079 */         this.index--; 
/*      */     }
/*      */     
/*      */     int getOffset() {
/* 1083 */       return this.index;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1087 */       StringBuffer stringBuffer = new StringBuffer();
/* 1088 */       for (byte b = 0; b < this.size; ) {
/* 1089 */         stringBuffer.append(this.intArray[b++]);
/* 1090 */         if (b < this.size)
/* 1091 */           stringBuffer.append(","); 
/*      */       } 
/* 1093 */       return stringBuffer.toString();
/*      */     } }
/*      */ 
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11InputMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */