/*     */ package sun.rmi.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.rmi.server.RMIClassLoader;
/*     */ import java.security.AccessControlException;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import sun.misc.ObjectStreamClassValidator;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.misc.VM;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MarshalInputStream
/*     */   extends ObjectInputStream
/*     */ {
/*  60 */   private volatile StreamChecker streamChecker = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static final boolean useCodebaseOnlyProperty = !((String)AccessController.<String>doPrivileged(new GetPropertyAction("java.rmi.server.useCodebaseOnly", "true"))).equalsIgnoreCase("false");
/*     */ 
/*     */   
/*  78 */   protected static Map<String, Class<?>> permittedSunClasses = new HashMap<>(3);
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean skipDefaultResolveClass = false;
/*     */ 
/*     */   
/*  85 */   private final Map<Object, Runnable> doneCallbacks = new HashMap<>(3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private boolean useCodebaseOnly = useCodebaseOnlyProperty;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 110 */       String str1 = "sun.rmi.server.Activation$ActivationSystemImpl_Stub";
/*     */       
/* 112 */       String str2 = "sun.rmi.registry.RegistryImpl_Stub";
/*     */       
/* 114 */       permittedSunClasses.put(str1, Class.forName(str1));
/* 115 */       permittedSunClasses.put(str2, Class.forName(str2));
/*     */     }
/* 117 */     catch (ClassNotFoundException classNotFoundException) {
/* 118 */       throw new NoClassDefFoundError("Missing system class: " + classNotFoundException
/* 119 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MarshalInputStream(InputStream paramInputStream) throws IOException, StreamCorruptedException {
/* 129 */     super(paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Runnable getDoneCallback(Object paramObject) {
/* 138 */     return this.doneCallbacks.get(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDoneCallback(Object paramObject, Runnable paramRunnable) {
/* 148 */     this.doneCallbacks.put(paramObject, paramRunnable);
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
/*     */   public void done() {
/* 161 */     Iterator<Runnable> iterator = this.doneCallbacks.values().iterator();
/* 162 */     while (iterator.hasNext()) {
/* 163 */       Runnable runnable = iterator.next();
/* 164 */       runnable.run();
/*     */     } 
/* 166 */     this.doneCallbacks.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 173 */     done();
/* 174 */     super.close();
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
/*     */   protected Class<?> resolveClass(ObjectStreamClass paramObjectStreamClass) throws IOException, ClassNotFoundException {
/* 189 */     Object object = readLocation();
/*     */     
/* 191 */     String str1 = paramObjectStreamClass.getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     ClassLoader classLoader = this.skipDefaultResolveClass ? null : latestUserDefinedLoader();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     String str2 = null;
/* 214 */     if (!this.useCodebaseOnly && object instanceof String) {
/* 215 */       str2 = (String)object;
/*     */     }
/*     */     
/*     */     try {
/* 219 */       return RMIClassLoader.loadClass(str2, str1, classLoader);
/*     */     }
/* 221 */     catch (AccessControlException accessControlException) {
/* 222 */       return checkSunClass(str1, accessControlException);
/* 223 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 229 */         if (Character.isLowerCase(str1.charAt(0)) && str1
/* 230 */           .indexOf('.') == -1)
/*     */         {
/* 232 */           return super.resolveClass(paramObjectStreamClass);
/*     */         }
/* 234 */       } catch (ClassNotFoundException classNotFoundException1) {}
/*     */       
/* 236 */       throw classNotFoundException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class<?> resolveProxyClass(String[] paramArrayOfString) throws IOException, ClassNotFoundException {
/* 247 */     StreamChecker streamChecker = this.streamChecker;
/* 248 */     if (streamChecker != null) {
/* 249 */       streamChecker.checkProxyInterfaceNames(paramArrayOfString);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 255 */     Object object = readLocation();
/*     */ 
/*     */     
/* 258 */     ClassLoader classLoader = this.skipDefaultResolveClass ? null : latestUserDefinedLoader();
/*     */     
/* 260 */     String str = null;
/* 261 */     if (!this.useCodebaseOnly && object instanceof String) {
/* 262 */       str = (String)object;
/*     */     }
/*     */     
/* 265 */     return RMIClassLoader.loadProxyClass(str, paramArrayOfString, classLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ClassLoader latestUserDefinedLoader() {
/* 276 */     return VM.latestUserDefinedLoader();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> checkSunClass(String paramString, AccessControlException paramAccessControlException) throws AccessControlException {
/* 287 */     Permission permission = paramAccessControlException.getPermission();
/* 288 */     String str = null;
/* 289 */     if (permission != null) {
/* 290 */       str = permission.getName();
/*     */     }
/*     */     
/* 293 */     Class<?> clazz = permittedSunClasses.get(paramString);
/*     */ 
/*     */     
/* 296 */     if (str == null || clazz == null || (
/*     */       
/* 298 */       !str.equals("accessClassInPackage.sun.rmi.server") && 
/* 299 */       !str.equals("accessClassInPackage.sun.rmi.registry")))
/*     */     {
/* 301 */       throw paramAccessControlException;
/*     */     }
/*     */     
/* 304 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object readLocation() throws IOException, ClassNotFoundException {
/* 315 */     return readObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void skipDefaultResolveClass() {
/* 323 */     this.skipDefaultResolveClass = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void useCodebaseOnly() {
/* 331 */     this.useCodebaseOnly = true;
/*     */   }
/*     */   
/*     */   synchronized void setStreamChecker(StreamChecker paramStreamChecker) {
/* 335 */     this.streamChecker = paramStreamChecker;
/* 336 */     SharedSecrets.getJavaObjectInputStreamAccess().setValidator(this, paramStreamChecker);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
/* 341 */     ObjectStreamClass objectStreamClass = super.readClassDescriptor();
/*     */     
/* 343 */     validateDesc(objectStreamClass);
/*     */     
/* 345 */     return objectStreamClass;
/*     */   }
/*     */   
/*     */   private void validateDesc(ObjectStreamClass paramObjectStreamClass) {
/*     */     StreamChecker streamChecker;
/* 350 */     synchronized (this) {
/* 351 */       streamChecker = this.streamChecker;
/*     */     } 
/* 353 */     if (streamChecker != null)
/* 354 */       streamChecker.validateDescriptor(paramObjectStreamClass); 
/*     */   }
/*     */   
/*     */   static interface StreamChecker extends ObjectStreamClassValidator {
/*     */     void checkProxyInterfaceNames(String[] param1ArrayOfString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/server/MarshalInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */