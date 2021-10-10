/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Service<S>
/*     */ {
/*     */   private static final String prefix = "META-INF/services/";
/*     */   
/*     */   private static void fail(Class<?> paramClass, String paramString, Throwable paramThrowable) throws ServiceConfigurationError {
/* 138 */     ServiceConfigurationError serviceConfigurationError = new ServiceConfigurationError(paramClass.getName() + ": " + paramString);
/* 139 */     serviceConfigurationError.initCause(paramThrowable);
/* 140 */     throw serviceConfigurationError;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void fail(Class<?> paramClass, String paramString) throws ServiceConfigurationError {
/* 146 */     throw new ServiceConfigurationError(paramClass.getName() + ": " + paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void fail(Class<?> paramClass, URL paramURL, int paramInt, String paramString) throws ServiceConfigurationError {
/* 152 */     fail(paramClass, paramURL + ":" + paramInt + ": " + paramString);
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
/*     */   private static int parseLine(Class<?> paramClass, URL paramURL, BufferedReader paramBufferedReader, int paramInt, List<String> paramList, Set<String> paramSet) throws IOException, ServiceConfigurationError {
/* 164 */     String str = paramBufferedReader.readLine();
/* 165 */     if (str == null) {
/* 166 */       return -1;
/*     */     }
/* 168 */     int i = str.indexOf('#');
/* 169 */     if (i >= 0) str = str.substring(0, i); 
/* 170 */     str = str.trim();
/* 171 */     int j = str.length();
/* 172 */     if (j != 0) {
/* 173 */       if (str.indexOf(' ') >= 0 || str.indexOf('\t') >= 0)
/* 174 */         fail(paramClass, paramURL, paramInt, "Illegal configuration-file syntax"); 
/* 175 */       int k = str.codePointAt(0);
/* 176 */       if (!Character.isJavaIdentifierStart(k))
/* 177 */         fail(paramClass, paramURL, paramInt, "Illegal provider-class name: " + str);  int m;
/* 178 */       for (m = Character.charCount(k); m < j; m += Character.charCount(k)) {
/* 179 */         k = str.codePointAt(m);
/* 180 */         if (!Character.isJavaIdentifierPart(k) && k != 46)
/* 181 */           fail(paramClass, paramURL, paramInt, "Illegal provider-class name: " + str); 
/*     */       } 
/* 183 */       if (!paramSet.contains(str)) {
/* 184 */         paramList.add(str);
/* 185 */         paramSet.add(str);
/*     */       } 
/*     */     } 
/* 188 */     return paramInt + 1;
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
/*     */   private static Iterator<String> parse(Class<?> paramClass, URL paramURL, Set<String> paramSet) throws ServiceConfigurationError {
/* 217 */     InputStream inputStream = null;
/* 218 */     BufferedReader bufferedReader = null;
/* 219 */     ArrayList<String> arrayList = new ArrayList();
/*     */     try {
/* 221 */       inputStream = paramURL.openStream();
/* 222 */       bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
/* 223 */       int i = 1;
/* 224 */       while ((i = parseLine(paramClass, paramURL, bufferedReader, i, arrayList, paramSet)) >= 0);
/* 225 */     } catch (IOException iOException) {
/* 226 */       fail(paramClass, ": " + iOException);
/*     */     } finally {
/*     */       try {
/* 229 */         if (bufferedReader != null) bufferedReader.close(); 
/* 230 */         if (inputStream != null) inputStream.close(); 
/* 231 */       } catch (IOException iOException) {
/* 232 */         fail(paramClass, ": " + iOException);
/*     */       } 
/*     */     } 
/* 235 */     return arrayList.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LazyIterator<S>
/*     */     implements Iterator<S>
/*     */   {
/*     */     Class<S> service;
/*     */     
/*     */     ClassLoader loader;
/*     */     
/* 246 */     Enumeration<URL> configs = null;
/* 247 */     Iterator<String> pending = null;
/* 248 */     Set<String> returned = new TreeSet<>();
/* 249 */     String nextName = null;
/*     */     
/*     */     private LazyIterator(Class<S> param1Class, ClassLoader param1ClassLoader) {
/* 252 */       this.service = param1Class;
/* 253 */       this.loader = param1ClassLoader;
/*     */     }
/*     */     
/*     */     public boolean hasNext() throws ServiceConfigurationError {
/* 257 */       if (this.nextName != null) {
/* 258 */         return true;
/*     */       }
/* 260 */       if (this.configs == null) {
/*     */         try {
/* 262 */           String str = "META-INF/services/" + this.service.getName();
/* 263 */           if (this.loader == null)
/* 264 */           { this.configs = ClassLoader.getSystemResources(str); }
/*     */           else
/* 266 */           { this.configs = this.loader.getResources(str); } 
/* 267 */         } catch (IOException iOException) {
/* 268 */           Service.fail(this.service, ": " + iOException);
/*     */         } 
/*     */       }
/* 271 */       while (this.pending == null || !this.pending.hasNext()) {
/* 272 */         if (!this.configs.hasMoreElements()) {
/* 273 */           return false;
/*     */         }
/* 275 */         this.pending = Service.parse(this.service, this.configs.nextElement(), this.returned);
/*     */       } 
/* 277 */       this.nextName = this.pending.next();
/* 278 */       return true;
/*     */     }
/*     */     
/*     */     public S next() throws ServiceConfigurationError {
/* 282 */       if (!hasNext()) {
/* 283 */         throw new NoSuchElementException();
/*     */       }
/* 285 */       String str = this.nextName;
/* 286 */       this.nextName = null;
/* 287 */       Class<?> clazz = null;
/*     */       try {
/* 289 */         clazz = Class.forName(str, false, this.loader);
/* 290 */       } catch (ClassNotFoundException classNotFoundException) {
/* 291 */         Service.fail(this.service, "Provider " + str + " not found");
/*     */       } 
/*     */       
/* 294 */       if (!this.service.isAssignableFrom(clazz)) {
/* 295 */         Service.fail(this.service, "Provider " + str + " not a subtype");
/*     */       }
/*     */       
/*     */       try {
/* 299 */         return this.service.cast(clazz.newInstance());
/* 300 */       } catch (Throwable throwable) {
/* 301 */         Service.fail(this.service, "Provider " + str + " could not be instantiated", throwable);
/*     */ 
/*     */ 
/*     */         
/* 305 */         return null;
/*     */       } 
/*     */     }
/*     */     public void remove() {
/* 309 */       throw new UnsupportedOperationException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <S> Iterator<S> providers(Class<S> paramClass, ClassLoader paramClassLoader) throws ServiceConfigurationError {
/* 356 */     return new LazyIterator<>(paramClass, paramClassLoader);
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
/*     */   public static <S> Iterator<S> providers(Class<S> paramClass) throws ServiceConfigurationError {
/* 388 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 389 */     return providers(paramClass, classLoader);
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
/*     */   public static <S> Iterator<S> installedProviders(Class<S> paramClass) throws ServiceConfigurationError {
/* 425 */     ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();
/* 426 */     ClassLoader classLoader2 = null;
/* 427 */     while (classLoader1 != null) {
/* 428 */       classLoader2 = classLoader1;
/* 429 */       classLoader1 = classLoader1.getParent();
/*     */     } 
/* 431 */     return providers(paramClass, classLoader2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/Service.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */