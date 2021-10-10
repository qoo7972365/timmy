/*    */ package sun.management;
/*    */ 
/*    */ import java.lang.management.ManagementPermission;
/*    */ import java.util.List;
/*    */ import javax.management.MalformedObjectNameException;
/*    */ import javax.management.ObjectName;
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
/*    */ public class Util
/*    */ {
/*    */   static RuntimeException newException(Exception paramException) {
/* 38 */     throw new RuntimeException(paramException);
/*    */   }
/*    */   
/* 41 */   private static final String[] EMPTY_STRING_ARRAY = new String[0];
/*    */   static String[] toStringArray(List<String> paramList) {
/* 43 */     return paramList.<String>toArray(EMPTY_STRING_ARRAY);
/*    */   }
/*    */   
/*    */   public static ObjectName newObjectName(String paramString1, String paramString2) {
/* 47 */     return newObjectName(paramString1 + ",name=" + paramString2);
/*    */   }
/*    */   
/*    */   public static ObjectName newObjectName(String paramString) {
/*    */     try {
/* 52 */       return ObjectName.getInstance(paramString);
/* 53 */     } catch (MalformedObjectNameException malformedObjectNameException) {
/* 54 */       throw new IllegalArgumentException(malformedObjectNameException);
/*    */     } 
/*    */   }
/*    */   
/* 58 */   private static ManagementPermission monitorPermission = new ManagementPermission("monitor");
/*    */   
/* 60 */   private static ManagementPermission controlPermission = new ManagementPermission("control");
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
/*    */   static void checkAccess(ManagementPermission paramManagementPermission) throws SecurityException {
/* 75 */     SecurityManager securityManager = System.getSecurityManager();
/* 76 */     if (securityManager != null) {
/* 77 */       securityManager.checkPermission(paramManagementPermission);
/*    */     }
/*    */   }
/*    */   
/*    */   static void checkMonitorAccess() throws SecurityException {
/* 82 */     checkAccess(monitorPermission);
/*    */   }
/*    */   static void checkControlAccess() throws SecurityException {
/* 85 */     checkAccess(controlPermission);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */