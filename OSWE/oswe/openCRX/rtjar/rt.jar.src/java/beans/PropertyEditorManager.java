/*     */ package java.beans;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertyEditorManager
/*     */ {
/*     */   public static void registerEditor(Class<?> paramClass1, Class<?> paramClass2) {
/*  75 */     SecurityManager securityManager = System.getSecurityManager();
/*  76 */     if (securityManager != null) {
/*  77 */       securityManager.checkPropertiesAccess();
/*     */     }
/*  79 */     ThreadGroupContext.getContext().getPropertyEditorFinder().register(paramClass1, paramClass2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PropertyEditor findEditor(Class<?> paramClass) {
/*  90 */     return ThreadGroupContext.getContext().getPropertyEditorFinder().find(paramClass);
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
/*     */   public static String[] getEditorSearchPath() {
/* 102 */     return ThreadGroupContext.getContext().getPropertyEditorFinder().getPackages();
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
/*     */   public static void setEditorSearchPath(String[] paramArrayOfString) {
/* 119 */     SecurityManager securityManager = System.getSecurityManager();
/* 120 */     if (securityManager != null) {
/* 121 */       securityManager.checkPropertiesAccess();
/*     */     }
/* 123 */     ThreadGroupContext.getContext().getPropertyEditorFinder().setPackages(paramArrayOfString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/PropertyEditorManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */