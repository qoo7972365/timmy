/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.BreakIterator;
/*     */ import java.text.spi.BreakIteratorProvider;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BreakIteratorProviderImpl
/*     */   extends BreakIteratorProvider
/*     */   implements AvailableLanguageTags
/*     */ {
/*     */   private static final int CHARACTER_INDEX = 0;
/*     */   private static final int WORD_INDEX = 1;
/*     */   private static final int LINE_INDEX = 2;
/*     */   private static final int SENTENCE_INDEX = 3;
/*     */   private final LocaleProviderAdapter.Type type;
/*     */   private final Set<String> langtags;
/*     */   
/*     */   public BreakIteratorProviderImpl(LocaleProviderAdapter.Type paramType, Set<String> paramSet) {
/*  54 */     this.type = paramType;
/*  55 */     this.langtags = paramSet;
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
/*     */   public Locale[] getAvailableLocales() {
/*  67 */     return LocaleProviderAdapter.toLocaleArray(this.langtags);
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
/*     */   public BreakIterator getWordInstance(Locale paramLocale) {
/*  85 */     return getBreakInstance(paramLocale, 1, "WordData", "WordDictionary");
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
/*     */   public BreakIterator getLineInstance(Locale paramLocale) {
/* 106 */     return getBreakInstance(paramLocale, 2, "LineData", "LineDictionary");
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
/*     */   public BreakIterator getCharacterInstance(Locale paramLocale) {
/* 127 */     return getBreakInstance(paramLocale, 0, "CharacterData", "CharacterDictionary");
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
/*     */   public BreakIterator getSentenceInstance(Locale paramLocale) {
/* 148 */     return getBreakInstance(paramLocale, 3, "SentenceData", "SentenceDictionary");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BreakIterator getBreakInstance(Locale paramLocale, int paramInt, String paramString1, String paramString2) {
/* 158 */     if (paramLocale == null) {
/* 159 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 162 */     LocaleResources localeResources = LocaleProviderAdapter.forJRE().getLocaleResources(paramLocale);
/* 163 */     String[] arrayOfString = (String[])localeResources.getBreakIteratorInfo("BreakIteratorClasses");
/* 164 */     String str = (String)localeResources.getBreakIteratorInfo(paramString1);
/*     */     try {
/*     */       String str1;
/* 167 */       switch (arrayOfString[paramInt]) {
/*     */         case "RuleBasedBreakIterator":
/* 169 */           return new RuleBasedBreakIterator(str);
/*     */         case "DictionaryBasedBreakIterator":
/* 171 */           str1 = (String)localeResources.getBreakIteratorInfo(paramString2);
/* 172 */           return new DictionaryBasedBreakIterator(str, str1);
/*     */       } 
/* 174 */       throw new IllegalArgumentException("Invalid break iterator class \"" + arrayOfString[paramInt] + "\"");
/*     */     
/*     */     }
/* 177 */     catch (IOException|java.util.MissingResourceException|IllegalArgumentException iOException) {
/* 178 */       throw new InternalError(iOException.toString(), iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getAvailableLanguageTags() {
/* 184 */     return this.langtags;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportedLocale(Locale paramLocale) {
/* 189 */     return LocaleProviderAdapter.isSupportedLocale(paramLocale, this.type, this.langtags);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/BreakIteratorProviderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */