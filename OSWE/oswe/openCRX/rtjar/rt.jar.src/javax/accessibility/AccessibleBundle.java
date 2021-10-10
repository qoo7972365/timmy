/*     */ package javax.accessibility;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
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
/*     */ public abstract class AccessibleBundle
/*     */ {
/*  52 */   private static Hashtable table = new Hashtable<>();
/*  53 */   private final String defaultResourceBundleName = "com.sun.accessibility.internal.resources.accessibility";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String key;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibleBundle() {
/*  67 */     this.key = null;
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
/*     */   protected String toDisplayString(String paramString, Locale paramLocale) {
/*  85 */     loadResourceBundle(paramString, paramLocale);
/*     */ 
/*     */     
/*  88 */     Object object = table.get(paramLocale);
/*  89 */     if (object != null && object instanceof Hashtable) {
/*  90 */       Hashtable hashtable = (Hashtable)object;
/*  91 */       object = hashtable.get(this.key);
/*     */       
/*  93 */       if (object != null && object instanceof String) {
/*  94 */         return (String)object;
/*     */       }
/*     */     } 
/*  97 */     return this.key;
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
/*     */   public String toDisplayString(Locale paramLocale) {
/* 109 */     return toDisplayString("com.sun.accessibility.internal.resources.accessibility", paramLocale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toDisplayString() {
/* 117 */     return toDisplayString(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 126 */     return toDisplayString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadResourceBundle(String paramString, Locale paramLocale) {
/* 134 */     if (!table.contains(paramLocale))
/*     */       
/*     */       try {
/* 137 */         Hashtable<Object, Object> hashtable = new Hashtable<>();
/*     */         
/* 139 */         ResourceBundle resourceBundle = ResourceBundle.getBundle(paramString, paramLocale);
/*     */         
/* 141 */         Enumeration<String> enumeration = resourceBundle.getKeys();
/* 142 */         while (enumeration.hasMoreElements()) {
/* 143 */           String str = enumeration.nextElement();
/* 144 */           hashtable.put(str, resourceBundle.getObject(str));
/*     */         } 
/*     */         
/* 147 */         table.put(paramLocale, hashtable);
/*     */       }
/* 149 */       catch (MissingResourceException missingResourceException) {
/* 150 */         System.err.println("loadResourceBundle: " + missingResourceException);
/*     */         return;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/accessibility/AccessibleBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */