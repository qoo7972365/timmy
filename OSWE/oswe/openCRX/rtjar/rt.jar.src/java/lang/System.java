/*      */ package java.lang;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.Console;
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Executable;
/*      */ import java.nio.channels.Channel;
/*      */ import java.nio.channels.spi.SelectorProvider;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.PropertyPermission;
/*      */ import jdk.internal.util.StaticProperty;
/*      */ import sun.misc.JavaLangAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.misc.VM;
/*      */ import sun.misc.Version;
/*      */ import sun.nio.ch.Interruptible;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.ConstantPool;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.reflect.annotation.AnnotationType;
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
/*      */ public final class System
/*      */ {
/*      */   static {
/*   72 */     registerNatives();
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
/*   85 */   public static final InputStream in = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   public static final PrintStream out = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  126 */   public static final PrintStream err = null;
/*      */ 
/*      */ 
/*      */   
/*  130 */   private static volatile SecurityManager security = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setIn(InputStream paramInputStream) {
/*  153 */     checkIO();
/*  154 */     setIn0(paramInputStream);
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
/*      */   public static void setOut(PrintStream paramPrintStream) {
/*  177 */     checkIO();
/*  178 */     setOut0(paramPrintStream);
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
/*      */   public static void setErr(PrintStream paramPrintStream) {
/*  201 */     checkIO();
/*  202 */     setErr0(paramPrintStream);
/*      */   }
/*      */   
/*  205 */   private static volatile Console cons = null;
/*      */ 
/*      */   
/*      */   private static Properties props;
/*      */ 
/*      */   
/*      */   private static String lineSeparator;
/*      */ 
/*      */   
/*      */   public static Console console() {
/*  215 */     if (cons == null) {
/*  216 */       synchronized (System.class) {
/*  217 */         cons = SharedSecrets.getJavaIOAccess().console();
/*      */       } 
/*      */     }
/*  220 */     return cons;
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
/*      */   public static Channel inheritedChannel() throws IOException {
/*  249 */     return SelectorProvider.provider().inheritedChannel();
/*      */   }
/*      */   
/*      */   private static void checkIO() {
/*  253 */     SecurityManager securityManager = getSecurityManager();
/*  254 */     if (securityManager != null) {
/*  255 */       securityManager.checkPermission(new RuntimePermission("setIO"));
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
/*      */   public static void setSecurityManager(SecurityManager paramSecurityManager) {
/*      */     try {
/*  289 */       paramSecurityManager.checkPackageAccess("java.lang");
/*  290 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  293 */     setSecurityManager0(paramSecurityManager);
/*      */   }
/*      */ 
/*      */   
/*      */   private static synchronized void setSecurityManager0(final SecurityManager s) {
/*  298 */     SecurityManager securityManager = getSecurityManager();
/*  299 */     if (securityManager != null)
/*      */     {
/*      */       
/*  302 */       securityManager.checkPermission(new RuntimePermission("setSecurityManager"));
/*      */     }
/*      */ 
/*      */     
/*  306 */     if (s != null && s.getClass().getClassLoader() != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  315 */       AccessController.doPrivileged(new PrivilegedAction() {
/*      */             public Object run() {
/*  317 */               s.getClass().getProtectionDomain()
/*  318 */                 .implies(SecurityConstants.ALL_PERMISSION);
/*  319 */               return null;
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*  324 */     security = s;
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
/*      */   public static SecurityManager getSecurityManager() {
/*  336 */     return security;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Properties getProperties() {
/*  630 */     SecurityManager securityManager = getSecurityManager();
/*  631 */     if (securityManager != null) {
/*  632 */       securityManager.checkPropertiesAccess();
/*      */     }
/*      */     
/*  635 */     return props;
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
/*      */   public static String lineSeparator() {
/*  650 */     return lineSeparator;
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
/*      */   public static void setProperties(Properties paramProperties) {
/*  678 */     SecurityManager securityManager = getSecurityManager();
/*  679 */     if (securityManager != null) {
/*  680 */       securityManager.checkPropertiesAccess();
/*      */     }
/*  682 */     if (paramProperties == null) {
/*  683 */       paramProperties = new Properties();
/*  684 */       initProperties(paramProperties);
/*      */     } 
/*  686 */     props = paramProperties;
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
/*      */   public static String getProperty(String paramString) {
/*  716 */     checkKey(paramString);
/*  717 */     SecurityManager securityManager = getSecurityManager();
/*  718 */     if (securityManager != null) {
/*  719 */       securityManager.checkPropertyAccess(paramString);
/*      */     }
/*      */     
/*  722 */     return props.getProperty(paramString);
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
/*      */   public static String getProperty(String paramString1, String paramString2) {
/*  752 */     checkKey(paramString1);
/*  753 */     SecurityManager securityManager = getSecurityManager();
/*  754 */     if (securityManager != null) {
/*  755 */       securityManager.checkPropertyAccess(paramString1);
/*      */     }
/*      */     
/*  758 */     return props.getProperty(paramString1, paramString2);
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
/*      */   public static String setProperty(String paramString1, String paramString2) {
/*  791 */     checkKey(paramString1);
/*  792 */     SecurityManager securityManager = getSecurityManager();
/*  793 */     if (securityManager != null) {
/*  794 */       securityManager.checkPermission(new PropertyPermission(paramString1, "write"));
/*      */     }
/*      */ 
/*      */     
/*  798 */     return (String)props.setProperty(paramString1, paramString2);
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
/*      */   public static String clearProperty(String paramString) {
/*  829 */     checkKey(paramString);
/*  830 */     SecurityManager securityManager = getSecurityManager();
/*  831 */     if (securityManager != null) {
/*  832 */       securityManager.checkPermission(new PropertyPermission(paramString, "write"));
/*      */     }
/*      */     
/*  835 */     return (String)props.remove(paramString);
/*      */   }
/*      */   
/*      */   private static void checkKey(String paramString) {
/*  839 */     if (paramString == null) {
/*  840 */       throw new NullPointerException("key can't be null");
/*      */     }
/*  842 */     if (paramString.equals("")) {
/*  843 */       throw new IllegalArgumentException("key can't be empty");
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
/*      */ 
/*      */   
/*      */   public static String getenv(String paramString) {
/*  894 */     SecurityManager securityManager = getSecurityManager();
/*  895 */     if (securityManager != null) {
/*  896 */       securityManager.checkPermission(new RuntimePermission("getenv." + paramString));
/*      */     }
/*      */     
/*  899 */     return ProcessEnvironment.getenv(paramString);
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
/*      */   public static Map<String, String> getenv() {
/*  944 */     SecurityManager securityManager = getSecurityManager();
/*  945 */     if (securityManager != null) {
/*  946 */       securityManager.checkPermission(new RuntimePermission("getenv.*"));
/*      */     }
/*      */     
/*  949 */     return ProcessEnvironment.getenv();
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
/*      */   public static void exit(int paramInt) {
/*  973 */     Runtime.getRuntime().exit(paramInt);
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
/*      */   public static void gc() {
/*  995 */     Runtime.getRuntime().gc();
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
/*      */   public static void runFinalization() {
/* 1017 */     Runtime.getRuntime().runFinalization();
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
/*      */   @Deprecated
/*      */   public static void runFinalizersOnExit(boolean paramBoolean) {
/* 1047 */     Runtime.runFinalizersOnExit(paramBoolean);
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
/*      */   @CallerSensitive
/*      */   public static void load(String paramString) {
/* 1088 */     Runtime.getRuntime().load0(Reflection.getCallerClass(), paramString);
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
/*      */   @CallerSensitive
/*      */   public static void loadLibrary(String paramString) {
/* 1124 */     Runtime.getRuntime().loadLibrary0(Reflection.getCallerClass(), paramString);
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
/*      */   private static PrintStream newPrintStream(FileOutputStream paramFileOutputStream, String paramString) {
/* 1145 */     if (paramString != null) {
/*      */       try {
/* 1147 */         return new PrintStream(new BufferedOutputStream(paramFileOutputStream, 128), true, paramString);
/* 1148 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*      */     }
/* 1150 */     return new PrintStream(new BufferedOutputStream(paramFileOutputStream, 128), true);
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
/*      */   private static void initializeSystemClass() {
/* 1167 */     props = new Properties();
/* 1168 */     initProperties(props);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1184 */     VM.saveAndRemoveProperties(props);
/*      */ 
/*      */     
/* 1187 */     lineSeparator = props.getProperty("line.separator");
/* 1188 */     StaticProperty.jdkSerialFilter();
/* 1189 */     Version.init();
/*      */     
/* 1191 */     FileInputStream fileInputStream = new FileInputStream(FileDescriptor.in);
/* 1192 */     FileOutputStream fileOutputStream1 = new FileOutputStream(FileDescriptor.out);
/* 1193 */     FileOutputStream fileOutputStream2 = new FileOutputStream(FileDescriptor.err);
/* 1194 */     setIn0(new BufferedInputStream(fileInputStream));
/* 1195 */     setOut0(newPrintStream(fileOutputStream1, props.getProperty("sun.stdout.encoding")));
/* 1196 */     setErr0(newPrintStream(fileOutputStream2, props.getProperty("sun.stderr.encoding")));
/*      */     
/* 1198 */     ClassLoader.initLibraryPaths();
/*      */ 
/*      */ 
/*      */     
/* 1202 */     loadLibrary("zip");
/*      */ 
/*      */     
/* 1205 */     Terminator.setup();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1211 */     VM.initializeOSEnvironment();
/*      */ 
/*      */ 
/*      */     
/* 1215 */     Thread thread = Thread.currentThread();
/* 1216 */     thread.getThreadGroup().add(thread);
/*      */ 
/*      */     
/* 1219 */     setJavaLangAccess();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1225 */     VM.booted();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setJavaLangAccess() {
/* 1230 */     SharedSecrets.setJavaLangAccess(new JavaLangAccess() {
/*      */           public ConstantPool getConstantPool(Class<?> param1Class) {
/* 1232 */             return param1Class.getConstantPool();
/*      */           }
/*      */           public boolean casAnnotationType(Class<?> param1Class, AnnotationType param1AnnotationType1, AnnotationType param1AnnotationType2) {
/* 1235 */             return param1Class.casAnnotationType(param1AnnotationType1, param1AnnotationType2);
/*      */           }
/*      */           public AnnotationType getAnnotationType(Class<?> param1Class) {
/* 1238 */             return param1Class.getAnnotationType();
/*      */           }
/*      */           public Map<Class<? extends Annotation>, Annotation> getDeclaredAnnotationMap(Class<?> param1Class) {
/* 1241 */             return param1Class.getDeclaredAnnotationMap();
/*      */           }
/*      */           public byte[] getRawClassAnnotations(Class<?> param1Class) {
/* 1244 */             return param1Class.getRawAnnotations();
/*      */           }
/*      */           public byte[] getRawClassTypeAnnotations(Class<?> param1Class) {
/* 1247 */             return param1Class.getRawTypeAnnotations();
/*      */           }
/*      */           public byte[] getRawExecutableTypeAnnotations(Executable param1Executable) {
/* 1250 */             return Class.getExecutableTypeAnnotationBytes(param1Executable);
/*      */           }
/*      */           
/*      */           public <E extends Enum<E>> E[] getEnumConstantsShared(Class<E> param1Class) {
/* 1254 */             return param1Class.getEnumConstantsShared();
/*      */           }
/*      */           public void blockedOn(Thread param1Thread, Interruptible param1Interruptible) {
/* 1257 */             param1Thread.blockedOn(param1Interruptible);
/*      */           }
/*      */           public void registerShutdownHook(int param1Int, boolean param1Boolean, Runnable param1Runnable) {
/* 1260 */             Shutdown.add(param1Int, param1Boolean, param1Runnable);
/*      */           }
/*      */           public int getStackTraceDepth(Throwable param1Throwable) {
/* 1263 */             return param1Throwable.getStackTraceDepth();
/*      */           }
/*      */           public StackTraceElement getStackTraceElement(Throwable param1Throwable, int param1Int) {
/* 1266 */             return param1Throwable.getStackTraceElement(param1Int);
/*      */           }
/*      */           public String newStringUnsafe(char[] param1ArrayOfchar) {
/* 1269 */             return new String(param1ArrayOfchar, true);
/*      */           }
/*      */           public Thread newThreadWithAcc(Runnable param1Runnable, AccessControlContext param1AccessControlContext) {
/* 1272 */             return new Thread(param1Runnable, param1AccessControlContext);
/*      */           }
/*      */           public void invokeFinalize(Object param1Object) throws Throwable {
/* 1275 */             param1Object.finalize();
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private static native void registerNatives();
/*      */   
/*      */   private static native void setIn0(InputStream paramInputStream);
/*      */   
/*      */   private static native void setOut0(PrintStream paramPrintStream);
/*      */   
/*      */   private static native void setErr0(PrintStream paramPrintStream);
/*      */   
/*      */   public static native long currentTimeMillis();
/*      */   
/*      */   public static native long nanoTime();
/*      */   
/*      */   public static native void arraycopy(Object paramObject1, int paramInt1, Object paramObject2, int paramInt2, int paramInt3);
/*      */   
/*      */   public static native int identityHashCode(Object paramObject);
/*      */   
/*      */   private static native Properties initProperties(Properties paramProperties);
/*      */   
/*      */   public static native String mapLibraryName(String paramString);
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/System.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */