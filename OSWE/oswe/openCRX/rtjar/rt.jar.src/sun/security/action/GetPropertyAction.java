/*     */ package sun.security.action;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GetPropertyAction
/*     */   implements PrivilegedAction<String>
/*     */ {
/*     */   private String theProp;
/*     */   private String defaultVal;
/*     */   
/*     */   public GetPropertyAction(String paramString) {
/*  63 */     this.theProp = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GetPropertyAction(String paramString1, String paramString2) {
/*  74 */     this.theProp = paramString1;
/*  75 */     this.defaultVal = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String run() {
/*  86 */     String str = System.getProperty(this.theProp);
/*  87 */     return (str == null) ? this.defaultVal : str;
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
/*     */   public static String privilegedGetProperty(String paramString) {
/* 104 */     if (System.getSecurityManager() == null) {
/* 105 */       return System.getProperty(paramString);
/*     */     }
/* 107 */     return AccessController.<String>doPrivileged(new GetPropertyAction(paramString));
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
/*     */   public static String privilegedGetProperty(String paramString1, String paramString2) {
/* 127 */     if (System.getSecurityManager() == null) {
/* 128 */       return System.getProperty(paramString1, paramString2);
/*     */     }
/* 130 */     return AccessController.<String>doPrivileged(new GetPropertyAction(paramString1, paramString2));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/action/GetPropertyAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */