/*     */ package jdk.xml.internal;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SecuritySupport
/*     */ {
/*  46 */   static final Properties cacheProps = new Properties();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static volatile boolean firstTime = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getSystemProperty(final String propName) {
/*  62 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run() {
/*  65 */             return System.getProperty(propName);
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
/*     */   public static <T> T getJAXPSystemProperty(Class<T> type, String propName, String defValue) {
/*  81 */     String value = getJAXPSystemProperty(propName);
/*  82 */     if (value == null) {
/*  83 */       value = defValue;
/*     */     }
/*  85 */     if (Integer.class.isAssignableFrom(type))
/*  86 */       return type.cast(Integer.valueOf(Integer.parseInt(value))); 
/*  87 */     if (Boolean.class.isAssignableFrom(type)) {
/*  88 */       return type.cast(Boolean.valueOf(Boolean.parseBoolean(value)));
/*     */     }
/*  90 */     return type.cast(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getJAXPSystemProperty(String propName) {
/* 101 */     String value = getSystemProperty(propName);
/* 102 */     if (value == null) {
/* 103 */       value = readJAXPProperty(propName);
/*     */     }
/* 105 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String readJAXPProperty(String propName) {
/* 115 */     String value = null;
/* 116 */     InputStream is = null;
/*     */     
/* 118 */     try { if (firstTime) {
/* 119 */         synchronized (cacheProps) {
/* 120 */           if (firstTime) {
/* 121 */             String configFile = getSystemProperty("java.home") + File.separator + "lib" + File.separator + "jaxp.properties";
/*     */             
/* 123 */             File f = new File(configFile);
/* 124 */             if (getFileExists(f)) {
/* 125 */               is = getFileInputStream(f);
/* 126 */               cacheProps.load(is);
/*     */             } 
/* 128 */             firstTime = false;
/*     */           } 
/*     */         } 
/*     */       }
/* 132 */       value = cacheProps.getProperty(propName); }
/*     */     
/* 134 */     catch (IOException iOException) {  }
/*     */     finally
/* 136 */     { if (is != null) {
/*     */         try {
/* 138 */           is.close();
/* 139 */         } catch (IOException iOException) {}
/*     */       } }
/*     */ 
/*     */     
/* 143 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean getFileExists(final File f) {
/* 148 */     return ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */         {
/*     */           public Boolean run() {
/* 151 */             return f.exists() ? Boolean.TRUE : Boolean.FALSE;
/*     */           }
/*     */         })).booleanValue();
/*     */   }
/*     */   
/*     */   static FileInputStream getFileInputStream(final File file) throws FileNotFoundException {
/*     */     try {
/* 158 */       return AccessController.<FileInputStream>doPrivileged(new PrivilegedExceptionAction<FileInputStream>()
/*     */           {
/*     */             public FileInputStream run() throws Exception {
/* 161 */               return new FileInputStream(file);
/*     */             }
/*     */           });
/* 164 */     } catch (PrivilegedActionException e) {
/* 165 */       throw (FileNotFoundException)e.getException();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/xml/internal/SecuritySupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */