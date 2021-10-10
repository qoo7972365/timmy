/*     */ package com.sun.jndi.cosnaming;
/*     */ 
/*     */ import com.sun.jndi.toolkit.url.UrlUtil;
/*     */ import java.net.MalformedURLException;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
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
/*     */ public final class IiopUrl
/*     */ {
/*     */   private static final int DEFAULT_IIOPNAME_PORT = 9999;
/*     */   private static final int DEFAULT_IIOP_PORT = 900;
/*     */   private static final String DEFAULT_HOST = "localhost";
/*     */   private Vector<Address> addresses;
/*     */   private String stringName;
/*     */   
/*     */   public static class Address
/*     */   {
/*  75 */     public int port = -1;
/*     */     
/*     */     public int major;
/*     */     
/*     */     public int minor;
/*     */     
/*     */     public String host;
/*     */ 
/*     */     
/*     */     public Address(String param1String, boolean param1Boolean) throws MalformedURLException {
/*     */       int j;
/*  86 */       if (param1Boolean || (j = param1String.indexOf('@')) < 0) {
/*  87 */         this.major = 1;
/*  88 */         this.minor = 0;
/*  89 */         i = 0;
/*     */       } else {
/*  91 */         int m = param1String.indexOf('.');
/*  92 */         if (m < 0) {
/*  93 */           throw new MalformedURLException("invalid version: " + param1String);
/*     */         }
/*     */         
/*     */         try {
/*  97 */           this.major = Integer.parseInt(param1String.substring(0, m));
/*  98 */           this.minor = Integer.parseInt(param1String.substring(m + 1, j));
/*  99 */         } catch (NumberFormatException numberFormatException) {
/* 100 */           throw new MalformedURLException("Nonnumeric version: " + param1String);
/*     */         } 
/*     */         
/* 103 */         i = j + 1;
/*     */       } 
/*     */ 
/*     */       
/* 107 */       int k = param1String.indexOf('/', i);
/* 108 */       if (k < 0) {
/* 109 */         k = param1String.length();
/*     */       }
/* 111 */       if (param1String.startsWith("[", i)) {
/* 112 */         int m = param1String.indexOf(']', i + 1);
/* 113 */         if (m < 0 || m > k) {
/* 114 */           throw new IllegalArgumentException("IiopURL: name is an Invalid URL: " + param1String);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 119 */         this.host = param1String.substring(i, m + 1);
/* 120 */         i = m + 1;
/*     */       } else {
/* 122 */         int m = param1String.indexOf(':', i);
/* 123 */         int n = (m < 0 || m > k) ? k : m;
/*     */ 
/*     */         
/* 126 */         if (i < n) {
/* 127 */           this.host = param1String.substring(i, n);
/*     */         }
/* 129 */         i = n;
/*     */       } 
/* 131 */       if (i + 1 < k) {
/* 132 */         if (param1String.startsWith(":", i)) {
/* 133 */           i++;
/* 134 */           this.port = Integer.parseInt(param1String
/* 135 */               .substring(i, k));
/*     */         } else {
/* 137 */           throw new IllegalArgumentException("IiopURL: name is an Invalid URL: " + param1String);
/*     */         } 
/*     */       }
/*     */       
/* 141 */       int i = k;
/* 142 */       if ("".equals(this.host) || this.host == null) {
/* 143 */         this.host = "localhost";
/*     */       }
/* 145 */       if (this.port == -1) {
/* 146 */         this.port = param1Boolean ? 900 : 9999;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector<Address> getAddresses() {
/* 153 */     return this.addresses;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStringName() {
/* 161 */     return this.stringName;
/*     */   }
/*     */   
/*     */   public Name getCosName() throws NamingException {
/* 165 */     return CNCtx.parser.parse(this.stringName);
/*     */   }
/*     */ 
/*     */   
/*     */   public IiopUrl(String paramString) throws MalformedURLException {
/*     */     byte b;
/*     */     boolean bool;
/* 172 */     if (paramString.startsWith("iiopname://")) {
/* 173 */       bool = false;
/* 174 */       b = 11;
/* 175 */     } else if (paramString.startsWith("iiop://")) {
/* 176 */       bool = true;
/* 177 */       b = 7;
/*     */     } else {
/* 179 */       throw new MalformedURLException("Invalid iiop/iiopname URL: " + paramString);
/*     */     } 
/* 181 */     int i = paramString.indexOf('/', b);
/* 182 */     if (i < 0) {
/* 183 */       i = paramString.length();
/* 184 */       this.stringName = "";
/*     */     } else {
/* 186 */       this.stringName = UrlUtil.decode(paramString.substring(i + 1));
/*     */     } 
/* 188 */     this.addresses = new Vector<>(3);
/* 189 */     if (bool) {
/*     */       
/* 191 */       this.addresses.addElement(new Address(paramString
/* 192 */             .substring(b, i), bool));
/*     */     } else {
/*     */       
/* 195 */       StringTokenizer stringTokenizer = new StringTokenizer(paramString.substring(b, i), ",");
/* 196 */       while (stringTokenizer.hasMoreTokens()) {
/* 197 */         this.addresses.addElement(new Address(stringTokenizer.nextToken(), bool));
/*     */       }
/* 199 */       if (this.addresses.size() == 0)
/* 200 */         this.addresses.addElement(new Address("", bool)); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/cosnaming/IiopUrl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */