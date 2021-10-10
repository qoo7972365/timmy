/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ import javax.security.auth.kerberos.DelegationPermission;
/*     */ import org.ietf.jgss.ChannelBinding;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import sun.security.jgss.GSSToken;
/*     */ import sun.security.krb5.Checksum;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbCred;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class InitialToken
/*     */   extends Krb5Token
/*     */ {
/*     */   private static final int CHECKSUM_TYPE = 32771;
/*     */   private static final int CHECKSUM_LENGTH_SIZE = 4;
/*     */   private static final int CHECKSUM_BINDINGS_SIZE = 16;
/*     */   private static final int CHECKSUM_FLAGS_SIZE = 4;
/*     */   private static final int CHECKSUM_DELEG_OPT_SIZE = 2;
/*     */   private static final int CHECKSUM_DELEG_LGTH_SIZE = 2;
/*     */   private static final int CHECKSUM_DELEG_FLAG = 1;
/*     */   private static final int CHECKSUM_MUTUAL_FLAG = 2;
/*     */   private static final int CHECKSUM_REPLAY_FLAG = 4;
/*     */   private static final int CHECKSUM_SEQUENCE_FLAG = 8;
/*     */   private static final int CHECKSUM_CONF_FLAG = 16;
/*     */   private static final int CHECKSUM_INTEG_FLAG = 32;
/*  57 */   private final byte[] CHECKSUM_FIRST_BYTES = new byte[] { 16, 0, 0, 0 };
/*     */   
/*     */   private static final int CHANNEL_BINDING_AF_INET = 2;
/*     */   
/*     */   private static final int CHANNEL_BINDING_AF_INET6 = 24;
/*     */   
/*     */   private static final int CHANNEL_BINDING_AF_NULL_ADDR = 255;
/*     */   private static final int Inet4_ADDRSZ = 4;
/*     */   private static final int Inet6_ADDRSZ = 16;
/*     */   
/*     */   protected class OverloadedChecksum
/*     */   {
/*  69 */     private byte[] checksumBytes = null;
/*  70 */     private Credentials delegCreds = null;
/*  71 */     private int flags = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OverloadedChecksum(Krb5Context param1Krb5Context, Credentials param1Credentials1, Credentials param1Credentials2) throws KrbException, IOException, GSSException {
/*  82 */       byte[] arrayOfByte1 = null;
/*  83 */       byte b = 0;
/*  84 */       int i = 24;
/*     */ 
/*     */       
/*  87 */       if (!param1Credentials1.isForwardable()) {
/*  88 */         param1Krb5Context.setCredDelegState(false);
/*  89 */         param1Krb5Context.setDelegPolicyState(false);
/*  90 */       } else if (param1Krb5Context.getCredDelegState()) {
/*  91 */         if (param1Krb5Context.getDelegPolicyState() && 
/*  92 */           !param1Credentials2.checkDelegate())
/*     */         {
/*  94 */           param1Krb5Context.setDelegPolicyState(false);
/*     */         }
/*     */       }
/*  97 */       else if (param1Krb5Context.getDelegPolicyState()) {
/*  98 */         if (param1Credentials2.checkDelegate()) {
/*  99 */           param1Krb5Context.setCredDelegState(true);
/*     */         } else {
/* 101 */           param1Krb5Context.setDelegPolicyState(false);
/*     */         } 
/*     */       } 
/*     */       
/* 105 */       if (param1Krb5Context.getCredDelegState()) {
/* 106 */         KrbCred krbCred = null;
/*     */         
/* 108 */         CipherHelper cipherHelper = param1Krb5Context.getCipherHelper(param1Credentials2.getSessionKey());
/* 109 */         if (useNullKey(cipherHelper)) {
/* 110 */           krbCred = new KrbCred(param1Credentials1, param1Credentials2, EncryptionKey.NULL_KEY);
/*     */         }
/*     */         else {
/*     */           
/* 114 */           krbCred = new KrbCred(param1Credentials1, param1Credentials2, param1Credentials2.getSessionKey());
/*     */         } 
/* 116 */         arrayOfByte1 = krbCred.getMessage();
/* 117 */         i += 4 + arrayOfByte1.length;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 122 */       this.checksumBytes = new byte[i];
/*     */       
/* 124 */       this.checksumBytes[b++] = InitialToken.this.CHECKSUM_FIRST_BYTES[0];
/* 125 */       this.checksumBytes[b++] = InitialToken.this.CHECKSUM_FIRST_BYTES[1];
/* 126 */       this.checksumBytes[b++] = InitialToken.this.CHECKSUM_FIRST_BYTES[2];
/* 127 */       this.checksumBytes[b++] = InitialToken.this.CHECKSUM_FIRST_BYTES[3];
/*     */       
/* 129 */       ChannelBinding channelBinding = param1Krb5Context.getChannelBinding();
/* 130 */       if (channelBinding != null) {
/*     */         
/* 132 */         byte[] arrayOfByte = InitialToken.this.computeChannelBinding(param1Krb5Context.getChannelBinding());
/* 133 */         System.arraycopy(arrayOfByte, 0, this.checksumBytes, b, arrayOfByte.length);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       b += 16;
/*     */       
/* 141 */       if (param1Krb5Context.getCredDelegState())
/* 142 */         this.flags |= 0x1; 
/* 143 */       if (param1Krb5Context.getMutualAuthState())
/* 144 */         this.flags |= 0x2; 
/* 145 */       if (param1Krb5Context.getReplayDetState())
/* 146 */         this.flags |= 0x4; 
/* 147 */       if (param1Krb5Context.getSequenceDetState())
/* 148 */         this.flags |= 0x8; 
/* 149 */       if (param1Krb5Context.getIntegState())
/* 150 */         this.flags |= 0x20; 
/* 151 */       if (param1Krb5Context.getConfState()) {
/* 152 */         this.flags |= 0x10;
/*     */       }
/* 154 */       byte[] arrayOfByte2 = new byte[4];
/* 155 */       GSSToken.writeLittleEndian(this.flags, arrayOfByte2);
/* 156 */       this.checksumBytes[b++] = arrayOfByte2[0];
/* 157 */       this.checksumBytes[b++] = arrayOfByte2[1];
/* 158 */       this.checksumBytes[b++] = arrayOfByte2[2];
/* 159 */       this.checksumBytes[b++] = arrayOfByte2[3];
/*     */       
/* 161 */       if (param1Krb5Context.getCredDelegState()) {
/*     */ 
/*     */         
/* 164 */         PrincipalName principalName = param1Credentials2.getServer();
/*     */ 
/*     */         
/* 167 */         StringBuffer stringBuffer = new StringBuffer("\"");
/* 168 */         stringBuffer.append(principalName.getName()).append('"');
/* 169 */         String str = principalName.getRealmAsString();
/* 170 */         stringBuffer.append(" \"krbtgt/").append(str).append('@');
/* 171 */         stringBuffer.append(str).append('"');
/* 172 */         SecurityManager securityManager = System.getSecurityManager();
/* 173 */         if (securityManager != null) {
/*     */           
/* 175 */           DelegationPermission delegationPermission = new DelegationPermission(stringBuffer.toString());
/* 176 */           securityManager.checkPermission(delegationPermission);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 185 */         this.checksumBytes[b++] = 1;
/* 186 */         this.checksumBytes[b++] = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 193 */         if (arrayOfByte1.length > 65535) {
/* 194 */           throw new GSSException(11, -1, "Incorrect message length");
/*     */         }
/*     */         
/* 197 */         GSSToken.writeLittleEndian(arrayOfByte1.length, arrayOfByte2);
/* 198 */         this.checksumBytes[b++] = arrayOfByte2[0];
/* 199 */         this.checksumBytes[b++] = arrayOfByte2[1];
/* 200 */         System.arraycopy(arrayOfByte1, 0, this.checksumBytes, b, arrayOfByte1.length);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OverloadedChecksum(Krb5Context param1Krb5Context, Checksum param1Checksum, EncryptionKey param1EncryptionKey1, EncryptionKey param1EncryptionKey2) throws GSSException, KrbException, IOException {
/* 217 */       boolean bool = false;
/*     */       
/* 219 */       if (param1Checksum == null) {
/* 220 */         GSSException gSSException = new GSSException(11, -1, "No cksum in AP_REQ's authenticator");
/*     */         
/* 222 */         gSSException.initCause(new KrbException(50));
/* 223 */         throw gSSException;
/*     */       } 
/* 225 */       this.checksumBytes = param1Checksum.getBytes();
/*     */       
/* 227 */       if (this.checksumBytes[0] != InitialToken.this.CHECKSUM_FIRST_BYTES[0] || this.checksumBytes[1] != InitialToken.this
/* 228 */         .CHECKSUM_FIRST_BYTES[1] || this.checksumBytes[2] != InitialToken.this
/* 229 */         .CHECKSUM_FIRST_BYTES[2] || this.checksumBytes[3] != InitialToken.this
/* 230 */         .CHECKSUM_FIRST_BYTES[3]) {
/* 231 */         throw new GSSException(11, -1, "Incorrect checksum");
/*     */       }
/*     */ 
/*     */       
/* 235 */       ChannelBinding channelBinding = param1Krb5Context.getChannelBinding();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 246 */       if (channelBinding != null) {
/* 247 */         byte[] arrayOfByte1 = new byte[16];
/* 248 */         System.arraycopy(this.checksumBytes, 4, arrayOfByte1, 0, 16);
/*     */ 
/*     */         
/* 251 */         byte[] arrayOfByte2 = new byte[16];
/* 252 */         if (!Arrays.equals(arrayOfByte2, arrayOfByte1)) {
/*     */           
/* 254 */           byte[] arrayOfByte = InitialToken.this.computeChannelBinding(channelBinding);
/* 255 */           if (!Arrays.equals(arrayOfByte, arrayOfByte1))
/*     */           {
/* 257 */             throw new GSSException(1, -1, "Bytes mismatch!");
/*     */           }
/*     */         } else {
/*     */           
/* 261 */           throw new GSSException(1, -1, "Token missing ChannelBinding!");
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 266 */       this.flags = GSSToken.readLittleEndian(this.checksumBytes, 20, 4);
/*     */       
/* 268 */       if ((this.flags & 0x1) > 0) {
/*     */         KrbCred krbCred;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 276 */         int i = GSSToken.readLittleEndian(this.checksumBytes, 26, 2);
/* 277 */         byte[] arrayOfByte = new byte[i];
/* 278 */         System.arraycopy(this.checksumBytes, 28, arrayOfByte, 0, i);
/*     */ 
/*     */         
/*     */         try {
/* 282 */           krbCred = new KrbCred(arrayOfByte, param1EncryptionKey1);
/* 283 */         } catch (KrbException krbException) {
/* 284 */           if (param1EncryptionKey2 != null) {
/* 285 */             krbCred = new KrbCred(arrayOfByte, param1EncryptionKey2);
/*     */           } else {
/* 287 */             throw krbException;
/*     */           } 
/*     */         } 
/* 290 */         this.delegCreds = krbCred.getDelegatedCreds()[0];
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean useNullKey(CipherHelper param1CipherHelper) {
/* 296 */       boolean bool = true;
/*     */       
/* 298 */       if (param1CipherHelper.getProto() == 1 || param1CipherHelper.isArcFour()) {
/* 299 */         bool = false;
/*     */       }
/* 301 */       return bool;
/*     */     }
/*     */     
/*     */     public Checksum getChecksum() throws KrbException {
/* 305 */       return new Checksum(this.checksumBytes, 32771);
/*     */     }
/*     */     
/*     */     public Credentials getDelegatedCreds() {
/* 309 */       return this.delegCreds;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setContextFlags(Krb5Context param1Krb5Context) {
/* 315 */       if ((this.flags & 0x1) > 0) {
/* 316 */         param1Krb5Context.setCredDelegState(true);
/*     */       }
/* 318 */       if ((this.flags & 0x2) == 0) {
/* 319 */         param1Krb5Context.setMutualAuthState(false);
/*     */       }
/* 321 */       if ((this.flags & 0x4) == 0) {
/* 322 */         param1Krb5Context.setReplayDetState(false);
/*     */       }
/* 324 */       if ((this.flags & 0x8) == 0) {
/* 325 */         param1Krb5Context.setSequenceDetState(false);
/*     */       }
/* 327 */       if ((this.flags & 0x10) == 0) {
/* 328 */         param1Krb5Context.setConfState(false);
/*     */       }
/* 330 */       if ((this.flags & 0x20) == 0) {
/* 331 */         param1Krb5Context.setIntegState(false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private int getAddrType(InetAddress paramInetAddress) {
/* 337 */     char c = 'ÿ';
/*     */     
/* 339 */     if (paramInetAddress instanceof java.net.Inet4Address) {
/* 340 */       c = '\002';
/* 341 */     } else if (paramInetAddress instanceof java.net.Inet6Address) {
/* 342 */       c = '\030';
/* 343 */     }  return c;
/*     */   }
/*     */   
/*     */   private byte[] getAddrBytes(InetAddress paramInetAddress) throws GSSException {
/* 347 */     int i = getAddrType(paramInetAddress);
/* 348 */     byte[] arrayOfByte = paramInetAddress.getAddress();
/* 349 */     if (arrayOfByte != null) {
/* 350 */       switch (i) {
/*     */         case 2:
/* 352 */           if (arrayOfByte.length != 4) {
/* 353 */             throw new GSSException(11, -1, "Incorrect AF-INET address length in ChannelBinding.");
/*     */           }
/*     */           
/* 356 */           return arrayOfByte;
/*     */         case 24:
/* 358 */           if (arrayOfByte.length != 16) {
/* 359 */             throw new GSSException(11, -1, "Incorrect AF-INET6 address length in ChannelBinding.");
/*     */           }
/*     */           
/* 362 */           return arrayOfByte;
/*     */       } 
/* 364 */       throw new GSSException(11, -1, "Cannot handle non AF-INET addresses in ChannelBinding.");
/*     */     } 
/*     */ 
/*     */     
/* 368 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] computeChannelBinding(ChannelBinding paramChannelBinding) throws GSSException {
/* 374 */     InetAddress inetAddress1 = paramChannelBinding.getInitiatorAddress();
/* 375 */     InetAddress inetAddress2 = paramChannelBinding.getAcceptorAddress();
/* 376 */     int i = 20;
/*     */     
/* 378 */     int j = getAddrType(inetAddress1);
/* 379 */     int k = getAddrType(inetAddress2);
/*     */     
/* 381 */     byte[] arrayOfByte1 = null;
/* 382 */     if (inetAddress1 != null) {
/* 383 */       arrayOfByte1 = getAddrBytes(inetAddress1);
/* 384 */       i += arrayOfByte1.length;
/*     */     } 
/*     */     
/* 387 */     byte[] arrayOfByte2 = null;
/* 388 */     if (inetAddress2 != null) {
/* 389 */       arrayOfByte2 = getAddrBytes(inetAddress2);
/* 390 */       i += arrayOfByte2.length;
/*     */     } 
/*     */     
/* 393 */     byte[] arrayOfByte3 = paramChannelBinding.getApplicationData();
/* 394 */     if (arrayOfByte3 != null) {
/* 395 */       i += arrayOfByte3.length;
/*     */     }
/*     */     
/* 398 */     byte[] arrayOfByte4 = new byte[i];
/*     */     
/* 400 */     int m = 0;
/*     */     
/* 402 */     writeLittleEndian(j, arrayOfByte4, m);
/* 403 */     m += true;
/*     */     
/* 405 */     if (arrayOfByte1 != null) {
/* 406 */       writeLittleEndian(arrayOfByte1.length, arrayOfByte4, m);
/* 407 */       m += true;
/* 408 */       System.arraycopy(arrayOfByte1, 0, arrayOfByte4, m, arrayOfByte1.length);
/*     */       
/* 410 */       m += arrayOfByte1.length;
/*     */     } else {
/*     */       
/* 413 */       m += 4;
/*     */     } 
/*     */     
/* 416 */     writeLittleEndian(k, arrayOfByte4, m);
/* 417 */     m += 4;
/*     */     
/* 419 */     if (arrayOfByte2 != null) {
/* 420 */       writeLittleEndian(arrayOfByte2.length, arrayOfByte4, m);
/* 421 */       m += 4;
/* 422 */       System.arraycopy(arrayOfByte2, 0, arrayOfByte4, m, arrayOfByte2.length);
/*     */       
/* 424 */       m += arrayOfByte2.length;
/*     */     } else {
/*     */       
/* 427 */       m += 4;
/*     */     } 
/*     */     
/* 430 */     if (arrayOfByte3 != null) {
/* 431 */       writeLittleEndian(arrayOfByte3.length, arrayOfByte4, m);
/* 432 */       m += 4;
/* 433 */       System.arraycopy(arrayOfByte3, 0, arrayOfByte4, m, arrayOfByte3.length);
/*     */       
/* 435 */       m += arrayOfByte3.length;
/*     */     } else {
/*     */       
/* 438 */       m += 4;
/*     */     } 
/*     */     
/*     */     try {
/* 442 */       MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/* 443 */       return messageDigest.digest(arrayOfByte4);
/* 444 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 445 */       throw new GSSException(11, -1, "Could not get MD5 Message Digest - " + noSuchAlgorithmException
/*     */           
/* 447 */           .getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract byte[] encode() throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/InitialToken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */