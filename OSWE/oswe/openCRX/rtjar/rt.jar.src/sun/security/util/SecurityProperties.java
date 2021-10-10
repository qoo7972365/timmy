/*    */ package sun.security.util;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.Security;
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
/*    */ public class SecurityProperties
/*    */ {
/*    */   public static String privilegedGetOverridable(String paramString) {
/* 43 */     if (System.getSecurityManager() == null) {
/* 44 */       return getOverridableProperty(paramString);
/*    */     }
/* 46 */     return AccessController.<String>doPrivileged(() -> getOverridableProperty(paramString));
/*    */   }
/*    */ 
/*    */   
/*    */   private static String getOverridableProperty(String paramString) {
/* 51 */     String str = System.getProperty(paramString);
/* 52 */     if (str == null) {
/* 53 */       return Security.getProperty(paramString);
/*    */     }
/* 55 */     return str;
/*    */   }
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
/*    */   public static boolean includedInExceptions(String paramString) {
/* 68 */     String str = privilegedGetOverridable("jdk.includeInExceptions");
/* 69 */     if (str == null) {
/* 70 */       return false;
/*    */     }
/*    */     
/* 73 */     String[] arrayOfString = str.split(",");
/* 74 */     for (String str1 : arrayOfString) {
/* 75 */       str1 = str1.trim();
/* 76 */       if (str1.equalsIgnoreCase(paramString)) {
/* 77 */         return true;
/*    */       }
/*    */     } 
/* 80 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/SecurityProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */