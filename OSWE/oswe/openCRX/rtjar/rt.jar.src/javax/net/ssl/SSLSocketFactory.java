/*     */ package javax.net.ssl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.Socket;
/*     */ import java.security.AccessController;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Security;
/*     */ import java.util.Locale;
/*     */ import javax.net.SocketFactory;
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
/*     */ public abstract class SSLSocketFactory
/*     */   extends SocketFactory
/*     */ {
/*     */   private static SSLSocketFactory theFactory;
/*     */   private static boolean propertyChecked;
/*     */   static final boolean DEBUG;
/*     */   
/*     */   static {
/*  55 */     String str = ((String)AccessController.<String>doPrivileged(new GetPropertyAction("javax.net.debug", ""))).toLowerCase(Locale.ENGLISH);
/*     */     
/*  57 */     DEBUG = (str.contains("all") || str.contains("ssl"));
/*     */   }
/*     */   
/*     */   private static void log(String paramString) {
/*  61 */     if (DEBUG) {
/*  62 */       System.out.println(paramString);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized SocketFactory getDefault() {
/*  89 */     if (theFactory != null) {
/*  90 */       return theFactory;
/*     */     }
/*     */     
/*  93 */     if (!propertyChecked) {
/*  94 */       propertyChecked = true;
/*  95 */       String str = getSecurityProperty("ssl.SocketFactory.provider");
/*  96 */       if (str != null) {
/*  97 */         log("setting up default SSLSocketFactory");
/*     */         try {
/*  99 */           Class<?> clazz = null;
/*     */           try {
/* 101 */             clazz = Class.forName(str);
/* 102 */           } catch (ClassNotFoundException classNotFoundException) {
/* 103 */             ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 104 */             if (classLoader != null) {
/* 105 */               clazz = classLoader.loadClass(str);
/*     */             }
/*     */           } 
/* 108 */           log("class " + str + " is loaded");
/* 109 */           SSLSocketFactory sSLSocketFactory = (SSLSocketFactory)clazz.newInstance();
/* 110 */           log("instantiated an instance of class " + str);
/* 111 */           theFactory = sSLSocketFactory;
/* 112 */           return sSLSocketFactory;
/* 113 */         } catch (Exception exception) {
/* 114 */           log("SSLSocketFactory instantiation failed: " + exception.toString());
/* 115 */           theFactory = new DefaultSSLSocketFactory(exception);
/* 116 */           return theFactory;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 122 */       return SSLContext.getDefault().getSocketFactory();
/* 123 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 124 */       return new DefaultSSLSocketFactory(noSuchAlgorithmException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static String getSecurityProperty(final String name) {
/* 129 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run() {
/* 132 */             String str = Security.getProperty(name);
/* 133 */             if (str != null) {
/* 134 */               str = str.trim();
/* 135 */               if (str.length() == 0) {
/* 136 */                 str = null;
/*     */               }
/*     */             } 
/* 139 */             return str;
/*     */           }
/*     */         });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Socket createSocket(Socket paramSocket, InputStream paramInputStream, boolean paramBoolean) throws IOException {
/* 232 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public abstract String[] getDefaultCipherSuites();
/*     */   
/*     */   public abstract String[] getSupportedCipherSuites();
/*     */   
/*     */   public abstract Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean) throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/SSLSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */