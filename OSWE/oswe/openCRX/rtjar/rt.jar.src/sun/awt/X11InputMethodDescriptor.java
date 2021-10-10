/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.im.spi.InputMethod;
/*     */ import java.awt.im.spi.InputMethodDescriptor;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class X11InputMethodDescriptor
/*     */   implements InputMethodDescriptor
/*     */ {
/*     */   private static Locale locale;
/*     */   
/*     */   public X11InputMethodDescriptor() {
/*  51 */     locale = getSupportedLocale();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locale[] getAvailableLocales() {
/*  58 */     return new Locale[] { locale };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDynamicLocaleList() {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String getInputMethodDisplayName(Locale paramLocale1, Locale paramLocale2) {
/*  76 */     String str = "System Input Methods";
/*  77 */     if (Locale.getDefault().equals(paramLocale2)) {
/*  78 */       str = Toolkit.getProperty("AWT.HostInputMethodDisplayName", str);
/*     */     }
/*  80 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getInputMethodIcon(Locale paramLocale) {
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract InputMethod createInputMethod() throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Locale getSupportedLocale() {
/* 100 */     return SunToolkit.getStartupLocale();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11InputMethodDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */