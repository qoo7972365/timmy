/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import com.sun.jndi.toolkit.url.Uri;
/*     */ import com.sun.jndi.toolkit.url.UrlUtil;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.naming.NamingException;
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
/*     */ public final class LdapURL
/*     */   extends Uri
/*     */ {
/*     */   private boolean useSsl = false;
/*  68 */   private String DN = null;
/*  69 */   private String attributes = null;
/*  70 */   private String scope = null;
/*  71 */   private String filter = null;
/*  72 */   private String extensions = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LdapURL(String paramString) throws NamingException {
/*     */     try {
/*  82 */       init(paramString);
/*  83 */       this.useSsl = this.scheme.equalsIgnoreCase("ldaps");
/*     */       
/*  85 */       if (!this.scheme.equalsIgnoreCase("ldap") && !this.useSsl) {
/*  86 */         throw new MalformedURLException("Not an LDAP URL: " + paramString);
/*     */       }
/*     */       
/*  89 */       parsePathAndQuery();
/*     */     }
/*  91 */     catch (MalformedURLException malformedURLException) {
/*  92 */       NamingException namingException = new NamingException("Cannot parse url: " + paramString);
/*  93 */       namingException.setRootCause(malformedURLException);
/*  94 */       throw namingException;
/*  95 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*  96 */       NamingException namingException = new NamingException("Cannot parse url: " + paramString);
/*  97 */       namingException.setRootCause(unsupportedEncodingException);
/*  98 */       throw namingException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useSsl() {
/* 106 */     return this.useSsl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDN() {
/* 113 */     return this.DN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributes() {
/* 120 */     return this.attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getScope() {
/* 127 */     return this.scope;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFilter() {
/* 134 */     return this.filter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExtensions() {
/* 141 */     return this.extensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] fromList(String paramString) throws NamingException {
/* 149 */     String[] arrayOfString1 = new String[(paramString.length() + 1) / 2];
/* 150 */     byte b = 0;
/* 151 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, " ");
/*     */     
/* 153 */     while (stringTokenizer.hasMoreTokens()) {
/* 154 */       arrayOfString1[b++] = stringTokenizer.nextToken();
/*     */     }
/* 156 */     String[] arrayOfString2 = new String[b];
/* 157 */     System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, b);
/* 158 */     return arrayOfString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasQueryComponents(String paramString) {
/* 165 */     return (paramString.lastIndexOf('?') != -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String toUrlString(String paramString1, int paramInt, String paramString2, boolean paramBoolean) {
/*     */     try {
/* 177 */       String str1 = (paramString1 != null) ? paramString1 : "";
/* 178 */       if (str1.indexOf(':') != -1 && str1.charAt(0) != '[') {
/* 179 */         str1 = "[" + str1 + "]";
/*     */       }
/* 181 */       String str2 = (paramInt != -1) ? (":" + paramInt) : "";
/* 182 */       String str3 = (paramString2 != null) ? ("/" + UrlUtil.encode(paramString2, "UTF8")) : "";
/*     */       
/* 184 */       return paramBoolean ? ("ldaps://" + str1 + str2 + str3) : ("ldap://" + str1 + str2 + str3);
/* 185 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */       
/* 187 */       throw new IllegalStateException("UTF-8 encoding unavailable");
/*     */     } 
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
/*     */   private void parsePathAndQuery() throws MalformedURLException, UnsupportedEncodingException {
/* 200 */     if (this.path.equals("")) {
/*     */       return;
/*     */     }
/*     */     
/* 204 */     this.DN = this.path.startsWith("/") ? this.path.substring(1) : this.path;
/* 205 */     if (this.DN.length() > 0) {
/* 206 */       this.DN = UrlUtil.decode(this.DN, "UTF8");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 211 */     if (this.query == null || this.query.length() < 2) {
/*     */       return;
/*     */     }
/*     */     
/* 215 */     int i = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 220 */     int j = this.query.indexOf('?', i);
/* 221 */     int k = (j == -1) ? this.query.length() : j;
/* 222 */     if (k - i > 0) {
/* 223 */       this.attributes = this.query.substring(i, k);
/*     */     }
/* 225 */     i = k + 1;
/* 226 */     if (i >= this.query.length()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 231 */     j = this.query.indexOf('?', i);
/* 232 */     k = (j == -1) ? this.query.length() : j;
/* 233 */     if (k - i > 0) {
/* 234 */       this.scope = this.query.substring(i, k);
/*     */     }
/* 236 */     i = k + 1;
/* 237 */     if (i >= this.query.length()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 242 */     j = this.query.indexOf('?', i);
/* 243 */     k = (j == -1) ? this.query.length() : j;
/* 244 */     if (k - i > 0) {
/* 245 */       this.filter = this.query.substring(i, k);
/* 246 */       this.filter = UrlUtil.decode(this.filter, "UTF8");
/*     */     } 
/* 248 */     i = k + 1;
/* 249 */     if (i >= this.query.length()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 254 */     if (this.query.length() - i > 0) {
/* 255 */       this.extensions = this.query.substring(i);
/* 256 */       this.extensions = UrlUtil.decode(this.extensions, "UTF8");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapURL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */