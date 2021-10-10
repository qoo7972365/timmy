/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import com.sun.security.jgss.AuthorizationDataEntry;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.InetAddress;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.krb5.Checksum;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbApReq;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.internal.AuthorizationData;
/*     */ import sun.security.krb5.internal.KerberosTime;
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
/*     */ class InitSecContextToken
/*     */   extends InitialToken
/*     */ {
/*     */   private static final boolean ACCEPTOR_USE_INITIATOR_SEQNUM;
/*     */   
/*     */   static {
/*  56 */     String str1 = "sun.security.krb5.acceptor.sequence.number.nonmutual";
/*  57 */     String str2 = GetPropertyAction.privilegedGetProperty(str1, "initiator");
/*  58 */     if (str2.equals("initiator")) {
/*  59 */       ACCEPTOR_USE_INITIATOR_SEQNUM = true;
/*  60 */     } else if (str2.equals("zero") || str2.equals("0")) {
/*  61 */       ACCEPTOR_USE_INITIATOR_SEQNUM = false;
/*     */     } else {
/*  63 */       throw new AssertionError("Unrecognized value for " + str1 + ": " + str2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  68 */   private KrbApReq apReq = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InitSecContextToken(Krb5Context paramKrb5Context, Credentials paramCredentials1, Credentials paramCredentials2) throws KrbException, IOException, GSSException {
/*  84 */     boolean bool = paramKrb5Context.getMutualAuthState();
/*  85 */     boolean bool1 = true;
/*  86 */     boolean bool2 = true;
/*     */     
/*  88 */     InitialToken.OverloadedChecksum overloadedChecksum = new InitialToken.OverloadedChecksum(this, paramKrb5Context, paramCredentials1, paramCredentials2);
/*     */ 
/*     */     
/*  91 */     Checksum checksum = overloadedChecksum.getChecksum();
/*     */     
/*  93 */     paramKrb5Context.setTktFlags(paramCredentials2.getFlags());
/*  94 */     paramKrb5Context.setAuthTime((new KerberosTime(paramCredentials2
/*  95 */           .getAuthTime())).toString());
/*  96 */     this.apReq = new KrbApReq(paramCredentials2, bool, bool1, bool2, checksum);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     paramKrb5Context.resetMySequenceNumber(this.apReq.getSeqNumber().intValue());
/*     */     
/* 104 */     EncryptionKey encryptionKey = this.apReq.getSubKey();
/* 105 */     if (encryptionKey != null) {
/* 106 */       paramKrb5Context.setKey(1, encryptionKey);
/*     */     } else {
/* 108 */       paramKrb5Context.setKey(0, paramCredentials2.getSessionKey());
/*     */     } 
/* 110 */     if (!bool) {
/* 111 */       paramKrb5Context.resetPeerSequenceNumber(ACCEPTOR_USE_INITIATOR_SEQNUM ? this.apReq
/*     */           
/* 113 */           .getSeqNumber().intValue() : 0);
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
/*     */   InitSecContextToken(Krb5Context paramKrb5Context, Krb5AcceptCredential paramKrb5AcceptCredential, InputStream paramInputStream) throws IOException, GSSException, KrbException {
/* 125 */     int i = paramInputStream.read() << 8 | paramInputStream.read();
/*     */     
/* 127 */     if (i != 256) {
/* 128 */       throw new GSSException(10, -1, "AP_REQ token id does not match!");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 133 */     byte[] arrayOfByte = (new DerValue(paramInputStream)).toByteArray();
/*     */ 
/*     */     
/* 136 */     InetAddress inetAddress = null;
/* 137 */     if (paramKrb5Context.getChannelBinding() != null) {
/* 138 */       inetAddress = paramKrb5Context.getChannelBinding().getInitiatorAddress();
/*     */     }
/* 140 */     this.apReq = new KrbApReq(arrayOfByte, paramKrb5AcceptCredential, inetAddress);
/*     */ 
/*     */     
/* 143 */     EncryptionKey encryptionKey1 = this.apReq.getCreds().getSessionKey();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     EncryptionKey encryptionKey2 = this.apReq.getSubKey();
/* 151 */     if (encryptionKey2 != null) {
/* 152 */       paramKrb5Context.setKey(1, encryptionKey2);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 158 */       paramKrb5Context.setKey(0, encryptionKey1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 163 */     InitialToken.OverloadedChecksum overloadedChecksum = new InitialToken.OverloadedChecksum(this, paramKrb5Context, this.apReq.getChecksum(), encryptionKey1, encryptionKey2);
/* 164 */     overloadedChecksum.setContextFlags(paramKrb5Context);
/* 165 */     Credentials credentials = overloadedChecksum.getDelegatedCreds();
/* 166 */     if (credentials != null) {
/*     */       
/* 168 */       Krb5InitCredential krb5InitCredential = Krb5InitCredential.getInstance((Krb5NameElement)paramKrb5Context
/* 169 */           .getSrcName(), credentials);
/*     */       
/* 171 */       paramKrb5Context.setDelegCred(krb5InitCredential);
/*     */     } 
/*     */     
/* 174 */     Integer integer = this.apReq.getSeqNumber();
/*     */     
/* 176 */     boolean bool = (integer != null) ? integer.intValue() : false;
/*     */     
/* 178 */     paramKrb5Context.resetPeerSequenceNumber(bool);
/* 179 */     if (!paramKrb5Context.getMutualAuthState()) {
/* 180 */       paramKrb5Context.resetMySequenceNumber(ACCEPTOR_USE_INITIATOR_SEQNUM ? bool : 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 185 */     paramKrb5Context.setAuthTime((new KerberosTime(this.apReq
/* 186 */           .getCreds().getAuthTime())).toString());
/* 187 */     paramKrb5Context.setTktFlags(this.apReq.getCreds().getFlags());
/* 188 */     AuthorizationData authorizationData = this.apReq.getCreds().getAuthzData();
/* 189 */     if (authorizationData == null) {
/* 190 */       paramKrb5Context.setAuthzData(null);
/*     */     } else {
/*     */       
/* 193 */       AuthorizationDataEntry[] arrayOfAuthorizationDataEntry = new AuthorizationDataEntry[authorizationData.count()];
/* 194 */       for (byte b = 0; b < authorizationData.count(); b++) {
/* 195 */         arrayOfAuthorizationDataEntry[b] = new AuthorizationDataEntry(
/* 196 */             (authorizationData.item(b)).adType, (authorizationData.item(b)).adData);
/*     */       }
/* 198 */       paramKrb5Context.setAuthzData(arrayOfAuthorizationDataEntry);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final KrbApReq getKrbApReq() {
/* 203 */     return this.apReq;
/*     */   }
/*     */   
/*     */   public final byte[] encode() throws IOException {
/* 207 */     byte[] arrayOfByte1 = this.apReq.getMessage();
/* 208 */     byte[] arrayOfByte2 = new byte[2 + arrayOfByte1.length];
/* 209 */     writeInt(256, arrayOfByte2, 0);
/* 210 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 2, arrayOfByte1.length);
/*     */ 
/*     */     
/* 213 */     return arrayOfByte2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/InitSecContextToken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */