/*     */ package com.sun.jndi.dns;
/*     */ 
/*     */ import com.sun.jndi.toolkit.url.UrlUtil;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import javax.naming.ConfigurationException;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.spi.InitialContextFactory;
/*     */ import sun.net.dns.ResolverConfiguration;
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
/*     */ public class DnsContextFactory
/*     */   implements InitialContextFactory
/*     */ {
/*     */   private static final String DEFAULT_URL = "dns:";
/*     */   private static final int DEFAULT_PORT = 53;
/*     */   
/*     */   public Context getInitialContext(Hashtable<?, ?> paramHashtable) throws NamingException {
/*  61 */     if (paramHashtable == null) {
/*  62 */       paramHashtable = new Hashtable<>(5);
/*     */     }
/*  64 */     return urlToContext(getInitCtxUrl(paramHashtable), paramHashtable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DnsContext getContext(String paramString, String[] paramArrayOfString, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  70 */     return new DnsContext(paramString, paramArrayOfString, paramHashtable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DnsContext getContext(String paramString, DnsUrl[] paramArrayOfDnsUrl, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  81 */     String[] arrayOfString = serversForUrls(paramArrayOfDnsUrl);
/*  82 */     DnsContext dnsContext = getContext(paramString, arrayOfString, paramHashtable);
/*  83 */     if (platformServersUsed(paramArrayOfDnsUrl)) {
/*  84 */       dnsContext.setProviderUrl(constructProviderUrl(paramString, arrayOfString));
/*     */     }
/*  86 */     return dnsContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean platformServersAvailable() {
/*  93 */     return 
/*     */       
/*  95 */       !filterNameServers(ResolverConfiguration.open().nameservers(), true).isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Context urlToContext(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/*     */     DnsUrl[] arrayOfDnsUrl;
/*     */     try {
/* 103 */       arrayOfDnsUrl = DnsUrl.fromList(paramString);
/* 104 */     } catch (MalformedURLException malformedURLException) {
/* 105 */       throw new ConfigurationException(malformedURLException.getMessage());
/*     */     } 
/* 107 */     if (arrayOfDnsUrl.length == 0) {
/* 108 */       throw new ConfigurationException("Invalid DNS pseudo-URL(s): " + paramString);
/*     */     }
/*     */     
/* 111 */     String str = arrayOfDnsUrl[0].getDomain();
/*     */ 
/*     */     
/* 114 */     for (byte b = 1; b < arrayOfDnsUrl.length; b++) {
/* 115 */       if (!str.equalsIgnoreCase(arrayOfDnsUrl[b].getDomain())) {
/* 116 */         throw new ConfigurationException("Conflicting domains: " + paramString);
/*     */       }
/*     */     } 
/*     */     
/* 120 */     return getContext(str, arrayOfDnsUrl, paramHashtable);
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
/*     */   private static String[] serversForUrls(DnsUrl[] paramArrayOfDnsUrl) throws NamingException {
/* 134 */     if (paramArrayOfDnsUrl.length == 0) {
/* 135 */       throw new ConfigurationException("DNS pseudo-URL required");
/*     */     }
/*     */     
/* 138 */     ArrayList<String> arrayList = new ArrayList();
/*     */     
/* 140 */     for (byte b = 0; b < paramArrayOfDnsUrl.length; b++) {
/* 141 */       String str = paramArrayOfDnsUrl[b].getHost();
/* 142 */       int i = paramArrayOfDnsUrl[b].getPort();
/*     */       
/* 144 */       if (str == null && i < 0) {
/*     */ 
/*     */ 
/*     */         
/* 148 */         List<String> list = filterNameServers(
/* 149 */             ResolverConfiguration.open().nameservers(), false);
/* 150 */         if (!list.isEmpty()) {
/* 151 */           arrayList.addAll(list);
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 156 */       if (str == null) {
/* 157 */         str = "localhost";
/*     */       }
/* 159 */       arrayList.add((i < 0) ? str : (str + ":" + i));
/*     */       
/*     */       continue;
/*     */     } 
/* 163 */     return arrayList.<String>toArray(new String[arrayList.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean platformServersUsed(DnsUrl[] paramArrayOfDnsUrl) {
/* 171 */     if (!platformServersAvailable()) {
/* 172 */       return false;
/*     */     }
/* 174 */     for (byte b = 0; b < paramArrayOfDnsUrl.length; b++) {
/* 175 */       if (paramArrayOfDnsUrl[b].getHost() == null && paramArrayOfDnsUrl[b]
/* 176 */         .getPort() < 0) {
/* 177 */         return true;
/*     */       }
/*     */     } 
/* 180 */     return false;
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
/*     */   private static String constructProviderUrl(String paramString, String[] paramArrayOfString) {
/* 192 */     String str = "";
/* 193 */     if (!paramString.equals(".")) {
/*     */       try {
/* 195 */         str = "/" + UrlUtil.encode(paramString, "ISO-8859-1");
/* 196 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 201 */     StringBuffer stringBuffer = new StringBuffer();
/* 202 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 203 */       if (b > 0) {
/* 204 */         stringBuffer.append(' ');
/*     */       }
/* 206 */       stringBuffer.append("dns://").append(paramArrayOfString[b]).append(str);
/*     */     } 
/* 208 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getInitCtxUrl(Hashtable<?, ?> paramHashtable) {
/* 216 */     String str = (String)paramHashtable.get("java.naming.provider.url");
/* 217 */     return (str != null) ? str : "dns:";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<String> filterNameServers(List<String> paramList, boolean paramBoolean) {
/* 227 */     SecurityManager securityManager = System.getSecurityManager();
/* 228 */     if (securityManager == null || paramList == null || paramList.isEmpty()) {
/* 229 */       return paramList;
/*     */     }
/* 231 */     ArrayList<String> arrayList = new ArrayList();
/* 232 */     for (String str1 : paramList) {
/* 233 */       int i = str1.indexOf(':', str1
/* 234 */           .indexOf(']') + 1);
/*     */ 
/*     */ 
/*     */       
/* 238 */       boolean bool = (i < 0) ? true : Integer.parseInt(str1
/* 239 */           .substring(i + 1));
/*     */ 
/*     */       
/* 242 */       String str2 = (i < 0) ? str1 : str1.substring(0, i);
/*     */       try {
/* 244 */         securityManager.checkConnect(str2, bool);
/* 245 */         arrayList.add(str1);
/* 246 */         if (paramBoolean) {
/* 247 */           return arrayList;
/*     */         }
/* 249 */       } catch (SecurityException securityException) {}
/*     */     } 
/*     */ 
/*     */     
/* 253 */     return arrayList;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/dns/DnsContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */