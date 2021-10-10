/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.internal.EncKrbCredPart;
/*     */ import sun.security.krb5.internal.HostAddresses;
/*     */ import sun.security.krb5.internal.KDCOptions;
/*     */ import sun.security.krb5.internal.KRBCred;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.KrbCredInfo;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ import sun.security.krb5.internal.TicketFlags;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KrbCred
/*     */ {
/*  49 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */   
/*  51 */   private byte[] obuf = null;
/*  52 */   private KRBCred credMessg = null;
/*  53 */   private Ticket ticket = null;
/*  54 */   private EncKrbCredPart encPart = null;
/*  55 */   private Credentials creds = null;
/*  56 */   private KerberosTime timeStamp = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KrbCred(Credentials paramCredentials1, Credentials paramCredentials2, EncryptionKey paramEncryptionKey) throws KrbException, IOException {
/*  64 */     PrincipalName principalName1 = paramCredentials1.getClient();
/*  65 */     PrincipalName principalName2 = paramCredentials1.getServer();
/*  66 */     if (!paramCredentials2.getClient().equals(principalName1)) {
/*  67 */       throw new KrbException(60, "Client principal does not match");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     KDCOptions kDCOptions = new KDCOptions();
/*  75 */     kDCOptions.set(2, true);
/*  76 */     kDCOptions.set(1, true);
/*     */     
/*  78 */     KrbTgsReq krbTgsReq = new KrbTgsReq(kDCOptions, paramCredentials1, principalName2, null, null, null, null, null, null, null, null, null);
/*     */ 
/*     */ 
/*     */     
/*  82 */     this.credMessg = createMessage(krbTgsReq.sendAndGetCreds(), paramEncryptionKey);
/*     */     
/*  84 */     this.obuf = this.credMessg.asn1Encode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   KRBCred createMessage(Credentials paramCredentials, EncryptionKey paramEncryptionKey) throws KrbException, IOException {
/*  91 */     EncryptionKey encryptionKey = paramCredentials.getSessionKey();
/*  92 */     PrincipalName principalName1 = paramCredentials.getClient();
/*  93 */     PrincipalName principalName2 = paramCredentials.getServer();
/*     */     
/*  95 */     KrbCredInfo krbCredInfo = new KrbCredInfo(encryptionKey, principalName1, paramCredentials.flags, paramCredentials.authTime, paramCredentials.startTime, paramCredentials.endTime, paramCredentials.renewTill, principalName2, paramCredentials.cAddr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     this.timeStamp = KerberosTime.now();
/* 102 */     KrbCredInfo[] arrayOfKrbCredInfo = { krbCredInfo };
/* 103 */     EncKrbCredPart encKrbCredPart = new EncKrbCredPart(arrayOfKrbCredInfo, this.timeStamp, null, null, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     EncryptedData encryptedData = new EncryptedData(paramEncryptionKey, encKrbCredPart.asn1Encode(), 14);
/*     */     
/* 110 */     Ticket[] arrayOfTicket = { paramCredentials.ticket };
/*     */     
/* 112 */     this.credMessg = new KRBCred(arrayOfTicket, encryptedData);
/*     */     
/* 114 */     return this.credMessg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KrbCred(byte[] paramArrayOfbyte, EncryptionKey paramEncryptionKey) throws KrbException, IOException {
/* 121 */     this.credMessg = new KRBCred(paramArrayOfbyte);
/*     */     
/* 123 */     this.ticket = this.credMessg.tickets[0];
/*     */     
/* 125 */     if (this.credMessg.encPart.getEType() == 0) {
/* 126 */       paramEncryptionKey = EncryptionKey.NULL_KEY;
/*     */     }
/* 128 */     byte[] arrayOfByte1 = this.credMessg.encPart.decrypt(paramEncryptionKey, 14);
/*     */     
/* 130 */     byte[] arrayOfByte2 = this.credMessg.encPart.reset(arrayOfByte1);
/* 131 */     DerValue derValue = new DerValue(arrayOfByte2);
/* 132 */     EncKrbCredPart encKrbCredPart = new EncKrbCredPart(derValue);
/*     */     
/* 134 */     this.timeStamp = encKrbCredPart.timeStamp;
/*     */     
/* 136 */     KrbCredInfo krbCredInfo = encKrbCredPart.ticketInfo[0];
/* 137 */     EncryptionKey encryptionKey = krbCredInfo.key;
/* 138 */     PrincipalName principalName1 = krbCredInfo.pname;
/* 139 */     TicketFlags ticketFlags = krbCredInfo.flags;
/* 140 */     KerberosTime kerberosTime1 = krbCredInfo.authtime;
/* 141 */     KerberosTime kerberosTime2 = krbCredInfo.starttime;
/* 142 */     KerberosTime kerberosTime3 = krbCredInfo.endtime;
/* 143 */     KerberosTime kerberosTime4 = krbCredInfo.renewTill;
/* 144 */     PrincipalName principalName2 = krbCredInfo.sname;
/* 145 */     HostAddresses hostAddresses = krbCredInfo.caddr;
/*     */     
/* 147 */     if (DEBUG) {
/* 148 */       System.out.println(">>>Delegated Creds have pname=" + principalName1 + " sname=" + principalName2 + " authtime=" + kerberosTime1 + " starttime=" + kerberosTime2 + " endtime=" + kerberosTime3 + "renewTill=" + kerberosTime4);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     this.creds = new Credentials(this.ticket, principalName1, null, principalName2, null, encryptionKey, ticketFlags, kerberosTime1, kerberosTime2, kerberosTime3, kerberosTime4, hostAddresses);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials[] getDelegatedCreds() {
/* 164 */     return new Credentials[] { this.creds };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getMessage() {
/* 172 */     return this.obuf;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KrbCred.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */