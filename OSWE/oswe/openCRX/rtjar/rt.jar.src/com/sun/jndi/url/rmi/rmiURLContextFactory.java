/*     */ package com.sun.jndi.url.rmi;
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
/*     */ 
/*     */ public class rmiURLContextFactory
/*     */   implements ObjectFactory
/*     */ {
/*     */   public Object getObjectInstance(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  53 */     if (paramObject == null)
/*  54 */       return new rmiURLContext(paramHashtable); 
/*  55 */     if (paramObject instanceof String)
/*  56 */       return getUsingURL((String)paramObject, paramHashtable); 
/*  57 */     if (paramObject instanceof String[]) {
/*  58 */       return getUsingURLs((String[])paramObject, paramHashtable);
/*     */     }
/*  60 */     throw new ConfigurationException("rmiURLContextFactory.getObjectInstance: argument must be an RMI URL String or an array of them");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object getUsingURL(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  69 */     rmiURLContext rmiURLContext = new rmiURLContext(paramHashtable);
/*     */     try {
/*  71 */       return rmiURLContext.lookup(paramString);
/*     */     } finally {
/*  73 */       rmiURLContext.close();
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
/*  85 */     if (paramArrayOfString.length == 0) {
/*  86 */       throw new ConfigurationException("rmiURLContextFactory: empty URL array");
/*     */     }
/*     */     
/*  89 */     rmiURLContext rmiURLContext = new rmiURLContext(paramHashtable);
/*     */     try {
/*  91 */       NamingException namingException = null;
/*  92 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/*     */         try {
/*  94 */           return rmiURLContext.lookup(paramArrayOfString[b]);
/*  95 */         } catch (NamingException namingException1) {
/*  96 */           namingException = namingException1;
/*     */         } 
/*     */       } 
/*  99 */       throw namingException;
/*     */     } finally {
/* 101 */       rmiURLContext.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/url/rmi/rmiURLContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */