/*     */ package com.sun.jndi.rmi.registry;
/*     */ 
/*     */ import com.sun.jndi.url.rmi.rmiURLContextFactory;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.ConfigurationException;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.NotContextException;
/*     */ import javax.naming.RefAddr;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.spi.InitialContextFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegistryContextFactory
/*     */   implements ObjectFactory, InitialContextFactory
/*     */ {
/*     */   public static final String ADDRESS_TYPE = "URL";
/*     */   
/*     */   public Context getInitialContext(Hashtable<?, ?> paramHashtable) throws NamingException {
/*  66 */     if (paramHashtable != null) {
/*  67 */       paramHashtable = (Hashtable<?, ?>)paramHashtable.clone();
/*     */     }
/*  69 */     return URLToContext(getInitCtxURL(paramHashtable), paramHashtable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getObjectInstance(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  76 */     if (!isRegistryRef(paramObject)) {
/*  77 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     Object object = URLsToObject(getURLs((Reference)paramObject), paramHashtable);
/*  91 */     if (object instanceof RegistryContext) {
/*  92 */       RegistryContext registryContext = (RegistryContext)object;
/*  93 */       registryContext.reference = (Reference)paramObject;
/*     */     } 
/*  95 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Context URLToContext(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 101 */     rmiURLContextFactory rmiURLContextFactory = new rmiURLContextFactory();
/* 102 */     Object object = rmiURLContextFactory.getObjectInstance(paramString, null, null, paramHashtable);
/*     */     
/* 104 */     if (object instanceof Context) {
/* 105 */       return (Context)object;
/*     */     }
/* 107 */     throw new NotContextException(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object URLsToObject(String[] paramArrayOfString, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 114 */     rmiURLContextFactory rmiURLContextFactory = new rmiURLContextFactory();
/* 115 */     return rmiURLContextFactory.getObjectInstance(paramArrayOfString, null, null, paramHashtable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getInitCtxURL(Hashtable<?, ?> paramHashtable) {
/* 126 */     String str = null;
/* 127 */     if (paramHashtable != null) {
/* 128 */       str = (String)paramHashtable.get("java.naming.provider.url");
/*     */     }
/* 130 */     return (str != null) ? str : "rmi:";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isRegistryRef(Object paramObject) {
/* 138 */     if (!(paramObject instanceof Reference)) {
/* 139 */       return false;
/*     */     }
/* 141 */     String str = RegistryContextFactory.class.getName();
/* 142 */     Reference reference = (Reference)paramObject;
/*     */     
/* 144 */     return str.equals(reference.getFactoryClassName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] getURLs(Reference paramReference) throws NamingException {
/* 152 */     byte b = 0;
/* 153 */     String[] arrayOfString1 = new String[paramReference.size()];
/*     */     
/* 155 */     Enumeration<RefAddr> enumeration = paramReference.getAll();
/* 156 */     while (enumeration.hasMoreElements()) {
/* 157 */       RefAddr refAddr = enumeration.nextElement();
/*     */       
/* 159 */       if (refAddr instanceof javax.naming.StringRefAddr && refAddr
/* 160 */         .getType().equals("URL"))
/*     */       {
/* 162 */         arrayOfString1[b++] = (String)refAddr.getContent();
/*     */       }
/*     */     } 
/* 165 */     if (b == 0) {
/* 166 */       throw new ConfigurationException("Reference contains no valid addresses");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 171 */     if (b == paramReference.size()) {
/* 172 */       return arrayOfString1;
/*     */     }
/* 174 */     String[] arrayOfString2 = new String[b];
/* 175 */     System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, b);
/* 176 */     return arrayOfString2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/rmi/registry/RegistryContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */