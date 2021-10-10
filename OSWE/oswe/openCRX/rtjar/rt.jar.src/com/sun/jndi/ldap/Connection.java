/*      */ package com.sun.jndi.ldap;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InterruptedIOException;
/*      */ import java.io.OutputStream;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.net.Socket;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Arrays;
/*      */ import javax.naming.CommunicationException;
/*      */ import javax.naming.InterruptedNamingException;
/*      */ import javax.naming.NamingException;
/*      */ import javax.naming.ldap.Control;
/*      */ import javax.net.ssl.SSLParameters;
/*      */ import javax.net.ssl.SSLSocket;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Connection
/*      */   implements Runnable
/*      */ {
/*      */   private static final boolean debug = false;
/*      */   private static final int dump = 0;
/*      */   private final Thread worker;
/*      */   private boolean v3 = true;
/*      */   public final String host;
/*      */   public final int port;
/*      */   private boolean bound = false;
/*  127 */   private OutputStream traceFile = null;
/*  128 */   private String traceTagIn = null;
/*  129 */   private String traceTagOut = null;
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream inStream;
/*      */ 
/*      */ 
/*      */   
/*      */   public OutputStream outStream;
/*      */ 
/*      */ 
/*      */   
/*      */   public Socket sock;
/*      */ 
/*      */ 
/*      */   
/*      */   private final LdapClient parent;
/*      */ 
/*      */   
/*  148 */   private int outMsgId = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  154 */   private LdapRequest pendingRequests = null;
/*      */   
/*  156 */   volatile IOException closureReason = null;
/*      */   
/*      */   volatile boolean useable = true;
/*      */   
/*      */   int readTimeout;
/*      */   int connectTimeout;
/*  162 */   private static final boolean IS_HOSTNAME_VERIFICATION_DISABLED = hostnameVerificationDisabledValue(); private final Object pauseLock; private boolean paused;
/*      */   
/*      */   private static boolean hostnameVerificationDisabledValue() {
/*  165 */     PrivilegedAction<String> privilegedAction = () -> System.getProperty("com.sun.jndi.ldap.object.disableEndpointIdentification");
/*      */     
/*  167 */     String str = AccessController.<String>doPrivileged(privilegedAction);
/*  168 */     if (str == null) {
/*  169 */       return false;
/*      */     }
/*  171 */     return str.isEmpty() ? true : Boolean.parseBoolean(str);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void setV3(boolean paramBoolean) {
/*  177 */     this.v3 = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setBound() {
/*  185 */     this.bound = true;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object createInetSocketAddress(String paramString, int paramInt) throws NoSuchMethodException {
/*      */     try {
/*  255 */       Class<?> clazz = Class.forName("java.net.InetSocketAddress");
/*      */ 
/*      */       
/*  258 */       Constructor<?> constructor = clazz.getConstructor(new Class[] { String.class, int.class });
/*      */ 
/*      */       
/*  261 */       return constructor.newInstance(new Object[] { paramString, new Integer(paramInt) });
/*      */     
/*      */     }
/*  264 */     catch (ClassNotFoundException|InstantiationException|InvocationTargetException|IllegalAccessException classNotFoundException) {
/*      */ 
/*      */ 
/*      */       
/*  268 */       throw new NoSuchMethodException();
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
/*      */   private Socket createSocket(String paramString1, int paramInt1, String paramString2, int paramInt2) throws Exception {
/*  285 */     Socket socket = null;
/*      */     
/*  287 */     if (paramString2 != null) {
/*      */ 
/*      */ 
/*      */       
/*  291 */       Class<?> clazz = Obj.helper.loadClass(paramString2);
/*      */       
/*  293 */       Method method1 = clazz.getMethod("getDefault", new Class[0]);
/*  294 */       Object object = method1.invoke(null, new Object[0]);
/*      */ 
/*      */ 
/*      */       
/*  298 */       Method method2 = null;
/*      */       
/*  300 */       if (paramInt2 > 0) {
/*      */         
/*      */         try {
/*  303 */           method2 = clazz.getMethod("createSocket", new Class[0]);
/*      */ 
/*      */           
/*  306 */           Method method = Socket.class.getMethod("connect", new Class[] {
/*  307 */                 Class.forName("java.net.SocketAddress"), int.class
/*      */               });
/*  309 */           Object object1 = createInetSocketAddress(paramString1, paramInt1);
/*      */ 
/*      */ 
/*      */           
/*  313 */           socket = (Socket)method2.invoke(object, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  321 */           method.invoke(socket, new Object[] { object1, new Integer(paramInt2) });
/*      */         
/*      */         }
/*  324 */         catch (NoSuchMethodException noSuchMethodException) {}
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  329 */       if (socket == null) {
/*  330 */         method2 = clazz.getMethod("createSocket", new Class[] { String.class, int.class });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  338 */         socket = (Socket)method2.invoke(object, new Object[] { paramString1, new Integer(paramInt1) });
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  343 */       if (paramInt2 > 0) {
/*      */         
/*      */         try {
/*      */           
/*  347 */           Constructor<Socket> constructor = Socket.class.getConstructor(new Class[0]);
/*      */           
/*  349 */           Method method = Socket.class.getMethod("connect", new Class[] {
/*  350 */                 Class.forName("java.net.SocketAddress"), int.class
/*      */               });
/*  352 */           Object object = createInetSocketAddress(paramString1, paramInt1);
/*      */           
/*  354 */           socket = constructor.newInstance(new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  360 */           method.invoke(socket, new Object[] { object, new Integer(paramInt2) });
/*      */         
/*      */         }
/*  363 */         catch (NoSuchMethodException noSuchMethodException) {}
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  368 */       if (socket == null)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  373 */         socket = new Socket(paramString1, paramInt1);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  381 */     if (socket instanceof SSLSocket) {
/*  382 */       SSLSocket sSLSocket = (SSLSocket)socket;
/*  383 */       if (!IS_HOSTNAME_VERIFICATION_DISABLED) {
/*  384 */         SSLParameters sSLParameters = sSLSocket.getSSLParameters();
/*  385 */         sSLParameters.setEndpointIdentificationAlgorithm("LDAPS");
/*  386 */         sSLSocket.setSSLParameters(sSLParameters);
/*      */       } 
/*  388 */       if (paramInt2 > 0) {
/*  389 */         int i = sSLSocket.getSoTimeout();
/*  390 */         sSLSocket.setSoTimeout(paramInt2);
/*  391 */         sSLSocket.startHandshake();
/*  392 */         sSLSocket.setSoTimeout(i);
/*      */       } 
/*      */     } 
/*  395 */     return socket;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized int getMsgId() {
/*  405 */     return ++this.outMsgId;
/*      */   }
/*      */   
/*      */   LdapRequest writeRequest(BerEncoder paramBerEncoder, int paramInt) throws IOException {
/*  409 */     return writeRequest(paramBerEncoder, paramInt, false, -1);
/*      */   }
/*      */ 
/*      */   
/*      */   LdapRequest writeRequest(BerEncoder paramBerEncoder, int paramInt, boolean paramBoolean) throws IOException {
/*  414 */     return writeRequest(paramBerEncoder, paramInt, paramBoolean, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   LdapRequest writeRequest(BerEncoder paramBerEncoder, int paramInt1, boolean paramBoolean, int paramInt2) throws IOException {
/*  420 */     LdapRequest ldapRequest = new LdapRequest(paramInt1, paramBoolean, paramInt2);
/*      */     
/*  422 */     addRequest(ldapRequest);
/*      */     
/*  424 */     if (this.traceFile != null) {
/*  425 */       Ber.dumpBER(this.traceFile, this.traceTagOut, paramBerEncoder.getBuf(), 0, paramBerEncoder.getDataLen());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  432 */     unpauseReader();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  439 */       synchronized (this) {
/*  440 */         this.outStream.write(paramBerEncoder.getBuf(), 0, paramBerEncoder.getDataLen());
/*  441 */         this.outStream.flush();
/*      */       } 
/*  443 */     } catch (IOException iOException) {
/*  444 */       cleanup(null, true);
/*  445 */       throw this.closureReason = iOException;
/*      */     } 
/*      */     
/*  448 */     return ldapRequest;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   BerDecoder readReply(LdapRequest paramLdapRequest) throws IOException, NamingException {
/*      */     BerDecoder berDecoder;
/*      */     try {
/*  461 */       berDecoder = paramLdapRequest.getReplyBer(this.readTimeout);
/*  462 */     } catch (InterruptedException interruptedException) {
/*  463 */       throw new InterruptedNamingException("Interrupted during LDAP operation");
/*      */     } 
/*      */ 
/*      */     
/*  467 */     if (berDecoder == null) {
/*  468 */       abandonRequest(paramLdapRequest, null);
/*  469 */       throw new NamingException("LDAP response read timed out, timeout used:" + this.readTimeout + "ms.");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  474 */     return berDecoder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void addRequest(LdapRequest paramLdapRequest) {
/*  485 */     LdapRequest ldapRequest = this.pendingRequests;
/*  486 */     if (ldapRequest == null) {
/*  487 */       this.pendingRequests = paramLdapRequest;
/*  488 */       paramLdapRequest.next = null;
/*      */     } else {
/*  490 */       paramLdapRequest.next = this.pendingRequests;
/*  491 */       this.pendingRequests = paramLdapRequest;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   synchronized LdapRequest findRequest(int paramInt) {
/*  497 */     LdapRequest ldapRequest = this.pendingRequests;
/*  498 */     while (ldapRequest != null) {
/*  499 */       if (ldapRequest.msgId == paramInt) {
/*  500 */         return ldapRequest;
/*      */       }
/*  502 */       ldapRequest = ldapRequest.next;
/*      */     } 
/*  504 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   synchronized void removeRequest(LdapRequest paramLdapRequest) {
/*  509 */     LdapRequest ldapRequest1 = this.pendingRequests;
/*  510 */     LdapRequest ldapRequest2 = null;
/*      */     
/*  512 */     while (ldapRequest1 != null) {
/*  513 */       if (ldapRequest1 == paramLdapRequest) {
/*  514 */         ldapRequest1.cancel();
/*      */         
/*  516 */         if (ldapRequest2 != null) {
/*  517 */           ldapRequest2.next = ldapRequest1.next;
/*      */         } else {
/*  519 */           this.pendingRequests = ldapRequest1.next;
/*      */         } 
/*  521 */         ldapRequest1.next = null;
/*      */       } 
/*  523 */       ldapRequest2 = ldapRequest1;
/*  524 */       ldapRequest1 = ldapRequest1.next;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void abandonRequest(LdapRequest paramLdapRequest, Control[] paramArrayOfControl) {
/*  530 */     removeRequest(paramLdapRequest);
/*      */     
/*  532 */     BerEncoder berEncoder = new BerEncoder(256);
/*  533 */     int i = getMsgId();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  539 */       berEncoder.beginSeq(48);
/*  540 */       berEncoder.encodeInt(i);
/*  541 */       berEncoder.encodeInt(paramLdapRequest.msgId, 80);
/*      */       
/*  543 */       if (this.v3) {
/*  544 */         LdapClient.encodeControls(berEncoder, paramArrayOfControl);
/*      */       }
/*  546 */       berEncoder.endSeq();
/*      */       
/*  548 */       if (this.traceFile != null) {
/*  549 */         Ber.dumpBER(this.traceFile, this.traceTagOut, berEncoder.getBuf(), 0, berEncoder
/*  550 */             .getDataLen());
/*      */       }
/*      */       
/*  553 */       synchronized (this) {
/*  554 */         this.outStream.write(berEncoder.getBuf(), 0, berEncoder.getDataLen());
/*  555 */         this.outStream.flush();
/*      */       }
/*      */     
/*  558 */     } catch (IOException iOException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void abandonOutstandingReqs(Control[] paramArrayOfControl) {
/*  566 */     LdapRequest ldapRequest = this.pendingRequests;
/*      */     
/*  568 */     while (ldapRequest != null) {
/*  569 */       abandonRequest(ldapRequest, paramArrayOfControl);
/*  570 */       this.pendingRequests = ldapRequest = ldapRequest.next;
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
/*      */   private void ldapUnbind(Control[] paramArrayOfControl) {
/*  583 */     BerEncoder berEncoder = new BerEncoder(256);
/*  584 */     int i = getMsgId();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  592 */       berEncoder.beginSeq(48);
/*  593 */       berEncoder.encodeInt(i);
/*      */       
/*  595 */       berEncoder.encodeByte(66);
/*  596 */       berEncoder.encodeByte(0);
/*      */       
/*  598 */       if (this.v3) {
/*  599 */         LdapClient.encodeControls(berEncoder, paramArrayOfControl);
/*      */       }
/*  601 */       berEncoder.endSeq();
/*      */       
/*  603 */       if (this.traceFile != null) {
/*  604 */         Ber.dumpBER(this.traceFile, this.traceTagOut, berEncoder.getBuf(), 0, berEncoder
/*  605 */             .getDataLen());
/*      */       }
/*      */       
/*  608 */       synchronized (this) {
/*  609 */         this.outStream.write(berEncoder.getBuf(), 0, berEncoder.getDataLen());
/*  610 */         this.outStream.flush();
/*      */       }
/*      */     
/*  613 */     } catch (IOException iOException) {}
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
/*      */   void cleanup(Control[] paramArrayOfControl, boolean paramBoolean) {
/*  631 */     boolean bool = false;
/*      */     
/*  633 */     synchronized (this) {
/*  634 */       this.useable = false;
/*      */       
/*  636 */       if (this.sock != null) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  641 */           if (!paramBoolean) {
/*  642 */             abandonOutstandingReqs(paramArrayOfControl);
/*      */           }
/*  644 */           if (this.bound) {
/*  645 */             ldapUnbind(paramArrayOfControl);
/*      */           }
/*      */         } finally {
/*      */           try {
/*  649 */             this.outStream.flush();
/*  650 */             this.sock.close();
/*  651 */             unpauseReader();
/*  652 */           } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */           
/*  656 */           if (!paramBoolean) {
/*  657 */             LdapRequest ldapRequest = this.pendingRequests;
/*  658 */             while (ldapRequest != null) {
/*  659 */               ldapRequest.cancel();
/*  660 */               ldapRequest = ldapRequest.next;
/*      */             } 
/*      */           } 
/*  663 */           this.sock = null;
/*      */         } 
/*  665 */         bool = paramBoolean;
/*      */       } 
/*  667 */       if (bool) {
/*  668 */         LdapRequest ldapRequest = this.pendingRequests;
/*  669 */         while (ldapRequest != null) {
/*  670 */           ldapRequest.close();
/*  671 */           ldapRequest = ldapRequest.next;
/*      */         } 
/*      */       } 
/*      */     } 
/*  675 */     if (bool) {
/*  676 */       this.parent.processConnectionClosure();
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
/*      */   public synchronized void replaceStreams(InputStream paramInputStream, OutputStream paramOutputStream) {
/*  691 */     this.inStream = paramInputStream;
/*      */ 
/*      */     
/*      */     try {
/*  695 */       this.outStream.flush();
/*  696 */     } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  702 */     this.outStream = paramOutputStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized InputStream getInputStream() {
/*  711 */     return this.inStream;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Connection(LdapClient paramLdapClient, String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3, OutputStream paramOutputStream) throws NamingException {
/*  762 */     this.pauseLock = new Object();
/*  763 */     this.paused = false; this.host = paramString1; this.port = paramInt1; this.parent = paramLdapClient; this.readTimeout = paramInt3; this.connectTimeout = paramInt2; if (paramOutputStream != null) { this.traceFile = paramOutputStream; this.traceTagIn = "<- " + paramString1 + ":" + paramInt1 + "\n\n"; this.traceTagOut = "-> " + paramString1 + ":" + paramInt1 + "\n\n"; }
/*      */      try { this.sock = createSocket(paramString1, paramInt1, paramString2, paramInt2); this.inStream = new BufferedInputStream(this.sock.getInputStream()); this.outStream = new BufferedOutputStream(this.sock.getOutputStream()); }
/*      */     catch (InvocationTargetException invocationTargetException)
/*      */     { Throwable throwable = invocationTargetException.getTargetException(); CommunicationException communicationException = new CommunicationException(paramString1 + ":" + paramInt1); communicationException.setRootCause(throwable); throw communicationException; }
/*      */     catch (Exception exception)
/*      */     { CommunicationException communicationException = new CommunicationException(paramString1 + ":" + paramInt1); communicationException.setRootCause(exception); throw communicationException; }
/*  769 */      this.worker = Obj.helper.createThread(this); this.worker.setDaemon(true); this.worker.start(); } private void unpauseReader() throws IOException { synchronized (this.pauseLock) {
/*  770 */       if (this.paused) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  775 */         this.paused = false;
/*  776 */         this.pauseLock.notify();
/*      */       } 
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void pauseReader() throws IOException {
/*  791 */     this.paused = true;
/*      */     try {
/*  793 */       while (this.paused) {
/*  794 */         this.pauseLock.wait();
/*      */       }
/*  796 */     } catch (InterruptedException interruptedException) {
/*  797 */       throw new InterruptedIOException("Pause/unpause reader has problems.");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void run() {
/*  821 */     InputStream inputStream = null;
/*      */ 
/*      */     
/*      */     try {
/*      */       while (true) {
/*      */         try {
/*  827 */           byte[] arrayOfByte1 = new byte[129];
/*      */           
/*  829 */           int j = 0;
/*  830 */           int k = 0;
/*  831 */           int m = 0;
/*      */           
/*  833 */           inputStream = getInputStream();
/*      */ 
/*      */           
/*  836 */           int i = inputStream.read(arrayOfByte1, j, 1);
/*  837 */           if (i < 0) {
/*  838 */             if (inputStream != getInputStream()) {
/*      */               continue;
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/*  845 */           if (arrayOfByte1[j++] != 48) {
/*      */             continue;
/*      */           }
/*      */           
/*  849 */           i = inputStream.read(arrayOfByte1, j, 1);
/*  850 */           if (i < 0)
/*      */             break; 
/*  852 */           k = arrayOfByte1[j++];
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  857 */           if ((k & 0x80) == 128) {
/*  858 */             m = k & 0x7F;
/*      */             
/*  860 */             i = 0;
/*  861 */             boolean bool = false;
/*      */ 
/*      */             
/*  864 */             while (i < m) {
/*  865 */               int n = inputStream.read(arrayOfByte1, j + i, m - i);
/*      */               
/*  867 */               if (n < 0) {
/*  868 */                 bool = true;
/*      */                 break;
/*      */               } 
/*  871 */               i += n;
/*      */             } 
/*      */ 
/*      */             
/*  875 */             if (bool) {
/*      */               break;
/*      */             }
/*      */             
/*  879 */             k = 0;
/*  880 */             for (byte b = 0; b < m; b++) {
/*  881 */               k = (k << 8) + (arrayOfByte1[j + b] & 0xFF);
/*      */             }
/*  883 */             j += i;
/*      */           } 
/*      */ 
/*      */           
/*  887 */           byte[] arrayOfByte2 = readFully(inputStream, k);
/*  888 */           arrayOfByte1 = Arrays.copyOf(arrayOfByte1, j + arrayOfByte2.length);
/*  889 */           System.arraycopy(arrayOfByte2, 0, arrayOfByte1, j, arrayOfByte2.length);
/*  890 */           j += arrayOfByte2.length;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/*  902 */             BerDecoder berDecoder = new BerDecoder(arrayOfByte1, 0, j);
/*      */             
/*  904 */             if (this.traceFile != null) {
/*  905 */               Ber.dumpBER(this.traceFile, this.traceTagIn, arrayOfByte1, 0, j);
/*      */             }
/*      */             
/*  908 */             berDecoder.parseSeq(null);
/*  909 */             int n = berDecoder.parseInt();
/*  910 */             berDecoder.reset();
/*      */             
/*  912 */             boolean bool = false;
/*      */             
/*  914 */             if (n == 0) {
/*      */               
/*  916 */               this.parent.processUnsolicited(berDecoder); continue;
/*      */             } 
/*  918 */             LdapRequest ldapRequest = findRequest(n);
/*      */             
/*  920 */             if (ldapRequest != null)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  927 */               synchronized (this.pauseLock) {
/*  928 */                 bool = ldapRequest.addReplyBer(berDecoder);
/*  929 */                 if (bool)
/*      */                 {
/*      */ 
/*      */ 
/*      */                   
/*  934 */                   pauseReader();
/*      */                 
/*      */                 }
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */           
/*      */           }
/*  944 */           catch (DecodeException decodeException) {}
/*      */         
/*      */         }
/*  947 */         catch (IOException iOException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  953 */           if (inputStream != getInputStream()) {
/*      */             continue;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  960 */           throw iOException;
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*  969 */     catch (IOException iOException) {
/*      */ 
/*      */ 
/*      */       
/*  973 */       this.closureReason = iOException;
/*      */     } finally {
/*  975 */       cleanup(null, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] readFully(InputStream paramInputStream, int paramInt) throws IOException {
/*  985 */     byte[] arrayOfByte = new byte[Math.min(paramInt, 8192)];
/*  986 */     int i = 0;
/*  987 */     while (i < paramInt) {
/*      */       int j;
/*  989 */       if (i >= arrayOfByte.length) {
/*  990 */         j = Math.min(paramInt - i, arrayOfByte.length + 8192);
/*  991 */         if (arrayOfByte.length < i + j) {
/*  992 */           arrayOfByte = Arrays.copyOf(arrayOfByte, i + j);
/*      */         }
/*      */       } else {
/*  995 */         j = arrayOfByte.length - i;
/*      */       } 
/*  997 */       int k = paramInputStream.read(arrayOfByte, i, j);
/*  998 */       if (k < 0) {
/*  999 */         if (arrayOfByte.length != i)
/* 1000 */           arrayOfByte = Arrays.copyOf(arrayOfByte, i); 
/*      */         break;
/*      */       } 
/* 1003 */       i += k;
/*      */     } 
/* 1005 */     return arrayOfByte;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/Connection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */