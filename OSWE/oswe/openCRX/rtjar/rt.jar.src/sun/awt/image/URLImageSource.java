/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.Permission;
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
/*     */ public class URLImageSource
/*     */   extends InputStreamImageSource
/*     */ {
/*     */   URL url;
/*     */   URLConnection conn;
/*     */   String actualHost;
/*     */   int actualPort;
/*     */   
/*     */   public URLImageSource(URL paramURL) {
/*  43 */     SecurityManager securityManager = System.getSecurityManager();
/*  44 */     if (securityManager != null) {
/*     */       
/*     */       try {
/*  47 */         Permission permission = URLUtil.getConnectPermission(paramURL);
/*  48 */         if (permission != null) {
/*     */           try {
/*  50 */             securityManager.checkPermission(permission);
/*  51 */           } catch (SecurityException securityException) {
/*     */ 
/*     */             
/*  54 */             if (permission instanceof java.io.FilePermission && permission
/*  55 */               .getActions().indexOf("read") != -1) {
/*  56 */               securityManager.checkRead(permission.getName());
/*  57 */             } else if (permission instanceof java.net.SocketPermission && permission
/*     */               
/*  59 */               .getActions().indexOf("connect") != -1) {
/*  60 */               securityManager.checkConnect(paramURL.getHost(), paramURL.getPort());
/*     */             } else {
/*  62 */               throw securityException;
/*     */             } 
/*     */           } 
/*     */         }
/*  66 */       } catch (IOException iOException) {
/*  67 */         securityManager.checkConnect(paramURL.getHost(), paramURL.getPort());
/*     */       } 
/*     */     }
/*  70 */     this.url = paramURL;
/*     */   }
/*     */ 
/*     */   
/*     */   public URLImageSource(String paramString) throws MalformedURLException {
/*  75 */     this(new URL(null, paramString));
/*     */   }
/*     */   
/*     */   public URLImageSource(URL paramURL, URLConnection paramURLConnection) {
/*  79 */     this(paramURL);
/*  80 */     this.conn = paramURLConnection;
/*     */   }
/*     */   
/*     */   public URLImageSource(URLConnection paramURLConnection) {
/*  84 */     this(paramURLConnection.getURL(), paramURLConnection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean checkSecurity(Object paramObject, boolean paramBoolean) {
/*  94 */     if (this.actualHost != null) {
/*     */       try {
/*  96 */         SecurityManager securityManager = System.getSecurityManager();
/*  97 */         if (securityManager != null) {
/*  98 */           securityManager.checkConnect(this.actualHost, this.actualPort, paramObject);
/*     */         }
/* 100 */       } catch (SecurityException securityException) {
/* 101 */         if (!paramBoolean) {
/* 102 */           throw securityException;
/*     */         }
/* 104 */         return false;
/*     */       } 
/*     */     }
/* 107 */     return true;
/*     */   }
/*     */   
/*     */   private synchronized URLConnection getConnection() throws IOException {
/*     */     URLConnection uRLConnection;
/* 112 */     if (this.conn != null) {
/* 113 */       uRLConnection = this.conn;
/* 114 */       this.conn = null;
/*     */     } else {
/* 116 */       uRLConnection = this.url.openConnection();
/*     */     } 
/* 118 */     return uRLConnection;
/*     */   }
/*     */   
/*     */   protected ImageDecoder getDecoder() {
/* 122 */     InputStream inputStream = null;
/* 123 */     String str = null;
/* 124 */     URLConnection uRLConnection = null;
/*     */     try {
/* 126 */       uRLConnection = getConnection();
/* 127 */       inputStream = uRLConnection.getInputStream();
/* 128 */       str = uRLConnection.getContentType();
/* 129 */       URL uRL = uRLConnection.getURL();
/* 130 */       if (uRL != this.url && (!uRL.getHost().equals(this.url.getHost()) || uRL
/* 131 */         .getPort() != this.url.getPort())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 137 */         if (this.actualHost != null && (!this.actualHost.equals(uRL.getHost()) || this.actualPort != uRL
/* 138 */           .getPort()))
/*     */         {
/* 140 */           throw new SecurityException("image moved!");
/*     */         }
/* 142 */         this.actualHost = uRL.getHost();
/* 143 */         this.actualPort = uRL.getPort();
/*     */       } 
/* 145 */     } catch (IOException iOException) {
/* 146 */       if (inputStream != null) {
/*     */         try {
/* 148 */           inputStream.close();
/* 149 */         } catch (IOException iOException1) {}
/*     */       }
/* 151 */       else if (uRLConnection instanceof HttpURLConnection) {
/* 152 */         ((HttpURLConnection)uRLConnection).disconnect();
/*     */       } 
/* 154 */       return null;
/*     */     } 
/*     */     
/* 157 */     ImageDecoder imageDecoder = decoderForType(inputStream, str);
/* 158 */     if (imageDecoder == null) {
/* 159 */       imageDecoder = getDecoder(inputStream);
/*     */     }
/*     */     
/* 162 */     if (imageDecoder == null)
/*     */     {
/* 164 */       if (inputStream != null) {
/*     */         try {
/* 166 */           inputStream.close();
/* 167 */         } catch (IOException iOException) {}
/*     */       }
/* 169 */       else if (uRLConnection instanceof HttpURLConnection) {
/* 170 */         ((HttpURLConnection)uRLConnection).disconnect();
/*     */       } 
/*     */     }
/* 173 */     return imageDecoder;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/URLImageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */