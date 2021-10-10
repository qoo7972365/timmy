/*     */ package javax.sql.rowset;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessControlException;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
/*     */ import java.util.PropertyPermission;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RowSetProvider
/*     */ {
/*     */   private static final String ROWSET_DEBUG_PROPERTY = "javax.sql.rowset.RowSetProvider.debug";
/*     */   private static final String ROWSET_FACTORY_IMPL = "com.sun.rowset.RowSetFactoryImpl";
/*     */   private static final String ROWSET_FACTORY_NAME = "javax.sql.rowset.RowSetFactory";
/*     */   private static boolean debug = true;
/*     */   
/*     */   static {
/*  72 */     String str = getSystemProperty("javax.sql.rowset.RowSetProvider.debug");
/*     */     
/*  74 */     debug = (str != null && !"false".equals(str));
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
/*     */   public static RowSetFactory newFactory() throws SQLException {
/* 128 */     RowSetFactory rowSetFactory = null;
/* 129 */     String str = null;
/*     */     try {
/* 131 */       trace("Checking for Rowset System Property...");
/* 132 */       str = getSystemProperty("javax.sql.rowset.RowSetFactory");
/* 133 */       if (str != null) {
/* 134 */         trace("Found system property, value=" + str);
/* 135 */         rowSetFactory = (RowSetFactory)ReflectUtil.newInstance(getFactoryClass(str, null, true));
/*     */       } 
/* 137 */     } catch (Exception exception) {
/* 138 */       throw new SQLException("RowSetFactory: " + str + " could not be instantiated: ", exception);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (rowSetFactory == null) {
/*     */ 
/*     */ 
/*     */       
/* 147 */       rowSetFactory = loadViaServiceLoader();
/*     */       
/* 149 */       rowSetFactory = (rowSetFactory == null) ? newFactory("com.sun.rowset.RowSetFactoryImpl", null) : rowSetFactory;
/*     */     } 
/* 151 */     return rowSetFactory;
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
/*     */   public static RowSetFactory newFactory(String paramString, ClassLoader paramClassLoader) throws SQLException {
/* 183 */     trace("***In newInstance()");
/*     */     
/* 185 */     if (paramString == null) {
/* 186 */       throw new SQLException("Error: factoryClassName cannot be null");
/*     */     }
/*     */     try {
/* 189 */       ReflectUtil.checkPackageAccess(paramString);
/* 190 */     } catch (AccessControlException accessControlException) {
/* 191 */       throw new SQLException("Access Exception", accessControlException);
/*     */     } 
/*     */     
/*     */     try {
/* 195 */       Class<?> clazz = getFactoryClass(paramString, paramClassLoader, false);
/* 196 */       RowSetFactory rowSetFactory = (RowSetFactory)clazz.newInstance();
/* 197 */       if (debug) {
/* 198 */         trace("Created new instance of " + clazz + " using ClassLoader: " + paramClassLoader);
/*     */       }
/*     */       
/* 201 */       return rowSetFactory;
/* 202 */     } catch (ClassNotFoundException classNotFoundException) {
/* 203 */       throw new SQLException("Provider " + paramString + " not found", classNotFoundException);
/*     */     }
/* 205 */     catch (Exception exception) {
/* 206 */       throw new SQLException("Provider " + paramString + " could not be instantiated: " + exception, exception);
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
/*     */   private static ClassLoader getContextClassLoader() throws SecurityException {
/* 218 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */         {
/*     */           public ClassLoader run() {
/* 221 */             ClassLoader classLoader = null;
/*     */             
/* 223 */             classLoader = Thread.currentThread().getContextClassLoader();
/*     */             
/* 225 */             if (classLoader == null) {
/* 226 */               classLoader = ClassLoader.getSystemClassLoader();
/*     */             }
/*     */             
/* 229 */             return classLoader;
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
/*     */   private static Class<?> getFactoryClass(String paramString, ClassLoader paramClassLoader, boolean paramBoolean) throws ClassNotFoundException {
/*     */     try {
/* 246 */       if (paramClassLoader == null) {
/* 247 */         paramClassLoader = getContextClassLoader();
/* 248 */         if (paramClassLoader == null) {
/* 249 */           throw new ClassNotFoundException();
/*     */         }
/* 251 */         return paramClassLoader.loadClass(paramString);
/*     */       } 
/*     */       
/* 254 */       return paramClassLoader.loadClass(paramString);
/*     */     }
/* 256 */     catch (ClassNotFoundException classNotFoundException) {
/* 257 */       if (paramBoolean)
/*     */       {
/* 259 */         return Class.forName(paramString, true, RowSetFactory.class.getClassLoader());
/*     */       }
/* 261 */       throw classNotFoundException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RowSetFactory loadViaServiceLoader() throws SQLException {
/* 271 */     RowSetFactory rowSetFactory = null;
/*     */     try {
/* 273 */       trace("***in loadViaServiceLoader():");
/* 274 */       Iterator<RowSetFactory> iterator = ServiceLoader.<RowSetFactory>load(RowSetFactory.class).iterator(); if (iterator.hasNext()) { RowSetFactory rowSetFactory1 = iterator.next();
/* 275 */         trace(" Loading done by the java.util.ServiceLoader :" + rowSetFactory1.getClass().getName());
/* 276 */         rowSetFactory = rowSetFactory1; }
/*     */ 
/*     */     
/* 279 */     } catch (ServiceConfigurationError serviceConfigurationError) {
/* 280 */       throw new SQLException("RowSetFactory: Error locating RowSetFactory using Service Loader API: " + serviceConfigurationError, serviceConfigurationError);
/*     */     } 
/*     */ 
/*     */     
/* 284 */     return rowSetFactory;
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
/*     */   private static String getSystemProperty(final String propName) {
/* 296 */     String str = null;
/*     */     try {
/* 298 */       str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */           {
/*     */             public String run() {
/* 301 */               return System.getProperty(propName);
/*     */             }
/*     */           },  (AccessControlContext)null, new Permission[] { new PropertyPermission(propName, "read") });
/* 304 */     } catch (SecurityException securityException) {
/* 305 */       trace("error getting " + propName + ":  " + securityException);
/* 306 */       if (debug) {
/* 307 */         securityException.printStackTrace();
/*     */       }
/*     */     } 
/* 310 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void trace(String paramString) {
/* 319 */     if (debug)
/* 320 */       System.err.println("###RowSets: " + paramString); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/RowSetProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */