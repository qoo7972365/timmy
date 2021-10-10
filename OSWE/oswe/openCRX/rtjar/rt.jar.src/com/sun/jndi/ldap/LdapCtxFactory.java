/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import com.sun.jndi.url.ldap.ldapURLContextFactory;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.naming.AuthenticationException;
/*     */ import javax.naming.ConfigurationException;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.RefAddr;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.BasicAttribute;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.ldap.Control;
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
/*     */ public final class LdapCtxFactory
/*     */   implements ObjectFactory, InitialContextFactory
/*     */ {
/*     */   public static final String ADDRESS_TYPE = "URL";
/*     */   
/*     */   public Object getObjectInstance(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws Exception {
/*  51 */     if (!isLdapRef(paramObject)) {
/*  52 */       return null;
/*     */     }
/*  54 */     ldapURLContextFactory ldapURLContextFactory = new ldapURLContextFactory();
/*  55 */     String[] arrayOfString = getURLs((Reference)paramObject);
/*  56 */     return ldapURLContextFactory.getObjectInstance(arrayOfString, paramName, paramContext, paramHashtable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Context getInitialContext(Hashtable<?, ?> paramHashtable) throws NamingException {
/*     */     try {
/*  66 */       String str = (paramHashtable != null) ? (String)paramHashtable.get("java.naming.provider.url") : null;
/*     */ 
/*     */       
/*  69 */       if (str == null) {
/*  70 */         return new LdapCtx("", "localhost", 389, paramHashtable, false);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  75 */       String[] arrayOfString = LdapURL.fromList(str);
/*     */       
/*  77 */       if (arrayOfString.length == 0) {
/*  78 */         throw new ConfigurationException("java.naming.provider.url property does not contain a URL");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  83 */       return getLdapCtxInstance(arrayOfString, paramHashtable);
/*     */     }
/*  85 */     catch (LdapReferralException ldapReferralException) {
/*     */       
/*  87 */       if (paramHashtable != null && "throw"
/*  88 */         .equals(paramHashtable.get("java.naming.referral"))) {
/*  89 */         throw ldapReferralException;
/*     */       }
/*     */ 
/*     */       
/*  93 */       Control[] arrayOfControl = (paramHashtable != null) ? (Control[])paramHashtable.get("java.naming.ldap.control.connect") : null;
/*     */       
/*  95 */       return ldapReferralException.getReferralContext(paramHashtable, arrayOfControl);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isLdapRef(Object paramObject) {
/* 104 */     if (!(paramObject instanceof Reference)) {
/* 105 */       return false;
/*     */     }
/* 107 */     String str = LdapCtxFactory.class.getName();
/* 108 */     Reference reference = (Reference)paramObject;
/*     */     
/* 110 */     return str.equals(reference.getFactoryClassName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] getURLs(Reference paramReference) throws NamingException {
/* 118 */     byte b = 0;
/* 119 */     String[] arrayOfString1 = new String[paramReference.size()];
/*     */     
/* 121 */     Enumeration<RefAddr> enumeration = paramReference.getAll();
/* 122 */     while (enumeration.hasMoreElements()) {
/* 123 */       RefAddr refAddr = enumeration.nextElement();
/*     */       
/* 125 */       if (refAddr instanceof javax.naming.StringRefAddr && refAddr
/* 126 */         .getType().equals("URL"))
/*     */       {
/* 128 */         arrayOfString1[b++] = (String)refAddr.getContent();
/*     */       }
/*     */     } 
/* 131 */     if (b == 0) {
/* 132 */       throw new ConfigurationException("Reference contains no valid addresses");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 137 */     if (b == paramReference.size()) {
/* 138 */       return arrayOfString1;
/*     */     }
/* 140 */     String[] arrayOfString2 = new String[b];
/* 141 */     System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, b);
/* 142 */     return arrayOfString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DirContext getLdapCtxInstance(Object paramObject, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 150 */     if (paramObject instanceof String)
/* 151 */       return getUsingURL((String)paramObject, paramHashtable); 
/* 152 */     if (paramObject instanceof String[]) {
/* 153 */       return getUsingURLs((String[])paramObject, paramHashtable);
/*     */     }
/* 155 */     throw new IllegalArgumentException("argument must be an LDAP URL String or array of them");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DirContext getUsingURL(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 162 */     DirContext dirContext = null;
/* 163 */     LdapURL ldapURL = new LdapURL(paramString);
/* 164 */     String str1 = ldapURL.getDN();
/* 165 */     String str2 = ldapURL.getHost();
/* 166 */     int i = ldapURL.getPort();
/*     */     
/* 168 */     String str3 = null;
/*     */     
/*     */     String[] arrayOfString;
/*     */     
/* 172 */     if (str2 == null && i == -1 && str1 != null && (
/*     */ 
/*     */       
/* 175 */       str3 = ServiceLocator.mapDnToDomainName(str1)) != null && (
/* 176 */       arrayOfString = ServiceLocator.getLdapService(str3, paramHashtable)) != null) {
/*     */ 
/*     */ 
/*     */       
/* 180 */       String str4 = ldapURL.getScheme() + "://";
/* 181 */       String[] arrayOfString1 = new String[arrayOfString.length];
/* 182 */       String str5 = ldapURL.getQuery();
/* 183 */       String str6 = ldapURL.getPath() + ((str5 != null) ? str5 : "");
/* 184 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 185 */         arrayOfString1[b] = str4 + arrayOfString[b] + str6;
/*     */       }
/* 187 */       dirContext = getUsingURLs(arrayOfString1, paramHashtable);
/*     */       
/* 189 */       ((LdapCtx)dirContext).setDomainName(str3);
/*     */     } else {
/*     */       
/* 192 */       dirContext = new LdapCtx(str1, str2, i, paramHashtable, ldapURL.useSsl());
/*     */       
/* 194 */       ((LdapCtx)dirContext).setProviderUrl(paramString);
/*     */     } 
/* 196 */     return dirContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DirContext getUsingURLs(String[] paramArrayOfString, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 206 */     NamingException namingException = null;
/* 207 */     Object object = null;
/* 208 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*     */       try {
/* 210 */         return getUsingURL(paramArrayOfString[b], paramHashtable);
/* 211 */       } catch (AuthenticationException authenticationException) {
/* 212 */         throw authenticationException;
/* 213 */       } catch (NamingException namingException1) {
/* 214 */         namingException = namingException1;
/*     */       } 
/*     */     } 
/* 217 */     throw namingException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Attribute createTypeNameAttr(Class<?> paramClass) {
/* 224 */     Vector<String> vector = new Vector(10);
/* 225 */     String[] arrayOfString = getTypeNames(paramClass, vector);
/* 226 */     if (arrayOfString.length > 0) {
/* 227 */       BasicAttribute basicAttribute = new BasicAttribute(Obj.JAVA_ATTRIBUTES[6]);
/*     */       
/* 229 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 230 */         basicAttribute.add(arrayOfString[b]);
/*     */       }
/* 232 */       return basicAttribute;
/*     */     } 
/* 234 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] getTypeNames(Class<?> paramClass, Vector<String> paramVector) {
/* 239 */     getClassesAux(paramClass, paramVector);
/* 240 */     Class[] arrayOfClass = paramClass.getInterfaces();
/* 241 */     for (byte b1 = 0; b1 < arrayOfClass.length; b1++) {
/* 242 */       getClassesAux(arrayOfClass[b1], paramVector);
/*     */     }
/* 244 */     String[] arrayOfString = new String[paramVector.size()];
/* 245 */     byte b2 = 0;
/*     */     
/* 247 */     for (String str : paramVector) {
/* 248 */       arrayOfString[b2++] = str;
/*     */     }
/* 250 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   private static void getClassesAux(Class<?> paramClass, Vector<String> paramVector) {
/* 254 */     if (!paramVector.contains(paramClass.getName())) {
/* 255 */       paramVector.addElement(paramClass.getName());
/*     */     }
/* 257 */     paramClass = paramClass.getSuperclass();
/*     */     
/* 259 */     while (paramClass != null) {
/* 260 */       getTypeNames(paramClass, paramVector);
/* 261 */       paramClass = paramClass.getSuperclass();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapCtxFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */