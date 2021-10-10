/*     */ package javax.rmi.CORBA;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.GetPropertyAction;
/*     */ import java.io.SerializablePermission;
/*     */ import java.net.MalformedURLException;
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.RMIClassLoader;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Properties;
/*     */ import org.omg.CORBA.INITIALIZE;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Util
/*     */ {
/*  71 */   private static final UtilDelegate utilDelegate = (UtilDelegate)createDelegate("javax.rmi.CORBA.UtilClass");
/*  72 */   private static boolean allowCustomValueHandler = readAllowCustomValueHandlerProperty(); private static final String UtilClassKey = "javax.rmi.CORBA.UtilClass";
/*     */   private static final String ALLOW_CREATEVALUEHANDLER_PROP = "jdk.rmi.CORBA.allowCustomValueHandler";
/*     */   
/*     */   private static boolean readAllowCustomValueHandlerProperty() {
/*  76 */     return (
/*  77 */       (Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */         {
/*     */           public Boolean run() {
/*  80 */             return Boolean.valueOf(Boolean.getBoolean("jdk.rmi.CORBA.allowCustomValueHandler"));
/*     */           }
/*     */         })).booleanValue();
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
/*     */   public static RemoteException mapSystemException(SystemException paramSystemException) {
/*  94 */     if (utilDelegate != null) {
/*  95 */       return utilDelegate.mapSystemException(paramSystemException);
/*     */     }
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeAny(OutputStream paramOutputStream, Object paramObject) {
/* 107 */     if (utilDelegate != null) {
/* 108 */       utilDelegate.writeAny(paramOutputStream, paramObject);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readAny(InputStream paramInputStream) {
/* 119 */     if (utilDelegate != null) {
/* 120 */       return utilDelegate.readAny(paramInputStream);
/*     */     }
/* 122 */     return null;
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
/*     */   public static void writeRemoteObject(OutputStream paramOutputStream, Object paramObject) {
/* 138 */     if (utilDelegate != null) {
/* 139 */       utilDelegate.writeRemoteObject(paramOutputStream, paramObject);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeAbstractObject(OutputStream paramOutputStream, Object paramObject) {
/* 158 */     if (utilDelegate != null) {
/* 159 */       utilDelegate.writeAbstractObject(paramOutputStream, paramObject);
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
/*     */   public static void registerTarget(Tie paramTie, Remote paramRemote) {
/* 172 */     if (utilDelegate != null) {
/* 173 */       utilDelegate.registerTarget(paramTie, paramRemote);
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
/*     */   
/*     */   public static void unexportObject(Remote paramRemote) throws NoSuchObjectException {
/* 188 */     if (utilDelegate != null) {
/* 189 */       utilDelegate.unexportObject(paramRemote);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Tie getTie(Remote paramRemote) {
/* 200 */     if (utilDelegate != null) {
/* 201 */       return utilDelegate.getTie(paramRemote);
/*     */     }
/* 203 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ValueHandler createValueHandler() {
/* 214 */     isCustomSerializationPermitted();
/*     */     
/* 216 */     if (utilDelegate != null) {
/* 217 */       return utilDelegate.createValueHandler();
/*     */     }
/* 219 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getCodebase(Class paramClass) {
/* 228 */     if (utilDelegate != null) {
/* 229 */       return utilDelegate.getCodebase(paramClass);
/*     */     }
/* 231 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class loadClass(String paramString1, String paramString2, ClassLoader paramClassLoader) throws ClassNotFoundException {
/* 264 */     if (utilDelegate != null) {
/* 265 */       return utilDelegate.loadClass(paramString1, paramString2, paramClassLoader);
/*     */     }
/* 267 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isLocal(Stub paramStub) throws RemoteException {
/* 293 */     if (utilDelegate != null) {
/* 294 */       return utilDelegate.isLocal(paramStub);
/*     */     }
/*     */     
/* 297 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RemoteException wrapException(Throwable paramThrowable) {
/* 308 */     if (utilDelegate != null) {
/* 309 */       return utilDelegate.wrapException(paramThrowable);
/*     */     }
/*     */     
/* 312 */     return null;
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
/*     */   public static Object[] copyObjects(Object[] paramArrayOfObject, ORB paramORB) throws RemoteException {
/* 327 */     if (utilDelegate != null) {
/* 328 */       return utilDelegate.copyObjects(paramArrayOfObject, paramORB);
/*     */     }
/*     */     
/* 331 */     return null;
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
/*     */   public static Object copyObject(Object paramObject, ORB paramORB) throws RemoteException {
/* 345 */     if (utilDelegate != null) {
/* 346 */       return utilDelegate.copyObject(paramObject, paramORB);
/*     */     }
/* 348 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object createDelegate(String paramString) {
/* 358 */     String str = AccessController.<String>doPrivileged((PrivilegedAction<String>)new GetPropertyAction(paramString));
/* 359 */     if (str == null) {
/* 360 */       Properties properties = getORBPropertiesFile();
/* 361 */       if (properties != null) {
/* 362 */         str = properties.getProperty(paramString);
/*     */       }
/*     */     } 
/*     */     
/* 366 */     if (str == null) {
/* 367 */       return new com.sun.corba.se.impl.javax.rmi.CORBA.Util();
/*     */     }
/*     */     
/*     */     try {
/* 371 */       return loadDelegateClass(str).newInstance();
/* 372 */     } catch (ClassNotFoundException classNotFoundException) {
/* 373 */       INITIALIZE iNITIALIZE = new INITIALIZE("Cannot instantiate " + str);
/* 374 */       iNITIALIZE.initCause(classNotFoundException);
/* 375 */       throw iNITIALIZE;
/* 376 */     } catch (Exception exception) {
/* 377 */       INITIALIZE iNITIALIZE = new INITIALIZE("Error while instantiating" + str);
/* 378 */       iNITIALIZE.initCause(exception);
/* 379 */       throw iNITIALIZE;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class loadDelegateClass(String paramString) throws ClassNotFoundException {
/*     */     try {
/* 386 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 387 */       return Class.forName(paramString, false, classLoader);
/* 388 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 393 */         return RMIClassLoader.loadClass(paramString);
/* 394 */       } catch (MalformedURLException malformedURLException) {
/* 395 */         String str = "Could not load " + paramString + ": " + malformedURLException.toString();
/* 396 */         ClassNotFoundException classNotFoundException1 = new ClassNotFoundException(str);
/* 397 */         throw classNotFoundException1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Properties getORBPropertiesFile() {
/* 405 */     return AccessController.<Properties>doPrivileged(new GetORBPropertiesFileAction());
/*     */   }
/*     */ 
/*     */   
/*     */   private static void isCustomSerializationPermitted() {
/* 410 */     SecurityManager securityManager = System.getSecurityManager();
/* 411 */     if (!allowCustomValueHandler && 
/* 412 */       securityManager != null)
/*     */     {
/*     */ 
/*     */       
/* 416 */       securityManager.checkPermission(new SerializablePermission("enableCustomValueHandler"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/rmi/CORBA/Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */