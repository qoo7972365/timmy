/*     */ package java.beans;
/*     */ 
/*     */ import com.sun.beans.finder.ClassFinder;
/*     */ import java.applet.Applet;
/*     */ import java.beans.beancontext.BeanContext;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.net.URL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Beans
/*     */ {
/*     */   public static Object instantiate(ClassLoader paramClassLoader, String paramString) throws IOException, ClassNotFoundException {
/*  77 */     return instantiate(paramClassLoader, paramString, null, null);
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
/*     */   public static Object instantiate(ClassLoader paramClassLoader, String paramString, BeanContext paramBeanContext) throws IOException, ClassNotFoundException {
/*  99 */     return instantiate(paramClassLoader, paramString, paramBeanContext, null);
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
/*     */   public static Object instantiate(ClassLoader paramClassLoader, String paramString, BeanContext paramBeanContext, AppletInitializer paramAppletInitializer) throws IOException, ClassNotFoundException {
/*     */     InputStream inputStream;
/* 158 */     ObjectInputStream objectInputStream = null;
/* 159 */     Object object = null;
/* 160 */     boolean bool = false;
/* 161 */     IOException iOException = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     if (paramClassLoader == null) {
/*     */       try {
/* 170 */         paramClassLoader = ClassLoader.getSystemClassLoader();
/* 171 */       } catch (SecurityException securityException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     String str = paramString.replace('.', '/').concat(".ser");
/* 179 */     if (paramClassLoader == null) {
/* 180 */       inputStream = ClassLoader.getSystemResourceAsStream(str);
/*     */     } else {
/* 182 */       inputStream = paramClassLoader.getResourceAsStream(str);
/* 183 */     }  if (inputStream != null) {
/*     */       try {
/* 185 */         if (paramClassLoader == null) {
/* 186 */           objectInputStream = new ObjectInputStream(inputStream);
/*     */         } else {
/* 188 */           objectInputStream = new ObjectInputStreamWithLoader(inputStream, paramClassLoader);
/*     */         } 
/* 190 */         object = objectInputStream.readObject();
/* 191 */         bool = true;
/* 192 */         objectInputStream.close();
/* 193 */       } catch (IOException iOException1) {
/* 194 */         inputStream.close();
/*     */ 
/*     */         
/* 197 */         iOException = iOException1;
/* 198 */       } catch (ClassNotFoundException classNotFoundException) {
/* 199 */         inputStream.close();
/* 200 */         throw classNotFoundException;
/*     */       } 
/*     */     }
/*     */     
/* 204 */     if (object == null) {
/*     */       Class<?> clazz;
/*     */ 
/*     */       
/*     */       try {
/* 209 */         clazz = ClassFinder.findClass(paramString, paramClassLoader);
/* 210 */       } catch (ClassNotFoundException classNotFoundException) {
/*     */ 
/*     */ 
/*     */         
/* 214 */         if (iOException != null) {
/* 215 */           throw iOException;
/*     */         }
/* 217 */         throw classNotFoundException;
/*     */       } 
/*     */       
/* 220 */       if (!Modifier.isPublic(clazz.getModifiers())) {
/* 221 */         throw new ClassNotFoundException("" + clazz + " : no public access");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 229 */         object = clazz.newInstance();
/* 230 */       } catch (Exception exception) {
/*     */ 
/*     */         
/* 233 */         throw new ClassNotFoundException("" + clazz + " : " + exception, exception);
/*     */       } 
/*     */     } 
/*     */     
/* 237 */     if (object != null) {
/*     */ 
/*     */ 
/*     */       
/* 241 */       BeansAppletStub beansAppletStub = null;
/*     */       
/* 243 */       if (object instanceof Applet)
/* 244 */       { Applet applet = (Applet)object;
/* 245 */         boolean bool1 = (paramAppletInitializer == null) ? true : false;
/*     */         
/* 247 */         if (bool1) {
/*     */           String str1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 260 */           if (bool) {
/*     */             
/* 262 */             str1 = paramString.replace('.', '/').concat(".ser");
/*     */           } else {
/*     */             
/* 265 */             str1 = paramString.replace('.', '/').concat(".class");
/*     */           } 
/*     */           
/* 268 */           URL uRL1 = null;
/* 269 */           URL uRL2 = null;
/* 270 */           URL uRL3 = null;
/*     */ 
/*     */           
/* 273 */           if (paramClassLoader == null) {
/* 274 */             uRL1 = ClassLoader.getSystemResource(str1);
/*     */           } else {
/* 276 */             uRL1 = paramClassLoader.getResource(str1);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 286 */           if (uRL1 != null) {
/* 287 */             String str2 = uRL1.toExternalForm();
/*     */             
/* 289 */             if (str2.endsWith(str1)) {
/* 290 */               int i = str2.length() - str1.length();
/* 291 */               uRL2 = new URL(str2.substring(0, i));
/* 292 */               uRL3 = uRL2;
/*     */               
/* 294 */               i = str2.lastIndexOf('/');
/*     */               
/* 296 */               if (i >= 0) {
/* 297 */                 uRL3 = new URL(str2.substring(0, i + 1));
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 303 */           BeansAppletContext beansAppletContext = new BeansAppletContext(applet);
/*     */           
/* 305 */           beansAppletStub = new BeansAppletStub(applet, beansAppletContext, uRL2, uRL3);
/* 306 */           applet.setStub(beansAppletStub);
/*     */         } else {
/* 308 */           paramAppletInitializer.initialize(applet, paramBeanContext);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 313 */         if (paramBeanContext != null) {
/* 314 */           unsafeBeanContextAdd(paramBeanContext, object);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 320 */         if (!bool) {
/*     */ 
/*     */ 
/*     */           
/* 324 */           applet.setSize(100, 100);
/* 325 */           applet.init();
/*     */         } 
/*     */         
/* 328 */         if (bool1)
/* 329 */         { beansAppletStub.active = true; }
/* 330 */         else { paramAppletInitializer.activate(applet); }
/*     */          }
/* 332 */       else if (paramBeanContext != null) { unsafeBeanContextAdd(paramBeanContext, object); }
/*     */     
/*     */     } 
/* 335 */     return object;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void unsafeBeanContextAdd(BeanContext paramBeanContext, Object paramObject) {
/* 340 */     paramBeanContext.add((E)paramObject);
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
/*     */   public static Object getInstanceOf(Object paramObject, Class<?> paramClass) {
/* 361 */     return paramObject;
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
/*     */   public static boolean isInstanceOf(Object paramObject, Class<?> paramClass) {
/* 376 */     return Introspector.isSubclass(paramObject.getClass(), paramClass);
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
/*     */   public static boolean isDesignTime() {
/* 388 */     return ThreadGroupContext.getContext().isDesignTime();
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
/*     */   public static boolean isGuiAvailable() {
/* 405 */     return ThreadGroupContext.getContext().isGuiAvailable();
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
/*     */   public static void setDesignTime(boolean paramBoolean) throws SecurityException {
/* 427 */     SecurityManager securityManager = System.getSecurityManager();
/* 428 */     if (securityManager != null) {
/* 429 */       securityManager.checkPropertiesAccess();
/*     */     }
/* 431 */     ThreadGroupContext.getContext().setDesignTime(paramBoolean);
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
/*     */   public static void setGuiAvailable(boolean paramBoolean) throws SecurityException {
/* 453 */     SecurityManager securityManager = System.getSecurityManager();
/* 454 */     if (securityManager != null) {
/* 455 */       securityManager.checkPropertiesAccess();
/*     */     }
/* 457 */     ThreadGroupContext.getContext().setGuiAvailable(paramBoolean);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/Beans.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */