/*     */ package java.security.cert;
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
/*     */ public class LDAPCertStoreParameters
/*     */   implements CertStoreParameters
/*     */ {
/*     */   private static final int LDAP_DEFAULT_PORT = 389;
/*     */   private int port;
/*     */   private String serverName;
/*     */   
/*     */   public LDAPCertStoreParameters(String paramString, int paramInt) {
/*  71 */     if (paramString == null)
/*  72 */       throw new NullPointerException(); 
/*  73 */     this.serverName = paramString;
/*  74 */     this.port = paramInt;
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
/*     */   public LDAPCertStoreParameters(String paramString) {
/*  86 */     this(paramString, 389);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LDAPCertStoreParameters() {
/*  94 */     this("localhost", 389);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getServerName() {
/* 103 */     return this.serverName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 112 */     return this.port;
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
/*     */   public Object clone() {
/*     */     try {
/* 128 */       return super.clone();
/* 129 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 131 */       throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 141 */     StringBuffer stringBuffer = new StringBuffer();
/* 142 */     stringBuffer.append("LDAPCertStoreParameters: [\n");
/*     */     
/* 144 */     stringBuffer.append("  serverName: " + this.serverName + "\n");
/* 145 */     stringBuffer.append("  port: " + this.port + "\n");
/* 146 */     stringBuffer.append("]");
/* 147 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/LDAPCertStoreParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */