/*     */ package com.sun.jndi.dns;
/*     */ 
/*     */ import com.sun.jndi.toolkit.url.Uri;
/*     */ import com.sun.jndi.toolkit.url.UrlUtil;
/*     */ import java.net.MalformedURLException;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DnsUrl
/*     */   extends Uri
/*     */ {
/*     */   private String domain;
/*     */   
/*     */   public static DnsUrl[] fromList(String paramString) throws MalformedURLException {
/*  69 */     DnsUrl[] arrayOfDnsUrl1 = new DnsUrl[(paramString.length() + 1) / 2];
/*  70 */     byte b = 0;
/*  71 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, " ");
/*     */     
/*  73 */     while (stringTokenizer.hasMoreTokens()) {
/*  74 */       arrayOfDnsUrl1[b++] = new DnsUrl(stringTokenizer.nextToken());
/*     */     }
/*  76 */     DnsUrl[] arrayOfDnsUrl2 = new DnsUrl[b];
/*  77 */     System.arraycopy(arrayOfDnsUrl1, 0, arrayOfDnsUrl2, 0, b);
/*  78 */     return arrayOfDnsUrl2;
/*     */   }
/*     */   
/*     */   public DnsUrl(String paramString) throws MalformedURLException {
/*  82 */     super(paramString);
/*     */     
/*  84 */     if (!this.scheme.equals("dns")) {
/*  85 */       throw new MalformedURLException(paramString + " is not a valid DNS pseudo-URL");
/*     */     }
/*     */ 
/*     */     
/*  89 */     this
/*  90 */       .domain = this.path.startsWith("/") ? this.path.substring(1) : this.path;
/*     */     
/*  92 */     this
/*     */       
/*  94 */       .domain = this.domain.equals("") ? "." : UrlUtil.decode(this.domain);
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
/*     */   public String getDomain() {
/* 106 */     return this.domain;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/dns/DnsUrl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */