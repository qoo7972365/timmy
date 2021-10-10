/*    */ package sun.awt;
/*    */ 
/*    */ import java.awt.AWTPermission;
/*    */ import java.security.Permission;
/*    */ import sun.security.util.PermissionFactory;
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
/*    */ public class AWTPermissionFactory
/*    */   implements PermissionFactory<AWTPermission>
/*    */ {
/*    */   public AWTPermission newPermission(String paramString) {
/* 40 */     return new AWTPermission(paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/AWTPermissionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */