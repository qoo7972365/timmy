/*     */ package com.sun.xml.internal.bind.v2.runtime.reflect.opt;
/*     */ 
/*     */ import com.sun.xml.internal.bind.Util;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Injector
/*     */ {
/*  61 */   private static final ReentrantReadWriteLock irwl = new ReentrantReadWriteLock();
/*  62 */   private static final Lock ir = irwl.readLock();
/*  63 */   private static final Lock iw = irwl.writeLock();
/*  64 */   private static final Map<ClassLoader, WeakReference<Injector>> injectors = new WeakHashMap<>();
/*     */   
/*  66 */   private static final Logger logger = Util.getClassLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class inject(ClassLoader cl, String className, byte[] image) {
/*  75 */     Injector injector = get(cl);
/*  76 */     if (injector != null) {
/*  77 */       return injector.inject(className, image);
/*     */     }
/*  79 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class find(ClassLoader cl, String className) {
/*  87 */     Injector injector = get(cl);
/*  88 */     if (injector != null) {
/*  89 */       return injector.find(className);
/*     */     }
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Injector get(ClassLoader cl) {
/*     */     WeakReference<Injector> wr;
/* 102 */     Injector injector = null;
/*     */     
/* 104 */     ir.lock();
/*     */     try {
/* 106 */       wr = injectors.get(cl);
/*     */     } finally {
/* 108 */       ir.unlock();
/*     */     } 
/* 110 */     if (wr != null) {
/* 111 */       injector = wr.get();
/*     */     }
/* 113 */     if (injector == null) {
/*     */       try {
/* 115 */         wr = new WeakReference<>(injector = new Injector(cl));
/* 116 */         iw.lock();
/*     */         try {
/* 118 */           if (!injectors.containsKey(cl)) {
/* 119 */             injectors.put(cl, wr);
/*     */           }
/*     */         } finally {
/* 122 */           iw.unlock();
/*     */         } 
/* 124 */       } catch (SecurityException e) {
/* 125 */         logger.log(Level.FINE, "Unable to set up a back-door for the injector", e);
/* 126 */         return null;
/*     */       } 
/*     */     }
/* 129 */     return injector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 134 */   private final Map<String, Class> classes = (Map)new HashMap<>();
/* 135 */   private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
/* 136 */   private final Lock r = this.rwl.readLock();
/* 137 */   private final Lock w = this.rwl.writeLock();
/*     */   
/*     */   private final ClassLoader parent;
/*     */   
/*     */   private final boolean loadable;
/*     */   
/*     */   private static final Method defineClass;
/*     */   
/*     */   private static final Method resolveClass;
/*     */   private static final Method findLoadedClass;
/*     */   
/*     */   static {
/*     */     try {
/* 150 */       defineClass = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class });
/* 151 */       resolveClass = ClassLoader.class.getDeclaredMethod("resolveClass", new Class[] { Class.class });
/* 152 */       findLoadedClass = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
/* 153 */     } catch (NoSuchMethodException e) {
/*     */       
/* 155 */       throw new NoSuchMethodError(e.getMessage());
/*     */     } 
/* 157 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */ 
/*     */           
/*     */           public Void run()
/*     */           {
/* 163 */             Injector.defineClass.setAccessible(true);
/* 164 */             Injector.resolveClass.setAccessible(true);
/* 165 */             Injector.findLoadedClass.setAccessible(true);
/* 166 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private Injector(ClassLoader parent) {
/* 172 */     this.parent = parent;
/* 173 */     assert parent != null;
/*     */     
/* 175 */     boolean loadableCheck = false;
/*     */     
/*     */     try {
/* 178 */       loadableCheck = (parent.loadClass(Accessor.class.getName()) == Accessor.class);
/* 179 */     } catch (ClassNotFoundException classNotFoundException) {}
/*     */ 
/*     */ 
/*     */     
/* 183 */     this.loadable = loadableCheck;
/*     */   }
/*     */ 
/*     */   
/*     */   private Class inject(String className, byte[] image) {
/* 188 */     if (!this.loadable)
/*     */     {
/* 190 */       return null;
/*     */     }
/*     */     
/* 193 */     boolean wlocked = false;
/* 194 */     boolean rlocked = false;
/*     */     
/*     */     try {
/* 197 */       this.r.lock();
/* 198 */       rlocked = true;
/*     */       
/* 200 */       Class c = this.classes.get(className);
/*     */ 
/*     */ 
/*     */       
/* 204 */       this.r.unlock();
/* 205 */       rlocked = false;
/*     */ 
/*     */       
/* 208 */       if (c == null) {
/*     */         
/*     */         try {
/* 211 */           c = (Class)findLoadedClass.invoke(this.parent, new Object[] { className.replace('/', '.') });
/* 212 */         } catch (IllegalArgumentException e) {
/* 213 */           logger.log(Level.FINE, "Unable to find " + className, e);
/* 214 */         } catch (IllegalAccessException e) {
/* 215 */           logger.log(Level.FINE, "Unable to find " + className, e);
/* 216 */         } catch (InvocationTargetException e) {
/* 217 */           Throwable t = e.getTargetException();
/* 218 */           logger.log(Level.FINE, "Unable to find " + className, t);
/*     */         } 
/*     */         
/* 221 */         if (c != null) {
/*     */           
/* 223 */           this.w.lock();
/* 224 */           wlocked = true;
/*     */           
/* 226 */           this.classes.put(className, c);
/*     */           
/* 228 */           this.w.unlock();
/* 229 */           wlocked = false;
/*     */           
/* 231 */           return c;
/*     */         } 
/*     */       } 
/*     */       
/* 235 */       if (c == null) {
/*     */         
/* 237 */         this.r.lock();
/* 238 */         rlocked = true;
/*     */         
/* 240 */         c = this.classes.get(className);
/*     */ 
/*     */ 
/*     */         
/* 244 */         this.r.unlock();
/* 245 */         rlocked = false;
/*     */         
/* 247 */         if (c == null) {
/*     */ 
/*     */           
/*     */           try {
/* 251 */             c = (Class)defineClass.invoke(this.parent, new Object[] { className.replace('/', '.'), image, Integer.valueOf(0), Integer.valueOf(image.length) });
/* 252 */             resolveClass.invoke(this.parent, new Object[] { c });
/* 253 */           } catch (IllegalAccessException e) {
/* 254 */             logger.log(Level.FINE, "Unable to inject " + className, e);
/* 255 */             return null;
/* 256 */           } catch (InvocationTargetException e) {
/* 257 */             Throwable t = e.getTargetException();
/* 258 */             if (t instanceof LinkageError) {
/* 259 */               logger.log(Level.FINE, "duplicate class definition bug occured? Please report this : " + className, t);
/*     */             } else {
/* 261 */               logger.log(Level.FINE, "Unable to inject " + className, t);
/*     */             } 
/* 263 */             return null;
/* 264 */           } catch (SecurityException e) {
/* 265 */             logger.log(Level.FINE, "Unable to inject " + className, e);
/* 266 */             return null;
/* 267 */           } catch (LinkageError e) {
/* 268 */             logger.log(Level.FINE, "Unable to inject " + className, e);
/* 269 */             return null;
/*     */           } 
/*     */           
/* 272 */           this.w.lock();
/* 273 */           wlocked = true;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 278 */           if (!this.classes.containsKey(className)) {
/* 279 */             this.classes.put(className, c);
/*     */           }
/*     */           
/* 282 */           this.w.unlock();
/* 283 */           wlocked = false;
/*     */         } 
/*     */       } 
/* 286 */       return c;
/*     */     } finally {
/* 288 */       if (rlocked) {
/* 289 */         this.r.unlock();
/*     */       }
/* 291 */       if (wlocked) {
/* 292 */         this.w.unlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private Class find(String className) {
/* 298 */     this.r.lock();
/*     */     try {
/* 300 */       return this.classes.get(className);
/*     */     } finally {
/* 302 */       this.r.unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/opt/Injector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */