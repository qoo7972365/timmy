/*     */ package sun.corba;
/*     */ 
/*     */ import java.io.ObjectInputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.reflect.ReflectionFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Bridge
/*     */ {
/*  74 */   private static final Class[] NO_ARGS = new Class[0];
/*  75 */   private static final Permission getBridgePermission = new BridgePermission("getBridge");
/*     */   
/*  77 */   private static Bridge bridge = null;
/*     */   
/*     */   private final Method latestUserDefinedLoaderMethod;
/*     */   
/*     */   private final Unsafe unsafe;
/*     */   
/*     */   private final ReflectionFactory reflectionFactory;
/*     */   
/*     */   public static final long INVALID_FIELD_OFFSET = -1L;
/*     */ 
/*     */   
/*     */   private Method getLatestUserDefinedLoaderMethod() {
/*  89 */     return AccessController.<Method>doPrivileged(new PrivilegedAction<Method>()
/*     */         {
/*     */           
/*     */           public Object run()
/*     */           {
/*  94 */             Method method = null;
/*     */             
/*     */             try {
/*  97 */               Class<ObjectInputStream> clazz = ObjectInputStream.class;
/*  98 */               method = clazz.getDeclaredMethod("latestUserDefinedLoader", Bridge
/*  99 */                   .NO_ARGS);
/* 100 */               method.setAccessible(true);
/* 101 */             } catch (NoSuchMethodException noSuchMethodException) {
/* 102 */               Error error = new Error("java.io.ObjectInputStream latestUserDefinedLoader " + noSuchMethodException);
/*     */               
/* 104 */               error.initCause(noSuchMethodException);
/* 105 */               throw error;
/*     */             } 
/*     */             
/* 108 */             return method;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private Unsafe getUnsafe() {
/* 115 */     Field field = AccessController.<Field>doPrivileged(new PrivilegedAction<Field>()
/*     */         {
/*     */           
/*     */           public Object run()
/*     */           {
/* 120 */             Field field = null;
/*     */             
/*     */             try {
/* 123 */               Class<Unsafe> clazz = Unsafe.class;
/* 124 */               field = clazz.getDeclaredField("theUnsafe");
/* 125 */               field.setAccessible(true);
/* 126 */               return field;
/* 127 */             } catch (NoSuchFieldException noSuchFieldException) {
/* 128 */               Error error = new Error("Could not access Unsafe");
/* 129 */               error.initCause(noSuchFieldException);
/* 130 */               throw error;
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 136 */     Unsafe unsafe = null;
/*     */     
/*     */     try {
/* 139 */       unsafe = (Unsafe)field.get(null);
/* 140 */     } catch (Throwable throwable) {
/* 141 */       Error error = new Error("Could not access Unsafe");
/* 142 */       error.initCause(throwable);
/* 143 */       throw error;
/*     */     } 
/*     */     
/* 146 */     return unsafe;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Bridge() {
/* 152 */     this.latestUserDefinedLoaderMethod = getLatestUserDefinedLoaderMethod();
/* 153 */     this.unsafe = getUnsafe();
/* 154 */     this.reflectionFactory = AccessController.<ReflectionFactory>doPrivileged((PrivilegedAction<ReflectionFactory>)new ReflectionFactory.GetReflectionFactoryAction());
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
/*     */   public static final synchronized Bridge get() {
/* 171 */     SecurityManager securityManager = System.getSecurityManager();
/* 172 */     if (securityManager != null) {
/* 173 */       securityManager.checkPermission(getBridgePermission);
/*     */     }
/* 175 */     if (bridge == null) {
/* 176 */       bridge = new Bridge();
/*     */     }
/*     */     
/* 179 */     return bridge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ClassLoader getLatestUserDefinedLoader() {
/*     */     try {
/* 189 */       return (ClassLoader)this.latestUserDefinedLoaderMethod.invoke(null, (Object[])NO_ARGS);
/*     */     }
/* 191 */     catch (InvocationTargetException invocationTargetException) {
/* 192 */       Error error = new Error("sun.corba.Bridge.latestUserDefinedLoader: " + invocationTargetException);
/*     */       
/* 194 */       error.initCause(invocationTargetException);
/* 195 */       throw error;
/* 196 */     } catch (IllegalAccessException illegalAccessException) {
/* 197 */       Error error = new Error("sun.corba.Bridge.latestUserDefinedLoader: " + illegalAccessException);
/*     */       
/* 199 */       error.initCause(illegalAccessException);
/* 200 */       throw error;
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
/*     */   public final int getInt(Object paramObject, long paramLong) {
/* 221 */     return this.unsafe.getInt(paramObject, paramLong);
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
/*     */   public final void putInt(Object paramObject, long paramLong, int paramInt) {
/* 244 */     this.unsafe.putInt(paramObject, paramLong, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Object getObject(Object paramObject, long paramLong) {
/* 252 */     return this.unsafe.getObject(paramObject, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void putObject(Object paramObject1, long paramLong, Object paramObject2) {
/* 260 */     this.unsafe.putObject(paramObject1, paramLong, paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean getBoolean(Object paramObject, long paramLong) {
/* 266 */     return this.unsafe.getBoolean(paramObject, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void putBoolean(Object paramObject, long paramLong, boolean paramBoolean) {
/* 271 */     this.unsafe.putBoolean(paramObject, paramLong, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public final byte getByte(Object paramObject, long paramLong) {
/* 276 */     return this.unsafe.getByte(paramObject, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void putByte(Object paramObject, long paramLong, byte paramByte) {
/* 281 */     this.unsafe.putByte(paramObject, paramLong, paramByte);
/*     */   }
/*     */ 
/*     */   
/*     */   public final short getShort(Object paramObject, long paramLong) {
/* 286 */     return this.unsafe.getShort(paramObject, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void putShort(Object paramObject, long paramLong, short paramShort) {
/* 291 */     this.unsafe.putShort(paramObject, paramLong, paramShort);
/*     */   }
/*     */ 
/*     */   
/*     */   public final char getChar(Object paramObject, long paramLong) {
/* 296 */     return this.unsafe.getChar(paramObject, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void putChar(Object paramObject, long paramLong, char paramChar) {
/* 301 */     this.unsafe.putChar(paramObject, paramLong, paramChar);
/*     */   }
/*     */ 
/*     */   
/*     */   public final long getLong(Object paramObject, long paramLong) {
/* 306 */     return this.unsafe.getLong(paramObject, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void putLong(Object paramObject, long paramLong1, long paramLong2) {
/* 311 */     this.unsafe.putLong(paramObject, paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */   
/*     */   public final float getFloat(Object paramObject, long paramLong) {
/* 316 */     return this.unsafe.getFloat(paramObject, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void putFloat(Object paramObject, long paramLong, float paramFloat) {
/* 321 */     this.unsafe.putFloat(paramObject, paramLong, paramFloat);
/*     */   }
/*     */ 
/*     */   
/*     */   public final double getDouble(Object paramObject, long paramLong) {
/* 326 */     return this.unsafe.getDouble(paramObject, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void putDouble(Object paramObject, long paramLong, double paramDouble) {
/* 331 */     this.unsafe.putDouble(paramObject, paramLong, paramDouble);
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
/*     */   public final long objectFieldOffset(Field paramField) {
/* 345 */     return this.unsafe.objectFieldOffset(paramField);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void throwException(Throwable paramThrowable) {
/* 353 */     this.unsafe.throwException(paramThrowable);
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
/*     */   public final Constructor newConstructorForSerialization(Class<?> paramClass, Constructor<?> paramConstructor) {
/* 366 */     return this.reflectionFactory.newConstructorForSerialization(paramClass, paramConstructor);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/corba/Bridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */