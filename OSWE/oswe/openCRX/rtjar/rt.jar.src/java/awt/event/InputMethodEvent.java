/*     */ package java.awt.event;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.font.TextHitInfo;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.SunToolkit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputMethodEvent
/*     */   extends AWTEvent
/*     */ {
/*     */   private static final long serialVersionUID = 4727190874778922661L;
/*     */   public static final int INPUT_METHOD_FIRST = 1100;
/*     */   public static final int INPUT_METHOD_TEXT_CHANGED = 1100;
/*     */   public static final int CARET_POSITION_CHANGED = 1101;
/*     */   public static final int INPUT_METHOD_LAST = 1101;
/*     */   long when;
/*     */   private transient AttributedCharacterIterator text;
/*     */   private transient int committedCharacterCount;
/*     */   private transient TextHitInfo caret;
/*     */   private transient TextHitInfo visiblePosition;
/*     */   
/*     */   public InputMethodEvent(Component paramComponent, int paramInt1, long paramLong, AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt2, TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2) {
/* 154 */     super(paramComponent, paramInt1);
/* 155 */     if (paramInt1 < 1100 || paramInt1 > 1101) {
/* 156 */       throw new IllegalArgumentException("id outside of valid range");
/*     */     }
/*     */     
/* 159 */     if (paramInt1 == 1101 && paramAttributedCharacterIterator != null) {
/* 160 */       throw new IllegalArgumentException("text must be null for CARET_POSITION_CHANGED");
/*     */     }
/*     */     
/* 163 */     this.when = paramLong;
/* 164 */     this.text = paramAttributedCharacterIterator;
/* 165 */     int i = 0;
/* 166 */     if (paramAttributedCharacterIterator != null) {
/* 167 */       i = paramAttributedCharacterIterator.getEndIndex() - paramAttributedCharacterIterator.getBeginIndex();
/*     */     }
/*     */     
/* 170 */     if (paramInt2 < 0 || paramInt2 > i) {
/* 171 */       throw new IllegalArgumentException("committedCharacterCount outside of valid range");
/*     */     }
/* 173 */     this.committedCharacterCount = paramInt2;
/*     */     
/* 175 */     this.caret = paramTextHitInfo1;
/* 176 */     this.visiblePosition = paramTextHitInfo2;
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
/*     */   public InputMethodEvent(Component paramComponent, int paramInt1, AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt2, TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2) {
/* 224 */     this(paramComponent, paramInt1, 
/* 225 */         getMostRecentEventTimeForSource(paramComponent), paramAttributedCharacterIterator, paramInt2, paramTextHitInfo1, paramTextHitInfo2);
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
/*     */   public InputMethodEvent(Component paramComponent, int paramInt, TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2) {
/* 267 */     this(paramComponent, paramInt, 
/* 268 */         getMostRecentEventTimeForSource(paramComponent), (AttributedCharacterIterator)null, 0, paramTextHitInfo1, paramTextHitInfo2);
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
/*     */   public AttributedCharacterIterator getText() {
/* 282 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCommittedCharacterCount() {
/* 289 */     return this.committedCharacterCount;
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
/*     */   public TextHitInfo getCaret() {
/* 305 */     return this.caret;
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
/*     */   public TextHitInfo getVisiblePosition() {
/* 321 */     return this.visiblePosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void consume() {
/* 329 */     this.consumed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConsumed() {
/* 337 */     return this.consumed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWhen() {
/* 347 */     return this.when;
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
/*     */   public String paramString() {
/*     */     String str1, str2, str4, str5;
/* 362 */     switch (this.id) {
/*     */       case 1100:
/* 364 */         str1 = "INPUT_METHOD_TEXT_CHANGED";
/*     */         break;
/*     */       case 1101:
/* 367 */         str1 = "CARET_POSITION_CHANGED";
/*     */         break;
/*     */       default:
/* 370 */         str1 = "unknown type";
/*     */         break;
/*     */     } 
/*     */     
/* 374 */     if (this.text == null) {
/* 375 */       str2 = "no text";
/*     */     } else {
/* 377 */       StringBuilder stringBuilder = new StringBuilder("\"");
/* 378 */       int i = this.committedCharacterCount;
/* 379 */       char c = this.text.first();
/* 380 */       while (i-- > 0) {
/* 381 */         stringBuilder.append(c);
/* 382 */         c = this.text.next();
/*     */       } 
/* 384 */       stringBuilder.append("\" + \"");
/* 385 */       while (c != Character.MAX_VALUE) {
/* 386 */         stringBuilder.append(c);
/* 387 */         c = this.text.next();
/*     */       } 
/* 389 */       stringBuilder.append("\"");
/* 390 */       str2 = stringBuilder.toString();
/*     */     } 
/*     */     
/* 393 */     String str3 = this.committedCharacterCount + " characters committed";
/*     */ 
/*     */     
/* 396 */     if (this.caret == null) {
/* 397 */       str4 = "no caret";
/*     */     } else {
/* 399 */       str4 = "caret: " + this.caret.toString();
/*     */     } 
/*     */ 
/*     */     
/* 403 */     if (this.visiblePosition == null) {
/* 404 */       str5 = "no visible position";
/*     */     } else {
/* 406 */       str5 = "visible position: " + this.visiblePosition.toString();
/*     */     } 
/*     */     
/* 409 */     return str1 + ", " + str2 + ", " + str3 + ", " + str4 + ", " + str5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 418 */     paramObjectInputStream.defaultReadObject();
/* 419 */     if (this.when == 0L)
/*     */     {
/* 421 */       this.when = EventQueue.getMostRecentEventTime();
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
/*     */   private static long getMostRecentEventTimeForSource(Object paramObject) {
/* 434 */     if (paramObject == null)
/*     */     {
/* 436 */       throw new IllegalArgumentException("null source");
/*     */     }
/* 438 */     AppContext appContext = SunToolkit.targetToAppContext(paramObject);
/* 439 */     EventQueue eventQueue = SunToolkit.getSystemEventQueueImplPP(appContext);
/* 440 */     return AWTAccessor.getEventQueueAccessor().getMostRecentEventTime(eventQueue);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/event/InputMethodEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */