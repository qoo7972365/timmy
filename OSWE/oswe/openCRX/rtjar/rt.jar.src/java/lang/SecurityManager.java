/*      */ package java.lang;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.FilePermission;
/*      */ import java.net.InetAddress;
/*      */ import java.net.SocketPermission;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.Permission;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.Security;
/*      */ import java.security.SecurityPermission;
/*      */ import java.util.PropertyPermission;
/*      */ import java.util.StringTokenizer;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SecurityManager
/*      */ {
/*      */   @Deprecated
/*      */   protected boolean inCheck;
/*      */   private boolean initialized = false;
/*      */   
/*      */   private boolean hasAllPermission() {
/*      */     try {
/*  252 */       checkPermission(SecurityConstants.ALL_PERMISSION);
/*  253 */       return true;
/*  254 */     } catch (SecurityException securityException) {
/*  255 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean getInCheck() {
/*  273 */     return this.inCheck;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SecurityManager() {
/*  294 */     synchronized (SecurityManager.class) {
/*  295 */       SecurityManager securityManager = System.getSecurityManager();
/*  296 */       if (securityManager != null)
/*      */       {
/*      */         
/*  299 */         securityManager.checkPermission(new RuntimePermission("createSecurityManager"));
/*      */       }
/*      */       
/*  302 */       this.initialized = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected native Class[] getClassContext();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected ClassLoader currentClassLoader() {
/*  357 */     ClassLoader classLoader = currentClassLoader0();
/*  358 */     if (classLoader != null && hasAllPermission())
/*  359 */       classLoader = null; 
/*  360 */     return classLoader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private native ClassLoader currentClassLoader0();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected Class<?> currentLoadedClass() {
/*  403 */     Class<?> clazz = currentLoadedClass0();
/*  404 */     if (clazz != null && hasAllPermission())
/*  405 */       clazz = null; 
/*  406 */     return clazz;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected native int classDepth(String paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected int classLoaderDepth() {
/*  462 */     int i = classLoaderDepth0();
/*  463 */     if (i != -1)
/*  464 */       if (hasAllPermission()) {
/*  465 */         i = -1;
/*      */       } else {
/*  467 */         i--;
/*      */       }  
/*  469 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private native int classLoaderDepth0();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected boolean inClass(String paramString) {
/*  487 */     return (classDepth(paramString) >= 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected boolean inClassLoader() {
/*  504 */     return (currentClassLoader() != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getSecurityContext() {
/*  530 */     return AccessController.getContext();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkPermission(Permission paramPermission) {
/*  549 */     AccessController.checkPermission(paramPermission);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkPermission(Permission paramPermission, Object paramObject) {
/*  584 */     if (paramObject instanceof AccessControlContext) {
/*  585 */       ((AccessControlContext)paramObject).checkPermission(paramPermission);
/*      */     } else {
/*  587 */       throw new SecurityException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkCreateClassLoader() {
/*  611 */     checkPermission(SecurityConstants.CREATE_CLASSLOADER_PERMISSION);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  619 */   private static ThreadGroup rootGroup = getRootGroup();
/*      */   
/*      */   private static ThreadGroup getRootGroup() {
/*  622 */     ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
/*  623 */     while (threadGroup.getParent() != null) {
/*  624 */       threadGroup = threadGroup.getParent();
/*      */     }
/*  626 */     return threadGroup;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkAccess(Thread paramThread) {
/*  672 */     if (paramThread == null) {
/*  673 */       throw new NullPointerException("thread can't be null");
/*      */     }
/*  675 */     if (paramThread.getThreadGroup() == rootGroup) {
/*  676 */       checkPermission(SecurityConstants.MODIFY_THREAD_PERMISSION);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkAccess(ThreadGroup paramThreadGroup) {
/*  725 */     if (paramThreadGroup == null) {
/*  726 */       throw new NullPointerException("thread group can't be null");
/*      */     }
/*  728 */     if (paramThreadGroup == rootGroup) {
/*  729 */       checkPermission(SecurityConstants.MODIFY_THREADGROUP_PERMISSION);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkExit(int paramInt) {
/*  761 */     checkPermission(new RuntimePermission("exitVM." + paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkExec(String paramString) {
/*  794 */     File file = new File(paramString);
/*  795 */     if (file.isAbsolute()) {
/*  796 */       checkPermission(new FilePermission(paramString, "execute"));
/*      */     } else {
/*      */       
/*  799 */       checkPermission(new FilePermission("<<ALL FILES>>", "execute"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkLink(String paramString) {
/*  832 */     if (paramString == null) {
/*  833 */       throw new NullPointerException("library can't be null");
/*      */     }
/*  835 */     checkPermission(new RuntimePermission("loadLibrary." + paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkRead(FileDescriptor paramFileDescriptor) {
/*  861 */     if (paramFileDescriptor == null) {
/*  862 */       throw new NullPointerException("file descriptor can't be null");
/*      */     }
/*  864 */     checkPermission(new RuntimePermission("readFileDescriptor"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkRead(String paramString) {
/*  888 */     checkPermission(new FilePermission(paramString, "read"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkRead(String paramString, Object paramObject) {
/*  923 */     checkPermission(new FilePermission(paramString, "read"), paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkWrite(FileDescriptor paramFileDescriptor) {
/*  951 */     if (paramFileDescriptor == null) {
/*  952 */       throw new NullPointerException("file descriptor can't be null");
/*      */     }
/*  954 */     checkPermission(new RuntimePermission("writeFileDescriptor"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkWrite(String paramString) {
/*  979 */     checkPermission(new FilePermission(paramString, "write"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkDelete(String paramString) {
/* 1007 */     checkPermission(new FilePermission(paramString, "delete"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkConnect(String paramString, int paramInt) {
/* 1041 */     if (paramString == null) {
/* 1042 */       throw new NullPointerException("host can't be null");
/*      */     }
/* 1044 */     if (!paramString.startsWith("[") && paramString.indexOf(':') != -1) {
/* 1045 */       paramString = "[" + paramString + "]";
/*      */     }
/* 1047 */     if (paramInt == -1) {
/* 1048 */       checkPermission(new SocketPermission(paramString, "resolve"));
/*      */     } else {
/*      */       
/* 1051 */       checkPermission(new SocketPermission(paramString + ":" + paramInt, "connect"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkConnect(String paramString, int paramInt, Object paramObject) {
/* 1096 */     if (paramString == null) {
/* 1097 */       throw new NullPointerException("host can't be null");
/*      */     }
/* 1099 */     if (!paramString.startsWith("[") && paramString.indexOf(':') != -1) {
/* 1100 */       paramString = "[" + paramString + "]";
/*      */     }
/* 1102 */     if (paramInt == -1) {
/* 1103 */       checkPermission(new SocketPermission(paramString, "resolve"), paramObject);
/*      */     }
/*      */     else {
/*      */       
/* 1107 */       checkPermission(new SocketPermission(paramString + ":" + paramInt, "connect"), paramObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkListen(int paramInt) {
/* 1131 */     checkPermission(new SocketPermission("localhost:" + paramInt, "listen"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkAccept(String paramString, int paramInt) {
/* 1161 */     if (paramString == null) {
/* 1162 */       throw new NullPointerException("host can't be null");
/*      */     }
/* 1164 */     if (!paramString.startsWith("[") && paramString.indexOf(':') != -1) {
/* 1165 */       paramString = "[" + paramString + "]";
/*      */     }
/* 1167 */     checkPermission(new SocketPermission(paramString + ":" + paramInt, "accept"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkMulticast(InetAddress paramInetAddress) {
/* 1194 */     String str = paramInetAddress.getHostAddress();
/* 1195 */     if (!str.startsWith("[") && str.indexOf(':') != -1) {
/* 1196 */       str = "[" + str + "]";
/*      */     }
/* 1198 */     checkPermission(new SocketPermission(str, "connect,accept"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void checkMulticast(InetAddress paramInetAddress, byte paramByte) {
/* 1230 */     String str = paramInetAddress.getHostAddress();
/* 1231 */     if (!str.startsWith("[") && str.indexOf(':') != -1) {
/* 1232 */       str = "[" + str + "]";
/*      */     }
/* 1234 */     checkPermission(new SocketPermission(str, "connect,accept"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkPropertiesAccess() {
/* 1262 */     checkPermission(new PropertyPermission("*", "read,write"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkPropertyAccess(String paramString) {
/* 1294 */     checkPermission(new PropertyPermission(paramString, "read"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean checkTopLevelWindow(Object paramObject) {
/* 1342 */     if (paramObject == null) {
/* 1343 */       throw new NullPointerException("window can't be null");
/*      */     }
/* 1345 */     Permission permission = SecurityConstants.AWT.TOPLEVEL_WINDOW_PERMISSION;
/* 1346 */     if (permission == null) {
/* 1347 */       permission = SecurityConstants.ALL_PERMISSION;
/*      */     }
/*      */     try {
/* 1350 */       checkPermission(permission);
/* 1351 */       return true;
/* 1352 */     } catch (SecurityException securityException) {
/*      */ 
/*      */       
/* 1355 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkPrintJobAccess() {
/* 1378 */     checkPermission(new RuntimePermission("queuePrintJob"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void checkSystemClipboardAccess() {
/* 1410 */     Permission permission = SecurityConstants.AWT.ACCESS_CLIPBOARD_PERMISSION;
/* 1411 */     if (permission == null) {
/* 1412 */       permission = SecurityConstants.ALL_PERMISSION;
/*      */     }
/* 1414 */     checkPermission(permission);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void checkAwtEventQueueAccess() {
/* 1446 */     Permission permission = SecurityConstants.AWT.CHECK_AWT_EVENTQUEUE_PERMISSION;
/* 1447 */     if (permission == null) {
/* 1448 */       permission = SecurityConstants.ALL_PERMISSION;
/*      */     }
/* 1450 */     checkPermission(permission);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean packageAccessValid = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] packageAccess;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1471 */   private static final Object packageAccessLock = new Object();
/*      */   
/*      */   private static boolean packageDefinitionValid = false;
/*      */   private static String[] packageDefinition;
/* 1475 */   private static final Object packageDefinitionLock = new Object();
/*      */   
/*      */   private static String[] getPackages(String paramString) {
/* 1478 */     String[] arrayOfString = null;
/* 1479 */     if (paramString != null && !paramString.equals("")) {
/* 1480 */       StringTokenizer stringTokenizer = new StringTokenizer(paramString, ",");
/*      */       
/* 1482 */       int i = stringTokenizer.countTokens();
/* 1483 */       if (i > 0) {
/* 1484 */         arrayOfString = new String[i];
/* 1485 */         byte b = 0;
/* 1486 */         while (stringTokenizer.hasMoreElements()) {
/* 1487 */           String str = stringTokenizer.nextToken().trim();
/* 1488 */           arrayOfString[b++] = str;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1493 */     if (arrayOfString == null)
/* 1494 */       arrayOfString = new String[0]; 
/* 1495 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkPackageAccess(String paramString) {
/*      */     String[] arrayOfString;
/* 1531 */     if (paramString == null) {
/* 1532 */       throw new NullPointerException("package name can't be null");
/*      */     }
/*      */ 
/*      */     
/* 1536 */     synchronized (packageAccessLock) {
/*      */ 
/*      */ 
/*      */       
/* 1540 */       if (!packageAccessValid) {
/*      */         
/* 1542 */         String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */             {
/*      */               public String run() {
/* 1545 */                 return Security.getProperty("package.access");
/*      */               }
/*      */             });
/*      */ 
/*      */         
/* 1550 */         packageAccess = getPackages(str);
/* 1551 */         packageAccessValid = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1556 */       arrayOfString = packageAccess;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1562 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 1563 */       if (paramString.startsWith(arrayOfString[b]) || arrayOfString[b].equals(paramString + ".")) {
/* 1564 */         checkPermission(new RuntimePermission("accessClassInPackage." + paramString));
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkPackageDefinition(String paramString) {
/*      */     String[] arrayOfString;
/* 1600 */     if (paramString == null) {
/* 1601 */       throw new NullPointerException("package name can't be null");
/*      */     }
/*      */ 
/*      */     
/* 1605 */     synchronized (packageDefinitionLock) {
/*      */ 
/*      */ 
/*      */       
/* 1609 */       if (!packageDefinitionValid) {
/*      */         
/* 1611 */         String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */             {
/*      */               public String run() {
/* 1614 */                 return Security.getProperty("package.definition");
/*      */               }
/*      */             });
/*      */ 
/*      */         
/* 1619 */         packageDefinition = getPackages(str);
/* 1620 */         packageDefinitionValid = true;
/*      */       } 
/*      */ 
/*      */       
/* 1624 */       arrayOfString = packageDefinition;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1630 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 1631 */       if (paramString.startsWith(arrayOfString[b]) || arrayOfString[b].equals(paramString + ".")) {
/* 1632 */         checkPermission(new RuntimePermission("defineClassInPackage." + paramString));
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkSetFactory() {
/* 1664 */     checkPermission(new RuntimePermission("setFactory"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @CallerSensitive
/*      */   public void checkMemberAccess(Class<?> paramClass, int paramInt) {
/* 1705 */     if (paramClass == null) {
/* 1706 */       throw new NullPointerException("class can't be null");
/*      */     }
/* 1708 */     if (paramInt != 0) {
/* 1709 */       Class[] arrayOfClass = getClassContext();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1721 */       if (arrayOfClass.length < 4 || arrayOfClass[3]
/* 1722 */         .getClassLoader() != paramClass.getClassLoader()) {
/* 1723 */         checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkSecurityAccess(String paramString) {
/* 1759 */     checkPermission(new SecurityPermission(paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private native Class<?> currentLoadedClass0();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ThreadGroup getThreadGroup() {
/* 1776 */     return Thread.currentThread().getThreadGroup();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/SecurityManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */