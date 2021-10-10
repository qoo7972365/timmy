/*     */ package com.sun.jndi.url.dns;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.ConfigurationException;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.spi.ObjectFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class dnsURLContextFactory
/*     */   implements ObjectFactory
/*     */ {
/*     */   public Object getObjectInstance(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  52 */     if (paramObject == null)
/*  53 */       return new dnsURLContext(paramHashtable); 
/*  54 */     if (paramObject instanceof String)
/*  55 */       return getUsingURL((String)paramObject, paramHashtable); 
/*  56 */     if (paramObject instanceof String[]) {
/*  57 */       return getUsingURLs((String[])paramObject, paramHashtable);
/*     */     }
/*  59 */     throw new ConfigurationException("dnsURLContextFactory.getObjectInstance: argument must be a DNS URL String or an array of them");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object getUsingURL(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  68 */     dnsURLContext dnsURLContext = new dnsURLContext(paramHashtable);
/*     */     try {
/*  70 */       return dnsURLContext.lookup(paramString);
/*     */     } finally {
/*  72 */       dnsURLContext.close();
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
/*     */   private static Object getUsingURLs(String[] paramArrayOfString, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  84 */     if (paramArrayOfString.length == 0) {
/*  85 */       throw new ConfigurationException("dnsURLContextFactory: empty URL array");
/*     */     }
/*     */     
/*  88 */     dnsURLContext dnsURLContext = new dnsURLContext(paramHashtable);
/*     */     try {
/*  90 */       NamingException namingException = null;
/*  91 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/*     */         try {
/*  93 */           return dnsURLContext.lookup(paramArrayOfString[b]);
/*  94 */         } catch (NamingException namingException1) {
/*  95 */           namingException = namingException1;
/*     */         } 
/*     */       } 
/*  98 */       throw namingException;
/*     */     } finally {
/* 100 */       dnsURLContext.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/url/dns/dnsURLContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */