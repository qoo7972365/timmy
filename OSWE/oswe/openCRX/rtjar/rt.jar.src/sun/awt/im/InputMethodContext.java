/*     */ package sun.awt.im;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.InputMethodEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.font.TextHitInfo;
/*     */ import java.awt.im.InputMethodRequests;
/*     */ import java.awt.im.spi.InputMethod;
/*     */ import java.awt.im.spi.InputMethodContext;
/*     */ import java.security.AccessController;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.text.AttributedString;
/*     */ import javax.swing.JFrame;
/*     */ import sun.awt.InputMethodSupport;
/*     */ import sun.security.action.GetPropertyAction;
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
/*     */ public class InputMethodContext
/*     */   extends InputContext
/*     */   implements InputMethodContext
/*     */ {
/*     */   private boolean dispatchingCommittedText;
/*     */   private CompositionAreaHandler compositionAreaHandler;
/*  67 */   private Object compositionAreaHandlerLock = new Object();
/*     */ 
/*     */   
/*     */   private static boolean belowTheSpotInputRequested;
/*     */   
/*     */   private boolean inputMethodSupportsBelowTheSpot;
/*     */ 
/*     */   
/*     */   static {
/*  76 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("java.awt.im.style", null));
/*     */     
/*  78 */     if (str == null) {
/*  79 */       str = Toolkit.getProperty("java.awt.im.style", null);
/*     */     }
/*  81 */     belowTheSpotInputRequested = "below-the-spot".equals(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setInputMethodSupportsBelowTheSpot(boolean paramBoolean) {
/*  92 */     this.inputMethodSupportsBelowTheSpot = paramBoolean;
/*     */   }
/*     */   
/*     */   boolean useBelowTheSpotInput() {
/*  96 */     return (belowTheSpotInputRequested && this.inputMethodSupportsBelowTheSpot);
/*     */   }
/*     */   
/*     */   private boolean haveActiveClient() {
/* 100 */     Component component = getClientComponent();
/* 101 */     return (component != null && component
/* 102 */       .getInputMethodRequests() != null);
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
/*     */   public void dispatchInputMethodEvent(int paramInt1, AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt2, TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2) {
/* 114 */     Component component = getClientComponent();
/* 115 */     if (component != null) {
/* 116 */       InputMethodEvent inputMethodEvent = new InputMethodEvent(component, paramInt1, paramAttributedCharacterIterator, paramInt2, paramTextHitInfo1, paramTextHitInfo2);
/*     */ 
/*     */       
/* 119 */       if (haveActiveClient() && !useBelowTheSpotInput()) {
/* 120 */         component.dispatchEvent(inputMethodEvent);
/*     */       } else {
/* 122 */         getCompositionAreaHandler(true).processInputMethodEvent(inputMethodEvent);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void dispatchCommittedText(Component paramComponent, AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt) {
/* 143 */     if (paramInt == 0 || paramAttributedCharacterIterator
/* 144 */       .getEndIndex() <= paramAttributedCharacterIterator.getBeginIndex()) {
/*     */       return;
/*     */     }
/* 147 */     long l = System.currentTimeMillis();
/* 148 */     this.dispatchingCommittedText = true;
/*     */     try {
/* 150 */       InputMethodRequests inputMethodRequests = paramComponent.getInputMethodRequests();
/* 151 */       if (inputMethodRequests != null) {
/*     */         
/* 153 */         int i = paramAttributedCharacterIterator.getBeginIndex();
/*     */         
/* 155 */         AttributedCharacterIterator attributedCharacterIterator = (new AttributedString(paramAttributedCharacterIterator, i, i + paramInt)).getIterator();
/*     */         
/* 157 */         InputMethodEvent inputMethodEvent = new InputMethodEvent(paramComponent, 1100, attributedCharacterIterator, paramInt, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 164 */         paramComponent.dispatchEvent(inputMethodEvent);
/*     */       } else {
/*     */         
/* 167 */         char c = paramAttributedCharacterIterator.first();
/* 168 */         while (paramInt-- > 0 && c != Character.MAX_VALUE) {
/* 169 */           KeyEvent keyEvent = new KeyEvent(paramComponent, 400, l, 0, 0, c);
/*     */           
/* 171 */           paramComponent.dispatchEvent(keyEvent);
/* 172 */           c = paramAttributedCharacterIterator.next();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 176 */       this.dispatchingCommittedText = false;
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
/*     */   public void dispatchEvent(AWTEvent paramAWTEvent) {
/* 188 */     if (paramAWTEvent instanceof InputMethodEvent) {
/* 189 */       if (((Component)paramAWTEvent.getSource()).getInputMethodRequests() == null || (
/* 190 */         useBelowTheSpotInput() && !this.dispatchingCommittedText)) {
/* 191 */         getCompositionAreaHandler(true).processInputMethodEvent((InputMethodEvent)paramAWTEvent);
/*     */       
/*     */       }
/*     */     }
/* 195 */     else if (!this.dispatchingCommittedText) {
/* 196 */       super.dispatchEvent(paramAWTEvent);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CompositionAreaHandler getCompositionAreaHandler(boolean paramBoolean) {
/* 207 */     synchronized (this.compositionAreaHandlerLock) {
/* 208 */       if (this.compositionAreaHandler == null) {
/* 209 */         this.compositionAreaHandler = new CompositionAreaHandler(this);
/*     */       }
/* 211 */       this.compositionAreaHandler.setClientComponent(getClientComponent());
/* 212 */       if (paramBoolean) {
/* 213 */         this.compositionAreaHandler.grabCompositionArea(false);
/*     */       }
/*     */       
/* 216 */       return this.compositionAreaHandler;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void grabCompositionArea(boolean paramBoolean) {
/* 226 */     synchronized (this.compositionAreaHandlerLock) {
/* 227 */       if (this.compositionAreaHandler != null) {
/* 228 */         this.compositionAreaHandler.grabCompositionArea(paramBoolean);
/*     */       }
/*     */       else {
/*     */         
/* 232 */         CompositionAreaHandler.closeCompositionArea();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void releaseCompositionArea() {
/* 242 */     synchronized (this.compositionAreaHandlerLock) {
/* 243 */       if (this.compositionAreaHandler != null) {
/* 244 */         this.compositionAreaHandler.releaseCompositionArea();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isCompositionAreaVisible() {
/* 256 */     if (this.compositionAreaHandler != null) {
/* 257 */       return this.compositionAreaHandler.isCompositionAreaVisible();
/*     */     }
/*     */     
/* 260 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setCompositionAreaVisible(boolean paramBoolean) {
/* 269 */     if (this.compositionAreaHandler != null) {
/* 270 */       this.compositionAreaHandler.setCompositionAreaVisible(paramBoolean);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getTextLocation(TextHitInfo paramTextHitInfo) {
/* 278 */     return getReq().getTextLocation(paramTextHitInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextHitInfo getLocationOffset(int paramInt1, int paramInt2) {
/* 285 */     return getReq().getLocationOffset(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInsertPositionOffset() {
/* 292 */     return getReq().getInsertPositionOffset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributedCharacterIterator getCommittedText(int paramInt1, int paramInt2, AttributedCharacterIterator.Attribute[] paramArrayOfAttribute) {
/* 301 */     return getReq().getCommittedText(paramInt1, paramInt2, paramArrayOfAttribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCommittedTextLength() {
/* 308 */     return getReq().getCommittedTextLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributedCharacterIterator cancelLatestCommittedText(AttributedCharacterIterator.Attribute[] paramArrayOfAttribute) {
/* 316 */     return getReq().cancelLatestCommittedText(paramArrayOfAttribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributedCharacterIterator getSelectedText(AttributedCharacterIterator.Attribute[] paramArrayOfAttribute) {
/* 323 */     return getReq().getSelectedText(paramArrayOfAttribute);
/*     */   }
/*     */   
/*     */   private InputMethodRequests getReq() {
/* 327 */     if (haveActiveClient() && !useBelowTheSpotInput()) {
/* 328 */       return getClientComponent().getInputMethodRequests();
/*     */     }
/* 330 */     return getCompositionAreaHandler(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Window createInputMethodWindow(String paramString, boolean paramBoolean) {
/* 336 */     InputMethodContext inputMethodContext = paramBoolean ? this : null;
/* 337 */     return createInputMethodWindow(paramString, inputMethodContext, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public JFrame createInputMethodJFrame(String paramString, boolean paramBoolean) {
/* 342 */     InputMethodContext inputMethodContext = paramBoolean ? this : null;
/* 343 */     return (JFrame)createInputMethodWindow(paramString, inputMethodContext, true);
/*     */   }
/*     */   
/*     */   static Window createInputMethodWindow(String paramString, InputContext paramInputContext, boolean paramBoolean) {
/* 347 */     if (GraphicsEnvironment.isHeadless()) {
/* 348 */       throw new HeadlessException();
/*     */     }
/* 350 */     if (paramBoolean) {
/* 351 */       return new InputMethodJFrame(paramString, paramInputContext);
/*     */     }
/* 353 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 354 */     if (toolkit instanceof InputMethodSupport) {
/* 355 */       return ((InputMethodSupport)toolkit).createInputMethodWindow(paramString, paramInputContext);
/*     */     }
/*     */ 
/*     */     
/* 359 */     throw new InternalError("Input methods must be supported");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableClientWindowNotification(InputMethod paramInputMethod, boolean paramBoolean) {
/* 366 */     super.enableClientWindowNotification(paramInputMethod, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setCompositionAreaUndecorated(boolean paramBoolean) {
/* 373 */     if (this.compositionAreaHandler != null)
/* 374 */       this.compositionAreaHandler.setCompositionAreaUndecorated(paramBoolean); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/im/InputMethodContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */