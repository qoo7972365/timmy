/*     */ package sun.security.x509;
/*     */ 
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
/*     */ public class PKIXExtensions
/*     */ {
/*  52 */   private static final int[] AuthorityKey_data = new int[] { 2, 5, 29, 35 };
/*  53 */   private static final int[] SubjectKey_data = new int[] { 2, 5, 29, 14 };
/*  54 */   private static final int[] KeyUsage_data = new int[] { 2, 5, 29, 15 };
/*  55 */   private static final int[] PrivateKeyUsage_data = new int[] { 2, 5, 29, 16 };
/*  56 */   private static final int[] CertificatePolicies_data = new int[] { 2, 5, 29, 32 };
/*  57 */   private static final int[] PolicyMappings_data = new int[] { 2, 5, 29, 33 };
/*  58 */   private static final int[] SubjectAlternativeName_data = new int[] { 2, 5, 29, 17 };
/*  59 */   private static final int[] IssuerAlternativeName_data = new int[] { 2, 5, 29, 18 };
/*  60 */   private static final int[] SubjectDirectoryAttributes_data = new int[] { 2, 5, 29, 9 };
/*  61 */   private static final int[] BasicConstraints_data = new int[] { 2, 5, 29, 19 };
/*  62 */   private static final int[] NameConstraints_data = new int[] { 2, 5, 29, 30 };
/*  63 */   private static final int[] PolicyConstraints_data = new int[] { 2, 5, 29, 36 };
/*  64 */   private static final int[] CRLDistributionPoints_data = new int[] { 2, 5, 29, 31 };
/*  65 */   private static final int[] CRLNumber_data = new int[] { 2, 5, 29, 20 };
/*  66 */   private static final int[] IssuingDistributionPoint_data = new int[] { 2, 5, 29, 28 };
/*  67 */   private static final int[] DeltaCRLIndicator_data = new int[] { 2, 5, 29, 27 };
/*  68 */   private static final int[] ReasonCode_data = new int[] { 2, 5, 29, 21 };
/*  69 */   private static final int[] HoldInstructionCode_data = new int[] { 2, 5, 29, 23 };
/*  70 */   private static final int[] InvalidityDate_data = new int[] { 2, 5, 29, 24 };
/*  71 */   private static final int[] ExtendedKeyUsage_data = new int[] { 2, 5, 29, 37 };
/*  72 */   private static final int[] InhibitAnyPolicy_data = new int[] { 2, 5, 29, 54 };
/*  73 */   private static final int[] CertificateIssuer_data = new int[] { 2, 5, 29, 29 };
/*  74 */   private static final int[] AuthInfoAccess_data = new int[] { 1, 3, 6, 1, 5, 5, 7, 1, 1 };
/*  75 */   private static final int[] SubjectInfoAccess_data = new int[] { 1, 3, 6, 1, 5, 5, 7, 1, 11 };
/*  76 */   private static final int[] FreshestCRL_data = new int[] { 2, 5, 29, 46 };
/*  77 */   private static final int[] OCSPNoCheck_data = new int[] { 1, 3, 6, 1, 5, 5, 7, 48, 1, 5 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   private static final int[] OCSPNonce_data = new int[] { 1, 3, 6, 1, 5, 5, 7, 48, 1, 2 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 241 */   public static final ObjectIdentifier AuthorityKey_Id = ObjectIdentifier.newInternal(AuthorityKey_data);
/* 242 */   public static final ObjectIdentifier SubjectKey_Id = ObjectIdentifier.newInternal(SubjectKey_data);
/* 243 */   public static final ObjectIdentifier KeyUsage_Id = ObjectIdentifier.newInternal(KeyUsage_data);
/* 244 */   public static final ObjectIdentifier PrivateKeyUsage_Id = ObjectIdentifier.newInternal(PrivateKeyUsage_data);
/*     */   
/* 246 */   public static final ObjectIdentifier CertificatePolicies_Id = ObjectIdentifier.newInternal(CertificatePolicies_data);
/* 247 */   public static final ObjectIdentifier PolicyMappings_Id = ObjectIdentifier.newInternal(PolicyMappings_data);
/*     */   
/* 249 */   public static final ObjectIdentifier SubjectAlternativeName_Id = ObjectIdentifier.newInternal(SubjectAlternativeName_data);
/*     */   
/* 251 */   public static final ObjectIdentifier IssuerAlternativeName_Id = ObjectIdentifier.newInternal(IssuerAlternativeName_data); public static final ObjectIdentifier SubjectDirectoryAttributes_Id; public static final ObjectIdentifier BasicConstraints_Id; public static final ObjectIdentifier NameConstraints_Id; public static final ObjectIdentifier PolicyConstraints_Id; public static final ObjectIdentifier CRLDistributionPoints_Id;
/* 252 */   public static final ObjectIdentifier ExtendedKeyUsage_Id = ObjectIdentifier.newInternal(ExtendedKeyUsage_data); public static final ObjectIdentifier CRLNumber_Id; public static final ObjectIdentifier IssuingDistributionPoint_Id; public static final ObjectIdentifier DeltaCRLIndicator_Id; public static final ObjectIdentifier ReasonCode_Id; public static final ObjectIdentifier HoldInstructionCode_Id; public static final ObjectIdentifier InvalidityDate_Id;
/* 253 */   public static final ObjectIdentifier InhibitAnyPolicy_Id = ObjectIdentifier.newInternal(InhibitAnyPolicy_data); public static final ObjectIdentifier CertificateIssuer_Id; public static final ObjectIdentifier AuthInfoAccess_Id; public static final ObjectIdentifier SubjectInfoAccess_Id; public static final ObjectIdentifier FreshestCRL_Id; public static final ObjectIdentifier OCSPNoCheck_Id; public static final ObjectIdentifier OCSPNonce_Id;
/*     */   static {
/* 255 */     SubjectDirectoryAttributes_Id = ObjectIdentifier.newInternal(SubjectDirectoryAttributes_data);
/*     */     
/* 257 */     BasicConstraints_Id = ObjectIdentifier.newInternal(BasicConstraints_data);
/* 258 */     ReasonCode_Id = ObjectIdentifier.newInternal(ReasonCode_data);
/*     */     
/* 260 */     HoldInstructionCode_Id = ObjectIdentifier.newInternal(HoldInstructionCode_data);
/* 261 */     InvalidityDate_Id = ObjectIdentifier.newInternal(InvalidityDate_data);
/*     */     
/* 263 */     NameConstraints_Id = ObjectIdentifier.newInternal(NameConstraints_data);
/*     */     
/* 265 */     PolicyConstraints_Id = ObjectIdentifier.newInternal(PolicyConstraints_data);
/*     */     
/* 267 */     CRLDistributionPoints_Id = ObjectIdentifier.newInternal(CRLDistributionPoints_data);
/*     */     
/* 269 */     CRLNumber_Id = ObjectIdentifier.newInternal(CRLNumber_data);
/*     */     
/* 271 */     IssuingDistributionPoint_Id = ObjectIdentifier.newInternal(IssuingDistributionPoint_data);
/*     */     
/* 273 */     DeltaCRLIndicator_Id = ObjectIdentifier.newInternal(DeltaCRLIndicator_data);
/*     */     
/* 275 */     CertificateIssuer_Id = ObjectIdentifier.newInternal(CertificateIssuer_data);
/*     */     
/* 277 */     AuthInfoAccess_Id = ObjectIdentifier.newInternal(AuthInfoAccess_data);
/*     */     
/* 279 */     SubjectInfoAccess_Id = ObjectIdentifier.newInternal(SubjectInfoAccess_data);
/* 280 */     FreshestCRL_Id = ObjectIdentifier.newInternal(FreshestCRL_data);
/* 281 */     OCSPNoCheck_Id = ObjectIdentifier.newInternal(OCSPNoCheck_data);
/* 282 */     OCSPNonce_Id = ObjectIdentifier.newInternal(OCSPNonce_data);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/PKIXExtensions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */