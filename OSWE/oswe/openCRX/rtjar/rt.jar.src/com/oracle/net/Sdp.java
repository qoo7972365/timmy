/*     */ package com.oracle.net;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketImpl;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.net.sdp.SdpSupport;
/*     */ import sun.nio.ch.Secrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Sdp
/*     */ {
/*     */   private static final Constructor<ServerSocket> serverSocketCtor;
/*     */   private static final Constructor<SocketImpl> socketImplCtor;
/*     */   
/*     */   static {
/*     */     try {
/*  60 */       serverSocketCtor = ServerSocket.class.getDeclaredConstructor(new Class[] { SocketImpl.class });
/*  61 */       setAccessible(serverSocketCtor);
/*  62 */     } catch (NoSuchMethodException noSuchMethodException) {
/*  63 */       throw new AssertionError(noSuchMethodException);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  73 */       Class<?> clazz = Class.forName("java.net.SdpSocketImpl", true, null);
/*  74 */       socketImplCtor = (Constructor)clazz.getDeclaredConstructor(new Class[0]);
/*  75 */       setAccessible(socketImplCtor);
/*  76 */     } catch (ClassNotFoundException classNotFoundException) {
/*  77 */       throw new AssertionError(classNotFoundException);
/*  78 */     } catch (NoSuchMethodException noSuchMethodException) {
/*  79 */       throw new AssertionError(noSuchMethodException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setAccessible(final AccessibleObject o) {
/*  84 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/*  86 */             o.setAccessible(true);
/*  87 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SdpSocket
/*     */     extends Socket
/*     */   {
/*     */     SdpSocket(SocketImpl param1SocketImpl) throws SocketException {
/*  97 */       super(param1SocketImpl);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SocketImpl createSocketImpl() {
/*     */     try {
/* 106 */       return socketImplCtor.newInstance(new Object[0]);
/* 107 */     } catch (InstantiationException instantiationException) {
/* 108 */       throw new AssertionError(instantiationException);
/* 109 */     } catch (IllegalAccessException illegalAccessException) {
/* 110 */       throw new AssertionError(illegalAccessException);
/* 111 */     } catch (InvocationTargetException invocationTargetException) {
/* 112 */       throw new AssertionError(invocationTargetException);
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
/*     */   public static Socket openSocket() throws IOException {
/* 128 */     SocketImpl socketImpl = createSocketImpl();
/* 129 */     return new SdpSocket(socketImpl);
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
/*     */   public static ServerSocket openServerSocket() throws IOException {
/* 145 */     SocketImpl socketImpl = createSocketImpl();
/*     */     try {
/* 147 */       return serverSocketCtor.newInstance(new Object[] { socketImpl });
/* 148 */     } catch (IllegalAccessException illegalAccessException) {
/* 149 */       throw new AssertionError(illegalAccessException);
/* 150 */     } catch (InstantiationException instantiationException) {
/* 151 */       throw new AssertionError(instantiationException);
/* 152 */     } catch (InvocationTargetException invocationTargetException) {
/* 153 */       Throwable throwable = invocationTargetException.getCause();
/* 154 */       if (throwable instanceof IOException)
/* 155 */         throw (IOException)throwable; 
/* 156 */       if (throwable instanceof RuntimeException)
/* 157 */         throw (RuntimeException)throwable; 
/* 158 */       throw new RuntimeException(invocationTargetException);
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
/*     */   public static SocketChannel openSocketChannel() throws IOException {
/* 177 */     FileDescriptor fileDescriptor = SdpSupport.createSocket();
/* 178 */     return Secrets.newSocketChannel(fileDescriptor);
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
/*     */   public static ServerSocketChannel openServerSocketChannel() throws IOException {
/* 198 */     FileDescriptor fileDescriptor = SdpSupport.createSocket();
/* 199 */     return Secrets.newServerSocketChannel(fileDescriptor);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/net/Sdp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */