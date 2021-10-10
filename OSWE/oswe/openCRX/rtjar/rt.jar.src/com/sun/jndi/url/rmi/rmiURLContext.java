/*     */ package com.sun.jndi.url.rmi;
/*     */ 
/*     */ import com.sun.jndi.rmi.registry.RegistryContext;
/*     */ import com.sun.jndi.toolkit.url.GenericURLContext;
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.spi.ResolveResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class rmiURLContext
/*     */   extends GenericURLContext
/*     */ {
/*     */   public rmiURLContext(Hashtable<?, ?> paramHashtable) {
/*  51 */     super(paramHashtable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResolveResult getRootURLContext(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  62 */     if (!paramString.startsWith("rmi:")) {
/*  63 */       throw new IllegalArgumentException("rmiURLContext: name is not an RMI URL: " + paramString);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     String str1 = null;
/*  70 */     int i = -1;
/*  71 */     String str2 = null;
/*     */     
/*  73 */     int j = 4;
/*     */     
/*  75 */     if (paramString.startsWith("//", j)) {
/*  76 */       j += 2;
/*  77 */       int k = paramString.indexOf('/', j);
/*  78 */       if (k < 0) {
/*  79 */         k = paramString.length();
/*     */       }
/*  81 */       if (paramString.startsWith("[", j)) {
/*  82 */         int m = paramString.indexOf(']', j + 1);
/*  83 */         if (m < 0 || m > k) {
/*  84 */           throw new IllegalArgumentException("rmiURLContext: name is an Invalid URL: " + paramString);
/*     */         }
/*     */         
/*  87 */         str1 = paramString.substring(j, m + 1);
/*  88 */         j = m + 1;
/*     */       } else {
/*  90 */         int m = paramString.indexOf(':', j);
/*  91 */         int n = (m < 0 || m > k) ? k : m;
/*     */ 
/*     */         
/*  94 */         if (j < n) {
/*  95 */           str1 = paramString.substring(j, n);
/*     */         }
/*  97 */         j = n;
/*     */       } 
/*  99 */       if (j + 1 < k) {
/* 100 */         if (paramString.startsWith(":", j)) {
/* 101 */           j++;
/* 102 */           i = Integer.parseInt(paramString.substring(j, k));
/*     */         } else {
/* 104 */           throw new IllegalArgumentException("rmiURLContext: name is an Invalid URL: " + paramString);
/*     */         } 
/*     */       }
/*     */       
/* 108 */       j = k;
/*     */     } 
/* 110 */     if ("".equals(str1)) {
/* 111 */       str1 = null;
/*     */     }
/* 113 */     if (paramString.startsWith("/", j)) {
/* 114 */       j++;
/*     */     }
/* 116 */     if (j < paramString.length()) {
/* 117 */       str2 = paramString.substring(j);
/*     */     }
/*     */ 
/*     */     
/* 121 */     CompositeName compositeName = new CompositeName();
/* 122 */     if (str2 != null) {
/* 123 */       compositeName.add(str2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     RegistryContext registryContext = new RegistryContext(str1, i, paramHashtable);
/*     */     
/* 133 */     return new ResolveResult(registryContext, compositeName);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/url/rmi/rmiURLContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */