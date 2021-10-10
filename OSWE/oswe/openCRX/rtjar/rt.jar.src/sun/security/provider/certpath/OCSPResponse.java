/*      */ package sun.security.provider.certpath;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.security.AccessController;
/*      */ import java.security.GeneralSecurityException;
/*      */ import java.security.InvalidKeyException;
/*      */ import java.security.PublicKey;
/*      */ import java.security.Signature;
/*      */ import java.security.cert.CRLReason;
/*      */ import java.security.cert.CertPathValidatorException;
/*      */ import java.security.cert.CertificateException;
/*      */ import java.security.cert.CertificateParsingException;
/*      */ import java.security.cert.Extension;
/*      */ import java.security.cert.TrustAnchor;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import sun.misc.HexDumpEncoder;
/*      */ import sun.security.action.GetIntegerAction;
/*      */ import sun.security.util.Debug;
/*      */ import sun.security.util.DerInputStream;
/*      */ import sun.security.util.DerValue;
/*      */ import sun.security.util.ObjectIdentifier;
/*      */ import sun.security.x509.AlgorithmId;
/*      */ import sun.security.x509.Extension;
/*      */ import sun.security.x509.KeyIdentifier;
/*      */ import sun.security.x509.PKIXExtensions;
/*      */ import sun.security.x509.X509CertImpl;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class OCSPResponse
/*      */ {
/*      */   public enum ResponseStatus
/*      */   {
/*  125 */     SUCCESSFUL,
/*  126 */     MALFORMED_REQUEST,
/*  127 */     INTERNAL_ERROR,
/*  128 */     TRY_LATER,
/*  129 */     UNUSED,
/*  130 */     SIG_REQUIRED,
/*  131 */     UNAUTHORIZED;
/*      */   }
/*  133 */   private static final ResponseStatus[] rsvalues = ResponseStatus.values();
/*      */   
/*  135 */   private static final Debug debug = Debug.getInstance("certpath");
/*  136 */   private static final boolean dump = (debug != null && Debug.isOn("ocsp"));
/*      */   
/*  138 */   private static final ObjectIdentifier OCSP_BASIC_RESPONSE_OID = ObjectIdentifier.newInternal(new int[] { 1, 3, 6, 1, 5, 5, 7, 48, 1, 1 });
/*      */ 
/*      */   
/*      */   private static final int CERT_STATUS_GOOD = 0;
/*      */ 
/*      */   
/*      */   private static final int CERT_STATUS_REVOKED = 1;
/*      */ 
/*      */   
/*      */   private static final int CERT_STATUS_UNKNOWN = 2;
/*      */ 
/*      */   
/*      */   private static final int NAME_TAG = 1;
/*      */   
/*      */   private static final int KEY_TAG = 2;
/*      */   
/*      */   private static final String KP_OCSP_SIGNING_OID = "1.3.6.1.5.5.7.3.9";
/*      */   
/*      */   private static final int DEFAULT_MAX_CLOCK_SKEW = 900000;
/*      */   
/*  158 */   private static final int MAX_CLOCK_SKEW = initializeClockSkew();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int initializeClockSkew() {
/*  166 */     Integer integer = AccessController.<Integer>doPrivileged(new GetIntegerAction("com.sun.security.ocsp.clockSkew"));
/*      */     
/*  168 */     if (integer == null || integer.intValue() < 0) {
/*  169 */       return 900000;
/*      */     }
/*      */ 
/*      */     
/*  173 */     return integer.intValue() * 1000;
/*      */   }
/*      */ 
/*      */   
/*  177 */   private static final CRLReason[] values = CRLReason.values();
/*      */   private final ResponseStatus responseStatus;
/*      */   private final Map<CertId, SingleResponse> singleResponseMap;
/*      */   private final AlgorithmId sigAlgId;
/*      */   private final byte[] signature;
/*      */   private final byte[] tbsResponseData;
/*      */   
/*      */   public OCSPResponse(byte[] paramArrayOfbyte) throws IOException {
/*      */     Map<String, Extension> map;
/*  186 */     this.signerCert = null;
/*      */     
/*  188 */     this.producedAtDate = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  197 */     if (dump) {
/*  198 */       HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/*  199 */       debug.println("OCSPResponse bytes...\n\n" + hexDumpEncoder
/*  200 */           .encode(paramArrayOfbyte) + "\n");
/*      */     } 
/*  202 */     DerValue derValue1 = new DerValue(paramArrayOfbyte);
/*  203 */     if (derValue1.tag != 48) {
/*  204 */       throw new IOException("Bad encoding in OCSP response: expected ASN.1 SEQUENCE tag.");
/*      */     }
/*      */     
/*  207 */     DerInputStream derInputStream1 = derValue1.getData();
/*      */ 
/*      */     
/*  210 */     int i = derInputStream1.getEnumerated();
/*  211 */     if (i >= 0 && i < rsvalues.length) {
/*  212 */       this.responseStatus = rsvalues[i];
/*      */     } else {
/*      */       
/*  215 */       throw new IOException("Unknown OCSPResponse status: " + i);
/*      */     } 
/*  217 */     if (debug != null) {
/*  218 */       debug.println("OCSP response status: " + this.responseStatus);
/*      */     }
/*  220 */     if (this.responseStatus != ResponseStatus.SUCCESSFUL) {
/*      */       
/*  222 */       this.singleResponseMap = Collections.emptyMap();
/*  223 */       this.certs = new ArrayList<>();
/*  224 */       this.sigAlgId = null;
/*  225 */       this.signature = null;
/*  226 */       this.tbsResponseData = null;
/*  227 */       this.responseNonce = null;
/*  228 */       this.responseExtensions = Collections.emptyMap();
/*  229 */       this.respId = null;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  234 */     derValue1 = derInputStream1.getDerValue();
/*  235 */     if (!derValue1.isContextSpecific((byte)0)) {
/*  236 */       throw new IOException("Bad encoding in responseBytes element of OCSP response: expected ASN.1 context specific tag 0.");
/*      */     }
/*      */     
/*  239 */     DerValue derValue2 = derValue1.data.getDerValue();
/*  240 */     if (derValue2.tag != 48) {
/*  241 */       throw new IOException("Bad encoding in responseBytes element of OCSP response: expected ASN.1 SEQUENCE tag.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  246 */     derInputStream1 = derValue2.data;
/*  247 */     ObjectIdentifier objectIdentifier = derInputStream1.getOID();
/*  248 */     if (objectIdentifier.equals(OCSP_BASIC_RESPONSE_OID)) {
/*  249 */       if (debug != null) {
/*  250 */         debug.println("OCSP response type: basic");
/*      */       }
/*      */     } else {
/*  253 */       if (debug != null) {
/*  254 */         debug.println("OCSP response type: " + objectIdentifier);
/*      */       }
/*  256 */       throw new IOException("Unsupported OCSP response type: " + objectIdentifier);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  262 */     DerInputStream derInputStream2 = new DerInputStream(derInputStream1.getOctetString());
/*      */     
/*  264 */     DerValue[] arrayOfDerValue1 = derInputStream2.getSequence(2);
/*  265 */     if (arrayOfDerValue1.length < 3) {
/*  266 */       throw new IOException("Unexpected BasicOCSPResponse value");
/*      */     }
/*      */     
/*  269 */     DerValue derValue3 = arrayOfDerValue1[0];
/*      */ 
/*      */     
/*  272 */     this.tbsResponseData = arrayOfDerValue1[0].toByteArray();
/*      */ 
/*      */     
/*  275 */     if (derValue3.tag != 48) {
/*  276 */       throw new IOException("Bad encoding in tbsResponseData element of OCSP response: expected ASN.1 SEQUENCE tag.");
/*      */     }
/*      */     
/*  279 */     DerInputStream derInputStream3 = derValue3.data;
/*  280 */     DerValue derValue4 = derInputStream3.getDerValue();
/*      */ 
/*      */     
/*  283 */     if (derValue4.isContextSpecific((byte)0))
/*      */     {
/*  285 */       if (derValue4.isConstructed() && derValue4.isContextSpecific()) {
/*      */         
/*  287 */         derValue4 = derValue4.data.getDerValue();
/*  288 */         int j = derValue4.getInteger();
/*  289 */         if (derValue4.data.available() != 0) {
/*  290 */           throw new IOException("Bad encoding in version  element of OCSP response: bad format");
/*      */         }
/*      */         
/*  293 */         derValue4 = derInputStream3.getDerValue();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  298 */     this.respId = new ResponderId(derValue4.toByteArray());
/*  299 */     if (debug != null) {
/*  300 */       debug.println("Responder ID: " + this.respId);
/*      */     }
/*      */ 
/*      */     
/*  304 */     derValue4 = derInputStream3.getDerValue();
/*  305 */     this.producedAtDate = derValue4.getGeneralizedTime();
/*  306 */     if (debug != null) {
/*  307 */       debug.println("OCSP response produced at: " + this.producedAtDate);
/*      */     }
/*      */ 
/*      */     
/*  311 */     DerValue[] arrayOfDerValue2 = derInputStream3.getSequence(1);
/*  312 */     this.singleResponseMap = new HashMap<>(arrayOfDerValue2.length);
/*  313 */     if (debug != null) {
/*  314 */       debug.println("OCSP number of SingleResponses: " + arrayOfDerValue2.length);
/*      */     }
/*      */     
/*  317 */     for (DerValue derValue : arrayOfDerValue2) {
/*  318 */       SingleResponse singleResponse = new SingleResponse(derValue);
/*  319 */       this.singleResponseMap.put(singleResponse.getCertId(), singleResponse);
/*      */     } 
/*      */ 
/*      */     
/*  323 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*  324 */     if (derInputStream3.available() > 0) {
/*  325 */       derValue4 = derInputStream3.getDerValue();
/*  326 */       if (derValue4.isContextSpecific((byte)1)) {
/*  327 */         map = parseExtensions(derValue4);
/*      */       }
/*      */     } 
/*  330 */     this.responseExtensions = map;
/*      */ 
/*      */     
/*  333 */     Extension extension = (Extension)map.get(PKIXExtensions.OCSPNonce_Id
/*  334 */         .toString());
/*  335 */     this
/*  336 */       .responseNonce = (extension != null) ? extension.getExtensionValue() : null;
/*  337 */     if (debug != null && this.responseNonce != null) {
/*  338 */       debug.println("Response nonce: " + Arrays.toString(this.responseNonce));
/*      */     }
/*      */ 
/*      */     
/*  342 */     this.sigAlgId = AlgorithmId.parse(arrayOfDerValue1[1]);
/*      */ 
/*      */     
/*  345 */     this.signature = arrayOfDerValue1[2].getBitString();
/*      */ 
/*      */     
/*  348 */     if (arrayOfDerValue1.length > 3) {
/*      */       
/*  350 */       DerValue derValue = arrayOfDerValue1[3];
/*  351 */       if (!derValue.isContextSpecific((byte)0)) {
/*  352 */         throw new IOException("Bad encoding in certs element of OCSP response: expected ASN.1 context specific tag 0.");
/*      */       }
/*      */       
/*  355 */       DerValue[] arrayOfDerValue = derValue.getData().getSequence(3);
/*  356 */       this.certs = new ArrayList<>(arrayOfDerValue.length);
/*      */       try {
/*  358 */         for (byte b = 0; b < arrayOfDerValue.length; b++) {
/*      */           
/*  360 */           X509CertImpl x509CertImpl = new X509CertImpl(arrayOfDerValue[b].toByteArray());
/*  361 */           this.certs.add(x509CertImpl);
/*      */           
/*  363 */           if (debug != null) {
/*  364 */             debug.println("OCSP response cert #" + (b + 1) + ": " + x509CertImpl
/*  365 */                 .getSubjectX500Principal());
/*      */           }
/*      */         } 
/*  368 */       } catch (CertificateException certificateException) {
/*  369 */         throw new IOException("Bad encoding in X509 Certificate", certificateException);
/*      */       } 
/*      */     } else {
/*  372 */       this.certs = new ArrayList<>();
/*      */     } 
/*      */   }
/*      */   
/*      */   private final byte[] responseNonce;
/*      */   private List<X509CertImpl> certs;
/*      */   private X509CertImpl signerCert;
/*      */   
/*      */   void verify(List<CertId> paramList, IssuerInfo paramIssuerInfo, X509Certificate paramX509Certificate, Date paramDate, byte[] paramArrayOfbyte, String paramString) throws CertPathValidatorException {
/*  381 */     switch (this.responseStatus) {
/*      */       case SUCCESSFUL:
/*      */         break;
/*      */       case TRY_LATER:
/*      */       case INTERNAL_ERROR:
/*  386 */         throw new CertPathValidatorException("OCSP response error: " + this.responseStatus, null, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
/*      */ 
/*      */ 
/*      */       
/*      */       default:
/*  391 */         throw new CertPathValidatorException("OCSP response error: " + this.responseStatus);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  397 */     for (CertId certId : paramList) {
/*  398 */       SingleResponse singleResponse = getSingleResponse(certId);
/*  399 */       if (singleResponse == null) {
/*  400 */         if (debug != null) {
/*  401 */           debug.println("No response found for CertId: " + certId);
/*      */         }
/*  403 */         throw new CertPathValidatorException("OCSP response does not include a response for a certificate supplied in the OCSP request");
/*      */       } 
/*      */ 
/*      */       
/*  407 */       if (debug != null) {
/*  408 */         debug.println("Status of certificate (with serial number " + certId
/*  409 */             .getSerialNumber() + ") is: " + singleResponse.getCertStatus());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  414 */     if (this.signerCert == null) {
/*      */ 
/*      */       
/*      */       try {
/*  418 */         if (paramIssuerInfo.getCertificate() != null) {
/*  419 */           this.certs.add(X509CertImpl.toImpl(paramIssuerInfo.getCertificate()));
/*      */         }
/*  421 */         if (paramX509Certificate != null) {
/*  422 */           this.certs.add(X509CertImpl.toImpl(paramX509Certificate));
/*      */         }
/*  424 */       } catch (CertificateException certificateException) {
/*  425 */         throw new CertPathValidatorException("Invalid issuer or trusted responder certificate", certificateException);
/*      */       } 
/*      */ 
/*      */       
/*  429 */       if (this.respId.getType() == ResponderId.Type.BY_NAME) {
/*  430 */         X500Principal x500Principal = this.respId.getResponderName();
/*  431 */         for (X509CertImpl x509CertImpl : this.certs) {
/*  432 */           if (x509CertImpl.getSubjectX500Principal().equals(x500Principal)) {
/*  433 */             this.signerCert = x509CertImpl;
/*      */             break;
/*      */           } 
/*      */         } 
/*  437 */       } else if (this.respId.getType() == ResponderId.Type.BY_KEY) {
/*  438 */         KeyIdentifier keyIdentifier = this.respId.getKeyIdentifier();
/*  439 */         for (X509CertImpl x509CertImpl : this.certs) {
/*      */ 
/*      */ 
/*      */           
/*  443 */           KeyIdentifier keyIdentifier1 = x509CertImpl.getSubjectKeyId();
/*  444 */           if (keyIdentifier1 != null && keyIdentifier.equals(keyIdentifier1)) {
/*  445 */             this.signerCert = x509CertImpl;
/*      */ 
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */ 
/*      */           
/*      */           try {
/*  454 */             keyIdentifier1 = new KeyIdentifier(x509CertImpl.getPublicKey());
/*  455 */           } catch (IOException iOException) {}
/*      */ 
/*      */           
/*  458 */           if (keyIdentifier.equals(keyIdentifier1)) {
/*  459 */             this.signerCert = x509CertImpl;
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  468 */     if (this.signerCert != null)
/*      */     {
/*  470 */       if (this.signerCert.getSubjectX500Principal().equals(paramIssuerInfo
/*  471 */           .getName()) && this.signerCert
/*  472 */         .getPublicKey().equals(paramIssuerInfo
/*  473 */           .getPublicKey())) {
/*  474 */         if (debug != null) {
/*  475 */           debug.println("OCSP response is signed by the target's Issuing CA");
/*      */ 
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  481 */       else if (this.signerCert.equals(paramX509Certificate)) {
/*  482 */         if (debug != null) {
/*  483 */           debug.println("OCSP response is signed by a Trusted Responder");
/*      */ 
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  489 */       else if (this.signerCert.getIssuerX500Principal().equals(paramIssuerInfo
/*  490 */           .getName())) {
/*      */ 
/*      */         
/*      */         try {
/*  494 */           List<String> list = this.signerCert.getExtendedKeyUsage();
/*  495 */           if (list == null || 
/*  496 */             !list.contains("1.3.6.1.5.5.7.3.9")) {
/*  497 */             throw new CertPathValidatorException("Responder's certificate not valid for signing OCSP responses");
/*      */           
/*      */           }
/*      */         }
/*  501 */         catch (CertificateParsingException certificateParsingException) {
/*      */           
/*  503 */           throw new CertPathValidatorException("Responder's certificate not valid for signing OCSP responses", certificateParsingException);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  511 */         AlgorithmChecker algorithmChecker = new AlgorithmChecker(paramIssuerInfo.getAnchor(), paramDate, paramString);
/*      */         
/*  513 */         algorithmChecker.init(false);
/*  514 */         algorithmChecker.check(this.signerCert, Collections.emptySet());
/*      */ 
/*      */         
/*      */         try {
/*  518 */           if (paramDate == null) {
/*  519 */             this.signerCert.checkValidity();
/*      */           } else {
/*  521 */             this.signerCert.checkValidity(paramDate);
/*      */           } 
/*  523 */         } catch (CertificateException certificateException) {
/*  524 */           throw new CertPathValidatorException("Responder's certificate not within the validity period", certificateException);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  537 */         Extension extension = this.signerCert.getExtension(PKIXExtensions.OCSPNoCheck_Id);
/*  538 */         if (extension != null && 
/*  539 */           debug != null) {
/*  540 */           debug.println("Responder's certificate includes the extension id-pkix-ocsp-nocheck.");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  550 */           this.signerCert.verify(paramIssuerInfo.getPublicKey());
/*  551 */           if (debug != null) {
/*  552 */             debug.println("OCSP response is signed by an Authorized Responder");
/*      */           
/*      */           }
/*      */         
/*      */         }
/*  557 */         catch (GeneralSecurityException generalSecurityException) {
/*  558 */           this.signerCert = null;
/*      */         } 
/*      */       } else {
/*  561 */         throw new CertPathValidatorException("Responder's certificate is not authorized to sign OCSP responses");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  569 */     if (this.signerCert != null) {
/*      */ 
/*      */       
/*  572 */       AlgorithmChecker.check(this.signerCert.getPublicKey(), this.sigAlgId, paramString);
/*      */       
/*  574 */       if (!verifySignature(this.signerCert)) {
/*  575 */         throw new CertPathValidatorException("Error verifying OCSP Response's signature");
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  580 */       throw new CertPathValidatorException("Unable to verify OCSP Response's signature");
/*      */     } 
/*      */ 
/*      */     
/*  584 */     if (paramArrayOfbyte != null && 
/*  585 */       this.responseNonce != null && !Arrays.equals(paramArrayOfbyte, this.responseNonce)) {
/*  586 */       throw new CertPathValidatorException("Nonces don't match");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  591 */     long l = (paramDate == null) ? System.currentTimeMillis() : paramDate.getTime();
/*  592 */     Date date1 = new Date(l + MAX_CLOCK_SKEW);
/*  593 */     Date date2 = new Date(l - MAX_CLOCK_SKEW);
/*  594 */     for (SingleResponse singleResponse : this.singleResponseMap.values()) {
/*  595 */       if (debug != null) {
/*  596 */         String str = "";
/*  597 */         if (singleResponse.nextUpdate != null) {
/*  598 */           str = " until " + singleResponse.nextUpdate;
/*      */         }
/*  600 */         debug.println("OCSP response validity interval is from " + singleResponse
/*  601 */             .thisUpdate + str);
/*  602 */         debug.println("Checking validity of OCSP response on: " + new Date(l));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  609 */       if (date1.before(singleResponse.thisUpdate) || date2
/*  610 */         .after(
/*  611 */           (singleResponse.nextUpdate != null) ? singleResponse.nextUpdate : singleResponse.thisUpdate))
/*      */       {
/*  613 */         throw new CertPathValidatorException("Response is unreliable: its validity interval is out-of-date");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private final ResponderId respId;
/*      */   
/*      */   private Date producedAtDate;
/*      */   
/*      */   private final Map<String, Extension> responseExtensions;
/*      */   
/*      */   public ResponseStatus getResponseStatus() {
/*  626 */     return this.responseStatus;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean verifySignature(X509Certificate paramX509Certificate) throws CertPathValidatorException {
/*      */     try {
/*  636 */       Signature signature = Signature.getInstance(this.sigAlgId.getName());
/*  637 */       signature.initVerify(paramX509Certificate.getPublicKey());
/*  638 */       signature.update(this.tbsResponseData);
/*      */       
/*  640 */       if (signature.verify(this.signature)) {
/*  641 */         if (debug != null) {
/*  642 */           debug.println("Verified signature of OCSP Response");
/*      */         }
/*  644 */         return true;
/*      */       } 
/*      */       
/*  647 */       if (debug != null) {
/*  648 */         debug.println("Error verifying signature of OCSP Response");
/*      */       }
/*      */       
/*  651 */       return false;
/*      */     }
/*  653 */     catch (InvalidKeyException|java.security.NoSuchAlgorithmException|java.security.SignatureException invalidKeyException) {
/*      */ 
/*      */       
/*  656 */       throw new CertPathValidatorException(invalidKeyException);
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
/*      */   public SingleResponse getSingleResponse(CertId paramCertId) {
/*  671 */     return this.singleResponseMap.get(paramCertId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<CertId> getCertIds() {
/*  681 */     return Collections.unmodifiableSet(this.singleResponseMap.keySet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   X509Certificate getSignerCertificate() {
/*  688 */     return this.signerCert;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResponderId getResponderId() {
/*  699 */     return this.respId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  709 */     StringBuilder stringBuilder = new StringBuilder();
/*  710 */     stringBuilder.append("OCSP Response:\n");
/*  711 */     stringBuilder.append("Response Status: ").append(this.responseStatus).append("\n");
/*  712 */     stringBuilder.append("Responder ID: ").append(this.respId).append("\n");
/*  713 */     stringBuilder.append("Produced at: ").append(this.producedAtDate).append("\n");
/*  714 */     int i = this.singleResponseMap.size();
/*  715 */     stringBuilder.append(i).append((i == 1) ? " response:\n" : " responses:\n");
/*      */     
/*  717 */     for (SingleResponse singleResponse : this.singleResponseMap.values()) {
/*  718 */       stringBuilder.append(singleResponse).append("\n");
/*      */     }
/*  720 */     if (this.responseExtensions != null && this.responseExtensions.size() > 0) {
/*  721 */       i = this.responseExtensions.size();
/*  722 */       stringBuilder.append(i).append((i == 1) ? " extension:\n" : " extensions:\n");
/*      */       
/*  724 */       for (String str : this.responseExtensions.keySet()) {
/*  725 */         stringBuilder.append(this.responseExtensions.get(str)).append("\n");
/*      */       }
/*      */     } 
/*      */     
/*  729 */     return stringBuilder.toString();
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
/*      */   private static Map<String, Extension> parseExtensions(DerValue paramDerValue) throws IOException {
/*  745 */     DerValue[] arrayOfDerValue = paramDerValue.data.getSequence(3);
/*  746 */     HashMap<Object, Object> hashMap = new HashMap<>(arrayOfDerValue.length);
/*      */ 
/*      */     
/*  749 */     for (DerValue derValue : arrayOfDerValue) {
/*  750 */       Extension extension = new Extension(derValue);
/*  751 */       if (debug != null) {
/*  752 */         debug.println("Extension: " + extension);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  757 */       if (extension.isCritical()) {
/*  758 */         throw new IOException("Unsupported OCSP critical extension: " + extension
/*  759 */             .getExtensionId());
/*      */       }
/*  761 */       hashMap.put(extension.getId(), extension);
/*      */     } 
/*      */     
/*  764 */     return (Map)hashMap;
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class SingleResponse
/*      */     implements OCSP.RevocationStatus
/*      */   {
/*      */     private final CertId certId;
/*      */     private final OCSP.RevocationStatus.CertStatus certStatus;
/*      */     private final Date thisUpdate;
/*      */     private final Date nextUpdate;
/*      */     private final Date revocationTime;
/*      */     private final CRLReason revocationReason;
/*      */     private final Map<String, Extension> singleExtensions;
/*      */     
/*      */     private SingleResponse(DerValue param1DerValue) throws IOException {
/*  780 */       if (param1DerValue.tag != 48) {
/*  781 */         throw new IOException("Bad ASN.1 encoding in SingleResponse");
/*      */       }
/*  783 */       DerInputStream derInputStream = param1DerValue.data;
/*      */       
/*  785 */       this.certId = new CertId((derInputStream.getDerValue()).data);
/*  786 */       DerValue derValue = derInputStream.getDerValue();
/*  787 */       short s = (short)(byte)(derValue.tag & 0x1F);
/*  788 */       if (s == 1) {
/*  789 */         this.certStatus = OCSP.RevocationStatus.CertStatus.REVOKED;
/*  790 */         this.revocationTime = derValue.data.getGeneralizedTime();
/*  791 */         if (derValue.data.available() != 0) {
/*  792 */           DerValue derValue1 = derValue.data.getDerValue();
/*  793 */           s = (short)(byte)(derValue1.tag & 0x1F);
/*  794 */           if (s == 0) {
/*  795 */             int i = derValue1.data.getEnumerated();
/*      */             
/*  797 */             if (i >= 0 && i < OCSPResponse.values.length) {
/*  798 */               this.revocationReason = OCSPResponse.values[i];
/*      */             } else {
/*  800 */               this.revocationReason = CRLReason.UNSPECIFIED;
/*      */             } 
/*      */           } else {
/*  803 */             this.revocationReason = CRLReason.UNSPECIFIED;
/*      */           } 
/*      */         } else {
/*  806 */           this.revocationReason = CRLReason.UNSPECIFIED;
/*      */         } 
/*      */         
/*  809 */         if (OCSPResponse.debug != null) {
/*  810 */           OCSPResponse.debug.println("Revocation time: " + this.revocationTime);
/*  811 */           OCSPResponse.debug.println("Revocation reason: " + this.revocationReason);
/*      */         } 
/*      */       } else {
/*  814 */         this.revocationTime = null;
/*  815 */         this.revocationReason = null;
/*  816 */         if (s == 0) {
/*  817 */           this.certStatus = OCSP.RevocationStatus.CertStatus.GOOD;
/*  818 */         } else if (s == 2) {
/*  819 */           this.certStatus = OCSP.RevocationStatus.CertStatus.UNKNOWN;
/*      */         } else {
/*  821 */           throw new IOException("Invalid certificate status");
/*      */         } 
/*      */       } 
/*      */       
/*  825 */       this.thisUpdate = derInputStream.getGeneralizedTime();
/*  826 */       if (OCSPResponse.debug != null) {
/*  827 */         OCSPResponse.debug.println("thisUpdate: " + this.thisUpdate);
/*      */       }
/*      */ 
/*      */       
/*  831 */       Date date = null;
/*  832 */       Map<String, Extension> map = null;
/*      */ 
/*      */ 
/*      */       
/*  836 */       if (derInputStream.available() > 0) {
/*  837 */         derValue = derInputStream.getDerValue();
/*      */ 
/*      */         
/*  840 */         if (derValue.isContextSpecific((byte)0)) {
/*  841 */           date = derValue.data.getGeneralizedTime();
/*  842 */           if (OCSPResponse.debug != null) {
/*  843 */             OCSPResponse.debug.println("nextUpdate: " + date);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  849 */           derValue = (derInputStream.available() > 0) ? derInputStream.getDerValue() : null;
/*      */         } 
/*      */ 
/*      */         
/*  853 */         if (derValue != null) {
/*  854 */           if (derValue.isContextSpecific((byte)1)) {
/*  855 */             map = OCSPResponse.parseExtensions(derValue);
/*      */ 
/*      */ 
/*      */             
/*  859 */             if (derInputStream.available() > 0) {
/*  860 */               throw new IOException(derInputStream.available() + " bytes of additional data in singleResponse");
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/*  865 */             throw new IOException("Unsupported singleResponse item, tag = " + 
/*  866 */                 String.format("%02X", new Object[] { Byte.valueOf(derValue.tag) }));
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/*  871 */       this.nextUpdate = date;
/*  872 */       this
/*  873 */         .singleExtensions = (map != null) ? map : Collections.<String, Extension>emptyMap();
/*  874 */       if (OCSPResponse.debug != null)
/*      */       {
/*  876 */         for (Extension extension : this.singleExtensions.values()) {
/*  877 */           OCSPResponse.debug.println("singleExtension: " + extension);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OCSP.RevocationStatus.CertStatus getCertStatus() {
/*  887 */       return this.certStatus;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CertId getCertId() {
/*  896 */       return this.certId;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Date getThisUpdate() {
/*  905 */       return (this.thisUpdate != null) ? (Date)this.thisUpdate.clone() : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Date getNextUpdate() {
/*  915 */       return (this.nextUpdate != null) ? (Date)this.nextUpdate.clone() : null;
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
/*      */     public Date getRevocationTime() {
/*  928 */       return (this.revocationTime != null) ? (Date)this.revocationTime.clone() : null;
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
/*      */     public CRLReason getRevocationReason() {
/*  942 */       return this.revocationReason;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map<String, Extension> getSingleExtensions() {
/*  953 */       return Collections.unmodifiableMap(this.singleExtensions);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  960 */       StringBuilder stringBuilder = new StringBuilder();
/*  961 */       stringBuilder.append("SingleResponse:\n");
/*  962 */       stringBuilder.append(this.certId);
/*  963 */       stringBuilder.append("\nCertStatus: ").append(this.certStatus).append("\n");
/*  964 */       if (this.certStatus == OCSP.RevocationStatus.CertStatus.REVOKED) {
/*  965 */         stringBuilder.append("revocationTime is ");
/*  966 */         stringBuilder.append(this.revocationTime).append("\n");
/*  967 */         stringBuilder.append("revocationReason is ");
/*  968 */         stringBuilder.append(this.revocationReason).append("\n");
/*      */       } 
/*  970 */       stringBuilder.append("thisUpdate is ").append(this.thisUpdate).append("\n");
/*  971 */       if (this.nextUpdate != null) {
/*  972 */         stringBuilder.append("nextUpdate is ").append(this.nextUpdate).append("\n");
/*      */       }
/*  974 */       for (Extension extension : this.singleExtensions.values()) {
/*  975 */         stringBuilder.append("singleExtension: ");
/*  976 */         stringBuilder.append(extension.toString()).append("\n");
/*      */       } 
/*  978 */       return stringBuilder.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class IssuerInfo
/*      */   {
/*      */     private final TrustAnchor anchor;
/*      */     
/*      */     private final X509Certificate certificate;
/*      */     
/*      */     private final X500Principal name;
/*      */     
/*      */     private final PublicKey pubKey;
/*      */ 
/*      */     
/*      */     IssuerInfo(TrustAnchor param1TrustAnchor) {
/*  996 */       this(param1TrustAnchor, (param1TrustAnchor != null) ? param1TrustAnchor.getTrustedCert() : null);
/*      */     }
/*      */     
/*      */     IssuerInfo(X509Certificate param1X509Certificate) {
/* 1000 */       this(null, param1X509Certificate);
/*      */     }
/*      */     
/*      */     IssuerInfo(TrustAnchor param1TrustAnchor, X509Certificate param1X509Certificate) {
/* 1004 */       if (param1TrustAnchor == null && param1X509Certificate == null) {
/* 1005 */         throw new NullPointerException("TrustAnchor and issuerCert cannot be null");
/*      */       }
/*      */       
/* 1008 */       this.anchor = param1TrustAnchor;
/* 1009 */       if (param1X509Certificate != null) {
/* 1010 */         this.name = param1X509Certificate.getSubjectX500Principal();
/* 1011 */         this.pubKey = param1X509Certificate.getPublicKey();
/* 1012 */         this.certificate = param1X509Certificate;
/*      */       } else {
/* 1014 */         this.name = param1TrustAnchor.getCA();
/* 1015 */         this.pubKey = param1TrustAnchor.getCAPublicKey();
/* 1016 */         this.certificate = param1TrustAnchor.getTrustedCert();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     X509Certificate getCertificate() {
/* 1028 */       return this.certificate;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     X500Principal getName() {
/* 1039 */       return this.name;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     PublicKey getPublicKey() {
/* 1048 */       return this.pubKey;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TrustAnchor getAnchor() {
/* 1057 */       return this.anchor;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1067 */       StringBuilder stringBuilder = new StringBuilder();
/* 1068 */       stringBuilder.append("Issuer Info:\n");
/* 1069 */       stringBuilder.append("Name: ").append(this.name.toString()).append("\n");
/* 1070 */       stringBuilder.append("Public Key:\n").append(this.pubKey.toString()).append("\n");
/* 1071 */       return stringBuilder.toString();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/OCSPResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */