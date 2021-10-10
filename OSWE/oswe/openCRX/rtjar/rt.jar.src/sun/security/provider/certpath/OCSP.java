/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.cert.CRLReason;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.Extension;
/*     */ import java.security.cert.TrustAnchor;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.x509.AccessDescription;
/*     */ import sun.security.x509.AuthorityInfoAccessExtension;
/*     */ import sun.security.x509.GeneralName;
/*     */ import sun.security.x509.PKIXExtensions;
/*     */ import sun.security.x509.URIName;
/*     */ import sun.security.x509.X509CertImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OCSP
/*     */ {
/*  69 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_CONNECT_TIMEOUT = 15000;
/*     */ 
/*     */ 
/*     */   
/*  78 */   private static final int CONNECT_TIMEOUT = initializeTimeout();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int initializeTimeout() {
/*  86 */     Integer integer = AccessController.<Integer>doPrivileged(new GetIntegerAction("com.sun.security.ocsp.timeout"));
/*     */     
/*  88 */     if (integer == null || integer.intValue() < 0) {
/*  89 */       return 15000;
/*     */     }
/*     */ 
/*     */     
/*  93 */     return integer.intValue() * 1000;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RevocationStatus check(X509Certificate paramX509Certificate1, X509Certificate paramX509Certificate2, URI paramURI, X509Certificate paramX509Certificate3, Date paramDate) throws IOException, CertPathValidatorException {
/* 123 */     return check(paramX509Certificate1, paramX509Certificate2, paramURI, paramX509Certificate3, paramDate, 
/* 124 */         Collections.emptyList(), "generic");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RevocationStatus check(X509Certificate paramX509Certificate1, X509Certificate paramX509Certificate2, URI paramURI, X509Certificate paramX509Certificate3, Date paramDate, List<Extension> paramList, String paramString) throws IOException, CertPathValidatorException {
/* 134 */     return check(paramX509Certificate1, paramURI, null, paramX509Certificate2, paramX509Certificate3, paramDate, paramList, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RevocationStatus check(X509Certificate paramX509Certificate1, URI paramURI, TrustAnchor paramTrustAnchor, X509Certificate paramX509Certificate2, X509Certificate paramX509Certificate3, Date paramDate, List<Extension> paramList, String paramString) throws IOException, CertPathValidatorException {
/*     */     CertId certId;
/*     */     try {
/* 146 */       X509CertImpl x509CertImpl = X509CertImpl.toImpl(paramX509Certificate1);
/* 147 */       certId = new CertId(paramX509Certificate2, x509CertImpl.getSerialNumberObject());
/* 148 */     } catch (CertificateException|IOException certificateException) {
/* 149 */       throw new CertPathValidatorException("Exception while encoding OCSPRequest", certificateException);
/*     */     } 
/*     */     
/* 152 */     OCSPResponse oCSPResponse = check(Collections.singletonList(certId), paramURI, new OCSPResponse.IssuerInfo(paramTrustAnchor, paramX509Certificate2), paramX509Certificate3, paramDate, paramList, paramString);
/*     */ 
/*     */     
/* 155 */     return oCSPResponse.getSingleResponse(certId);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static OCSPResponse check(List<CertId> paramList, URI paramURI, OCSPResponse.IssuerInfo paramIssuerInfo, X509Certificate paramX509Certificate, Date paramDate, List<Extension> paramList1, String paramString) throws IOException, CertPathValidatorException {
/* 182 */     byte[] arrayOfByte = null;
/* 183 */     for (Extension extension : paramList1) {
/* 184 */       if (extension.getId().equals(PKIXExtensions.OCSPNonce_Id.toString())) {
/* 185 */         arrayOfByte = extension.getValue();
/*     */       }
/*     */     } 
/*     */     
/* 189 */     OCSPResponse oCSPResponse = null;
/*     */     try {
/* 191 */       byte[] arrayOfByte1 = getOCSPBytes(paramList, paramURI, paramList1);
/* 192 */       oCSPResponse = new OCSPResponse(arrayOfByte1);
/*     */ 
/*     */       
/* 195 */       oCSPResponse.verify(paramList, paramIssuerInfo, paramX509Certificate, paramDate, arrayOfByte, paramString);
/*     */     }
/* 197 */     catch (IOException iOException) {
/* 198 */       throw new CertPathValidatorException("Unable to determine revocation status due to network error", iOException, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 203 */     return oCSPResponse;
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
/*     */   public static byte[] getOCSPBytes(List<CertId> paramList, URI paramURI, List<Extension> paramList1) throws IOException {
/* 223 */     OCSPRequest oCSPRequest = new OCSPRequest(paramList, paramList1);
/* 224 */     byte[] arrayOfByte1 = oCSPRequest.encodeBytes();
/*     */     
/* 226 */     InputStream inputStream = null;
/* 227 */     OutputStream outputStream = null;
/* 228 */     byte[] arrayOfByte2 = null;
/*     */     
/*     */     try {
/* 231 */       URL uRL = paramURI.toURL();
/* 232 */       if (debug != null) {
/* 233 */         debug.println("connecting to OCSP service at: " + uRL);
/*     */       }
/* 235 */       HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
/* 236 */       httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
/* 237 */       httpURLConnection.setReadTimeout(CONNECT_TIMEOUT);
/* 238 */       httpURLConnection.setDoOutput(true);
/* 239 */       httpURLConnection.setDoInput(true);
/* 240 */       httpURLConnection.setRequestMethod("POST");
/* 241 */       httpURLConnection
/* 242 */         .setRequestProperty("Content-type", "application/ocsp-request");
/* 243 */       httpURLConnection
/* 244 */         .setRequestProperty("Content-length", String.valueOf(arrayOfByte1.length));
/* 245 */       outputStream = httpURLConnection.getOutputStream();
/* 246 */       outputStream.write(arrayOfByte1);
/* 247 */       outputStream.flush();
/*     */       
/* 249 */       if (debug != null && httpURLConnection
/* 250 */         .getResponseCode() != 200) {
/* 251 */         debug.println("Received HTTP error: " + httpURLConnection.getResponseCode() + " - " + httpURLConnection
/* 252 */             .getResponseMessage());
/*     */       }
/* 254 */       inputStream = httpURLConnection.getInputStream();
/* 255 */       int i = httpURLConnection.getContentLength();
/* 256 */       if (i == -1) {
/* 257 */         i = Integer.MAX_VALUE;
/*     */       }
/* 259 */       arrayOfByte2 = new byte[(i > 2048) ? 2048 : i];
/* 260 */       int j = 0;
/* 261 */       while (j < i) {
/* 262 */         int k = inputStream.read(arrayOfByte2, j, arrayOfByte2.length - j);
/* 263 */         if (k < 0) {
/*     */           break;
/*     */         }
/* 266 */         j += k;
/* 267 */         if (j >= arrayOfByte2.length && j < i) {
/* 268 */           arrayOfByte2 = Arrays.copyOf(arrayOfByte2, j * 2);
/*     */         }
/*     */       } 
/* 271 */       arrayOfByte2 = Arrays.copyOf(arrayOfByte2, j);
/*     */     } finally {
/* 273 */       if (inputStream != null) {
/*     */         try {
/* 275 */           inputStream.close();
/* 276 */         } catch (IOException iOException) {
/* 277 */           throw iOException;
/*     */         } 
/*     */       }
/* 280 */       if (outputStream != null) {
/*     */         try {
/* 282 */           outputStream.close();
/* 283 */         } catch (IOException iOException) {
/* 284 */           throw iOException;
/*     */         } 
/*     */       }
/*     */     } 
/* 288 */     return arrayOfByte2;
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
/*     */   public static URI getResponderURI(X509Certificate paramX509Certificate) {
/*     */     try {
/* 302 */       return getResponderURI(X509CertImpl.toImpl(paramX509Certificate));
/* 303 */     } catch (CertificateException certificateException) {
/*     */       
/* 305 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static URI getResponderURI(X509CertImpl paramX509CertImpl) {
/* 313 */     AuthorityInfoAccessExtension authorityInfoAccessExtension = paramX509CertImpl.getAuthorityInfoAccessExtension();
/* 314 */     if (authorityInfoAccessExtension == null) {
/* 315 */       return null;
/*     */     }
/*     */     
/* 318 */     List<AccessDescription> list = authorityInfoAccessExtension.getAccessDescriptions();
/* 319 */     for (AccessDescription accessDescription : list) {
/* 320 */       if (accessDescription.getAccessMethod().equals(AccessDescription.Ad_OCSP_Id)) {
/*     */ 
/*     */         
/* 323 */         GeneralName generalName = accessDescription.getAccessLocation();
/* 324 */         if (generalName.getType() == 6) {
/* 325 */           URIName uRIName = (URIName)generalName.getName();
/* 326 */           return uRIName.getURI();
/*     */         } 
/*     */       } 
/*     */     } 
/* 330 */     return null;
/*     */   }
/*     */   public static interface RevocationStatus { CertStatus getCertStatus();
/*     */     Date getRevocationTime();
/*     */     CRLReason getRevocationReason();
/*     */     Map<String, Extension> getSingleExtensions();
/*     */     
/* 337 */     public enum CertStatus { GOOD, REVOKED, UNKNOWN; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/OCSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */