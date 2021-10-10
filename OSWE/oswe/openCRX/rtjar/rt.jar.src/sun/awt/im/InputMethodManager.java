/*     */ package sun.awt.im;
/*     */ 
/*     */ import java.awt.Component;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InputMethodManager
/*     */ {
/*     */   private static final String threadName = "AWT-InputMethodManager";
/* 141 */   private static final Object LOCK = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static InputMethodManager inputMethodManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final InputMethodManager getInstance() {
/* 156 */     if (inputMethodManager != null) {
/* 157 */       return inputMethodManager;
/*     */     }
/* 159 */     synchronized (LOCK) {
/* 160 */       if (inputMethodManager == null) {
/* 161 */         ExecutableInputMethodManager executableInputMethodManager = new ExecutableInputMethodManager();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 166 */         if (executableInputMethodManager.hasMultipleInputMethods()) {
/* 167 */           executableInputMethodManager.initialize();
/* 168 */           Thread thread = new Thread(executableInputMethodManager, "AWT-InputMethodManager");
/* 169 */           thread.setDaemon(true);
/* 170 */           thread.setPriority(6);
/* 171 */           thread.start();
/*     */         } 
/* 173 */         inputMethodManager = executableInputMethodManager;
/*     */       } 
/*     */     } 
/* 176 */     return inputMethodManager;
/*     */   }
/*     */   
/*     */   public abstract String getTriggerMenuString();
/*     */   
/*     */   public abstract void notifyChangeRequest(Component paramComponent);
/*     */   
/*     */   public abstract void notifyChangeRequestByHotKey(Component paramComponent);
/*     */   
/*     */   abstract void setInputContext(InputContext paramInputContext);
/*     */   
/*     */   abstract InputMethodLocator findInputMethod(Locale paramLocale);
/*     */   
/*     */   abstract Locale getDefaultKeyboardLocale();
/*     */   
/*     */   abstract boolean hasMultipleInputMethods();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/im/InputMethodManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */