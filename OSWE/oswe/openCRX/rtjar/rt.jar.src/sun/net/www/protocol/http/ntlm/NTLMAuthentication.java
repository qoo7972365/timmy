/*     */ package sun.net.www.protocol.http.ntlm;
/*     */ 
/*     */ import com.sun.security.ntlm.Client;
/*     */ import com.sun.security.ntlm.NTLMException;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.PasswordAuthentication;
/*     */ import java.net.URL;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.AccessController;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Base64;
/*     */ import java.util.Random;
/*     */ import sun.net.www.HeaderParser;
/*     */ import sun.net.www.protocol.http.AuthScheme;
/*     */ import sun.net.www.protocol.http.AuthenticationInfo;
/*     */ import sun.net.www.protocol.http.HttpURLConnection;
/*     */ import sun.security.action.GetPropertyAction;
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
/*     */ public class NTLMAuthentication
/*     */   extends AuthenticationInfo
/*     */ {
/*     */   private static final long serialVersionUID = 170L;
/*  73 */   private static final NTLMAuthenticationCallback NTLMAuthCallback = NTLMAuthenticationCallback.getNTLMAuthenticationCallback();
/*     */ 
/*     */ 
/*     */   
/*     */   private String hostname;
/*     */ 
/*     */   
/*  80 */   private static String defaultDomain = AccessController.<String>doPrivileged(new GetPropertyAction("http.auth.ntlm.domain", "")); private static final boolean ntlmCache;
/*     */   static {
/*  82 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("jdk.ntlm.cache", "true"));
/*     */     
/*  84 */     ntlmCache = Boolean.parseBoolean(str);
/*     */   }
/*     */   PasswordAuthentication pw; Client client;
/*     */   public static boolean supportsTransparentAuth() {
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTrustedSite(URL paramURL) {
/*  97 */     if (NTLMAuthCallback != null)
/*  98 */       return NTLMAuthCallback.isTrustedSite(paramURL); 
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void init0() {
/* 104 */     this.hostname = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run() {
/*     */             String str;
/*     */             try {
/* 109 */               str = InetAddress.getLocalHost().getHostName();
/* 110 */             } catch (UnknownHostException unknownHostException) {
/* 111 */               str = "localhost";
/*     */             } 
/* 113 */             return str;
/*     */           }
/*     */         });
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
/*     */   public NTLMAuthentication(boolean paramBoolean, URL paramURL, PasswordAuthentication paramPasswordAuthentication) {
/* 128 */     super(paramBoolean ? 112 : 115, AuthScheme.NTLM, paramURL, "");
/*     */ 
/*     */ 
/*     */     
/* 132 */     init(paramPasswordAuthentication);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(PasswordAuthentication paramPasswordAuthentication) {
/*     */     String str1, str2;
/* 139 */     this.pw = paramPasswordAuthentication;
/* 140 */     String str3 = paramPasswordAuthentication.getUserName();
/* 141 */     int i = str3.indexOf('\\');
/* 142 */     if (i == -1) {
/* 143 */       str1 = str3;
/* 144 */       str2 = defaultDomain;
/*     */     } else {
/* 146 */       str2 = str3.substring(0, i).toUpperCase();
/* 147 */       str1 = str3.substring(i + 1);
/*     */     } 
/* 149 */     char[] arrayOfChar = paramPasswordAuthentication.getPassword();
/* 150 */     init0();
/*     */     try {
/* 152 */       this.client = new Client(System.getProperty("ntlm.version"), this.hostname, str1, str2, arrayOfChar);
/*     */     }
/* 154 */     catch (NTLMException nTLMException) {
/*     */       try {
/* 156 */         this.client = new Client(null, this.hostname, str1, str2, arrayOfChar);
/* 157 */       } catch (NTLMException nTLMException1) {
/*     */         
/* 159 */         throw new AssertionError("Really?");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NTLMAuthentication(boolean paramBoolean, String paramString, int paramInt, PasswordAuthentication paramPasswordAuthentication) {
/* 169 */     super(paramBoolean ? 112 : 115, AuthScheme.NTLM, paramString, paramInt, "");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     init(paramPasswordAuthentication);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean useAuthCache() {
/* 179 */     return (ntlmCache && super.useAuthCache());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsPreemptiveAuthorization() {
/* 187 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeaderValue(URL paramURL, String paramString) {
/* 195 */     throw new RuntimeException("getHeaderValue not supported");
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
/*     */   public boolean isAuthorizationStale(String paramString) {
/* 208 */     return false;
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
/*     */   public synchronized boolean setHeaders(HttpURLConnection paramHttpURLConnection, HeaderParser paramHeaderParser, String paramString) {
/*     */     try {
/*     */       String str;
/* 224 */       if (paramString.length() < 6) {
/* 225 */         str = buildType1Msg();
/*     */       } else {
/* 227 */         String str1 = paramString.substring(5);
/* 228 */         str = buildType3Msg(str1);
/*     */       } 
/* 230 */       paramHttpURLConnection.setAuthenticationProperty(getHeaderName(), str);
/* 231 */       return true;
/* 232 */     } catch (IOException iOException) {
/* 233 */       return false;
/* 234 */     } catch (GeneralSecurityException generalSecurityException) {
/* 235 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String buildType1Msg() {
/* 240 */     byte[] arrayOfByte = this.client.type1();
/* 241 */     return "NTLM " + Base64.getEncoder().encodeToString(arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildType3Msg(String paramString) throws GeneralSecurityException, IOException {
/* 250 */     byte[] arrayOfByte1 = Base64.getDecoder().decode(paramString);
/* 251 */     byte[] arrayOfByte2 = new byte[8];
/* 252 */     (new Random()).nextBytes(arrayOfByte2);
/* 253 */     byte[] arrayOfByte3 = this.client.type3(arrayOfByte1, arrayOfByte2);
/* 254 */     return "NTLM " + Base64.getEncoder().encodeToString(arrayOfByte3);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/http/ntlm/NTLMAuthentication.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */