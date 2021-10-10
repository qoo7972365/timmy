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
/*      */ import com.sun.nio.sctp.SctpMultiChannel;
/*      */ import com.sun.nio.sctp.SctpSocketOption;
/*      */ import com.sun.nio.sctp.SctpStandardSocketOptions;
/*      */ import java.io.FileDescriptor;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.SocketAddress;
/*      */ import java.net.SocketException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.NotYetBoundException;
/*      */ import java.nio.channels.spi.SelectorProvider;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
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
/*      */ public class SctpMultiChannelImpl
/*      */   extends SctpMultiChannel
/*      */   implements SelChImpl
/*      */ {
/*      */   private final FileDescriptor fd;
/*      */   private final int fdVal;
/*   79 */   private volatile long receiverThread = 0L;
/*   80 */   private volatile long senderThread = 0L;
/*      */ 
/*      */   
/*   83 */   private final Object receiveLock = new Object();
/*      */ 
/*      */   
/*   86 */   private final Object sendLock = new Object();
/*      */ 
/*      */ 
/*      */   
/*   90 */   private final Object stateLock = new Object();
/*      */   
/*      */   private enum ChannelState {
/*   93 */     UNINITIALIZED,
/*   94 */     KILLPENDING,
/*   95 */     KILLED;
/*      */   }
/*      */ 
/*      */   
/*   99 */   private ChannelState state = ChannelState.UNINITIALIZED;
/*      */ 
/*      */   
/*  102 */   int port = -1;
/*  103 */   private HashSet<InetSocketAddress> localAddresses = new HashSet<>();
/*      */ 
/*      */   
/*      */   private boolean wildcard;
/*      */   
/*  108 */   private HashMap<SocketAddress, Association> addressMap = new HashMap<>();
/*      */   
/*  110 */   private HashMap<Association, Set<SocketAddress>> associationMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  117 */   private final ThreadLocal<Association> associationToRemove = new ThreadLocal<Association>()
/*      */     {
/*      */       protected Association initialValue() {
/*  120 */         return null;
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  125 */   private final ThreadLocal<Boolean> receiveInvoked = new ThreadLocal<Boolean>()
/*      */     {
/*      */       protected Boolean initialValue() {
/*  128 */         return Boolean.FALSE;
/*      */       }
/*      */     };
/*      */   
/*      */   private InternalNotificationHandler internalNotificationHandler;
/*      */   
/*      */   public SctpMultiChannelImpl(SelectorProvider paramSelectorProvider) throws IOException {
/*  135 */     super(paramSelectorProvider);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  588 */     this.internalNotificationHandler = new InternalNotificationHandler(); this.fd = SctpNet.socket(false); this.fdVal = IOUtil.fdVal(this.fd);
/*      */   }
/*      */   public SctpMultiChannel bind(SocketAddress paramSocketAddress, int paramInt) throws IOException { synchronized (this.receiveLock) { synchronized (this.sendLock) { synchronized (this.stateLock) { ensureOpen(); if (isBound()) SctpNet.throwAlreadyBoundException();  InetSocketAddress inetSocketAddress1 = (paramSocketAddress == null) ? new InetSocketAddress(0) : Net.checkAddress(paramSocketAddress); SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkListen(inetSocketAddress1.getPort());  Net.bind(this.fd, inetSocketAddress1.getAddress(), inetSocketAddress1.getPort()); InetSocketAddress inetSocketAddress2 = Net.localAddress(this.fd); this.port = inetSocketAddress2.getPort(); this.localAddresses.add(inetSocketAddress1); if (inetSocketAddress1.getAddress().isAnyLocalAddress()) this.wildcard = true;  SctpNet.listen(this.fdVal, (paramInt < 1) ? 50 : paramInt); }  }  }  return this; }
/*      */   public SctpMultiChannel bindAddress(InetAddress paramInetAddress) throws IOException { return bindUnbindAddress(paramInetAddress, true); }
/*      */   public SctpMultiChannel unbindAddress(InetAddress paramInetAddress) throws IOException { return bindUnbindAddress(paramInetAddress, false); }
/*  593 */   private SctpMultiChannel bindUnbindAddress(InetAddress paramInetAddress, boolean paramBoolean) throws IOException { if (paramInetAddress == null) throw new IllegalArgumentException();  synchronized (this.receiveLock) { synchronized (this.sendLock) { synchronized (this.stateLock) { if (!isOpen()) throw new ClosedChannelException();  if (!isBound()) throw new NotYetBoundException();  if (this.wildcard) throw new IllegalStateException("Cannot add or remove addresses from a channel that is bound to the wildcard address");  if (paramInetAddress.isAnyLocalAddress()) throw new IllegalArgumentException("Cannot add or remove the wildcard address");  if (paramBoolean) { for (InetSocketAddress inetSocketAddress : this.localAddresses) { if (inetSocketAddress.getAddress().equals(paramInetAddress)) SctpNet.throwAlreadyBoundException();  }  } else { if (this.localAddresses.size() <= 1) throw new IllegalUnbindException("Cannot remove address from a channel with only one address bound");  boolean bool = false; for (InetSocketAddress inetSocketAddress : this.localAddresses) { if (inetSocketAddress.getAddress().equals(paramInetAddress)) { bool = true; break; }  }  if (!bool) throw new IllegalUnbindException("Cannot remove address from a channel that is not bound to that address");  }  SctpNet.bindx(this.fdVal, new InetAddress[] { paramInetAddress }, this.port, paramBoolean); if (paramBoolean) { this.localAddresses.add(new InetSocketAddress(paramInetAddress, this.port)); } else { for (InetSocketAddress inetSocketAddress : this.localAddresses) { if (inetSocketAddress.getAddress().equals(paramInetAddress)) { this.localAddresses.remove(inetSocketAddress); break; }  }  }  }  }  }  return this; } public Set<Association> associations() throws ClosedChannelException, NotYetBoundException { synchronized (this.stateLock) { if (!isOpen()) throw new ClosedChannelException();  if (!isBound()) throw new NotYetBoundException();  return Collections.unmodifiableSet(this.associationMap.keySet()); }  } private boolean isBound() { synchronized (this.stateLock) { return !(this.port == -1); }  } private void ensureOpen() throws IOException { synchronized (this.stateLock) { if (!isOpen()) throw new ClosedChannelException();  }  } private void receiverCleanup() throws IOException { synchronized (this.stateLock) { this.receiverThread = 0L; if (this.state == ChannelState.KILLPENDING) kill();  }  } private void senderCleanup() throws IOException { synchronized (this.stateLock) { this.senderThread = 0L; if (this.state == ChannelState.KILLPENDING) kill();  }  } protected void implConfigureBlocking(boolean paramBoolean) throws IOException { IOUtil.configureBlocking(this.fd, paramBoolean); } public void implCloseSelectableChannel() throws IOException { synchronized (this.stateLock) { SctpNet.preClose(this.fdVal); if (this.receiverThread != 0L) NativeThread.signal(this.receiverThread);  if (this.senderThread != 0L) NativeThread.signal(this.senderThread);  if (!isRegistered()) kill();  }  } public FileDescriptor getFD() { return this.fd; } public int getFDVal() { return this.fdVal; } private boolean translateReadyOps(int paramInt1, int paramInt2, SelectionKeyImpl paramSelectionKeyImpl) { int i = paramSelectionKeyImpl.nioInterestOps(); int j = paramSelectionKeyImpl.nioReadyOps(); int k = paramInt2; if ((paramInt1 & Net.POLLNVAL) != 0) return false;  if ((paramInt1 & (Net.POLLERR | Net.POLLHUP)) != 0) { k = i; paramSelectionKeyImpl.nioReadyOps(k); return ((k & (j ^ 0xFFFFFFFF)) != 0); }  if ((paramInt1 & Net.POLLIN) != 0 && (i & 0x1) != 0) k |= 0x1;  if ((paramInt1 & Net.POLLOUT) != 0 && (i & 0x4) != 0) k |= 0x4;  paramSelectionKeyImpl.nioReadyOps(k); return ((k & (j ^ 0xFFFFFFFF)) != 0); } public boolean translateAndUpdateReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) { return translateReadyOps(paramInt, paramSelectionKeyImpl.nioReadyOps(), paramSelectionKeyImpl); } public boolean translateAndSetReadyOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) { return translateReadyOps(paramInt, 0, paramSelectionKeyImpl); } public void translateAndSetInterestOps(int paramInt, SelectionKeyImpl paramSelectionKeyImpl) { int i = 0; if ((paramInt & 0x1) != 0) i |= Net.POLLIN;  if ((paramInt & 0x4) != 0) i |= Net.POLLOUT;  paramSelectionKeyImpl.selector.putEventOps(paramSelectionKeyImpl, i); } public void kill() throws IOException { synchronized (this.stateLock) { if (this.state == ChannelState.KILLED) return;  if (this.state == ChannelState.UNINITIALIZED) { this.state = ChannelState.KILLED; return; }  assert !isOpen() && !isRegistered(); if (this.receiverThread == 0L && this.senderThread == 0L) { SctpNet.close(this.fdVal); this.state = ChannelState.KILLED; } else { this.state = ChannelState.KILLPENDING; }  }  } public <T> SctpMultiChannel setOption(SctpSocketOption<T> paramSctpSocketOption, T paramT, Association paramAssociation) throws IOException { if (paramSctpSocketOption == null) throw new NullPointerException();  if (!supportedOptions().contains(paramSctpSocketOption)) throw new UnsupportedOperationException("'" + paramSctpSocketOption + "' not supported");  synchronized (this.stateLock) { if (paramAssociation != null && (paramSctpSocketOption.equals(SctpStandardSocketOptions.SCTP_PRIMARY_ADDR) || paramSctpSocketOption.equals(SctpStandardSocketOptions.SCTP_SET_PEER_PRIMARY_ADDR))) checkAssociation(paramAssociation);  if (!isOpen()) throw new ClosedChannelException();  boolean bool = (paramAssociation == null) ? false : paramAssociation.associationID(); SctpNet.setSocketOption(this.fdVal, paramSctpSocketOption, paramT, bool); }  return this; } private void handleNotificationInternal(ResultContainer paramResultContainer) { invokeNotificationHandler(paramResultContainer, this.internalNotificationHandler, (Object)null); }
/*      */   public <T> T getOption(SctpSocketOption<T> paramSctpSocketOption, Association paramAssociation) throws IOException { if (paramSctpSocketOption == null) throw new NullPointerException();  if (!supportedOptions().contains(paramSctpSocketOption)) throw new UnsupportedOperationException("'" + paramSctpSocketOption + "' not supported");  synchronized (this.stateLock) { if (paramAssociation != null && (paramSctpSocketOption.equals(SctpStandardSocketOptions.SCTP_PRIMARY_ADDR) || paramSctpSocketOption.equals(SctpStandardSocketOptions.SCTP_SET_PEER_PRIMARY_ADDR))) checkAssociation(paramAssociation);  if (!isOpen()) throw new ClosedChannelException();  boolean bool = (paramAssociation == null) ? false : paramAssociation.associationID(); return (T)SctpNet.getSocketOption(this.fdVal, paramSctpSocketOption, bool); }  }
/*      */   private static class DefaultOptionsHolder {
/*      */     static final Set<SctpSocketOption<?>> defaultOptions = defaultOptions();
/*      */     private static Set<SctpSocketOption<?>> defaultOptions() { HashSet<SctpSocketOption<Boolean>> hashSet = new HashSet(10); hashSet.add(SctpStandardSocketOptions.SCTP_DISABLE_FRAGMENTS); hashSet.add(SctpStandardSocketOptions.SCTP_EXPLICIT_COMPLETE); hashSet.add(SctpStandardSocketOptions.SCTP_FRAGMENT_INTERLEAVE); hashSet.add(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS); hashSet.add(SctpStandardSocketOptions.SCTP_NODELAY); hashSet.add(SctpStandardSocketOptions.SCTP_PRIMARY_ADDR); hashSet.add(SctpStandardSocketOptions.SCTP_SET_PEER_PRIMARY_ADDR); hashSet.add(SctpStandardSocketOptions.SO_SNDBUF); hashSet.add(SctpStandardSocketOptions.SO_RCVBUF); hashSet.add(SctpStandardSocketOptions.SO_LINGER); return Collections.unmodifiableSet(hashSet); }
/*      */   }
/*      */   public final Set<SctpSocketOption<?>> supportedOptions() { return DefaultOptionsHolder.defaultOptions; } public <T> MessageInfo receive(ByteBuffer paramByteBuffer, T paramT, NotificationHandler<T> paramNotificationHandler) throws IOException { if (paramByteBuffer == null) throw new IllegalArgumentException("buffer cannot be null");  if (paramByteBuffer.isReadOnly()) throw new IllegalArgumentException("Read-only buffer");  if (((Boolean)this.receiveInvoked.get()).booleanValue()) throw new IllegalReceiveException("cannot invoke receive from handler");  this.receiveInvoked.set(Boolean.TRUE); try { ResultContainer resultContainer = new ResultContainer(); do { resultContainer.clear(); synchronized (this.receiveLock) { ensureOpen(); if (!isBound()) throw new NotYetBoundException();  int i = 0; try { begin(); synchronized (this.stateLock) { if (!isOpen()) return null;  this.receiverThread = NativeThread.current(); }  do { i = receive(this.fdVal, paramByteBuffer, resultContainer); } while (i == -3 && isOpen()); } finally { receiverCleanup(); end((i > 0 || i == -2)); assert IOStatus.check(i); }  if (!resultContainer.isNotification()) { if (resultContainer.hasSomething()) { MessageInfoImpl messageInfoImpl = resultContainer.getMessageInfo(); messageInfoImpl.setAssociation(lookupAssociation(messageInfoImpl.associationID())); SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) { InetSocketAddress inetSocketAddress = (InetSocketAddress)messageInfoImpl.address(); if (!this.addressMap.containsKey(inetSocketAddress)) try { securityManager.checkAccept(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort()); } catch (SecurityException securityException) { paramByteBuffer.clear(); throw securityException; }   }  assert messageInfoImpl.association() != null; return messageInfoImpl; }  return null; }  synchronized (this.stateLock) { handleNotificationInternal(resultContainer); }  }  } while (paramNotificationHandler == null || invokeNotificationHandler(resultContainer, paramNotificationHandler, paramT) == HandlerResult.CONTINUE); } finally { this.receiveInvoked.set(Boolean.FALSE); }  return null; } private int receive(int paramInt, ByteBuffer paramByteBuffer, ResultContainer paramResultContainer) throws IOException { int i = paramByteBuffer.position(); int j = paramByteBuffer.limit(); assert i <= j; boolean bool = (i <= j) ? (j - i) : false; if (paramByteBuffer instanceof DirectBuffer && bool)
/*      */       return receiveIntoNativeBuffer(paramInt, paramResultContainer, paramByteBuffer, bool, i);  int k = Math.max(bool, 1); ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(k); try { int m = receiveIntoNativeBuffer(paramInt, paramResultContainer, byteBuffer, k, 0); byteBuffer.flip(); if (m > 0 && bool)
/*      */         paramByteBuffer.put(byteBuffer);  return m; } finally { Util.releaseTemporaryDirectBuffer(byteBuffer); }  } private int receiveIntoNativeBuffer(int paramInt1, ResultContainer paramResultContainer, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3) throws IOException { int i = receive0(paramInt1, paramResultContainer, ((DirectBuffer)paramByteBuffer).address() + paramInt3, paramInt2); if (i > 0)
/*      */       paramByteBuffer.position(paramInt3 + i);  return i; } private class InternalNotificationHandler extends AbstractNotificationHandler<Object> {
/*  603 */     public HandlerResult handleNotification(AssociationChangeNotification param1AssociationChangeNotification, Object param1Object) { AssociationImpl associationImpl; AssociationChange associationChange = (AssociationChange)param1AssociationChangeNotification;
/*      */ 
/*      */       
/*  606 */       switch (param1AssociationChangeNotification.event()) {
/*      */         
/*      */         case COMM_UP:
/*  609 */           associationImpl = new AssociationImpl(associationChange.assocId(), associationChange.maxInStreams(), associationChange.maxOutStreams());
/*  610 */           SctpMultiChannelImpl.this.addAssociation(associationImpl);
/*      */           break;
/*      */ 
/*      */         
/*      */         case SHUTDOWN:
/*      */         case COMM_LOST:
/*  616 */           SctpMultiChannelImpl.this.associationToRemove.set(SctpMultiChannelImpl.this.lookupAssociation(associationChange.assocId())); break;
/*      */       } 
/*  618 */       return HandlerResult.CONTINUE; }
/*      */ 
/*      */ 
/*      */     
/*      */     private InternalNotificationHandler() {}
/*      */   }
/*      */   
/*      */   private <T> HandlerResult invokeNotificationHandler(ResultContainer paramResultContainer, NotificationHandler<T> paramNotificationHandler, T paramT) {
/*      */     HandlerResult handlerResult;
/*  627 */     SctpNotification sctpNotification = paramResultContainer.notification();
/*  628 */     sctpNotification.setAssociation(lookupAssociation(sctpNotification.assocId()));
/*      */     
/*  630 */     if (!(paramNotificationHandler instanceof AbstractNotificationHandler)) {
/*  631 */       handlerResult = paramNotificationHandler.handleNotification(sctpNotification, paramT);
/*      */     } else {
/*  633 */       AbstractNotificationHandler<T> abstractNotificationHandler = (AbstractNotificationHandler)paramNotificationHandler;
/*      */       
/*  635 */       switch (paramResultContainer.type()) {
/*      */         case 3:
/*  637 */           handlerResult = abstractNotificationHandler.handleNotification(paramResultContainer
/*  638 */               .getAssociationChanged(), paramT);
/*      */           break;
/*      */         case 4:
/*  641 */           handlerResult = abstractNotificationHandler.handleNotification(paramResultContainer
/*  642 */               .getPeerAddressChanged(), paramT);
/*      */           break;
/*      */         case 2:
/*  645 */           handlerResult = abstractNotificationHandler.handleNotification(paramResultContainer
/*  646 */               .getSendFailed(), paramT);
/*      */           break;
/*      */         case 5:
/*  649 */           handlerResult = abstractNotificationHandler.handleNotification(paramResultContainer
/*  650 */               .getShutdown(), paramT);
/*      */           break;
/*      */         
/*      */         default:
/*  654 */           handlerResult = abstractNotificationHandler.handleNotification(paramResultContainer
/*  655 */               .notification(), paramT);
/*      */           break;
/*      */       } 
/*      */     } 
/*  659 */     if (!(paramNotificationHandler instanceof InternalNotificationHandler)) {
/*      */ 
/*      */       
/*  662 */       Association association = this.associationToRemove.get();
/*  663 */       if (association != null) {
/*  664 */         removeAssociation(association);
/*  665 */         this.associationToRemove.set(null);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  670 */     return handlerResult;
/*      */   }
/*      */ 
/*      */   
/*      */   private Association lookupAssociation(int paramInt) {
/*  675 */     synchronized (this.stateLock) {
/*  676 */       Set<Association> set = this.associationMap.keySet();
/*  677 */       for (Association association : set) {
/*  678 */         if (association.associationID() == paramInt) {
/*  679 */           return association;
/*      */         }
/*      */       } 
/*      */     } 
/*  683 */     return null;
/*      */   }
/*      */   
/*      */   private void addAssociation(Association paramAssociation) {
/*  687 */     synchronized (this.stateLock) {
/*  688 */       int i = paramAssociation.associationID();
/*  689 */       Set<SocketAddress> set = null;
/*      */       
/*      */       try {
/*  692 */         set = SctpNet.getRemoteAddresses(this.fdVal, i);
/*  693 */       } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  698 */       this.associationMap.put(paramAssociation, set);
/*  699 */       if (set != null)
/*  700 */         for (SocketAddress socketAddress : set) {
/*  701 */           this.addressMap.put(socketAddress, paramAssociation);
/*      */         } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void removeAssociation(Association paramAssociation) {
/*  707 */     synchronized (this.stateLock) {
/*  708 */       int i = paramAssociation.associationID();
/*  709 */       Set<SocketAddress> set = null;
/*      */       
/*      */       try {
/*  712 */         set = SctpNet.getRemoteAddresses(this.fdVal, i);
/*  713 */       } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  718 */       Set<Association> set1 = this.associationMap.keySet();
/*  719 */       for (Association association : set1) {
/*  720 */         if (association.associationID() == i) {
/*  721 */           this.associationMap.remove(association);
/*      */           break;
/*      */         } 
/*      */       } 
/*  725 */       if (set != null) {
/*  726 */         for (SocketAddress socketAddress : set) {
/*  727 */           this.addressMap.remove(socketAddress);
/*      */         }
/*      */       } else {
/*      */         
/*  731 */         Set<Map.Entry<SocketAddress, Association>> set2 = this.addressMap.entrySet();
/*  732 */         Iterator<Map.Entry<SocketAddress, Association>> iterator = set2.iterator();
/*  733 */         while (iterator.hasNext()) {
/*  734 */           Map.Entry entry = iterator.next();
/*  735 */           if (((Association)entry.getValue()).equals(paramAssociation)) {
/*  736 */             iterator.remove();
/*      */           }
/*      */         } 
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
/*      */   private boolean checkAssociation(Association paramAssociation) {
/*  751 */     synchronized (this.stateLock) {
/*  752 */       for (Association association : this.associationMap.keySet()) {
/*  753 */         if (paramAssociation.equals(association)) {
/*  754 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*  758 */     throw new IllegalArgumentException("Given Association is not controlled by this channel");
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkStreamNumber(Association paramAssociation, int paramInt) {
/*  763 */     synchronized (this.stateLock) {
/*  764 */       if (paramInt < 0 || paramInt >= paramAssociation.maxOutboundStreams()) {
/*  765 */         throw new InvalidStreamException();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int send(ByteBuffer paramByteBuffer, MessageInfo paramMessageInfo) throws IOException {
/*  776 */     if (paramByteBuffer == null) {
/*  777 */       throw new IllegalArgumentException("buffer cannot be null");
/*      */     }
/*  779 */     if (paramMessageInfo == null) {
/*  780 */       throw new IllegalArgumentException("messageInfo cannot be null");
/*      */     }
/*  782 */     synchronized (this.sendLock) {
/*  783 */       ensureOpen();
/*      */       
/*  785 */       if (!isBound()) {
/*  786 */         bind((SocketAddress)null, 0);
/*      */       }
/*  788 */       int i = 0;
/*      */       try {
/*  790 */         int j = -1;
/*  791 */         InetSocketAddress inetSocketAddress = null;
/*  792 */         begin();
/*      */         
/*  794 */         synchronized (this.stateLock) {
/*  795 */           if (!isOpen())
/*  796 */             return 0; 
/*  797 */           this.senderThread = NativeThread.current();
/*      */ 
/*      */           
/*  800 */           Association association = paramMessageInfo.association();
/*  801 */           InetSocketAddress inetSocketAddress1 = (InetSocketAddress)paramMessageInfo.address();
/*  802 */           if (association != null) {
/*  803 */             checkAssociation(association);
/*  804 */             checkStreamNumber(association, paramMessageInfo.streamNumber());
/*  805 */             j = association.associationID();
/*      */             
/*  807 */             if (inetSocketAddress1 != null) {
/*  808 */               if (!association.equals(this.addressMap.get(inetSocketAddress1)))
/*  809 */                 throw new IllegalArgumentException("given preferred address is not part of this association"); 
/*  810 */               inetSocketAddress = inetSocketAddress1;
/*      */             } 
/*  812 */           } else if (inetSocketAddress1 != null) {
/*  813 */             inetSocketAddress = inetSocketAddress1;
/*  814 */             Association association1 = this.addressMap.get(inetSocketAddress1);
/*  815 */             if (association1 != null) {
/*  816 */               checkStreamNumber(association1, paramMessageInfo.streamNumber());
/*  817 */               j = association1.associationID();
/*      */             } else {
/*      */               
/*  820 */               SecurityManager securityManager = System.getSecurityManager();
/*  821 */               if (securityManager != null)
/*  822 */                 securityManager.checkConnect(inetSocketAddress1.getAddress().getHostAddress(), inetSocketAddress1
/*  823 */                     .getPort()); 
/*      */             } 
/*      */           } else {
/*  826 */             throw new AssertionError("Both association and address cannot be null");
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*      */         do {
/*  832 */           i = send(this.fdVal, paramByteBuffer, j, inetSocketAddress, paramMessageInfo);
/*  833 */         } while (i == -3 && isOpen());
/*      */         
/*  835 */         return IOStatus.normalize(i);
/*      */       } finally {
/*  837 */         senderCleanup();
/*  838 */         end((i > 0 || i == -2));
/*  839 */         assert IOStatus.check(i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int send(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, SocketAddress paramSocketAddress, MessageInfo paramMessageInfo) throws IOException {
/*  850 */     int i = paramMessageInfo.streamNumber();
/*  851 */     boolean bool = paramMessageInfo.isUnordered();
/*  852 */     int j = paramMessageInfo.payloadProtocolID();
/*      */     
/*  854 */     if (paramByteBuffer instanceof DirectBuffer) {
/*  855 */       return sendFromNativeBuffer(paramInt1, paramByteBuffer, paramSocketAddress, paramInt2, i, bool, j);
/*      */     }
/*      */ 
/*      */     
/*  859 */     int k = paramByteBuffer.position();
/*  860 */     int m = paramByteBuffer.limit();
/*  861 */     assert k <= m && i >= 0;
/*      */     
/*  863 */     boolean bool1 = (k <= m) ? (m - k) : false;
/*  864 */     ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(bool1);
/*      */     try {
/*  866 */       byteBuffer.put(paramByteBuffer);
/*  867 */       byteBuffer.flip();
/*      */       
/*  869 */       paramByteBuffer.position(k);
/*      */       
/*  871 */       int n = sendFromNativeBuffer(paramInt1, byteBuffer, paramSocketAddress, paramInt2, i, bool, j);
/*      */       
/*  873 */       if (n > 0)
/*      */       {
/*  875 */         paramByteBuffer.position(k + n);
/*      */       }
/*  877 */       return n;
/*      */     } finally {
/*  879 */       Util.releaseTemporaryDirectBuffer(byteBuffer);
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
/*      */   private int sendFromNativeBuffer(int paramInt1, ByteBuffer paramByteBuffer, SocketAddress paramSocketAddress, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4) throws IOException {
/*  891 */     InetAddress inetAddress = null;
/*  892 */     int i = 0;
/*  893 */     if (paramSocketAddress != null) {
/*  894 */       InetSocketAddress inetSocketAddress = Net.checkAddress(paramSocketAddress);
/*  895 */       inetAddress = inetSocketAddress.getAddress();
/*  896 */       i = inetSocketAddress.getPort();
/*      */     } 
/*  898 */     int j = paramByteBuffer.position();
/*  899 */     int k = paramByteBuffer.limit();
/*  900 */     assert j <= k;
/*  901 */     boolean bool = (j <= k) ? (k - j) : false;
/*      */     
/*  903 */     int m = send0(paramInt1, ((DirectBuffer)paramByteBuffer).address() + j, bool, inetAddress, i, paramInt2, paramInt3, paramBoolean, paramInt4);
/*      */     
/*  905 */     if (m > 0)
/*  906 */       paramByteBuffer.position(j + m); 
/*  907 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SctpMultiChannel shutdown(Association paramAssociation) throws IOException {
/*  913 */     synchronized (this.stateLock) {
/*  914 */       checkAssociation(paramAssociation);
/*  915 */       if (!isOpen()) {
/*  916 */         throw new ClosedChannelException();
/*      */       }
/*  918 */       SctpNet.shutdown(this.fdVal, paramAssociation.associationID());
/*      */     } 
/*  920 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<SocketAddress> getAllLocalAddresses() throws IOException {
/*  926 */     synchronized (this.stateLock) {
/*  927 */       if (!isOpen())
/*  928 */         throw new ClosedChannelException(); 
/*  929 */       if (!isBound()) {
/*  930 */         return Collections.emptySet();
/*      */       }
/*  932 */       return SctpNet.getLocalAddresses(this.fdVal);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<SocketAddress> getRemoteAddresses(Association paramAssociation) throws IOException {
/*  939 */     synchronized (this.stateLock) {
/*  940 */       checkAssociation(paramAssociation);
/*  941 */       if (!isOpen()) {
/*  942 */         throw new ClosedChannelException();
/*      */       }
/*      */       try {
/*  945 */         return SctpNet.getRemoteAddresses(this.fdVal, paramAssociation.associationID());
/*  946 */       } catch (SocketException socketException) {
/*      */         
/*  948 */         Set<SocketAddress> set = this.associationMap.get(paramAssociation);
/*  949 */         return (set != null) ? set : Collections.<SocketAddress>emptySet();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SctpChannel branch(Association paramAssociation) throws IOException {
/*  957 */     synchronized (this.stateLock) {
/*  958 */       checkAssociation(paramAssociation);
/*  959 */       if (!isOpen()) {
/*  960 */         throw new ClosedChannelException();
/*      */       }
/*  962 */       FileDescriptor fileDescriptor = SctpNet.branch(this.fdVal, paramAssociation
/*  963 */           .associationID());
/*      */       
/*  965 */       removeAssociation(paramAssociation);
/*      */       
/*  967 */       return new SctpChannelImpl(provider(), fileDescriptor, paramAssociation);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int receive0(int paramInt1, ResultContainer paramResultContainer, long paramLong, int paramInt2) throws IOException {
/*  978 */     return SctpChannelImpl.receive0(paramInt1, paramResultContainer, paramLong, paramInt2, false);
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
/*      */   private static int send0(int paramInt1, long paramLong, int paramInt2, InetAddress paramInetAddress, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, int paramInt6) throws IOException {
/*  992 */     return SctpChannelImpl.send0(paramInt1, paramLong, paramInt2, paramInetAddress, paramInt3, paramInt4, paramInt5, paramBoolean, paramInt6);
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/*  997 */     IOUtil.load();
/*  998 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */         {
/*      */           public Void run() {
/* 1001 */             System.loadLibrary("sctp");
/* 1002 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/sctp/SctpMultiChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */