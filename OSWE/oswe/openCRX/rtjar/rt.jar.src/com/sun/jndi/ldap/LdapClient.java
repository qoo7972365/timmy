/*      */ package com.sun.jndi.ldap;
/*      */ 
/*      */ import com.sun.jndi.ldap.pool.PoolCallback;
/*      */ import com.sun.jndi.ldap.pool.PooledConnection;
/*      */ import com.sun.jndi.ldap.sasl.LdapSasl;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Locale;
/*      */ import java.util.Vector;
/*      */ import javax.naming.AuthenticationException;
/*      */ import javax.naming.AuthenticationNotSupportedException;
/*      */ import javax.naming.CommunicationException;
/*      */ import javax.naming.NamingEnumeration;
/*      */ import javax.naming.NamingException;
/*      */ import javax.naming.directory.Attribute;
/*      */ import javax.naming.directory.BasicAttributes;
/*      */ import javax.naming.directory.InvalidAttributeValueException;
/*      */ import javax.naming.ldap.Control;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class LdapClient
/*      */   implements PooledConnection
/*      */ {
/*      */   private static final int debug = 0;
/*      */   static final boolean caseIgnore = true;
/*   84 */   private static final Hashtable<String, Boolean> defaultBinaryAttrs = new Hashtable<>(23, 0.75F); private static final String DISCONNECT_OID = "1.3.6.1.4.1.1466.20036"; boolean isLdapv3; final Connection conn; private final PoolCallback pcb; private final boolean pooled; private boolean authenticateCalled = false; static final int SCOPE_BASE_OBJECT = 0; static final int SCOPE_ONE_LEVEL = 1; static final int SCOPE_SUBTREE = 2; static final int ADD = 0; static final int DELETE = 1; static final int REPLACE = 2; static final int LDAP_VERSION3_VERSION2 = 32; static final int LDAP_VERSION2 = 2; static final int LDAP_VERSION3 = 3;
/*      */   
/*      */   static {
/*   87 */     defaultBinaryAttrs.put("userpassword", Boolean.TRUE);
/*   88 */     defaultBinaryAttrs.put("javaserializeddata", Boolean.TRUE);
/*      */     
/*   90 */     defaultBinaryAttrs.put("javaserializedobject", Boolean.TRUE);
/*      */     
/*   92 */     defaultBinaryAttrs.put("jpegphoto", Boolean.TRUE);
/*      */     
/*   94 */     defaultBinaryAttrs.put("audio", Boolean.TRUE);
/*   95 */     defaultBinaryAttrs.put("thumbnailphoto", Boolean.TRUE);
/*      */     
/*   97 */     defaultBinaryAttrs.put("thumbnaillogo", Boolean.TRUE);
/*      */     
/*   99 */     defaultBinaryAttrs.put("usercertificate", Boolean.TRUE);
/*  100 */     defaultBinaryAttrs.put("cacertificate", Boolean.TRUE);
/*  101 */     defaultBinaryAttrs.put("certificaterevocationlist", Boolean.TRUE);
/*      */     
/*  103 */     defaultBinaryAttrs.put("authorityrevocationlist", Boolean.TRUE);
/*  104 */     defaultBinaryAttrs.put("crosscertificatepair", Boolean.TRUE);
/*  105 */     defaultBinaryAttrs.put("photo", Boolean.TRUE);
/*  106 */     defaultBinaryAttrs.put("personalsignature", Boolean.TRUE);
/*      */     
/*  108 */     defaultBinaryAttrs.put("x500uniqueidentifier", Boolean.TRUE);
/*      */   }
/*      */   static final int LDAP_VERSION = 3; static final int LDAP_REF_FOLLOW = 1; static final int LDAP_REF_THROW = 2; static final int LDAP_REF_IGNORE = 3; static final int LDAP_REF_FOLLOW_SCHEME = 4; static final String LDAP_URL = "ldap://"; static final String LDAPS_URL = "ldaps://";
/*      */   static final int LBER_BOOLEAN = 1;
/*      */   static final int LBER_INTEGER = 2;
/*      */   static final int LBER_BITSTRING = 3;
/*      */   static final int LBER_OCTETSTRING = 4;
/*      */   static final int LBER_NULL = 5;
/*  116 */   int referenceCount = 1; static final int LBER_ENUMERATED = 10; static final int LBER_SEQUENCE = 48; static final int LBER_SET = 49; static final int LDAP_SUPERIOR_DN = 128; static final int LDAP_REQ_BIND = 96; static final int LDAP_REQ_UNBIND = 66; static final int LDAP_REQ_SEARCH = 99; static final int LDAP_REQ_MODIFY = 102; static final int LDAP_REQ_ADD = 104; static final int LDAP_REQ_DELETE = 74; static final int LDAP_REQ_MODRDN = 108;
/*      */   static final int LDAP_REQ_COMPARE = 110;
/*      */   static final int LDAP_REQ_ABANDON = 80;
/*      */   static final int LDAP_REQ_EXTENSION = 119;
/*      */   static final int LDAP_REP_BIND = 97;
/*      */   static final int LDAP_REP_SEARCH = 100;
/*      */   static final int LDAP_REP_SEARCH_REF = 115;
/*      */   static final int LDAP_REP_RESULT = 101;
/*      */   static final int LDAP_REP_MODIFY = 103;
/*      */   static final int LDAP_REP_ADD = 105;
/*      */   static final int LDAP_REP_DELETE = 107;
/*      */   static final int LDAP_REP_MODRDN = 109;
/*      */   static final int LDAP_REP_COMPARE = 111;
/*      */   static final int LDAP_REP_EXTENSION = 120;
/*      */   static final int LDAP_REP_REFERRAL = 163;
/*      */   static final int LDAP_REP_EXT_OID = 138;
/*      */   static final int LDAP_REP_EXT_VAL = 139;
/*      */   static final int LDAP_CONTROLS = 160;
/*      */   static final String LDAP_CONTROL_MANAGE_DSA_IT = "2.16.840.1.113730.3.4.2";
/*      */   static final String LDAP_CONTROL_PREFERRED_LANG = "1.3.6.1.4.1.1466.20035";
/*      */   static final String LDAP_CONTROL_PAGED_RESULTS = "1.2.840.113556.1.4.319";
/*      */   static final String LDAP_CONTROL_SERVER_SORT_REQ = "1.2.840.113556.1.4.473";
/*      */   static final String LDAP_CONTROL_SERVER_SORT_RES = "1.2.840.113556.1.4.474";
/*      */   static final int LDAP_SUCCESS = 0;
/*      */   static final int LDAP_OPERATIONS_ERROR = 1;
/*      */   static final int LDAP_PROTOCOL_ERROR = 2;
/*      */   static final int LDAP_TIME_LIMIT_EXCEEDED = 3;
/*      */   
/*      */   synchronized boolean authenticateCalled() {
/*  145 */     return this.authenticateCalled;
/*      */   }
/*      */   static final int LDAP_SIZE_LIMIT_EXCEEDED = 4; static final int LDAP_COMPARE_FALSE = 5; static final int LDAP_COMPARE_TRUE = 6; static final int LDAP_AUTH_METHOD_NOT_SUPPORTED = 7; static final int LDAP_STRONG_AUTH_REQUIRED = 8; static final int LDAP_PARTIAL_RESULTS = 9; static final int LDAP_REFERRAL = 10; static final int LDAP_ADMIN_LIMIT_EXCEEDED = 11; static final int LDAP_UNAVAILABLE_CRITICAL_EXTENSION = 12;
/*      */   static final int LDAP_CONFIDENTIALITY_REQUIRED = 13;
/*      */   static final int LDAP_SASL_BIND_IN_PROGRESS = 14;
/*      */   static final int LDAP_NO_SUCH_ATTRIBUTE = 16;
/*      */   
/*      */   synchronized LdapResult authenticate(boolean paramBoolean, String paramString1, Object paramObject, int paramInt, String paramString2, Control[] paramArrayOfControl, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  153 */     int i = this.conn.readTimeout;
/*  154 */     this.conn.readTimeout = this.conn.connectTimeout;
/*  155 */     LdapResult ldapResult = null;
/*      */     
/*      */     try {
/*  158 */       this.authenticateCalled = true;
/*      */       
/*      */       try {
/*  161 */         ensureOpen();
/*  162 */       } catch (IOException iOException) {
/*  163 */         CommunicationException communicationException = new CommunicationException();
/*  164 */         communicationException.setRootCause(iOException);
/*  165 */         throw communicationException;
/*      */       } 
/*      */       
/*  168 */       switch (paramInt) {
/*      */         case 3:
/*      */         case 32:
/*  171 */           this.isLdapv3 = true;
/*      */           break;
/*      */         case 2:
/*  174 */           this.isLdapv3 = false;
/*      */           break;
/*      */         default:
/*  177 */           throw new CommunicationException("Protocol version " + paramInt + " not supported");
/*      */       } 
/*      */ 
/*      */       
/*  181 */       if (paramString2.equalsIgnoreCase("none") || paramString2
/*  182 */         .equalsIgnoreCase("anonymous")) {
/*      */ 
/*      */ 
/*      */         
/*  186 */         if (!paramBoolean || paramInt == 2 || paramInt == 32 || (paramArrayOfControl != null && paramArrayOfControl.length > 0)) {
/*      */ 
/*      */           
/*      */           try {
/*      */ 
/*      */             
/*  192 */             ldapResult = ldapBind(paramString1 = null, (byte[])(paramObject = null), paramArrayOfControl, null, false);
/*      */             
/*  194 */             if (ldapResult.status == 0) {
/*  195 */               this.conn.setBound();
/*      */             }
/*  197 */           } catch (IOException iOException) {
/*  198 */             CommunicationException communicationException = new CommunicationException("anonymous bind failed: " + this.conn.host + ":" + this.conn.port);
/*      */ 
/*      */             
/*  201 */             communicationException.setRootCause(iOException);
/*  202 */             throw communicationException;
/*      */           } 
/*      */         } else {
/*      */           
/*  206 */           ldapResult = new LdapResult();
/*  207 */           ldapResult.status = 0;
/*      */         } 
/*  209 */       } else if (paramString2.equalsIgnoreCase("simple")) {
/*      */         
/*  211 */         byte[] arrayOfByte = null;
/*      */         try {
/*  213 */           arrayOfByte = encodePassword(paramObject, this.isLdapv3);
/*  214 */           ldapResult = ldapBind(paramString1, arrayOfByte, paramArrayOfControl, null, false);
/*  215 */           if (ldapResult.status == 0) {
/*  216 */             this.conn.setBound();
/*      */           }
/*  218 */         } catch (IOException iOException) {
/*  219 */           CommunicationException communicationException = new CommunicationException("simple bind failed: " + this.conn.host + ":" + this.conn.port);
/*      */ 
/*      */           
/*  222 */           communicationException.setRootCause(iOException);
/*  223 */           throw communicationException;
/*      */         }
/*      */         finally {
/*      */           
/*  227 */           if (arrayOfByte != paramObject && arrayOfByte != null) {
/*  228 */             for (byte b = 0; b < arrayOfByte.length; b++) {
/*  229 */               arrayOfByte[b] = 0;
/*      */             }
/*      */           }
/*      */         } 
/*  233 */       } else if (this.isLdapv3) {
/*      */         
/*      */         try {
/*  236 */           ldapResult = LdapSasl.saslBind(this, this.conn, this.conn.host, paramString1, paramObject, paramString2, paramHashtable, paramArrayOfControl);
/*      */           
/*  238 */           if (ldapResult.status == 0) {
/*  239 */             this.conn.setBound();
/*      */           }
/*  241 */         } catch (IOException iOException) {
/*  242 */           CommunicationException communicationException = new CommunicationException("SASL bind failed: " + this.conn.host + ":" + this.conn.port);
/*      */ 
/*      */           
/*  245 */           communicationException.setRootCause(iOException);
/*  246 */           throw communicationException;
/*      */         } 
/*      */       } else {
/*  249 */         throw new AuthenticationNotSupportedException(paramString2);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  255 */       if (paramBoolean && ldapResult.status == 2 && paramInt == 32 && (paramString2
/*      */ 
/*      */         
/*  258 */         .equalsIgnoreCase("none") || paramString2
/*  259 */         .equalsIgnoreCase("anonymous") || paramString2
/*  260 */         .equalsIgnoreCase("simple"))) {
/*      */         
/*  262 */         byte[] arrayOfByte = null;
/*      */         try {
/*  264 */           this.isLdapv3 = false;
/*  265 */           arrayOfByte = encodePassword(paramObject, false);
/*  266 */           ldapResult = ldapBind(paramString1, arrayOfByte, paramArrayOfControl, null, false);
/*  267 */           if (ldapResult.status == 0) {
/*  268 */             this.conn.setBound();
/*      */           }
/*  270 */         } catch (IOException iOException) {
/*  271 */           CommunicationException communicationException = new CommunicationException(paramString2 + ":" + this.conn.host + ":" + this.conn.port);
/*      */ 
/*      */           
/*  274 */           communicationException.setRootCause(iOException);
/*  275 */           throw communicationException;
/*      */         }
/*      */         finally {
/*      */           
/*  279 */           if (arrayOfByte != paramObject && arrayOfByte != null) {
/*  280 */             for (byte b = 0; b < arrayOfByte.length; b++) {
/*  281 */               arrayOfByte[b] = 0;
/*      */             }
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  293 */       if (ldapResult.status == 32) {
/*  294 */         throw new AuthenticationException(
/*  295 */             getErrorMessage(ldapResult.status, ldapResult.errorMessage));
/*      */       }
/*  297 */       this.conn.setV3(this.isLdapv3);
/*  298 */       return ldapResult;
/*      */     } finally {
/*  300 */       this.conn.readTimeout = i;
/*      */     } 
/*      */   }
/*      */   static final int LDAP_UNDEFINED_ATTRIBUTE_TYPE = 17; static final int LDAP_INAPPROPRIATE_MATCHING = 18; static final int LDAP_CONSTRAINT_VIOLATION = 19; static final int LDAP_ATTRIBUTE_OR_VALUE_EXISTS = 20; static final int LDAP_INVALID_ATTRIBUTE_SYNTAX = 21; static final int LDAP_NO_SUCH_OBJECT = 32; static final int LDAP_ALIAS_PROBLEM = 33; static final int LDAP_INVALID_DN_SYNTAX = 34; static final int LDAP_IS_LEAF = 35; static final int LDAP_ALIAS_DEREFERENCING_PROBLEM = 36; static final int LDAP_INAPPROPRIATE_AUTHENTICATION = 48; static final int LDAP_INVALID_CREDENTIALS = 49; static final int LDAP_INSUFFICIENT_ACCESS_RIGHTS = 50; static final int LDAP_BUSY = 51; static final int LDAP_UNAVAILABLE = 52;
/*      */   static final int LDAP_UNWILLING_TO_PERFORM = 53;
/*      */   static final int LDAP_LOOP_DETECT = 54;
/*      */   static final int LDAP_NAMING_VIOLATION = 64;
/*      */   static final int LDAP_OBJECT_CLASS_VIOLATION = 65;
/*      */   static final int LDAP_NOT_ALLOWED_ON_NON_LEAF = 66;
/*      */   static final int LDAP_NOT_ALLOWED_ON_RDN = 67;
/*      */   static final int LDAP_ENTRY_ALREADY_EXISTS = 68;
/*      */   static final int LDAP_OBJECT_CLASS_MODS_PROHIBITED = 69;
/*      */   static final int LDAP_AFFECTS_MULTIPLE_DSAS = 71;
/*      */   static final int LDAP_OTHER = 80;
/*      */   
/*      */   public synchronized LdapResult ldapBind(String paramString1, byte[] paramArrayOfbyte, Control[] paramArrayOfControl, String paramString2, boolean paramBoolean) throws IOException, NamingException {
/*  316 */     ensureOpen();
/*      */ 
/*      */     
/*  319 */     this.conn.abandonOutstandingReqs(null);
/*      */     
/*  321 */     BerEncoder berEncoder = new BerEncoder();
/*  322 */     int i = this.conn.getMsgId();
/*  323 */     LdapResult ldapResult = new LdapResult();
/*  324 */     ldapResult.status = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  329 */     berEncoder.beginSeq(48);
/*  330 */     berEncoder.encodeInt(i);
/*  331 */     berEncoder.beginSeq(96);
/*  332 */     berEncoder.encodeInt(this.isLdapv3 ? 3 : 2);
/*  333 */     berEncoder.encodeString(paramString1, this.isLdapv3);
/*      */ 
/*      */     
/*  336 */     if (paramString2 != null) {
/*  337 */       berEncoder.beginSeq(163);
/*  338 */       berEncoder.encodeString(paramString2, this.isLdapv3);
/*  339 */       if (paramArrayOfbyte != null) {
/*  340 */         berEncoder.encodeOctetString(paramArrayOfbyte, 4);
/*      */       }
/*      */       
/*  343 */       berEncoder.endSeq();
/*      */     }
/*  345 */     else if (paramArrayOfbyte != null) {
/*  346 */       berEncoder.encodeOctetString(paramArrayOfbyte, 128);
/*      */     } else {
/*  348 */       berEncoder.encodeOctetString(null, 128, 0, 0);
/*      */     } 
/*      */     
/*  351 */     berEncoder.endSeq();
/*      */ 
/*      */     
/*  354 */     if (this.isLdapv3) {
/*  355 */       encodeControls(berEncoder, paramArrayOfControl);
/*      */     }
/*  357 */     berEncoder.endSeq();
/*      */     
/*  359 */     LdapRequest ldapRequest = this.conn.writeRequest(berEncoder, i, paramBoolean);
/*  360 */     if (paramArrayOfbyte != null) {
/*  361 */       berEncoder.reset();
/*      */     }
/*      */ 
/*      */     
/*  365 */     BerDecoder berDecoder = this.conn.readReply(ldapRequest);
/*      */     
/*  367 */     berDecoder.parseSeq(null);
/*  368 */     berDecoder.parseInt();
/*  369 */     if (berDecoder.parseByte() != 97) {
/*  370 */       return ldapResult;
/*      */     }
/*      */     
/*  373 */     berDecoder.parseLength();
/*  374 */     parseResult(berDecoder, ldapResult, this.isLdapv3);
/*      */ 
/*      */     
/*  377 */     if (this.isLdapv3 && berDecoder
/*  378 */       .bytesLeft() > 0 && berDecoder
/*  379 */       .peekByte() == 135) {
/*  380 */       ldapResult.serverCreds = berDecoder.parseOctetString(135, null);
/*      */     }
/*      */     
/*  383 */     ldapResult.resControls = this.isLdapv3 ? parseControls(berDecoder) : null;
/*      */     
/*  385 */     this.conn.removeRequest(ldapRequest);
/*  386 */     return ldapResult;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean usingSaslStreams() {
/*  396 */     return this.conn.inStream instanceof com.sun.jndi.ldap.sasl.SaslInputStream;
/*      */   }
/*      */   
/*      */   synchronized void incRefCount() {
/*  400 */     this.referenceCount++;
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
/*      */   private static byte[] encodePassword(Object paramObject, boolean paramBoolean) throws IOException {
/*  412 */     if (paramObject instanceof char[]) {
/*  413 */       paramObject = new String((char[])paramObject);
/*      */     }
/*      */     
/*  416 */     if (paramObject instanceof String) {
/*  417 */       if (paramBoolean) {
/*  418 */         return ((String)paramObject).getBytes("UTF8");
/*      */       }
/*  420 */       return ((String)paramObject).getBytes("8859_1");
/*      */     } 
/*      */     
/*  423 */     return (byte[])paramObject;
/*      */   }
/*      */ 
/*      */   
/*      */   synchronized void close(Control[] paramArrayOfControl, boolean paramBoolean) {
/*  428 */     this.referenceCount--;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  436 */     if (this.referenceCount <= 0)
/*      */     {
/*  438 */       if (!this.pooled) {
/*      */         
/*  440 */         this.conn.cleanup(paramArrayOfControl, false);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  445 */       else if (paramBoolean) {
/*  446 */         this.conn.cleanup(paramArrayOfControl, false);
/*  447 */         this.pcb.removePooledConnection(this);
/*      */       } else {
/*  449 */         this.pcb.releasePooledConnection(this);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void forceClose(boolean paramBoolean) {
/*  457 */     this.referenceCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  465 */     this.conn.cleanup(null, false);
/*  466 */     if (paramBoolean) {
/*  467 */       this.pcb.removePooledConnection(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void finalize() {
/*  473 */     forceClose(this.pooled);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void closeConnection() {
/*  480 */     forceClose(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void processConnectionClosure() {
/*  491 */     if (this.unsolicited.size() > 0) {
/*      */       String str;
/*  493 */       if (this.conn != null) {
/*  494 */         str = this.conn.host + ":" + this.conn.port + " connection closed";
/*      */       } else {
/*  496 */         str = "Connection closed";
/*      */       } 
/*  498 */       notifyUnsolicited(new CommunicationException(str));
/*      */     } 
/*      */ 
/*      */     
/*  502 */     if (this.pooled) {
/*  503 */       this.pcb.removePooledConnection(this);
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
/*      */   LdapResult search(String paramString1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, String[] paramArrayOfString, String paramString2, int paramInt5, Control[] paramArrayOfControl, Hashtable<String, Boolean> paramHashtable, boolean paramBoolean2, int paramInt6) throws IOException, NamingException {
/*  524 */     ensureOpen();
/*      */     
/*  526 */     LdapResult ldapResult = new LdapResult();
/*      */     
/*  528 */     BerEncoder berEncoder = new BerEncoder();
/*  529 */     int i = this.conn.getMsgId();
/*      */     
/*  531 */     berEncoder.beginSeq(48);
/*  532 */     berEncoder.encodeInt(i);
/*  533 */     berEncoder.beginSeq(99);
/*  534 */     berEncoder.encodeString((paramString1 == null) ? "" : paramString1, this.isLdapv3);
/*  535 */     berEncoder.encodeInt(paramInt1, 10);
/*  536 */     berEncoder.encodeInt(paramInt2, 10);
/*  537 */     berEncoder.encodeInt(paramInt3);
/*  538 */     berEncoder.encodeInt(paramInt4);
/*  539 */     berEncoder.encodeBoolean(paramBoolean1);
/*  540 */     Filter.encodeFilterString(berEncoder, paramString2, this.isLdapv3);
/*  541 */     berEncoder.beginSeq(48);
/*  542 */     berEncoder.encodeStringArray(paramArrayOfString, this.isLdapv3);
/*  543 */     berEncoder.endSeq();
/*  544 */     berEncoder.endSeq();
/*  545 */     if (this.isLdapv3) encodeControls(berEncoder, paramArrayOfControl); 
/*  546 */     berEncoder.endSeq();
/*      */ 
/*      */     
/*  549 */     LdapRequest ldapRequest = this.conn.writeRequest(berEncoder, i, false, paramInt6);
/*      */     
/*  551 */     ldapResult.msgId = i;
/*  552 */     ldapResult.status = 0;
/*  553 */     if (paramBoolean2)
/*      */     {
/*  555 */       ldapResult = getSearchReply(ldapRequest, paramInt5, ldapResult, paramHashtable);
/*      */     }
/*  557 */     return ldapResult;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void clearSearchReply(LdapResult paramLdapResult, Control[] paramArrayOfControl) {
/*  564 */     if (paramLdapResult != null) {
/*      */ 
/*      */ 
/*      */       
/*  568 */       LdapRequest ldapRequest = this.conn.findRequest(paramLdapResult.msgId);
/*  569 */       if (ldapRequest == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  578 */       if (ldapRequest.hasSearchCompleted()) {
/*  579 */         this.conn.removeRequest(ldapRequest);
/*      */       } else {
/*  581 */         this.conn.abandonRequest(ldapRequest, paramArrayOfControl);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   LdapResult getSearchReply(int paramInt, LdapResult paramLdapResult, Hashtable<String, Boolean> paramHashtable) throws IOException, NamingException {
/*  592 */     ensureOpen();
/*      */     
/*      */     LdapRequest ldapRequest;
/*      */     
/*  596 */     if ((ldapRequest = this.conn.findRequest(paramLdapResult.msgId)) == null) {
/*  597 */       return null;
/*      */     }
/*      */     
/*  600 */     return getSearchReply(ldapRequest, paramInt, paramLdapResult, paramHashtable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LdapResult getSearchReply(LdapRequest paramLdapRequest, int paramInt, LdapResult paramLdapResult, Hashtable<String, Boolean> paramHashtable) throws IOException, NamingException {
/*  607 */     if (paramInt == 0) {
/*  608 */       paramInt = Integer.MAX_VALUE;
/*      */     }
/*  610 */     if (paramLdapResult.entries != null) {
/*  611 */       paramLdapResult.entries.setSize(0);
/*      */     } else {
/*  613 */       paramLdapResult.entries = new Vector<>((paramInt == Integer.MAX_VALUE) ? 32 : paramInt);
/*      */     } 
/*      */ 
/*      */     
/*  617 */     if (paramLdapResult.referrals != null) {
/*  618 */       paramLdapResult.referrals.setSize(0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  631 */     for (byte b = 0; b < paramInt; ) {
/*  632 */       BerDecoder berDecoder = this.conn.readReply(paramLdapRequest);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  637 */       berDecoder.parseSeq(null);
/*  638 */       berDecoder.parseInt();
/*  639 */       int i = berDecoder.parseSeq(null);
/*      */       
/*  641 */       if (i == 100) {
/*      */ 
/*      */         
/*  644 */         BasicAttributes basicAttributes = new BasicAttributes(true);
/*  645 */         String str = berDecoder.parseString(this.isLdapv3);
/*  646 */         LdapEntry ldapEntry = new LdapEntry(str, basicAttributes);
/*  647 */         int[] arrayOfInt = new int[1];
/*      */         
/*  649 */         berDecoder.parseSeq(arrayOfInt);
/*  650 */         int j = berDecoder.getParsePosition() + arrayOfInt[0];
/*  651 */         while (berDecoder.getParsePosition() < j && berDecoder
/*  652 */           .bytesLeft() > 0) {
/*  653 */           Attribute attribute = parseAttribute(berDecoder, paramHashtable);
/*  654 */           basicAttributes.put(attribute);
/*      */         } 
/*  656 */         ldapEntry.respCtls = this.isLdapv3 ? parseControls(berDecoder) : null;
/*      */         
/*  658 */         paramLdapResult.entries.addElement(ldapEntry);
/*  659 */         b++; continue;
/*      */       } 
/*  661 */       if (i == 115 && this.isLdapv3) {
/*      */ 
/*      */         
/*  664 */         Vector<String> vector = new Vector(4);
/*      */ 
/*      */ 
/*      */         
/*  668 */         if (berDecoder.peekByte() == 48)
/*      */         {
/*  670 */           berDecoder.parseSeq(null);
/*      */         }
/*      */         
/*  673 */         while (berDecoder.bytesLeft() > 0 && berDecoder
/*  674 */           .peekByte() == 4)
/*      */         {
/*  676 */           vector.addElement(berDecoder.parseString(this.isLdapv3));
/*      */         }
/*      */         
/*  679 */         if (paramLdapResult.referrals == null) {
/*  680 */           paramLdapResult.referrals = new Vector<>(4);
/*      */         }
/*  682 */         paramLdapResult.referrals.addElement(vector);
/*  683 */         paramLdapResult.resControls = this.isLdapv3 ? parseControls(berDecoder) : null;
/*      */         
/*      */         continue;
/*      */       } 
/*  687 */       if (i == 120) {
/*      */         
/*  689 */         parseExtResponse(berDecoder, paramLdapResult); continue;
/*      */       } 
/*  691 */       if (i == 101) {
/*      */         
/*  693 */         parseResult(berDecoder, paramLdapResult, this.isLdapv3);
/*  694 */         paramLdapResult.resControls = this.isLdapv3 ? parseControls(berDecoder) : null;
/*      */         
/*  696 */         this.conn.removeRequest(paramLdapRequest);
/*  697 */         return paramLdapResult;
/*      */       } 
/*      */     } 
/*      */     
/*  701 */     return paramLdapResult;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Attribute parseAttribute(BerDecoder paramBerDecoder, Hashtable<String, Boolean> paramHashtable) throws IOException {
/*  708 */     int[] arrayOfInt = new int[1];
/*  709 */     int i = paramBerDecoder.parseSeq(null);
/*  710 */     String str = paramBerDecoder.parseString(this.isLdapv3);
/*  711 */     boolean bool = isBinaryValued(str, paramHashtable);
/*  712 */     LdapAttribute ldapAttribute = new LdapAttribute(str);
/*      */     
/*  714 */     if ((i = paramBerDecoder.parseSeq(arrayOfInt)) == 49) {
/*  715 */       int j = arrayOfInt[0];
/*  716 */       while (paramBerDecoder.bytesLeft() > 0 && j > 0) {
/*      */         try {
/*  718 */           j -= parseAttributeValue(paramBerDecoder, ldapAttribute, bool);
/*  719 */         } catch (IOException iOException) {
/*  720 */           paramBerDecoder.seek(j);
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } else {
/*  726 */       paramBerDecoder.seek(arrayOfInt[0]);
/*      */     } 
/*  728 */     return ldapAttribute;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int parseAttributeValue(BerDecoder paramBerDecoder, Attribute paramAttribute, boolean paramBoolean) throws IOException {
/*  737 */     int[] arrayOfInt = new int[1];
/*      */     
/*  739 */     if (paramBoolean) {
/*  740 */       paramAttribute.add(paramBerDecoder.parseOctetString(paramBerDecoder.peekByte(), arrayOfInt));
/*      */     } else {
/*  742 */       paramAttribute.add(paramBerDecoder.parseStringWithTag(4, this.isLdapv3, arrayOfInt));
/*      */     } 
/*      */     
/*  745 */     return arrayOfInt[0];
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isBinaryValued(String paramString, Hashtable<String, Boolean> paramHashtable) {
/*  750 */     String str = paramString.toLowerCase(Locale.ENGLISH);
/*      */     
/*  752 */     return (str.indexOf(";binary") != -1 || defaultBinaryAttrs
/*  753 */       .containsKey(str) || (paramHashtable != null && paramHashtable
/*  754 */       .containsKey(str)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void parseResult(BerDecoder paramBerDecoder, LdapResult paramLdapResult, boolean paramBoolean) throws IOException {
/*  761 */     paramLdapResult.status = paramBerDecoder.parseEnumeration();
/*  762 */     paramLdapResult.matchedDN = paramBerDecoder.parseString(paramBoolean);
/*  763 */     paramLdapResult.errorMessage = paramBerDecoder.parseString(paramBoolean);
/*      */ 
/*      */     
/*  766 */     if (paramBoolean && paramBerDecoder
/*  767 */       .bytesLeft() > 0 && paramBerDecoder
/*  768 */       .peekByte() == 163) {
/*      */       
/*  770 */       Vector<String> vector = new Vector(4);
/*  771 */       int[] arrayOfInt = new int[1];
/*      */       
/*  773 */       paramBerDecoder.parseSeq(arrayOfInt);
/*  774 */       int i = paramBerDecoder.getParsePosition() + arrayOfInt[0];
/*  775 */       while (paramBerDecoder.getParsePosition() < i && paramBerDecoder
/*  776 */         .bytesLeft() > 0)
/*      */       {
/*  778 */         vector.addElement(paramBerDecoder.parseString(paramBoolean));
/*      */       }
/*      */       
/*  781 */       if (paramLdapResult.referrals == null) {
/*  782 */         paramLdapResult.referrals = new Vector<>(4);
/*      */       }
/*  784 */       paramLdapResult.referrals.addElement(vector);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Vector<Control> parseControls(BerDecoder paramBerDecoder) throws IOException {
/*  792 */     if (paramBerDecoder.bytesLeft() > 0 && paramBerDecoder.peekByte() == 160) {
/*  793 */       Vector<BasicControl> vector = new Vector(4);
/*      */       
/*  795 */       boolean bool = false;
/*  796 */       byte[] arrayOfByte = null;
/*  797 */       int[] arrayOfInt = new int[1];
/*      */       
/*  799 */       paramBerDecoder.parseSeq(arrayOfInt);
/*  800 */       int i = paramBerDecoder.getParsePosition() + arrayOfInt[0];
/*  801 */       while (paramBerDecoder.getParsePosition() < i && paramBerDecoder
/*  802 */         .bytesLeft() > 0) {
/*      */         
/*  804 */         paramBerDecoder.parseSeq(null);
/*  805 */         String str = paramBerDecoder.parseString(true);
/*      */         
/*  807 */         if (paramBerDecoder.bytesLeft() > 0 && paramBerDecoder
/*  808 */           .peekByte() == 1) {
/*  809 */           bool = paramBerDecoder.parseBoolean();
/*      */         }
/*  811 */         if (paramBerDecoder.bytesLeft() > 0 && paramBerDecoder
/*  812 */           .peekByte() == 4)
/*      */         {
/*  814 */           arrayOfByte = paramBerDecoder.parseOctetString(4, null);
/*      */         }
/*  816 */         if (str != null) {
/*  817 */           vector.addElement(new BasicControl(str, bool, arrayOfByte));
/*      */         }
/*      */       } 
/*      */       
/*  821 */       return (Vector)vector;
/*      */     } 
/*  823 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseExtResponse(BerDecoder paramBerDecoder, LdapResult paramLdapResult) throws IOException {
/*  830 */     parseResult(paramBerDecoder, paramLdapResult, this.isLdapv3);
/*      */     
/*  832 */     if (paramBerDecoder.bytesLeft() > 0 && paramBerDecoder
/*  833 */       .peekByte() == 138) {
/*  834 */       paramLdapResult
/*  835 */         .extensionId = paramBerDecoder.parseStringWithTag(138, this.isLdapv3, null);
/*      */     }
/*  837 */     if (paramBerDecoder.bytesLeft() > 0 && paramBerDecoder
/*  838 */       .peekByte() == 139) {
/*  839 */       paramLdapResult
/*  840 */         .extensionValue = paramBerDecoder.parseOctetString(139, null);
/*      */     }
/*      */     
/*  843 */     paramLdapResult.resControls = parseControls(paramBerDecoder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void encodeControls(BerEncoder paramBerEncoder, Control[] paramArrayOfControl) throws IOException {
/*  852 */     if (paramArrayOfControl == null || paramArrayOfControl.length == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  858 */     paramBerEncoder.beginSeq(160);
/*      */     
/*  860 */     for (byte b = 0; b < paramArrayOfControl.length; b++) {
/*  861 */       paramBerEncoder.beginSeq(48);
/*  862 */       paramBerEncoder.encodeString(paramArrayOfControl[b].getID(), true);
/*  863 */       if (paramArrayOfControl[b].isCritical())
/*  864 */         paramBerEncoder.encodeBoolean(true); 
/*      */       byte[] arrayOfByte;
/*  866 */       if ((arrayOfByte = paramArrayOfControl[b].getEncodedValue()) != null) {
/*  867 */         paramBerEncoder.encodeOctetString(arrayOfByte, 4);
/*      */       }
/*  869 */       paramBerEncoder.endSeq();
/*      */     } 
/*  871 */     paramBerEncoder.endSeq();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LdapResult processReply(LdapRequest paramLdapRequest, LdapResult paramLdapResult, int paramInt) throws IOException, NamingException {
/*  881 */     BerDecoder berDecoder = this.conn.readReply(paramLdapRequest);
/*      */     
/*  883 */     berDecoder.parseSeq(null);
/*  884 */     berDecoder.parseInt();
/*  885 */     if (berDecoder.parseByte() != paramInt) {
/*  886 */       return paramLdapResult;
/*      */     }
/*      */     
/*  889 */     berDecoder.parseLength();
/*  890 */     parseResult(berDecoder, paramLdapResult, this.isLdapv3);
/*  891 */     paramLdapResult.resControls = this.isLdapv3 ? parseControls(berDecoder) : null;
/*      */     
/*  893 */     this.conn.removeRequest(paramLdapRequest);
/*      */     
/*  895 */     return paramLdapResult;
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
/*      */   LdapResult modify(String paramString, int[] paramArrayOfint, Attribute[] paramArrayOfAttribute, Control[] paramArrayOfControl) throws IOException, NamingException {
/*  919 */     ensureOpen();
/*      */     
/*  921 */     LdapResult ldapResult = new LdapResult();
/*  922 */     ldapResult.status = 1;
/*      */     
/*  924 */     if (paramString == null || paramArrayOfint.length != paramArrayOfAttribute.length) {
/*  925 */       return ldapResult;
/*      */     }
/*  927 */     BerEncoder berEncoder = new BerEncoder();
/*  928 */     int i = this.conn.getMsgId();
/*      */     
/*  930 */     berEncoder.beginSeq(48);
/*  931 */     berEncoder.encodeInt(i);
/*  932 */     berEncoder.beginSeq(102);
/*  933 */     berEncoder.encodeString(paramString, this.isLdapv3);
/*  934 */     berEncoder.beginSeq(48);
/*  935 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/*  936 */       berEncoder.beginSeq(48);
/*  937 */       berEncoder.encodeInt(paramArrayOfint[b], 10);
/*      */ 
/*      */       
/*  940 */       if (paramArrayOfint[b] == 0 && hasNoValue(paramArrayOfAttribute[b])) {
/*  941 */         throw new InvalidAttributeValueException("'" + paramArrayOfAttribute[b]
/*  942 */             .getID() + "' has no values.");
/*      */       }
/*  944 */       encodeAttribute(berEncoder, paramArrayOfAttribute[b]);
/*      */       
/*  946 */       berEncoder.endSeq();
/*      */     } 
/*  948 */     berEncoder.endSeq();
/*  949 */     berEncoder.endSeq();
/*  950 */     if (this.isLdapv3) encodeControls(berEncoder, paramArrayOfControl); 
/*  951 */     berEncoder.endSeq();
/*      */     
/*  953 */     LdapRequest ldapRequest = this.conn.writeRequest(berEncoder, i);
/*      */     
/*  955 */     return processReply(ldapRequest, ldapResult, 103);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void encodeAttribute(BerEncoder paramBerEncoder, Attribute paramAttribute) throws IOException, NamingException {
/*  961 */     paramBerEncoder.beginSeq(48);
/*  962 */     paramBerEncoder.encodeString(paramAttribute.getID(), this.isLdapv3);
/*  963 */     paramBerEncoder.beginSeq(49);
/*  964 */     NamingEnumeration<?> namingEnumeration = paramAttribute.getAll();
/*      */     
/*  966 */     while (namingEnumeration.hasMore()) {
/*  967 */       Object object = namingEnumeration.next();
/*  968 */       if (object instanceof String) {
/*  969 */         paramBerEncoder.encodeString((String)object, this.isLdapv3); continue;
/*  970 */       }  if (object instanceof byte[]) {
/*  971 */         paramBerEncoder.encodeOctetString((byte[])object, 4); continue;
/*  972 */       }  if (object == null) {
/*      */         continue;
/*      */       }
/*  975 */       throw new InvalidAttributeValueException("Malformed '" + paramAttribute
/*  976 */           .getID() + "' attribute value");
/*      */     } 
/*      */     
/*  979 */     paramBerEncoder.endSeq();
/*  980 */     paramBerEncoder.endSeq();
/*      */   }
/*      */   
/*      */   private static boolean hasNoValue(Attribute paramAttribute) throws NamingException {
/*  984 */     return (paramAttribute.size() == 0 || (paramAttribute.size() == 1 && paramAttribute.get() == null));
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
/*      */   LdapResult add(LdapEntry paramLdapEntry, Control[] paramArrayOfControl) throws IOException, NamingException {
/*  997 */     ensureOpen();
/*      */     
/*  999 */     LdapResult ldapResult = new LdapResult();
/* 1000 */     ldapResult.status = 1;
/*      */     
/* 1002 */     if (paramLdapEntry == null || paramLdapEntry.DN == null) {
/* 1003 */       return ldapResult;
/*      */     }
/* 1005 */     BerEncoder berEncoder = new BerEncoder();
/* 1006 */     int i = this.conn.getMsgId();
/*      */ 
/*      */     
/* 1009 */     berEncoder.beginSeq(48);
/* 1010 */     berEncoder.encodeInt(i);
/* 1011 */     berEncoder.beginSeq(104);
/* 1012 */     berEncoder.encodeString(paramLdapEntry.DN, this.isLdapv3);
/* 1013 */     berEncoder.beginSeq(48);
/*      */     
/* 1015 */     NamingEnumeration<? extends Attribute> namingEnumeration = paramLdapEntry.attributes.getAll();
/* 1016 */     while (namingEnumeration.hasMore()) {
/* 1017 */       Attribute attribute = namingEnumeration.next();
/*      */ 
/*      */       
/* 1020 */       if (hasNoValue(attribute)) {
/* 1021 */         throw new InvalidAttributeValueException("'" + attribute
/* 1022 */             .getID() + "' has no values.");
/*      */       }
/* 1024 */       encodeAttribute(berEncoder, attribute);
/*      */     } 
/*      */     
/* 1027 */     berEncoder.endSeq();
/* 1028 */     berEncoder.endSeq();
/* 1029 */     if (this.isLdapv3) encodeControls(berEncoder, paramArrayOfControl); 
/* 1030 */     berEncoder.endSeq();
/*      */     
/* 1032 */     LdapRequest ldapRequest = this.conn.writeRequest(berEncoder, i);
/* 1033 */     return processReply(ldapRequest, ldapResult, 105);
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
/*      */   LdapResult delete(String paramString, Control[] paramArrayOfControl) throws IOException, NamingException {
/* 1046 */     ensureOpen();
/*      */     
/* 1048 */     LdapResult ldapResult = new LdapResult();
/* 1049 */     ldapResult.status = 1;
/*      */     
/* 1051 */     if (paramString == null) {
/* 1052 */       return ldapResult;
/*      */     }
/* 1054 */     BerEncoder berEncoder = new BerEncoder();
/* 1055 */     int i = this.conn.getMsgId();
/*      */     
/* 1057 */     berEncoder.beginSeq(48);
/* 1058 */     berEncoder.encodeInt(i);
/* 1059 */     berEncoder.encodeString(paramString, 74, this.isLdapv3);
/* 1060 */     if (this.isLdapv3) encodeControls(berEncoder, paramArrayOfControl); 
/* 1061 */     berEncoder.endSeq();
/*      */     
/* 1063 */     LdapRequest ldapRequest = this.conn.writeRequest(berEncoder, i);
/*      */     
/* 1065 */     return processReply(ldapRequest, ldapResult, 107);
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
/*      */   LdapResult moddn(String paramString1, String paramString2, boolean paramBoolean, String paramString3, Control[] paramArrayOfControl) throws IOException, NamingException {
/* 1085 */     ensureOpen();
/*      */ 
/*      */     
/* 1088 */     boolean bool = (paramString3 != null && paramString3.length() > 0) ? true : false;
/*      */     
/* 1090 */     LdapResult ldapResult = new LdapResult();
/* 1091 */     ldapResult.status = 1;
/*      */     
/* 1093 */     if (paramString1 == null || paramString2 == null) {
/* 1094 */       return ldapResult;
/*      */     }
/* 1096 */     BerEncoder berEncoder = new BerEncoder();
/* 1097 */     int i = this.conn.getMsgId();
/*      */     
/* 1099 */     berEncoder.beginSeq(48);
/* 1100 */     berEncoder.encodeInt(i);
/* 1101 */     berEncoder.beginSeq(108);
/* 1102 */     berEncoder.encodeString(paramString1, this.isLdapv3);
/* 1103 */     berEncoder.encodeString(paramString2, this.isLdapv3);
/* 1104 */     berEncoder.encodeBoolean(paramBoolean);
/* 1105 */     if (this.isLdapv3 && bool)
/*      */     {
/* 1107 */       berEncoder.encodeString(paramString3, 128, this.isLdapv3);
/*      */     }
/* 1109 */     berEncoder.endSeq();
/* 1110 */     if (this.isLdapv3) encodeControls(berEncoder, paramArrayOfControl); 
/* 1111 */     berEncoder.endSeq();
/*      */ 
/*      */     
/* 1114 */     LdapRequest ldapRequest = this.conn.writeRequest(berEncoder, i);
/*      */     
/* 1116 */     return processReply(ldapRequest, ldapResult, 109);
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
/*      */   LdapResult compare(String paramString1, String paramString2, String paramString3, Control[] paramArrayOfControl) throws IOException, NamingException {
/* 1129 */     ensureOpen();
/*      */     
/* 1131 */     LdapResult ldapResult = new LdapResult();
/* 1132 */     ldapResult.status = 1;
/*      */     
/* 1134 */     if (paramString1 == null || paramString2 == null || paramString3 == null) {
/* 1135 */       return ldapResult;
/*      */     }
/* 1137 */     BerEncoder berEncoder = new BerEncoder();
/* 1138 */     int i = this.conn.getMsgId();
/*      */     
/* 1140 */     berEncoder.beginSeq(48);
/* 1141 */     berEncoder.encodeInt(i);
/* 1142 */     berEncoder.beginSeq(110);
/* 1143 */     berEncoder.encodeString(paramString1, this.isLdapv3);
/* 1144 */     berEncoder.beginSeq(48);
/* 1145 */     berEncoder.encodeString(paramString2, this.isLdapv3);
/*      */ 
/*      */ 
/*      */     
/* 1149 */     byte[] arrayOfByte = this.isLdapv3 ? paramString3.getBytes("UTF8") : paramString3.getBytes("8859_1");
/* 1150 */     berEncoder.encodeOctetString(
/* 1151 */         Filter.unescapeFilterValue(arrayOfByte, 0, arrayOfByte.length), 4);
/*      */ 
/*      */     
/* 1154 */     berEncoder.endSeq();
/* 1155 */     berEncoder.endSeq();
/* 1156 */     if (this.isLdapv3) encodeControls(berEncoder, paramArrayOfControl); 
/* 1157 */     berEncoder.endSeq();
/*      */     
/* 1159 */     LdapRequest ldapRequest = this.conn.writeRequest(berEncoder, i);
/*      */     
/* 1161 */     return processReply(ldapRequest, ldapResult, 111);
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
/*      */   LdapResult extendedOp(String paramString, byte[] paramArrayOfbyte, Control[] paramArrayOfControl, boolean paramBoolean) throws IOException, NamingException {
/* 1173 */     ensureOpen();
/*      */     
/* 1175 */     LdapResult ldapResult = new LdapResult();
/* 1176 */     ldapResult.status = 1;
/*      */     
/* 1178 */     if (paramString == null) {
/* 1179 */       return ldapResult;
/*      */     }
/* 1181 */     BerEncoder berEncoder = new BerEncoder();
/* 1182 */     int i = this.conn.getMsgId();
/*      */     
/* 1184 */     berEncoder.beginSeq(48);
/* 1185 */     berEncoder.encodeInt(i);
/* 1186 */     berEncoder.beginSeq(119);
/* 1187 */     berEncoder.encodeString(paramString, 128, this.isLdapv3);
/*      */     
/* 1189 */     if (paramArrayOfbyte != null) {
/* 1190 */       berEncoder.encodeOctetString(paramArrayOfbyte, 129);
/*      */     }
/*      */     
/* 1193 */     berEncoder.endSeq();
/* 1194 */     encodeControls(berEncoder, paramArrayOfControl);
/* 1195 */     berEncoder.endSeq();
/*      */     
/* 1197 */     LdapRequest ldapRequest = this.conn.writeRequest(berEncoder, i, paramBoolean);
/*      */     
/* 1199 */     BerDecoder berDecoder = this.conn.readReply(ldapRequest);
/*      */     
/* 1201 */     berDecoder.parseSeq(null);
/* 1202 */     berDecoder.parseInt();
/* 1203 */     if (berDecoder.parseByte() != 120) {
/* 1204 */       return ldapResult;
/*      */     }
/*      */     
/* 1207 */     berDecoder.parseLength();
/* 1208 */     parseExtResponse(berDecoder, ldapResult);
/* 1209 */     this.conn.removeRequest(ldapRequest);
/*      */     
/* 1211 */     return ldapResult;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1329 */   static final String[] ldap_error_message = new String[] { "Success", "Operations Error", "Protocol Error", "Timelimit Exceeded", "Sizelimit Exceeded", "Compare False", "Compare True", "Authentication Method Not Supported", "Strong Authentication Required", null, "Referral", "Administrative Limit Exceeded", "Unavailable Critical Extension", "Confidentiality Required", "SASL Bind In Progress", null, "No Such Attribute", "Undefined Attribute Type", "Inappropriate Matching", "Constraint Violation", "Attribute Or Value Exists", "Invalid Attribute Syntax", null, null, null, null, null, null, null, null, null, null, "No Such Object", "Alias Problem", "Invalid DN Syntax", null, "Alias Dereferencing Problem", null, null, null, null, null, null, null, null, null, null, null, "Inappropriate Authentication", "Invalid Credentials", "Insufficient Access Rights", "Busy", "Unavailable", "Unwilling To Perform", "Loop Detect", null, null, null, null, null, null, null, null, null, "Naming Violation", "Object Class Violation", "Not Allowed On Non-leaf", "Not Allowed On RDN", "Entry Already Exists", "Object Class Modifications Prohibited", null, "Affects Multiple DSAs", null, null, null, null, null, null, null, null, "Other", null, null, null, null, null, null, null, null, null, null };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector<LdapCtx> unsolicited;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String getErrorMessage(int paramInt, String paramString) {
/* 1436 */     String str = "[LDAP: error code " + paramInt;
/*      */     
/* 1438 */     if (paramString != null && paramString.length() != 0) {
/*      */ 
/*      */       
/* 1441 */       str = str + " - " + paramString + "]";
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/* 1447 */         if (ldap_error_message[paramInt] != null) {
/* 1448 */           str = str + " - " + ldap_error_message[paramInt] + "]";
/*      */         }
/*      */       }
/* 1451 */       catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 1452 */         str = str + "]";
/*      */       } 
/*      */     } 
/* 1455 */     return str;
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
/*      */   LdapClient(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3, OutputStream paramOutputStream, PoolCallback paramPoolCallback) throws NamingException {
/* 1483 */     this.unsolicited = new Vector<>(3);
/*      */     this.conn = new Connection(this, paramString1, paramInt1, paramString2, paramInt2, paramInt3, paramOutputStream);
/*      */     this.pcb = paramPoolCallback;
/*      */     this.pooled = (paramPoolCallback != null);
/*      */   } void addUnsolicited(LdapCtx paramLdapCtx) {
/* 1488 */     this.unsolicited.addElement(paramLdapCtx);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void removeUnsolicited(LdapCtx paramLdapCtx) {
/* 1495 */     this.unsolicited.removeElement(paramLdapCtx);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void processUnsolicited(BerDecoder paramBerDecoder) {
/*      */     try {
/* 1506 */       LdapResult ldapResult = new LdapResult();
/*      */       
/* 1508 */       paramBerDecoder.parseSeq(null);
/* 1509 */       paramBerDecoder.parseInt();
/* 1510 */       if (paramBerDecoder.parseByte() != 120) {
/* 1511 */         throw new IOException("Unsolicited Notification must be an Extended Response");
/*      */       }
/*      */       
/* 1514 */       paramBerDecoder.parseLength();
/* 1515 */       parseExtResponse(paramBerDecoder, ldapResult);
/*      */       
/* 1517 */       if ("1.3.6.1.4.1.1466.20036".equals(ldapResult.extensionId))
/*      */       {
/* 1519 */         forceClose(this.pooled);
/*      */       }
/*      */       
/* 1522 */       LdapCtx ldapCtx = null;
/* 1523 */       UnsolicitedResponseImpl unsolicitedResponseImpl = null;
/*      */       
/* 1525 */       synchronized (this.unsolicited) {
/* 1526 */         if (this.unsolicited.size() > 0) {
/* 1527 */           ldapCtx = this.unsolicited.elementAt(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1540 */           unsolicitedResponseImpl = new UnsolicitedResponseImpl(ldapResult.extensionId, ldapResult.extensionValue, ldapResult.referrals, ldapResult.status, ldapResult.errorMessage, ldapResult.matchedDN, (ldapResult.resControls != null) ? ldapCtx.convertControls(ldapResult.resControls) : null);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1545 */       if (unsolicitedResponseImpl != null)
/*      */       {
/* 1547 */         notifyUnsolicited(unsolicitedResponseImpl);
/*      */ 
/*      */ 
/*      */         
/* 1551 */         if ("1.3.6.1.4.1.1466.20036".equals(ldapResult.extensionId)) {
/* 1552 */           notifyUnsolicited(new CommunicationException("Connection closed"));
/*      */         }
/*      */       }
/*      */     
/* 1556 */     } catch (IOException iOException) {
/* 1557 */       CommunicationException communicationException = new CommunicationException("Problem parsing unsolicited notification");
/*      */       
/* 1559 */       communicationException.setRootCause(iOException);
/*      */       
/* 1561 */       notifyUnsolicited(communicationException);
/*      */     }
/* 1563 */     catch (NamingException namingException) {
/* 1564 */       notifyUnsolicited(namingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void notifyUnsolicited(Object paramObject) {
/*      */     Vector<LdapCtx> vector;
/* 1571 */     synchronized (this.unsolicited) {
/* 1572 */       vector = new Vector<>(this.unsolicited);
/* 1573 */       if (paramObject instanceof NamingException) {
/* 1574 */         this.unsolicited.setSize(0);
/*      */       }
/*      */     } 
/* 1577 */     for (byte b = 0; b < vector.size(); b++) {
/* 1578 */       ((LdapCtx)vector.elementAt(b)).fireUnsolicited(paramObject);
/*      */     }
/*      */   }
/*      */   
/*      */   private void ensureOpen() throws IOException {
/* 1583 */     if (this.conn == null || !this.conn.useable) {
/* 1584 */       if (this.conn != null && this.conn.closureReason != null) {
/* 1585 */         throw this.conn.closureReason;
/*      */       }
/* 1587 */       throw new IOException("connection closed");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static LdapClient getInstance(boolean paramBoolean, String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3, OutputStream paramOutputStream, int paramInt4, String paramString3, Control[] paramArrayOfControl, String paramString4, String paramString5, Object paramObject, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 1598 */     if (paramBoolean && 
/* 1599 */       LdapPoolManager.isPoolingAllowed(paramString2, paramOutputStream, paramString3, paramString4, paramHashtable)) {
/*      */       
/* 1601 */       LdapClient ldapClient = LdapPoolManager.getLdapClient(paramString1, paramInt1, paramString2, paramInt2, paramInt3, paramOutputStream, paramInt4, paramString3, paramArrayOfControl, paramString4, paramString5, paramObject, paramHashtable);
/*      */ 
/*      */ 
/*      */       
/* 1605 */       ldapClient.referenceCount = 1;
/* 1606 */       return ldapClient;
/*      */     } 
/*      */     
/* 1609 */     return new LdapClient(paramString1, paramInt1, paramString2, paramInt2, paramInt3, paramOutputStream, null);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */