/*     */ package sun.security.jgss.wrapper;
/*     */ 
/*     */ import com.sun.security.jgss.InquireType;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Provider;
/*     */ import javax.security.auth.kerberos.DelegationPermission;
/*     */ import org.ietf.jgss.ChannelBinding;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.MessageProp;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.GSSExceptionImpl;
/*     */ import sun.security.jgss.GSSHeader;
/*     */ import sun.security.jgss.GSSUtil;
/*     */ import sun.security.jgss.spi.GSSContextSpi;
/*     */ import sun.security.jgss.spi.GSSCredentialSpi;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.jgss.spnego.NegTokenInit;
/*     */ import sun.security.jgss.spnego.NegTokenTarg;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NativeGSSContext
/*     */   implements GSSContextSpi
/*     */ {
/*     */   private static final int GSS_C_DELEG_FLAG = 1;
/*     */   private static final int GSS_C_MUTUAL_FLAG = 2;
/*     */   private static final int GSS_C_REPLAY_FLAG = 4;
/*     */   private static final int GSS_C_SEQUENCE_FLAG = 8;
/*     */   private static final int GSS_C_CONF_FLAG = 16;
/*     */   private static final int GSS_C_INTEG_FLAG = 32;
/*     */   private static final int GSS_C_ANON_FLAG = 64;
/*     */   private static final int GSS_C_PROT_READY_FLAG = 128;
/*     */   private static final int GSS_C_TRANS_FLAG = 256;
/*     */   private static final int NUM_OF_INQUIRE_VALUES = 6;
/*  63 */   private long pContext = 0L;
/*     */   
/*     */   private GSSNameElement srcName;
/*     */   private GSSNameElement targetName;
/*     */   private GSSCredElement cred;
/*     */   private boolean isInitiator;
/*     */   private boolean isEstablished;
/*     */   private Oid actualMech;
/*     */   private ChannelBinding cb;
/*     */   private GSSCredElement delegatedCred;
/*     */   private int flags;
/*  74 */   private int lifetime = 0;
/*     */ 
/*     */   
/*     */   private final GSSLibStub cStub;
/*     */   
/*     */   private boolean skipDelegPermCheck;
/*     */   
/*     */   private boolean skipServicePermCheck;
/*     */ 
/*     */   
/*     */   private static Oid getMechFromSpNegoToken(byte[] paramArrayOfbyte, boolean paramBoolean) throws GSSException {
/*  85 */     Oid oid = null;
/*  86 */     if (paramBoolean) {
/*  87 */       GSSHeader gSSHeader = null;
/*     */       try {
/*  89 */         gSSHeader = new GSSHeader(new ByteArrayInputStream(paramArrayOfbyte));
/*  90 */       } catch (IOException iOException) {
/*  91 */         throw new GSSExceptionImpl(11, iOException);
/*     */       } 
/*  93 */       int i = gSSHeader.getMechTokenLength();
/*  94 */       byte[] arrayOfByte = new byte[i];
/*  95 */       System.arraycopy(paramArrayOfbyte, paramArrayOfbyte.length - i, arrayOfByte, 0, arrayOfByte.length);
/*     */ 
/*     */       
/*  98 */       NegTokenInit negTokenInit = new NegTokenInit(arrayOfByte);
/*  99 */       if (negTokenInit.getMechToken() != null) {
/* 100 */         Oid[] arrayOfOid = negTokenInit.getMechTypeList();
/* 101 */         oid = arrayOfOid[0];
/*     */       } 
/*     */     } else {
/* 104 */       NegTokenTarg negTokenTarg = new NegTokenTarg(paramArrayOfbyte);
/* 105 */       oid = negTokenTarg.getSupportedMech();
/*     */     } 
/* 107 */     return oid;
/*     */   }
/*     */ 
/*     */   
/*     */   private void doServicePermCheck() throws GSSException {
/* 112 */     if (System.getSecurityManager() != null) {
/* 113 */       String str1 = this.isInitiator ? "initiate" : "accept";
/*     */ 
/*     */       
/* 116 */       if (GSSUtil.isSpNegoMech(this.cStub.getMech()) && this.isInitiator && !this.isEstablished)
/*     */       {
/* 118 */         if (this.srcName == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 123 */           GSSCredElement gSSCredElement = new GSSCredElement(null, this.lifetime, 1, GSSLibStub.getInstance(GSSUtil.GSS_KRB5_MECH_OID));
/* 124 */           gSSCredElement.dispose();
/*     */         } else {
/* 126 */           String str = Krb5Util.getTGSName(this.srcName);
/* 127 */           Krb5Util.checkServicePermission(str, str1);
/*     */         } 
/*     */       }
/* 130 */       String str2 = this.targetName.getKrbName();
/* 131 */       Krb5Util.checkServicePermission(str2, str1);
/* 132 */       this.skipServicePermCheck = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void doDelegPermCheck() throws GSSException {
/* 138 */     SecurityManager securityManager = System.getSecurityManager();
/* 139 */     if (securityManager != null) {
/* 140 */       String str1 = this.targetName.getKrbName();
/* 141 */       String str2 = Krb5Util.getTGSName(this.targetName);
/* 142 */       StringBuffer stringBuffer = new StringBuffer("\"");
/* 143 */       stringBuffer.append(str1).append("\" \"");
/* 144 */       stringBuffer.append(str2).append('"');
/* 145 */       String str3 = stringBuffer.toString();
/* 146 */       SunNativeProvider.debug("Checking DelegationPermission (" + str3 + ")");
/*     */       
/* 148 */       DelegationPermission delegationPermission = new DelegationPermission(str3);
/*     */       
/* 150 */       securityManager.checkPermission(delegationPermission);
/* 151 */       this.skipDelegPermCheck = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] retrieveToken(InputStream paramInputStream, int paramInt) throws GSSException {
/*     */     try {
/* 158 */       byte[] arrayOfByte = null;
/* 159 */       if (paramInt != -1) {
/*     */         
/* 161 */         SunNativeProvider.debug("Precomputed mechToken length: " + paramInt);
/*     */ 
/*     */         
/* 164 */         GSSHeader gSSHeader = new GSSHeader(new ObjectIdentifier(this.cStub.getMech().toString()), paramInt);
/*     */         
/* 166 */         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(600);
/*     */         
/* 168 */         byte[] arrayOfByte1 = new byte[paramInt];
/* 169 */         int i = paramInputStream.read(arrayOfByte1);
/* 170 */         assert paramInt == i;
/* 171 */         gSSHeader.encode(byteArrayOutputStream);
/* 172 */         byteArrayOutputStream.write(arrayOfByte1);
/* 173 */         arrayOfByte = byteArrayOutputStream.toByteArray();
/*     */       } else {
/*     */         
/* 176 */         assert paramInt == -1;
/* 177 */         DerValue derValue = new DerValue(paramInputStream);
/* 178 */         arrayOfByte = derValue.toByteArray();
/*     */       } 
/* 180 */       SunNativeProvider.debug("Complete Token length: " + arrayOfByte.length);
/*     */       
/* 182 */       return arrayOfByte;
/* 183 */     } catch (IOException iOException) {
/* 184 */       throw new GSSExceptionImpl(11, iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   NativeGSSContext(GSSNameElement paramGSSNameElement, GSSCredElement paramGSSCredElement, int paramInt, GSSLibStub paramGSSLibStub) throws GSSException {
/* 191 */     if (paramGSSNameElement == null) {
/* 192 */       throw new GSSException(11, 1, "null peer");
/*     */     }
/* 194 */     this.cStub = paramGSSLibStub;
/* 195 */     this.cred = paramGSSCredElement;
/* 196 */     this.targetName = paramGSSNameElement;
/* 197 */     this.isInitiator = true;
/* 198 */     this.lifetime = paramInt;
/*     */     
/* 200 */     if (GSSUtil.isKerberosMech(this.cStub.getMech())) {
/* 201 */       doServicePermCheck();
/* 202 */       if (this.cred == null) {
/* 203 */         this.cred = new GSSCredElement(null, this.lifetime, 1, this.cStub);
/*     */       }
/*     */       
/* 206 */       this.srcName = this.cred.getName();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   NativeGSSContext(GSSCredElement paramGSSCredElement, GSSLibStub paramGSSLibStub) throws GSSException {
/* 213 */     this.cStub = paramGSSLibStub;
/* 214 */     this.cred = paramGSSCredElement;
/*     */     
/* 216 */     if (this.cred != null) this.targetName = this.cred.getName();
/*     */     
/* 218 */     this.isInitiator = false;
/*     */ 
/*     */     
/* 221 */     if (GSSUtil.isKerberosMech(this.cStub.getMech()) && this.targetName != null) {
/* 222 */       doServicePermCheck();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NativeGSSContext(long paramLong, GSSLibStub paramGSSLibStub) throws GSSException {
/* 231 */     assert this.pContext != 0L;
/* 232 */     this.pContext = paramLong;
/* 233 */     this.cStub = paramGSSLibStub;
/*     */ 
/*     */     
/* 236 */     long[] arrayOfLong = this.cStub.inquireContext(this.pContext);
/* 237 */     if (arrayOfLong.length != 6) {
/* 238 */       throw new RuntimeException("Bug w/ GSSLibStub.inquireContext()");
/*     */     }
/* 240 */     this.srcName = new GSSNameElement(arrayOfLong[0], this.cStub);
/* 241 */     this.targetName = new GSSNameElement(arrayOfLong[1], this.cStub);
/* 242 */     this.isInitiator = (arrayOfLong[2] != 0L);
/* 243 */     this.isEstablished = (arrayOfLong[3] != 0L);
/* 244 */     this.flags = (int)arrayOfLong[4];
/* 245 */     this.lifetime = (int)arrayOfLong[5];
/*     */ 
/*     */ 
/*     */     
/* 249 */     Oid oid = this.cStub.getMech();
/* 250 */     if (GSSUtil.isSpNegoMech(oid) || GSSUtil.isKerberosMech(oid)) {
/* 251 */       doServicePermCheck();
/*     */     }
/*     */   }
/*     */   
/*     */   public Provider getProvider() {
/* 256 */     return SunNativeProvider.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] initSecContext(InputStream paramInputStream, int paramInt) throws GSSException {
/* 261 */     byte[] arrayOfByte = null;
/* 262 */     if (!this.isEstablished && this.isInitiator) {
/* 263 */       byte[] arrayOfByte1 = null;
/*     */       
/* 265 */       if (this.pContext != 0L) {
/* 266 */         arrayOfByte1 = retrieveToken(paramInputStream, paramInt);
/* 267 */         SunNativeProvider.debug("initSecContext=> inToken len=" + arrayOfByte1.length);
/*     */       } 
/*     */ 
/*     */       
/* 271 */       if (!getCredDelegState()) this.skipDelegPermCheck = true;
/*     */       
/* 273 */       if (GSSUtil.isKerberosMech(this.cStub.getMech()) && !this.skipDelegPermCheck) {
/* 274 */         doDelegPermCheck();
/*     */       }
/*     */       
/* 277 */       long l = (this.cred == null) ? 0L : this.cred.pCred;
/* 278 */       arrayOfByte = this.cStub.initContext(l, this.targetName.pName, this.cb, arrayOfByte1, this);
/*     */       
/* 280 */       SunNativeProvider.debug("initSecContext=> outToken len=" + ((arrayOfByte == null) ? 0 : arrayOfByte.length));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 285 */       if (GSSUtil.isSpNegoMech(this.cStub.getMech()) && arrayOfByte != null) {
/*     */         
/* 287 */         this.actualMech = getMechFromSpNegoToken(arrayOfByte, true);
/*     */         
/* 289 */         if (GSSUtil.isKerberosMech(this.actualMech)) {
/* 290 */           if (!this.skipServicePermCheck) doServicePermCheck(); 
/* 291 */           if (!this.skipDelegPermCheck) doDelegPermCheck();
/*     */         
/*     */         } 
/*     */       } 
/* 295 */       if (this.isEstablished) {
/* 296 */         if (this.srcName == null) {
/* 297 */           this
/* 298 */             .srcName = new GSSNameElement(this.cStub.getContextName(this.pContext, true), this.cStub);
/*     */         }
/* 300 */         if (this.cred == null) {
/* 301 */           this.cred = new GSSCredElement(this.srcName, this.lifetime, 1, this.cStub);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 307 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] acceptSecContext(InputStream paramInputStream, int paramInt) throws GSSException {
/* 312 */     byte[] arrayOfByte = null;
/* 313 */     if (!this.isEstablished && !this.isInitiator) {
/* 314 */       byte[] arrayOfByte1 = retrieveToken(paramInputStream, paramInt);
/* 315 */       SunNativeProvider.debug("acceptSecContext=> inToken len=" + arrayOfByte1.length);
/*     */       
/* 317 */       long l = (this.cred == null) ? 0L : this.cred.pCred;
/* 318 */       arrayOfByte = this.cStub.acceptContext(l, this.cb, arrayOfByte1, this);
/* 319 */       SunNativeProvider.debug("acceptSecContext=> outToken len=" + ((arrayOfByte == null) ? 0 : arrayOfByte.length));
/*     */ 
/*     */       
/* 322 */       if (this.targetName == null) {
/* 323 */         this
/* 324 */           .targetName = new GSSNameElement(this.cStub.getContextName(this.pContext, false), this.cStub);
/*     */ 
/*     */         
/* 327 */         if (this.cred != null) this.cred.dispose(); 
/* 328 */         this.cred = new GSSCredElement(this.targetName, this.lifetime, 2, this.cStub);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 334 */       if (GSSUtil.isSpNegoMech(this.cStub.getMech()) && arrayOfByte != null && !this.skipServicePermCheck)
/*     */       {
/* 336 */         if (GSSUtil.isKerberosMech(
/* 337 */             getMechFromSpNegoToken(arrayOfByte, false))) {
/* 338 */           doServicePermCheck();
/*     */         }
/*     */       }
/*     */     } 
/* 342 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public boolean isEstablished() {
/* 346 */     return this.isEstablished;
/*     */   }
/*     */   
/*     */   public void dispose() throws GSSException {
/* 350 */     this.srcName = null;
/* 351 */     this.targetName = null;
/* 352 */     this.cred = null;
/* 353 */     this.delegatedCred = null;
/* 354 */     if (this.pContext != 0L) {
/* 355 */       this.pContext = this.cStub.deleteContext(this.pContext);
/* 356 */       this.pContext = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWrapSizeLimit(int paramInt1, boolean paramBoolean, int paramInt2) throws GSSException {
/* 363 */     return this.cStub.wrapSizeLimit(this.pContext, paramBoolean ? 1 : 0, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/* 369 */     byte[] arrayOfByte = paramArrayOfbyte;
/* 370 */     if (paramInt1 != 0 || paramInt2 != paramArrayOfbyte.length) {
/* 371 */       arrayOfByte = new byte[paramInt2];
/* 372 */       System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, paramInt2);
/*     */     } 
/* 374 */     return this.cStub.wrap(this.pContext, arrayOfByte, paramMessageProp);
/*     */   }
/*     */ 
/*     */   
/*     */   public void wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/*     */     try {
/* 380 */       byte[] arrayOfByte = wrap(paramArrayOfbyte, paramInt1, paramInt2, paramMessageProp);
/* 381 */       paramOutputStream.write(arrayOfByte);
/* 382 */     } catch (IOException iOException) {
/* 383 */       throw new GSSExceptionImpl(11, iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int wrap(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, MessageProp paramMessageProp) throws GSSException {
/* 389 */     byte[] arrayOfByte = wrap(paramArrayOfbyte1, paramInt1, paramInt2, paramMessageProp);
/* 390 */     System.arraycopy(arrayOfByte, 0, paramArrayOfbyte2, paramInt3, arrayOfByte.length);
/* 391 */     return arrayOfByte.length;
/*     */   }
/*     */   
/*     */   public void wrap(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/*     */     try {
/* 396 */       byte[] arrayOfByte1 = new byte[paramInputStream.available()];
/* 397 */       int i = paramInputStream.read(arrayOfByte1);
/* 398 */       byte[] arrayOfByte2 = wrap(arrayOfByte1, 0, i, paramMessageProp);
/* 399 */       paramOutputStream.write(arrayOfByte2);
/* 400 */     } catch (IOException iOException) {
/* 401 */       throw new GSSExceptionImpl(11, iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/* 408 */     if (paramInt1 != 0 || paramInt2 != paramArrayOfbyte.length) {
/* 409 */       byte[] arrayOfByte = new byte[paramInt2];
/* 410 */       System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, paramInt2);
/* 411 */       return this.cStub.unwrap(this.pContext, arrayOfByte, paramMessageProp);
/*     */     } 
/* 413 */     return this.cStub.unwrap(this.pContext, paramArrayOfbyte, paramMessageProp);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int unwrap(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, MessageProp paramMessageProp) throws GSSException {
/* 419 */     byte[] arrayOfByte = null;
/* 420 */     if (paramInt1 != 0 || paramInt2 != paramArrayOfbyte1.length) {
/* 421 */       byte[] arrayOfByte1 = new byte[paramInt2];
/* 422 */       System.arraycopy(paramArrayOfbyte1, paramInt1, arrayOfByte1, 0, paramInt2);
/* 423 */       arrayOfByte = this.cStub.unwrap(this.pContext, arrayOfByte1, paramMessageProp);
/*     */     } else {
/* 425 */       arrayOfByte = this.cStub.unwrap(this.pContext, paramArrayOfbyte1, paramMessageProp);
/*     */     } 
/* 427 */     System.arraycopy(arrayOfByte, 0, paramArrayOfbyte2, paramInt3, arrayOfByte.length);
/* 428 */     return arrayOfByte.length;
/*     */   }
/*     */   
/*     */   public void unwrap(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/*     */     try {
/* 433 */       byte[] arrayOfByte1 = new byte[paramInputStream.available()];
/* 434 */       int i = paramInputStream.read(arrayOfByte1);
/* 435 */       byte[] arrayOfByte2 = unwrap(arrayOfByte1, 0, i, paramMessageProp);
/* 436 */       paramOutputStream.write(arrayOfByte2);
/* 437 */       paramOutputStream.flush();
/* 438 */     } catch (IOException iOException) {
/* 439 */       throw new GSSExceptionImpl(11, iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int unwrap(InputStream paramInputStream, byte[] paramArrayOfbyte, int paramInt, MessageProp paramMessageProp) throws GSSException {
/* 446 */     byte[] arrayOfByte1 = null;
/* 447 */     int i = 0;
/*     */     try {
/* 449 */       arrayOfByte1 = new byte[paramInputStream.available()];
/* 450 */       i = paramInputStream.read(arrayOfByte1);
/* 451 */       byte[] arrayOfByte = unwrap(arrayOfByte1, 0, i, paramMessageProp);
/* 452 */     } catch (IOException iOException) {
/* 453 */       throw new GSSExceptionImpl(11, iOException);
/*     */     } 
/* 455 */     byte[] arrayOfByte2 = unwrap(arrayOfByte1, 0, i, paramMessageProp);
/* 456 */     System.arraycopy(arrayOfByte2, 0, paramArrayOfbyte, paramInt, arrayOfByte2.length);
/* 457 */     return arrayOfByte2.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getMIC(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/* 462 */     boolean bool = (paramMessageProp == null) ? false : paramMessageProp.getQOP();
/* 463 */     byte[] arrayOfByte = paramArrayOfbyte;
/* 464 */     if (paramInt1 != 0 || paramInt2 != paramArrayOfbyte.length) {
/* 465 */       arrayOfByte = new byte[paramInt2];
/* 466 */       System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, paramInt2);
/*     */     } 
/* 468 */     return this.cStub.getMic(this.pContext, bool, arrayOfByte);
/*     */   }
/*     */ 
/*     */   
/*     */   public void getMIC(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/*     */     try {
/* 474 */       int i = 0;
/* 475 */       byte[] arrayOfByte1 = new byte[paramInputStream.available()];
/* 476 */       i = paramInputStream.read(arrayOfByte1);
/*     */       
/* 478 */       byte[] arrayOfByte2 = getMIC(arrayOfByte1, 0, i, paramMessageProp);
/* 479 */       if (arrayOfByte2 != null && arrayOfByte2.length != 0) {
/* 480 */         paramOutputStream.write(arrayOfByte2);
/*     */       }
/* 482 */     } catch (IOException iOException) {
/* 483 */       throw new GSSExceptionImpl(11, iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifyMIC(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4, MessageProp paramMessageProp) throws GSSException {
/* 490 */     byte[] arrayOfByte1 = paramArrayOfbyte1;
/* 491 */     byte[] arrayOfByte2 = paramArrayOfbyte2;
/* 492 */     if (paramInt1 != 0 || paramInt2 != paramArrayOfbyte1.length) {
/* 493 */       arrayOfByte1 = new byte[paramInt2];
/* 494 */       System.arraycopy(paramArrayOfbyte1, paramInt1, arrayOfByte1, 0, paramInt2);
/*     */     } 
/* 496 */     if (paramInt3 != 0 || paramInt4 != paramArrayOfbyte2.length) {
/* 497 */       arrayOfByte2 = new byte[paramInt4];
/* 498 */       System.arraycopy(paramArrayOfbyte2, paramInt3, arrayOfByte2, 0, paramInt4);
/*     */     } 
/* 500 */     this.cStub.verifyMic(this.pContext, arrayOfByte1, arrayOfByte2, paramMessageProp);
/*     */   }
/*     */ 
/*     */   
/*     */   public void verifyMIC(InputStream paramInputStream1, InputStream paramInputStream2, MessageProp paramMessageProp) throws GSSException {
/*     */     try {
/* 506 */       byte[] arrayOfByte1 = new byte[paramInputStream2.available()];
/* 507 */       int i = paramInputStream2.read(arrayOfByte1);
/* 508 */       byte[] arrayOfByte2 = new byte[paramInputStream1.available()];
/* 509 */       int j = paramInputStream1.read(arrayOfByte2);
/* 510 */       verifyMIC(arrayOfByte2, 0, j, arrayOfByte1, 0, i, paramMessageProp);
/* 511 */     } catch (IOException iOException) {
/* 512 */       throw new GSSExceptionImpl(11, iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] export() throws GSSException {
/* 517 */     byte[] arrayOfByte = this.cStub.exportContext(this.pContext);
/* 518 */     this.pContext = 0L;
/* 519 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   private void changeFlags(int paramInt, boolean paramBoolean) {
/* 523 */     if (this.isInitiator && this.pContext == 0L)
/* 524 */       if (paramBoolean) {
/* 525 */         this.flags |= paramInt;
/*     */       } else {
/* 527 */         this.flags &= paramInt ^ 0xFFFFFFFF;
/*     */       }  
/*     */   }
/*     */   
/*     */   public void requestMutualAuth(boolean paramBoolean) throws GSSException {
/* 532 */     changeFlags(2, paramBoolean);
/*     */   }
/*     */   public void requestReplayDet(boolean paramBoolean) throws GSSException {
/* 535 */     changeFlags(4, paramBoolean);
/*     */   }
/*     */   public void requestSequenceDet(boolean paramBoolean) throws GSSException {
/* 538 */     changeFlags(8, paramBoolean);
/*     */   }
/*     */   public void requestCredDeleg(boolean paramBoolean) throws GSSException {
/* 541 */     changeFlags(1, paramBoolean);
/*     */   }
/*     */   public void requestAnonymity(boolean paramBoolean) throws GSSException {
/* 544 */     changeFlags(64, paramBoolean);
/*     */   }
/*     */   public void requestConf(boolean paramBoolean) throws GSSException {
/* 547 */     changeFlags(16, paramBoolean);
/*     */   }
/*     */   public void requestInteg(boolean paramBoolean) throws GSSException {
/* 550 */     changeFlags(32, paramBoolean);
/*     */   }
/*     */   
/*     */   public void requestDelegPolicy(boolean paramBoolean) throws GSSException {}
/*     */   
/*     */   public void requestLifetime(int paramInt) throws GSSException {
/* 556 */     if (this.isInitiator && this.pContext == 0L)
/* 557 */       this.lifetime = paramInt; 
/*     */   }
/*     */   
/*     */   public void setChannelBinding(ChannelBinding paramChannelBinding) throws GSSException {
/* 561 */     if (this.pContext == 0L) {
/* 562 */       this.cb = paramChannelBinding;
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean checkFlags(int paramInt) {
/* 567 */     return ((this.flags & paramInt) != 0);
/*     */   }
/*     */   public boolean getCredDelegState() {
/* 570 */     return checkFlags(1);
/*     */   }
/*     */   public boolean getMutualAuthState() {
/* 573 */     return checkFlags(2);
/*     */   }
/*     */   public boolean getReplayDetState() {
/* 576 */     return checkFlags(4);
/*     */   }
/*     */   public boolean getSequenceDetState() {
/* 579 */     return checkFlags(8);
/*     */   }
/*     */   public boolean getAnonymityState() {
/* 582 */     return checkFlags(64);
/*     */   }
/*     */   public boolean isTransferable() throws GSSException {
/* 585 */     return checkFlags(256);
/*     */   }
/*     */   public boolean isProtReady() {
/* 588 */     return checkFlags(128);
/*     */   }
/*     */   public boolean getConfState() {
/* 591 */     return checkFlags(16);
/*     */   }
/*     */   public boolean getIntegState() {
/* 594 */     return checkFlags(32);
/*     */   }
/*     */   public boolean getDelegPolicyState() {
/* 597 */     return false;
/*     */   }
/*     */   public int getLifetime() {
/* 600 */     return this.cStub.getContextTime(this.pContext);
/*     */   }
/*     */   public GSSNameSpi getSrcName() throws GSSException {
/* 603 */     return this.srcName;
/*     */   }
/*     */   public GSSNameSpi getTargName() throws GSSException {
/* 606 */     return this.targetName;
/*     */   }
/*     */   public Oid getMech() throws GSSException {
/* 609 */     if (this.isEstablished && this.actualMech != null) {
/* 610 */       return this.actualMech;
/*     */     }
/* 612 */     return this.cStub.getMech();
/*     */   }
/*     */   
/*     */   public GSSCredentialSpi getDelegCred() throws GSSException {
/* 616 */     return this.delegatedCred;
/*     */   }
/*     */   public boolean isInitiator() {
/* 619 */     return this.isInitiator;
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 623 */     dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object inquireSecContext(InquireType paramInquireType) throws GSSException {
/* 628 */     throw new GSSException(16, -1, "Inquire type not supported.");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/wrapper/NativeGSSContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */