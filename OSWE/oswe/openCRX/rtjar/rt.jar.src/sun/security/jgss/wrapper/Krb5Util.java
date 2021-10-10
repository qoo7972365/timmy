/*    */ package sun.security.jgss.wrapper;
/*    */ 
/*    */ import javax.security.auth.kerberos.ServicePermission;
/*    */ import org.ietf.jgss.GSSException;
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
/*    */ class Krb5Util
/*    */ {
/*    */   static String getTGSName(GSSNameElement paramGSSNameElement) throws GSSException {
/* 41 */     String str1 = paramGSSNameElement.getKrbName();
/* 42 */     int i = str1.indexOf("@");
/* 43 */     String str2 = str1.substring(i + 1);
/* 44 */     StringBuffer stringBuffer = new StringBuffer("krbtgt/");
/* 45 */     stringBuffer.append(str2).append('@').append(str2);
/* 46 */     return stringBuffer.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static void checkServicePermission(String paramString1, String paramString2) {
/* 52 */     SecurityManager securityManager = System.getSecurityManager();
/* 53 */     if (securityManager != null) {
/* 54 */       SunNativeProvider.debug("Checking ServicePermission(" + paramString1 + ", " + paramString2 + ")");
/*    */       
/* 56 */       ServicePermission servicePermission = new ServicePermission(paramString1, paramString2);
/*    */       
/* 58 */       securityManager.checkPermission(servicePermission);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/wrapper/Krb5Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */