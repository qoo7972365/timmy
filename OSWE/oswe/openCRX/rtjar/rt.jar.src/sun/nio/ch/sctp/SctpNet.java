/*     */ package sun.nio.ch.sctp;
/*     */ 
/*     */ import com.sun.nio.sctp.SctpSocketOption;
/*     */ import com.sun.nio.sctp.SctpStandardSocketOptions;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.AlreadyBoundException;
/*     */ import java.security.AccessController;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import sun.nio.ch.IOUtil;
/*     */ import sun.nio.ch.Net;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SctpNet
/*     */ {
/*  43 */   static final String osName = AccessController.<String>doPrivileged(new GetPropertyAction("os.name"));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean IPv4MappedAddresses() {
/*  49 */     if ("SunOS".equals(osName))
/*     */     {
/*  51 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  55 */     return false;
/*     */   }
/*     */   
/*     */   static boolean throwAlreadyBoundException() throws IOException {
/*  59 */     throw new AlreadyBoundException();
/*     */   }
/*     */   
/*     */   static void listen(int paramInt1, int paramInt2) throws IOException {
/*  63 */     listen0(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   static int connect(int paramInt1, InetAddress paramInetAddress, int paramInt2) throws IOException {
/*  68 */     return connect0(paramInt1, paramInetAddress, paramInt2);
/*     */   }
/*     */   
/*     */   static void close(int paramInt) throws IOException {
/*  72 */     close0(paramInt);
/*     */   }
/*     */   
/*     */   static void preClose(int paramInt) throws IOException {
/*  76 */     preClose0(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static FileDescriptor socket(boolean paramBoolean) throws IOException {
/*  85 */     int i = socket0(paramBoolean);
/*  86 */     return IOUtil.newFD(i);
/*     */   }
/*     */ 
/*     */   
/*     */   static void bindx(int paramInt1, InetAddress[] paramArrayOfInetAddress, int paramInt2, boolean paramBoolean) throws IOException {
/*  91 */     bindx(paramInt1, paramArrayOfInetAddress, paramInt2, paramArrayOfInetAddress.length, paramBoolean, 
/*  92 */         IPv4MappedAddresses());
/*     */   }
/*     */ 
/*     */   
/*     */   static Set<SocketAddress> getLocalAddresses(int paramInt) throws IOException {
/*  97 */     Set<SocketAddress> set = null;
/*  98 */     SocketAddress[] arrayOfSocketAddress = getLocalAddresses0(paramInt);
/*     */     
/* 100 */     if (arrayOfSocketAddress != null) {
/* 101 */       set = getRevealedLocalAddressSet(arrayOfSocketAddress);
/*     */     }
/*     */     
/* 104 */     return set;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Set<SocketAddress> getRevealedLocalAddressSet(SocketAddress[] paramArrayOfSocketAddress) {
/* 110 */     SecurityManager securityManager = System.getSecurityManager();
/* 111 */     HashSet<SocketAddress> hashSet = new HashSet(paramArrayOfSocketAddress.length);
/* 112 */     for (SocketAddress socketAddress : paramArrayOfSocketAddress) {
/* 113 */       hashSet.add(getRevealedLocalAddress(socketAddress, securityManager));
/*     */     }
/* 115 */     return hashSet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static SocketAddress getRevealedLocalAddress(SocketAddress paramSocketAddress, SecurityManager paramSecurityManager) {
/* 121 */     if (paramSecurityManager == null || paramSocketAddress == null)
/* 122 */       return paramSocketAddress; 
/* 123 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*     */     try {
/* 125 */       paramSecurityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), -1);
/*     */     }
/* 127 */     catch (SecurityException securityException) {
/*     */       
/* 129 */       return new InetSocketAddress(InetAddress.getLoopbackAddress(), inetSocketAddress
/* 130 */           .getPort());
/*     */     } 
/* 132 */     return paramSocketAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   static Set<SocketAddress> getRemoteAddresses(int paramInt1, int paramInt2) throws IOException {
/* 137 */     HashSet<SocketAddress> hashSet = null;
/* 138 */     SocketAddress[] arrayOfSocketAddress = getRemoteAddresses0(paramInt1, paramInt2);
/*     */     
/* 140 */     if (arrayOfSocketAddress != null) {
/* 141 */       hashSet = new HashSet(arrayOfSocketAddress.length);
/* 142 */       for (SocketAddress socketAddress : arrayOfSocketAddress) {
/* 143 */         hashSet.add(socketAddress);
/*     */       }
/*     */     } 
/* 146 */     return hashSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> void setSocketOption(int paramInt1, SctpSocketOption<T> paramSctpSocketOption, T paramT, int paramInt2) throws IOException {
/* 154 */     if (paramT == null) {
/* 155 */       throw new IllegalArgumentException("Invalid option value");
/*     */     }
/* 157 */     if (paramSctpSocketOption.equals(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS)) {
/* 158 */       SctpStandardSocketOptions.InitMaxStreams initMaxStreams = (SctpStandardSocketOptions.InitMaxStreams)paramT;
/* 159 */       setInitMsgOption0(paramInt1, initMaxStreams
/* 160 */           .maxInStreams(), initMaxStreams.maxOutStreams());
/* 161 */     } else if (paramSctpSocketOption.equals(SctpStandardSocketOptions.SCTP_PRIMARY_ADDR) || paramSctpSocketOption
/* 162 */       .equals(SctpStandardSocketOptions.SCTP_SET_PEER_PRIMARY_ADDR)) {
/*     */       
/* 164 */       SocketAddress socketAddress = (SocketAddress)paramT;
/* 165 */       if (socketAddress == null) {
/* 166 */         throw new IllegalArgumentException("Invalid option value");
/*     */       }
/* 168 */       Net.checkAddress(socketAddress);
/* 169 */       InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
/*     */       
/* 171 */       if (paramSctpSocketOption.equals(SctpStandardSocketOptions.SCTP_PRIMARY_ADDR)) {
/* 172 */         setPrimAddrOption0(paramInt1, paramInt2, inetSocketAddress
/*     */             
/* 174 */             .getAddress(), inetSocketAddress
/* 175 */             .getPort());
/*     */       } else {
/* 177 */         setPeerPrimAddrOption0(paramInt1, paramInt2, inetSocketAddress
/*     */             
/* 179 */             .getAddress(), inetSocketAddress
/* 180 */             .getPort(), 
/* 181 */             IPv4MappedAddresses());
/*     */       } 
/* 183 */     } else if (paramSctpSocketOption.equals(SctpStandardSocketOptions.SCTP_DISABLE_FRAGMENTS) || paramSctpSocketOption
/* 184 */       .equals(SctpStandardSocketOptions.SCTP_EXPLICIT_COMPLETE) || paramSctpSocketOption
/* 185 */       .equals(SctpStandardSocketOptions.SCTP_FRAGMENT_INTERLEAVE) || paramSctpSocketOption
/* 186 */       .equals(SctpStandardSocketOptions.SCTP_NODELAY) || paramSctpSocketOption
/* 187 */       .equals(SctpStandardSocketOptions.SO_SNDBUF) || paramSctpSocketOption
/* 188 */       .equals(SctpStandardSocketOptions.SO_RCVBUF) || paramSctpSocketOption
/* 189 */       .equals(SctpStandardSocketOptions.SO_LINGER)) {
/* 190 */       setIntOption(paramInt1, paramSctpSocketOption, paramT);
/*     */     } else {
/* 192 */       throw new AssertionError("Unknown socket option");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static Object getSocketOption(int paramInt1, SctpSocketOption<?> paramSctpSocketOption, int paramInt2) throws IOException {
/* 198 */     if (paramSctpSocketOption.equals(SctpStandardSocketOptions.SCTP_SET_PEER_PRIMARY_ADDR)) {
/* 199 */       throw new IllegalArgumentException("SCTP_SET_PEER_PRIMARY_ADDR cannot be retrieved");
/*     */     }
/* 201 */     if (paramSctpSocketOption.equals(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS)) {
/*     */       
/* 203 */       int[] arrayOfInt = new int[2];
/* 204 */       getInitMsgOption0(paramInt1, arrayOfInt);
/* 205 */       return SctpStandardSocketOptions.InitMaxStreams.create(arrayOfInt[0], arrayOfInt[1]);
/* 206 */     }  if (paramSctpSocketOption.equals(SctpStandardSocketOptions.SCTP_PRIMARY_ADDR))
/* 207 */       return getPrimAddrOption0(paramInt1, paramInt2); 
/* 208 */     if (paramSctpSocketOption.equals(SctpStandardSocketOptions.SCTP_DISABLE_FRAGMENTS) || paramSctpSocketOption
/* 209 */       .equals(SctpStandardSocketOptions.SCTP_EXPLICIT_COMPLETE) || paramSctpSocketOption
/* 210 */       .equals(SctpStandardSocketOptions.SCTP_FRAGMENT_INTERLEAVE) || paramSctpSocketOption
/* 211 */       .equals(SctpStandardSocketOptions.SCTP_NODELAY) || paramSctpSocketOption
/* 212 */       .equals(SctpStandardSocketOptions.SO_SNDBUF) || paramSctpSocketOption
/* 213 */       .equals(SctpStandardSocketOptions.SO_RCVBUF) || paramSctpSocketOption
/* 214 */       .equals(SctpStandardSocketOptions.SO_LINGER)) {
/* 215 */       return getIntOption(paramInt1, paramSctpSocketOption);
/*     */     }
/* 217 */     throw new AssertionError("Unknown socket option");
/*     */   }
/*     */ 
/*     */   
/*     */   static void setIntOption(int paramInt, SctpSocketOption<?> paramSctpSocketOption, Object paramObject) throws IOException {
/*     */     boolean bool;
/* 223 */     if (paramObject == null) {
/* 224 */       throw new IllegalArgumentException("Invalid option value");
/*     */     }
/* 226 */     Class<?> clazz = paramSctpSocketOption.type();
/* 227 */     if (clazz != Integer.class && clazz != Boolean.class) {
/* 228 */       throw new AssertionError("Should not reach here");
/*     */     }
/* 230 */     if (paramSctpSocketOption == SctpStandardSocketOptions.SO_RCVBUF || paramSctpSocketOption == SctpStandardSocketOptions.SO_SNDBUF) {
/*     */ 
/*     */       
/* 233 */       bool = ((Integer)paramObject).intValue();
/* 234 */       if (bool < 0) {
/* 235 */         throw new IllegalArgumentException("Invalid send/receive buffer size");
/*     */       }
/* 237 */     } else if (paramSctpSocketOption == SctpStandardSocketOptions.SO_LINGER) {
/* 238 */       bool = ((Integer)paramObject).intValue();
/* 239 */       if (bool < 0)
/* 240 */         paramObject = Integer.valueOf(-1); 
/* 241 */       if (bool > '￿')
/* 242 */         paramObject = Integer.valueOf(65535); 
/* 243 */     } else if (paramSctpSocketOption.equals(SctpStandardSocketOptions.SCTP_FRAGMENT_INTERLEAVE)) {
/* 244 */       bool = ((Integer)paramObject).intValue();
/* 245 */       if (bool < 0 || bool > 2) {
/* 246 */         throw new IllegalArgumentException("Invalid value for SCTP_FRAGMENT_INTERLEAVE");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 251 */     if (clazz == Integer.class) {
/* 252 */       bool = ((Integer)paramObject).intValue();
/*     */     } else {
/* 254 */       boolean bool1 = ((Boolean)paramObject).booleanValue();
/* 255 */       bool = bool1 ? true : false;
/*     */     } 
/*     */     
/* 258 */     setIntOption0(paramInt, ((SctpStdSocketOption)paramSctpSocketOption).constValue(), bool);
/*     */   }
/*     */ 
/*     */   
/*     */   static Object getIntOption(int paramInt, SctpSocketOption<?> paramSctpSocketOption) throws IOException {
/* 263 */     Class<?> clazz = paramSctpSocketOption.type();
/*     */     
/* 265 */     if (clazz != Integer.class && clazz != Boolean.class) {
/* 266 */       throw new AssertionError("Should not reach here");
/*     */     }
/* 268 */     if (!(paramSctpSocketOption instanceof SctpStdSocketOption)) {
/* 269 */       throw new AssertionError("Should not reach here");
/*     */     }
/* 271 */     int i = getIntOption0(paramInt, ((SctpStdSocketOption)paramSctpSocketOption)
/* 272 */         .constValue());
/*     */     
/* 274 */     if (clazz == Integer.class) {
/* 275 */       return Integer.valueOf(i);
/*     */     }
/* 277 */     return (i == 0) ? Boolean.FALSE : Boolean.TRUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void shutdown(int paramInt1, int paramInt2) throws IOException {
/* 283 */     shutdown0(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   static FileDescriptor branch(int paramInt1, int paramInt2) throws IOException {
/* 287 */     int i = branch0(paramInt1, paramInt2);
/* 288 */     return IOUtil.newFD(i);
/*     */   }
/*     */ 
/*     */   
/*     */   static native int socket0(boolean paramBoolean) throws IOException;
/*     */ 
/*     */   
/*     */   static native void listen0(int paramInt1, int paramInt2) throws IOException;
/*     */ 
/*     */   
/*     */   static native int connect0(int paramInt1, InetAddress paramInetAddress, int paramInt2) throws IOException;
/*     */ 
/*     */   
/*     */   static native void close0(int paramInt) throws IOException;
/*     */ 
/*     */   
/*     */   static native void preClose0(int paramInt) throws IOException;
/*     */ 
/*     */   
/*     */   static native void bindx(int paramInt1, InetAddress[] paramArrayOfInetAddress, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2) throws IOException;
/*     */ 
/*     */   
/*     */   static native int getIntOption0(int paramInt1, int paramInt2) throws IOException;
/*     */ 
/*     */   
/*     */   static native void setIntOption0(int paramInt1, int paramInt2, int paramInt3) throws IOException;
/*     */ 
/*     */   
/*     */   static native SocketAddress[] getLocalAddresses0(int paramInt) throws IOException;
/*     */ 
/*     */   
/*     */   static native SocketAddress[] getRemoteAddresses0(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   static native int branch0(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   static native void setPrimAddrOption0(int paramInt1, int paramInt2, InetAddress paramInetAddress, int paramInt3) throws IOException;
/*     */   
/*     */   static native void setPeerPrimAddrOption0(int paramInt1, int paramInt2, InetAddress paramInetAddress, int paramInt3, boolean paramBoolean) throws IOException;
/*     */   
/*     */   static native SocketAddress getPrimAddrOption0(int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   static native void getInitMsgOption0(int paramInt, int[] paramArrayOfint) throws IOException;
/*     */   
/*     */   static native void setInitMsgOption0(int paramInt1, int paramInt2, int paramInt3) throws IOException;
/*     */   
/*     */   static native void shutdown0(int paramInt1, int paramInt2);
/*     */   
/*     */   static native void init();
/*     */   
/*     */   static {
/* 338 */     init();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/sctp/SctpNet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */