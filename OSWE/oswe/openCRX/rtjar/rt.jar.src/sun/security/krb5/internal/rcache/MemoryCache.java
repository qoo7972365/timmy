/*    */ package sun.security.krb5.internal.rcache;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import sun.security.krb5.internal.KerberosTime;
/*    */ import sun.security.krb5.internal.Krb5;
/*    */ import sun.security.krb5.internal.KrbApErrException;
/*    */ import sun.security.krb5.internal.ReplayCache;
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
/*    */ public class MemoryCache
/*    */   extends ReplayCache
/*    */ {
/* 50 */   private static final int lifespan = KerberosTime.getDefaultSkew();
/* 51 */   private static final boolean DEBUG = Krb5.DEBUG;
/*    */   
/* 53 */   private final Map<String, AuthList> content = new ConcurrentHashMap<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void checkAndStore(KerberosTime paramKerberosTime, AuthTimeWithHash paramAuthTimeWithHash) throws KrbApErrException {
/* 58 */     String str = paramAuthTimeWithHash.client + "|" + paramAuthTimeWithHash.server;
/* 59 */     ((AuthList)this.content.computeIfAbsent(str, paramString -> new AuthList(lifespan)))
/* 60 */       .put(paramAuthTimeWithHash, paramKerberosTime);
/* 61 */     if (DEBUG) {
/* 62 */       System.out.println("MemoryCache: add " + paramAuthTimeWithHash + " to " + str);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 68 */     StringBuilder stringBuilder = new StringBuilder();
/* 69 */     for (AuthList authList : this.content.values()) {
/* 70 */       stringBuilder.append(authList.toString());
/*    */     }
/* 72 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/rcache/MemoryCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */