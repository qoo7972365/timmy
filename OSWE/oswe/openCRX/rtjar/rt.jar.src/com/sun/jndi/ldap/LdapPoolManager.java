/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import com.sun.jndi.ldap.pool.Pool;
/*     */ import com.sun.jndi.ldap.pool.PoolCleaner;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.naming.CommunicationException;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.ldap.Control;
/*     */ import sun.misc.InnocuousThread;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LdapPoolManager
/*     */ {
/*     */   private static final String DEBUG = "com.sun.jndi.ldap.connect.pool.debug";
/*  62 */   public static final boolean debug = "all"
/*  63 */     .equalsIgnoreCase(getProperty("com.sun.jndi.ldap.connect.pool.debug", null));
/*     */   
/*  65 */   public static final boolean trace = (debug || "fine"
/*  66 */     .equalsIgnoreCase(getProperty("com.sun.jndi.ldap.connect.pool.debug", null)));
/*     */ 
/*     */   
/*     */   private static final String POOL_AUTH = "com.sun.jndi.ldap.connect.pool.authentication";
/*     */ 
/*     */   
/*     */   private static final String POOL_PROTOCOL = "com.sun.jndi.ldap.connect.pool.protocol";
/*     */ 
/*     */   
/*     */   private static final String MAX_POOL_SIZE = "com.sun.jndi.ldap.connect.pool.maxsize";
/*     */ 
/*     */   
/*     */   private static final String PREF_POOL_SIZE = "com.sun.jndi.ldap.connect.pool.prefsize";
/*     */ 
/*     */   
/*     */   private static final String INIT_POOL_SIZE = "com.sun.jndi.ldap.connect.pool.initsize";
/*     */ 
/*     */   
/*     */   private static final String POOL_TIMEOUT = "com.sun.jndi.ldap.connect.pool.timeout";
/*     */ 
/*     */   
/*     */   private static final String SASL_CALLBACK = "java.naming.security.sasl.callback";
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_MAX_POOL_SIZE = 0;
/*     */   
/*     */   private static final int DEFAULT_PREF_POOL_SIZE = 0;
/*     */   
/*     */   private static final int DEFAULT_INIT_POOL_SIZE = 1;
/*     */   
/*     */   private static final int DEFAULT_TIMEOUT = 0;
/*     */   
/*     */   private static final String DEFAULT_AUTH_MECHS = "none simple";
/*     */   
/*     */   private static final String DEFAULT_PROTOCOLS = "plain";
/*     */   
/*     */   private static final int NONE = 0;
/*     */   
/*     */   private static final int SIMPLE = 1;
/*     */   
/*     */   private static final int DIGEST = 2;
/*     */   
/*     */   private static final long idleTimeout;
/*     */   
/*     */   private static final int maxSize;
/*     */   
/*     */   private static final int prefSize;
/*     */   
/*     */   private static final int initSize;
/*     */   
/*     */   private static boolean supportPlainProtocol = false;
/*     */   
/*     */   private static boolean supportSslProtocol = false;
/*     */   
/* 120 */   private static final Pool[] pools = new Pool[3];
/*     */   
/*     */   static {
/* 123 */     maxSize = getInteger("com.sun.jndi.ldap.connect.pool.maxsize", 0);
/*     */     
/* 125 */     prefSize = getInteger("com.sun.jndi.ldap.connect.pool.prefsize", 0);
/*     */     
/* 127 */     initSize = getInteger("com.sun.jndi.ldap.connect.pool.initsize", 1);
/*     */     
/* 129 */     idleTimeout = getLong("com.sun.jndi.ldap.connect.pool.timeout", 0L);
/*     */ 
/*     */     
/* 132 */     String str = getProperty("com.sun.jndi.ldap.connect.pool.authentication", "none simple");
/* 133 */     StringTokenizer stringTokenizer = new StringTokenizer(str);
/* 134 */     int i = stringTokenizer.countTokens();
/*     */ 
/*     */     
/* 137 */     for (byte b1 = 0; b1 < i; b1++) {
/* 138 */       String str1 = stringTokenizer.nextToken().toLowerCase(Locale.ENGLISH);
/* 139 */       if (str1.equals("anonymous")) {
/* 140 */         str1 = "none";
/*     */       }
/*     */       
/* 143 */       int j = findPool(str1);
/* 144 */       if (j >= 0 && pools[j] == null) {
/* 145 */         pools[j] = new Pool(initSize, prefSize, maxSize);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 150 */     str = getProperty("com.sun.jndi.ldap.connect.pool.protocol", "plain");
/* 151 */     stringTokenizer = new StringTokenizer(str);
/* 152 */     i = stringTokenizer.countTokens();
/*     */     
/* 154 */     for (byte b2 = 0; b2 < i; b2++) {
/* 155 */       String str1 = stringTokenizer.nextToken();
/* 156 */       if ("plain".equalsIgnoreCase(str1)) {
/* 157 */         supportPlainProtocol = true;
/* 158 */       } else if ("ssl".equalsIgnoreCase(str1)) {
/* 159 */         supportSslProtocol = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 165 */     if (idleTimeout > 0L) {
/*     */       
/* 167 */       PrivilegedAction<Void> privilegedAction = new PrivilegedAction<Void>() {
/*     */           public Void run() {
/* 169 */             Thread thread = InnocuousThread.newSystemThread("LDAP PoolCleaner", new PoolCleaner(LdapPoolManager
/*     */                   
/* 171 */                   .idleTimeout, LdapPoolManager.pools));
/* 172 */             assert thread.getContextClassLoader() == null;
/* 173 */             thread.setDaemon(true);
/* 174 */             thread.start();
/* 175 */             return null; }
/*     */         };
/* 177 */       AccessController.doPrivileged(privilegedAction);
/*     */     } 
/*     */     
/* 180 */     if (debug) {
/* 181 */       showStats(System.err);
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
/*     */   private static int findPool(String paramString) {
/* 196 */     if ("none".equalsIgnoreCase(paramString))
/* 197 */       return 0; 
/* 198 */     if ("simple".equalsIgnoreCase(paramString))
/* 199 */       return 1; 
/* 200 */     if ("digest-md5".equalsIgnoreCase(paramString)) {
/* 201 */       return 2;
/*     */     }
/* 203 */     return -1;
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
/*     */   static boolean isPoolingAllowed(String paramString1, OutputStream paramOutputStream, String paramString2, String paramString3, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 232 */     if ((paramOutputStream != null && !debug) || (paramString3 == null && !supportPlainProtocol) || ("ssl"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 238 */       .equalsIgnoreCase(paramString3) && !supportSslProtocol)) {
/*     */       
/* 240 */       d("Pooling disallowed due to tracing or unsupported pooling of protocol");
/* 241 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 245 */     String str = "java.util.Comparator";
/* 246 */     boolean bool = false;
/* 247 */     if (paramString1 != null && 
/* 248 */       !paramString1.equals("javax.net.ssl.SSLSocketFactory")) {
/*     */       try {
/* 250 */         Class<?> clazz = Obj.helper.loadClass(paramString1);
/* 251 */         Class[] arrayOfClass = clazz.getInterfaces();
/* 252 */         for (byte b = 0; b < arrayOfClass.length; b++) {
/* 253 */           if (arrayOfClass[b].getCanonicalName().equals(str)) {
/* 254 */             bool = true;
/*     */           }
/*     */         } 
/* 257 */       } catch (Exception exception) {
/* 258 */         CommunicationException communicationException = new CommunicationException("Loading the socket factory");
/*     */         
/* 260 */         communicationException.setRootCause(exception);
/* 261 */         throw communicationException;
/*     */       } 
/* 263 */       if (!bool) {
/* 264 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 269 */     int i = findPool(paramString2);
/* 270 */     if (i < 0 || pools[i] == null) {
/* 271 */       d("authmech not found: ", paramString2);
/*     */       
/* 273 */       return false;
/*     */     } 
/*     */     
/* 276 */     d("using authmech: ", paramString2);
/*     */     
/* 278 */     switch (i) {
/*     */       case 0:
/*     */       case 1:
/* 281 */         return true;
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 286 */         return (paramHashtable == null || paramHashtable.get("java.naming.security.sasl.callback") == null);
/*     */     } 
/* 288 */     return false;
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
/*     */   static LdapClient getLdapClient(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3, OutputStream paramOutputStream, int paramInt4, String paramString3, Control[] paramArrayOfControl, String paramString4, String paramString5, Object paramObject, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 312 */     ClientId clientId = null;
/*     */ 
/*     */     
/* 315 */     int i = findPool(paramString3); Pool pool;
/* 316 */     if (i < 0 || (pool = pools[i]) == null) {
/* 317 */       throw new IllegalArgumentException("Attempting to use pooling for an unsupported mechanism: " + paramString3);
/*     */     }
/*     */ 
/*     */     
/* 321 */     switch (i) {
/*     */       case 0:
/* 323 */         clientId = new ClientId(paramInt4, paramString1, paramInt1, paramString4, paramArrayOfControl, paramOutputStream, paramString2);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 329 */         clientId = new SimpleClientId(paramInt4, paramString1, paramInt1, paramString4, paramArrayOfControl, paramOutputStream, paramString2, paramString5, paramObject);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 335 */         clientId = new DigestClientId(paramInt4, paramString1, paramInt1, paramString4, paramArrayOfControl, paramOutputStream, paramString2, paramString5, paramObject, paramHashtable);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 340 */     return (LdapClient)pool.getPooledConnection(clientId, paramInt2, new LdapClientFactory(paramString1, paramInt1, paramString2, paramInt2, paramInt3, paramOutputStream));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void showStats(PrintStream paramPrintStream) {
/* 346 */     paramPrintStream.println("***** start *****");
/* 347 */     paramPrintStream.println("idle timeout: " + idleTimeout);
/* 348 */     paramPrintStream.println("maximum pool size: " + maxSize);
/* 349 */     paramPrintStream.println("preferred pool size: " + prefSize);
/* 350 */     paramPrintStream.println("initial pool size: " + initSize);
/* 351 */     paramPrintStream.println("protocol types: " + (supportPlainProtocol ? "plain " : "") + (supportSslProtocol ? "ssl" : ""));
/*     */     
/* 353 */     paramPrintStream.println("authentication types: " + ((pools[0] != null) ? "none " : "") + ((pools[1] != null) ? "simple " : "") + ((pools[2] != null) ? "DIGEST-MD5 " : ""));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     for (byte b = 0; b < pools.length; b++) {
/* 359 */       if (pools[b] != null) {
/* 360 */         paramPrintStream.println(((b == 0) ? "anonymous pools" : ((b == 1) ? "simple auth pools" : ((b == 2) ? "digest pools" : ""))) + ":");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 365 */         pools[b].showStats(paramPrintStream);
/*     */       } 
/*     */     } 
/* 368 */     paramPrintStream.println("***** end *****");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void expire(long paramLong) {
/* 379 */     for (byte b = 0; b < pools.length; b++) {
/* 380 */       if (pools[b] != null) {
/* 381 */         pools[b].expire(paramLong);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void d(String paramString) {
/* 387 */     if (debug) {
/* 388 */       System.err.println("LdapPoolManager: " + paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void d(String paramString1, String paramString2) {
/* 393 */     if (debug) {
/* 394 */       System.err.println("LdapPoolManager: " + paramString1 + paramString2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String getProperty(final String propName, final String defVal) {
/* 400 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run() {
/*     */             try {
/* 404 */               return System.getProperty(propName, defVal);
/* 405 */             } catch (SecurityException securityException) {
/* 406 */               return defVal;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static final int getInteger(final String propName, final int defVal) {
/* 414 */     Integer integer = AccessController.<Integer>doPrivileged(new PrivilegedAction<Integer>()
/*     */         {
/*     */           public Integer run() {
/*     */             try {
/* 418 */               return Integer.getInteger(propName, defVal);
/* 419 */             } catch (SecurityException securityException) {
/* 420 */               return new Integer(defVal);
/*     */             } 
/*     */           }
/*     */         });
/* 424 */     return integer.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private static final long getLong(final String propName, final long defVal) {
/* 429 */     Long long_ = AccessController.<Long>doPrivileged(new PrivilegedAction<Long>()
/*     */         {
/*     */           public Long run() {
/*     */             try {
/* 433 */               return Long.getLong(propName, defVal);
/* 434 */             } catch (SecurityException securityException) {
/* 435 */               return new Long(defVal);
/*     */             } 
/*     */           }
/*     */         });
/* 439 */     return long_.longValue();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapPoolManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */