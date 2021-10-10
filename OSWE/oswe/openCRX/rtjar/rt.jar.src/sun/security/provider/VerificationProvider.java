/*    */ package sun.security.provider;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.Provider;
/*    */ import java.util.LinkedHashMap;
/*    */ import sun.security.action.PutAllAction;
/*    */ import sun.security.rsa.SunRsaSignEntries;
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
/*    */ 
/*    */ 
/*    */ public final class VerificationProvider
/*    */   extends Provider
/*    */ {
/*    */   private static final long serialVersionUID = 7482667077568930381L;
/*    */   private static final boolean ACTIVE;
/*    */   
/*    */   static {
/*    */     boolean bool;
/*    */     try {
/* 54 */       Class.forName("sun.security.provider.Sun");
/* 55 */       Class.forName("sun.security.rsa.SunRsaSign");
/* 56 */       bool = false;
/* 57 */     } catch (ClassNotFoundException classNotFoundException) {
/* 58 */       bool = true;
/*    */     } 
/* 60 */     ACTIVE = bool;
/*    */   }
/*    */   
/*    */   public VerificationProvider() {
/* 64 */     super("SunJarVerification", 1.8D, "Jar Verification Provider");
/*    */ 
/*    */     
/* 67 */     if (!ACTIVE) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 74 */     if (System.getSecurityManager() == null) {
/* 75 */       SunEntries.putEntries(this);
/* 76 */       SunRsaSignEntries.putEntries(this);
/*    */     } else {
/*    */       
/* 79 */       LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
/* 80 */       SunEntries.putEntries(linkedHashMap);
/* 81 */       SunRsaSignEntries.putEntries(linkedHashMap);
/* 82 */       AccessController.doPrivileged(new PutAllAction(this, linkedHashMap));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/VerificationProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */