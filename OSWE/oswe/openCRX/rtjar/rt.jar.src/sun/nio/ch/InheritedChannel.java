/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketOption;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.Channel;
/*     */ import java.nio.channels.DatagramChannel;
/*     */ import java.nio.channels.MembershipKey;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class InheritedChannel
/*     */ {
/*     */   private static final int UNKNOWN = -1;
/*     */   private static final int SOCK_STREAM = 1;
/*     */   private static final int SOCK_DGRAM = 2;
/*     */   private static final int O_RDONLY = 0;
/*     */   private static final int O_WRONLY = 1;
/*     */   private static final int O_RDWR = 2;
/*  58 */   private static int devnull = -1;
/*     */   
/*     */   private static void detachIOStreams() {
/*     */     try {
/*  62 */       dup2(devnull, 0);
/*  63 */       dup2(devnull, 1);
/*  64 */       dup2(devnull, 2);
/*  65 */     } catch (IOException iOException) {
/*     */       
/*  67 */       throw new InternalError(iOException);
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
/*     */   public static class InheritedSocketChannelImpl
/*     */     extends SocketChannelImpl
/*     */   {
/*     */     InheritedSocketChannelImpl(SelectorProvider param1SelectorProvider, FileDescriptor param1FileDescriptor, InetSocketAddress param1InetSocketAddress) throws IOException {
/*  83 */       super(param1SelectorProvider, param1FileDescriptor, param1InetSocketAddress);
/*     */     }
/*     */     
/*     */     protected void implCloseSelectableChannel() throws IOException {
/*  87 */       super.implCloseSelectableChannel();
/*  88 */       InheritedChannel.detachIOStreams();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class InheritedServerSocketChannelImpl
/*     */     extends ServerSocketChannelImpl
/*     */   {
/*     */     InheritedServerSocketChannelImpl(SelectorProvider param1SelectorProvider, FileDescriptor param1FileDescriptor) throws IOException {
/*  99 */       super(param1SelectorProvider, param1FileDescriptor, true);
/*     */     }
/*     */     
/*     */     protected void implCloseSelectableChannel() throws IOException {
/* 103 */       super.implCloseSelectableChannel();
/* 104 */       InheritedChannel.detachIOStreams();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class InheritedDatagramChannelImpl
/*     */     extends DatagramChannelImpl
/*     */   {
/*     */     InheritedDatagramChannelImpl(SelectorProvider param1SelectorProvider, FileDescriptor param1FileDescriptor) throws IOException {
/* 116 */       super(param1SelectorProvider, param1FileDescriptor);
/*     */     }
/*     */     
/*     */     protected void implCloseSelectableChannel() throws IOException {
/* 120 */       super.implCloseSelectableChannel();
/* 121 */       InheritedChannel.detachIOStreams();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkAccess(Channel paramChannel) {
/* 130 */     SecurityManager securityManager = System.getSecurityManager();
/* 131 */     if (securityManager != null) {
/* 132 */       securityManager.checkPermission(new RuntimePermission("inheritedChannel"));
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
/*     */   private static Channel createChannel() throws IOException {
/*     */     InheritedDatagramChannelImpl inheritedDatagramChannelImpl;
/* 151 */     int i = dup(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     int j = soType0(i);
/* 158 */     if (j != 1 && j != 2) {
/* 159 */       close0(i);
/* 160 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     Class[] arrayOfClass = { int.class };
/* 169 */     Constructor<?> constructor = Reflect.lookupConstructor("java.io.FileDescriptor", arrayOfClass);
/*     */     
/* 171 */     Object[] arrayOfObject = { new Integer(i) };
/* 172 */     FileDescriptor fileDescriptor = (FileDescriptor)Reflect.invoke(constructor, arrayOfObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     SelectorProvider selectorProvider = SelectorProvider.provider();
/* 181 */     assert selectorProvider instanceof SelectorProviderImpl;
/*     */ 
/*     */     
/* 184 */     if (j == 1) {
/* 185 */       InetAddress inetAddress = peerAddress0(i);
/* 186 */       if (inetAddress == null) {
/* 187 */         InheritedServerSocketChannelImpl inheritedServerSocketChannelImpl = new InheritedServerSocketChannelImpl(selectorProvider, fileDescriptor);
/*     */       } else {
/* 189 */         int k = peerPort0(i);
/* 190 */         assert k > 0;
/* 191 */         InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, k);
/* 192 */         InheritedSocketChannelImpl inheritedSocketChannelImpl = new InheritedSocketChannelImpl(selectorProvider, fileDescriptor, inetSocketAddress);
/*     */       } 
/*     */     } else {
/* 195 */       inheritedDatagramChannelImpl = new InheritedDatagramChannelImpl(selectorProvider, fileDescriptor);
/*     */     } 
/* 197 */     return inheritedDatagramChannelImpl;
/*     */   }
/*     */   
/*     */   private static boolean haveChannel = false;
/* 201 */   private static Channel channel = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized Channel getChannel() throws IOException {
/* 208 */     if (devnull < 0) {
/* 209 */       devnull = open0("/dev/null", 2);
/*     */     }
/*     */ 
/*     */     
/* 213 */     if (!haveChannel) {
/* 214 */       channel = createChannel();
/* 215 */       haveChannel = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 220 */     if (channel != null) {
/* 221 */       checkAccess(channel);
/*     */     }
/* 223 */     return channel;
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
/*     */   static {
/* 238 */     IOUtil.load();
/*     */   }
/*     */   
/*     */   private static native int dup(int paramInt) throws IOException;
/*     */   
/*     */   private static native void dup2(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private static native int open0(String paramString, int paramInt) throws IOException;
/*     */   
/*     */   private static native void close0(int paramInt) throws IOException;
/*     */   
/*     */   private static native int soType0(int paramInt);
/*     */   
/*     */   private static native InetAddress peerAddress0(int paramInt);
/*     */   
/*     */   private static native int peerPort0(int paramInt);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/InheritedChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */