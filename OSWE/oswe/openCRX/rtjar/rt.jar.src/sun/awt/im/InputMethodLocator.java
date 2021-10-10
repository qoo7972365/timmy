/*     */ package sun.awt.im;
/*     */ 
/*     */ import java.awt.AWTException;
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
/*     */ final class InputMethodLocator
/*     */ {
/*     */   private InputMethodDescriptor descriptor;
/*     */   private ClassLoader loader;
/*     */   private Locale locale;
/*     */   
/*     */   InputMethodLocator(InputMethodDescriptor paramInputMethodDescriptor, ClassLoader paramClassLoader, Locale paramLocale) {
/*  49 */     if (paramInputMethodDescriptor == null) {
/*  50 */       throw new NullPointerException("descriptor can't be null");
/*     */     }
/*  52 */     this.descriptor = paramInputMethodDescriptor;
/*  53 */     this.loader = paramClassLoader;
/*  54 */     this.locale = paramLocale;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  58 */     if (paramObject == this) {
/*  59 */       return true;
/*     */     }
/*  61 */     if (paramObject == null || getClass() != paramObject.getClass()) {
/*  62 */       return false;
/*     */     }
/*     */     
/*  65 */     InputMethodLocator inputMethodLocator = (InputMethodLocator)paramObject;
/*  66 */     if (!this.descriptor.getClass().equals(inputMethodLocator.descriptor.getClass())) {
/*  67 */       return false;
/*     */     }
/*  69 */     if ((this.loader == null && inputMethodLocator.loader != null) || (this.loader != null && 
/*  70 */       !this.loader.equals(inputMethodLocator.loader))) {
/*  71 */       return false;
/*     */     }
/*  73 */     if ((this.locale == null && inputMethodLocator.locale != null) || (this.locale != null && 
/*  74 */       !this.locale.equals(inputMethodLocator.locale))) {
/*  75 */       return false;
/*     */     }
/*  77 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  81 */     int i = this.descriptor.hashCode();
/*  82 */     if (this.loader != null) {
/*  83 */       i |= this.loader.hashCode() << 10;
/*     */     }
/*  85 */     if (this.locale != null) {
/*  86 */       i |= this.locale.hashCode() << 20;
/*     */     }
/*  88 */     return i;
/*     */   }
/*     */   
/*     */   InputMethodDescriptor getDescriptor() {
/*  92 */     return this.descriptor;
/*     */   }
/*     */   
/*     */   ClassLoader getClassLoader() {
/*  96 */     return this.loader;
/*     */   }
/*     */   
/*     */   Locale getLocale() {
/* 100 */     return this.locale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isLocaleAvailable(Locale paramLocale) {
/*     */     try {
/* 109 */       Locale[] arrayOfLocale = this.descriptor.getAvailableLocales();
/* 110 */       for (byte b = 0; b < arrayOfLocale.length; b++) {
/* 111 */         if (arrayOfLocale[b].equals(paramLocale)) {
/* 112 */           return true;
/*     */         }
/*     */       } 
/* 115 */     } catch (AWTException aWTException) {}
/*     */ 
/*     */     
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InputMethodLocator deriveLocator(Locale paramLocale) {
/* 128 */     if (paramLocale == this.locale) {
/* 129 */       return this;
/*     */     }
/* 131 */     return new InputMethodLocator(this.descriptor, this.loader, paramLocale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean sameInputMethod(InputMethodLocator paramInputMethodLocator) {
/* 140 */     if (paramInputMethodLocator == this) {
/* 141 */       return true;
/*     */     }
/* 143 */     if (paramInputMethodLocator == null) {
/* 144 */       return false;
/*     */     }
/*     */     
/* 147 */     if (!this.descriptor.getClass().equals(paramInputMethodLocator.descriptor.getClass())) {
/* 148 */       return false;
/*     */     }
/* 150 */     if ((this.loader == null && paramInputMethodLocator.loader != null) || (this.loader != null && 
/* 151 */       !this.loader.equals(paramInputMethodLocator.loader))) {
/* 152 */       return false;
/*     */     }
/* 154 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getActionCommandString() {
/* 164 */     String str = this.descriptor.getClass().getName();
/* 165 */     if (this.locale == null) {
/* 166 */       return str;
/*     */     }
/* 168 */     return str + "\n" + this.locale.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/im/InputMethodLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */