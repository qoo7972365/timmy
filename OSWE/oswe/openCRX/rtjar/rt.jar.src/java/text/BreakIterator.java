/*     */ package java.text;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.text.spi.BreakIteratorProvider;
/*     */ import java.util.Locale;
/*     */ import sun.util.locale.provider.LocaleProviderAdapter;
/*     */ import sun.util.locale.provider.LocaleServiceProviderPool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BreakIterator
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int DONE = -1;
/*     */   private static final int CHARACTER_INDEX = 0;
/*     */   private static final int WORD_INDEX = 1;
/*     */   private static final int LINE_INDEX = 2;
/*     */   private static final int SENTENCE_INDEX = 3;
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 245 */       return super.clone();
/*     */     }
/* 247 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/* 248 */       throw new InternalError(cloneNotSupportedException);
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
/*     */   public abstract int first();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int last();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int next(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int next();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int previous();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int following(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int preceding(int paramInt) {
/* 353 */     int i = following(paramInt);
/* 354 */     while (i >= paramInt && i != -1) {
/* 355 */       i = previous();
/*     */     }
/* 357 */     return i;
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
/*     */   public boolean isBoundary(int paramInt) {
/* 378 */     if (paramInt == 0) {
/* 379 */       return true;
/*     */     }
/* 381 */     int i = following(paramInt - 1);
/* 382 */     if (i == -1) {
/* 383 */       throw new IllegalArgumentException();
/*     */     }
/* 385 */     return (i == paramInt);
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
/*     */   public abstract int current();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract CharacterIterator getText();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String paramString) {
/* 420 */     setText(new StringCharacterIterator(paramString));
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
/* 436 */   private static final SoftReference<BreakIteratorCache>[] iterCache = (SoftReference<BreakIteratorCache>[])new SoftReference[4];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setText(CharacterIterator paramCharacterIterator);
/*     */ 
/*     */ 
/*     */   
/*     */   public static BreakIterator getWordInstance() {
/* 446 */     return getWordInstance(Locale.getDefault());
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
/*     */   public static BreakIterator getWordInstance(Locale paramLocale) {
/* 459 */     return getBreakInstance(paramLocale, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BreakIterator getLineInstance() {
/* 470 */     return getLineInstance(Locale.getDefault());
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
/*     */   public static BreakIterator getLineInstance(Locale paramLocale) {
/* 483 */     return getBreakInstance(paramLocale, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BreakIterator getCharacterInstance() {
/* 494 */     return getCharacterInstance(Locale.getDefault());
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
/*     */   public static BreakIterator getCharacterInstance(Locale paramLocale) {
/* 507 */     return getBreakInstance(paramLocale, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BreakIterator getSentenceInstance() {
/* 518 */     return getSentenceInstance(Locale.getDefault());
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
/*     */   public static BreakIterator getSentenceInstance(Locale paramLocale) {
/* 531 */     return getBreakInstance(paramLocale, 3);
/*     */   }
/*     */   
/*     */   private static BreakIterator getBreakInstance(Locale paramLocale, int paramInt) {
/* 535 */     if (iterCache[paramInt] != null) {
/* 536 */       BreakIteratorCache breakIteratorCache1 = iterCache[paramInt].get();
/* 537 */       if (breakIteratorCache1 != null && 
/* 538 */         breakIteratorCache1.getLocale().equals(paramLocale)) {
/* 539 */         return breakIteratorCache1.createBreakInstance();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 544 */     BreakIterator breakIterator = createBreakInstance(paramLocale, paramInt);
/* 545 */     BreakIteratorCache breakIteratorCache = new BreakIteratorCache(paramLocale, breakIterator);
/* 546 */     iterCache[paramInt] = new SoftReference<>(breakIteratorCache);
/* 547 */     return breakIterator;
/*     */   }
/*     */ 
/*     */   
/*     */   private static BreakIterator createBreakInstance(Locale paramLocale, int paramInt) {
/* 552 */     LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.getAdapter((Class)BreakIteratorProvider.class, paramLocale);
/* 553 */     BreakIterator breakIterator = createBreakInstance(localeProviderAdapter, paramLocale, paramInt);
/* 554 */     if (breakIterator == null) {
/* 555 */       breakIterator = createBreakInstance(LocaleProviderAdapter.forJRE(), paramLocale, paramInt);
/*     */     }
/* 557 */     return breakIterator;
/*     */   }
/*     */   
/*     */   private static BreakIterator createBreakInstance(LocaleProviderAdapter paramLocaleProviderAdapter, Locale paramLocale, int paramInt) {
/* 561 */     BreakIteratorProvider breakIteratorProvider = paramLocaleProviderAdapter.getBreakIteratorProvider();
/* 562 */     BreakIterator breakIterator = null;
/* 563 */     switch (paramInt) {
/*     */       case 0:
/* 565 */         breakIterator = breakIteratorProvider.getCharacterInstance(paramLocale);
/*     */         break;
/*     */       case 1:
/* 568 */         breakIterator = breakIteratorProvider.getWordInstance(paramLocale);
/*     */         break;
/*     */       case 2:
/* 571 */         breakIterator = breakIteratorProvider.getLineInstance(paramLocale);
/*     */         break;
/*     */       case 3:
/* 574 */         breakIterator = breakIteratorProvider.getSentenceInstance(paramLocale);
/*     */         break;
/*     */     } 
/* 577 */     return breakIterator;
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
/*     */   public static synchronized Locale[] getAvailableLocales() {
/* 596 */     LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)BreakIteratorProvider.class);
/* 597 */     return localeServiceProviderPool.getAvailableLocales();
/*     */   }
/*     */   
/*     */   private static final class BreakIteratorCache
/*     */   {
/*     */     private BreakIterator iter;
/*     */     private Locale locale;
/*     */     
/*     */     BreakIteratorCache(Locale param1Locale, BreakIterator param1BreakIterator) {
/* 606 */       this.locale = param1Locale;
/* 607 */       this.iter = (BreakIterator)param1BreakIterator.clone();
/*     */     }
/*     */     
/*     */     Locale getLocale() {
/* 611 */       return this.locale;
/*     */     }
/*     */     
/*     */     BreakIterator createBreakInstance() {
/* 615 */       return (BreakIterator)this.iter.clone();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/BreakIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */