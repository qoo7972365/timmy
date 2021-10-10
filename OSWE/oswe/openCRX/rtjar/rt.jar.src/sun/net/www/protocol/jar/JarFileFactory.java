/*     */ package sun.net.www.protocol.jar;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.Permission;
/*     */ import java.util.HashMap;
/*     */ import java.util.jar.JarFile;
/*     */ import sun.net.util.URLUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JarFileFactory
/*     */   implements URLJarFile.URLJarFileCloseController
/*     */ {
/*  46 */   private static final HashMap<String, JarFile> fileCache = new HashMap<>();
/*     */ 
/*     */   
/*  49 */   private static final HashMap<JarFile, URL> urlCache = new HashMap<>();
/*     */   
/*  51 */   private static final JarFileFactory instance = new JarFileFactory();
/*     */ 
/*     */ 
/*     */   
/*     */   public static JarFileFactory getInstance() {
/*  56 */     return instance;
/*     */   }
/*     */   
/*     */   URLConnection getConnection(JarFile paramJarFile) throws IOException {
/*     */     URL uRL;
/*  61 */     synchronized (instance) {
/*  62 */       uRL = urlCache.get(paramJarFile);
/*     */     } 
/*  64 */     if (uRL != null) {
/*  65 */       return uRL.openConnection();
/*     */     }
/*  67 */     return null;
/*     */   }
/*     */   
/*     */   public JarFile get(URL paramURL) throws IOException {
/*  71 */     return get(paramURL, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JarFile get(URL paramURL, boolean paramBoolean) throws IOException {
/*     */     JarFile jarFile;
/*  79 */     if (paramBoolean) {
/*  80 */       synchronized (instance) {
/*  81 */         jarFile = getCachedJarFile(paramURL);
/*     */       } 
/*  83 */       if (jarFile == null) {
/*  84 */         JarFile jarFile1 = URLJarFile.getJarFile(paramURL, this);
/*  85 */         synchronized (instance) {
/*  86 */           jarFile = getCachedJarFile(paramURL);
/*  87 */           if (jarFile == null) {
/*  88 */             fileCache.put(URLUtil.urlNoFragString(paramURL), jarFile1);
/*  89 */             urlCache.put(jarFile1, paramURL);
/*  90 */             jarFile = jarFile1;
/*     */           }
/*  92 */           else if (jarFile1 != null) {
/*  93 */             jarFile1.close();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/*  99 */       jarFile = URLJarFile.getJarFile(paramURL, this);
/*     */     } 
/* 101 */     if (jarFile == null) {
/* 102 */       throw new FileNotFoundException(paramURL.toString());
/*     */     }
/* 104 */     return jarFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(JarFile paramJarFile) {
/* 113 */     synchronized (instance) {
/* 114 */       URL uRL = urlCache.remove(paramJarFile);
/* 115 */       if (uRL != null)
/* 116 */         fileCache.remove(URLUtil.urlNoFragString(uRL)); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private JarFile getCachedJarFile(URL paramURL) {
/* 121 */     assert Thread.holdsLock(instance);
/* 122 */     JarFile jarFile = fileCache.get(URLUtil.urlNoFragString(paramURL));
/*     */ 
/*     */     
/* 125 */     if (jarFile != null) {
/* 126 */       Permission permission = getPermission(jarFile);
/* 127 */       if (permission != null) {
/* 128 */         SecurityManager securityManager = System.getSecurityManager();
/* 129 */         if (securityManager != null) {
/*     */           try {
/* 131 */             securityManager.checkPermission(permission);
/* 132 */           } catch (SecurityException securityException) {
/*     */ 
/*     */             
/* 135 */             if (permission instanceof java.io.FilePermission && permission
/* 136 */               .getActions().indexOf("read") != -1) {
/* 137 */               securityManager.checkRead(permission.getName());
/* 138 */             } else if (permission instanceof java.net.SocketPermission && permission
/*     */               
/* 140 */               .getActions().indexOf("connect") != -1) {
/* 141 */               securityManager.checkConnect(paramURL.getHost(), paramURL.getPort());
/*     */             } else {
/* 143 */               throw securityException;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 149 */     return jarFile;
/*     */   }
/*     */   
/*     */   private Permission getPermission(JarFile paramJarFile) {
/*     */     try {
/* 154 */       URLConnection uRLConnection = getConnection(paramJarFile);
/* 155 */       if (uRLConnection != null)
/* 156 */         return uRLConnection.getPermission(); 
/* 157 */     } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */     
/* 161 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/jar/JarFileFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */