/*     */ package javax.rmi;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.GetPropertyAction;
/*     */ import java.net.MalformedURLException;
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.RMIClassLoader;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Properties;
/*     */ import javax.rmi.CORBA.PortableRemoteObjectDelegate;
/*     */ import org.omg.CORBA.INITIALIZE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PortableRemoteObject
/*     */ {
/*  75 */   private static final PortableRemoteObjectDelegate proDelegate = (PortableRemoteObjectDelegate)createDelegate("javax.rmi.CORBA.PortableRemoteObjectClass");
/*     */ 
/*     */   
/*     */   private static final String PortableRemoteObjectClassKey = "javax.rmi.CORBA.PortableRemoteObjectClass";
/*     */ 
/*     */ 
/*     */   
/*     */   protected PortableRemoteObject() throws RemoteException {
/*  83 */     if (proDelegate != null) {
/*  84 */       exportObject((Remote)this);
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
/*     */   public static void exportObject(Remote paramRemote) throws RemoteException {
/*  99 */     if (proDelegate != null) {
/* 100 */       proDelegate.exportObject(paramRemote);
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
/*     */   public static Remote toStub(Remote paramRemote) throws NoSuchObjectException {
/* 115 */     if (proDelegate != null) {
/* 116 */       return proDelegate.toStub(paramRemote);
/*     */     }
/* 118 */     return null;
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
/*     */   public static void unexportObject(Remote paramRemote) throws NoSuchObjectException {
/* 131 */     if (proDelegate != null) {
/* 132 */       proDelegate.unexportObject(paramRemote);
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
/*     */   public static Object narrow(Object paramObject, Class paramClass) throws ClassCastException {
/* 149 */     if (proDelegate != null) {
/* 150 */       return proDelegate.narrow(paramObject, paramClass);
/*     */     }
/* 152 */     return null;
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
/*     */   public static void connect(Remote paramRemote1, Remote paramRemote2) throws RemoteException {
/* 171 */     if (proDelegate != null) {
/* 172 */       proDelegate.connect(paramRemote1, paramRemote2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object createDelegate(String paramString) {
/* 183 */     String str = AccessController.<String>doPrivileged((PrivilegedAction<String>)new GetPropertyAction(paramString));
/* 184 */     if (str == null) {
/* 185 */       Properties properties = getORBPropertiesFile();
/* 186 */       if (properties != null) {
/* 187 */         str = properties.getProperty(paramString);
/*     */       }
/*     */     } 
/* 190 */     if (str == null) {
/* 191 */       return new com.sun.corba.se.impl.javax.rmi.PortableRemoteObject();
/*     */     }
/*     */     
/*     */     try {
/* 195 */       return loadDelegateClass(str).newInstance();
/* 196 */     } catch (ClassNotFoundException classNotFoundException) {
/* 197 */       INITIALIZE iNITIALIZE = new INITIALIZE("Cannot instantiate " + str);
/* 198 */       iNITIALIZE.initCause(classNotFoundException);
/* 199 */       throw iNITIALIZE;
/* 200 */     } catch (Exception exception) {
/* 201 */       INITIALIZE iNITIALIZE = new INITIALIZE("Error while instantiating" + str);
/* 202 */       iNITIALIZE.initCause(exception);
/* 203 */       throw iNITIALIZE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class loadDelegateClass(String paramString) throws ClassNotFoundException {
/*     */     try {
/* 211 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 212 */       return Class.forName(paramString, false, classLoader);
/* 213 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 218 */         return RMIClassLoader.loadClass(paramString);
/* 219 */       } catch (MalformedURLException malformedURLException) {
/* 220 */         String str = "Could not load " + paramString + ": " + malformedURLException.toString();
/* 221 */         ClassNotFoundException classNotFoundException1 = new ClassNotFoundException(str);
/* 222 */         throw classNotFoundException1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Properties getORBPropertiesFile() {
/* 230 */     return AccessController.<Properties>doPrivileged(new GetORBPropertiesFileAction());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/rmi/PortableRemoteObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */