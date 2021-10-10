/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.DefaultFocusTraversalPolicy;
/*     */ import java.awt.DefaultKeyboardFocusManager;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FocusManager
/*     */   extends DefaultKeyboardFocusManager
/*     */ {
/*     */   public static final String FOCUS_MANAGER_CLASS_PROPERTY = "FocusManagerClassName";
/*     */   private static boolean enabled = true;
/*     */   
/*     */   public static FocusManager getCurrentManager() {
/*  75 */     KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/*  76 */     if (keyboardFocusManager instanceof FocusManager) {
/*  77 */       return (FocusManager)keyboardFocusManager;
/*     */     }
/*  79 */     return new DelegatingDefaultFocusManager(keyboardFocusManager);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setCurrentManager(FocusManager paramFocusManager) throws SecurityException {
/* 114 */     KeyboardFocusManager keyboardFocusManager = (paramFocusManager instanceof DelegatingDefaultFocusManager) ? ((DelegatingDefaultFocusManager)paramFocusManager).getDelegate() : paramFocusManager;
/*     */     
/* 116 */     KeyboardFocusManager.setCurrentKeyboardFocusManager(keyboardFocusManager);
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
/*     */   @Deprecated
/*     */   public static void disableSwingFocusManager() {
/* 131 */     if (enabled) {
/* 132 */       enabled = false;
/* 133 */       KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 134 */         .setDefaultFocusTraversalPolicy(new DefaultFocusTraversalPolicy());
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
/*     */   @Deprecated
/*     */   public static boolean isFocusManagerEnabled() {
/* 149 */     return enabled;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/FocusManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */