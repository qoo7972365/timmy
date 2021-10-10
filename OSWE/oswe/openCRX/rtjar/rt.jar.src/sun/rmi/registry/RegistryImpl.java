/*     */ package sun.rmi.registry;
/*     */ 
/*     */ import java.io.FilePermission;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.SocketPermission;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.net.UnknownHostException;
/*     */ import java.rmi.AccessException;
/*     */ import java.rmi.AlreadyBoundException;
/*     */ import java.rmi.NotBoundException;
/*     */ import java.rmi.RMISecurityManager;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.activation.ActivationID;
/*     */ import java.rmi.registry.Registry;
/*     */ import java.rmi.server.ObjID;
/*     */ import java.rmi.server.RMIClientSocketFactory;
/*     */ import java.rmi.server.RMIServerSocketFactory;
/*     */ import java.rmi.server.RemoteServer;
/*     */ import java.rmi.server.ServerNotActiveException;
/*     */ import java.rmi.server.UID;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSource;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.Permissions;
/*     */ import java.security.Policy;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.security.Security;
/*     */ import java.security.cert.Certificate;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import sun.misc.ObjectInputFilter;
/*     */ import sun.misc.URLClassPath;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.server.LoaderHandler;
/*     */ import sun.rmi.server.UnicastRef;
/*     */ import sun.rmi.server.UnicastServerRef;
/*     */ import sun.rmi.server.UnicastServerRef2;
/*     */ import sun.rmi.transport.LiveRef;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegistryImpl
/*     */   extends RemoteServer
/*     */   implements Registry
/*     */ {
/*     */   private static final long serialVersionUID = 4666870661827494597L;
/*  85 */   private Hashtable<String, Remote> bindings = new Hashtable<>(101);
/*     */   
/*  87 */   private static Hashtable<InetAddress, InetAddress> allowedAccessCache = new Hashtable<>(3);
/*     */   
/*     */   private static RegistryImpl registry;
/*  90 */   private static ObjID id = new ObjID(0);
/*     */   
/*  92 */   private static ResourceBundle resources = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String REGISTRY_FILTER_PROPNAME = "sun.rmi.registry.registryFilter";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int REGISTRY_MAX_DEPTH = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int REGISTRY_MAX_ARRAY_SIZE = 1000000;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   private static final ObjectInputFilter registryFilter = AccessController.<ObjectInputFilter>doPrivileged(RegistryImpl::initRegistryFilter);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ObjectInputFilter initRegistryFilter() {
/* 120 */     ObjectInputFilter objectInputFilter = null;
/* 121 */     String str = System.getProperty("sun.rmi.registry.registryFilter");
/* 122 */     if (str == null) {
/* 123 */       str = Security.getProperty("sun.rmi.registry.registryFilter");
/*     */     }
/* 125 */     if (str != null) {
/* 126 */       objectInputFilter = ObjectInputFilter.Config.createFilter2(str);
/* 127 */       Log log = Log.getLog("sun.rmi.registry", "registry", -1);
/* 128 */       if (log.isLoggable(Log.BRIEF)) {
/* 129 */         log.log(Log.BRIEF, "registryFilter = " + objectInputFilter);
/*     */       }
/*     */     } 
/* 132 */     return objectInputFilter;
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
/*     */   public RegistryImpl(int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory) throws RemoteException {
/* 144 */     this(paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory, RegistryImpl::registryFilter);
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
/*     */   public RegistryImpl(final int port, final RMIClientSocketFactory csf, final RMIServerSocketFactory ssf, final ObjectInputFilter serialFilter) throws RemoteException {
/* 158 */     if (port == 1099 && System.getSecurityManager() != null) {
/*     */       
/*     */       try {
/* 161 */         AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
/*     */               public Void run() throws RemoteException {
/* 163 */                 LiveRef liveRef = new LiveRef(RegistryImpl.id, port, csf, ssf);
/* 164 */                 RegistryImpl.this.setup(new UnicastServerRef2(liveRef, serialFilter));
/* 165 */                 return null;
/*     */               }
/*     */             }(AccessControlContext)null, new Permission[] { new SocketPermission("localhost:" + port, "listen,accept") });
/* 168 */       } catch (PrivilegedActionException privilegedActionException) {
/* 169 */         throw (RemoteException)privilegedActionException.getException();
/*     */       } 
/*     */     } else {
/* 172 */       LiveRef liveRef = new LiveRef(id, port, csf, ssf);
/* 173 */       setup(new UnicastServerRef2(liveRef, serialFilter));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegistryImpl(final int port) throws RemoteException {
/* 183 */     if (port == 1099 && System.getSecurityManager() != null) {
/*     */       
/*     */       try {
/* 186 */         AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
/*     */               public Void run() throws RemoteException {
/* 188 */                 LiveRef liveRef = new LiveRef(RegistryImpl.id, port);
/* 189 */                 RegistryImpl.this.setup(new UnicastServerRef(liveRef, param1FilterInfo -> RegistryImpl.registryFilter(param1FilterInfo)));
/* 190 */                 return null;
/*     */               }
/*     */             }(AccessControlContext)null, new Permission[] { new SocketPermission("localhost:" + port, "listen,accept") });
/* 193 */       } catch (PrivilegedActionException privilegedActionException) {
/* 194 */         throw (RemoteException)privilegedActionException.getException();
/*     */       } 
/*     */     } else {
/* 197 */       LiveRef liveRef = new LiveRef(id, port);
/* 198 */       setup(new UnicastServerRef(liveRef, RegistryImpl::registryFilter));
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
/*     */ 
/*     */   
/*     */   private void setup(UnicastServerRef paramUnicastServerRef) throws RemoteException {
/* 212 */     this.ref = paramUnicastServerRef;
/* 213 */     paramUnicastServerRef.exportObject(this, null, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Remote lookup(String paramString) throws RemoteException, NotBoundException {
/* 224 */     synchronized (this.bindings) {
/* 225 */       Remote remote = this.bindings.get(paramString);
/* 226 */       if (remote == null)
/* 227 */         throw new NotBoundException(paramString); 
/* 228 */       return remote;
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
/*     */ 
/*     */   
/*     */   public void bind(String paramString, Remote paramRemote) throws RemoteException, AlreadyBoundException, AccessException {
/* 242 */     synchronized (this.bindings) {
/* 243 */       Remote remote = this.bindings.get(paramString);
/* 244 */       if (remote != null)
/* 245 */         throw new AlreadyBoundException(paramString); 
/* 246 */       this.bindings.put(paramString, paramRemote);
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
/*     */ 
/*     */   
/*     */   public void unbind(String paramString) throws RemoteException, NotBoundException, AccessException {
/* 260 */     synchronized (this.bindings) {
/* 261 */       Remote remote = this.bindings.get(paramString);
/* 262 */       if (remote == null)
/* 263 */         throw new NotBoundException(paramString); 
/* 264 */       this.bindings.remove(paramString);
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
/*     */   
/*     */   public void rebind(String paramString, Remote paramRemote) throws RemoteException, AccessException {
/* 277 */     this.bindings.put(paramString, paramRemote);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] list() throws RemoteException {
/*     */     String[] arrayOfString;
/* 288 */     synchronized (this.bindings) {
/* 289 */       int i = this.bindings.size();
/* 290 */       arrayOfString = new String[i];
/* 291 */       Enumeration<String> enumeration = this.bindings.keys();
/* 292 */       while (--i >= 0)
/* 293 */         arrayOfString[i] = enumeration.nextElement(); 
/*     */     } 
/* 295 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkAccess(String paramString) throws AccessException {
/*     */     try {
/*     */       InetAddress inetAddress;
/* 307 */       final String clientHostName = getClientHost();
/*     */ 
/*     */       
/*     */       try {
/* 311 */         inetAddress = AccessController.<InetAddress>doPrivileged(new PrivilegedExceptionAction<InetAddress>()
/*     */             {
/*     */               
/*     */               public InetAddress run() throws UnknownHostException
/*     */               {
/* 316 */                 return InetAddress.getByName(clientHostName);
/*     */               }
/*     */             });
/* 319 */       } catch (PrivilegedActionException privilegedActionException) {
/* 320 */         throw (UnknownHostException)privilegedActionException.getException();
/*     */       } 
/*     */ 
/*     */       
/* 324 */       if (allowedAccessCache.get(inetAddress) == null)
/*     */       {
/* 326 */         if (inetAddress.isAnyLocalAddress()) {
/* 327 */           throw new AccessException(paramString + " disallowed; origin unknown");
/*     */         }
/*     */ 
/*     */         
/*     */         try {
/* 332 */           final InetAddress finalClientHost = inetAddress;
/*     */           
/* 334 */           AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */               {
/*     */ 
/*     */ 
/*     */                 
/*     */                 public Void run() throws IOException
/*     */                 {
/* 341 */                   (new ServerSocket(0, 10, finalClientHost)).close();
/* 342 */                   RegistryImpl.allowedAccessCache.put(finalClientHost, finalClientHost);
/*     */                   
/* 344 */                   return null;
/*     */                 }
/*     */               });
/* 347 */         } catch (PrivilegedActionException privilegedActionException) {
/*     */ 
/*     */           
/* 350 */           throw new AccessException(paramString + " disallowed; origin " + inetAddress + " is non-local host");
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 355 */     } catch (ServerNotActiveException serverNotActiveException) {
/*     */ 
/*     */     
/*     */     }
/* 359 */     catch (UnknownHostException unknownHostException) {
/* 360 */       throw new AccessException(paramString + " disallowed; origin is unknown host");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ObjID getID() {
/* 365 */     return id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getTextResource(String paramString) {
/* 372 */     if (resources == null) {
/*     */       try {
/* 374 */         resources = ResourceBundle.getBundle("sun.rmi.registry.resources.rmiregistry");
/*     */       }
/* 376 */       catch (MissingResourceException missingResourceException) {}
/*     */       
/* 378 */       if (resources == null)
/*     */       {
/* 380 */         return "[missing resource file: " + paramString + "]";
/*     */       }
/*     */     } 
/*     */     
/* 384 */     String str = null;
/*     */     try {
/* 386 */       str = resources.getString(paramString);
/* 387 */     } catch (MissingResourceException missingResourceException) {}
/*     */ 
/*     */     
/* 390 */     if (str == null) {
/* 391 */       return "[missing resource: " + paramString + "]";
/*     */     }
/* 393 */     return str;
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
/*     */   private static ObjectInputFilter.Status registryFilter(ObjectInputFilter.FilterInfo paramFilterInfo) {
/* 408 */     if (registryFilter != null) {
/* 409 */       ObjectInputFilter.Status status = registryFilter.checkInput(paramFilterInfo);
/* 410 */       if (status != ObjectInputFilter.Status.UNDECIDED)
/*     */       {
/* 412 */         return status;
/*     */       }
/*     */     } 
/*     */     
/* 416 */     if (paramFilterInfo.depth() > 20L) {
/* 417 */       return ObjectInputFilter.Status.REJECTED;
/*     */     }
/* 419 */     Class<String> clazz = paramFilterInfo.serialClass();
/* 420 */     if (clazz != null) {
/* 421 */       if (clazz.isArray())
/*     */       {
/* 423 */         return (paramFilterInfo.arrayLength() >= 0L && paramFilterInfo.arrayLength() > 1000000L) ? ObjectInputFilter.Status.REJECTED : ObjectInputFilter.Status.UNDECIDED;
/*     */       }
/*     */ 
/*     */       
/* 427 */       if (String.class == clazz || Number.class
/* 428 */         .isAssignableFrom(clazz) || Remote.class
/* 429 */         .isAssignableFrom(clazz) || Proxy.class
/* 430 */         .isAssignableFrom(clazz) || UnicastRef.class
/* 431 */         .isAssignableFrom(clazz) || RMIClientSocketFactory.class
/* 432 */         .isAssignableFrom(clazz) || RMIServerSocketFactory.class
/* 433 */         .isAssignableFrom(clazz) || ActivationID.class
/* 434 */         .isAssignableFrom(clazz) || UID.class
/* 435 */         .isAssignableFrom(clazz)) {
/* 436 */         return ObjectInputFilter.Status.ALLOWED;
/*     */       }
/* 438 */       return ObjectInputFilter.Status.REJECTED;
/*     */     } 
/*     */     
/* 441 */     return ObjectInputFilter.Status.UNDECIDED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 452 */     if (System.getSecurityManager() == null) {
/* 453 */       System.setSecurityManager(new RMISecurityManager());
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
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 470 */       String str = System.getProperty("env.class.path");
/* 471 */       if (str == null) {
/* 472 */         str = ".";
/*     */       }
/* 474 */       URL[] arrayOfURL = URLClassPath.pathToURLs(str);
/* 475 */       URLClassLoader uRLClassLoader = new URLClassLoader(arrayOfURL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 482 */       LoaderHandler.registerCodebaseLoader(uRLClassLoader);
/*     */       
/* 484 */       Thread.currentThread().setContextClassLoader(uRLClassLoader);
/*     */       
/* 486 */       final boolean regPort = (paramArrayOfString.length >= 1) ? Integer.parseInt(paramArrayOfString[0]) : true;
/*     */       
/*     */       try {
/* 489 */         registry = AccessController.<RegistryImpl>doPrivileged(new PrivilegedExceptionAction<RegistryImpl>()
/*     */             {
/*     */               public RegistryImpl run() throws RemoteException {
/* 492 */                 return new RegistryImpl(regPort);
/*     */               }
/* 494 */             },  getAccessControlContext(bool));
/* 495 */       } catch (PrivilegedActionException privilegedActionException) {
/* 496 */         throw (RemoteException)privilegedActionException.getException();
/*     */       } 
/*     */       
/*     */       while (true) {
/*     */         try {
/*     */           while (true)
/* 502 */             Thread.sleep(Long.MAX_VALUE);  break;
/* 503 */         } catch (InterruptedException interruptedException) {}
/*     */       }
/*     */     
/* 506 */     } catch (NumberFormatException numberFormatException) {
/* 507 */       System.err.println(MessageFormat.format(
/* 508 */             getTextResource("rmiregistry.port.badnumber"), new Object[] { paramArrayOfString[0] }));
/*     */       
/* 510 */       System.err.println(MessageFormat.format(
/* 511 */             getTextResource("rmiregistry.usage"), new Object[] { "rmiregistry" }));
/*     */     }
/* 513 */     catch (Exception exception) {
/* 514 */       exception.printStackTrace();
/*     */     } 
/* 516 */     System.exit(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AccessControlContext getAccessControlContext(int paramInt) {
/* 526 */     PermissionCollection permissionCollection = AccessController.<PermissionCollection>doPrivileged(new PrivilegedAction<PermissionCollection>()
/*     */         {
/*     */           public PermissionCollection run() {
/* 529 */             CodeSource codeSource = new CodeSource(null, (Certificate[])null);
/*     */             
/* 531 */             Policy policy = Policy.getPolicy();
/* 532 */             if (policy != null) {
/* 533 */               return policy.getPermissions(codeSource);
/*     */             }
/* 535 */             return new Permissions();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 545 */     permissionCollection.add(new SocketPermission("*", "connect,accept"));
/* 546 */     permissionCollection.add(new SocketPermission("localhost:" + paramInt, "listen,accept"));
/*     */     
/* 548 */     permissionCollection.add(new RuntimePermission("accessClassInPackage.sun.jvmstat.*"));
/* 549 */     permissionCollection.add(new RuntimePermission("accessClassInPackage.sun.jvm.hotspot.*"));
/*     */     
/* 551 */     permissionCollection.add(new FilePermission("<<ALL FILES>>", "read"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 557 */     ProtectionDomain protectionDomain = new ProtectionDomain(new CodeSource(null, (Certificate[])null), permissionCollection);
/*     */ 
/*     */     
/* 560 */     return new AccessControlContext(new ProtectionDomain[] { protectionDomain });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/registry/RegistryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */