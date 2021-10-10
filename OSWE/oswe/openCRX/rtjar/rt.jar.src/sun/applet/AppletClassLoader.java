/*     */ package sun.applet;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FilePermission;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.net.URLConnection;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSource;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.NoSuchElementException;
/*     */ import sun.awt.AppContext;
/*     */ import sun.misc.IOUtils;
/*     */ import sun.net.www.ParseUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AppletClassLoader
/*     */   extends URLClassLoader
/*     */ {
/*     */   private URL base;
/*     */   private CodeSource codesource;
/*     */   private AccessControlContext acc;
/*     */   private boolean exceptionStatus = false;
/*  70 */   private final Object threadGroupSynchronizer = new Object();
/*  71 */   private final Object grabReleaseSynchronizer = new Object();
/*     */   private boolean codebaseLookup = true;
/*     */   private volatile boolean allowRecursiveDirectoryRead = true;
/*     */   private Object syncResourceAsStream;
/*     */   private Object syncResourceAsStreamFromJar;
/*     */   private boolean resourceAsStreamInCall;
/*     */   private boolean resourceAsStreamFromJarInCall; private AppletThreadGroup threadGroup; private AppContext appContext; int usageCount; private HashMap jdk11AppletInfo; private HashMap jdk12AppletInfo; public void disableRecursiveDirectoryRead() { this.allowRecursiveDirectoryRead = false; } void setCodebaseLookup(boolean paramBoolean) { this.codebaseLookup = paramBoolean; } URL getBaseURL() { return this.base; } public URL[] getURLs() { URL[] arrayOfURL1 = super.getURLs(); URL[] arrayOfURL2 = new URL[arrayOfURL1.length + 1]; System.arraycopy(arrayOfURL1, 0, arrayOfURL2, 0, arrayOfURL1.length); arrayOfURL2[arrayOfURL2.length - 1] = this.base; return arrayOfURL2; } protected void addJar(String paramString) throws IOException { URL uRL; try { uRL = new URL(this.base, paramString); } catch (MalformedURLException malformedURLException) { throw new IllegalArgumentException("name"); }  addURL(uRL); } public synchronized Class loadClass(String paramString, boolean paramBoolean) throws ClassNotFoundException { int i = paramString.lastIndexOf('.'); if (i != -1) { SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null)
/*     */         securityManager.checkPackageAccess(paramString.substring(0, i));  }  try { return super.loadClass(paramString, paramBoolean); } catch (ClassNotFoundException classNotFoundException) { throw classNotFoundException; } catch (RuntimeException runtimeException) { throw runtimeException; }
/*     */     catch (Error error) { throw error; }
/*  80 */      } protected AppletClassLoader(URL paramURL) { super(new URL[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 347 */     this.syncResourceAsStream = new Object();
/* 348 */     this.syncResourceAsStreamFromJar = new Object();
/*     */ 
/*     */     
/* 351 */     this.resourceAsStreamInCall = false;
/* 352 */     this.resourceAsStreamFromJarInCall = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 694 */     this.usageCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 776 */     this.jdk11AppletInfo = new HashMap<>();
/* 777 */     this.jdk12AppletInfo = new HashMap<>(); this.base = paramURL; this.codesource = new CodeSource(paramURL, (Certificate[])null); this.acc = AccessController.getContext(); }
/*     */   protected Class findClass(String paramString) throws ClassNotFoundException { int i = paramString.indexOf(";"); String str = ""; if (i != -1) { str = paramString.substring(i, paramString.length()); paramString = paramString.substring(0, i); }  try { return super.findClass(paramString); } catch (ClassNotFoundException classNotFoundException) { if (!this.codebaseLookup) throw new ClassNotFoundException(paramString);  String str1 = ParseUtil.encodePath(paramString.replace('.', '/'), false); final String path = str1 + ".class" + str; try { byte[] arrayOfByte = AccessController.<byte[]>doPrivileged(new PrivilegedExceptionAction<byte>() {
/*     */               public Object run() throws IOException { try { URL uRL = new URL(AppletClassLoader.this.base, path); if (AppletClassLoader.this.base.getProtocol().equals(uRL.getProtocol()) && AppletClassLoader.this.base.getHost().equals(uRL.getHost()) && AppletClassLoader.this.base.getPort() == uRL.getPort()) return AppletClassLoader.getBytes(uRL);  return null; } catch (Exception exception) { return null; }  }
/*     */             }this.acc); if (arrayOfByte != null) return defineClass(paramString, arrayOfByte, 0, arrayOfByte.length, this.codesource);  throw new ClassNotFoundException(paramString); } catch (PrivilegedActionException privilegedActionException) { throw new ClassNotFoundException(paramString, privilegedActionException.getException()); }  }  }
/*     */   protected PermissionCollection getPermissions(CodeSource paramCodeSource) { Permission permission; PermissionCollection permissionCollection = super.getPermissions(paramCodeSource); URL uRL = paramCodeSource.getLocation(); String str = null; try { permission = uRL.openConnection().getPermission(); } catch (IOException iOException) { permission = null; }  if (permission instanceof FilePermission) { str = permission.getName(); } else if (permission == null && uRL.getProtocol().equals("file")) { str = uRL.getFile().replace('/', File.separatorChar); str = ParseUtil.decode(str); }  if (str != null) { String str1 = str; if (!str.endsWith(File.separator)) { int i = str.lastIndexOf(File.separatorChar); if (i != -1) { str = str.substring(0, i + 1) + "-"; permissionCollection.add(new FilePermission(str, "read")); }  }  File file = new File(str1); boolean bool = file.isDirectory(); if (this.allowRecursiveDirectoryRead && (bool || str1.toLowerCase().endsWith(".jar") || str1.toLowerCase().endsWith(".zip"))) { Permission permission1; try { permission1 = this.base.openConnection().getPermission(); } catch (IOException iOException) { permission1 = null; }  if (permission1 instanceof FilePermission) { String str2 = permission1.getName(); if (str2.endsWith(File.separator)) str2 = str2 + "-";  permissionCollection.add(new FilePermission(str2, "read")); } else if (permission1 == null && this.base.getProtocol().equals("file")) { String str2 = this.base.getFile().replace('/', File.separatorChar); str2 = ParseUtil.decode(str2); if (str2.endsWith(File.separator))
/*     */             str2 = str2 + "-";  permissionCollection.add(new FilePermission(str2, "read")); }  }  }  return permissionCollection; }
/*     */   private static byte[] getBytes(URL paramURL) throws IOException { byte[] arrayOfByte; URLConnection uRLConnection = paramURL.openConnection(); if (uRLConnection instanceof HttpURLConnection) { HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection; int j = httpURLConnection.getResponseCode(); if (j >= 400)
/*     */         throw new IOException("open HTTP connection failed.");  }  int i = uRLConnection.getContentLength(); BufferedInputStream bufferedInputStream = new BufferedInputStream(uRLConnection.getInputStream()); try { arrayOfByte = IOUtils.readAllBytes(bufferedInputStream); if (i != -1 && arrayOfByte.length != i)
/*     */         throw new EOFException("Expected:" + i + ", read:" + arrayOfByte.length);  } finally { bufferedInputStream.close(); }  return arrayOfByte; }
/*     */   public InputStream getResourceAsStream(String paramString) { if (paramString == null)
/*     */       throw new NullPointerException("name");  try { InputStream inputStream = null; synchronized (this.syncResourceAsStream) { this.resourceAsStreamInCall = true; inputStream = super.getResourceAsStream(paramString); this.resourceAsStreamInCall = false; }  if (this.codebaseLookup == true && inputStream == null) { URL uRL = new URL(this.base, ParseUtil.encodePath(paramString, false)); inputStream = uRL.openStream(); }  return inputStream; } catch (Exception exception) { return null; }  } public InputStream getResourceAsStreamFromJar(String paramString) { if (paramString == null)
/* 788 */       throw new NullPointerException("name");  try { InputStream inputStream = null; synchronized (this.syncResourceAsStreamFromJar) { this.resourceAsStreamFromJarInCall = true; inputStream = super.getResourceAsStream(paramString); this.resourceAsStreamFromJarInCall = false; }  return inputStream; } catch (Exception exception) { return null; }  } void setJDK11Target(Class paramClass, boolean paramBoolean) { this.jdk11AppletInfo.put(paramClass.toString(), Boolean.valueOf(paramBoolean)); } public URL findResource(String paramString) { URL uRL = super.findResource(paramString); if (paramString.startsWith("META-INF/")) return uRL;  if (!this.codebaseLookup) return uRL;  if (uRL == null) { boolean bool1 = false; synchronized (this.syncResourceAsStreamFromJar) { bool1 = this.resourceAsStreamFromJarInCall; }  if (bool1) return null;  boolean bool2 = false; synchronized (this.syncResourceAsStream) { bool2 = this.resourceAsStreamInCall; }  if (!bool2) try { uRL = new URL(this.base, ParseUtil.encodePath(paramString, false)); if (!resourceExists(uRL)) uRL = null;  } catch (Exception exception) { uRL = null; }   }  return uRL; } private boolean resourceExists(URL paramURL) { boolean bool = true; try { URLConnection uRLConnection = paramURL.openConnection(); if (uRLConnection instanceof HttpURLConnection) { HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection; httpURLConnection.setRequestMethod("HEAD"); int i = httpURLConnection.getResponseCode(); if (i == 200) return true;  if (i >= 400) return false;  } else { InputStream inputStream = uRLConnection.getInputStream(); inputStream.close(); }  } catch (Exception exception) { bool = false; }  return bool; } public Enumeration findResources(String paramString) throws IOException { final Enumeration<URL> e = super.findResources(paramString); if (paramString.startsWith("META-INF/")) return enumeration;  if (!this.codebaseLookup) return enumeration;  URL uRL1 = new URL(this.base, ParseUtil.encodePath(paramString, false)); if (!resourceExists(uRL1)) uRL1 = null;  final URL url = uRL1; return new Enumeration() { private boolean done; public Object nextElement() { if (!this.done) { if (e.hasMoreElements()) return e.nextElement();  this.done = true; if (url != null) return url;  }  throw new NoSuchElementException(); }
/*     */         public boolean hasMoreElements() { return (!this.done && (e.hasMoreElements() || url != null)); } }
/*     */       ; }
/*     */   Class loadCode(String paramString) throws ClassNotFoundException { paramString = paramString.replace('/', '.'); paramString = paramString.replace(File.separatorChar, '.'); String str1 = null; int i = paramString.indexOf(";"); if (i != -1) { str1 = paramString.substring(i, paramString.length()); paramString = paramString.substring(0, i); }  String str2 = paramString; if (paramString.endsWith(".class") || paramString.endsWith(".java")) paramString = paramString.substring(0, paramString.lastIndexOf('.'));  try { if (str1 != null) paramString = paramString + str1;  return loadClass(paramString); } catch (ClassNotFoundException classNotFoundException) { if (str1 != null) str2 = str2 + str1;  return loadClass(str2); }  }
/*     */   public ThreadGroup getThreadGroup() { synchronized (this.threadGroupSynchronizer) { if (this.threadGroup == null || this.threadGroup.isDestroyed()) AccessController.doPrivileged(new PrivilegedAction() { public Object run() { AppletClassLoader.this.threadGroup = new AppletThreadGroup(AppletClassLoader.this.base + "-threadGroup"); AppContextCreator appContextCreator = new AppContextCreator(AppletClassLoader.this.threadGroup); appContextCreator.setContextClassLoader(AppletClassLoader.this); appContextCreator.start(); try { synchronized (appContextCreator.syncObject) { while (!appContextCreator.created) appContextCreator.syncObject.wait();  }  } catch (InterruptedException interruptedException) {} AppletClassLoader.this.appContext = appContextCreator.appContext; return null; } }
/*     */           );  return this.threadGroup; }  }
/*     */   public AppContext getAppContext() { return this.appContext; }
/*     */   public void grab() { synchronized (this.grabReleaseSynchronizer) { this.usageCount++; }  getThreadGroup(); }
/*     */   protected void setExceptionStatus() { this.exceptionStatus = true; }
/*     */   public boolean getExceptionStatus() { return this.exceptionStatus; }
/*     */   protected void release() { AppContext appContext = null; synchronized (this.grabReleaseSynchronizer) { if (this.usageCount > 1) { this.usageCount--; } else { synchronized (this.threadGroupSynchronizer) { appContext = resetAppContext(); }  }  }  if (appContext != null) try { appContext.dispose(); } catch (IllegalThreadStateException illegalThreadStateException) {}  }
/*     */   protected AppContext resetAppContext() { AppContext appContext = null; synchronized (this.threadGroupSynchronizer) { appContext = this.appContext; this.usageCount = 0; this.appContext = null; this.threadGroup = null; }  return appContext; }
/* 800 */   void setJDK12Target(Class paramClass, boolean paramBoolean) { this.jdk12AppletInfo.put(paramClass.toString(), Boolean.valueOf(paramBoolean)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Boolean isJDK11Target(Class paramClass) {
/* 813 */     return (Boolean)this.jdk11AppletInfo.get(paramClass.toString());
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
/*     */   Boolean isJDK12Target(Class paramClass) {
/* 826 */     return (Boolean)this.jdk12AppletInfo.get(paramClass.toString());
/*     */   }
/*     */   
/* 829 */   private static AppletMessageHandler mh = new AppletMessageHandler("appletclassloader");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void printError(String paramString, Throwable paramThrowable) {
/* 836 */     String str = null;
/* 837 */     if (paramThrowable == null) {
/* 838 */       str = mh.getMessage("filenotfound", paramString);
/* 839 */     } else if (paramThrowable instanceof IOException) {
/* 840 */       str = mh.getMessage("fileioexception", paramString);
/* 841 */     } else if (paramThrowable instanceof ClassFormatError) {
/* 842 */       str = mh.getMessage("fileformat", paramString);
/* 843 */     } else if (paramThrowable instanceof ThreadDeath) {
/* 844 */       str = mh.getMessage("filedeath", paramString);
/* 845 */     } else if (paramThrowable instanceof Error) {
/* 846 */       str = mh.getMessage("fileerror", paramThrowable.toString(), paramString);
/*     */     } 
/* 848 */     if (str != null)
/* 849 */       System.err.println(str); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/applet/AppletClassLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */