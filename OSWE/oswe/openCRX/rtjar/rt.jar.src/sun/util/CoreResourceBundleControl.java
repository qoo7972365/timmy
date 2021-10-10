/*     */ package sun.util;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoreResourceBundleControl
/*     */   extends ResourceBundle.Control
/*     */ {
/*  58 */   private static CoreResourceBundleControl resourceBundleControlInstance = new CoreResourceBundleControl();
/*     */ 
/*     */ 
/*     */   
/*  62 */   private final Collection<Locale> excludedJDKLocales = Arrays.asList(new Locale[] { Locale.GERMANY, Locale.ENGLISH, Locale.US, new Locale("es", "ES"), Locale.FRANCE, Locale.ITALY, Locale.JAPAN, Locale.KOREA, new Locale("sv", "SE"), Locale.CHINESE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CoreResourceBundleControl getRBControlInstance() {
/*  72 */     return resourceBundleControlInstance;
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
/*     */   public static CoreResourceBundleControl getRBControlInstance(String paramString) {
/*  84 */     if (paramString.startsWith("com.sun.") || paramString
/*  85 */       .startsWith("java.") || paramString
/*  86 */       .startsWith("javax.") || paramString
/*  87 */       .startsWith("sun.")) {
/*  88 */       return resourceBundleControlInstance;
/*     */     }
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Locale> getCandidateLocales(String paramString, Locale paramLocale) {
/* 100 */     List<Locale> list = super.getCandidateLocales(paramString, paramLocale);
/* 101 */     list.removeAll(this.excludedJDKLocales);
/* 102 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTimeToLive(String paramString, Locale paramLocale) {
/* 112 */     return -1L;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/CoreResourceBundleControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */