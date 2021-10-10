/*     */ package javax.security.auth.kerberos;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Objects;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.Destroyable;
/*     */ import javax.security.auth.RefreshFailedException;
/*     */ import javax.security.auth.Refreshable;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.KrbException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KerberosTicket
/*     */   implements Destroyable, Refreshable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7395334370157380539L;
/*     */   private static final int FORWARDABLE_TICKET_FLAG = 1;
/*     */   private static final int FORWARDED_TICKET_FLAG = 2;
/*     */   private static final int PROXIABLE_TICKET_FLAG = 3;
/*     */   private static final int PROXY_TICKET_FLAG = 4;
/*     */   private static final int POSTDATED_TICKET_FLAG = 6;
/*     */   private static final int RENEWABLE_TICKET_FLAG = 8;
/*     */   private static final int INITIAL_TICKET_FLAG = 9;
/*     */   private static final int NUM_FLAGS = 32;
/*     */   private byte[] asn1Encoding;
/*     */   private KeyImpl sessionKey;
/*     */   private boolean[] flags;
/*     */   private Date authTime;
/*     */   private Date startTime;
/*     */   private Date endTime;
/*     */   private Date renewTill;
/*     */   private KerberosPrincipal client;
/*     */   private KerberosPrincipal server;
/*     */   private InetAddress[] clientAddresses;
/* 197 */   transient KerberosPrincipal clientAlias = null;
/*     */   
/* 199 */   transient KerberosPrincipal serverAlias = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 205 */   KerberosTicket proxy = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient boolean destroyed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosTicket(byte[] paramArrayOfbyte1, KerberosPrincipal paramKerberosPrincipal1, KerberosPrincipal paramKerberosPrincipal2, byte[] paramArrayOfbyte2, int paramInt, boolean[] paramArrayOfboolean, Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4, InetAddress[] paramArrayOfInetAddress) {
/* 252 */     init(paramArrayOfbyte1, paramKerberosPrincipal1, paramKerberosPrincipal2, paramArrayOfbyte2, paramInt, paramArrayOfboolean, paramDate1, paramDate2, paramDate3, paramDate4, paramArrayOfInetAddress);
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
/*     */   private void init(byte[] paramArrayOfbyte1, KerberosPrincipal paramKerberosPrincipal1, KerberosPrincipal paramKerberosPrincipal2, byte[] paramArrayOfbyte2, int paramInt, boolean[] paramArrayOfboolean, Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4, InetAddress[] paramArrayOfInetAddress) {
/* 267 */     if (paramArrayOfbyte2 == null) {
/* 268 */       throw new IllegalArgumentException("Session key for ticket cannot be null");
/*     */     }
/* 270 */     init(paramArrayOfbyte1, paramKerberosPrincipal1, paramKerberosPrincipal2, new KeyImpl(paramArrayOfbyte2, paramInt), paramArrayOfboolean, paramDate1, paramDate2, paramDate3, paramDate4, paramArrayOfInetAddress);
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
/*     */   private void init(byte[] paramArrayOfbyte, KerberosPrincipal paramKerberosPrincipal1, KerberosPrincipal paramKerberosPrincipal2, KeyImpl paramKeyImpl, boolean[] paramArrayOfboolean, Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4, InetAddress[] paramArrayOfInetAddress) {
/* 285 */     if (paramArrayOfbyte == null) {
/* 286 */       throw new IllegalArgumentException("ASN.1 encoding of ticket cannot be null");
/*     */     }
/* 288 */     this.asn1Encoding = (byte[])paramArrayOfbyte.clone();
/*     */     
/* 290 */     if (paramKerberosPrincipal1 == null) {
/* 291 */       throw new IllegalArgumentException("Client name in ticket cannot be null");
/*     */     }
/* 293 */     this.client = paramKerberosPrincipal1;
/*     */     
/* 295 */     if (paramKerberosPrincipal2 == null) {
/* 296 */       throw new IllegalArgumentException("Server name in ticket cannot be null");
/*     */     }
/* 298 */     this.server = paramKerberosPrincipal2;
/*     */ 
/*     */     
/* 301 */     this.sessionKey = paramKeyImpl;
/*     */     
/* 303 */     if (paramArrayOfboolean != null) {
/* 304 */       if (paramArrayOfboolean.length >= 32) {
/* 305 */         this.flags = (boolean[])paramArrayOfboolean.clone();
/*     */       } else {
/* 307 */         this.flags = new boolean[32];
/*     */         
/* 309 */         for (byte b = 0; b < paramArrayOfboolean.length; b++)
/* 310 */           this.flags[b] = paramArrayOfboolean[b]; 
/*     */       } 
/*     */     } else {
/* 313 */       this.flags = new boolean[32];
/*     */     } 
/* 315 */     if (this.flags[8] && paramDate4 != null) {
/* 316 */       this.renewTill = new Date(paramDate4.getTime());
/*     */     }
/*     */     
/* 319 */     if (paramDate1 != null) {
/* 320 */       this.authTime = new Date(paramDate1.getTime());
/*     */     }
/* 322 */     if (paramDate2 != null) {
/* 323 */       this.startTime = new Date(paramDate2.getTime());
/*     */     } else {
/* 325 */       this.startTime = this.authTime;
/*     */     } 
/*     */     
/* 328 */     if (paramDate3 == null) {
/* 329 */       throw new IllegalArgumentException("End time for ticket validity cannot be null");
/*     */     }
/* 331 */     this.endTime = new Date(paramDate3.getTime());
/*     */     
/* 333 */     if (paramArrayOfInetAddress != null) {
/* 334 */       this.clientAddresses = (InetAddress[])paramArrayOfInetAddress.clone();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final KerberosPrincipal getClient() {
/* 343 */     return this.client;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final KerberosPrincipal getServer() {
/* 352 */     return this.server;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final SecretKey getSessionKey() {
/* 361 */     if (this.destroyed)
/* 362 */       throw new IllegalStateException("This ticket is no longer valid"); 
/* 363 */     return this.sessionKey;
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
/*     */   public final int getSessionKeyType() {
/* 376 */     if (this.destroyed)
/* 377 */       throw new IllegalStateException("This ticket is no longer valid"); 
/* 378 */     return this.sessionKey.getKeyType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isForwardable() {
/* 387 */     return (this.flags == null) ? false : this.flags[1];
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
/*     */   public final boolean isForwarded() {
/* 399 */     return (this.flags == null) ? false : this.flags[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isProxiable() {
/* 408 */     return (this.flags == null) ? false : this.flags[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isProxy() {
/* 417 */     return (this.flags == null) ? false : this.flags[4];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isPostdated() {
/* 427 */     return (this.flags == null) ? false : this.flags[6];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isRenewable() {
/* 438 */     return (this.flags == null) ? false : this.flags[8];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isInitial() {
/* 449 */     return (this.flags == null) ? false : this.flags[9];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean[] getFlags() {
/* 460 */     return (this.flags == null) ? null : (boolean[])this.flags.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Date getAuthTime() {
/* 470 */     return (this.authTime == null) ? null : (Date)this.authTime.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Date getStartTime() {
/* 480 */     return (this.startTime == null) ? null : (Date)this.startTime.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Date getEndTime() {
/* 489 */     return (this.endTime == null) ? null : (Date)this.endTime.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Date getRenewTill() {
/* 499 */     return (this.renewTill == null) ? null : (Date)this.renewTill.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final InetAddress[] getClientAddresses() {
/* 509 */     return (this.clientAddresses == null) ? null : (InetAddress[])this.clientAddresses.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] getEncoded() {
/* 518 */     if (this.destroyed)
/* 519 */       throw new IllegalStateException("This ticket is no longer valid"); 
/* 520 */     return (byte[])this.asn1Encoding.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCurrent() {
/* 525 */     return (this.endTime == null) ? false : ((System.currentTimeMillis() <= this.endTime.getTime()));
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
/*     */   public void refresh() throws RefreshFailedException {
/*     */     IOException iOException;
/* 549 */     if (this.destroyed) {
/* 550 */       throw new RefreshFailedException("A destroyed ticket cannot be renewd.");
/*     */     }
/*     */     
/* 553 */     if (!isRenewable()) {
/* 554 */       throw new RefreshFailedException("This ticket is not renewable");
/*     */     }
/* 556 */     if (getRenewTill() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 561 */     if (System.currentTimeMillis() > getRenewTill().getTime()) {
/* 562 */       throw new RefreshFailedException("This ticket is past its last renewal time.");
/*     */     }
/* 564 */     KrbException krbException = null;
/* 565 */     Credentials credentials = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 576 */       credentials = new Credentials(this.asn1Encoding, this.client.toString(), (this.clientAlias != null) ? this.clientAlias.getName() : null, this.server.toString(), (this.serverAlias != null) ? this.serverAlias.getName() : null, this.sessionKey.getEncoded(), this.sessionKey.getKeyType(), this.flags, this.authTime, this.startTime, this.endTime, this.renewTill, this.clientAddresses);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 583 */       credentials = credentials.renew();
/* 584 */     } catch (KrbException krbException1) {
/* 585 */       krbException = krbException1;
/* 586 */     } catch (IOException iOException1) {
/* 587 */       iOException = iOException1;
/*     */     } 
/*     */     
/* 590 */     if (iOException != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 595 */       RefreshFailedException refreshFailedException = new RefreshFailedException("Failed to renew Kerberos Ticket for client " + this.client + " and server " + this.server + " - " + iOException.getMessage());
/* 596 */       refreshFailedException.initCause(iOException);
/* 597 */       throw refreshFailedException;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 603 */     synchronized (this) {
/*     */       try {
/* 605 */         destroy();
/* 606 */       } catch (DestroyFailedException destroyFailedException) {}
/*     */ 
/*     */       
/* 609 */       init(credentials.getEncoded(), new KerberosPrincipal(credentials
/* 610 */             .getClient().getName()), new KerberosPrincipal(credentials
/* 611 */             .getServer().getName(), 2), credentials
/*     */           
/* 613 */           .getSessionKey().getBytes(), credentials
/* 614 */           .getSessionKey().getEType(), credentials
/* 615 */           .getFlags(), credentials
/* 616 */           .getAuthTime(), credentials
/* 617 */           .getStartTime(), credentials
/* 618 */           .getEndTime(), credentials
/* 619 */           .getRenewTill(), credentials
/* 620 */           .getClientAddresses());
/* 621 */       this.destroyed = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() throws DestroyFailedException {
/* 630 */     if (!this.destroyed) {
/* 631 */       Arrays.fill(this.asn1Encoding, (byte)0);
/* 632 */       this.client = null;
/* 633 */       this.server = null;
/* 634 */       this.sessionKey.destroy();
/* 635 */       this.flags = null;
/* 636 */       this.authTime = null;
/* 637 */       this.startTime = null;
/* 638 */       this.endTime = null;
/* 639 */       this.renewTill = null;
/* 640 */       this.clientAddresses = null;
/* 641 */       this.destroyed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDestroyed() {
/* 649 */     return this.destroyed;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 653 */     if (this.destroyed) {
/* 654 */       return "Destroyed KerberosTicket";
/*     */     }
/* 656 */     StringBuffer stringBuffer = new StringBuffer();
/* 657 */     if (this.clientAddresses != null) {
/* 658 */       for (byte b = 0; b < this.clientAddresses.length; b++) {
/* 659 */         stringBuffer.append("clientAddresses[" + b + "] = " + this.clientAddresses[b]
/* 660 */             .toString());
/*     */       }
/*     */     }
/* 663 */     return "Ticket (hex) = \n" + (new HexDumpEncoder())
/* 664 */       .encodeBuffer(this.asn1Encoding) + "\nClient Principal = " + this.client
/* 665 */       .toString() + "\nServer Principal = " + this.server
/* 666 */       .toString() + "\nSession Key = " + this.sessionKey
/* 667 */       .toString() + "\nForwardable Ticket " + this.flags[1] + "\nForwarded Ticket " + this.flags[2] + "\nProxiable Ticket " + this.flags[3] + "\nProxy Ticket " + this.flags[4] + "\nPostdated Ticket " + this.flags[6] + "\nRenewable Ticket " + this.flags[8] + "\nInitial Ticket " + this.flags[8] + "\nAuth Time = " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 675 */       String.valueOf(this.authTime) + "\nStart Time = " + 
/* 676 */       String.valueOf(this.startTime) + "\nEnd Time = " + this.endTime
/* 677 */       .toString() + "\nRenew Till = " + 
/* 678 */       String.valueOf(this.renewTill) + "\nClient Addresses " + ((this.clientAddresses == null) ? " Null " : (stringBuffer
/*     */       
/* 680 */       .toString() + ((this.proxy == null) ? "" : "\nwith a proxy ticket") + "\n"));
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
/*     */   public int hashCode() {
/* 692 */     int i = 17;
/* 693 */     if (isDestroyed()) {
/* 694 */       return i;
/*     */     }
/* 696 */     i = i * 37 + Arrays.hashCode(getEncoded());
/* 697 */     i = i * 37 + this.endTime.hashCode();
/* 698 */     i = i * 37 + this.client.hashCode();
/* 699 */     i = i * 37 + this.server.hashCode();
/* 700 */     i = i * 37 + this.sessionKey.hashCode();
/*     */ 
/*     */     
/* 703 */     if (this.authTime != null) {
/* 704 */       i = i * 37 + this.authTime.hashCode();
/*     */     }
/*     */ 
/*     */     
/* 708 */     if (this.startTime != null) {
/* 709 */       i = i * 37 + this.startTime.hashCode();
/*     */     }
/*     */ 
/*     */     
/* 713 */     if (this.renewTill != null) {
/* 714 */       i = i * 37 + this.renewTill.hashCode();
/*     */     }
/*     */ 
/*     */     
/* 718 */     i = i * 37 + Arrays.hashCode((Object[])this.clientAddresses);
/*     */     
/* 720 */     if (this.proxy != null) {
/* 721 */       i = i * 37 + this.proxy.hashCode();
/*     */     }
/* 723 */     return i * 37 + Arrays.hashCode(this.flags);
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
/*     */   public boolean equals(Object paramObject) {
/* 740 */     if (paramObject == this) {
/* 741 */       return true;
/*     */     }
/* 743 */     if (!(paramObject instanceof KerberosTicket)) {
/* 744 */       return false;
/*     */     }
/*     */     
/* 747 */     KerberosTicket kerberosTicket = (KerberosTicket)paramObject;
/* 748 */     if (isDestroyed() || kerberosTicket.isDestroyed()) {
/* 749 */       return false;
/*     */     }
/*     */     
/* 752 */     if (!Arrays.equals(getEncoded(), kerberosTicket.getEncoded()) || 
/* 753 */       !this.endTime.equals(kerberosTicket.getEndTime()) || 
/* 754 */       !this.server.equals(kerberosTicket.getServer()) || 
/* 755 */       !this.client.equals(kerberosTicket.getClient()) || 
/* 756 */       !this.sessionKey.equals(kerberosTicket.getSessionKey()) || 
/* 757 */       !Arrays.equals((Object[])this.clientAddresses, (Object[])kerberosTicket.getClientAddresses()) || 
/* 758 */       !Arrays.equals(this.flags, kerberosTicket.getFlags())) {
/* 759 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 763 */     if (this.authTime == null) {
/* 764 */       if (kerberosTicket.getAuthTime() != null) {
/* 765 */         return false;
/*     */       }
/* 767 */     } else if (!this.authTime.equals(kerberosTicket.getAuthTime())) {
/* 768 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 772 */     if (this.startTime == null) {
/* 773 */       if (kerberosTicket.getStartTime() != null) {
/* 774 */         return false;
/*     */       }
/* 776 */     } else if (!this.startTime.equals(kerberosTicket.getStartTime())) {
/* 777 */       return false;
/*     */     } 
/*     */     
/* 780 */     if (this.renewTill == null) {
/* 781 */       if (kerberosTicket.getRenewTill() != null) {
/* 782 */         return false;
/*     */       }
/* 784 */     } else if (!this.renewTill.equals(kerberosTicket.getRenewTill())) {
/* 785 */       return false;
/*     */     } 
/*     */     
/* 788 */     if (!Objects.equals(this.proxy, kerberosTicket.proxy)) {
/* 789 */       return false;
/*     */     }
/*     */     
/* 792 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 797 */     paramObjectInputStream.defaultReadObject();
/* 798 */     if (this.sessionKey == null) {
/* 799 */       throw new InvalidObjectException("Session key cannot be null");
/*     */     }
/*     */     try {
/* 802 */       init(this.asn1Encoding, this.client, this.server, this.sessionKey, this.flags, this.authTime, this.startTime, this.endTime, this.renewTill, this.clientAddresses);
/*     */     
/*     */     }
/* 805 */     catch (IllegalArgumentException illegalArgumentException) {
/* 806 */       throw (InvalidObjectException)(new InvalidObjectException(illegalArgumentException
/* 807 */           .getMessage())).initCause(illegalArgumentException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/kerberos/KerberosTicket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */