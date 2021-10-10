/*     */ package com.sun.jndi.rmi.registry;
/*     */ 
/*     */ import java.rmi.AlreadyBoundException;
/*     */ import java.rmi.NotBoundException;
/*     */ import java.rmi.RMISecurityManager;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.registry.LocateRegistry;
/*     */ import java.rmi.registry.Registry;
/*     */ import java.rmi.server.RMIClientSocketFactory;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.CommunicationException;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.ConfigurationException;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameAlreadyBoundException;
/*     */ import javax.naming.NameClassPair;
/*     */ import javax.naming.NameNotFoundException;
/*     */ import javax.naming.NameParser;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.NoPermissionException;
/*     */ import javax.naming.OperationNotSupportedException;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.Referenceable;
/*     */ import javax.naming.ServiceUnavailableException;
/*     */ import javax.naming.StringRefAddr;
/*     */ import javax.naming.spi.NamingManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegistryContext
/*     */   implements Context, Referenceable
/*     */ {
/*     */   private Hashtable<String, Object> environment;
/*     */   private Registry registry;
/*     */   private String host;
/*     */   private int port;
/*  55 */   private static final NameParser nameParser = new AtomicNameParser();
/*     */ 
/*     */   
/*     */   private static final String SOCKET_FACTORY = "com.sun.jndi.rmi.factory.socket";
/*     */   
/*     */   static final boolean trustURLCodebase;
/*     */ 
/*     */   
/*     */   static {
/*  64 */     PrivilegedAction<String> privilegedAction = () -> System.getProperty("com.sun.jndi.rmi.object.trustURLCodebase", "false");
/*     */     
/*  66 */     String str = AccessController.<String>doPrivileged(privilegedAction);
/*  67 */     trustURLCodebase = "true".equalsIgnoreCase(str);
/*     */   }
/*     */   
/*  70 */   Reference reference = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String SECURITY_MGR = "java.naming.rmi.security.manager";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegistryContext(String paramString, int paramInt, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  88 */     this.environment = (paramHashtable == null) ? new Hashtable<>(5) : (Hashtable)paramHashtable;
/*     */ 
/*     */     
/*  91 */     if (this.environment.get("java.naming.rmi.security.manager") != null) {
/*  92 */       installSecurityMgr();
/*     */     }
/*     */ 
/*     */     
/*  96 */     if (paramString != null && paramString.charAt(0) == '[') {
/*  97 */       paramString = paramString.substring(1, paramString.length() - 1);
/*     */     }
/*     */ 
/*     */     
/* 101 */     RMIClientSocketFactory rMIClientSocketFactory = (RMIClientSocketFactory)this.environment.get("com.sun.jndi.rmi.factory.socket");
/* 102 */     this.registry = getRegistry(paramString, paramInt, rMIClientSocketFactory);
/* 103 */     this.host = paramString;
/* 104 */     this.port = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RegistryContext(RegistryContext paramRegistryContext) {
/* 115 */     this.environment = (Hashtable<String, Object>)paramRegistryContext.environment.clone();
/* 116 */     this.registry = paramRegistryContext.registry;
/* 117 */     this.host = paramRegistryContext.host;
/* 118 */     this.port = paramRegistryContext.port;
/* 119 */     this.reference = paramRegistryContext.reference;
/*     */   }
/*     */   
/*     */   protected void finalize() {
/* 123 */     close();
/*     */   }
/*     */   public Object lookup(Name paramName) throws NamingException {
/*     */     Remote remote;
/* 127 */     if (paramName.isEmpty()) {
/* 128 */       return new RegistryContext(this);
/*     */     }
/*     */     
/*     */     try {
/* 132 */       remote = this.registry.lookup(paramName.get(0));
/* 133 */     } catch (NotBoundException notBoundException) {
/* 134 */       throw new NameNotFoundException(paramName.get(0));
/* 135 */     } catch (RemoteException remoteException) {
/* 136 */       throw (NamingException)wrapRemoteException(remoteException).fillInStackTrace();
/*     */     } 
/* 138 */     return decodeObject(remote, paramName.getPrefix(1));
/*     */   }
/*     */   
/*     */   public Object lookup(String paramString) throws NamingException {
/* 142 */     return lookup(new CompositeName(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(Name paramName, Object paramObject) throws NamingException {
/* 150 */     if (paramName.isEmpty()) {
/* 151 */       throw new InvalidNameException("RegistryContext: Cannot bind empty name");
/*     */     }
/*     */     
/*     */     try {
/* 155 */       this.registry.bind(paramName.get(0), encodeObject(paramObject, paramName.getPrefix(1)));
/* 156 */     } catch (AlreadyBoundException alreadyBoundException) {
/* 157 */       NameAlreadyBoundException nameAlreadyBoundException = new NameAlreadyBoundException(paramName.get(0));
/* 158 */       nameAlreadyBoundException.setRootCause(alreadyBoundException);
/* 159 */       throw nameAlreadyBoundException;
/* 160 */     } catch (RemoteException remoteException) {
/* 161 */       throw (NamingException)wrapRemoteException(remoteException).fillInStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void bind(String paramString, Object paramObject) throws NamingException {
/* 166 */     bind(new CompositeName(paramString), paramObject);
/*     */   }
/*     */   
/*     */   public void rebind(Name paramName, Object paramObject) throws NamingException {
/* 170 */     if (paramName.isEmpty()) {
/* 171 */       throw new InvalidNameException("RegistryContext: Cannot rebind empty name");
/*     */     }
/*     */     
/*     */     try {
/* 175 */       this.registry.rebind(paramName.get(0), encodeObject(paramObject, paramName.getPrefix(1)));
/* 176 */     } catch (RemoteException remoteException) {
/* 177 */       throw (NamingException)wrapRemoteException(remoteException).fillInStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rebind(String paramString, Object paramObject) throws NamingException {
/* 182 */     rebind(new CompositeName(paramString), paramObject);
/*     */   }
/*     */   
/*     */   public void unbind(Name paramName) throws NamingException {
/* 186 */     if (paramName.isEmpty()) {
/* 187 */       throw new InvalidNameException("RegistryContext: Cannot unbind empty name");
/*     */     }
/*     */     
/*     */     try {
/* 191 */       this.registry.unbind(paramName.get(0));
/* 192 */     } catch (NotBoundException notBoundException) {
/*     */     
/* 194 */     } catch (RemoteException remoteException) {
/* 195 */       throw (NamingException)wrapRemoteException(remoteException).fillInStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unbind(String paramString) throws NamingException {
/* 200 */     unbind(new CompositeName(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rename(Name paramName1, Name paramName2) throws NamingException {
/* 208 */     bind(paramName2, lookup(paramName1));
/* 209 */     unbind(paramName1);
/*     */   }
/*     */   
/*     */   public void rename(String paramString1, String paramString2) throws NamingException {
/* 213 */     rename(new CompositeName(paramString1), new CompositeName(paramString2));
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(Name paramName) throws NamingException {
/* 218 */     if (!paramName.isEmpty()) {
/* 219 */       throw new InvalidNameException("RegistryContext: can only list \"\"");
/*     */     }
/*     */     
/*     */     try {
/* 223 */       String[] arrayOfString = this.registry.list();
/* 224 */       return new NameClassPairEnumeration(arrayOfString);
/* 225 */     } catch (RemoteException remoteException) {
/* 226 */       throw (NamingException)wrapRemoteException(remoteException).fillInStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(String paramString) throws NamingException {
/* 232 */     return list(new CompositeName(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(Name paramName) throws NamingException {
/* 238 */     if (!paramName.isEmpty()) {
/* 239 */       throw new InvalidNameException("RegistryContext: can only list \"\"");
/*     */     }
/*     */     
/*     */     try {
/* 243 */       String[] arrayOfString = this.registry.list();
/* 244 */       return new BindingEnumeration(this, arrayOfString);
/* 245 */     } catch (RemoteException remoteException) {
/* 246 */       throw (NamingException)wrapRemoteException(remoteException).fillInStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(String paramString) throws NamingException {
/* 252 */     return listBindings(new CompositeName(paramString));
/*     */   }
/*     */   
/*     */   public void destroySubcontext(Name paramName) throws NamingException {
/* 256 */     throw new OperationNotSupportedException();
/*     */   }
/*     */   
/*     */   public void destroySubcontext(String paramString) throws NamingException {
/* 260 */     throw new OperationNotSupportedException();
/*     */   }
/*     */   
/*     */   public Context createSubcontext(Name paramName) throws NamingException {
/* 264 */     throw new OperationNotSupportedException();
/*     */   }
/*     */   
/*     */   public Context createSubcontext(String paramString) throws NamingException {
/* 268 */     throw new OperationNotSupportedException();
/*     */   }
/*     */   
/*     */   public Object lookupLink(Name paramName) throws NamingException {
/* 272 */     return lookup(paramName);
/*     */   }
/*     */   
/*     */   public Object lookupLink(String paramString) throws NamingException {
/* 276 */     return lookup(paramString);
/*     */   }
/*     */   
/*     */   public NameParser getNameParser(Name paramName) throws NamingException {
/* 280 */     return nameParser;
/*     */   }
/*     */   
/*     */   public NameParser getNameParser(String paramString) throws NamingException {
/* 284 */     return nameParser;
/*     */   }
/*     */   
/*     */   public Name composeName(Name paramName1, Name paramName2) throws NamingException {
/* 288 */     Name name = (Name)paramName2.clone();
/* 289 */     return name.addAll(paramName1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String composeName(String paramString1, String paramString2) throws NamingException {
/* 295 */     return composeName(new CompositeName(paramString1), new CompositeName(paramString2))
/* 296 */       .toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object removeFromEnvironment(String paramString) throws NamingException {
/* 302 */     return this.environment.remove(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object addToEnvironment(String paramString, Object paramObject) throws NamingException {
/* 308 */     if (paramString.equals("java.naming.rmi.security.manager")) {
/* 309 */       installSecurityMgr();
/*     */     }
/* 311 */     return this.environment.put(paramString, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public Hashtable<String, Object> getEnvironment() throws NamingException {
/* 316 */     return (Hashtable<String, Object>)this.environment.clone();
/*     */   }
/*     */   
/*     */   public void close() {
/* 320 */     this.environment = null;
/* 321 */     this.registry = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNameInNamespace() {
/* 327 */     return "";
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
/*     */   
/*     */   public Reference getReference() throws NamingException {
/* 342 */     if (this.reference != null) {
/* 343 */       return (Reference)this.reference.clone();
/*     */     }
/* 345 */     if (this.host == null || this.host.equals("localhost")) {
/* 346 */       throw new ConfigurationException("Cannot create a reference for an RMI registry whose host was unspecified or specified as \"localhost\"");
/*     */     }
/*     */ 
/*     */     
/* 350 */     String str = "rmi://";
/*     */ 
/*     */     
/* 353 */     str = (this.host.indexOf(":") > -1) ? (str + "[" + this.host + "]") : (str + this.host);
/*     */     
/* 355 */     if (this.port > 0) {
/* 356 */       str = str + ":" + Integer.toString(this.port);
/*     */     }
/* 358 */     StringRefAddr stringRefAddr = new StringRefAddr("URL", str);
/*     */     
/* 360 */     return new Reference(RegistryContext.class.getName(), stringRefAddr, RegistryContextFactory.class
/*     */         
/* 362 */         .getName(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NamingException wrapRemoteException(RemoteException paramRemoteException) {
/*     */     NamingException namingException;
/* 374 */     if (paramRemoteException instanceof java.rmi.ConnectException) {
/* 375 */       namingException = new ServiceUnavailableException();
/*     */     }
/* 377 */     else if (paramRemoteException instanceof java.rmi.AccessException) {
/* 378 */       namingException = new NoPermissionException();
/*     */     }
/* 380 */     else if (paramRemoteException instanceof java.rmi.StubNotFoundException || paramRemoteException instanceof java.rmi.UnknownHostException || paramRemoteException instanceof java.rmi.server.SocketSecurityException) {
/*     */ 
/*     */       
/* 383 */       namingException = new ConfigurationException();
/*     */     }
/* 385 */     else if (paramRemoteException instanceof java.rmi.server.ExportException || paramRemoteException instanceof java.rmi.ConnectIOException || paramRemoteException instanceof java.rmi.MarshalException || paramRemoteException instanceof java.rmi.UnmarshalException || paramRemoteException instanceof java.rmi.NoSuchObjectException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 390 */       namingException = new CommunicationException();
/*     */     }
/* 392 */     else if (paramRemoteException instanceof java.rmi.ServerException && paramRemoteException.detail instanceof RemoteException) {
/*     */       
/* 394 */       namingException = wrapRemoteException((RemoteException)paramRemoteException.detail);
/*     */     } else {
/*     */       
/* 397 */       namingException = new NamingException();
/*     */     } 
/* 399 */     namingException.setRootCause(paramRemoteException);
/* 400 */     return namingException;
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
/*     */   
/*     */   private static Registry getRegistry(String paramString, int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory) throws NamingException {
/*     */     try {
/* 416 */       if (paramRMIClientSocketFactory == null) {
/* 417 */         return LocateRegistry.getRegistry(paramString, paramInt);
/*     */       }
/* 419 */       return LocateRegistry.getRegistry(paramString, paramInt, paramRMIClientSocketFactory);
/*     */     }
/* 421 */     catch (RemoteException remoteException) {
/* 422 */       throw (NamingException)wrapRemoteException(remoteException).fillInStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void installSecurityMgr() {
/*     */     try {
/* 433 */       System.setSecurityManager(new RMISecurityManager());
/* 434 */     } catch (Exception exception) {}
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
/*     */ 
/*     */   
/*     */   private Remote encodeObject(Object paramObject, Name paramName) throws NamingException, RemoteException {
/* 450 */     paramObject = NamingManager.getStateToBind(paramObject, paramName, this, this.environment);
/*     */     
/* 452 */     if (paramObject instanceof Remote) {
/* 453 */       return (Remote)paramObject;
/*     */     }
/* 455 */     if (paramObject instanceof Reference) {
/* 456 */       return new ReferenceWrapper((Reference)paramObject);
/*     */     }
/* 458 */     if (paramObject instanceof Referenceable) {
/* 459 */       return new ReferenceWrapper(((Referenceable)paramObject).getReference());
/*     */     }
/* 461 */     throw new IllegalArgumentException("RegistryContext: object to bind must be Remote, Reference, or Referenceable");
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
/*     */   private Object decodeObject(Remote paramRemote, Name paramName) throws NamingException {
/*     */     try {
/* 476 */       Reference reference1 = (Reference)((paramRemote instanceof RemoteReference) ? ((RemoteReference)paramRemote).getReference() : paramRemote);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 486 */       Reference reference2 = null;
/* 487 */       if (reference1 instanceof Reference) {
/* 488 */         reference2 = reference1;
/* 489 */       } else if (reference1 instanceof Referenceable) {
/* 490 */         reference2 = ((Referenceable)reference1).getReference();
/*     */       } 
/*     */       
/* 493 */       if (reference2 != null && reference2.getFactoryClassLocation() != null && !trustURLCodebase)
/*     */       {
/* 495 */         throw new ConfigurationException("The object factory is untrusted. Set the system property 'com.sun.jndi.rmi.object.trustURLCodebase' to 'true'.");
/*     */       }
/*     */ 
/*     */       
/* 499 */       return NamingManager.getObjectInstance(reference1, paramName, this, this.environment);
/*     */     }
/* 501 */     catch (NamingException namingException) {
/* 502 */       throw namingException;
/* 503 */     } catch (RemoteException remoteException) {
/*     */       
/* 505 */       throw (NamingException)wrapRemoteException(remoteException).fillInStackTrace();
/* 506 */     } catch (Exception exception) {
/* 507 */       NamingException namingException = new NamingException();
/* 508 */       namingException.setRootCause(exception);
/* 509 */       throw namingException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/rmi/registry/RegistryContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */