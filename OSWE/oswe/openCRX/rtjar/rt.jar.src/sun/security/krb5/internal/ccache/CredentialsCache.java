/*     */ package sun.security.krb5.internal.ccache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.RealmException;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.LoginOptions;
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
/*     */ public abstract class CredentialsCache
/*     */ {
/*  46 */   static CredentialsCache singleton = null;
/*     */   static String cacheName;
/*  48 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */   
/*     */   public static CredentialsCache getInstance(PrincipalName paramPrincipalName) {
/*  51 */     return FileCredentialsCache.acquireInstance(paramPrincipalName, null);
/*     */   }
/*     */   
/*     */   public static CredentialsCache getInstance(String paramString) {
/*  55 */     if (paramString.length() >= 5 && paramString.substring(0, 5).equalsIgnoreCase("FILE:")) {
/*  56 */       return FileCredentialsCache.acquireInstance(null, paramString.substring(5));
/*     */     }
/*     */ 
/*     */     
/*  60 */     return FileCredentialsCache.acquireInstance(null, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CredentialsCache getInstance(PrincipalName paramPrincipalName, String paramString) {
/*  67 */     if (paramString != null && paramString
/*  68 */       .length() >= 5 && paramString
/*  69 */       .regionMatches(true, 0, "FILE:", 0, 5)) {
/*  70 */       return FileCredentialsCache.acquireInstance(paramPrincipalName, paramString
/*  71 */           .substring(5));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     return FileCredentialsCache.acquireInstance(paramPrincipalName, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CredentialsCache getInstance() {
/*  87 */     return FileCredentialsCache.acquireInstance();
/*     */   }
/*     */   
/*     */   public static CredentialsCache create(PrincipalName paramPrincipalName, String paramString) {
/*  91 */     if (paramString == null) {
/*  92 */       throw new RuntimeException("cache name error");
/*     */     }
/*  94 */     if (paramString.length() >= 5 && paramString
/*  95 */       .regionMatches(true, 0, "FILE:", 0, 5)) {
/*  96 */       paramString = paramString.substring(5);
/*  97 */       return FileCredentialsCache.New(paramPrincipalName, paramString);
/*     */     } 
/*     */ 
/*     */     
/* 101 */     return FileCredentialsCache.New(paramPrincipalName, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public static CredentialsCache create(PrincipalName paramPrincipalName) {
/* 106 */     return FileCredentialsCache.New(paramPrincipalName);
/*     */   } public abstract PrincipalName getPrimaryPrincipal(); public abstract void update(Credentials paramCredentials); public abstract void save() throws IOException, KrbException; public abstract Credentials[] getCredsList();
/*     */   public abstract Credentials getDefaultCreds();
/*     */   public static String cacheName() {
/* 110 */     return cacheName;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Credentials getInitialCreds();
/*     */   
/*     */   public abstract Credentials getCreds(PrincipalName paramPrincipalName);
/*     */   
/*     */   public abstract Credentials getCreds(LoginOptions paramLoginOptions, PrincipalName paramPrincipalName);
/*     */   
/*     */   public abstract void addConfigEntry(ConfigEntry paramConfigEntry);
/*     */   
/*     */   public abstract List<ConfigEntry> getConfigEntries();
/*     */   
/*     */   public ConfigEntry getConfigEntry(String paramString) {
/* 125 */     List<ConfigEntry> list = getConfigEntries();
/* 126 */     if (list != null) {
/* 127 */       for (ConfigEntry configEntry : list) {
/* 128 */         if (configEntry.getName().equals(paramString)) {
/* 129 */           return configEntry;
/*     */         }
/*     */       } 
/*     */     }
/* 133 */     return null;
/*     */   }
/*     */   public static class ConfigEntry { private final String name; private final PrincipalName princ;
/*     */     private final byte[] data;
/*     */     
/*     */     public ConfigEntry(String param1String, PrincipalName param1PrincipalName, byte[] param1ArrayOfbyte) {
/* 139 */       this.name = param1String;
/* 140 */       this.princ = param1PrincipalName;
/* 141 */       this.data = param1ArrayOfbyte;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/* 149 */       return this.name;
/*     */     }
/*     */     
/*     */     public PrincipalName getPrinc() {
/* 153 */       return this.princ;
/*     */     }
/*     */     
/*     */     public byte[] getData() {
/* 157 */       return this.data;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 162 */       return this.name + ((this.princ != null) ? ("." + this.princ) : "") + ": " + new String(this.data);
/*     */     }
/*     */ 
/*     */     
/*     */     public PrincipalName getSName() {
/*     */       try {
/* 168 */         return new PrincipalName("krb5_ccache_conf_data/" + this.name + ((this.princ != null) ? ("/" + this.princ) : "") + "@X-CACHECONF:");
/*     */       
/*     */       }
/* 171 */       catch (RealmException realmException) {
/* 172 */         throw new AssertionError(realmException);
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/ccache/CredentialsCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */