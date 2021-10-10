/*      */ package sun.nio.ch.sctp;
/*      */ 
/*      */ import com.sun.nio.sctp.AbstractNotificationHandler;
/*      */ import com.sun.nio.sctp.Association;
/*      */ import com.sun.nio.sctp.AssociationChangeNotification;
/*      */ import com.sun.nio.sctp.HandlerResult;
/*      */ import com.sun.nio.sctp.IllegalReceiveException;
/*      */ import com.sun.nio.sctp.IllegalUnbindException;
/*      */ import com.sun.nio.sctp.InvalidStreamException;
/*      */ import com.sun.nio.sctp.MessageInfo;
/*      */ import com.sun.nio.sctp.NotificationHandler;
/*      */ import com.sun.nio.sctp.SctpChannel;
/*      */ import com.sun.nio.sctp.SctpSocketOption;
/*      */ import com.sun.nio.sctp.SctpStandardSocketOptions;
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.SocketAddress;
/*      */ import java.net.SocketException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.channels.AlreadyConnectedException;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.ConnectionPendingException;
/*      */ import java.nio.channels.NoConnectionPendingException;
/*      */ import java.nio.channels.NotYetBoundException;
/*      */ import java.nio.channels.NotYetConnectedException;
/*      */ import java.nio.channels.spi.SelectorProvider;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Set;
/*      */ import sun.nio.ch.DirectBuffer;
/*      */ import sun.nio.ch.IOStatus;
/*      */ import sun.nio.ch.IOUtil;
/*      */ import sun.nio.ch.NativeThread;
/*      */ import sun.nio.ch.Net;
/*      */ import sun.nio.ch.SelChImpl;
/*      */ import sun.nio.ch.SelectionKeyImpl;
/*      */ import sun.nio.ch.Util;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SctpChannelImpl
/*      */   extends SctpChannel
/*      */   implements SelChImpl
/*      */ {
/*      */   private final FileDescriptor fd;
/*      */   private final int fdVal;
/*   82 */   private volatile long receiverThread = 0L;
/*   83 */   private volatile long senderThread = 0L;
/*      */ 
/*      */   
/*   86 */   private final Object receiveLock = new Object();
/*      */ 
/*      */   
/*   89 */   private final Object sendLock = new Object();
/*      */   
/*   91 */   private final ThreadLocal<Boolean> receiveInvoked = new ThreadLocal<Boolean>()
/*      */     {
/*      */       protected Boolean initialValue() {
/*   94 */         return Boolean.FALSE;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*  100 */   private final Object stateLock = new Object();
/*      */   
/*      */   private enum ChannelState {
/*  103 */     UNINITIALIZED,
/*  104 */     UNCONNECTED,
/*  105 */     PENDING,
/*  106 */     CONNECTED,
/*  107 */     KILLPENDING,
/*  108 */     KILLED;
/*      */   }
/*      */   
/*  111 */   private ChannelState state = ChannelState.UNINITIALIZED;
/*      */ 
/*      */   
/*  114 */   int port = -1;
/*  115 */   private HashSet<InetSocketAddress> localAddresses = new HashSet<>();
/*      */ 
/*      */   
/*      */   private boolean wildcard;
/*      */ 
/*      */   
/*      */   private boolean readyToConnect;
/*      */ 
/*      */   
/*      */   private boolean isShutdown;
/*      */   
/*      */   private Association association;
/*      */   
/*  128 */   private Set<SocketAddress> remoteAddresses = Collections.emptySet();
/*      */ 
/*      */   
/*      */   private InternalNotificationHandler internalNotificationHandler;
/*      */ 
/*      */ 
/*      */   
/*      */   public SctpChannelImpl(SelectorProvider paramSelectorProvider) throws IOException
/*      */   {
/*  137 */     super(paramSelectorProvider);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  866 */     this.internalNotificationHandler = new InternalNotificationHandler(); this.fd = SctpNet.socket(true); this.fdVal = IOUtil.fdVal(this.fd); this.state = ChannelState.UNCONNECTED; } public SctpChannelImpl(SelectorProvider paramSelectorProvider, FileDescriptor paramFileDescriptor) throws IOException { this(paramSelectorProvider, paramFileDescriptor, (Association)null); } public SctpChannel bind(SocketAddress paramSocketAddress) throws IOException { synchronized (this.receiveLock) { synchronized (this.sendLock) { synchronized (this.stateLock) { ensureOpenAndUnconnected(); if (isBound()) SctpNet.throwAlreadyBoundException();  InetSocketAddress inetSocketAddress1 = (paramSocketAddress == null) ? new InetSocketAddress(0) : Net.checkAddress(paramSocketAddress); SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkListen(inetSocketAddress1.getPort());  Net.bind(this.fd, inetSocketAddress1.getAddress(), inetSocketAddress1.getPort()); InetSocketAddress inetSocketAddress2 = Net.localAddress(this.fd); this.port = inetSocketAddress2.getPort(); this.localAddresses.add(inetSocketAddress1); if (inetSocketAddress1.getAddress().isAnyLocalAddress()) this.wildcard = true;  }  }  }  return this; } public SctpChannel bindAddress(InetAddress paramInetAddress) throws IOException { bindUnbindAddress(paramInetAddress, true); this.localAddresses.add(new InetSocketAddress(paramInetAddress, this.port)); return this; } public SctpChannel unbindAddress(InetAddress paramInetAddress) throws IOException { bindUnbindAddress(paramInetAddress, false); this.localAddresses.remove(new InetSocketAddress(paramInetAddress, this.port)); return this; } private SctpChannel bindUnbindAddress(InetAddress paramInetAddress, boolean paramBoolean) throws IOException { if (paramInetAddress == null) throw new IllegalArgumentException();  synchronized (this.receiveLock) { synchronized (this.sendLock) { synchronized (this.stateLock) { if (!isOpen()) throw new ClosedChannelException();  if (!isBound()) throw new NotYetBoundException();  if (this.wildcard) throw new IllegalStateException("Cannot add or remove addresses from a channel that is bound to the wildcard address");  if (paramInetAddress.isAnyLocalAddress()) throw new IllegalArgumentException("Cannot add or remove the wildcard address");  if (paramBoolean) { for (InetSocketAddress inetSocketAddress : this.localAddresses) { if (inetSocketAddress.getAddress().equals(paramInetAddress)) SctpNet.throwAlreadyBoundException();  }  } else { if (this.localAddresses.size() <= 1) throw new IllegalUnbindException("Cannot remove address from a channel with only one address bound");  boolean bool = false; for (InetSocketAddress inetSocketAddress : this.localAddresses) { if (inetSocketAddress.getAddress().equals(paramInetAddress)) { bool = true; break; }  }  if (!bool) throw new IllegalUnbindException("Cannot remove address from a channel that is not bound to that address");  }  SctpNet.bindx(this.fdVal, new InetAddress[] { paramInetAddress }, this.port, paramBoolean); if (paramBoolean) { this.localAddresses.add(new InetSocketAddress(paramInetAddress, this.port)); } else { for (InetSocketAddress inetSocketAddress : this.localAddresses) { if (inetSocketAddress.getAddress().equals(paramInetAddress)) { this.localAddresses.remove(inetSocketAddress); break; }  }  }  }  }  }  return this; } private boolean isBound() { synchronized (this.stateLock) { return !(this.port == -1); }  } private boolean isConnected() { synchronized (this.stateLock) { return (this.state == ChannelState.CONNECTED); }  } private void ensureOpenAndUnconnected() throws IOException { synchronized (this.stateLock) { if (!isOpen()) throw new ClosedChannelException();  if (isConnected()) throw new AlreadyConnectedException();  if (this.state == ChannelState.PENDING) throw new ConnectionPendingException();  }  } private boolean ensureReceiveOpen() throws ClosedChannelException { synchronized (this.stateLock) { if (!isOpen()) throw new ClosedChannelException();  if (!isConnected()) throw new NotYetConnectedException();  return true; }  } public SctpChannelImpl(SelectorProvider paramSelectorProvider, FileDescriptor paramFileDescriptor, Association paramAssociation) throws IOException { super(paramSelectorProvider); this.internalNotificationHandler = new InternalNotificationHandler(); this.fd = paramFileDescriptor; this.fdVal = IOUtil.fdVal(paramFileDescriptor); this.state = ChannelState.CONNECTED; this.port = Net.localAddress(paramFileDescriptor).getPort(); if (paramAssociation != null) { this.association = paramAssociation; } else { ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(50); try { receive(byteBuffer, (Object)null, (NotificationHandler<?>)null, true); } finally { Util.releaseTemporaryDirectBuffer(byteBuffer); }  }  }
/*      */   private void ensureSendOpen() throws ClosedChannelException { synchronized (this.stateLock) { if (!isOpen()) throw new ClosedChannelException();  if (this.isShutdown) throw new ClosedChannelException();  if (!isConnected()) throw new NotYetConnectedException();  }  }
/*      */   private void receiverCleanup() throws IOException { synchronized (this.stateLock) { this.receiverThread = 0L; if (this.state == ChannelState.KILLPENDING) kill();  }  }
/*      */   private void senderCleanup() throws IOException { synchronized (this.stateLock) { this.senderThread = 0L; if (this.state == ChannelState.KILLPENDING) kill();  }  }
/*      */   public Association association() throws ClosedChannelException { synchronized (this.stateLock) { if (!isOpen()) throw new ClosedChannelException();  if (!isConnected()) return null;  return this.association; }  }
/*  871 */   public boolean connect(SocketAddress paramSocketAddress) throws IOException { synchronized (this.receiveLock) { synchronized (this.sendLock) { ensureOpenAndUnconnected(); InetSocketAddress inetSocketAddress = Net.checkAddress(paramSocketAddress); SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort());  synchronized (blockingLock()) { int i = 0; try { try { begin(); synchronized (this.stateLock) { if (!isOpen()) return false;  this.receiverThread = NativeThread.current(); }  while (true) { InetAddress inetAddress = inetSocketAddress.getAddress(); if (inetAddress.isAnyLocalAddress()) inetAddress = InetAddress.getLocalHost();  i = SctpNet.connect(this.fdVal, inetAddress, inetSocketAddress.getPort()); if (i == -3 && isOpen()) continue;  break; }  } finally { receiverCleanup(); end((i > 0 || i == -2)); assert IOStatus.check(i); }  } catch (IOException iOException) { close(); throw iOException; }  if (i > 0) synchronized (this.stateLock) { this.state = ChannelState.CONNECTED; if (!isBound()) { InetSocketAddress inetSocketAddress1 = Net.localAddress(this.fd); this.port = inetSocketAddress1.getPort(); }  ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(50); try { receive(byteBuffer, (Object)null, (NotificationHandler<?>)null, true); } finally { Util.releaseTemporaryDirectBuffer(byteBuffer); }  try { this.remoteAddresses = getRemoteAddresses(); } catch (IOException iOException) {} return true; }   synchronized (this.stateLock) { if (!isBlocking()) { this.state = ChannelState.PENDING; } else { assert false; }  }  }  return false; }  }  } public boolean connect(SocketAddress paramSocketAddress, int paramInt1, int paramInt2) throws IOException { ensureOpenAndUnconnected(); return setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, SctpStandardSocketOptions.InitMaxStreams.create(paramInt2, paramInt1)).connect(paramSocketAddress); } public boolean isConnectionPending() { synchronized (this.stateLock) { return (this.state == ChannelState.PENDING); }  } public boolean finishConnect() throws IOException { synchronized (this.receiveLock) { synchronized (this.sendLock) { synchronized (this.stateLock) { if (!isOpen()) throw new ClosedChannelException();  if (isConnected()) return true;  if (this.state != ChannelState.PENDING) throw new NoConnectionPendingException();  }  int i = 0; try { try { begin(); synchronized (blockingLock()) { synchronized (this.stateLock) { if (!isOpen()) return false;  this.receiverThread = NativeThread.current(); }  if (!isBlocking()) { while (true) { i = checkConnect(this.fd, false, this.readyToConnect); if (i == -3 && isOpen()) continue;  break; }  } else { while (true) { i = checkConnect(this.fd, true, this.readyToConnect); if (i == 0) continue;  if (i == -3 && isOpen()) continue;  break; }  }  }  } finally { synchronized (this.stateLock) { this.receiverThread = 0L; if (this.state == ChannelState.KILLPENDING) { kill(); i = 0; }  }  end((i > 0 || i == -2)); assert IOStatus.check(i); }  } catch (IOException iOException) { close(); throw iOException; }  if (i > 0) synchronized (this.stateLock) { this.state = ChannelState.CONNECTED; if (!isBound()) { InetSocketAddress inetSocketAddress = Net.localAddress(this.fd); this.port = inetSocketAddress.getPort(); }  ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(50); try { receive(byteBuffer, (Object)null, (NotificationHandler<?>)null, true); } finally { Util.releaseTemporaryDirectBuffer(byteBuffer); }  try { this.remoteAddresses = getRemoteAddresses(); } catch (IOException iOException) {} return true; }   }  }  return false; } protected void implConfigureBlocking(boolean paramBoolean) throws IOException { IOUtil.configureBlocking(this.fd, paramBoolean); } public void implCloseSelectableChannel() throws IOException { synchronized (this.stateLock) { SctpNet.preClose(this.fdVal); if (this.receiverThread != 0L) NativeThread.signal(this.receiverThread);  if (this.senderThread != 0L) NativeThread.signal(this.senderThread);  if (!isRegistered()) kill();  }  } public FileDescriptor getFD() { return this.fd; } public int getFDVal() { return this.fdVal; } private boolean translateReadyOps(int paramInt1, int paramInt2, SelectionKeyImpl paramSelectionKeyImpl) { int i = paramSelectionKeyImpl.nioInterestOps(); int j = paramSelectionKeyImpl.nioReadyOps(); int k = paramInt2; if ((paramInt1 & Net.POLLNVAL) != 0) return false;  if ((paramInt1 & (Net.POLLERR | Net.POLLHUP)) != 0) { k = i; paramSelectionKeyImpl.nioReadyOps(k); this.readyToConnect = true; return ((k & (j ^ 0xFFFFFFFF)) != 0); }  if ((paramInt1 & Net.POLLIN) != 0 && (i & 0x1) != 0 && isConnected()) k |= 0x1;  if ((paramInt1 & Net.POLLCONN) != 0 && (i & 0x8) != 0 && (this.state == ChannelState.UNCONNECTED || this.state == ChannelState.PENDING)) { k |= 0x8; this.readyToConnect = true; }  if ((paramInt1 & Net.POLLOUT) != 0 && (i & 0x4) != 0 && isConnected()) k |= 0x4;  paramSelectionKeyImpl.nioReadyOps(k); return ((k & (j ^ 0xFFFFFFFF)) != 0); } public boolean translateAndUpdateReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) { return translateReadyOps(paramInt, paramSelectionKeyImpl.nioReadyOps(), paramSelectionKeyImpl); } public boolean translateAndSetReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) { return translateReadyOps(paramInt, 0, paramSelectionKeyImpl); } public void translateAndSetInterestOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) { int i = 0; if ((paramInt & 0x1) != 0) i |= Net.POLLIN;  if ((paramInt & 0x4) != 0) i |= Net.POLLOUT;  if ((paramInt & 0x8) != 0) i |= Net.POLLCONN;  paramSelectionKeyImpl.selector.putEventOps(paramSelectionKeyImpl, i); } public void kill() throws IOException { synchronized (this.stateLock) { if (this.state == ChannelState.KILLED) return;  if (this.state == ChannelState.UNINITIALIZED) { this.state = ChannelState.KILLED; return; }  assert !isOpen() && !isRegistered(); if (this.receiverThread == 0L && this.senderThread == 0L) { SctpNet.close(this.fdVal); this.state = ChannelState.KILLED; } else { this.state = ChannelState.KILLPENDING; }  }  } private void handleNotificationInternal(ResultContainer paramResultContainer) { invokeNotificationHandler(paramResultContainer, this.internalNotificationHandler, (Object)null); }
/*      */   public <T> SctpChannel setOption(SctpSocketOption<T> paramSctpSocketOption, T paramT) throws IOException { if (paramSctpSocketOption == null) throw new NullPointerException();  if (!supportedOptions().contains(paramSctpSocketOption)) throw new UnsupportedOperationException("'" + paramSctpSocketOption + "' not supported");  synchronized (this.stateLock) { if (!isOpen()) throw new ClosedChannelException();  SctpNet.setSocketOption(this.fdVal, paramSctpSocketOption, paramT, 0); }  return this; }
/*      */   public <T> T getOption(SctpSocketOption<T> paramSctpSocketOption) throws IOException { if (paramSctpSocketOption == null) throw new NullPointerException();  if (!supportedOptions().contains(paramSctpSocketOption)) throw new UnsupportedOperationException("'" + paramSctpSocketOption + "' not supported");  synchronized (this.stateLock) { if (!isOpen()) throw new ClosedChannelException();  return (T)SctpNet.getSocketOption(this.fdVal, paramSctpSocketOption, 0); }  }
/*      */   private static class DefaultOptionsHolder {
/*      */     static final Set<SctpSocketOption<?>> defaultOptions = defaultOptions();
/*      */     private static Set<SctpSocketOption<?>> defaultOptions() { HashSet<SctpSocketOption<Boolean>> hashSet = new HashSet(10); hashSet.add(SctpStandardSocketOptions.SCTP_DISABLE_FRAGMENTS); hashSet.add(SctpStandardSocketOptions.SCTP_EXPLICIT_COMPLETE); hashSet.add(SctpStandardSocketOptions.SCTP_FRAGMENT_INTERLEAVE); hashSet.add(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS); hashSet.add(SctpStandardSocketOptions.SCTP_NODELAY); hashSet.add(SctpStandardSocketOptions.SCTP_PRIMARY_ADDR); hashSet.add(SctpStandardSocketOptions.SCTP_SET_PEER_PRIMARY_ADDR); hashSet.add(SctpStandardSocketOptions.SO_SNDBUF); hashSet.add(SctpStandardSocketOptions.SO_RCVBUF); hashSet.add(SctpStandardSocketOptions.SO_LINGER); return Collections.unmodifiableSet(hashSet); }
/*      */   }
/*      */   public final Set<SctpSocketOption<?>> supportedOptions() { return DefaultOptionsHolder.defaultOptions; }
/*      */   public <T> MessageInfo receive(ByteBuffer paramByteBuffer, T paramT, NotificationHandler<T> paramNotificationHandler) throws IOException { return receive(paramByteBuffer, paramT, paramNotificationHandler, false); } private <T> MessageInfo receive(ByteBuffer paramByteBuffer, T paramT, NotificationHandler<T> paramNotificationHandler, boolean paramBoolean) throws IOException { if (paramByteBuffer == null) throw new IllegalArgumentException("buffer cannot be null");  if (paramByteBuffer.isReadOnly()) throw new IllegalArgumentException("Read-only buffer");  if (((Boolean)this.receiveInvoked.get()).booleanValue()) throw new IllegalReceiveException("cannot invoke receive from handler");  this.receiveInvoked.set(Boolean.TRUE); try { ResultContainer resultContainer = new ResultContainer(); do { resultContainer.clear(); synchronized (this.receiveLock) { if (!ensureReceiveOpen()) return null;  int i = 0; try { begin(); synchronized (this.stateLock) { if (!isOpen()) return null;  this.receiverThread = NativeThread.current(); }  do { i = receive(this.fdVal, paramByteBuffer, resultContainer, paramBoolean); } while (i == -3 && isOpen()); } finally { receiverCleanup(); end((i > 0 || i == -2)); assert IOStatus.check(i); }  if (!resultContainer.isNotification()) { if (resultContainer.hasSomething()) { MessageInfoImpl messageInfoImpl = resultContainer.getMessageInfo(); synchronized (this.stateLock) { assert this.association != null; messageInfoImpl.setAssociation(this.association); }  return messageInfoImpl; }  return null; }  synchronized (this.stateLock) { handleNotificationInternal(resultContainer); }  if (paramBoolean) return null;  }  } while (paramNotificationHandler == null || invokeNotificationHandler(resultContainer, paramNotificationHandler, paramT) == HandlerResult.CONTINUE); return null; } finally { this.receiveInvoked.set(Boolean.FALSE); }  } private int receive(int paramInt, ByteBuffer paramByteBuffer, ResultContainer paramResultContainer, boolean paramBoolean) throws IOException { int i = paramByteBuffer.position(); int j = paramByteBuffer.limit(); assert i <= j; boolean bool = (i <= j) ? (j - i) : false; if (paramByteBuffer instanceof DirectBuffer && bool) return receiveIntoNativeBuffer(paramInt, paramResultContainer, paramByteBuffer, bool, i, paramBoolean);  int k = Math.max(bool, 1); ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(k); try { int m = receiveIntoNativeBuffer(paramInt, paramResultContainer, byteBuffer, k, 0, paramBoolean); byteBuffer.flip(); if (m > 0 && bool) paramByteBuffer.put(byteBuffer);  return m; } finally { Util.releaseTemporaryDirectBuffer(byteBuffer); }  } private int receiveIntoNativeBuffer(int paramInt1, ResultContainer paramResultContainer, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3, boolean paramBoolean) throws IOException { int i = receive0(paramInt1, paramResultContainer, ((DirectBuffer)paramByteBuffer).address() + paramInt3, paramInt2, paramBoolean); if (i > 0)
/*      */       paramByteBuffer.position(paramInt3 + i);  return i; } private class InternalNotificationHandler extends AbstractNotificationHandler<Object> {
/*  881 */     public HandlerResult handleNotification(AssociationChangeNotification param1AssociationChangeNotification, Object param1Object) { if (param1AssociationChangeNotification.event().equals(AssociationChangeNotification.AssocChangeEvent.COMM_UP) && SctpChannelImpl.this
/*      */         
/*  883 */         .association == null) {
/*  884 */         AssociationChange associationChange = (AssociationChange)param1AssociationChangeNotification;
/*  885 */         SctpChannelImpl.this.association = new AssociationImpl(associationChange
/*  886 */             .assocId(), associationChange.maxInStreams(), associationChange.maxOutStreams());
/*      */       } 
/*  888 */       return HandlerResult.CONTINUE; }
/*      */ 
/*      */ 
/*      */     
/*      */     private InternalNotificationHandler() {}
/*      */   }
/*      */   
/*      */   private <T> HandlerResult invokeNotificationHandler(ResultContainer paramResultContainer, NotificationHandler<T> paramNotificationHandler, T paramT) {
/*  896 */     SctpNotification sctpNotification = paramResultContainer.notification();
/*  897 */     synchronized (this.stateLock) {
/*  898 */       sctpNotification.setAssociation(this.association);
/*      */     } 
/*      */     
/*  901 */     if (!(paramNotificationHandler instanceof AbstractNotificationHandler)) {
/*  902 */       return paramNotificationHandler.handleNotification(sctpNotification, paramT);
/*      */     }
/*      */ 
/*      */     
/*  906 */     AbstractNotificationHandler<T> abstractNotificationHandler = (AbstractNotificationHandler)paramNotificationHandler;
/*      */     
/*  908 */     switch (paramResultContainer.type()) {
/*      */       case 3:
/*  910 */         return abstractNotificationHandler.handleNotification(paramResultContainer
/*  911 */             .getAssociationChanged(), paramT);
/*      */       case 4:
/*  913 */         return abstractNotificationHandler.handleNotification(paramResultContainer
/*  914 */             .getPeerAddressChanged(), paramT);
/*      */       case 2:
/*  916 */         return abstractNotificationHandler.handleNotification(paramResultContainer
/*  917 */             .getSendFailed(), paramT);
/*      */       case 5:
/*  919 */         return abstractNotificationHandler.handleNotification(paramResultContainer
/*  920 */             .getShutdown(), paramT);
/*      */     } 
/*      */     
/*  923 */     return abstractNotificationHandler.handleNotification(paramResultContainer
/*  924 */         .notification(), paramT);
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkAssociation(Association paramAssociation) {
/*  929 */     synchronized (this.stateLock) {
/*  930 */       if (paramAssociation != null && !paramAssociation.equals(this.association)) {
/*  931 */         throw new IllegalArgumentException("Cannot send to another association");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkStreamNumber(int paramInt) {
/*  938 */     synchronized (this.stateLock) {
/*  939 */       if (this.association != null && (
/*  940 */         paramInt < 0 || paramInt >= this.association
/*  941 */         .maxOutboundStreams())) {
/*  942 */         throw new InvalidStreamException();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int send(ByteBuffer paramByteBuffer, MessageInfo paramMessageInfo) throws IOException {
/*  954 */     if (paramByteBuffer == null) {
/*  955 */       throw new IllegalArgumentException("buffer cannot be null");
/*      */     }
/*  957 */     if (paramMessageInfo == null) {
/*  958 */       throw new IllegalArgumentException("messageInfo cannot be null");
/*      */     }
/*  960 */     checkAssociation(paramMessageInfo.association());
/*  961 */     checkStreamNumber(paramMessageInfo.streamNumber());
/*      */     
/*  963 */     synchronized (this.sendLock) {
/*  964 */       ensureSendOpen();
/*      */       
/*  966 */       int i = 0;
/*      */       try {
/*  968 */         begin();
/*      */         
/*  970 */         synchronized (this.stateLock) {
/*  971 */           if (!isOpen())
/*  972 */             return 0; 
/*  973 */           this.senderThread = NativeThread.current();
/*      */         } 
/*      */         
/*      */         do {
/*  977 */           i = send(this.fdVal, paramByteBuffer, paramMessageInfo);
/*  978 */         } while (i == -3 && isOpen());
/*      */         
/*  980 */         return IOStatus.normalize(i);
/*      */       } finally {
/*  982 */         senderCleanup();
/*  983 */         end((i > 0 || i == -2));
/*  984 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private int send(int paramInt, ByteBuffer paramByteBuffer, MessageInfo paramMessageInfo) throws IOException {
/*  991 */     int i = paramMessageInfo.streamNumber();
/*  992 */     SocketAddress socketAddress = paramMessageInfo.address();
/*  993 */     boolean bool = paramMessageInfo.isUnordered();
/*  994 */     int j = paramMessageInfo.payloadProtocolID();
/*      */     
/*  996 */     if (paramByteBuffer instanceof DirectBuffer) {
/*  997 */       return sendFromNativeBuffer(paramInt, paramByteBuffer, socketAddress, i, bool, j);
/*      */     }
/*      */ 
/*      */     
/* 1001 */     int k = paramByteBuffer.position();
/* 1002 */     int m = paramByteBuffer.limit();
/* 1003 */     assert k <= m && i >= 0;
/*      */     
/* 1005 */     boolean bool1 = (k <= m) ? (m - k) : false;
/* 1006 */     ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(bool1);
/*      */     try {
/* 1008 */       byteBuffer.put(paramByteBuffer);
/* 1009 */       byteBuffer.flip();
/*      */       
/* 1011 */       paramByteBuffer.position(k);
/*      */       
/* 1013 */       int n = sendFromNativeBuffer(paramInt, byteBuffer, socketAddress, i, bool, j);
/*      */       
/* 1015 */       if (n > 0)
/*      */       {
/* 1017 */         paramByteBuffer.position(k + n);
/*      */       }
/* 1019 */       return n;
/*      */     } finally {
/* 1021 */       Util.releaseTemporaryDirectBuffer(byteBuffer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int sendFromNativeBuffer(int paramInt1, ByteBuffer paramByteBuffer, SocketAddress paramSocketAddress, int paramInt2, boolean paramBoolean, int paramInt3) throws IOException {
/* 1032 */     InetAddress inetAddress = null;
/* 1033 */     int i = 0;
/* 1034 */     if (paramSocketAddress != null) {
/* 1035 */       InetSocketAddress inetSocketAddress = Net.checkAddress(paramSocketAddress);
/* 1036 */       inetAddress = inetSocketAddress.getAddress();
/* 1037 */       i = inetSocketAddress.getPort();
/*      */     } 
/*      */     
/* 1040 */     int j = paramByteBuffer.position();
/* 1041 */     int k = paramByteBuffer.limit();
/* 1042 */     assert j <= k;
/* 1043 */     boolean bool = (j <= k) ? (k - j) : false;
/*      */     
/* 1045 */     int m = send0(paramInt1, ((DirectBuffer)paramByteBuffer).address() + j, bool, inetAddress, i, -1, paramInt2, paramBoolean, paramInt3);
/*      */     
/* 1047 */     if (m > 0)
/* 1048 */       paramByteBuffer.position(j + m); 
/* 1049 */     return m;
/*      */   }
/*      */ 
/*      */   
/*      */   public SctpChannel shutdown() throws IOException {
/* 1054 */     synchronized (this.stateLock) {
/* 1055 */       if (this.isShutdown) {
/* 1056 */         return this;
/*      */       }
/* 1058 */       ensureSendOpen();
/* 1059 */       SctpNet.shutdown(this.fdVal, -1);
/* 1060 */       if (this.senderThread != 0L)
/* 1061 */         NativeThread.signal(this.senderThread); 
/* 1062 */       this.isShutdown = true;
/*      */     } 
/* 1064 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<SocketAddress> getAllLocalAddresses() throws IOException {
/* 1070 */     synchronized (this.stateLock) {
/* 1071 */       if (!isOpen())
/* 1072 */         throw new ClosedChannelException(); 
/* 1073 */       if (!isBound()) {
/* 1074 */         return Collections.emptySet();
/*      */       }
/* 1076 */       return SctpNet.getLocalAddresses(this.fdVal);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<SocketAddress> getRemoteAddresses() throws IOException {
/* 1083 */     synchronized (this.stateLock) {
/* 1084 */       if (!isOpen())
/* 1085 */         throw new ClosedChannelException(); 
/* 1086 */       if (!isConnected() || this.isShutdown) {
/* 1087 */         return Collections.emptySet();
/*      */       }
/*      */       try {
/* 1090 */         return SctpNet.getRemoteAddresses(this.fdVal, 0);
/* 1091 */       } catch (SocketException socketException) {
/*      */         
/* 1093 */         return this.remoteAddresses;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/* 1112 */     IOUtil.load();
/* 1113 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */         {
/*      */           public Void run() {
/* 1116 */             System.loadLibrary("sctp");
/* 1117 */             return null;
/*      */           }
/*      */         });
/* 1120 */     initIDs();
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   static native int receive0(int paramInt1, ResultContainer paramResultContainer, long paramLong, int paramInt2, boolean paramBoolean) throws IOException;
/*      */   
/*      */   static native int send0(int paramInt1, long paramLong, int paramInt2, InetAddress paramInetAddress, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, int paramInt6) throws IOException;
/*      */   
/*      */   private static native int checkConnect(FileDescriptor paramFileDescriptor, boolean paramBoolean1, boolean paramBoolean2) throws IOException;
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/sctp/SctpChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */