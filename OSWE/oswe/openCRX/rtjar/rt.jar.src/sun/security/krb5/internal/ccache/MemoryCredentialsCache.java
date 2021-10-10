/*    */ package sun.security.krb5.internal.ccache;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import sun.security.krb5.KrbException;
/*    */ import sun.security.krb5.PrincipalName;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MemoryCredentialsCache
/*    */   extends CredentialsCache
/*    */ {
/*    */   private static CredentialsCache getCCacheInstance(PrincipalName paramPrincipalName) {
/* 51 */     return null;
/*    */   }
/*    */   
/*    */   private static CredentialsCache getCCacheInstance(PrincipalName paramPrincipalName, File paramFile) {
/* 55 */     return null;
/*    */   }
/*    */   
/*    */   public abstract boolean exists(String paramString);
/*    */   
/*    */   public abstract void update(Credentials paramCredentials);
/*    */   
/*    */   public abstract void save() throws IOException, KrbException;
/*    */   
/*    */   public abstract Credentials[] getCredsList();
/*    */   
/*    */   public abstract Credentials getCreds(PrincipalName paramPrincipalName);
/*    */   
/*    */   public abstract PrincipalName getPrimaryPrincipal();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/ccache/MemoryCredentialsCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */