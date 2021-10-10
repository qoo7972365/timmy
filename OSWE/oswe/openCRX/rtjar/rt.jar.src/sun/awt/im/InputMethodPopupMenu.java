/*     */ package sun.awt.im;
/*     */ 
/*     */ import java.awt.AWTException;
/*     */ import java.awt.Component;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class InputMethodPopupMenu
/*     */   implements ActionListener
/*     */ {
/*     */   static InputMethodPopupMenu getInstance(Component paramComponent, String paramString) {
/*  58 */     if (paramComponent instanceof javax.swing.JFrame || paramComponent instanceof javax.swing.JDialog)
/*     */     {
/*  60 */       return new JInputMethodPopupMenu(paramString);
/*     */     }
/*  62 */     return new AWTInputMethodPopupMenu(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   abstract void show(Component paramComponent, int paramInt1, int paramInt2);
/*     */   
/*     */   abstract void removeAll();
/*     */   
/*     */   abstract void addSeparator();
/*     */   
/*     */   abstract void addToComponent(Component paramComponent);
/*     */   
/*     */   abstract Object createSubmenu(String paramString);
/*     */   
/*     */   abstract void add(Object paramObject);
/*     */   
/*     */   abstract void addMenuItem(String paramString1, String paramString2, String paramString3);
/*     */   
/*     */   abstract void addMenuItem(Object paramObject, String paramString1, String paramString2, String paramString3);
/*     */   
/*     */   void addOneInputMethodToMenu(InputMethodLocator paramInputMethodLocator, String paramString) {
/*     */     byte b;
/*  84 */     InputMethodDescriptor inputMethodDescriptor = paramInputMethodLocator.getDescriptor();
/*  85 */     String str1 = inputMethodDescriptor.getInputMethodDisplayName(null, Locale.getDefault());
/*  86 */     String str2 = paramInputMethodLocator.getActionCommandString();
/*  87 */     Locale[] arrayOfLocale = null;
/*     */     
/*     */     try {
/*  90 */       arrayOfLocale = inputMethodDescriptor.getAvailableLocales();
/*  91 */       b = arrayOfLocale.length;
/*  92 */     } catch (AWTException aWTException) {
/*     */ 
/*     */ 
/*     */       
/*  96 */       b = 0;
/*     */     } 
/*  98 */     if (!b) {
/*     */       
/* 100 */       addMenuItem(str1, null, paramString);
/* 101 */     } else if (b == 1) {
/* 102 */       if (inputMethodDescriptor.hasDynamicLocaleList()) {
/*     */ 
/*     */ 
/*     */         
/* 106 */         str1 = inputMethodDescriptor.getInputMethodDisplayName(arrayOfLocale[0], Locale.getDefault());
/* 107 */         str2 = paramInputMethodLocator.deriveLocator(arrayOfLocale[0]).getActionCommandString();
/*     */       } 
/* 109 */       addMenuItem(str1, str2, paramString);
/*     */     } else {
/* 111 */       Object object = createSubmenu(str1);
/* 112 */       add(object);
/* 113 */       for (byte b1 = 0; b1 < b; b1++) {
/* 114 */         Locale locale = arrayOfLocale[b1];
/* 115 */         String str3 = getLocaleName(locale);
/* 116 */         String str4 = paramInputMethodLocator.deriveLocator(locale).getActionCommandString();
/* 117 */         addMenuItem(object, str3, str4, paramString);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isSelected(String paramString1, String paramString2) {
/* 127 */     if (paramString1 == null || paramString2 == null) {
/* 128 */       return false;
/*     */     }
/* 130 */     if (paramString1.equals(paramString2)) {
/* 131 */       return true;
/*     */     }
/*     */     
/* 134 */     int i = paramString2.indexOf('\n');
/* 135 */     if (i != -1 && paramString2.substring(0, i).equals(paramString1)) {
/* 136 */       return true;
/*     */     }
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getLocaleName(Locale paramLocale) {
/* 149 */     String str1 = paramLocale.toString();
/* 150 */     String str2 = Toolkit.getProperty("AWT.InputMethodLanguage." + str1, null);
/* 151 */     if (str2 == null) {
/* 152 */       str2 = paramLocale.getDisplayName();
/* 153 */       if (str2 == null || str2.length() == 0)
/* 154 */         str2 = str1; 
/*     */     } 
/* 156 */     return str2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent paramActionEvent) {
/* 161 */     String str = paramActionEvent.getActionCommand();
/* 162 */     ((ExecutableInputMethodManager)InputMethodManager.getInstance()).changeInputMethod(str);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/im/InputMethodPopupMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */