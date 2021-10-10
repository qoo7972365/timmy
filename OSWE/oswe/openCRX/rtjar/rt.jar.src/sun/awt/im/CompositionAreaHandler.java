/*     */ package sun.awt.im;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.InputMethodEvent;
/*     */ import java.awt.event.InputMethodListener;
/*     */ import java.awt.font.TextAttribute;
/*     */ import java.awt.font.TextHitInfo;
/*     */ import java.awt.im.InputMethodRequests;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.text.AttributedString;
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
/*     */ class CompositionAreaHandler
/*     */   implements InputMethodListener, InputMethodRequests
/*     */ {
/*     */   private static CompositionArea compositionArea;
/*  54 */   private static Object compositionAreaLock = new Object();
/*     */   
/*     */   private static CompositionAreaHandler compositionAreaOwner;
/*     */   private AttributedCharacterIterator composedText;
/*  58 */   private TextHitInfo caret = null;
/*  59 */   private WeakReference<Component> clientComponent = new WeakReference<>(null);
/*     */ 
/*     */   
/*     */   private InputMethodContext inputMethodContext;
/*     */ 
/*     */   
/*     */   CompositionAreaHandler(InputMethodContext paramInputMethodContext) {
/*  66 */     this.inputMethodContext = paramInputMethodContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createCompositionArea() {
/*  73 */     synchronized (compositionAreaLock) {
/*  74 */       compositionArea = new CompositionArea();
/*  75 */       if (compositionAreaOwner != null) {
/*  76 */         compositionArea.setHandlerInfo(compositionAreaOwner, this.inputMethodContext);
/*     */       }
/*     */ 
/*     */       
/*  80 */       Component component = this.clientComponent.get();
/*  81 */       if (component != null) {
/*  82 */         InputMethodRequests inputMethodRequests = component.getInputMethodRequests();
/*  83 */         if (inputMethodRequests != null && this.inputMethodContext.useBelowTheSpotInput()) {
/*  84 */           setCompositionAreaUndecorated(true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void setClientComponent(Component paramComponent) {
/*  91 */     this.clientComponent = new WeakReference<>(paramComponent);
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
/*     */   void grabCompositionArea(boolean paramBoolean) {
/* 103 */     synchronized (compositionAreaLock) {
/* 104 */       if (compositionAreaOwner != this) {
/* 105 */         compositionAreaOwner = this;
/* 106 */         if (compositionArea != null) {
/* 107 */           compositionArea.setHandlerInfo(this, this.inputMethodContext);
/*     */         }
/* 109 */         if (paramBoolean) {
/*     */           
/* 111 */           if (this.composedText != null && compositionArea == null) {
/* 112 */             createCompositionArea();
/*     */           }
/* 114 */           if (compositionArea != null) {
/* 115 */             compositionArea.setText(this.composedText, this.caret);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void releaseCompositionArea() {
/* 127 */     synchronized (compositionAreaLock) {
/* 128 */       if (compositionAreaOwner == this) {
/* 129 */         compositionAreaOwner = null;
/* 130 */         if (compositionArea != null) {
/* 131 */           compositionArea.setHandlerInfo((CompositionAreaHandler)null, (InputContext)null);
/* 132 */           compositionArea.setText((AttributedCharacterIterator)null, (TextHitInfo)null);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void closeCompositionArea() {
/* 143 */     if (compositionArea != null) {
/* 144 */       synchronized (compositionAreaLock) {
/* 145 */         compositionAreaOwner = null;
/* 146 */         compositionArea.setHandlerInfo((CompositionAreaHandler)null, (InputContext)null);
/* 147 */         compositionArea.setText((AttributedCharacterIterator)null, (TextHitInfo)null);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isCompositionAreaVisible() {
/* 156 */     if (compositionArea != null) {
/* 157 */       return compositionArea.isCompositionAreaVisible();
/*     */     }
/*     */     
/* 160 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setCompositionAreaVisible(boolean paramBoolean) {
/* 168 */     if (compositionArea != null) {
/* 169 */       compositionArea.setCompositionAreaVisible(paramBoolean);
/*     */     }
/*     */   }
/*     */   
/*     */   void processInputMethodEvent(InputMethodEvent paramInputMethodEvent) {
/* 174 */     if (paramInputMethodEvent.getID() == 1100) {
/* 175 */       inputMethodTextChanged(paramInputMethodEvent);
/*     */     } else {
/* 177 */       caretPositionChanged(paramInputMethodEvent);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setCompositionAreaUndecorated(boolean paramBoolean) {
/* 185 */     if (compositionArea != null) {
/* 186 */       compositionArea.setCompositionAreaUndecorated(paramBoolean);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   private static final AttributedCharacterIterator.Attribute[] IM_ATTRIBUTES = new AttributedCharacterIterator.Attribute[] { TextAttribute.INPUT_METHOD_HIGHLIGHT };
/*     */ 
/*     */   
/*     */   public void inputMethodTextChanged(InputMethodEvent paramInputMethodEvent) {
/* 198 */     AttributedCharacterIterator attributedCharacterIterator = paramInputMethodEvent.getText();
/* 199 */     int i = paramInputMethodEvent.getCommittedCharacterCount();
/*     */ 
/*     */     
/* 202 */     this.composedText = null;
/* 203 */     this.caret = null;
/* 204 */     if (attributedCharacterIterator != null && i < attributedCharacterIterator
/* 205 */       .getEndIndex() - attributedCharacterIterator.getBeginIndex()) {
/*     */ 
/*     */       
/* 208 */       if (compositionArea == null) {
/* 209 */         createCompositionArea();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 216 */       AttributedString attributedString = new AttributedString(attributedCharacterIterator, attributedCharacterIterator.getBeginIndex() + i, attributedCharacterIterator.getEndIndex(), IM_ATTRIBUTES);
/* 217 */       attributedString.addAttribute(TextAttribute.FONT, compositionArea.getFont());
/* 218 */       this.composedText = attributedString.getIterator();
/* 219 */       this.caret = paramInputMethodEvent.getCaret();
/*     */     } 
/*     */     
/* 222 */     if (compositionArea != null) {
/* 223 */       compositionArea.setText(this.composedText, this.caret);
/*     */     }
/*     */ 
/*     */     
/* 227 */     if (i > 0) {
/* 228 */       this.inputMethodContext.dispatchCommittedText((Component)paramInputMethodEvent.getSource(), attributedCharacterIterator, i);
/*     */ 
/*     */ 
/*     */       
/* 232 */       if (isCompositionAreaVisible()) {
/* 233 */         compositionArea.updateWindowLocation();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 238 */     paramInputMethodEvent.consume();
/*     */   }
/*     */   
/*     */   public void caretPositionChanged(InputMethodEvent paramInputMethodEvent) {
/* 242 */     if (compositionArea != null) {
/* 243 */       compositionArea.setCaret(paramInputMethodEvent.getCaret());
/*     */     }
/*     */ 
/*     */     
/* 247 */     paramInputMethodEvent.consume();
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
/*     */   InputMethodRequests getClientInputMethodRequests() {
/* 261 */     Component component = this.clientComponent.get();
/* 262 */     if (component != null) {
/* 263 */       return component.getInputMethodRequests();
/*     */     }
/*     */     
/* 266 */     return null;
/*     */   }
/*     */   
/*     */   public Rectangle getTextLocation(TextHitInfo paramTextHitInfo) {
/* 270 */     synchronized (compositionAreaLock) {
/* 271 */       if (compositionAreaOwner == this && isCompositionAreaVisible())
/* 272 */         return compositionArea.getTextLocation(paramTextHitInfo); 
/* 273 */       if (this.composedText != null)
/*     */       {
/* 275 */         return new Rectangle(0, 0, 0, 10);
/*     */       }
/* 277 */       InputMethodRequests inputMethodRequests = getClientInputMethodRequests();
/* 278 */       if (inputMethodRequests != null) {
/* 279 */         return inputMethodRequests.getTextLocation(paramTextHitInfo);
/*     */       }
/*     */       
/* 282 */       return new Rectangle(0, 0, 0, 10);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TextHitInfo getLocationOffset(int paramInt1, int paramInt2) {
/* 289 */     synchronized (compositionAreaLock) {
/* 290 */       if (compositionAreaOwner == this && isCompositionAreaVisible()) {
/* 291 */         return compositionArea.getLocationOffset(paramInt1, paramInt2);
/*     */       }
/* 293 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInsertPositionOffset() {
/* 299 */     InputMethodRequests inputMethodRequests = getClientInputMethodRequests();
/* 300 */     if (inputMethodRequests != null) {
/* 301 */       return inputMethodRequests.getInsertPositionOffset();
/*     */     }
/*     */ 
/*     */     
/* 305 */     return 0;
/*     */   }
/*     */   
/* 308 */   private static final AttributedCharacterIterator EMPTY_TEXT = (new AttributedString(""))
/* 309 */     .getIterator();
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributedCharacterIterator getCommittedText(int paramInt1, int paramInt2, AttributedCharacterIterator.Attribute[] paramArrayOfAttribute) {
/* 314 */     InputMethodRequests inputMethodRequests = getClientInputMethodRequests();
/* 315 */     if (inputMethodRequests != null) {
/* 316 */       return inputMethodRequests.getCommittedText(paramInt1, paramInt2, paramArrayOfAttribute);
/*     */     }
/*     */ 
/*     */     
/* 320 */     return EMPTY_TEXT;
/*     */   }
/*     */   
/*     */   public int getCommittedTextLength() {
/* 324 */     InputMethodRequests inputMethodRequests = getClientInputMethodRequests();
/* 325 */     if (inputMethodRequests != null) {
/* 326 */       return inputMethodRequests.getCommittedTextLength();
/*     */     }
/*     */ 
/*     */     
/* 330 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributedCharacterIterator cancelLatestCommittedText(AttributedCharacterIterator.Attribute[] paramArrayOfAttribute) {
/* 335 */     InputMethodRequests inputMethodRequests = getClientInputMethodRequests();
/* 336 */     if (inputMethodRequests != null) {
/* 337 */       return inputMethodRequests.cancelLatestCommittedText(paramArrayOfAttribute);
/*     */     }
/*     */ 
/*     */     
/* 341 */     return null;
/*     */   }
/*     */   
/*     */   public AttributedCharacterIterator getSelectedText(AttributedCharacterIterator.Attribute[] paramArrayOfAttribute) {
/* 345 */     InputMethodRequests inputMethodRequests = getClientInputMethodRequests();
/* 346 */     if (inputMethodRequests != null) {
/* 347 */       return inputMethodRequests.getSelectedText(paramArrayOfAttribute);
/*     */     }
/*     */ 
/*     */     
/* 351 */     return EMPTY_TEXT;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/im/CompositionAreaHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */