/*    */ package sun.util.locale.provider;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Locale;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FallbackLocaleProviderAdapter
/*    */   extends JRELocaleProviderAdapter
/*    */ {
/* 44 */   private static final Set<String> rootTagSet = Collections.singleton(Locale.ROOT.toLanguageTag());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   private final LocaleResources rootLocaleResources = new LocaleResources(this, Locale.ROOT);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LocaleProviderAdapter.Type getAdapterType() {
/* 57 */     return LocaleProviderAdapter.Type.FALLBACK;
/*    */   }
/*    */ 
/*    */   
/*    */   public LocaleResources getLocaleResources(Locale paramLocale) {
/* 62 */     return this.rootLocaleResources;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Set<String> createLanguageTagSet(String paramString) {
/* 67 */     return rootTagSet;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/FallbackLocaleProviderAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */