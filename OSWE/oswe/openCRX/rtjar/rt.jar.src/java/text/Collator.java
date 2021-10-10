/*     */ package java.text;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.text.spi.CollatorProvider;
/*     */ import java.util.Comparator;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
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
/*     */ public abstract class Collator
/*     */   implements Comparator<Object>, Cloneable
/*     */ {
/*     */   public static final int PRIMARY = 0;
/*     */   public static final int SECONDARY = 1;
/*     */   public static final int TERTIARY = 2;
/*     */   public static final int IDENTICAL = 3;
/*     */   public static final int NO_DECOMPOSITION = 0;
/*     */   public static final int CANONICAL_DECOMPOSITION = 1;
/*     */   public static final int FULL_DECOMPOSITION = 2;
/*     */   private int strength;
/*     */   private int decmp;
/*     */   
/*     */   public static synchronized Collator getInstance() {
/* 224 */     return getInstance(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Collator getInstance(Locale paramLocale) {
/* 235 */     SoftReference<Collator> softReference = cache.get(paramLocale);
/* 236 */     Collator collator = (softReference != null) ? softReference.get() : null;
/* 237 */     if (collator == null) {
/*     */       
/* 239 */       LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.getAdapter((Class)CollatorProvider.class, paramLocale);
/*     */       
/* 241 */       CollatorProvider collatorProvider = localeProviderAdapter.getCollatorProvider();
/* 242 */       collator = collatorProvider.getInstance(paramLocale);
/* 243 */       if (collator == null)
/*     */       {
/* 245 */         collator = LocaleProviderAdapter.forJRE().getCollatorProvider().getInstance(paramLocale);
/*     */       }
/*     */       while (true) {
/* 248 */         if (softReference != null)
/*     */         {
/* 250 */           cache.remove(paramLocale, softReference);
/*     */         }
/* 252 */         softReference = cache.putIfAbsent(paramLocale, new SoftReference<>(collator));
/* 253 */         if (softReference == null) {
/*     */           break;
/*     */         }
/* 256 */         Collator collator1 = softReference.get();
/* 257 */         if (collator1 != null) {
/* 258 */           collator = collator1;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 263 */     return (Collator)collator.clone();
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
/*     */   public abstract int compare(String paramString1, String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compare(Object paramObject1, Object paramObject2) {
/* 304 */     return compare((String)paramObject1, (String)paramObject2);
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
/*     */   public abstract CollationKey getCollationKey(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(String paramString1, String paramString2) {
/* 331 */     return (compare(paramString1, paramString2) == 0);
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
/*     */   public synchronized int getStrength() {
/* 347 */     return this.strength;
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
/*     */   public synchronized void setStrength(int paramInt) {
/* 364 */     if (paramInt != 0 && paramInt != 1 && paramInt != 2 && paramInt != 3)
/*     */     {
/*     */ 
/*     */       
/* 368 */       throw new IllegalArgumentException("Incorrect comparison level.");
/*     */     }
/* 370 */     this.strength = paramInt;
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
/*     */   public synchronized int getDecomposition() {
/* 394 */     return this.decmp;
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
/*     */   public synchronized void setDecomposition(int paramInt) {
/* 408 */     if (paramInt != 0 && paramInt != 1 && paramInt != 2)
/*     */     {
/*     */       
/* 411 */       throw new IllegalArgumentException("Wrong decomposition mode.");
/*     */     }
/* 413 */     this.decmp = paramInt;
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
/*     */   public static synchronized Locale[] getAvailableLocales() {
/* 431 */     LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)CollatorProvider.class);
/* 432 */     return localeServiceProviderPool.getAvailableLocales();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 442 */       return super.clone();
/* 443 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 444 */       throw new InternalError(cloneNotSupportedException);
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
/*     */   public boolean equals(Object paramObject) {
/* 457 */     if (this == paramObject) {
/* 458 */       return true;
/*     */     }
/* 460 */     if (paramObject == null) {
/* 461 */       return false;
/*     */     }
/* 463 */     if (getClass() != paramObject.getClass()) {
/* 464 */       return false;
/*     */     }
/* 466 */     Collator collator = (Collator)paramObject;
/* 467 */     return (this.strength == collator.strength && this.decmp == collator.decmp);
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
/*     */   public abstract int hashCode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Collator() {
/* 489 */     this.strength = 0;
/* 490 */     this.decmp = 0; this.strength = 2;
/* 491 */     this.decmp = 1; } private static final ConcurrentMap<Locale, SoftReference<Collator>> cache = new ConcurrentHashMap<>();
/*     */   static final int LESS = -1;
/*     */   static final int EQUAL = 0;
/*     */   static final int GREATER = 1;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/Collator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */