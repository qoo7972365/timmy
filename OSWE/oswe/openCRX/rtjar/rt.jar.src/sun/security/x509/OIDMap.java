/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OIDMap
/*     */ {
/*     */   private static final String ROOT = "x509.info.extensions";
/*     */   private static final String AUTH_KEY_IDENTIFIER = "x509.info.extensions.AuthorityKeyIdentifier";
/*     */   private static final String SUB_KEY_IDENTIFIER = "x509.info.extensions.SubjectKeyIdentifier";
/*     */   private static final String KEY_USAGE = "x509.info.extensions.KeyUsage";
/*     */   private static final String PRIVATE_KEY_USAGE = "x509.info.extensions.PrivateKeyUsage";
/*     */   private static final String POLICY_MAPPINGS = "x509.info.extensions.PolicyMappings";
/*     */   private static final String SUB_ALT_NAME = "x509.info.extensions.SubjectAlternativeName";
/*     */   private static final String ISSUER_ALT_NAME = "x509.info.extensions.IssuerAlternativeName";
/*     */   private static final String BASIC_CONSTRAINTS = "x509.info.extensions.BasicConstraints";
/*     */   private static final String NAME_CONSTRAINTS = "x509.info.extensions.NameConstraints";
/*     */   private static final String POLICY_CONSTRAINTS = "x509.info.extensions.PolicyConstraints";
/*     */   private static final String CRL_NUMBER = "x509.info.extensions.CRLNumber";
/*     */   private static final String CRL_REASON = "x509.info.extensions.CRLReasonCode";
/*     */   private static final String NETSCAPE_CERT = "x509.info.extensions.NetscapeCertType";
/*     */   private static final String CERT_POLICIES = "x509.info.extensions.CertificatePolicies";
/*     */   private static final String EXT_KEY_USAGE = "x509.info.extensions.ExtendedKeyUsage";
/*     */   private static final String INHIBIT_ANY_POLICY = "x509.info.extensions.InhibitAnyPolicy";
/*     */   private static final String CRL_DIST_POINTS = "x509.info.extensions.CRLDistributionPoints";
/*     */   private static final String CERT_ISSUER = "x509.info.extensions.CertificateIssuer";
/*     */   private static final String SUBJECT_INFO_ACCESS = "x509.info.extensions.SubjectInfoAccess";
/*     */   private static final String AUTH_INFO_ACCESS = "x509.info.extensions.AuthorityInfoAccess";
/*     */   private static final String ISSUING_DIST_POINT = "x509.info.extensions.IssuingDistributionPoint";
/*     */   private static final String DELTA_CRL_INDICATOR = "x509.info.extensions.DeltaCRLIndicator";
/*     */   private static final String FRESHEST_CRL = "x509.info.extensions.FreshestCRL";
/*     */   private static final String OCSPNOCHECK = "x509.info.extensions.OCSPNoCheck";
/* 105 */   private static final int[] NetscapeCertType_data = new int[] { 2, 16, 840, 1, 113730, 1, 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   private static final Map<ObjectIdentifier, OIDInfo> oidMap = new HashMap<>();
/* 116 */   private static final Map<String, OIDInfo> nameMap = new HashMap<>(); static {
/* 117 */     addInternal("x509.info.extensions.SubjectKeyIdentifier", PKIXExtensions.SubjectKey_Id, "sun.security.x509.SubjectKeyIdentifierExtension");
/*     */     
/* 119 */     addInternal("x509.info.extensions.KeyUsage", PKIXExtensions.KeyUsage_Id, "sun.security.x509.KeyUsageExtension");
/*     */     
/* 121 */     addInternal("x509.info.extensions.PrivateKeyUsage", PKIXExtensions.PrivateKeyUsage_Id, "sun.security.x509.PrivateKeyUsageExtension");
/*     */     
/* 123 */     addInternal("x509.info.extensions.SubjectAlternativeName", PKIXExtensions.SubjectAlternativeName_Id, "sun.security.x509.SubjectAlternativeNameExtension");
/*     */     
/* 125 */     addInternal("x509.info.extensions.IssuerAlternativeName", PKIXExtensions.IssuerAlternativeName_Id, "sun.security.x509.IssuerAlternativeNameExtension");
/*     */     
/* 127 */     addInternal("x509.info.extensions.BasicConstraints", PKIXExtensions.BasicConstraints_Id, "sun.security.x509.BasicConstraintsExtension");
/*     */     
/* 129 */     addInternal("x509.info.extensions.CRLNumber", PKIXExtensions.CRLNumber_Id, "sun.security.x509.CRLNumberExtension");
/*     */     
/* 131 */     addInternal("x509.info.extensions.CRLReasonCode", PKIXExtensions.ReasonCode_Id, "sun.security.x509.CRLReasonCodeExtension");
/*     */     
/* 133 */     addInternal("x509.info.extensions.NameConstraints", PKIXExtensions.NameConstraints_Id, "sun.security.x509.NameConstraintsExtension");
/*     */     
/* 135 */     addInternal("x509.info.extensions.PolicyMappings", PKIXExtensions.PolicyMappings_Id, "sun.security.x509.PolicyMappingsExtension");
/*     */     
/* 137 */     addInternal("x509.info.extensions.AuthorityKeyIdentifier", PKIXExtensions.AuthorityKey_Id, "sun.security.x509.AuthorityKeyIdentifierExtension");
/*     */     
/* 139 */     addInternal("x509.info.extensions.PolicyConstraints", PKIXExtensions.PolicyConstraints_Id, "sun.security.x509.PolicyConstraintsExtension");
/*     */     
/* 141 */     addInternal("x509.info.extensions.NetscapeCertType", 
/* 142 */         ObjectIdentifier.newInternal(new int[] { 2, 16, 840, 1, 113730, 1, 1 }, ), "sun.security.x509.NetscapeCertTypeExtension");
/*     */     
/* 144 */     addInternal("x509.info.extensions.CertificatePolicies", PKIXExtensions.CertificatePolicies_Id, "sun.security.x509.CertificatePoliciesExtension");
/*     */     
/* 146 */     addInternal("x509.info.extensions.ExtendedKeyUsage", PKIXExtensions.ExtendedKeyUsage_Id, "sun.security.x509.ExtendedKeyUsageExtension");
/*     */     
/* 148 */     addInternal("x509.info.extensions.InhibitAnyPolicy", PKIXExtensions.InhibitAnyPolicy_Id, "sun.security.x509.InhibitAnyPolicyExtension");
/*     */     
/* 150 */     addInternal("x509.info.extensions.CRLDistributionPoints", PKIXExtensions.CRLDistributionPoints_Id, "sun.security.x509.CRLDistributionPointsExtension");
/*     */     
/* 152 */     addInternal("x509.info.extensions.CertificateIssuer", PKIXExtensions.CertificateIssuer_Id, "sun.security.x509.CertificateIssuerExtension");
/*     */     
/* 154 */     addInternal("x509.info.extensions.SubjectInfoAccess", PKIXExtensions.SubjectInfoAccess_Id, "sun.security.x509.SubjectInfoAccessExtension");
/*     */     
/* 156 */     addInternal("x509.info.extensions.AuthorityInfoAccess", PKIXExtensions.AuthInfoAccess_Id, "sun.security.x509.AuthorityInfoAccessExtension");
/*     */     
/* 158 */     addInternal("x509.info.extensions.IssuingDistributionPoint", PKIXExtensions.IssuingDistributionPoint_Id, "sun.security.x509.IssuingDistributionPointExtension");
/*     */ 
/*     */     
/* 161 */     addInternal("x509.info.extensions.DeltaCRLIndicator", PKIXExtensions.DeltaCRLIndicator_Id, "sun.security.x509.DeltaCRLIndicatorExtension");
/*     */     
/* 163 */     addInternal("x509.info.extensions.FreshestCRL", PKIXExtensions.FreshestCRL_Id, "sun.security.x509.FreshestCRLExtension");
/*     */     
/* 165 */     addInternal("x509.info.extensions.OCSPNoCheck", PKIXExtensions.OCSPNoCheck_Id, "sun.security.x509.OCSPNoCheckExtension");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addInternal(String paramString1, ObjectIdentifier paramObjectIdentifier, String paramString2) {
/* 175 */     OIDInfo oIDInfo = new OIDInfo(paramString1, paramObjectIdentifier, paramString2);
/* 176 */     oidMap.put(paramObjectIdentifier, oIDInfo);
/* 177 */     nameMap.put(paramString1, oIDInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class OIDInfo
/*     */   {
/*     */     final ObjectIdentifier oid;
/*     */     
/*     */     final String name;
/*     */     
/*     */     final String className;
/*     */     private volatile Class<?> clazz;
/*     */     
/*     */     OIDInfo(String param1String1, ObjectIdentifier param1ObjectIdentifier, String param1String2) {
/* 191 */       this.name = param1String1;
/* 192 */       this.oid = param1ObjectIdentifier;
/* 193 */       this.className = param1String2;
/*     */     }
/*     */     
/*     */     OIDInfo(String param1String, ObjectIdentifier param1ObjectIdentifier, Class<?> param1Class) {
/* 197 */       this.name = param1String;
/* 198 */       this.oid = param1ObjectIdentifier;
/* 199 */       this.className = param1Class.getName();
/* 200 */       this.clazz = param1Class;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Class<?> getClazz() throws CertificateException {
/*     */       try {
/* 208 */         Class<?> clazz = this.clazz;
/* 209 */         if (clazz == null) {
/* 210 */           clazz = Class.forName(this.className);
/* 211 */           this.clazz = clazz;
/*     */         } 
/* 213 */         return clazz;
/* 214 */       } catch (ClassNotFoundException classNotFoundException) {
/* 215 */         throw new CertificateException("Could not load class: " + classNotFoundException, classNotFoundException);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addAttribute(String paramString1, String paramString2, Class<?> paramClass) throws CertificateException {
/*     */     ObjectIdentifier objectIdentifier;
/*     */     try {
/* 233 */       objectIdentifier = new ObjectIdentifier(paramString2);
/* 234 */     } catch (IOException iOException) {
/* 235 */       throw new CertificateException("Invalid Object identifier: " + paramString2);
/*     */     } 
/*     */     
/* 238 */     OIDInfo oIDInfo = new OIDInfo(paramString1, objectIdentifier, paramClass);
/* 239 */     if (oidMap.put(objectIdentifier, oIDInfo) != null) {
/* 240 */       throw new CertificateException("Object identifier already exists: " + paramString2);
/*     */     }
/*     */     
/* 243 */     if (nameMap.put(paramString1, oIDInfo) != null) {
/* 244 */       throw new CertificateException("Name already exists: " + paramString1);
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
/*     */   public static String getName(ObjectIdentifier paramObjectIdentifier) {
/* 256 */     OIDInfo oIDInfo = oidMap.get(paramObjectIdentifier);
/* 257 */     return (oIDInfo == null) ? null : oIDInfo.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObjectIdentifier getOID(String paramString) {
/* 268 */     OIDInfo oIDInfo = nameMap.get(paramString);
/* 269 */     return (oIDInfo == null) ? null : oIDInfo.oid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> getClass(String paramString) throws CertificateException {
/* 279 */     OIDInfo oIDInfo = nameMap.get(paramString);
/* 280 */     return (oIDInfo == null) ? null : oIDInfo.getClazz();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> getClass(ObjectIdentifier paramObjectIdentifier) throws CertificateException {
/* 291 */     OIDInfo oIDInfo = oidMap.get(paramObjectIdentifier);
/* 292 */     return (oIDInfo == null) ? null : oIDInfo.getClazz();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/OIDMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */