/*     */ package sun.instrument;
/*     */ 
/*     */ import java.lang.instrument.ClassDefinition;
/*     */ import java.lang.instrument.ClassFileTransformer;
/*     */ import java.lang.instrument.Instrumentation;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.jar.JarFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InstrumentationImpl
/*     */   implements Instrumentation
/*     */ {
/*     */   private final TransformerManager mTransformerManager;
/*     */   private TransformerManager mRetransfomableTransformerManager;
/*     */   private final long mNativeAgent;
/*     */   private final boolean mEnvironmentSupportsRedefineClasses;
/*     */   private volatile boolean mEnvironmentSupportsRetransformClassesKnown;
/*     */   private volatile boolean mEnvironmentSupportsRetransformClasses;
/*     */   private final boolean mEnvironmentSupportsNativeMethodPrefix;
/*     */   
/*     */   private InstrumentationImpl(long paramLong, boolean paramBoolean1, boolean paramBoolean2) {
/*  67 */     this.mTransformerManager = new TransformerManager(false);
/*  68 */     this.mRetransfomableTransformerManager = null;
/*  69 */     this.mNativeAgent = paramLong;
/*  70 */     this.mEnvironmentSupportsRedefineClasses = paramBoolean1;
/*  71 */     this.mEnvironmentSupportsRetransformClassesKnown = false;
/*  72 */     this.mEnvironmentSupportsRetransformClasses = false;
/*  73 */     this.mEnvironmentSupportsNativeMethodPrefix = paramBoolean2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addTransformer(ClassFileTransformer paramClassFileTransformer) {
/*  78 */     addTransformer(paramClassFileTransformer, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void addTransformer(ClassFileTransformer paramClassFileTransformer, boolean paramBoolean) {
/*  83 */     if (paramClassFileTransformer == null) {
/*  84 */       throw new NullPointerException("null passed as 'transformer' in addTransformer");
/*     */     }
/*  86 */     if (paramBoolean) {
/*  87 */       if (!isRetransformClassesSupported()) {
/*  88 */         throw new UnsupportedOperationException("adding retransformable transformers is not supported in this environment");
/*     */       }
/*     */       
/*  91 */       if (this.mRetransfomableTransformerManager == null) {
/*  92 */         this.mRetransfomableTransformerManager = new TransformerManager(true);
/*     */       }
/*  94 */       this.mRetransfomableTransformerManager.addTransformer(paramClassFileTransformer);
/*  95 */       if (this.mRetransfomableTransformerManager.getTransformerCount() == 1) {
/*  96 */         setHasRetransformableTransformers(this.mNativeAgent, true);
/*     */       }
/*     */     } else {
/*  99 */       this.mTransformerManager.addTransformer(paramClassFileTransformer);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean removeTransformer(ClassFileTransformer paramClassFileTransformer) {
/* 105 */     if (paramClassFileTransformer == null) {
/* 106 */       throw new NullPointerException("null passed as 'transformer' in removeTransformer");
/*     */     }
/* 108 */     TransformerManager transformerManager = findTransformerManager(paramClassFileTransformer);
/* 109 */     if (transformerManager != null) {
/* 110 */       transformerManager.removeTransformer(paramClassFileTransformer);
/* 111 */       if (transformerManager.isRetransformable() && transformerManager.getTransformerCount() == 0) {
/* 112 */         setHasRetransformableTransformers(this.mNativeAgent, false);
/*     */       }
/* 114 */       return true;
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isModifiableClass(Class<?> paramClass) {
/* 121 */     if (paramClass == null) {
/* 122 */       throw new NullPointerException("null passed as 'theClass' in isModifiableClass");
/*     */     }
/*     */     
/* 125 */     return isModifiableClass0(this.mNativeAgent, paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRetransformClassesSupported() {
/* 131 */     if (!this.mEnvironmentSupportsRetransformClassesKnown) {
/* 132 */       this.mEnvironmentSupportsRetransformClasses = isRetransformClassesSupported0(this.mNativeAgent);
/* 133 */       this.mEnvironmentSupportsRetransformClassesKnown = true;
/*     */     } 
/* 135 */     return this.mEnvironmentSupportsRetransformClasses;
/*     */   }
/*     */ 
/*     */   
/*     */   public void retransformClasses(Class<?>... paramVarArgs) {
/* 140 */     if (!isRetransformClassesSupported()) {
/* 141 */       throw new UnsupportedOperationException("retransformClasses is not supported in this environment");
/*     */     }
/*     */     
/* 144 */     retransformClasses0(this.mNativeAgent, paramVarArgs);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRedefineClassesSupported() {
/* 149 */     return this.mEnvironmentSupportsRedefineClasses;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void redefineClasses(ClassDefinition... paramVarArgs) throws ClassNotFoundException {
/* 155 */     if (!isRedefineClassesSupported()) {
/* 156 */       throw new UnsupportedOperationException("redefineClasses is not supported in this environment");
/*     */     }
/* 158 */     if (paramVarArgs == null) {
/* 159 */       throw new NullPointerException("null passed as 'definitions' in redefineClasses");
/*     */     }
/* 161 */     for (byte b = 0; b < paramVarArgs.length; b++) {
/* 162 */       if (paramVarArgs[b] == null) {
/* 163 */         throw new NullPointerException("element of 'definitions' is null in redefineClasses");
/*     */       }
/*     */     } 
/* 166 */     if (paramVarArgs.length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 170 */     redefineClasses0(this.mNativeAgent, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class[] getAllLoadedClasses() {
/* 176 */     return getAllLoadedClasses0(this.mNativeAgent);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class[] getInitiatedClasses(ClassLoader paramClassLoader) {
/* 182 */     return getInitiatedClasses0(this.mNativeAgent, paramClassLoader);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getObjectSize(Object paramObject) {
/* 187 */     if (paramObject == null) {
/* 188 */       throw new NullPointerException("null passed as 'objectToSize' in getObjectSize");
/*     */     }
/* 190 */     return getObjectSize0(this.mNativeAgent, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendToBootstrapClassLoaderSearch(JarFile paramJarFile) {
/* 195 */     appendToClassLoaderSearch0(this.mNativeAgent, paramJarFile.getName(), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendToSystemClassLoaderSearch(JarFile paramJarFile) {
/* 200 */     appendToClassLoaderSearch0(this.mNativeAgent, paramJarFile.getName(), false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNativeMethodPrefixSupported() {
/* 205 */     return this.mEnvironmentSupportsNativeMethodPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setNativeMethodPrefix(ClassFileTransformer paramClassFileTransformer, String paramString) {
/* 210 */     if (!isNativeMethodPrefixSupported()) {
/* 211 */       throw new UnsupportedOperationException("setNativeMethodPrefix is not supported in this environment");
/*     */     }
/*     */     
/* 214 */     if (paramClassFileTransformer == null) {
/* 215 */       throw new NullPointerException("null passed as 'transformer' in setNativeMethodPrefix");
/*     */     }
/*     */     
/* 218 */     TransformerManager transformerManager = findTransformerManager(paramClassFileTransformer);
/* 219 */     if (transformerManager == null) {
/* 220 */       throw new IllegalArgumentException("transformer not registered in setNativeMethodPrefix");
/*     */     }
/*     */     
/* 223 */     transformerManager.setNativeMethodPrefix(paramClassFileTransformer, paramString);
/* 224 */     String[] arrayOfString = transformerManager.getNativeMethodPrefixes();
/* 225 */     setNativeMethodPrefixes(this.mNativeAgent, arrayOfString, transformerManager.isRetransformable());
/*     */   }
/*     */ 
/*     */   
/*     */   private TransformerManager findTransformerManager(ClassFileTransformer paramClassFileTransformer) {
/* 230 */     if (this.mTransformerManager.includesTransformer(paramClassFileTransformer)) {
/* 231 */       return this.mTransformerManager;
/*     */     }
/* 233 */     if (this.mRetransfomableTransformerManager != null && this.mRetransfomableTransformerManager
/* 234 */       .includesTransformer(paramClassFileTransformer)) {
/* 235 */       return this.mRetransfomableTransformerManager;
/*     */     }
/* 237 */     return null;
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
/*     */   static {
/* 278 */     System.loadLibrary("instrument");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setAccessible(final AccessibleObject ao, final boolean accessible) {
/* 289 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public Object run() {
/* 291 */             ao.setAccessible(accessible);
/* 292 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadClassAndStartAgent(String paramString1, String paramString2, String paramString3) throws Throwable {
/* 303 */     ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 304 */     Class<?> clazz = classLoader.loadClass(paramString1);
/*     */     
/* 306 */     Method method = null;
/* 307 */     NoSuchMethodException noSuchMethodException = null;
/* 308 */     boolean bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 327 */       method = clazz.getDeclaredMethod(paramString2, new Class[] { String.class, Instrumentation.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 333 */       bool = true;
/* 334 */     } catch (NoSuchMethodException noSuchMethodException1) {
/*     */       
/* 336 */       noSuchMethodException = noSuchMethodException1;
/*     */     } 
/*     */     
/* 339 */     if (method == null) {
/*     */       
/*     */       try {
/* 342 */         method = clazz.getDeclaredMethod(paramString2, new Class[] { String.class });
/*     */       }
/* 344 */       catch (NoSuchMethodException noSuchMethodException1) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 350 */     if (method == null) {
/*     */       
/*     */       try {
/* 353 */         method = clazz.getMethod(paramString2, new Class[] { String.class, Instrumentation.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 359 */         bool = true;
/* 360 */       } catch (NoSuchMethodException noSuchMethodException1) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 366 */     if (method == null) {
/*     */       
/*     */       try {
/* 369 */         method = clazz.getMethod(paramString2, new Class[] { String.class });
/*     */       }
/* 371 */       catch (NoSuchMethodException noSuchMethodException1) {
/*     */ 
/*     */         
/* 374 */         throw noSuchMethodException;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 382 */     setAccessible(method, true);
/*     */ 
/*     */     
/* 385 */     if (bool) {
/* 386 */       method.invoke(null, new Object[] { paramString3, this });
/*     */     } else {
/* 388 */       method.invoke(null, new Object[] { paramString3 });
/*     */     } 
/*     */ 
/*     */     
/* 392 */     setAccessible(method, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadClassAndCallPremain(String paramString1, String paramString2) throws Throwable {
/* 401 */     loadClassAndStartAgent(paramString1, "premain", paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadClassAndCallAgentmain(String paramString1, String paramString2) throws Throwable {
/* 411 */     loadClassAndStartAgent(paramString1, "agentmain", paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] transform(ClassLoader paramClassLoader, String paramString, Class<?> paramClass, ProtectionDomain paramProtectionDomain, byte[] paramArrayOfbyte, boolean paramBoolean) {
/* 422 */     TransformerManager transformerManager = paramBoolean ? this.mRetransfomableTransformerManager : this.mTransformerManager;
/*     */ 
/*     */     
/* 425 */     if (transformerManager == null) {
/* 426 */       return null;
/*     */     }
/* 428 */     return transformerManager.transform(paramClassLoader, paramString, paramClass, paramProtectionDomain, paramArrayOfbyte);
/*     */   }
/*     */   
/*     */   private native boolean isModifiableClass0(long paramLong, Class<?> paramClass);
/*     */   
/*     */   private native boolean isRetransformClassesSupported0(long paramLong);
/*     */   
/*     */   private native void setHasRetransformableTransformers(long paramLong, boolean paramBoolean);
/*     */   
/*     */   private native void retransformClasses0(long paramLong, Class<?>[] paramArrayOfClass);
/*     */   
/*     */   private native void redefineClasses0(long paramLong, ClassDefinition[] paramArrayOfClassDefinition) throws ClassNotFoundException;
/*     */   
/*     */   private native Class[] getAllLoadedClasses0(long paramLong);
/*     */   
/*     */   private native Class[] getInitiatedClasses0(long paramLong, ClassLoader paramClassLoader);
/*     */   
/*     */   private native long getObjectSize0(long paramLong, Object paramObject);
/*     */   
/*     */   private native void appendToClassLoaderSearch0(long paramLong, String paramString, boolean paramBoolean);
/*     */   
/*     */   private native void setNativeMethodPrefixes(long paramLong, String[] paramArrayOfString, boolean paramBoolean);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/instrument/InstrumentationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */