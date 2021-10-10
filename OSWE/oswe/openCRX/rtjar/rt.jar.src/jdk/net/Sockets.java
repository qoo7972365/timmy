/*     */ package jdk.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketOption;
/*     */ import java.net.StandardSocketOptions;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import jdk.Exported;
/*     */ import sun.net.ExtendedOptionsImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported
/*     */ public class Sockets
/*     */ {
/*  62 */   private static final HashMap<Class<?>, Set<SocketOption<?>>> options = new HashMap<>();
/*     */   
/*     */   static {
/*  65 */     initOptionSets();
/*  66 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  69 */             Sockets.initMethods();
/*  70 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static Method siSetOption;
/*     */   private static Method siGetOption;
/*     */   private static Method dsiSetOption;
/*     */   private static Method dsiGetOption;
/*     */   
/*     */   private static void initMethods() {
/*     */     try {
/*  83 */       Class<?> clazz = Class.forName("java.net.SocketSecrets");
/*     */       
/*  85 */       siSetOption = clazz.getDeclaredMethod("setOption", new Class[] { Object.class, SocketOption.class, Object.class });
/*     */ 
/*     */ 
/*     */       
/*  89 */       siSetOption.setAccessible(true);
/*     */       
/*  91 */       siGetOption = clazz.getDeclaredMethod("getOption", new Class[] { Object.class, SocketOption.class });
/*     */ 
/*     */       
/*  94 */       siGetOption.setAccessible(true);
/*     */       
/*  96 */       dsiSetOption = clazz.getDeclaredMethod("setOption", new Class[] { DatagramSocket.class, SocketOption.class, Object.class });
/*     */ 
/*     */ 
/*     */       
/* 100 */       dsiSetOption.setAccessible(true);
/*     */       
/* 102 */       dsiGetOption = clazz.getDeclaredMethod("getOption", new Class[] { DatagramSocket.class, SocketOption.class });
/*     */ 
/*     */       
/* 105 */       dsiGetOption.setAccessible(true);
/* 106 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 107 */       throw new InternalError(reflectiveOperationException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> void invokeSet(Method paramMethod, Object paramObject, SocketOption<T> paramSocketOption, T paramT) throws IOException {
/*     */     try {
/* 116 */       paramMethod.invoke(null, new Object[] { paramObject, paramSocketOption, paramT });
/* 117 */     } catch (Exception exception) {
/* 118 */       if (exception instanceof InvocationTargetException) {
/* 119 */         Throwable throwable = ((InvocationTargetException)exception).getTargetException();
/* 120 */         if (throwable instanceof IOException)
/* 121 */           throw (IOException)throwable; 
/* 122 */         if (throwable instanceof RuntimeException) {
/* 123 */           throw (RuntimeException)throwable;
/*     */         }
/*     */       } 
/* 126 */       throw new RuntimeException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> T invokeGet(Method paramMethod, Object paramObject, SocketOption<T> paramSocketOption) throws IOException {
/*     */     try {
/* 134 */       return (T)paramMethod.invoke(null, new Object[] { paramObject, paramSocketOption });
/* 135 */     } catch (Exception exception) {
/* 136 */       if (exception instanceof InvocationTargetException) {
/* 137 */         Throwable throwable = ((InvocationTargetException)exception).getTargetException();
/* 138 */         if (throwable instanceof IOException)
/* 139 */           throw (IOException)throwable; 
/* 140 */         if (throwable instanceof RuntimeException) {
/* 141 */           throw (RuntimeException)throwable;
/*     */         }
/*     */       } 
/* 144 */       throw new RuntimeException(exception);
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
/*     */   public static <T> void setOption(Socket paramSocket, SocketOption<T> paramSocketOption, T paramT) throws IOException {
/* 175 */     if (!isSupported(Socket.class, paramSocketOption)) {
/* 176 */       throw new UnsupportedOperationException(paramSocketOption.name());
/*     */     }
/* 178 */     invokeSet(siSetOption, paramSocket, paramSocketOption, paramT);
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
/*     */   public static <T> T getOption(Socket paramSocket, SocketOption<T> paramSocketOption) throws IOException {
/* 203 */     if (!isSupported(Socket.class, paramSocketOption)) {
/* 204 */       throw new UnsupportedOperationException(paramSocketOption.name());
/*     */     }
/* 206 */     return invokeGet(siGetOption, paramSocket, paramSocketOption);
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
/*     */   public static <T> void setOption(ServerSocket paramServerSocket, SocketOption<T> paramSocketOption, T paramT) throws IOException {
/* 233 */     if (!isSupported(ServerSocket.class, paramSocketOption)) {
/* 234 */       throw new UnsupportedOperationException(paramSocketOption.name());
/*     */     }
/* 236 */     invokeSet(siSetOption, paramServerSocket, paramSocketOption, paramT);
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
/*     */   public static <T> T getOption(ServerSocket paramServerSocket, SocketOption<T> paramSocketOption) throws IOException {
/* 261 */     if (!isSupported(ServerSocket.class, paramSocketOption)) {
/* 262 */       throw new UnsupportedOperationException(paramSocketOption.name());
/*     */     }
/* 264 */     return invokeGet(siGetOption, paramServerSocket, paramSocketOption);
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
/*     */   public static <T> void setOption(DatagramSocket paramDatagramSocket, SocketOption<T> paramSocketOption, T paramT) throws IOException {
/* 292 */     if (!isSupported(paramDatagramSocket.getClass(), paramSocketOption)) {
/* 293 */       throw new UnsupportedOperationException(paramSocketOption.name());
/*     */     }
/* 295 */     invokeSet(dsiSetOption, paramDatagramSocket, paramSocketOption, paramT);
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
/*     */   public static <T> T getOption(DatagramSocket paramDatagramSocket, SocketOption<T> paramSocketOption) throws IOException {
/* 321 */     if (!isSupported(paramDatagramSocket.getClass(), paramSocketOption)) {
/* 322 */       throw new UnsupportedOperationException(paramSocketOption.name());
/*     */     }
/* 324 */     return invokeGet(dsiGetOption, paramDatagramSocket, paramSocketOption);
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
/*     */   public static Set<SocketOption<?>> supportedOptions(Class<?> paramClass) {
/* 338 */     Set<SocketOption<?>> set = options.get(paramClass);
/* 339 */     if (set == null) {
/* 340 */       throw new IllegalArgumentException("unknown socket type");
/*     */     }
/* 342 */     return set;
/*     */   }
/*     */   
/*     */   private static boolean isSupported(Class<?> paramClass, SocketOption<?> paramSocketOption) {
/* 346 */     Set<SocketOption<?>> set = supportedOptions(paramClass);
/* 347 */     return set.contains(paramSocketOption);
/*     */   }
/*     */   
/*     */   private static void initOptionSets() {
/* 351 */     boolean bool = ExtendedOptionsImpl.flowSupported();
/*     */ 
/*     */ 
/*     */     
/* 355 */     HashSet<SocketOption<Boolean>> hashSet = new HashSet();
/* 356 */     hashSet.add(StandardSocketOptions.SO_KEEPALIVE);
/* 357 */     hashSet.add(StandardSocketOptions.SO_SNDBUF);
/* 358 */     hashSet.add(StandardSocketOptions.SO_RCVBUF);
/* 359 */     hashSet.add(StandardSocketOptions.SO_REUSEADDR);
/* 360 */     hashSet.add(StandardSocketOptions.SO_LINGER);
/* 361 */     hashSet.add(StandardSocketOptions.IP_TOS);
/* 362 */     hashSet.add(StandardSocketOptions.TCP_NODELAY);
/* 363 */     if (bool) {
/* 364 */       hashSet.add(ExtendedSocketOptions.SO_FLOW_SLA);
/*     */     }
/* 366 */     Set<SocketOption<Boolean>> set = Collections.unmodifiableSet(hashSet);
/* 367 */     options.put(Socket.class, set);
/*     */ 
/*     */ 
/*     */     
/* 371 */     set = new HashSet<>();
/* 372 */     set.add(StandardSocketOptions.SO_RCVBUF);
/* 373 */     set.add(StandardSocketOptions.SO_REUSEADDR);
/* 374 */     set.add(StandardSocketOptions.IP_TOS);
/* 375 */     set = Collections.unmodifiableSet(set);
/* 376 */     options.put(ServerSocket.class, set);
/*     */ 
/*     */ 
/*     */     
/* 380 */     set = new HashSet<>();
/* 381 */     set.add(StandardSocketOptions.SO_SNDBUF);
/* 382 */     set.add(StandardSocketOptions.SO_RCVBUF);
/* 383 */     set.add(StandardSocketOptions.SO_REUSEADDR);
/* 384 */     set.add(StandardSocketOptions.IP_TOS);
/* 385 */     if (bool) {
/* 386 */       set.add(ExtendedSocketOptions.SO_FLOW_SLA);
/*     */     }
/* 388 */     set = Collections.unmodifiableSet(set);
/* 389 */     options.put(DatagramSocket.class, set);
/*     */ 
/*     */ 
/*     */     
/* 393 */     set = new HashSet<>();
/* 394 */     set.add(StandardSocketOptions.SO_SNDBUF);
/* 395 */     set.add(StandardSocketOptions.SO_RCVBUF);
/* 396 */     set.add(StandardSocketOptions.SO_REUSEADDR);
/* 397 */     set.add(StandardSocketOptions.IP_TOS);
/* 398 */     set.add(StandardSocketOptions.IP_MULTICAST_IF);
/* 399 */     set.add(StandardSocketOptions.IP_MULTICAST_TTL);
/* 400 */     set.add(StandardSocketOptions.IP_MULTICAST_LOOP);
/* 401 */     if (bool) {
/* 402 */       set.add(ExtendedSocketOptions.SO_FLOW_SLA);
/*     */     }
/* 404 */     set = Collections.unmodifiableSet(set);
/* 405 */     options.put(MulticastSocket.class, set);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/net/Sockets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */