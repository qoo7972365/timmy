/*     */ package sun.net.www.protocol.http;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.PasswordAuthentication;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.Base64;
/*     */ import sun.net.www.HeaderParser;
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
/*     */ class BasicAuthentication
/*     */   extends AuthenticationInfo
/*     */ {
/*     */   private static final long serialVersionUID = 100L;
/*     */   String auth;
/*     */   
/*     */   public BasicAuthentication(boolean paramBoolean, String paramString1, int paramInt, String paramString2, PasswordAuthentication paramPasswordAuthentication) {
/*  58 */     super(paramBoolean ? 112 : 115, AuthScheme.BASIC, paramString1, paramInt, paramString2);
/*     */     
/*  60 */     String str = paramPasswordAuthentication.getUserName() + ":";
/*  61 */     byte[] arrayOfByte1 = null;
/*     */     try {
/*  63 */       arrayOfByte1 = str.getBytes("ISO-8859-1");
/*  64 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */       assert false;
/*     */     } 
/*     */ 
/*     */     
/*  69 */     char[] arrayOfChar = paramPasswordAuthentication.getPassword();
/*  70 */     byte[] arrayOfByte2 = new byte[arrayOfChar.length];
/*  71 */     for (byte b = 0; b < arrayOfChar.length; b++) {
/*  72 */       arrayOfByte2[b] = (byte)arrayOfChar[b];
/*     */     }
/*     */     
/*  75 */     byte[] arrayOfByte3 = new byte[arrayOfByte1.length + arrayOfByte2.length];
/*  76 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte1.length);
/*  77 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte3, arrayOfByte1.length, arrayOfByte2.length);
/*     */     
/*  79 */     this.auth = "Basic " + Base64.getEncoder().encodeToString(arrayOfByte3);
/*  80 */     this.pw = paramPasswordAuthentication;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicAuthentication(boolean paramBoolean, String paramString1, int paramInt, String paramString2, String paramString3) {
/*  88 */     super(paramBoolean ? 112 : 115, AuthScheme.BASIC, paramString1, paramInt, paramString2);
/*     */     
/*  90 */     this.auth = "Basic " + paramString3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicAuthentication(boolean paramBoolean, URL paramURL, String paramString, PasswordAuthentication paramPasswordAuthentication) {
/*  98 */     super(paramBoolean ? 112 : 115, AuthScheme.BASIC, paramURL, paramString);
/*     */     
/* 100 */     String str = paramPasswordAuthentication.getUserName() + ":";
/* 101 */     byte[] arrayOfByte1 = null;
/*     */     try {
/* 103 */       arrayOfByte1 = str.getBytes("ISO-8859-1");
/* 104 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */       assert false;
/*     */     } 
/*     */ 
/*     */     
/* 109 */     char[] arrayOfChar = paramPasswordAuthentication.getPassword();
/* 110 */     byte[] arrayOfByte2 = new byte[arrayOfChar.length];
/* 111 */     for (byte b = 0; b < arrayOfChar.length; b++) {
/* 112 */       arrayOfByte2[b] = (byte)arrayOfChar[b];
/*     */     }
/*     */     
/* 115 */     byte[] arrayOfByte3 = new byte[arrayOfByte1.length + arrayOfByte2.length];
/* 116 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte1.length);
/* 117 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte3, arrayOfByte1.length, arrayOfByte2.length);
/*     */     
/* 119 */     this.auth = "Basic " + Base64.getEncoder().encodeToString(arrayOfByte3);
/* 120 */     this.pw = paramPasswordAuthentication;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicAuthentication(boolean paramBoolean, URL paramURL, String paramString1, String paramString2) {
/* 128 */     super(paramBoolean ? 112 : 115, AuthScheme.BASIC, paramURL, paramString1);
/*     */     
/* 130 */     this.auth = "Basic " + paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsPreemptiveAuthorization() {
/* 138 */     return true;
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
/*     */   public boolean setHeaders(HttpURLConnection paramHttpURLConnection, HeaderParser paramHeaderParser, String paramString) {
/* 151 */     paramHttpURLConnection.setAuthenticationProperty(getHeaderName(), getHeaderValue((URL)null, (String)null));
/* 152 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeaderValue(URL paramURL, String paramString) {
/* 163 */     return this.auth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAuthorizationStale(String paramString) {
/* 173 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getRootPath(String paramString1, String paramString2) {
/* 183 */     int i = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 188 */       paramString1 = (new URI(paramString1)).normalize().getPath();
/* 189 */       paramString2 = (new URI(paramString2)).normalize().getPath();
/* 190 */     } catch (URISyntaxException uRISyntaxException) {}
/*     */ 
/*     */ 
/*     */     
/* 194 */     while (i < paramString2.length()) {
/* 195 */       int j = paramString2.indexOf('/', i + 1);
/* 196 */       if (j != -1 && paramString2.regionMatches(0, paramString1, 0, j + 1)) {
/* 197 */         i = j; continue;
/*     */       } 
/* 199 */       return paramString2.substring(0, i + 1);
/*     */     } 
/*     */     
/* 202 */     return paramString1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/http/BasicAuthentication.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */