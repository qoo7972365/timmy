/*     */ package sun.applet;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.net.URLClassLoader;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import sun.awt.AWTSecurityManager;
/*     */ import sun.awt.AppContext;
/*     */ import sun.security.util.SecurityConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AppletSecurity
/*     */   extends AWTSecurityManager
/*     */ {
/*  57 */   private static Field facc = null;
/*     */ 
/*     */   
/*  60 */   private static Field fcontext = null; private HashSet restrictedPackages; private boolean inThreadGroupCheck;
/*     */   
/*     */   static {
/*     */     try {
/*  64 */       facc = URLClassLoader.class.getDeclaredField("acc");
/*  65 */       facc.setAccessible(true);
/*  66 */       fcontext = AccessControlContext.class.getDeclaredField("context");
/*  67 */       fcontext.setAccessible(true);
/*  68 */     } catch (NoSuchFieldException noSuchFieldException) {
/*  69 */       throw new UnsupportedOperationException(noSuchFieldException);
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
/*     */   public AppletSecurity() {
/*  82 */     this.restrictedPackages = new HashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     this.inThreadGroupCheck = false; reset();
/*     */   } public void reset() { this.restrictedPackages.clear(); AccessController.doPrivileged(new PrivilegedAction() { public Object run() { Enumeration<?> enumeration = System.getProperties().propertyNames(); while (enumeration.hasMoreElements()) { String str = (String)enumeration.nextElement(); if (str != null && str.startsWith("package.restrict.access.")) {
/*     */                 String str1 = System.getProperty(str); if (str1 != null && str1.equalsIgnoreCase("true")) {
/*     */                   String str2 = str.substring(24); AppletSecurity.this.restrictedPackages.add(str2);
/*     */                 } 
/*     */               }  }
/*     */              return null; } }
/* 240 */       ); } public synchronized void checkAccess(ThreadGroup paramThreadGroup) { if (this.inThreadGroupCheck) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 245 */       checkPermission(SecurityConstants.MODIFY_THREADGROUP_PERMISSION);
/*     */     } else {
/*     */       try {
/* 248 */         this.inThreadGroupCheck = true;
/* 249 */         if (!inThreadGroup(paramThreadGroup)) {
/* 250 */           checkPermission(SecurityConstants.MODIFY_THREADGROUP_PERMISSION);
/*     */         }
/*     */       } finally {
/* 253 */         this.inThreadGroupCheck = false;
/*     */       } 
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkPackageAccess(String paramString) {
/* 281 */     super.checkPackageAccess(paramString);
/*     */ 
/*     */     
/* 284 */     for (String str : this.restrictedPackages)
/*     */     
/*     */     { 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 291 */       if (paramString.equals(str) || paramString.startsWith(str + "."))
/*     */       {
/* 293 */         checkPermission(new RuntimePermission("accessClassInPackage." + paramString)); }  } 
/*     */   }
/*     */   private AppletClassLoader currentAppletClassLoader() { ClassLoader classLoader = currentClassLoader(); if (classLoader == null || classLoader instanceof AppletClassLoader)
/*     */       return (AppletClassLoader)classLoader;  Class[] arrayOfClass = getClassContext(); byte b; for (b = 0; b < arrayOfClass.length; b++) { classLoader = arrayOfClass[b].getClassLoader(); if (classLoader instanceof AppletClassLoader)
/*     */         return (AppletClassLoader)classLoader;  }  for (b = 0; b < arrayOfClass.length; b++) { final ClassLoader currentLoader = arrayOfClass[b].getClassLoader(); if (classLoader1 instanceof URLClassLoader) { classLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */               public Object run() { AccessControlContext accessControlContext = null; ProtectionDomain[] arrayOfProtectionDomain = null; try { accessControlContext = (AccessControlContext)AppletSecurity.facc.get(currentLoader); if (accessControlContext == null)
/*     */                     return null;  arrayOfProtectionDomain = (ProtectionDomain[])AppletSecurity.fcontext.get(accessControlContext); if (arrayOfProtectionDomain == null)
/*     */                     return null;  } catch (Exception exception) { throw new UnsupportedOperationException(exception); }  for (byte b = 0; b < arrayOfProtectionDomain.length; b++) { ClassLoader classLoader = arrayOfProtectionDomain[b].getClassLoader(); if (classLoader instanceof AppletClassLoader)
/*     */                     return classLoader;  }
/*     */                  return null; }
/*     */             }); if (classLoader != null)
/*     */           return (AppletClassLoader)classLoader;  }
/*     */        }
/*     */      classLoader = Thread.currentThread().getContextClassLoader(); if (classLoader instanceof AppletClassLoader)
/*     */       return (AppletClassLoader)classLoader;  return (AppletClassLoader)null; }
/*     */   protected boolean inThreadGroup(ThreadGroup paramThreadGroup) { if (currentAppletClassLoader() == null)
/*     */       return false;  return getThreadGroup().parentOf(paramThreadGroup); } protected boolean inThreadGroup(Thread paramThread) { return inThreadGroup(paramThread.getThreadGroup()); } public void checkAccess(Thread paramThread) { if (paramThread.getState() != Thread.State.TERMINATED && !inThreadGroup(paramThread))
/* 310 */       checkPermission(SecurityConstants.MODIFY_THREAD_PERMISSION);  } public void checkAwtEventQueueAccess() { AppContext appContext = AppContext.getAppContext();
/* 311 */     AppletClassLoader appletClassLoader = currentAppletClassLoader();
/*     */     
/* 313 */     if (AppContext.isMainContext(appContext) && appletClassLoader != null)
/*     */     {
/*     */ 
/*     */       
/* 317 */       checkPermission(SecurityConstants.AWT.CHECK_AWT_EVENTQUEUE_PERMISSION);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThreadGroup getThreadGroup() {
/* 329 */     AppletClassLoader appletClassLoader = currentAppletClassLoader();
/*     */     
/* 331 */     ThreadGroup threadGroup = (appletClassLoader == null) ? null : appletClassLoader.getThreadGroup();
/* 332 */     if (threadGroup != null) {
/* 333 */       return threadGroup;
/*     */     }
/* 335 */     return super.getThreadGroup();
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
/*     */   public AppContext getAppContext() {
/* 352 */     AppletClassLoader appletClassLoader = currentAppletClassLoader();
/*     */     
/* 354 */     if (appletClassLoader == null) {
/* 355 */       return null;
/*     */     }
/* 357 */     AppContext appContext = appletClassLoader.getAppContext();
/*     */ 
/*     */ 
/*     */     
/* 361 */     if (appContext == null) {
/* 362 */       throw new SecurityException("Applet classloader has invalid AppContext");
/*     */     }
/*     */     
/* 365 */     return appContext;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/applet/AppletSecurity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */