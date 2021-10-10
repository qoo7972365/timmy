/*      */ package sun.security.jgss.krb5;
/*      */ 
/*      */ import com.sun.security.jgss.AuthorizationDataEntry;
/*      */ import com.sun.security.jgss.InquireType;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.Key;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.security.Provider;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.security.auth.kerberos.KerberosTicket;
/*      */ import javax.security.auth.kerberos.ServicePermission;
/*      */ import org.ietf.jgss.ChannelBinding;
/*      */ import org.ietf.jgss.GSSException;
/*      */ import org.ietf.jgss.MessageProp;
/*      */ import org.ietf.jgss.Oid;
/*      */ import sun.misc.HexDumpEncoder;
/*      */ import sun.security.jgss.GSSCaller;
/*      */ import sun.security.jgss.GSSUtil;
/*      */ import sun.security.jgss.TokenTracker;
/*      */ import sun.security.jgss.spi.GSSContextSpi;
/*      */ import sun.security.jgss.spi.GSSCredentialSpi;
/*      */ import sun.security.jgss.spi.GSSNameSpi;
/*      */ import sun.security.krb5.Credentials;
/*      */ import sun.security.krb5.EncryptionKey;
/*      */ import sun.security.krb5.KrbApReq;
/*      */ import sun.security.krb5.KrbException;
/*      */ import sun.security.krb5.PrincipalName;
/*      */ import sun.security.krb5.internal.Ticket;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class Krb5Context
/*      */   implements GSSContextSpi
/*      */ {
/*      */   private static final int STATE_NEW = 1;
/*      */   private static final int STATE_IN_PROCESS = 2;
/*      */   private static final int STATE_DONE = 3;
/*      */   private static final int STATE_DELETED = 4;
/*   69 */   private int state = 1;
/*      */   
/*      */   public static final int SESSION_KEY = 0;
/*      */   
/*      */   public static final int INITIATOR_SUBKEY = 1;
/*      */   
/*      */   public static final int ACCEPTOR_SUBKEY = 2;
/*      */   
/*      */   private boolean credDelegState = false;
/*      */   
/*      */   private boolean mutualAuthState = true;
/*      */   
/*      */   private boolean replayDetState = true;
/*      */   
/*      */   private boolean sequenceDetState = true;
/*      */   
/*      */   private boolean confState = true;
/*      */   
/*      */   private boolean integState = true;
/*      */   
/*      */   private boolean delegPolicyState = false;
/*      */   private boolean isConstrainedDelegationTried = false;
/*      */   private int mySeqNumber;
/*      */   private int peerSeqNumber;
/*      */   private int keySrc;
/*      */   private TokenTracker peerTokenTracker;
/*   95 */   private CipherHelper cipherHelper = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  106 */   private Object mySeqNumberLock = new Object();
/*  107 */   private Object peerSeqNumberLock = new Object();
/*      */   
/*      */   private EncryptionKey key;
/*      */   
/*      */   private Krb5NameElement myName;
/*      */   
/*      */   private Krb5NameElement peerName;
/*      */   
/*      */   private int lifetime;
/*      */   
/*      */   private boolean initiator;
/*      */   private ChannelBinding channelBinding;
/*      */   private Krb5CredElement myCred;
/*      */   private Krb5CredElement delegatedCred;
/*      */   private Credentials serviceCreds;
/*      */   private KrbApReq apReq;
/*      */   Ticket serviceTicket;
/*      */   private final GSSCaller caller;
/*  125 */   private static final boolean DEBUG = Krb5Util.DEBUG;
/*      */   
/*      */   private boolean[] tktFlags;
/*      */   
/*      */   private String authTime;
/*      */   
/*      */   private AuthorizationDataEntry[] authzData;
/*      */ 
/*      */   
/*      */   Krb5Context(GSSCaller paramGSSCaller, Krb5NameElement paramKrb5NameElement, Krb5CredElement paramKrb5CredElement, int paramInt) throws GSSException {
/*  135 */     if (paramKrb5NameElement == null) {
/*  136 */       throw new IllegalArgumentException("Cannot have null peer name");
/*      */     }
/*  138 */     this.caller = paramGSSCaller;
/*  139 */     this.peerName = paramKrb5NameElement;
/*  140 */     this.myCred = paramKrb5CredElement;
/*  141 */     this.lifetime = paramInt;
/*  142 */     this.initiator = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Krb5Context(GSSCaller paramGSSCaller, Krb5CredElement paramKrb5CredElement) throws GSSException {
/*  151 */     this.caller = paramGSSCaller;
/*  152 */     this.myCred = paramKrb5CredElement;
/*  153 */     this.initiator = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Krb5Context(GSSCaller paramGSSCaller, byte[] paramArrayOfbyte) throws GSSException {
/*  161 */     throw new GSSException(16, -1, "GSS Import Context not available");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isTransferable() throws GSSException {
/*  170 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getLifetime() {
/*  178 */     return Integer.MAX_VALUE;
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
/*      */   public void requestLifetime(int paramInt) throws GSSException {
/*  205 */     if (this.state == 1 && isInitiator()) {
/*  206 */       this.lifetime = paramInt;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestConf(boolean paramBoolean) throws GSSException {
/*  213 */     if (this.state == 1 && isInitiator()) {
/*  214 */       this.confState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getConfState() {
/*  221 */     return this.confState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestInteg(boolean paramBoolean) throws GSSException {
/*  228 */     if (this.state == 1 && isInitiator()) {
/*  229 */       this.integState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getIntegState() {
/*  236 */     return this.integState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestCredDeleg(boolean paramBoolean) throws GSSException {
/*  244 */     if (this.state == 1 && isInitiator() && (
/*  245 */       this.myCred == null || !(this.myCred instanceof Krb5ProxyCredential))) {
/*  246 */       this.credDelegState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getCredDelegState() {
/*  255 */     if (isInitiator()) {
/*  256 */       return this.credDelegState;
/*      */     }
/*      */ 
/*      */     
/*  260 */     tryConstrainedDelegation();
/*  261 */     return (this.delegatedCred != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestMutualAuth(boolean paramBoolean) throws GSSException {
/*  271 */     if (this.state == 1 && isInitiator()) {
/*  272 */       this.mutualAuthState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getMutualAuthState() {
/*  282 */     return this.mutualAuthState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestReplayDet(boolean paramBoolean) throws GSSException {
/*  290 */     if (this.state == 1 && isInitiator()) {
/*  291 */       this.replayDetState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getReplayDetState() {
/*  299 */     return (this.replayDetState || this.sequenceDetState);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestSequenceDet(boolean paramBoolean) throws GSSException {
/*  307 */     if (this.state == 1 && isInitiator()) {
/*  308 */       this.sequenceDetState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getSequenceDetState() {
/*  316 */     return (this.sequenceDetState || this.replayDetState);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestDelegPolicy(boolean paramBoolean) {
/*  323 */     if (this.state == 1 && isInitiator()) {
/*  324 */       this.delegPolicyState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getDelegPolicyState() {
/*  331 */     return this.delegPolicyState;
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
/*      */   public final void requestAnonymity(boolean paramBoolean) throws GSSException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getAnonymityState() {
/*  353 */     return false;
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
/*      */   final CipherHelper getCipherHelper(EncryptionKey paramEncryptionKey) throws GSSException {
/*  365 */     EncryptionKey encryptionKey = null;
/*  366 */     if (this.cipherHelper == null) {
/*  367 */       encryptionKey = (getKey() == null) ? paramEncryptionKey : getKey();
/*  368 */       this.cipherHelper = new CipherHelper(encryptionKey);
/*      */     } 
/*  370 */     return this.cipherHelper;
/*      */   }
/*      */   
/*      */   final int incrementMySequenceNumber() {
/*      */     int i;
/*  375 */     synchronized (this.mySeqNumberLock) {
/*  376 */       i = this.mySeqNumber;
/*  377 */       this.mySeqNumber = i + 1;
/*      */     } 
/*  379 */     return i;
/*      */   }
/*      */   
/*      */   final void resetMySequenceNumber(int paramInt) {
/*  383 */     if (DEBUG) {
/*  384 */       System.out.println("Krb5Context setting mySeqNumber to: " + paramInt);
/*      */     }
/*      */     
/*  387 */     synchronized (this.mySeqNumberLock) {
/*  388 */       this.mySeqNumber = paramInt;
/*      */     } 
/*      */   }
/*      */   
/*      */   final void resetPeerSequenceNumber(int paramInt) {
/*  393 */     if (DEBUG) {
/*  394 */       System.out.println("Krb5Context setting peerSeqNumber to: " + paramInt);
/*      */     }
/*      */     
/*  397 */     synchronized (this.peerSeqNumberLock) {
/*  398 */       this.peerSeqNumber = paramInt;
/*  399 */       this.peerTokenTracker = new TokenTracker(this.peerSeqNumber);
/*      */     } 
/*      */   }
/*      */   
/*      */   final void setKey(int paramInt, EncryptionKey paramEncryptionKey) throws GSSException {
/*  404 */     this.key = paramEncryptionKey;
/*  405 */     this.keySrc = paramInt;
/*      */     
/*  407 */     this.cipherHelper = new CipherHelper(paramEncryptionKey);
/*      */   }
/*      */   
/*      */   public final int getKeySrc() {
/*  411 */     return this.keySrc;
/*      */   }
/*      */   
/*      */   private final EncryptionKey getKey() {
/*  415 */     return this.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void setDelegCred(Krb5CredElement paramKrb5CredElement) {
/*  423 */     this.delegatedCred = paramKrb5CredElement;
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
/*      */   final void setCredDelegState(boolean paramBoolean) {
/*  439 */     this.credDelegState = paramBoolean;
/*      */   }
/*      */   
/*      */   final void setMutualAuthState(boolean paramBoolean) {
/*  443 */     this.mutualAuthState = paramBoolean;
/*      */   }
/*      */   
/*      */   final void setReplayDetState(boolean paramBoolean) {
/*  447 */     this.replayDetState = paramBoolean;
/*      */   }
/*      */   
/*      */   final void setSequenceDetState(boolean paramBoolean) {
/*  451 */     this.sequenceDetState = paramBoolean;
/*      */   }
/*      */   
/*      */   final void setConfState(boolean paramBoolean) {
/*  455 */     this.confState = paramBoolean;
/*      */   }
/*      */   
/*      */   final void setIntegState(boolean paramBoolean) {
/*  459 */     this.integState = paramBoolean;
/*      */   }
/*      */   
/*      */   final void setDelegPolicyState(boolean paramBoolean) {
/*  463 */     this.delegPolicyState = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setChannelBinding(ChannelBinding paramChannelBinding) throws GSSException {
/*  472 */     this.channelBinding = paramChannelBinding;
/*      */   }
/*      */   
/*      */   final ChannelBinding getChannelBinding() {
/*  476 */     return this.channelBinding;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Oid getMech() {
/*  485 */     return Krb5MechFactory.GSS_KRB5_MECH_OID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final GSSNameSpi getSrcName() throws GSSException {
/*  495 */     return isInitiator() ? this.myName : this.peerName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final GSSNameSpi getTargName() throws GSSException {
/*  505 */     return !isInitiator() ? this.myName : this.peerName;
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
/*      */   public final GSSCredentialSpi getDelegCred() throws GSSException {
/*  520 */     if (this.state != 2 && this.state != 3)
/*  521 */       throw new GSSException(12); 
/*  522 */     if (isInitiator()) {
/*  523 */       throw new GSSException(13);
/*      */     }
/*  525 */     tryConstrainedDelegation();
/*  526 */     if (this.delegatedCred == null) {
/*  527 */       throw new GSSException(13);
/*      */     }
/*  529 */     return this.delegatedCred;
/*      */   }
/*      */   
/*      */   private void tryConstrainedDelegation() {
/*  533 */     if (this.state != 2 && this.state != 3) {
/*      */       return;
/*      */     }
/*      */     
/*  537 */     if (!this.isConstrainedDelegationTried) {
/*  538 */       if (this.delegatedCred == null) {
/*  539 */         if (DEBUG) {
/*  540 */           System.out.println(">>> Constrained deleg from " + this.caller);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  546 */           this
/*  547 */             .delegatedCred = new Krb5ProxyCredential(Krb5InitCredential.getInstance(GSSCaller.CALLER_ACCEPT, this.myName, this.lifetime), this.peerName, this.serviceTicket);
/*      */         
/*      */         }
/*  550 */         catch (GSSException gSSException) {}
/*      */       } 
/*      */ 
/*      */       
/*  554 */       this.isConstrainedDelegationTried = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isInitiator() {
/*  564 */     return this.initiator;
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
/*      */   public final boolean isProtReady() {
/*  576 */     return (this.state == 3);
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
/*      */   public final byte[] initSecContext(InputStream paramInputStream, int paramInt) throws GSSException {
/*  595 */     byte[] arrayOfByte = null;
/*  596 */     InitSecContextToken initSecContextToken = null;
/*  597 */     byte b = 11;
/*  598 */     if (DEBUG) {
/*  599 */       System.out.println("Entered Krb5Context.initSecContext with state=" + 
/*  600 */           printState(this.state));
/*      */     }
/*  602 */     if (!isInitiator()) {
/*  603 */       throw new GSSException(11, -1, "initSecContext on an acceptor GSSContext");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  609 */       if (this.state == 1) {
/*  610 */         Credentials credentials; final Krb5ProxyCredential second; this.state = 2;
/*      */         
/*  612 */         b = 13;
/*      */         
/*  614 */         if (this.myCred == null) {
/*  615 */           this.myCred = Krb5InitCredential.getInstance(this.caller, this.myName, 0);
/*      */           
/*  617 */           this.myCred = Krb5ProxyCredential.tryImpersonation(this.caller, (Krb5InitCredential)this.myCred);
/*      */         }
/*  619 */         else if (!this.myCred.isInitiatorCredential()) {
/*  620 */           throw new GSSException(b, -1, "No TGT available");
/*      */         } 
/*      */         
/*  623 */         this.myName = (Krb5NameElement)this.myCred.getName();
/*      */ 
/*      */         
/*  626 */         if (this.myCred instanceof Krb5InitCredential) {
/*  627 */           krb5ProxyCredential = null;
/*  628 */           credentials = ((Krb5InitCredential)this.myCred).getKrb5Credentials();
/*      */         } else {
/*  630 */           krb5ProxyCredential = (Krb5ProxyCredential)this.myCred;
/*  631 */           credentials = krb5ProxyCredential.self.getKrb5Credentials();
/*      */         } 
/*      */         
/*  634 */         checkPermission(this.peerName.getKrb5PrincipalName().getName(), "initiate");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  643 */         final AccessControlContext acc = AccessController.getContext();
/*      */         
/*  645 */         if (GSSUtil.useSubjectCredsOnly(this.caller)) {
/*  646 */           final KerberosTicket kt = null;
/*      */           
/*      */           try {
/*  649 */             kerberosTicket = AccessController.<KerberosTicket>doPrivileged(new PrivilegedExceptionAction<KerberosTicket>()
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   public KerberosTicket run() throws Exception
/*      */                   {
/*  657 */                     return Krb5Util.getServiceTicket(GSSCaller.CALLER_UNKNOWN, (second == null) ? Krb5Context.this
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  662 */                         .myName.getKrb5PrincipalName().getName() : second
/*  663 */                         .getName().getKrb5PrincipalName().getName(), Krb5Context.this
/*  664 */                         .peerName.getKrb5PrincipalName().getName(), acc);
/*      */                   }
/*      */                 });
/*  667 */           } catch (PrivilegedActionException privilegedActionException) {
/*  668 */             if (DEBUG) {
/*  669 */               System.out.println("Attempt to obtain service ticket from the subject failed!");
/*      */             }
/*      */           } 
/*      */           
/*  673 */           if (kerberosTicket != null) {
/*  674 */             if (DEBUG) {
/*  675 */               System.out.println("Found service ticket in the subject" + kerberosTicket);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  683 */             this.serviceCreds = Krb5Util.ticketToCreds(kerberosTicket);
/*      */           } 
/*      */         } 
/*  686 */         if (this.serviceCreds == null) {
/*      */ 
/*      */           
/*  689 */           if (DEBUG) {
/*  690 */             System.out.println("Service ticket not found in the subject");
/*      */           }
/*      */ 
/*      */           
/*  694 */           if (krb5ProxyCredential == null) {
/*  695 */             this.serviceCreds = Credentials.acquireServiceCreds(this.peerName
/*  696 */                 .getKrb5PrincipalName().getName(), credentials);
/*      */           } else {
/*      */             
/*  699 */             this.serviceCreds = Credentials.acquireS4U2proxyCreds(this.peerName
/*  700 */                 .getKrb5PrincipalName().getName(), krb5ProxyCredential.tkt, krb5ProxyCredential
/*      */                 
/*  702 */                 .getName().getKrb5PrincipalName(), credentials);
/*      */           } 
/*      */           
/*  705 */           if (GSSUtil.useSubjectCredsOnly(this.caller)) {
/*      */             
/*  707 */             final Subject subject = AccessController.<Subject>doPrivileged(new PrivilegedAction<Subject>()
/*      */                 {
/*      */                   public Subject run() {
/*  710 */                     return Subject.getSubject(acc);
/*      */                   }
/*      */                 });
/*  713 */             if (subject != null && 
/*  714 */               !subject.isReadOnly()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  723 */               final KerberosTicket kt = Krb5Util.credsToTicket(this.serviceCreds);
/*  724 */               AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */                   {
/*      */                     public Void run() {
/*  727 */                       subject.getPrivateCredentials().add(kt);
/*  728 */                       return null;
/*      */                     }
/*      */                   });
/*      */             
/*      */             }
/*  733 */             else if (DEBUG) {
/*  734 */               System.out.println("Subject is readOnly;Kerberos Service ticket not stored");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  742 */         b = 11;
/*  743 */         initSecContextToken = new InitSecContextToken(this, credentials, this.serviceCreds);
/*  744 */         this.apReq = initSecContextToken.getKrbApReq();
/*  745 */         arrayOfByte = initSecContextToken.encode();
/*  746 */         this.myCred = null;
/*  747 */         if (!getMutualAuthState()) {
/*  748 */           this.state = 3;
/*      */         }
/*  750 */         if (DEBUG) {
/*  751 */           System.out.println("Created InitSecContextToken:\n" + (new HexDumpEncoder())
/*  752 */               .encodeBuffer(arrayOfByte));
/*      */         }
/*  754 */       } else if (this.state == 2) {
/*      */ 
/*      */         
/*  757 */         new AcceptSecContextToken(this, this.serviceCreds, this.apReq, paramInputStream);
/*  758 */         this.serviceCreds = null;
/*  759 */         this.apReq = null;
/*  760 */         this.state = 3;
/*      */       
/*      */       }
/*  763 */       else if (DEBUG) {
/*  764 */         System.out.println(this.state);
/*      */       }
/*      */     
/*  767 */     } catch (KrbException krbException) {
/*  768 */       if (DEBUG) {
/*  769 */         krbException.printStackTrace();
/*      */       }
/*      */       
/*  772 */       GSSException gSSException = new GSSException(b, -1, krbException.getMessage());
/*  773 */       gSSException.initCause(krbException);
/*  774 */       throw gSSException;
/*  775 */     } catch (IOException iOException) {
/*      */       
/*  777 */       GSSException gSSException = new GSSException(b, -1, iOException.getMessage());
/*  778 */       gSSException.initCause(iOException);
/*  779 */       throw gSSException;
/*      */     } 
/*  781 */     return arrayOfByte;
/*      */   }
/*      */   
/*      */   public final boolean isEstablished() {
/*  785 */     return (this.state == 3);
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
/*      */   public final byte[] acceptSecContext(InputStream paramInputStream, int paramInt) throws GSSException {
/*  803 */     byte[] arrayOfByte = null;
/*      */     
/*  805 */     if (DEBUG) {
/*  806 */       System.out.println("Entered Krb5Context.acceptSecContext with state=" + 
/*  807 */           printState(this.state));
/*      */     }
/*      */     
/*  810 */     if (isInitiator()) {
/*  811 */       throw new GSSException(11, -1, "acceptSecContext on an initiator GSSContext");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  816 */       if (this.state == 1) {
/*  817 */         this.state = 2;
/*  818 */         if (this.myCred == null) {
/*  819 */           this.myCred = Krb5AcceptCredential.getInstance(this.caller, this.myName);
/*  820 */         } else if (!this.myCred.isAcceptorCredential()) {
/*  821 */           throw new GSSException(13, -1, "No Secret Key available");
/*      */         } 
/*      */         
/*  824 */         this.myName = (Krb5NameElement)this.myCred.getName();
/*      */ 
/*      */         
/*  827 */         if (this.myName != null) {
/*  828 */           Krb5MechFactory.checkAcceptCredPermission(this.myName, this.myName);
/*      */         }
/*      */         
/*  831 */         InitSecContextToken initSecContextToken = new InitSecContextToken(this, (Krb5AcceptCredential)this.myCred, paramInputStream);
/*      */         
/*  833 */         PrincipalName principalName = initSecContextToken.getKrbApReq().getClient();
/*  834 */         this.peerName = Krb5NameElement.getInstance(principalName);
/*      */ 
/*      */         
/*  837 */         if (this.myName == null) {
/*  838 */           this.myName = Krb5NameElement.getInstance(initSecContextToken
/*  839 */               .getKrbApReq().getCreds().getServer());
/*  840 */           Krb5MechFactory.checkAcceptCredPermission(this.myName, this.myName);
/*      */         } 
/*      */         
/*  843 */         if (getMutualAuthState())
/*      */         {
/*  845 */           arrayOfByte = (new AcceptSecContextToken(this, initSecContextToken.getKrbApReq())).encode();
/*      */         }
/*  847 */         this.serviceTicket = initSecContextToken.getKrbApReq().getCreds().getTicket();
/*  848 */         this.myCred = null;
/*  849 */         this.state = 3;
/*      */       
/*      */       }
/*  852 */       else if (DEBUG) {
/*  853 */         System.out.println(this.state);
/*      */       }
/*      */     
/*  856 */     } catch (KrbException krbException) {
/*      */       
/*  858 */       GSSException gSSException = new GSSException(11, -1, krbException.getMessage());
/*  859 */       gSSException.initCause(krbException);
/*  860 */       throw gSSException;
/*  861 */     } catch (IOException iOException) {
/*  862 */       if (DEBUG) {
/*  863 */         iOException.printStackTrace();
/*      */       }
/*      */       
/*  866 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/*  867 */       gSSException.initCause(iOException);
/*  868 */       throw gSSException;
/*      */     } 
/*      */     
/*  871 */     return arrayOfByte;
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
/*      */   public final int getWrapSizeLimit(int paramInt1, boolean paramBoolean, int paramInt2) throws GSSException {
/*  891 */     int i = 0;
/*  892 */     if (this.cipherHelper.getProto() == 0) {
/*  893 */       i = WrapToken.getSizeLimit(paramInt1, paramBoolean, paramInt2, 
/*  894 */           getCipherHelper(null));
/*  895 */     } else if (this.cipherHelper.getProto() == 1) {
/*  896 */       i = WrapToken_v2.getSizeLimit(paramInt1, paramBoolean, paramInt2, 
/*  897 */           getCipherHelper(null));
/*      */     } 
/*  899 */     return i;
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
/*      */   public final byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/*  911 */     if (DEBUG) {
/*  912 */       System.out.println("Krb5Context.wrap: data=[" + 
/*  913 */           getHexBytes(paramArrayOfbyte, paramInt1, paramInt2) + "]");
/*      */     }
/*      */ 
/*      */     
/*  917 */     if (this.state != 3) {
/*  918 */       throw new GSSException(12, -1, "Wrap called in invalid state!");
/*      */     }
/*      */     
/*  921 */     byte[] arrayOfByte = null;
/*      */     try {
/*  923 */       if (this.cipherHelper.getProto() == 0) {
/*  924 */         WrapToken wrapToken = new WrapToken(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/*  926 */         arrayOfByte = wrapToken.encode();
/*  927 */       } else if (this.cipherHelper.getProto() == 1) {
/*  928 */         WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/*  930 */         arrayOfByte = wrapToken_v2.encode();
/*      */       } 
/*  932 */       if (DEBUG) {
/*  933 */         System.out.println("Krb5Context.wrap: token=[" + 
/*  934 */             getHexBytes(arrayOfByte, 0, arrayOfByte.length) + "]");
/*      */       }
/*      */       
/*  937 */       return arrayOfByte;
/*  938 */     } catch (IOException iOException) {
/*  939 */       arrayOfByte = null;
/*      */       
/*  941 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/*  942 */       gSSException.initCause(iOException);
/*  943 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int wrap(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, MessageProp paramMessageProp) throws GSSException {
/*  951 */     if (this.state != 3) {
/*  952 */       throw new GSSException(12, -1, "Wrap called in invalid state!");
/*      */     }
/*      */     
/*  955 */     int i = 0;
/*      */     try {
/*  957 */       if (this.cipherHelper.getProto() == 0) {
/*  958 */         WrapToken wrapToken = new WrapToken(this, paramMessageProp, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */         
/*  960 */         i = wrapToken.encode(paramArrayOfbyte2, paramInt3);
/*  961 */       } else if (this.cipherHelper.getProto() == 1) {
/*  962 */         WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramMessageProp, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */         
/*  964 */         i = wrapToken_v2.encode(paramArrayOfbyte2, paramInt3);
/*      */       } 
/*  966 */       if (DEBUG) {
/*  967 */         System.out.println("Krb5Context.wrap: token=[" + 
/*  968 */             getHexBytes(paramArrayOfbyte2, paramInt3, i) + "]");
/*      */       }
/*      */       
/*  971 */       return i;
/*  972 */     } catch (IOException iOException) {
/*  973 */       i = 0;
/*      */       
/*  975 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/*  976 */       gSSException.initCause(iOException);
/*  977 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/*  985 */     if (this.state != 3) {
/*  986 */       throw new GSSException(12, -1, "Wrap called in invalid state!");
/*      */     }
/*      */     
/*  989 */     byte[] arrayOfByte = null;
/*      */     try {
/*  991 */       if (this.cipherHelper.getProto() == 0) {
/*  992 */         WrapToken wrapToken = new WrapToken(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/*  994 */         wrapToken.encode(paramOutputStream);
/*  995 */         if (DEBUG) {
/*  996 */           arrayOfByte = wrapToken.encode();
/*      */         }
/*  998 */       } else if (this.cipherHelper.getProto() == 1) {
/*  999 */         WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/* 1001 */         wrapToken_v2.encode(paramOutputStream);
/* 1002 */         if (DEBUG) {
/* 1003 */           arrayOfByte = wrapToken_v2.encode();
/*      */         }
/*      */       } 
/* 1006 */     } catch (IOException iOException) {
/*      */       
/* 1008 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1009 */       gSSException.initCause(iOException);
/* 1010 */       throw gSSException;
/*      */     } 
/*      */     
/* 1013 */     if (DEBUG) {
/* 1014 */       System.out.println("Krb5Context.wrap: token=[" + 
/* 1015 */           getHexBytes(arrayOfByte, 0, arrayOfByte.length) + "]");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void wrap(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/*      */     byte[] arrayOfByte;
/*      */     try {
/* 1025 */       arrayOfByte = new byte[paramInputStream.available()];
/* 1026 */       paramInputStream.read(arrayOfByte);
/* 1027 */     } catch (IOException iOException) {
/*      */       
/* 1029 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1030 */       gSSException.initCause(iOException);
/* 1031 */       throw gSSException;
/*      */     } 
/* 1033 */     wrap(arrayOfByte, 0, arrayOfByte.length, paramOutputStream, paramMessageProp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/* 1040 */     if (DEBUG) {
/* 1041 */       System.out.println("Krb5Context.unwrap: token=[" + 
/* 1042 */           getHexBytes(paramArrayOfbyte, paramInt1, paramInt2) + "]");
/*      */     }
/*      */ 
/*      */     
/* 1046 */     if (this.state != 3) {
/* 1047 */       throw new GSSException(12, -1, " Unwrap called in invalid state!");
/*      */     }
/*      */ 
/*      */     
/* 1051 */     byte[] arrayOfByte = null;
/* 1052 */     if (this.cipherHelper.getProto() == 0) {
/* 1053 */       WrapToken wrapToken = new WrapToken(this, paramArrayOfbyte, paramInt1, paramInt2, paramMessageProp);
/*      */       
/* 1055 */       arrayOfByte = wrapToken.getData();
/* 1056 */       setSequencingAndReplayProps(wrapToken, paramMessageProp);
/* 1057 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1058 */       WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramArrayOfbyte, paramInt1, paramInt2, paramMessageProp);
/*      */       
/* 1060 */       arrayOfByte = wrapToken_v2.getData();
/* 1061 */       setSequencingAndReplayProps(wrapToken_v2, paramMessageProp);
/*      */     } 
/*      */     
/* 1064 */     if (DEBUG) {
/* 1065 */       System.out.println("Krb5Context.unwrap: data=[" + 
/* 1066 */           getHexBytes(arrayOfByte, 0, arrayOfByte.length) + "]");
/*      */     }
/*      */ 
/*      */     
/* 1070 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int unwrap(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, MessageProp paramMessageProp) throws GSSException {
/* 1077 */     if (this.state != 3) {
/* 1078 */       throw new GSSException(12, -1, "Unwrap called in invalid state!");
/*      */     }
/*      */     
/* 1081 */     if (this.cipherHelper.getProto() == 0) {
/* 1082 */       WrapToken wrapToken = new WrapToken(this, paramArrayOfbyte1, paramInt1, paramInt2, paramMessageProp);
/*      */       
/* 1084 */       paramInt2 = wrapToken.getData(paramArrayOfbyte2, paramInt3);
/* 1085 */       setSequencingAndReplayProps(wrapToken, paramMessageProp);
/* 1086 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1087 */       WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramArrayOfbyte1, paramInt1, paramInt2, paramMessageProp);
/*      */       
/* 1089 */       paramInt2 = wrapToken_v2.getData(paramArrayOfbyte2, paramInt3);
/* 1090 */       setSequencingAndReplayProps(wrapToken_v2, paramMessageProp);
/*      */     } 
/* 1092 */     return paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int unwrap(InputStream paramInputStream, byte[] paramArrayOfbyte, int paramInt, MessageProp paramMessageProp) throws GSSException {
/* 1099 */     if (this.state != 3) {
/* 1100 */       throw new GSSException(12, -1, "Unwrap called in invalid state!");
/*      */     }
/*      */     
/* 1103 */     int i = 0;
/* 1104 */     if (this.cipherHelper.getProto() == 0) {
/* 1105 */       WrapToken wrapToken = new WrapToken(this, paramInputStream, paramMessageProp);
/* 1106 */       i = wrapToken.getData(paramArrayOfbyte, paramInt);
/* 1107 */       setSequencingAndReplayProps(wrapToken, paramMessageProp);
/* 1108 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1109 */       WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramInputStream, paramMessageProp);
/* 1110 */       i = wrapToken_v2.getData(paramArrayOfbyte, paramInt);
/* 1111 */       setSequencingAndReplayProps(wrapToken_v2, paramMessageProp);
/*      */     } 
/* 1113 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void unwrap(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/* 1120 */     if (this.state != 3) {
/* 1121 */       throw new GSSException(12, -1, "Unwrap called in invalid state!");
/*      */     }
/*      */     
/* 1124 */     byte[] arrayOfByte = null;
/* 1125 */     if (this.cipherHelper.getProto() == 0) {
/* 1126 */       WrapToken wrapToken = new WrapToken(this, paramInputStream, paramMessageProp);
/* 1127 */       arrayOfByte = wrapToken.getData();
/* 1128 */       setSequencingAndReplayProps(wrapToken, paramMessageProp);
/* 1129 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1130 */       WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramInputStream, paramMessageProp);
/* 1131 */       arrayOfByte = wrapToken_v2.getData();
/* 1132 */       setSequencingAndReplayProps(wrapToken_v2, paramMessageProp);
/*      */     } 
/*      */     
/*      */     try {
/* 1136 */       paramOutputStream.write(arrayOfByte);
/* 1137 */     } catch (IOException iOException) {
/*      */       
/* 1139 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1140 */       gSSException.initCause(iOException);
/* 1141 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] getMIC(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/* 1149 */     byte[] arrayOfByte = null;
/*      */     try {
/* 1151 */       if (this.cipherHelper.getProto() == 0) {
/* 1152 */         MicToken micToken = new MicToken(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/* 1154 */         arrayOfByte = micToken.encode();
/* 1155 */       } else if (this.cipherHelper.getProto() == 1) {
/* 1156 */         MicToken_v2 micToken_v2 = new MicToken_v2(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/* 1158 */         arrayOfByte = micToken_v2.encode();
/*      */       } 
/* 1160 */       return arrayOfByte;
/* 1161 */     } catch (IOException iOException) {
/* 1162 */       arrayOfByte = null;
/*      */       
/* 1164 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1165 */       gSSException.initCause(iOException);
/* 1166 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getMIC(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, MessageProp paramMessageProp) throws GSSException {
/* 1175 */     int i = 0;
/*      */     try {
/* 1177 */       if (this.cipherHelper.getProto() == 0) {
/* 1178 */         MicToken micToken = new MicToken(this, paramMessageProp, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */         
/* 1180 */         i = micToken.encode(paramArrayOfbyte2, paramInt3);
/* 1181 */       } else if (this.cipherHelper.getProto() == 1) {
/* 1182 */         MicToken_v2 micToken_v2 = new MicToken_v2(this, paramMessageProp, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */         
/* 1184 */         i = micToken_v2.encode(paramArrayOfbyte2, paramInt3);
/*      */       } 
/* 1186 */       return i;
/* 1187 */     } catch (IOException iOException) {
/* 1188 */       i = 0;
/*      */       
/* 1190 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1191 */       gSSException.initCause(iOException);
/* 1192 */       throw gSSException;
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
/*      */   private void getMIC(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/*      */     try {
/* 1208 */       if (this.cipherHelper.getProto() == 0) {
/* 1209 */         MicToken micToken = new MicToken(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/* 1211 */         micToken.encode(paramOutputStream);
/* 1212 */       } else if (this.cipherHelper.getProto() == 1) {
/* 1213 */         MicToken_v2 micToken_v2 = new MicToken_v2(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/* 1215 */         micToken_v2.encode(paramOutputStream);
/*      */       } 
/* 1217 */     } catch (IOException iOException) {
/*      */       
/* 1219 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1220 */       gSSException.initCause(iOException);
/* 1221 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void getMIC(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/*      */     byte[] arrayOfByte;
/*      */     try {
/* 1229 */       arrayOfByte = new byte[paramInputStream.available()];
/* 1230 */       paramInputStream.read(arrayOfByte);
/* 1231 */     } catch (IOException iOException) {
/*      */       
/* 1233 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1234 */       gSSException.initCause(iOException);
/* 1235 */       throw gSSException;
/*      */     } 
/* 1237 */     getMIC(arrayOfByte, 0, arrayOfByte.length, paramOutputStream, paramMessageProp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void verifyMIC(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4, MessageProp paramMessageProp) throws GSSException {
/* 1245 */     if (this.cipherHelper.getProto() == 0) {
/* 1246 */       MicToken micToken = new MicToken(this, paramArrayOfbyte1, paramInt1, paramInt2, paramMessageProp);
/*      */       
/* 1248 */       micToken.verify(paramArrayOfbyte2, paramInt3, paramInt4);
/* 1249 */       setSequencingAndReplayProps(micToken, paramMessageProp);
/* 1250 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1251 */       MicToken_v2 micToken_v2 = new MicToken_v2(this, paramArrayOfbyte1, paramInt1, paramInt2, paramMessageProp);
/*      */       
/* 1253 */       micToken_v2.verify(paramArrayOfbyte2, paramInt3, paramInt4);
/* 1254 */       setSequencingAndReplayProps(micToken_v2, paramMessageProp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void verifyMIC(InputStream paramInputStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/* 1263 */     if (this.cipherHelper.getProto() == 0) {
/* 1264 */       MicToken micToken = new MicToken(this, paramInputStream, paramMessageProp);
/* 1265 */       micToken.verify(paramArrayOfbyte, paramInt1, paramInt2);
/* 1266 */       setSequencingAndReplayProps(micToken, paramMessageProp);
/* 1267 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1268 */       MicToken_v2 micToken_v2 = new MicToken_v2(this, paramInputStream, paramMessageProp);
/* 1269 */       micToken_v2.verify(paramArrayOfbyte, paramInt1, paramInt2);
/* 1270 */       setSequencingAndReplayProps(micToken_v2, paramMessageProp);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void verifyMIC(InputStream paramInputStream1, InputStream paramInputStream2, MessageProp paramMessageProp) throws GSSException {
/*      */     byte[] arrayOfByte;
/*      */     try {
/* 1278 */       arrayOfByte = new byte[paramInputStream2.available()];
/* 1279 */       paramInputStream2.read(arrayOfByte);
/* 1280 */     } catch (IOException iOException) {
/*      */       
/* 1282 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1283 */       gSSException.initCause(iOException);
/* 1284 */       throw gSSException;
/*      */     } 
/* 1286 */     verifyMIC(paramInputStream1, arrayOfByte, 0, arrayOfByte.length, paramMessageProp);
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
/*      */   public final byte[] export() throws GSSException {
/* 1298 */     throw new GSSException(16, -1, "GSS Export Context not available");
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
/*      */   public final void dispose() throws GSSException {
/* 1310 */     this.state = 4;
/* 1311 */     this.delegatedCred = null;
/*      */   }
/*      */   
/*      */   public final Provider getProvider() {
/* 1315 */     return Krb5MechFactory.PROVIDER;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setSequencingAndReplayProps(MessageToken paramMessageToken, MessageProp paramMessageProp) {
/* 1324 */     if (this.replayDetState || this.sequenceDetState) {
/* 1325 */       int i = paramMessageToken.getSequenceNumber();
/* 1326 */       this.peerTokenTracker.getProps(i, paramMessageProp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setSequencingAndReplayProps(MessageToken_v2 paramMessageToken_v2, MessageProp paramMessageProp) {
/* 1336 */     if (this.replayDetState || this.sequenceDetState) {
/* 1337 */       int i = paramMessageToken_v2.getSequenceNumber();
/* 1338 */       this.peerTokenTracker.getProps(i, paramMessageProp);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkPermission(String paramString1, String paramString2) {
/* 1343 */     SecurityManager securityManager = System.getSecurityManager();
/* 1344 */     if (securityManager != null) {
/* 1345 */       ServicePermission servicePermission = new ServicePermission(paramString1, paramString2);
/*      */       
/* 1347 */       securityManager.checkPermission(servicePermission);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getHexBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 1353 */     StringBuffer stringBuffer = new StringBuffer();
/* 1354 */     for (byte b = 0; b < paramInt2; b++) {
/*      */       
/* 1356 */       int i = paramArrayOfbyte[b] >> 4 & 0xF;
/* 1357 */       int j = paramArrayOfbyte[b] & 0xF;
/*      */       
/* 1359 */       stringBuffer.append(Integer.toHexString(i));
/* 1360 */       stringBuffer.append(Integer.toHexString(j));
/* 1361 */       stringBuffer.append(' ');
/*      */     } 
/* 1363 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   private static String printState(int paramInt) {
/* 1367 */     switch (paramInt) {
/*      */       case 1:
/* 1369 */         return "STATE_NEW";
/*      */       case 2:
/* 1371 */         return "STATE_IN_PROCESS";
/*      */       case 3:
/* 1373 */         return "STATE_DONE";
/*      */       case 4:
/* 1375 */         return "STATE_DELETED";
/*      */     } 
/* 1377 */     return "Unknown state " + paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   GSSCaller getCaller() {
/* 1383 */     return this.caller;
/*      */   }
/*      */ 
/*      */   
/*      */   static class KerberosSessionKey
/*      */     implements Key
/*      */   {
/*      */     private static final long serialVersionUID = 699307378954123869L;
/*      */     
/*      */     private final EncryptionKey key;
/*      */     
/*      */     KerberosSessionKey(EncryptionKey param1EncryptionKey) {
/* 1395 */       this.key = param1EncryptionKey;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getAlgorithm() {
/* 1400 */       return Integer.toString(this.key.getEType());
/*      */     }
/*      */ 
/*      */     
/*      */     public String getFormat() {
/* 1405 */       return "RAW";
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] getEncoded() {
/* 1410 */       return (byte[])this.key.getBytes().clone();
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1415 */       return "Kerberos session key: etype: " + this.key.getEType() + "\n" + (new HexDumpEncoder())
/* 1416 */         .encodeBuffer(this.key.getBytes());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object inquireSecContext(InquireType paramInquireType) throws GSSException {
/* 1425 */     if (!isEstablished()) {
/* 1426 */       throw new GSSException(12, -1, "Security context not established.");
/*      */     }
/*      */     
/* 1429 */     switch (paramInquireType) {
/*      */       case KRB5_GET_SESSION_KEY:
/* 1431 */         return new KerberosSessionKey(this.key);
/*      */       case KRB5_GET_TKT_FLAGS:
/* 1433 */         return this.tktFlags.clone();
/*      */       case KRB5_GET_AUTHZ_DATA:
/* 1435 */         if (isInitiator()) {
/* 1436 */           throw new GSSException(16, -1, "AuthzData not available on initiator side.");
/*      */         }
/*      */         
/* 1439 */         return (this.authzData == null) ? null : this.authzData.clone();
/*      */       
/*      */       case KRB5_GET_AUTHTIME:
/* 1442 */         return this.authTime;
/*      */     } 
/* 1444 */     throw new GSSException(16, -1, "Inquire type not supported.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTktFlags(boolean[] paramArrayOfboolean) {
/* 1454 */     this.tktFlags = paramArrayOfboolean;
/*      */   }
/*      */   
/*      */   public void setAuthTime(String paramString) {
/* 1458 */     this.authTime = paramString;
/*      */   }
/*      */   
/*      */   public void setAuthzData(AuthorizationDataEntry[] paramArrayOfAuthorizationDataEntry) {
/* 1462 */     this.authzData = paramArrayOfAuthorizationDataEntry;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/Krb5Context.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */