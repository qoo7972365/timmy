/*     */ package com.sun.jndi.cosnaming;
/*     */ 
/*     */ import com.sun.jndi.toolkit.url.UrlUtil;
/*     */ import java.net.MalformedURLException;
/*     */ import javax.naming.Name;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CorbanameUrl
/*     */ {
/*     */   private String stringName;
/*     */   private String location;
/*     */   
/*     */   public String getStringName() {
/*  77 */     return this.stringName;
/*     */   }
/*     */   
/*     */   public Name getCosName() throws NamingException {
/*  81 */     return CNCtx.parser.parse(this.stringName);
/*     */   }
/*     */   
/*     */   public String getLocation() {
/*  85 */     return "corbaloc:" + this.location;
/*     */   }
/*     */ 
/*     */   
/*     */   public CorbanameUrl(String paramString) throws MalformedURLException {
/*  90 */     if (!paramString.startsWith("corbaname:")) {
/*  91 */       throw new MalformedURLException("Invalid corbaname URL: " + paramString);
/*     */     }
/*     */     
/*  94 */     byte b = 10;
/*     */     
/*  96 */     int i = paramString.indexOf('#', b);
/*  97 */     if (i < 0) {
/*  98 */       i = paramString.length();
/*  99 */       this.stringName = "";
/*     */     } else {
/* 101 */       this.stringName = UrlUtil.decode(paramString.substring(i + 1));
/*     */     } 
/* 103 */     this.location = paramString.substring(b, i);
/*     */     
/* 105 */     int j = this.location.indexOf("/");
/* 106 */     if (j >= 0) {
/*     */       
/* 108 */       if (j == this.location.length() - 1) {
/* 109 */         this.location += "NameService";
/*     */       }
/*     */     } else {
/* 112 */       this.location += "/NameService";
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/cosnaming/CorbanameUrl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */