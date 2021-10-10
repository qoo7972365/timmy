/*      */ package com.sun.corba.se.impl.transport;
/*      */ 
/*      */ import com.sun.corba.se.impl.encoding.CDROutputObject;
/*      */ import com.sun.corba.se.impl.encoding.CachedCodeBase;
/*      */ import com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
/*      */ import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
/*      */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*      */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.CancelRequestMessage;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
/*      */ import com.sun.corba.se.pept.broker.Broker;
/*      */ import com.sun.corba.se.pept.encoding.InputObject;
/*      */ import com.sun.corba.se.pept.encoding.OutputObject;
/*      */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*      */ import com.sun.corba.se.pept.transport.Acceptor;
/*      */ import com.sun.corba.se.pept.transport.Connection;
/*      */ import com.sun.corba.se.pept.transport.ConnectionCache;
/*      */ import com.sun.corba.se.pept.transport.ContactInfo;
/*      */ import com.sun.corba.se.pept.transport.EventHandler;
/*      */ import com.sun.corba.se.pept.transport.InboundConnectionCache;
/*      */ import com.sun.corba.se.pept.transport.OutboundConnectionCache;
/*      */ import com.sun.corba.se.pept.transport.ResponseWaitingRoom;
/*      */ import com.sun.corba.se.pept.transport.Selector;
/*      */ import com.sun.corba.se.spi.ior.IOR;
/*      */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*      */ import com.sun.corba.se.spi.orb.ORB;
/*      */ import com.sun.corba.se.spi.orbutil.threadpool.NoSuchThreadPoolException;
/*      */ import com.sun.corba.se.spi.orbutil.threadpool.NoSuchWorkQueueException;
/*      */ import com.sun.corba.se.spi.orbutil.threadpool.Work;
/*      */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*      */ import com.sun.corba.se.spi.transport.CorbaConnection;
/*      */ import com.sun.corba.se.spi.transport.CorbaContactInfo;
/*      */ import com.sun.corba.se.spi.transport.CorbaResponseWaitingRoom;
/*      */ import com.sun.corba.se.spi.transport.ReadTimeouts;
/*      */ import com.sun.org.omg.SendingContext.CodeBase;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.Socket;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.channels.SelectableChannel;
/*      */ import java.nio.channels.SocketChannel;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import org.omg.CORBA.COMM_FAILURE;
/*      */ import org.omg.CORBA.CompletionStatus;
/*      */ import org.omg.CORBA.INTERNAL;
/*      */ import org.omg.CORBA.SystemException;
/*      */ import org.omg.CORBA.portable.OutputStream;
/*      */ import sun.corba.OutputStreamFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SocketOrChannelConnectionImpl
/*      */   extends EventHandlerBase
/*      */   implements CorbaConnection, Work
/*      */ {
/*      */   public static boolean dprintWriteLocks = false;
/*      */   protected long enqueueTime;
/*      */   protected SocketChannel socketChannel;
/*      */   protected CorbaContactInfo contactInfo;
/*      */   protected Acceptor acceptor;
/*      */   protected ConnectionCache connectionCache;
/*      */   protected Socket socket;
/*      */   
/*      */   public SocketChannel getSocketChannel() {
/*  113 */     return this.socketChannel;
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
/*  127 */   protected long timeStamp = 0L;
/*      */ 
/*      */   
/*      */   protected boolean isServer = false;
/*      */   
/*  132 */   protected int requestId = 5;
/*      */   protected CorbaResponseWaitingRoom responseWaitingRoom;
/*      */   protected int state;
/*  135 */   protected Object stateEvent = new Object();
/*  136 */   protected Object writeEvent = new Object();
/*      */   protected boolean writeLocked;
/*  138 */   protected int serverRequestCount = 0;
/*      */ 
/*      */ 
/*      */   
/*  142 */   Map serverRequestMap = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean postInitialContexts = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected IOR codeBaseServerIOR;
/*      */ 
/*      */ 
/*      */   
/*  156 */   protected CachedCodeBase cachedCodeBase = new CachedCodeBase(this);
/*      */ 
/*      */   
/*      */   protected ORBUtilSystemException wrapper;
/*      */ 
/*      */   
/*      */   protected ReadTimeouts readTimeouts;
/*      */ 
/*      */   
/*      */   protected boolean shouldReadGiopHeaderOnly;
/*      */ 
/*      */   
/*  168 */   protected CorbaMessageMediator partialMessageMediator = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CodeSetComponentInfo.CodeSetContext codeSetContext;
/*      */ 
/*      */ 
/*      */   
/*      */   protected MessageMediator clientReply_1_1;
/*      */ 
/*      */ 
/*      */   
/*      */   protected MessageMediator serverRequest_1_1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SocketOrChannelConnectionImpl(ORB paramORB, boolean paramBoolean1, boolean paramBoolean2) {
/*  187 */     this(paramORB);
/*  188 */     setUseSelectThreadToWait(paramBoolean1);
/*  189 */     setUseWorkerThreadForEvent(paramBoolean2);
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
/*      */   public SocketOrChannelConnectionImpl(ORB paramORB, CorbaContactInfo paramCorbaContactInfo, boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2, int paramInt) {
/*  201 */     this(paramORB, paramBoolean1, paramBoolean2);
/*      */     
/*  203 */     this.contactInfo = paramCorbaContactInfo;
/*      */     
/*      */     try {
/*  206 */       this
/*  207 */         .socket = paramORB.getORBData().getSocketFactory().createSocket(paramString1, new InetSocketAddress(paramString2, paramInt));
/*      */       
/*  209 */       this.socketChannel = this.socket.getChannel();
/*      */       
/*  211 */       if (this.socketChannel != null) {
/*  212 */         boolean bool = !paramBoolean1 ? true : false;
/*  213 */         this.socketChannel.configureBlocking(bool);
/*      */       }
/*      */       else {
/*      */         
/*  217 */         setUseSelectThreadToWait(false);
/*      */       } 
/*  219 */       if (paramORB.transportDebugFlag) {
/*  220 */         dprint(".initialize: connection created: " + this.socket);
/*      */       }
/*  222 */     } catch (Throwable throwable) {
/*  223 */       throw this.wrapper.connectFailure(throwable, paramString1, paramString2, 
/*  224 */           Integer.toString(paramInt));
/*      */     } 
/*  226 */     this.state = 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SocketOrChannelConnectionImpl(ORB paramORB, CorbaContactInfo paramCorbaContactInfo, String paramString1, String paramString2, int paramInt) {
/*  236 */     this(paramORB, paramCorbaContactInfo, paramORB
/*  237 */         .getORBData().connectionSocketUseSelectThreadToWait(), paramORB
/*  238 */         .getORBData().connectionSocketUseWorkerThreadForEvent(), paramString1, paramString2, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SocketOrChannelConnectionImpl(ORB paramORB, Acceptor paramAcceptor, Socket paramSocket, boolean paramBoolean1, boolean paramBoolean2) {
/*  249 */     this(paramORB, paramBoolean1, paramBoolean2);
/*      */     
/*  251 */     this.socket = paramSocket;
/*  252 */     this.socketChannel = paramSocket.getChannel();
/*  253 */     if (this.socketChannel != null) {
/*      */       
/*      */       try {
/*  256 */         boolean bool = !paramBoolean1 ? true : false;
/*  257 */         this.socketChannel.configureBlocking(bool);
/*  258 */       } catch (IOException iOException) {
/*  259 */         RuntimeException runtimeException = new RuntimeException();
/*  260 */         runtimeException.initCause(iOException);
/*  261 */         throw runtimeException;
/*      */       } 
/*      */     }
/*  264 */     this.acceptor = paramAcceptor;
/*      */     
/*  266 */     this.serverRequestMap = Collections.synchronizedMap(new HashMap<>());
/*  267 */     this.isServer = true;
/*      */     
/*  269 */     this.state = 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SocketOrChannelConnectionImpl(ORB paramORB, Acceptor paramAcceptor, Socket paramSocket) {
/*  277 */     this(paramORB, paramAcceptor, paramSocket, 
/*  278 */         (paramSocket.getChannel() == null) ? false : paramORB
/*      */         
/*  280 */         .getORBData().connectionSocketUseSelectThreadToWait(), 
/*  281 */         (paramSocket.getChannel() == null) ? false : paramORB
/*      */         
/*  283 */         .getORBData().connectionSocketUseWorkerThreadForEvent());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldRegisterReadEvent() {
/*  293 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldRegisterServerReadEvent() {
/*  298 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean read() {
/*      */     try {
/*  304 */       if (this.orb.transportDebugFlag) {
/*  305 */         dprint(".read->: " + this);
/*      */       }
/*  307 */       CorbaMessageMediator corbaMessageMediator = readBits();
/*  308 */       if (corbaMessageMediator != null)
/*      */       {
/*      */         
/*  311 */         return dispatch(corbaMessageMediator);
/*      */       }
/*  313 */       return true;
/*      */     } finally {
/*  315 */       if (this.orb.transportDebugFlag) {
/*  316 */         dprint(".read<-: " + this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected CorbaMessageMediator readBits() {
/*      */     try {
/*      */       MessageMediator messageMediator;
/*  325 */       if (this.orb.transportDebugFlag) {
/*  326 */         dprint(".readBits->: " + this);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  331 */       if (this.contactInfo != null) {
/*      */         
/*  333 */         messageMediator = this.contactInfo.createMessageMediator((Broker)this.orb, (Connection)this);
/*  334 */       } else if (this.acceptor != null) {
/*  335 */         messageMediator = this.acceptor.createMessageMediator((Broker)this.orb, (Connection)this);
/*      */       } else {
/*  337 */         throw new RuntimeException("SocketOrChannelConnectionImpl.readBits");
/*      */       } 
/*      */       
/*  340 */       return (CorbaMessageMediator)messageMediator;
/*      */     }
/*  342 */     catch (ThreadDeath threadDeath) {
/*  343 */       if (this.orb.transportDebugFlag) {
/*  344 */         dprint(".readBits: " + this + ": ThreadDeath: " + threadDeath, threadDeath);
/*      */       }
/*      */       try {
/*  347 */         purgeCalls((SystemException)this.wrapper.connectionAbort(threadDeath), false, false);
/*  348 */       } catch (Throwable throwable) {
/*  349 */         if (this.orb.transportDebugFlag) {
/*  350 */           dprint(".readBits: " + this + ": purgeCalls: Throwable: " + throwable, throwable);
/*      */         }
/*      */       } 
/*  353 */       throw threadDeath;
/*  354 */     } catch (Throwable throwable) {
/*  355 */       if (this.orb.transportDebugFlag) {
/*  356 */         dprint(".readBits: " + this + ": Throwable: " + throwable, throwable);
/*      */       }
/*      */       
/*      */       try {
/*  360 */         if (throwable instanceof INTERNAL) {
/*  361 */           sendMessageError(GIOPVersion.DEFAULT_VERSION);
/*      */         }
/*  363 */       } catch (IOException iOException) {
/*  364 */         if (this.orb.transportDebugFlag) {
/*  365 */           dprint(".readBits: " + this + ": sendMessageError: IOException: " + iOException, iOException);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  370 */       Selector selector = this.orb.getTransportManager().getSelector(0);
/*  371 */       if (selector != null) {
/*  372 */         selector.unregisterForEvent(this);
/*      */       }
/*      */       
/*  375 */       purgeCalls((SystemException)this.wrapper.connectionAbort(throwable), true, false);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */       
/*  384 */       if (this.orb.transportDebugFlag) {
/*  385 */         dprint(".readBits<-: " + this);
/*      */       }
/*      */     } 
/*  388 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected CorbaMessageMediator finishReadingBits(MessageMediator paramMessageMediator) {
/*      */     try {
/*  395 */       if (this.orb.transportDebugFlag) {
/*  396 */         dprint(".finishReadingBits->: " + this);
/*      */       }
/*      */ 
/*      */       
/*  400 */       if (this.contactInfo != null) {
/*      */         
/*  402 */         paramMessageMediator = this.contactInfo.finishCreatingMessageMediator((Broker)this.orb, (Connection)this, paramMessageMediator);
/*  403 */       } else if (this.acceptor != null) {
/*      */         
/*  405 */         paramMessageMediator = this.acceptor.finishCreatingMessageMediator((Broker)this.orb, (Connection)this, paramMessageMediator);
/*      */       } else {
/*  407 */         throw new RuntimeException("SocketOrChannelConnectionImpl.finishReadingBits");
/*      */       } 
/*      */       
/*  410 */       return (CorbaMessageMediator)paramMessageMediator;
/*      */     }
/*  412 */     catch (ThreadDeath threadDeath) {
/*  413 */       if (this.orb.transportDebugFlag) {
/*  414 */         dprint(".finishReadingBits: " + this + ": ThreadDeath: " + threadDeath, threadDeath);
/*      */       }
/*      */       try {
/*  417 */         purgeCalls((SystemException)this.wrapper.connectionAbort(threadDeath), false, false);
/*  418 */       } catch (Throwable throwable) {
/*  419 */         if (this.orb.transportDebugFlag) {
/*  420 */           dprint(".finishReadingBits: " + this + ": purgeCalls: Throwable: " + throwable, throwable);
/*      */         }
/*      */       } 
/*  423 */       throw threadDeath;
/*  424 */     } catch (Throwable throwable) {
/*  425 */       if (this.orb.transportDebugFlag) {
/*  426 */         dprint(".finishReadingBits: " + this + ": Throwable: " + throwable, throwable);
/*      */       }
/*      */       
/*      */       try {
/*  430 */         if (throwable instanceof INTERNAL) {
/*  431 */           sendMessageError(GIOPVersion.DEFAULT_VERSION);
/*      */         }
/*  433 */       } catch (IOException iOException) {
/*  434 */         if (this.orb.transportDebugFlag) {
/*  435 */           dprint(".finishReadingBits: " + this + ": sendMessageError: IOException: " + iOException, iOException);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  440 */       this.orb.getTransportManager().getSelector(0).unregisterForEvent(this);
/*      */       
/*  442 */       purgeCalls((SystemException)this.wrapper.connectionAbort(throwable), true, false);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */       
/*  451 */       if (this.orb.transportDebugFlag) {
/*  452 */         dprint(".finishReadingBits<-: " + this);
/*      */       }
/*      */     } 
/*  455 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean dispatch(CorbaMessageMediator paramCorbaMessageMediator) {
/*      */     try {
/*  461 */       if (this.orb.transportDebugFlag) {
/*  462 */         dprint(".dispatch->: " + this);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  474 */       boolean bool = paramCorbaMessageMediator.getProtocolHandler().handleRequest((MessageMediator)paramCorbaMessageMediator);
/*      */       
/*  476 */       return bool;
/*      */     }
/*  478 */     catch (ThreadDeath threadDeath) {
/*  479 */       if (this.orb.transportDebugFlag) {
/*  480 */         dprint(".dispatch: ThreadDeath", threadDeath);
/*      */       }
/*      */       try {
/*  483 */         purgeCalls((SystemException)this.wrapper.connectionAbort(threadDeath), false, false);
/*  484 */       } catch (Throwable throwable) {
/*  485 */         if (this.orb.transportDebugFlag) {
/*  486 */           dprint(".dispatch: purgeCalls: Throwable", throwable);
/*      */         }
/*      */       } 
/*  489 */       throw threadDeath;
/*  490 */     } catch (Throwable throwable) {
/*  491 */       if (this.orb.transportDebugFlag) {
/*  492 */         dprint(".dispatch: Throwable", throwable);
/*      */       }
/*      */       
/*      */       try {
/*  496 */         if (throwable instanceof INTERNAL) {
/*  497 */           sendMessageError(GIOPVersion.DEFAULT_VERSION);
/*      */         }
/*  499 */       } catch (IOException iOException) {
/*  500 */         if (this.orb.transportDebugFlag) {
/*  501 */           dprint(".dispatch: sendMessageError: IOException", iOException);
/*      */         }
/*      */       } 
/*  504 */       purgeCalls((SystemException)this.wrapper.connectionAbort(throwable), false, false);
/*      */     }
/*      */     finally {
/*      */       
/*  508 */       if (this.orb.transportDebugFlag) {
/*  509 */         dprint(".dispatch<-: " + this);
/*      */       }
/*      */     } 
/*      */     
/*  513 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldUseDirectByteBuffers() {
/*  518 */     return (getSocketChannel() != null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer read(int paramInt1, int paramInt2, int paramInt3, long paramLong) throws IOException {
/*  524 */     if (shouldUseDirectByteBuffers()) {
/*      */ 
/*      */       
/*  527 */       ByteBuffer byteBuffer1 = this.orb.getByteBufferPool().getByteBuffer(paramInt1);
/*      */       
/*  529 */       if (this.orb.transportDebugFlag) {
/*      */         
/*  531 */         int i = System.identityHashCode(byteBuffer1);
/*  532 */         StringBuffer stringBuffer = new StringBuffer(80);
/*  533 */         stringBuffer.append(".read: got ByteBuffer id (");
/*  534 */         stringBuffer.append(i).append(") from ByteBufferPool.");
/*  535 */         String str = stringBuffer.toString();
/*  536 */         dprint(str);
/*      */       } 
/*      */       
/*  539 */       byteBuffer1.position(paramInt2);
/*  540 */       byteBuffer1.limit(paramInt1);
/*      */       
/*  542 */       readFully(byteBuffer1, paramInt3, paramLong);
/*      */       
/*  544 */       return byteBuffer1;
/*      */     } 
/*      */     
/*  547 */     byte[] arrayOfByte = new byte[paramInt1];
/*  548 */     readFully(getSocket().getInputStream(), arrayOfByte, paramInt2, paramInt3, paramLong);
/*      */     
/*  550 */     ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
/*  551 */     byteBuffer.limit(paramInt1);
/*  552 */     return byteBuffer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer read(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, long paramLong) throws IOException {
/*  559 */     int i = paramInt1 + paramInt2;
/*  560 */     if (shouldUseDirectByteBuffers()) {
/*      */       
/*  562 */       if (!paramByteBuffer.isDirect()) {
/*  563 */         throw this.wrapper.unexpectedNonDirectByteBufferWithChannelSocket();
/*      */       }
/*  565 */       if (i > paramByteBuffer.capacity()) {
/*  566 */         if (this.orb.transportDebugFlag) {
/*      */           
/*  568 */           int j = System.identityHashCode(paramByteBuffer);
/*  569 */           StringBuffer stringBuffer = new StringBuffer(80);
/*  570 */           stringBuffer.append(".read: releasing ByteBuffer id (")
/*  571 */             .append(j).append(") to ByteBufferPool.");
/*  572 */           String str = stringBuffer.toString();
/*  573 */           dprint(str);
/*      */         } 
/*  575 */         this.orb.getByteBufferPool().releaseByteBuffer(paramByteBuffer);
/*  576 */         paramByteBuffer = this.orb.getByteBufferPool().getByteBuffer(i);
/*      */       } 
/*  578 */       paramByteBuffer.position(paramInt1);
/*  579 */       paramByteBuffer.limit(i);
/*  580 */       readFully(paramByteBuffer, paramInt2, paramLong);
/*  581 */       paramByteBuffer.position(0);
/*  582 */       paramByteBuffer.limit(i);
/*  583 */       return paramByteBuffer;
/*      */     } 
/*  585 */     if (paramByteBuffer.isDirect()) {
/*  586 */       throw this.wrapper.unexpectedDirectByteBufferWithNonChannelSocket();
/*      */     }
/*  588 */     byte[] arrayOfByte = new byte[i];
/*  589 */     readFully(getSocket().getInputStream(), arrayOfByte, paramInt1, paramInt2, paramLong);
/*      */     
/*  591 */     return ByteBuffer.wrap(arrayOfByte);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void readFully(ByteBuffer paramByteBuffer, int paramInt, long paramLong) throws IOException {
/*  597 */     int i = 0;
/*  598 */     int j = 0;
/*  599 */     long l1 = this.readTimeouts.get_initial_time_to_wait();
/*  600 */     long l2 = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     do {
/*  620 */       j = getSocketChannel().read(paramByteBuffer);
/*      */       
/*  622 */       if (j < 0) {
/*  623 */         throw new IOException("End-of-stream");
/*      */       }
/*  625 */       if (j == 0) {
/*      */         try {
/*  627 */           Thread.sleep(l1);
/*  628 */           l2 += l1;
/*      */           
/*  630 */           l1 = (long)(l1 * this.readTimeouts.get_backoff_factor());
/*      */         }
/*  632 */         catch (InterruptedException interruptedException) {
/*      */           
/*  634 */           if (this.orb.transportDebugFlag) {
/*  635 */             dprint("readFully(): unexpected exception " + interruptedException
/*  636 */                 .toString());
/*      */           }
/*      */         } 
/*      */       } else {
/*      */         
/*  641 */         i += j;
/*      */       }
/*      */     
/*  644 */     } while (i < paramInt && l2 < paramLong);
/*      */     
/*  646 */     if (i < paramInt && l2 >= paramLong)
/*      */     {
/*      */       
/*  649 */       throw this.wrapper.transportReadTimeoutExceeded(new Integer(paramInt), new Integer(i), new Long(paramLong), new Long(l2));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  654 */     getConnectionCache().stampTime((Connection)this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readFully(InputStream paramInputStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, long paramLong) throws IOException {
/*  662 */     int i = 0;
/*  663 */     int j = 0;
/*  664 */     long l1 = this.readTimeouts.get_initial_time_to_wait();
/*  665 */     long l2 = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     do {
/*  685 */       j = paramInputStream.read(paramArrayOfbyte, paramInt1 + i, paramInt2 - i);
/*  686 */       if (j < 0) {
/*  687 */         throw new IOException("End-of-stream");
/*      */       }
/*  689 */       if (j == 0) {
/*      */         try {
/*  691 */           Thread.sleep(l1);
/*  692 */           l2 += l1;
/*      */           
/*  694 */           l1 = (long)(l1 * this.readTimeouts.get_backoff_factor());
/*      */         }
/*  696 */         catch (InterruptedException interruptedException) {
/*      */           
/*  698 */           if (this.orb.transportDebugFlag) {
/*  699 */             dprint("readFully(): unexpected exception " + interruptedException
/*  700 */                 .toString());
/*      */           }
/*      */         } 
/*      */       } else {
/*      */         
/*  705 */         i += j;
/*      */       }
/*      */     
/*  708 */     } while (i < paramInt2 && l2 < paramLong);
/*      */     
/*  710 */     if (i < paramInt2 && l2 >= paramLong)
/*      */     {
/*      */       
/*  713 */       throw this.wrapper.transportReadTimeoutExceeded(new Integer(paramInt2), new Integer(i), new Long(paramLong), new Long(l2));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  718 */     getConnectionCache().stampTime((Connection)this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(ByteBuffer paramByteBuffer) throws IOException {
/*  724 */     if (shouldUseDirectByteBuffers()) {
/*      */       
/*      */       do
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  736 */         getSocketChannel().write(paramByteBuffer);
/*      */       }
/*  738 */       while (paramByteBuffer.hasRemaining());
/*      */     } else {
/*      */       
/*  741 */       if (!paramByteBuffer.hasArray()) {
/*  742 */         throw this.wrapper.unexpectedDirectByteBufferWithNonChannelSocket();
/*      */       }
/*  744 */       byte[] arrayOfByte = paramByteBuffer.array();
/*  745 */       getSocket().getOutputStream().write(arrayOfByte, 0, paramByteBuffer.limit());
/*  746 */       getSocket().getOutputStream().flush();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  752 */     getConnectionCache().stampTime((Connection)this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void close() {
/*      */     try {
/*  761 */       if (this.orb.transportDebugFlag) {
/*  762 */         dprint(".close->: " + this);
/*      */       }
/*  764 */       writeLock();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  772 */       if (isBusy()) {
/*  773 */         writeUnlock();
/*  774 */         if (this.orb.transportDebugFlag) {
/*  775 */           dprint(".close: isBusy so no close: " + this);
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*      */       try {
/*      */         try {
/*  782 */           sendCloseConnection(GIOPVersion.V1_0);
/*  783 */         } catch (Throwable throwable) {
/*  784 */           this.wrapper.exceptionWhenSendingCloseConnection(throwable);
/*      */         } 
/*      */         
/*  787 */         synchronized (this.stateEvent) {
/*  788 */           this.state = 3;
/*  789 */           this.stateEvent.notifyAll();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  798 */         purgeCalls((SystemException)this.wrapper.connectionRebind(), false, true);
/*      */       }
/*  800 */       catch (Exception exception) {
/*  801 */         if (this.orb.transportDebugFlag) {
/*  802 */           dprint(".close: exception: " + this, exception);
/*      */         }
/*      */       } 
/*      */       try {
/*  806 */         Selector selector = this.orb.getTransportManager().getSelector(0);
/*  807 */         if (selector != null) {
/*  808 */           selector.unregisterForEvent(this);
/*      */         }
/*  810 */         if (this.socketChannel != null) {
/*  811 */           this.socketChannel.close();
/*      */         }
/*  813 */         this.socket.close();
/*  814 */       } catch (IOException iOException) {
/*  815 */         if (this.orb.transportDebugFlag) {
/*  816 */           dprint(".close: " + this, iOException);
/*      */         }
/*      */       } 
/*  819 */       closeConnectionResources();
/*      */     } finally {
/*  821 */       if (this.orb.transportDebugFlag) {
/*  822 */         dprint(".close<-: " + this);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void closeConnectionResources() {
/*  828 */     if (this.orb.transportDebugFlag) {
/*  829 */       dprint(".closeConnectionResources->: " + this);
/*      */     }
/*  831 */     Selector selector = this.orb.getTransportManager().getSelector(0);
/*  832 */     if (selector != null) {
/*  833 */       selector.unregisterForEvent(this);
/*      */     }
/*      */     try {
/*  836 */       if (this.socketChannel != null)
/*  837 */         this.socketChannel.close(); 
/*  838 */       if (this.socket != null && !this.socket.isClosed())
/*  839 */         this.socket.close(); 
/*  840 */     } catch (IOException iOException) {
/*  841 */       if (this.orb.transportDebugFlag) {
/*  842 */         dprint(".closeConnectionResources: " + this, iOException);
/*      */       }
/*      */     } 
/*  845 */     if (this.orb.transportDebugFlag) {
/*  846 */       dprint(".closeConnectionResources<-: " + this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Acceptor getAcceptor() {
/*  853 */     return this.acceptor;
/*      */   }
/*      */ 
/*      */   
/*      */   public ContactInfo getContactInfo() {
/*  858 */     return (ContactInfo)this.contactInfo;
/*      */   }
/*      */ 
/*      */   
/*      */   public EventHandler getEventHandler() {
/*  863 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public OutputObject createOutputObject(MessageMediator paramMessageMediator) {
/*  869 */     throw new RuntimeException("*****SocketOrChannelConnectionImpl.createOutputObject - should not be called.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServer() {
/*  878 */     return this.isServer;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBusy() {
/*  883 */     if (this.serverRequestCount > 0 || 
/*  884 */       getResponseWaitingRoom().numberRegistered() > 0)
/*      */     {
/*  886 */       return true;
/*      */     }
/*  888 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getTimeStamp() {
/*  894 */     return this.timeStamp;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTimeStamp(long paramLong) {
/*  899 */     this.timeStamp = paramLong;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setState(String paramString) {
/*  904 */     synchronized (this.stateEvent) {
/*  905 */       if (paramString.equals("ESTABLISHED")) {
/*  906 */         this.state = 2;
/*  907 */         this.stateEvent.notifyAll();
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
/*      */   public void writeLock() {
/*      */     try {
/*  924 */       if (dprintWriteLocks && this.orb.transportDebugFlag) {
/*  925 */         dprint(".writeLock->: " + this);
/*      */       }
/*      */       
/*      */       while (true) {
/*  929 */         int i = this.state;
/*  930 */         switch (i) {
/*      */           
/*      */           case 1:
/*  933 */             synchronized (this.stateEvent) {
/*  934 */               if (this.state != 1) {
/*      */                 continue;
/*      */               }
/*      */               
/*      */               try {
/*  939 */                 this.stateEvent.wait();
/*  940 */               } catch (InterruptedException interruptedException) {
/*  941 */                 if (this.orb.transportDebugFlag) {
/*  942 */                   dprint(".writeLock: OPENING InterruptedException: " + this);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */             continue;
/*      */ 
/*      */           
/*      */           case 2:
/*  950 */             synchronized (this.writeEvent) {
/*  951 */               if (!this.writeLocked) {
/*  952 */                 this.writeLocked = true;
/*      */ 
/*      */                 
/*      */                 return;
/*      */               } 
/*      */               
/*      */               try {
/*  959 */                 while (this.state == 2 && this.writeLocked) {
/*  960 */                   this.writeEvent.wait(100L);
/*      */                 }
/*  962 */               } catch (InterruptedException interruptedException) {
/*  963 */                 if (this.orb.transportDebugFlag) {
/*  964 */                   dprint(".writeLock: ESTABLISHED InterruptedException: " + this);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */             continue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 5:
/*  977 */             synchronized (this.stateEvent) {
/*  978 */               if (this.state != 5) {
/*      */                 continue;
/*      */               }
/*  981 */               throw this.wrapper.writeErrorSend();
/*      */             } 
/*      */ 
/*      */ 
/*      */           
/*      */           case 4:
/*  987 */             synchronized (this.stateEvent) {
/*  988 */               if (this.state != 4) {
/*      */                 continue;
/*      */               }
/*  991 */               throw this.wrapper.connectionCloseRebind();
/*      */             } 
/*      */         }  break;
/*      */       } 
/*  995 */       if (this.orb.transportDebugFlag) {
/*  996 */         dprint(".writeLock: default: " + this);
/*      */       }
/*      */       
/*  999 */       throw new RuntimeException(".writeLock: bad state");
/*      */     }
/*      */     finally {
/*      */       
/* 1003 */       if (dprintWriteLocks && this.orb.transportDebugFlag) {
/* 1004 */         dprint(".writeLock<-: " + this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeUnlock() {
/*      */     try {
/* 1012 */       if (dprintWriteLocks && this.orb.transportDebugFlag) {
/* 1013 */         dprint(".writeUnlock->: " + this);
/*      */       }
/* 1015 */       synchronized (this.writeEvent) {
/* 1016 */         this.writeLocked = false;
/* 1017 */         this.writeEvent.notify();
/*      */       } 
/*      */     } finally {
/* 1020 */       if (dprintWriteLocks && this.orb.transportDebugFlag) {
/* 1021 */         dprint(".writeUnlock<-: " + this);
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
/*      */   public void sendWithoutLock(OutputObject paramOutputObject) {
/*      */     try {
/* 1039 */       CDROutputObject cDROutputObject = (CDROutputObject)paramOutputObject;
/* 1040 */       cDROutputObject.writeTo(this);
/*      */ 
/*      */     
/*      */     }
/* 1044 */     catch (IOException iOException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1090 */       COMM_FAILURE cOMM_FAILURE = this.wrapper.writeErrorSend(iOException);
/* 1091 */       purgeCalls((SystemException)cOMM_FAILURE, false, true);
/* 1092 */       throw cOMM_FAILURE;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void registerWaiter(MessageMediator paramMessageMediator) {
/* 1098 */     this.responseWaitingRoom.registerWaiter(paramMessageMediator);
/*      */   }
/*      */ 
/*      */   
/*      */   public void unregisterWaiter(MessageMediator paramMessageMediator) {
/* 1103 */     this.responseWaitingRoom.unregisterWaiter(paramMessageMediator);
/*      */   }
/*      */ 
/*      */   
/*      */   public InputObject waitForResponse(MessageMediator paramMessageMediator) {
/* 1108 */     return this.responseWaitingRoom.waitForResponse(paramMessageMediator);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setConnectionCache(ConnectionCache paramConnectionCache) {
/* 1113 */     this.connectionCache = paramConnectionCache;
/*      */   }
/*      */ 
/*      */   
/*      */   public ConnectionCache getConnectionCache() {
/* 1118 */     return this.connectionCache;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseSelectThreadToWait(boolean paramBoolean) {
/* 1128 */     this.useSelectThreadToWait = paramBoolean;
/*      */ 
/*      */ 
/*      */     
/* 1132 */     setReadGiopHeaderOnly(shouldUseSelectThreadToWait());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEvent() {
/* 1137 */     if (this.orb.transportDebugFlag) {
/* 1138 */       dprint(".handleEvent->: " + this);
/*      */     }
/* 1140 */     getSelectionKey().interestOps(getSelectionKey().interestOps() & (
/* 1141 */         getInterestOps() ^ 0xFFFFFFFF));
/*      */     
/* 1143 */     if (shouldUseWorkerThreadForEvent()) {
/* 1144 */       NoSuchWorkQueueException noSuchWorkQueueException; NoSuchThreadPoolException noSuchThreadPoolException = null;
/*      */       try {
/* 1146 */         int i = 0;
/* 1147 */         if (shouldReadGiopHeaderOnly()) {
/* 1148 */           this.partialMessageMediator = readBits();
/*      */           
/* 1150 */           i = this.partialMessageMediator.getThreadPoolToUse();
/*      */         } 
/*      */         
/* 1153 */         if (this.orb.transportDebugFlag) {
/* 1154 */           dprint(".handleEvent: addWork to pool: " + i);
/*      */         }
/* 1156 */         this.orb.getThreadPoolManager().getThreadPool(i)
/* 1157 */           .getWorkQueue(0).addWork(getWork());
/* 1158 */       } catch (NoSuchThreadPoolException noSuchThreadPoolException1) {
/* 1159 */         noSuchThreadPoolException = noSuchThreadPoolException1;
/* 1160 */       } catch (NoSuchWorkQueueException noSuchWorkQueueException1) {
/* 1161 */         noSuchWorkQueueException = noSuchWorkQueueException1;
/*      */       } 
/*      */       
/* 1164 */       if (noSuchWorkQueueException != null) {
/* 1165 */         if (this.orb.transportDebugFlag) {
/* 1166 */           dprint(".handleEvent: " + noSuchWorkQueueException);
/*      */         }
/* 1168 */         INTERNAL iNTERNAL = new INTERNAL("NoSuchThreadPoolException");
/* 1169 */         iNTERNAL.initCause((Throwable)noSuchWorkQueueException);
/* 1170 */         throw iNTERNAL;
/*      */       } 
/*      */     } else {
/* 1173 */       if (this.orb.transportDebugFlag) {
/* 1174 */         dprint(".handleEvent: doWork");
/*      */       }
/* 1176 */       getWork().doWork();
/*      */     } 
/* 1178 */     if (this.orb.transportDebugFlag) {
/* 1179 */       dprint(".handleEvent<-: " + this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public SelectableChannel getChannel() {
/* 1185 */     return this.socketChannel;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInterestOps() {
/* 1190 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Connection getConnection() {
/* 1197 */     return (Connection)this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 1207 */     return toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public void doWork() {
/*      */     try {
/* 1213 */       if (this.orb.transportDebugFlag) {
/* 1214 */         dprint(".doWork->: " + this);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1222 */       if (!shouldReadGiopHeaderOnly()) {
/* 1223 */         read();
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1229 */         CorbaMessageMediator corbaMessageMediator = getPartialMessageMediator();
/*      */ 
/*      */         
/* 1232 */         corbaMessageMediator = finishReadingBits((MessageMediator)corbaMessageMediator);
/*      */         
/* 1234 */         if (corbaMessageMediator != null)
/*      */         {
/*      */           
/* 1237 */           dispatch(corbaMessageMediator);
/*      */         }
/*      */       } 
/* 1240 */     } catch (Throwable throwable) {
/* 1241 */       if (this.orb.transportDebugFlag) {
/* 1242 */         dprint(".doWork: ignoring Throwable: " + throwable + " " + this);
/*      */       }
/*      */     }
/*      */     finally {
/*      */       
/* 1247 */       if (this.orb.transportDebugFlag) {
/* 1248 */         dprint(".doWork<-: " + this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEnqueueTime(long paramLong) {
/* 1255 */     this.enqueueTime = paramLong;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getEnqueueTime() {
/* 1260 */     return this.enqueueTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldReadGiopHeaderOnly() {
/* 1270 */     return this.shouldReadGiopHeaderOnly;
/*      */   }
/*      */   
/*      */   protected void setReadGiopHeaderOnly(boolean paramBoolean) {
/* 1274 */     this.shouldReadGiopHeaderOnly = paramBoolean;
/*      */   }
/*      */ 
/*      */   
/*      */   public ResponseWaitingRoom getResponseWaitingRoom() {
/* 1279 */     return (ResponseWaitingRoom)this.responseWaitingRoom;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serverRequestMapPut(int paramInt, CorbaMessageMediator paramCorbaMessageMediator) {
/* 1288 */     this.serverRequestMap.put(new Integer(paramInt), paramCorbaMessageMediator);
/*      */   }
/*      */ 
/*      */   
/*      */   public CorbaMessageMediator serverRequestMapGet(int paramInt) {
/* 1293 */     return (CorbaMessageMediator)this.serverRequestMap
/* 1294 */       .get(new Integer(paramInt));
/*      */   }
/*      */ 
/*      */   
/*      */   public void serverRequestMapRemove(int paramInt) {
/* 1299 */     this.serverRequestMap.remove(new Integer(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket getSocket() {
/* 1307 */     return this.socket;
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
/*      */   public synchronized void serverRequestProcessingBegins() {
/* 1320 */     this.serverRequestCount++;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void serverRequestProcessingEnds() {
/* 1325 */     this.serverRequestCount--;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int getNextRequestId() {
/* 1334 */     return this.requestId++;
/*      */   }
/*      */   
/*      */   protected SocketOrChannelConnectionImpl(ORB paramORB) {
/* 1338 */     this.codeSetContext = null; this.orb = paramORB;
/*      */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.transport");
/*      */     setWork(this);
/*      */     this.responseWaitingRoom = new CorbaResponseWaitingRoomImpl(paramORB, this);
/* 1342 */     setReadTimeouts(paramORB.getORBData().getTransportTCPReadTimeouts()); } public ORB getBroker() { return this.orb; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CodeSetComponentInfo.CodeSetContext getCodeSetContext() {
/* 1354 */     if (this.codeSetContext == null) {
/* 1355 */       synchronized (this) {
/* 1356 */         return this.codeSetContext;
/*      */       } 
/*      */     }
/*      */     
/* 1360 */     return this.codeSetContext;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void setCodeSetContext(CodeSetComponentInfo.CodeSetContext paramCodeSetContext) {
/* 1365 */     if (this.codeSetContext == null) {
/*      */       
/* 1367 */       if (OSFCodeSetRegistry.lookupEntry(paramCodeSetContext.getCharCodeSet()) == null || 
/* 1368 */         OSFCodeSetRegistry.lookupEntry(paramCodeSetContext.getWCharCodeSet()) == null)
/*      */       {
/*      */ 
/*      */         
/* 1372 */         throw this.wrapper.badCodesetsFromClient();
/*      */       }
/*      */       
/* 1375 */       this.codeSetContext = paramCodeSetContext;
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
/*      */   public MessageMediator clientRequestMapGet(int paramInt) {
/* 1392 */     return this.responseWaitingRoom.getMessageMediator(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clientReply_1_1_Put(MessageMediator paramMessageMediator) {
/* 1399 */     this.clientReply_1_1 = paramMessageMediator;
/*      */   }
/*      */ 
/*      */   
/*      */   public MessageMediator clientReply_1_1_Get() {
/* 1404 */     return this.clientReply_1_1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clientReply_1_1_Remove() {
/* 1409 */     this.clientReply_1_1 = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serverRequest_1_1_Put(MessageMediator paramMessageMediator) {
/* 1416 */     this.serverRequest_1_1 = paramMessageMediator;
/*      */   }
/*      */ 
/*      */   
/*      */   public MessageMediator serverRequest_1_1_Get() {
/* 1421 */     return this.serverRequest_1_1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void serverRequest_1_1_Remove() {
/* 1426 */     this.serverRequest_1_1 = null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getStateString(int paramInt) {
/* 1431 */     synchronized (this.stateEvent) {
/* 1432 */       switch (paramInt) { case 1:
/* 1433 */           return "OPENING";
/* 1434 */         case 2: return "ESTABLISHED";
/* 1435 */         case 3: return "CLOSE_SENT";
/* 1436 */         case 4: return "CLOSE_RECVD";
/* 1437 */         case 5: return "ABORT"; }
/* 1438 */        return "???";
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized boolean isPostInitialContexts() {
/* 1444 */     return this.postInitialContexts;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void setPostInitialContexts() {
/* 1449 */     this.postInitialContexts = true;
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
/*      */ 
/*      */   
/*      */   public void purgeCalls(SystemException paramSystemException, boolean paramBoolean1, boolean paramBoolean2) {
/* 1468 */     int i = paramSystemException.minor;
/*      */     
/*      */     try {
/* 1471 */       if (this.orb.transportDebugFlag) {
/* 1472 */         dprint(".purgeCalls->: " + i + "/" + paramBoolean1 + "/" + paramBoolean2 + " " + this);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1480 */       synchronized (this.stateEvent) {
/* 1481 */         if (this.state == 5 || this.state == 4) {
/* 1482 */           if (this.orb.transportDebugFlag) {
/* 1483 */             dprint(".purgeCalls: exiting since state is: " + 
/* 1484 */                 getStateString(this.state) + " " + this);
/*      */           }
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       
/*      */       try {
/* 1493 */         if (!paramBoolean2) {
/* 1494 */           writeLock();
/*      */         }
/* 1496 */       } catch (SystemException systemException) {
/* 1497 */         if (this.orb.transportDebugFlag) {
/* 1498 */           dprint(".purgeCalls: SystemException" + systemException + "; continuing " + this);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1505 */       synchronized (this.stateEvent) {
/* 1506 */         if (i == 1398079697) {
/* 1507 */           this.state = 4;
/* 1508 */           paramSystemException.completed = CompletionStatus.COMPLETED_NO;
/*      */         } else {
/* 1510 */           this.state = 5;
/* 1511 */           paramSystemException.completed = CompletionStatus.COMPLETED_MAYBE;
/*      */         } 
/* 1513 */         this.stateEvent.notifyAll();
/*      */       } 
/*      */       
/*      */       try {
/* 1517 */         this.socket.getInputStream().close();
/* 1518 */         this.socket.getOutputStream().close();
/* 1519 */         this.socket.close();
/* 1520 */       } catch (Exception exception) {
/* 1521 */         if (this.orb.transportDebugFlag) {
/* 1522 */           dprint(".purgeCalls: Exception closing socket: " + exception + " " + this);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1530 */       this.responseWaitingRoom.signalExceptionToAllWaiters(paramSystemException);
/*      */     } finally {
/* 1532 */       if (this.contactInfo != null) {
/* 1533 */         ((OutboundConnectionCache)getConnectionCache()).remove((ContactInfo)this.contactInfo);
/* 1534 */       } else if (this.acceptor != null) {
/* 1535 */         ((InboundConnectionCache)getConnectionCache()).remove((Connection)this);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1550 */       writeUnlock();
/*      */       
/* 1552 */       if (this.orb.transportDebugFlag) {
/* 1553 */         dprint(".purgeCalls<-: " + i + "/" + paramBoolean1 + "/" + paramBoolean2 + " " + this);
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
/*      */   public void sendCloseConnection(GIOPVersion paramGIOPVersion) throws IOException {
/* 1568 */     Message message = MessageBase.createCloseConnection(paramGIOPVersion);
/* 1569 */     sendHelper(paramGIOPVersion, message);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendMessageError(GIOPVersion paramGIOPVersion) throws IOException {
/* 1575 */     Message message = MessageBase.createMessageError(paramGIOPVersion);
/* 1576 */     sendHelper(paramGIOPVersion, message);
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
/*      */   public void sendCancelRequest(GIOPVersion paramGIOPVersion, int paramInt) throws IOException {
/* 1588 */     CancelRequestMessage cancelRequestMessage = MessageBase.createCancelRequest(paramGIOPVersion, paramInt);
/* 1589 */     sendHelper(paramGIOPVersion, (Message)cancelRequestMessage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void sendHelper(GIOPVersion paramGIOPVersion, Message paramMessage) throws IOException {
/* 1597 */     CDROutputObject cDROutputObject = OutputStreamFactory.newCDROutputObject(this.orb, null, paramGIOPVersion, this, paramMessage, (byte)1);
/*      */     
/* 1599 */     paramMessage.write((OutputStream)cDROutputObject);
/*      */     
/* 1601 */     cDROutputObject.writeTo(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendCancelRequestWithLock(GIOPVersion paramGIOPVersion, int paramInt) throws IOException {
/* 1608 */     writeLock();
/*      */     try {
/* 1610 */       sendCancelRequest(paramGIOPVersion, paramInt);
/*      */     } finally {
/* 1612 */       writeUnlock();
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
/*      */   
/*      */   public final void setCodeBaseIOR(IOR paramIOR) {
/* 1631 */     this.codeBaseServerIOR = paramIOR;
/*      */   }
/*      */   
/*      */   public final IOR getCodeBaseIOR() {
/* 1635 */     return this.codeBaseServerIOR;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final CodeBase getCodeBase() {
/* 1641 */     return (CodeBase)this.cachedCodeBase;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setReadTimeouts(ReadTimeouts paramReadTimeouts) {
/* 1648 */     this.readTimeouts = paramReadTimeouts;
/*      */   }
/*      */   
/*      */   protected void setPartialMessageMediator(CorbaMessageMediator paramCorbaMessageMediator) {
/* 1652 */     this.partialMessageMediator = paramCorbaMessageMediator;
/*      */   }
/*      */   
/*      */   protected CorbaMessageMediator getPartialMessageMediator() {
/* 1656 */     return this.partialMessageMediator;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1661 */     synchronized (this.stateEvent) {
/* 1662 */       return "SocketOrChannelConnectionImpl[ " + ((this.socketChannel == null) ? this.socket
/*      */ 
/*      */         
/* 1665 */         .toString() : this.socketChannel.toString()) + " " + 
/* 1666 */         getStateString(this.state) + " " + 
/* 1667 */         shouldUseSelectThreadToWait() + " " + 
/* 1668 */         shouldUseWorkerThreadForEvent() + " " + 
/* 1669 */         shouldReadGiopHeaderOnly() + "]";
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dprint(String paramString) {
/* 1677 */     ORBUtility.dprint("SocketOrChannelConnectionImpl", paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void dprint(String paramString, Throwable paramThrowable) {
/* 1682 */     dprint(paramString);
/* 1683 */     paramThrowable.printStackTrace(System.out);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/SocketOrChannelConnectionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */