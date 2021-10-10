/*     */ package sun.net.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.net.URLPermission;
/*     */ import java.security.Permission;
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
/*     */ public class URLUtil
/*     */ {
/*     */   public static String urlNoFragString(URL paramURL) {
/*  48 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/*  50 */     String str1 = paramURL.getProtocol();
/*  51 */     if (str1 != null) {
/*     */       
/*  53 */       str1 = str1.toLowerCase();
/*  54 */       stringBuilder.append(str1);
/*  55 */       stringBuilder.append("://");
/*     */     } 
/*     */     
/*  58 */     String str2 = paramURL.getHost();
/*  59 */     if (str2 != null) {
/*     */       
/*  61 */       str2 = str2.toLowerCase();
/*  62 */       stringBuilder.append(str2);
/*     */       
/*  64 */       int i = paramURL.getPort();
/*  65 */       if (i == -1)
/*     */       {
/*     */         
/*  68 */         i = paramURL.getDefaultPort();
/*     */       }
/*  70 */       if (i != -1) {
/*  71 */         stringBuilder.append(":").append(i);
/*     */       }
/*     */     } 
/*     */     
/*  75 */     String str3 = paramURL.getFile();
/*  76 */     if (str3 != null) {
/*  77 */       stringBuilder.append(str3);
/*     */     }
/*     */     
/*  80 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public static Permission getConnectPermission(URL paramURL) throws IOException {
/*  84 */     String str = paramURL.toString().toLowerCase();
/*  85 */     if (str.startsWith("http:") || str.startsWith("https:"))
/*  86 */       return getURLConnectPermission(paramURL); 
/*  87 */     if (str.startsWith("jar:http:") || str.startsWith("jar:https:")) {
/*  88 */       String str1 = paramURL.toString();
/*  89 */       int i = str1.indexOf("!/");
/*  90 */       str1 = str1.substring(4, (i > -1) ? i : str1.length());
/*  91 */       URL uRL = new URL(str1);
/*  92 */       return getURLConnectPermission(uRL);
/*     */     } 
/*     */     
/*  95 */     return paramURL.openConnection().getPermission();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Permission getURLConnectPermission(URL paramURL) {
/* 100 */     String str = paramURL.getProtocol() + "://" + paramURL.getAuthority() + paramURL.getPath();
/* 101 */     return new URLPermission(str);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/util/URLUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */